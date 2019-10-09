package com.enstrapp.fieldtekpro.notifications;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class Notification_Complete_REST
{

    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;

    public static String Get_NOCO_Data(Activity activity, String notification_id)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username",null);
            password = app_sharedpreferences.getString("Password",null);
            /*token = FieldTekPro_SharedPref.getString("token",null);
            cookie = FieldTekPro_SharedPref.getString("cookie",null);*/
            String webservice_type = "REST";
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Q", "S", webservice_type});
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


            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");


            Model_Notif_RELEASE_REST.IsDevice isDevice = new Model_Notif_RELEASE_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);

            ItNotifComplete_REST itNotifComplet=new ItNotifComplete_REST();
            itNotifComplet.setQMNUM(notification_id);


            List<ItNotifComplete_REST> itNotifComplete= new ArrayList<ItNotifComplete_REST>();
             itNotifComplete.add(itNotifComplet);

            Model_Notif_Complete_REST model_notif_complete = new Model_Notif_Complete_REST();
            model_notif_complete.setIsDevice(isDevice);
            model_notif_complete.setIvCommit("X");
            model_notif_complete.setItNotifComplete(itNotifComplete);
            model_notif_complete.setIvTransmitType("");

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Notifications_SER> call = service.postNotif_Complete(url_link,model_notif_complete, basic, map);
            Response<REST_Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null) {
                    //Get_Response = new Gson().toJson(response.body(), SER_DueNotifications.class);

                    /*Reading Response Data and Parsing to Serializable*/
                    REST_Notifications_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    String response_data = new Gson().toJson(response.body().geteTMESSAGE());
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    JSONObject jsonobject = new JSONObject();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < response_data_jsonArray.length(); i++)
                    {
                        jsonobject = response_data_jsonArray.getJSONObject(i);
                        String message = jsonobject.getString("MESSAGE");
                        if(i == 0)
                        {
                            stringBuilder.append(message);
                            stringBuilder.append("\n");
                        }
                        else
                        {
                            stringBuilder.append(message.substring(1));
                            stringBuilder.append("\n");
                        }
                       /* if (message.startsWith("S"))
                        {
                            Get_Response = "success";
                        }
                        else if (message.startsWith("E"))
                        {
                            Get_Response = message;
                        }*/
                        Get_Response = stringBuilder.toString();
                        /*Converting GSON Response to JSON Data for Parsing*/
                    }
                }
            }
            else
            {
            }
        }
        catch (Exception ex)
        {
           Get_Response = "Unable to process Notification Complete. Please try again.";
        }
        finally
        {
        }
        return Get_Response;
    }

}
