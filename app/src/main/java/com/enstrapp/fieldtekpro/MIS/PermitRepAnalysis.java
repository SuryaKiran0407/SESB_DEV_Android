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

/**
 * Created by Enstrapp on 20/02/18.
 */

public class PermitRepAnalysis {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();

    /*EsPermitTotal Tabel and Fieldnames*/
    private static final String TABLE_EsPermitTotal = "EsPermitTotal";
    private static final String KEY_EsPermitTotal_ID = "ID";
    private static final String KEY_EsPermitTotal_Total = "Total";
    private static final String KEY_EsPermitTotal_Crea = "Crea";
    private static final String KEY_EsPermitTotal_Prep = "Prep";
    private static final String KEY_EsPermitTotal_Clsd = "Clsd ";
    private static final String KEY_EsPermitTotal_Reje = "Reje";
    /*EsPermitTotal Tabel and Fieldnames*/

    /*EsPermitAppr Tabel and Fieldnames*/
    private static final String TABLE_EsPermitAppr = "EsPermitAppr";
    private static final String KEY_EsPermitAppr_ID = "ID";
    private static final String KEY_EsPermitAppr_Total = "Total";
    private static final String KEY_EsPermitAppr_Red = "Red";
    private static final String KEY_EsPermitAppr_Yellow = "Yellow";
    private static final String KEY_EsPermitAppr_Green = "Green";

    /*EsPermitAppr Tabel and Fieldnames*/

    /*EtPermitTotalWerks Tabel and Fieldnames*/
    private static final String TABLE_EtPermitTotalWerks = "EtPermitTotalWerks";
    private static final String KEY_EtPermitTotalWerks_ID = "ID";
    private static final String KEY_EtPermitTotalWerks_Iwerk = "Iwerk";
    private static final String KEY_EtPermitTotalWerks_Name = "Name";
    private static final String KEY_EtPermitTotalWerks_Total = "Total";
    private static final String KEY_EtPermitTotalWerks_Crea = "Crea";
    private static final String KEY_EtPermitTotalWerks_Prep = "Prep";
    private static final String KEY_EtPermitTotalWerks_Clsd = "Clsd";
    private static final String KEY_EtPermitTotalWerks_Reje = "Reje";

    /*EtPermitTotalWerks Tabel and Fieldnames*/


    /*EtPermitApprWerks Tabel and Fieldnames*/
    private static final String TABLE_EtPermitApprWerks = "EtPermitApprWerks";
    private static final String KEY_EtPermitApprWerks_ID = "ID";
    private static final String KEY_EtPermitApprWerks_Iwerk = "Iwerk";
    private static final String KEY_EtPermitApprWerks_Name = "Name";
    private static final String KEY_EtPermitApprWerks_Total = "Total";
    private static final String KEY_EtPermitApprWerks_Red = "Red";
    private static final String KEY_EtPermitApprWerks_Yellow = "Yellow";
    private static final String KEY_EtPermitApprWerks_Green = "Green";

    /*EtPermitApprWerks Tabel and Fieldnames*/


    /*EtPermitTotalArbpl Tabel and Fieldnames*/
    private static final String TABLE_EtPermitTotalArbpl = "EtPermitTotalArbpl";
    private static final String KEY_EtPermitTotalArbpl_ID = "ID";
    private static final String KEY_EtPermitTotalArbpl_Iwerk = "Iwerk";
    private static final String KEY_EtPermitTotalArbpl_Arbpl = "Arbpl";
    private static final String KEY_EtPermitTotalArbpl_Name = "Name";
    private static final String KEY_EtPermitTotalArbpl_Total = "Total";
    private static final String KEY_EtPermitTotalArbpl_Crea = "Crea";
    private static final String KEY_EtPermitTotalArbpl_Prep = "Prep";
    private static final String KEY_EtPermitTotalArbpl_Clsd = "Clsd";
    private static final String KEY_EtPermitTotalArbpl_Reje = "Reje";


    /*EtPermitTotalArbpl Tabel and Fieldnames*/

    /*EtPermitApprArbpl Tabel and Fieldnames*/
    private static final String TABLE_EtPermitApprArbpl = "EtPermitApprArbpl";
    private static final String KEY_EtPermitApprArbpl_ID = "ID";
    private static final String KEY_EtPermitApprArbpl_Iwerk = "Iwerk";
    private static final String KEY_EtPermitApprArbpl_Arbpl = "Arbpl";
    private static final String KEY_EtPermitApprArbpl_Name = "Name";
    private static final String KEY_EtPermitApprArbpl_Total = "Total";
    private static final String KEY_EtPermitApprArbpl_Red = "Red";
    private static final String KEY_EtPermitApprArbpl_Yellow = "Yellow";
    private static final String KEY_EtPermitApprArbpl_Green = "Green";

