package com.enstrapp.fieldtekpro.SendMessage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Initialload.VHLP_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
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

public class VHLP_Token
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String Get_Token_Data(Activity activity)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            App_db.execSQL("delete from EtUsers");
             /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username",null);
            password = app_sharedpreferences.getString("Password",null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Z","F4", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            else
            {
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
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<VHLP_SER> call = service.getVHLPDetails(url_link, basic, map);
            Response<VHLP_SER> response = call.execute();
            int response_status_code = response.code();
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                     /*Reading Response Data and Parsing to Serializable*/
                    VHLP_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                    /*Converting GSON Response to JSON Data for Parsing*/

                    /*Converting Response JSON Data to JSONArray for Reading*/
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/

                    App_db.beginTransaction();

                    /*Reading Data by using FOR Loop*/
                    for(int i = 0; i < response_data_jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                        /*Reading and Inserting Data into Database Table for ET_USERS*/
                        if (jsonObject.has("EtUsers"))
                        {
                            try
                            {
                                String EtUsers_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtUsers().getResults());
                                if (EtUsers_response_data != null && !EtUsers_response_data.equals("") && !EtUsers_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtUsers_response_data);
                                    if (jsonArray.length() > 0)
                                    {
                                        String EtUsers_sql = "Insert into EtUsers (Muser, Fname, Lname, Tokenid) values(?,?,?,?);";
                                        SQLiteStatement EtUsers_statement = App_db.compileStatement(EtUsers_sql);
                                        EtUsers_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtUsers_statement.bindString(1, jsonArray.getJSONObject(j).optString("Muser"));
                                            EtUsers_statement.bindString(2, jsonArray.getJSONObject(j).optString("Fname"));
                                            EtUsers_statement.bindString(3, jsonArray.getJSONObject(j).optString("Lname"));
                                            EtUsers_statement.bindString(4, jsonArray.getJSONObject(j).optString("Tokenid"));
                                            EtUsers_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for ET_USERS*/

                    }

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            }
            else
            {
                Get_Response = "exception";
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
