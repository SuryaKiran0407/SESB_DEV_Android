package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.Utilities.ItMatnrPost;
import com.enstrapp.fieldtekpro.Utilities.MAC_Model1;
import com.enstrapp.fieldtekpro.Utilities.Material_Availability_Check_SER;
import com.google.gson.Gson;

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

public class Material_Availability_Check {

    private static String cookie = "", token = "", password = "", url_link = "", username = "",
            device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String material_availability_check(Activity activity, String BOM,
                                                     String Component, String Component_text,
                                                     String quantity, String date, String Plant,
                                                     String storage_location) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            token = FieldTekPro_SharedPref.getString("token", null);
            cookie = FieldTekPro_SharedPref.getString("cookie", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",
                    null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype " +
                            "= ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"C6", "MC", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);

            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", token);
            map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            ItMatnrPost itMatnrPost = new ItMatnrPost();
            itMatnrPost.setMatnr(Component);
            itMatnrPost.setMaktx(Component_text);
            itMatnrPost.setWerks(Plant);
            itMatnrPost.setLgort(storage_location);
            itMatnrPost.setRdate(date);
            itMatnrPost.setErfmg(quantity);

            ArrayList<ItMatnrPost> headerArrayList = new ArrayList<>();
            headerArrayList.add(itMatnrPost);

            ArrayList headerArrayList1 = new ArrayList<>();

            MAC_Model1 mac_model1 = new MAC_Model1();
            mac_model1.setMuser(username);
            mac_model1.setDeviceid(device_id);
            mac_model1.setDevicesno(device_serial_number);
            mac_model1.setUdid(device_uuid);
            mac_model1.setItMatnrPost(headerArrayList);
            mac_model1.setEvAvailable(headerArrayList1);

            //Material_Availability_Check_Model material_availability_check_model = new Material_Availability_Check_Model(mac_model1);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120000, TimeUnit.SECONDS)
                    .writeTimeout(120000, TimeUnit.SECONDS)
                    .readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),
                    Base64.NO_WRAP);
            Call<Material_Availability_Check_SER> call = service
                    .getMaterial_Availability_Check(url_link, basic, map, mac_model1);
            Response<Material_Availability_Check_SER> response = call.execute();
            int response_status_code = response.code();

            if (response_status_code == 201) {
                if (response.isSuccessful() && response.body() != null) {
                    /*Reading Response Data and Parsing to Serializable*/
                    Material_Availability_Check_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getEvAvailable()
                            .getResults().get(0).getMessage());
                    Get_Response = response_data;
                    /*Converting GSON Response to JSON Data for Parsing*/
                }
            } else {
                Get_Response = "exception";
            }

        } catch (Exception ex) {
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }
}
