package com.enstrapp.fieldtekpro.InitialLoad_Rest;

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

import com.enstrapp.fieldtekpro.Initialload.FLOC_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;

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

public class REST_FLOC {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();
    private static long startTime = 0;

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

    public static String Get_FLOC_Data(Activity activity, String transmit_type)
    {
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


            Rest_Model_Login_Device modelLoginDeviceRest = new Rest_Model_Login_Device();
            modelLoginDeviceRest.setMUSER(username.toUpperCase().toString());
            modelLoginDeviceRest.setDEVICEID(device_id);
            modelLoginDeviceRest.setDEVICESNO(device_serial_number);
            modelLoginDeviceRest.setUDID(device_uuid);

            Rest_Model_Login modelLoginRest = new Rest_Model_Login();
            modelLoginRest.setIv_transmit_type("LOAD");
            modelLoginRest.setIv_user(username);
            modelLoginRest.setIs_device(modelLoginDeviceRest);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180000, TimeUnit.MILLISECONDS)
                    .writeTimeout(180000, TimeUnit.MILLISECONDS)
                    .readTimeout(180000, TimeUnit.MILLISECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " +
                    Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_FLOC_SER> call = service.postFLOCDetails(url_link, basic, modelLoginRest);
            Response<REST_FLOC_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_FLOC_code", response_status_code + "...");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    startTime = System.currentTimeMillis();
                    App_db.execSQL("PRAGMA synchronous=OFF");
                    App_db.setLockingEnabled(false);
                    App_db.beginTransaction();

                    /*Reading and Inserting Data into Database Table for EtFuncEquip*/
                    try
                    {
                        List<REST_FLOC_SER.ET_FUNC_EQUIP> etFuncEquip_results = response.body().getET_FUNC_EQUIP();
                        if (etFuncEquip_results != null && etFuncEquip_results.size() > 0)
                        {
                            String EtFuncEquip_sql = "Insert into EtFuncEquip (Tplnr, Pltxt, Werks, Arbpl, Kostl, Fltyp, Ingrp, Tplma, Eqart, Rbnr, Inactive, Level, Stplnr, Iwerk) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtFuncEquip_statement = App_db.compileStatement(EtFuncEquip_sql);
                            EtFuncEquip_statement.clearBindings();
                            for (REST_FLOC_SER.ET_FUNC_EQUIP etUsersResult : etFuncEquip_results)
                            {
                                EtFuncEquip_statement.bindString(1, c_e.check_empty(etUsersResult.getTplnr()));
                                EtFuncEquip_statement.bindString(2, c_e.check_empty(etUsersResult.getPltxt()));
                                EtFuncEquip_statement.bindString(3, c_e.check_empty(etUsersResult.getWerks()));
                                EtFuncEquip_statement.bindString(4, c_e.check_empty(etUsersResult.getArbpl()));
                                EtFuncEquip_statement.bindString(5, c_e.check_empty(etUsersResult.getKostl()));
                                EtFuncEquip_statement.bindString(6, c_e.check_empty(etUsersResult.getFltyp()));
                                EtFuncEquip_statement.bindString(7, c_e.check_empty(etUsersResult.getIngrp()));
                                EtFuncEquip_statement.bindString(8, c_e.check_empty(etUsersResult.getTplma()));
                                EtFuncEquip_statement.bindString(9, c_e.check_empty(etUsersResult.getEqart()));
                                EtFuncEquip_statement.bindString(10, c_e.check_empty(etUsersResult.getRbnr()));
                                EtFuncEquip_statement.bindString(11, c_e.check_empty(etUsersResult.getInactive()));
                                EtFuncEquip_statement.bindString(12, c_e.check_empty(etUsersResult.getLevel()));
                                EtFuncEquip_statement.bindString(13, c_e.check_empty(etUsersResult.getStplnr()));
                                EtFuncEquip_statement.bindString(14, c_e.check_empty(etUsersResult.getIwerk()));
                                EtFuncEquip_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtFuncEquip*/



                    /*Reading and Inserting Data into Database Table for EtEqui*/
                    try
                    {
                        List<REST_FLOC_SER.ET_EQUI> EtEqui_results = response.body().getET_EQUI();
                        if (EtEqui_results != null && EtEqui_results.size() > 0)
                        {
                            String Equip_sql = "Insert into EtEqui (Tplnr, Pltxt, Equnr, Spras, Eqktx, Rbnr, Eqtyp, Herst, Eqart, Werks, Arbpl, Kostl, Ingrp, Serge, Typbz, Mapar, Inactive, Permit, Hequi, Stlkz, Level, Sequi, Stort, Beber, Anlnr, Anlun, Ivdat, Invzu, Iwerk, Bukrs) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement Equip_statement = App_db.compileStatement(Equip_sql);
                            Equip_statement.clearBindings();
                            for (REST_FLOC_SER.ET_EQUI etUsersResult : EtEqui_results)
                            {
                                Equip_statement.bindString(1, c_e.check_empty(etUsersResult.getTplnr()));
                                Equip_statement.bindString(2, "");
                                Equip_statement.bindString(3, c_e.check_empty(etUsersResult.getEqunr()));
                                Equip_statement.bindString(4, c_e.check_empty(etUsersResult.getSpras()));
                                Equip_statement.bindString(5, c_e.check_empty(etUsersResult.getEqktx()));
                                Equip_statement.bindString(6, c_e.check_empty(etUsersResult.getRbnr()));
                                Equip_statement.bindString(7, c_e.check_empty(etUsersResult.getEqtyp()));
                                Equip_statement.bindString(8, c_e.check_empty(etUsersResult.getHerst()));
                                Equip_statement.bindString(9, c_e.check_empty(etUsersResult.getEqart()));
                                Equip_statement.bindString(10, c_e.check_empty(etUsersResult.getWerks()));
                                Equip_statement.bindString(11, c_e.check_empty(etUsersResult.getArbpl()));
                                Equip_statement.bindString(12, c_e.check_empty(etUsersResult.getKostl()));
                                Equip_statement.bindString(13, c_e.check_empty(etUsersResult.getIngrp()));
                                Equip_statement.bindString(14, c_e.check_empty(etUsersResult.getSerge()));
                                Equip_statement.bindString(15, c_e.check_empty(etUsersResult.getTypbz()));
                                Equip_statement.bindString(16, c_e.check_empty(etUsersResult.getMapar()));
                                Equip_statement.bindString(17, "");
                                Equip_statement.bindString(18, "");
                                Equip_statement.bindString(19, c_e.check_empty(etUsersResult.getHequi()));
                                Equip_statement.bindString(20, c_e.check_empty(etUsersResult.getStlkz()));
                                Equip_statement.bindString(21, c_e.check_empty(etUsersResult.getLevel()));
                                Equip_statement.bindString(22, c_e.check_empty(etUsersResult.getSequi()));
                                Equip_statement.bindString(23, c_e.check_empty(etUsersResult.getStort()));
                                Equip_statement.bindString(24, c_e.check_empty(etUsersResult.getBeber()));
                                Equip_statement.bindString(25, c_e.check_empty(etUsersResult.getAnlnr()));
                                Equip_statement.bindString(26, c_e.check_empty(etUsersResult.getAnlun()));
                                Equip_statement.bindString(27, c_e.check_empty(etUsersResult.getIvdat()));
                                Equip_statement.bindString(28, c_e.check_empty(etUsersResult.getInvzu()));
                                Equip_statement.bindString(29, c_e.check_empty(etUsersResult.getIwerk()));
                                Equip_statement.bindString(30, c_e.check_empty(etUsersResult.getBukrs()));
                                Equip_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtEqui*/


                    App_db.setTransactionSuccessful();
                    Get_Response = "success";

                }
                else
                {
                    Get_Response = "exception";
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
            App_db.endTransaction();
            App_db.setLockingEnabled(true);
            App_db.execSQL("PRAGMA synchronous=NORMAL");
            final long endtime = System.currentTimeMillis();
            Log.v("kiran_FLOC_insert", String.valueOf(endtime - startTime)+" Milliseconds");
        }
        return Get_Response;
    }
}
