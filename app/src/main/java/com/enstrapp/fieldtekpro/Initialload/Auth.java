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
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

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

public class Auth {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

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

    public static String Get_Auth_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD")) {

                /* Authorization EtBusf */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Authorization_EtBusf);
                String CREATE_TABLE_Authorization_EtBusf = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Authorization_EtBusf + ""
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
                String CREATE_TABLE_Authorization_EtMusrf = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Authorization_EtMusrf + ""
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
                App_db.execSQL("DROP TABLE IF EXISTS " + KEY_GET_USER_FUNCTIONS_EtUsrf);
                String CREATE_Get_User_FUNCTION_EtUsrf_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + KEY_GET_USER_FUNCTIONS_EtUsrf + ""
                        + "( "
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Mandt + " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Usgrp + " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Zdoctype + " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Zactivity + " TEXT,"
                        + KEY_GET_USER_FUNCTIONS_EtUsrf_Inactive + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_Get_User_FUNCTION_EtUsrf_TABLE);
                /* Creating Get_User_FUNCTION_EtUsrf Table with Fields */
            } else {
//                App_db.execSQL("delete from GET_USER_DATA");
                App_db.execSQL("delete from Authorization_EtBusf");
                App_db.execSQL("delete from Authorization_EtMusrf");
                App_db.execSQL("delete from GET_USER_FUNCTIONS_EtUsrf");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"SA", "RD", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180000, TimeUnit.MILLISECONDS)
                    .writeTimeout(180000, TimeUnit.MILLISECONDS)
                    .readTimeout(180000, TimeUnit.MILLISECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " +
                    Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Auth_SER> call = service.getAuthDetails(url_link, basic, map);
            Response<Auth_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Auth_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Auth_SER.Result> results = response.body().getD().getResults();
                    App_db.beginTransaction();

                    if (results != null && results.size() > 0) {

                        /*EtBusf*/
                        Auth_SER.EtBusf etBusf = results.get(0).getEtBusf();
                        if (etBusf != null) {
                            List<Auth_SER.EtBusf_Result> etBusfResults = etBusf.getResults();
                            if (etBusfResults != null && etBusfResults.size() > 0) {
                                String sql = "Insert into Authorization_EtBusf (Mandt,Usgrp," +
                                        "Busftype,Active) values (?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for (Auth_SER.EtBusf_Result eB : etBusfResults) {
                                    statement.bindString(1, c_e.check_empty(eB.getMandt()));
                                    statement.bindString(2, c_e.check_empty(eB.getUsgrp()));
                                    statement.bindString(3, c_e.check_empty(eB.getBusftype()));
                                    statement.bindString(4, c_e.check_empty(eB.getActive()));
                                    statement.execute();
                                }
                            }
                        }

                        /*EtMusrf*/
                        Auth_SER.EtMusrf etMusrf = results.get(0).getEtMusrf();
                        if (etMusrf != null) {
                            List<Auth_SER.EtMusrf_Result> etMusrfResults = etMusrf.getResults();
                            if (etMusrfResults != null && etMusrfResults.size() > 0) {
                                String sql = "Insert into Authorization_EtMusrf (Mandt, Muser," +
                                        " Zdoctype, Zactivity, Inactive) values(?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for (Auth_SER.EtMusrf_Result eM : etMusrfResults) {
                                    statement.bindString(1, c_e.check_empty(eM.getMandt()));
                                    statement.bindString(2, c_e.check_empty(eM.getMuser()));
                                    statement.bindString(3, c_e.check_empty(eM.getZdoctype()));
                                    statement.bindString(4, c_e.check_empty(eM.getZactivity()));
                                    statement.bindString(5, c_e.check_empty(eM.getInactive()));
                                    statement.execute();
                                }
                            }
                        }

                        /*EtUsrf*/
                        Auth_SER.EtUsrf etUsrf = results.get(0).getEtUsrf();
                        if (etUsrf != null) {
                            List<Auth_SER.EtUsrf_Result> etUsrfResults = etUsrf.getResults();
                            if (etUsrfResults != null && etUsrfResults.size() > 0) {
                                String sql = "Insert into GET_USER_FUNCTIONS_EtUsrf (Mandt, Usgrp," +
                                        " Zdoctype, Zactivity, Inactive) values(?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(sql);
                                statement.clearBindings();
                                for (Auth_SER.EtUsrf_Result eUs : etUsrfResults) {
                                    statement.bindString(1, c_e.check_empty(eUs.getMandt()));
                                    statement.bindString(2, c_e.check_empty(eUs.getUsgrp()));
                                    statement.bindString(3, c_e.check_empty(eUs.getZdoctype()));
                                    statement.bindString(4, c_e.check_empty(eUs.getZactivity()));
                                    statement.bindString(5, c_e.check_empty(eUs.getInactive()));
                                    statement.execute();
                                }
                            }
                        }
                    }
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
