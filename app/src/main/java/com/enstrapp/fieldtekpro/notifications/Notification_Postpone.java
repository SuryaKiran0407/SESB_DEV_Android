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
import com.enstrapp.fieldtekpro_sesb_dev.R;
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

public class Notification_Postpone {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();

    public static String Get_Notif_Postpone_Data(Context context, String notification_id) {
        try {
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = context.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"Q", "PP", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            } else {
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
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
            map.put("Operation", "NOPO");
            map.put("Ivcommit", "X");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Notifications_SER> call = service.Notif_Release(url_link, basic, map);
            Response<Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder Message_stringbuilder = new StringBuilder();
                    if (response.body().getD().getResults().get(0).getEvMessage().getResults() != null) {
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
                    String qmnum = " ";
                    if (message.startsWith("S")) {
                        if (message.contains("Notification already postponed!")) {
                            Get_Response = "already postponed";
                        } else if (message.contains("Notification postponed successfully")) {

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
                                /*Reading and Inserting Data into Database Table for EtNotifHeader*/
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
                                                        //ValuesHCf.put("Qmnum", qmnum);
                                                        ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                        ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                        ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                        ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                        ValuesHCf.put("Value", customFields_result.getValue());
                                                        ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                        ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                        ValuesHCf.put("Length", customFields_result.getLength());
                                                        ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                        App_db.insert("EtNotifHeader_CustomInfo ", null, ValuesHCf);
                                                    }
                                                }
                                            App_db.insert("DUE_NOTIFICATION_NotifHeader", null, values);
                                        }
                                    }
                                }
                                /*Reading and Inserting Data into Database Table for EtNotifHeader*/

                                /*Reading and Inserting Data into Database Table for EtNotifItems*/
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
                                                        // ValuesHCf.put("Qmnum", eNI.getQmnum());
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
                                /*Reading and Inserting Data into Database Table for EtNotifItems*/

                                /*Reading and Inserting Data into Database Table for EtNotifActvs*/
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
                                                        //ValuesHCf.put("Qmnum", eNA.getQmnum());
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
                                /*Reading and Inserting Data into Database Table for EtNotifActvs*/

                                /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
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
                                /*Reading and Inserting Data into Database Table for EtNotifLongtext*/

                                /*Reading and Inserting Data into Database Table for EtNotifStatus*/
                                if (response.body().getD().getResults().get(0).getEtNotifStatus() != null) {
                                    if (response.body().getD().getResults().get(0).getEtNotifStatus().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtNotifStatus().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifStatus_Result eNS : response.body().getD().getResults()
                                                .get(0).getEtNotifStatus().getResults()) {
                                            values.put("UUID", "");
                                            //values.put("Qmnum", eNS.getQmnum());
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
                                /*Reading and Inserting Data into Database Table for EtNotifStatus*/

                                /*Reading and Inserting Data into Database Table for EtDocs*/
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
                                /*Reading and Inserting Data into Database Table for EtDocs*/

                                /*Reading and Inserting Data into Database Table for EtNotifTasks*/
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
                                            values.put("Taskcodetext", eNT.getTaskcodetext());
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
                                                        //ValuesHCf.put("Qmnum", eNT.getQmnum());
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
                                /*Reading and Inserting Data into Database Table for EtNotifTasks*/

                                App_db.setTransactionSuccessful();
                                Get_Response = "success";
                            } finally {
                                App_db.endTransaction();
                            }
                        }
                    } else {
                        Get_Response = message;
                    }

                    /*Reading Data by using FOR Loop*/
                }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }
}
