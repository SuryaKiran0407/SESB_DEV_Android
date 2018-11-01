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

public class FLOC {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

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
                String CREATE_SEARCH_FLOC_EQUIP_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEARCH_FLOC_EQUIP + ""
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
                String CREATE_SEARCH_FLOC_EQUIP_Equip_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtEqui + ""
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
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"C7", "FE", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            } else {
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
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
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<FLOC_SER> call = service.getFLOCDetails(url_link, basic, map);
            Response<FLOC_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_FLOC_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    /*Reading Response Data and Parsing to Serializable*/
                    FLOC_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                    /*Converting GSON Response to JSON Data for Parsing*/

                    /*Converting Response JSON Data to JSONArray for Reading*/
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/

                    /*Reading Data by using FOR Loop*/
                    for (int i = 0; i < response_data_jsonArray.length(); i++) {
                        /*Reading Data by using FOR Loop*/
                        JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                        App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EtFuncEquip*/
                        if (jsonObject.has("EtFuncEquip")) {
                            try {
                                String EtFuncEquip_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtFuncEquip().getResults());
                                JSONArray jsonArray = new JSONArray(EtFuncEquip_response_data);
                                String EtFuncEquip_sql = "Insert into EtFuncEquip (Tplnr, Pltxt, Werks, Arbpl, Kostl, Fltyp, Ingrp, Tplma, Eqart, Rbnr, Inactive, Level, Stplnr, Iwerk) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtFuncEquip_statement = App_db.compileStatement(EtFuncEquip_sql);
                                EtFuncEquip_statement.clearBindings();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    EtFuncEquip_statement.bindString(1, jsonArray.getJSONObject(j).optString("Tplnr"));
                                    EtFuncEquip_statement.bindString(2, jsonArray.getJSONObject(j).optString("Pltxt"));
                                    EtFuncEquip_statement.bindString(3, jsonArray.getJSONObject(j).optString("Werks"));
                                    EtFuncEquip_statement.bindString(4, jsonArray.getJSONObject(j).optString("Arbpl"));
                                    EtFuncEquip_statement.bindString(5, jsonArray.getJSONObject(j).optString("Kostl"));
                                    EtFuncEquip_statement.bindString(6, jsonArray.getJSONObject(j).optString("Fltyp"));
                                    EtFuncEquip_statement.bindString(7, jsonArray.getJSONObject(j).optString("Ingrp"));
                                    EtFuncEquip_statement.bindString(8, jsonArray.getJSONObject(j).optString("Tplma"));
                                    EtFuncEquip_statement.bindString(9, jsonArray.getJSONObject(j).optString("Eqart"));
                                    EtFuncEquip_statement.bindString(10, jsonArray.getJSONObject(j).optString("Rbnr"));
                                    EtFuncEquip_statement.bindString(11, jsonArray.getJSONObject(j).optString("Inactive"));
                                    EtFuncEquip_statement.bindString(12, jsonArray.getJSONObject(j).optString("Level"));
                                    EtFuncEquip_statement.bindString(13, jsonArray.getJSONObject(j).optString("Stplnr"));
                                    EtFuncEquip_statement.bindString(14, jsonArray.getJSONObject(j).optString("Iwerk"));
                                    EtFuncEquip_statement.execute();
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtFuncEquip*/


                        /*Reading and Inserting Data into Database Table for EtEqui*/
                        if (jsonObject.has("EtEqui")) {
                            try {
                                String EtEqui_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtEqui().getResults());
                                JSONArray jsonArray = new JSONArray(EtEqui_response_data);
                                String Equip_sql = "Insert into EtEqui (Tplnr, Pltxt, Equnr, Spras, Eqktx, Rbnr, Eqtyp, Herst, Eqart, Werks, Arbpl, Kostl, Ingrp, Serge, Typbz, Mapar, Inactive, Permit, Hequi, Stlkz, Level, Sequi, Stort, Beber, Anlnr, Anlun, Ivdat, Invzu, Iwerk, Bukrs) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement Equip_statement = App_db.compileStatement(Equip_sql);
                                Equip_statement.clearBindings();
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    Equip_statement.bindString(1, jsonArray.getJSONObject(j).optString("Tplnr"));
                                    Equip_statement.bindString(2, "");
                                    Equip_statement.bindString(3, jsonArray.getJSONObject(j).optString("Equnr"));
                                    Equip_statement.bindString(4, jsonArray.getJSONObject(j).optString("Spras"));
                                    Equip_statement.bindString(5, jsonArray.getJSONObject(j).optString("Eqktx"));
                                    Equip_statement.bindString(6, jsonArray.getJSONObject(j).optString("Rbnr"));
                                    Equip_statement.bindString(7, jsonArray.getJSONObject(j).optString("Eqtyp"));
                                    Equip_statement.bindString(8, jsonArray.getJSONObject(j).optString("Herst"));
                                    Equip_statement.bindString(9, jsonArray.getJSONObject(j).optString("Eqart"));
                                    Equip_statement.bindString(10, jsonArray.getJSONObject(j).optString("Werks"));
                                    Equip_statement.bindString(11, jsonArray.getJSONObject(j).optString("Arbpl"));
                                    Equip_statement.bindString(12, jsonArray.getJSONObject(j).optString("Kostl"));
                                    Equip_statement.bindString(13, jsonArray.getJSONObject(j).optString("Ingrp"));
                                    Equip_statement.bindString(14, jsonArray.getJSONObject(j).optString("Serge"));
                                    Equip_statement.bindString(15, jsonArray.getJSONObject(j).optString("Typbz"));
                                    Equip_statement.bindString(16, jsonArray.getJSONObject(j).optString("Mapar"));
                                    Equip_statement.bindString(17, "");
                                    Equip_statement.bindString(18, "");
                                    Equip_statement.bindString(19, jsonArray.getJSONObject(j).optString("Hequi"));
                                    Equip_statement.bindString(20, jsonArray.getJSONObject(j).optString("Stlkz"));
                                    Equip_statement.bindString(21, jsonArray.getJSONObject(j).optString("Level"));
                                    Equip_statement.bindString(22, jsonArray.getJSONObject(j).optString("Sequi"));
                                    Equip_statement.bindString(23, jsonArray.getJSONObject(j).optString("Stort"));
                                    Equip_statement.bindString(24, jsonArray.getJSONObject(j).optString("Beber"));
                                    Equip_statement.bindString(25, jsonArray.getJSONObject(j).optString("Anlnr"));
                                    Equip_statement.bindString(26, jsonArray.getJSONObject(j).optString("Anlun"));
                                    Equip_statement.bindString(27, jsonArray.getJSONObject(j).optString("Ivdat"));
                                    Equip_statement.bindString(28, jsonArray.getJSONObject(j).optString("Invzu"));
                                    Equip_statement.bindString(29, jsonArray.getJSONObject(j).optString("Iwerk"));
                                    Equip_statement.bindString(30, jsonArray.getJSONObject(j).optString("Bukrs"));
                                    Equip_statement.execute();
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtEqui*/

                    }
                    /*Reading Data by using FOR Loop*/

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
            }
        } catch (Exception ex) {
            Log.v("kiran_floc_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }

}
