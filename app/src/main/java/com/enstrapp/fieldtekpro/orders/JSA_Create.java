package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
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

public class JSA_Create {
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";

    public static String Post_JSA_Data(Activity activity, String title, String remark, String job_id,
                                       String job_text, String aufnr,
                                       List<JSA_AssessmentTeam_Fragment.AssessTeam_List_Object>
                                               assessTeam_list_objects, String opstatus_id,
                                       String opstatus_text, String loc_id, String loc_text,
                                       List<JSA_JobSteps_Fragment.JobStep_List_Object>
                                               jobstep_list_object,
                                       List<JSA_Hazards_Fragment.Hazards_List_Object>
                                               hazards_list_object,
                                       List<JSA_ImpactsControls_Fragment.ImpactsControls_List_Object>
                                               impactsControls_list_objects) {
        try {
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
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where" +
                    " Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D6", "I",
                    webservice_type});
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

            /*Objects for Assigning Basic Info and Sending Data in BODY*/
            JSA_Model_BasicInfo jsa_model_basicInfo = new JSA_Model_BasicInfo();
            jsa_model_basicInfo.setTitle(title);
            jsa_model_basicInfo.setComment(remark);
            jsa_model_basicInfo.setRasstatus("02");
            jsa_model_basicInfo.setJob(job_id);
            jsa_model_basicInfo.setJobtxt(job_text);
            jsa_model_basicInfo.setAufnr(aufnr);
            jsa_model_basicInfo.setAction("I");
            jsa_model_basicInfo.setRastype("EHHSS_RAT_JHA");
            jsa_model_basicInfo.setOpstatus(opstatus_id);
            jsa_model_basicInfo.setOpstatustxt(opstatus_text);
            jsa_model_basicInfo.setLocation(loc_id);
            jsa_model_basicInfo.setLocationtxt(loc_text);
            /*Objects for Assigning Basic Info and Sending Data in BODY*/

            /*Adding Basic Info to Arraylist*/
            ArrayList<JSA_Model_BasicInfo> basicInfoArrayList = new ArrayList<>();
            basicInfoArrayList.add(jsa_model_basicInfo);
            /*Adding Basic Info to Arraylist*/

            /*Adding Assessment Team to Arraylist*/
            ArrayList<JSA_Model_AssessmentTeam> assessmentTeamArrayList = new ArrayList<>();
            /*Adding Assessment Team to Arraylist*/

            /*Objects for Assigning Assessment Team and Sending Data in BODY*/
            for (int i = 0; i < assessTeam_list_objects.size(); i++) {
                JSA_Model_AssessmentTeam jsa_model_assessmentTeam = new JSA_Model_AssessmentTeam();
                jsa_model_assessmentTeam.setAction("I");
                jsa_model_assessmentTeam.setAufnr(aufnr);
                jsa_model_assessmentTeam.setPersonID(assessTeam_list_objects.get(i).getName_id());
                jsa_model_assessmentTeam.setPersontxt(assessTeam_list_objects.get(i).getName_text());
                jsa_model_assessmentTeam.setRole(assessTeam_list_objects.get(i).getRole_id());
                jsa_model_assessmentTeam.setRoletxt(assessTeam_list_objects.get(i).getRole_text());
                assessmentTeamArrayList.add(jsa_model_assessmentTeam);
            }
            /*Objects for Assigning Assessment Team and Sending Data in BODY*/

            /*Adding Job Steps to Arraylist*/
            ArrayList<JSA_Model_ItJSARisks> jobstepsArrayList = new ArrayList<>();
            /*Adding Job Steps to Arraylist*/

            /*Adding Impacts to Arraylist*/
            ArrayList<JSA_Model_ItJSAImp> ImpactsArrayList = new ArrayList<>();
            /*Adding Impacts to Arraylist*/

            /*Adding Controls to Arraylist*/
            ArrayList<JSA_Model_ItJSARskCtrl> ControlsArrayList = new ArrayList<>();
            /*Adding Controls to Arraylist*/

