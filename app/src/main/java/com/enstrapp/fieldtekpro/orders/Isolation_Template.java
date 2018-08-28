package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

public class Isolation_Template {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty checkempty = new Check_Empty();
    private static StringBuffer message = new StringBuffer();
    private static boolean success = false;
    private static ArrayList<OrdrPermitPrcbl> opp_al = new ArrayList<>();
    private static ArrayList<OrdrTagUnTagTextPrcbl> otp_al = new ArrayList<>();
    private static ArrayList<OrdrTagUnTagTextPrcbl> outp_al = new ArrayList<>();
    private static ArrayList<OrdrWDItemPrcbl> wdi_al = new ArrayList<>();

    /* EtWcmWdData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdData = "Tmp_EtWcmWdData";
    private static final String KEY_GET_EtWcmWdData_ID = "id";
    private static final String KEY_GET_EtWcmWdData_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWdData_Objart = "Objart";
    private static final String KEY_GET_EtWcmWdData_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdData_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWdData_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWdData_Usage = "Usage";
    private static final String KEY_GET_EtWcmWdData_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWdData_Train = "Train";
    private static final String KEY_GET_EtWcmWdData_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWdData_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWdData_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWdData_Etape = "Etape";
    private static final String KEY_GET_EtWcmWdData_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWdData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdData_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWdData_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWdData_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWdData_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWdData_Priok = "Priok";
    private static final String KEY_GET_EtWcmWdData_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWdData_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWdData_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWdData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdData_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdData_Crea = "Crea";
    private static final String KEY_GET_EtWcmWdData_Prep = "Prep";
    private static final String KEY_GET_EtWcmWdData_Comp = "Comp";
    private static final String KEY_GET_EtWcmWdData_Appr = "Appr";
    private static final String KEY_GET_EtWcmWdData_Action = "Action";
    private static final String KEY_GET_EtWcmWdData_Begru = "Begru";
    private static final String KEY_GET_EtWcmWdData_Begtx = "Begtx";
	/* EtWcmWdData Table and Fields Names */

    /* EtWcmWdDataTagtext Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataTagtext = "Tmp_EtWcmWdDataTagtext";
    private static final String KEY_EtWcmWdDataTagtext_ID = "id";
    private static final String KEY_EtWcmWdDataTagtext_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataTagtext_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataTagtext_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataTagtext_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataTagtext_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataTagtext_Action = "Action";
	/* EtWcmWdDataTagtext Table and Fields Names */

    /* EtWcmWdDataUntagtext Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataUntagtext = "Tmp_EtWcmWdDataUntagtext";
    private static final String KEY_EtWcmWdDataUntagtext_ID = "id";
    private static final String KEY_EtWcmWdDataUntagtext_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataUntagtext_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataUntagtext_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataUntagtext_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataUntagtext_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataUntagtext_Action = "Action";
	/* EtWcmWdDataUntagtext Table and Fields Names */

    /* EtWcmWdItemData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdItemData = "Tmp_EtWcmWdItemData";
    private static final String KEY_GET_EtWcmWdItemData_ID = "id";
    private static final String KEY_GET_EtWcmWdItemData_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdItemData_Wcitm = "Wcitm";
    private static final String KEY_GET_EtWcmWdItemData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdItemData_Itmtyp = "Itmtyp";
    private static final String KEY_GET_EtWcmWdItemData_Seq = "Seq";
    private static final String KEY_GET_EtWcmWdItemData_Pred = "Pred";
    private static final String KEY_GET_EtWcmWdItemData_Succ = "Succ";
    private static final String KEY_GET_EtWcmWdItemData_Ccobj = "Ccobj";
    private static final String KEY_GET_EtWcmWdItemData_Cctyp = "Cctyp";
    private static final String KEY_GET_EtWcmWdItemData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdItemData_Tggrp = "Tggrp";
    private static final String KEY_GET_EtWcmWdItemData_Tgstep = "Tgstep";
    private static final String KEY_GET_EtWcmWdItemData_Tgproc = "Tgproc";
    private static final String KEY_GET_EtWcmWdItemData_Tgtyp = "Tgtyp";
    private static final String KEY_GET_EtWcmWdItemData_Tgseq = "Tgseq";
    private static final String KEY_GET_EtWcmWdItemData_Tgtxt = "Tgtxt";
    private static final String KEY_GET_EtWcmWdItemData_Unstep = "Unstep";
    private static final String KEY_GET_EtWcmWdItemData_Unproc = "Unproc";
    private static final String KEY_GET_EtWcmWdItemData_Untyp = "Untyp";
    private static final String KEY_GET_EtWcmWdItemData_Unseq = "Unseq";
    private static final String KEY_GET_EtWcmWdItemData_Untxt = "Untxt";
    private static final String KEY_GET_EtWcmWdItemData_Phblflg = "Phblflg";
    private static final String KEY_GET_EtWcmWdItemData_Phbltyp = "Phbltyp";
    private static final String KEY_GET_EtWcmWdItemData_Phblnr = "Phblnr";
    private static final String KEY_GET_EtWcmWdItemData_Tgflg = "Tgflg";
    private static final String KEY_GET_EtWcmWdItemData_Tgform = "Tgform";
    private static final String KEY_GET_EtWcmWdItemData_Tgnr = "Tgnr";
    private static final String KEY_GET_EtWcmWdItemData_Unform = "Unform";
    private static final String KEY_GET_EtWcmWdItemData_Unnr = "Unnr";
    private static final String KEY_GET_EtWcmWdItemData_Control = "Control";
    private static final String KEY_GET_EtWcmWdItemData_Location = "Location";
    private static final String KEY_GET_EtWcmWdItemData_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdItemData_Action = "Action";
    private static final String KEY_GET_EtWcmWdItemData_Btg = "Btg";
    private static final String KEY_GET_EtWcmWdItemData_Etg = "Etg";
    private static final String KEY_GET_EtWcmWdItemData_Bug = "Bug";
    private static final String KEY_GET_EtWcmWdItemData_Eug = "Eug";
    /* EtWcmWdItemData Table and Fields Names */