    /*EtPermitApprArbpl Tabel and Fieldnames*/

    /*EtPermitWw Tabel and Fieldnames*/
    private static final String TABLE_EtPermitWw = "EtPermitWw";
    private static final String KEY_EtPermitWw_ID = "ID";
    private static final String KEY_EtPermitWw_Iwerk = "Iwerk";
    private static final String KEY_EtPermitWw_Arbpl = "Arbpl";
    private static final String KEY_EtPermitWw_Crea = "Crea";
    private static final String KEY_EtPermitWw_Prep = "Prep";
    private static final String KEY_EtPermitWw_Clsd = "Clsd";
    private static final String KEY_EtPermitWw_Reje = "Reje";
    private static final String KEY_EtPermitWw_Red = "Red";
    private static final String KEY_EtPermitWw_Yellow = "Yellow";
    private static final String KEY_EtPermitWw_Green = "Green";
    private static final String KEY_EtPermitWw_Aufnr = "Aufnr";
    private static final String KEY_EtPermitWw_Wapnr = "Wapnr";
    private static final String KEY_EtPermitWw_Objart = "Objart";
    private static final String KEY_EtPermitWw_Objnr = "Objnr";
    private static final String KEY_EtPermitWw_Stxt = "Stxt";
    private static final String KEY_EtPermitWw_Datefr = "Datefr";
    private static final String KEY_EtPermitWw_Timefr = "Timefr";
    private static final String KEY_EtPermitWw_Dateto = "Dateto";
    private static final String KEY_EtPermitWw_Timeto = "Timeto";
    private static final String KEY_EtPermitWw_Priok = "Priok";
    private static final String KEY_EtPermitWw_Tplnr = "Tplnr";
    private static final String KEY_EtPermitWw_Equnr = "Equnr";
    private static final String KEY_EtPermitWw_Eqktx = "Eqktx";
    private static final String KEY_EtPermitWw_Refobj = "Refobj";

    /*EtPermitWw Tabel and Fieldnames*/


    /*EtPermitWa Tabel and Fieldnames*/
    private static final String TABLE_EtPermitWa = "EtPermitWa";
    private static final String KEY_EtPermitWa_ID = "ID";
    private static final String KEY_EtPermitWa_Iwerk = "Iwerk";
    private static final String KEY_EtPermitWa_Crea = "Crea";
    private static final String KEY_EtPermitWa_Prep = "Prep";
    private static final String KEY_EtPermitWa_Clsd = "Clsd";
    private static final String KEY_EtPermitWa_Reje = "Reje";
    private static final String KEY_EtPermitWa_Red = "Red";
    private static final String KEY_EtPermitWa_Yellow = "Yellow";
    private static final String KEY_EtPermitWa_Green = "Green";
    private static final String KEY_EtPermitWa_Aufnr = "Aufnr";
    private static final String KEY_EtPermitWa_Wapnr = "Wapnr";
    private static final String KEY_EtPermitWa_Wapinr = "Wapinr";
    private static final String KEY_EtPermitWa_Objart = "Objart";
    private static final String KEY_EtPermitWa_Objtyp = "Objtyp";
    private static final String KEY_EtPermitWa_Objnr = "Objnr";
    private static final String KEY_EtPermitWa_Stxt = "Stxt";
    private static final String KEY_EtPermitWa_Arbpl = "Arbpl";
    private static final String KEY_EtPermitWa_Datefr = "Datefr";
    private static final String KEY_EtPermitWa_Timefr = "Timefr";
    private static final String KEY_EtPermitWa_Dateto = "Dateto";
    private static final String KEY_EtPermitWa_Timeto = "Timeto";
    private static final String KEY_EtPermitWa_Priok = "Priok";
    private static final String KEY_EtPermitWa_Tplnr = "Tplnr";
    private static final String KEY_EtPermitWa_Equnr = "Equnr";
    private static final String KEY_EtPermitWa_Eqktx = "Eqktx";
    private static final String KEY_EtPermitWa_Refobj = "Refobj";

    /*EtPermitWa Tabel and Fieldnames*/

