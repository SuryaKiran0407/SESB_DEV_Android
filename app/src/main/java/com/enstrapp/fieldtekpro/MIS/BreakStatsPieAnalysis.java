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
import com.enstrapp.fieldtekpro_sesb_dev.R;
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

/**
 * Created by Enstrapp on 21/02/18.
 */

public class BreakStatsPieAnalysis {
    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();


    /*EsBkdnTotal Tabel and Fieldnames*/

    private static final String TABLE_EsBkdnTotal = "EsBkdnTotal";
    private static final String KEY_EsBkdnTotal = "ID";
    private static final String KEY_EsBkdnTotal_Sunit = "Sunit";
    private static final String KEY_EsBkdnTotal_Smsaus = "Smsaus";
    private static final String KEY_EsBkdnTotal_Sauszt= "Sauszt";
    private static final String KEY_EsBkdnTotal_Seqtbr = "Seqtbr ";
    private static final String KEY_EsBkdnTotal_Count = "Count";
    private static final String KEY_EsBkdnTotal_TotM2 = "TotM2";
    private static final String KEY_EsBkdnTotal_TotM3 = "TotM3";
    private static final String KEY_EsBkdnTotal_Bdpmrat= "Bdpmrat";
    private static final String KEY_EsBkdnTotal_WitHrs = "WitHrs ";
    private static final String KEY_EsBkdnTotal_WitoutHrs= "WitoutHrs";
    private static final String KEY_EsBkdnTotal_MttrHours = "MttrHours ";
    private static final String KEY_EsBkdnTotal_MtbrHours = "MtbrHours ";

    /*EsBkdnTotal Tabel and Fieldnames*/

     /*EtBkdnPlantTotal Tabel and Fieldnames*/

    private static final String TABLE_EtBkdnPlantTotal = "EtBkdnPlantTotal";
    private static final String KEY_EtBkdnPlantTotal_ID = "ID";
    private static final String KEY_EtBkdnPlantTotal_Iwerk = "Iwerk";
    private static final String KEY_EtBkdnPlantTotal_Warea = "Warea";
    private static final String KEY_EtBkdnPlantTotal_Arbpl = "Arbpl";
    private static final String KEY_EtBkdnPlantTotal_Spmon = "Spmon";
    private static final String KEY_EtBkdnPlantTotal_Sunit = "Sunit";
    private static final String KEY_EtBkdnPlantTotal_Smsaus = "Smsaus";
    private static final String KEY_EtBkdnPlantTotal_Count = "Count";
    private static final String KEY_EtBkdnPlantTotal_TotM2 = "TotM2";
    private static final String KEY_EtBkdnPlantTotal_TotM3= "TotM3";
    private static final String KEY_EtBkdnPlantTotal_Bdpmrat = "Bdpmrat";
    private static final String KEY_EtBkdnPlantTotal_WitHrs = "WitHrs";
    private static final String KEY_EtBkdnPlantTotal_WitoutHrs= "WitoutHrs";
    private static final String KEY_EtBkdnPlantTotal_MttrHours= "MttrHours";
    private static final String KEY_EtBkdnPlantTotal_MtbrHours = "MtbrHours";
    private static final String KEY_EtBkdnPlantTotal_Name = "Name";

    /*EtBkdnPlantTotal Tabel and Fieldnames*/



    /*EtBkdnWareaTotal Tabel and Fieldnames*/
    private static final String TABLE_EtBkdnWareaTotal = "EtBkdnWareaTotal";
    private static final String KEY_EtBkdnWareaTotal_ID = "ID";
    private static final String KEY_EtBkdnWareaTotal_Iwerk = "Iwerk";
    private static final String KEY_EtBkdnWareaTotal_Warea = "Warea";
    private static final String KEY_EtBkdnWareaTotal_Arbpl = "Arbpl";
    private static final String KEY_EtBkdnWareaTotal_Spmon = "Spmon";
    private static final String KEY_EtBkdnWareaTotal_Sunit = "Sunit";
    private static final String KEY_EtBkdnWareaTotal_Smsaus = "Smsaus";
    private static final String KEY_EtBkdnWareaTotal_Count = "Count";
    private static final String KEY_EtBkdnWareaTotal_TotM2= "TotM2";
    private static final String KEY_EtBkdnWareaTotal_TotM3 = "TotM3";
    private static final String KEY_EtBkdnWareaTotal_Bdpmrat = "Bdpmrat";
    private static final String KEY_EtBkdnWareaTotal_WitHrs = "WitHrs";
    private static final String KEY_EtBkdnWareaTotal_WitoutHrs = "WitoutHrs";
    private static final String KEY_EtBkdnWareaTotal_MttrHours= "MttrHours";
    private static final String KEY_EtBkdnWareaTotal_MtbrHours = "MtbrHours";
    private static final String KEY_EtBkdnWareaTotal_Name = "Name";


