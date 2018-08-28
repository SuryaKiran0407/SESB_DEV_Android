package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

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

public class SyncMap
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Syncmap_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";


    /* GEO_TAG */
    private static final String TABLE_GEO_TAG = "GEO_TAG";
    private static final String KEY_GEO_TAG_ID = "ID";
    private static final String KEY_GEO_TAG_Tplnr = "Tplnr";
    private static final String KEY_GEO_TAG_Equnr = "Equnr";
    private static final String KEY_GEO_TAG_Latitude = "Latitude";
    private static final String KEY_GEO_TAG_Longitude = "Longitude";
    private static final String KEY_GEO_TAG_Altitude = "Altitude";
	/* GEO_TAG */

    /* Get_Sync_Map_Data Table and Fields Names */
    public static final String TABLE_GetSyncMapData = "Get_SYNC_MAP_DATA";
    private static final String KEY_GetSyncMapData_ID = "id";
    private static final String KEY_Sysid = "Sysid";
    private static final String KEY_Endpoint = "Endpoint";
    private static final String KEY_Zdoctype = "Zdoctype";
    private static final String KEY_Zactivity = "Zactivity";
    private static final String KEY_Zwsrv = "Zwsrv";
	/* Get_Sync_Map_Data Table and Fields Names */

    /*GET ORDERS_CONFIRMATION_FIELDS Table and Fields names */
    private static final String TABLE_ORDER_CONFIRMATION_TIMER = "ORDER_CONFIRMATION_TIMER";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_ID = "id";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_UUID = "UUID";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_OrderNo = "Order_No";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_OperationNo = "Operation_ID";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_Timestamp = "Timestamp";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_Status = "STATUS";
    private static final String KEY_ORDER_CONFIRMATION_TIMER_Sec = "seconds";
    /*GET ORDERS_CONFIRMATION_FIELDS Table and Fields names */

    /* Change Log Table and Fields Names */
    private static final String TABLE_ChangeLog = "Alert_Log";
    private static final String KEY_ChangeLog_ID = "ID";
    private static final String KEY_ChangeLog_Date = "DATE";
    private static final String KEY_ChangeLog_Time = "TIME";
    private static final String KEY_ChangeLog_DocumentCategory = "DOCUMENT_CATEGORY";
    private static final String KEY_ChangeLog_ActivityType = "ACTIVITY_TYPE";
    private static final String KEY_ChangeLog_User = "USER";
    private static final String KEY_ChangeLog_ObjectID = "OBJECT_ID";
    private static final String KEY_ChangeLog_Status = "STATUS";
    private static final String KEY_ChangeLog_UUID = "UUID";
    private static final String KEY_ChangeLog_Message = "MESSAGE";
    private static final String KEY_ChangeLog_Loguuid = "LOG_UUID";
	/* Change Log Table and Fields Names */

    public static String Get_Syncmap_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equalsIgnoreCase("LOAD"))
            {
                /* Creating GEO_TAG Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GEO_TAG);
                String CREATE_TABLE_GEO_TAG = "CREATE TABLE IF NOT EXISTS "+ TABLE_GEO_TAG+ ""
                        + "( "
                        + KEY_GEO_TAG_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GEO_TAG_Tplnr+ " TEXT,"
                        + KEY_GEO_TAG_Equnr + " TEXT,"
                        + KEY_GEO_TAG_Latitude + " TEXT,"
                        + KEY_GEO_TAG_Longitude + " TEXT,"
                        + KEY_GEO_TAG_Altitude + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GEO_TAG);
		        /* Creating GEO_TAG Table with Fields */

                /* Creating Get_Sync_Map_Data Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GetSyncMapData);
                String CREATE_Get_Sync_Map_Data_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GetSyncMapData + ""
                        + "( "
                        + KEY_GetSyncMapData_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_Sysid + " TEXT,"
                        + KEY_Endpoint+ " TEXT,"
                        + KEY_Zdoctype + " TEXT,"
                        + KEY_Zactivity + " TEXT,"
                        + KEY_Zwsrv + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_Get_Sync_Map_Data_TABLE);
		        /* Creating Get_Sync_Map_Data Table with Fields */

		        /* GET_CONFIRMATION_TIMER */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_CONFIRMATION_TIMER);
                String CREATE_TABLE_ORDER_CONFIRMATION_TIMER = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDER_CONFIRMATION_TIMER + ""
                        + "( "
                        + KEY_ORDER_CONFIRMATION_TIMER_ID + " INTEGER PRIMARY KEY,"
                        + KEY_ORDER_CONFIRMATION_TIMER_UUID + " TEXT,"
                        + KEY_ORDER_CONFIRMATION_TIMER_OrderNo + " TEXT,"
                        + KEY_ORDER_CONFIRMATION_TIMER_OperationNo + " TEXT,"
                        + KEY_ORDER_CONFIRMATION_TIMER_Timestamp + " TEXT,"
                        + KEY_ORDER_CONFIRMATION_TIMER_Status + " TEXT,"
                        + KEY_ORDER_CONFIRMATION_TIMER_Sec + " TEXT "
                        + ")";
                App_db.execSQL(CREATE_TABLE_ORDER_CONFIRMATION_TIMER);
		        /* GET_CONFIRMATION_TIMER */

		        /* Change Log Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_ChangeLog);
                String CREATE_TABLE_ChangeLog = "CREATE TABLE IF NOT EXISTS "+ TABLE_ChangeLog + ""
                        + "( "
                        + KEY_ChangeLog_ID + " INTEGER PRIMARY KEY,"
                        + KEY_ChangeLog_Date + " TEXT,"
                        + KEY_ChangeLog_Time + " TEXT,"
                        + KEY_ChangeLog_DocumentCategory + " TEXT,"
                        + KEY_ChangeLog_ActivityType + " TEXT,"
                        + KEY_ChangeLog_User + " TEXT,"
                        + KEY_ChangeLog_ObjectID + " TEXT,"
                        + KEY_ChangeLog_Status + " TEXT,"
                        + KEY_ChangeLog_UUID + " TEXT,"
                        + KEY_ChangeLog_Message + " TEXT,"
                        + KEY_ChangeLog_Loguuid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_ChangeLog);
		        /* Change Log Table and Fields Names */
            }
            else
            {
                App_db.execSQL("delete from Get_SYNC_MAP_DATA");
                App_db.execSQL("delete from Alert_Log");
                App_db.execSQL("delete from GEO_TAG");
            }
            url_link = activity.getResources().getString(R.string.syncmap_url);
             /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
		    /* Initializing Shared Preferences */
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
            Call<SyncMap_SER> call = service.getSyncmapDetails(url_link, basic, map);
            Response<SyncMap_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Syncmap_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String response_data_status = new Gson().toJson(response.body(),SyncMap_SER.class);
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response_data_status);
                        if(jsonObject.has("d"))
                        {
                            String results = jsonObject.getJSONObject("d").getString("results");
                            if (results != null && !results.equals("") && !results.equals("null"))
                            {
                                JSONArray jsonArray = new JSONArray(results);
                                if(jsonArray.length() > 0)
                                {
                                    String sql = "Insert into Get_SYNC_MAP_DATA (Sysid, Endpoint, Zdoctype, Zactivity, Zwsrv) values(?,?,?,?,?);";
                                    SQLiteStatement statement = App_db.compileStatement(sql);
                                    App_db.beginTransaction();
                                    statement.clearBindings();
                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        statement.bindString(1, jsonArray.getJSONObject(i).optString("Sysid"));
                                        statement.bindString(2, jsonArray.getJSONObject(i).optString("Endpoint"));
                                        statement.bindString(3, jsonArray.getJSONObject(i).optString("Zdoctype"));
                                        statement.bindString(4, jsonArray.getJSONObject(i).optString("Zactivity"));
                                        statement.bindString(5, jsonArray.getJSONObject(i).optString("Zwsrv"));
                                        statement.execute();
                                    }
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();
                                    Get_Syncmap_Response = "success";
                                }
                                else
                                {
                                    Get_Syncmap_Response = "no data";
                                }
                            }
                            else
                            {
                                Get_Syncmap_Response = "no data";
                            }
                        }
                        else
                        {
                            Get_Syncmap_Response = "no data";
                        }
                    }
                    catch (Exception e)
                    {
                        Get_Syncmap_Response = "exception";
                    }
                }
                else
                {
                    Get_Syncmap_Response = "no data";
                }
            }
            else
            {
                Get_Syncmap_Response = "no data";
            }
        }
        catch (Exception ex)
        {
            Get_Syncmap_Response = "exception";
        }
        finally
        {
        }
        return Get_Syncmap_Response;
    }

}
