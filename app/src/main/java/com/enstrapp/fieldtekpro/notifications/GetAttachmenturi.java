package com.enstrapp.fieldtekpro.notifications;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;

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

public class GetAttachmenturi
{

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Data = "", Get_Response = "";
    private static Map<String, String> response1 = new HashMap<String, String>();



    public static Map<String, String>  DocTypeUri(Activity activity, String DocId, String object_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"AT", "AT", webservice_type});
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
            map.put("IvUser", username);
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvTransmitType", "LOAD");
            map.put("DocID", DocId);
            map.put("Objtype", object_type);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Attachment_Download_SER> call = service.getAttachmentURLDetails(url_link, basic, map);
            Response<Attachment_Download_SER> response = call.execute();
            int response_status_code = response.code();
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    Attachment_Download_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = rs.getD().getContent();
                    /*Converting GSON Response to JSON Data for Parsing*/

                    if (response_data != null && !response_data.equals(""))
                    {
                        Get_Response = "Success";
                        Get_Data = response_data;
                    }
                    else
                    {
                        Get_Response = "error";
                        Get_Data = "";
                    }

                }
            }
            else
            {
                Get_Response = "exception";
                Get_Data = "";
            }
        }
        catch (Exception ex)
        {
            Get_Response = "exception";
            Get_Data = "";
        }
        finally
        {
        }
        response1.put("response_status", Get_Response);
        response1.put("response_data", Get_Data);
        return response1;
    }

}