    /*EtPermitWd Tabel and Fieldnames*/
    private static final String TABLE_EtPermitWd = "EtPermitWd";
    private static final String KEY_EtPermitWd_ID = "ID";
    private static final String KEY_EtPermitWd_Iwerk = "Iwerk";
    private static final String KEY_EtPermitWd_Crea = "Crea";
    private static final String KEY_EtPermitWd_Prep = "Prep";
    private static final String KEY_EtPermitWd_Clsd = "Clsd";
    private static final String KEY_EtPermitWd_Reje = "Reje";
    private static final String KEY_EtPermitWd_Red = "Red";
    private static final String KEY_EtPermitWd_Yellow = "Yellow";
    private static final String KEY_EtPermitWd_Green = "Green";
    private static final String KEY_EtPermitWd_Aufnr = "Aufnr";
    private static final String KEY_EtPermitWd_Wapnr = "Wapnr";
    private static final String KEY_EtPermitWd_Wapinr = "Wapinr";
    private static final String KEY_EtPermitWd_Wcnr = "Wcnr";
    private static final String KEY_EtPermitWd_Objart = "Objart";
    private static final String KEY_EtPermitWd_Objtyp = "Objtyp";
    private static final String KEY_EtPermitWd_Objnr = "Objnr";
    private static final String KEY_EtPermitWd_Stxt = "Stxt";
    private static final String KEY_EtPermitWd_Arbpl = "Arbpl";
    private static final String KEY_EtPermitWd_Datefr = "Datefr";
    private static final String KEY_EtPermitWd_Timefr = "Timefr";
    private static final String KEY_EtPermitWd_Dateto = "Dateto";
    private static final String KEY_EtPermitWd_Timeto = "Timeto";
    private static final String KEY_EtPermitWd_Priok = "Priok";
    private static final String KEY_EtPermitWd_Tplnr = "Tplnr";
    private static final String KEY_EtPermitWd_Equnr = "Equnr";
    private static final String KEY_EtPermitWd_Eqktx = "Eqktx";
    private static final String KEY_EtPermitWd_Refobj = "Refobj";

    /*EtPermitWd Tabel and Fieldnames*/

    public static String Get_PermitReport_Analysis_Data(Activity activity, String transmit_type, String month, String year) {
        try {

            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Creating EsPermitTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EsPermitTotal);
            String CREATE_TABLE_EsPermitTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EsPermitTotal + ""
                    + "( "
                    + KEY_EsPermitTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EsPermitTotal_Total + " TEXT,"
                    + KEY_EsPermitTotal_Crea + " TEXT,"
                    + KEY_EsPermitTotal_Prep + " TEXT,"
                    + KEY_EsPermitTotal_Clsd + " TEXT,"
                    + KEY_EsPermitTotal_Reje + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EsPermitTotal);
            /* Creating EsPermitTotal Table with Fields */

            /* Creating EsPermitAppr Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EsPermitAppr);
            String CREATE_TABLE_EsPermitAppr = "CREATE TABLE IF NOT EXISTS " + TABLE_EsPermitAppr + ""
                    + "( "
                    + KEY_EsPermitAppr_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EsPermitAppr_Total + " TEXT,"
                    + KEY_EsPermitAppr_Red + " TEXT,"
                    + KEY_EsPermitAppr_Yellow + " TEXT,"
                    + KEY_EsPermitAppr_Green + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EsPermitAppr);
            /* Creating EsPermitAppr Table with Fields */


            /* Creating EtPermitTotalWerks Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitTotalWerks);
            String CREATE_TABLE_EtPermitTotalWerks = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitTotalWerks + ""
                    + "( "
                    + KEY_EtPermitTotalWerks_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitTotalWerks_Iwerk + " TEXT,"
                    + KEY_EtPermitTotalWerks_Name + " TEXT,"
                    + KEY_EtPermitTotalWerks_Total + " TEXT,"
                    + KEY_EtPermitTotalWerks_Crea + " TEXT,"
                    + KEY_EtPermitTotalWerks_Prep + " TEXT,"
                    + KEY_EtPermitTotalWerks_Clsd + " TEXT,"
                    + KEY_EtPermitTotalWerks_Reje + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitTotalWerks);
            /* Creating EtPermitTotalWerks Table with Fields */

            /* Creating EtPermitApprWerks Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitApprWerks);
            String CREATE_TABLE_EtPermitApprWerks = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitApprWerks + ""
                    + "( "
                    + KEY_EtPermitApprWerks_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitApprWerks_Iwerk + " TEXT,"
                    + KEY_EtPermitApprWerks_Name + " TEXT,"
                    + KEY_EtPermitApprWerks_Total + " TEXT,"
                    + KEY_EtPermitApprWerks_Red + " TEXT,"
                    + KEY_EtPermitApprWerks_Yellow + " TEXT,"
                    + KEY_EtPermitApprWerks_Green + " TEXT"

                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitApprWerks);
            /* Creating EtPermitApprWerks Table with Fields */


