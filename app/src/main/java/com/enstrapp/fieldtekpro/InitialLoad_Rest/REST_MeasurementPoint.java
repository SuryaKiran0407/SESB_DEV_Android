package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class REST_MeasurementPoint
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    /*GET_SEARCH_EtEquiMptt and Fields Names*/
    private static final String TABLE_SEARCH_EtEquiMptt = "EtEquiMptt";
    private static final String KEY_SEARCH_EtEquiMptt_id = "id";
    private static final String KEY_SEARCH_EtEquiMptt_Tplnr= "Tplnr";
    private static final String KEY_SEARCH_EtEquiMptt_Strno = "Strno";
    private static final String KEY_SEARCH_EtEquiMptt_Equnr = "Equnr";
    private static final String KEY_SEARCH_EtEquiMptt_Point = "Point";
    private static final String KEY_SEARCH_EtEquiMptt_Mpobj = "Mpobj";
    private static final String KEY_SEARCH_EtEquiMptt_Mpobt= "Mpobt";
    private static final String KEY_SEARCH_EtEquiMptt_Psort = "Psort";
    private static final String KEY_SEARCH_EtEquiMptt_Pttxt = "Pttxt";
    private static final String KEY_SEARCH_EtEquiMptt_Mptyp = "Mptyp";
    private static final String KEY_SEARCH_EtEquiMptt_Atinn = "Atinn";
    private static final String KEY_SEARCH_EtEquiMptt_Atbez = "Atbez";
    private static final String KEY_SEARCH_EtEquiMptt_Mrngu = "Mrngu";
    private static final String KEY_SEARCH_EtEquiMptt_Msehl= "Msehl";
    private static final String KEY_SEARCH_EtEquiMptt_Desir = "Desir";
    private static final String KEY_SEARCH_EtEquiMptt_Mrmin = "Mrmin";
    private static final String KEY_SEARCH_EtEquiMptt_Mrmax = "Mrmax";
    private static final String KEY_SEARCH_EtEquiMptt_Cdsuf = "Cdsuf";
    private static final String KEY_SEARCH_EtEquiMptt_Codct = "Codct";
    private static final String KEY_SEARCH_EtEquiMptt_Codgr = "Codgr";
    /*GET_SEARCH_EtEquiMptt and Fields Names*/


    /*FlocMPs and Fields Names*/
    private static final String TABLE_FlocMPs = "FlocMPs";
    private static final String KEY_FlocMPs_id = "id";
    private static final String KEY_FlocMPs_Tplnr= "Tplnr";
    private static final String KEY_FlocMPs_Strno = "Strno";
    private static final String KEY_FlocMPs_Equnr = "Equnr";
    private static final String KEY_FlocMPs_Point = "Point";
    private static final String KEY_FlocMPs_Mpobj = "Mpobj";
    private static final String KEY_FlocMPs_Mpobt= "Mpobt";
    private static final String KEY_FlocMPs_Psort = "Psort";
    private static final String KEY_FlocMPs_Pttxt = "Pttxt";
    private static final String KEY_FlocMPs_Mptyp = "Mptyp";
    private static final String KEY_FlocMPs_Atinn = "Atinn";
    private static final String KEY_FlocMPs_Atbez = "Atbez";
    private static final String KEY_FlocMPs_Mrngu = "Mrngu";
    private static final String KEY_FlocMPs_Msehl= "Msehl";
    private static final String KEY_FlocMPs_Desir = "Desir";
    private static final String KEY_FlocMPs_Mrmin = "Mrmin";
    private static final String KEY_FlocMPs_Mrmax = "Mrmax";
    private static final String KEY_FlocMPs_Cdsuf = "Cdsuf";
    private static final String KEY_FlocMPs_Codct = "Codct";
    private static final String KEY_FlocMPs_Codgr = "Codgr";
    /*FlocMPs and Fields Names*/


    /*EtImel and Fields Names*/
    private static final String TABLE_EtImel = "EtImel";
    private static final String KEY_EtImel_id = "id";
    private static final String KEY_EtImel_Melnr = "Melnr";
    private static final String KEY_EtImel_Namel = "Namel";
    private static final String KEY_EtImel_Meltx = "Meltx";
    private static final String KEY_EtImel_Erdat = "Erdat";
    private static final String KEY_EtImel_Ernam = "Ernam";
    /*EtImel and Fields Names*/


    /*EtImelMptt and Fields Names*/
    private static final String TABLE_EtImelMptt = "EtImelMptt";
    private static final String KEY_EtImelMptt_id = "id";
    private static final String KEY_EtImelMptt_Melnr = "Melnr";
    private static final String KEY_EtImelMptt_Posnr = "Posnr";
    private static final String KEY_EtImelMptt_Sortf = "Sortf";
    private static final String KEY_EtImelMptt_Tplnr = "Tplnr";
    private static final String KEY_EtImelMptt_Strno = "Strno";
    private static final String KEY_EtImelMptt_Equnr = "Equnr";
    private static final String KEY_EtImelMptt_Point = "Point";
    private static final String KEY_EtImelMptt_Mpobj = "Mpobj";
    private static final String KEY_EtImelMptt_Mpobt = "Mpobt";
    private static final String KEY_EtImelMptt_Psort = "Psort";
    private static final String KEY_EtImelMptt_Pttxt = "Pttxt";
    private static final String KEY_EtImelMptt_Mptyp = "Mptyp";
    private static final String KEY_EtImelMptt_Atinn = "Atinn";
    private static final String KEY_EtImelMptt_Atbez = "Atbez";
    private static final String KEY_EtImelMptt_Mrngu = "Mrngu";
    private static final String KEY_EtImelMptt_Msehl = "Msehl";
    private static final String KEY_EtImelMptt_Desir = "Desir";
    private static final String KEY_EtImelMptt_Mrmin = "Mrmin";
    private static final String KEY_EtImelMptt_Mrmax = "Mrmax";
    private static final String KEY_EtImelMptt_Cdsuf = "Cdsuf";
    private static final String KEY_EtImelMptt_Codct = "Codct";
    private static final String KEY_EtImelMptt_Codgr = "Codgr";
    /*EtImelMptt and Fields Names*/


    private static long startTime = 0;
    private static Check_Empty c_e = new Check_Empty();


    public static String Get_Mpoint_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);

            /*GET_SEARCH_EtEquiMptt and Fields Names */
            App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SEARCH_EtEquiMptt);
            String CREATE_TABLE_SEARCH_EtEquiMptt= "CREATE TABLE IF NOT EXISTS "+ TABLE_SEARCH_EtEquiMptt+ ""
                    + "( "
                    + KEY_SEARCH_EtEquiMptt_id+ " INTEGER PRIMARY KEY,"
                    + KEY_SEARCH_EtEquiMptt_Tplnr+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Strno+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Equnr+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Point+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mpobj+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mpobt+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Psort+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Pttxt+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mptyp+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Atinn+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Atbez+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrngu+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Msehl+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Desir+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrmin+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Mrmax+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Cdsuf+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Codct+ " TEXT,"
                    + KEY_SEARCH_EtEquiMptt_Codgr+ " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_SEARCH_EtEquiMptt);
            /*GET_SEARCH_EtEquiMptt and Fields Names */


            /*TABLE_FlocMPs and Fields Names */
            App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FlocMPs);
            String CREATE_TABLE_FlocMPs = "CREATE TABLE IF NOT EXISTS "+ TABLE_FlocMPs+ ""
                    + "( "
                    + KEY_FlocMPs_id+ " INTEGER PRIMARY KEY,"
                    + KEY_FlocMPs_Tplnr+ " TEXT,"
                    + KEY_FlocMPs_Strno+ " TEXT,"
                    + KEY_FlocMPs_Equnr+ " TEXT,"
                    + KEY_FlocMPs_Point+ " TEXT,"
                    + KEY_FlocMPs_Mpobj+ " TEXT,"
                    + KEY_FlocMPs_Mpobt+ " TEXT,"
                    + KEY_FlocMPs_Psort+ " TEXT,"
                    + KEY_FlocMPs_Pttxt+ " TEXT,"
                    + KEY_FlocMPs_Mptyp+ " TEXT,"
                    + KEY_FlocMPs_Atinn+ " TEXT,"
                    + KEY_FlocMPs_Atbez+ " TEXT,"
                    + KEY_FlocMPs_Mrngu+ " TEXT,"
                    + KEY_FlocMPs_Msehl+ " TEXT,"
                    + KEY_FlocMPs_Desir+ " TEXT,"
                    + KEY_FlocMPs_Mrmin+ " TEXT,"
                    + KEY_FlocMPs_Mrmax+ " TEXT,"
                    + KEY_FlocMPs_Cdsuf+ " TEXT,"
                    + KEY_FlocMPs_Codct+ " TEXT,"
                    + KEY_FlocMPs_Codgr+ " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_FlocMPs);
            /*TABLE_FlocMPs and Fields Names */


            /*EtImel and Fields Names */
            App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtImel);
            String CREATE_TABLE_EtImel = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtImel+ ""
                    + "( "
                    + KEY_EtImel_id+ " INTEGER PRIMARY KEY,"
                    + KEY_EtImel_Melnr+ " TEXT,"
                    + KEY_EtImel_Namel+ " TEXT,"
                    + KEY_EtImel_Meltx+ " TEXT,"
                    + KEY_EtImel_Erdat+ " TEXT,"
                    + KEY_EtImel_Ernam+ " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtImel);
            /*EtImel and Fields Names */


            /*EtImelMptt and Fields Names */
            App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtImelMptt);
            String CREATE_TABLE_EtImelMptt = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtImelMptt+ ""
                    + "( "
                    + KEY_EtImelMptt_id+ " INTEGER PRIMARY KEY,"
                    + KEY_EtImelMptt_Melnr+ " TEXT,"
                    + KEY_EtImelMptt_Posnr+ " TEXT,"
                    + KEY_EtImelMptt_Sortf+ " TEXT,"
                    + KEY_EtImelMptt_Tplnr+ " TEXT,"
                    + KEY_EtImelMptt_Strno+ " TEXT,"
                    + KEY_EtImelMptt_Equnr+ " TEXT,"
                    + KEY_EtImelMptt_Point+ " TEXT,"
                    + KEY_EtImelMptt_Mpobj+ " TEXT,"
                    + KEY_EtImelMptt_Mpobt+ " TEXT,"
                    + KEY_EtImelMptt_Psort+ " TEXT,"
                    + KEY_EtImelMptt_Pttxt+ " TEXT,"
                    + KEY_EtImelMptt_Mptyp+ " TEXT,"
                    + KEY_EtImelMptt_Atinn+ " TEXT,"
                    + KEY_EtImelMptt_Atbez+ " TEXT,"
                    + KEY_EtImelMptt_Mrngu+ " TEXT,"
                    + KEY_EtImelMptt_Msehl+ " TEXT,"
                    + KEY_EtImelMptt_Desir+ " TEXT,"
                    + KEY_EtImelMptt_Mrmin+ " TEXT,"
                    + KEY_EtImelMptt_Mrmax+ " TEXT,"
                    + KEY_EtImelMptt_Cdsuf+ " TEXT,"
                    + KEY_EtImelMptt_Codct+ " TEXT,"
                    + KEY_EtImelMptt_Codgr+ " TEXT"
                    + ")";
            App_db.execSQL(CREATE_TABLE_EtImelMptt);
            /*EtImelMptt and Fields Names */


            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"MP", "RD", webservice_type});
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
            Call<REST_MeasurementPoint_SER> call = service.postMPointDetails(url_link, basic, modelLoginRest);
            Response<REST_MeasurementPoint_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_MPOINT_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    startTime = System.currentTimeMillis();
                    App_db.execSQL("PRAGMA synchronous=OFF");
                    App_db.setLockingEnabled(false);
                    App_db.beginTransaction();


                    /*Reading and Inserting Data into Database Table for ETEQUIMPTT*/
                    try
                    {
                        List<REST_MeasurementPoint_SER.ETEQUIMPTT> ETEQUIMPTT_results = response.body().getETEQUIMPTT();
                        if (ETEQUIMPTT_results != null && ETEQUIMPTT_results.size() > 0)
                        {
                            String EtEquiMptt_sql = "Insert into EtEquiMptt (Tplnr, Strno, Equnr, Point, Mpobj, Mpobt, Psort, Pttxt, Mptyp, Atinn, Atbez, Mrngu, Msehl, Desir, Mrmin, Mrmax, Cdsuf, Codct, Codgr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtEquiMptt_statement = App_db.compileStatement(EtEquiMptt_sql);
                            EtEquiMptt_statement.clearBindings();
                            for (REST_MeasurementPoint_SER.ETEQUIMPTT equiMPsResult : ETEQUIMPTT_results)
                            {
                                EtEquiMptt_statement.bindString(1,c_e.check_empty(equiMPsResult.getTplnr()));
                                EtEquiMptt_statement.bindString(2,c_e.check_empty(equiMPsResult.getStrno()));
                                EtEquiMptt_statement.bindString(3,c_e.check_empty(equiMPsResult.getEQUNR()));
                                EtEquiMptt_statement.bindString(4,c_e.check_empty(equiMPsResult.getPOINT()));
                                EtEquiMptt_statement.bindString(5,c_e.check_empty(equiMPsResult.getMPOBJ()));
                                EtEquiMptt_statement.bindString(6,c_e.check_empty(equiMPsResult.getMpobt()));
                                EtEquiMptt_statement.bindString(7,c_e.check_empty(equiMPsResult.getPsort()));
                                EtEquiMptt_statement.bindString(8,c_e.check_empty(equiMPsResult.getPTTXT()));
                                EtEquiMptt_statement.bindString(9,c_e.check_empty(equiMPsResult.getMPTYP()));
                                EtEquiMptt_statement.bindString(10,c_e.check_empty(equiMPsResult.getATINN()));
                                EtEquiMptt_statement.bindString(11,c_e.check_empty(equiMPsResult.getATBEZ()));
                                EtEquiMptt_statement.bindString(12,c_e.check_empty(equiMPsResult.getMRNGU()));
                                EtEquiMptt_statement.bindString(13,c_e.check_empty(equiMPsResult.getMSEHL()));
                                EtEquiMptt_statement.bindString(14,c_e.check_empty(equiMPsResult.getDESIR()));
                                EtEquiMptt_statement.bindString(15,c_e.check_empty(equiMPsResult.getMRMIN()));
                                EtEquiMptt_statement.bindString(16,c_e.check_empty(equiMPsResult.getMRMAX()));
                                EtEquiMptt_statement.bindString(17,c_e.check_empty(equiMPsResult.getCdsuf()));
                                EtEquiMptt_statement.bindString(18,c_e.check_empty(equiMPsResult.getCodct()));
                                EtEquiMptt_statement.bindString(19,c_e.check_empty(equiMPsResult.getCodgr()));
                                EtEquiMptt_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for ETEQUIMPTT*/


                    App_db.setTransactionSuccessful();
                    Get_Response = "success";
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
            App_db.endTransaction();
            App_db.setLockingEnabled(true);
            App_db.execSQL("PRAGMA synchronous=NORMAL");
            final long endtime = System.currentTimeMillis();
            Log.v("kiran_MPOINT_insert", String.valueOf(endtime - startTime)+" Milliseconds");
        }
        return Get_Response;
    }

}
