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
import com.enstrapp.fieldtekpro.R;
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

public class BOM_Reservation
{

    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String post_bom_reservation(Context activity, String BOM, String Component, String Component_text, String quantity, String Unit, String Plant, String storage_location, String Date, String movement_type_id, String costcenter_id, String order_number)
    {
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            token = FieldTekPro_SharedPref.getString("token",null);
            cookie = FieldTekPro_SharedPref.getString("cookie",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"R", "R", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = ""+ Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32)| device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);

            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", token);
            map.put("Cookie", cookie);
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            /*Adding BOM Header to Arraylist*/
            Model_BOM_IsReservHeader bom_isReservHeader = new Model_BOM_IsReservHeader();
            bom_isReservHeader.setMovementType(movement_type_id);
            bom_isReservHeader.setPlant(Plant);
            bom_isReservHeader.setCostCenter(costcenter_id);
            bom_isReservHeader.setOrderNo(order_number);
            ArrayList<Model_BOM_IsReservHeader> bomheaderArrayList = new ArrayList<>();
            bomheaderArrayList.add(bom_isReservHeader);
            /*Adding BOM Header to Arraylist*/

            /*Adding BOM Components to Arraylist*/
            Model_BOM_ItReservComp bom_itReservComp = new Model_BOM_ItReservComp();
            bom_itReservComp.setBomComponent(Component);
            bom_itReservComp.setQuantity(quantity);
            bom_itReservComp.setUnit(Unit);
            bom_itReservComp.setReqmtDate(Date);
            bom_itReservComp.setStoreLoc(storage_location);
            ArrayList<Model_BOM_ItReservComp> bom_itReservComps_array = new ArrayList<>();
            bom_itReservComps_array.add(bom_itReservComp);
            /*Adding BOM Components to Arraylist*/

            ArrayList EtMessageArrayList = new ArrayList<>();

            Model_BOM_Reservation bom_reservation = new Model_BOM_Reservation();
            bom_reservation.setMuser(username.toUpperCase().toString());
            bom_reservation.setDeviceid(device_id);
            bom_reservation.setDevicesno(device_serial_number);
            bom_reservation.setUdid(device_uuid);
            bom_reservation.setIvTransmitType("LOAD");
            bom_reservation.setIvCommit(true);
            bom_reservation.setIsReservHeader(bomheaderArrayList);
            bom_reservation.setItReservComp(bom_itReservComps_array);
            bom_reservation.setEtMessage(EtMessageArrayList);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<SER_BOM_Reservation> call = service.postBOMReservation(url_link, basic, map, bom_reservation);
            Response<SER_BOM_Reservation> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_MAC_code",response_status_code+"...");
            if(response_status_code == 201)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    SER_BOM_Reservation rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getEtMessage().getResults());
                    JSONArray response_JsonArray = new JSONArray(response_data);
                    StringBuilder stringBuilder = new StringBuilder();
                    if(response_JsonArray.length() > 0)
                    {
                        for(int i = 0; i < response_JsonArray.length(); i++)
                        {
                            String message = response_JsonArray.getJSONObject(i).optString("Message");
                            String Resnum = response_JsonArray.getJSONObject(i).optString("Resnum");
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
