package com.enstrapp.fieldtekpro.equipment_inspection;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
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

public class Post_InspectionChecklist_REST
{
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "", Get_Data = "";
    private static Map<String, String> response = new HashMap<String, String>();

    public static Map<String, String> post_inspection_data(Activity activity, List<Equipment_StartInspection_Activity.INSP_Object> inspdata_list, String equipment_id, String date, String time) {
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
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EI", "I", webservice_type});
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
            Interface service = retrofit.create(Interface.class);

            /*For Send Data in POST Header*/
            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", token);
            map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");
            /*For Send Data in POST Header*/


            ArrayList<Model_INSP_Imrg> headerArrayList = new ArrayList<>();
            for (int i = 0; i < inspdata_list.size(); i++)
            {
                Model_INSP_Imrg model_insp_imrg = new Model_INSP_Imrg();
                model_insp_imrg.setQmnum("");
                model_insp_imrg.setAufnr("");
                model_insp_imrg.setVornr("");
                model_insp_imrg.setEqunr(equipment_id);
                model_insp_imrg.setMdocm("");
                model_insp_imrg.setPoint(inspdata_list.get(i).getPoint());
                model_insp_imrg.setMpobj("");
                model_insp_imrg.setMpobt("");
                model_insp_imrg.setPsort("");
                model_insp_imrg.setPttxt(inspdata_list.get(i).getPttxt());
                model_insp_imrg.setAtinn("");
                model_insp_imrg.setIdate(date);
                model_insp_imrg.setItime(time);
                model_insp_imrg.setMdtxt(inspdata_list.get(i).getNotes());
                model_insp_imrg.setReadr("");
                boolean normal_result = inspdata_list.get(i).isNormal();
                boolean alarm_result = inspdata_list.get(i).isAlarm();
                boolean critical_result = inspdata_list.get(i).isCritical();
                if (normal_result == true)
                {
                    model_insp_imrg.setAtbez("OK");
                }
                else if (alarm_result == true)
                {
                    model_insp_imrg.setAtbez("POK");
                }
                else if (critical_result == true)
                {
                    model_insp_imrg.setAtbez("NOK");
                }
                else
                {
                    model_insp_imrg.setAtbez("");
                }
                model_insp_imrg.setMsehi("");
                model_insp_imrg.setMsehl("");
                model_insp_imrg.setReadc(inspdata_list.get(i).getReading());
                model_insp_imrg.setDesic("");
                model_insp_imrg.setPrest("");
                boolean cat = inspdata_list.get(i).isCreated_after_task();
                if (cat == true)
                {
                    model_insp_imrg.setDocaf("X");
                }
                else
                {
                    model_insp_imrg.setDocaf("");
                }
                model_insp_imrg.setCodct("");
                model_insp_imrg.setCodgr(inspdata_list.get(i).getCodgr());
                model_insp_imrg.setVlcod(inspdata_list.get(i).getValuation_type_id());
                model_insp_imrg.setAction("I");
                headerArrayList.add(model_insp_imrg);
            }


            /*Adding EtMsg to Arraylist*/
            ArrayList EtMsg_ArrayList = new ArrayList<>();
            /*Adding EtMsg to Arraylist*/


            /*Calling Model_INSP_CHK Model with Data*/
            Model_INSP_CHK model_notif_create = new Model_INSP_CHK();
            model_notif_create.setMuser(username.toUpperCase().toString());
            model_notif_create.setDeviceid(device_id);
            model_notif_create.setDevicesno(device_serial_number);
            model_notif_create.setUdid(device_uuid);
            model_notif_create.setIvTransmitType("LOAD");
            model_notif_create.setIvCommit(true);
            model_notif_create.setItMeaImrg(headerArrayList);
            model_notif_create.setEtMsg(EtMsg_ArrayList);
            /*Calling Model_INSP_CHK Model with Data*/

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<INSPCHK_SER> call = service.postINSPCHK(url_link, model_notif_create, basic, map);
            Response<INSPCHK_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    INSPCHK_SER rs = response.body();
                    StringBuilder Message_stringbuilder = new StringBuilder();
                    String response_data = new Gson().toJson(rs.getD().getEtMsg().getResults());
                    if (response_data != null && !response_data.equals(""))
                    {
                        JSONArray jsonObject = new JSONArray(response_data);
                        for (int i = 0; i < jsonObject.length(); i++)
                        {
                            String Message = jsonObject.getJSONObject(i).optString("Message");
                            Message_stringbuilder.append(Message);
                        }
                    }
                    Get_Response = "success";
                    Get_Data = Message_stringbuilder.toString();
                }
            }
            else
            {
            }
        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.unable_inspchklst);
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
