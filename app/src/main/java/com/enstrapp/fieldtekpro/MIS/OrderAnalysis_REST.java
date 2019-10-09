package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;
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

/**
 * Created by Enstrapp on 19/02/18.
 */

public class OrderAnalysis_REST
{
    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();

    /*EsOrdTotal Tabel and Fieldnames*/
    private static final String TABLE_EsOrdTotal = "EsOrdTotal";
    private static final String KEY_EsOrdTotal_ID = "ID";
    private static final String KEY_EsOrdTotal_Total = "Total";
    private static final String KEY_EsOrdTotal_Outs = "Outs";
    private static final String KEY_EsOrdTotal_Inpr = "Inpr";
    private static final String KEY_EsOrdTotal_Comp = "Comp";
    private static final String KEY_EsOrdTotal_Dele = "Dele";
    /*EsOrdTotal Tabel and Fieldnames*/

    /*EtOrdPlantTotal Tabel and Fieldnames*/
    private static final String TABLE_EtOrdPlantTotal = "EtOrdPlantTotal";
    private static final String KEY_EtOrdPlantTotal_ID = "ID";
    private static final String KEY_EtOrdPlantTotal_Total = "Total";
    private static final String KEY_EtOrdPlantTotal_Swerk = "Swerk";
    private static final String KEY_EtOrdPlantTotal_Name = "Name";
    private static final String KEY_EtOrdPlantTotal_Auart = "Auart";
    private static final String KEY_EtOrdPlantTotal_Auartx = "Auartx";
    private static final String KEY_EtOrdPlantTotal_Outs = "Outs";
    private static final String KEY_EtOrdPlantTotal_Inpr = "Inpr";
    private static final String KEY_EtOrdPlantTotal_Comp = "Comp";
    private static final String KEY_EtOrdPlantTotal_Dele = "Dele";
    /*EtOrdPlantTotal Tabel and Fieldnames*/

    /*EtOrdArbplTotal Tabel and Fieldnames*/
    private static final String TABLE_EtOrdArbplTotal = "EtOrdArbplTotal";
    private static final String KEY_EtOrdArbplTotal_ID = "ID";
    private static final String KEY_EtOrdArbplTotal_Total = "Total";
    private static final String KEY_EtOrdArbplTotal_Swerk = "Swerk";
    private static final String KEY_EtOrdArbplTotal_Arbpl = "Arbpl";
    private static final String KEY_EtOrdArbplTotal_Name = "Name";
    private static final String KEY_EtOrdArbplTotal_Auart = "Auart";
    private static final String KEY_EtOrdArbplTotal_Auartx = "Auartx";
    private static final String KEY_EtOrdArbplTotal_Outs = "Outs";
    private static final String KEY_EtOrdArbplTotal_Inpr = "Inpr";
    private static final String KEY_EtOrdArbplTotal_Comp = "Comp";
    private static final String KEY_EtOrdArbplTotal_Dele = "Dele";
    /*EtOrdArbplTotal Tabel and Fieldnames*/

    /*EtOrdTypeTotal Tabel and Fieldnames*/
    private static final String TABLE_EtOrdTypeTotal = "EtOrdTypeTotal";
    private static final String KEY_EtOrdTypeTotal_ID = "ID";
    private static final String KEY_EtOrdTypeTotal_Total = "Total";
    private static final String KEY_EtOrdTypeTotal_Swerk = "Swerk";
    private static final String KEY_EtOrdTypeTotal_Arbpl = "Arbpl";
    private static final String KEY_EtOrdTypeTotal_Auart = "Auart";
    private static final String KEY_EtOrdTypeTotal_Auartx = "Auartx";
    private static final String KEY_EtOrdTypeTotal_Outs = "Outs";
    private static final String KEY_EtOrdTypeTotal_Inpr = "Inpr";
    private static final String KEY_EtOrdTypeTotal_Comp = "Comp";
    private static final String KEY_EtOrdTypeTotal_Dele = "Dele";
    /*EtOrdTypeTotal Tabel and Fieldnames*/

