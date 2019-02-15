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

public class MeasurementPoint {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    /*GET_SEARCH_EtEquiMptt and Fields Names*/
    private static final String TABLE_SEARCH_EtEquiMptt = "EtEquiMptt";
    private static final String KEY_SEARCH_EtEquiMptt_id = "id";
    private static final String KEY_SEARCH_EtEquiMptt_Tplnr = "Tplnr";
    private static final String KEY_SEARCH_EtEquiMptt_Strno = "Strno";
    private static final String KEY_SEARCH_EtEquiMptt_Equnr = "Equnr";
    private static final String KEY_SEARCH_EtEquiMptt_Point = "Point";
    private static final String KEY_SEARCH_EtEquiMptt_Mpobj = "Mpobj";
    private static final String KEY_SEARCH_EtEquiMptt_Mpobt = "Mpobt";
    private static final String KEY_SEARCH_EtEquiMptt_Psort = "Psort";
    private static final String KEY_SEARCH_EtEquiMptt_Pttxt = "Pttxt";
    private static final String KEY_SEARCH_EtEquiMptt_Mptyp = "Mptyp";
    private static final String KEY_SEARCH_EtEquiMptt_Atinn = "Atinn";
    private static final String KEY_SEARCH_EtEquiMptt_Atbez = "Atbez";
    private static final String KEY_SEARCH_EtEquiMptt_Mrngu = "Mrngu";
    private static final String KEY_SEARCH_EtEquiMptt_Msehl = "Msehl";
    private static final String KEY_SEARCH_EtEquiMptt_Desir = "Desir";
    private static final String KEY_SEARCH_EtEquiMptt_Mrmin = "Mrmin";
    private static final String KEY_SEARCH_EtEquiMptt_Mrmax = "Mrmax";
    private static final String KEY_SEARCH_EtEquiMptt_Cdsuf = "Cdsuf";
    private static final String KEY_SEARCH_EtEquiMptt_Codct = "Codct";
    private static final String KEY_SEARCH_EtEquiMptt_Codgr = "Codgr";
    /*GET_SEARCH_EtEquiMptt and Fields Names*/

