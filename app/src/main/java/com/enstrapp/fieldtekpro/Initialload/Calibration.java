package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.ContentValues;
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
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

import java.text.DecimalFormat;
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

public class Calibration {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

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

    public static String Get_Calibration_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD")) {
                /* EtQinspData Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtQinspData);
                String CREATE_TABLE_EtQinspData = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtQinspData + ""
                        + "( "
                        + KEY_EtQinspData_ID + " INTEGER PRIMARY KEY,"//0
                        + KEY_EtQinspData_Aufnr + " TEXT,"//1
                        + KEY_EtQinspData_Prueflos + " TEXT,"//2
                        + KEY_EtQinspData_Vornr + " TEXT,"//3
                        + KEY_EtQinspData_Plnty + " TEXT,"//4
                        + KEY_EtQinspData_Plnnr + " TEXT,"//5
                        + KEY_EtQinspData_Plnkn + " TEXT,"//6
                        + KEY_EtQinspData_Merknr + " TEXT,"//7
                        + KEY_EtQinspData_Quantitat + " TEXT,"//8
                        + KEY_EtQinspData_Qualitat + " TEXT,"//9
                        + KEY_EtQinspData_QpmkZaehl + " TEXT,"//10
                        + KEY_EtQinspData_Msehi + " TEXT,"//11
                        + KEY_EtQinspData_Msehl + " TEXT,"//12
                        + KEY_EtQinspData_Verwmerkm + " TEXT,"//13
                        + KEY_EtQinspData_Kurztext + " TEXT,"//1
                        + KEY_EtQinspData_Result + " TEXT,"//2
                        + KEY_EtQinspData_Sollwert + " TEXT,"//3
                        + KEY_EtQinspData_Toleranzob + " TEXT,"//4
                        + KEY_EtQinspData_Toleranzub + " TEXT,"//5
                        + KEY_EtQinspData_Rueckmelnr + " TEXT,"//6
                        + KEY_EtQinspData_Satzstatus + " TEXT,"//7
                        + KEY_EtQinspData_Equnr + " TEXT,"//8
                        + KEY_EtQinspData_Pruefbemkt + " TEXT,"//8
                        + KEY_EtQinspData_Mbewertg + " TEXT,"//9
                        + KEY_EtQinspData_Pruefer + " TEXT,"//10
                        + KEY_EtQinspData_Pruefdatuv + " TEXT,"//11
                        + KEY_EtQinspData_Pruefdatub + " TEXT,"//12
                        + KEY_EtQinspData_Pruefzeitv + " TEXT,"//13
                        + KEY_EtQinspData_Pruefzeitb + " TEXT,"//1
                        + KEY_EtQinspData_Iststpumf + " TEXT,"//2
                        + KEY_EtQinspData_Anzfehleh + " TEXT,"//3
                        + KEY_EtQinspData_Anzwertg + " TEXT,"//4
                        + KEY_EtQinspData_Ktextmat + " TEXT,"//1
                        + KEY_EtQinspData_Katab1 + " TEXT,"//2
                        + KEY_EtQinspData_Katalgart1 + " TEXT,"//3
                        + KEY_EtQinspData_Auswmenge1 + " TEXT,"//4
                        + KEY_EtQinspData_Codetext + " TEXT,"//4
                        + KEY_EtQinspData_Xstatus + " TEXT,"//6
                        + KEY_EtQinspData_Action + " TEXT,"//7
                        + KEY_EtQinspData_UUID + " TEXT,"//7
                        + KEY_EtQinspData_Udid + " TEXT,"//7
                        + KEY_EtQinspData_Werks + " TEXT"//7
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtQinspData);
                /* EtQinspData Table and Fields Names */

