package com.enstrapp.fieldtekpro.Initialload;

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
import com.enstrapp.fieldtekpro.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.DecimalFormat;
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

public class Calibration
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    /* EtQinspData Table and Fields Names */
    private static final String TABLE_EtQinspData = "EtQinspData";
    private static final String KEY_EtQinspData_ID = "id";//0
    private static final String KEY_EtQinspData_Aufnr = "Aufnr";//1
    private static final String KEY_EtQinspData_Prueflos = "Prueflos";//2
    private static final String KEY_EtQinspData_Vornr = "Vornr";//3
    private static final String KEY_EtQinspData_Plnty = "Plnty";//4
    private static final String KEY_EtQinspData_Plnnr = "Plnnr";//5
    private static final String KEY_EtQinspData_Plnkn = "Plnkn";//6
    private static final String KEY_EtQinspData_Merknr = "Merknr";//7
    private static final String KEY_EtQinspData_Quantitat = "Quantitat";//8
    private static final String KEY_EtQinspData_Qualitat = "Qualitat";//9
    private static final String KEY_EtQinspData_QpmkZaehl = "QpmkZaehl";//10
    private static final String KEY_EtQinspData_Msehi = "Msehi";//11
    private static final String KEY_EtQinspData_Msehl = "Msehl";//12
    private static final String KEY_EtQinspData_Verwmerkm = "Verwmerkm";//13
    private static final String KEY_EtQinspData_Kurztext = "Kurztext";//14
    private static final String KEY_EtQinspData_Result = "Result";//15
    private static final String KEY_EtQinspData_Sollwert = "Sollwert";//16
    private static final String KEY_EtQinspData_Toleranzob = "Toleranzob";//17
    private static final String KEY_EtQinspData_Toleranzub = "Toleranzub";//18
    private static final String KEY_EtQinspData_Rueckmelnr = "Rueckmelnr";//19
    private static final String KEY_EtQinspData_Satzstatus = "Satzstatus";//20
    private static final String KEY_EtQinspData_Equnr = "Equnr";//21
    private static final String KEY_EtQinspData_Pruefbemkt = "Pruefbemkt";//22
    private static final String KEY_EtQinspData_Mbewertg = "Mbewertg";//23
    private static final String KEY_EtQinspData_Pruefer = "Pruefer";//24
    private static final String KEY_EtQinspData_Pruefdatuv = "Pruefdatuv";//25
    private static final String KEY_EtQinspData_Pruefdatub = "Pruefdatub";//26
    private static final String KEY_EtQinspData_Pruefzeitv = "Pruefzeitv";//27
    private static final String KEY_EtQinspData_Pruefzeitb = "Pruefzeitb";//28
    private static final String KEY_EtQinspData_Iststpumf = "Iststpumf";//29
    private static final String KEY_EtQinspData_Anzfehleh = "Anzfehleh";//30
    private static final String KEY_EtQinspData_Anzwertg = "Anzwertg";//31
    private static final String KEY_EtQinspData_Ktextmat = "Ktextmat";//32
    private static final String KEY_EtQinspData_Katab1 = "Katab1";//33
    private static final String KEY_EtQinspData_Katalgart1 = "Katalgart1";//34
    private static final String KEY_EtQinspData_Auswmenge1 = "Auswmenge1";//35
    private static final String KEY_EtQinspData_Codetext = "Codetext";//36
    private static final String KEY_EtQinspData_Xstatus = "Xstatus";//37
    private static final String KEY_EtQinspData_Action = "Action";//38
    private static final String KEY_EtQinspData_UUID = "UUID";//39
    private static final String KEY_EtQinspData_Udid = "Udid";//40
    private static final String KEY_EtQinspData_Werks = "Werks";//40
	/* EtQinspData Table and Fields Names */


    /* EtQudData Table and Fields Names */
    private static final String TABLE_EtQudData = "EtQudData";
    private static final String KEY_EtQudData_ID = "id";//0
    private static final String KEY_EtQudData_Prueflos = "Prueflos";//1
    private static final String KEY_EtQudData_Aufnr = "Aufnr";//2
    private static final String KEY_EtQudData_Werks = "Werks";//3
    private static final String KEY_EtQudData_Equnr = "Equnr";//4
    private static final String KEY_EtQudData_Vkatart = "Vkatart";//5
    private static final String KEY_EtQudData_Vcodegrp = "Vcodegrp";//6
    private static final String KEY_EtQudData_Vauswahlmg = "Vauswahlmg";//7
    private static final String KEY_EtQudData_Vcode = "Vcode";//8
    private static final String KEY_EtQudData_Qkennzahl = "Qkennzahl";//9
    private static final String KEY_EtQudData_Vname = "Vname";//10
    private static final String KEY_EtQudData_Vdatum = "Vdatum";//11
    private static final String KEY_EtQudData_Vaedatum = "Vaedatum";//12
    private static final String KEY_EtQudData_Vezeitaen = "Vezeitaen";//13
    private static final String KEY_EtQudData_Udtext = "Udtext";//14
    private static final String KEY_EtQudData_Udforce = "Udforce";//15
    private static final String KEY_EtQudData_Rcode = "Rcode";//16
    private static final String KEY_EtQudData_Xstatus = "Xstatus";//17
    private static final String KEY_EtQudData_Action = "Action";//18
    private static final String KEY_EtQudData_Udid = "Udid";//19
    private static final String KEY_EtQudData_Status = "Status";//19
    /* EtQudData Table and Fields Names */

    public static String Get_Calibration_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equalsIgnoreCase("LOAD"))
            {
                /* EtQinspData Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtQinspData);
                String CREATE_TABLE_EtQinspData = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtQinspData+ ""
                        + "( "
                        + KEY_EtQinspData_ID+ " INTEGER PRIMARY KEY,"//0
                        + KEY_EtQinspData_Aufnr+ " TEXT,"//1
                        + KEY_EtQinspData_Prueflos+ " TEXT,"//2
                        + KEY_EtQinspData_Vornr+ " TEXT,"//3
                        + KEY_EtQinspData_Plnty+ " TEXT,"//4
                        + KEY_EtQinspData_Plnnr+ " TEXT,"//5
                        + KEY_EtQinspData_Plnkn+ " TEXT,"//6
                        + KEY_EtQinspData_Merknr+ " TEXT,"//7
                        + KEY_EtQinspData_Quantitat+ " TEXT,"//8
                        + KEY_EtQinspData_Qualitat+ " TEXT,"//9
                        + KEY_EtQinspData_QpmkZaehl+ " TEXT,"//10
                        + KEY_EtQinspData_Msehi+ " TEXT,"//11
                        + KEY_EtQinspData_Msehl+ " TEXT,"//12
                        + KEY_EtQinspData_Verwmerkm+ " TEXT,"//13
                        + KEY_EtQinspData_Kurztext+ " TEXT,"//1
                        + KEY_EtQinspData_Result+ " TEXT,"//2
                        + KEY_EtQinspData_Sollwert+ " TEXT,"//3
                        + KEY_EtQinspData_Toleranzob+ " TEXT,"//4
                        + KEY_EtQinspData_Toleranzub+ " TEXT,"//5
                        + KEY_EtQinspData_Rueckmelnr+ " TEXT,"//6
                        + KEY_EtQinspData_Satzstatus+ " TEXT,"//7
                        + KEY_EtQinspData_Equnr+ " TEXT,"//8
                        + KEY_EtQinspData_Pruefbemkt+ " TEXT,"//8
                        + KEY_EtQinspData_Mbewertg+ " TEXT,"//9
                        + KEY_EtQinspData_Pruefer+ " TEXT,"//10
                        + KEY_EtQinspData_Pruefdatuv+ " TEXT,"//11
                        + KEY_EtQinspData_Pruefdatub+ " TEXT,"//12
                        + KEY_EtQinspData_Pruefzeitv+ " TEXT,"//13
                        + KEY_EtQinspData_Pruefzeitb+ " TEXT,"//1
                        + KEY_EtQinspData_Iststpumf+ " TEXT,"//2
                        + KEY_EtQinspData_Anzfehleh+ " TEXT,"//3
                        + KEY_EtQinspData_Anzwertg+ " TEXT,"//4
                        + KEY_EtQinspData_Ktextmat+ " TEXT,"//1
                        + KEY_EtQinspData_Katab1+ " TEXT,"//2
                        + KEY_EtQinspData_Katalgart1+ " TEXT,"//3
                        + KEY_EtQinspData_Auswmenge1+ " TEXT,"//4
                        + KEY_EtQinspData_Codetext+ " TEXT,"//4
                        + KEY_EtQinspData_Xstatus+ " TEXT,"//6
                        + KEY_EtQinspData_Action+ " TEXT,"//7
                        + KEY_EtQinspData_UUID+ " TEXT,"//7
                        + KEY_EtQinspData_Udid+ " TEXT,"//7
                        + KEY_EtQinspData_Werks+ " TEXT"//7
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtQinspData);
		        /* EtQinspData Table and Fields Names */

		        /* EtQudData Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtQudData);
                String CREATE_TABLE_EtQudData = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtQudData+ ""
                        + "( "
                        + KEY_EtQudData_ID+ " INTEGER PRIMARY KEY,"//0
                        + KEY_EtQudData_Prueflos+ " TEXT,"//1
                        + KEY_EtQudData_Aufnr+ " TEXT,"//2
                        + KEY_EtQudData_Werks+ " TEXT,"//4
                        + KEY_EtQudData_Equnr+ " TEXT,"//3
                        + KEY_EtQudData_Vkatart+ " TEXT,"//5
                        + KEY_EtQudData_Vcodegrp+ " TEXT,"//6
                        + KEY_EtQudData_Vauswahlmg+ " TEXT,"//7
                        + KEY_EtQudData_Vcode+ " TEXT,"//8
                        + KEY_EtQudData_Qkennzahl+ " TEXT,"//9
                        + KEY_EtQudData_Vname+ " TEXT,"//10
                        + KEY_EtQudData_Vdatum+ " TEXT,"//11
                        + KEY_EtQudData_Vaedatum+ " TEXT,"//12
                        + KEY_EtQudData_Vezeitaen+ " TEXT,"//13
                        + KEY_EtQudData_Udtext+ " TEXT,"//14
                        + KEY_EtQudData_Udforce+ " TEXT,"//15
                        + KEY_EtQudData_Rcode+ " TEXT,"//16
                        + KEY_EtQudData_Xstatus+ " TEXT,"//17
                        + KEY_EtQudData_Action+ " TEXT,"//18
                        + KEY_EtQudData_Udid+ " TEXT,"//19
                        + KEY_EtQudData_Status+ " TEXT"//20
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtQudData);
		        /* EtQudData Table and Fields Names */
            }
            else
            {
                App_db.execSQL("delete from EtQinspData");
                App_db.execSQL("delete from EtQudData");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"D3", "RD", webservice_type});
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
            Call<Calibration_SER> call = service.getCalibrationDetails(url_link, basic, map);
            Response<Calibration_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Calibration_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    /*Reading Response Data and Parsing to Serializable*/
                    Calibration_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    App_db.beginTransaction();

                    /*Reading and Inserting Data into Database Table for EtQinspData*/
                    String EtQinspData_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtQinspData().getResults());
                    if (EtQinspData_response_data != null && !EtQinspData_response_data.equals("") && !EtQinspData_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EtQinspData_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String EtQinspData_sql = "Insert into EtQinspData (Aufnr, Prueflos, Vornr, Plnty, Plnnr, Plnkn, Merknr, Quantitat, Qualitat, QpmkZaehl, Msehi, Msehl, Verwmerkm, Kurztext, Result, Sollwert, Toleranzob, Toleranzub, Rueckmelnr, Satzstatus, Equnr, Pruefbemkt, Mbewertg, Pruefer, Pruefdatuv, Pruefdatub, Pruefzeitv, Pruefzeitb, Iststpumf, Anzfehleh, Anzwertg, Ktextmat, Katab1, Katalgart1, Auswmenge1, Codetext, Xstatus, Action, UUID, Udid, Werks) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement statement = App_db.compileStatement(EtQinspData_sql);
                            statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Prueflos"));
                                statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Vornr"));
                                statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Plnty"));
                                statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Plnnr"));
                                statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Plnkn"));
                                statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Merknr"));
                                statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Quantitat"));
                                statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Qualitat"));
                                statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("QpmkZaehl"));
                                statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Msehi"));
                                statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Msehl"));
                                statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Verwmerkm"));
                                statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Kurztext"));
                                statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Result"));
                                statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Sollwert"));
                                statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("Toleranzob"));
                                statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("Toleranzub"));
                                statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Rueckmelnr"));
                                statement.bindString(20, response_data_jsonArray.getJSONObject(j).optString("Satzstatus"));
                                statement.bindString(21, response_data_jsonArray.getJSONObject(j).optString("Equnr"));
                                statement.bindString(22, response_data_jsonArray.getJSONObject(j).optString("Pruefbemkt"));

                                String QUANTITAT = response_data_jsonArray.getJSONObject(j).optString("Quantitat");
                                if(QUANTITAT.equalsIgnoreCase("X"))
                                {
                                    String result = response_data_jsonArray.getJSONObject(j).optString("Result");
                                    if (result != null && !result.equals(""))
                                    {
                                        String fromm = "", too = "";
                                        String from = response_data_jsonArray.getJSONObject(j).optString("Toleranzub");
                                        if (from != null && !from.equals(""))
                                        {
                                            if(from.contains(","))
                                            {
                                                String result1 = from.replace(",",".");
                                                Float dvd = Float.parseFloat(result1);
                                                DecimalFormat df = new DecimalFormat("###.##");
                                                too = df.format(dvd);
                                            }
                                            else
                                            {
                                                Float dvd = Float.parseFloat(from);
                                                DecimalFormat df = new DecimalFormat("###.##");
                                                too = df.format(dvd);
                                            }
                                        }

                                        String to = response_data_jsonArray.getJSONObject(j).optString("Toleranzob");
                                        if (to != null && !to.equals(""))
                                        {
                                            if(to.contains(","))
                                            {
                                                String result1 = to.replace(",",".");
                                                Float dvd = Float.parseFloat(result1);
                                                DecimalFormat df = new DecimalFormat("###.##");
                                                fromm = df.format(dvd);
                                            }
                                            else
                                            {
                                                Float dvd = Float.parseFloat(to);
                                                DecimalFormat df = new DecimalFormat("###.##");
                                                fromm = df.format(dvd);
                                            }
                                        }

                                        if(result.contains(","))
                                        {
                                            String result1 = result.replace(",",".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            String res = df.format(dvd);
                                            Float floatt = Float.parseFloat(res);
                                            Float floatt1 = Float.parseFloat(too);
                                            Float floatt2 = Float.parseFloat(fromm);
                                            if(floatt >= floatt1 && floatt <= floatt2)
                                            {
                                                statement.bindString(23, "A");
                                            }
                                            else
                                            {
                                                statement.bindString(23, "R");
                                            }
                                        }
                                        else
                                        {
                                            if (too != null && !too.equals("") && fromm != null && !fromm.equals(""))
                                            {
                                                Float floatt = Float.parseFloat(result);
                                                Float floatt1 = Float.parseFloat(too);
                                                Float floatt2 = Float.parseFloat(fromm);
                                                if(floatt >= floatt1 && floatt <= floatt2)
                                                {
                                                    statement.bindString(23, "A");
                                                }
                                                else
                                                {
                                                    statement.bindString(23, "R");
                                                }
                                            }
                                            else
                                            {
                                                statement.bindString(23, "R");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        statement.bindString(23, "R");
                                    }
                                }
                                else
                                {
                                    statement.bindString(23, response_data_jsonArray.getJSONObject(j).optString("Mbewertg"));
                                }

                                statement.bindString(24, response_data_jsonArray.getJSONObject(j).optString("Pruefer"));
                                statement.bindString(25, response_data_jsonArray.getJSONObject(j).optString("Pruefdatuv"));
                                statement.bindString(26, response_data_jsonArray.getJSONObject(j).optString("Pruefdatub"));
                                statement.bindString(27, response_data_jsonArray.getJSONObject(j).optString("Pruefzeitv"));
                                statement.bindString(28, response_data_jsonArray.getJSONObject(j).optString("Pruefzeitb"));
                                statement.bindString(29, response_data_jsonArray.getJSONObject(j).optString("Iststpumf"));
                                statement.bindString(30, response_data_jsonArray.getJSONObject(j).optString("Anzfehleh"));
                                statement.bindString(31, response_data_jsonArray.getJSONObject(j).optString("Anzwertg"));
                                statement.bindString(32, response_data_jsonArray.getJSONObject(j).optString("Ktextmat"));
                                statement.bindString(33, response_data_jsonArray.getJSONObject(j).optString("Katab1"));
                                statement.bindString(34, response_data_jsonArray.getJSONObject(j).optString("Katalgart1"));
                                statement.bindString(35, response_data_jsonArray.getJSONObject(j).optString("Auswmenge1"));
                                statement.bindString(36, response_data_jsonArray.getJSONObject(j).optString("Codetext"));
                                statement.bindString(37, response_data_jsonArray.getJSONObject(j).optString("Xstatus"));
                                statement.bindString(38, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                UUID uniqueKey_uuid = UUID.randomUUID();
                                statement.bindString(39, uniqueKey_uuid.toString());
                                statement.bindString(40, response_data_jsonArray.getJSONObject(j).optString("Udid"));
                                statement.bindString(41, response_data_jsonArray.getJSONObject(j).optString("Werks"));
                                statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtQinspData*/



                    /*Reading and Inserting Data into Database Table for EtQudData*/
                    String EtQudData_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtQudData().getResults());
                    if (EtQudData_response_data != null && !EtQudData_response_data.equals("") && !EtQudData_response_data.equals("null"))
                    {
                        JSONArray response_data_jsonArray = new JSONArray(EtQudData_response_data);
                        if(response_data_jsonArray.length() > 0)
                        {
                            String EtQudData_sql = "Insert into EtQudData (Prueflos,Aufnr,Werks, Equnr, Vkatart, Vcodegrp, Vauswahlmg, Vcode, Qkennzahl, Vname, Vdatum, Vaedatum, Vezeitaen, Udtext, Udforce, Rcode, Xstatus, Action, Udid, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtQudData_statement = App_db.compileStatement(EtQudData_sql);
                            EtQudData_statement.clearBindings();
                            for(int j = 0; j < response_data_jsonArray.length(); j++)
                            {
                                EtQudData_statement.bindString(1, response_data_jsonArray.getJSONObject(j).optString("Prueflos"));
                                EtQudData_statement.bindString(2, response_data_jsonArray.getJSONObject(j).optString("Aufnr"));
                                EtQudData_statement.bindString(3, response_data_jsonArray.getJSONObject(j).optString("Werks"));
                                EtQudData_statement.bindString(4, response_data_jsonArray.getJSONObject(j).optString("Equnr"));
                                EtQudData_statement.bindString(5, response_data_jsonArray.getJSONObject(j).optString("Vkatart"));
                                EtQudData_statement.bindString(6, response_data_jsonArray.getJSONObject(j).optString("Vcodegrp"));
                                EtQudData_statement.bindString(7, response_data_jsonArray.getJSONObject(j).optString("Vauswahlmg"));
                                EtQudData_statement.bindString(8, response_data_jsonArray.getJSONObject(j).optString("Vcode"));
                                EtQudData_statement.bindString(9, response_data_jsonArray.getJSONObject(j).optString("Qkennzahl"));
                                EtQudData_statement.bindString(10, response_data_jsonArray.getJSONObject(j).optString("Vname"));
                                EtQudData_statement.bindString(11, response_data_jsonArray.getJSONObject(j).optString("Vdatum"));
                                EtQudData_statement.bindString(12, response_data_jsonArray.getJSONObject(j).optString("Vaedatum"));
                                EtQudData_statement.bindString(13, response_data_jsonArray.getJSONObject(j).optString("Vezeitaen"));
                                EtQudData_statement.bindString(14, response_data_jsonArray.getJSONObject(j).optString("Udtext"));
                                EtQudData_statement.bindString(15, response_data_jsonArray.getJSONObject(j).optString("Udforce"));
                                EtQudData_statement.bindString(16, response_data_jsonArray.getJSONObject(j).optString("Rcode"));
                                EtQudData_statement.bindString(17, response_data_jsonArray.getJSONObject(j).optString("Xstatus"));
                                EtQudData_statement.bindString(18, response_data_jsonArray.getJSONObject(j).optString("Action"));
                                EtQudData_statement.bindString(19, response_data_jsonArray.getJSONObject(j).optString("Udid"));
                                String Vcode = response_data_jsonArray.getJSONObject(j).optString("Vcode");
                                String notes = response_data_jsonArray.getJSONObject(j).optString("Udtext");
                                if (Vcode != null&& !Vcode.equals("") || notes != null&& !notes.equals(""))
                                {
                                    EtQudData_statement.bindString(20, "hide");
                                }
                                else
                                {
                                    EtQudData_statement.bindString(20, "visible");
                                }
                                EtQudData_statement.execute();
                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtQudData*/


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
