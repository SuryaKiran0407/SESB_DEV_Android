package com.enstrapp.fieldtekpro.PermitList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Permit_Isolation {
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";

    public static String Post_Data(Activity activity, ArrayList<Model_Permit_Isolation_ItWcmWcagn> permit_list, Set<HashMap<String, String>> list_uuid) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            token = app_sharedpreferences.getString("token", null);
            cookie = app_sharedpreferences.getString("cookie", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D5", "PS", webservice_type});
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
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);

            /*For Send Data in POST Header*/
            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", token);
            map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");
            /*For Send Data in POST Header*/

            ArrayList EtMessages_ArrayList = new ArrayList<>();

            ArrayList EtWcmWcagns_ArrayList = new ArrayList<>();

            Model_Permit_Isolation model_permit_isolation = new Model_Permit_Isolation();
            model_permit_isolation.setMuser(username.toUpperCase().toString());
            model_permit_isolation.setDeviceid(device_id);
            model_permit_isolation.setDevicesno(device_serial_number);
            model_permit_isolation.setUdid(device_uuid);
            model_permit_isolation.setIvCommit(true);
            model_permit_isolation.setOperation("ISORD");
            model_permit_isolation.setItWcmWcagns(permit_list);
            model_permit_isolation.setEtWcmWcagns(EtWcmWcagns_ArrayList);
            model_permit_isolation.setEtMessages(EtMessages_ArrayList);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.postpermitisolationData(url_link, model_permit_isolation, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    Orders_SER rs = response.body();
                    String response_data = new Gson().toJson(rs.getD());
                    try {
                        StringBuffer message = new StringBuffer();
                        JSONObject jsonObject = new JSONObject(response_data);
                        if (jsonObject.has("EtMessages")) {
                            String EtMessages_response = new Gson().toJson(rs.getD().getEtMessages().getResults());
                            JSONArray jsonArray = new JSONArray(EtMessages_response);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                message.append(jsonArray.getJSONObject(j).optString("Message"));
                            }
                        }


                        if (jsonObject.has("EtWcmWcagns")) {
                            try {
                                String EtWcmWcagns_response_data = new Gson().toJson(rs.getD().getEtWcmWcagns().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWcagns_response_data);
                                if (jsonArray.length() > 0) {
                                    for (HashMap<String, String> each : list_uuid) {
                                        String aufnr = each.get("Aufnr");
                                        App_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{aufnr});
                                    }
                                    App_db.beginTransaction();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String aufnr = jsonArray.getJSONObject(j).optString("Aufnr");
                                        for (HashMap<String, String> each : list_uuid) {
                                            String old_aufnr = each.get("Aufnr");
                                            if (aufnr.equals(old_aufnr)) {
                                                uuid = each.get("uuid");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            String EtWcmWcagns_sql = "Insert into EtWcmWcagns (UUID, Aufnr, Objnr, Counter, Objart, Objtyp, Pmsog, Gntxt, Geniakt, Genvname, Action, Werks, Crname, Hilvl, Procflg, Direction, Copyflg, Mandflg, Deacflg, Status, Asgnflg, Autoflg, Agent, Valflg, Wcmuse, Gendatum, Gentime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWcagns_statement = App_db.compileStatement(EtWcmWcagns_sql);
                                            EtWcmWcagns_statement.clearBindings();
                                            EtWcmWcagns_statement.bindString(1, uuid);
                                            EtWcmWcagns_statement.bindString(2, jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtWcmWcagns_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWcagns_statement.bindString(4, jsonArray.getJSONObject(j).optString("Counter"));
                                            EtWcmWcagns_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWcagns_statement.bindString(6, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWcagns_statement.bindString(7, jsonArray.getJSONObject(j).optString("Pmsog"));
                                            EtWcmWcagns_statement.bindString(8, jsonArray.getJSONObject(j).optString("Gntxt"));
                                            EtWcmWcagns_statement.bindString(9, jsonArray.getJSONObject(j).optString("Geniakt"));
                                            EtWcmWcagns_statement.bindString(10, jsonArray.getJSONObject(j).optString("Genvname"));
                                            EtWcmWcagns_statement.bindString(11, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWcagns_statement.bindString(12, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtWcmWcagns_statement.bindString(13, jsonArray.getJSONObject(j).optString("Crname"));
                                            EtWcmWcagns_statement.bindString(14, jsonArray.getJSONObject(j).optString("Hilvl"));
                                            EtWcmWcagns_statement.bindString(15, jsonArray.getJSONObject(j).optString("Procflg"));
                                            EtWcmWcagns_statement.bindString(16, jsonArray.getJSONObject(j).optString("Direction"));
                                            EtWcmWcagns_statement.bindString(17, jsonArray.getJSONObject(j).optString("Copyflg"));
                                            EtWcmWcagns_statement.bindString(18, jsonArray.getJSONObject(j).optString("Mandflg"));
                                            EtWcmWcagns_statement.bindString(19, jsonArray.getJSONObject(j).optString("Deacflg"));
                                            EtWcmWcagns_statement.bindString(20, jsonArray.getJSONObject(j).optString("Status"));
                                            EtWcmWcagns_statement.bindString(21, jsonArray.getJSONObject(j).optString("Asgnflg"));
                                            EtWcmWcagns_statement.bindString(22, jsonArray.getJSONObject(j).optString("Autoflg"));
                                            EtWcmWcagns_statement.bindString(23, jsonArray.getJSONObject(j).optString("Agent"));
                                            EtWcmWcagns_statement.bindString(24, jsonArray.getJSONObject(j).optString("Valflg"));
                                            EtWcmWcagns_statement.bindString(25, jsonArray.getJSONObject(j).optString("Wcmuse"));
                                            EtWcmWcagns_statement.bindString(26, jsonArray.getJSONObject(j).optString("Gendatum"));
                                            EtWcmWcagns_statement.bindString(27, jsonArray.getJSONObject(j).optString("Gentime"));
                                            EtWcmWcagns_statement.execute();
                                        }
                                    }
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();
                                }
                            } catch (Exception e) {
                            }
                        }
                        Get_Response = message.toString();
                    } catch (Exception e) {
                    }
                }
            } else {
            }
        } catch (Exception e) {
            Get_Response = activity.getString(R.string.permit_unable);
        } finally {
        }
        return Get_Response;
    }


}