            /* Creating EtPermitTotalArbpl Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitTotalArbpl);
            String CREATE_TABLE_EtPermitTotalArbpl = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitTotalArbpl + ""
                    + "( "
                    + KEY_EtPermitTotalArbpl_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitTotalArbpl_Iwerk + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Arbpl + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Name + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Total + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Crea + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Prep + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Clsd + " TEXT,"
                    + KEY_EtPermitTotalArbpl_Reje + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitTotalArbpl);
            /* Creating EtPermitTotalArbpl Table with Fields */

            /* Creating EtPermitApprArbpl Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitApprArbpl);
            String CREATE_TABLE_EtPermitApprArbpl = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitApprArbpl + ""
                    + "( "
                    + KEY_EtPermitApprArbpl_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitApprArbpl_Iwerk + " TEXT,"
                    + KEY_EtPermitApprArbpl_Arbpl + " TEXT,"
                    + KEY_EtPermitApprArbpl_Name + " TEXT,"
                    + KEY_EtPermitApprArbpl_Total + " TEXT,"
                    + KEY_EtPermitApprArbpl_Red + " TEXT,"
                    + KEY_EtPermitApprArbpl_Yellow + " TEXT,"
                    + KEY_EtPermitApprArbpl_Green + " TEXT"

                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitApprArbpl);
            /* Creating EtPermitApprArbpl Table with Fields */


            /* Creating EtPermitWw Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitWw);
            String CREATE_TABLE_EtPermitWw = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitWw + ""
                    + "( "
                    + KEY_EtPermitWw_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitWw_Iwerk + " TEXT,"
                    + KEY_EtPermitWw_Arbpl + " TEXT,"
                    + KEY_EtPermitWw_Crea + " TEXT,"
                    + KEY_EtPermitWw_Prep + " TEXT,"
                    + KEY_EtPermitWw_Clsd + " TEXT,"
                    + KEY_EtPermitWw_Reje + " TEXT,"
                    + KEY_EtPermitWw_Red + " TEXT,"
                    + KEY_EtPermitWw_Yellow + " TEXT,"
                    + KEY_EtPermitWw_Green + " TEXT,"
                    + KEY_EtPermitWw_Aufnr + " TEXT,"
                    + KEY_EtPermitWw_Wapnr + " TEXT,"

                    + KEY_EtPermitWw_Objart + " TEXT,"
                    + KEY_EtPermitWw_Objnr + " TEXT,"
                    + KEY_EtPermitWw_Stxt + " TEXT,"
                    + KEY_EtPermitWw_Datefr + " TEXT,"
                    + KEY_EtPermitWw_Timefr + " TEXT,"
                    + KEY_EtPermitWw_Dateto + " TEXT,"
                    + KEY_EtPermitWw_Timeto + " TEXT,"
                    + KEY_EtPermitWw_Priok + " TEXT,"
                    + KEY_EtPermitWw_Tplnr + " TEXT,"
                    + KEY_EtPermitWw_Equnr + " TEXT,"
                    + KEY_EtPermitWw_Eqktx + " TEXT,"
                    + KEY_EtPermitWw_Refobj + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitWw);
            /* Creating EtPermitWw Table with Fields */

            /* Creating EtPermitWa Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitWa);
            String CREATE_TABLE_EtPermitWa = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitWa + ""
                    + "( "
                    + KEY_EtPermitWa_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitWa_Iwerk + " TEXT,"
                    + KEY_EtPermitWa_Crea + " TEXT,"
                    + KEY_EtPermitWa_Prep + " TEXT,"
                    + KEY_EtPermitWa_Clsd + " TEXT,"
                    + KEY_EtPermitWa_Reje + " TEXT,"
                    + KEY_EtPermitWa_Red + " TEXT,"
                    + KEY_EtPermitWa_Yellow + " TEXT,"
                    + KEY_EtPermitWa_Green + " TEXT,"
                    + KEY_EtPermitWa_Aufnr + " TEXT,"
                    + KEY_EtPermitWa_Wapnr + " TEXT,"
                    + KEY_EtPermitWa_Wapinr + " TEXT,"

                    + KEY_EtPermitWa_Objart + " TEXT,"
                    + KEY_EtPermitWa_Objtyp + " TEXT,"
                    + KEY_EtPermitWa_Objnr + " TEXT,"
                    + KEY_EtPermitWa_Stxt + " TEXT,"
                    + KEY_EtPermitWa_Arbpl + " TEXT,"
                    + KEY_EtPermitWa_Datefr + " TEXT,"
                    + KEY_EtPermitWa_Timefr + " TEXT,"
                    + KEY_EtPermitWa_Dateto + " TEXT,"
                    + KEY_EtPermitWa_Timeto + " TEXT,"
                    + KEY_EtPermitWa_Priok + " TEXT,"
                    + KEY_EtPermitWa_Tplnr + " TEXT,"
                    + KEY_EtPermitWa_Equnr + " TEXT,"
                    + KEY_EtPermitWa_Eqktx + " TEXT,"
                    + KEY_EtPermitWa_Refobj + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitWa);
            /* Creating EtPermitWa Table with Fields */

