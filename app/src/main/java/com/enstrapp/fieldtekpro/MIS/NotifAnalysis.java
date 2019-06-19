package com.enstrapp.fieldtekpro.MIS;

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

public class NotifAnalysis {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();

    /*EtNotifArbplTotal Tabel and Fieldnames*/
    private static final String TABLE_EtNotifArbplTotal = "EtNotifArbplTotal";
    private static final String KEY_EtNotifArbplTotal_ID = "ID";
    private static final String KEY_EtNotifArbplTotal_Total = "Total";
    private static final String KEY_EtNotifArbplTotal_Swerk = "Swerk";
    private static final String KEY_EtNotifArbplTotal_Arbpl = "Arbpl";
    private static final String KEY_EtNotifArbplTotal_Name = "Name";
    private static final String KEY_EtNotifArbplTotal_Qmart = "Qmart";
    private static final String KEY_EtNotifArbplTotal_Qmartx = "Qmartx";
    private static final String KEY_EtNotifArbplTotal_Outs = "Outs";
    private static final String KEY_EtNotifArbplTotal_Inpr = "Inpr";
    private static final String KEY_EtNotifArbplTotal_Comp = "Comp";
    private static final String KEY_EtNotifArbplTotal_Dele = "Dele";
    /*EtNotifArbplTotal Tabel and Fieldnames*/

    /*EtNotifPlantTotal Tabel and Fieldnames*/
    private static final String TABLE_EtNotifPlantTotal = "EtNotifPlantTotal";
    private static final String KEY_EtNotifPlantTotal_ID = "ID";
    private static final String KEY_EtNotifPlantTotal_Swerk = "Swerk";
    private static final String KEY_EtNotifPlantTotal_Total = "Total";
    private static final String KEY_EtNotifPlantTotal_Name = "Name";
    private static final String KEY_EtNotifPlantTotal_Qmart = "Qmart";
    private static final String KEY_EtNotifPlantTotal_Qmartx = "Qmartx";
    private static final String KEY_EtNotifPlantTotal_Outs = "Outs";
    private static final String KEY_EtNotifPlantTotal_Inpr = "Inpr";
    private static final String KEY_EtNotifPlantTotal_Comp = "Comp";
    private static final String KEY_EtNotifPlantTotal_Dele = "Dele";
    /*EtNotifPlantTotal Tabel and Fieldnames*/

    /*EtNotifRep Tabel and Fieldnames*/
    private static final String TABLE_EtNotifRep = "EtNotifRep";
    private static final String KEY_EtNotifRep_ID = "ID";
    private static final String KEY_EtNotifRep_Phase = "Phase";
    private static final String KEY_EtNotifRep_Swerk = "Swerk";
    private static final String KEY_EtNotifRep_Arbpl = "Arbpl";
    private static final String KEY_EtNotifRep_Qmart = "Qmart";
    private static final String KEY_EtNotifRep_Qmnum = "Qmnum";
    private static final String KEY_EtNotifRep_Qmtxt = "Qmtxt";
    private static final String KEY_EtNotifRep_Ernam = "Ernam";
    private static final String KEY_EtNotifRep_Equnr = "Equnr";
    private static final String KEY_EtNotifRep_Eqktx = "Eqktx";
    private static final String KEY_EtNotifRep_Priok = "Priok";
    private static final String KEY_EtNotifRep_Strmn = "Strmn";
    private static final String KEY_EtNotifRep_Ltrmn = "Ltrmn";
    private static final String KEY_EtNotifRep_Auswk = "Auswk";
    private static final String KEY_EtNotifRep_Tplnr = "Tplnr";
    private static final String KEY_EtNotifRep_Msaus = "Msaus";
    private static final String KEY_EtNotifRep_Ausvn = "Ausvn";
    private static final String KEY_EtNotifRep_Ausbs = "Ausbs";
    /*EtNotifRep Tabel and Fieldnames*/

