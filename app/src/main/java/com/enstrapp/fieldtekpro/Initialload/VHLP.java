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

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";


    private static final String TABLE_EtGlacc = "EtGlacc";
    private static final String KEY_EtGlacc_ID = "id";
    private static final String KEY_EtGlacc_Bukrs = "Bukrs";
    private static final String KEY_EtGlacc_Ktopl = "Ktopl";
    private static final String KEY_EtGlacc_Glacct = "Glacct";
    private static final String KEY_EtGlacc_GlacctText = "GlacctText";


    private static final String TABLE_EtAsset = "EtAsset";
    private static final String KEY_EtAsset_ID = "id";
    private static final String KEY_EtAsset_Bukrs = "Bukrs";
    private static final String KEY_EtAsset_Anln1 = "Anln1";
    private static final String KEY_EtAsset_Anlhtxt = "Anlhtxt";


    private static final String TABLE_EtValType = "EtValType";
    private static final String KEY_EtValType_ID = "id";
    private static final String KEY_EtValType_Bukrs = "Batch";


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
                String CREATE_GET_CUSTOM_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_CUSTOM_FIELDS + ""
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
                String CREATE_TABLE_EtUdecCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_EtUdecCodes + ""
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
                String CREATE_TABLE_EtInspCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_EtInspCodes + ""
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
                String CREATE_GET_LIST_COST_CENTER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_LIST_COST_CENTER + ""
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


                /* EtGlacc Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtGlacc);
                String CREATE_GET_TABLE_EtGlacc = "CREATE TABLE IF NOT EXISTS " + TABLE_EtGlacc + ""
                        + "( "
                        + KEY_EtGlacc_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtGlacc_Bukrs + " TEXT,"
                        + KEY_EtGlacc_Ktopl + " TEXT,"
                        + KEY_EtGlacc_Glacct + " TEXT,"
                        + KEY_EtGlacc_GlacctText + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_EtGlacc);
                /* EtGlacc Table and Fields Names */



                /* EtAsset Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtAsset);
                String CREATE_GET_TABLE_EtAsset = "CREATE TABLE IF NOT EXISTS " + TABLE_EtAsset + ""
                        + "( "
                        + KEY_EtAsset_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtAsset_Bukrs + " TEXT,"
                        + KEY_EtAsset_Anln1 + " TEXT,"
                        + KEY_EtAsset_Anlhtxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_EtAsset);
                /* EtAsset Table and Fields Names */



                /* EtTq80 Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtTq80);
                String CREATE_GET_TABLE_GET_EtTq80 = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtTq80 + ""
                        + "( "
                        + KEY_GET_EtTq80_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtTq80_Qmart + " TEXT,"
                        + KEY_GET_EtTq80_Auart + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_GET_EtTq80);
                /* EtTq80 Table and Fields Names */


                /* GET_EtConfReason Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtConfReason);
                String CREATE_TABLE_GET_EtConfReason = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtConfReason + ""
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
                String CREATE_GET_LIST_MOVEMENT_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_LIST_MOVEMENT_TYPES + ""
                        + "( "
                        + KEY_GET_LIST_MOVEMENT_TYPES_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Bwart + " TEXT,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Btext + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_MOVEMENT_TYPES_TABLE);
                /* Creating GET_LIST_MOVEMENT_TYPES Table with Fields */

                /* Creating TABLE_Get_NOTIF_CODES_ItemCodes_Codes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Get_NOTIFCODES_ItemCodes);
                String CREATE_Get_NOTIFCODES_ItemCodes_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Get_NOTIFCODES_ItemCodes + ""
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
                String CREATE_TABLE_Get_NOTIFCODES_CauseCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_Get_NOTIFCODES_CauseCodes + ""
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
                String CREATE_TABLE_Get_NOTIFCODES_TaskCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_Notif_TaskCodes + ""
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
                String CREATE_TABLE_Get_NOTIFCODES_ObjectCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_Get_NOTIFCODES_ObjectCodes + ""
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
                String CREATE_TABLE_GET_Notif_ActCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_Notif_ActCodes + ""
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
                String CREATE_TABLE_GET_EtNotifEffect = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtNotifEffect + ""
                        + "( "
                        + KEY_GET_EtNotifEffect_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtNotifEffect_Auswk + " TEXT,"
                        + KEY_GET_EtNotifEffect_Auswkt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtNotifEffect);
                /* Creating GET_EtNotifEffect Table with Fields */



                /* Creating EtValType Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtValType);
                String CREATE_TABLE_EtValType = "CREATE TABLE IF NOT EXISTS " + TABLE_EtValType + ""
                        + "( "
                        + KEY_EtValType_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtValType_Bukrs + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtValType);
                /* Creating EtValType Table with Fields */


                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_NOTIFICATION_PRIORITY);
                String CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_NOTIFICATION_PRIORITY + ""
                        + "( "
                        + KEY_GET_NOTIFICATION__PRIORITY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priok + " TEXT,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE);
                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */

                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_NOTIFICATION_TYPES);
                String CREATE_GET_NOTIFICATIONS_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_NOTIFICATION_TYPES + ""
                        + "( "
                        + KEY_GET_NOTIFICATION_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmart + " TEXT,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmartx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_TYPE_TABLE);
                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */

                /* Creating GET_ORDER_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_ORDER_PRIORITY);
                String CREATE_GET_ORDER_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_ORDER_PRIORITY + ""
                        + "( "
                        + KEY_GET_ORDER__PRIORITY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_PRIORITY_Priok + " TEXT,"
                        + KEY_GET_ORDER_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_PRIORITY_TABLE);
                /* Creating GET_ORDER_PRIORITY Table with Fields */

                /* Creating GET_EtOrdSyscond Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrdSyscond);
                String CREATE_TABLE_GET_EtOrdSyscond = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtOrdSyscond + ""
                        + "( "
                        + KEY_GET_EtOrdSyscond_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrdSyscond_Anlzu + " TEXT,"
                        + KEY_GET_EtOrdSyscond_Anlzux + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrdSyscond);
                /* Creating GET_EtOrdSyscond Table with Fields */

                /* Creating GET_ORDER_TYPES Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_ORDER_TYPES);
                String CREATE_GET_ORDER_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_ORDER_TYPES + ""
                        + "( "
                        + KEY_GET_ORDER__TYPES_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_TYPES_Auart + " TEXT,"
                        + KEY_GET_ORDER_TYPES_Txt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_TYPES_TABLE);
                /* Creating GET_ORDER_TYPES Table with Fields */

                /* Creating GET_PLANTS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_PLANTS);
                String CREATE_GET_PLANTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_PLANTS + ""
                        + "( "
                        + KEY_GET_PLANTS_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_PLANTS_Werks + " TEXT,"
                        + KEY_GET_PLANTS_Name1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_PLANTS_TABLE);
                /* Creating GET_PLANTS Table with Fields */

                /* GET_CONTROL_KEY Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_CONTROL_KEY);
                String CREATE_GET_CONTROL_KEY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_CONTROL_KEY + ""
                        + "( "
                        + KEY_GET_CONTROL_KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_CONTROL_KEY_Steus + " TEXT,"
                        + KEY_GET_CONTROL_KEY_Txt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_CONTROL_KEY_TABLE);
                /* GET_CONTROL_KEY Table and Fields Names */

                /* Creating GET_SLOC Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_SLOC);
                String CREATE_GET_SLOC_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_SLOC + ""
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
                String CREATE_GET_UNITS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_UNITS + ""
                        + "( "
                        + KEY_GET_UNITS_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_UNITS_UnitType + " TEXT,"
                        + KEY_GET_UNITS_Meins + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_UNITS_TABLE);
                /* Creating GET_UNITS Table with Fields */

                /* Creating GET_WKCTR Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_WKCTR);
                String CREATE_GET_WKCTR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_WKCTR + ""
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
                String CREATE_TABLE_GET_EtIngrp = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtIngrp + ""
                        + "( "
                        + KEY_GET_EtIngrp_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtIngrp_Iwerk + " TEXT,"
                        + KEY_GET_EtIngrp_Ingrp + " TEXT,"
                        + KEY_GET_EtIngrp_Innam + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtIngrp);

                /* Creating ET_PARVW Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_ET_PARVW);
                String CREATE_ET_PARVW_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ET_PARVW + ""
                        + "( "
                        + KEY_ET_PARVW_ID + " INTEGER PRIMARY KEY,"
                        + KEY_ET_PARVW_PARVW + " TEXT,"
                        + KEY_ET_PARVW_VTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_ET_PARVW_TABLE);
                /* Creating ET_PARVW Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtPernr);
                String CREATE_TABLE_EtPernr = "CREATE TABLE IF NOT EXISTS " + TABLE_EtPernr + ""
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
                String CREATE_TABLE_EtMeasCodes = "CREATE TABLE IF NOT EXISTS " + TABLE_EtMeasCodes + ""
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
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username", null);
            password = app_sharedpreferences.getString("Password", null);
            String webservice_type = app_sharedpreferences.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"Z", "F4", webservice_type});
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
            Call<VHLP_SER> call = service.getVHLPDetails(url_link, basic, map);
            Response<VHLP_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_Vhlp_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                        App_db.beginTransaction();
                        if (response.body().getD().getResults().get(0).getEtFields() != null)
                            try {
                                if (response.body().getD().getResults().get(0).getEtFields().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtFields().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtFields_Result etFields_result : response.body().getD().getResults()
                                            .get(0).getEtFields().getResults()) {
                                        values.put("Fieldname", etFields_result.getFieldname());
                                        values.put("ZdoctypeItem", etFields_result.getZdoctypeItem());
                                        values.put("Datatype", etFields_result.getDatatype());
                                        values.put("Tabname", etFields_result.getTabname());
                                        values.put("Zdoctype", etFields_result.getZdoctype());
                                        values.put("Sequence", etFields_result.getSequence());
                                        values.put("Flabel", etFields_result.getFlabel());
                                        values.put("Spras", etFields_result.getSpras());
                                        values.put("Length", etFields_result.getLength());
                                        App_db.insert("GET_CUSTOM_FIELDS", null, values);
                                    }
                                }
                                /*Reading and Inserting Data into Database Table for EtFields*/

                                /*Reading and Inserting Data into Database Table for EtInspCodes*/
                                if (response.body().getD().getResults().get(0).getEtInspCodes() != null)
                                    if (response.body().getD().getResults().get(0).getEtInspCodes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtInspCodes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtInspCodes_Result etInspCodes_result : response.body().getD().getResults()
                                                .get(0).getEtInspCodes().getResults()) {
                                            values.put("Werks", etInspCodes_result.getWerks());
                                            values.put("Katalogart", etInspCodes_result.getKatalogart());
                                            values.put("Auswahlmge", etInspCodes_result.getAuswahlmge());
                                            values.put("Codegruppe", etInspCodes_result.getCodegruppe());
                                            values.put("Kurztext", etInspCodes_result.getKurztext());
                                            if (etInspCodes_result.getInspCodes() != null)
                                                if (etInspCodes_result.getInspCodes().getResults() != null
                                                        && etInspCodes_result.getInspCodes().getResults().size() > 0) {
                                                    ContentValues values1 = new ContentValues();
                                                    for (VHLP_SER.InspCodes_Codes_Result inspCodes_codes_result : etInspCodes_result.getInspCodes().getResults()) {
                                                        values.put("Code", inspCodes_codes_result.getCode());
                                                        values.put("Kurztext1", inspCodes_codes_result.getKurztext1());
                                                        values.put("Bewertung", inspCodes_codes_result.getBewertung());
                                                        values.put("Fehlklasse", inspCodes_codes_result.getFehlklasse());
                                                        values.put("Qkennzahl", inspCodes_codes_result.getQkennzahl());
                                                        values.put("Folgeakti", inspCodes_codes_result.getFolgeakti());
                                                        values.put("Fehlklassetxt", inspCodes_codes_result.getFehlklassetxt());

                                                    }

                                                }
                                            App_db.insert("EtInspCodes", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtInspCodes*/

                                /*Reading and Inserting Data into Database Table for ET_USERS*/

                                if (response.body().getD().getResults().get(0).getEtUsers() != null)
                                    if (response.body().getD().getResults().get(0).getEtUsers().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtUsers().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtUsers_Result etUsers_result : response.body().getD().getResults()
                                                .get(0).getEtUsers().getResults()) {
                                            values.put("Muser", etUsers_result.getMuser());
                                            values.put("Fname", etUsers_result.getFname());
                                            values.put("Lname", etUsers_result.getLname());
                                            values.put("Tokenid", etUsers_result.getTokenid());
                                            App_db.insert("EtUsers", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for ET_USERS*/

                                /*Reading and Inserting Data into Database Table for EtTq80*/
                                if (response.body().getD().getResults().get(0).getEtTq80() != null)
                                    if (response.body().getD().getResults().get(0).getEtTq80().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtTq80().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtTq80_Result etTq80_result : response.body().getD().getResults()
                                                .get(0).getEtTq80().getResults()) {
                                            values.put("Qmart", etTq80_result.getQmart());
                                            values.put("Auart", etTq80_result.getAuart());
                                            App_db.insert("EtTq80", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtTq80*/

                                /*Reading and Inserting Data into Database Table for EtNotifEffect*/
                                if (response.body().getD().getResults().get(0).getEtNotifEffect() != null)
                                    if (response.body().getD().getResults().get(0).getEtNotifEffect().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtNotifEffect().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtNotifEffect_Result etNotifEffect_result : response.body().getD().getResults()
                                                .get(0).getEtNotifEffect().getResults()) {
                                            values.put("Auswk", etNotifEffect_result.getAuswk());
                                            values.put("Auswkt", etNotifEffect_result.getAuswkt());
                                            App_db.insert("EtNotifEffect", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtNotifEffect*/

                                /*Reading and Inserting Data into Database Table for EtMeasCodes*/
                                if (response.body().getD().getResults().get(0).getEtMeasCodes() != null)
                                    if (response.body().getD().getResults().get(0).getEtMeasCodes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtMeasCodes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtMeasCodes_Result etMeasCodes_result : response.body().getD().getResults()
                                                .get(0).getEtMeasCodes().getResults()) {
                                            values.put("Codegruppe", etMeasCodes_result.getCodegruppe());
                                            values.put("Kurztext", etMeasCodes_result.getKurztext());
                                            if (etMeasCodes_result.getEtMeasCodesCodes() != null)
                                                if (etMeasCodes_result.getEtMeasCodesCodes().getResults() != null
                                                        && etMeasCodes_result.getEtMeasCodesCodes().getResults().size() > 0) {
                                                    ContentValues values1 = new ContentValues();
                                                    for (VHLP_SER.Codes_Result codes_result : etMeasCodes_result.getEtMeasCodesCodes().getResults()) {
                                                        values.put("Code", codes_result.getCode());
                                                        values.put("Kurztext1", codes_result.getKurztext1());

                                                    }
                                                }
                                            App_db.insert("EtMeasCodes", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtMeasCodes*/

                                /*Reading and Inserting Data into Database Table for EtOrdSyscond*/
                                if (response.body().getD().getResults().get(0).getEtOrdSyscond() != null)
                                    if (response.body().getD().getResults().get(0).getEtOrdSyscond().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtOrdSyscond().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtOrdSyscond_Result etOrdSyscond_result : response.body().getD().getResults()
                                                .get(0).getEtOrdSyscond().getResults()) {
                                            values.put("Anlzu", etOrdSyscond_result.getAnlzu());
                                            values.put("Anlzux", etOrdSyscond_result.getAnlzux());
                                            App_db.insert("EtOrdSyscond", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtOrdSyscond*/

                                /*Reading and Inserting Data into Database Table for EtIngrp*/
                                if (response.body().getD().getResults().get(0).getEtIngrp() != null)
                                    if (response.body().getD().getResults().get(0).getEtIngrp().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtIngrp().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtIngrp_Result etIngrp_result : response.body().getD().getResults()
                                                .get(0).getEtIngrp().getResults()) {
                                            values.put("Iwerk", etIngrp_result.getIwerk());
                                            values.put("Ingrp", etIngrp_result.getIngrp());
                                            values.put("Innam", etIngrp_result.getInnam());
                                            App_db.insert("GET_EtIngrp", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtIngrp*/

                                /*Reading and Inserting Data into Database Table for EtIlart*/
                                if (response.body().getD().getResults().get(0).getEtIlart() != null)
                                    if (response.body().getD().getResults().get(0).getEtIlart().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtIlart().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtIlart_Result etIlart_result : response.body().getD().getResults()
                                                .get(0).getEtIlart().getResults()) {
                                            values.put("Auart", etIlart_result.getAuart());
                                            values.put("Ilart", etIlart_result.getIlart());
                                            values.put("Ilatx", etIlart_result.getIlatx());
                                            App_db.insert("EtIlart", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtIlart*/

                                /*Reading and Inserting Data into Database Table for EtConfReason*/
                                if (response.body().getD().getResults().get(0).getEtConfReason() != null)
                                    if (response.body().getD().getResults().get(0).getEtConfReason().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtConfReason().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtConfReason_Result etConfReason_result : response.body().getD().getResults()
                                                .get(0).getEtConfReason().getResults()) {
                                            values.put("Werks", etConfReason_result.getWerks());
                                            values.put("Grund", etConfReason_result.getGrund());
                                            values.put("Grdtx", etConfReason_result.getGrdtx());
                                            App_db.insert("GET_EtConfReason", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtConfReason*/

                                /*Reading and Inserting Data into Database Table for EtSteus*/
                                if (response.body().getD().getResults().get(0).getEtSteus() != null)
                                    if (response.body().getD().getResults().get(0).getEtSteus().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtSteus().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtSteus_Result etSteus_result : response.body().getD().getResults()
                                                .get(0).getEtSteus().getResults()) {
                                            values.put("Steus", etSteus_result.getSteus());
                                            values.put("Txt", etSteus_result.getTxt());
                                            App_db.insert("GET_CONTROL_KEY", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtSteus*/


                                /*Reading and Inserting Data into Database Table for EtStloc*/
                                if (response.body().getD().getResults().get(0).getEtStloc() != null)
                                    if (response.body().getD().getResults().get(0).getEtStloc().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtStloc().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtStloc_Result etStloc_result : response.body().getD().getResults()
                                                .get(0).getEtStloc().getResults()) {
                                            values.put("Werks", etStloc_result.getWerks());
                                            values.put("Name1", etStloc_result.getName1());
                                            values.put("Lgort", etStloc_result.getLgort());
                                            values.put("Lgobe", etStloc_result.getLgobe());
                                            App_db.insert("GET_SLOC", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtPlants*/
                                if (response.body().getD().getResults().get(0).getEtPlants() != null)
                                    if (response.body().getD().getResults().get(0).getEtPlants().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtPlants().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtPlants_Result etPlants_result : response.body().getD().getResults()
                                                .get(0).getEtPlants().getResults()) {
                                            values.put("Werks", etPlants_result.getWerks());
                                            values.put("Name1", etPlants_result.getName1());
                                            App_db.insert("GET_PLANTS", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtUnits*/
                                if (response.body().getD().getResults().get(0).getEtUnits() != null)
                                    if (response.body().getD().getResults().get(0).getEtUnits().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtUnits().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtUnits_Result etUnits_result : response.body().getD().getResults()
                                                .get(0).getEtUnits().getResults()) {
                                            values.put("UnitType", etUnits_result.getUnitType());
                                            values.put("Meins", etUnits_result.getMeins());
                                            App_db.insert("GET_UNITS", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtPernr*/
                                if (response.body().getD().getResults().get(0).getEtPernr() != null)
                                    if (response.body().getD().getResults().get(0).getEtPernr().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtPernr().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtPernr_Result etPernr_result : response.body().getD().getResults()
                                                .get(0).getEtPernr().getResults()) {
                                            values.put("Werks", etPernr_result.getWerks());
                                            values.put("Arbpl", etPernr_result.getArbpl());
                                            values.put("Objid", etPernr_result.getObjid());
                                            values.put("Lastname", etPernr_result.getLastname());
                                            values.put("Firstname", etPernr_result.getFirstname());
                                            App_db.insert("GET_EtPernr", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtWkctrPlant*/
                                if (response.body().getD().getResults().get(0).getEtWkctrPlant() != null)
                                    if (response.body().getD().getResults().get(0).getEtWkctrPlant().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtWkctrPlant().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtWkctrPlant_Result etWkctrPlant_result : response.body().getD().getResults()
                                                .get(0).getEtWkctrPlant().getResults()) {
                                            values.put("Bukrs", etWkctrPlant_result.getBukrs());
                                            values.put("Kokrs", etWkctrPlant_result.getKokrs());
                                            values.put("Objid", etWkctrPlant_result.getObjid());
                                            values.put("Steus", etWkctrPlant_result.getSteus());
                                            values.put("Werks", etWkctrPlant_result.getWerks());
                                            values.put("Name1", etWkctrPlant_result.getName1());
                                            values.put("Arbpl", etWkctrPlant_result.getArbpl());
                                            values.put("Ktext", etWkctrPlant_result.getKtext());
                                            App_db.insert("GET_WKCTR", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtKostl*/
                                if (response.body().getD().getResults().get(0).getEtKostl() != null)
                                    if (response.body().getD().getResults().get(0).getEtKostl().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtKostl().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtKostl_Result etKostl_result : response.body().getD().getResults()
                                                .get(0).getEtKostl().getResults()) {
                                            values.put("Bukrs", etKostl_result.getBukrs());
                                            values.put("Kostl", etKostl_result.getKostl());
                                            values.put("Ktext", etKostl_result.getKtext());
                                            values.put("Kokrs", etKostl_result.getKokrs());
                                            values.put("Werks", etKostl_result.getWerks());
                                            values.put("Warea", etKostl_result.getWarea());
                                            App_db.insert("GET_LIST_COST_CENTER", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtMovementTypes*/
                                if (response.body().getD().getResults().get(0).getEtMovementTypes() != null)
                                    if (response.body().getD().getResults().get(0).getEtMovementTypes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtMovementTypes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtMovementTypes_Result etMovementTypes_result : response.body().getD().getResults()
                                                .get(0).getEtMovementTypes().getResults()) {
                                            values.put("Bwart", etMovementTypes_result.getBwart());
                                            values.put("Btext", etMovementTypes_result.getBtext());
                                            App_db.insert("GET_LIST_MOVEMENT_TYPES", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtOrdPriority*/
                                if (response.body().getD().getResults().get(0).getEtOrdPriority() != null)
                                    if (response.body().getD().getResults().get(0).getEtOrdPriority().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtOrdPriority().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtOrdPriority_Result etOrdPriority_result : response.body().getD().getResults()
                                                .get(0).getEtOrdPriority().getResults()) {
                                            values.put("Priok", etOrdPriority_result.getPriok());
                                            values.put("Priokx", etOrdPriority_result.getPriokx());
                                            App_db.insert("GET_ORDER_PRIORITY", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtOrdTypes*/
                                if (response.body().getD().getResults().get(0).getEtOrdTypes() != null)
                                    if (response.body().getD().getResults().get(0).getEtOrdTypes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtOrdTypes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtOrdTypes_Result etOrdTypes_result : response.body().getD().getResults()
                                                .get(0).getEtOrdTypes().getResults()) {
                                            values.put("Auart", etOrdTypes_result.getAuart());
                                            values.put("Txt", etOrdTypes_result.getTxt());
                                            App_db.insert("GET_ORDER_TYPES", null, values);
                                        }
                                    }

                                /*EtNotifCodes*/
                                if (response.body().getD().getResults().get(0).getEtNotifCodes() != null) {
                                    if (response.body().getD().getResults().get(0).getEtNotifCodes().getResults() != null &&
                                            response.body().getD().getResults().get(0).getEtNotifCodes().getResults().size() > 0) {
                                        for (VHLP_SER.EtNotifCodes_Result eNC :
                                                response.body().getD().getResults().get(0).getEtNotifCodes().getResults()) {

                                            /*ItemCodes*/
                                            if (eNC.getItemCodes() != null) {
                                                if (eNC.getItemCodes().getResults() != null &&
                                                        eNC.getItemCodes().getResults().size() > 0) {
                                                    for (VHLP_SER.ItemCodes_Result iC : eNC.getItemCodes().getResults()) {
                                                        if (iC.getICodes() != null) {
                                                            if (iC.getICodes().getResults() != null &&
                                                                    iC.getICodes().getResults().size() > 0) {
                                                                ContentValues values = new ContentValues();
                                                                for (VHLP_SER.Codes_Result cR : iC.getICodes().getResults()) {
                                                                    values.put("NotifType", eNC.getNotifType());
                                                                    values.put("Rbnr", eNC.getRbnr());
                                                                    values.put("Codegruppe", iC.getCodegruppe());
                                                                    values.put("Kurztext", iC.getKurztext());
                                                                    values.put("Code", cR.getCode());
                                                                    values.put("Kurztext1", cR.getKurztext1());
                                                                    App_db.insert("Get_NOTIFCODES_ItemCodes", null, values);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            if (eNC.getCauseCodes() != null) {
                                                if (eNC.getCauseCodes().getResults() != null
                                                        && eNC.getCauseCodes().getResults().size() > 0) {
                                                    for (VHLP_SER.CauseCodes_Result causeCodes_result : eNC.getCauseCodes().getResults()) {

                                                        if (causeCodes_result.getICodes() != null)
                                                            if (causeCodes_result.getICodes().getResults() != null
                                                                    && causeCodes_result.getICodes().getResults().size() > 0) {
                                                                ContentValues values = new ContentValues();
                                                                for (VHLP_SER.Codes_Result codes_result : causeCodes_result.getICodes().getResults()) {
                                                                    values.put("NotifType", eNC.getNotifType());
                                                                    values.put("Rbnr", eNC.getRbnr());
                                                                    values.put("Codegruppe", causeCodes_result.getCodegruppe());
                                                                    values.put("Kurztext", causeCodes_result.getKurztext());
                                                                    values.put("Code", codes_result.getCode());
                                                                    values.put("Kurztext1", codes_result.getKurztext1());
                                                                    App_db.insert("Get_NOTIFCODES_CauseCodes", null, values);
                                                                }
                                                            }

                                                    }

                                                }
                                            }
                                            if (eNC.getObjectCodes() != null) {
                                                if (eNC.getObjectCodes().getResults() != null
                                                        && eNC.getObjectCodes().getResults().size() > 0) {
                                                    for (VHLP_SER.ObjectCodes_Result objectCodes_result : eNC.getObjectCodes().getResults()) {
                                                        if (objectCodes_result.getICodes() != null)
                                                            if (objectCodes_result.getICodes().getResults() != null
                                                                    && objectCodes_result.getICodes().getResults().size() > 0) {
                                                                ContentValues values = new ContentValues();
                                                                for (VHLP_SER.Codes_Result codes_result : objectCodes_result.getICodes().getResults()) {
                                                                    values.put("NotifType", eNC.getNotifType());
                                                                    values.put("Rbnr", eNC.getRbnr());
                                                                    values.put("Codegruppe", objectCodes_result.getCodegruppe());
                                                                    values.put("Kurztext", objectCodes_result.getKurztext());
                                                                    values.put("Code", codes_result.getCode());
                                                                    values.put("Kurztext1", codes_result.getKurztext1());
                                                                    App_db.insert("Get_NOTIFCODES_ObjectCodes", null, values);
                                                                }
                                                            }

                                                    }
                                                }
                                            }
                                            if (eNC.getActCodes() != null) {
                                                if (eNC.getActCodes().getResults() != null
                                                        && eNC.getActCodes().getResults().size() > 0) {
                                                    for (VHLP_SER.ActCodes_Result actCodes_result : eNC.getActCodes().getResults()) {
                                                        if (actCodes_result.getACall() != null)
                                                            if (actCodes_result.getACall().getResults() != null
                                                                    && actCodes_result.getACall().getResults().size() > 0) {
                                                                ContentValues values = new ContentValues();
                                                                for (VHLP_SER.Codes_Result codes_result : actCodes_result.getACall().getResults()) {
                                                                    values.put("NotifType", eNC.getNotifType());
                                                                    values.put("Rbnr", eNC.getRbnr());
                                                                    values.put("Codegruppe", actCodes_result.getCodegruppe());
                                                                    values.put("Kurztext", actCodes_result.getKurztext());
                                                                    values.put("Code", codes_result.getCode());
                                                                    values.put("Kurztext1", codes_result.getKurztext1());
                                                                    App_db.insert("Get_NOTIFCODES_ActCodes", null, values);
                                                                }
                                                            }

                                                    }
                                                }
                                            }
                                            if (eNC.getTaskCodes() != null)
                                                if (eNC.getTaskCodes().getResults() != null
                                                        && eNC.getTaskCodes().getResults().size() > 0) {
                                                    for (VHLP_SER.TaskCodes_Result taskCodes_result : eNC.getTaskCodes().getResults()) {
                                                        if (taskCodes_result.getACall() != null)
                                                            if (taskCodes_result.getACall().getResults() != null
                                                                    && taskCodes_result.getACall().getResults().size() > 0) {
                                                                ContentValues values = new ContentValues();
                                                                for (VHLP_SER.Codes_Result codes_result : taskCodes_result.getACall().getResults()) {
                                                                    values.put("Codegruppe", taskCodes_result.getCodegruppe());
                                                                    values.put("Kurztext", taskCodes_result.getKurztext());
                                                                    values.put("Code", codes_result.getCode());
                                                                    values.put("Kurztext1", codes_result.getKurztext1());
                                                                    App_db.insert("Get_NOTIFCODES_TaskCodes", null, values);
                                                                }
                                                            }
                                                    }
                                                }
                                        }
                                    }
                                }

                                /*Reading and Inserting Data into Database Table for EtNotifPriority*/
                                if (response.body().getD().getResults().get(0).getEtNotifPriority() != null)
                                    if (response.body().getD().getResults().get(0).getEtNotifPriority().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtNotifPriority().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtNotifPriority_Result etNotifPriority_result : response.body().getD().getResults()
                                                .get(0).getEtNotifPriority().getResults()) {
                                            values.put("Priok", etNotifPriority_result.getPriok());
                                            values.put("Priokx", etNotifPriority_result.getPriokx());
                                            App_db.insert("GET_NOTIFICATION_PRIORITY", null, values);
                                        }
                                    }

                                /*Reading and Inserting Data into Database Table for EtNotifTypes*/
                                if (response.body().getD().getResults().get(0).getEtNotifTypes() != null)
                                    if (response.body().getD().getResults().get(0).getEtNotifTypes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtNotifTypes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtNotifTypes_Result etNotifTypes_result : response.body().getD().getResults()
                                                .get(0).getEtNotifTypes().getResults()) {
                                            values.put("Qmart", etNotifTypes_result.getQmart());
                                            values.put("Qmartx", etNotifTypes_result.getQmartx());
                                            App_db.insert("GET_NOTIFICATION_TYPES", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtNotifTypes*/


                                /*Reading and Inserting Data into Database Table for EtRevnr*/
                                if (response.body().getD().getResults().get(0).getEtRevnr() != null)
                                    if (response.body().getD().getResults().get(0).getEtRevnr().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtRevnr().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtRevnr_Result etRevnr_result : response.body().getD().getResults()
                                                .get(0).getEtRevnr().getResults()) {
                                            values.put("Iwerk", etRevnr_result.getIwerk());
                                            values.put("Revnr", etRevnr_result.getRevnr());
                                            values.put("Revtx", etRevnr_result.getRevtx());
                                            App_db.insert("EtRevnr", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtRevnr*/

                                /*Reading and Inserting Data into Database Table for EtWbs*/
                                if (response.body().getD().getResults().get(0).getEtWbs() != null)
                                    if (response.body().getD().getResults().get(0).getEtWbs().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtWbs().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtWbs_Result etWbs_result : response.body().getD().getResults()
                                                .get(0).getEtWbs().getResults()) {
                                            values.put("Iwerk", etWbs_result.getIwerk());
                                            values.put("Gsber", etWbs_result.getGsber());
                                            values.put("Posid", etWbs_result.getPosid());
                                            values.put("Poski", etWbs_result.getPoski());
                                            values.put("Post1", etWbs_result.getPost1());
                                            values.put("Pspnr", etWbs_result.getPspnr());
                                            values.put("Pspid", etWbs_result.getPspid());
                                            App_db.insert("EtWbs", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtWbs*/


                                /*Reading and Inserting Data into Database Table for EtUdecCodes*/
                                if (response.body().getD().getResults().get(0).getEtUdecCodes() != null)
                                    if (response.body().getD().getResults().get(0).getEtUdecCodes().getResults() != null
                                            && response.body().getD().getResults().get(0).getEtUdecCodes().getResults().size() > 0) {
                                        ContentValues values = new ContentValues();
                                        for (VHLP_SER.EtUdecCodes_Result etUdecCodes_result : response.body().getD().getResults()
                                                .get(0).getEtUdecCodes().getResults()) {
                                            values.put("Werks", etUdecCodes_result.getWerks());
                                            values.put("Katalogart", etUdecCodes_result.getKatalogart());
                                            values.put("Auswahlmge", etUdecCodes_result.getAuswahlmge());
                                            values.put("Codegruppe", etUdecCodes_result.getCodegruppe());
                                            values.put("Kurztext", etUdecCodes_result.getKurztext());
                                            if (etUdecCodes_result.getUdecCodes() != null)
                                                if (etUdecCodes_result.getUdecCodes().getResults() != null
                                                        && etUdecCodes_result.getUdecCodes().getResults().size() > 0) {
                                                    ContentValues values1 = new ContentValues();
                                                    for (VHLP_SER.UdecCodes_Result udecCodes_result : etUdecCodes_result.getUdecCodes().getResults()) {
                                                        values.put("Code", udecCodes_result.getCode());
                                                        values.put("Kurztext1", udecCodes_result.getKurztext1());
                                                        values.put("Bewertung", udecCodes_result.getBewertung());
                                                        values.put("Fehlklasse", udecCodes_result.getFehlklasse());
                                                        values.put("Qkennzahl", udecCodes_result.getQkennzahl());
                                                        values.put("Folgeakti", udecCodes_result.getFolgeakti());
                                                        values.put("Fehlklassetxt", udecCodes_result.getFehlklassetxt());

                                                    }
                                                }
                                            App_db.insert("EtUdecCodes", null, values);
                                        }
                                    }
                                /*Reading and Inserting Data into Database Table for EtMeasCodes*/

                                /* Creating EtValType Table with Fields */
                                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtValType);
                                String CREATE_TABLE_EtValType = "CREATE TABLE IF NOT EXISTS " + TABLE_EtValType + ""
                                        + "( "
                                        + KEY_EtValType_ID + " INTEGER PRIMARY KEY,"
                                        + KEY_EtValType_Bukrs + " TEXT"
                                        + ")";
                                App_db.execSQL(CREATE_TABLE_EtValType);
                                /* Creating EtValType Table with Fields */


                                App_db.setTransactionSuccessful();

                                Get_Response = "success";
                            } finally {
                                App_db.endTransaction();
                            }
                    }

                }
            }
        } catch (Exception ex) {
            Log.v("VHLP_Exception", "" + ex.getMessage());
        }

        return Get_Response;
    }

}
