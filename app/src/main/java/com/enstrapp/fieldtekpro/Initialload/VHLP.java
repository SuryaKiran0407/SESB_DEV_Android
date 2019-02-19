package com.enstrapp.fieldtekpro.Initialload;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;

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

public class VHLP
{

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
    private static final String KEY_GET_EtConfReason_Grdtx= "Grdtx";
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
    private static final String KEY_GET_Notif_ActCodes_Code= "Code";
    private static final String KEY_GET_Notif_ActCodes_Kurztext1 = "Kurztext1";
	/* EtNotifCodes_ActCodes Table and Fields Names */

    /* EtNotifCodes_TaskCodes Table and Fields Names */
    private static final String TABLE_GET_Notif_TaskCodes = "Get_NOTIFCODES_TaskCodes";
    private static final String KEY_GET_Notif_TaskCodes_ID = "id";
    private static final String KEY_GET_Notif_TaskCodes_Codegruppe = "Codegruppe";
    private static final String KEY_GET_Notif_TaskCodes_Kurztext = "Kurztext";
    private static final String KEY_GET_Notif_TaskCodes_Code= "Code";
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
    private static final String KEY_GET_EtIngrp_Innam= "Innam";


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
    private static final String KEY_EtPernr_Objid= "Objid";
    private static final String KEY_EtPernr_Lastname= "Lastname";
    private static final String KEY_EtPernr_Firstname= "Firstname";


    private static final String TABLE_EtIlart = "EtIlart";
    private static final String KEY_EtIlart_ID = "id";
    private static final String KEY_EtIlart_Auart = "Auart";
    private static final String KEY_EtIlart_Ilart = "Ilart";
    private static final String KEY_EtIlart_Ilatx = "Ilatx";


