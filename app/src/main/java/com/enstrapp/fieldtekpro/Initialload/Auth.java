package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class Auth
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    /* Get_User_Data Table and Fields Names */
    private static final String TABLE_GET_USER_DATA = "GET_USER_DATA";
    private static final String KEY_GET_USER_DATA_ID = "id";
    private static final String KEY_SAPUSER = "Sapuser";
    private static final String KEY_MUSER = "Muser";
    private static final String KEY_FNAME = "Fname";
    private static final String KEY_LNAME = "Lname";
    private static final String KEY_KOSTL = "Kostl";
    private static final String KEY_ARBPL = "Arbpl";
    private static final String KEY_IWERK = "Iwerk";
    private static final String KEY_OUNIT = "Ounit";
    private static final String KEY_PERNR = "Pernr";
    private static final String KEY_INGRP = "Ingrp";
    private static final String KEY_PARVW = "Parvw";
    private static final String KEY_PARNR = "Parnr";
    private static final String KEY_SUSER = "Suser";
    private static final String KEY_USTYP = "Ustyp";
    private static final String KEY_USGRP = "Usgrp";
	/* Get_User_Data Table and Fields Names */

    /* Authorization EtBusf */
    private static final String TABLE_Authorization_EtBusf = "Authorization_EtBusf";
    private static final String KEY_Authorization_EtBusf_ID = "ID";
    private static final String KEY_Authorization_EtBusf_Mandt = "Mandt";
    private static final String KEY_Authorization_EtBusf_Usgrp = "Usgrp";
    private static final String KEY_Authorization_EtBusf_Busftype = "Busftype";
    private static final String KEY_Authorization_EtBusf_Active = "Active";
	/* Authorization EtBusf */

    /* Authorization EtMusrf */
    private static final String TABLE_Authorization_EtMusrf = "Authorization_EtMusrf";
    private static final String KEY_Authorization_EtMusrf_ID = "ID";
    private static final String KEY_Authorization_EtMusrf_Mandt = "Mandt";
    private static final String KEY_Authorization_EtMusrf_Muser = "Muser";
    private static final String KEY_Authorization_EtMusrf_Zdoctype = "Zdoctype";
    private static final String KEY_Authorization_EtMusrf_Zactivity = "Zactivity";
    private static final String KEY_Authorization_EtMusrf_Inactive = "Inactive";
	/* Authorization EtMusrf */

    /* Get_User_FUNCTION_EtUsrf Table and Fields Names */
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf = "GET_USER_FUNCTIONS_EtUsrf";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_ID = "id";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_Mandt = "Mandt";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_Usgrp = "Usgrp";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_Zdoctype = "Zdoctype";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_Zactivity = "Zactivity";
    private static final String KEY_GET_USER_FUNCTIONS_EtUsrf_Inactive = "Inactive";
	/* Get_User_FUNCTION_EtUsrf Table and Fields Names */

    public static String Get_Auth_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equalsIgnoreCase("LOAD"))
            {
                /* Creating GET_USER_DATA Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_USER_DATA);
                String CREATE_GET_USER_DATA_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_USER_DATA + ""
                        + "( "
                        + KEY_GET_USER_DATA_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_SAPUSER + " TEXT,"
                        + KEY_MUSER+ " TEXT,"
                        + KEY_FNAME + " TEXT,"
                        + KEY_LNAME + " TEXT,"
                        + KEY_KOSTL + " TEXT,"
                        + KEY_ARBPL + " TEXT,"
                        + KEY_IWERK+ " TEXT,"
                        + KEY_OUNIT + " TEXT,"
                        + KEY_PERNR + " TEXT,"
                        + KEY_INGRP + " TEXT,"
                        + KEY_PARVW + " TEXT,"
                        + KEY_PARNR+ " TEXT,"
                        + KEY_SUSER + " TEXT,"
                        + KEY_USTYP + " TEXT,"
                        + KEY_USGRP + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_USER_DATA_TABLE);
		        /* Creating GET_USER_DATA Table with Fields */

		        /* Authorization EtBusf */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Authorization_EtBusf);
                String CREATE_TABLE_Authorization_EtBusf = "CREATE TABLE IF NOT EXISTS "+ TABLE_Authorization_EtBusf + ""
                        + "( "
                        + KEY_Authorization_EtBusf_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Authorization_EtBusf_Mandt + " TEXT,"
                        + KEY_Authorization_EtBusf_Usgrp + " TEXT,"
                        + KEY_Authorization_EtBusf_Busftype + " TEXT,"
                        + KEY_Authorization_EtBusf_Active + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Authorization_EtBusf);
		        /* Authorization EtBusf */

		        /* Authorization EtMusrf */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Authorization_EtMusrf);
                String CREATE_TABLE_Authorization_EtMusrf = "CREATE TABLE IF NOT EXISTS "+ TABLE_Authorization_EtMusrf + ""
                        + "( "
                        + KEY_Authorization_EtMusrf_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Authorization_EtMusrf_Mandt + " TEXT,"
                        + KEY_Authorization_EtMusrf_Muser + " TEXT,"
                        + KEY_Authorization_EtMusrf_Zdoctype + " TEXT,"
                        + KEY_Authorization_EtMusrf_Zactivity + " TEXT,"
                        + KEY_Authorization_EtMusrf_Inactive + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Authorization_EtMusrf);
		        /* Authorization EtMusrf */

		        /* Creating Get_User_FUNCTION_EtUsrf Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ KEY_GET_USER_FUNCTIONS_EtUsrf);
                String CREATE_Get_User_FUNCTION_EtUsrf_TABLE = "CREATE TABLE IF NOT EXISTS "+ KEY_GET_USER_FUNCTIONS_EtUsrf+ ""
                        + "( "
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Mandt+ " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Usgrp+ " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Zdoctype+ " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Zactivity+ " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Inactive + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_Get_User_FUNCTION_EtUsrf_TABLE);
		        /* Creating Get_User_FUNCTION_EtUsrf Table with Fields */
            }
            else
            {
                App_db.execSQL("delete from GET_USER_DATA");
                App_db.execSQL("delete from Authorization_EtBusf");
                App_db.execSQL("delete from Authorization_EtMusrf");
                App_db.execSQL("delete from GET_USER_FUNCTIONS_EtUsrf");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"SA", "RD", webservice_type});
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
            map.put("IvTransmitType", transmit_type);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Auth_SER> call = service.getAuthDetails(url_link, basic, map);
            Response<Auth_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Auth_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    Auth_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    App_db.beginTransaction();

                    /*Reading and Inserting Data into Database Table for EsUser*/
                    String EsUser_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEsUser().getResults());
                    if (EsUser_response_data != null && !EsUser_response_data.equals("") && !EsUser_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EsUser_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String sql = "Insert into GET_USER_DATA (Sapuser,Muser,Fname,Lname,Kostl,Arbpl,Iwerk,Ounit,Pernr,Ingrp,Parvw,Parnr,Suser,Ustyp,Usgrp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement statement = App_db.compileStatement(sql);
                            statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Sapuser"));
                                statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Muser"));
                                statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Fname"));
                                statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Lname"));
                                statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Kostl"));
                                statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Arbpl"));
                                statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Iwerk"));
                                statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Ounit"));
                                statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Pernr"));
                                statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Ingrp"));
                                statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Parvw"));
                                statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Parnr"));
                                statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Suser"));
                                statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Ustyp"));
                                statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Usgrp"));
                                statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EsUser*/


                    /*Reading and Inserting Data into Database Table for EtBusf*/
                    String EtBusf_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtBusf().getResults());
                    if (EtBusf_response_data != null && !EtBusf_response_data.equals("") && !EtBusf_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EtBusf_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String sql = "Insert into Authorization_EtBusf (Mandt,Usgrp,Busftype,Active) values (?,?,?,?);";
                            SQLiteStatement statement = App_db.compileStatement(sql);
                            statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Mandt"));
                                statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Usgrp"));
                                statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Busftype"));
                                statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Active"));
                                statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtBusf*/


                    /*Reading and Inserting Data into Database Table for EtMusrf*/
                    String EtMusrf_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtMusrf().getResults());
                    if (EtMusrf_response_data != null && !EtMusrf_response_data.equals("") && !EtMusrf_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EtBusf_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String sql = "Insert into Authorization_EtMusrf (Mandt, Muser, Zdoctype, Zactivity, Inactive) values(?,?,?,?,?);";
                            SQLiteStatement statement = App_db.compileStatement(sql);
                            statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Mandt"));
                                statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Muser"));
                                statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Zdoctype"));
                                statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Zactivity"));
                                statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Inactive"));
                                statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtMusrf*/


                    /*Reading and Inserting Data into Database Table for EtUsrf*/
                    String EtUsrf_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtUsrf().getResults());
                    if (EtUsrf_response_data != null && !EtUsrf_response_data.equals("") && !EtUsrf_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EtUsrf_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String sql = "Insert into GET_USER_FUNCTIONS_EtUsrf (Mandt, Usgrp, Zdoctype, Zactivity, Inactive) values(?,?,?,?,?);";
                            SQLiteStatement statement = App_db.compileStatement(sql);
                            statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Mandt"));
                                statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Usgrp"));
                                statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Zdoctype"));
                                statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Zactivity"));
                                statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Inactive"));
                                statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtMusrf*/


                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            }
            else
            {
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