    /*EtNotifTypeTotal Tabel and Fieldnames*/
    private static final String TABLE_EtNotifTypeTotal = "EtNotifTypeTotal";
    private static final String KEY_EtNotifTypeTotal_ID = "ID";
    private static final String KEY_EtNotifTypeTotal_Total = "Total";
    private static final String KEY_EtNotifTypeTotal_Swerk = "Swerk";
    private static final String KEY_EtNotifTypeTotal_Arbpl = "Arbpl";
    private static final String KEY_EtNotifTypeTotal_Qmart = "Qmart";
    private static final String KEY_EtNotifTypeTotal_Qmartx = "Qmartx";
    private static final String KEY_EtNotifTypeTotal_Outs = "Outs";
    private static final String KEY_EtNotifTypeTotal_Inpr = "Inpr";
    private static final String KEY_EtNotifTypeTotal_Comp = "Comp";
    private static final String KEY_EtNotifTypeTotal_Dele = "Dele";
    /*EtNotifTypeTotal Tabel and Fieldnames*/

    /*EsNotifTotal Tabel and Fieldnames*/
    private static final String TABLE_EsNotifTotal = "EsNotifTotal";
    private static final String KEY_EsNotifTotal_ID = "ID";
    private static final String KEY_EsNotifTotal_Total = "Total";
    private static final String KEY_EsNotifTotal_Outs = "Outs";
    private static final String KEY_EsNotifTotal_Inpr = "Inpr";
    private static final String KEY_EsNotifTotal_Comp = "Comp";
    private static final String KEY_EsNotifTotal_Dele = "Dele";
    /*EsNotifTotal Tabel and Fieldnames*/

    public static String Get_NotifAnalysis_Data(Activity activity, String transmit_type, String month, String year)
    {
        try
        {

            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Creating EtNotifArbplTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifArbplTotal);
            String CREATE_TABLE_EtNotifArbplTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtNotifArbplTotal + ""
                    + "( "
                    + KEY_EtNotifArbplTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtNotifArbplTotal_Total + " TEXT,"
                    + KEY_EtNotifArbplTotal_Swerk + " TEXT,"
                    + KEY_EtNotifArbplTotal_Arbpl + " TEXT,"
                    + KEY_EtNotifArbplTotal_Name + " TEXT,"
                    + KEY_EtNotifArbplTotal_Qmart + " TEXT,"
                    + KEY_EtNotifArbplTotal_Qmartx + " TEXT,"
                    + KEY_EtNotifArbplTotal_Outs + " TEXT,"
                    + KEY_EtNotifArbplTotal_Inpr + " TEXT,"
                    + KEY_EtNotifArbplTotal_Comp + " TEXT,"
                    + KEY_EtNotifArbplTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtNotifArbplTotal);
                /* Creating EtNotifArbplTotal Table with Fields */

                /* Creating EtNotifPlantTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifPlantTotal);
            String CREATE_TABLE_EtNotifPlantTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtNotifPlantTotal + ""
                    + "( "
                    + KEY_EtNotifPlantTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtNotifPlantTotal_Total + " TEXT,"
                    + KEY_EtNotifPlantTotal_Swerk + " TEXT,"
                    + KEY_EtNotifPlantTotal_Name + " TEXT,"
                    + KEY_EtNotifPlantTotal_Qmart + " TEXT,"
                    + KEY_EtNotifPlantTotal_Qmartx + " TEXT,"
                    + KEY_EtNotifPlantTotal_Outs + " TEXT,"
                    + KEY_EtNotifPlantTotal_Inpr + " TEXT,"
                    + KEY_EtNotifPlantTotal_Comp + " TEXT,"
                    + KEY_EtNotifPlantTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtNotifPlantTotal);
                /* Creating EtNotifPlantTotal Table with Fields */

                /* Creating EtNotifRep  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifRep);
            String CREATE_TABLE_EtNotifRep = "CREATE TABLE IF NOT EXISTS " + TABLE_EtNotifRep + ""
                    + "( "
                    + KEY_EtNotifRep_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtNotifRep_Phase + " TEXT,"
                    + KEY_EtNotifRep_Swerk + " TEXT,"
                    + KEY_EtNotifRep_Arbpl + " TEXT,"
                    + KEY_EtNotifRep_Qmart + " TEXT,"
                    + KEY_EtNotifRep_Qmnum + " TEXT,"
                    + KEY_EtNotifRep_Qmtxt + " TEXT,"
                    + KEY_EtNotifRep_Ernam + " TEXT,"
                    + KEY_EtNotifRep_Equnr + " TEXT,"
                    + KEY_EtNotifRep_Eqktx + " TEXT,"
                    + KEY_EtNotifRep_Priok + " TEXT,"
                    + KEY_EtNotifRep_Strmn + " TEXT,"
                    + KEY_EtNotifRep_Ltrmn + " TEXT,"
                    + KEY_EtNotifRep_Auswk + " TEXT,"
                    + KEY_EtNotifRep_Tplnr + " TEXT,"
                    + KEY_EtNotifRep_Msaus + " TEXT,"
                    + KEY_EtNotifRep_Ausvn + " TEXT,"
                    + KEY_EtNotifRep_Ausbs + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtNotifRep);
                /* Creating EtNotifRep  Table with Fields */

