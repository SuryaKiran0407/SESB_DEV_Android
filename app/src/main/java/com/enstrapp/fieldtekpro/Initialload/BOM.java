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
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

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

public class BOM {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    /* BOM_RESERVE_HEADER Table and Fields Names */
    private static final String TABLE_BOM_RESERVE_HEADER = "BOM_RESERVE_HEADER";
    private static final String KEY_BOM_RESERVE_HEADER_ID = "ID";
    private static final String KEY_BOM_RESERVE_HEADER_UUID = "UUID";
    private static final String KEY_BOM_RESERVE_HEADER_BOM_ID = "BOM_ID";
    private static final String KEY_BOM_RESERVE_HEADER_PLANT = "PLANT";
    private static final String KEY_BOM_RESERVE_HEADER_REQ_DATE = "REQUIREMENT_DATE";
    private static final String KEY_BOM_RESERVE_HEADER_MOVEMENT_TYPE_ID = "MOVEMENT_TYPE_ID";
    private static final String KEY_BOM_RESERVE_HEADER_MOVEMENT_TYPE_TEXT = "MOVEMENT_TYPE_TEXT";
    private static final String KEY_BOM_RESERVE_HEADER_ORDER_NUMBER = "ORDER_NUMBER";
    private static final String KEY_BOM_RESERVE_HEADER_COST_CENTER_ID = "COST_CENTER_ID";
    private static final String KEY_BOM_RESERVE_HEADER_COST_CENTER_TEXT = "COST_CENTER_TEXT";
    private static final String KEY_BOM_RESERVE_HEADER_Quantity = "Quantity";
    private static final String KEY_BOM_RESERVE_HEADER_Unit = "Unit";
    private static final String KEY_BOM_RESERVE_HEADER_Lgort = "Lgort";
    /* BOM_RESERVE_HEADER Table and Fields Names */

    /* GET_LIST_OF_EQBOMS_EtBomHeader and Fields Names */
    private static final String TABLE_LIST_OF_EQBOMS_EtBomHeader = "EtBomHeader";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomHeader_ID = "id";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomHeader_Bom = "Bom";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomHeader_BomDesc = "BomDesc";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomHeader_Plant = "Plant";
    /* GET_LIST_OF_EQBOMS_EtBomHeader and Fields Names */

    /* GET_LIST_OF_EQBOMS_EtBomItem and Fields Names */
    private static final String TABLE_LIST_OF_EQBOMS_EtBomItem = "EtBomItem";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_ID = "id";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_Bom = "Bom";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_BomComponent = "BomComponent";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_CompText = "CompText";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_Quantity = "Quantity";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_Unit = "Unit";
    private static final String KEY_GET_LIST_OF_EQBOMS_EtBomItem_Stlkz = "Stlkz";
    /* GET_LIST_OF_EQBOMS_EtBomItem and Fields Names */

    /* GET_STOCK_DATA and Fields Names */
    private static final String TABLE_GET_STOCK_DATA = "GET_STOCK_DATA";
    private static final String KEY_GET_STOCK_DATA_ID = "id";
    private static final String KEY_GET_STOCK_DATA_Matnr = "Matnr";
    private static final String KEY_GET_STOCK_DATA_Werks = "Werks";
    private static final String KEY_GET_STOCK_DATA_Maktx = "Maktx";
    private static final String KEY_GET_STOCK_DATA_Lgort = "Lgort";
    private static final String KEY_GET_STOCK_DATA_Labst = "Labst";
    private static final String KEY_GET_STOCK_DATA_Speme = "Speme";
    private static final String KEY_GET_STOCK_DATA_Lgpbe = "Lgpbe";
    private static final String KEY_GET_STOCK_DATA_Bwtar = "Bwtar";
    /* GET_STOCK_DATA and Fields Names */