    /*EtBkdnWareaTotal Tabel and Fieldnames*/

        /*EtBkdnArbplTotal Tabel and Fieldnames*/
        private static final String TABLE_EtBkdnArbplTotal = "EtBkdnArbplTotal";
    private static final String KEY_EtBkdnArbplTotal_ID = "ID";
    private static final String KEY_EtBkdnArbplTotal_Iwerk = "Iwerk";
    private static final String KEY_EtBkdnArbplTotal_Arbpl = "Arbpl";
    private static final String KEY_EtBkdnArbplTotal_Spmon = "Spmon";
    private static final String KEY_EtBkdnArbplTotal_Sunit= "Sunit";
    private static final String KEY_EtBkdnArbplTotal_Smsaus = "Smsaus";
    private static final String KEY_EtBkdnArbplTotal_Count = "Count";
    private static final String KEY_EtBkdnArbplTotal_TotM2 = "TotM2";
    private static final String KEY_EtBkdnArbplTotal_TotM3 = "TotM3";
    private static final String KEY_EtBkdnArbplTotal_Bdpmrat = "Bdpmrat";
    private static final String KEY_EtBkdnArbplTotal_WitHrs = "WitHrs";
    private static final String KEY_EtBkdnArbplTotal_WitoutHrs = "WitoutHrs";
    private static final String KEY_EtBkdnArbplTotal_MttrHours = "MttrHours";
    private static final String KEY_EtBkdnArbplTotal_MtbrHours = "MtbrHours";
    private static final String KEY_EtBkdnArbplTotal_Name= "Name";

    /*EtBkdnArbplTotal Tabel and Fieldnames*/

        /*EtBkdnPmonthTotal Tabel and Fieldnames*/

    private static final String TABLE_EtBkdnPmonthTotal = "EtBkdnPmonthTotal";
    private static final String KEY_EtBkdnPmonthTotal_ID = "ID";
    private static final String KEY_EtBkdnPmonthTotal_Iwerk = "Iwerk";
    private static final String KEY_EtBkdnPmonthTotal_Warea = "Warea";
    private static final String KEY_EtBkdnPmonthTotal_Spmon = "Spmon";
    private static final String KEY_EtBkdnPmonthTotal_Sunit= "Sunit";
    private static final String KEY_EtBkdnPmonthTotal_Smsaus = "Smsaus";
    private static final String KEY_EtBkdnPmonthTotal_Count = "Count";
    private static final String KEY_EtBkdnPmonthTotal_TotM2 = "TotM2";
    private static final String KEY_EtBkdnPmonthTotal_TotM3 = "TotM3";
    private static final String KEY_EtBkdnPmonthTotal_Bdpmrat = "Bdpmrat";
    private static final String KEY_EtBkdnPmonthTotal_WitHrs = "WitHrs";
    private static final String KEY_EtBkdnPmonthTotal_WitoutHrs = "WitoutHrs";
    private static final String KEY_EtBkdnPmonthTotal_MttrHours = "MttrHours";
    private static final String KEY_EtBkdnPmonthTotal_MtbrHours = "MtbrHours";
    private static final String KEY_EtBkdnPmonthTotal_Name= "Name";

        /*EtBkdnPmonthTotal Tabel and Fieldnames*/

                /*EtBkdnMonthTotal Tabel and Fieldnames*/

    private static final String TABLE_EtBkdnMonthTotal = "EtBkdnMonthTotal";
    private static final String KEY_EtBkdnMonthTotal_ID = "ID";
    private static final String KEY_EtBkdnMonthTotal_Iwerk = "Iwerk";
    private static final String KEY_EtBkdnMonthTotal_Warea = "Warea";
    private static final String KEY_EtBkdnMonthTotal_Arbpl = "Arbpl";
    private static final String KEY_EtBkdnMonthTotal_Spmon = "Spmon";
    private static final String KEY_EtBkdnMonthTotal_Sunit = "Sunit";
    private static final String KEY_EtBkdnMonthTotal_Smsaus = "Smsaus";
    private static final String KEY_EtBkdnMonthTotal_Count = "Count";
    private static final String KEY_EtBkdnMonthTotal_TotM2= "TotM2";
    private static final String KEY_EtBkdnMonthTotal_TotM3 = "TotM3";
    private static final String KEY_EtBkdnMonthTotal_Bdpmrat = "Bdpmrat";
    private static final String KEY_EtBkdnMonthTotal_WitHrs = "WitHrs";
    private static final String KEY_EtBkdnMonthTotal_WitoutHrs = "WitoutHrs";
    private static final String KEY_EtBkdnMonthTotal_MttrHours= "MttrHours";
    private static final String KEY_EtBkdnMonthTotal_MtbrHours = "MtbrHours";
    private static final String KEY_EtBkdnMonthTotal_Name = "Name";


