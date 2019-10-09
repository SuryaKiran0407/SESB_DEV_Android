package com.enstrapp.fieldtekpro.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Notifications_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Notifications_SER;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
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

public class Notifications_Create_REST
{
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "", Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> Post_NotifCreate_Data(Context activity, String transmit_type, String notification_type_id, String long_text, String functionlocation_id, String equipment_id, String equipment_text, String priority_type_id, String priority_type_text, String plannergroup_id, String plannergroup_text, String Reported_by, String personresponsible_id, String personresponsible_text, String req_st_date, String req_st_time, String req_end_date, String req_end_time, String mal_st_date, String mal_st_time, String mal_end_date, String mal_end_time, String effect_id, String effect_text, String plant_id, String workcenter_id, String primary_user_resp, ArrayList<Model_Notif_Causecode> causecodeArrayList, ArrayList<Model_Notif_Activity> ActivityArrayList, ArrayList<Model_Notif_Attachments> AttachmentsArrayList, ArrayList<Model_Notif_Longtext> LongtextsArrayList, ArrayList<Model_Notif_Task> TasksArrayList, ArrayList<Model_CustomInfo> header_custominfo, String Breakdown_status) {
        try
        {
            Get_Response = "";
            Get_Data = "";
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            token = app_sharedpreferences.getString("token", null);
            cookie = app_sharedpreferences.getString("cookie", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"Q", "I", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);

            /*For Send Data in POST Header*/
            Map<String, String> map = new HashMap<>();
            //map.put("x-csrf-token", token);
            //map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");
            /*For Send Data in POST Header*/

            /*Objects for Assigning Notification Header and Sending Data in BODY*/
            Model_Notif_Header_REST model_notif_header = new Model_Notif_Header_REST();
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


            ArrayList<Model_Notif_Longtext_REST> longtextRests = new ArrayList<>();
            if(LongtextsArrayList.size() > 0)
            {
                for(int i = 0; i < LongtextsArrayList.size(); i++)
                {
                    Model_Notif_Longtext_REST mnc = new Model_Notif_Longtext_REST();
                    mnc.setQmnum("");
                    mnc.setObjkey(LongtextsArrayList.get(i).getObjkey());
                    mnc.setObjtype(LongtextsArrayList.get(i).getObjtype());
                    mnc.setTextLine(LongtextsArrayList.get(i).getTextLine());
                    longtextRests.add(mnc);
                }
            }



            ArrayList<Model_Notif_Attachments_REST> attachmentsRestArrayList = new ArrayList<>();
            if(AttachmentsArrayList.size() > 0)
            {
                for(int i = 0; i < AttachmentsArrayList.size(); i++)
                {
                    Model_Notif_Attachments_REST mnc = new Model_Notif_Attachments_REST();
                    mnc.setObjtype(AttachmentsArrayList.get(i).getObjtype());
                    mnc.setZobjid(AttachmentsArrayList.get(i).getZobjid());
                    mnc.setZdoctype(AttachmentsArrayList.get(i).getZdoctype());
                    mnc.setZdoctypeItem(AttachmentsArrayList.get(i).getZdoctypeItem());
                    mnc.setFilename(AttachmentsArrayList.get(i).getFilename());
                    mnc.setFiletype(AttachmentsArrayList.get(i).getFiletype());
                    mnc.setFsize(AttachmentsArrayList.get(i).getFsize());
                    mnc.setContent(AttachmentsArrayList.get(i).getContent());
                    mnc.setDocId(AttachmentsArrayList.get(i).getDocId());
                    mnc.setDocType(AttachmentsArrayList.get(i).getDocType());
                    attachmentsRestArrayList.add(mnc);
                }
            }


            ArrayList<Model_Notif_Causecode_REST> causecodeRests = new ArrayList<>();
            if(causecodeArrayList.size() > 0)
            {
                for(int i = 0; i < causecodeArrayList.size(); i++)
                {
                    Model_Notif_Causecode_REST mnc = new Model_Notif_Causecode_REST();
                    mnc.setQmnum("");
                    mnc.setItemKey(causecodeArrayList.get(i).getItemKey());
                    mnc.setItempartGrp(causecodeArrayList.get(i).getItempartGrp());
                    mnc.setPartgrptext(causecodeArrayList.get(i).getPartgrptext());
                    mnc.setItempartCod(causecodeArrayList.get(i).getItempartCod());
                    mnc.setPartcodetext(causecodeArrayList.get(i).getPartcodetext());
                    mnc.setItemdefectGrp(causecodeArrayList.get(i).getItemdefectGrp());
                    mnc.setDefectgrptext(causecodeArrayList.get(i).getDefectgrptext());
                    mnc.setItemdefectCod(causecodeArrayList.get(i).getItemdefectCod());
                    mnc.setDefectcodetext(causecodeArrayList.get(i).getDefectcodetext());
                    mnc.setItemdefectShtxt(causecodeArrayList.get(i).getItemdefectShtxt());
                    mnc.setCauseKey(causecodeArrayList.get(i).getCauseKey());
                    mnc.setCauseGrp(causecodeArrayList.get(i).getCauseGrp());
                    mnc.setCausegrptext(causecodeArrayList.get(i).getCausegrptext());
                    mnc.setCauseCod(causecodeArrayList.get(i).getCauseCod());
                    mnc.setCausecodetext(causecodeArrayList.get(i).getCausecodetext());
                    mnc.setCauseShtxt(causecodeArrayList.get(i).getCauseShtxt());
                    mnc.setUsr01("");
                    mnc.setUsr02("");
                    mnc.setUsr03("");
                    mnc.setUsr04("");
                    mnc.setUsr05("");
                    mnc.setAction("I");
                    causecodeRests.add(mnc);
                }
            }


            ArrayList<Model_Notif_Activity_REST> activityRests = new ArrayList<>();
            if(ActivityArrayList.size() > 0)
            {
                for(int i = 0; i < ActivityArrayList.size(); i++)
                {
                    Model_Notif_Activity_REST mnc = new Model_Notif_Activity_REST();
                    mnc.setQMNUM("");
                    mnc.setACTKEY(ActivityArrayList.get(i).getActvKey());
                    mnc.setITEMKEY(ActivityArrayList.get(i).getItemKey());
                    mnc.setACTVGRP(ActivityArrayList.get(i).getActvGrp());
                    mnc.setACTVCOD(ActivityArrayList.get(i).getActvCod());
                    mnc.setACTCODETEXT(ActivityArrayList.get(i).getActcodetext());
                    mnc.setACTVSHTXT(ActivityArrayList.get(i).getActvShtxt());
                    mnc.setSTARTDATE(ActivityArrayList.get(i).getStartDate());
                    mnc.setENDDATE(ActivityArrayList.get(i).getEndDate());
                    mnc.setSTARTTIME(ActivityArrayList.get(i).getStartTime());
                    mnc.setENDTIME(ActivityArrayList.get(i).getEndTime());
                    mnc.setACTION("I");
                    activityRests.add(mnc);
                }
            }



            ArrayList<Model_Notif_Task_REST> taskRests = new ArrayList<>();
            if(TasksArrayList.size() > 0)
            {
                for(int i = 0; i < TasksArrayList.size(); i++)
                {
                    Model_Notif_Task_REST mnc = new Model_Notif_Task_REST();
                    mnc.setQMNUM("");
                    mnc.setITEMKEY(TasksArrayList.get(i).getItemKey());
                    mnc.setTASKKEY(TasksArrayList.get(i).getTaskKey());
                    mnc.setTASKGRP(TasksArrayList.get(i).getTaskGrp());
                    mnc.setTASKGRPTEXT(TasksArrayList.get(i).getTaskgrptext());
                    mnc.setTASKCOD(TasksArrayList.get(i).getTaskCod());
                    mnc.setTASKCODETEXT(TasksArrayList.get(i).getTaskcodetext());
                    mnc.setTASKSHTXT(TasksArrayList.get(i).getTaskShtxt());
                    mnc.setPSTER(TasksArrayList.get(i).getPster());
                    mnc.setPETER(TasksArrayList.get(i).getPeter());
                    mnc.setPSTUR(TasksArrayList.get(i).getPstur());
                    mnc.setPETUR(TasksArrayList.get(i).getPetur());
                    mnc.setPARVW(TasksArrayList.get(i).getParvw());
                    mnc.setPARNR(TasksArrayList.get(i).getParnr());
                    mnc.setERLNAM(TasksArrayList.get(i).getErlnam());
                    mnc.setERLDAT(TasksArrayList.get(i).getErldat());
                    mnc.setERLZEIT(TasksArrayList.get(i).getErlzeit());
                    String release = TasksArrayList.get(i).getRelease();
                    if (release.equalsIgnoreCase("X"))
                    {
                        mnc.setRELEASE("X");
                    }
                    else
                    {
                        mnc.setRELEASE("");
                    }
                    String Complete = TasksArrayList.get(i).getComplete();
                    if (Complete.equalsIgnoreCase("X"))
                    {
                        mnc.setCOMPLETE("X");
                    }
                    else
                    {
                        mnc.setCOMPLETE("");
                    }
                    String Success = TasksArrayList.get(i).getSuccess();
                    if (Success.equalsIgnoreCase("X"))
                    {
                        mnc.setSUCCESS("X");
                    }
                    else
                    {
                        mnc.setSUCCESS("");
                    }
                    mnc.setACTION("I");
                    taskRests.add(mnc);
                }
            }


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
            model_notif_create.setOperation("CRNOT");
            model_notif_create.setItNotifHeader(headerArrayList);
            model_notif_create.setEtNotifHeader(arrayList);
            model_notif_create.setItNotifItems(causecodeRests);
            model_notif_create.setEtNotifItems(arrayList);
            model_notif_create.setEtNotifActvs(arrayList);
            model_notif_create.setItNotifActvs(activityRests);
            model_notif_create.setEtDocs(arrayList);
            model_notif_create.setItDocs(attachmentsRestArrayList);
            model_notif_create.setEtNotifTasks(arrayList);
            model_notif_create.setItNotifTasks(taskRests);
            model_notif_create.setEtNotifStatus(arrayList);
            model_notif_create.setEvMessage(arrayList);
            model_notif_create.setEtNotifDup(arrayList);
            model_notif_create.setEtNotifLongtext(arrayList);
            model_notif_create.setItNotifLongtext(longtextRests);
            /*Calling Notification Create Model with Data*/

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Notifications_SER> call = service.postNotifCreateData(url_link, model_notif_create, basic, map);
            Response<REST_Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_response_status_code",response_status_code+"....");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String response_data = response.body().geteVMESSAGE();
                    String EtNotifDup_data = new Gson().toJson(response.body().geteTNOTIFDUP());
                    if (EtNotifDup_data != null && !EtNotifDup_data.equals("") && !EtNotifDup_data.equals("null"))
                    {
                        try
                        {
                            JSONArray EtNotifDup_jsonArray = new JSONArray(EtNotifDup_data);
                            if(EtNotifDup_jsonArray.length() > 0)
                            {
                                Get_Response = "Duplicate";
                                Get_Data = EtNotifDup_jsonArray.toString();
                            }
                            else
                            {
                                Get_Response = "Unable to process Notification Create. Please try again.";
                                Get_Data = "";
                            }
                        }
                        catch (Exception e)
                        {
                        }
                    }
                    else if (response_data != null && !response_data.equals("") && !response_data.equals("null"))
                    {
                        try
                        {
                            String qmnum = response.body().getEvNotifNum();
                            if (qmnum != null && !qmnum.equals("") && !qmnum.equals("null"))
                            {
                                String notif_insert_status = REST_Insert_Notifications_Data.Insert_Notif_Data(activity, response.body(), "","CRNOT");
                                if(notif_insert_status.equalsIgnoreCase("Success"))
                                {
                                    Get_Response = "Success";
                                    Get_Data = qmnum;
                                }
                                else
                                {
                                    Get_Response = "Unable to process Notification Create. Please try again.";
                                    Get_Data = "";
                                }
                            }
                            else
                            {
                                /*StringBuilder Message_stringbuilder = new StringBuilder();
                                JSONArray evmessage_jsonArray = new JSONArray(response_data);
                                for(int i = 0; i < evmessage_jsonArray.length(); i++)
                                {
                                    String Message = evmessage_jsonArray.getJSONObject(i).optString("Message");
                                    Message_stringbuilder.append(Message.substring(1));
                                }*/
                                String message = response_data;
                                if (message != null && !message.equals(""))
                                {
                                    Get_Response = "Error";
                                    Get_Data = message;
                                }
                                else
                                {
                                    Get_Response = "Unable to process Notification Create. Please try again.";
                                    Get_Data = "";
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            Get_Response = "Unable to process Notification Create. Please try again.";
                            Get_Data = "";
                        }
                    }
                }
            }
            else
            {
                Get_Response = activity.getString(R.string.notification_unabletocreate);
                Get_Data = "";
            }

        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.notification_unabletocreate);
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