    public static String Get_Data(Activity activity, String transmit_type, String operation, String equip, String funcLoc) {
        try {
            Get_Response = "";
            success = false;
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Creating EtWcmWdData Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdData);
            String CREATE_TABLE_GET_EtWcmWdData = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdData + ""
                    + "( "
                    + KEY_GET_EtWcmWdData_ID + " INTEGER PRIMARY KEY,"
                    + KEY_GET_EtWcmWdData_Aufnr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Objart + " TEXT,"
                    + KEY_GET_EtWcmWdData_Wcnr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Iwerk + " TEXT,"
                    + KEY_GET_EtWcmWdData_Objtyp + " TEXT,"
                    + KEY_GET_EtWcmWdData_Usage + " TEXT,"
                    + KEY_GET_EtWcmWdData_Usagex + " TEXT,"
                    + KEY_GET_EtWcmWdData_Train + " TEXT,"
                    + KEY_GET_EtWcmWdData_Trainx + " TEXT,"
                    + KEY_GET_EtWcmWdData_Anlzu + " TEXT,"
                    + KEY_GET_EtWcmWdData_Anlzux + " TEXT,"
                    + KEY_GET_EtWcmWdData_Etape + " TEXT,"
                    + KEY_GET_EtWcmWdData_Etapex + " TEXT,"
                    + KEY_GET_EtWcmWdData_Stxt + " TEXT,"
                    + KEY_GET_EtWcmWdData_Datefr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Timefr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Dateto + " TEXT,"
                    + KEY_GET_EtWcmWdData_Timeto + " TEXT,"
                    + KEY_GET_EtWcmWdData_Priok + " TEXT,"
                    + KEY_GET_EtWcmWdData_Priokx + " TEXT, "
                    + KEY_GET_EtWcmWdData_Rctime + " TEXT,"
                    + KEY_GET_EtWcmWdData_Rcunit + " TEXT,"
                    + KEY_GET_EtWcmWdData_Objnr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Refobj + " TEXT,"
                    + KEY_GET_EtWcmWdData_Crea + " TEXT,"
                    + KEY_GET_EtWcmWdData_Prep + " TEXT,"
                    + KEY_GET_EtWcmWdData_Comp + " TEXT,"
                    + KEY_GET_EtWcmWdData_Appr + " TEXT,"
                    + KEY_GET_EtWcmWdData_Action + " TEXT,"
                    + KEY_GET_EtWcmWdData_Begru + " TEXT,"
                    + KEY_GET_EtWcmWdData_Begtx + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_GET_EtWcmWdData);
		        /* Creating EtWcmWdData Table with Fields */