    public static String Get_BOM_Data(Activity activity, String transmit_type, String bom_id) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equals("LOAD")) {
                /* Creating GET_LIST_OF_EQBOMS_EtBomHeader Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_OF_EQBOMS_EtBomHeader);
                String CREATE_GET_LIST_OF_EQBOMS_EtBomHeader_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_LIST_OF_EQBOMS_EtBomHeader + ""
                        + "( "
                        + KEY_GET_LIST_OF_EQBOMS_EtBomHeader_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomHeader_Bom + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomHeader_BomDesc + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomHeader_Plant + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_OF_EQBOMS_EtBomHeader_TABLE);
                /* Creating GET_LIST_OF_EQBOMS_EtBomHeader Table with Fields */

                /* Creating GET_LIST_OF_EQBOMS_EtBomItem Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_OF_EQBOMS_EtBomItem);
                String CREATE_GET_LIST_OF_EQBOMS_EtBomItem_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_LIST_OF_EQBOMS_EtBomItem + ""
                        + "( "
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_Bom + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_BomComponent + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_CompText + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_Quantity + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_Unit + " TEXT,"
                        + KEY_GET_LIST_OF_EQBOMS_EtBomItem_Stlkz + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_OF_EQBOMS_EtBomItem_TABLE);
                /* Creating GET_LIST_OF_EQBOMS_EtBomItem Table with Fields */

                /* Creating GET_STOCK_DATA Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_STOCK_DATA);
                String CREATE_GET_STOCK_DATA_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_STOCK_DATA + ""
                        + "( "
                        + KEY_GET_STOCK_DATA_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_STOCK_DATA_Matnr + " TEXT,"
                        + KEY_GET_STOCK_DATA_Werks + " TEXT,"
                        + KEY_GET_STOCK_DATA_Maktx + " TEXT,"
                        + KEY_GET_STOCK_DATA_Lgort + " TEXT,"
                        + KEY_GET_STOCK_DATA_Labst + " TEXT,"
                        + KEY_GET_STOCK_DATA_Speme + " TEXT,"
                        + KEY_GET_STOCK_DATA_Bwtar + " TEXT,"
                        + KEY_GET_STOCK_DATA_Lgpbe + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_STOCK_DATA_TABLE);
                /* Creating GET_STOCK_DATA Table with Fields */

                /* BOM_RESERVE_HEADER Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOM_RESERVE_HEADER);
                String CREATE_TABLE_BOM_RESERVE_HEADER = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_BOM_RESERVE_HEADER + ""
                        + "( "
                        + KEY_BOM_RESERVE_HEADER_ID + " INTEGER PRIMARY KEY,"
                        + KEY_BOM_RESERVE_HEADER_UUID + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_BOM_ID + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_PLANT + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_REQ_DATE + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_MOVEMENT_TYPE_ID + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_MOVEMENT_TYPE_TEXT + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_ORDER_NUMBER + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_COST_CENTER_ID + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_COST_CENTER_TEXT + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_Quantity + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_Unit + " TEXT,"
                        + KEY_BOM_RESERVE_HEADER_Lgort + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_BOM_RESERVE_HEADER);
                /* BOM_RESERVE_HEADER Table and Fields Names */

            }
            else
            {
                if (bom_id != null && !bom_id.equals(""))
                {
                    App_db.execSQL("delete from EtBomHeader where Bom = ?", new String[]{bom_id});
                    App_db.execSQL("delete from EtBomItem where Bom = ?", new String[]{bom_id});
                }
                else
                {
                    /*App_db.execSQL("delete from EtBomHeader");
                    App_db.execSQL("delete from EtBomItem");
                    App_db.execSQL("delete from GET_STOCK_DATA");*/
                }
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
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype" +
                            " = ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"C5", "EQ", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            } else {
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
            map.put("IvEqNo", bom_id);
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
            Call<BOM_SER> call = service.getBOMDetails(url_link, basic, map);
            Response<BOM_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_BOM_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0)
                {

                    App_db.execSQL("delete from EtBomHeader");
                    App_db.execSQL("delete from EtBomItem");
                    App_db.execSQL("delete from GET_STOCK_DATA");

                    App_db.beginTransaction();
                    try
                    {
                        /*EtBomHeader*/
                        if (response.body().getD().getResults().get(0).getEtBomHeader() != null) {
                            if (response.body().getD().getResults().get(0).getEtBomHeader().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtBomHeader().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (BOM_SER.EtBomHeader_Result eBH : response.body().getD().getResults().get(0).getEtBomHeader().getResults()) {
                                    values.put("Bom", eBH.getBom());
                                    values.put("BomDesc", eBH.getBomDesc());
                                    values.put("Plant", eBH.getPlant());
                                    App_db.insert("EtBomHeader", null, values);
                                }
                            }
                        }

                        /*EtBomItem*/
                        if (response.body().getD().getResults().get(0).getEtBomItem() != null) {
                            if (response.body().getD().getResults().get(0).getEtBomItem().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtBomItem().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (BOM_SER.EtBomItem_Result eBI : response.body().getD().getResults().get(0).getEtBomItem().getResults()) {
                                    values.put("Bom", eBI.getBom());
                                    values.put("BomComponent", eBI.getBomComponent());
                                    values.put("CompText", eBI.getCompText());
                                    values.put("Quantity", eBI.getQuantity());
                                    values.put("Unit", eBI.getUnit());
                                    values.put("Stlkz", eBI.getStlkz());
                                    App_db.insert("EtBomItem", null, values);
                                }
                            }
                        }
                        /*EtStock*/
                        if (response.body().getD().getResults().get(0).getEtStock() != null) {
                            if (response.body().getD().getResults().get(0).getEtStock().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtStock().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (BOM_SER.EtStock_Result eBI : response.body().getD().getResults().get(0).getEtStock().getResults()) {
                                    values.put("Matnr", eBI.getMatnr());
                                    values.put("Werks", eBI.getWerks());
                                    values.put("Maktx", eBI.getMaktx());
                                    values.put("Lgort", eBI.getLgort());
                                    values.put("Labst", eBI.getLabst());
                                    values.put("Speme", eBI.getSpeme());
                                    values.put("Bwtar", eBI.getBwtar());
                                    values.put("Lgpbe", eBI.getLgpbe());
                                    App_db.insert("GET_STOCK_DATA", null, values);
                                }
                            }
                        }
                        App_db.setTransactionSuccessful();
                        Get_Response = "success";

                    } finally {
                        App_db.endTransaction();
                    }
                }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
