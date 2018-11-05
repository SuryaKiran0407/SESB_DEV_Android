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
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class VHLP {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();

    /* EtUdecCodes Table and Fields Names */
    private static final String TABLE_EtUdecCodes = "EtUdecCodes";
    private static final String KEY_EtUdecCodes_id = "id";
    private static final String KEY_EtUdecCodes_Werks = "Werks";
    private static final String KEY_EtUdecCodes_Katalogart = "Katalogart";
    private static final String KEY_EtUdecCodes_Auswahlmge = "Auswahlmge";
    private static final String KEY_EtUdecCodes_Codegruppe = "Codegruppe";
    private static final String KEY_EtUdecCodes_Kurztext = "Kurztext";
    private static final String KEY_EtUdecCodes_Code = "Code";
    private static final String KEY_EtUdecCodes_Kurztext1 = "Kurztext1";
    private static final String KEY_EtUdecCodes_Bewertung = "Bewertung";
    private static final String KEY_EtUdecCodes_Fehlklasse = "Fehlklasse";
    private static final String KEY_EtUdecCodes_Qkennzahl = "Qkennzahl";
    private static final String KEY_EtUdecCodes_Folgeakti = "Folgeakti";
    private static final String KEY_EtUdecCodes_Fehlklassetxt = "Fehlklassetxt";
    /* EtUdecCodes Table and Fields Names */


    /* EtInspCodes Table and Fields Names */
    private static final String TABLE_EtInspCodes = "EtInspCodes";
    private static final String KEY_EtInspCodes_id = "id";
    private static final String KEY_EtInspCodes_Werks = "Werks";
    private static final String KEY_EtInspCodes_Katalogart = "Katalogart";
    private static final String KEY_EtInspCodes_Auswahlmge = "Auswahlmge";
    private static final String KEY_EtInspCodes_Codegruppe = "Codegruppe";
    private static final String KEY_EtInspCodes_Kurztext = "Kurztext";
    private static final String KEY_EtInspCodes_Code = "Code";
    private static final String KEY_EtInspCodes_Kurztext1 = "Kurztext1";
    private static final String KEY_EtInspCodes_Bewertung = "Bewertung";
    private static final String KEY_EtInspCodes_Fehlklasse = "Fehlklasse";
    private static final String KEY_EtInspCodes_Qkennzahl = "Qkennzahl";
    private static final String KEY_EtInspCodes_Folgeakti = "Folgeakti";
    private static final String KEY_EtInspCodes_Fehlklassetxt = "Fehlklassetxt";
    /* EtInspCodes Table and Fields Names */

    private static final String TABLE_EtWbs = "EtWbs";
    private static final String KEY_EtWbs_ID = "id";
    private static final String KEY_EtWbs_Iwerk = "Iwerk";
    private static final String KEY_EtWbs_Gsber = "Gsber";
    private static final String KEY_EtWbs_Posid = "Posid";
    private static final String KEY_EtWbs_Poski = "Poski";
    private static final String KEY_EtWbs_Post1 = "Post1";
    private static final String KEY_EtWbs_Pspnr = "Pspnr";
    private static final String KEY_EtWbs_Pspid = "Pspid";

    private static final String TABLE_EtRevnr = "EtRevnr";
    private static final String KEY_EtRevnr_ID = "id";
    private static final String KEY_EtRevnr_Iwerk = "Iwerk";
    private static final String KEY_EtRevnr_Revnr = "Revnr";
    private static final String KEY_EtRevnr_Revtx = "Revtx";

    /* EtMeasCodes Table and Fields Names */
    private static final String TABLE_EtMeasCodes = "EtMeasCodes";
    private static final String KEY_EtMeasCodes_id = "id";
    private static final String KEY_EtMeasCodes_Codegruppe = "Codegruppe";
    private static final String KEY_EtMeasCodes_Kurztext = "Kurztext";
    private static final String KEY_EtMeasCodes_Code = "Code";
    private static final String KEY_EtMeasCodes_Kurztext1 = "Kurztext1";
    /* EtMeasCodes Table and Fields Names */

    private static final String TABLE_EtUsers = "EtUsers";
    private static final String KEY_EtUsers_ID = "id";
    private static final String KEY_EtUsers_Muser = "Muser";
    private static final String KEY_EtUsers_Fname = "Fname";
    private static final String KEY_EtUsers_Lname = "Lname";
    private static final String KEY_EtUsers_Tokenid = "Tokenid";

    /* EtTq80 Table and Fields Names */
    private static final String TABLE_GET_EtTq80 = "EtTq80";
    private static final String KEY_GET_EtTq80_ID = "id";
    private static final String KEY_GET_EtTq80_Qmart = "QMART";
    private static final String KEY_GET_EtTq80_Auart = "AUART";
    /* EtTq80 Table and Fields Names */

    /* GET_EtConfReason Table and Fields Names */
    private static final String TABLE_GET_EtConfReason = "GET_EtConfReason";
    private static final String KEY_GET_EtConfReason_ID = "id";
    private static final String KEY_GET_EtConfReason_Werks = "Werks";
    private static final String KEY_GET_EtConfReason_Grund = "Grund";
    private static final String KEY_GET_EtConfReason_Grdtx = "Grdtx";
    /* GET_EtConfReason Table and Fields Names */


    /* GET_CUSTOM_FIELDS Table and Fields Names */
    private static final String TABLE_GET_CUSTOM_FIELDS = "GET_CUSTOM_FIELDS";
    private static final String KEY_GET_CUSTOM_FIELDS_ID = "id";
    private static final String KEY_GET_CUSTOM_FIELDS_Fieldname = "Fieldname";
    private static final String KEY_GET_CUSTOM_FIELDS_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_GET_CUSTOM_FIELDS_Datatype = "Datatype";
    private static final String KEY_GET_CUSTOM_FIELDS_Tabname = "Tabname";
    private static final String KEY_GET_CUSTOM_FIELDS_Zdoctype = "Zdoctype";
    private static final String KEY_GET_CUSTOM_FIELDS_Sequence = "Sequence";
    private static final String KEY_GET_CUSTOM_FIELDS_Flabel = "Flabel";
    private static final String KEY_GET_CUSTOM_FIELDS_Spras = "Spras";
    private static final String KEY_GET_CUSTOM_FIELDS_Length = "Length";
    /* GET_CUSTOM_FIELDS Table and Fields Names */


    /* GET_LIST_MOVEMENT_TYPES Table and Fields Names */
    private static final String TABLE_GET_LIST_MOVEMENT_TYPES = "GET_LIST_MOVEMENT_TYPES";
    private static final String KEY_GET_LIST_MOVEMENT_TYPES_ID = "id";
    private static final String KEY_GET_LIST_MOVEMENT_TYPES_Bwart = "Bwart";
    private static final String KEY_GET_LIST_MOVEMENT_TYPES_Btext = "Btext";
    /* GET_LIST_MOVEMENT_TYPES Table and Fields Names */

    /* Get_NOTIF_CODES_ItemCodes Table and Fields Names */
    private static final String TABLE_Get_NOTIFCODES_ItemCodes = "Get_NOTIFCODES_ItemCodes";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_ID = "ID";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_NotifType = "NotifType";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_Rbnr = "Rbnr";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_Codegruppe = "Codegruppe";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_Kurztext = "Kurztext";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_Code = "Code";
    private static final String KEY_Get_NOTIFCODES_ItemCodes_Kurztext1 = "Kurztext1";
    /* Get_NOTIF_CODES_ItemCodes Table and Fields Names */

    /* Get_NOTIFCODES_CauseCodes Table and Fields Names */
    private static final String TABLE_Get_NOTIFCODES_CauseCodes = "Get_NOTIFCODES_CauseCodes";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_ID = "ID";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_NotifType = "NotifType";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_Rbnr = "Rbnr";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_Codegruppe = "Codegruppe";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_Kurztext = "Kurztext";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_Code = "Code";
    private static final String KEY_Get_NOTIFCODES_CauseCodes_Kurztext1 = "Kurztext1";
    /* Get_NOTIFCODES_CauseCodes Table and Fields Names */

    /* Get_NOTIFCODES_ObjectCodes Table and Fields Names */
    private static final String TABLE_Get_NOTIFCODES_ObjectCodes = "Get_NOTIFCODES_ObjectCodes";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_ID = "ID";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_NotifType = "NotifType";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_Rbnr = "Rbnr";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_Codegruppe = "Codegruppe";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_Kurztext = "Kurztext";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_Code = "Code";
    private static final String KEY_Get_NOTIFCODES_ObjectCodes_Kurztext1 = "Kurztext1";
    /* Get_NOTIFCODES_ObjectCodes Table and Fields Names */

    /* EtNotifCodes_ActCodes Table and Fields Names */
    private static final String TABLE_GET_Notif_ActCodes = "Get_NOTIFCODES_ActCodes";
    private static final String KEY_GET_Notif_ActCodes_ID = "id";
    private static final String KEY_GET_Notif_ActCodes_NotifType = "NotifType";
    private static final String KEY_GET_Notif_ActCodes_Rbnr = "Rbnr";
    private static final String KEY_GET_Notif_ActCodes_Codegruppe = "Codegruppe";
    private static final String KEY_GET_Notif_ActCodes_Kurztext = "Kurztext";
    private static final String KEY_GET_Notif_ActCodes_Code = "Code";
    private static final String KEY_GET_Notif_ActCodes_Kurztext1 = "Kurztext1";
    /* EtNotifCodes_ActCodes Table and Fields Names */

    /* EtNotifCodes_TaskCodes Table and Fields Names */
    private static final String TABLE_GET_Notif_TaskCodes = "Get_NOTIFCODES_TaskCodes";
    private static final String KEY_GET_Notif_TaskCodes_ID = "id";
    private static final String KEY_GET_Notif_TaskCodes_Codegruppe = "Codegruppe";
    private static final String KEY_GET_Notif_TaskCodes_Kurztext = "Kurztext";
    private static final String KEY_GET_Notif_TaskCodes_Code = "Code";
    private static final String KEY_GET_Notif_TaskCodes_Kurztext1 = "Kurztext1";
    /* EtNotifCodes_TaskCodes Table and Fields Names */

    /* GET_EtNotifEffect Table and Fields Names */
    private static final String TABLE_GET_EtNotifEffect = "EtNotifEffect";
    private static final String KEY_GET_EtNotifEffect_ID = "id";
    private static final String KEY_GET_EtNotifEffect_Auswk = "Auswk";
    private static final String KEY_GET_EtNotifEffect_Auswkt = "Auswkt";
    /* GET_EtNotifEffect Table and Fields Names */

    /* GET_NOTIFICATION_PRIORITY Table and Fields Names */
    private static final String TABLE_GET_NOTIFICATION_PRIORITY = "GET_NOTIFICATION_PRIORITY";
    private static final String KEY_GET_NOTIFICATION__PRIORITY_ID = "id";
    private static final String KEY_GET_NOTIFICATION_PRIORITY_Priok = "Priok";
    private static final String KEY_GET_NOTIFICATION_PRIORITY_Priokx = "Priokx";
    /* GET_NOTIFICATION_PRIORITY Table and Fields Names */

    /* GET_NOTIFICATION_TYPES Table and Fields Names */
    private static final String TABLE_GET_NOTIFICATION_TYPES = "GET_NOTIFICATION_TYPES";
    private static final String KEY_GET_NOTIFICATION_ID = "id";
    private static final String KEY_GET_NOTIFICATION_TYPES_Qmart = "Qmart";
    private static final String KEY_GET_NOTIFICATION_TYPES_Qmartx = "Qmartx";
    /* GET_NOTIFICATION_TYPES Table and Fields Names */

    /* GET_ORDER_PRIORITY Table and Fields Names */
    private static final String TABLE_GET_ORDER_PRIORITY = "GET_ORDER_PRIORITY";
    private static final String KEY_GET_ORDER__PRIORITY_ID = "id";
    private static final String KEY_GET_ORDER_PRIORITY_Priok = "Priok";
    private static final String KEY_GET_ORDER_PRIORITY_Priokx = "Priokx";
    /* GET_ORDER_PRIORITY Table and Fields Names */

    /* GET_EtOrdSyscond Table and Fields Names */
    private static final String TABLE_GET_EtOrdSyscond = "EtOrdSyscond";
    private static final String KEY_GET_EtOrdSyscond_ID = "id";
    private static final String KEY_GET_EtOrdSyscond_Anlzu = "Anlzu";
    private static final String KEY_GET_EtOrdSyscond_Anlzux = "Anlzux";
    /* GET_EtOrdSyscond Table and Fields Names */

    /* GET_ORDER_TYPES Table and Fields Names */
    private static final String TABLE_GET_ORDER_TYPES = "GET_ORDER_TYPES";
    private static final String KEY_GET_ORDER__TYPES_ID = "id";
    private static final String KEY_GET_ORDER_TYPES_Auart = "Auart";
    private static final String KEY_GET_ORDER_TYPES_Txt = "Txt";
    /* GET_ORDER_TYPES Table and Fields Names */

    /* GET_PLANTS Table and Fields Names */
    private static final String TABLE_GET_PLANTS = "GET_PLANTS";
    private static final String KEY_GET_PLANTS_ID = "id";
    private static final String KEY_GET_PLANTS_Werks = "Werks";
    private static final String KEY_GET_PLANTS_Name1 = "Name1";
    /* GET_PLANTS Table and Fields Names */

    /* GET_CONTROL_KEY Table and Fields Names */
    private static final String TABLE_GET_CONTROL_KEY = "GET_CONTROL_KEY";
    private static final String KEY_GET_CONTROL_KEY_ID = "id";
    private static final String KEY_GET_CONTROL_KEY_Steus = "Steus";
    private static final String KEY_GET_CONTROL_KEY_Txt = "Txt";
    /* GET_CONTROL_KEY Table and Fields Names */

    /* GET_SLOC Table and Fields Names */
    private static final String TABLE_GET_SLOC = "GET_SLOC";
    private static final String KEY_GET_SLOC_ID = "id";
    private static final String KEY_GET_SLOC_Werks = "Werks";
    private static final String KEY_GET_SLOC_Name1 = "Name1";
    private static final String KEY_GET_SLOC_Lgort = "Lgort";
    private static final String KEY_GET_SLOC_Lgobe = "Lgobe";
    /* GET_SLOC Table and Fields Names */

    /* GET_UNITS Table and Fields Names */
    private static final String TABLE_GET_UNITS = "GET_UNITS";
    private static final String KEY_GET_UNITS_ID = "id";
    private static final String KEY_GET_UNITS_UnitType = "UnitType";
    private static final String KEY_GET_UNITS_Meins = "Meins";
    /* GET_UNITS Table and Fields Names */

    /* GET_WKCTR Table and Fields Names */
    private static final String TABLE_GET_WKCTR = "GET_WKCTR";
    private static final String KEY_GET_WKCTR_ID = "id";
    private static final String KEY_GET_WKCTR_Bukrs = "Bukrs";
    private static final String KEY_GET_WKCTR_Kokrs = "Kokrs";
    private static final String KEY_GET_WKCTR_Objid = "Objid";
    private static final String KEY_GET_WKCTR_Steus = "Steus";
    private static final String KEY_GET_WKCTR_Werks = "Werks";
    private static final String KEY_GET_WKCTR_Name1 = "Name1";
    private static final String KEY_GET_WKCTR_Arbpl = "Arbpl";
    private static final String KEY_GET_WKCTR_Ktext = "Ktext";
    /* GET_WKCTR Table and Fields Names */

    private static final String TABLE_GET_EtIngrp = "GET_EtIngrp";
    private static final String KEY_GET_EtIngrp_ID = "id";
    private static final String KEY_GET_EtIngrp_Iwerk = "Iwerk";
    private static final String KEY_GET_EtIngrp_Ingrp = "Ingrp";
    private static final String KEY_GET_EtIngrp_Innam = "Innam";


    private static final String TABLE_ET_PARVW = "ET_PARVW";
    private static final String KEY_ET_PARVW_ID = "id";
    private static final String KEY_ET_PARVW_PARVW = "PARVW";
    private static final String KEY_ET_PARVW_VTEXT = "VTEXT";


    /* GET_LIST_COST_CENTER Table and Fields Names */
    private static final String TABLE_GET_LIST_COST_CENTER = "GET_LIST_COST_CENTER";
    private static final String KEY_GET_LIST_COST_CENTER_ID = "id";
    private static final String KEY_GET_LIST_COST_CENTER_Bukrs = "Bukrs";
    private static final String KEY_GET_LIST_COST_CENTER_Kostl = "Kostl";
    private static final String KEY_GET_LIST_COST_CENTER_Ktext = "Ktext";
    private static final String KEY_GET_LIST_COST_CENTER_Kokrs = "Kokrs";
    private static final String KEY_GET_LIST_COST_CENTER_Werks = "Werks";
    private static final String KEY_GET_LIST_COST_CENTER_Warea = "Warea";
    /* GET_LIST_COST_CENTER Table and Fields Names */


    private static final String TABLE_EtPernr = "GET_EtPernr";
    private static final String KEY_EtPernr_ID = "id";
    private static final String KEY_EtPernr_Werks = "Werks";
    private static final String KEY_EtPernr_Arbpl = "Arbpl";
    private static final String KEY_EtPernr_Objid = "Objid";
    private static final String KEY_EtPernr_Lastname = "Lastname";
    private static final String KEY_EtPernr_Firstname = "Firstname";


    private static final String TABLE_EtIlart = "EtIlart";
    private static final String KEY_EtIlart_ID = "id";
    private static final String KEY_EtIlart_Auart = "Auart";
    private static final String KEY_EtIlart_Ilart = "Ilart";
    private static final String KEY_EtIlart_Ilatx = "Ilatx";


    public static String Get_VHLP_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD")) {
                /* Creating GET_CUSTOM_FIELDS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_CUSTOM_FIELDS);
                String CREATE_GET_CUSTOM_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_CUSTOM_FIELDS + ""
                        + "( "
                        + KEY_GET_CUSTOM_FIELDS_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_CUSTOM_FIELDS_Fieldname + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_ZdoctypeItem + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Datatype + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Tabname + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Zdoctype + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Sequence + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Flabel + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Spras + " TEXT,"
                        + KEY_GET_CUSTOM_FIELDS_Length + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_CUSTOM_FIELDS_TABLE);
                /* Creating GET_CUSTOM_FIELDS Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtUdecCodes);
                String CREATE_TABLE_EtUdecCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtUdecCodes + ""
                        + "( "
                        + KEY_EtUdecCodes_id + " INTEGER PRIMARY KEY,"
                        + KEY_EtUdecCodes_Werks + " TEXT,"
                        + KEY_EtUdecCodes_Katalogart + " TEXT,"
                        + KEY_EtUdecCodes_Auswahlmge + " TEXT,"
                        + KEY_EtUdecCodes_Codegruppe + " TEXT,"
                        + KEY_EtUdecCodes_Kurztext + " TEXT,"
                        + KEY_EtUdecCodes_Code + " TEXT,"
                        + KEY_EtUdecCodes_Kurztext1 + " TEXT,"
                        + KEY_EtUdecCodes_Bewertung + " TEXT,"
                        + KEY_EtUdecCodes_Fehlklasse + " TEXT,"
                        + KEY_EtUdecCodes_Qkennzahl + " TEXT,"
                        + KEY_EtUdecCodes_Folgeakti + " TEXT,"
                        + KEY_EtUdecCodes_Fehlklassetxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUdecCodes);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtInspCodes);
                String CREATE_TABLE_EtInspCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtInspCodes + ""
                        + "( "
                        + KEY_EtInspCodes_id + " INTEGER PRIMARY KEY,"
                        + KEY_EtInspCodes_Werks + " TEXT,"
                        + KEY_EtInspCodes_Katalogart + " TEXT,"
                        + KEY_EtInspCodes_Auswahlmge + " TEXT,"
                        + KEY_EtInspCodes_Codegruppe + " TEXT,"
                        + KEY_EtInspCodes_Kurztext + " TEXT,"
                        + KEY_EtInspCodes_Code + " TEXT,"
                        + KEY_EtInspCodes_Kurztext1 + " TEXT,"
                        + KEY_EtInspCodes_Bewertung + " TEXT,"
                        + KEY_EtInspCodes_Fehlklasse + " TEXT,"
                        + KEY_EtInspCodes_Qkennzahl + " TEXT,"
                        + KEY_EtInspCodes_Folgeakti + " TEXT,"
                        + KEY_EtInspCodes_Fehlklassetxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtInspCodes);

                /* Creating GET_LIST_COST_CENTER Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_LIST_COST_CENTER);
                String CREATE_GET_LIST_COST_CENTER_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_LIST_COST_CENTER + ""
                        + "( "
                        + KEY_GET_LIST_COST_CENTER_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_COST_CENTER_Bukrs + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Kostl + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Ktext + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Kokrs + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Werks + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Warea + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_COST_CENTER_TABLE);
                /* Creating GET_LIST_COST_CENTER Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtUsers);
                String CREATE_TABLE_EtUsers = "CREATE TABLE IF NOT EXISTS " + TABLE_EtUsers + ""
                        + "( "
                        + KEY_EtUsers_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtUsers_Muser + " TEXT,"
                        + KEY_EtUsers_Fname + " TEXT,"
                        + KEY_EtUsers_Lname + " TEXT,"
                        + KEY_EtUsers_Tokenid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUsers);

                /* EtTq80 Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtTq80);
                String CREATE_GET_TABLE_GET_EtTq80 = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtTq80 + ""
                        + "( "
                        + KEY_GET_EtTq80_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtTq80_Qmart + " TEXT,"
                        + KEY_GET_EtTq80_Auart + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_GET_EtTq80);
                /* EtTq80 Table and Fields Names */

                /* GET_EtConfReason Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtConfReason);
                String CREATE_TABLE_GET_EtConfReason = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtConfReason + ""
                        + "( "
                        + KEY_GET_EtConfReason_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtConfReason_Werks + " TEXT,"
                        + KEY_GET_EtConfReason_Grund + " TEXT,"
                        + KEY_GET_EtConfReason_Grdtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtConfReason);
                /* GET_EtConfReason Table and Fields Names */

                /* Creating GET_LIST_MOVEMENT_TYPES Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_LIST_MOVEMENT_TYPES);
                String CREATE_GET_LIST_MOVEMENT_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_LIST_MOVEMENT_TYPES + ""
                        + "( "
                        + KEY_GET_LIST_MOVEMENT_TYPES_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Bwart + " TEXT,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Btext + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_MOVEMENT_TYPES_TABLE);
                /* Creating GET_LIST_MOVEMENT_TYPES Table with Fields */

                /* Creating TABLE_Get_NOTIF_CODES_ItemCodes_Codes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Get_NOTIFCODES_ItemCodes);
                String CREATE_Get_NOTIFCODES_ItemCodes_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Get_NOTIFCODES_ItemCodes + ""
                        + "( "
                        + KEY_Get_NOTIFCODES_ItemCodes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_ItemCodes_NotifType + " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Rbnr + " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Codegruppe + " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Kurztext + " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Code + " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_Get_NOTIFCODES_ItemCodes_TABLE);
                /* Creating TABLE_Get_NOTIF_CODES_ItemCodes_Codes Table with Fields */

                /* Creating TABLE_Get_NOTIFCODES_CauseCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Get_NOTIFCODES_CauseCodes);
                String CREATE_TABLE_Get_NOTIFCODES_CauseCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Get_NOTIFCODES_CauseCodes + ""
                        + "( "
                        + KEY_Get_NOTIFCODES_CauseCodes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_CauseCodes_NotifType + " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Rbnr + " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Codegruppe + " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Kurztext + " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Code + " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_CauseCodes);
                /* Creating TABLE_Get_NOTIFCODES_CauseCodes Table with Fields */

                /* Creating TABLE_Get_NOTIFCODES_TaskCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_Notif_TaskCodes);
                String CREATE_TABLE_Get_NOTIFCODES_TaskCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_Notif_TaskCodes + ""
                        + "( "
                        + KEY_GET_Notif_TaskCodes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_Notif_TaskCodes_Codegruppe + " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Kurztext + " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Code + " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_TaskCodes);
                /* Creating TABLE_Get_NOTIFCODES_TaskCodes Table with Fields */

                /* Creating Get_NOTIFCODES_ObjectCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Get_NOTIFCODES_ObjectCodes);
                String CREATE_TABLE_Get_NOTIFCODES_ObjectCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Get_NOTIFCODES_ObjectCodes + ""
                        + "( "
                        + KEY_Get_NOTIFCODES_ObjectCodes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_NotifType + " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Rbnr + " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Codegruppe + " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Kurztext + " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Code + " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_ObjectCodes);
                /* Creating Get_NOTIFCODES_ObjectCodes Table with Fields */

                /* Creating EtNotifCodes_ActCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_Notif_ActCodes);
                String CREATE_TABLE_GET_Notif_ActCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_Notif_ActCodes + ""
                        + "( "
                        + KEY_GET_Notif_ActCodes_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_Notif_ActCodes_NotifType + " TEXT,"
                        + KEY_GET_Notif_ActCodes_Rbnr + " TEXT,"
                        + KEY_GET_Notif_ActCodes_Codegruppe + " TEXT,"
                        + KEY_GET_Notif_ActCodes_Kurztext + " TEXT,"
                        + KEY_GET_Notif_ActCodes_Code + " TEXT,"
                        + KEY_GET_Notif_ActCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_Notif_ActCodes);
                /* Creating EtNotifCodes_TaskCodes_Codes Table with Fields */

                /* Creating GET_EtNotifEffect Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtNotifEffect);
                String CREATE_TABLE_GET_EtNotifEffect = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtNotifEffect + ""
                        + "( "
                        + KEY_GET_EtNotifEffect_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtNotifEffect_Auswk + " TEXT,"
                        + KEY_GET_EtNotifEffect_Auswkt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtNotifEffect);
                /* Creating GET_EtNotifEffect Table with Fields */

                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_NOTIFICATION_PRIORITY);
                String CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_NOTIFICATION_PRIORITY + ""
                        + "( "
                        + KEY_GET_NOTIFICATION__PRIORITY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priok + " TEXT,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE);
                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */

                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_NOTIFICATION_TYPES);
                String CREATE_GET_NOTIFICATIONS_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_NOTIFICATION_TYPES + ""
                        + "( "
                        + KEY_GET_NOTIFICATION_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmart + " TEXT,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmartx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_TYPE_TABLE);
                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */

                /* Creating GET_ORDER_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_ORDER_PRIORITY);
                String CREATE_GET_ORDER_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_ORDER_PRIORITY + ""
                        + "( "
                        + KEY_GET_ORDER__PRIORITY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_PRIORITY_Priok + " TEXT,"
                        + KEY_GET_ORDER_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_PRIORITY_TABLE);
                /* Creating GET_ORDER_PRIORITY Table with Fields */

                /* Creating GET_EtOrdSyscond Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrdSyscond);
                String CREATE_TABLE_GET_EtOrdSyscond = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtOrdSyscond + ""
                        + "( "
                        + KEY_GET_EtOrdSyscond_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrdSyscond_Anlzu + " TEXT,"
                        + KEY_GET_EtOrdSyscond_Anlzux + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrdSyscond);
                /* Creating GET_EtOrdSyscond Table with Fields */

                /* Creating GET_ORDER_TYPES Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_ORDER_TYPES);
                String CREATE_GET_ORDER_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_ORDER_TYPES + ""
                        + "( "
                        + KEY_GET_ORDER__TYPES_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_TYPES_Auart + " TEXT,"
                        + KEY_GET_ORDER_TYPES_Txt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_TYPES_TABLE);
                /* Creating GET_ORDER_TYPES Table with Fields */

                /* Creating GET_PLANTS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_PLANTS);
                String CREATE_GET_PLANTS_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_PLANTS + ""
                        + "( "
                        + KEY_GET_PLANTS_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_PLANTS_Werks + " TEXT,"
                        + KEY_GET_PLANTS_Name1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_PLANTS_TABLE);
                /* Creating GET_PLANTS Table with Fields */

                /* GET_CONTROL_KEY Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_CONTROL_KEY);
                String CREATE_GET_CONTROL_KEY_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_CONTROL_KEY + ""
                        + "( "
                        + KEY_GET_CONTROL_KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_CONTROL_KEY_Steus + " TEXT,"
                        + KEY_GET_CONTROL_KEY_Txt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_CONTROL_KEY_TABLE);
                /* GET_CONTROL_KEY Table and Fields Names */

                /* Creating GET_SLOC Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_SLOC);
                String CREATE_GET_SLOC_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_SLOC + ""
                        + "( "
                        + KEY_GET_SLOC_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_SLOC_Werks + " TEXT,"
                        + KEY_GET_SLOC_Name1 + " TEXT,"
                        + KEY_GET_SLOC_Lgort + " TEXT,"
                        + KEY_GET_SLOC_Lgobe + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_SLOC_TABLE);
                /* Creating GET_SLOC Table with Fields */

                /* Creating GET_UNITS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_UNITS);
                String CREATE_GET_UNITS_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_UNITS + ""
                        + "( "
                        + KEY_GET_UNITS_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_UNITS_UnitType + " TEXT,"
                        + KEY_GET_UNITS_Meins + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_UNITS_TABLE);
                /* Creating GET_UNITS Table with Fields */

                /* Creating GET_WKCTR Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_WKCTR);
                String CREATE_GET_WKCTR_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_WKCTR + ""
                        + "( "
                        + KEY_GET_WKCTR_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_WKCTR_Bukrs + " TEXT,"
                        + KEY_GET_WKCTR_Kokrs + " TEXT,"
                        + KEY_GET_WKCTR_Objid + " TEXT,"
                        + KEY_GET_WKCTR_Steus + " TEXT,"
                        + KEY_GET_WKCTR_Werks + " TEXT,"
                        + KEY_GET_WKCTR_Name1 + " TEXT,"
                        + KEY_GET_WKCTR_Arbpl + " TEXT,"
                        + KEY_GET_WKCTR_Ktext + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_WKCTR_TABLE);
                /* Creating GET_WKCTR Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtIngrp);
                String CREATE_TABLE_GET_EtIngrp = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtIngrp + ""
                        + "( "
                        + KEY_GET_EtIngrp_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtIngrp_Iwerk + " TEXT,"
                        + KEY_GET_EtIngrp_Ingrp + " TEXT,"
                        + KEY_GET_EtIngrp_Innam + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtIngrp);

                /* Creating ET_PARVW Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_ET_PARVW);
                String CREATE_ET_PARVW_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_ET_PARVW + ""
                        + "( "
                        + KEY_ET_PARVW_ID + " INTEGER PRIMARY KEY,"
                        + KEY_ET_PARVW_PARVW + " TEXT,"
                        + KEY_ET_PARVW_VTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_ET_PARVW_TABLE);
                /* Creating ET_PARVW Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPernr);
                String CREATE_TABLE_EtPernr = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtPernr + ""
                        + "( "
                        + KEY_EtPernr_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtPernr_Werks + " TEXT,"
                        + KEY_EtPernr_Arbpl + " TEXT,"
                        + KEY_EtPernr_Objid + " TEXT,"
                        + KEY_EtPernr_Lastname + " TEXT,"
                        + KEY_EtPernr_Firstname + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtPernr);

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtMeasCodes);
                String CREATE_TABLE_EtMeasCodes = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtMeasCodes + ""
                        + "( "
                        + KEY_EtMeasCodes_id + " INTEGER PRIMARY KEY,"
                        + KEY_EtMeasCodes_Codegruppe + " TEXT,"
                        + KEY_EtMeasCodes_Kurztext + " TEXT,"
                        + KEY_EtMeasCodes_Code + " TEXT,"
                        + KEY_EtMeasCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtMeasCodes);

                /* Creating EtWbs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtWbs);
                String CREATE_EtWbs_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtWbs + ""
                        + "( "
                        + KEY_EtWbs_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWbs_Iwerk + " TEXT,"
                        + KEY_EtWbs_Gsber + " TEXT,"
                        + KEY_EtWbs_Posid + " TEXT,"
                        + KEY_EtWbs_Poski + " TEXT,"
                        + KEY_EtWbs_Post1 + " TEXT,"
                        + KEY_EtWbs_Pspnr + " TEXT,"
                        + KEY_EtWbs_Pspid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_EtWbs_TABLE);
                /* Creating EtWbs Table with Fields */

                /* Creating EtRevnr Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtRevnr);
                String CREATE_EtRevnr_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EtRevnr + ""
                        + "( "
                        + KEY_EtRevnr_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtRevnr_Iwerk + " TEXT,"
                        + KEY_EtRevnr_Revnr + " TEXT,"
                        + KEY_EtRevnr_Revtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_EtRevnr_TABLE);
                /* Creating EtRevnr Table with Fields */

                /* EtIlart Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtIlart);
                String CREATE_TABLE_EtIlart = "CREATE TABLE IF NOT EXISTS " + TABLE_EtIlart + ""
                        + "( "
                        + KEY_EtIlart_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtIlart_Auart + " TEXT,"
                        + KEY_EtIlart_Ilart + " TEXT,"
                        + KEY_EtIlart_Ilatx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtIlart);
                /* EtIlart Table and Fields Names */

            } else {

            }
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            String webservice_type = app_sharedpreferences
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"Z", "F4", webservice_type});
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
            Call<VHLP_SER> call = service.getVHLPDetails(url_link, basic, map);
            Response<VHLP_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Vhlp_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VHLP_SER.Result> results = response.body().getD().getResults();
                    App_db.beginTransaction();

                    if (results != null && results.size() > 0) {

                        /*EtUsers*/
                        VHLP_SER.EtUsers etUsers = results.get(0).getEtUsers();
                        if (etUsers != null) {
                            List<VHLP_SER.EtUsers_Result> etUsersResults = etUsers.getResults();
                            if (etUsersResults != null && etUsersResults.size() > 0) {
                                String EtUsers_sql = "Insert into EtUsers (Muser, Fname, Lname, " +
                                        "Tokenid) values(?,?,?,?);";
                                SQLiteStatement EtUsers_statement = App_db.compileStatement(EtUsers_sql);
                                EtUsers_statement.clearBindings();
                                for (VHLP_SER.EtUsers_Result eU : etUsersResults) {
                                    EtUsers_statement.bindString(1, c_e.check_empty(eU.getMuser()));
                                    EtUsers_statement.bindString(2, c_e.check_empty(eU.getFname()));
                                    EtUsers_statement.bindString(3, c_e.check_empty(eU.getLname()));
                                    EtUsers_statement.bindString(4, c_e.check_empty(eU.getTokenid()));
                                    EtUsers_statement.execute();
                                }
                            }
                        }

                        /*EtTq80*/
                        VHLP_SER.EtTq80 etTq80 = results.get(0).getEtTq80();
                        if (etTq80 != null) {
                            List<VHLP_SER.EtTq80_Result> etTq80Results = etTq80.getResults();
                            if (etTq80Results != null && etTq80Results.size() > 0) {
                                String EtTq80_sql = "Insert into EtTq80 (QMART, AUART) values(?,?);";
                                SQLiteStatement EtTq80_statement = App_db.compileStatement(EtTq80_sql);
                                EtTq80_statement.clearBindings();
                                for (VHLP_SER.EtTq80_Result eT : etTq80Results) {
                                    EtTq80_statement.bindString(1, c_e.check_empty(eT.getQmart()));
                                    EtTq80_statement.bindString(2, c_e.check_empty(eT.getAuart()));
                                    EtTq80_statement.execute();
                                }
                            }
                        }

                        /*EtNotifEffect*/
                        VHLP_SER.EtNotifEffect etNotifEffect = results.get(0).getEtNotifEffect();
                        if (etNotifEffect != null) {
                            List<VHLP_SER.EtNotifEffect_Result> etNotifEffectResults = etNotifEffect.getResults();
                            if (etNotifEffectResults != null && etNotifEffectResults.size() > 0) {
                                String EtNotifEffect_sql = "Insert into EtNotifEffect (Auswk, Auswkt) values(?,?);";
                                SQLiteStatement EtNotifEffect_statement = App_db.compileStatement(EtNotifEffect_sql);
                                EtNotifEffect_statement.clearBindings();
                                for (VHLP_SER.EtNotifEffect_Result eNE : etNotifEffectResults) {
                                    EtNotifEffect_statement.bindString(1, c_e.check_empty(eNE.getAuswk()));
                                    EtNotifEffect_statement.bindString(2, c_e.check_empty(eNE.getAuswkt()));
                                    EtNotifEffect_statement.execute();
                                }
                            }
                        }

                        /*EtOrdSyscond*/
                        VHLP_SER.EtOrdSyscond etOrdSyscond = results.get(0).getEtOrdSyscond();
                        if (etOrdSyscond != null) {
                            List<VHLP_SER.EtOrdSyscond_Result> etOrdSyscondResults = etOrdSyscond.getResults();
                            if (etOrdSyscondResults != null && etOrdSyscondResults.size() > 0) {
                                String EtOrdSyscond_sql = "Insert into EtOrdSyscond (Anlzu, Anlzux) values(?,?);";
                                SQLiteStatement EtOrdSyscond_statement = App_db.compileStatement(EtOrdSyscond_sql);
                                for (VHLP_SER.EtOrdSyscond_Result eOS : etOrdSyscondResults) {
                                    EtOrdSyscond_statement.bindString(1, c_e.check_empty(eOS.getAnlzu()));
                                    EtOrdSyscond_statement.bindString(2, c_e.check_empty(eOS.getAnlzux()));
                                    EtOrdSyscond_statement.execute();
                                }
                            }
                        }

                        /*EtIngrp*/
                        VHLP_SER.EtIngrp etIngrp = results.get(0).getEtIngrp();
                        if (etIngrp != null) {
                            List<VHLP_SER.EtIngrp_Result> etIngrpResults = etIngrp.getResults();
                            if (etIngrpResults != null && etIngrpResults.size() > 0) {
                                String EtIngrp_sql = "Insert into GET_EtIngrp (Iwerk, Ingrp, Innam) values(?,?,?);";
                                SQLiteStatement EtIngrp_statement = App_db.compileStatement(EtIngrp_sql);
                                EtIngrp_statement.clearBindings();
                                for (VHLP_SER.EtIngrp_Result eI : etIngrpResults) {
                                    EtIngrp_statement.bindString(1, c_e.check_empty(eI.getIwerk()));
                                    EtIngrp_statement.bindString(2, c_e.check_empty(eI.getIngrp()));
                                    EtIngrp_statement.bindString(3, c_e.check_empty(eI.getInnam()));
                                    EtIngrp_statement.execute();
                                }
                            }
                        }

                        /*EtConfReason*/
                        VHLP_SER.EtConfReason etConfReason = results.get(0).getEtConfReason();
                        if (etConfReason != null) {
                            List<VHLP_SER.EtConfReason_Result> etConfReasonResults = etConfReason.getResults();
                            if (etConfReasonResults != null && etConfReasonResults.size() > 0) {
                                String EtConfReason_sql = "Insert into GET_EtConfReason (Werks, Grund, Grdtx) values(?,?,?);";
                                SQLiteStatement EtConfReason_statement = App_db.compileStatement(EtConfReason_sql);
                                EtConfReason_statement.clearBindings();
                                for (VHLP_SER.EtConfReason_Result eCR : etConfReasonResults) {
                                    EtConfReason_statement.bindString(1, c_e.check_empty(eCR.getWerks()));
                                    EtConfReason_statement.bindString(2, c_e.check_empty(eCR.getGrund()));
                                    EtConfReason_statement.bindString(3, c_e.check_empty(eCR.getGrdtx()));
                                    EtConfReason_statement.execute();
                                }
                            }
                        }

                        /*EtSteus*/
                        VHLP_SER.EtSteus etSteus = results.get(0).getEtSteus();
                        if (etSteus != null) {
                            List<VHLP_SER.EtSteus_Result> etSteusResults = etSteus.getResults();
                            if (etSteusResults != null && etSteusResults.size() > 0) {
                                String EtSteus_sql = "Insert into GET_CONTROL_KEY (Steus, Txt) values(?,?);";
                                SQLiteStatement EtSteus_statement = App_db.compileStatement(EtSteus_sql);
                                EtSteus_statement.clearBindings();
                                for (VHLP_SER.EtSteus_Result eS : etSteusResults) {
                                    EtSteus_statement.bindString(1, c_e.check_empty(eS.getSteus()));
                                    EtSteus_statement.bindString(2, c_e.check_empty(eS.getTxt()));
                                    EtSteus_statement.execute();
                                }
                            }
                        }

                        /*EtStloc*/
                        VHLP_SER.EtStloc etStloc = results.get(0).getEtStloc();
                        if (etStloc != null) {
                            List<VHLP_SER.EtStloc_Result> etStlocResults = etStloc.getResults();
                            if (etStlocResults != null && etStlocResults.size() > 0) {
                                String EtStloc_sql = "Insert into GET_SLOC (Werks, Name1, Lgort, Lgobe)" +
                                        " values(?,?,?,?);";
                                SQLiteStatement EtStloc_statement = App_db.compileStatement(EtStloc_sql);
                                EtStloc_statement.clearBindings();
                                for (VHLP_SER.EtStloc_Result eSL : etStlocResults) {
                                    EtStloc_statement.bindString(1, c_e.check_empty(eSL.getWerks()));
                                    EtStloc_statement.bindString(2, c_e.check_empty(eSL.getName1()));
                                    EtStloc_statement.bindString(3, c_e.check_empty(eSL.getLgort()));
                                    EtStloc_statement.bindString(4, c_e.check_empty(eSL.getLgobe()));
                                    EtStloc_statement.execute();
                                }
                            }
                        }

                        /*EtPlants*/
                        VHLP_SER.EtPlants etPlants = results.get(0).getEtPlants();
                        if (etPlants != null) {
                            List<VHLP_SER.EtPlants_Result> etPlantsResults = etPlants.getResults();
                            if (etPlantsResults != null && etPlantsResults.size() > 0) {
                                String EtPlants_sql = "Insert into GET_PLANTS (Werks, Name1) values(?,?);";
                                SQLiteStatement EtPlants_statement = App_db.compileStatement(EtPlants_sql);
                                EtPlants_statement.clearBindings();
                                for (VHLP_SER.EtPlants_Result eP : etPlantsResults) {
                                    EtPlants_statement.bindString(1, c_e.check_empty(eP.getWerks()));
                                    EtPlants_statement.bindString(2, c_e.check_empty(eP.getName1()));
                                    EtPlants_statement.execute();
                                }
                            }
                        }

                        /*EtUnits*/
                        VHLP_SER.EtUnits etUnits = results.get(0).getEtUnits();
                        if (etUnits != null) {
                            List<VHLP_SER.EtUnits_Result> etUnitsResults = etUnits.getResults();
                            if (etUnitsResults != null && etUnitsResults.size() > 0) {
                                String EtUnits_sql = "Insert into GET_UNITS (UnitType, Meins) values(?,?);";
                                SQLiteStatement EtUnits_statement = App_db.compileStatement(EtUnits_sql);
                                EtUnits_statement.clearBindings();
                                for (VHLP_SER.EtUnits_Result eU : etUnitsResults) {
                                    EtUnits_statement.bindString(1, c_e.check_empty(eU.getUnitType()));
                                    EtUnits_statement.bindString(2, c_e.check_empty(eU.getMeins()));
                                    EtUnits_statement.execute();
                                }
                            }
                        }

                        /*EtPernr*/
                        VHLP_SER.EtPernr etPernr = results.get(0).getEtPernr();
                        if (etPernr != null) {
                            List<VHLP_SER.EtPernr_Result> etPernrResults = etPernr.getResults();
                            if (etPernrResults != null && etPernrResults.size() > 0) {
                                String EtPernr_sql = "Insert into GET_EtPernr (Werks, Arbpl, Objid," +
                                        "Lastname,Firstname) values(?,?,?,?,?);";
                                SQLiteStatement EtPernr_statement = App_db.compileStatement(EtPernr_sql);
                                EtPernr_statement.clearBindings();
                                for (VHLP_SER.EtPernr_Result eP : etPernrResults) {
                                    EtPernr_statement.bindString(1, c_e.check_empty(eP.getWerks()));
                                    EtPernr_statement.bindString(2, c_e.check_empty(eP.getArbpl()));
                                    EtPernr_statement.bindString(3, c_e.check_empty(eP.getObjid()));
                                    EtPernr_statement.bindString(4, c_e.check_empty(eP.getLastname()));
                                    EtPernr_statement.bindString(5, c_e.check_empty(eP.getFirstname()));
                                    EtPernr_statement.execute();
                                }
                            }
                        }

                        /*EtWkctrPlant*/
                        VHLP_SER.EtWkctrPlant etWkctrPlant = results.get(0).getEtWkctrPlant();
                        if (etWkctrPlant != null) {
                            List<VHLP_SER.EtWkctrPlant_Result> etWkctrPlantResults = etWkctrPlant.getResults();
                            if (etWkctrPlantResults != null && etWkctrPlantResults.size() > 0) {
                                String EtWkctrPlant_sql = "Insert into GET_WKCTR (Bukrs, Kokrs, Objid," +
                                        " Steus, Werks, Name1, Arbpl, Ktext) values(?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtWkctrPlant_statement = App_db.compileStatement(EtWkctrPlant_sql);
                                EtWkctrPlant_statement.clearBindings();
                                for (VHLP_SER.EtWkctrPlant_Result eW : etWkctrPlantResults) {
                                    EtWkctrPlant_statement.bindString(1, c_e.check_empty(eW.getBukrs()));
                                    EtWkctrPlant_statement.bindString(2, c_e.check_empty(eW.getKokrs()));
                                    EtWkctrPlant_statement.bindString(3, c_e.check_empty(eW.getObjid()));
                                    EtWkctrPlant_statement.bindString(4, c_e.check_empty(eW.getSteus()));
                                    EtWkctrPlant_statement.bindString(5, c_e.check_empty(eW.getWerks()));
                                    EtWkctrPlant_statement.bindString(6, c_e.check_empty(eW.getName1()));
                                    EtWkctrPlant_statement.bindString(7, c_e.check_empty(eW.getArbpl()));
                                    EtWkctrPlant_statement.bindString(8, c_e.check_empty(eW.getKtext()));
                                    EtWkctrPlant_statement.execute();
                                }
                            }
                        }

                        /*EtKostl*/
                        VHLP_SER.EtKostl etKostl = results.get(0).getEtKostl();
                        if (etKostl != null) {
                            List<VHLP_SER.EtKostl_Result> etKostlResults = etKostl.getResults();
                            if (etKostlResults != null && etKostlResults.size() > 0) {
                                String EtKostl_sql = "Insert into GET_LIST_COST_CENTER (Bukrs, Kostl," +
                                        " Ktext, Kokrs, Werks, Warea) values(?,?,?,?,?,?);";
                                SQLiteStatement EtKostl_statement = App_db.compileStatement(EtKostl_sql);
                                EtKostl_statement.clearBindings();
                                for (VHLP_SER.EtKostl_Result eK : etKostlResults) {
                                    EtKostl_statement.bindString(1, c_e.check_empty(eK.getBukrs()));
                                    EtKostl_statement.bindString(2, c_e.check_empty(eK.getKostl()));
                                    EtKostl_statement.bindString(3, c_e.check_empty(eK.getKtext()));
                                    EtKostl_statement.bindString(4, c_e.check_empty(eK.getKokrs()));
                                    EtKostl_statement.bindString(5, c_e.check_empty(eK.getWerks()));
                                    EtKostl_statement.bindString(6, c_e.check_empty(eK.getWarea()));
                                    EtKostl_statement.execute();
                                }
                            }
                        }

                        /*EtMovementTypes*/
                        VHLP_SER.EtMovementTypes etMovementTypes = results.get(0).getEtMovementTypes();
                        if (etMovementTypes != null) {
                            List<VHLP_SER.EtMovementTypes_Result> etMovementTypesResults = etMovementTypes.getResults();
                            if (etMovementTypesResults != null && etMovementTypesResults.size() > 0) {
                                String EtMovementTypes_sql = "Insert into GET_LIST_MOVEMENT_TYPES (Bwart, Btext) values(?,?);";
                                SQLiteStatement EtMovementTypes_statement = App_db.compileStatement(EtMovementTypes_sql);
                                EtMovementTypes_statement.clearBindings();
                                for (VHLP_SER.EtMovementTypes_Result eMT : etMovementTypesResults) {
                                    EtMovementTypes_statement.bindString(1, c_e.check_empty(eMT.getBwart()));
                                    EtMovementTypes_statement.bindString(2, c_e.check_empty(eMT.getBtext()));
                                    EtMovementTypes_statement.execute();
                                }
                            }
                        }

                        /*EtOrdPriority*/
                        VHLP_SER.EtOrdPriority etOrdPriority = results.get(0).getEtOrdPriority();
                        if (etOrdPriority != null) {
                            List<VHLP_SER.EtOrdPriority_Result> etOrdPriorityResults = etOrdPriority.getResults();
                            if (etOrdPriorityResults != null && etOrdPriorityResults.size() > 0) {
                                String EtOrdPriority_sql = "Insert into GET_ORDER_PRIORITY (Priok, Priokx) values(?,?);";
                                SQLiteStatement EtOrdPriority_statement = App_db.compileStatement(EtOrdPriority_sql);
                                EtOrdPriority_statement.clearBindings();
                                for (VHLP_SER.EtOrdPriority_Result eOP : etOrdPriorityResults) {
                                    EtOrdPriority_statement.bindString(1, c_e.check_empty(eOP.getPriok()));
                                    EtOrdPriority_statement.bindString(2, c_e.check_empty(eOP.getPriokx()));
                                    EtOrdPriority_statement.execute();
                                }
                            }
                        }

                        /*EtOrdtypes*/
                        VHLP_SER.EtOrdTypes etOrdTypes = results.get(0).getEtOrdTypes();
                        if (etOrdTypes != null) {
                            List<VHLP_SER.EtOrdTypes_Result> etOrdTypesResults = etOrdTypes.getResults();
                            if (etOrdTypesResults != null && etOrdTypesResults.size() > 0) {
                                String EtOrdTypes_sql = "Insert into GET_ORDER_TYPES (Auart, Txt) values(?,?);";
                                SQLiteStatement EtOrdTypes_statement = App_db.compileStatement(EtOrdTypes_sql);
                                EtOrdTypes_statement.clearBindings();
                                for (VHLP_SER.EtOrdTypes_Result eOT : etOrdTypesResults) {
                                    EtOrdTypes_statement.bindString(1, c_e.check_empty(eOT.getAuart()));
                                    EtOrdTypes_statement.bindString(2, c_e.check_empty(eOT.getTxt()));
                                    EtOrdTypes_statement.execute();
                                }
                            }
                        }

                        /*EtNotifPriority*/
                        VHLP_SER.EtNotifPriority etNotifPriority = results.get(0).getEtNotifPriority();
                        if (etNotifPriority != null) {
                            List<VHLP_SER.EtNotifPriority_Result> etNotifPriorityResults = etNotifPriority.getResults();
                            if (etNotifPriorityResults != null && etNotifPriorityResults.size() > 0) {
                                String EtNotifPriority_sql = "Insert into GET_NOTIFICATION_PRIORITY (Priok, Priokx) values(?,?);";
                                SQLiteStatement EtNotifPriority_statement = App_db.compileStatement(EtNotifPriority_sql);
                                EtNotifPriority_statement.clearBindings();
                                for (VHLP_SER.EtNotifPriority_Result eNP : etNotifPriorityResults) {
                                    EtNotifPriority_statement.bindString(1, c_e.check_empty(eNP.getPriok()));
                                    EtNotifPriority_statement.bindString(2, c_e.check_empty(eNP.getPriokx()));
                                    EtNotifPriority_statement.execute();
                                }
                            }
                        }

                        /*EtNotifTypes*/
                        VHLP_SER.EtNotifTypes etNotifTypes = results.get(0).getEtNotifTypes();
                        if (etNotifTypes != null) {
                            List<VHLP_SER.EtNotifTypes_Result> etNotifTypesResults = etNotifTypes.getResults();
                            if (etNotifTypesResults != null && etNotifTypesResults.size() > 0) {
                                String EtNotifTypes_sql = "Insert into GET_NOTIFICATION_TYPES (Qmart, Qmartx) values(?,?);";
                                SQLiteStatement EtNotifTypes_statement = App_db.compileStatement(EtNotifTypes_sql);
                                EtNotifTypes_statement.clearBindings();
                                for (VHLP_SER.EtNotifTypes_Result eNT : etNotifTypesResults) {
                                    EtNotifTypes_statement.bindString(1, c_e.check_empty(eNT.getQmart()));
                                    EtNotifTypes_statement.bindString(2, c_e.check_empty(eNT.getQmartx()));
                                    EtNotifTypes_statement.execute();
                                }
                            }
                        }

                        /*EtNotifCodes*/
                        VHLP_SER.EtNotifCodes etNotifCodes = results.get(0).getEtNotifCodes();
                        if (etNotifCodes != null) {
                            List<VHLP_SER.EtNotifCodes_Result> etNotifCodes_results = etNotifCodes.getResults();
                            if (etNotifCodes_results != null && etNotifCodes_results.size() > 0) {
                                for (VHLP_SER.EtNotifCodes_Result eNC : etNotifCodes_results) {

                                    /*ItemCodes*/
                                    VHLP_SER.ItemCodes itemCodes = eNC.getItemCodes();
                                    if (itemCodes != null) {
                                        List<VHLP_SER.ItemCodes_Result> itemCodesResults = itemCodes.getResults();
                                        if (itemCodesResults != null && itemCodesResults.size() > 0) {
                                            for (VHLP_SER.ItemCodes_Result iC : itemCodesResults) {
                                                VHLP_SER.Codes codes = iC.getICodes();
                                                if (codes != null) {
                                                    List<VHLP_SER.Codes_Result> codesResults = codes.getResults();
                                                    if (codesResults != null && codesResults.size() > 0) {
                                                        for (VHLP_SER.Codes_Result cR : codesResults) {
                                                            String ItemCodes_sql = "Insert into Get_NOTIFCODES_ItemCodes " +
                                                                    "(NotifType, Rbnr, Codegruppe, Kurztext, Code, " +
                                                                    "Kurztext1) values(?,?,?,?,?,?);";
                                                            SQLiteStatement ItemCodes_statement = App_db.compileStatement(ItemCodes_sql);
                                                            ItemCodes_statement.clearBindings();
                                                            ItemCodes_statement.bindString(1, c_e.check_empty(eNC.getNotifType()));
                                                            ItemCodes_statement.bindString(2, c_e.check_empty(eNC.getRbnr()));
                                                            ItemCodes_statement.bindString(3, c_e.check_empty(iC.getCodegruppe()));
                                                            ItemCodes_statement.bindString(4, c_e.check_empty(iC.getKurztext()));
                                                            ItemCodes_statement.bindString(5, c_e.check_empty(cR.getCode()));
                                                            ItemCodes_statement.bindString(6, c_e.check_empty(cR.getKurztext1()));
                                                            ItemCodes_statement.execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /*CauseCodes*/
                                    VHLP_SER.CauseCodes causeCodes = eNC.getCauseCodes();
                                    if (causeCodes != null) {
                                        List<VHLP_SER.CauseCodes_Result> causeCodesResults = causeCodes.getResults();
                                        if (causeCodesResults != null && causeCodesResults.size() > 0) {
                                            for (VHLP_SER.CauseCodes_Result cC : causeCodesResults) {
                                                VHLP_SER.Codes codes = cC.getICodes();
                                                if (codes != null) {
                                                    List<VHLP_SER.Codes_Result> codesResults = codes.getResults();
                                                    if (codesResults != null && codesResults.size() > 0) {
                                                        for (VHLP_SER.Codes_Result cR : codesResults) {
                                                            String CauseCodes_sql = "Insert into Get_NOTIFCODES_CauseCodes (NotifType, Rbnr," +
                                                                    " Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                                                            SQLiteStatement CauseCodes_statement = App_db.compileStatement(CauseCodes_sql);
                                                            CauseCodes_statement.clearBindings();
                                                            CauseCodes_statement.bindString(1, c_e.check_empty(eNC.getNotifType()));
                                                            CauseCodes_statement.bindString(2, c_e.check_empty(eNC.getRbnr()));
                                                            CauseCodes_statement.bindString(3, c_e.check_empty(cC.getCodegruppe()));
                                                            CauseCodes_statement.bindString(4, c_e.check_empty(cC.getKurztext()));
                                                            CauseCodes_statement.bindString(5, c_e.check_empty(cR.getCode()));
                                                            CauseCodes_statement.bindString(6, c_e.check_empty(cR.getKurztext1()));
                                                            CauseCodes_statement.execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /*ObjectCodes*/
                                    VHLP_SER.ObjectCodes objectCodes = eNC.getObjectCodes();
                                    if (objectCodes != null) {
                                        List<VHLP_SER.ObjectCodes_Result> objectCodesResults = objectCodes.getResults();
                                        if (objectCodesResults != null && objectCodesResults.size() > 0) {
                                            for (VHLP_SER.ObjectCodes_Result oC : objectCodesResults) {
                                                VHLP_SER.Codes codes = oC.getICodes();
                                                if (codes != null) {
                                                    List<VHLP_SER.Codes_Result> codesResults = codes.getResults();
                                                    if (codesResults != null && codesResults.size() > 0) {
                                                        for (VHLP_SER.Codes_Result cR : codesResults) {
                                                            String ObjectCodes_sql = "Insert into Get_NOTIFCODES_ObjectCodes (NotifType, Rbnr," +
                                                                    " Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                                                            SQLiteStatement ObjectCodes_statement = App_db.compileStatement(ObjectCodes_sql);
                                                            ObjectCodes_statement.clearBindings();
                                                            ObjectCodes_statement.bindString(1, c_e.check_empty(eNC.getNotifType()));
                                                            ObjectCodes_statement.bindString(2, c_e.check_empty(eNC.getRbnr()));
                                                            ObjectCodes_statement.bindString(3, c_e.check_empty(oC.getCodegruppe()));
                                                            ObjectCodes_statement.bindString(4, c_e.check_empty(oC.getKurztext()));
                                                            ObjectCodes_statement.bindString(5, c_e.check_empty(cR.getCode()));
                                                            ObjectCodes_statement.bindString(6, c_e.check_empty(cR.getKurztext1()));
                                                            ObjectCodes_statement.execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    /*ActCodes*/
                                    VHLP_SER.ActCodes actCodes = eNC.getActCodes();
                                    if (actCodes != null) {
                                        List<VHLP_SER.ActCodes_Result> actCodesResults = actCodes.getResults();
                                        if (actCodesResults != null && actCodesResults.size() > 0) {
                                            for (VHLP_SER.ActCodes_Result aC : actCodesResults) {
                                                VHLP_SER.Codes codes = aC.getACall();
                                                if (codes != null) {
                                                    List<VHLP_SER.Codes_Result> codesResults = codes.getResults();
                                                    if (codesResults != null && codesResults.size() > 0) {
                                                        for (VHLP_SER.Codes_Result cR : codesResults) {
                                                            String ActCodes_sql = "Insert into Get_NOTIFCODES_ActCodes (NotifType, Rbnr," +
                                                                    " Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                                                            SQLiteStatement ActCodes_statement = App_db.compileStatement(ActCodes_sql);
                                                            ActCodes_statement.clearBindings();
                                                            ActCodes_statement.bindString(1, c_e.check_empty(eNC.getNotifType()));
                                                            ActCodes_statement.bindString(2, c_e.check_empty(eNC.getRbnr()));
                                                            ActCodes_statement.bindString(3, c_e.check_empty(aC.getCodegruppe()));
                                                            ActCodes_statement.bindString(4, c_e.check_empty(aC.getKurztext()));
                                                            ActCodes_statement.bindString(5, c_e.check_empty(cR.getCode()));
                                                            ActCodes_statement.bindString(6, c_e.check_empty(cR.getKurztext1()));
                                                            ActCodes_statement.execute();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtMeasCodes*/
                        VHLP_SER.EtMeasCodes etMeasCodes = results.get(0).getEtMeasCodes();
                        if (etMeasCodes != null) {
                            List<VHLP_SER.EtMeasCodes_Result> etMeasCodesResults = etMeasCodes.getResults();
                            if (etMeasCodesResults != null && etMeasCodesResults.size() > 0) {
                                for (VHLP_SER.EtMeasCodes_Result eM : etMeasCodesResults) {
                                    VHLP_SER.Codes codes = eM.getEtMeasCodesCodes();
                                    if (codes != null) {
                                        List<VHLP_SER.Codes_Result> codesResults = codes.getResults();
                                        if (codesResults != null && codesResults.size() > 0) {
                                            String EtMeasCodes_sql = "Insert into EtMeasCodes (Codegruppe, Kurztext, Code, " +
                                                    "Kurztext1) values(?,?,?,?);";
                                            SQLiteStatement EtMeasCodes_statement = App_db.compileStatement(EtMeasCodes_sql);
                                            EtMeasCodes_statement.clearBindings();
                                            for (VHLP_SER.Codes_Result cR : codesResults) {
                                                EtMeasCodes_statement.bindString(1, c_e.check_empty(eM.getCodegruppe()));
                                                EtMeasCodes_statement.bindString(2, c_e.check_empty(eM.getKurztext()));
                                                EtMeasCodes_statement.bindString(3, c_e.check_empty(cR.getCode()));
                                                EtMeasCodes_statement.bindString(4, c_e.check_empty(cR.getKurztext1()));
                                                EtMeasCodes_statement.execute();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtWbs*/
                        VHLP_SER.EtWbs etWbs = results.get(0).getEtWbs();
                        if (etWbs != null) {
                            List<VHLP_SER.EtWbs_Result> etWbsResults = etWbs.getResults();
                            if (etWbsResults != null && etWbsResults.size() > 0) {
                                String EtWbs_sql = "Insert into EtWbs ( Iwerk, Gsber, Posid, Poski, Post1, " +
                                        "Pspnr, Pspid) values(?,?,?,?,?,?,?);";
                                SQLiteStatement EtWbs_statement = App_db.compileStatement(EtWbs_sql);
                                EtWbs_statement.clearBindings();
                                for (VHLP_SER.EtWbs_Result eW : etWbsResults) {
                                    EtWbs_statement.bindString(1, c_e.check_empty(eW.getIwerk()));
                                    EtWbs_statement.bindString(2, c_e.check_empty(eW.getGsber()));
                                    EtWbs_statement.bindString(3, c_e.check_empty(eW.getPosid()));
                                    EtWbs_statement.bindString(4, c_e.check_empty(eW.getPoski()));
                                    EtWbs_statement.bindString(5, c_e.check_empty(eW.getPost1()));
                                    EtWbs_statement.bindString(6, c_e.check_empty(eW.getPspnr()));
                                    EtWbs_statement.bindString(7, c_e.check_empty(eW.getPspid()));
                                    EtWbs_statement.execute();
                                }
                            }
                        }

                        /*EtRevnr*/
                        VHLP_SER.EtRevnr etRevnr = results.get(0).getEtRevnr();
                        if (etRevnr != null) {
                            List<VHLP_SER.EtRevnr_Result> etRevnrResults = etRevnr.getResults();
                            if (etRevnrResults != null && etRevnrResults.size() > 0) {
                                String EtRevnr_sql = "Insert into EtRevnr ( Iwerk, Revnr, Revtx) values(?,?,?);";
                                SQLiteStatement EtRevnr_statement = App_db.compileStatement(EtRevnr_sql);
                                EtRevnr_statement.clearBindings();
                                for (VHLP_SER.EtRevnr_Result eR : etRevnrResults) {
                                    EtRevnr_statement.bindString(1, c_e.check_empty(eR.getIwerk()));
                                    EtRevnr_statement.bindString(2, c_e.check_empty(eR.getRevnr()));
                                    EtRevnr_statement.bindString(3, c_e.check_empty(eR.getRevtx()));
                                    EtRevnr_statement.execute();
                                }
                            }
                        }

                        /*EtFields*/
                        VHLP_SER.EtFields etFields = results.get(0).getEtFields();
                        if (etFields != null) {
                            List<VHLP_SER.EtFields_Result> etFieldsResults = etFields.getResults();
                            if (etFieldsResults != null && etFieldsResults.size() > 0) {
                                String EtFields_sql = "Insert into GET_CUSTOM_FIELDS (Fieldname, ZdoctypeItem," +
                                        " Datatype, Tabname, Zdoctype, Sequence, Flabel, Spras, Length)" +
                                        " values(?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement EtFields_statement = App_db.compileStatement(EtFields_sql);
                                EtFields_statement.clearBindings();
                                for (VHLP_SER.EtFields_Result eF : etFieldsResults) {
                                    EtFields_statement.bindString(1, c_e.check_empty(eF.getFieldname()));
                                    EtFields_statement.bindString(2, c_e.check_empty(eF.getZdoctypeItem()));
                                    EtFields_statement.bindString(3, c_e.check_empty(eF.getDatatype()));
                                    EtFields_statement.bindString(4, c_e.check_empty(eF.getTabname()));
                                    EtFields_statement.bindString(5, c_e.check_empty(eF.getZdoctype()));
                                    EtFields_statement.bindString(6, c_e.check_empty(eF.getSequence()));
                                    EtFields_statement.bindString(7, c_e.check_empty(eF.getFlabel()));
                                    EtFields_statement.bindString(8, c_e.check_empty(eF.getSpras()));
                                    EtFields_statement.bindString(9, c_e.check_empty(eF.getLength()));
                                    EtFields_statement.execute();
                                }
                            }
                        }

                        /*EtInspCodes*/
                        VHLP_SER.EtInspCodes etInspCodes = results.get(0).getEtInspCodes();
                        if (etInspCodes != null) {
                            List<VHLP_SER.EtInspCodes_Result> etInspCodesResults = etInspCodes.getResults();
                            if (etInspCodesResults != null && etInspCodesResults.size() > 0) {
                                for (VHLP_SER.EtInspCodes_Result eI : etInspCodesResults) {
                                    VHLP_SER.InspCodes inspCodes = eI.getInspCodes();
                                    if (inspCodes != null) {
                                        List<VHLP_SER.InspCodes_Codes_Result> inspCodesCodesResults = inspCodes.getResults();
                                        if (inspCodesCodesResults != null && inspCodesCodesResults.size() > 0) {
                                            String EtInspCodes_sql = "Insert into EtInspCodes (Werks, Katalogart, Auswahlmge," +
                                                    " Codegruppe, Kurztext, Code, Kurztext1, Bewertung, Fehlklasse, Qkennzahl," +
                                                    " Folgeakti, Fehlklassetxt) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtInspCodes_statement = App_db.compileStatement(EtInspCodes_sql);
                                            EtInspCodes_statement.clearBindings();
                                            for (VHLP_SER.InspCodes_Codes_Result iCR : inspCodesCodesResults) {
                                                EtInspCodes_statement.bindString(1, c_e.check_empty(eI.getWerks()));
                                                EtInspCodes_statement.bindString(2, c_e.check_empty(eI.getKatalogart()));
                                                EtInspCodes_statement.bindString(3, c_e.check_empty(eI.getAuswahlmge()));
                                                EtInspCodes_statement.bindString(4, c_e.check_empty(eI.getCodegruppe()));
                                                EtInspCodes_statement.bindString(5, c_e.check_empty(eI.getKurztext()));
                                                EtInspCodes_statement.bindString(6, c_e.check_empty(iCR.getCode()));
                                                EtInspCodes_statement.bindString(7, c_e.check_empty(iCR.getKurztext1()));
                                                EtInspCodes_statement.bindString(8, c_e.check_empty(iCR.getBewertung()));
                                                EtInspCodes_statement.bindString(9, c_e.check_empty(iCR.getFehlklasse()));
                                                EtInspCodes_statement.bindString(10, c_e.check_empty(String.valueOf(iCR.getQkennzahl())));
                                                EtInspCodes_statement.bindString(11, c_e.check_empty(iCR.getFolgeakti()));
                                                EtInspCodes_statement.bindString(12, c_e.check_empty(iCR.getFehlklassetxt()));
                                                EtInspCodes_statement.execute();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtIlart*/
                        VHLP_SER.EtIlart etIlart = results.get(0).getEtIlart();
                        if (etIlart != null) {
                            List<VHLP_SER.EtIlart_Result> etIlartResults = etIlart.getResults();
                            if (etIlartResults != null && etIlartResults.size() > 0) {
                                String EtIlart_sql = "Insert into EtIlart (Auart, Ilart, Ilatx) values(?,?,?);";
                                SQLiteStatement EtIlart_statement = App_db.compileStatement(EtIlart_sql);
                                EtIlart_statement.clearBindings();
                                for (VHLP_SER.EtIlart_Result eIR : etIlartResults) {
                                    EtIlart_statement.bindString(1, c_e.check_empty(eIR.getAuart()));
                                    EtIlart_statement.bindString(2, c_e.check_empty(eIR.getIlart()));
                                    EtIlart_statement.bindString(3, c_e.check_empty(eIR.getIlatx()));
                                    EtIlart_statement.execute();
                                }
                            }
                        }

                        /*EtUdecCodes*/
                        VHLP_SER.EtUdecCodes etUdecCodes = results.get(0).getEtUdecCodes();
                        if (etUdecCodes != null) {
                            List<VHLP_SER.EtUdecCodes_Result> etUdecCodesResults = etUdecCodes.getResults();
                            if (etUdecCodesResults != null && etUdecCodesResults.size() > 0) {
                                for (VHLP_SER.EtUdecCodes_Result eUC : etUdecCodesResults) {
                                    VHLP_SER.UdecCodes udecCodes = eUC.getUdecCodes();
                                    if (udecCodes != null) {
                                        List<VHLP_SER.UdecCodes_Result> udecCodesResults = udecCodes.getResults();
                                        if (udecCodesResults != null && udecCodesResults.size() > 0) {
                                            String EtUdecCodes_sql = "Insert into EtUdecCodes (Werks, Katalogart, " +
                                                    "Auswahlmge, Codegruppe, Kurztext, Code, Kurztext1, Bewertung," +
                                                    " Fehlklasse, Qkennzahl, Folgeakti, Fehlklassetxt) " +
                                                    "values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtUdecCodes_statement = App_db.compileStatement(EtUdecCodes_sql);
                                            EtUdecCodes_statement.clearBindings();
                                            for (VHLP_SER.UdecCodes_Result uCR : udecCodesResults) {
                                                EtUdecCodes_statement.bindString(1, c_e.check_empty(eUC.getWerks()));
                                                EtUdecCodes_statement.bindString(2, c_e.check_empty(eUC.getKatalogart()));
                                                EtUdecCodes_statement.bindString(3, c_e.check_empty(eUC.getAuswahlmge()));
                                                EtUdecCodes_statement.bindString(4, c_e.check_empty(eUC.getCodegruppe()));
                                                EtUdecCodes_statement.bindString(5, c_e.check_empty(eUC.getKurztext()));
                                                EtUdecCodes_statement.bindString(6, c_e.check_empty(uCR.getCode()));
                                                EtUdecCodes_statement.bindString(7, c_e.check_empty(uCR.getKurztext1()));
                                                EtUdecCodes_statement.bindString(8, c_e.check_empty(uCR.getBewertung()));
                                                EtUdecCodes_statement.bindString(9, c_e.check_empty(uCR.getFehlklasse()));
                                                EtUdecCodes_statement.bindString(10, c_e.check_empty(String.valueOf(uCR.getQkennzahl())));
                                                EtUdecCodes_statement.bindString(11, c_e.check_empty(uCR.getFolgeakti()));
                                                EtUdecCodes_statement.bindString(12, c_e.check_empty(uCR.getFehlklassetxt()));
                                                EtUdecCodes_statement.execute();
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }


                    /*Reading and Inserting Data into Database Table for EtNotifCodes*//*

                    try {
                        String ItemCodes_sql = "Insert into Get_NOTIFCODES_ItemCodes (NotifType, Rbnr, Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                        SQLiteStatement ItemCodes_statement = App_db.compileStatement(ItemCodes_sql);
                        ItemCodes_statement.clearBindings();

                        String CauseCodes_sql = "Insert into Get_NOTIFCODES_CauseCodes (NotifType, Rbnr, Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                        SQLiteStatement CauseCodes_statement = App_db.compileStatement(CauseCodes_sql);
                        CauseCodes_statement.clearBindings();

                        String ObjectCodes_sql = "Insert into Get_NOTIFCODES_ObjectCodes (NotifType, Rbnr, Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                        SQLiteStatement ObjectCodes_statement = App_db.compileStatement(ObjectCodes_sql);
                        ObjectCodes_statement.clearBindings();

                        String ActCodes_sql = "Insert into Get_NOTIFCODES_ActCodes (NotifType, Rbnr, Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?,?,?);";
                        SQLiteStatement ActCodes_statement = App_db.compileStatement(ActCodes_sql);
                        ActCodes_statement.clearBindings();

                        String TaskCodes_sql = "Insert into Get_NOTIFCODES_TaskCodes (Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?);";
                        SQLiteStatement TasktCodes_statement = App_db.compileStatement(TaskCodes_sql);
                        TasktCodes_statement.clearBindings();

                        String EtNotifCodes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtNotifCodes().getResults());
                        if (EtNotifCodes_response_data != null && !EtNotifCodes_response_data.equals("") && !EtNotifCodes_response_data.equals("null")) {
                            JSONArray jsonArray = new JSONArray(EtNotifCodes_response_data);
                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    String NotifType = jsonArray.getJSONObject(j).optString("NotifType");
                                    String Rbnr = jsonArray.getJSONObject(j).optString("Rbnr");

                                    */
                    /*Reading and Inserting Data into Database Table for ItemCodes*//*

                                    String ItemCodes = jsonArray.getJSONObject(j).optString("ItemCodes");
                                    JSONObject ItemCodes_jsonobject = new JSONObject(ItemCodes);
                                    String ItemCodes_Result = ItemCodes_jsonobject.getString("results");
                                    if (ItemCodes_Result != null && !ItemCodes_Result.equals("") && !ItemCodes_Result.equals("null")) {
                                        JSONArray ItemCodes_jsonArray = new JSONArray(ItemCodes_Result);
                                        if (ItemCodes_jsonArray.length() > 0) {
                                            for (int k = 0; k < ItemCodes_jsonArray.length(); k++) {
                                                String Codegruppe = ItemCodes_jsonArray.getJSONObject(k).optString("Codegruppe");
                                                String Kurztext = ItemCodes_jsonArray.getJSONObject(k).optString("Kurztext");
                                                String ICodes = ItemCodes_jsonArray.getJSONObject(k).optString("ICodes");
                                                JSONObject ICodes_jsonobject = new JSONObject(ICodes);
                                                String ICodes_Result = ICodes_jsonobject.getString("results");
                                                JSONArray ICodes_jsonArray = new JSONArray(ICodes_Result);
                                                if (ICodes_jsonArray.length() > 0) {
                                                    for (int m = 0; m < ICodes_jsonArray.length(); m++) {
                                                        String Code = ICodes_jsonArray.getJSONObject(m).optString("Code");
                                                        String Kurztext1 = ICodes_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                        ItemCodes_statement.bindString(1, NotifType);
                                                        ItemCodes_statement.bindString(2, Rbnr);
                                                        ItemCodes_statement.bindString(3, Codegruppe);
                                                        ItemCodes_statement.bindString(4, Kurztext);
                                                        ItemCodes_statement.bindString(5, Code);
                                                        ItemCodes_statement.bindString(6, Kurztext1);
                                                        ItemCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    */
                    /*Reading and Inserting Data into Database Table for ItemCodes*//*



                     */
                    /*Reading and Inserting Data into Database Table for CauseCodes*//*

                                    String CauseCodes = jsonArray.getJSONObject(j).optString("CauseCodes");
                                    JSONObject CauseCodes_jsonobject = new JSONObject(CauseCodes);
                                    String CauseCodes_Result = CauseCodes_jsonobject.getString("results");
                                    if (CauseCodes_Result != null && !CauseCodes_Result.equals("") && !CauseCodes_Result.equals("null")) {
                                        JSONArray CauseCodes_jsonArray = new JSONArray(CauseCodes_Result);
                                        if (CauseCodes_jsonArray.length() > 0) {
                                            for (int k = 0; k < CauseCodes_jsonArray.length(); k++) {
                                                String Codegruppe = CauseCodes_jsonArray.getJSONObject(k).optString("Codegruppe");
                                                String Kurztext = CauseCodes_jsonArray.getJSONObject(k).optString("Kurztext");
                                                String CCall = CauseCodes_jsonArray.getJSONObject(k).optString("CCall");
                                                JSONObject CCall_jsonobject = new JSONObject(CCall);
                                                String CCall_Result = CCall_jsonobject.getString("results");
                                                JSONArray CCall_jsonArray = new JSONArray(CCall_Result);
                                                if (CCall_jsonArray.length() > 0) {
                                                    for (int m = 0; m < CCall_jsonArray.length(); m++) {
                                                        String Code = CCall_jsonArray.getJSONObject(m).optString("Code");
                                                        String Kurztext1 = CCall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                        CauseCodes_statement.bindString(1, NotifType);
                                                        CauseCodes_statement.bindString(2, Rbnr);
                                                        CauseCodes_statement.bindString(3, Codegruppe);
                                                        CauseCodes_statement.bindString(4, Kurztext);
                                                        CauseCodes_statement.bindString(5, Code);
                                                        CauseCodes_statement.bindString(6, Kurztext1);
                                                        CauseCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    */
                    /*Reading and Inserting Data into Database Table for CauseCodes*//*



                     */
                    /*Reading and Inserting Data into Database Table for ObjectCodes*//*

                                    String ObjectCodes = jsonArray.getJSONObject(j).optString("ObjectCodes");
                                    JSONObject ObjectCodes_jsonobject = new JSONObject(ObjectCodes);
                                    String ObjectCodes_Result = ObjectCodes_jsonobject.getString("results");
                                    if (ObjectCodes_Result != null && !ObjectCodes_Result.equals("") && !ObjectCodes_Result.equals("null")) {
                                        JSONArray ObjectCodes_jsonArray = new JSONArray(ObjectCodes_Result);
                                        if (ObjectCodes_jsonArray.length() > 0) {
                                            for (int k = 0; k < ObjectCodes_jsonArray.length(); k++) {
                                                String Codegruppe = ObjectCodes_jsonArray.getJSONObject(k).optString("Codegruppe");
                                                String Kurztext = ObjectCodes_jsonArray.getJSONObject(k).optString("Kurztext");
                                                String OCall = ObjectCodes_jsonArray.getJSONObject(k).optString("OCall");
                                                JSONObject OCall_jsonobject = new JSONObject(OCall);
                                                String OCall_Result = OCall_jsonobject.getString("results");
                                                JSONArray OCall_jsonArray = new JSONArray(OCall_Result);
                                                if (OCall_jsonArray.length() > 0) {
                                                    for (int m = 0; m < OCall_jsonArray.length(); m++) {
                                                        String Code = OCall_jsonArray.getJSONObject(m).optString("Code");
                                                        String Kurztext1 = OCall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                        ObjectCodes_statement.bindString(1, NotifType);
                                                        ObjectCodes_statement.bindString(2, Rbnr);
                                                        ObjectCodes_statement.bindString(3, Codegruppe);
                                                        ObjectCodes_statement.bindString(4, Kurztext);
                                                        ObjectCodes_statement.bindString(5, Code);
                                                        ObjectCodes_statement.bindString(6, Kurztext1);
                                                        ObjectCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    */
                    /*Reading and Inserting Data into Database Table for ObjectCodes*//*



                     */
                    /*Reading and Inserting Data into Database Table for ActCodes*//*

                                    String ActCodes = jsonArray.getJSONObject(j).optString("ActCodes");
                                    JSONObject ActCodes_jsonobject = new JSONObject(ActCodes);
                                    String ActCodes_Result = ActCodes_jsonobject.getString("results");
                                    if (ActCodes_Result != null && !ActCodes_Result.equals("") && !ActCodes_Result.equals("null")) {
                                        JSONArray ActCodes_jsonArray = new JSONArray(ActCodes_Result);
                                        if (ActCodes_jsonArray.length() > 0) {
                                            for (int k = 0; k < ActCodes_jsonArray.length(); k++) {
                                                String Codegruppe = ActCodes_jsonArray.getJSONObject(k).optString("Codegruppe");
                                                String Kurztext = ActCodes_jsonArray.getJSONObject(k).optString("Kurztext");
                                                String ACall = ActCodes_jsonArray.getJSONObject(k).optString("ACall");
                                                JSONObject ACall_jsonobject = new JSONObject(ACall);
                                                String ACall_Result = ACall_jsonobject.getString("results");
                                                JSONArray ACall_jsonArray = new JSONArray(ACall_Result);
                                                if (ACall_jsonArray.length() > 0) {
                                                    for (int m = 0; m < ACall_jsonArray.length(); m++) {
                                                        String Code = ACall_jsonArray.getJSONObject(m).optString("Code");
                                                        String Kurztext1 = ACall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                        ActCodes_statement.bindString(1, NotifType);
                                                        ActCodes_statement.bindString(2, Rbnr);
                                                        ActCodes_statement.bindString(3, Codegruppe);
                                                        ActCodes_statement.bindString(4, Kurztext);
                                                        ActCodes_statement.bindString(5, Code);
                                                        ActCodes_statement.bindString(6, Kurztext1);
                                                        ActCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    */
                    /*Reading and Inserting Data into Database Table for ActCodes*//*



                     */
                    /*Reading and Inserting Data into Database Table for Task Codes*//*

                                    String TaskCodes = jsonArray.getJSONObject(j).optString("TaskCodes");
                                    JSONObject TaskCodes_jsonobject = new JSONObject(TaskCodes);
                                    String TaskCodes_Result = TaskCodes_jsonobject.getString("results");
                                    if (TaskCodes_Result != null && !TaskCodes_Result.equals("") && !TaskCodes_Result.equals("null")) {
                                        JSONArray TaskCodes_jsonArray = new JSONArray(TaskCodes_Result);
                                        if (TaskCodes_jsonArray.length() > 0) {
                                            for (int k = 0; k < TaskCodes_jsonArray.length(); k++) {
                                                String Codegruppe = TaskCodes_jsonArray.getJSONObject(k).optString("Codegruppe");
                                                String Kurztext = TaskCodes_jsonArray.getJSONObject(k).optString("Kurztext");
                                                String TCall = TaskCodes_jsonArray.getJSONObject(k).optString("TCall");
                                                JSONObject TCall_jsonobject = new JSONObject(TCall);
                                                String TCall_Result = TCall_jsonobject.getString("results");
                                                JSONArray TCall_jsonArray = new JSONArray(TCall_Result);
                                                if (TCall_jsonArray.length() > 0) {
                                                    for (int m = 0; m < TCall_jsonArray.length(); m++) {
                                                        String Code = TCall_jsonArray.getJSONObject(m).optString("Code");
                                                        String Kurztext1 = TCall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                        TasktCodes_statement.bindString(1, Codegruppe);
                                                        TasktCodes_statement.bindString(2, Kurztext);
                                                        TasktCodes_statement.bindString(3, Code);
                                                        TasktCodes_statement.bindString(4, Kurztext1);
                                                        TasktCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    */
                    /*Reading and Inserting Data into Database Table for Task Codes*//*


                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    */
                    /*Reading and Inserting Data into Database Table for EtNotifCodes*//*
*/
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
                Get_Response = "exception";
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