		        /* Creating EtWcmWdItemData Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdItemData);
            String CREATE_TABLE_GET_EtWcmWdItemData = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdItemData + ""
                    + "( "
                    + KEY_GET_EtWcmWdItemData_ID + " INTEGER PRIMARY KEY,"
                    + KEY_GET_EtWcmWdItemData_Wcnr + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Wcitm + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Objnr + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Itmtyp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Seq + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Pred + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Succ + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Ccobj + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Cctyp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Stxt + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tggrp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgstep + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgproc + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgtyp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgseq + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgtxt + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Unstep + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Unproc + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Untyp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Unseq + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Untxt + " TEXT, "
                    + KEY_GET_EtWcmWdItemData_Phblflg + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Phbltyp + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Phblnr + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgflg + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgform + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Tgnr + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Unform + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Unnr + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Control + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Location + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Refobj + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Action + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Btg + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Etg + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Bug + " TEXT,"
                    + KEY_GET_EtWcmWdItemData_Eug + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_GET_EtWcmWdItemData);
		        /* Creating EtWcmWdItemData Table with Fields */

            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataTagtext);
            String CREATE_TABLE_EtWcmWdDataTagtext = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataTagtext + ""
                    + "( "
                    + KEY_EtWcmWdDataTagtext_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtWcmWdDataTagtext_Aufnr + " TEXT,"
                    + KEY_EtWcmWdDataTagtext_Wcnr + " TEXT,"
                    + KEY_EtWcmWdDataTagtext_Objtype + " TEXT,"
                    + KEY_EtWcmWdDataTagtext_FormatCol + " TEXT,"
                    + KEY_EtWcmWdDataTagtext_TextLine + " TEXT,"
                    + KEY_EtWcmWdDataTagtext_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtWcmWdDataTagtext);

            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataUntagtext);
            String CREATE_TABLE_EtWcmWdDataUntagtext = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataUntagtext + ""
                    + "( "
                    + KEY_EtWcmWdDataUntagtext_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtWcmWdDataUntagtext_Aufnr + " TEXT,"
                    + KEY_EtWcmWdDataUntagtext_Wcnr + " TEXT,"
                    + KEY_EtWcmWdDataUntagtext_Objtype + " TEXT,"
                    + KEY_EtWcmWdDataUntagtext_FormatCol + " TEXT,"
                    + KEY_EtWcmWdDataUntagtext_TextLine + " TEXT,"
                    + KEY_EtWcmWdDataUntagtext_Action + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtWcmWdDataUntagtext);

            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D7", "RD", "ODATA"});
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
            map.put("IvUser", username.toUpperCase().toString());
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IVEQUNR", equip);
            map.put("IVTPLNR", funcLoc);
            map.put("Operation", operation);
            map.put("IvTransmitType", transmit_type);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.getORDERDetails(url_link, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_DORD_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {

                    orders_uuid_list.clear();

                        /*Reading Response Data and Parsing to Serializable*/
                    Orders_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                    try {
                        App_db.beginTransaction();

                        for (int i = 0; i < response_data_jsonArray.length(); i++) {
                            JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                            /*Reading and Inserting Data into Database Table for EtWcmWdData*/
                            if (jsonObject.has("EtWcmWdData")) {
                                try {
                                    String EtWcmWdData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWdData_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWcmWdData_sql = "Insert into Tmp_EtWcmWdData (Aufnr, Objart, Wcnr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWdData_statement = App_db.compileStatement(EtWcmWdData_sql);
                                        EtWcmWdData_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtWcmWdData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtWcmWdData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWdData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Wcnr"));
                                            EtWcmWdData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWdData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWdData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Usage"));
                                            EtWcmWdData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usagex"));
                                            EtWcmWdData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Train"));
                                            EtWcmWdData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Trainx"));
                                            EtWcmWdData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtWcmWdData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtWcmWdData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Etape"));
                                            EtWcmWdData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etapex"));
                                            EtWcmWdData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWdData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Datefr"));
                                            EtWcmWdData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Timefr"));
                                            EtWcmWdData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Dateto"));
                                            EtWcmWdData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Timeto"));
                                            EtWcmWdData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtWcmWdData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtWcmWdData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Rctime"));
                                            EtWcmWdData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Rcunit"));
                                            EtWcmWdData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWdData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Refobj"));
                                            EtWcmWdData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Crea"));
                                            EtWcmWdData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Prep"));
                                            EtWcmWdData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Comp"));
                                            EtWcmWdData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Appr"));
                                            EtWcmWdData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWdData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Begru"));
                                            EtWcmWdData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begtx"));
                                            if (jsonArray.getJSONObject(j).has("EtWcmWdDataTagtext")) {
                                                try {
                                                    String EtWcmWdDataTagtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults().get(j).getEtWcmWdDataTagtext().getResults());
                                                    JSONArray jsonArray1 = new JSONArray(EtWcmWdDataTagtext_response_data);
                                                    if (jsonArray1.length() > 0) {
                                                        String EtWcmWdDataTagtext_sql = "Insert into Tmp_EtWcmWdDataTagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                        SQLiteStatement EtWcmWdDataTagtext_statement = App_db.compileStatement(EtWcmWdDataTagtext_sql);
                                                        EtWcmWdDataTagtext_statement.clearBindings();
                                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                                            EtWcmWdDataTagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                            EtWcmWdDataTagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                            EtWcmWdDataTagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                            EtWcmWdDataTagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                            EtWcmWdDataTagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                            EtWcmWdDataTagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                            EtWcmWdDataTagtext_statement.execute();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                            if (jsonArray.getJSONObject(j).has("EtWcmWdDataUntagtext")) {
                                                try {
                                                    String EtWcmWdDataUntagtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults().get(j).getEtWcmWdDataUntagtext().getResults());
                                                    JSONArray jsonArray1 = new JSONArray(EtWcmWdDataUntagtext_response_data);
                                                    if (jsonArray1.length() > 0) {
                                                        String EtWcmWdDataUntagtext_sql = "Insert into Tmp_EtWcmWdDataUntagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                        SQLiteStatement EtWcmWdDataUntagtext_statement = App_db.compileStatement(EtWcmWdDataUntagtext_sql);
                                                        EtWcmWdDataUntagtext_statement.clearBindings();
                                                        for (int k = 0; k < jsonArray1.length(); k++) {
                                                            EtWcmWdDataUntagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                            EtWcmWdDataUntagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                            EtWcmWdDataUntagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                            EtWcmWdDataUntagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                            EtWcmWdDataUntagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                            EtWcmWdDataUntagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                            EtWcmWdDataUntagtext_statement.execute();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                            EtWcmWdData_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/

                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                            if (jsonObject.has("EtWcmWdItemData")) {
                                try {
                                    String EtWcmWdItemData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdItemData().getResults());
                                    JSONArray jsonArray = new JSONArray(EtWcmWdItemData_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String EtWcmWdItemData_sql = "Insert into Tmp_EtWcmWdItemData (Wcnr, Wcitm, Objnr, Itmtyp, Seq, Pred, Succ, Ccobj, Cctyp, Stxt, Tggrp, Tgstep, Tgproc, Tgtyp, Tgseq, Tgtxt, Unstep, Unproc, Untyp, Unseq, Untxt, Phblflg, Phbltyp, Phblnr, Tgflg, Tgform, Tgnr, Unform, Unnr, Control, Location, Refobj, Action, Btg, Etg, Bug, Eug) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWdItemData_statement = App_db.compileStatement(EtWcmWdItemData_sql);
                                            EtWcmWdItemData_statement.clearBindings();
                                            EtWcmWdItemData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Wcnr"));
                                            EtWcmWdItemData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Wcitm"));
                                            EtWcmWdItemData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWdItemData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Itmtyp"));
                                            EtWcmWdItemData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Seq"));
                                            EtWcmWdItemData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Pred"));
                                            EtWcmWdItemData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Succ"));
                                            EtWcmWdItemData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Ccobj"));
                                            EtWcmWdItemData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Cctyp"));
                                            EtWcmWdItemData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWdItemData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Tggrp"));
                                            EtWcmWdItemData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Tgstep"));
                                            EtWcmWdItemData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Tgproc"));
                                            EtWcmWdItemData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Tgtyp"));
                                            EtWcmWdItemData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Tgseq"));
                                            EtWcmWdItemData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Tgtxt"));
                                            EtWcmWdItemData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Unstep"));
                                            EtWcmWdItemData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Unproc"));
                                            EtWcmWdItemData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Untyp"));
                                            EtWcmWdItemData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Unseq"));
                                            EtWcmWdItemData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Untxt"));
                                            EtWcmWdItemData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Phblflg"));
                                            EtWcmWdItemData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Phbltyp"));
                                            EtWcmWdItemData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Phblnr"));
                                            EtWcmWdItemData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Tgflg"));
                                            EtWcmWdItemData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Tgform"));
                                            EtWcmWdItemData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Tgnr"));
                                            EtWcmWdItemData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Unform"));
                                            EtWcmWdItemData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Unnr"));
                                            EtWcmWdItemData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Control"));
                                            EtWcmWdItemData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Location"));
                                            EtWcmWdItemData_statement.bindString(32, jsonArray.getJSONObject(j).optString("Refobj"));
                                            EtWcmWdItemData_statement.bindString(33, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWdItemData_statement.bindString(34, jsonArray.getJSONObject(j).optString("Btg"));
                                            EtWcmWdItemData_statement.bindString(35, jsonArray.getJSONObject(j).optString("Etg"));
                                            EtWcmWdItemData_statement.bindString(36, jsonArray.getJSONObject(j).optString("Bug"));
                                            EtWcmWdItemData_statement.bindString(37, jsonArray.getJSONObject(j).optString("Eug"));
                                            EtWcmWdItemData_statement.execute();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                        }

                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                        Get_Response = "success";
                    } catch (Exception e) {

                    }
                } else {
                    Get_Response = "Unable to Get Template. Please try again.";
                }
            }
        } catch (Exception e) {
            Log.v("POAT_REL", "" + e.getMessage());
            Get_Response = "Unable to Get Template. Please try again.";
        }
        return Get_Response;
    }
}
