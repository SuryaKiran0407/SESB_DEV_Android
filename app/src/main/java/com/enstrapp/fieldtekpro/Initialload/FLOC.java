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

public class FLOC {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    /* EtFuncEquip and Fields Names */
    private static final String TABLE_SEARCH_FLOC_EQUIP = "EtFuncEquip";
    private static final String KEY_SEARCH_FLOC_EQUIP_ID = "id";
    private static final String KEY_SEARCH_FLOC_EQUIP_Tplnr = "Tplnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Pltxt = "Pltxt";
    private static final String KEY_SEARCH_FLOC_EQUIP_Werks = "Werks";
    private static final String KEY_SEARCH_FLOC_EQUIP_Arbpl = "Arbpl";
    private static final String KEY_SEARCH_FLOC_EQUIP_Kostl = "Kostl";
    private static final String KEY_SEARCH_FLOC_EQUIP_Fltyp = "Fltyp";
    private static final String KEY_SEARCH_FLOC_EQUIP_Ingrp = "Ingrp";
    private static final String KEY_SEARCH_FLOC_EQUIP_Tplma = "Tplma";
    private static final String KEY_SEARCH_FLOC_EQUIP_Eqart = "Eqart";
    private static final String KEY_SEARCH_FLOC_EQUIP_Rbnr = "Rbnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Inactive = "Inactive";
    private static final String KEY_SEARCH_FLOC_EQUIP_Level = "Level";
    private static final String KEY_SEARCH_FLOC_EQUIP_Stplnr = "Stplnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Iwerk = "Iwerk";
    /* EtFuncEquip and Fields Names */

    /*EtEqui and Fields Names */
    private static final String TABLE_EtEqui = "EtEqui";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_ID = "id";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Tplnr = "Tplnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Pltxt = "Pltxt";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Equnr = "Equnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Spras = "Spras";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Eqktx = "Eqktx";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Rbnr = "Rbnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Eqtyp = "Eqtyp";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Herst = "Herst";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Eqart = "Eqart";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Werks = "Werks";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Arbpl = "Arbpl";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Kostl = "Kostl";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Ingrp = "Ingrp";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Serge = "Serge";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Typbz = "Typbz";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Mapar = "Mapar";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Inactive = "Inactive";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Permit = "Permit";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Hequi = "Hequi";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Stlkz = "Stlkz";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Level = "Level";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Sequi = "Sequi";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Stort = "Stort";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Beber = "Beber";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Anlnr = "Anlnr";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Anlun = "Anlun";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Ivdat = "Ivdat";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Invzu = "Invzu";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Iwerk = "Iwerk";
    private static final String KEY_SEARCH_FLOC_EQUIP_Equip_Bukrs = "Bukrs";
    /* EtEqui and Fields Names */