    /*EtBkdnMonthTotal Tabel and Fieldnames*/
    public static String Get_BreakStatsPieAnalysis_Data(Activity activity, String transmit_type) {
        try {

            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Creating EsBkdnTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EsBkdnTotal);
            String CREATE_TABLE_EsBkdnTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EsBkdnTotal + ""
                    + "( "
                    + KEY_EsBkdnTotal+ " INTEGER PRIMARY KEY,"
                    + KEY_EsBkdnTotal_Sunit + " TEXT,"
                    + KEY_EsBkdnTotal_Smsaus + " TEXT,"
                    + KEY_EsBkdnTotal_Sauszt + " TEXT,"
                    + KEY_EsBkdnTotal_Seqtbr+ " TEXT,"
                    + KEY_EsBkdnTotal_Count + " TEXT,"
                    + KEY_EsBkdnTotal_TotM2 + " TEXT,"
                    + KEY_EsBkdnTotal_TotM3 + " TEXT,"
                    + KEY_EsBkdnTotal_Bdpmrat + " TEXT,"
                    + KEY_EsBkdnTotal_WitHrs+ " TEXT,"
                    + KEY_EsBkdnTotal_WitoutHrs + " TEXT,"
                    + KEY_EsBkdnTotal_MttrHours+ " TEXT,"
                    + KEY_EsBkdnTotal_MtbrHours + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EsBkdnTotal);
                /* Creating EsPermitTotal Table with Fields */
                
                
                /* Creating EtBkdnPlantTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtBkdnPlantTotal);
            String CREATE_TABLE_EtBkdnPlantTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtBkdnPlantTotal + ""
                    + "( "
                    + KEY_EtBkdnPlantTotal_ID+ " INTEGER PRIMARY KEY,"
                    + KEY_EtBkdnPlantTotal_Iwerk + " TEXT,"
                    + KEY_EtBkdnPlantTotal_Warea + " TEXT,"
                    + KEY_EtBkdnPlantTotal_Arbpl + " TEXT,"
                    + KEY_EtBkdnPlantTotal_Spmon+ " TEXT,"
                    + KEY_EtBkdnPlantTotal_Sunit + " TEXT,"
                    + KEY_EtBkdnPlantTotal_Smsaus + " TEXT,"
                    + KEY_EtBkdnPlantTotal_Count + " TEXT,"
                    + KEY_EtBkdnPlantTotal_TotM2 + " TEXT,"
                    + KEY_EtBkdnPlantTotal_TotM3+ " TEXT,"
                    + KEY_EtBkdnPlantTotal_Bdpmrat + " TEXT,"
                    + KEY_EtBkdnPlantTotal_WitHrs+ " TEXT,"
                    + KEY_EtBkdnPlantTotal_WitoutHrs + " TEXT,"
                    + KEY_EtBkdnPlantTotal_MttrHours + " TEXT,"
                    + KEY_EtBkdnPlantTotal_MtbrHours+ " TEXT,"
                    + KEY_EtBkdnPlantTotal_Name + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtBkdnPlantTotal);
                /* Creating EtBkdnPlantTotal Table with Fields */

