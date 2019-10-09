package com.enstrapp.fieldtekpro.equipment_update;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro.Utilities.Model_BOM_RESV_REST;
import com.enstrapp.fieldtekpro.Utilities.SER_BOM_Reservation_REST;
import com.enstrapp.fieldtekpro.Utilities.is_device;
import com.enstrapp.fieldtekpro.Utilities.is_reserv_comp;
import com.enstrapp.fieldtekpro.Utilities.is_reserv_header;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.google.gson.Gson;

import org.json.JSONArray;

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

public class EQ_UPDATE_REST {

    private static String cookie = "", token = "", password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    public static String post_eq_data(Context activity, String functionlocation_id, String equipment_id,
                                      String iwerk, String work_center,
                                      String manufacturer, String modelno, String mpo, String mso,
                                      String mc, String cym, String cm)
    {
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
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EQ", "U", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            String URL = activity.getString(R.string.ip_address);

            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json;odata=verbose");
            map.put("Content-Type", "application/json");


            EQUI_Model_REST equi_model_rest = new EQUI_Model_REST();
            equi_model_rest.setTPLNR(functionlocation_id);
            equi_model_rest.setEQUNR(equipment_id);
            equi_model_rest.setEQTYP("");
            equi_model_rest.setEQART("");
            equi_model_rest.setWERKS(iwerk);
            equi_model_rest.setARBPL(work_center);
            equi_model_rest.setHERST(manufacturer);
            equi_model_rest.setTYPBZ(modelno);
            equi_model_rest.setMAPAR(mpo);
            equi_model_rest.setSERGE(mso);
            equi_model_rest.setHERLD(mc);
            equi_model_rest.setBAUJJ(cym);
            equi_model_rest.setBAUMM(cm);


            is_device is_device= new is_device();
            is_device.setMUSER(username.toUpperCase().toString());
            is_device.setDEVICEID(device_id);
            is_device.setDEVICESNO(device_serial_number);
            is_device.setUDID(device_uuid);
            is_device.setIVTRANSMITTYPE("LOAD");
            is_device.setIVCOMMIT("X");
            is_device.setERROR("");

            EQ_Update_Model_REST bom_reservation = new EQ_Update_Model_REST();
            bom_reservation.setMuser(username.toUpperCase().toString());
            bom_reservation.setDeviceid(device_id);
            bom_reservation.setDevicesno(device_serial_number);
            bom_reservation.setUdid(device_uuid);
            bom_reservation.setIvTransmitType("LOAD");
            bom_reservation.setIvCommit("X");
            bom_reservation.setIs_equi(equi_model_rest);
            bom_reservation.setIs_device(is_device);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.SECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);

            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<SER_EQUIP_UPDATE_REST> call = service.postEQUIUPDATE(url_link, basic, map, bom_reservation);
            Response<SER_EQUIP_UPDATE_REST> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_EQUI_UPDATE_code", response_status_code + "...");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String response_data = new Gson().toJson(response.body().geteTMESSAGE());
                    JSONArray response_JsonArray = new JSONArray(response_data);
                    StringBuilder stringBuilder = new StringBuilder();
                    if (response_JsonArray.length() > 0)
                    {
                        for (int i = 0; i < response_JsonArray.length(); i++)
                        {
                            String message = response_JsonArray.getJSONObject(i).optString("MESSAGE");
                            stringBuilder.append(message);
                        }
                        Get_Response = stringBuilder.toString();
                        if(Get_Response.contains("Equipment data saved successfully!"))
                        {
                            try
                            {
                                SER_EQUIP_UPDATE_REST.ES_EQUI EtEqui_results = response.body().getES_EQUI();
                                if (EtEqui_results != null && !EtEqui_results.equals(""))
                                {
                                    App_db.execSQL("delete from EtEqui where Equnr = ?", new String[]{equipment_id});
                                    String Equip_sql = "Insert into EtEqui (Tplnr, Pltxt, Equnr, Spras, Eqktx, Rbnr, Eqtyp, Herst, Eqart, Werks, Arbpl, Kostl, Ingrp, Serge, Typbz, Mapar, Inactive, Permit, Hequi, Stlkz, Level, Sequi, Stort, Beber, Anlnr, Anlun, Ivdat, Invzu, Iwerk, Bukrs,HERLD,BAUJJ,BAUMM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement Equip_statement = App_db.compileStatement(Equip_sql);
                                    Equip_statement.clearBindings();
                                    Equip_statement.bindString(1, c_e.check_empty(EtEqui_results.getTplnr()));
                                    Equip_statement.bindString(2, "");
                                    Equip_statement.bindString(3, c_e.check_empty(EtEqui_results.getEqunr()));
                                    Equip_statement.bindString(4, c_e.check_empty(EtEqui_results.getSpras()));
                                    Equip_statement.bindString(5, c_e.check_empty(EtEqui_results.getEqktx()));
                                    Equip_statement.bindString(6, c_e.check_empty(EtEqui_results.getRbnr()));
                                    Equip_statement.bindString(7, c_e.check_empty(EtEqui_results.getEqtyp()));
                                    Equip_statement.bindString(8, c_e.check_empty(EtEqui_results.getHerst()));
                                    Equip_statement.bindString(9, c_e.check_empty(EtEqui_results.getEqart()));
                                    Equip_statement.bindString(10, c_e.check_empty(EtEqui_results.getWerks()));
                                    Equip_statement.bindString(11, c_e.check_empty(EtEqui_results.getArbpl()));
                                    Equip_statement.bindString(12, c_e.check_empty(EtEqui_results.getKostl()));
                                    Equip_statement.bindString(13, c_e.check_empty(EtEqui_results.getIngrp()));
                                    Equip_statement.bindString(14, c_e.check_empty(EtEqui_results.getSerge()));
                                    Equip_statement.bindString(15, c_e.check_empty(EtEqui_results.getTypbz()));
                                    Equip_statement.bindString(16, c_e.check_empty(EtEqui_results.getMapar()));
                                    Equip_statement.bindString(17, "");
                                    Equip_statement.bindString(18, "");
                                    Equip_statement.bindString(19, c_e.check_empty(EtEqui_results.getHequi()));
                                    Equip_statement.bindString(20, c_e.check_empty(EtEqui_results.getStlkz()));
                                    Equip_statement.bindString(21, c_e.check_empty(EtEqui_results.getLevel()));
                                    Equip_statement.bindString(22, c_e.check_empty(EtEqui_results.getSequi()));
                                    Equip_statement.bindString(23, c_e.check_empty(EtEqui_results.getStort()));
                                    Equip_statement.bindString(24, c_e.check_empty(EtEqui_results.getBeber()));
                                    Equip_statement.bindString(25, c_e.check_empty(EtEqui_results.getAnlnr()));
                                    Equip_statement.bindString(26, c_e.check_empty(EtEqui_results.getAnlun()));
                                    Equip_statement.bindString(27, c_e.check_empty(EtEqui_results.getIvdat()));
                                    Equip_statement.bindString(28, c_e.check_empty(EtEqui_results.getInvzu()));
                                    Equip_statement.bindString(29, c_e.check_empty(EtEqui_results.getIwerk()));
                                    Equip_statement.bindString(30, c_e.check_empty(EtEqui_results.getBukrs()));
                                    Equip_statement.bindString(31, c_e.check_empty(EtEqui_results.getHERLD()));
                                    Equip_statement.bindString(32, c_e.check_empty(EtEqui_results.getBAUJJ()));
                                    Equip_statement.bindString(33, c_e.check_empty(EtEqui_results.getBAUMM()));
                                    Equip_statement.execute();
                                }
                            }
                            catch (Exception e)
                            {
                            }
                        }
                    }
                    else
                    {
                        Get_Response = "nodata";
                    }
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
