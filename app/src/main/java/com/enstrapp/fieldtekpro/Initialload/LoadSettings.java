package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class LoadSettings {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    public static String Get_LoadSettings_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
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
                    new String[]{"D1", "F4", webservice_type});
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
            Call<LoadSettings_SER> call = service.getLoadSettingsDetails(url_link, basic, map);
            Response<LoadSettings_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_LoadSettings_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LoadSettings_SER.Result> results = response.body().getD().getResults();
                    if (results != null && results.size() > 0) {
                        if (transmit_type.equalsIgnoreCase("LOAD")) {
                            LoadSettings_SER.EsIload esIload = results.get(0).getEsIload();
                            if (esIload != null) {
                                List<LoadSettings_SER.Result_> results1 = esIload.getResults();
                                if (results1 != null && results1.size() > 0) {
                                    for (LoadSettings_SER.Result_ rs : results1) {
                                        if (c_e.check_empty(rs.getVhlp()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");

                                        if (c_e.check_empty(rs.getFloc()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");

                                        if (c_e.check_empty(rs.getEqui()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");

                                        if (c_e.check_empty(rs.getMat()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");

                                        if (c_e.check_empty(rs.getStock()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");

                                        if (c_e.check_empty(rs.getEbom()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");

                                        if (c_e.check_empty(rs.getDnot()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");

                                        if (c_e.check_empty(rs.getDord()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");

                                        if (c_e.check_empty(rs.getAuth()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");

                                        if (c_e.check_empty(rs.getSett()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");

                                        if (c_e.check_empty(rs.getNfcd()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                                    }
                                    FieldTekPro_SharedPrefeditor.commit();
                                    Get_Response = "success";
                                } else {
                                    Get_Response = "no data";
                                }
                            } else {
                                Get_Response = "no data";
                            }
                        } else {
                            LoadSettings_SER.EsRefresh esRefresh = results.get(0).getEsRefresh();
                            if (esRefresh != null) {
                                List<LoadSettings_SER.Result__> results1 = esRefresh.getResults();
                                if (results1 != null && results1.size() > 0) {
                                    for (LoadSettings_SER.Result__ rs : results1) {
                                        if (c_e.check_empty(rs.getVhlp()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");

                                        if (c_e.check_empty(rs.getFloc()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");

                                        if (c_e.check_empty(rs.getEqui()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");

                                        if (c_e.check_empty(rs.getMat()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");

                                        if (c_e.check_empty(rs.getStock()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");

                                        if (c_e.check_empty(rs.getEbom()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");

                                        if (c_e.check_empty(rs.getDnot()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");

                                        if (c_e.check_empty(rs.getDord()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");

                                        if (c_e.check_empty(rs.getAuth()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");

                                        if (c_e.check_empty(rs.getSett()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");

                                        if (c_e.check_empty(rs.getNfcd()).equals("X"))
                                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                                        else
                                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                                    }
                                    FieldTekPro_SharedPrefeditor.commit();
                                    Get_Response = "success";
                                } else {
                                    Get_Response = "no data";
                                }
                            } else {
                                Get_Response = "no data";
                            }
                        }
                    } else {
                        Get_Response = "exception";
                    }
                } else {
                    Get_Response = "exception";
                }
            } else {
                Get_Response = "exception";
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