                /* Creating EtBkdnWareaTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtBkdnWareaTotal);
            String CREATE_TABLE_EtBkdnWareaTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtBkdnWareaTotal + ""
                    + "( "
                    + KEY_EtBkdnWareaTotal_ID+ " INTEGER PRIMARY KEY,"
                    + KEY_EtBkdnWareaTotal_Iwerk + " TEXT,"
                    + KEY_EtBkdnWareaTotal_Warea + " TEXT,"
                    + KEY_EtBkdnWareaTotal_Arbpl + " TEXT,"
                    + KEY_EtBkdnWareaTotal_Spmon+ " TEXT,"
                    + KEY_EtBkdnWareaTotal_Sunit + " TEXT,"
                    + KEY_EtBkdnWareaTotal_Smsaus + " TEXT,"
                    + KEY_EtBkdnWareaTotal_Count + " TEXT,"
                    + KEY_EtBkdnWareaTotal_TotM2 + " TEXT,"
                    + KEY_EtBkdnWareaTotal_TotM3+ " TEXT,"
                    + KEY_EtBkdnWareaTotal_Bdpmrat + " TEXT,"
                    + KEY_EtBkdnWareaTotal_WitHrs+ " TEXT,"
                    + KEY_EtBkdnWareaTotal_WitoutHrs + " TEXT,"
                    + KEY_EtBkdnWareaTotal_MttrHours + " TEXT,"
                    + KEY_EtBkdnWareaTotal_MtbrHours+ " TEXT,"
                    + KEY_EtBkdnWareaTotal_Name + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtBkdnWareaTotal);
                /* Creating EtBkdnWareaTotal Table with Fields */

                /* Creating EtBkdnArbplTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtBkdnArbplTotal);
            String CREATE_TABLE_EtBkdnArbplTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtBkdnArbplTotal + ""
                    + "( "
                    + KEY_EtBkdnArbplTotal_ID+ " INTEGER PRIMARY KEY,"
                    + KEY_EtBkdnArbplTotal_Iwerk + " TEXT,"
                    + KEY_EtBkdnArbplTotal_Arbpl + " TEXT,"
                    + KEY_EtBkdnArbplTotal_Spmon+ " TEXT,"
                    + KEY_EtBkdnArbplTotal_Sunit + " TEXT,"
                    + KEY_EtBkdnArbplTotal_Smsaus + " TEXT,"
                    + KEY_EtBkdnArbplTotal_Count + " TEXT,"
                    + KEY_EtBkdnArbplTotal_TotM2 + " TEXT,"
                    + KEY_EtBkdnArbplTotal_TotM3+ " TEXT,"
                    + KEY_EtBkdnArbplTotal_Bdpmrat + " TEXT,"
                    + KEY_EtBkdnArbplTotal_WitHrs+ " TEXT,"
                    + KEY_EtBkdnArbplTotal_WitoutHrs + " TEXT,"
                    + KEY_EtBkdnArbplTotal_MttrHours + " TEXT,"
                    + KEY_EtBkdnArbplTotal_MtbrHours+ " TEXT,"
                    + KEY_EtBkdnArbplTotal_Name + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtBkdnArbplTotal);
                /* Creating EtBkdnArbplTotal Table with Fields */

                /* Creating EtBkdnPmonthTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtBkdnPmonthTotal);
            String CREATE_TABLE_EtBkdnPmonthTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtBkdnPmonthTotal + ""
                    + "( "
                    + KEY_EtBkdnPmonthTotal_ID+ " INTEGER PRIMARY KEY,"
                    + KEY_EtBkdnPmonthTotal_Iwerk + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Warea + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Spmon+ " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Sunit + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Smsaus + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Count + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_TotM2 + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_TotM3+ " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Bdpmrat + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_WitHrs+ " TEXT,"
                    + KEY_EtBkdnPmonthTotal_WitoutHrs + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_MttrHours + " TEXT,"
                    + KEY_EtBkdnPmonthTotal_MtbrHours+ " TEXT,"
                    + KEY_EtBkdnPmonthTotal_Name + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtBkdnPmonthTotal);
                /* Creating EtBkdnPmonthTotal Table with Fields */

