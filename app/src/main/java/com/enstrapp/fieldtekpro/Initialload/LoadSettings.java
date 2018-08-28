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

public class LoadSettings
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
            Call<LoadSettings_SER> call = service.getLoadSettingsDetails(url_link, basic, map);
            Response<LoadSettings_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_LoadSettings_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    LoadSettings_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                    /*Converting GSON Response to JSON Data for Parsing*/

                    /*Converting Response JSON Data to JSONArray for Reading*/
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/

                    /*Reading Data by using FOR Loop*/
                    for(int i = 0; i < response_data_jsonArray.length(); i++)
                    {
                        /*Reading Data by using FOR Loop*/
                        JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                        /*Checking transmit type to read EsIload or EsRefresh*/
                        if(transmit_type.equalsIgnoreCase("LOAD"))
                        {
                            /*if transmit_type is EsIload and Reading all the values*/
                            if(jsonObject.has("EsIload"))
                            {
                                String EsIload_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEsIload().getResults());
                                if (EsIload_response_data != null && !EsIload_response_data.equals("") && !EsIload_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EsIload_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String vhlp_status = jsonArray.getJSONObject(j).optString("Vhlp");
                                            if(vhlp_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");
                                            }
                                            String Floc_status = jsonArray.getJSONObject(j).optString("Floc");
                                            if(Floc_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");
                                            }
                                            String Equi_status = jsonArray.getJSONObject(j).optString("Equi");
                                            if(Equi_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");
                                            }
                                            String Mat_status = jsonArray.getJSONObject(j).optString("Mat");
                                            if(Mat_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");
                                            }
                                            String Stock_status = jsonArray.getJSONObject(j).optString("Stock");
                                            if(Stock_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");
                                            }
                                            String Ebom_status = jsonArray.getJSONObject(j).optString("Ebom");
                                            if(Ebom_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");
                                            }
                                            String Dnot_status = jsonArray.getJSONObject(j).optString("Dnot");
                                            if(Dnot_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");
                                            }
                                            String Dord_status = jsonArray.getJSONObject(j).optString("Dord");
                                            if(Dord_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");
                                            }
                                            String Auth_status = jsonArray.getJSONObject(j).optString("Auth");
                                            if(Auth_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");
                                            }
                                            String Sett_status = jsonArray.getJSONObject(j).optString("Sett");
                                            if(Sett_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");
                                            }
                                            String Nfcd_status = jsonArray.getJSONObject(j).optString("Nfcd");
                                            if(Nfcd_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                                            }
                                        }
                                        FieldTekPro_SharedPrefeditor.commit();
                                        Get_Response = "success";
                                    }
                                }
                                else
                                {
                                    Get_Response = "no data";
                                }
                            }
                            else
                            {
                                Get_Response = "no data";
                            }
                            /*if transmit_type is EsIload and Reading all the values*/
                        }
                        else
                        {
                            /*if transmit_type is EsRefresh and Reading all the values*/
                            if(jsonObject.has("EsRefresh"))
                            {
                                String EsRefresh_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEsRefresh().getResults());
                                Log.v("kiran_EsRefresh_response_data",EsRefresh_response_data+".....");
                                if (EsRefresh_response_data != null && !EsRefresh_response_data.equals("") && !EsRefresh_response_data.equals("null"))
                                {
                                    JSONArray jsonArray = new JSONArray(EsRefresh_response_data);
                                    if(jsonArray.length() > 0)
                                    {
                                        for(int j = 0; j < jsonArray.length(); j++)
                                        {
                                            String vhlp_status = jsonArray.getJSONObject(j).optString("Vhlp");
                                            if(vhlp_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Vhlp_Load_status", "");
                                            }
                                            String Floc_status = jsonArray.getJSONObject(j).optString("Floc");
                                            if(Floc_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Floc_Load_status", "");
                                            }
                                            String Equi_status = jsonArray.getJSONObject(j).optString("Equi");
                                            if(Equi_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Equi_Load_status", "");
                                            }
                                            String Mat_status = jsonArray.getJSONObject(j).optString("Mat");
                                            if(Mat_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Mat_Load_status", "");
                                            }
                                            String Stock_status = jsonArray.getJSONObject(j).optString("Stock");
                                            if(Stock_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Stock_Load_status", "");
                                            }
                                            String Ebom_status = jsonArray.getJSONObject(j).optString("Ebom");
                                            if(Ebom_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Ebom_Load_status", "");
                                            }
                                            String Dnot_status = jsonArray.getJSONObject(j).optString("Dnot");
                                            if(Dnot_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dnot_Load_status", "");
                                            }
                                            String Dord_status = jsonArray.getJSONObject(j).optString("Dord");
                                            if(Dord_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Dord_Load_status", "");
                                            }
                                            String Auth_status = jsonArray.getJSONObject(j).optString("Auth");
                                            if(Auth_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Auth_Load_status", "");
                                            }
                                            String Sett_status = jsonArray.getJSONObject(j).optString("Sett");
                                            if(Sett_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Sett_Load_status", "");
                                            }
                                            String Nfcd_status = jsonArray.getJSONObject(j).optString("Nfcd");
                                            if(Nfcd_status.equalsIgnoreCase("X"))
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "X");
                                            }
                                            else
                                            {
                                                FieldTekPro_SharedPrefeditor.putString("Nfcd_Load_status", "");
                                            }
                                        }
                                        FieldTekPro_SharedPrefeditor.commit();
                                        Get_Response = "success";
                                    }
                                    else
                                    {
                                        Get_Response = "no data";
                                    }
                                }
                                else
                                {
                                    Get_Response = "no data";
                                }
                            }
                            /*if transmit_type is EsRefresh and Reading all the values*/
                        }
                        /*Checking transmit type to read EsIload or EsRefresh*/
                    }
                    /*Reading Data by using FOR Loop*/
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