    public static String Get_VHLP_Data(Activity activity, String transmit_type)
    {
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

                           /* try {
                                String EtInspCodes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtInspCodes().getResults());
                                if (EtInspCodes_response_data != null && !EtInspCodes_response_data.equals("") && !EtInspCodes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtInspCodes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtInspCodes_sql = "Insert into EtInspCodes (Werks, Katalogart, Auswahlmge, Codegruppe, Kurztext, Code, Kurztext1, Bewertung, Fehlklasse, Qkennzahl, Folgeakti, Fehlklassetxt) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtInspCodes_statement = App_db.compileStatement(EtInspCodes_sql);
                                        EtInspCodes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String Werks = jsonArray.getJSONObject(j).optString("Werks");
                                            String Katalogart = jsonArray.getJSONObject(j).optString("Katalogart");
                                            String Auswahlmge = jsonArray.getJSONObject(j).optString("Auswahlmge");
                                            String Codegruppe = jsonArray.getJSONObject(j).optString("Codegruppe");
                                            String Kurztext = jsonArray.getJSONObject(j).optString("Kurztext");
                                            String InspCodes = jsonArray.getJSONObject(j).optString("InspCodes");
                                            JSONObject ACall_jsonobject = new JSONObject(InspCodes);
                                            String InspCodes_Result = ACall_jsonobject.getString("results");
                                            JSONArray InspCodes_jsonArray = new JSONArray(InspCodes_Result);
                                            if (InspCodes_jsonArray.length() > 0) {
                                                for (int m = 0; m < InspCodes_jsonArray.length(); m++) {
                                                    String Code = InspCodes_jsonArray.getJSONObject(m).optString("Code");
                                                    String Kurztext1 = InspCodes_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                    String Bewertung = InspCodes_jsonArray.getJSONObject(m).optString("Bewertung");
                                                    String Fehlklasse = InspCodes_jsonArray.getJSONObject(m).optString("Fehlklasse");
                                                    String Qkennzahl = InspCodes_jsonArray.getJSONObject(m).optString("Qkennzahl");
                                                    String Folgeakti = InspCodes_jsonArray.getJSONObject(m).optString("Folgeakti");
                                                    String Fehlklassetxt = InspCodes_jsonArray.getJSONObject(m).optString("Fehlklassetxt");
                                                    EtInspCodes_statement.bindString(1, Werks);
                                                    EtInspCodes_statement.bindString(2, Katalogart);
                                                    EtInspCodes_statement.bindString(3, Auswahlmge);
                                                    EtInspCodes_statement.bindString(4, Codegruppe);
                                                    EtInspCodes_statement.bindString(5, Kurztext);
                                                    EtInspCodes_statement.bindString(6, Code);
                                                    EtInspCodes_statement.bindString(7, Kurztext1);
                                                    EtInspCodes_statement.bindString(8, Bewertung);
                                                    EtInspCodes_statement.bindString(9, Fehlklasse);
                                                    EtInspCodes_statement.bindString(10, Qkennzahl);
                                                    EtInspCodes_statement.bindString(11, Folgeakti);
                                                    EtInspCodes_statement.bindString(12, Fehlklassetxt);
                                                    EtInspCodes_statement.execute();
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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

                           /* try {
                                String EtUsers_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtUsers().getResults());
                                if (EtUsers_response_data != null && !EtUsers_response_data.equals("") && !EtUsers_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtUsers_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtUsers_sql = "Insert into EtUsers (Muser, Fname, Lname, Tokenid) values(?,?,?,?);";
                                        SQLiteStatement EtUsers_statement = App_db.compileStatement(EtUsers_sql);
                                        EtUsers_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtUsers_statement.bindString(1, jsonArray.getJSONObject(j).optString("Muser"));
                                            EtUsers_statement.bindString(2, jsonArray.getJSONObject(j).optString("Fname"));
                                            EtUsers_statement.bindString(3, jsonArray.getJSONObject(j).optString("Lname"));
                                            EtUsers_statement.bindString(4, jsonArray.getJSONObject(j).optString("Tokenid"));
                                            EtUsers_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtTq80_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtTq80().getResults());
                                if (EtTq80_response_data != null && !EtTq80_response_data.equals("") && !EtTq80_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtTq80_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtTq80_sql = "Insert into EtTq80 (QMART, AUART) values(?,?);";
                                        SQLiteStatement EtTq80_statement = App_db.compileStatement(EtTq80_sql);
                                        EtTq80_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtTq80_statement.bindString(1, jsonArray.getJSONObject(j).optString("Qmart"));
                                            EtTq80_statement.bindString(2, jsonArray.getJSONObject(j).optString("Auart"));
                                            EtTq80_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                            /*try {
                                String EtNotifEffect_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtNotifEffect().getResults());
                                if (EtNotifEffect_response_data != null && !EtNotifEffect_response_data.equals("") && !EtNotifEffect_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtNotifEffect_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtNotifEffect_sql = "Insert into EtNotifEffect (Auswk, Auswkt) values(?,?);";
                                        SQLiteStatement EtNotifEffect_statement = App_db.compileStatement(EtNotifEffect_sql);
                                        EtNotifEffect_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtNotifEffect_statement.bindString(1, jsonArray.getJSONObject(j).optString("Auswk"));
                                            EtNotifEffect_statement.bindString(2, jsonArray.getJSONObject(j).optString("Auswkt"));
                                            EtNotifEffect_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtMeasCodes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtMeasCodes().getResults());
                                if (EtMeasCodes_response_data != null && !EtMeasCodes_response_data.equals("") && !EtMeasCodes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtMeasCodes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtMeasCodes_sql = "Insert into EtMeasCodes (Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?);";
                                        SQLiteStatement EtMeasCodes_statement = App_db.compileStatement(EtMeasCodes_sql);
                                        EtMeasCodes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String Codegruppe = jsonArray.getJSONObject(j).optString("Codegruppe");
                                            String Kurztext = jsonArray.getJSONObject(j).optString("Kurztext");
                                            String ACall = jsonArray.getJSONObject(j).optString("MCodes");
                                            JSONObject ACall_jsonobject = new JSONObject(ACall);
                                            String ACall_Result = ACall_jsonobject.getString("results");
                                            JSONArray ACall_jsonArray = new JSONArray(ACall_Result);
                                            if (ACall_jsonArray.length() > 0) {
                                                for (int m = 0; m < ACall_jsonArray.length(); m++) {
                                                    String Code = ACall_jsonArray.getJSONObject(m).optString("Code");
                                                    String Kurztext1 = ACall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                    EtMeasCodes_statement.bindString(1, Codegruppe);
                                                    EtMeasCodes_statement.bindString(2, Kurztext);
                                                    EtMeasCodes_statement.bindString(3, Code);
                                                    EtMeasCodes_statement.bindString(4, Kurztext1);
                                                    EtMeasCodes_statement.execute();
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                            /*try {
                                String EtOrdSyscond_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtOrdSyscond().getResults());
                                if (EtOrdSyscond_response_data != null && !EtOrdSyscond_response_data.equals("") && !EtOrdSyscond_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtOrdSyscond_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrdSyscond_sql = "Insert into EtOrdSyscond (Anlzu, Anlzux) values(?,?);";
                                        SQLiteStatement EtOrdSyscond_statement = App_db.compileStatement(EtOrdSyscond_sql);
                                        EtOrdSyscond_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtOrdSyscond_statement.bindString(1, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtOrdSyscond_statement.bindString(2, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtOrdSyscond_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtIngrp_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtIngrp().getResults());
                                if (EtIngrp_response_data != null && !EtIngrp_response_data.equals("") && !EtIngrp_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtIngrp_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtIngrp_sql = "Insert into GET_EtIngrp (Iwerk, Ingrp, Innam) values(?,?,?);";
                                        SQLiteStatement EtIngrp_statement = App_db.compileStatement(EtIngrp_sql);
                                        EtIngrp_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtIngrp_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtIngrp_statement.bindString(2, jsonArray.getJSONObject(j).optString("Ingrp"));
                                            EtIngrp_statement.bindString(3, jsonArray.getJSONObject(j).optString("Innam"));
                                            EtIngrp_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtIlart_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtIlart().getResults());
                                if (EtIlart_response_data != null && !EtIlart_response_data.equals("") && !EtIlart_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtIlart_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtIlart_sql = "Insert into EtIlart (Auart, Ilart, Ilatx) values(?,?,?);";
                                        SQLiteStatement EtIlart_statement = App_db.compileStatement(EtIlart_sql);
                                        EtIlart_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtIlart_statement.bindString(1, jsonArray.getJSONObject(j).optString("Auart"));
                                            EtIlart_statement.bindString(2, jsonArray.getJSONObject(j).optString("Ilart"));
                                            EtIlart_statement.bindString(3, jsonArray.getJSONObject(j).optString("Ilatx"));
                                            EtIlart_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                          /*  try {
                                String EtConfReason_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtConfReason().getResults());
                                if (EtConfReason_response_data != null && !EtConfReason_response_data.equals("") && !EtConfReason_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtConfReason_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtConfReason_sql = "Insert into GET_EtConfReason (Werks, Grund, Grdtx) values(?,?,?);";
                                        SQLiteStatement EtConfReason_statement = App_db.compileStatement(EtConfReason_sql);
                                        EtConfReason_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtConfReason_statement.bindString(1, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtConfReason_statement.bindString(2, jsonArray.getJSONObject(j).optString("Grund"));
                                            EtConfReason_statement.bindString(3, jsonArray.getJSONObject(j).optString("Grdtx"));
                                            EtConfReason_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtSteus_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtSteus().getResults());
                                if (EtSteus_response_data != null && !EtSteus_response_data.equals("") && !EtSteus_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtSteus_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtSteus_sql = "Insert into GET_CONTROL_KEY (Steus, Txt) values(?,?);";
                                        SQLiteStatement EtSteus_statement = App_db.compileStatement(EtSteus_sql);
                                        EtSteus_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtSteus_statement.bindString(1, jsonArray.getJSONObject(j).optString("Steus"));
                                            EtSteus_statement.bindString(2, jsonArray.getJSONObject(j).optString("Txt"));
                                            EtSteus_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtStloc_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtStloc().getResults());
                                if (EtStloc_response_data != null && !EtStloc_response_data.equals("") && !EtStloc_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtStloc_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtStloc_sql = "Insert into GET_SLOC (Werks, Name1, Lgort, Lgobe) values(?,?,?,?);";
                                        SQLiteStatement EtStloc_statement = App_db.compileStatement(EtStloc_sql);
                                        EtStloc_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtStloc_statement.bindString(1, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtStloc_statement.bindString(2, jsonArray.getJSONObject(j).optString("Name1"));
                                            EtStloc_statement.bindString(3, jsonArray.getJSONObject(j).optString("Lgort"));
                                            EtStloc_statement.bindString(4, jsonArray.getJSONObject(j).optString("Lgobe"));
                                            EtStloc_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                    */        /*Reading and Inserting Data into Database Table for EtStloc*/


                            /*Reading and Inserting Data into Database Table for EtPlants*/
                            if (response.body().getD().getResults().get(0).getEtPlants() != null)
                                if (response.body().getD().getResults().get(0).getEtPlants().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtPlants().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtPlants_Result etPlants_result : response.body().getD().getResults()
                                            .get(0).getEtPlants().getResults()) {
                                        values.put("Werks",etPlants_result.getWerks());
                                        values.put("Name1",etPlants_result.getName1());
                                        App_db.insert("GET_PLANTS", null, values);
                                    }
                                }
                          /*  try {
                                String EtPlants_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtPlants().getResults());
                                if (EtPlants_response_data != null && !EtPlants_response_data.equals("") && !EtPlants_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtPlants_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtPlants_sql = "Insert into GET_PLANTS (Werks, Name1) values(?,?);";
                                        SQLiteStatement EtPlants_statement = App_db.compileStatement(EtPlants_sql);
                                        EtPlants_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtPlants_statement.bindString(1, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtPlants_statement.bindString(2, jsonArray.getJSONObject(j).optString("Name1"));
                                            EtPlants_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtPlants*/


