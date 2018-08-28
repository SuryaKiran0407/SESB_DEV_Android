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

public class VHLP_WCM
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    /* EtWcmWcco Table and Fields Names */
    private static final String TABLE_EtWcmWcco = "EtWcmWcco";
    private static final String KEY_EtWcmWcco_id = "id";
    private static final String KEY_EtWcmWcco_Iwerk = "Iwerk";
    private static final String KEY_EtWcmWcco_Objart = "Objart";
    private static final String KEY_EtWcmWcco_Objtyp = "Objtyp";
    private static final String KEY_EtWcmWcco_Wcmuse = "Wcmuse";
    private static final String KEY_EtWcmWcco_Direction = "Direction";
    private static final String KEY_EtWcmWcco_Asgnflg = "Asgnflg";
	/* EtWcmWcco Table and Fields Names */

    private static final String TABLE_EtUsgrpWccp = "EtUsgrpWccp";
    private static final String KEY_EtUsgrpWccp_ID = "id";
    private static final String KEY_EtUsgrpWccp_Usgrp = "Usgrp";
    private static final String KEY_EtUsgrpWccp_Pmsog= "Pmsog";

    private static final String TABLE_EtWcmUsages = "EtWcmUsages";
    private static final String KEY_EtWcmUsages_ID = "id";
    private static final String KEY_EtWcmUsages_Iwerk = "Iwerk";
    private static final String KEY_EtWcmUsages_Objart = "Objart";
    private static final String KEY_EtWcmUsages_Use = "Use";
    private static final String KEY_EtWcmUsages_Usex = "Usex";

    /* EtWcmBegru Table and Fields Names */
    private static final String TABLE_GET_EtWcmBegru = "EtWcmBegru";
    private static final String KEY_GET_EtWcmBegru_ID = "id";
    private static final String KEY_GET_EtWcmBegru_Begru = "Begru";
    private static final String KEY_GET_EtWcmBegru_Begtx = "Begtx";
	/* EtWcmBegru Table and Fields Names */

    /* EtWcmTgtyp Table and Fields Names */
    private static final String TABLE_EtWcmTgtyp = "EtWcmTgtyp";
    private static final String KEY_EtWcmTgtyp_ID = "id";
    private static final String KEY_EtWcmTgtyp_Iwerk = "Iwerk";
    private static final String KEY_EtWcmTgtyp_Tggrp = "Tggrp";
    private static final String KEY_EtWcmTgtyp_Tgproc = "Tgproc";
    private static final String KEY_EtWcmTgtyp_Tgtyp = "Tgtyp";
    private static final String KEY_EtWcmTgtyp_Unproc = "Unproc";
    private static final String KEY_EtWcmTgtyp_Untyp = "Untyp";
    private static final String KEY_EtWcmTgtyp_Phblflg = "Phblflg";
    private static final String KEY_EtWcmTgtyp_Tgflg = "Tgflg";
    private static final String KEY_EtWcmTgtyp_Usable = "Usable";
    private static final String KEY_EtWcmTgtyp_Tgprocx = "Tgprocx ";
	/* EtWcmTgtyp Table and Fields Names */

    /* EtWcmTypes Table and Fields Names */
    private static final String TABLE_EtWcmTypes = "EtWcmTypes";
    private static final String KEY_EtWcmTypes_ID = "id";
    private static final String KEY_EtWcmTypes_Iwerk = "Iwerk";
    private static final String KEY_EtWcmTypes_Objart= "Objart";
    private static final String KEY_EtWcmTypes_Objtyp = "Objtyp";
    private static final String KEY_EtWcmTypes_Stxt = "Stxt";
	/* EtWcmTypes Table and Fields Names */

    /* EtWcmWcvp6 Table and Fields Names */
    private static final String TABLE_EtWcmWcvp6 = "EtWcmWcvp6";
    private static final String KEY_EtWcmWcvp6_ID = "id";
    private static final String KEY_EtWcmWcvp6_Mandt = "Mandt";
    private static final String KEY_EtWcmWcvp6_Iwerk = "Iwerk";
    private static final String KEY_EtWcmWcvp6_Objart= "Objart";
    private static final String KEY_EtWcmWcvp6_Objtyp = "Objtyp";
    private static final String KEY_EtWcmWcvp6_Pmsog = "Pmsog";
    private static final String KEY_EtWcmWcvp6_Gntxt = "Gntxt";
    private static final String KEY_EtWcmWcvp6_Agent = "Agent";
    private static final String KEY_EtWcmWcvp6_Stxt = "Stxt";
    /* EtWcmWcvp6 Table and Fields Names */

    private static final String TABLE_EtWcmWork = "EtWcmWork";
    private static final String KEY_EtWcmWork_ID = "id";
    private static final String KEY_EtWcmWork_Iwerk = "Iwerk";
    private static final String KEY_EtWcmWork_Objart = "Objart";
    private static final String KEY_EtWcmWork_Objtyp = "Objtyp";
    private static final String KEY_EtWcmWork_Wapiuse = "Wapiuse";
    private static final String KEY_EtWcmWork_Wkid = "Wkid";
    private static final String KEY_EtWcmWork_ScrtextL = "ScrtextL";
    private static final String KEY_EtWcmWork_Wkgrp = "Wkgrp";
    private static final String KEY_EtWcmWork_Propflg = "Propflg";
    private static final String KEY_EtWcmWork_Modflg = "Modflg";
    private static final String KEY_EtWcmWork_Dpflg = "Dpflg";
    private static final String KEY_EtWcmWork_Aprv4unmarked = "Aprv4unmarked";
    private static final String KEY_EtWcmWork_Aprv4marked = "Aprv4marked";

    private static final String TABLE_EtWCMReqm = "EtWCMReqm";
    private static final String KEY_EtWCMReqm_ID = "id";
    private static final String KEY_EtWCMReqm_Iwerk = "Iwerk";
    private static final String KEY_EtWCMReqm_Objart = "Objart";
    private static final String KEY_EtWCMReqm_Objtyp = "Objtyp";
    private static final String KEY_EtWCMReqm_Wapiuse = "Wapiuse";
    private static final String KEY_EtWCMReqm_Needid = "Needid";
    private static final String KEY_EtWCMReqm_ScrtextL = "ScrtextL";
    private static final String KEY_EtWCMReqm_Needgrp = "Needgrp";
    private static final String KEY_EtWCMReqm_Propflg = "Propflg";
    private static final String KEY_EtWCMReqm_Modflg = "Modflg";
    private static final String KEY_EtWCMReqm_Dpflg = "Dpflg";
    private static final String KEY_EtWCMReqm_Aprv4unmarked = "Aprv4unmarked";
    private static final String KEY_EtWCMReqm_Aprv4marked = "Aprv4marked";

    public static String Get_VHLP_WCM_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equalsIgnoreCase("LOAD"))
            {

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtWcmWcco);
                String CREATE_TABLE_EtWcmWcco = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmWcco+ ""
                        + "( "
                        + KEY_EtWcmWcco_id+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWcco_Iwerk+ " TEXT,"
                        + KEY_EtWcmWcco_Objart+ " TEXT,"
                        + KEY_EtWcmWcco_Objtyp + " TEXT,"
                        + KEY_EtWcmWcco_Wcmuse+ " TEXT,"
                        + KEY_EtWcmWcco_Direction+ " TEXT,"
                        + KEY_EtWcmWcco_Asgnflg+ " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWcco);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmUsages);
                String CREATE_TABLE_EtWcmUsages = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmUsages + ""
                        + "( "
                        + KEY_EtWcmUsages_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmUsages_Iwerk+ " TEXT,"
                        + KEY_EtWcmUsages_Objart + " TEXT,"
                        + KEY_EtWcmUsages_Use + " TEXT,"
                        + KEY_EtWcmUsages_Usex + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmUsages);

                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_EtWcmBegru);
                String CREATE_TABLE_GET_EtWcmBegru = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtWcmBegru+ ""
                        + "( "
                        + KEY_GET_EtWcmBegru_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmBegru_Begru+ " TEXT,"
                        + KEY_GET_EtWcmBegru_Begtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmBegru);
		        /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */

		        /* Creating EtWcmTgtyp Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtWcmTgtyp);
                String CREATE_TABLE_EtWcmTgtyp = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmTgtyp + ""
                        + "( "
                        + KEY_EtWcmTgtyp_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmTgtyp_Iwerk + " TEXT,"
                        + KEY_EtWcmTgtyp_Tggrp + " TEXT,"
                        + KEY_EtWcmTgtyp_Tgproc + " TEXT,"
                        + KEY_EtWcmTgtyp_Tgtyp + " TEXT,"
                        + KEY_EtWcmTgtyp_Unproc + " TEXT,"
                        + KEY_EtWcmTgtyp_Untyp + " TEXT,"
                        + KEY_EtWcmTgtyp_Phblflg + " TEXT,"
                        + KEY_EtWcmTgtyp_Tgflg + " TEXT,"
                        + KEY_EtWcmTgtyp_Usable + " TEXT,"
                        + KEY_EtWcmTgtyp_Tgprocx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmTgtyp);
		        /* Creating EtWcmTgtyp Table with Fields */

		        /* Creating TABLE_EtWcmTypes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmTypes);
                String CREATE_TABLE_EtWcmTypes = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmTypes + ""
                        + "( "
                        + KEY_EtWcmTypes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmTypes_Iwerk + " TEXT,"
                        + KEY_EtWcmTypes_Objart + " TEXT,"
                        + KEY_EtWcmTypes_Objtyp + " TEXT,"
                        + KEY_EtWcmTypes_Stxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmTypes);
		        /* Creating TABLE_EtWcmTypes Table with Fields */

		        /* Creating TABLE_EtWcmWcvp6 Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtWcmWcvp6);
                String CREATE_TABLE_EtWcmWcvp6 = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmWcvp6 + ""
                        + "( "
                        + KEY_EtWcmWcvp6_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWcvp6_Mandt + " TEXT,"
                        + KEY_EtWcmWcvp6_Iwerk + " TEXT,"
                        + KEY_EtWcmWcvp6_Objart + " TEXT,"
                        + KEY_EtWcmWcvp6_Objtyp + " TEXT,"
                        + KEY_EtWcmWcvp6_Pmsog + " TEXT,"
                        + KEY_EtWcmWcvp6_Gntxt + " TEXT,"
                        + KEY_EtWcmWcvp6_Agent + " TEXT,"
                        + KEY_EtWcmWcvp6_Stxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWcvp6);
		        /* Creating TABLE_EtWcmWcvp6 Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmWork);
                String CREATE_TABLE_EtWcmWork = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWcmWork + ""
                        + "( "
                        + KEY_EtWcmWork_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWork_Iwerk+ " TEXT,"
                        + KEY_EtWcmWork_Objart + " TEXT,"
                        + KEY_EtWcmWork_Objtyp + " TEXT,"
                        + KEY_EtWcmWork_Wapiuse + " TEXT,"
                        + KEY_EtWcmWork_Wkid+ " TEXT,"
                        + KEY_EtWcmWork_ScrtextL + " TEXT,"
                        + KEY_EtWcmWork_Wkgrp + " TEXT,"
                        + KEY_EtWcmWork_Propflg + " TEXT,"
                        + KEY_EtWcmWork_Modflg+ " TEXT,"
                        + KEY_EtWcmWork_Dpflg + " TEXT,"
                        + KEY_EtWcmWork_Aprv4unmarked + " TEXT,"
                        + KEY_EtWcmWork_Aprv4marked + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWork);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWCMReqm);
                String CREATE_TABLE_EtWCMReqm = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWCMReqm + ""
                        + "( "
                        + KEY_EtWCMReqm_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWCMReqm_Iwerk+ " TEXT,"
                        + KEY_EtWCMReqm_Objart + " TEXT,"
                        + KEY_EtWCMReqm_Objtyp + " TEXT,"
                        + KEY_EtWCMReqm_Wapiuse + " TEXT,"
                        + KEY_EtWCMReqm_Needid+ " TEXT,"
                        + KEY_EtWCMReqm_ScrtextL + " TEXT,"
                        + KEY_EtWCMReqm_Needgrp + " TEXT,"
                        + KEY_EtWCMReqm_Propflg + " TEXT,"
                        + KEY_EtWCMReqm_Modflg+ " TEXT,"
                        + KEY_EtWCMReqm_Dpflg + " TEXT,"
                        + KEY_EtWCMReqm_Aprv4unmarked + " TEXT,"
                        + KEY_EtWCMReqm_Aprv4marked + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWCMReqm);

                /* Creating EtUsgrpWccp Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtUsgrpWccp);
                String CREATE_TABLE_EtUsgrpWccp = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtUsgrpWccp+ ""
                        + "( "
                        + KEY_EtUsgrpWccp_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtUsgrpWccp_Usgrp+ " TEXT,"
                        + KEY_EtUsgrpWccp_Pmsog + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUsgrpWccp);
		        /* Creating EtUsgrpWccp Table with Fields */

            }
            else
            {

            }
             /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username",null);
            password = app_sharedpreferences.getString("Password",null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Z1","F4", webservice_type});
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
            map.put("IvTransmitType", transmit_type);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<VHLP_WCM_SER> call = service.getVHLPWCMDetails(url_link, basic, map);
            Response<VHLP_WCM_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Vhlp_WCM_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                     /*Reading Response Data and Parsing to Serializable*/
                    VHLP_WCM_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                    if (response_data != null && !response_data.equals("") && !response_data.equals("null"))
                    {
                         /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            App_db.beginTransaction();


                            /*Reading and Inserting Data into Database Table for EtWCMUsgrp*/
                            try
                            {
                                String EtWCMUsgrp_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMUsgrp().getResults());
                                if (EtWCMUsgrp_response_data != null && !EtWCMUsgrp_response_data.equals("") && !EtWCMUsgrp_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMUsgrp_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtUsgrpWccp_sql = "Insert into EtUsgrpWccp (Usgrp, Pmsog) values(?,?);";
                                        SQLiteStatement EtUsgrpWccp_statement = App_db.compileStatement(EtUsgrpWccp_sql);
                                        EtUsgrpWccp_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtUsgrpWccp_statement.bindString(1, jsonArray.getJSONObject(j).optString("Usgrp"));
                                            EtUsgrpWccp_statement.bindString(2, jsonArray.getJSONObject(j).optString("Pmsog"));
                                            EtUsgrpWccp_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMUsgrp*/


                            /*Reading and Inserting Data into Database Table for EtWCMUsages*/
                            try
                            {
                                String EtWCMUsages_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMUsages().getResults());
                                if (EtWCMUsages_response_data != null && !EtWCMUsages_response_data.equals("") && !EtWCMUsages_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMUsages_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmUsages_sql = "Insert into EtWcmUsages (Iwerk, Objart, Use, Usex) values(?,?,?,?);";
                                        SQLiteStatement EtWcmUsages_statement = App_db.compileStatement(EtWcmUsages_sql);
                                        EtWcmUsages_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmUsages_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmUsages_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmUsages_statement.bindString(3, jsonArray.getJSONObject(j).optString("Use"));
                                            EtWcmUsages_statement.bindString(4, jsonArray.getJSONObject(j).optString("Usex"));
                                            EtWcmUsages_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMUsages*/


                            /*Reading and Inserting Data into Database Table for EtWCMBegru*/
                            try
                            {
                                String EtWCMBegru_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMBegru().getResults());
                                if (EtWCMBegru_response_data != null && !EtWCMBegru_response_data.equals("") && !EtWCMBegru_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMBegru_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmBegru_sql = "Insert into EtWcmBegru (Begru, Begtx) values(?,?);";
                                        SQLiteStatement EtWcmBegru_statement = App_db.compileStatement(EtWcmBegru_sql);
                                        EtWcmBegru_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmBegru_statement.bindString(1, jsonArray.getJSONObject(j).optString("Begru"));
                                            EtWcmBegru_statement.bindString(2, jsonArray.getJSONObject(j).optString("Begtx"));
                                            EtWcmBegru_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMBegru*/


                            /*Reading and Inserting Data into Database Table for EtWCMTgtyp*/
                            try
                            {
                                String EtWCMTgtyp_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMTgtyp().getResults());
                                if (EtWCMTgtyp_response_data != null && !EtWCMTgtyp_response_data.equals("") && !EtWCMTgtyp_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMTgtyp_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmTgtyp_sql = "Insert into EtWcmTgtyp (Iwerk, Tggrp, Tgproc, Tgtyp, Unproc, Untyp, Phblflg, Tgflg, Usable, Tgprocx) values(?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmTgtyp_statement = App_db.compileStatement(EtWcmTgtyp_sql);
                                        EtWcmTgtyp_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmTgtyp_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmTgtyp_statement.bindString(2, jsonArray.getJSONObject(j).optString("Tggrp"));
                                            EtWcmTgtyp_statement.bindString(3, jsonArray.getJSONObject(j).optString("Tgproc"));
                                            EtWcmTgtyp_statement.bindString(4, jsonArray.getJSONObject(j).optString("Tgtyp"));
                                            EtWcmTgtyp_statement.bindString(5, jsonArray.getJSONObject(j).optString("Unproc"));
                                            EtWcmTgtyp_statement.bindString(6, jsonArray.getJSONObject(j).optString("Untyp"));
                                            EtWcmTgtyp_statement.bindString(7, jsonArray.getJSONObject(j).optString("Phblflg"));
                                            EtWcmTgtyp_statement.bindString(8, jsonArray.getJSONObject(j).optString("Tgflg"));
                                            EtWcmTgtyp_statement.bindString(9, jsonArray.getJSONObject(j).optString("Usable"));
                                            EtWcmTgtyp_statement.bindString(10, jsonArray.getJSONObject(j).optString("Tgprocx"));
                                            EtWcmTgtyp_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMTgtyp*/


                            /*Reading and Inserting Data into Database Table for EtWCMTypes*/
                            try
                            {
                                String EtWCMTypes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMTypes().getResults());
                                if (EtWCMTypes_response_data != null && !EtWCMTypes_response_data.equals("") && !EtWCMTypes_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMTypes_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmTypes_sql = "Insert into EtWcmTypes (Iwerk, Objart, Objtyp, Stxt) values(?,?,?,?);";
                                        SQLiteStatement EtWcmTypes_statement = App_db.compileStatement(EtWcmTypes_sql);
                                        EtWcmTypes_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmTypes_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmTypes_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmTypes_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmTypes_statement.bindString(4, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmTypes_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMTypes*/


                            /*Reading and Inserting Data into Database Table for EtWCMWcvp6*/
                            try
                            {
                                String EtWCMWcvp6_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMWcvp6().getResults());
                                if (EtWCMWcvp6_response_data != null && !EtWCMWcvp6_response_data.equals("") && !EtWCMWcvp6_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMWcvp6_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmWcvp6_sql = "Insert into EtWcmWcvp6 (Mandt, Iwerk, Objart, Objtyp, Pmsog, Gntxt, Agent, Stxt) values(?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWcvp6_statement = App_db.compileStatement(EtWcmWcvp6_sql);
                                        EtWcmWcvp6_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmWcvp6_statement.bindString(1, jsonArray.getJSONObject(j).optString("Mandt"));
                                            EtWcmWcvp6_statement.bindString(2, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWcvp6_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWcvp6_statement.bindString(4, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWcvp6_statement.bindString(5, jsonArray.getJSONObject(j).optString("Pmsog"));
                                            EtWcmWcvp6_statement.bindString(6, jsonArray.getJSONObject(j).optString("Gntxt"));
                                            EtWcmWcvp6_statement.bindString(7, jsonArray.getJSONObject(j).optString("Agent"));
                                            EtWcmWcvp6_statement.bindString(8, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWcvp6_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMWcvp6*/


                            /*Reading and Inserting Data into Database Table for EtWCMWork*/
                            try
                            {
                                String EtWCMWork_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMWork().getResults());
                                if (EtWCMWork_response_data != null && !EtWCMWork_response_data.equals("") && !EtWCMWork_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMWork_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmWork_sql = "Insert into EtWcmWork (Iwerk, Objart, Objtyp, Wapiuse, Wkid, ScrtextL, Wkgrp, Propflg, Modflg, Dpflg, Aprv4unmarked, Aprv4marked) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWork_statement = App_db.compileStatement(EtWcmWork_sql);
                                        EtWcmWork_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmWork_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWork_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWork_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWork_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapiuse"));
                                            EtWcmWork_statement.bindString(5, jsonArray.getJSONObject(j).optString("Wkid"));
                                            EtWcmWork_statement.bindString(6, jsonArray.getJSONObject(j).optString("ScrtextL"));
                                            EtWcmWork_statement.bindString(7, jsonArray.getJSONObject(j).optString("Wkgrp"));
                                            EtWcmWork_statement.bindString(8, jsonArray.getJSONObject(j).optString("Propflg"));
                                            EtWcmWork_statement.bindString(9, jsonArray.getJSONObject(j).optString("Modflg"));
                                            EtWcmWork_statement.bindString(10, jsonArray.getJSONObject(j).optString("Dpflg"));
                                            EtWcmWork_statement.bindString(11, jsonArray.getJSONObject(j).optString("Aprv4unmarked"));
                                            EtWcmWork_statement.bindString(12, jsonArray.getJSONObject(j).optString("Aprv4marked"));
                                            EtWcmWork_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMWork*/


                            /*Reading and Inserting Data into Database Table for EtWCMReqm*/
                            try
                            {
                                String EtWCMReqm_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWCMReqm().getResults());
                                if (EtWCMReqm_response_data != null && !EtWCMReqm_response_data.equals("") && !EtWCMReqm_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWCMReqm_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmWork_sql = "Insert into EtWCMReqm (Iwerk, Objart, Objtyp, Wapiuse, Needid, ScrtextL, Needgrp, Propflg, Modflg, Dpflg, Aprv4unmarked, Aprv4marked) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWork_statement = App_db.compileStatement(EtWcmWork_sql);
                                        EtWcmWork_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmWork_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWork_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWork_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWork_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapiuse"));
                                            EtWcmWork_statement.bindString(5, jsonArray.getJSONObject(j).optString("Needid"));
                                            EtWcmWork_statement.bindString(6, jsonArray.getJSONObject(j).optString("ScrtextL"));
                                            EtWcmWork_statement.bindString(7, jsonArray.getJSONObject(j).optString("Needgrp"));
                                            EtWcmWork_statement.bindString(8, jsonArray.getJSONObject(j).optString("Propflg"));
                                            EtWcmWork_statement.bindString(9, jsonArray.getJSONObject(j).optString("Modflg"));
                                            EtWcmWork_statement.bindString(10, jsonArray.getJSONObject(j).optString("Dpflg"));
                                            EtWcmWork_statement.bindString(11, jsonArray.getJSONObject(j).optString("Aprv4unmarked"));
                                            EtWcmWork_statement.bindString(12, jsonArray.getJSONObject(j).optString("Aprv4marked"));
                                            EtWcmWork_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWCMReqm*/


                            /*Reading and Inserting Data into Database Table for EtWcmWcco*/
                            try
                            {
                                String EtWcmWcco_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWcmWcco().getResults());
                                if (EtWcmWcco_response_data != null && !EtWcmWcco_response_data.equals("") && !EtWcmWcco_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EtWcmWcco_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        String EtWcmWcco_sql = "Insert into EtWcmWcco (Iwerk, Objart, Objtyp, Wcmuse, Direction, Asgnflg) values(?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWcco_statement = App_db.compileStatement(EtWcmWcco_sql);
                                        EtWcmWcco_statement.clearBindings();
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            EtWcmWcco_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWcco_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWcco_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWcco_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wcmuse"));
                                            EtWcmWcco_statement.bindString(5, jsonArray.getJSONObject(j).optString("Direction"));
                                            EtWcmWcco_statement.bindString(6, jsonArray.getJSONObject(j).optString("Asgnflg"));
                                            EtWcmWcco_statement.execute();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                            }
                            /*Reading and Inserting Data into Database Table for EtWcmWcco*/

                            App_db.setTransactionSuccessful();
                            App_db.endTransaction();
                            Get_Response = "success";
                        }
                        /*Converting Response JSON Data to JSONArray for Reading*/
                    }
                    /*Converting GSON Response to JSON Data for Parsing*/
                }
            }
            else
            {
                Get_Response = "exception";
            }
        }
        catch (Exception ex)
        {
            Log.v("kiran_ex",ex.getMessage()+"...");
            Get_Response = "exception";
        }
        finally
        {
        }
        return Get_Response;
    }

}