            /*Objects for Hazards and Sending Data in BODY*/
            int k = 0;
            for (int i = 0; i < hazards_list_object.size(); i++) {
                String hazards = hazards_list_object.get(i).getHazard_array();
                if (hazards != null && !hazards.equals("")) {
                    String jobstep_id = hazards_list_object.get(i).getJobstep_id();
                    String jobstep_text = hazards_list_object.get(i).getJobstep_text();
                    String person_resp = hazards_list_object.get(i).getPerson_resp();
                    String hazardcategory_id = hazards_list_object.get(i).getHazardcategory_id();
                    String hazardcategory_text = hazards_list_object.get(i).getHazardcategory_text();
                    JSONArray jsonArray = new JSONArray(hazards);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        String checked_status = jsonArray.getJSONObject(j).optString("selected_status");
                        if (checked_status.equalsIgnoreCase("true")) {
                            k = k + 1;
                            JSA_Model_ItJSARisks jsaModelItJSARisks = new JSA_Model_ItJSARisks();
                            jsaModelItJSARisks.setAufnr(aufnr);
                            jsaModelItJSARisks.setRasid("");
                            jsaModelItJSARisks.setStepID("");
                            jsaModelItJSARisks.setStepSeq(Integer.parseInt(jobstep_id));
                            //jsaModelItJSARisks.setRiskID("000"+k);
                            jsaModelItJSARisks.setRiskID("" + k);
                            jsaModelItJSARisks.setStepPers(person_resp);
                            jsaModelItJSARisks.setHazard(jsonArray.getJSONObject(j).optString("work_id"));
                            jsaModelItJSARisks.setRiskLevel("");
                            jsaModelItJSARisks.setRiskType("");
                            jsaModelItJSARisks.setEvaluation("");
                            jsaModelItJSARisks.setLikelihood("");
                            jsaModelItJSARisks.setSeverity("");
                            jsaModelItJSARisks.setHazCat(hazardcategory_id);
                            jsaModelItJSARisks.setHazardtxt(jsonArray.getJSONObject(j).optString("work_text"));
                            jsaModelItJSARisks.setHazCattxt(hazardcategory_text);
                            jsaModelItJSARisks.setStepTxt(jobstep_text);
                            jsaModelItJSARisks.setRiskLeveltxt("");
                            jsaModelItJSARisks.setRiskTypetxt("");
                            jsaModelItJSARisks.setAction("I");
                            jobstepsArrayList.add(jsaModelItJSARisks);

                            String hazard_id = jsonArray.getJSONObject(j).optString("work_id");

                            /*Objects for Impacts and Sending Data in BODY*/
                            for (int i1 = 0; i1 < impactsControls_list_objects.size(); i1++) {
                                String hazardtype_id = impactsControls_list_objects.get(i1).getHazardtype_id();
                                if (hazard_id.equals(hazardtype_id)) {
                                    String impact_data = impactsControls_list_objects.get(i1).getImpact_data();
                                    if (impact_data != null && !impact_data.equals("")) {
                                        Gson gson = new Gson();
                                        Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {
                                        }.getType();
                                        List<JSA_Impacts_Activity.Type_Object> objectList = gson.fromJson(impact_data, type);
                                        for (int i2 = 0; i2 < objectList.size(); i2++) {
                                            boolean impact_checked_status = objectList.get(i2).getSelected_status();
                                            if (impact_checked_status) {
                                                JSA_Model_ItJSAImp jsaModelItJSAImp = new JSA_Model_ItJSAImp();
                                                jsaModelItJSAImp.setAufnr(aufnr);
                                                jsaModelItJSAImp.setRasid("");
                                                jsaModelItJSAImp.setStepID("");
                                                jsaModelItJSAImp.setRiskID("" + k);
                                                jsaModelItJSAImp.setAction("I");
                                                jsaModelItJSAImp.setImpact(objectList.get(i2).getId());
                                                jsaModelItJSAImp.setImpacttxt(objectList.get(i2).getText());
                                                ImpactsArrayList.add(jsaModelItJSAImp);
                                            }
                                        }
                                    }
                                }
                            }
                            /*Objects for Impacts and Sending Data in BODY*/

                            /*Objects for Controls and Sending Data in BODY*/
                            for (int i1 = 0; i1 < impactsControls_list_objects.size(); i1++) {
                                String hazardtype_id = impactsControls_list_objects.get(i1).getHazardtype_id();
                                if (hazard_id.equals(hazardtype_id)) {
                                    String Controls_data = impactsControls_list_objects.get(i1).getControls_data();
                                    if (Controls_data != null && !Controls_data.equals("")) {
                                        Gson gson = new Gson();
                                        Type type1 = new TypeToken<List<JSA_Controls_Activity.Type_Object>>() {
                                        }.getType();
                                        List<JSA_Controls_Activity.Type_Object> objectList = gson.fromJson(Controls_data, type1);
                                        for (int i2 = 0; i2 < objectList.size(); i2++) {
                                            boolean control_checked_status = objectList.get(i2).getSelected_status();
                                            if (control_checked_status) {
                                                JSA_Model_ItJSARskCtrl jsaModelItJSARskCtrl = new JSA_Model_ItJSARskCtrl();
                                                jsaModelItJSARskCtrl.setAufnr(aufnr);
                                                jsaModelItJSARskCtrl.setRasid("");
                                                jsaModelItJSARskCtrl.setStepID("");
                                                //jsaModelItJSARskCtrl.setRiskID("000"+k);
                                                jsaModelItJSARskCtrl.setRiskID("" + k);
                                                jsaModelItJSARskCtrl.setCatalogCode(objectList.get(i2).getId());
                                                jsaModelItJSARskCtrl.setCatalogCodetxt(objectList.get(i2).getText());
                                                jsaModelItJSARskCtrl.setAction("I");
                                                String type = "", subject = "", CtrlID = "";
                                                String ControlCode = objectList.get(i2).getId();
                                                try {
                                                    Cursor cursor1 = App_db.rawQuery("select *" +
                                                                    " from EtEHSHazctrl where HazardCode = ?" +
                                                                    " and ControlCode = ? ",
                                                            new String[]{hazard_id, ControlCode});
                                                    if (cursor1 != null && cursor1.getCount() > 0) {
                                                        if (cursor1.moveToFirst()) {
                                                            do {
                                                                type = cursor1.getString(3);
                                                                subject = cursor1.getString(4);
                                                                CtrlID = cursor1.getString(6);
                                                            }
                                                            while (cursor1.moveToNext());
                                                        }
                                                    } else {
                                                        cursor1.close();
                                                    }
                                                } catch (Exception e) {
                                                }
                                                jsaModelItJSARskCtrl.setType(type);
                                                jsaModelItJSARskCtrl.setSubject(subject);
                                                jsaModelItJSARskCtrl.setCtrlid(CtrlID);
                                                ControlsArrayList.add(jsaModelItJSARskCtrl);
                                            }
                                        }
                                    }
                                }
                            }
                            /*Objects for Controls and Sending Data in BODY*/
                        }
                    }
                }
            }
            /*Objects for Hazards and Sending Data in BODY*/

            /*Adding EtJSAHdr to Arraylist*/
            ArrayList EtJSAHdr_ArrayList = new ArrayList<>();
            /*Adding EtJSAHdr to Arraylist*/

            /*Adding EtJSAPer to Arraylist*/
            ArrayList EtJSAPer_ArrayList = new ArrayList<>();
            /*Adding EtJSAPer to Arraylist*/

            /*Adding EtJSARisks to Arraylist*/
            ArrayList EtJSARisks_ArrayList = new ArrayList<>();
            /*Adding EtJSARisks to Arraylist*/

            /*Adding EtJSAImp to Arraylist*/
            ArrayList EtJSAImp_ArrayList = new ArrayList<>();
            /*Adding EtJSAImp to Arraylist*/

            /*Adding EtJSARskCtrl to Arraylist*/
            ArrayList EtJSARskCtrl_ArrayList = new ArrayList<>();
            /*Adding EtJSARskCtrl to Arraylist*/

            /*Adding EtMessages to Arraylist*/
            ArrayList EtMessages_ArrayList = new ArrayList<>();
            /*Adding EtMessages to Arraylist*/

            /*Calling JSA Create Model with Data*/
            JSA_Model_Create model_notif_create = new JSA_Model_Create();
            model_notif_create.setIvAufnr(aufnr);
            model_notif_create.setMuser(username.toUpperCase().toString());
            model_notif_create.setDeviceid(device_id);
            model_notif_create.setDevicesno(device_serial_number);
            model_notif_create.setUdid(device_uuid);
            model_notif_create.setIvTransmitType("");
            model_notif_create.setIsJSAHdr(basicInfoArrayList);
            model_notif_create.setItJSAPer(assessmentTeamArrayList);
            model_notif_create.setItJSARisks(jobstepsArrayList);
            model_notif_create.setItJSAImp(ImpactsArrayList);
            model_notif_create.setItJSARskCtrl(ControlsArrayList);
            model_notif_create.setEtJSAHdr(EtJSAHdr_ArrayList);
            model_notif_create.setEtJSAPer(EtJSAPer_ArrayList);
            model_notif_create.setEtJSARisks(EtJSARisks_ArrayList);
            model_notif_create.setEtJSAImp(EtJSAImp_ArrayList);
            model_notif_create.setEtJSARskCtrl(EtJSARskCtrl_ArrayList);
            model_notif_create.setEtMessages(EtMessages_ArrayList);
            /*Calling JSA Create Model with Data*/

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<JSA_Create_SER> call = service.postJSACreateData(url_link, model_notif_create, basic, map);
            Response<JSA_Create_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_response_status_code", response_status_code + "...");
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    JSA_Create_SER rs = response.body();

                    StringBuilder Message_stringbuilder = new StringBuilder();
                    String EtMessagesdata = new Gson().toJson(rs.getD().getEtMessages().getResults());
                    if (EtMessagesdata != null && !EtMessagesdata.equals("")) {
                        JSONArray jsonObject = new JSONArray(EtMessagesdata);
                        for (int i = 0; i < jsonObject.length(); i++) {
                            String Message = jsonObject.getJSONObject(i).optString("Message");
                            Message_stringbuilder.append(Message);
                        }
                    }
                    Get_Response = Message_stringbuilder.toString();
                    /*Log.v("kiran_header",response_data+"....");
                    Log.v("kiran_per",new Gson().toJson(rs.getD().getEtJSAPer())+"....");
                    Log.v("kiran_risk",new Gson().toJson(rs.getD().getEtJSARisks())+"....");
                    Log.v("kiran_imp",new Gson().toJson(rs.getD().getEtJSAImp())+"....");
                    Log.v("kiran_Con",new Gson().toJson(rs.getD().getEtJSARskCtrl())+"....");*/
                }
            } else {
            }
        } catch (Exception e) {
            Get_Response = activity.getString(R.string.jsa_unable);
        } finally {
        }
        return Get_Response;
    }


}
