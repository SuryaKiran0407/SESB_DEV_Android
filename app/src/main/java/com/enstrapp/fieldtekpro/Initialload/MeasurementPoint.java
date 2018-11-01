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

public class MeasurementPoint {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

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
            String CREATE_TABLE_SEARCH_EtEquiMptt = "CREATE TABLE IF NOT EXISTS " + TABLE_SEARCH_EtEquiMptt + ""
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
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"MP", "RD", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
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
            Call<MeasurementPoint_SER> call = service.getMPointDetails(url_link, basic, map);
            Response<MeasurementPoint_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_mpoint_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        MeasurementPoint_SER rs = response.body();
                        String response_data = new Gson().toJson(rs.getD().getResults().get(0).getEquiMPs().getResults());
                        if (response_data != null && !response_data.equals("") && !response_data.equals("null")) {
                            App_db.beginTransaction();
                            JSONArray response_data_jsonArray = new JSONArray(response_data);
                            if (response_data_jsonArray.length() > 0) {
                                String EtEquiMptt_sql = "Insert into EtEquiMptt (Tplnr, Strno, Equnr, Point, Mpobj, Mpobt, Psort, Pttxt, Mptyp, Atinn, Atbez, Mrngu, Msehl, Desir, Mrmin, Mrmax, Cdsuf, Codct, Codgr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtEquiMptt_statement = App_db.compileStatement(EtEquiMptt_sql);
                                EtEquiMptt_statement.clearBindings();
                                for (int j = 0; j < response_data_jsonArray.length(); j++) {
                                    EtEquiMptt_statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Tplnr"));
                                    EtEquiMptt_statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Strno"));
                                    EtEquiMptt_statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Equnr"));
                                    EtEquiMptt_statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Point"));
                                    EtEquiMptt_statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Mpobj"));
                                    EtEquiMptt_statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Mpobt"));
                                    EtEquiMptt_statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Psort"));
                                    EtEquiMptt_statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Pttxt"));
                                    EtEquiMptt_statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Mptyp"));
                                    EtEquiMptt_statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Atinn"));
                                    EtEquiMptt_statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Atbez"));
                                    EtEquiMptt_statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Mrngu"));
                                    EtEquiMptt_statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Msehl"));
                                    EtEquiMptt_statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Desir"));
                                    EtEquiMptt_statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Mrmin"));
                                    EtEquiMptt_statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Mrmax"));
                                    EtEquiMptt_statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("Cdsuf"));
                                    EtEquiMptt_statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("Codct"));
                                    EtEquiMptt_statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Codgr"));
                                    EtEquiMptt_statement.execute();
                                }
                            }
                            App_db.setTransactionSuccessful();
                            App_db.endTransaction();
                            Get_Response = "success";
                        }
                    } catch (Exception e) {
                        Get_Response = "exception";
                    }
                }
            } else {
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }

}
