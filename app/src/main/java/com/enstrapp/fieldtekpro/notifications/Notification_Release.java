package com.enstrapp.fieldtekpro.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Notification_Release {

    private static String Get_Data = "", Aufnr = "", password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> Get_Notif_Release_Data(Context context, String notification_id) {
        try {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = context
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",
                    null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ?" +
                    " and Zactivity = ? and Endpoint = ?", new String[]{"Q", "RL", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            } else {
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = context.getString(R.string.ip_address);
            Map<String, String> map = new HashMap<>();
            map.put("IvUser", username);
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvTransmitType", "");
            map.put("qmnum", notification_id);
            map.put("Operation", "RLNOT");
            map.put("Ivcommit", "X");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120000, TimeUnit.MILLISECONDS)
                    .writeTimeout(120000, TimeUnit.SECONDS)
                    .readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),
                    Base64.NO_WRAP);
            Call<Notifications_SER> call = service.Notif_Release(url_link, basic, map);
            Response<Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    /*Reading Response Data and Parsing to Serializable*/
                    StringBuilder Message_stringbuilder = new StringBuilder();
                    if (response.body().getD().getResults().get(0).getEvMessage().getResults()!=null) {
                        if (response.body().getD().getResults().get(0).getEvMessage().getResults() != null
                                && response.body().getD().getResults().get(0).getEvMessage().getResults().size() > 0) {
                            ContentValues values = new ContentValues();

                            for (Notifications_SER.EvMessage_Result eM : response.body().getD().getResults().get(0).getEvMessage().getResults()) {
                                values.put("Message", eM.getMessage());
                                values.put("Qmnum", eM.getQmnum());
                                String Message = eM.getMessage();
                                String qmnum = eM.getQmnum();
                                Message_stringbuilder.append(Message);
                            }
                        }
                    }

                                String message = Message_stringbuilder.toString();
                                String qmnum =" ";
                                if (message.startsWith("S")) {
                                    if (message.contains("Notification already released!")) {
                                        Get_Response = "already released";
                                        Get_Data = "";
                                    } else if (message.contains("Notification released successfully")) {

                                        App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from EtNotifHeader_CustomInfo where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from EtNotifItems_CustomInfo where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from EtNotifActivity_CustomInfo where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from EtNotifStatus where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks where Qmnum = ?", new String[]{notification_id});
                                        App_db.execSQL("delete from EtNotifTask_CustomInfo where Qmnum = ?", new String[]{notification_id});


                                        App_db.beginTransaction();
                                        try {
                                            try {
                                                if (response.body().getD().getResults().get(0).getEtNotifHeader() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifHeader().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifHeader().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifHeader_Result eN : response.body().getD().getResults()
                                                                .get(0).getEtNotifHeader().getResults()) {
                                                            values.put("UUID", eN.getQmnum());
                                                            qmnum = eN.getQmnum();
                                                            values.put("NotifType", eN.getNotifType());
                                                            values.put("Qmnum", eN.getQmnum());
                                                            values.put("NotifShorttxt", eN.getNotifShorttxt());
                                                            values.put("FunctionLoc", eN.getFunctionLoc());
                                                            values.put("Equipment", eN.getEquipment());
                                                            values.put("Bautl", eN.getBautl());
                                                            values.put("ReportedBy", eN.getReportedBy());
                                                            values.put("MalfuncStdate", eN.getMalfuncStdate());
                                                            values.put("MalfuncEddate", eN.getMalfuncEddate());
                                                            values.put("MalfuncSttime", eN.getMalfuncSttime());
                                                            values.put("MalfuncEdtime", eN.getMalfuncEdtime());
                                                            values.put("BreakdownInd", eN.getBreakdownInd());
                                                            values.put("Priority", eN.getPriority());
                                                            values.put("Ingrp", eN.getIngrp());
                                                            values.put("Arbpl", eN.getArbpl());
                                                            values.put("Werks", eN.getWerks());
                                                            values.put("Strmn", eN.getStrmn());
                                                            values.put("Ltrmn", eN.getLtrmn());
                                                            values.put("Aufnr", eN.getAufnr());
                                                            values.put("Docs", eN.getDocs());
                                                            values.put("Altitude", eN.getAltitude());
                                                            values.put("Latitude", eN.getLatitude());
                                                            values.put("Longitude", eN.getLongitude());
                                                            values.put("Closed", eN.getClosed());
                                                            values.put("Completed", eN.getCompleted());
                                                            values.put("Createdon", eN.getCreatedon());
                                                            values.put("Qmartx", eN.getQmartx());
                                                            values.put("Pltxt", eN.getPltxt());
                                                            values.put("Eqktx", eN.getEqktx());
                                                            values.put("Priokx", eN.getPriokx());
                                                            values.put("Auftext", eN.getAuftext());
                                                            values.put("Auarttext", eN.getAuarttext());
                                                            values.put("Plantname", eN.getPlantname());
                                                            values.put("Wkctrname", eN.getWkctrname());
                                                            values.put("Ingrpname", eN.getIngrpname());
                                                            values.put("Maktx", eN.getMaktx());
                                                            values.put("Xstatus", eN.getXstatus());
                                                            values.put("Usr01", eN.getUsr01());
                                                            values.put("Usr02", eN.getUsr02());
                                                            values.put("Usr03", eN.getUsr03());
                                                            values.put("Usr04", eN.getUsr04());
                                                            values.put("Usr05", eN.getUsr05());
                                                            values.put("STATUS", eN.getXstatus());
                                                            values.put("ParnrVw", eN.getParnrVw());
                                                            values.put("NameVw", eN.getNameVw());
                                                            values.put("Auswk", eN.getAuswk());
                                                            values.put("Shift", eN.getShift());
                                                            values.put("Noofperson", eN.getNoofperson());
                                                            values.put("Auswkt", eN.getAuswkt());
                                                            values.put("Strur", eN.getStrur());
                                                            values.put("Ltrur", eN.getLtrur());
                                                            values.put("sort_malfc", eN.getStrmn() + " " + eN.getStrur());
                                                            values.put("Qmdat", eN.getQmdat());
                                                            if (eN.getEtCustomFields() != null)
                                                                if (eN.getEtCustomFields().getResults() != null
                                                                        && eN.getEtCustomFields().getResults().size() > 0) {
                                                                    ContentValues ValuesHCf = new ContentValues();
                                                                    for (Notifications_SER.CustomFields_Result customFields_result : eN.getEtCustomFields().getResults()) {
                                                                        ValuesHCf.put("UUID", eN.getQmnum());
                                                                        ValuesHCf.put("Qmnum", qmnum);
                                                                        ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                                        ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                                        ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                                        ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                                        ValuesHCf.put("Value", customFields_result.getValue());
                                                                        ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                                        ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                                        ValuesHCf.put("Length", customFields_result.getLength());
                                                                        ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                                        App_db.insert("EtOrderHeader_CustomInfo", null, ValuesHCf);
                                                                    }
                                                                }
                                                            App_db.insert("DUE_NOTIFICATION_NotifHeader", null, values);
                                                        }
                                                    }
                                                }

                                            }catch (Exception e)
                                            {

                                            }
                                            /*Reading and Inserting Data into Database Table for EtNotifHeader*/
                                /*if (jsonObject.has("EtNotifHeader")) {
                                    try {
                                        String EtNotifHeader_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifHeader().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifHeader_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifHeader_sql = "Insert into DUE_NOTIFICATION_NotifHeader (UUID,NotifType,Qmnum,NotifShorttxt,FunctionLoc,Equipment,Bautl,ReportedBy,MalfuncStdate,MalfuncEddate,MalfuncSttime,MalfuncEdtime,BreakdownInd,Priority,Ingrp,Arbpl,Werks,Strmn,Ltrmn,Aufnr,Docs,Altitude,Latitude,Longitude,Closed,Completed,Createdon,Qmartx,Pltxt,Eqktx,Priokx,Auftext,Auarttext,Plantname,Wkctrname,Ingrpname,Maktx,Xstatus,Usr01,Usr02,Usr03,Usr04,Usr05,STATUS,ParnrVw,NameVw,Auswk,Shift,Noofperson,Auswkt, Strur, Ltrur, sort_malfc, Qmdat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtNotifHeader_statement = App_db.compileStatement(EtNotifHeader_sql);
                                                    EtNotifHeader_statement.clearBindings();
                                                    EtNotifHeader_statement.bindString(1, uuid);
                                                    EtNotifHeader_statement.bindString(2, jsonArray.getJSONObject(j).optString("NotifType"));
                                                    EtNotifHeader_statement.bindString(3, Qmnum);
                                                    EtNotifHeader_statement.bindString(4, jsonArray.getJSONObject(j).optString("NotifShorttxt"));
                                                    EtNotifHeader_statement.bindString(5, jsonArray.getJSONObject(j).optString("FunctionLoc"));
                                                    EtNotifHeader_statement.bindString(6, jsonArray.getJSONObject(j).optString("Equipment"));
                                                    EtNotifHeader_statement.bindString(7, jsonArray.getJSONObject(j).optString("Bautl"));
                                                    EtNotifHeader_statement.bindString(8, jsonArray.getJSONObject(j).optString("ReportedBy"));
                                                    EtNotifHeader_statement.bindString(9, jsonArray.getJSONObject(j).optString("MalfuncStdate"));
                                                    EtNotifHeader_statement.bindString(10, jsonArray.getJSONObject(j).optString("MalfuncEddate"));
                                                    EtNotifHeader_statement.bindString(11, jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                                    EtNotifHeader_statement.bindString(12, jsonArray.getJSONObject(j).optString("MalfuncEdtime"));
                                                    EtNotifHeader_statement.bindString(13, jsonArray.getJSONObject(j).optString("BreakdownInd"));
                                                    EtNotifHeader_statement.bindString(14, jsonArray.getJSONObject(j).optString("Priority"));
                                                    EtNotifHeader_statement.bindString(15, jsonArray.getJSONObject(j).optString("Ingrp"));
                                                    EtNotifHeader_statement.bindString(16, jsonArray.getJSONObject(j).optString("Arbpl"));
                                                    EtNotifHeader_statement.bindString(17, jsonArray.getJSONObject(j).optString("Werks"));
                                                    EtNotifHeader_statement.bindString(18, jsonArray.getJSONObject(j).optString("Strmn"));
                                                    EtNotifHeader_statement.bindString(19, jsonArray.getJSONObject(j).optString("Ltrmn"));
                                                    EtNotifHeader_statement.bindString(20, jsonArray.getJSONObject(j).optString("Aufnr"));
                                                    Aufnr = jsonArray.getJSONObject(j).optString("Aufnr");
                                                    EtNotifHeader_statement.bindString(21, jsonArray.getJSONObject(j).optString("Docs"));
                                                    EtNotifHeader_statement.bindString(22, jsonArray.getJSONObject(j).optString("Altitude"));
                                                    EtNotifHeader_statement.bindString(23, jsonArray.getJSONObject(j).optString("Latitude"));
                                                    EtNotifHeader_statement.bindString(24, jsonArray.getJSONObject(j).optString("Longitude"));
                                                    EtNotifHeader_statement.bindString(25, jsonArray.getJSONObject(j).optString("Closed"));
                                                    EtNotifHeader_statement.bindString(26, jsonArray.getJSONObject(j).optString("Completed"));
                                                    EtNotifHeader_statement.bindString(27, jsonArray.getJSONObject(j).optString("Createdon"));
                                                    EtNotifHeader_statement.bindString(28, jsonArray.getJSONObject(j).optString("Qmartx"));
                                                    EtNotifHeader_statement.bindString(29, jsonArray.getJSONObject(j).optString("Pltxt"));
                                                    EtNotifHeader_statement.bindString(30, jsonArray.getJSONObject(j).optString("Eqktx"));
                                                    EtNotifHeader_statement.bindString(31, jsonArray.getJSONObject(j).optString("Priokx"));
                                                    EtNotifHeader_statement.bindString(32, jsonArray.getJSONObject(j).optString("Auftext"));
                                                    EtNotifHeader_statement.bindString(33, jsonArray.getJSONObject(j).optString("Auarttext"));
                                                    EtNotifHeader_statement.bindString(34, jsonArray.getJSONObject(j).optString("Plantname"));
                                                    EtNotifHeader_statement.bindString(35, jsonArray.getJSONObject(j).optString("Wkctrname"));
                                                    EtNotifHeader_statement.bindString(36, jsonArray.getJSONObject(j).optString("Ingrpname"));
                                                    EtNotifHeader_statement.bindString(37, jsonArray.getJSONObject(j).optString("Maktx"));
                                                    EtNotifHeader_statement.bindString(38, jsonArray.getJSONObject(j).optString("Xstatus"));
                                                    EtNotifHeader_statement.bindString(39, jsonArray.getJSONObject(j).optString("Usr01"));
                                                    EtNotifHeader_statement.bindString(40, jsonArray.getJSONObject(j).optString("Usr02"));
                                                    EtNotifHeader_statement.bindString(41, jsonArray.getJSONObject(j).optString("Usr03"));
                                                    EtNotifHeader_statement.bindString(42, jsonArray.getJSONObject(j).optString("Usr04"));
                                                    EtNotifHeader_statement.bindString(43, jsonArray.getJSONObject(j).optString("Usr05"));
                                                    EtNotifHeader_statement.bindString(44, jsonArray.getJSONObject(j).optString("Xstatus"));
                                                    EtNotifHeader_statement.bindString(45, jsonArray.getJSONObject(j).optString("ParnrVw"));
                                                    EtNotifHeader_statement.bindString(46, jsonArray.getJSONObject(j).optString("NameVw"));
                                                    EtNotifHeader_statement.bindString(47, jsonArray.getJSONObject(j).optString("Auswk"));
                                                    EtNotifHeader_statement.bindString(48, jsonArray.getJSONObject(j).optString("Shift"));
                                                    EtNotifHeader_statement.bindString(49, jsonArray.getJSONObject(j).optString("Noofperson"));
                                                    EtNotifHeader_statement.bindString(50, jsonArray.getJSONObject(j).optString("Auswkt"));
                                                    EtNotifHeader_statement.bindString(51, jsonArray.getJSONObject(j).optString("Strur"));
                                                    EtNotifHeader_statement.bindString(52, jsonArray.getJSONObject(j).optString("Ltrur"));
                                                    EtNotifHeader_statement.bindString(53, jsonArray.getJSONObject(j).optString("MalfuncStdate") + " " + jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                                    EtNotifHeader_statement.bindString(54, jsonArray.getJSONObject(j).optString("Qmdat"));
                                                    EtNotifHeader_statement.execute();

                                                    try {
                                                        String Fields = jsonArray.getJSONObject(j).optString("EtNotifHeaderFields");
                                                        JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                        String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                        JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                        if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                            String sql1 = "Insert into EtNotifHeader_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                            SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                            statement1.clearBindings();
                                                            for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                                statement1.bindString(1, uuid);
                                                                statement1.bindString(2, Qmnum);
                                                                statement1.bindString(3, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                                statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                                statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                                statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                                statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                                statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                                statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                                statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                                statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                                statement1.execute();
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifHeader*/

                                            /*Reading and Inserting Data into Database Table for EtNotifItems*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtNotifItems() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifItems().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifItems().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifItems_Result eNI : response.body().getD().getResults()
                                                                .get(0).getEtNotifItems().getResults()) {
                                                            values.put("UUID", eNI.getQmnum());
                                                            values.put("Qmnum", eNI.getQmnum());
                                                            values.put("ItemKey", eNI.getItemKey());
                                                            values.put("ItempartGrp", eNI.getItempartGrp());
                                                            values.put("Partgrptext", eNI.getPartgrptext());
                                                            values.put("ItempartCod", eNI.getItempartCod());
                                                            values.put("Partcodetext", eNI.getPartcodetext());
                                                            values.put("ItemdefectGrp", eNI.getItemdefectGrp());
                                                            values.put("Defectgrptext", eNI.getDefectgrptext());
                                                            values.put("ItemdefectCod", eNI.getItemdefectCod());
                                                            values.put("Defectcodetext", eNI.getDefectcodetext());
                                                            values.put("ItemdefectShtxt", eNI.getItemdefectShtxt());
                                                            values.put("CauseKey", eNI.getCauseKey());
                                                            values.put("CauseGrp", eNI.getCauseGrp());
                                                            values.put("Causegrptext", eNI.getCausegrptext());
                                                            values.put("CauseCod", eNI.getCauseCod());
                                                            values.put("Causecodetext", eNI.getCausecodetext());
                                                            values.put("CauseShtxt", eNI.getCauseShtxt());
                                                            values.put("Usr01", eNI.getUsr01());
                                                            values.put("Usr02", eNI.getUsr02());
                                                            values.put("Usr03", eNI.getUsr03());
                                                            values.put("Usr04", eNI.getUsr04());
                                                            values.put("Usr05", eNI.getUsr05());
                                                            values.put("Status", "U");
                                                            if (eNI.getEtCustomFields() != null)
                                                                if (eNI.getEtCustomFields().getResults() != null
                                                                        && eNI.getEtCustomFields().getResults().size() > 0) {
                                                                    ContentValues ValuesHCf = new ContentValues();
                                                                    for (Notifications_SER.CustomFields_Result customFields_result : eNI.getEtCustomFields().getResults()) {
                                                                        ValuesHCf.put("UUID", eNI.getQmnum());
                                                                        ValuesHCf.put("Qmnum", eNI.getQmnum());
                                                                        ValuesHCf.put("ItemKey", eNI.getItemKey());
                                                                        ValuesHCf.put("CauseKey", eNI.getCauseKey());
                                                                        ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                                        ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                                        ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                                        ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                                        ValuesHCf.put("Value", customFields_result.getValue());
                                                                        ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                                        ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                                        ValuesHCf.put("Length", customFields_result.getLength());
                                                                        ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                                        App_db.insert("EtNotifItems_CustomInfo", null, ValuesHCf);
                                                                    }
                                                                }
                                                            App_db.insert("DUE_NOTIFICATIONS_EtNotifItems", null, values);
                                                        }
                                                    }
                                                }
                                            }catch(Exception e)
                                            {

                                            }
                               /* if (jsonObject.has("EtNotifItems")) {
                                    try {
                                        String EtNotifItems_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifItems().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifItems_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                                                    EtNotifItems_statement.clearBindings();
                                                    EtNotifItems_statement.bindString(1, uuid);
                                                    EtNotifItems_statement.bindString(2, Qmnum);
                                                    EtNotifItems_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                    EtNotifItems_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                    EtNotifItems_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                    EtNotifItems_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                    EtNotifItems_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                    EtNotifItems_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                    EtNotifItems_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                    EtNotifItems_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                    EtNotifItems_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                    EtNotifItems_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                    EtNotifItems_statement.bindString(13, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                    EtNotifItems_statement.bindString(14, jsonArray.getJSONObject(j).optString("CauseGrp"));
                                                    EtNotifItems_statement.bindString(15, jsonArray.getJSONObject(j).optString("Causegrptext"));
                                                    EtNotifItems_statement.bindString(16, jsonArray.getJSONObject(j).optString("CauseCod"));
                                                    EtNotifItems_statement.bindString(17, jsonArray.getJSONObject(j).optString("Causecodetext"));
                                                    EtNotifItems_statement.bindString(18, jsonArray.getJSONObject(j).optString("CauseShtxt"));
                                                    EtNotifItems_statement.bindString(19, jsonArray.getJSONObject(j).optString("Usr01"));
                                                    EtNotifItems_statement.bindString(20, jsonArray.getJSONObject(j).optString("Usr02"));
                                                    EtNotifItems_statement.bindString(21, jsonArray.getJSONObject(j).optString("Usr03"));
                                                    EtNotifItems_statement.bindString(22, jsonArray.getJSONObject(j).optString("Usr04"));
                                                    EtNotifItems_statement.bindString(23, jsonArray.getJSONObject(j).optString("Usr05"));
                                                    EtNotifItems_statement.bindString(24, "U");
                                                    EtNotifItems_statement.execute();

                                                    try {
                                                        String Fields = jsonArray.getJSONObject(j).optString("EtNotifItemsFields");
                                                        JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                        String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                        JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                        if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                            String sql = "Insert into EtNotifItems_CustomInfo (UUID, Qmnum, ItemKey, CauseKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                            SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                            statement1.clearBindings();
                                                            for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                                statement1.bindString(1, uuid);
                                                                statement1.bindString(2, Qmnum);
                                                                statement1.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                                statement1.bindString(4, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                                statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                                statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                                statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                                statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                                statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                                statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                                statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                                statement1.bindString(12, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                                statement1.bindString(13, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                                statement1.execute();
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifItems*/

                                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtNotifActvs() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifActvs().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifActvs().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifActvs_Result eNA : response.body().getD().getResults()
                                                                .get(0).getEtNotifActvs().getResults()) {
                                                            values.put("UUID", eNA.getQmnum());
                                                            values.put("Qmnum", eNA.getQmnum());
                                                            values.put("ItemKey", eNA.getItemKey());
                                                            values.put("ItempartGrp", eNA.getItempartGrp());
                                                            values.put("Partgrptext", eNA.getPartgrptext());
                                                            values.put("ItempartCod", eNA.getItempartCod());
                                                            values.put("Partcodetext", eNA.getPartcodetext());
                                                            values.put("ItemdefectGrp", eNA.getItemdefectGrp());
                                                            values.put("Defectgrptext", eNA.getDefectgrptext());
                                                            values.put("ItemdefectCod", eNA.getItemdefectCod());
                                                            values.put("Defectcodetext", eNA.getDefectcodetext());
                                                            values.put("ItemdefectShtxt", eNA.getItemdefectShtxt());
                                                            values.put("CauseKey", eNA.getCauseKey());
                                                            values.put("ActvKey", eNA.getActvKey());
                                                            values.put("ActvGrp", eNA.getActvGrp());
                                                            values.put("Actgrptext", eNA.getActgrptext());
                                                            values.put("ActvCod", eNA.getActvCod());
                                                            values.put("Actcodetext", eNA.getActcodetext());
                                                            values.put("ActvShtxt", eNA.getActvShtxt());
                                                            values.put("StartDate", eNA.getStartDate());
                                                            values.put("StartTime", eNA.getStartTime());
                                                            values.put("EndDate", eNA.getEndDate());
                                                            values.put("EndTime", eNA.getEndTime());
                                                            values.put("Usr01", eNA.getUsr01());
                                                            values.put("Usr02", eNA.getUsr02());
                                                            values.put("Usr03", eNA.getUsr03());
                                                            values.put("Usr04", eNA.getUsr04());
                                                            values.put("Usr05", eNA.getUsr05());
                                                            values.put("Fields", "");
                                                            values.put("Action", "U");
                                                            if (eNA.getEtCustomFields() != null)
                                                                if (eNA.getEtCustomFields().getResults() != null
                                                                        && eNA.getEtCustomFields().getResults().size() > 0) {
                                                                    ContentValues ValuesHCf = new ContentValues();
                                                                    for (Notifications_SER.CustomFields_Result customFields_result : eNA.getEtCustomFields().getResults()) {
                                                                        ValuesHCf.put("UUID", eNA.getQmnum());
                                                                        ValuesHCf.put("Qmnum", eNA.getQmnum());
                                                                        ValuesHCf.put("ActvKey", eNA.getActvKey());
                                                                        ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                                        ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                                        ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                                        ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                                        ValuesHCf.put("Value", customFields_result.getValue());
                                                                        ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                                        ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                                        ValuesHCf.put("Length", customFields_result.getLength());
                                                                        ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                                        App_db.insert("EtNotifActivity_CustomInfo", null, ValuesHCf);
                                                                    }
                                                                }

                                                            App_db.insert("DUE_NOTIFICATION_EtNotifActvs", null, values);
                                                        }
                                                    }
                                                }
                                            }catch (Exception e)
                                            {

                                            }
                               /* if (jsonObject.has("EtNotifActvs")) {
                                    try {
                                        String EtNotifActvs_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifActvs().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifActvs_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                                                    EtNotifActvs_statement.clearBindings();
                                                    EtNotifActvs_statement.bindString(1, uuid);
                                                    EtNotifActvs_statement.bindString(2, Qmnum);
                                                    EtNotifActvs_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                    EtNotifActvs_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                    EtNotifActvs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                    EtNotifActvs_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                    EtNotifActvs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                    EtNotifActvs_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                    EtNotifActvs_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                    EtNotifActvs_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                    EtNotifActvs_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                    EtNotifActvs_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                    EtNotifActvs_statement.bindString(13, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                    EtNotifActvs_statement.bindString(14, jsonArray.getJSONObject(j).optString("ActvKey"));
                                                    EtNotifActvs_statement.bindString(15, jsonArray.getJSONObject(j).optString("ActvGrp"));
                                                    EtNotifActvs_statement.bindString(16, jsonArray.getJSONObject(j).optString("Actgrptext"));
                                                    EtNotifActvs_statement.bindString(17, jsonArray.getJSONObject(j).optString("ActvCod"));
                                                    EtNotifActvs_statement.bindString(18, jsonArray.getJSONObject(j).optString("Actcodetext"));
                                                    EtNotifActvs_statement.bindString(19, jsonArray.getJSONObject(j).optString("ActvShtxt"));
                                                    EtNotifActvs_statement.bindString(20, jsonArray.getJSONObject(j).optString("StartDate"));
                                                    EtNotifActvs_statement.bindString(21, jsonArray.getJSONObject(j).optString("StartTime"));
                                                    EtNotifActvs_statement.bindString(22, jsonArray.getJSONObject(j).optString("EndDate"));
                                                    EtNotifActvs_statement.bindString(23, jsonArray.getJSONObject(j).optString("EndTime"));
                                                    EtNotifActvs_statement.bindString(24, jsonArray.getJSONObject(j).optString("Usr01"));
                                                    EtNotifActvs_statement.bindString(25, jsonArray.getJSONObject(j).optString("Usr02"));
                                                    EtNotifActvs_statement.bindString(26, jsonArray.getJSONObject(j).optString("Usr03"));
                                                    EtNotifActvs_statement.bindString(27, jsonArray.getJSONObject(j).optString("Usr04"));
                                                    EtNotifActvs_statement.bindString(28, jsonArray.getJSONObject(j).optString("Usr05"));
                                                    EtNotifActvs_statement.bindString(29, "");
                                                    EtNotifActvs_statement.bindString(30, "U");
                                                    EtNotifActvs_statement.execute();

                                                    try {
                                                        String ActvKey = jsonArray.getJSONObject(j).optString("ActvKey");
                                                        String Fields = jsonArray.getJSONObject(j).optString("EtNotifActvsFields");
                                                        JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                        String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                        JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                        if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                            String sql = "Insert into EtNotifActivity_CustomInfo (UUID, Qmnum, ActvKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                            SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                            statement1.clearBindings();
                                                            for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                                statement1.bindString(1, uuid);
                                                                statement1.bindString(2, Qmnum);
                                                                statement1.bindString(3, ActvKey);
                                                                statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                                statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                                statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                                statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                                statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                                statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                                statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                                statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                                statement1.bindString(12, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                                statement1.execute();
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/

                                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtNotifLongtext() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifLongtext().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifLongtext().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifLongtext_Result eNL : response.body().getD().getResults()
                                                                .get(0).getEtNotifLongtext().getResults()) {
                                                            values.put("UUID", eNL.getQmnum());
                                                            values.put("Qmnum", eNL.getQmnum());
                                                            values.put("Objtype", eNL.getObjtype());
                                                            values.put("TextLine", eNL.getTextLine());
                                                            values.put("Objkey", eNL.getObjkey());
                                                            App_db.insert("DUE_NOTIFICATIONS_EtNotifLongtext", null, values);
                                                        }
                                                    }
                                                }
                                            }catch (Exception e)
                                            {

                                            }
                                /*if (jsonObject.has("EtNotifLongtext")) {
                                    try {
                                        String EtNotifLongtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifLongtext().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifLongtext_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext (UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                                                    SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                                                    EtNotifLongtext_statement.clearBindings();
                                                    EtNotifLongtext_statement.bindString(1, uuid);
                                                    EtNotifLongtext_statement.bindString(2, Qmnum);
                                                    EtNotifLongtext_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtype"));
                                                    EtNotifLongtext_statement.bindString(4, jsonArray.getJSONObject(j).optString("TextLine"));
                                                    EtNotifLongtext_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objkey"));
                                                    EtNotifLongtext_statement.execute();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/

                                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtNotifStatus() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifStatus().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifStatus().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifStatus_Result eNS : response.body().getD().getResults()
                                                                .get(0).getEtNotifStatus().getResults()) {
                                                            values.put("UUID", "");
                                                            values.put("Qmnum", eNS.getQmnum());
                                                            values.put("Aufnr", eNS.getAufnr());
                                                            values.put("Objnr", eNS.getObjnr());
                                                            values.put("Manum", eNS.getManum());
                                                            values.put("Stsma", eNS.getStsma());
                                                            values.put("Inist", eNS.getInist());
                                                            values.put("Stonr", eNS.getStonr());
                                                            values.put("Hsonr", eNS.getHsonr());
                                                            values.put("Nsonr", eNS.getNsonr());
                                                            values.put("Stat", eNS.getStat());
                                                            values.put("Act", eNS.getAct());
                                                            values.put("Txt04", eNS.getTxt04());
                                                            values.put("Txt30", eNS.getTxt30());
                                                            values.put("Action", eNS.getAction());
                                                            App_db.insert("EtNotifStatus", null, values);
                                                        }
                                                    }
                                                }
                                            }catch (Exception e)
                                            {

                                            }
                               /* if (jsonObject.has("EtNotifStatus")) {
                                    try {
                                        String EtNotifStatus_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifStatus().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifStatus_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum,Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act,Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                                                    EtNotifStatus_statement.clearBindings();
                                                    EtNotifStatus_statement.bindString(1, uuid);
                                                    EtNotifStatus_statement.bindString(2, Qmnum);
                                                    EtNotifStatus_statement.bindString(3, jsonArray.getJSONObject(j).optString("Aufnr"));
                                                    EtNotifStatus_statement.bindString(4, jsonArray.getJSONObject(j).optString("Objnr"));
                                                    EtNotifStatus_statement.bindString(5, jsonArray.getJSONObject(j).optString("Manum"));
                                                    EtNotifStatus_statement.bindString(6, jsonArray.getJSONObject(j).optString("Stsma"));
                                                    EtNotifStatus_statement.bindString(7, jsonArray.getJSONObject(j).optString("Inist"));
                                                    EtNotifStatus_statement.bindString(8, jsonArray.getJSONObject(j).optString("Stonr"));
                                                    EtNotifStatus_statement.bindString(9, jsonArray.getJSONObject(j).optString("Hsonr"));
                                                    EtNotifStatus_statement.bindString(10, jsonArray.getJSONObject(j).optString("Nsonr"));
                                                    EtNotifStatus_statement.bindString(11, jsonArray.getJSONObject(j).optString("Stat"));
                                                    EtNotifStatus_statement.bindString(12, jsonArray.getJSONObject(j).optString("Act"));
                                                    EtNotifStatus_statement.bindString(13, jsonArray.getJSONObject(j).optString("Txt04"));
                                                    EtNotifStatus_statement.bindString(14, jsonArray.getJSONObject(j).optString("Txt30"));
                                                    EtNotifStatus_statement.bindString(15, "");
                                                    EtNotifStatus_statement.execute();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/

                                            /*Reading and Inserting Data into Database Table for EtDocs*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtDocs() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtDocs().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtDocs().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtDocs_Result eD : response.body().getD().getResults()
                                                                .get(0).getEtDocs().getResults()) {
                                                            values.put("UUID", eD.getZobjid());
                                                            values.put("Zobjid", eD.getZobjid());
                                                            values.put("Zdoctype", eD.getZdoctype());
                                                            values.put("ZdoctypeItem", eD.getZdoctypeItem());
                                                            values.put("Filename", eD.getFilename());
                                                            values.put("Filetype", eD.getFiletype());
                                                            values.put("Fsize", eD.getFsize());
                                                            values.put("Content", eD.getContent());
                                                            values.put("DocId", eD.getDocId());
                                                            values.put("DocType", eD.getDocType());
                                                            values.put("Objtype", eD.getObjtype());
                                                            values.put("Filepath", "");
                                                            values.put("Status", "Old");
                                                            values.put("Contentx", eD.getContentX());
                                                            App_db.insert("DUE_NOTIFICATION_EtDocs", null, values);
                                                        }
                                                    }
                                                }
                                            }catch (Exception e)
                                            {

                                            }
                                /*if (jsonObject.has("EtDocs")) {
                                    try {
                                        String EtDocs_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtDocs().getResults());
                                        JSONArray jsonArray = new JSONArray(EtDocs_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Zobjid = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Zobjid"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status, Contentx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                                    EtDocs_statement.clearBindings();
                                                    EtDocs_statement.bindString(1, uuid);
                                                    EtDocs_statement.bindString(2, jsonArray.getJSONObject(j).optString("Zobjid"));
                                                    EtDocs_statement.bindString(3, jsonArray.getJSONObject(j).optString("Zdoctype"));
                                                    EtDocs_statement.bindString(4, jsonArray.getJSONObject(j).optString("ZdoctypeItem"));
                                                    EtDocs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Filename"));
                                                    EtDocs_statement.bindString(6, jsonArray.getJSONObject(j).optString("Filetype"));
                                                    EtDocs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Fsize"));
                                                    EtDocs_statement.bindString(8, jsonArray.getJSONObject(j).optString("Content"));
                                                    EtDocs_statement.bindString(9, jsonArray.getJSONObject(j).optString("DocId"));
                                                    EtDocs_statement.bindString(10, jsonArray.getJSONObject(j).optString("DocType"));
                                                    EtDocs_statement.bindString(11, jsonArray.getJSONObject(j).optString("Objtype"));
                                                    EtDocs_statement.bindString(12, "");
                                                    EtDocs_statement.bindString(13, "Old");
                                                    EtDocs_statement.bindString(14, jsonArray.getJSONObject(j).optString("Contentx"));
                                                    EtDocs_statement.execute();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtDocs*/

                                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/
                                            try {

                                                if (response.body().getD().getResults().get(0).getEtNotifTasks() != null) {
                                                    if (response.body().getD().getResults().get(0).getEtNotifTasks().getResults() != null
                                                            && response.body().getD().getResults().get(0).getEtNotifTasks().getResults().size() > 0) {
                                                        ContentValues values = new ContentValues();
                                                        for (Notifications_SER.EtNotifTasks_Result eNT : response.body().getD().getResults()
                                                                .get(0).getEtNotifTasks().getResults()) {
                                                            values.put("UUID", eNT.getQmnum());
                                                            values.put("Qmnum", eNT.getQmnum());
                                                            values.put("ItemKey", eNT.getItemKey());
                                                            values.put("ItempartGrp", eNT.getItempartGrp());
                                                            values.put("Partgrptext", eNT.getPartgrptext());
                                                            values.put("ItempartCod", eNT.getItempartCod());
                                                            values.put("Partcodetext", eNT.getPartcodetext());
                                                            values.put("ItemdefectGrp", eNT.getItemdefectGrp());
                                                            values.put("Defectgrptext", eNT.getDefectgrptext());
                                                            values.put("ItemdefectCod", eNT.getItemdefectCod());
                                                            values.put("Defectcodetext", eNT.getDefectcodetext());
                                                            values.put("ItemdefectShtxt", eNT.getItemdefectShtxt());
                                                            values.put("TaskKey", eNT.getTaskKey());
                                                            values.put("TaskGrp", eNT.getTaskGrp());
                                                            values.put("Taskgrptext", eNT.getTaskgrptext());
                                                            values.put("TaskCod", eNT.getTaskCod());
                                                            values.put(" Taskcodetext", eNT.getTaskcodetext());
                                                            values.put("TaskShtxt", eNT.getTaskShtxt());
                                                            values.put("Pster", eNT.getPster());
                                                            values.put("Peter", eNT.getPeter());
                                                            values.put("Pstur", eNT.getPstur());
                                                            values.put("Petur", eNT.getPetur());
                                                            values.put("Parvw", eNT.getParvw());
                                                            values.put("Parnr", eNT.getParnr());
                                                            values.put("Erlnam", eNT.getErlnam());
                                                            values.put("Erldat", eNT.getErldat());
                                                            values.put("Erlzeit", eNT.getErlzeit());
                                                            values.put("Release", eNT.getRelease());
                                                            values.put("Complete", eNT.getComplete());
                                                            values.put("Success", eNT.getSuccess());
                                                            values.put("UserStatus", eNT.getUserStatus());
                                                            values.put("SysStatus", eNT.getSysStatus());
                                                            values.put("Smsttxt", eNT.getSmsttxt());
                                                            values.put("Smastxt", eNT.getSmastxt());
                                                            values.put("Usr01", eNT.getUsr01());
                                                            values.put("Usr02", eNT.getUsr02());
                                                            values.put("Usr03", eNT.getUsr03());
                                                            values.put("Usr04", eNT.getUsr04());
                                                            values.put("Usr05", eNT.getUsr05());
                                                            values.put("Action", "U");
                                                            if (eNT.getEtCustomFields() != null)
                                                                if (eNT.getEtCustomFields().getResults() != null
                                                                        && eNT.getEtCustomFields().getResults().size() > 0) {
                                                                    ContentValues ValuesHCf = new ContentValues();
                                                                    for (Notifications_SER.CustomFields_Result customFields_result : eNT.getEtCustomFields().getResults()) {
                                                                        ValuesHCf.put("UUID", eNT.getQmnum());
                                                                        ValuesHCf.put("Qmnum", eNT.getQmnum());
                                                                        ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                                        ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                                        ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                                        ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                                        ValuesHCf.put("Value", customFields_result.getValue());
                                                                        ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                                        ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                                        ValuesHCf.put("Length", customFields_result.getLength());
                                                                        ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                                        App_db.insert("EtNotifTask_CustomInfo", null, ValuesHCf);
                                                                    }
                                                                }

                                                            App_db.insert("DUE_NOTIFICATION_EtNotifTasks", null, values);
                                                        }
                                                    }
                                                }
                                            }catch (Exception e)
                                            {

                                            }
                                /*if (jsonObject.has("EtNotifTasks")) {
                                    try {
                                        String EtNotifTasks_response_data = new Gson().toJson(rs.getD().getEtNotifTasks().getResults());
                                        JSONArray jsonArray = new JSONArray(EtNotifTasks_response_data);
                                        if (jsonArray.length() > 0) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                                if (uuid != null && !uuid.equals("")) {
                                                    String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext, TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat, Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                                                    EtNotifTasks_statement.clearBindings();
                                                    EtNotifTasks_statement.bindString(1, uuid);
                                                    EtNotifTasks_statement.bindString(2, Qmnum);
                                                    EtNotifTasks_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                    EtNotifTasks_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                    EtNotifTasks_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                    EtNotifTasks_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                    EtNotifTasks_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                    EtNotifTasks_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                    EtNotifTasks_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                    EtNotifTasks_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                    EtNotifTasks_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                    EtNotifTasks_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                    EtNotifTasks_statement.bindString(13, jsonArray.getJSONObject(j).optString("TaskKey"));
                                                    EtNotifTasks_statement.bindString(14, jsonArray.getJSONObject(j).optString("TaskGrp"));
                                                    EtNotifTasks_statement.bindString(15, jsonArray.getJSONObject(j).optString("Taskgrptext"));
                                                    EtNotifTasks_statement.bindString(16, jsonArray.getJSONObject(j).optString("TaskCod"));
                                                    EtNotifTasks_statement.bindString(17, jsonArray.getJSONObject(j).optString("Taskcodetext"));
                                                    EtNotifTasks_statement.bindString(18, jsonArray.getJSONObject(j).optString("TaskShtxt"));
                                                    EtNotifTasks_statement.bindString(19, jsonArray.getJSONObject(j).optString("Pster"));
                                                    EtNotifTasks_statement.bindString(20, jsonArray.getJSONObject(j).optString("Peter"));
                                                    EtNotifTasks_statement.bindString(21, jsonArray.getJSONObject(j).optString("Pstur"));
                                                    EtNotifTasks_statement.bindString(22, jsonArray.getJSONObject(j).optString("Petur"));
                                                    EtNotifTasks_statement.bindString(23, jsonArray.getJSONObject(j).optString("Parvw"));
                                                    EtNotifTasks_statement.bindString(24, jsonArray.getJSONObject(j).optString("Parnr"));
                                                    EtNotifTasks_statement.bindString(25, jsonArray.getJSONObject(j).optString("Erlnam"));
                                                    EtNotifTasks_statement.bindString(26, jsonArray.getJSONObject(j).optString("Erldat"));
                                                    EtNotifTasks_statement.bindString(27, jsonArray.getJSONObject(j).optString("Erlzeit"));
                                                    EtNotifTasks_statement.bindString(28, jsonArray.getJSONObject(j).optString("Release"));
                                                    EtNotifTasks_statement.bindString(29, jsonArray.getJSONObject(j).optString("Complete"));
                                                    EtNotifTasks_statement.bindString(30, jsonArray.getJSONObject(j).optString("Success"));
                                                    EtNotifTasks_statement.bindString(31, jsonArray.getJSONObject(j).optString("UserStatus"));
                                                    EtNotifTasks_statement.bindString(32, jsonArray.getJSONObject(j).optString("SysStatus"));
                                                    EtNotifTasks_statement.bindString(33, jsonArray.getJSONObject(j).optString("Smsttxt"));
                                                    EtNotifTasks_statement.bindString(34, jsonArray.getJSONObject(j).optString("Smastxt"));
                                                    EtNotifTasks_statement.bindString(35, jsonArray.getJSONObject(j).optString("Usr01"));
                                                    EtNotifTasks_statement.bindString(36, jsonArray.getJSONObject(j).optString("Usr02"));
                                                    EtNotifTasks_statement.bindString(37, jsonArray.getJSONObject(j).optString("Usr03"));
                                                    EtNotifTasks_statement.bindString(38, jsonArray.getJSONObject(j).optString("Usr04"));
                                                    EtNotifTasks_statement.bindString(39, jsonArray.getJSONObject(j).optString("Usr05"));
                                                    EtNotifTasks_statement.bindString(40, "U");
                                                    EtNotifTasks_statement.execute();

                                                    try {
                                                        String Fields = jsonArray.getJSONObject(j).optString("EtNotifTasksFields");
                                                        JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                        String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                        JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                        if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                            String sql1 = "Insert into EtNotifTask_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                            SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                            statement1.clearBindings();
                                                            for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                                statement1.bindString(1, uuid);
                                                                statement1.bindString(2, Qmnum);
                                                                statement1.bindString(3, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                                statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                                statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                                statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                                statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                                statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                                statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                                statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                                statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                                statement1.execute();
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }*/
                                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/

                                            App_db.setTransactionSuccessful();
                                            Get_Response = "success";
                                            Get_Data = Aufnr;
                                        } finally {
                                            App_db.endTransaction();
                                        }
                                    }
                                } else {
                                    Get_Response = message;
                                    Get_Data = "";
                                }

                            /*Reading Data by using FOR Loop*/
                        }
                     else {
                        Get_Response = "exception";
                        Get_Data = "";
                    }
                }

        }catch (Exception ex) {
            Get_Response = "exception";
            Get_Data = "";
        } finally {
        }
        response.put("response_status", Get_Response);
        response.put("response_data", Get_Data);
        return response;
    }
}
