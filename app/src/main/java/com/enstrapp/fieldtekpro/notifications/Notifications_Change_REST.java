package com.enstrapp.fieldtekpro.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Notifications_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Notifications_SER;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Notifications_Change_REST
{
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
                                                            ArrayList<Model_Notif_Causecode_REST>
                                                                    causecodeArrayList,
                                                            ArrayList<Model_Notif_Activity_REST>
                                                                    ActivityArrayList,
                                                            ArrayList<Model_Notif_Attachments_REST>
                                                                    AttachmentsArrayList,
                                                            ArrayList<Model_Notif_Longtext_REST>
                                                                    LongtextsArrayList,
                                                            ArrayList<Model_Notif_Status_REST>
                                                                    statusArrayList,
                                                            ArrayList<Model_Notif_Task_REST>
                                                                    TasksArrayList,
                                                            ArrayList<Model_CustomInfo>
                                                                    header_custominfo,String created_aufnr,
                                                            String Breakdown_status) {
        try
        {
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
            REST_Interface service = retrofit.create(REST_Interface.class);

            /*For Send Data in POST Header*/
            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");
            /*For Send Data in POST Header*/



            /*Objects for Assigning Notification Header and Sending Data in BODY*/
            Model_Notif_Header_REST model_notif_header = new Model_Notif_Header_REST();
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
            model_notif_header.setBreakdownInd(Breakdown_status);
            model_notif_header.setItNotifHeaderFields(header_custominfo);
            /*Objects for Assigning Notification Header and Sending Data in BODY*/


            /*Adding Notification Header to Arraylist*/
            ArrayList<Model_Notif_Header_REST> headerArrayList = new ArrayList<>();
            headerArrayList.add(model_notif_header);
            /*Adding Notification Header to Arraylist*/

            ArrayList arrayList = new ArrayList<>();


            Model_Notif_Create_REST.IsDevice isDevice = new Model_Notif_Create_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);


            /*Calling Notification Create Model with Data*/
            Model_Notif_Create_REST model_notif_create = new Model_Notif_Create_REST();
            model_notif_create.setIsDevice(isDevice);
            model_notif_create.setIvTransmitType(transmit_type);
            model_notif_create.setIvCommit(true);
            model_notif_create.setOperation("CHNOT");
            model_notif_create.setItNotifHeader(headerArrayList);
            model_notif_create.setEtNotifHeader(arrayList);
            model_notif_create.setItNotifItems(causecodeArrayList);
            model_notif_create.setEtNotifItems(arrayList);
            model_notif_create.setEtNotifActvs(arrayList);
            model_notif_create.setItNotifActvs(ActivityArrayList);
            model_notif_create.setEtDocs(arrayList);
            model_notif_create.setItDocs(AttachmentsArrayList);
            model_notif_create.setEtNotifTasks(arrayList);
            model_notif_create.setItNotifTasks(TasksArrayList);
            model_notif_create.setEvMessage(arrayList);
            model_notif_create.setEtNotifLongtext(arrayList);
            model_notif_create.setItNotifLongtext(LongtextsArrayList);
            model_notif_create.setItNotifStatus(statusArrayList);
            model_notif_create.setEtNotifStatus(arrayList);
            /*Calling Notification Create Model with Data*/


            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Notifications_SER> call = service.postNotifCreateData(url_link, model_notif_create, basic, map);
            Response<REST_Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    REST_Notifications_SER notification_response = response.body();


                    String response_data = new Gson().toJson(notification_response.geteTMESSAGE());
                    if (response_data != null && !response_data.equals("") && !response_data.equals("null"))
                    {
                        StringBuilder Message_stringbuilder = new StringBuilder();
                        JSONArray jsonObject = new JSONArray(response_data);
                        for(int i = 0; i < jsonObject.length(); i++)
                        {
                            String Message = jsonObject.getJSONObject(i).optString("MESSAGE");
                            if(i > 0)
                            {
                                Message = Message.substring(1);
                            }
                            Message_stringbuilder.append(Message);
                        }
                        String message = Message_stringbuilder.toString();
                        if(message.startsWith("S"))
                        {
                            String notif_insert_status = REST_Insert_Notifications_Data.Insert_Notif_Data(activity, notification_response, notif_id,"CRNOT");
                            if(notif_insert_status.equalsIgnoreCase("Success"))
                            {
                                Get_Response = "Success";
                                Get_Data = notif_id;
                            }
                            else
                            {
                                Get_Response = "Unable to process Notification Change. Please try again.";
                                Get_Data = "";
                            }
                        }
                        else
                        {
                            Get_Response = "Error";
                            Get_Data = message;
                        }
                    }
                    else
                    {
                        Get_Response = "Unable to process Notification Change. Please try again.";
                        Get_Data = "";
                    }


                }
            }
            else
            {
                Get_Response = "Unable to process Notification Change. Please try again.";
                Get_Data = "";
            }
        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.notification_unabletochange);
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
