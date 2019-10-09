package com.enstrapp.fieldtekpro.orders;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Insert_Orders_Data;
import com.enstrapp.fieldtekpro.InitialLoad_Rest.REST_Orders_SER;
import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_RELEASE_REST;

import java.util.ArrayList;
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

public class Order_Rel_REST
{

    private static String password = "", url_link = "", username = "", device_serial_number = "",device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static boolean success = false;

    public static String Get_Data(Context activity, String transmit_type, String ivCommit,String operation, String orderId)
    {
        try
        {
            Get_Response = "";
            success = false;
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            FieldTekPro_SharedPref = activity .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +" ? and Zactivity = ? and Endpoint = ?", new String[]{"W", "RL", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            device_id = Settings.Secure.getString(activity.getContentResolver(),Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            String URL = activity.getString(R.string.ip_address);

            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");

            Model_Order_RELEASE_REST.IsDevice isDevice = new Model_Order_RELEASE_REST.IsDevice();
            isDevice.setMUSER(username.toUpperCase().toString());
            isDevice.setDEVICEID(device_id);
            isDevice.setDEVICESNO(device_serial_number);
            isDevice.setUDID(device_uuid);
            isDevice.setIV_TRANSMIT_TYPE("LOAD");
            isDevice.setIV_COMMIT(true);

            Model_Order_RELEASE_REST model_order_release_rest = new Model_Order_RELEASE_REST();
            model_order_release_rest.setIsDevice(isDevice);
            model_order_release_rest.setIv_aufnr(orderId);
            model_order_release_rest.setIV_TRANSMIT_TYPE("LOAD");
            model_order_release_rest.setIV_COMMIT(true);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
            Call<REST_Orders_SER> call = service.getORDERDetails(url_link, model_order_release_rest, basic, map);
            Response<REST_Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    REST_Orders_SER rs = response.body();
                    String message = rs.geteVMESSAGE();

                    if(message.toString().startsWith("S"))
                    {
                        success = true;
                    }
                    else
                    {
                        success = false;
                    }

                    if (success)
                    {
                        String orders_insert_status = REST_Insert_Orders_Data.Insert_Order_Data(activity, rs, orderId, "CRORD");
                        if (orders_insert_status.equalsIgnoreCase("Success"))
                        {
                            Get_Response = message.toString();
                        }
                        else
                        {
                            Get_Response = "Exception";
                        }
                    }
                    else
                    {
                        Get_Response = message.toString();
                    }
                }
                else
                {
                    Get_Response = activity.getString(R.string.relordr_unable);
                }
            }
            else
            {
                Get_Response = activity.getString(R.string.relordr_unable);
            }
        }
        catch (Exception e)
        {
            Get_Response = activity.getString(R.string.relordr_unable);
        }
        return Get_Response;
    }
}
