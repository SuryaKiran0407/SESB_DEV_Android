package com.enstrapp.fieldtekpro.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Notifications_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Notifications_SER;
import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

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

public class Notification_Postpone_REST {

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
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            Model_Notif_RELEASE_REST.IsDevice isDevice = new Model_Notif_RELEASE_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);

            Model_Notif_RELEASE_REST model_notif_release_rest = new Model_Notif_RELEASE_REST();
            model_notif_release_rest.setIsDevice(isDevice);
            model_notif_release_rest.setIv_qmnum(notification_id);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_Notifications_SER> call = service.Post_Notif_PostPone(url_link, model_notif_release_rest, basic, map);
            Response<REST_Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    REST_Notifications_SER notification_response = response.body();

                    String response_data = notification_response.geteVMESSAGE();
                    if (response_data != null && !response_data.equals("") && !response_data.equals("null"))
                    {
                        String message = response_data;
                        if (message != null && !message.equals("") && !message.equals("null"))
                        {
                            if(message.startsWith("S"))
                            {
                                if(message.contains("Notification already postponed!"))
                                {
                                    Get_Response = "already postponed";
                                }
                                else if(message.contains("Notification postponed successfully"))
                                {
                                    String notif_insert_status = REST_Insert_Notifications_Data.Insert_Notif_Data(context, notification_response, notification_id,"DUNOT");
                                    if(notif_insert_status.equalsIgnoreCase("Success"))
                                    {
                                        Get_Response = "Success";
                                    }
                                    else
                                    {
                                        Get_Response = "Unable to process Notification Postpone. Please try again.";
                                    }
                                }
                                else
                                {
                                    Get_Response = "Error";
                                }
                            }
                            else
                            {
                                Get_Response = message;
                            }
                        }
                        else
                        {
                            Get_Response = "Unable to process Notification Postpone. Please try again.";
                        }
                    }
                    else
                    {
                        Get_Response = "Unable to process Notification Postpone. Please try again.";
                    }

                }
                else
                {
                    Get_Response = "exception";
                }
            }
        }
        catch (Exception ex)
        {
            Get_Response = "exception";
        }
        finally
        {
        }
        return Get_Response;
    }
}