    public static String Get_FLOC_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equals("LOAD")) {
                /* Creating GET_SEARCH_FLOC_EQUIP Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_FLOC_EQUIP);
                String CREATE_SEARCH_FLOC_EQUIP_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_SEARCH_FLOC_EQUIP + ""
                        + "( "
                        + KEY_SEARCH_FLOC_EQUIP_ID + " INTEGER PRIMARY KEY,"
                        + KEY_SEARCH_FLOC_EQUIP_Tplnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Pltxt + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Werks + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Arbpl + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Kostl + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Fltyp + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Ingrp + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Tplma + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Eqart + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Rbnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Inactive + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Level + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Stplnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Iwerk + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_SEARCH_FLOC_EQUIP_TABLE);
                /* Creating GET_SEARCH_FLOC_EQUIP Table with Fields */

                /* Creating EtEqui Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtEqui);
                String CREATE_SEARCH_FLOC_EQUIP_Equip_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtEqui + ""
                        + "( "
                        + KEY_SEARCH_FLOC_EQUIP_Equip_ID + " INTEGER PRIMARY KEY,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Tplnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Pltxt + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Equnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Spras + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Eqktx + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Rbnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Eqtyp + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Herst + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Eqart + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Werks + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Arbpl + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Kostl + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Ingrp + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Serge + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Typbz + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Mapar + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Inactive + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Permit + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Hequi + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Stlkz + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Level + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Sequi + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Stort + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Beber + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Anlnr + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Anlun + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Ivdat + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Invzu + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Iwerk + " TEXT,"
                        + KEY_SEARCH_FLOC_EQUIP_Equip_Bukrs + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_SEARCH_FLOC_EQUIP_Equip_TABLE);
                /* Creating EtEqui Table with Fields */
            } else {
                App_db.execSQL("delete from EtFuncEquip");
                App_db.execSQL("delete from EtEqui");
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
                    new String[]{"C7", "FE", webservice_type});
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
            map.put("IvTplnr", "");
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
            Call<FLOC_SER> call = service.getFLOCDetails(url_link, basic, map);
            Response<FLOC_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_FLOC_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {
                        /*EtFuncEquip*/
                        if (response.body().getD().getResults().get(0).getEtFuncEquip() != null) {
                            if (response.body().getD().getResults().get(0).getEtFuncEquip().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtFuncEquip().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (FLOC_SER.EtFuncEquip_Result eFE : response.body().getD().getResults().get(0).getEtFuncEquip().getResults()) {
                                    values.put("Tplnr", eFE.getTplnr());
                                    values.put("Pltxt", eFE.getPltxt());
                                    values.put("Werks", eFE.getWerks());
                                    values.put("Arbpl", eFE.getArbpl());
                                    values.put("Kostl", eFE.getKostl());
                                    values.put("Fltyp", eFE.getFltyp());
                                    values.put("Ingrp", eFE.getIngrp());
                                    values.put("Tplma", eFE.getTplma());
                                    values.put("Eqart", eFE.getEqart());
                                    values.put("Rbnr", eFE.getRbnr());
                                    values.put("Inactive", eFE.getInactive());
                                    values.put("Level", eFE.getLevel());
                                    values.put("Stplnr", eFE.getStplnr());
                                    values.put("Iwerk", eFE.getIwerk());
                                    App_db.insert("EtFuncEquip", null, values);
                                }
                            }
                        }
                        /*EtEqui*/
                        if (response.body().getD().getResults().get(0).getEtEqui() != null) {
                            if (response.body().getD().getResults().get(0).getEtEqui().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtEqui().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (FLOC_SER.EtEqui_Result eEE : response.body().getD().getResults().get(0).getEtEqui().getResults()) {
                                    values.put("Tplnr", eEE.getTplnr());
                                    values.put("Pltxt", " ");
                                    values.put("Equnr", eEE.getEqunr());
                                    values.put("Spras", " ");
                                    values.put("Eqktx", eEE.getEqktx());
                                    values.put("Rbnr", eEE.getRbnr());
                                    values.put("Eqtyp", eEE.getEqtyp());
                                    values.put("Herst", eEE.getHerst());
                                    values.put("Eqart", eEE.getEqart());
                                    values.put("Werks", eEE.getWerks());
                                    values.put("Arbpl", eEE.getArbpl());
                                    values.put("Kostl", eEE.getKostl());
                                    values.put("Ingrp", eEE.getIngrp());
                                    values.put("Serge", eEE.getSerge());
                                    values.put("Typbz", eEE.getTypbz());
                                    values.put("Mapar", eEE.getMapar());
                                    values.put("Inactive", " ");
                                    values.put("Permit", " ");
                                    values.put("Hequi", eEE.getHequi());
                                    values.put("Stlkz", eEE.getStlkz());
                                    values.put("Level", eEE.getLevel());
                                    values.put("Sequi", eEE.getSequi());
                                    values.put("Stort", eEE.getStort());
                                    values.put("Beber", eEE.getBeber());
                                    values.put("Anlnr", eEE.getAnlnr());
                                    values.put("Anlun", eEE.getAnlun());
                                    values.put("Ivdat", eEE.getIvdat());
                                    values.put("Invzu", eEE.getInvzu());
                                    values.put("Iwerk", eEE.getIwerk());
                                    values.put("Bukrs", eEE.getBukrs());
                                    App_db.insert("EtEqui", null, values);
                                }
                            }
                        }
                        App_db.setTransactionSuccessful();
                        Get_Response = "success";
                    } finally {
                        App_db.endTransaction();
                    }
                }
            }
        } catch (Exception ex) {
            Log.v("kiran_floc_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
