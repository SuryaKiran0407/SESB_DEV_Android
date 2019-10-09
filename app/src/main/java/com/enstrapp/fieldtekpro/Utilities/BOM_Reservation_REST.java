package com.enstrapp.fieldtekpro.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;

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

public class BOM_Reservation_REST {

    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String post_bom_reservation(Context activity, String BOM, String Component, String Component_text, String quantity, String Unit, String Plant, String storage_location, String Date, String movement_type_id, String costcenter_id, String order_number, String Val_type, String Batch) {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            token = FieldTekPro_SharedPref.getString("token", null);
            cookie = FieldTekPro_SharedPref.getString("cookie", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R", "R", webservice_type});
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
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            /*Adding BOM Header to Arraylist*/
            is_reserv_header bom_isReservHeader = new is_reserv_header();
            bom_isReservHeader.setMOVEMENTTYPE(movement_type_id);
            bom_isReservHeader.setPLANT(Plant);
            bom_isReservHeader.setCOSTCENTER(costcenter_id);
            bom_isReservHeader.setORDERNO(order_number);
            /*Adding BOM Header to Arraylist*/

            /*Adding BOM Components to Arraylist*/
            is_reserv_comp bom_itReservComp = new is_reserv_comp();
            bom_itReservComp.setBOMCOMPONENT(Component);
            bom_itReservComp.setQUANTITY(quantity);
            bom_itReservComp.setUNIT(Unit);
            bom_itReservComp.setREQMTDATE(Date);
            bom_itReservComp.setSTORELOC(storage_location);
            bom_itReservComp.setBwtar(Val_type);
            bom_itReservComp.setCharg(Batch);
            ArrayList<is_reserv_comp> bom_itReservComps_array = new ArrayList<>();
            bom_itReservComps_array.add(bom_itReservComp);
            /*Adding BOM Components to Arraylist*/


            is_device is_device= new is_device();
            is_device.setMUSER(username.toUpperCase().toString());
            is_device.setDEVICEID(device_id);
            is_device.setDEVICESNO(device_serial_number);
            is_device.setUDID(device_uuid);
            is_device.setIVTRANSMITTYPE("LOAD");
            is_device.setIVCOMMIT("X");
            is_device.setERROR("");

            Model_BOM_RESV_REST bom_reservation = new Model_BOM_RESV_REST();
            bom_reservation.setMuser(username.toUpperCase().toString());
            bom_reservation.setDeviceid(device_id);
            bom_reservation.setDevicesno(device_serial_number);
            bom_reservation.setUdid(device_uuid);
            bom_reservation.setIvTransmitType("LOAD");
            bom_reservation.setIvCommit("X");
            bom_reservation.setIs_reserv_header(bom_isReservHeader);
            bom_reservation.setIt_reserv_comp(bom_itReservComps_array);
            bom_reservation.setIs_device(is_device);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<SER_BOM_Reservation_REST> call = service.postBOMReservation(url_link, basic, map, bom_reservation);
            Response<SER_BOM_Reservation_REST> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_BOM_RESERV_code", response_status_code + "...");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(response.body().geteTMESSAGE());
                    JSONArray response_JsonArray = new JSONArray(response_data);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (response_JsonArray.length() > 0)
                    {
                        for (int i = 0; i < response_JsonArray.length(); i++)
                        {
                            String message = response_JsonArray.getJSONObject(i).optString("MESSAGE");
                            //String Resnum = response_JsonArray.getJSONObject(i).optString("Resnum");
                            stringBuilder.append(message);
                        }
                        Get_Response = stringBuilder.toString();
                    }
                    else
                    {
                        Get_Response = "nodata";
                    }
                    /*Converting GSON Response to JSON Data for Parsing*/
                }
            }
        }
        catch (Exception ex)
        {
            Get_Response = "exception";
        }
        finally
        {
        }
        return Get_Response;
    }
}
