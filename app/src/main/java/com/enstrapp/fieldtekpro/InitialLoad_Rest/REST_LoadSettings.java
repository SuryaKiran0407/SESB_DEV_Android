package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class REST_LoadSettings
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static String Get_LoadSettings_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"D1", "F4", webservice_type});
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

            Rest_Model_Login_Device modelLoginDeviceRest = new Rest_Model_Login_Device();
            modelLoginDeviceRest.setMUSER(username.toUpperCase().toString());
            modelLoginDeviceRest.setDEVICEID(device_id);
            modelLoginDeviceRest.setDEVICESNO(device_serial_number);
            modelLoginDeviceRest.setUDID(device_uuid);

            Rest_Model_Login modelLoginRest = new Rest_Model_Login();
            modelLoginRest.setIv_transmit_type("LOAD");
            modelLoginRest.setIv_user(username);
            modelLoginRest.setIs_device(modelLoginDeviceRest);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_LoadSettings_SER> call = service.postLoadSettingsDetails(url_link, basic, modelLoginRest);
            Response<REST_LoadSettings_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_LoadSettings_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    if(transmit_type.equalsIgnoreCase("LOAD"))
                    {
                        String response_data = new Gson().toJson(response.body().getESILOAD());
                        JSONObject response_data_jsonObject = new JSONObject(response_data);
                        String vhlp_status = response_data_jsonObject.optString("VHLP");
                        if(vhlp_status.equalsIgnoreCase("true"))
                       {
                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                       }
                       else
                        {
                           FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");
                        }
                        String Floc_status = response_data_jsonObject.optString("FLOC");
                        if(Floc_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");
                        }
                        String Equi_status = response_data_jsonObject.optString("EQUI");
                        if(Equi_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");
                        }
                        String Mat_status = response_data_jsonObject.optString("MAT");
                        if(Mat_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");
                        }
                        String Stock_status = response_data_jsonObject.optString("STOCK");
                        if(Stock_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");
                        }
                        String Ebom_status = response_data_jsonObject.optString("EBOM");
                        if(Ebom_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");
                        }
                        String Dnot_status = response_data_jsonObject.optString("DNOT");
                        if(Dnot_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");
                        }
                        String Dord_status = response_data_jsonObject.optString("DORD");
                        if(Dord_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");
                        }
                        String Auth_status = response_data_jsonObject.optString("AUTH");
                        if(Auth_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");
                        }
                        String Sett_status = response_data_jsonObject.optString("SETT");
                        if(Sett_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");
                        }
                        String Nfcd_status = response_data_jsonObject.optString("NFCD");
                        if(Nfcd_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                        }
                        FieldTekPro_SharedPrefeditor.commit();
                        Get_Response = "success";
                    }
                    else
                    {
                        String response_data = new Gson().toJson(response.body().getESREFRESH());
                        JSONObject response_data_jsonObject = new JSONObject(response_data);
                        String vhlp_status = response_data_jsonObject.optString("VHLP");
                        if(vhlp_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");
                        }
                        String Floc_status = response_data_jsonObject.optString("FLOC");
                        if(Floc_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");
                        }
                        String Equi_status = response_data_jsonObject.optString("EQUI");
                        if(Equi_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");
                        }
                        String Mat_status = response_data_jsonObject.optString("MAT");
                        if(Mat_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");
                        }
                        String Stock_status = response_data_jsonObject.optString("STOCK");
                        if(Stock_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");
                        }
                        String Ebom_status = response_data_jsonObject.optString("EBOM");
                        if(Ebom_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");
                        }
                        String Dnot_status = response_data_jsonObject.optString("DNOT");
                        if(Dnot_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");
                        }
                        String Dord_status = response_data_jsonObject.optString("DORD");
                        if(Dord_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");
                        }
                        String Auth_status = response_data_jsonObject.optString("AUTH");
                        if(Auth_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");
                        }
                        String Sett_status = response_data_jsonObject.optString("SETT");
                        if(Sett_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");
                        }
                        String Nfcd_status = response_data_jsonObject.optString("NFCD");
                        if(Nfcd_status.equalsIgnoreCase("true"))
                        {
                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                        }
                        else
                        {
                            FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                        }
                        FieldTekPro_SharedPrefeditor.commit();
                        Get_Response = "success";
                    }
                }
                else
                {
                    Get_Response = "exception";
                }
            }
            else
            {
                Get_Response = "exception";
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