    /*EtOrdRep Tabel and Fieldnames*/
    private static final String TABLE_EtOrdRep = "EtOrdRep";
    private static final String KEY_EtOrdRep_ID = "ID";
    private static final String KEY_EtOrdRep_Phase = "Phase";
    private static final String KEY_EtOrdRep_Swerk = "Swerk";
    private static final String KEY_EtOrdRep_Arbpl = "Arbpl";
    private static final String KEY_EtOrdRep_Auart = "Auart";
    private static final String KEY_EtOrdRep_Aufnr = "Aufnr";
    private static final String KEY_EtOrdRep_Qmart = "Qmart";
    private static final String KEY_EtOrdRep_Qmnum = "Qmnum";
    private static final String KEY_EtOrdRep_Ktext = "Ktext";
    private static final String KEY_EtOrdRep_Ernam = "Ernam";
    private static final String KEY_EtOrdRep_Equnr = "Equnr";
    private static final String KEY_EtOrdRep_Eqktx = "Eqktx";
    private static final String KEY_EtOrdRep_Priok = "Priok";
    private static final String KEY_EtOrdRep_Gstrp = "Gstrp";
    private static final String KEY_EtOrdRep_Gltrp = "Gltrp";
    private static final String KEY_EtOrdRep_Tplnr = "Tplnr";
    private static final String KEY_EtOrdRep_Idat2 = "Idat2";
    /*EtOrdRep Tabel and Fieldnames*/

    public static String Get_OrderAnalysis_Data(Activity activity, String transmit_type, String selected_month, String selected_year) {
        try {

            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Creating EsOrdTotal Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EsOrdTotal);
            String CREATE_TABLE_EsOrdTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EsOrdTotal + ""
                    + "( "
                    + KEY_EsOrdTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EsOrdTotal_Total + " TEXT,"
                    + KEY_EsOrdTotal_Outs + " TEXT,"
                    + KEY_EsOrdTotal_Inpr + " TEXT,"
                    + KEY_EsOrdTotal_Comp + " TEXT,"
                    + KEY_EsOrdTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EsOrdTotal);
            /* Creating EsOrdTotal Table with Fields */


            /* Creating EtOrdPlantTotal  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtOrdPlantTotal);
            String CREATE_TABLE_EtOrdPlantTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtOrdPlantTotal + ""
                    + "( "
                    + KEY_EtOrdPlantTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtOrdPlantTotal_Total + " TEXT,"
                    + KEY_EtOrdPlantTotal_Swerk + " TEXT,"
                    + KEY_EtOrdPlantTotal_Name + " TEXT,"
                    + KEY_EtOrdPlantTotal_Auart + " TEXT,"
                    + KEY_EtOrdPlantTotal_Auartx + " TEXT,"
                    + KEY_EtOrdPlantTotal_Outs + " TEXT,"
                    + KEY_EtOrdPlantTotal_Inpr + " TEXT,"
                    + KEY_EtOrdPlantTotal_Comp + " TEXT,"
                    + KEY_EtOrdPlantTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtOrdPlantTotal);
            /* Creating EtOrdPlantTotal  Table with Fields */

            /* Creating EtOrdArbplTotal  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtOrdArbplTotal);
            String CREATE_TABLE_EtOrdArbplTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtOrdArbplTotal + ""
                    + "( "
                    + KEY_EtOrdArbplTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtOrdArbplTotal_Total + " TEXT,"
                    + KEY_EtOrdArbplTotal_Swerk + " TEXT,"
                    + KEY_EtOrdArbplTotal_Arbpl + " TEXT,"
                    + KEY_EtOrdArbplTotal_Name + " TEXT,"
                    + KEY_EtOrdArbplTotal_Auart + " TEXT,"
                    + KEY_EtOrdArbplTotal_Auartx + " TEXT,"
                    + KEY_EtOrdArbplTotal_Outs + " TEXT,"
                    + KEY_EtOrdArbplTotal_Inpr + " TEXT,"
                    + KEY_EtOrdArbplTotal_Comp + " TEXT,"
                    + KEY_EtOrdArbplTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtOrdArbplTotal);
            /* Creating EtOrdArbplTotal  Table with Fields */