            /* Creating EtPermitWd Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPermitWd);
            String CREATE_TABLE_EtPermitWd = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPermitWd + ""
                    + "( "
                    + KEY_EtPermitWd_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtPermitWd_Iwerk + " TEXT,"
                    + KEY_EtPermitWd_Arbpl + " TEXT,"
                    + KEY_EtPermitWd_Crea + " TEXT,"
                    + KEY_EtPermitWd_Prep + " TEXT,"
                    + KEY_EtPermitWd_Clsd + " TEXT,"
                    + KEY_EtPermitWd_Reje + " TEXT,"
                    + KEY_EtPermitWd_Red + " TEXT,"
                    + KEY_EtPermitWd_Yellow + " TEXT,"
                    + KEY_EtPermitWd_Green + " TEXT,"
                    + KEY_EtPermitWd_Aufnr + " TEXT,"
                    + KEY_EtPermitWd_Wapnr + " TEXT,"
                    + KEY_EtPermitWd_Wapinr + " TEXT,"
                    + KEY_EtPermitWd_Wcnr + " TEXT,"

                    + KEY_EtPermitWd_Objart + " TEXT,"
                    + KEY_EtPermitWd_Objtyp + " TEXT,"
                    + KEY_EtPermitWd_Objnr + " TEXT,"
                    + KEY_EtPermitWd_Stxt + " TEXT,"
                    + KEY_EtPermitWd_Datefr + " TEXT,"
                    + KEY_EtPermitWd_Timefr + " TEXT,"
                    + KEY_EtPermitWd_Dateto + " TEXT,"
                    + KEY_EtPermitWd_Timeto + " TEXT,"
                    + KEY_EtPermitWd_Priok + " TEXT,"
                    + KEY_EtPermitWd_Tplnr + " TEXT,"
                    + KEY_EtPermitWd_Equnr + " TEXT,"
                    + KEY_EtPermitWd_Eqktx + " TEXT,"
                    + KEY_EtPermitWd_Refobj + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtPermitWd);
            /* Creating EtPermitWd Table with Fields */

            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R3", "RD", webservice_type});
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
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvTransmitType", transmit_type);
            map.put("IvIwerk", "");
            map.put("IvMonth", month);
            map.put("IvYear", year);
            map.put("IvArbpl", "");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Permit_Reports_SER> call = service.getPermitReport_Analysis(url_link, basic, map);
            Response<Permit_Reports_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_PermitReport", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {

