package com.enstrapp.fieldtekpro.Initialload;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Token
{

    private static String password = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;

    public static String Get_Token(Context activity)
    {
        try
        {
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = ""+ Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),((long) device_id.hashCode() << 32)| device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            Map<String, String> map = new HashMap<>();
            map.put("x-csrf-token", "fetch");
            map.put("Accept", "application/json;odata=verbose");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Token_SER> call = service.getToken(URL, basic, map);
            Response<Token_SER> response = call.execute();
            int response_status_code = response.code();
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String token = "";
                    StringBuilder sb = new StringBuilder();
                    Headers headers = response.headers();
                    for(int i = 0; i < headers.size(); i++)
                    {
                        String headers_name = headers.name(i);
                        if(headers_name.equalsIgnoreCase("set-cookie"))
                        {
                            String cookie = headers.value(i);
                            if(cookie.startsWith("MYSAPSSO2"))
                            {
                            }
                            else if(cookie.startsWith("sap-usercontext=sap-client"))
                            {
                                if(cookie.contains("path=/"))
                                {
                                    cookie = cookie.replace("path=/","").trim();
                                    sb.append(cookie);
                                    sb.append(" ");
                                }
                                else
                                {
                                    sb.append(cookie);
                                }
                            }
                            else if(cookie.startsWith("SAP_SESSIONID"))
                            {
                                if(cookie.contains("path=/"))
                                {
                                    cookie = cookie.replace("path=/","").trim();
                                    cookie = cookie.replace(";","");
                                    sb.append(cookie);
                                }
                                else
                                {
                                    sb.append(cookie);
                                }
                            }
                        }
                        else if(headers_name.equalsIgnoreCase("x-csrf-token"))
                        {
                            token = headers.value(i);
                        }
                    }
                    FieldTekPro_SharedPrefeditor.putString("cookie", sb.toString());
                    FieldTekPro_SharedPrefeditor.putString("token", token);
                    FieldTekPro_SharedPrefeditor.commit();
                    Get_Response = "success";
                }
            }
            else
            {
                Get_Response = "no data";
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