            /* Creating EtOrdTypeTotal  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtOrdTypeTotal);
            String CREATE_TABLE_EtOrdTypeTotal = "CREATE TABLE IF NOT EXISTS " + TABLE_EtOrdTypeTotal + ""
                    + "( "
                    + KEY_EtOrdTypeTotal_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtOrdTypeTotal_Total + " TEXT,"
                    + KEY_EtOrdTypeTotal_Swerk + " TEXT,"
                    + KEY_EtOrdTypeTotal_Arbpl + " TEXT,"
                    + KEY_EtOrdTypeTotal_Auart + " TEXT,"
                    + KEY_EtOrdTypeTotal_Auartx + " TEXT,"
                    + KEY_EtOrdTypeTotal_Outs + " TEXT,"
                    + KEY_EtOrdTypeTotal_Inpr + " TEXT,"
                    + KEY_EtOrdTypeTotal_Comp + " TEXT,"
                    + KEY_EtOrdTypeTotal_Dele + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtOrdTypeTotal);
            /* Creating EtOrdTypeTotal  Table with Fields */

            /* Creating EtOrdRep  Table with Fields */
            App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtOrdRep);
            String CREATE_TABLE_EtOrdRep = "CREATE TABLE IF NOT EXISTS " + TABLE_EtOrdRep + ""
                    + "( "
                    + KEY_EtOrdRep_ID + " INTEGER PRIMARY KEY,"
                    + KEY_EtOrdRep_Phase + " TEXT,"
                    + KEY_EtOrdRep_Swerk + " TEXT,"
                    + KEY_EtOrdRep_Arbpl + " TEXT,"
                    + KEY_EtOrdRep_Auart + " TEXT,"
                    + KEY_EtOrdRep_Aufnr + " TEXT,"
                    + KEY_EtOrdRep_Qmart + " TEXT,"
                    + KEY_EtOrdRep_Qmnum + " TEXT,"
                    + KEY_EtOrdRep_Ktext + " TEXT,"
                    + KEY_EtOrdRep_Ernam + " TEXT,"
                    + KEY_EtOrdRep_Equnr + " TEXT,"
                    + KEY_EtOrdRep_Eqktx + " TEXT,"
                    + KEY_EtOrdRep_Priok + " TEXT,"
                    + KEY_EtOrdRep_Gstrp + " TEXT,"
                    + KEY_EtOrdRep_Gltrp + " TEXT,"
                    + KEY_EtOrdRep_Tplnr + " TEXT,"
                    + KEY_EtOrdRep_Idat2 + " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtOrdRep);
            /* Creating EtOrdRep  Table with Fields */

            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R4", "RD", webservice_type});
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

            Rest_Model_Login_Device modelLoginDeviceRest = new Rest_Model_Login_Device();
            modelLoginDeviceRest.setMUSER(username.toUpperCase().toString());
            modelLoginDeviceRest.setDEVICEID(device_id);
            modelLoginDeviceRest.setDEVICESNO(device_serial_number);
            modelLoginDeviceRest.setUDID(device_uuid);
            modelLoginDeviceRest.setiVTRANSMITTYPE("MISR");
            modelLoginDeviceRest.setiVCOMMIT("X");
            modelLoginDeviceRest.seteRROR("");


            Rest_Model_Login modelLoginRest = new Rest_Model_Login();
            modelLoginRest.setIv_transmit_type("MISR");
            modelLoginRest.setIv_user(username);
            modelLoginRest.setIs_device(modelLoginDeviceRest);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<OrderAnalysis_SER_REST> call = service.postOrderAnalysis(url_link, basic, modelLoginRest);
            Response<OrderAnalysis_SER_REST> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {

                    App_db.beginTransaction();


                    try
                    {
                        String EsOrdTotal_response_data = new Gson().toJson(response.body().getEsOrdTotal());
                        JSONObject jsonObject = new JSONObject(EsOrdTotal_response_data);
                        if (jsonObject.length() > 0)
                        {
                            String EsOrdTotal_sql = "Insert into EsOrdTotal (Total, Outs, Inpr, Comp, Dele) values(?,?,?,?,?);";
                            SQLiteStatement EsOrdTotal_statement = App_db.compileStatement(EsOrdTotal_sql);
                            EsOrdTotal_statement.clearBindings();
                            EsOrdTotal_statement.bindString(1, jsonObject.optString("TOTAL"));
                            EsOrdTotal_statement.bindString(2, jsonObject.optString("OUTS"));
                            EsOrdTotal_statement.bindString(3, jsonObject.optString("INPR"));
                            EsOrdTotal_statement.bindString(4, jsonObject.optString("COMP"));
                            EsOrdTotal_statement.bindString(5, jsonObject.optString("DELE"));
                            EsOrdTotal_statement.execute();
                        }
                    }
                    catch (Exception e)
                    {
                    }




                    try
                    {
                        String EtOrdPlantTotal_response_data = new Gson().toJson(response.body().getEtOrdPlantTotal());
                        JSONArray jsonArray = new JSONArray(EtOrdPlantTotal_response_data);
                        if (jsonArray.length() > 0)
                        {
                            String EtOrdPlantTotal_sql = "Insert into EtOrdPlantTotal (Total, Swerk, Name, Auart, Auartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtOrdPlantTotal_statement = App_db.compileStatement(EtOrdPlantTotal_sql);
                            EtOrdPlantTotal_statement.clearBindings();
                            for (int j = 0; j < jsonArray.length(); j++)
                            {
                                EtOrdPlantTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("TOTAL"));
                                EtOrdPlantTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("SWERK"));
                                EtOrdPlantTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("NAME"));
                                EtOrdPlantTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("AUART"));
                                EtOrdPlantTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("AUARTX"));
                                EtOrdPlantTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("OUTS"));
                                EtOrdPlantTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("INPR"));
                                EtOrdPlantTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("COMP"));
                                EtOrdPlantTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("DELE"));
                                EtOrdPlantTotal_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }




                    try
                    {
                        String EtOrdArbplTotal_response_data = new Gson().toJson(response.body().getEtOrdArbplTotal());
                        JSONArray jsonArray = new JSONArray(EtOrdArbplTotal_response_data);
                        if (jsonArray.length() > 0)
                        {
                            String EtOrdArbplTotal_sql = "Insert into EtOrdArbplTotal (Total, Swerk, Arbpl, Name, Auart, Auartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtOrdArbplTotal_statement = App_db.compileStatement(EtOrdArbplTotal_sql);
                            EtOrdArbplTotal_statement.clearBindings();
                            for (int j = 0; j < jsonArray.length(); j++)
                            {
                                EtOrdArbplTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("TOTAL"));
                                EtOrdArbplTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("SWERK"));
                                EtOrdArbplTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("ARBPL"));
                                EtOrdArbplTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("NAME"));
                                EtOrdArbplTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("AUART"));
                                EtOrdArbplTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("AUARTX"));
                                EtOrdArbplTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("OUTS"));
                                EtOrdArbplTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("INPR"));
                                EtOrdArbplTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("COMP"));
                                EtOrdArbplTotal_statement.bindString(10, jsonArray.getJSONObject(j).optString("DELE"));
                                EtOrdArbplTotal_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }



                    try
                    {
                        String EtOrdTypeTotal_response_data = new Gson().toJson(response.body().getEtOrdTypeTotal());
                        JSONArray jsonArray = new JSONArray(EtOrdTypeTotal_response_data);
                        if (jsonArray.length() > 0)
                        {
                            String EtOrdTypeTotal_sql = "Insert into EtOrdTypeTotal (Total, Swerk, Arbpl, Auart, Auartx, Outs, Inpr, Comp, Dele) values(?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtOrdTypeTotal_statement = App_db.compileStatement(EtOrdTypeTotal_sql);
                            EtOrdTypeTotal_statement.clearBindings();
                            for (int j = 0; j < jsonArray.length(); j++)
                            {
                                EtOrdTypeTotal_statement.bindString(1, jsonArray.getJSONObject(j).optString("TOTAL"));
                                EtOrdTypeTotal_statement.bindString(2, jsonArray.getJSONObject(j).optString("SWERK"));
                                EtOrdTypeTotal_statement.bindString(3, jsonArray.getJSONObject(j).optString("ARBPL"));
                                EtOrdTypeTotal_statement.bindString(4, jsonArray.getJSONObject(j).optString("AUART"));
                                EtOrdTypeTotal_statement.bindString(5, jsonArray.getJSONObject(j).optString("AUARTX"));
                                EtOrdTypeTotal_statement.bindString(6, jsonArray.getJSONObject(j).optString("OUTS"));
                                EtOrdTypeTotal_statement.bindString(7, jsonArray.getJSONObject(j).optString("INPR"));
                                EtOrdTypeTotal_statement.bindString(8, jsonArray.getJSONObject(j).optString("COMP"));
                                EtOrdTypeTotal_statement.bindString(9, jsonArray.getJSONObject(j).optString("DELE"));
                                EtOrdTypeTotal_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }



                    try
                    {
                        String EtOrdRep_response_data = new Gson().toJson(response.body().getEtOrdRep());
                        JSONArray jsonArray = new JSONArray(EtOrdRep_response_data);
                        if (jsonArray.length() > 0)
                        {
                            String EtOrdRep_sql = "Insert into EtOrdRep (Phase, Swerk, Arbpl, Auart, Aufnr, Qmart, Qmnum, Ktext, Ernam, Equnr, Eqktx, Priok, Gstrp, Gltrp, Tplnr, Idat2) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtOrdRep_statement = App_db.compileStatement(EtOrdRep_sql);
                            EtOrdRep_statement.clearBindings();
                            for (int j = 0; j < jsonArray.length(); j++)
                            {
                                EtOrdRep_statement.bindString(1, jsonArray.getJSONObject(j).optString("PHASE"));
                                EtOrdRep_statement.bindString(2, jsonArray.getJSONObject(j).optString("SWERK"));
                                EtOrdRep_statement.bindString(3, jsonArray.getJSONObject(j).optString("ARBPL"));
                                EtOrdRep_statement.bindString(4, jsonArray.getJSONObject(j).optString("AUART"));
                                EtOrdRep_statement.bindString(5, jsonArray.getJSONObject(j).optString("AUFNR"));
                                EtOrdRep_statement.bindString(6, jsonArray.getJSONObject(j).optString("QMART"));
                                EtOrdRep_statement.bindString(7, jsonArray.getJSONObject(j).optString("QMNUM"));
                                EtOrdRep_statement.bindString(8, jsonArray.getJSONObject(j).optString("KTEXT"));
                                EtOrdRep_statement.bindString(9, jsonArray.getJSONObject(j).optString("ERNAM"));
                                EtOrdRep_statement.bindString(10, jsonArray.getJSONObject(j).optString("EQUNR"));
                                EtOrdRep_statement.bindString(11, jsonArray.getJSONObject(j).optString("EQKTX"));
                                EtOrdRep_statement.bindString(12, jsonArray.getJSONObject(j).optString("PRIOK"));
                                EtOrdRep_statement.bindString(13, jsonArray.getJSONObject(j).optString("GSTRP"));
                                EtOrdRep_statement.bindString(14, jsonArray.getJSONObject(j).optString("GLTRP"));
                                EtOrdRep_statement.bindString(15, jsonArray.getJSONObject(j).optString("TPLNR"));
                                EtOrdRep_statement.bindString(16, jsonArray.getJSONObject(j).optString("IDAT2"));
                                EtOrdRep_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            }
            else
            {
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




