                /* Creating EtBkdnMonthTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtBkdnMonthTotal);
            String CREATE_TABLE_EtBkdnMonthTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtBkdnMonthTotal + ""
                    + "( "
                    + KEY_EtBkdnMonthTotal_ID+ " INTEGER PRIMARY KEY,"
                    + KEY_EtBkdnMonthTotal_Iwerk + " TEXT,"
                    + KEY_EtBkdnMonthTotal_Warea + " TEXT,"
                    + KEY_EtBkdnMonthTotal_Arbpl + " TEXT,"
                    + KEY_EtBkdnMonthTotal_Spmon+ " TEXT,"
                    + KEY_EtBkdnMonthTotal_Sunit + " TEXT,"
                    + KEY_EtBkdnMonthTotal_Smsaus + " TEXT,"
                    + KEY_EtBkdnMonthTotal_Count + " TEXT,"
                    + KEY_EtBkdnMonthTotal_TotM2 + " TEXT,"
                    + KEY_EtBkdnMonthTotal_TotM3+ " TEXT,"
                    + KEY_EtBkdnMonthTotal_Bdpmrat + " TEXT,"
                    + KEY_EtBkdnMonthTotal_WitHrs+ " TEXT,"
                    + KEY_EtBkdnMonthTotal_WitoutHrs + " TEXT,"
                    + KEY_EtBkdnMonthTotal_MttrHours + " TEXT,"
                    + KEY_EtBkdnMonthTotal_MtbrHours+ " TEXT,"
                    + KEY_EtBkdnMonthTotal_Name + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtBkdnMonthTotal);
                /* Creating EtBkdnMonthTotal Table with Fields */

/* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R1", "RD", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            else {
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
            map.put("DateSign", "");
            map.put("DateOption", "");
            map.put("DateLow", "");
            map.put("DateHigh", "");
            map.put("IvLyear"," ");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<BreakStatsPie_SER> call = service.getBreakStatsPieAnalysis(url_link, basic, map);
            Response<BreakStatsPie_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_BreakStatsPie", response_status_code + "...");

            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {

                    /*Reading Response Data and Parsing to Serializable*/
                   BreakStatsPie_SER rs = response.body();
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