                /* Creating EtNotifTypeTotal  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifTypeTotal);
            String CREATE_TABLE_EtNotifTypeTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtNotifTypeTotal + ""
                    + "( "
                    + KEY_EtNotifTypeTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtNotifTypeTotal_Total + " TEXT,"
                    + KEY_EtNotifTypeTotal_Swerk + " TEXT,"
                    + KEY_EtNotifTypeTotal_Arbpl + " TEXT,"
                    + KEY_EtNotifTypeTotal_Qmart + " TEXT,"
                    + KEY_EtNotifTypeTotal_Qmartx + " TEXT,"
                    + KEY_EtNotifTypeTotal_Outs + " TEXT,"
                    + KEY_EtNotifTypeTotal_Inpr + " TEXT,"
                    + KEY_EtNotifTypeTotal_Comp + " TEXT,"
                    + KEY_EtNotifTypeTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtNotifTypeTotal);
                /* Creating EtNotifTypeTotal  Table with Fields */

                /* Creating EsNotifTotal  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EsNotifTotal);
            String CREATE_TABLE_EsNotifTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EsNotifTotal + ""
                    + "( "
                    + KEY_EsNotifTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EsNotifTotal_Total + " TEXT,"
                    + KEY_EsNotifTotal_Outs + " TEXT,"
                    + KEY_EsNotifTotal_Inpr + " TEXT,"
                    + KEY_EsNotifTotal_Comp + " TEXT,"
                    + KEY_EsNotifTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EsNotifTotal);
                /* Creating EsNotifTotal  Table with Fields */

            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R2", "RD", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
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
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvTransmitType", transmit_type);
            map.put("IvIwerk", "");
            map.put("IvMonth", month);
            map.put("IvYear", year);
            map.put("IvDays", "");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<NotifAnalysis_SER> call = service.getNotifAnalysis(url_link, basic, map);
            Response<NotifAnalysis_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_NotifAnalysis", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {

