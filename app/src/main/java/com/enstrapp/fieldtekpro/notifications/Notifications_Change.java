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

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

public class Notifications_Change {
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "",
            Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> Post_NotifChange_Data(Context activity, String transmit_type,
                                                            String notif_id,
                                                            String notification_type_id,
                                                            String long_text,
                                                            String functionlocation_id,
                                                            String equipment_id,
                                                            String equipment_text,
                                                            String priority_type_id,
                                                            String priority_type_text,
                                                            String plannergroup_id,
                                                            String plannergroup_text,
                                                            String Reported_by,
                                                            String personresponsible_id,
                                                            String personresponsible_text,
                                                            String req_st_date,
                                                            String req_st_time,
                                                            String req_end_date,
                                                            String req_end_time,
                                                            String mal_st_date,
                                                            String mal_st_time,
                                                            String mal_end_date,
                                                            String mal_end_time, String effect_id,
                                                            String effect_text, String plant_id,
                                                            String workcenter_id,
                                                            String primary_user_resp,
                                                            ArrayList<Model_Notif_Causecode>
                                                                    causecodeArrayList,
                                                            ArrayList<Model_Notif_Activity>
                                                                    ActivityArrayList,
                                                            ArrayList<Model_Notif_Attachments>
                                                                    AttachmentsArrayList,
                                                            ArrayList<Model_Notif_Longtext>
                                                                    LongtextsArrayList,
                                                            ArrayList<Model_Notif_Status>
                                                                    statusArrayList,
                                                            ArrayList<Model_Notif_Task>
                                                                    TasksArrayList,
                                                            ArrayList<Model_CustomInfo>
                                                                    header_custominfo,String created_aufnr) {
        try {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            token = app_sharedpreferences.getString("token", null);
            cookie = app_sharedpreferences.getString("cookie", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",
                    null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ?" +
                    " and Zactivity = ? and Endpoint = ?", new String[]{"Q", "U", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120000, TimeUnit.SECONDS)
                    .writeTimeout(120000, TimeUnit.SECONDS)
                    .readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);

            /*For Send Data in POST Header*/
            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", token);
            map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");
            /*For Send Data in POST Header*/

            /*Objects for Assigning Notification Header and Sending Data in BODY*/
            Model_Notif_Header model_notif_header = new Model_Notif_Header();
            model_notif_header.setQmnum(notif_id);
            model_notif_header.setNotifType(notification_type_id);
            model_notif_header.setNotifShorttxt(long_text);
            model_notif_header.setFunctionLoc(functionlocation_id);
            model_notif_header.setEquipment(equipment_id);
            model_notif_header.setReportedBy(Reported_by);
            model_notif_header.setAufnr(created_aufnr);
            /*For Malfunction Start date*/
            if (mal_st_date != null && !mal_st_date.equals("")) {
                model_notif_header.setMalfuncStdate(mal_st_date);
            } else {
                model_notif_header.setMalfuncStdate("00000000");
            }
            /*For Malfunction Start date*/
            /*For Malfunction End date*/
            if (mal_end_date != null && !mal_end_date.equals("")) {
                model_notif_header.setMalfuncEddate(mal_end_date);
            } else {
                model_notif_header.setMalfuncEddate("00000000");
            }
            /*For Malfunction End date*/

            if (mal_st_time != null && !mal_st_time.equals("")) {
                model_notif_header.setMalfuncSttime(mal_st_time);
            } else {
                model_notif_header.setMalfuncSttime("000000");
            }

            if (mal_end_time != null && !mal_end_time.equals("")) {
                model_notif_header.setMalfuncEdtime(mal_end_time);
            } else {
                model_notif_header.setMalfuncEdtime("000000");
            }

            model_notif_header.setPriority(priority_type_id);
            model_notif_header.setIngrp(plannergroup_id);
            model_notif_header.setWerks(plant_id);
            /*For Required Start date*/
            if (req_st_date != null && !req_st_date.equals("")) {
                model_notif_header.setStrmn(req_st_date);
            } else {
                model_notif_header.setStrmn("00000000");
            }
            /*For Required Start date*/
            /*For Required End date*/
            if (req_end_date != null && !req_end_date.equals("")) {
                model_notif_header.setLtrmn(req_end_date);
            } else {
                model_notif_header.setLtrmn("00000000");
            }
            /*For Required End date*/

            if (req_st_time != null && !req_st_time.equals("")) {
                model_notif_header.setStrur(req_st_time);
            } else {
                model_notif_header.setStrur("000000");
            }

            if (req_end_time != null && !req_end_time.equals("")) {
                model_notif_header.setLtrur(req_end_time);
            } else {
                model_notif_header.setLtrur("000000");
            }

            model_notif_header.setParnrVw(personresponsible_id);
            model_notif_header.setDocs("");
            model_notif_header.setAuswk(effect_id);
            model_notif_header.setArbpl(workcenter_id);
            model_notif_header.setUsr01(primary_user_resp);
            model_notif_header.setQmdat("00000000");
            model_notif_header.setItNotifHeaderFields(header_custominfo);
            /*Objects for Assigning Notification Header and Sending Data in BODY*/

            /*Adding Notification Header to Arraylist*/
            ArrayList<Model_Notif_Header> headerArrayList = new ArrayList<>();
            headerArrayList.add(model_notif_header);
            /*Adding Notification Header to Arraylist*/

            /*Adding EvMessage to Arraylist*/
            ArrayList EvMessage_ArrayList = new ArrayList<>();
            /*Adding EvMessage to Arraylist*/

            /*Adding EtNotifHeader to Arraylist*/
            ArrayList EtNotifHeader_ArrayList = new ArrayList<>();
            /*Adding EtNotifHeader to Arraylist*/

            /*Adding EtNotifItems to Arraylist*/
            ArrayList EtNotifItems_ArrayList = new ArrayList<>();
            /*Adding EtNotifItems to Arraylist*/

            /*Adding EtNotifActvs to Arraylist*/
            ArrayList EtNotifActvs_ArrayList = new ArrayList<>();
            /*Adding EtNotifActvs to Arraylist*/

            /*Adding EtDocs to Arraylist*/
            ArrayList EtDocs_ArrayList = new ArrayList<>();
            /*Adding EtDocs to Arraylist*/

            /*Adding EtNotifStatus to Arraylist*/
            ArrayList EtNotifStatus_ArrayList = new ArrayList<>();
            /*Adding EtNotifStatus to Arraylist*/

            /*Adding EtNotifLongtext to Arraylist*/
            ArrayList EtNotifLongtext_ArrayList = new ArrayList<>();
            /*Adding EtNotifLongtext to Arraylist*/

            /*Adding EtNotifTask to Arraylist*/
            ArrayList EtNotifTask_ArrayList = new ArrayList<>();
            /*Adding EtNotifTask to Arraylist*/

            /*Adding Arraylist*/
            ArrayList arrayList = new ArrayList<>();
            /*Adding Arraylist*/

            Model_Notif_Header_Custominfo custominfo = new Model_Notif_Header_Custominfo();
            custominfo.setEtNotifHeaderFields(arrayList);
            EtNotifHeader_ArrayList.add(custominfo);

            Model_Notif_Causecode_Custominfo causecode_custominfo = new Model_Notif_Causecode_Custominfo();
            causecode_custominfo.setEtNotifItemsFields(arrayList);
            EtNotifItems_ArrayList.add(causecode_custominfo);

            Model_Notif_Activity_Custominfo activity_custominfo = new Model_Notif_Activity_Custominfo();
            activity_custominfo.setEtNotifActvsFields(arrayList);
            EtNotifActvs_ArrayList.add(activity_custominfo);

            Model_Notif_Task_Custominfo task_custominfo = new Model_Notif_Task_Custominfo();
            task_custominfo.setEtNotifTasksFields(arrayList);
            EtNotifTask_ArrayList.add(task_custominfo);

            /*Calling Notification Create Model with Data*/
            Model_Notif_Create model_notif_create = new Model_Notif_Create();
            model_notif_create.setIvUser(username);
            model_notif_create.setMuser(username.toUpperCase().toString());
            model_notif_create.setDeviceid(device_id);
            model_notif_create.setDevicesno(device_serial_number);
            model_notif_create.setUdid(device_uuid);
            model_notif_create.setIvTransmitType(transmit_type);
            model_notif_create.setIvCommit(true);
            model_notif_create.setOperation("CHNOT");
            model_notif_create.setItNotifHeader(headerArrayList);
            model_notif_create.setEtNotifHeader(EtNotifHeader_ArrayList);
            model_notif_create.setItNotifItems(causecodeArrayList);
            model_notif_create.setEtNotifItems(EtNotifItems_ArrayList);
            model_notif_create.setEtNotifActvs(EtNotifActvs_ArrayList);
            model_notif_create.setItNotifActvs(ActivityArrayList);
            model_notif_create.setEtDocs(EtDocs_ArrayList);
            model_notif_create.setItDocs(AttachmentsArrayList);
            model_notif_create.setEtNotifTasks(EtNotifTask_ArrayList);
            model_notif_create.setItNotifTasks(TasksArrayList);
            model_notif_create.setEtNotifStatus(EtNotifStatus_ArrayList);
            model_notif_create.setEvMessage(EvMessage_ArrayList);
            model_notif_create.setEtNotifLongtext(EtNotifLongtext_ArrayList);
            model_notif_create.setItNotifLongtext(LongtextsArrayList);
            model_notif_create.setItNotifStatus(statusArrayList);
            model_notif_create.setEtNotifStatus(EtNotifStatus_ArrayList);
            /*Calling Notification Create Model with Data*/

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Notifications_SER> call = service.getNotifCreateData(url_link, model_notif_create, basic, map);
            Response<Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {

                    /*Converting GSON Response to JSON Data for Parsing*/
                    StringBuilder Message_stringbuilder = new StringBuilder();
                    if (response.body().getD().getEvMessage().getResults() != null) {
                        if (response.body().getD().getEvMessage().getResults() != null
                                && response.body().getD().getEvMessage().getResults().size() > 0) {
                            ContentValues values = new ContentValues();

                            for (Notifications_SER.EvMessage_Result eM : response.body().getD().getEvMessage().getResults()) {
                                values.put("Message", eM.getMessage());
                                values.put("Qmnum", eM.getQmnum());
                                String Message = eM.getMessage();
                                Message_stringbuilder.append(Message);
                            }
                        }
                    }
                    String message = Message_stringbuilder.toString();
                    String qmnum = " ";
                    if (message.startsWith("S")) {

                        /*Reading and Inserting Data into Database Table for EtNotifHeader*/

                        App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from EtNotifHeader_CustomInfo where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from EtNotifItems_CustomInfo where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from EtNotifActivity_CustomInfo where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from EtNotifStatus where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notif_id});
                        App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks where Qmnum = ?", new String[]{notif_id});
                        App_db.execSQL("delete from EtNotifTask_CustomInfo where Qmnum = ?", new String[]{notif_id});

                        App_db.beginTransaction();
                        try {

                            try {
                                if (response.body().getD().getEtNotifHeader().getResults() != null) {
                                    if (response.body().getD().getEtNotifHeader().getResults() != null
                                            && response.body().getD().getEtNotifHeader().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifHeader_Result eN : response.body().getD().getEtNotifHeader().getResults()) {
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
                                                        //  ValuesHCf.put("Qmnum", qmnum);
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
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtNotifItems*/
                            try {
                                if (response.body().getD().getEtNotifItems().getResults() != null) {
                                    if (response.body().getD().getEtNotifItems().getResults() != null
                                            && response.body().getD().getEtNotifItems().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifItems_Result eNI : response.body().getD().getEtNotifItems().getResults()) {
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
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/
                            try {

                                if (response.body().getD().getEtNotifActvs().getResults() != null) {
                                    if (response.body().getD().getEtNotifActvs().getResults() != null
                                            && response.body().getD().getEtNotifActvs().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifActvs_Result eNA : response.body().getD().getEtNotifActvs().getResults()) {
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
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtDocs*/
                            try {

                                if (response.body().getD().getEtDocs().getResults() != null) {
                                    if (response.body().getD().getEtDocs().getResults() != null
                                            && response.body().getD().getEtDocs().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtDocs_Result eD : response.body().getD().getEtDocs().getResults()) {
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
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/
                            try {
                                if (response.body().getD().getEtNotifStatus().getResults() != null) {
                                    if (response.body().getD().getEtNotifStatus().getResults() != null
                                            && response.body().getD().getEtNotifStatus().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifStatus_Result eNS : response.body().getD().getEtNotifStatus().getResults()) {
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
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
                            try {

                                if (response.body().getD().getEtNotifLongtext().getResults() != null) {
                                    if (response.body().getD().getEtNotifLongtext().getResults() != null
                                            && response.body().getD().getEtNotifLongtext().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifLongtext_Result eNL : response.body().getD().getEtNotifLongtext().getResults()) {
                                            values.put("UUID", eNL.getQmnum());
                                            values.put("Qmnum", eNL.getQmnum());
                                            values.put("Objtype", eNL.getObjtype());
                                            values.put("TextLine", eNL.getTextLine());
                                            values.put("Objkey", eNL.getObjkey());
                                            App_db.insert("DUE_NOTIFICATIONS_EtNotifLongtext", null, values);
                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }
                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/
                            try {
                                if (response.body().getD().getEtNotifTasks().getResults() != null) {
                                    if (response.body().getD().getEtNotifTasks().getResults() != null
                                            && response.body().getD().getEtNotifTasks().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Notifications_SER.EtNotifTasks_Result eNT : response.body().getD().getEtNotifTasks().getResults()) {
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
                            } catch (Exception e) {

                            }
                            App_db.setTransactionSuccessful();
                            Get_Response = "success";
                            Get_Data = qmnum;
                        } finally {
                            App_db.endTransaction();
                        }
                    } else {
                        Get_Response = message;
                        Get_Data = "";
                    }

                } else {
                    Get_Response = activity.getString(R.string.notification_unabletochange);
                    Get_Data = "";
                }
                /*Converting GSON Response to JSON Data for Parsing*/

            } else {
            }
        } catch (Exception e) {
            Get_Response = activity.getString(R.string.notification_unabletochange);
            Get_Data = "";
        } finally {
        }
        response.put("response_status", Get_Response);
        response.put("response_data", Get_Data);
        return response;
    }
}