                    /*Reading Response Data and Parsing to Serializable*/
                    Permit_Reports_SER rs = response.body();
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
                        if (jsonObject.has("EsPermitTotal")) {
                            try {
                                String EsPermitTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEsPermitTotal().getResults());
                                JSONArray jsonArray = new JSONArray(EsPermitTotal_response_data);
                                if (jsonArray.length() > 0) {
                                    String EsPermitTotal_sql = "Insert into EsPermitTotal (Total, Crea, Prep, Clsd,Reje) values(?,?,?,?,?);";
                                    SQLiteStatement EsPermitTotal_statement = App_db.compileStatement(EsPermitTotal_sql);
                                    EsPermitTotal_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EsPermitTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EsPermitTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("Crea"));
                                        EsPermitTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("Prep"));
                                        EsPermitTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EsPermitTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("Reje"));
                                        EsPermitTotal_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EsPermitTotal*/


                        /*Reading and Inserting Data into Database Table for EsPermitAppr*/

                        if (jsonObject.has("EsPermitAppr")) {
                            try {
                                String EsPermitAppr_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEsPermitAppr().getResults());
                                JSONArray jsonArray = new JSONArray(EsPermitAppr_response_data);
                                if (jsonArray.length() > 0) {
                                    String EsPermitAppr_sql = "Insert into EsPermitAppr (Total,Red,Yellow,Green) values(?,?,?,?);";
                                    SQLiteStatement EsPermitAppr_statement = App_db.compileStatement(EsPermitAppr_sql);
                                    EsPermitAppr_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EsPermitAppr_statement.bindString(1, jsonArray.getJSONObject(j).optString("Total"));
                                        EsPermitAppr_statement.bindString(2, jsonArray.getJSONObject(j).optString("Red"));
                                        EsPermitAppr_statement.bindString(3, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EsPermitAppr_statement.bindString(4, jsonArray.getJSONObject(j).optString("Green"));
                                        EsPermitAppr_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }

                        /*Reading and Inserting Data into Database Table for EsPermitAppr*/



                        /*Reading and Inserting Data into Database Table for EtPermitTotalWerks*/

                        if (jsonObject.has("EtPermitTotalWerks")) {
                            try {
                                String EtPermitTotalWerks_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitTotalWerks().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitTotalWerks_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitTotalWerks_sql = "Insert into EtPermitTotalWerks (Iwerk, Name, Total, Crea, Prep, Clsd, Reje) values(?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitTotalWerks_statement = App_db.compileStatement(EtPermitTotalWerks_sql);
                                    EtPermitTotalWerks_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitTotalWerks_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitTotalWerks_statement.bindString(2, jsonArray.getJSONObject(j).optString("Name"));
                                        EtPermitTotalWerks_statement.bindString(3, jsonArray.getJSONObject(j).optString("Total"));
                                        EtPermitTotalWerks_statement.bindString(4, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtPermitTotalWerks_statement.bindString(5, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtPermitTotalWerks_statement.bindString(6, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EtPermitTotalWerks_statement.bindString(7, jsonArray.getJSONObject(j).optString("Reje"));
                                        EtPermitTotalWerks_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitTotalWerks*/

                        /*Reading and Inserting Data into Database Table for EtPermitApprWerks*/

                        if (jsonObject.has("EtPermitApprWerks")) {
                            try {
                                String EtPermitApprWerks_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitApprWerks().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitApprWerks_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitApprWerks_sql = "Insert into EtPermitApprWerks (Iwerk, Name, Total, Red, Yellow, Green) values(?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitApprWerks_statement = App_db.compileStatement(EtPermitApprWerks_sql);
                                    EtPermitApprWerks_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitApprWerks_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitApprWerks_statement.bindString(2, jsonArray.getJSONObject(j).optString("Name"));
                                        EtPermitApprWerks_statement.bindString(3, jsonArray.getJSONObject(j).optString("Total"));
                                        EtPermitApprWerks_statement.bindString(4, jsonArray.getJSONObject(j).optString("Red"));
                                        EtPermitApprWerks_statement.bindString(5, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EtPermitApprWerks_statement.bindString(6, jsonArray.getJSONObject(j).optString("Green"));
                                        EtPermitApprWerks_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitApprWerks*/


                        /*Reading and Inserting Data into Database Table for EtPermitTotalArbpl*/

                        if (jsonObject.has("EtPermitTotalArbpl")) {
                            try {
                                String EtPermitTotalArbpl_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitTotalArbpl().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitTotalArbpl_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitTotalArbpl_sql = "Insert into EtPermitTotalArbpl (Iwerk,Arbpl,Name,Total,Crea,Prep,Clsd,Reje) values(?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitTotalArbpl_statement = App_db.compileStatement(EtPermitTotalArbpl_sql);
                                    EtPermitTotalArbpl_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitTotalArbpl_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitTotalArbpl_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtPermitTotalArbpl_statement.bindString(3, jsonArray.getJSONObject(j).optString("Name"));
                                        EtPermitTotalArbpl_statement.bindString(4, jsonArray.getJSONObject(j).optString("Total"));
                                        EtPermitTotalArbpl_statement.bindString(5, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtPermitTotalArbpl_statement.bindString(6, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtPermitTotalArbpl_statement.bindString(7, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EtPermitTotalArbpl_statement.bindString(8, jsonArray.getJSONObject(j).optString("Reje"));
                                        EtPermitTotalArbpl_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitTotalArbpl*/


                        /*Reading and Inserting Data into Database Table for EtPermitApprArbpl*/

                        if (jsonObject.has("EtPermitApprArbpl")) {
                            try {
                                String EtPermitApprArbpl_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitApprArbpl().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitApprArbpl_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitApprArbpl_sql = "Insert into EtPermitApprArbpl (Iwerk,Arbpl,Name,Total,Red,Yellow,Green) values(?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitApprArbpl_statement = App_db.compileStatement(EtPermitApprArbpl_sql);
                                    EtPermitApprArbpl_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitApprArbpl_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitApprArbpl_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtPermitApprArbpl_statement.bindString(3, jsonArray.getJSONObject(j).optString("Name"));
                                        EtPermitApprArbpl_statement.bindString(4, jsonArray.getJSONObject(j).optString("Total"));
                                        EtPermitApprArbpl_statement.bindString(5, jsonArray.getJSONObject(j).optString("Red"));
                                        EtPermitApprArbpl_statement.bindString(6, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EtPermitApprArbpl_statement.bindString(7, jsonArray.getJSONObject(j).optString("Green"));
                                        EtPermitApprArbpl_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitApprArbpl*/




                        /*Reading and Inserting Data into Database Table for EtPermitWw*/

                        if (jsonObject.has("EtPermitWw")) {
                            try {
                                String EtPermitWw_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitWw().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitWw_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitWw_sql = "Insert into EtPermitWw (Iwerk,Arbpl,Crea,Prep,Clsd,Reje,Red,Yellow,Green,Aufnr,Wapnr,Objart,Objnr,Stxt,Datefr,Timefr,Dateto,Timeto,Priok,Tplnr,Equnr,Eqktx,Refobj) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitWw_statement = App_db.compileStatement(EtPermitWw_sql);
                                    EtPermitWw_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitWw_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitWw_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtPermitWw_statement.bindString(3, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtPermitWw_statement.bindString(4, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtPermitWw_statement.bindString(5, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EtPermitWw_statement.bindString(6, jsonArray.getJSONObject(j).optString("Reje"));
                                        EtPermitWw_statement.bindString(7, jsonArray.getJSONObject(j).optString("Red"));
                                        EtPermitWw_statement.bindString(8, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EtPermitWw_statement.bindString(9, jsonArray.getJSONObject(j).optString("Green"));
                                        EtPermitWw_statement.bindString(10, jsonArray.getJSONObject(j).optString("Aufnr"));
                                        EtPermitWw_statement.bindString(11, jsonArray.getJSONObject(j).optString("Wapnr"));
                                        EtPermitWw_statement.bindString(12, jsonArray.getJSONObject(j).optString("Objart"));
                                        EtPermitWw_statement.bindString(13, jsonArray.getJSONObject(j).optString("Objnr"));
                                        EtPermitWw_statement.bindString(14, jsonArray.getJSONObject(j).optString("Stxt"));
                                        EtPermitWw_statement.bindString(15, jsonArray.getJSONObject(j).optString("Datefr"));
                                        EtPermitWw_statement.bindString(16, jsonArray.getJSONObject(j).optString("Timefr"));
                                        EtPermitWw_statement.bindString(17, jsonArray.getJSONObject(j).optString("Dateto"));
                                        EtPermitWw_statement.bindString(18, jsonArray.getJSONObject(j).optString("Timeto"));
                                        EtPermitWw_statement.bindString(19, jsonArray.getJSONObject(j).optString("Priok"));
                                        EtPermitWw_statement.bindString(20, jsonArray.getJSONObject(j).optString("Tplnr"));
                                        EtPermitWw_statement.bindString(21, jsonArray.getJSONObject(j).optString("Equnr"));
                                        EtPermitWw_statement.bindString(22, jsonArray.getJSONObject(j).optString("Eqktx"));
                                        EtPermitWw_statement.bindString(23, jsonArray.getJSONObject(j).optString("Refobj"));
                                        EtPermitWw_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitWw*/

                        /*Reading and Inserting Data into Database Table for EtPermitWa*/

                        if (jsonObject.has("EtPermitWa")) {
                            try {
                                String EtPermitWa_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitWa().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitWa_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitWa_sql = "Insert into EtPermitWa (Iwerk,Crea,Prep,Clsd,Reje,Red,Yellow,Green,Aufnr,Wapnr,Wapinr,Objart,Objtyp,Objnr,Stxt,Arbpl,Datefr,Timefr,Dateto,Timeto,Priok,Tplnr,Equnr,Eqktx,Refobj) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitWa_statement = App_db.compileStatement(EtPermitWa_sql);
                                    EtPermitWa_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitWa_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitWa_statement.bindString(2, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtPermitWa_statement.bindString(3, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtPermitWa_statement.bindString(4, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EtPermitWa_statement.bindString(5, jsonArray.getJSONObject(j).optString("Reje"));
                                        EtPermitWa_statement.bindString(6, jsonArray.getJSONObject(j).optString("Red"));
                                        EtPermitWa_statement.bindString(7, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EtPermitWa_statement.bindString(8, jsonArray.getJSONObject(j).optString("Green"));
                                        EtPermitWa_statement.bindString(9, jsonArray.getJSONObject(j).optString("Aufnr"));
                                        EtPermitWa_statement.bindString(10, jsonArray.getJSONObject(j).optString("Wapnr"));
                                        EtPermitWa_statement.bindString(11, jsonArray.getJSONObject(j).optString("Wapinr"));
                                        EtPermitWa_statement.bindString(12, jsonArray.getJSONObject(j).optString("Objart"));
                                        EtPermitWa_statement.bindString(13, jsonArray.getJSONObject(j).optString("Objtyp"));
                                        EtPermitWa_statement.bindString(14, jsonArray.getJSONObject(j).optString("Objnr"));
                                        EtPermitWa_statement.bindString(15, jsonArray.getJSONObject(j).optString("Stxt"));
                                        EtPermitWa_statement.bindString(16, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtPermitWa_statement.bindString(17, jsonArray.getJSONObject(j).optString("Datefr"));
                                        EtPermitWa_statement.bindString(18, jsonArray.getJSONObject(j).optString("Timefr"));
                                        EtPermitWa_statement.bindString(19, jsonArray.getJSONObject(j).optString("Dateto"));
                                        EtPermitWa_statement.bindString(20, jsonArray.getJSONObject(j).optString("Timeto"));
                                        EtPermitWa_statement.bindString(21, jsonArray.getJSONObject(j).optString("Priok"));
                                        EtPermitWa_statement.bindString(22, jsonArray.getJSONObject(j).optString("Tplnr"));
                                        EtPermitWa_statement.bindString(23, jsonArray.getJSONObject(j).optString("Equnr"));
                                        EtPermitWa_statement.bindString(24, jsonArray.getJSONObject(j).optString("Eqktx"));
                                        EtPermitWa_statement.bindString(25, jsonArray.getJSONObject(j).optString("Refobj"));
                                        EtPermitWa_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitWa*/

                        /*Reading and Inserting Data into Database Table for EtPermitWd*/

                        if (jsonObject.has("EtPermitWd")) {
                            try {
                                String EtPermitWd_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtPermitWd().getResults());
                                JSONArray jsonArray = new JSONArray(EtPermitWd_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtPermitWd_sql = "Insert into EtPermitWd(Iwerk,Arbpl,Crea,Prep,Clsd,Reje,Red,Yellow,Green,Aufnr,Wapnr,Wapinr,Wcnr,Objart,Objtyp,Objnr,Stxt,Datefr,Timefr,Dateto,Timeto,Priok,Tplnr,Equnr,Eqktx,Refobj) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtPermitWd_statement = App_db.compileStatement(EtPermitWd_sql);
                                    EtPermitWd_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtPermitWd_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtPermitWd_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                        EtPermitWd_statement.bindString(3, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtPermitWd_statement.bindString(4, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtPermitWd_statement.bindString(5, jsonArray.getJSONObject(j).optString("Clsd"));
                                        EtPermitWd_statement.bindString(6, jsonArray.getJSONObject(j).optString("Reje"));
                                        EtPermitWd_statement.bindString(7, jsonArray.getJSONObject(j).optString("Red"));
                                        EtPermitWd_statement.bindString(8, jsonArray.getJSONObject(j).optString("Yellow"));
                                        EtPermitWd_statement.bindString(9, jsonArray.getJSONObject(j).optString("Green"));
                                        EtPermitWd_statement.bindString(10, jsonArray.getJSONObject(j).optString("Aufnr"));
                                        EtPermitWd_statement.bindString(11, jsonArray.getJSONObject(j).optString("Wapnr"));
                                        EtPermitWd_statement.bindString(12, jsonArray.getJSONObject(j).optString("Wapinr"));
                                        EtPermitWd_statement.bindString(13, jsonArray.getJSONObject(j).optString("Wcnr"));
                                        EtPermitWd_statement.bindString(14, jsonArray.getJSONObject(j).optString("Objart"));
                                        EtPermitWd_statement.bindString(15, jsonArray.getJSONObject(j).optString("Objtyp"));
                                        EtPermitWd_statement.bindString(16, jsonArray.getJSONObject(j).optString("Objnr"));
                                        EtPermitWd_statement.bindString(17, jsonArray.getJSONObject(j).optString("Stxt"));
                                        EtPermitWd_statement.bindString(18, jsonArray.getJSONObject(j).optString("Datefr"));
                                        EtPermitWd_statement.bindString(19, jsonArray.getJSONObject(j).optString("Timefr"));
                                        EtPermitWd_statement.bindString(20, jsonArray.getJSONObject(j).optString("Dateto"));
                                        EtPermitWd_statement.bindString(21, jsonArray.getJSONObject(j).optString("Timeto"));
                                        EtPermitWd_statement.bindString(22, jsonArray.getJSONObject(j).optString("Priok"));
                                        EtPermitWd_statement.bindString(23, jsonArray.getJSONObject(j).optString("Tplnr"));
                                        EtPermitWd_statement.bindString(24, jsonArray.getJSONObject(j).optString("Equnr"));
                                        EtPermitWd_statement.bindString(25, jsonArray.getJSONObject(j).optString("Eqktx"));
                                        EtPermitWd_statement.bindString(26, jsonArray.getJSONObject(j).optString("Refobj"));
                                        EtPermitWd_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtPermitWd*/
                    }
                    /*Reading Data by using FOR Loop*/

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
            }
        } catch (Exception ex) {
            Log.v("kiran_PermitReport_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }
}