                            /*Reading and Inserting Data into Database Table for EtUnits*/
                            if (response.body().getD().getResults().get(0).getEtUnits() != null)
                                if (response.body().getD().getResults().get(0).getEtUnits().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtUnits().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtUnits_Result etUnits_result : response.body().getD().getResults()
                                            .get(0).getEtUnits().getResults()) {
                                        values.put("UnitType",etUnits_result.getUnitType());
                                        values.put("Meins",etUnits_result.getMeins());
                                        App_db.insert("GET_UNITS", null, values);
                                    }
                                }
                            /*try {
                                String EtUnits_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtUnits().getResults());
                                if (EtUnits_response_data != null && !EtUnits_response_data.equals("") && !EtUnits_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtUnits_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtUnits_sql = "Insert into GET_UNITS (UnitType, Meins) values(?,?);";
                                        SQLiteStatement EtUnits_statement = App_db.compileStatement(EtUnits_sql);
                                        EtUnits_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtUnits_statement.bindString(1, jsonArray.getJSONObject(j).optString("UnitType"));
                                            EtUnits_statement.bindString(2, jsonArray.getJSONObject(j).optString("Meins"));
                                            EtUnits_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtUnits*/


                            /*Reading and Inserting Data into Database Table for EtPernr*/
                            if (response.body().getD().getResults().get(0).getEtPernr() != null)
                                if (response.body().getD().getResults().get(0).getEtPernr().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtPernr().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtPernr_Result etPernr_result : response.body().getD().getResults()
                                            .get(0).getEtPernr().getResults()) {
                                        values.put("Werks",etPernr_result.getWerks());
                                        values.put("Arbpl",etPernr_result.getArbpl());
                                        values.put("Objid",etPernr_result.getObjid());
                                        values.put("Lastname",etPernr_result.getLastname());
                                        values.put("Firstname",etPernr_result.getFirstname());
                                        App_db.insert("GET_EtPernr", null, values);
                                    }
                                }
                            /*try {
                                String EtPernr_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtPernr().getResults());
                                if (EtPernr_response_data != null && !EtPernr_response_data.equals("") && !EtPernr_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtPernr_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtPernr_sql = "Insert into GET_EtPernr (Werks, Arbpl, Objid,Lastname,Firstname) values(?,?,?,?,?);";
                                        SQLiteStatement EtPernr_statement = App_db.compileStatement(EtPernr_sql);
                                        EtPernr_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtPernr_statement.bindString(1, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtPernr_statement.bindString(2, jsonArray.getJSONObject(j).optString("Arbpl"));
                                            EtPernr_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objid"));
                                            EtPernr_statement.bindString(4, jsonArray.getJSONObject(j).optString("Lastname"));
                                            EtPernr_statement.bindString(5, jsonArray.getJSONObject(j).optString("Firstname"));
                                            EtPernr_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtPernr*/


                            /*Reading and Inserting Data into Database Table for EtWkctrPlant*/
                            if (response.body().getD().getResults().get(0).getEtWkctrPlant() != null)
                                if (response.body().getD().getResults().get(0).getEtWkctrPlant().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtWkctrPlant().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtWkctrPlant_Result etWkctrPlant_result : response.body().getD().getResults()
                                            .get(0).getEtWkctrPlant().getResults()) {
                                        values.put("Bukrs",etWkctrPlant_result.getBukrs());
                                        values.put("Kokrs",etWkctrPlant_result.getKokrs());
                                        values.put("Objid",etWkctrPlant_result.getObjid());
                                        values.put("Steus",etWkctrPlant_result.getSteus());
                                        values.put("Werks",etWkctrPlant_result.getWerks());
                                        values.put("Name1",etWkctrPlant_result.getName1());
                                        values.put("Arbpl",etWkctrPlant_result.getArbpl());
                                        values.put("Ktext",etWkctrPlant_result.getKtext());
                                        App_db.insert("GET_WKCTR", null, values);
                                    }
                                }
                           /* try {
                                String EtWkctrPlant_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWkctrPlant().getResults());
                                if (EtWkctrPlant_response_data != null && !EtWkctrPlant_response_data.equals("") && !EtWkctrPlant_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtWkctrPlant_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWkctrPlant_sql = "Insert into GET_WKCTR (Bukrs, Kokrs, Objid, Steus, Werks, Name1, Arbpl, Ktext) values(?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWkctrPlant_statement = App_db.compileStatement(EtWkctrPlant_sql);
                                        EtWkctrPlant_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtWkctrPlant_statement.bindString(1, jsonArray.getJSONObject(j).optString("Bukrs"));
                                            EtWkctrPlant_statement.bindString(2, jsonArray.getJSONObject(j).optString("Kokrs"));
                                            EtWkctrPlant_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objid"));
                                            EtWkctrPlant_statement.bindString(4, jsonArray.getJSONObject(j).optString("Steus"));
                                            EtWkctrPlant_statement.bindString(5, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtWkctrPlant_statement.bindString(6, jsonArray.getJSONObject(j).optString("Name1"));
                                            EtWkctrPlant_statement.bindString(7, jsonArray.getJSONObject(j).optString("Arbpl"));
                                            EtWkctrPlant_statement.bindString(8, jsonArray.getJSONObject(j).optString("Ktext"));
                                            EtWkctrPlant_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtWkctrPlant*/


                            /*Reading and Inserting Data into Database Table for EtKostl*/
                            if (response.body().getD().getResults().get(0).getEtKostl() != null)
                                if (response.body().getD().getResults().get(0).getEtKostl().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtKostl().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtKostl_Result etKostl_result : response.body().getD().getResults()
                                            .get(0).getEtKostl().getResults()) {
                                        values.put("Bukrs",etKostl_result.getBukrs());
                                        values.put("Kostl",etKostl_result.getKostl());
                                        values.put("Ktext",etKostl_result.getKtext());
                                        values.put("Kokrs",etKostl_result.getKokrs());
                                        values.put("Werks",etKostl_result.getWerks());
                                        values.put("Warea",etKostl_result.getWarea());
                                        App_db.insert("GET_LIST_COST_CENTER", null, values);
                                    }
                                }
                           /* try {
                                String EtKostl_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtKostl().getResults());
                                if (EtKostl_response_data != null && !EtKostl_response_data.equals("") && !EtKostl_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtKostl_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtKostl_sql = "Insert into GET_LIST_COST_CENTER (Bukrs, Kostl, Ktext, Kokrs, Werks, Warea) values(?,?,?,?,?,?);";
                                        SQLiteStatement EtKostl_statement = App_db.compileStatement(EtKostl_sql);
                                        EtKostl_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtKostl_statement.bindString(1, jsonArray.getJSONObject(j).optString("Bukrs"));
                                            EtKostl_statement.bindString(2, jsonArray.getJSONObject(j).optString("Kostl"));
                                            EtKostl_statement.bindString(3, jsonArray.getJSONObject(j).optString("Ktext"));
                                            EtKostl_statement.bindString(4, jsonArray.getJSONObject(j).optString("Kokrs"));
                                            EtKostl_statement.bindString(5, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtKostl_statement.bindString(6, jsonArray.getJSONObject(j).optString("Warea"));
                                            EtKostl_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtKostl*/


                            /*Reading and Inserting Data into Database Table for EtMovementTypes*/
                            if (response.body().getD().getResults().get(0).getEtMovementTypes() != null)
                                if (response.body().getD().getResults().get(0).getEtMovementTypes().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtMovementTypes().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtMovementTypes_Result etMovementTypes_result : response.body().getD().getResults()
                                            .get(0).getEtMovementTypes().getResults()) {
                                        values.put("Bwart",etMovementTypes_result.getBwart());
                                        values.put("Btext",etMovementTypes_result.getBtext());
                                        App_db.insert("GET_LIST_MOVEMENT_TYPES", null, values);
                                    }
                                }
                           /* try {
                                String EtMovementTypes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtMovementTypes().getResults());
                                if (EtMovementTypes_response_data != null && !EtMovementTypes_response_data.equals("") && !EtMovementTypes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtMovementTypes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtMovementTypes_sql = "Insert into GET_LIST_MOVEMENT_TYPES (Bwart, Btext) values(?,?);";
                                        SQLiteStatement EtMovementTypes_statement = App_db.compileStatement(EtMovementTypes_sql);
                                        EtMovementTypes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtMovementTypes_statement.bindString(1, jsonArray.getJSONObject(j).optString("Bwart"));
                                            EtMovementTypes_statement.bindString(2, jsonArray.getJSONObject(j).optString("Btext"));
                                            EtMovementTypes_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtOrdTypes*/


                            /*Reading and Inserting Data into Database Table for EtOrdPriority*/
                            if (response.body().getD().getResults().get(0).getEtOrdPriority() != null)
                                if (response.body().getD().getResults().get(0).getEtOrdPriority().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrdPriority().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtOrdPriority_Result etOrdPriority_result : response.body().getD().getResults()
                                            .get(0).getEtOrdPriority().getResults()) {
                                        values.put("Priok",etOrdPriority_result.getPriok());
                                        values.put("Priokx",etOrdPriority_result.getPriokx());
                                        App_db.insert("GET_ORDER_PRIORITY", null, values);
                                    }
                                }
                        /*    try {
                                String EtOrdPriority_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtOrdPriority().getResults());
                                if (EtOrdPriority_response_data != null && !EtOrdPriority_response_data.equals("") && !EtOrdPriority_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtOrdPriority_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrdPriority_sql = "Insert into GET_ORDER_PRIORITY (Priok, Priokx) values(?,?);";
                                        SQLiteStatement EtOrdPriority_statement = App_db.compileStatement(EtOrdPriority_sql);
                                        EtOrdPriority_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtOrdPriority_statement.bindString(1, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtOrdPriority_statement.bindString(2, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtOrdPriority_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtOrdPriority*/


                            /*Reading and Inserting Data into Database Table for EtOrdTypes*/
                            if (response.body().getD().getResults().get(0).getEtOrdTypes() != null)
                                if (response.body().getD().getResults().get(0).getEtOrdTypes().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrdTypes().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtOrdTypes_Result etOrdTypes_result : response.body().getD().getResults()
                                            .get(0).getEtOrdTypes().getResults()) {
                                        values.put("Auart",etOrdTypes_result.getAuart());
                                        values.put("Txt",etOrdTypes_result.getTxt());
                                        App_db.insert("GET_ORDER_TYPES", null, values);
                                    }
                                }
                           /* try {
                                String EtOrdTypes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtOrdTypes().getResults());
                                if (EtOrdTypes_response_data != null && !EtOrdTypes_response_data.equals("") && !EtOrdTypes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtOrdTypes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtOrdTypes_sql = "Insert into GET_ORDER_TYPES (Auart, Txt) values(?,?);";
                                        SQLiteStatement EtOrdTypes_statement = App_db.compileStatement(EtOrdTypes_sql);
                                        EtOrdTypes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtOrdTypes_statement.bindString(1, jsonArray.getJSONObject(j).optString("Auart"));
                                            EtOrdTypes_statement.bindString(2, jsonArray.getJSONObject(j).optString("Txt"));
                                            EtOrdTypes_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtOrdTypes*/


                            /*Reading and Inserting Data into Database Table for EtNotifCodes*/
                            if (response.body().getD().getResults().get(0).getEtNotifCodes() != null)
                                if (response.body().getD().getResults().get(0).getEtNotifCodes().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtNotifCodes().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtNotifCodes_Result etNotifCodes_result : response.body().getD().getResults()
                                            .get(0).getEtNotifCodes().getResults()) {
                                        values.put("NotifType", etNotifCodes_result.getNotifType());
                                        values.put("Rbnr", etNotifCodes_result.getRbnr());
                                        if (etNotifCodes_result.getItemCodes() != null)
                                            if (etNotifCodes_result.getItemCodes().getResults() != null
                                                    && etNotifCodes_result.getItemCodes().getResults().size() > 0) {
                                                ContentValues valuesICode = new ContentValues();
                                                for (VHLP_SER.ItemCodes_Result itemCodes_result : etNotifCodes_result.getItemCodes().getResults()) {
                                                   values.put("Codegruppe", itemCodes_result.getCodegruppe());
                                                   values.put("Kurztext", itemCodes_result.getKurztext());
                                                    if (itemCodes_result.getICodes() != null)
                                                        if (itemCodes_result.getICodes().getResults() != null
                                                                && itemCodes_result.getICodes().getResults().size() > 0) {
                                                            ContentValues valuesCodes = new ContentValues();
                                                            for (VHLP_SER.Codes_Result codes_result : itemCodes_result.getICodes().getResults()) {
                                                                values.put("Code", codes_result.getCode());
                                                                values.put("Kurztext1", codes_result.getKurztext1());

                                                            }
                                                        }

                                                }
                                                App_db.insert("Get_NOTIFCODES_ItemCodes", null, values);
                                            }
                                        if (etNotifCodes_result.getCauseCodes() != null)
                                            if (etNotifCodes_result.getCauseCodes().getResults() != null
                                                    && etNotifCodes_result.getCauseCodes().getResults().size() > 0) {
                                                ContentValues valuesCCode = new ContentValues();
                                                for (VHLP_SER.CauseCodes_Result causeCodes_result : etNotifCodes_result.getCauseCodes().getResults()) {
                                                    values.put("Codegruppe", causeCodes_result.getCodegruppe());
                                                    values.put("Kurztext", causeCodes_result.getKurztext());
                                                    if (causeCodes_result.getICodes() != null)
                                                        if (causeCodes_result.getICodes().getResults() != null
                                                                && causeCodes_result.getICodes().getResults().size() > 0) {
                                                            ContentValues valuesCodes = new ContentValues();
                                                            for (VHLP_SER.Codes_Result codes_result : causeCodes_result.getICodes().getResults()) {
                                                                values.put("Code", codes_result.getCode());
                                                                values.put("Kurztext1", codes_result.getKurztext1());

                                                            }
                                                        }
                                                    App_db.insert("Get_NOTIFCODES_CauseCodes", null, values);
                                                }

                                            }
                                        if (etNotifCodes_result.getObjectCodes() != null)
                                            if (etNotifCodes_result.getObjectCodes().getResults() != null
                                                    && etNotifCodes_result.getObjectCodes().getResults().size() > 0) {
                                                ContentValues valuesOCode = new ContentValues();
                                                for (VHLP_SER.ObjectCodes_Result objectCodes_result : etNotifCodes_result.getObjectCodes().getResults()) {
                                                    values.put("Codegruppe", objectCodes_result.getCodegruppe());
                                                    values.put("Kurztext", objectCodes_result.getKurztext());
                                                    if (objectCodes_result.getICodes() != null)
                                                        if (objectCodes_result.getICodes().getResults() != null
                                                                && objectCodes_result.getICodes().getResults().size() > 0) {
                                                            ContentValues valuesCodes = new ContentValues();
                                                            for (VHLP_SER.Codes_Result codes_result : objectCodes_result.getICodes().getResults()) {
                                                                values.put("Code", codes_result.getCode());
                                                                values.put("Kurztext1", codes_result.getKurztext1());

                                                            }
                                                        }
                                                    App_db.insert("Get_NOTIFCODES_ObjectCodes", null, values);
                                                }
                                            }
                                        if (etNotifCodes_result.getActCodes() != null)
                                            if (etNotifCodes_result.getActCodes().getResults() != null
                                                    && etNotifCodes_result.getActCodes().getResults().size() > 0) {
                                                ContentValues valuesACode = new ContentValues();
                                                for (VHLP_SER.ActCodes_Result actCodes_result : etNotifCodes_result.getActCodes().getResults()) {
                                                    values.put("Codegruppe", actCodes_result.getCodegruppe());
                                                    values.put("Kurztext", actCodes_result.getKurztext());
                                                    if (actCodes_result.getACall() != null)
                                                        if (actCodes_result.getACall().getResults() != null
                                                                && actCodes_result.getACall().getResults().size() > 0) {
                                                            ContentValues valuesCodes = new ContentValues();
                                                            for (VHLP_SER.Codes_Result codes_result : actCodes_result.getACall().getResults()) {
                                                                values.put("Code", codes_result.getCode());
                                                                values.put("Kurztext1", codes_result.getKurztext1());

                                                            }
                                                        }
                                                    App_db.insert("Get_NOTIFCODES_ActCodes", null, values);
                                                }
                                            }
                                        if (etNotifCodes_result.getTaskCodes() != null)
                                            if (etNotifCodes_result.getTaskCodes().getResults() != null
                                                    && etNotifCodes_result.getTaskCodes().getResults().size() > 0) {
                                                ContentValues valuesTCode = new ContentValues();
                                                for (VHLP_SER.TaskCodes_Result taskCodes_result : etNotifCodes_result.getTaskCodes().getResults()) {
                                                    values.put("Codegruppe", taskCodes_result.getCodegruppe());
                                                    values.put("Kurztext", taskCodes_result.getKurztext());
                                                    if (taskCodes_result.getACall() != null)
                                                        if (taskCodes_result.getACall().getResults() != null
                                                                && taskCodes_result.getACall().getResults().size() > 0) {
                                                            ContentValues valuesCodes = new ContentValues();
                                                            for (VHLP_SER.Codes_Result codes_result : taskCodes_result.getACall().getResults()) {
                                                                values.put("Code", codes_result.getCode());
                                                                values.put("Kurztext1", codes_result.getKurztext1());

                                                            }
                                                        }
                                                    App_db.insert("Get_NOTIFCODES_TaskCodes", null, values);
                                                }
                                            }
                                        /*App_db.insert("Get_NOTIFCODES_ItemCodes", null, values);
                                        App_db.insert("Get_NOTIFCODES_CauseCodes", null, values);
                                        App_db.insert("Get_NOTIFCODES_ObjectCodes", null, values);
                                        App_db.insert("Get_NOTIFCODES_ActCodes", null, values);
                                        App_db.insert("Get_NOTIFCODES_TaskCodes", null, values);*/

                                    }
                                }
                            /*try {
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

                                            *//*Reading and Inserting Data into Database Table for ItemCodes*//*
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
                                            *//*Reading and Inserting Data into Database Table for ItemCodes*//*


                                            *//*Reading and Inserting Data into Database Table for CauseCodes*//*
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
                                            *//*Reading and Inserting Data into Database Table for CauseCodes*//*


                                            *//*Reading and Inserting Data into Database Table for ObjectCodes*//*
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
                                            *//*Reading and Inserting Data into Database Table for ObjectCodes*//*


                                            *//*Reading and Inserting Data into Database Table for ActCodes*//*
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
                                            *//*Reading and Inserting Data into Database Table for ActCodes*//*


                                            *//*Reading and Inserting Data into Database Table for Task Codes*//*
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
                                            *//*Reading and Inserting Data into Database Table for Task Codes*//*

                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtNotifCodes*/


                            /*Reading and Inserting Data into Database Table for EtNotifPriority*/
                            if (response.body().getD().getResults().get(0).getEtNotifPriority() != null)
                                if (response.body().getD().getResults().get(0).getEtNotifPriority().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtNotifPriority().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtNotifPriority_Result etNotifPriority_result : response.body().getD().getResults()
                                            .get(0).getEtNotifPriority().getResults()) {
                                        values.put("Priok",etNotifPriority_result.getPriok());
                                        values.put("Priokx",etNotifPriority_result.getPriokx());
                                        App_db.insert("GET_NOTIFICATION_PRIORITY", null, values);
                                    }
                                }
                            /*try {
                                String EtNotifPriority_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtNotifPriority().getResults());
                                if (EtNotifPriority_response_data != null && !EtNotifPriority_response_data.equals("") && !EtNotifPriority_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtNotifPriority_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtNotifPriority_sql = "Insert into GET_NOTIFICATION_PRIORITY (Priok, Priokx) values(?,?);";
                                        SQLiteStatement EtNotifPriority_statement = App_db.compileStatement(EtNotifPriority_sql);
                                        EtNotifPriority_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtNotifPriority_statement.bindString(1, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtNotifPriority_statement.bindString(2, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtNotifPriority_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtNotifPriority*/


                            /*Reading and Inserting Data into Database Table for EtNotifTypes*/
                            if (response.body().getD().getResults().get(0).getEtNotifTypes() != null)
                                if (response.body().getD().getResults().get(0).getEtNotifTypes().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtNotifTypes().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtNotifTypes_Result etNotifTypes_result : response.body().getD().getResults()
                                            .get(0).getEtNotifTypes().getResults()) {
                                        values.put("Qmart",etNotifTypes_result.getQmart());
                                        values.put("Qmartx",etNotifTypes_result.getQmartx());
                                        App_db.insert("GET_NOTIFICATION_TYPES", null, values);
                                    }
                                }
                            /*try {
                                String EtNotifTypes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtNotifTypes().getResults());
                                if (EtNotifTypes_response_data != null && !EtNotifTypes_response_data.equals("") && !EtNotifTypes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtNotifTypes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtNotifTypes_sql = "Insert into GET_NOTIFICATION_TYPES (Qmart, Qmartx) values(?,?);";
                                        SQLiteStatement EtNotifTypes_statement = App_db.compileStatement(EtNotifTypes_sql);
                                        EtNotifTypes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtNotifTypes_statement.bindString(1, jsonArray.getJSONObject(j).optString("Qmart"));
                                            EtNotifTypes_statement.bindString(2, jsonArray.getJSONObject(j).optString("Qmartx"));
                                            EtNotifTypes_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtNotifTypes*/


                            /*Reading and Inserting Data into Database Table for EtRevnr*/
                            if (response.body().getD().getResults().get(0).getEtRevnr() != null)
                                if (response.body().getD().getResults().get(0).getEtRevnr().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtRevnr().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtRevnr_Result etRevnr_result : response.body().getD().getResults()
                                            .get(0).getEtRevnr().getResults()) {
                                        values.put("Iwerk",etRevnr_result.getIwerk());
                                        values.put("Revnr",etRevnr_result.getRevnr());
                                        values.put("Revtx",etRevnr_result.getRevtx());
                                        App_db.insert("EtRevnr", null, values);
                                    }
                                }
                           /* try {
                                String EtRevnr_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtRevnr().getResults());
                                if (EtRevnr_response_data != null && !EtRevnr_response_data.equals("") && !EtRevnr_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtRevnr_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtRevnr_sql = "Insert into EtRevnr ( Iwerk, Revnr, Revtx) values(?,?,?);";
                                        SQLiteStatement EtRevnr_statement = App_db.compileStatement(EtRevnr_sql);
                                        EtRevnr_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtRevnr_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtRevnr_statement.bindString(2, jsonArray.getJSONObject(j).optString("Revnr"));
                                            EtRevnr_statement.bindString(3, jsonArray.getJSONObject(j).optString("Revtx"));
                                            EtRevnr_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtRevnr*/


                            /*Reading and Inserting Data into Database Table for EtWbs*/
                            if (response.body().getD().getResults().get(0).getEtWbs() != null)
                                if (response.body().getD().getResults().get(0).getEtWbs().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtWbs().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtWbs_Result etWbs_result : response.body().getD().getResults()
                                            .get(0).getEtWbs().getResults()) {
                                        values.put("Iwerk",etWbs_result.getIwerk());
                                        values.put("Gsber",etWbs_result.getGsber());
                                        values.put("Posid",etWbs_result.getPosid());
                                        values.put("Poski",etWbs_result.getPoski());
                                        values.put("Post1",etWbs_result.getPost1());
                                        values.put("Pspnr",etWbs_result.getPspnr());
                                        values.put("Pspid",etWbs_result.getPspid());
                                        App_db.insert("EtWbs", null, values);
                                    }
                                }
                           /* try {
                                String EtWbs_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtWbs().getResults());
                                if (EtWbs_response_data != null && !EtWbs_response_data.equals("") && !EtWbs_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtWbs_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtWbs_sql = "Insert into EtWbs ( Iwerk, Gsber, Posid, Poski, Post1, Pspnr, Pspid) values(?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWbs_statement = App_db.compileStatement(EtWbs_sql);
                                        EtWbs_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtWbs_statement.bindString(1, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWbs_statement.bindString(2, jsonArray.getJSONObject(j).optString("Gsber"));
                                            EtWbs_statement.bindString(3, jsonArray.getJSONObject(j).optString("Posid"));
                                            EtWbs_statement.bindString(4, jsonArray.getJSONObject(j).optString("Poski"));
                                            EtWbs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Post1"));
                                            EtWbs_statement.bindString(6, jsonArray.getJSONObject(j).optString("Pspnr"));
                                            EtWbs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Pspid"));
                                            EtWbs_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
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
                           /* try {
                                String EtUdecCodes_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtUdecCodes().getResults());
                                if (EtUdecCodes_response_data != null && !EtUdecCodes_response_data.equals("") && !EtUdecCodes_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtUdecCodes_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtUdecCodes_sql = "Insert into EtUdecCodes (Werks, Katalogart, Auswahlmge, Codegruppe, Kurztext, Code, Kurztext1, Bewertung, Fehlklasse, Qkennzahl, Folgeakti, Fehlklassetxt) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtUdecCodes_statement = App_db.compileStatement(EtUdecCodes_sql);
                                        EtUdecCodes_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String Werks = jsonArray.getJSONObject(j).optString("Werks");
                                            String Katalogart = jsonArray.getJSONObject(j).optString("Katalogart");
                                            String Auswahlmge = jsonArray.getJSONObject(j).optString("Auswahlmge");
                                            String Codegruppe = jsonArray.getJSONObject(j).optString("Codegruppe");
                                            String Kurztext = jsonArray.getJSONObject(j).optString("Kurztext");
                                            String UdecCodes = jsonArray.getJSONObject(j).optString("UdecCodes");
                                            JSONObject ACall_jsonobject = new JSONObject(UdecCodes);
                                            String ACall_Result = ACall_jsonobject.getString("results");
                                            JSONArray ACall_jsonArray = new JSONArray(ACall_Result);
                                            if (ACall_jsonArray.length() > 0) {
                                                for (int m = 0; m < ACall_jsonArray.length(); m++) {
                                                    String Code = ACall_jsonArray.getJSONObject(m).optString("Code");
                                                    String Kurztext1 = ACall_jsonArray.getJSONObject(m).optString("Kurztext1");
                                                    String Bewertung = ACall_jsonArray.getJSONObject(m).optString("Bewertung");
                                                    String Fehlklasse = ACall_jsonArray.getJSONObject(m).optString("Fehlklasse");
                                                    String Qkennzahl = ACall_jsonArray.getJSONObject(m).optString("Qkennzahl");
                                                    String Folgeakti = ACall_jsonArray.getJSONObject(m).optString("Folgeakti");
                                                    String Fehlklassetxt = ACall_jsonArray.getJSONObject(m).optString("Fehlklassetxt");
                                                    EtUdecCodes_statement.bindString(1, Werks);
                                                    EtUdecCodes_statement.bindString(2, Katalogart);
                                                    EtUdecCodes_statement.bindString(3, Auswahlmge);
                                                    EtUdecCodes_statement.bindString(4, Codegruppe);
                                                    EtUdecCodes_statement.bindString(5, Kurztext);
                                                    EtUdecCodes_statement.bindString(6, Code);
                                                    EtUdecCodes_statement.bindString(7, Kurztext1);
                                                    EtUdecCodes_statement.bindString(8, Bewertung);
                                                    EtUdecCodes_statement.bindString(9, Fehlklasse);
                                                    EtUdecCodes_statement.bindString(10, Qkennzahl);
                                                    EtUdecCodes_statement.bindString(11, Folgeakti);
                                                    EtUdecCodes_statement.bindString(12, Fehlklassetxt);
                                                    EtUdecCodes_statement.execute();
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtMeasCodes*/



                            /*Reading and Inserting Data into Database Table for EtGlacc*/
                          /*  if (response.body().getD().getResults().get(0).getEtGlacc() != null)
                                if (response.body().getD().getResults().get(0).getEtGlacc().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtGlacc().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtGlacc_Result etGlacc_result : response.body().getD().getResults()
                                            .get(0).getEtGlacc().getResults()) {
                                        values.put("Bukrs",etGlacc_result.getBukrs());
                                        values.put("Ktopl",etGlacc_result.getKtopl());
                                        values.put("Glacct",etGlacc_result.getGlacct());
                                        values.put("GlacctText",etGlacc_result.getGlacctText());
                                        App_db.insert("EtGlacc", null, values);
                                    }
                                }*/
                            /*try {
                                String EtGlacc_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtGlacc().getResults());
                                if (EtGlacc_response_data != null && !EtGlacc_response_data.equals("") && !EtGlacc_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtGlacc_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtGlacc_sql = "Insert into EtGlacc ( Bukrs, Ktopl, Glacct, GlacctText) values(?,?,?,?);";
                                        SQLiteStatement EtGlacc_statement = App_db.compileStatement(EtGlacc_sql);
                                        EtGlacc_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtGlacc_statement.bindString(1, jsonArray.getJSONObject(j).optString("Bukrs"));
                                            EtGlacc_statement.bindString(2, jsonArray.getJSONObject(j).optString("Ktopl"));
                                            EtGlacc_statement.bindString(3, jsonArray.getJSONObject(j).optString("Glacct"));
                                            EtGlacc_statement.bindString(4, jsonArray.getJSONObject(j).optString("GlacctText"));
                                            EtGlacc_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtGlacc*/



                            /*Reading and Inserting Data into Database Table for EtAsset*/
                           /* if (response.body().getD().getResults().get(0).getEtAsset() != null)
                                if (response.body().getD().getResults().get(0).getEtAsset().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtAsset().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtAsset_Result etAsset_result : response.body().getD().getResults()
                                            .get(0).getEtAsset().getResults()) {
                                        values.put("Bukrs",etAsset_result.getBukrs());
                                        values.put("Anln1",etAsset_result.getAnln1());
                                        values.put("Anlhtxt",etAsset_result.getAnlhtxt());
                                        App_db.insert("EtAsset", null, values);
                                    }
                                }*/
                           /* try {
                                String EtAsset_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtAsset().getResults());
                                if (EtAsset_response_data != null && !EtAsset_response_data.equals("") && !EtAsset_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtAsset_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtAsset_sql = "Insert into EtAsset ( Bukrs, Anln1, Anlhtxt) values(?,?,?);";
                                        SQLiteStatement EtAsset_statement = App_db.compileStatement(EtAsset_sql);
                                        EtAsset_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtAsset_statement.bindString(1, jsonArray.getJSONObject(j).optString("Bukrs"));
                                            EtAsset_statement.bindString(2, jsonArray.getJSONObject(j).optString("Anln1"));
                                            EtAsset_statement.bindString(3, jsonArray.getJSONObject(j).optString("Anlhtxt"));
                                            EtAsset_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtAsset*/



                            /*Reading and Inserting Data into Database Table for EtValType*/
                           /* if (response.body().getD().getResults().get(0).getEtValType() != null)
                                if (response.body().getD().getResults().get(0).getEtValType().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtValType().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (VHLP_SER.EtValType_Result etValType_result : response.body().getD().getResults()
                                            .get(0).getEtValType().getResults()) {
                                        values.put("Batch",etValType_result.getBatch());
                                        App_db.insert("EtValType", null, values);
                                    }
                                }*/
                            /*try {
                                String EtValType_response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtValType().getResults());
                                if (EtValType_response_data != null && !EtValType_response_data.equals("") && !EtValType_response_data.equals("null")) {
                                    JSONArray jsonArray = new JSONArray(EtValType_response_data);
                                    if (jsonArray.length() > 0) {
                                        String EtValType_sql = "Insert into EtValType ( Batch) values(?);";
                                        SQLiteStatement EtValType_statement = App_db.compileStatement(EtValType_sql);
                                        EtValType_statement.clearBindings();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            EtValType_statement.bindString(1, jsonArray.getJSONObject(j).optString("Batch"));
                                            EtValType_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }*/
                            /*Reading and Inserting Data into Database Table for EtValType*/




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
        }
        catch (Exception ex)
        {
            Log.v("VHLP_Exception", "" + ex.getMessage());
        }

        return Get_Response;
    }

}
