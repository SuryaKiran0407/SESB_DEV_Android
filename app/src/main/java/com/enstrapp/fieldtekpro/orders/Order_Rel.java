package com.enstrapp.fieldtekpro.orders;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Initialload.Orders_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class Order_Rel {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty checkempty = new Check_Empty();
    private static StringBuffer message = new StringBuffer();
    private static boolean success = false;

    public static String Get_Data(Context activity, String transmit_type, String ivCommit,
                                  String operation, String orderId) {
        try {
            Get_Response = "";
            success = false;
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",
                    null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                    " ? and Zactivity = ? and Endpoint = ?", new String[]{"W", "RL", "ODATA"});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            } else {
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = activity.getString(R.string.ip_address);
            Map<String, String> map = new HashMap<>();
            map.put("IvUser", username.toUpperCase().toString());
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("IvAufnr", orderId);
            map.put("Operation", operation);
            map.put("IvTransmitType", transmit_type);
            map.put("IvCommit", ivCommit);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120000, TimeUnit.MILLISECONDS)
                    .writeTimeout(120000, TimeUnit.SECONDS)
                    .readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),
                    Base64.NO_WRAP);
            Call<Orders_SER> call = service.getORDERDetails(url_link, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {

                    message = new StringBuffer();
                    try {
                        if (response.body().getD().getResults().get(0).getEvMessageRe().getResults()!=null) {
                            if (response.body().getD().getResults().get(0).getEvMessageRe().getResults() != null && response.body().getD().getResults().get(0).getEvMessageRe().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Orders_SER.EvMessageRe_Result etMessages_result : response.body().getD().getResults().get(0).getEvMessageRe().getResults()) {
                                    values.put("Message", etMessages_result.getMessage());
                                    message.append(etMessages_result.getMessage());

                                }
                            }
                        }
                        if (message.toString().startsWith("S")) {
                            success = true;
                            App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtWcmWwData where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtWcmWaData where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtWcmWdData where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{orderId});
                            App_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{orderId});

                            try {
                                if (response.body().getD().getEtWcmWaChkReq().getResults() != null) {
                                    if (response.body().getD().getEtWcmWaChkReq().getResults() != null && response.body().getD().getEtWcmWaChkReq().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWaChkReq_Result etWcmWaChkReq_result : response.body().getD().getEtWcmWaChkReq().getResults()) {
                                            values.put("Wapinr", etWcmWaChkReq_result.getWapinr());
                                            App_db.delete("EtWcmWaChkReq", "Wapinr = ?", new String[]{"wapinr"});
                                            App_db.delete("EtWcmWaData", "Wapinr = ?", new String[]{"wapinr"});
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (response.body().getD().getEtWcmWdData().getResults() != null) {
                                    if (response.body().getD().getEtWcmWdData().getResults() != null && response.body().getD().getEtWcmWdData().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (Orders_SER.EtWcmWdData_Result etWcmWdData_result : response.body().getD().getEtWcmWdData().getResults()) {
                                            values.put("Wcnr", etWcmWdData_result.getWapinr());
                                            App_db.delete("EtWcmWdItemData", "Wcnr = ?", new String[]{"Wcnr"});

                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            success = false;
                            Get_Response = message.toString();
                        }
                    } catch (Exception e) {
                        success = false;
                    }
                }
                if (success) {
                    /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/
                    if (response.body().getD().getResults().get(0).getEtOrderHeader().getResults() != null) {
                        if (response.body().getD().getResults().get(0).getEtOrderHeader().getResults() != null && response.body().getD().getResults().get(0).getEtOrderHeader().getResults().size() > 0) {
                            ContentValues values = new ContentValues();
                            for (Orders_SER.EtOrderHeader_Result etOrderHeader_result : response.body().getD().getResults().get(0).getEtOrderHeader().getResults()) {
                                values.put("UUID", " ");
                                values.put("Aufnr", etOrderHeader_result.getAufnr());
                                App_db.insert("EtWcmWdItemData", null, values);

                            }
                        }
                    }
                    /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                    App_db.beginTransaction();

                    /*Reading and Inserting Data into Database Table for EtOrderHeader*/
                    try {
                        try {
                            if (response.body().getD().getResults().get(0).getEtOrderHeader().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderHeader().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderHeader().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderHeader_Result orderHeader_result : response.body().getD().getResults().get(0).getEtOrderHeader().getResults()) {
                                        values.put("UUID", orderHeader_result.getAufnr());
                                        values.put("Aufnr", orderHeader_result.getAufnr());
                                        String Aufnr_id = orderHeader_result.getAufnr();
                                        values.put("Auart", orderHeader_result.getAuart());
                                        values.put("Ktext", orderHeader_result.getKtext());
                                        values.put("Ilart", orderHeader_result.getIlart());
                                        values.put("Ernam", orderHeader_result.getErnam());
                                        values.put("Erdat", orderHeader_result.getErdat());
                                        values.put("Priok", orderHeader_result.getPriok());
                                        values.put("Equnr", orderHeader_result.getEqunr());
                                        values.put("Strno", orderHeader_result.getStrno());
                                        values.put("TplnrInt", orderHeader_result.getTplnrInt());
                                        values.put("Bautl", orderHeader_result.getBautl());
                                        values.put("Gltrp", orderHeader_result.getGltrp());
                                        values.put("Gstrp", orderHeader_result.getGstrp());
                                        values.put("Docs", orderHeader_result.getDocs());
                                        values.put("Permits", orderHeader_result.getPermits());
                                        values.put("Altitude", orderHeader_result.getAltitude());
                                        values.put("Latitude", orderHeader_result.getLatitude());
                                        values.put("Longitude", orderHeader_result.getLongitude());
                                        values.put("Qmnum", orderHeader_result.getQmnum());
                                        values.put("Closed", orderHeader_result.getClosed());
                                        values.put("Completed", orderHeader_result.getCompleted());
                                        values.put("Ingrp", orderHeader_result.getIngrp());
                                        values.put("Arbpl", orderHeader_result.getArbpl());
                                        values.put("Werks", orderHeader_result.getWerks());
                                        values.put("Bemot", orderHeader_result.getBemot());
                                        values.put("Aueru", orderHeader_result.getAueru());
                                        values.put("Auarttext", orderHeader_result.getAuarttext());
                                        values.put("Qmartx", orderHeader_result.getQmartx());
                                        values.put("Qmtxt", orderHeader_result.getQmtxt());
                                        values.put("Pltxt", orderHeader_result.getPltxt());
                                        values.put("Eqktx", orderHeader_result.getEqktx());
                                        values.put("Priokx", orderHeader_result.getPriokx());
                                        values.put("Ilatx", orderHeader_result.getIlatx());
                                        values.put("Plantname", orderHeader_result.getPlantname());
                                        values.put("Wkctrname", orderHeader_result.getWkctrname());
                                        values.put("Ingrpname", orderHeader_result.getIngrpname());
                                        values.put("Maktx", orderHeader_result.getMaktx());
                                        values.put("Xstatus", orderHeader_result.getXstatus());
                                        values.put("Usr01", orderHeader_result.getUsr01());
                                        values.put("Usr02", orderHeader_result.getUsr02());
                                        values.put("Usr03", orderHeader_result.getUsr03());
                                        values.put("Usr04", orderHeader_result.getUsr04());
                                        values.put("Usr05", orderHeader_result.getUsr05());
                                        values.put("Kokrs", orderHeader_result.getKokrs());
                                        values.put("Kostl", orderHeader_result.getKostl());
                                        values.put("Anlzu", orderHeader_result.getAnlzu());
                                        values.put("Anlzux", orderHeader_result.getAnlzux());
                                        values.put("Ausvn", (String) orderHeader_result.getAusvn());
                                        values.put("Ausbs", (String) orderHeader_result.getAusbs());
                                        values.put("Auswk", orderHeader_result.getAuswk());
                                        values.put("Qmnam", orderHeader_result.getQmnam());
                                        values.put("ParnrVw", orderHeader_result.getParnrVw());
                                        values.put("NameVw", orderHeader_result.getNameVw());
                                        values.put("Posid", orderHeader_result.getPosid());
                                        values.put("Revnr", orderHeader_result.getRevnr());
                                        App_db.insert("DUE_ORDERS_EtOrderHeader", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/


                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtOrderOperations().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderOperations().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderOperations().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderOperations_Result orderOperations : response.body().getD().getResults().get(0).getEtOrderOperations().getResults()) {
                                        values.put("UUID", orderOperations.getAufnr());
                                        values.put("Aufnr", orderOperations.getAufnr());
                                        String Aufnr_id = orderOperations.getAufnr();
                                        values.put("Vornr", orderOperations.getVornr());
                                        values.put("Uvorn", orderOperations.getUvorn());
                                        values.put("Ltxa1", orderOperations.getLtxa1());
                                        values.put("Arbpl", orderOperations.getArbpl());
                                        values.put("Werks", orderOperations.getWerks());
                                        values.put("Steus", orderOperations.getSteus());
                                        values.put("Larnt", orderOperations.getLarnt());
                                        values.put("Dauno", orderOperations.getDauno());
                                        values.put("Daune", orderOperations.getDaune());
                                        values.put("Fsavd", orderOperations.getFsavd());
                                        values.put("Ssedd", orderOperations.getSsedd());
                                        values.put("Pernr", orderOperations.getPernr());
                                        values.put("Asnum", orderOperations.getAsnum());
                                        values.put("Plnty", orderOperations.getPlnty());
                                        values.put("Plnal", orderOperations.getPlnal());
                                        values.put("Plnnr", orderOperations.getPlnnr());
                                        values.put("Rueck", orderOperations.getRueck());
                                        values.put("Aueru", orderOperations.getAueru());
                                        values.put("ArbplText", orderOperations.getArbplText());
                                        values.put("WerksText", orderOperations.getWerksText());
                                        values.put("SteusText", orderOperations.getSteusText());
                                        values.put("LarntText", orderOperations.getLarntText());
                                        values.put("Usr01", orderOperations.getUsr01());
                                        values.put("Usr02", orderOperations.getUsr02());
                                        values.put("Usr03", orderOperations.getUsr03());
                                        values.put("Usr04", orderOperations.getUsr04());
                                        values.put("Usr05", orderOperations.getUsr05());
                                        values.put("Action", orderOperations.getAction());
                                        App_db.insert("DUE_ORDERS_EtOrderOperations", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/


                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/
                        try {

                            if (response.body().getD().getResults().get(0).getEtOrderLongtext().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderLongtext().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderLongtext().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderLongtext_Result orderLongtext_result : response.body().getD().getResults().get(0).getEtOrderLongtext().getResults()) {
                                        values.put("UUID", orderLongtext_result.getAufnr());
                                        values.put("Aufnr", orderLongtext_result.getAufnr());
                                        values.put("Activity", orderLongtext_result.getActivity());
                                        values.put("TextLine", orderLongtext_result.getTextLine());
                                        values.put("Tdid", orderLongtext_result.getTdid());
                                        App_db.insert("DUE_ORDERS_Longtext", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/

                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtOrderOlist().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderOlist().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderOlist().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderOlist_Result orderOlist_result : response.body().getD().getResults().get(0).getEtOrderOlist().getResults()) {
                                        values.put("UUID", orderOlist_result.getAufnr());
                                        values.put("Aufnr", orderOlist_result.getAufnr());
                                        values.put("Obknr", orderOlist_result.getObknr());
                                        values.put("Obzae", orderOlist_result.getObzae());
                                        values.put("Qmnum", orderOlist_result.getQmnum());
                                        values.put("Equnr", orderOlist_result.getEqunr());
                                        values.put("Strno", orderOlist_result.getStrno());
                                        values.put("Tplnr", orderOlist_result.getTplnr());
                                        values.put("Bautl", orderOlist_result.getBautl());
                                        values.put("Qmtxt", orderOlist_result.getQmtxt());
                                        values.put("Pltxt", orderOlist_result.getPltxt());
                                        values.put("Eqktx", orderOlist_result.getEqktx());
                                        values.put("Maktx", orderOlist_result.getMaktx());
                                        values.put("Action", orderOlist_result.getAction());
                                        App_db.insert("EtOrderOlist", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/

                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtOrderStatus() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderStatus().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderStatus().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderStatus_Result orderStatus_result : response.body().getD().getResults().get(0).getEtOrderStatus().getResults()) {
                                        values.put("UUID", orderStatus_result.getAufnr());
                                        values.put("Aufnr", orderStatus_result.getAufnr());
                                        values.put("Vornr", orderStatus_result.getVornr());
                                        values.put("Objnr", orderStatus_result.getObjnr());
                                        values.put("Stsma", orderStatus_result.getStsma());
                                        values.put("Inist", orderStatus_result.getInist());
                                        values.put("Stonr", orderStatus_result.getStonr());
                                        values.put("Hsonr", orderStatus_result.getHsonr());
                                        values.put("Nsonr", orderStatus_result.getNsonr());
                                        values.put("Stat", orderStatus_result.getStat());
                                        values.put("Act", orderStatus_result.getAct());
                                        values.put("Txt04", orderStatus_result.getTxt04());
                                        values.put("Txt30", orderStatus_result.getTxt30());
                                        values.put("Action", orderStatus_result.getAction());
                                        App_db.insert("EtOrderStatus", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/

                        /*Reading and Inserting Data into Database Table for EtDocs*/
                        try {

                            if (response.body().getD().getResults().get(0).getEtDocs().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtDocs().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtDocs().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtDocs_Result etDocs_result : response.body().getD().getResults().get(0).getEtDocs().getResults()) {
                                        values.put("UUID", etDocs_result.getZobjid());
                                        values.put("Zobjid", etDocs_result.getZobjid());
                                        values.put("Zdoctype", etDocs_result.getZdoctype());
                                        values.put("ZdoctypeItem", etDocs_result.getZdoctypeItem());
                                        values.put("Filename", etDocs_result.getFilename());
                                        values.put("Filetype", etDocs_result.getFiletype());
                                        values.put("Fsize", etDocs_result.getFsize());
                                        values.put("Content", etDocs_result.getContent());
                                        values.put("DocID", etDocs_result.getDocID());
                                        values.put("DocType", etDocs_result.getDocType());
                                        values.put("Objtype", etDocs_result.getObjtype());
                                        values.put("Contentx", etDocs_result.getContentX());
                                        App_db.insert("DUE_ORDERS_EtDocs", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtDocs*/

                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWwData().getResults() != null)
                                if (response.body().getD().getEtWcmWwData().getResults() != null &&
                                        response.body().getD().getEtWcmWwData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWwData_Result wcmWwData_result : response.body().getD().getEtWcmWwData().getResults()) {
                                        values.put("UUID", wcmWwData_result.getAufnr());
                                        values.put("Aufnr", wcmWwData_result.getAufnr());
                                        values.put("Objart", wcmWwData_result.getObjart());
                                        values.put("Wapnr", wcmWwData_result.getWapnr());
                                        values.put("Iwerk", wcmWwData_result.getIwerk());
                                        values.put("Usage", wcmWwData_result.getUsage());
                                        values.put("Usagex", wcmWwData_result.getUsagex());
                                        values.put("Train", wcmWwData_result.getTrain());
                                        values.put("Trainx", wcmWwData_result.getTrainx());
                                        values.put("Anlzu", wcmWwData_result.getAnlzu());
                                        values.put("Anlzux", wcmWwData_result.getAnlzux());
                                        values.put("Etape", wcmWwData_result.getEtape());
                                        values.put("Etapex", wcmWwData_result.getEtapex());
                                        values.put("Rctime", wcmWwData_result.getRctime());
                                        values.put("Rcunit", wcmWwData_result.getRcunit());
                                        values.put("Priok", wcmWwData_result.getPriok());
                                        values.put("Priokx", wcmWwData_result.getPriokx());
                                        values.put("Stxt", wcmWwData_result.getStxt());
                                        values.put("Datefr", wcmWwData_result.getDatefr());
                                        values.put("Timefr", wcmWwData_result.getTimefr());
                                        values.put("Dateto", wcmWwData_result.getDateto());
                                        values.put("Timeto", wcmWwData_result.getTimeto());
                                        values.put("Objnr", wcmWwData_result.getObjnr());
                                        values.put("Crea", wcmWwData_result.getCrea());
                                        values.put("Prep", wcmWwData_result.getPrep());
                                        values.put("Comp", wcmWwData_result.getComp());
                                        values.put("Appr", wcmWwData_result.getAppr());
                                        values.put("Pappr", wcmWwData_result.getPappr());
                                        values.put("Action", wcmWwData_result.getAction());
                                        values.put("Begru", wcmWwData_result.getBegru());
                                        values.put("Begtx", wcmWwData_result.getBegtx());
                                        App_db.insert("EtWcmWwData", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/

                        /*Reading and Inserting Data into Database Table for EtWcmWaData*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWaData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWaData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWaData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWaData_Result wcmWaData_result : response.body().getD().getResults().get(0).getEtWcmWaData().getResults()) {
                                        values.put("UUID", wcmWaData_result.getAufnr());
                                        values.put("Aufnr", wcmWaData_result.getAufnr());
                                        values.put("Objart", wcmWaData_result.getObjart());
                                        values.put("Wapinr", wcmWaData_result.getWapinr());
                                        values.put("Iwerk", wcmWaData_result.getIwerk());
                                        values.put("Objtyp", wcmWaData_result.getObjtyp());
                                        values.put("Usage", wcmWaData_result.getUsage());
                                        values.put("Usagex", wcmWaData_result.getUsagex());
                                        values.put("Train", wcmWaData_result.getTrain());
                                        values.put("Trainx", wcmWaData_result.getTrainx());
                                        values.put("Anlzu", wcmWaData_result.getAnlzu());
                                        values.put("Anlzux", wcmWaData_result.getAnlzux());
                                        values.put("Etape", wcmWaData_result.getEtape());
                                        values.put("Etapex", wcmWaData_result.getEtapex());
                                        values.put("Stxt", wcmWaData_result.getStxt());
                                        values.put("Datefr", wcmWaData_result.getDatefr());
                                        values.put("Timefr", wcmWaData_result.getTimefr());
                                        values.put("Dateto", wcmWaData_result.getDateto());
                                        values.put("Timeto", wcmWaData_result.getTimeto());
                                        values.put("Priok", wcmWaData_result.getPriok());
                                        values.put("Priokx", wcmWaData_result.getPriokx());
                                        values.put("Rctime", wcmWaData_result.getRctime());
                                        values.put("Rcunit", wcmWaData_result.getRcunit());
                                        values.put("Objnr", wcmWaData_result.getObjnr());
                                        values.put("Refobj", wcmWaData_result.getRefobj());
                                        values.put("Crea", wcmWaData_result.getCrea());
                                        values.put("Prep", wcmWaData_result.getPrep());
                                        values.put("Comp", wcmWaData_result.getComp());
                                        values.put("Appr", wcmWaData_result.getAppr());
                                        values.put("Action", wcmWaData_result.getAction());
                                        values.put("Begru", wcmWaData_result.getBegru());
                                        values.put("Begtx", wcmWaData_result.getBegtx());
                                        values.put("Extperiod", wcmWaData_result.getExtperiod());
                                        App_db.insert("EtWcmWaData", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWaData*/

                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWaChkReq_Result wcmWaChkReq_result : response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults()) {

                                        values.put("Wapinr", wcmWaChkReq_result.getWapinr());
                                        values.put("Wapityp", wcmWaChkReq_result.getWapityp());
                                        values.put("Needid", wcmWaChkReq_result.getNeedid());
                                        values.put("Value", wcmWaChkReq_result.getValue());
                                        values.put("ChkPointType", wcmWaChkReq_result.getChkPointType());
                                        values.put("Desctext", wcmWaChkReq_result.getDesctext());
                                        values.put("Tplnr", wcmWaChkReq_result.getTplnr());
                                        values.put("Wkgrp", wcmWaChkReq_result.getWkgrp());
                                        values.put("Needgrp", wcmWaChkReq_result.getNeedgrp());
                                        values.put("Equnr", wcmWaChkReq_result.getEqunr());
                                        values.put("Wkid", wcmWaChkReq_result.getWkid());
                                        App_db.insert("EtWcmWaChkReq", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/

                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWdData().getResults() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWdData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWdData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWdData_Result wcmWdData_result : response.body().getD().getResults().get(0).getEtWcmWdData().getResults()) {
                                        values.put("Aufnr", wcmWdData_result.getAufnr());
                                        values.put("Objart", wcmWdData_result.getObjart());
                                        values.put("Wcnr", wcmWdData_result.getWcnr());
                                        values.put("Iwerk", wcmWdData_result.getIwerk());
                                        values.put("Objtyp", wcmWdData_result.getObjtyp());
                                        values.put("Usage", wcmWdData_result.getUsage());
                                        values.put("Usagex", wcmWdData_result.getUsagex());
                                        values.put("Train", wcmWdData_result.getTrain());
                                        values.put("Trainx", wcmWdData_result.getTrainx());
                                        values.put("Anlzu", wcmWdData_result.getAnlzu());
                                        values.put("Anlzux", wcmWdData_result.getAnlzux());
                                        values.put("Etape", wcmWdData_result.getEtape());
                                        values.put("Etapex", wcmWdData_result.getEtapex());
                                        values.put("Stxt", wcmWdData_result.getStxt());
                                        values.put("Datefr", wcmWdData_result.getDatefr());
                                        values.put("Timefr", wcmWdData_result.getTimefr());
                                        values.put("Dateto", wcmWdData_result.getDateto());
                                        values.put("Timeto", wcmWdData_result.getTimeto());
                                        values.put("Priok", wcmWdData_result.getPriok());
                                        values.put("Priokx", wcmWdData_result.getPriokx());
                                        values.put("Rctime", wcmWdData_result.getRctime());
                                        values.put("Rcunit", wcmWdData_result.getRcunit());
                                        values.put("Objnr", wcmWdData_result.getObjnr());
                                        values.put("Refobj", wcmWdData_result.getRefobj());
                                        values.put("Crea", wcmWdData_result.getCrea());
                                        values.put("Prep", wcmWdData_result.getPrep());
                                        values.put("Comp", wcmWdData_result.getComp());
                                        values.put("Appr", wcmWdData_result.getAppr());
                                        values.put("Action", wcmWdData_result.getAction());
                                        values.put("Begru", wcmWdData_result.getBegru());
                                        values.put("Begtx", wcmWdData_result.getBegtx());
                                        App_db.insert("EtWcmWdData", null, values);

                                        /*EtWcmWdDataTagtext*/
                                        if (wcmWdData_result.getEtWcmWdDataTagtext() != null)
                                            if (wcmWdData_result.getEtWcmWdDataTagtext().getResults() != null
                                                    && wcmWdData_result.getEtWcmWdDataTagtext().getResults().size() > 0) {
                                                ContentValues valuesTag = new ContentValues();
                                                for (Orders_SER.EtWcmWdDataTagtext_Result wcmWdDataTagtext_result :
                                                        wcmWdData_result.getEtWcmWdDataTagtext().getResults()) {
                                                    valuesTag.put("Aufnr", wcmWdDataTagtext_result.getAufnr());
                                                    valuesTag.put("Wcnr", wcmWdDataTagtext_result.getWcnr());
                                                    valuesTag.put("Objtype", wcmWdDataTagtext_result.getObjtype());
                                                    valuesTag.put("FormatCol", wcmWdDataTagtext_result.getFormatCol());
                                                    valuesTag.put("TextLine", wcmWdDataTagtext_result.getTextLine());
                                                    valuesTag.put("Action", wcmWdDataTagtext_result.getAction());
                                                    App_db.insert("EtWcmWdDataTagtext", null, valuesTag);
                                                }
                                            }

                                        /*EtWcmWdDataUntagtext*/
                                        if (wcmWdData_result.getEtWcmWdDataUntagtext() != null)
                                            if (wcmWdData_result.getEtWcmWdDataUntagtext().getResults() != null
                                                    && wcmWdData_result.getEtWcmWdDataUntagtext().getResults().size() > 0) {
                                                ContentValues valuesUnTag = new ContentValues();
                                                for (Orders_SER.EtWcmWdDataTagtext_Result wcmWdDataUntagtext :
                                                        wcmWdData_result.getEtWcmWdDataUntagtext().getResults()) {
                                                    valuesUnTag.put("Aufnr", wcmWdDataUntagtext.getAufnr());
                                                    valuesUnTag.put("Wcnr", wcmWdDataUntagtext.getWcnr());
                                                    valuesUnTag.put("Objtype", wcmWdDataUntagtext.getObjtype());
                                                    valuesUnTag.put("FormatCol", wcmWdDataUntagtext.getFormatCol());
                                                    valuesUnTag.put("TextLine", wcmWdDataUntagtext.getTextLine());
                                                    valuesUnTag.put("Action", wcmWdDataUntagtext.getAction());
                                                    App_db.insert("EtWcmWdDataUntagtext", null, valuesUnTag);
                                                }
                                            }
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/

                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWdItemData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWdItemData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWdItemData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWdItemData_Result wcmWdItemData_result : response.body().getD().getResults().get(0).getEtWcmWdItemData().getResults()) {
                                        values.put("Wcnr", wcmWdItemData_result.getWcnr());
                                        values.put("Wcitm", wcmWdItemData_result.getWcitm());
                                        values.put("Objnr", wcmWdItemData_result.getObjnr());
                                        values.put("Itmtyp", wcmWdItemData_result.getItmtyp());
                                        values.put("Seq", wcmWdItemData_result.getSeq());
                                        values.put("Pred", wcmWdItemData_result.getPred());
                                        values.put("Succ", wcmWdItemData_result.getSucc());
                                        values.put("Ccobj", wcmWdItemData_result.getCcobj());
                                        values.put("Cctyp", wcmWdItemData_result.getCctyp());
                                        values.put("Stxt", wcmWdItemData_result.getStxt());
                                        values.put("Tggrp", wcmWdItemData_result.getTggrp());
                                        values.put("Tgstep", wcmWdItemData_result.getTgstep());
                                        values.put("Tgproc", wcmWdItemData_result.getTgproc());
                                        values.put("Tgtyp", wcmWdItemData_result.getTgtyp());
                                        values.put("Tgseq", wcmWdItemData_result.getTgseq());
                                        values.put("Tgtxt", wcmWdItemData_result.getTgtxt());
                                        values.put("Unstep", wcmWdItemData_result.getUnstep());
                                        values.put("Unproc", wcmWdItemData_result.getUnproc());
                                        values.put("Untyp", wcmWdItemData_result.getUntyp());
                                        values.put("Unseq", wcmWdItemData_result.getUnseq());
                                        values.put("Untxt", wcmWdItemData_result.getUntxt());
                                        values.put("Phblflg", wcmWdItemData_result.getPhblflg());
                                        values.put("Phbltyp", wcmWdItemData_result.getPhbltyp());
                                        values.put("Phblnr", wcmWdItemData_result.getPhblnr());
                                        values.put("Tgflg", wcmWdItemData_result.getTgflg());
                                        values.put("Tgform", wcmWdItemData_result.getTgform());
                                        values.put("Tgnr", wcmWdItemData_result.getTgnr());
                                        values.put("Unform", wcmWdItemData_result.getUnform());
                                        values.put("Unnr", wcmWdItemData_result.getUnnr());
                                        values.put("Control", wcmWdItemData_result.getControl());
                                        values.put("Location", wcmWdItemData_result.getLocation());
                                        values.put("Refobj", wcmWdItemData_result.getRefobj());
                                        values.put("Action", wcmWdItemData_result.getAction());
                                        values.put("Btg", wcmWdItemData_result.getBtg());
                                        values.put("Etg", wcmWdItemData_result.getEtg());
                                        values.put("Bug", wcmWdItemData_result.getBug());
                                        values.put("Eug", wcmWdItemData_result.getEug());
                                        App_db.insert("EtWcmWdItemData", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/

                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtWcmWcagns() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWcagns().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtWcmWcagns().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWcagns_Result wcmWcagns_result : response.body().getD().getEtWcmWcagns().getResults()) {
                                        values.put("UUID", wcmWcagns_result.getAufnr());
                                        values.put("Aufnr", wcmWcagns_result.getAufnr());
                                        values.put("Objnr", wcmWcagns_result.getObjnr());
                                        values.put("Counter", wcmWcagns_result.getCounter());
                                        values.put("Objart", wcmWcagns_result.getObjart());
                                        values.put("Objtyp", wcmWcagns_result.getObjtyp());
                                        values.put("Pmsog", wcmWcagns_result.getPmsog());
                                        values.put("Gntxt", wcmWcagns_result.getGntxt());
                                        values.put("Geniakt", wcmWcagns_result.getGeniakt());
                                        values.put("Genvname", wcmWcagns_result.getGenvname());
                                        values.put("Action", wcmWcagns_result.getAction());
                                        values.put("Werks", wcmWcagns_result.getWerks());
                                        values.put("Crname", wcmWcagns_result.getCrname());
                                        values.put("Hilvl", wcmWcagns_result.getHilvl());
                                        values.put("Procflg", wcmWcagns_result.getProcflg());
                                        values.put("Direction", wcmWcagns_result.getDirection());
                                        values.put("Copyflg", wcmWcagns_result.getCopyflg());
                                        values.put("Mandflg", wcmWcagns_result.getMandflg());
                                        values.put("Deacflg", wcmWcagns_result.getDeacflg());
                                        values.put("Status", wcmWcagns_result.getStatus());
                                        values.put("Asgnflg", wcmWcagns_result.getAsgnflg());
                                        values.put("Autoflg", wcmWcagns_result.getAutoflg());
                                        values.put("Agent", wcmWcagns_result.getAgent());
                                        values.put("Valflg", wcmWcagns_result.getValflg());
                                        values.put("Wcmuse", wcmWcagns_result.getWcmuse());
                                        values.put("Gendatum", wcmWcagns_result.getGendatum());
                                        values.put("Gentime", wcmWcagns_result.getGentime());
                                        App_db.insert("EtWcmWcagns", null, values);
                                    }
                                }
                        } catch (Exception e) {

                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/

                        /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                        try {
                            if (response.body().getD().getResults().get(0).getEtOrderComponents() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderComponents().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderComponents().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderComponents_Result etOrderComponents_result :response.body().getD().getResults().get(0).getEtOrderComponents().getResults()) {
                                        values.put("UUID", etOrderComponents_result.getAufnr());
                                        values.put("Aufnr", etOrderComponents_result.getAufnr());
                                        values.put("Vornr", etOrderComponents_result.getVornr());
                                        values.put("Uvorn", etOrderComponents_result.getUvorn());
                                        values.put("Rsnum", etOrderComponents_result.getRsnum());
                                        values.put("Rspos", etOrderComponents_result.getRspos());
                                        values.put("Matnr", etOrderComponents_result.getMatnr());
                                        values.put("Werks", etOrderComponents_result.getWerks());
                                        values.put("Lgort", etOrderComponents_result.getLgort());
                                        values.put("Posnr", etOrderComponents_result.getPosnr());
                                        values.put("Bdmng", etOrderComponents_result.getBdmng());
                                        values.put("Meins", etOrderComponents_result.getMeins());
                                        values.put("Postp", etOrderComponents_result.getPostp());
                                        values.put("MatnrText", etOrderComponents_result.getMatnrText());
                                        values.put("WerksText", etOrderComponents_result.getWerksText());
                                        values.put("LgortText", etOrderComponents_result.getLgortText());
                                        values.put("PostpText", etOrderComponents_result.getPostpText());
                                        values.put("Usr01", etOrderComponents_result.getUsr01());
                                        values.put("Usr02", etOrderComponents_result.getUsr02());
                                        values.put("Usr03", etOrderComponents_result.getUsr03());
                                        values.put("Usr04", etOrderComponents_result.getUsr04());
                                        values.put("Usr05", etOrderComponents_result.getUsr05());
                                        values.put("Action", etOrderComponents_result.getAction());
                                        values.put("Wempf", etOrderComponents_result.getWempf());
                                        values.put("Ablad", etOrderComponents_result.getAblad());
                                        App_db.insert("EtOrderComponents", null, values);
                                        if (etOrderComponents_result.getEtOrderComponentsFields() != null)
                                            if (etOrderComponents_result.getEtOrderComponentsFields().getResults() != null
                                                    &&etOrderComponents_result.getEtOrderComponentsFields().getResults().size() > 0) {
                                                ContentValues ValuesOCf = new ContentValues();
                                                for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields : etOrderComponents_result.getEtOrderComponentsFields().getResults()) {
                                                    ValuesOCf.put("UUID", etOrderComponents_result.getAufnr());
                                                    ValuesOCf.put("Aufnr", etOrderComponents_result.getAufnr());
                                                    ValuesOCf.put("Zdoctype", etOrderHeaderFields.getZdoctype());
                                                    ValuesOCf.put("ZdoctypeItem", etOrderHeaderFields.getZdoctypeItem());
                                                    ValuesOCf.put("Tabname", etOrderHeaderFields.getTabname());
                                                    ValuesOCf.put("Fieldname", etOrderHeaderFields.getFieldname());
                                                    ValuesOCf.put("Value", etOrderHeaderFields.getValue());
                                                    ValuesOCf.put("Flabel", etOrderHeaderFields.getFlabel());
                                                    ValuesOCf.put("Sequence", etOrderHeaderFields.getSequence());
                                                    ValuesOCf.put("Length", etOrderHeaderFields.getLength());
                                                    ValuesOCf.put("Datatype", etOrderHeaderFields.getDatatype());
                                                    ValuesOCf.put("OperationID", etOrderComponents_result.getVornr());
                                                    ValuesOCf.put("PartID", etOrderComponents_result.getPosnr());
                                                    App_db.insert("DUE_ORDERS_EtOrderComponents_FIELDS ", null, values);
                                                }
                                            }
                                    }
                                }
                        } catch (Exception e) {

                        }
                        App_db.setTransactionSuccessful();

                        Get_Response = message.toString();
                    } finally {
                        App_db.endTransaction();
                    }
                    /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                    /*Reading Data by using FOR Loop*/
                } else {
                    Get_Response = activity.getString(R.string.relordr_unable);
                }
            } else {
                Get_Response = activity.getString(R.string.relordr_unable);
            }

        } catch (Exception e) {
            Get_Response = activity.getString(R.string.relordr_unable);
        }
        return Get_Response;
    }
}
