package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;

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

public class VHLP_WCM {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

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
    private static final String KEY_EtUsgrpWccp_Pmsog = "Pmsog";

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
    private static final String KEY_EtWcmTypes_Objart = "Objart";
    private static final String KEY_EtWcmTypes_Objtyp = "Objtyp";
    private static final String KEY_EtWcmTypes_Stxt = "Stxt";
    /* EtWcmTypes Table and Fields Names */

    /* EtWcmWcvp6 Table and Fields Names */
    private static final String TABLE_EtWcmWcvp6 = "EtWcmWcvp6";
    private static final String KEY_EtWcmWcvp6_ID = "id";
    private static final String KEY_EtWcmWcvp6_Mandt = "Mandt";
    private static final String KEY_EtWcmWcvp6_Iwerk = "Iwerk";
    private static final String KEY_EtWcmWcvp6_Objart = "Objart";
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

    public static String Get_VHLP_WCM_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD")) {

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmWcco);
                String CREATE_TABLE_EtWcmWcco = "CREATE TABLE IF NOT EXISTS " + TABLE_EtWcmWcco + ""
                        + "( "
                        + KEY_EtWcmWcco_id + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWcco_Iwerk + " TEXT,"
                        + KEY_EtWcmWcco_Objart + " TEXT,"
                        + KEY_EtWcmWcco_Objtyp + " TEXT,"
                        + KEY_EtWcmWcco_Wcmuse + " TEXT,"
                        + KEY_EtWcmWcco_Direction + " TEXT,"
                        + KEY_EtWcmWcco_Asgnflg + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWcco);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmUsages);
                String CREATE_TABLE_EtWcmUsages = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWcmUsages + ""
                        + "( "
                        + KEY_EtWcmUsages_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmUsages_Iwerk + " TEXT,"
                        + KEY_EtWcmUsages_Objart + " TEXT,"
                        + KEY_EtWcmUsages_Use + " TEXT,"
                        + KEY_EtWcmUsages_Usex + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmUsages);

                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmBegru);
                String CREATE_TABLE_GET_EtWcmBegru = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmBegru + ""
                        + "( "
                        + KEY_GET_EtWcmBegru_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmBegru_Begru + " TEXT,"
                        + KEY_GET_EtWcmBegru_Begtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmBegru);
                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */

                /* Creating EtWcmTgtyp Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmTgtyp);
                String CREATE_TABLE_EtWcmTgtyp = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWcmTgtyp + ""
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
                String CREATE_TABLE_EtWcmTypes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWcmTypes + ""
                        + "( "
                        + KEY_EtWcmTypes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmTypes_Iwerk + " TEXT,"
                        + KEY_EtWcmTypes_Objart + " TEXT,"
                        + KEY_EtWcmTypes_Objtyp + " TEXT,"
                        + KEY_EtWcmTypes_Stxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmTypes);
                /* Creating TABLE_EtWcmTypes Table with Fields */

                /* Creating TABLE_EtWcmWcvp6 Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWcmWcvp6);
                String CREATE_TABLE_EtWcmWcvp6 = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWcmWcvp6 + ""
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
                String CREATE_TABLE_EtWcmWork = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWcmWork + ""
                        + "( "
                        + KEY_EtWcmWork_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWork_Iwerk + " TEXT,"
                        + KEY_EtWcmWork_Objart + " TEXT,"
                        + KEY_EtWcmWork_Objtyp + " TEXT,"
                        + KEY_EtWcmWork_Wapiuse + " TEXT,"
                        + KEY_EtWcmWork_Wkid + " TEXT,"
                        + KEY_EtWcmWork_ScrtextL + " TEXT,"
                        + KEY_EtWcmWork_Wkgrp + " TEXT,"
                        + KEY_EtWcmWork_Propflg + " TEXT,"
                        + KEY_EtWcmWork_Modflg + " TEXT,"
                        + KEY_EtWcmWork_Dpflg + " TEXT,"
                        + KEY_EtWcmWork_Aprv4unmarked + " TEXT,"
                        + KEY_EtWcmWork_Aprv4marked + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWork);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWCMReqm);
                String CREATE_TABLE_EtWCMReqm = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtWCMReqm + ""
                        + "( "
                        + KEY_EtWCMReqm_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWCMReqm_Iwerk + " TEXT,"
                        + KEY_EtWCMReqm_Objart + " TEXT,"
                        + KEY_EtWCMReqm_Objtyp + " TEXT,"
                        + KEY_EtWCMReqm_Wapiuse + " TEXT,"
                        + KEY_EtWCMReqm_Needid + " TEXT,"
                        + KEY_EtWCMReqm_ScrtextL + " TEXT,"
                        + KEY_EtWCMReqm_Needgrp + " TEXT,"
                        + KEY_EtWCMReqm_Propflg + " TEXT,"
                        + KEY_EtWCMReqm_Modflg + " TEXT,"
                        + KEY_EtWCMReqm_Dpflg + " TEXT,"
                        + KEY_EtWCMReqm_Aprv4unmarked + " TEXT,"
                        + KEY_EtWCMReqm_Aprv4marked + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWCMReqm);

                /* Creating EtUsgrpWccp Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtUsgrpWccp);
                String CREATE_TABLE_EtUsgrpWccp = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtUsgrpWccp + ""
                        + "( "
                        + KEY_EtUsgrpWccp_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtUsgrpWccp_Usgrp + " TEXT,"
                        + KEY_EtUsgrpWccp_Pmsog + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUsgrpWccp);
                /* Creating EtUsgrpWccp Table with Fields */

            } else {

            }
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            String webservice_type = app_sharedpreferences
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"Z1", "F4", webservice_type});
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
            Call<VHLP_WCM_SER> call = service.getVHLPWCMDetails(url_link, basic, map);
            Response<VHLP_WCM_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Vhlp_WCM_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {
                        /*EtWCMUsages*/
                        if (response.body().getD().getResults().get(0).getEtWCMUsages() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMUsages().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMUsages().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMUsages_Result eWU : response.body().getD().getResults().get(0).getEtWCMUsages().getResults()) {
                                    values.put("Iwerk", eWU.getIwerk());
                                    values.put("Objart", eWU.getObjart());
                                    values.put("Use", eWU.getUse());
                                    values.put("Usex", eWU.getUsex());
                                    App_db.insert("EtWcmUsages", null, values);
                                }
                            }
                        }

                        /*EtWCMBegru*/
                        if (response.body().getD().getResults().get(0).getEtWCMBegru() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMBegru().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMBegru().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMBegru_Result eWB : response.body().getD().getResults().get(0).getEtWCMBegru().getResults()) {
                                    values.put("Begru", eWB.getBegru());
                                    values.put("Begtx", eWB.getBegtx());
                                    App_db.insert("EtWcmBegru", null, values);
                                }
                            }
                        }

                        /*EtWCMTgtyp*/
                        if (response.body().getD().getResults().get(0).getEtWCMTgtyp() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMTgtyp().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMTgtyp().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMTgtyp_Result eWT : response.body().getD().getResults().get(0).getEtWCMTgtyp().getResults()) {
                                    values.put("Iwerk", eWT.getIwerk());
                                    values.put("Tggrp", eWT.getTggrp());
                                    values.put("Tgproc", eWT.getTgproc());
                                    values.put("Tgtyp", eWT.getTgtyp());
                                    values.put("Unproc", eWT.getUnproc());
                                    values.put("Untyp", eWT.getUntyp());
                                    values.put("Phblflg", eWT.getPhblflg());
                                    values.put("Tgflg", eWT.getTgflg());
                                    values.put("Usable", eWT.getUsable());
                                    values.put("Tgprocx", eWT.getTgprocx());
                                    App_db.insert("EtWcmTgtyp", null, values);
                                }
                            }
                        }

                        /*EtWCMTypes*/
                        if (response.body().getD().getResults().get(0).getEtWCMTypes() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMTypes().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMTypes().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMTypes_Result eWTY : response.body().getD().getResults().get(0).getEtWCMTypes().getResults()) {
                                    values.put("Iwerk", eWTY.getIwerk());
                                    values.put("Objart", eWTY.getObjart());
                                    values.put("Objtyp", eWTY.getObjtyp());
                                    values.put("Stxt", eWTY.getStxt());
                                    App_db.insert("EtWcmTypes", null, values);
                                }
                            }
                        }

                        /*EtWCMWcvp6*/
                        if (response.body().getD().getResults().get(0).getEtWCMWcvp6() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMWcvp6().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMWcvp6().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMWcvp6_Result eC6 : response.body().getD().getResults().get(0).getEtWCMWcvp6().getResults()) {
                                    values.put("Mandt", " ");
                                    values.put("Iwerk", eC6.getIwerk());
                                    values.put("Objart", eC6.getObjart());
                                    values.put("Objtyp", eC6.getObjtyp());
                                    values.put("Pmsog", eC6.getPmsog());
                                    values.put("Gntxt", eC6.getGntxt());
                                    values.put("Agent", eC6.getAgent());
                                    values.put("Stxt", eC6.getStxt());
                                    App_db.insert("EtWcmWcvp6", null, values);
                                }
                            }
                        }

                        /*EtWCMWork*/
                        if (response.body().getD().getResults().get(0).getEtWCMWork() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMWork().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMWork().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMWork_Result eWW : response.body().getD().getResults().get(0).getEtWCMWork().getResults()) {
                                    values.put("Iwerk", eWW.getIwerk());
                                    values.put("Objart", eWW.getObjart());
                                    values.put("Objtyp", eWW.getObjtyp());
                                    values.put("Wapiuse", eWW.getWapiuse());
                                    values.put("Wkid", eWW.getWkid());
                                    values.put("ScrtextL", eWW.getScrtextL());
                                    values.put("Wkgrp", eWW.getWkgrp());
                                    values.put("Propflg", eWW.getPropflg());
                                    values.put("Modflg", eWW.getModflg());
                                    values.put("Dpflg", eWW.getDpflg());
                                    values.put("Aprv4unmarked", eWW.getAprv4unmarked());
                                    values.put("Aprv4marked", eWW.getAprv4marked());
                                    App_db.insert("EtWcmWork", null, values);
                                }
                            }
                        }


                        /*EtWCMReqm*/
                        if (response.body().getD().getResults().get(0).getEtWCMReqm() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMReqm().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMReqm().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMReqm_Result eWR : response.body().getD().getResults().get(0).getEtWCMReqm().getResults()) {
                                    values.put("Iwerk", eWR.getIwerk());
                                    values.put("Objart", eWR.getObjart());
                                    values.put("Objtyp", eWR.getObjtyp());
                                    values.put("Wapiuse", eWR.getWapiuse());
                                    values.put("Needid", eWR.getNeedid());
                                    values.put("ScrtextL", eWR.getScrtextL());
                                    values.put("Needgrp", eWR.getNeedgrp());
                                    values.put("Propflg", eWR.getPropflg());
                                    values.put("Modflg", eWR.getModflg());
                                    values.put("Dpflg", eWR.getDpflg());
                                    values.put("Aprv4unmarked", eWR.getAprv4unmarked());
                                    values.put("Aprv4marked", eWR.getAprv4marked());
                                    App_db.insert("EtWCMReqm", null, values);
                                }
                            }
                        }


                        /*EtWCMUsgrp*/
                        if (response.body().getD().getResults().get(0).getEtWCMUsgrp() != null) {
                            if (response.body().getD().getResults().get(0).getEtWCMUsgrp().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWCMUsgrp().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWCMUsgrp_Result eWR : response.body().getD().getResults().get(0).getEtWCMUsgrp().getResults()) {
                                    values.put("Usgrp", eWR.getUsgrp());
                                    values.put("Pmsog", eWR.getPmsog());
                                    App_db.insert("EtUsgrpWccp", null, values);
                                }
                            }
                        }

                        /*EtECMWcco*/
                        if (response.body().getD().getResults().get(0).getEtWcmWcco() != null) {
                            if (response.body().getD().getResults().get(0).getEtWcmWcco().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtWcmWcco().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (VHLP_WCM_SER.EtWcmWcco_Result eWWO : response.body().getD().getResults().get(0).getEtWcmWcco().getResults()) {
                                    values.put("Iwerk", eWWO.getIwerk());
                                    values.put("Objart", eWWO.getObjart());
                                    values.put("Objtyp", eWWO.getObjtyp());
                                    values.put("Wcmuse", eWWO.getWcmuse());
                                    values.put("Direction", eWWO.getDirection());
                                    values.put("Asgnflg", eWWO.getAsgnflg());
                                    App_db.insert("EtWcmWcco", null, values);
                                }
                            }
                        }

                        App_db.setTransactionSuccessful();
                        Get_Response = "success";
                    } finally {
                        App_db.endTransaction();
                    }
                }
            } else {
                Get_Response = "exception";
            }
        } catch (Exception ex) {
            Log.v("kiran_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
