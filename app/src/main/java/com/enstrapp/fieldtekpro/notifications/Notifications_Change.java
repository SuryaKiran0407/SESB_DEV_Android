package com.enstrapp.fieldtekpro.notifications;

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
import com.enstrapp.fieldtekpro.R;
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

public class Notifications_Change
{
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String  cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "", Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> Post_NotifChange_Data(Context activity, String transmit_type, String notif_id, String notification_type_id, String long_text, String functionlocation_id, String equipment_id, String equipment_text, String priority_type_id, String priority_type_text, String plannergroup_id, String plannergroup_text, String Reported_by, String personresponsible_id, String personresponsible_text, String req_st_date, String req_st_time, String req_end_date, String req_end_time, String mal_st_date, String mal_st_time, String  mal_end_date, String mal_end_time, String effect_id, String effect_text, String plant_id, String workcenter_id, String primary_user_resp, ArrayList<Model_Notif_Causecode> causecodeArrayList, ArrayList<Model_Notif_Activity> ActivityArrayList, ArrayList<Model_Notif_Attachments> AttachmentsArrayList, ArrayList<Model_Notif_Longtext> LongtextsArrayList, ArrayList<Model_Notif_Status> statusArrayList, ArrayList<Model_Notif_Task> TasksArrayList, ArrayList<Model_CustomInfo> header_custominfo)
    {
        try
        {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username",null);
            password = app_sharedpreferences.getString("Password",null);
            token = app_sharedpreferences.getString("token",null);
            cookie = app_sharedpreferences.getString("cookie",null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Q","U", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = ""+ Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32)| device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
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
            /*For Malfunction Start date*/
            if (mal_st_date != null && !mal_st_date.equals(""))
            {
                model_notif_header.setMalfuncStdate(mal_st_date);
            }
            else
            {
                model_notif_header.setMalfuncStdate("00000000");
            }
			/*For Malfunction Start date*/
			/*For Malfunction End date*/
            if (mal_end_date != null && !mal_end_date.equals(""))
            {
                model_notif_header.setMalfuncEddate(mal_end_date);
            }
            else
            {
                model_notif_header.setMalfuncEddate("00000000");
            }
			/*For Malfunction End date*/

            if (mal_st_time != null && !mal_st_time.equals(""))
            {
                model_notif_header.setMalfuncSttime(mal_st_time);
            }
            else
            {
                model_notif_header.setMalfuncSttime("000000");
            }

            if (mal_end_time != null && !mal_end_time.equals(""))
            {
                model_notif_header.setMalfuncEdtime(mal_end_time);
            }
            else
            {
                model_notif_header.setMalfuncEdtime("000000");
            }

            model_notif_header.setPriority(priority_type_id);
            model_notif_header.setIngrp(plannergroup_id);
            model_notif_header.setWerks(plant_id);
            /*For Required Start date*/
            if (req_st_date != null && !req_st_date.equals(""))
            {
                model_notif_header.setStrmn(req_st_date);
            }
            else
            {
                model_notif_header.setStrmn("00000000");
            }
            /*For Required Start date*/
            /*For Required End date*/
            if (req_end_date != null && !req_end_date.equals(""))
            {
                model_notif_header.setLtrmn(req_end_date);
            }
            else
            {
                model_notif_header.setLtrmn("00000000");
            }
            /*For Required End date*/

            if (req_st_time != null && !req_st_time.equals(""))
            {
                model_notif_header.setStrur(req_st_time);
            }
            else
            {
                model_notif_header.setStrur("000000");
            }

            if (req_end_time != null && !req_end_time.equals(""))
            {
                model_notif_header.setLtrur(req_end_time);
            }
            else
            {
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
            if(response_status_code == 201)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    Notifications_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getEvMessage().getResults());
                    if (response_data != null && !response_data.equals(""))
                    {
                        StringBuilder Message_stringbuilder = new StringBuilder();
                        JSONArray jsonObject = new JSONArray(response_data);
                        for(int i = 0; i < jsonObject.length(); i++)
                        {
                            String Message = jsonObject.getJSONObject(i).optString("Message");
                            Message_stringbuilder.append(Message);
                        }
                        String message = Message_stringbuilder.toString();
                        if(message.startsWith("S"))
                        {
                            String notifresponse_data = new Gson().toJson(rs.getD());
                            //String EtNotifHeader_Data = new Gson().toJson(rs.getD().getEtNotifHeader().getResults());
                            JSONObject notifresponse_jsonObject = new JSONObject(notifresponse_data);

                            UUID uniqueKey = UUID.randomUUID();
                            String uuid = uniqueKey.toString();
                            String qmnum = "";

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

                            if(notifresponse_jsonObject.has("EtNotifHeader"))
                            {
                                try
                                {
                                    String EtNotifHeader_response_data = new Gson().toJson(rs.getD().getEtNotifHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifHeader_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifHeader_sql = "Insert into DUE_NOTIFICATION_NotifHeader (UUID,NotifType,Qmnum,NotifShorttxt,FunctionLoc,Equipment,Bautl,ReportedBy,MalfuncStdate,MalfuncEddate,MalfuncSttime,MalfuncEdtime,BreakdownInd,Priority,Ingrp,Arbpl,Werks,Strmn,Ltrmn,Aufnr,Docs,Altitude,Latitude,Longitude,Closed,Completed,Createdon,Qmartx,Pltxt,Eqktx,Priokx,Auftext,Auarttext,Plantname,Wkctrname,Ingrpname,Maktx,Xstatus,Usr01,Usr02,Usr03,Usr04,Usr05,STATUS,ParnrVw,NameVw,Auswk,Shift,Noofperson,Auswkt, Strur, Ltrur, sort_malfc, Qmdat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifHeader_statement = App_db.compileStatement(EtNotifHeader_sql);
                                            EtNotifHeader_statement.clearBindings();
                                            EtNotifHeader_statement.bindString(1,uuid);
                                            EtNotifHeader_statement.bindString(2,jsonArray.getJSONObject(j).optString("NotifType"));
                                            EtNotifHeader_statement.bindString(3,jsonArray.getJSONObject(j).optString("Qmnum"));
                                            qmnum = jsonArray.getJSONObject(j).optString("Qmnum");
                                            EtNotifHeader_statement.bindString(4,jsonArray.getJSONObject(j).optString("NotifShorttxt"));
                                            EtNotifHeader_statement.bindString(5,jsonArray.getJSONObject(j).optString("FunctionLoc"));
                                            EtNotifHeader_statement.bindString(6,jsonArray.getJSONObject(j).optString("Equipment"));
                                            EtNotifHeader_statement.bindString(7,jsonArray.getJSONObject(j).optString("Bautl"));
                                            EtNotifHeader_statement.bindString(8,jsonArray.getJSONObject(j).optString("ReportedBy"));
                                            EtNotifHeader_statement.bindString(9,jsonArray.getJSONObject(j).optString("MalfuncStdate"));
                                            EtNotifHeader_statement.bindString(10,jsonArray.getJSONObject(j).optString("MalfuncEddate"));
                                            EtNotifHeader_statement.bindString(11,jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                            EtNotifHeader_statement.bindString(12,jsonArray.getJSONObject(j).optString("MalfuncEdtime"));
                                            EtNotifHeader_statement.bindString(13,jsonArray.getJSONObject(j).optString("BreakdownInd"));
                                            EtNotifHeader_statement.bindString(14,jsonArray.getJSONObject(j).optString("Priority"));
                                            EtNotifHeader_statement.bindString(15,jsonArray.getJSONObject(j).optString("Ingrp"));
                                            EtNotifHeader_statement.bindString(16,jsonArray.getJSONObject(j).optString("Arbpl"));
                                            EtNotifHeader_statement.bindString(17,jsonArray.getJSONObject(j).optString("Werks"));
                                            EtNotifHeader_statement.bindString(18,jsonArray.getJSONObject(j).optString("Strmn"));
                                            EtNotifHeader_statement.bindString(19,jsonArray.getJSONObject(j).optString("Ltrmn"));
                                            EtNotifHeader_statement.bindString(20,jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtNotifHeader_statement.bindString(21,jsonArray.getJSONObject(j).optString("Docs"));
                                            EtNotifHeader_statement.bindString(22,jsonArray.getJSONObject(j).optString("Altitude"));
                                            EtNotifHeader_statement.bindString(23,jsonArray.getJSONObject(j).optString("Latitude"));
                                            EtNotifHeader_statement.bindString(24,jsonArray.getJSONObject(j).optString("Longitude"));
                                            EtNotifHeader_statement.bindString(25,jsonArray.getJSONObject(j).optString("Closed"));
                                            EtNotifHeader_statement.bindString(26,jsonArray.getJSONObject(j).optString("Completed"));
                                            EtNotifHeader_statement.bindString(27,jsonArray.getJSONObject(j).optString("Createdon"));
                                            EtNotifHeader_statement.bindString(28,jsonArray.getJSONObject(j).optString("Qmartx"));
                                            EtNotifHeader_statement.bindString(29,jsonArray.getJSONObject(j).optString("Pltxt"));
                                            EtNotifHeader_statement.bindString(30,jsonArray.getJSONObject(j).optString("Eqktx"));
                                            EtNotifHeader_statement.bindString(31,jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtNotifHeader_statement.bindString(32,jsonArray.getJSONObject(j).optString("Auftext"));
                                            EtNotifHeader_statement.bindString(33,jsonArray.getJSONObject(j).optString("Auarttext"));
                                            EtNotifHeader_statement.bindString(34,jsonArray.getJSONObject(j).optString("Plantname"));
                                            EtNotifHeader_statement.bindString(35,jsonArray.getJSONObject(j).optString("Wkctrname"));
                                            EtNotifHeader_statement.bindString(36,jsonArray.getJSONObject(j).optString("Ingrpname"));
                                            EtNotifHeader_statement.bindString(37,jsonArray.getJSONObject(j).optString("Maktx"));
                                            EtNotifHeader_statement.bindString(38,jsonArray.getJSONObject(j).optString("Xstatus"));
                                            EtNotifHeader_statement.bindString(39,jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtNotifHeader_statement.bindString(40,jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtNotifHeader_statement.bindString(41,jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtNotifHeader_statement.bindString(42,jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtNotifHeader_statement.bindString(43,jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtNotifHeader_statement.bindString(44,jsonArray.getJSONObject(j).optString("Xstatus"));
                                            EtNotifHeader_statement.bindString(45,jsonArray.getJSONObject(j).optString("ParnrVw"));
                                            EtNotifHeader_statement.bindString(46,jsonArray.getJSONObject(j).optString("NameVw"));
                                            EtNotifHeader_statement.bindString(47,jsonArray.getJSONObject(j).optString("Auswk"));
                                            EtNotifHeader_statement.bindString(48,jsonArray.getJSONObject(j).optString("Shift"));
                                            EtNotifHeader_statement.bindString(49,jsonArray.getJSONObject(j).optString("Noofperson"));
                                            EtNotifHeader_statement.bindString(50,jsonArray.getJSONObject(j).optString("Auswkt"));
                                            EtNotifHeader_statement.bindString(51,jsonArray.getJSONObject(j).optString("Strur"));
                                            EtNotifHeader_statement.bindString(52,jsonArray.getJSONObject(j).optString("Ltrur"));
                                            EtNotifHeader_statement.bindString(53,jsonArray.getJSONObject(j).optString("MalfuncStdate")+" "+jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                            EtNotifHeader_statement.bindString(54,jsonArray.getJSONObject(j).optString("Qmdat"));
                                            EtNotifHeader_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifHeaderFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql1 = "Insert into EtNotifHeader_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,qmnum);
                                                        statement1.bindString(3,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifHeader*/


                            /*Reading and Inserting Data into Database Table for EtNotifItems*/
                            if(notifresponse_jsonObject.has("EtNotifItems"))
                            {
                                try
                                {
                                    String EtNotifItems_response_data = new Gson().toJson(rs.getD().getEtNotifItems().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifItems_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                                            EtNotifItems_statement.clearBindings();
                                            EtNotifItems_statement.bindString(1,uuid);
                                            EtNotifItems_statement.bindString(2,qmnum);
                                            EtNotifItems_statement.bindString(3,jsonArray.getJSONObject(j).optString("ItemKey"));
                                            EtNotifItems_statement.bindString(4,jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                            EtNotifItems_statement.bindString(5,jsonArray.getJSONObject(j).optString("Partgrptext"));
                                            EtNotifItems_statement.bindString(6,jsonArray.getJSONObject(j).optString("ItempartCod"));
                                            EtNotifItems_statement.bindString(7,jsonArray.getJSONObject(j).optString("Partcodetext"));
                                            EtNotifItems_statement.bindString(8,jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                            EtNotifItems_statement.bindString(9,jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                            EtNotifItems_statement.bindString(10,jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                            EtNotifItems_statement.bindString(11,jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                            EtNotifItems_statement.bindString(12,jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                            EtNotifItems_statement.bindString(13,jsonArray.getJSONObject(j).optString("CauseKey"));
                                            EtNotifItems_statement.bindString(14,jsonArray.getJSONObject(j).optString("CauseGrp"));
                                            EtNotifItems_statement.bindString(15,jsonArray.getJSONObject(j).optString("Causegrptext"));
                                            EtNotifItems_statement.bindString(16,jsonArray.getJSONObject(j).optString("CauseCod"));
                                            EtNotifItems_statement.bindString(17,jsonArray.getJSONObject(j).optString("Causecodetext"));
                                            EtNotifItems_statement.bindString(18,jsonArray.getJSONObject(j).optString("CauseShtxt"));
                                            EtNotifItems_statement.bindString(19,jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtNotifItems_statement.bindString(20,jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtNotifItems_statement.bindString(21,jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtNotifItems_statement.bindString(22,jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtNotifItems_statement.bindString(23,jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtNotifItems_statement.bindString(24,"U");
                                            EtNotifItems_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifItemsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql = "Insert into EtNotifItems_CustomInfo (UUID, Qmnum, ItemKey, CauseKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,qmnum);
                                                        statement1.bindString(3,jsonArray.getJSONObject(j).optString("ItemKey"));
                                                        statement1.bindString(4,jsonArray.getJSONObject(j).optString("CauseKey"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(12,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(13,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifItems*/


                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/
                            if(notifresponse_jsonObject.has("EtNotifActvs"))
                            {
                                try
                                {
                                    String EtNotifActvs_response_data = new Gson().toJson(rs.getD().getEtNotifActvs().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifActvs_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                                            EtNotifActvs_statement.clearBindings();
                                            EtNotifActvs_statement.bindString(1,uuid);
                                            EtNotifActvs_statement.bindString(2,qmnum);
                                            EtNotifActvs_statement.bindString(3,jsonArray.getJSONObject(j).optString("ItemKey"));
                                            EtNotifActvs_statement.bindString(4,jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                            EtNotifActvs_statement.bindString(5,jsonArray.getJSONObject(j).optString("Partgrptext"));
                                            EtNotifActvs_statement.bindString(6,jsonArray.getJSONObject(j).optString("ItempartCod"));
                                            EtNotifActvs_statement.bindString(7,jsonArray.getJSONObject(j).optString("Partcodetext"));
                                            EtNotifActvs_statement.bindString(8,jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                            EtNotifActvs_statement.bindString(9,jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                            EtNotifActvs_statement.bindString(10,jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                            EtNotifActvs_statement.bindString(11,jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                            EtNotifActvs_statement.bindString(12,jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                            EtNotifActvs_statement.bindString(13,jsonArray.getJSONObject(j).optString("CauseKey"));
                                            EtNotifActvs_statement.bindString(14,jsonArray.getJSONObject(j).optString("ActvKey"));
                                            EtNotifActvs_statement.bindString(15,jsonArray.getJSONObject(j).optString("ActvGrp"));
                                            EtNotifActvs_statement.bindString(16,jsonArray.getJSONObject(j).optString("Actgrptext"));
                                            EtNotifActvs_statement.bindString(17,jsonArray.getJSONObject(j).optString("ActvCod"));
                                            EtNotifActvs_statement.bindString(18,jsonArray.getJSONObject(j).optString("Actcodetext"));
                                            EtNotifActvs_statement.bindString(19,jsonArray.getJSONObject(j).optString("ActvShtxt"));
                                            EtNotifActvs_statement.bindString(20,jsonArray.getJSONObject(j).optString("StartDate"));
                                            EtNotifActvs_statement.bindString(21,jsonArray.getJSONObject(j).optString("StartTime"));
                                            EtNotifActvs_statement.bindString(22,jsonArray.getJSONObject(j).optString("EndDate"));
                                            EtNotifActvs_statement.bindString(23,jsonArray.getJSONObject(j).optString("EndTime"));
                                            EtNotifActvs_statement.bindString(24,jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtNotifActvs_statement.bindString(25,jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtNotifActvs_statement.bindString(26,jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtNotifActvs_statement.bindString(27,jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtNotifActvs_statement.bindString(28,jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtNotifActvs_statement.bindString(29,"");
                                            EtNotifActvs_statement.bindString(30,"U");
                                            EtNotifActvs_statement.execute();


                                            try
                                            {
                                                String ActvKey = jsonArray.getJSONObject(j).optString("ActvKey");
                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifActvsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql = "Insert into EtNotifActivity_CustomInfo (UUID, Qmnum, ActvKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,qmnum);
                                                        statement1.bindString(3,ActvKey);
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(12,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/


                            /*Reading and Inserting Data into Database Table for EtDocs*/
                            if(notifresponse_jsonObject.has("EtDocs"))
                            {
                                try
                                {
                                    String EtDocs_response_data = new Gson().toJson(rs.getD().getEtDocs().getResults());
                                    JSONArray jsonArray = new JSONArray(EtDocs_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                            EtDocs_statement.clearBindings();
                                            EtDocs_statement.bindString(1,uuid);
                                            EtDocs_statement.bindString(2,jsonArray.getJSONObject(j).optString("Zobjid"));
                                            EtDocs_statement.bindString(3,jsonArray.getJSONObject(j).optString("Zdoctype"));
                                            EtDocs_statement.bindString(4,jsonArray.getJSONObject(j).optString("ZdoctypeItem"));
                                            EtDocs_statement.bindString(5,jsonArray.getJSONObject(j).optString("Filename"));
                                            EtDocs_statement.bindString(6,jsonArray.getJSONObject(j).optString("Filetype"));
                                            EtDocs_statement.bindString(7,jsonArray.getJSONObject(j).optString("Fsize"));
                                            EtDocs_statement.bindString(8,jsonArray.getJSONObject(j).optString("Content"));
                                            EtDocs_statement.bindString(9,jsonArray.getJSONObject(j).optString("DocId"));
                                            EtDocs_statement.bindString(10,jsonArray.getJSONObject(j).optString("DocType"));
                                            EtDocs_statement.bindString(11,jsonArray.getJSONObject(j).optString("Objtype"));
                                            EtDocs_statement.bindString(12,"");
                                            EtDocs_statement.bindString(13,"Old");
                                            EtDocs_statement.execute();
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtDocs*/


                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/
                            if(notifresponse_jsonObject.has("EtNotifStatus"))
                            {
                                try
                                {
                                    String EtNotifStatus_response_data = new Gson().toJson(rs.getD().getEtNotifStatus().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifStatus_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum,Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act,Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                                            EtNotifStatus_statement.clearBindings();
                                            EtNotifStatus_statement.bindString(1,uuid);
                                            EtNotifStatus_statement.bindString(2,qmnum);
                                            EtNotifStatus_statement.bindString(3,jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtNotifStatus_statement.bindString(4,jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtNotifStatus_statement.bindString(5,jsonArray.getJSONObject(j).optString("Manum"));
                                            EtNotifStatus_statement.bindString(6,jsonArray.getJSONObject(j).optString("Stsma"));
                                            EtNotifStatus_statement.bindString(7,jsonArray.getJSONObject(j).optString("Inist"));
                                            EtNotifStatus_statement.bindString(8,jsonArray.getJSONObject(j).optString("Stonr"));
                                            EtNotifStatus_statement.bindString(9,jsonArray.getJSONObject(j).optString("Hsonr"));
                                            EtNotifStatus_statement.bindString(10,jsonArray.getJSONObject(j).optString("Nsonr"));
                                            EtNotifStatus_statement.bindString(11,jsonArray.getJSONObject(j).optString("Stat"));
                                            EtNotifStatus_statement.bindString(12,jsonArray.getJSONObject(j).optString("Act"));
                                            EtNotifStatus_statement.bindString(13,jsonArray.getJSONObject(j).optString("Txt04"));
                                            EtNotifStatus_statement.bindString(14,jsonArray.getJSONObject(j).optString("Txt30"));
                                            EtNotifStatus_statement.bindString(15,"");
                                            EtNotifStatus_statement.execute();
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/


                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
                            if(notifresponse_jsonObject.has("EtNotifLongtext"))
                            {
                                try
                                {
                                    String EtNotifStatus_response_data = new Gson().toJson(rs.getD().getEtNotifLongtext().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifStatus_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext (UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                                            SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                                            EtNotifLongtext_statement.clearBindings();
                                            EtNotifLongtext_statement.bindString(1,uuid);
                                            EtNotifLongtext_statement.bindString(2,qmnum);
                                            EtNotifLongtext_statement.bindString(3,jsonArray.getJSONObject(j).optString("Objtype"));
                                            EtNotifLongtext_statement.bindString(4,jsonArray.getJSONObject(j).optString("TextLine"));
                                            EtNotifLongtext_statement.bindString(5,jsonArray.getJSONObject(j).optString("Objkey"));
                                            EtNotifLongtext_statement.execute();
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/


                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/
                            if(notifresponse_jsonObject.has("EtNotifTasks"))
                            {
                                try
                                {
                                    String EtNotifTasks_response_data = new Gson().toJson(rs.getD().getEtNotifTasks().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifTasks_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext, TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat, Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                                            EtNotifTasks_statement.clearBindings();
                                            EtNotifTasks_statement.bindString(1,uuid);
                                            EtNotifTasks_statement.bindString(2,qmnum);
                                            EtNotifTasks_statement.bindString(3,jsonArray.getJSONObject(j).optString("ItemKey"));
                                            EtNotifTasks_statement.bindString(4,jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                            EtNotifTasks_statement.bindString(5,jsonArray.getJSONObject(j).optString("Partgrptext"));
                                            EtNotifTasks_statement.bindString(6,jsonArray.getJSONObject(j).optString("ItempartCod"));
                                            EtNotifTasks_statement.bindString(7,jsonArray.getJSONObject(j).optString("Partcodetext"));
                                            EtNotifTasks_statement.bindString(8,jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                            EtNotifTasks_statement.bindString(9,jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                            EtNotifTasks_statement.bindString(10,jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                            EtNotifTasks_statement.bindString(11,jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                            EtNotifTasks_statement.bindString(12,jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                            EtNotifTasks_statement.bindString(13,jsonArray.getJSONObject(j).optString("TaskKey"));
                                            EtNotifTasks_statement.bindString(14,jsonArray.getJSONObject(j).optString("TaskGrp"));
                                            EtNotifTasks_statement.bindString(15,jsonArray.getJSONObject(j).optString("Taskgrptext"));
                                            EtNotifTasks_statement.bindString(16,jsonArray.getJSONObject(j).optString("TaskCod"));
                                            EtNotifTasks_statement.bindString(17,jsonArray.getJSONObject(j).optString("Taskcodetext"));
                                            EtNotifTasks_statement.bindString(18,jsonArray.getJSONObject(j).optString("TaskShtxt"));
                                            EtNotifTasks_statement.bindString(19,jsonArray.getJSONObject(j).optString("Pster"));
                                            EtNotifTasks_statement.bindString(20,jsonArray.getJSONObject(j).optString("Peter"));
                                            EtNotifTasks_statement.bindString(21,jsonArray.getJSONObject(j).optString("Pstur"));
                                            EtNotifTasks_statement.bindString(22,jsonArray.getJSONObject(j).optString("Petur"));
                                            EtNotifTasks_statement.bindString(23,jsonArray.getJSONObject(j).optString("Parvw"));
                                            EtNotifTasks_statement.bindString(24,jsonArray.getJSONObject(j).optString("Parnr"));
                                            EtNotifTasks_statement.bindString(25,jsonArray.getJSONObject(j).optString("Erlnam"));
                                            EtNotifTasks_statement.bindString(26,jsonArray.getJSONObject(j).optString("Erldat"));
                                            EtNotifTasks_statement.bindString(27,jsonArray.getJSONObject(j).optString("Erlzeit"));
                                            EtNotifTasks_statement.bindString(28,jsonArray.getJSONObject(j).optString("Release"));
                                            EtNotifTasks_statement.bindString(29,jsonArray.getJSONObject(j).optString("Complete"));
                                            EtNotifTasks_statement.bindString(30,jsonArray.getJSONObject(j).optString("Success"));
                                            EtNotifTasks_statement.bindString(31,jsonArray.getJSONObject(j).optString("UserStatus"));
                                            EtNotifTasks_statement.bindString(32,jsonArray.getJSONObject(j).optString("SysStatus"));
                                            EtNotifTasks_statement.bindString(33,jsonArray.getJSONObject(j).optString("Smsttxt"));
                                            EtNotifTasks_statement.bindString(34,jsonArray.getJSONObject(j).optString("Smastxt"));
                                            EtNotifTasks_statement.bindString(35,jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtNotifTasks_statement.bindString(36,jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtNotifTasks_statement.bindString(37,jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtNotifTasks_statement.bindString(38,jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtNotifTasks_statement.bindString(39,jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtNotifTasks_statement.bindString(40,"U");
                                            EtNotifTasks_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifTasksFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql1 = "Insert into EtNotifTask_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,qmnum);
                                                        statement1.bindString(3,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/


                            App_db.setTransactionSuccessful();
                            App_db.endTransaction();

                            Get_Response = "success";
                            Get_Data = qmnum;
                        }
                        else
                        {
                            Get_Response = message;
                            Get_Data = "";
                        }
                    }
                    else
                    {
                        Get_Response = "Unable to process Notification Change. Please try again.";
                        Get_Data = "";
                    }
                    /*Converting GSON Response to JSON Data for Parsing*/
                }
            }
            else
            {
            }
        }
        catch(Exception e)
        {
            Get_Response = "Unable to process Notification Change. Please try again.";
            Get_Data = "";
        }
        finally
        {
        }
        response.put("response_status", Get_Response);
        response.put("response_data", Get_Data);
        return response;
    }


}