                    /*Reading Response Data and Parsing to Serializable*/
                    NotifAnalysis_SER rs = response.body();
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

                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/
                        App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EtNotifArbplTotal*/
                        if (jsonObject.has("EtNotifArbplTotal")) {
                            try {
                                String EtNotifArbplTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifArbplTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtNotifArbplTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtNotifArbplTotal_sql = "Insert into EtNotifArbplTotal (Total, Swerk, Arbpl, Name, Qmart, Qmartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtNotifArbplTotal_statement = App_db.compileStatement(EtNotifArbplTotal_sql);
                                    EtNotifArbplTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtNotifArbplTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EtNotifArbplTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Swerk"));
                                        EtNotifArbplTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtNotifArbplTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Name"));
                                        EtNotifArbplTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmart"));
                                        EtNotifArbplTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Qmartx"));
                                        EtNotifArbplTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Outs"));
                                        EtNotifArbplTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("Inpr"));
                                        EtNotifArbplTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("Comp"));
                                        EtNotifArbplTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("Dele"));
                                        EtNotifArbplTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtNotifArbplTotal*/

                        /*Reading and Inserting Data into Database Table for EtNotifPlantTotal*/
                        if (jsonObject.has("EtNotifPlantTotal")) {
                            try {
                                String EtNotifPlantTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifPlantTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtNotifPlantTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtNotifPlantTotal_sql = "Insert into EtNotifPlantTotal (Total, Swerk, Name, Qmart, Qmartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtNotifPlantTotal_statement = App_db.compileStatement(EtNotifPlantTotal_sql);
                                    EtNotifPlantTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtNotifPlantTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EtNotifPlantTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Swerk"));
                                        EtNotifPlantTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Name"));
                                        EtNotifPlantTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Qmart"));
                                        EtNotifPlantTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmartx"));
                                        EtNotifPlantTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Outs"));
                                        EtNotifPlantTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Inpr"));
                                        EtNotifPlantTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("Comp"));
                                        EtNotifPlantTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("Dele"));
                                        EtNotifPlantTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtNotifPlantTotal*/

                        /*Reading and Inserting Data into Database Table for EtEtNotifRep*/
                        if (jsonObject.has("EtNotifRep")) {
                            try {
                                String EtNotifRep_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifRep().getResults());
                                JSONArray jsonArray = new JSONArray(EtNotifRep_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtNotifRep_sql = "Insert into EtNotifRep (Phase, Swerk, Arbpl, Qmart, Qmnum, Qmtxt, Ernam, Equnr, Eqktx, Priok, Strmn, Ltrmn, Auswk, Tplnr, Msaus, Ausvn, Ausbs) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtNotifRep_statement = App_db.compileStatement(EtNotifRep_sql);
                                    EtNotifRep_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtNotifRep_statement.bindString(1, jsonArray.getJSONObject(j).optString("Phase"));
                                        EtNotifRep_statement.bindString(2, jsonArray.getJSONObject(j).optString("Swerk"));
                                        EtNotifRep_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtNotifRep_statement.bindString(4, jsonArray.getJSONObject(j).optString("Qmart"));
                                        EtNotifRep_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmnum"));
                                        EtNotifRep_statement.bindString(6, jsonArray.getJSONObject(j).optString("Qmtxt"));
                                        EtNotifRep_statement.bindString(7, jsonArray.getJSONObject(j).optString("Ernam"));
                                        EtNotifRep_statement.bindString(8, jsonArray.getJSONObject(j).optString("Equnr"));
                                        EtNotifRep_statement.bindString(9, jsonArray.getJSONObject(j).optString("Eqktx"));
                                        EtNotifRep_statement.bindString(10, jsonArray.getJSONObject(j).optString("Priok"));
                                        EtNotifRep_statement.bindString(11, jsonArray.getJSONObject(j).optString("Strmn"));
                                        EtNotifRep_statement.bindString(12, jsonArray.getJSONObject(j).optString("Ltrmn"));
                                        EtNotifRep_statement.bindString(13, jsonArray.getJSONObject(j).optString("Auswk"));
                                        EtNotifRep_statement.bindString(14, jsonArray.getJSONObject(j).optString("Tplnr"));
                                        EtNotifRep_statement.bindString(15, jsonArray.getJSONObject(j).optString("Msaus"));
                                        EtNotifRep_statement.bindString(16, jsonArray.getJSONObject(j).optString("Ausvn"));
                                        EtNotifRep_statement.bindString(17, jsonArray.getJSONObject(j).optString("Ausbs"));
                                        EtNotifRep_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtNotifRep*/

                        /*Reading and Inserting Data into Database Table for EtNotifTypeTotal*/
                        if (jsonObject.has("EtNotifTypeTotal")) {
                            try {
                                String EtNotifTypeTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifTypeTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtNotifTypeTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtNotifTypeTotal_sql = "Insert into EtNotifTypeTotal (Total, Swerk, Arbpl, Qmart, Qmartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtNotifTypeTotal_statement = App_db.compileStatement(EtNotifTypeTotal_sql);
                                    EtNotifTypeTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtNotifTypeTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EtNotifTypeTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Swerk"));
                                        EtNotifTypeTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtNotifTypeTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Qmart"));
                                        EtNotifTypeTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmartx"));
                                        EtNotifTypeTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Outs"));
                                        EtNotifTypeTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Inpr"));
                                        EtNotifTypeTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("Comp"));
                                        EtNotifTypeTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("Dele"));
                                        EtNotifTypeTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtNotifTypeTotal*/

                        /*Reading and Inserting Data into Database Table for EsNotifTotal*/
                        if (jsonObject.has("EsNotifTotal")) {
                            try {
                                String EsNotifTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEsNotifTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EsNotifTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EsNotifTotal_sql = "Insert into EsNotifTotal (Total, Outs, Inpr, Comp, Dele) values(?,?,?,?,?);";
                                    SQLiteStatement EsNotifTotal_statement = App_db.compileStatement(EsNotifTotal_sql);
                                    EsNotifTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EsNotifTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EsNotifTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Outs"));
                                        EsNotifTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Inpr"));
                                        EsNotifTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Comp"));
                                        EsNotifTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Dele"));
                                        EsNotifTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EsNotifTotal*/
                    }
                    /*Reading Data by using FOR Loop*/

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
            }
        } catch (Exception ex) {
            Log.v("kiran_notifanaly_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }
}