                /* EtQudData Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtQudData);
                String CREATE_TABLE_EtQudData = "CREATE TABLE IF NOT EXISTS " + TABLE_EtQudData + ""
                        + "( "
                        + KEY_EtQudData_ID + " INTEGER PRIMARY KEY,"//0
                        + KEY_EtQudData_Prueflos + " TEXT,"//1
                        + KEY_EtQudData_Aufnr + " TEXT,"//2
                        + KEY_EtQudData_Werks + " TEXT,"//4
                        + KEY_EtQudData_Equnr + " TEXT,"//3
                        + KEY_EtQudData_Vkatart + " TEXT,"//5
                        + KEY_EtQudData_Vcodegrp + " TEXT,"//6
                        + KEY_EtQudData_Vauswahlmg + " TEXT,"//7
                        + KEY_EtQudData_Vcode + " TEXT,"//8
                        + KEY_EtQudData_Qkennzahl + " TEXT,"//9
                        + KEY_EtQudData_Vname + " TEXT,"//10
                        + KEY_EtQudData_Vdatum + " TEXT,"//11
                        + KEY_EtQudData_Vaedatum + " TEXT,"//12
                        + KEY_EtQudData_Vezeitaen + " TEXT,"//13
                        + KEY_EtQudData_Udtext + " TEXT,"//14
                        + KEY_EtQudData_Udforce + " TEXT,"//15
                        + KEY_EtQudData_Rcode + " TEXT,"//16
                        + KEY_EtQudData_Xstatus + " TEXT,"//17
                        + KEY_EtQudData_Action + " TEXT,"//18
                        + KEY_EtQudData_Udid + " TEXT,"//19
                        + KEY_EtQudData_Status + " TEXT"//20
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtQudData);
                /* EtQudData Table and Fields Names */
            } else {
                App_db.execSQL("delete from EtQinspData");
                App_db.execSQL("delete from EtQudData");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"D3", "RD", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure
                    .getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180000, TimeUnit.MILLISECONDS)
                    .writeTimeout(180000, TimeUnit.MILLISECONDS)
                    .readTimeout(180000, TimeUnit.MILLISECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " +
                    Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Calibration_SER> call = service.getCalibrationDetails(url_link, basic, map);
            Response<Calibration_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Calibration_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {

                        /*EtQinspData*/
                        if (response.body().getD().getResults().get(0).getEtQinspData() != null) {
                            if (response.body().getD().getResults().get(0).getEtQinspData().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtQinspData().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Calibration_SER.EtQinspData_Result eQD : response.body().getD().getResults().get(0).getEtQinspData().getResults()) {
                                    values.put("Aufnr, ", eQD.getAufnr());
                                    values.put("Prueflos", eQD.getPrueflos());
                                    values.put("Vornr", eQD.getVornr());
                                    values.put("Plnty", eQD.getPlnty());
                                    values.put("Plnnr", eQD.getPlnnr());
                                    values.put("Plnkn", eQD.getPlnkn());
                                    values.put("Merknr", eQD.getMerknr());
                                    values.put("Quantitat", eQD.getQuantitat());
                                    values.put("Qualitat", eQD.getQualitat());
                                    values.put("QpmkZaehl", eQD.getQpmkZaehl());
                                    values.put("Msehi", eQD.getMsehi());
                                    values.put("Msehl", eQD.getMsehl());
                                    values.put("Verwmerkm", eQD.getVerwmerkm());
                                    values.put("Kurztext", eQD.getKurztext());
                                    values.put("Result", eQD.getResult());
                                    values.put("Sollwert", eQD.getSollwert());
                                    values.put("Toleranzob", eQD.getToleranzob());
                                    values.put("Toleranzub", eQD.getToleranzub());
                                    values.put("Rueckmelnr", eQD.getRueckmelnr());
                                    values.put("Satzstatus", eQD.getSatzstatus());
                                    values.put("Equnr", eQD.getEqunr());
                                    values.put("Pruefbemkt", eQD.getPruefbemkt());
                                    values.put("Mbewertg", eQD.getMbewertg());
                                    values.put("Pruefer", eQD.getPruefer());
                                    values.put("Pruefdatuv", eQD.getPruefdatuv());
                                    values.put("Pruefdatub", eQD.getPruefdatub());
                                    values.put("Pruefzeitv", eQD.getPruefzeitv());
                                    values.put("Pruefzeitb", eQD.getPruefzeitb());
                                    values.put("Iststpumf", eQD.getIststpumf());
                                    values.put("Anzfehleh", eQD.getAnzfehleh());
                                    values.put("Anzwertg", eQD.getAnzwertg());
                                    values.put("Ktextmat", eQD.getKtextmat());
                                    values.put("Katab1", eQD.getKatab1());
                                    values.put("Katalgart1", eQD.getKatalgart1());
                                    values.put("Auswmenge1", eQD.getAuswmenge1());
                                    values.put("Codetext", eQD.getCodetext());
                                    values.put("Xstatus", eQD.getXstatus());
                                    values.put("Action", eQD.getAction());
                                    values.put("UUID", " ");
                                    values.put("Udid", eQD.getUdid());
                                    values.put("Werks", eQD.getWerks());
                                    App_db.insert("EtQinspData", null, values);
                                }
                            }
                        }
                        /*Calibration_SER.EtQinspData etQinspData = results.get(0).getEtQinspData();
                        if (etQinspData != null) {
                            List<Calibration_SER.EtQinspData_Result> etQinspDataResults = etQinspData.getResults();
                            if (etQinspDataResults != null && etQinspDataResults.size() > 0) {
                                String EtQinspData_sql = "Insert into EtQinspData (Aufnr, Prueflos, Vornr, " +
                                        "Plnty, Plnnr, Plnkn, Merknr, Quantitat, Qualitat, QpmkZaehl, Msehi," +
                                        " Msehl, Verwmerkm, Kurztext, Result, Sollwert, Toleranzob, Toleranzub," +
                                        " Rueckmelnr, Satzstatus, Equnr, Pruefbemkt, Mbewertg, Pruefer, " +
                                        "Pruefdatuv, Pruefdatub, Pruefzeitv, Pruefzeitb, Iststpumf, Anzfehleh," +
                                        " Anzwertg, Ktextmat, Katab1, Katalgart1, Auswmenge1, Codetext, Xstatus," +
                                        " Action, UUID, Udid, Werks)" +
                                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                        "?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement = App_db.compileStatement(EtQinspData_sql);
                                statement.clearBindings();
                                for (Calibration_SER.EtQinspData_Result eQ : etQinspDataResults) {
                                    statement.bindString(1, c_e.check_empty(eQ.getAufnr()));
                                    statement.bindString(2, c_e.check_empty(eQ.getPrueflos()));
                                    statement.bindString(3, c_e.check_empty(eQ.getVornr()));
                                    statement.bindString(4, c_e.check_empty(eQ.getPlnty()));
                                    statement.bindString(5, c_e.check_empty(eQ.getPlnnr()));
                                    statement.bindString(6, c_e.check_empty(eQ.getPlnkn()));
                                    statement.bindString(7, c_e.check_empty(eQ.getMerknr()));
                                    statement.bindString(8, c_e.check_empty(eQ.getQuantitat()));
                                    statement.bindString(9, c_e.check_empty(eQ.getQualitat()));
                                    statement.bindString(10, c_e.check_empty(eQ.getQpmkZaehl()));
                                    statement.bindString(11, c_e.check_empty(eQ.getMsehi()));
                                    statement.bindString(12, c_e.check_empty(eQ.getMsehl()));
                                    statement.bindString(13, c_e.check_empty(eQ.getVerwmerkm()));
                                    statement.bindString(14, c_e.check_empty(eQ.getKurztext()));
                                    statement.bindString(15, c_e.check_empty(eQ.getResult()));
                                    statement.bindString(16, c_e.check_empty(eQ.getSollwert()));
                                    statement.bindString(17, c_e.check_empty(eQ.getToleranzob()));
                                    statement.bindString(18, c_e.check_empty(eQ.getToleranzub()));
                                    statement.bindString(19, c_e.check_empty(eQ.getRueckmelnr()));
                                    statement.bindString(20, c_e.check_empty(eQ.getSatzstatus()));
                                    statement.bindString(21, c_e.check_empty(eQ.getEqunr()));
                                    statement.bindString(22, c_e.check_empty(eQ.getPruefbemkt()));
                                    statement.bindString(23, getMbewertg(c_e.check_empty(eQ.getQuantitat()),
                                            c_e.check_empty(eQ.getResult()), c_e.check_empty(eQ.getToleranzub()),
                                            c_e.check_empty(eQ.getToleranzob()), c_e.check_empty(eQ.getMbewertg())));
                                    statement.bindString(24, c_e.check_empty(eQ.getPruefer()));
                                    statement.bindString(25, c_e.check_empty(eQ.getPruefdatuv()));
                                    statement.bindString(26, c_e.check_empty(eQ.getPruefdatub()));
                                    statement.bindString(27, c_e.check_empty(eQ.getPruefzeitv()));
                                    statement.bindString(28, c_e.check_empty(eQ.getPruefzeitb()));
                                    statement.bindString(29, c_e.check_empty(String.valueOf(eQ.getIststpumf())));
                                    statement.bindString(30, c_e.check_empty(String.valueOf(eQ.getAnzfehleh())));
                                    statement.bindString(31, c_e.check_empty(String.valueOf(eQ.getAnzwertg())));
                                    statement.bindString(32, c_e.check_empty(eQ.getKtextmat()));
                                    statement.bindString(33, c_e.check_empty(eQ.getKatab1()));
                                    statement.bindString(34, c_e.check_empty(eQ.getKatalgart1()));
                                    statement.bindString(35, c_e.check_empty(eQ.getAuswmenge1()));
                                    statement.bindString(36, c_e.check_empty(eQ.getCodetext()));
                                    statement.bindString(37, c_e.check_empty(eQ.getXstatus()));
                                    statement.bindString(38, c_e.check_empty(eQ.getAction()));
                                    statement.bindString(39, UUID.randomUUID().toString());
                                    statement.bindString(40, c_e.check_empty(eQ.getUdid()));
                                    statement.bindString(41, c_e.check_empty(eQ.getWerks()));
                                    statement.execute();
                                }
                            }
                        }*/

                        /*EtQudData*/
                        if (response.body().getD().getResults().get(0).getEtQudData() != null) {
                            if (response.body().getD().getResults().get(0).getEtQudData().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtQudData().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Calibration_SER.EtQudData_Result eQD : response.body().getD().getResults().get(0).getEtQudData().getResults()) {
                                    values.put("Aufnr, ", eQD.getAufnr());
                                    values.put("Prueflos", eQD.getPrueflos());
                                    values.put("Werks", eQD.getWerks());
                                    values.put("Equnr", eQD.getEqunr());
                                    values.put("Vkatart", eQD.getVkatart());
                                    values.put("Vcodegrp", eQD.getVcodegrp());
                                    values.put("Vauswahlmg", eQD.getVauswahlmg());
                                    values.put("Vcode", eQD.getVcode());
                                    values.put("Qkennzahl", eQD.getQkennzahl());
                                    values.put("Vname", eQD.getVname());
                                    values.put("Vdatum", eQD.getVdatum());
                                    values.put("Vaedatum", eQD.getVaedatum());
                                    values.put("Vezeitaen", eQD.getVezeitaen());
                                    values.put("Udtext", eQD.getUdtext());
                                    values.put("Udforce", eQD.getUdforce());
                                    values.put("Rcode", eQD.getRcode());
                                    values.put("Xstatus", eQD.getXstatus());
                                    values.put("Action", eQD.getAction());
                                    values.put("Udid", eQD.getUdid());
                                    if(!eQD.getVcode().equals("")||!eQD.getUdtext().equals(""))
                                    values.put("Status", "hide");
                                    else
                                        values.put("Status", "Visible");
                                    App_db.insert("EtQudData", null, values);
                                }
                            }
                        }
                       /* Calibration_SER.EtQudData etQudData = results.get(0).getEtQudData();
                        if (etQudData != null) {
                            List<Calibration_SER.EtQudData_Result> etQudDataResults = etQudData.getResults();
                            if (etQudDataResults != null && etQudDataResults.size() > 0) {
                                String EtQudData_sql = "Insert into EtQudData (Prueflos,Aufnr,Werks, Equnr," +
                                        " Vkatart, Vcodegrp, Vauswahlmg, Vcode, Qkennzahl, Vname, Vdatum," +
                                        " Vaedatum, Vezeitaen, Udtext, Udforce, Rcode, Xstatus, Action, " +
                                        "Udid, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtQudData_statement = App_db.compileStatement(EtQudData_sql);
                                EtQudData_statement.clearBindings();
                                for (Calibration_SER.EtQudData_Result eQU : etQudDataResults) {
                                    EtQudData_statement.bindString(1, c_e.check_empty(eQU.getPrueflos()));
                                    EtQudData_statement.bindString(2, c_e.check_empty(eQU.getAufnr()));
                                    EtQudData_statement.bindString(3, c_e.check_empty(eQU.getWerks()));
                                    EtQudData_statement.bindString(4, c_e.check_empty(eQU.getEqunr()));
                                    EtQudData_statement.bindString(5, c_e.check_empty(eQU.getVkatart()));
                                    EtQudData_statement.bindString(6, c_e.check_empty(eQU.getVcodegrp()));
                                    EtQudData_statement.bindString(7, c_e.check_empty(eQU.getVauswahlmg()));
                                    EtQudData_statement.bindString(8, c_e.check_empty(eQU.getVcode()));
                                    EtQudData_statement.bindString(9, c_e.check_empty(eQU.getQkennzahl()));
                                    EtQudData_statement.bindString(10, c_e.check_empty(eQU.getVname()));
                                    EtQudData_statement.bindString(11, c_e.check_empty(eQU.getVdatum()));
                                    EtQudData_statement.bindString(12, c_e.check_empty(eQU.getVaedatum()));
                                    EtQudData_statement.bindString(13, c_e.check_empty(eQU.getVezeitaen()));
                                    EtQudData_statement.bindString(14, c_e.check_empty(eQU.getUdtext()));
                                    EtQudData_statement.bindString(15, c_e.check_empty(eQU.getUdforce()));
                                    EtQudData_statement.bindString(16, c_e.check_empty(eQU.getRcode()));
                                    EtQudData_statement.bindString(17, c_e.check_empty(eQU.getXstatus()));
                                    EtQudData_statement.bindString(18, c_e.check_empty(eQU.getAction()));
                                    EtQudData_statement.bindString(19, c_e.check_empty(eQU.getUdid()));
                                    if (!c_e.check_empty(eQU.getVcode()).equals("") || !c_e.check_empty(eQU.getUdtext()).equals(""))
                                        EtQudData_statement.bindString(20, "hide");
                                    else
                                        EtQudData_statement.bindString(20, "visible");
                                    EtQudData_statement.execute();
                                }
                            }
                        }
                    }*/
                    App_db.setTransactionSuccessful();

                    Get_Response = "success";
                }finally {
                        App_db.endTransaction();
                    }
                    }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }

    private static String getMbewertg(String quantitat, String result, String toleranzub,
                                      String toleranzob, String mbewertg) {
        if (quantitat.equalsIgnoreCase("X")) {
            if (!result.equals("")) {
                String from = "", to = "";
                if (!toleranzub.equals("")) {
                    if (toleranzub.contains(","))
                        to = decimalFormatterComma(toleranzub);
                    else
                        to = decimalFormatter(toleranzub);
                }

                if (!toleranzob.equals("")) {
                    if (toleranzob.contains(","))
                        from = decimalFormatterComma(toleranzob);
                    else
                        from = decimalFormatter(toleranzob);
                }

                if (result.contains(",")) {
                    Float floatResult = Float.parseFloat(
                            decimalFormatterComma(
                                    result));
                    Float floatTo = Float.parseFloat(to);
                    Float floatFrom = Float.parseFloat(from);
                    if (floatResult >= floatTo && floatResult <= floatFrom)
                        return "A";
                    else
                        return "R";
                } else {
                    if (to != null && !to.equals("") && from != null && !from.equals("")) {
                        Float floatResult = Float.parseFloat(result);
                        Float floatTo = Float.parseFloat(to);
                        Float floatFrom = Float.parseFloat(from);
                        if (floatResult >= floatTo && floatResult <= floatFrom)
                            return "A";
                        else
                            return "R";
                    } else {
                        return "R";
                    }
                }
            } else {
                return "R";
            }
        } else {
            return mbewertg;
        }
    }

    private static String decimalFormatterComma(String result) {
        return new DecimalFormat("###.##")
                .format(Float.parseFloat(result.replace(",", ".")));
    }

    private static String decimalFormatter(String result) {
        return new DecimalFormat("###.##").format(Float.parseFloat(result));
    }

}