    public static String Get_Mpoint_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /*GET_SEARCH_EtEquiMptt and Fields Names */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_EtEquiMptt);
            String CREATE_TABLE_SEARCH_EtEquiMptt = "CREATE TABLE IF NOT EXISTS "
                    + TABLE_SEARCH_EtEquiMptt + ""
                    + "( "
                    + KEY_SEARCH_EtEquiMptt_id + " INTEGER PRIMARY KEY,"
                    + KEY_SEARCH_EtEquiMptt_Tplnr + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Strno + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Equnr + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Point + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mpobj + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mpobt + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Psort + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Pttxt + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mptyp + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Atinn + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Atbez + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrngu + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Msehl + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Desir + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrmin + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrmax + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Cdsuf + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Codct + " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Codgr + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_SEARCH_EtEquiMptt);
            /*GET_SEARCH_EtEquiMptt and Fields Names */
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
                    new String[]{"MP", "RD", webservice_type});
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
            Call<MeasurementPoint_SER> call = service.getMPointDetails(url_link, basic, map);
            Response<MeasurementPoint_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_mpoint_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {

                        /*EquiMPs*/
                        if (response.body().getD().getResults().get(0).getEquiMPs() != null) {
                            if (response.body().getD().getResults().get(0).getEquiMPs().getResults() != null
                                    && response.body().getD().getResults().get(0).getEquiMPs().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (MeasurementPoint_SER.EquiMPs_Result eMP : response.body().getD().getResults().get(0).getEquiMPs().getResults()) {
                                    values.put("Tplnr, ", eMP.getTplnr());
                                    values.put("Strno", eMP.getStrno());
                                    values.put("Equnr", eMP.getEqunr());
                                    values.put("Point", eMP.getPoint());
                                    values.put("Mpobj", eMP.getMpobj());
                                    values.put("Mpobt", eMP.getMpobt());
                                    values.put("Psort", eMP.getPsort());
                                    values.put("Pttxt", eMP.getPttxt());
                                    values.put("Mptyp", eMP.getMptyp());
                                    values.put("Atinn", eMP.getAtinn());
                                    values.put("Atbez", eMP.getAtbez());
                                    values.put("Mrngu", eMP.getMrngu());
                                    values.put("Msehl", eMP.getMsehl());
                                    values.put("Desir", eMP.getDesir());
                                    values.put("Mrmin", eMP.getMrmin());
                                    values.put("Mrmax", eMP.getMrmax());
                                    values.put("Cdsuf", eMP.getCdsuf());
                                    values.put("Codct", eMP.getCodct());
                                    values.put("Codgr", eMP.getCodgr());
                                    App_db.insert("EtEquiMptt", null, values);
                                }
                            }
                        }
                        /*MeasurementPoint_SER.EquiMPs equiMPs = results.get(0).getEquiMPs();
                        if (equiMPs != null) {
                            List<MeasurementPoint_SER.EquiMPs_Result> equiMPsResults = equiMPs.getResults();
                            if (equiMPsResults != null && equiMPsResults.size() > 0) {
                                String EtEquiMptt_sql = "Insert into EtEquiMptt (Tplnr, Strno, " +
                                        "Equnr, Point, Mpobj, Mpobt, Psort, Pttxt, Mptyp, Atinn, " +
                                        "Atbez, Mrngu, Msehl, Desir, Mrmin, Mrmax, Cdsuf, Codct, " +
                                        "Codgr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtEquiMptt_statement = App_db.compileStatement(EtEquiMptt_sql);
                                EtEquiMptt_statement.clearBindings();
                                for (MeasurementPoint_SER.EquiMPs_Result eM : equiMPsResults) {
                                    EtEquiMptt_statement.bindString(1, c_e.check_empty(eM.getTplnr()));
                                    EtEquiMptt_statement.bindString(2, c_e.check_empty(eM.getStrno()));
                                    EtEquiMptt_statement.bindString(3, c_e.check_empty(eM.getEqunr()));
                                    EtEquiMptt_statement.bindString(4, c_e.check_empty(eM.getPoint()));
                                    EtEquiMptt_statement.bindString(5, c_e.check_empty(eM.getMpobj()));
                                    EtEquiMptt_statement.bindString(6, c_e.check_empty(eM.getMpobt()));
                                    EtEquiMptt_statement.bindString(7, c_e.check_empty(eM.getPsort()));
                                    EtEquiMptt_statement.bindString(8, c_e.check_empty(eM.getPttxt()));
                                    EtEquiMptt_statement.bindString(9, c_e.check_empty(eM.getMptyp()));
                                    EtEquiMptt_statement.bindString(10, c_e.check_empty(eM.getAtinn()));
                                    EtEquiMptt_statement.bindString(11, c_e.check_empty(eM.getAtbez()));
                                    EtEquiMptt_statement.bindString(12, c_e.check_empty(eM.getMrngu()));
                                    EtEquiMptt_statement.bindString(13, c_e.check_empty(eM.getMsehl()));
                                    EtEquiMptt_statement.bindString(14, c_e.check_empty(eM.getDesir()));
                                    EtEquiMptt_statement.bindString(15, c_e.check_empty(eM.getMrmin()));
                                    EtEquiMptt_statement.bindString(16, c_e.check_empty(eM.getMrmax()));
                                    EtEquiMptt_statement.bindString(17, c_e.check_empty(eM.getCdsuf()));
                                    EtEquiMptt_statement.bindString(18, c_e.check_empty(eM.getCodct()));
                                    EtEquiMptt_statement.bindString(19, c_e.check_empty(eM.getCodgr()));
                                    EtEquiMptt_statement.execute();
                                }
                            }
                        }
                    }*/
                    App_db.setTransactionSuccessful();

                    Get_Response = "success";
                }finally {
                        App_db.endTransaction();
                    }
                    }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