                        /*Reading and Inserting Data into Database Table for EsPermitTotal UUID*/
                        App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EsPermitTotal*/
                        if (jsonObject.has("EsBkdnTotal")) {
                            try {
                                String EsBkdnTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEsBkdnTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EsBkdnTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EsBkdnTotal_sql = "Insert into EsBkdnTotal (Sunit, Smsaus, Sauszt, Seqtbr,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EsBkdnTotal_statement = App_db.compileStatement(EsBkdnTotal_sql);
                                    EsBkdnTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EsBkdnTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EsBkdnTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EsBkdnTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Sauszt"));
                                        EsBkdnTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Seqtbr"));
                                        EsBkdnTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Count"));
                                        EsBkdnTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EsBkdnTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EsBkdnTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EsBkdnTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EsBkdnTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EsBkdnTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EsBkdnTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EsBkdnTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EsPermitTotal*/

                            /*Reading and Inserting Data into Database Table for EtBkdnPlantTotal*/
                        if (jsonObject.has("EtBkdnPlantTotal")) {
                            try {
                                String EtBkdnPlantTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnPlantTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtBkdnPlantTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtBkdnPlantTotal_sql = "Insert into EtBkdnPlantTotal (Iwerk, Warea, Arbpl, Spmon,Sunit,Smsaus,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours,Name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtBkdnPlantTotal_statement = App_db.compileStatement(EtBkdnPlantTotal_sql);
                                    EtBkdnPlantTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtBkdnPlantTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtBkdnPlantTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Warea"));
                                        EtBkdnPlantTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtBkdnPlantTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Spmon"));
                                        EtBkdnPlantTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EtBkdnPlantTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EtBkdnPlantTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Count"));
                                        EtBkdnPlantTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EtBkdnPlantTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EtBkdnPlantTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EtBkdnPlantTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EtBkdnPlantTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EtBkdnPlantTotal_statement.bindString(13, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EtBkdnPlantTotal_statement.bindString(14, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EtBkdnPlantTotal_statement.bindString(15, jsonArray.getJSONObject(j).optString("Name"));
                                        EtBkdnPlantTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EtBkdnPlantTotal*/


                            /*Reading and Inserting Data into Database Table for EtBkdnWareaTotal*/
                        if (jsonObject.has("EtBkdnWareaTotal")) {
                            try {
                                String EtBkdnWareaTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnWareaTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtBkdnWareaTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtBkdnWareaTotal_sql = "Insert into EtBkdnWareaTotal (Iwerk, Warea, Arbpl, Spmon,Sunit,Smsaus,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours,Name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtBkdnWareaTotal_statement = App_db.compileStatement(EtBkdnWareaTotal_sql);
                                    EtBkdnWareaTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtBkdnWareaTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtBkdnWareaTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Warea"));
                                        EtBkdnWareaTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtBkdnWareaTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Spmon"));
                                        EtBkdnWareaTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EtBkdnWareaTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EtBkdnWareaTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Count"));
                                        EtBkdnWareaTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EtBkdnWareaTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EtBkdnWareaTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EtBkdnWareaTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EtBkdnWareaTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EtBkdnWareaTotal_statement.bindString(13, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EtBkdnWareaTotal_statement.bindString(14, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EtBkdnWareaTotal_statement.bindString(15, jsonArray.getJSONObject(j).optString("Name"));
                                        EtBkdnWareaTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EtBkdnWareaTotal*/


                            /*Reading and Inserting Data into Database Table for EtBkdnArbplTotal*/
                        if (jsonObject.has("EtBkdnArbplTotal")) {
                            try {
                                String EtBkdnArbplTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnArbplTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtBkdnArbplTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtBkdnArbplTotal_sql = "Insert into EtBkdnArbplTotal (Iwerk, Arbpl, Spmon,Sunit,Smsaus,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours,Name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtBkdnArbplTotal_statement = App_db.compileStatement(EtBkdnArbplTotal_sql);
                                    EtBkdnArbplTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtBkdnArbplTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));

                                        EtBkdnArbplTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtBkdnArbplTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Spmon"));
                                        EtBkdnArbplTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EtBkdnArbplTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EtBkdnArbplTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Count"));
                                        EtBkdnArbplTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EtBkdnArbplTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EtBkdnArbplTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EtBkdnArbplTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EtBkdnArbplTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EtBkdnArbplTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EtBkdnArbplTotal_statement.bindString(13, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EtBkdnArbplTotal_statement.bindString(14, jsonArray.getJSONObject(j).optString("Name"));
                                        EtBkdnArbplTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EtBkdnArbplTotal*/


                            /*Reading and Inserting Data into Database Table for EtBkdnPmonthTotal*/
                        if (jsonObject.has("EtBkdnPmonthTotal")) {
                            try {
                                String EtBkdnPmonthTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnPmonthTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtBkdnPmonthTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtBkdnPmonthTotal_sql = "Insert into EtBkdnPmonthTotal (Iwerk, Warea, Spmon,Sunit,Smsaus,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours,Name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtBkdnPmonthTotal_statement = App_db.compileStatement(EtBkdnPmonthTotal_sql);
                                    EtBkdnPmonthTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtBkdnPmonthTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtBkdnPmonthTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Warea"));
                                        EtBkdnPmonthTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Spmon"));
                                        EtBkdnPmonthTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EtBkdnPmonthTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EtBkdnPmonthTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Count"));
                                        EtBkdnPmonthTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EtBkdnPmonthTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EtBkdnPmonthTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EtBkdnPmonthTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EtBkdnPmonthTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EtBkdnPmonthTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EtBkdnPmonthTotal_statement.bindString(13, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EtBkdnPmonthTotal_statement.bindString(14, jsonArray.getJSONObject(j).optString("Name"));
                                        EtBkdnPmonthTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EtBkdnPmonthTotal*/


                            /*Reading and Inserting Data into Database Table for EtBkdnMonthTotal*/
                        if (jsonObject.has("EtBkdnMonthTotal")) {
                            try {
                                String EtBkdnMonthTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnMonthTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EtBkdnMonthTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtBkdnMonthTotal_sql = "Insert into EtBkdnMonthTotal (Iwerk, Warea, Arbpl, Spmon,Sunit,Smsaus,Count,TotM2,TotM3,Bdpmrat,WitHrs,WitoutHrs,MttrHours,MtbrHours,Name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtBkdnMonthTotal_statement = App_db.compileStatement(EtBkdnMonthTotal_sql);
                                    EtBkdnMonthTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtBkdnMonthTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtBkdnMonthTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Warea"));
                                        EtBkdnMonthTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtBkdnMonthTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Spmon"));
                                        EtBkdnMonthTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Sunit"));
                                        EtBkdnMonthTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("Smsaus"));
                                        EtBkdnMonthTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("Count"));
                                        EtBkdnMonthTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("TotM2"));
                                        EtBkdnMonthTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("TotM3"));
                                        EtBkdnMonthTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                        EtBkdnMonthTotal_statement.bindString(11, jsonArray.getJSONObject(j).optString("WitHrs"));
                                        EtBkdnMonthTotal_statement.bindString(12, jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                        EtBkdnMonthTotal_statement.bindString(13, jsonArray.getJSONObject(j).optString("MttrHours"));
                                        EtBkdnMonthTotal_statement.bindString(14, jsonArray.getJSONObject(j).optString("MtbrHours"));
                                        EtBkdnMonthTotal_statement.bindString(15, jsonArray.getJSONObject(j).optString("Name"));
                                        EtBkdnMonthTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                            /*Reading and Inserting Data into Database Table for EtBkdnMonthTotal*/

                    }
                    /*Reading Data by using FOR Loop*/

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
            }
        } catch (Exception ex) {
            Log.v("kiran_BreakStats_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }








        }
