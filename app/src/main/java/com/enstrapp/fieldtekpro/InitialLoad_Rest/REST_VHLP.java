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

public class REST_VHLP
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty c_e = new Check_Empty();


    private static final String TABLE_ETKSTAR = "ETKSTAR";
    private static final String KEY_ETKSTAR_ID = "id";
    private static final String KEY_ETKSTAR_KOKRS = "KOKRS";
    private static final String KEY_ETKSTAR_WERKS= "WERKS";
    private static final String KEY_ETKSTAR_KSTAR = "KSTAR";
    private static final String KEY_ETKSTAR_KOSTL = "KOSTL";
    private static final String KEY_ETKSTAR_LTEXT = "LTEXT";


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


    private static final String TABLE_ET_POSTP = "ET_POSTP";
    private static final String KEY_ET_POSTP_ID = "id";
    private static final String KEY_ET_POSTP_POSTP = "POSTP";
    private static final String KEY_ET_POSTP_PTEXT = "PTEXT";


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
    private static final String KEY_GET_ORDER_TYPES_Notdat = "Notdat";
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



    private static final String TABLE_ETLSTAR = "ETLSTAR";
    private static final String KEY_ETLSTAR_ID = "id";
    private static final String KEY_EtIlart_KOKRS = "KOKRS";
    private static final String KEY_EtIlart_KOSTL = "KOSTL";
    private static final String KEY_EtIlart_LATYP = "LATYP";
    private static final String KEY_EtIlart_LSTAR = "LSTAR";
    private static final String KEY_EtIlart_KTEXT = "KTEXT";



    private static long startTime = 0;


    public static String Get_VHLP_Data(Activity activity, String transmit_type)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equalsIgnoreCase("LOAD"))
            {
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtUdecCodes);
                String CREATE_TABLE_EtUdecCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtUdecCodes+ ""
                        + "( "
                        + KEY_EtUdecCodes_id+ " INTEGER PRIMARY KEY,"
                        + KEY_EtUdecCodes_Werks+ " TEXT,"
                        + KEY_EtUdecCodes_Katalogart+ " TEXT,"
                        + KEY_EtUdecCodes_Auswahlmge + " TEXT,"
                        + KEY_EtUdecCodes_Codegruppe+ " TEXT,"
                        + KEY_EtUdecCodes_Kurztext+ " TEXT,"
                        + KEY_EtUdecCodes_Code + " TEXT,"
                        + KEY_EtUdecCodes_Kurztext1+ " TEXT,"
                        + KEY_EtUdecCodes_Bewertung+ " TEXT,"
                        + KEY_EtUdecCodes_Fehlklasse + " TEXT,"
                        + KEY_EtUdecCodes_Qkennzahl+ " TEXT,"
                        + KEY_EtUdecCodes_Folgeakti+ " TEXT,"
                        + KEY_EtUdecCodes_Fehlklassetxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUdecCodes);

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtInspCodes);
                String CREATE_TABLE_EtInspCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtInspCodes+ ""
                        + "( "
                        + KEY_EtInspCodes_id+ " INTEGER PRIMARY KEY,"
                        + KEY_EtInspCodes_Werks+ " TEXT,"
                        + KEY_EtInspCodes_Katalogart+ " TEXT,"
                        + KEY_EtInspCodes_Auswahlmge + " TEXT,"
                        + KEY_EtInspCodes_Codegruppe+ " TEXT,"
                        + KEY_EtInspCodes_Kurztext+ " TEXT,"
                        + KEY_EtInspCodes_Code + " TEXT,"
                        + KEY_EtInspCodes_Kurztext1+ " TEXT,"
                        + KEY_EtInspCodes_Bewertung+ " TEXT,"
                        + KEY_EtInspCodes_Fehlklasse + " TEXT,"
                        + KEY_EtInspCodes_Qkennzahl+ " TEXT,"
                        + KEY_EtInspCodes_Folgeakti+ " TEXT,"
                        + KEY_EtInspCodes_Fehlklassetxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtInspCodes);

                /* Creating GET_LIST_COST_CENTER Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_LIST_COST_CENTER);
                String CREATE_GET_LIST_COST_CENTER_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_LIST_COST_CENTER+ ""
                        + "( "
                        + KEY_GET_LIST_COST_CENTER_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_COST_CENTER_Bukrs+ " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Kostl+ " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Ktext + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Kokrs + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Werks + " TEXT,"
                        + KEY_GET_LIST_COST_CENTER_Warea + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_COST_CENTER_TABLE);
                /* Creating GET_LIST_COST_CENTER Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtUsers);
                String CREATE_TABLE_EtUsers = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtUsers+ ""
                        + "( "
                        + KEY_EtUsers_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtUsers_Muser+ " TEXT,"
                        + KEY_EtUsers_Fname + " TEXT,"
                        + KEY_EtUsers_Lname + " TEXT,"
                        + KEY_EtUsers_Tokenid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtUsers);



                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ETKSTAR);
                String CREATE_TABLE_ETKSTAR = "CREATE TABLE IF NOT EXISTS "+ TABLE_ETKSTAR+ ""
                        + "( "
                        + KEY_ETKSTAR_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_ETKSTAR_KOKRS+ " TEXT,"
                        + KEY_ETKSTAR_WERKS + " TEXT,"
                        + KEY_ETKSTAR_KSTAR + " TEXT,"
                        + KEY_ETKSTAR_KOSTL + " TEXT,"
                        + KEY_ETKSTAR_LTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_ETKSTAR);



                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ETLSTAR);
                String CREATE_TABLE_ETLSTAR = "CREATE TABLE IF NOT EXISTS "+ TABLE_ETLSTAR+ ""
                        + "( "
                        + KEY_ETLSTAR_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtIlart_KOKRS+ " TEXT,"
                        + KEY_EtIlart_KOSTL + " TEXT,"
                        + KEY_EtIlart_LATYP + " TEXT,"
                        + KEY_EtIlart_LSTAR + " TEXT,"
                        + KEY_EtIlart_KTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_ETLSTAR);



                /* EtGlacc Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtGlacc);
                String CREATE_GET_TABLE_EtGlacc = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtGlacc+ ""
                        + "( "
                        + KEY_EtGlacc_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtGlacc_Bukrs+ " TEXT,"
                        + KEY_EtGlacc_Ktopl + " TEXT,"
                        + KEY_EtGlacc_Glacct + " TEXT,"
                        + KEY_EtGlacc_GlacctText + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_EtGlacc);
                /* EtGlacc Table and Fields Names */



                /* EtAsset Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtAsset);
                String CREATE_GET_TABLE_EtAsset = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtAsset+ ""
                        + "( "
                        + KEY_EtAsset_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtAsset_Bukrs+ " TEXT,"
                        + KEY_EtAsset_Anln1 + " TEXT,"
                        + KEY_EtAsset_Anlhtxt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_EtAsset);
                /* EtAsset Table and Fields Names */



                /* EtTq80 Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_EtTq80);
                String CREATE_GET_TABLE_GET_EtTq80 = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtTq80+ ""
                        + "( "
                        + KEY_GET_EtTq80_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtTq80_Qmart+ " TEXT,"
                        + KEY_GET_EtTq80_Auart + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_GET_EtTq80);
                /* EtTq80 Table and Fields Names */



                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ET_POSTP);
                String CREATE_GET_TABLE_ET_POSTP = "CREATE TABLE IF NOT EXISTS "+ TABLE_ET_POSTP+ ""
                        + "( "
                        + KEY_ET_POSTP_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_ET_POSTP_POSTP+ " TEXT,"
                        + KEY_ET_POSTP_PTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_TABLE_ET_POSTP);



                /* GET_EtConfReason Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_EtConfReason);
                String CREATE_TABLE_GET_EtConfReason = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtConfReason+ ""
                        + "( "
                        + KEY_GET_EtConfReason_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtConfReason_Werks+ " TEXT,"
                        + KEY_GET_EtConfReason_Grund + " TEXT,"
                        + KEY_GET_EtConfReason_Grdtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtConfReason);
                /* GET_EtConfReason Table and Fields Names */


                /* Creating GET_LIST_MOVEMENT_TYPES Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_LIST_MOVEMENT_TYPES);
                String CREATE_GET_LIST_MOVEMENT_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_LIST_MOVEMENT_TYPES+ ""
                        + "( "
                        + KEY_GET_LIST_MOVEMENT_TYPES_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Bwart+ " TEXT,"
                        + KEY_GET_LIST_MOVEMENT_TYPES_Btext + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_LIST_MOVEMENT_TYPES_TABLE);
                /* Creating GET_LIST_MOVEMENT_TYPES Table with Fields */

                /* Creating TABLE_Get_NOTIF_CODES_ItemCodes_Codes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Get_NOTIFCODES_ItemCodes);
                String CREATE_Get_NOTIFCODES_ItemCodes_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_Get_NOTIFCODES_ItemCodes+ ""
                        + "( "
                        + KEY_Get_NOTIFCODES_ItemCodes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_ItemCodes_NotifType+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Rbnr+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Codegruppe+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Kurztext+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Code+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ItemCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_Get_NOTIFCODES_ItemCodes_TABLE);
                /* Creating TABLE_Get_NOTIF_CODES_ItemCodes_Codes Table with Fields */

                /* Creating TABLE_Get_NOTIFCODES_CauseCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Get_NOTIFCODES_CauseCodes);
                String CREATE_TABLE_Get_NOTIFCODES_CauseCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_Get_NOTIFCODES_CauseCodes+ ""
                        + "( "
                        + KEY_Get_NOTIFCODES_CauseCodes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_CauseCodes_NotifType+ " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Rbnr+ " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Codegruppe+ " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Kurztext+ " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Code+ " TEXT,"
                        + KEY_Get_NOTIFCODES_CauseCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_CauseCodes);
                /* Creating TABLE_Get_NOTIFCODES_CauseCodes Table with Fields */

                /* Creating TABLE_Get_NOTIFCODES_TaskCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_Notif_TaskCodes);
                String CREATE_TABLE_Get_NOTIFCODES_TaskCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_Notif_TaskCodes+ ""
                        + "( "
                        + KEY_GET_Notif_TaskCodes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_Notif_TaskCodes_Codegruppe+ " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Kurztext+ " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Code+ " TEXT,"
                        + KEY_GET_Notif_TaskCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_TaskCodes);
                /* Creating TABLE_Get_NOTIFCODES_TaskCodes Table with Fields */

                /* Creating Get_NOTIFCODES_ObjectCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Get_NOTIFCODES_ObjectCodes);
                String CREATE_TABLE_Get_NOTIFCODES_ObjectCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_Get_NOTIFCODES_ObjectCodes+ ""
                        + "( "
                        + KEY_Get_NOTIFCODES_ObjectCodes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_NotifType+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Rbnr+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Codegruppe+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Kurztext+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Code+ " TEXT,"
                        + KEY_Get_NOTIFCODES_ObjectCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Get_NOTIFCODES_ObjectCodes);
                /* Creating Get_NOTIFCODES_ObjectCodes Table with Fields */

                /* Creating EtNotifCodes_ActCodes Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_Notif_ActCodes);
                String CREATE_TABLE_GET_Notif_ActCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_Notif_ActCodes+ ""
                        + "( "
                        + KEY_GET_Notif_ActCodes_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_Notif_ActCodes_NotifType+ " TEXT,"
                        + KEY_GET_Notif_ActCodes_Rbnr+ " TEXT,"
                        + KEY_GET_Notif_ActCodes_Codegruppe+ " TEXT,"
                        + KEY_GET_Notif_ActCodes_Kurztext+ " TEXT,"
                        + KEY_GET_Notif_ActCodes_Code+ " TEXT,"
                        + KEY_GET_Notif_ActCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_Notif_ActCodes);
                /* Creating EtNotifCodes_TaskCodes_Codes Table with Fields */


                /* Creating GET_EtNotifEffect Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_EtNotifEffect);
                String CREATE_TABLE_GET_EtNotifEffect = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtNotifEffect+ ""
                        + "( "
                        + KEY_GET_EtNotifEffect_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtNotifEffect_Auswk+ " TEXT,"
                        + KEY_GET_EtNotifEffect_Auswkt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtNotifEffect);
                /* Creating GET_EtNotifEffect Table with Fields */



                /* Creating EtValType Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtValType);
                String CREATE_TABLE_EtValType = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtValType+ ""
                        + "( "
                        + KEY_EtValType_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtValType_Bukrs+ " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtValType);
                /* Creating EtValType Table with Fields */


                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_NOTIFICATION_PRIORITY);
                String CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_NOTIFICATION_PRIORITY+ ""
                        + "( "
                        + KEY_GET_NOTIFICATION__PRIORITY_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priok+ " TEXT,"
                        + KEY_GET_NOTIFICATION_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_PRIORITY_TABLE);
                /* Creating GET_NOTIFICATIONS_PRIORITY Table with Fields */

                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_NOTIFICATION_TYPES);
                String CREATE_GET_NOTIFICATIONS_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_NOTIFICATION_TYPES+ ""
                        + "( "
                        + KEY_GET_NOTIFICATION_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmart+ " TEXT,"
                        + KEY_GET_NOTIFICATION_TYPES_Qmartx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_NOTIFICATIONS_TYPE_TABLE);
                /* Creating GET_NOTIFICATIONS_TYPE Table with Fields */

                /* Creating GET_ORDER_PRIORITY Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_ORDER_PRIORITY);
                String CREATE_GET_ORDER_PRIORITY_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_ORDER_PRIORITY + ""
                        + "( "
                        + KEY_GET_ORDER__PRIORITY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_PRIORITY_Priok + " TEXT,"
                        + KEY_GET_ORDER_PRIORITY_Priokx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_PRIORITY_TABLE);
                /* Creating GET_ORDER_PRIORITY Table with Fields */

                /* Creating GET_EtOrdSyscond Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrdSyscond);
                String CREATE_TABLE_GET_EtOrdSyscond = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtOrdSyscond + ""
                        + "( "
                        + KEY_GET_EtOrdSyscond_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrdSyscond_Anlzu+ " TEXT,"
                        + KEY_GET_EtOrdSyscond_Anlzux + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrdSyscond);
                /* Creating GET_EtOrdSyscond Table with Fields */

                /* Creating GET_ORDER_TYPES Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_ORDER_TYPES);
                String CREATE_GET_ORDER_TYPES_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_ORDER_TYPES + ""
                        + "( "
                        + KEY_GET_ORDER__TYPES_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_ORDER_TYPES_Auart+" TEXT,"
                        + KEY_GET_ORDER_TYPES_Txt + " TEXT,"
                        + KEY_GET_ORDER_TYPES_Notdat + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_ORDER_TYPES_TABLE);
                /* Creating GET_ORDER_TYPES Table with Fields */

                /* Creating GET_PLANTS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_PLANTS);
                String CREATE_GET_PLANTS_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_PLANTS + ""
                        + "( "
                        + KEY_GET_PLANTS_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_PLANTS_Werks + " TEXT,"
                        + KEY_GET_PLANTS_Name1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_PLANTS_TABLE);
                /* Creating GET_PLANTS Table with Fields */

                /* GET_CONTROL_KEY Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_CONTROL_KEY);
                String CREATE_GET_CONTROL_KEY_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_CONTROL_KEY+ ""
                        + "( "
                        + KEY_GET_CONTROL_KEY_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_CONTROL_KEY_Steus+ " TEXT,"
                        + KEY_GET_CONTROL_KEY_Txt + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_CONTROL_KEY_TABLE);
                /* GET_CONTROL_KEY Table and Fields Names */

                /* Creating GET_SLOC Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_SLOC);
                String CREATE_GET_SLOC_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_SLOC + ""
                        + "( "
                        + KEY_GET_SLOC_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_SLOC_Werks + " TEXT,"
                        + KEY_GET_SLOC_Name1 + " TEXT,"
                        + KEY_GET_SLOC_Lgort + " TEXT,"
                        + KEY_GET_SLOC_Lgobe + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_SLOC_TABLE);
                /* Creating GET_SLOC Table with Fields */

                /* Creating GET_UNITS Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_UNITS);
                String CREATE_GET_UNITS_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_UNITS + ""
                        + "( "
                        + KEY_GET_UNITS_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_UNITS_UnitType + " TEXT,"
                        + KEY_GET_UNITS_Meins + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_UNITS_TABLE);
                /* Creating GET_UNITS Table with Fields */

                /* Creating GET_WKCTR Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_WKCTR);
                String CREATE_GET_WKCTR_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_WKCTR + ""
                        + "( "
                        + KEY_GET_WKCTR_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_WKCTR_Bukrs + " TEXT,"
                        + KEY_GET_WKCTR_Kokrs + " TEXT,"
                        + KEY_GET_WKCTR_Objid+ " TEXT,"
                        + KEY_GET_WKCTR_Steus + " TEXT,"
                        + KEY_GET_WKCTR_Werks + " TEXT,"
                        + KEY_GET_WKCTR_Name1+ " TEXT,"
                        + KEY_GET_WKCTR_Arbpl + " TEXT,"
                        + KEY_GET_WKCTR_Ktext + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_GET_WKCTR_TABLE);
                /* Creating GET_WKCTR Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_EtIngrp);
                String CREATE_TABLE_GET_EtIngrp = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_EtIngrp+ ""
                        + "( "
                        + KEY_GET_EtIngrp_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtIngrp_Iwerk+ " TEXT,"
                        + KEY_GET_EtIngrp_Ingrp + " TEXT,"
                        + KEY_GET_EtIngrp_Innam + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtIngrp);

                /* Creating ET_PARVW Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_ET_PARVW);
                String CREATE_ET_PARVW_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_ET_PARVW + ""
                        + "( "
                        + KEY_ET_PARVW_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_ET_PARVW_PARVW + " TEXT,"
                        + KEY_ET_PARVW_VTEXT + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_ET_PARVW_TABLE);
                /* Creating ET_PARVW Table with Fields */

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtPernr);
                String CREATE_TABLE_EtPernr = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtPernr+ ""
                        + "( "
                        + KEY_EtPernr_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtPernr_Werks+ " TEXT,"
                        + KEY_EtPernr_Arbpl + " TEXT,"
                        + KEY_EtPernr_Objid + " TEXT,"
                        + KEY_EtPernr_Lastname + " TEXT,"
                        + KEY_EtPernr_Firstname + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtPernr);

                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtMeasCodes);
                String CREATE_TABLE_EtMeasCodes = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtMeasCodes+ ""
                        + "( "
                        + KEY_EtMeasCodes_id+ " INTEGER PRIMARY KEY,"
                        + KEY_EtMeasCodes_Codegruppe+ " TEXT,"
                        + KEY_EtMeasCodes_Kurztext+ " TEXT,"
                        + KEY_EtMeasCodes_Code + " TEXT,"
                        + KEY_EtMeasCodes_Kurztext1 + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtMeasCodes);

                /* Creating EtWbs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtWbs);
                String CREATE_EtWbs_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtWbs+ ""
                        + "( "
                        + KEY_EtWbs_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtWbs_Iwerk+ " TEXT,"
                        + KEY_EtWbs_Gsber+ " TEXT,"
                        + KEY_EtWbs_Posid+ " TEXT,"
                        + KEY_EtWbs_Poski+ " TEXT,"
                        + KEY_EtWbs_Post1+ " TEXT,"
                        + KEY_EtWbs_Pspnr+ " TEXT,"
                        + KEY_EtWbs_Pspid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_EtWbs_TABLE);
                /* Creating EtWbs Table with Fields */

                /* Creating EtRevnr Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtRevnr);
                String CREATE_EtRevnr_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtRevnr+ ""
                        + "( "
                        + KEY_EtRevnr_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtRevnr_Iwerk+ " TEXT,"
                        + KEY_EtRevnr_Revnr+ " TEXT,"
                        + KEY_EtRevnr_Revtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_EtRevnr_TABLE);
                /* Creating EtRevnr Table with Fields */

                /* EtIlart Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EtIlart);
                String CREATE_TABLE_EtIlart = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtIlart+ ""
                        + "( "
                        + KEY_EtIlart_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtIlart_Auart + " TEXT,"
                        + KEY_EtIlart_Ilart + " TEXT,"
                        + KEY_EtIlart_Ilatx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtIlart);
                /* EtIlart Table and Fields Names */

            }
            else
            {

            }
            /* Initializing Shared Preferences */
            app_sharedpreferences = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            app_editor = app_sharedpreferences.edit();
            username = app_sharedpreferences.getString("Username",null);
            password = app_sharedpreferences.getString("Password",null);
            String webservice_type = app_sharedpreferences.getString("webservice_type",null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Z","F4", webservice_type});
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
            modelLoginRest.setIv_transmit_type(transmit_type);
            modelLoginRest.setIv_user(username);
            modelLoginRest.setIs_device(modelLoginDeviceRest);

            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            REST_Interface service = retrofit.create(REST_Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<REST_VHLP_SER> call = service.postVHLPDetails(url_link, basic, modelLoginRest);
            Response<REST_VHLP_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_VHLP_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    startTime = System.currentTimeMillis();
                    App_db.execSQL("PRAGMA synchronous=OFF");
                    App_db.setLockingEnabled(false);
                    App_db.beginTransaction();



                    /*Reading and Inserting Data into Database Table for EtInspCodes*/
                    try
                    {
                        List<REST_VHLP_SER.ETINSPCODE> ETINSPCODE_results = response.body().getETINSPCODES();
                        if (ETINSPCODE_results != null && ETINSPCODE_results.size() > 0)
                        {
                            String EtInspCodes_sql = "Insert into EtInspCodes (Werks, Katalogart, Auswahlmge, Codegruppe, Kurztext, Code, Kurztext1, Bewertung, Fehlklasse, Qkennzahl, Folgeakti, Fehlklassetxt) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtInspCodes_statement = App_db.compileStatement(EtInspCodes_sql);
                            EtInspCodes_statement.clearBindings();
                            for (REST_VHLP_SER.ETINSPCODE etInspCodes_result : ETINSPCODE_results)
                            {
                                String Werks = c_e.check_empty(etInspCodes_result.getWERKS());
                                String Katalogart = c_e.check_empty(etInspCodes_result.getKATALOGART());
                                String Auswahlmge = c_e.check_empty(etInspCodes_result.getAUSWAHLMGE());
                                String Codegruppe = c_e.check_empty(etInspCodes_result.getCODEGRUPPE());
                                String Kurztext = c_e.check_empty(etInspCodes_result.getKurztext());
                                List<REST_VHLP_SER.CODE____> inspCodesCodesResults = etInspCodes_result.getCODES();
                                if (inspCodesCodesResults != null && inspCodesCodesResults.size() > 0)
                                {
                                    for (REST_VHLP_SER.CODE____ etInspCodesCodesResult : inspCodesCodesResults)
                                    {
                                        String Code = c_e.check_empty(etInspCodesCodesResult.getCODE());
                                        String Kurztext1 = c_e.check_empty(etInspCodesCodesResult.getKURZTEXT1());
                                        String Bewertung = c_e.check_empty(etInspCodesCodesResult.getBEWERTUNG());
                                        String Fehlklasse = c_e.check_empty(etInspCodesCodesResult.getFEHLKLASSE());
                                        String Qkennzahl = c_e.check_empty(etInspCodesCodesResult.getQKENNZAHL());
                                        String Folgeakti = c_e.check_empty(etInspCodesCodesResult.getFOLGEAKTI());
                                        String Fehlklassetxt = c_e.check_empty(etInspCodesCodesResult.getFEHLKLASSETXT());
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
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtInspCodes*/




                    /*Reading and Inserting Data into Database Table for ET_KSTAR*/
                    try
                    {
                        List<REST_VHLP_SER.ET_KSTAR> ETKSTAR_results = response.body().getETKSTAR();
                        if (ETKSTAR_results != null && ETKSTAR_results.size() > 0)
                        {
                            String EtUsers_sql = "Insert into ETKSTAR (KOKRS, WERKS, KSTAR, KOSTL, LTEXT) values(?,?,?,?,?);";
                            SQLiteStatement ETKSTAR_statement = App_db.compileStatement(EtUsers_sql);
                            ETKSTAR_statement.clearBindings();
                            for (REST_VHLP_SER.ET_KSTAR etKstar : ETKSTAR_results)
                            {
                                ETKSTAR_statement.bindString(1, c_e.check_empty(etKstar.getKOKRS()));
                                ETKSTAR_statement.bindString(2, c_e.check_empty(etKstar.getWERKS()));
                                ETKSTAR_statement.bindString(3, c_e.check_empty(etKstar.getKSTAR()));
                                ETKSTAR_statement.bindString(4, c_e.check_empty(etKstar.getKOSTL()));
                                ETKSTAR_statement.bindString(5, c_e.check_empty(etKstar.getLTEXT()));
                                ETKSTAR_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for ET_KSTAR*/



                    /*Reading and Inserting Data into Database Table for ET_USERS*/
                    try
                    {
                        List<REST_VHLP_SER.ETUSER> ETUSER_results = response.body().getETUSERS();
                        if (ETUSER_results != null && ETUSER_results.size() > 0)
                        {
                            String EtUsers_sql = "Insert into EtUsers (Muser, Fname, Lname, Tokenid) values(?,?,?,?);";
                            SQLiteStatement EtUsers_statement = App_db.compileStatement(EtUsers_sql);
                            EtUsers_statement.clearBindings();
                            for (REST_VHLP_SER.ETUSER etUsersResult : ETUSER_results)
                            {
                                EtUsers_statement.bindString(1, c_e.check_empty(etUsersResult.getMUSER()));
                                EtUsers_statement.bindString(2, c_e.check_empty(etUsersResult.getFNAME()));
                                EtUsers_statement.bindString(3, c_e.check_empty(etUsersResult.getLNAME()));
                                EtUsers_statement.bindString(4, c_e.check_empty(etUsersResult.getTOKENID()));
                                EtUsers_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for ET_USERS*/





                    /*Reading and Inserting Data into Database Table for ET_LSTAR*/
                    try
                    {
                        List<REST_VHLP_SER.ETLSTAR> ETLSTAR_results = response.body().getETLSTAR();
                        if (ETLSTAR_results != null && ETLSTAR_results.size() > 0)
                        {
                            String ETLSTAR_sql = "Insert into ETLSTAR (KOKRS, KOSTL, LATYP, LSTAR, KTEXT) values(?,?,?,?,?);";
                            SQLiteStatement ETLSTAR_statement = App_db.compileStatement(ETLSTAR_sql);
                            ETLSTAR_statement.clearBindings();
                            for (REST_VHLP_SER.ETLSTAR etUsersResult : ETLSTAR_results)
                            {
                                ETLSTAR_statement.bindString(1, c_e.check_empty(etUsersResult.getKOKRS()));
                                ETLSTAR_statement.bindString(2, c_e.check_empty(etUsersResult.getKOSTL()));
                                ETLSTAR_statement.bindString(3, c_e.check_empty(etUsersResult.getLATYP()));
                                ETLSTAR_statement.bindString(4, c_e.check_empty(etUsersResult.getLSTAR()));
                                ETLSTAR_statement.bindString(5, c_e.check_empty(etUsersResult.getKTEXT()));
                                ETLSTAR_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for ET_LSTAR*/



                    /*Reading and Inserting Data into Database Table for EtTq80*/
                    try
                    {
                        List<REST_VHLP_SER.ETTQ80> ETTQ80_results = response.body().getETTQ80();
                        if (ETTQ80_results != null && ETTQ80_results.size() > 0)
                        {
                            String EtTq80_sql = "Insert into EtTq80 (QMART, AUART) values(?,?);";
                            SQLiteStatement EtTq80_statement = App_db.compileStatement(EtTq80_sql);
                            EtTq80_statement.clearBindings();
                            for (REST_VHLP_SER.ETTQ80 etTq80Result : ETTQ80_results)
                            {
                                EtTq80_statement.bindString(1, c_e.check_empty(etTq80Result.getQMART()));
                                EtTq80_statement.bindString(2, c_e.check_empty(etTq80Result.getAUART()));
                                EtTq80_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtTq80*/




                    /*Reading and Inserting Data into Database Table for ET_POSTP*/
                    try
                    {
                        List<REST_VHLP_SER.ETPOSTP> ETPOSTP_results = response.body().getEtpostps();
                        if (ETPOSTP_results != null && ETPOSTP_results.size() > 0)
                        {
                            String ETPOSTP_sql = "Insert into ET_POSTP (POSTP, PTEXT) values(?,?);";
                            SQLiteStatement ETPOSTP_statement = App_db.compileStatement(ETPOSTP_sql);
                            ETPOSTP_statement.clearBindings();
                            for (REST_VHLP_SER.ETPOSTP etpostp : ETPOSTP_results)
                            {
                                ETPOSTP_statement.bindString(1, c_e.check_empty(etpostp.getPOSTP()));
                                ETPOSTP_statement.bindString(2, c_e.check_empty(etpostp.getPTEXT()));
                                ETPOSTP_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for ET_POSTP*/



                    /*Reading and Inserting Data into Database Table for EtNotifEffect*/
                    try
                    {
                        List<REST_VHLP_SER.ETNOTIFEFFECT> ETNOTIFEFFECT_results = response.body().getETNOTIFEFFECT();
                        if (ETNOTIFEFFECT_results != null && ETNOTIFEFFECT_results.size() > 0)
                        {
                            String EtNotifEffect_sql = "Insert into EtNotifEffect (Auswk, Auswkt) values(?,?);";
                            SQLiteStatement EtNotifEffect_statement = App_db.compileStatement(EtNotifEffect_sql);
                            EtNotifEffect_statement.clearBindings();
                            for (REST_VHLP_SER.ETNOTIFEFFECT result : ETNOTIFEFFECT_results)
                            {
                                EtNotifEffect_statement.bindString(1, c_e.check_empty(result.getAUSWK()));
                                EtNotifEffect_statement.bindString(2, c_e.check_empty(result.getAUSWKT()));
                                EtNotifEffect_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtNotifEffect*/





                    /*Reading and Inserting Data into Database Table for EtMeasCodes*/
                    try
                    {
                        List<REST_VHLP_SER.ETMEASCODE> ETMEASCODE_results = response.body().getETMEASCODES();
                        if (ETMEASCODE_results != null && ETMEASCODE_results.size() > 0)
                        {
                            String EtMeasCodes_sql = "Insert into EtMeasCodes (Codegruppe, Kurztext, Code, Kurztext1) values(?,?,?,?);";
                            SQLiteStatement EtMeasCodes_statement = App_db.compileStatement(EtMeasCodes_sql);
                            EtMeasCodes_statement.clearBindings();
                            for (REST_VHLP_SER.ETMEASCODE etMeasCodes_result : ETMEASCODE_results)
                            {
                                String Codegruppe = c_e.check_empty(etMeasCodes_result.getCODEGRUPPE());
                                String Kurztext = c_e.check_empty(etMeasCodes_result.getKURZTEXT());
                                List<REST_VHLP_SER.CODE___> codes_results = etMeasCodes_result.getCODES();
                                if (codes_results != null && codes_results.size() > 0)
                                {
                                    for (REST_VHLP_SER.CODE___ codesResult : codes_results)
                                    {
                                        String Code = c_e.check_empty(codesResult.getCODE());
                                        String Kurztext1 = c_e.check_empty(codesResult.getKURZTEXT1());
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
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtMeasCodes*/






                    /*Reading and Inserting Data into Database Table for EtOrdSyscond*/
                    try
                    {
                        List<REST_VHLP_SER.ETORDSYSCOND> ETORDSYSCOND_results = response.body().getETORDSYSCOND();
                        if (ETORDSYSCOND_results != null && ETORDSYSCOND_results.size() > 0)
                        {
                            String EtOrdSyscond_sql = "Insert into EtOrdSyscond (Anlzu, Anlzux) values(?,?);";
                            SQLiteStatement EtOrdSyscond_statement = App_db.compileStatement(EtOrdSyscond_sql);
                            EtOrdSyscond_statement.clearBindings();
                            for (REST_VHLP_SER.ETORDSYSCOND result : ETORDSYSCOND_results)
                            {
                                EtOrdSyscond_statement.bindString(1, c_e.check_empty(result.getANLZU()));
                                EtOrdSyscond_statement.bindString(2, c_e.check_empty(result.getANLZUX()));
                                EtOrdSyscond_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtOrdSyscond*/





                    /*Reading and Inserting Data into Database Table for EtIngrp*/
                    try
                    {
                        List<REST_VHLP_SER.ETINGRP> ETINGRP_results = response.body().getETINGRP();
                        if (ETINGRP_results != null && ETINGRP_results.size() > 0)
                        {
                            String EtIngrp_sql = "Insert into GET_EtIngrp (Iwerk, Ingrp, Innam) values(?,?,?);";
                            SQLiteStatement EtIngrp_statement = App_db.compileStatement(EtIngrp_sql);
                            EtIngrp_statement.clearBindings();
                            for (REST_VHLP_SER.ETINGRP result : ETINGRP_results)
                            {
                                EtIngrp_statement.bindString(1, c_e.check_empty(result.getIWERK()));
                                EtIngrp_statement.bindString(2, c_e.check_empty(result.getINGRP()));
                                EtIngrp_statement.bindString(3, c_e.check_empty(result.getINNAM()));
                                EtIngrp_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtIngrp*/





                    /*Reading and Inserting Data into Database Table for EtIlart*/
                    try
                    {
                        List<REST_VHLP_SER.ETILART> ETILART_results = response.body().getETILART();
                        if (ETILART_results != null && ETILART_results.size() > 0)
                        {
                            String EtIlart_sql = "Insert into EtIlart (Auart, Ilart, Ilatx) values(?,?,?);";
                            SQLiteStatement EtIlart_statement = App_db.compileStatement(EtIlart_sql);
                            EtIlart_statement.clearBindings();
                            for (REST_VHLP_SER.ETILART result : ETILART_results)
                            {
                                EtIlart_statement.bindString(1, c_e.check_empty(result.getAUART()));
                                EtIlart_statement.bindString(2, result.getILART());
                                EtIlart_statement.bindString(3, c_e.check_empty(result.getILATX()));
                                EtIlart_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtIlart*/






                    /*Reading and Inserting Data into Database Table for EtConfReason*/
                    try
                    {
                        List<REST_VHLP_SER.ETCONFREASON> ETCONFREASON_results = response.body().getETCONFREASON();
                        if (ETCONFREASON_results != null && ETCONFREASON_results.size() > 0)
                        {
                            String EtConfReason_sql = "Insert into GET_EtConfReason (Werks, Grund, Grdtx) values(?,?,?);";
                            SQLiteStatement EtConfReason_statement = App_db.compileStatement(EtConfReason_sql);
                            EtConfReason_statement.clearBindings();
                            for (REST_VHLP_SER.ETCONFREASON result : ETCONFREASON_results)
                            {
                                EtConfReason_statement.bindString(1, c_e.check_empty(result.getWERKS()));
                                EtConfReason_statement.bindString(2, c_e.check_empty(result.getGRUND()));
                                EtConfReason_statement.bindString(3, c_e.check_empty(result.getGRDTX()));
                                EtConfReason_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtConfReason*/






                    /*Reading and Inserting Data into Database Table for EtSteus*/
                    try
                    {
                        List<REST_VHLP_SER.ETSTEU> ETSTEU_results = response.body().getETSTEUS();
                        if (ETSTEU_results != null && ETSTEU_results.size() > 0)
                        {
                            String EtSteus_sql = "Insert into GET_CONTROL_KEY (Steus, Txt) values(?,?);";
                            SQLiteStatement EtSteus_statement = App_db.compileStatement(EtSteus_sql);
                            EtSteus_statement.clearBindings();
                            for (REST_VHLP_SER.ETSTEU result : ETSTEU_results)
                            {
                                EtSteus_statement.bindString(1, c_e.check_empty(result.getSTEUS()));
                                EtSteus_statement.bindString(2, c_e.check_empty(result.getTXT()));
                                EtSteus_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtSteus*/






                    /*Reading and Inserting Data into Database Table for EtStloc*/
                    try
                    {
                        List<REST_VHLP_SER.ETSTLOC> ETSTLOC_results = response.body().getETSTLOC();
                        if (ETSTLOC_results != null && ETSTLOC_results.size() > 0)
                        {
                            String EtStloc_sql = "Insert into GET_SLOC (Werks, Name1, Lgort, Lgobe) values(?,?,?,?);";
                            SQLiteStatement EtStloc_statement = App_db.compileStatement(EtStloc_sql);
                            EtStloc_statement.clearBindings();
                            for (REST_VHLP_SER.ETSTLOC result : ETSTLOC_results)
                            {
                                EtStloc_statement.bindString(1, c_e.check_empty(result.getWERKS()));
                                EtStloc_statement.bindString(2, c_e.check_empty(result.getNAME1()));
                                EtStloc_statement.bindString(3, c_e.check_empty(result.getLGORT()));
                                EtStloc_statement.bindString(4, c_e.check_empty(result.getLGOBE()));
                                EtStloc_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtStloc*/






                    /*Reading and Inserting Data into Database Table for EtPlants*/
                    try
                    {
                        List<REST_VHLP_SER.ETPLANT> ETPLANT_results = response.body().getETPLANTS();
                        if (ETPLANT_results != null && ETPLANT_results.size() > 0)
                        {
                            String EtPlants_sql = "Insert into GET_PLANTS (Werks, Name1) values(?,?);";
                            SQLiteStatement EtPlants_statement = App_db.compileStatement(EtPlants_sql);
                            EtPlants_statement.clearBindings();
                            for (REST_VHLP_SER.ETPLANT result : ETPLANT_results)
                            {
                                EtPlants_statement.bindString(1, c_e.check_empty(result.getWERKS()));
                                EtPlants_statement.bindString(2, c_e.check_empty(result.getNAME1()));
                                EtPlants_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtPlants*/






                    /*Reading and Inserting Data into Database Table for EtUnits*/
                    try
                    {
                        List<REST_VHLP_SER.ETUNIT> ETUNIT_results = response.body().getETUNITS();
                        if (ETUNIT_results != null && ETUNIT_results.size() > 0)
                        {
                            String EtUnits_sql = "Insert into GET_UNITS (UnitType, Meins) values(?,?);";
                            SQLiteStatement EtUnits_statement = App_db.compileStatement(EtUnits_sql);
                            EtUnits_statement.clearBindings();
                            for (REST_VHLP_SER.ETUNIT result : ETUNIT_results)
                            {
                                EtUnits_statement.bindString(1, c_e.check_empty(result.getUNITTYPE()));
                                EtUnits_statement.bindString(2, c_e.check_empty(result.getMEINS()));
                                EtUnits_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtUnits*/





                    /*Reading and Inserting Data into Database Table for EtPernr*/
                    try
                    {
                        List<REST_VHLP_SER.ETPERNR> ETPERNR_results = response.body().geteTPERNR();
                        if (ETPERNR_results != null && ETPERNR_results.size() > 0)
                        {
                            String EtPernr_sql = "Insert into GET_EtPernr (Werks, Arbpl, Objid, Lastname, Firstname) values(?,?,?,?,?);";
                            SQLiteStatement EtPernr_statement = App_db.compileStatement(EtPernr_sql);
                            EtPernr_statement.clearBindings();
                            for (REST_VHLP_SER.ETPERNR result : ETPERNR_results)
                            {
                                EtPernr_statement.bindString(1, c_e.check_empty(result.getWerks()));
                                EtPernr_statement.bindString(2, c_e.check_empty(result.getArbpl()));
                                EtPernr_statement.bindString(3, c_e.check_empty(result.getObjid()));
                                EtPernr_statement.bindString(4, c_e.check_empty(result.getLastname()));
                                EtPernr_statement.bindString(5, c_e.check_empty(result.getFirstname()));
                                EtPernr_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtPernr*/





                    /*Reading and Inserting Data into Database Table for EtWkctrPlant*/
                    try
                    {
                        List<REST_VHLP_SER.ETWKCTRPLANT> ETWKCTRPLANT_results = response.body().getETWKCTRPLANT();
                        if (ETWKCTRPLANT_results != null && ETWKCTRPLANT_results.size() > 0)
                        {
                            String EtWkctrPlant_sql = "Insert into GET_WKCTR (Bukrs, Kokrs, Objid, Steus, Werks, Name1, Arbpl, Ktext) values(?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtWkctrPlant_statement = App_db.compileStatement(EtWkctrPlant_sql);
                            EtWkctrPlant_statement.clearBindings();
                            for (REST_VHLP_SER.ETWKCTRPLANT result : ETWKCTRPLANT_results)
                            {
                                EtWkctrPlant_statement.bindString(1, c_e.check_empty(result.getBUKRS()));
                                EtWkctrPlant_statement.bindString(2, c_e.check_empty(result.getKOKRS()));
                                EtWkctrPlant_statement.bindString(3, c_e.check_empty(result.getOBJID()));
                                EtWkctrPlant_statement.bindString(4, c_e.check_empty(result.getSTEUS()));
                                EtWkctrPlant_statement.bindString(5, c_e.check_empty(result.getWERKS()));
                                EtWkctrPlant_statement.bindString(6, c_e.check_empty(result.getNAME1()));
                                EtWkctrPlant_statement.bindString(7, c_e.check_empty(result.getARBPL()));
                                EtWkctrPlant_statement.bindString(8, c_e.check_empty(result.getKTEXT()));
                                EtWkctrPlant_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtWkctrPlant*/





                    /*Reading and Inserting Data into Database Table for EtKostl*/
                    try
                    {
                        List<REST_VHLP_SER.ETKOSTL> ETKOSTL_results = response.body().getETKOSTL();
                        if (ETKOSTL_results != null && ETKOSTL_results.size() > 0)
                        {
                            String EtKostl_sql = "Insert into GET_LIST_COST_CENTER (Bukrs, Kostl, Ktext, Kokrs, Werks, Warea) values(?,?,?,?,?,?);";
                            SQLiteStatement EtKostl_statement = App_db.compileStatement(EtKostl_sql);
                            EtKostl_statement.clearBindings();
                            for (REST_VHLP_SER.ETKOSTL result : ETKOSTL_results)
                            {
                                EtKostl_statement.bindString(1, c_e.check_empty(result.getBUKRS()));
                                EtKostl_statement.bindString(2, c_e.check_empty(result.getKOSTL()));
                                EtKostl_statement.bindString(3, c_e.check_empty(result.getKTEXT()));
                                EtKostl_statement.bindString(4, c_e.check_empty(result.getKOKRS()));
                                EtKostl_statement.bindString(5, c_e.check_empty(result.getWERKS()));
                                EtKostl_statement.bindString(6, c_e.check_empty(result.getWAREA()));
                                EtKostl_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtKostl*/





                    /*Reading and Inserting Data into Database Table for EtMovementTypes*/
                    try
                    {
                        List<REST_VHLP_SER.ETMOVEMENTTYPE> ETMOVEMENTTYPE_results = response.body().getETMOVEMENTTYPES();
                        if (ETMOVEMENTTYPE_results != null && ETMOVEMENTTYPE_results.size() > 0)
                        {
                            String EtMovementTypes_sql = "Insert into GET_LIST_MOVEMENT_TYPES (Bwart, Btext) values(?,?);";
                            SQLiteStatement EtMovementTypes_statement = App_db.compileStatement(EtMovementTypes_sql);
                            EtMovementTypes_statement.clearBindings();
                            for (REST_VHLP_SER.ETMOVEMENTTYPE result : ETMOVEMENTTYPE_results)
                            {
                                EtMovementTypes_statement.bindString(1, c_e.check_empty(result.getBWART()));
                                EtMovementTypes_statement.bindString(2, c_e.check_empty(result.getBTEXT()));
                                EtMovementTypes_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtMovementTypes*/





                    /*Reading and Inserting Data into Database Table for EtOrdPriority*/
                    try
                    {
                        List<REST_VHLP_SER.ETORDPRIORITY> ETORDPRIORITY_results = response.body().getETORDPRIORITY();
                        if (ETORDPRIORITY_results != null && ETORDPRIORITY_results.size() > 0)
                        {
                            String EtOrdPriority_sql = "Insert into GET_ORDER_PRIORITY (Priok, Priokx) values(?,?);";
                            SQLiteStatement EtOrdPriority_statement = App_db.compileStatement(EtOrdPriority_sql);
                            EtOrdPriority_statement.clearBindings();
                            for (REST_VHLP_SER.ETORDPRIORITY result : ETORDPRIORITY_results)
                            {
                                EtOrdPriority_statement.bindString(1, c_e.check_empty(result.getPRIOK()));
                                EtOrdPriority_statement.bindString(2, c_e.check_empty(result.getPRIOKX()));
                                EtOrdPriority_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtOrdPriority*/





                    /*Reading and Inserting Data into Database Table for EtOrdTypes*/
                    try
                    {
                        List<REST_VHLP_SER.ETORDTYPE> ETORDTYPE_results = response.body().getETORDTYPES();
                        if (ETORDTYPE_results != null && ETORDTYPE_results.size() > 0)
                        {
                            String EtOrdTypes_sql = "Insert into GET_ORDER_TYPES (Auart, Txt, Notdat) values(?,?,?);";
                            SQLiteStatement EtOrdTypes_statement = App_db.compileStatement(EtOrdTypes_sql);
                            EtOrdTypes_statement.clearBindings();
                            for (REST_VHLP_SER.ETORDTYPE result : ETORDTYPE_results)
                            {
                                EtOrdTypes_statement.bindString(1, c_e.check_empty(result.getAUART()));
                                EtOrdTypes_statement.bindString(2, c_e.check_empty(result.getTXT()));
                                EtOrdTypes_statement.bindString(3, c_e.check_empty(result.getACTIVE()));
                                EtOrdTypes_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtOrdPriority*/








                    /*Reading and Inserting Data into Database Table for EtNotifCodes*/
                    try
                    {
                        List<REST_VHLP_SER.ETNOTIFCODE> ETNOTIFCODE_results = response.body().getETNOTIFCODES();
                        if (ETNOTIFCODE_results != null && ETNOTIFCODE_results.size() > 0)
                        {
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


                            for (REST_VHLP_SER.ETNOTIFCODE etNotifCodes_result : ETNOTIFCODE_results)
                            {
                                String NotifType = c_e.check_empty(etNotifCodes_result.getNOTIFTYPE());
                                String Rbnr = c_e.check_empty(etNotifCodes_result.getRBNR());


                                        /*Reading and Inserting Data into Database Table for ItemCodes*/
                                        List<REST_VHLP_SER.ITEMCODE> itemCodes_results = etNotifCodes_result.getITEMCODES();
                                        if (itemCodes_results != null && itemCodes_results.size() > 0)
                                        {
                                            for (REST_VHLP_SER.ITEMCODE codesResult : itemCodes_results)
                                            {
                                                String Codegruppe = codesResult.getCODEGRUPPE();
                                                String Kurztext = codesResult.getKURZTEXT();
                                                List<REST_VHLP_SER.CODE> itemCodesResults = codesResult.getCODES();
                                                if (itemCodesResults != null && itemCodesResults.size() > 0)
                                                {
                                                    for (REST_VHLP_SER.CODE iCodes_result : itemCodesResults)
                                                    {
                                                        String Code = iCodes_result.getCODE();
                                                        String Kurztext1 = iCodes_result.getKURZTEXT1();
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
                                        /*Reading and Inserting Data into Database Table for ItemCodes*/



                                        /*Reading and Inserting Data into Database Table for CauseCodes*/
                                        List<REST_VHLP_SER.CAUSECODE> causeCodes_results = etNotifCodes_result.getCAUSECODES();
                                        if (causeCodes_results != null && causeCodes_results.size() > 0)
                                        {
                                            for (REST_VHLP_SER.CAUSECODE codesResult : causeCodes_results)
                                            {
                                                String Codegruppe = codesResult.getCODEGRUPPE();
                                                String Kurztext = codesResult.getKURZTEXT();
                                                List<REST_VHLP_SER.CODE_> itemCodesResults = codesResult.getCODES();
                                                if (itemCodesResults != null && itemCodesResults.size() > 0)
                                                {
                                                    for (REST_VHLP_SER.CODE_ iCodes_result : itemCodesResults)
                                                    {
                                                        String Code = iCodes_result.getCODE();
                                                        String Kurztext1 = iCodes_result.getKURZTEXT1();
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
                                        /*Reading and Inserting Data into Database Table for CauseCodes*/



                                        /*Reading and Inserting Data into Database Table for ObjectCodes*/
                                        List<REST_VHLP_SER.OBJECTCODE> objectCodes_results = etNotifCodes_result.getOBJECTCODES();
                                        if (objectCodes_results != null && objectCodes_results.size() > 0)
                                        {
                                            for (REST_VHLP_SER.OBJECTCODE codesResult : objectCodes_results)
                                            {
                                                String Codegruppe = codesResult.getCODEGRUPPE();
                                                String Kurztext = codesResult.getKURZTEXT();
                                                List<REST_VHLP_SER.CODE__> itemCodesResults = codesResult.getCODES();
                                                if (itemCodesResults != null && itemCodesResults.size() > 0)
                                                {
                                                    for (REST_VHLP_SER.CODE__ iCodes_result : itemCodesResults)
                                                    {
                                                        String Code = iCodes_result.getCODE();
                                                        String Kurztext1 = iCodes_result.getKURZTEXT1();
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
                                        /*Reading and Inserting Data into Database Table for ObjectCodes*/




                                        /*Reading and Inserting Data into Database Table for ActCodes*/
                                        List<REST_VHLP_SER.OBJECTCODE> ActCODES_results = etNotifCodes_result.getActCODES();
                                        if (ActCODES_results != null && ActCODES_results.size() > 0)
                                        {
                                            for (REST_VHLP_SER.OBJECTCODE codesResult : ActCODES_results)
                                            {
                                                String Codegruppe = codesResult.getCODEGRUPPE();
                                                String Kurztext = codesResult.getKURZTEXT();
                                                List<REST_VHLP_SER.CODE__> itemCodesResults = codesResult.getCODES();
                                                if (itemCodesResults != null && itemCodesResults.size() > 0)
                                                {
                                                    for (REST_VHLP_SER.CODE__ iCodes_result : itemCodesResults)
                                                    {
                                                        String Code = iCodes_result.getCODE();
                                                        String Kurztext1 = iCodes_result.getKURZTEXT1();
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
                                        /*Reading and Inserting Data into Database Table for ActCodes*/



                                        /*Reading and Inserting Data into Database Table for TaskCodes*/
                                        List<REST_VHLP_SER.OBJECTCODE> TaskCodes_results = etNotifCodes_result.getTaskCODES();
                                        if (TaskCodes_results != null && TaskCodes_results.size() > 0)
                                        {
                                            for (REST_VHLP_SER.OBJECTCODE codesResult : TaskCodes_results)
                                            {
                                                String Codegruppe = codesResult.getCODEGRUPPE();
                                                String Kurztext = codesResult.getKURZTEXT();
                                                List<REST_VHLP_SER.CODE__> itemCodesResults = codesResult.getCODES();
                                                if (itemCodesResults != null && itemCodesResults.size() > 0)
                                                {
                                                    for (REST_VHLP_SER.CODE__ iCodes_result : itemCodesResults)
                                                    {
                                                        String Code = iCodes_result.getCODE();
                                                        String Kurztext1 = iCodes_result.getKURZTEXT1();
                                                        TasktCodes_statement.bindString(1, Codegruppe);
                                                        TasktCodes_statement.bindString(2, Kurztext);
                                                        TasktCodes_statement.bindString(3, Code);
                                                        TasktCodes_statement.bindString(4, Kurztext1);
                                                        TasktCodes_statement.execute();
                                                    }
                                                }
                                            }
                                        }
                                        /*Reading and Inserting Data into Database Table for TaskCodes*/
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtNotifCodes*/






                    /*Reading and Inserting Data into Database Table for EtNotifPriority*/
                    try
                    {
                        List<REST_VHLP_SER.ETNOTIFPRIORITY> ETNOTIFPRIORITY_results = response.body().getETNOTIFPRIORITY();
                        if (ETNOTIFPRIORITY_results != null && ETNOTIFPRIORITY_results.size() > 0)
                        {
                            String EtNotifPriority_sql = "Insert into GET_NOTIFICATION_PRIORITY (Priok, Priokx) values(?,?);";
                            SQLiteStatement EtNotifPriority_statement = App_db.compileStatement(EtNotifPriority_sql);
                            EtNotifPriority_statement.clearBindings();
                            for (REST_VHLP_SER.ETNOTIFPRIORITY result : ETNOTIFPRIORITY_results)
                            {
                                EtNotifPriority_statement.bindString(1, c_e.check_empty(result.getPRIOK()));
                                EtNotifPriority_statement.bindString(2, c_e.check_empty(result.getPRIOKX()));
                                EtNotifPriority_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtNotifPriority*/





                    /*Reading and Inserting Data into Database Table for EtNotifTypes*/
                    try
                    {
                        List<REST_VHLP_SER.ETNOTIFTYPE> ETNOTIFTYPE_results = response.body().getETNOTIFTYPES();
                        if (ETNOTIFTYPE_results != null && ETNOTIFTYPE_results.size() > 0)
                        {
                            String EtNotifTypes_sql = "Insert into GET_NOTIFICATION_TYPES (Qmart, Qmartx) values(?,?);";
                            SQLiteStatement EtNotifTypes_statement = App_db.compileStatement(EtNotifTypes_sql);
                            EtNotifTypes_statement.clearBindings();
                            for (REST_VHLP_SER.ETNOTIFTYPE result : ETNOTIFTYPE_results)
                            {
                                EtNotifTypes_statement.bindString(1, c_e.check_empty(result.getQMART()));
                                EtNotifTypes_statement.bindString(2, c_e.check_empty(result.getQMARTX()));
                                EtNotifTypes_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtNotifTypes*/




                    /*Reading and Inserting Data into Database Table for EtRevnr*/
                    try
                    {
                        List<REST_VHLP_SER.ETREVNR> ETREVNR_results = response.body().getETREVNR();
                        if (ETREVNR_results != null && ETREVNR_results.size() > 0)
                        {
                            String EtRevnr_sql = "Insert into EtRevnr ( Iwerk, Revnr, Revtx) values(?,?,?);";
                            SQLiteStatement EtRevnr_statement = App_db.compileStatement(EtRevnr_sql);
                            EtRevnr_statement.clearBindings();
                            for (REST_VHLP_SER.ETREVNR result : ETREVNR_results)
                            {
                                EtRevnr_statement.bindString(1, c_e.check_empty(result.getIWERK()));
                                EtRevnr_statement.bindString(2, c_e.check_empty(result.getREVNR()));
                                EtRevnr_statement.bindString(3, c_e.check_empty(result.getREVTX()));
                                EtRevnr_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtRevnr*/




                    /*Reading and Inserting Data into Database Table for EtWbs*/

                    try
                    {
                        List<REST_VHLP_SER.ETWBS> ETWBS_results = response.body().getEtwbs();
                        if (ETWBS_results != null && ETWBS_results.size() > 0)
                        {
                            String ETWBS_sql = "Insert into EtWbs (Iwerk, Gsber, Posid, Poski, Post1, Pspnr, Pspid) values(?,?,?,?,?,?,?);";
                            SQLiteStatement ETWBS_statement = App_db.compileStatement(ETWBS_sql);
                            ETWBS_statement.clearBindings();
                            for (REST_VHLP_SER.ETWBS result : ETWBS_results)
                            {
                                ETWBS_statement.bindString(1, c_e.check_empty(result.getIwerk()));
                                ETWBS_statement.bindString(2, c_e.check_empty(result.getGsber()));
                                ETWBS_statement.bindString(3, c_e.check_empty(result.getPosid()));
                                ETWBS_statement.bindString(4, c_e.check_empty(result.getPoski()));
                                ETWBS_statement.bindString(5, c_e.check_empty(result.getPost1()));
                                ETWBS_statement.bindString(6, c_e.check_empty(result.getPspnr()));
                                ETWBS_statement.bindString(7, c_e.check_empty(result.getPspid()));
                                ETWBS_statement.execute();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtWbs*/




                    /*Reading and Inserting Data into Database Table for EtUdecCodes*/
                    try
                    {
                        List<REST_VHLP_SER.ETUDECCODE> ETUDECCODE_results = response.body().getETUDECCODES();
                        if (ETUDECCODE_results != null && ETUDECCODE_results.size() > 0)
                        {
                            String EtUdecCodes_sql = "Insert into EtUdecCodes (Werks, Katalogart, Auswahlmge, Codegruppe, Kurztext, Code, Kurztext1, Bewertung, Fehlklasse, Qkennzahl, Folgeakti, Fehlklassetxt) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                            SQLiteStatement EtUdecCodes_statement = App_db.compileStatement(EtUdecCodes_sql);
                            EtUdecCodes_statement.clearBindings();
                            for (REST_VHLP_SER.ETUDECCODE etUdecCodesResult : ETUDECCODE_results)
                            {
                                String Werks = c_e.check_empty(etUdecCodesResult.getWERKS());
                                String Katalogart = c_e.check_empty(etUdecCodesResult.getKATALOGART());
                                String Auswahlmge = c_e.check_empty(etUdecCodesResult.getAUSWAHLMGE());
                                String Codegruppe = c_e.check_empty(etUdecCodesResult.getCODEGRUPPE());
                                String Kurztext = c_e.check_empty(etUdecCodesResult.getKURZTEXT());
                                List<REST_VHLP_SER.CODE_____> udecCodesResults = etUdecCodesResult.getCODES();
                                if (udecCodesResults != null && udecCodesResults.size() > 0)
                                {
                                    for (REST_VHLP_SER.CODE_____ udecCodes_result : udecCodesResults)
                                    {
                                        String Code = c_e.check_empty(udecCodes_result.getCODE());
                                        String Kurztext1 = c_e.check_empty(udecCodes_result.getKURZTEXT1());
                                        String Bewertung = c_e.check_empty(udecCodes_result.getBEWERTUNG());
                                        String Fehlklasse = c_e.check_empty(udecCodes_result.getFEHLKLASSE());
                                        String Qkennzahl = c_e.check_empty(udecCodes_result.getQKENNZAHL());
                                        String Folgeakti = c_e.check_empty(udecCodes_result.getFOLGEAKTI());
                                        String Fehlklassetxt = c_e.check_empty(udecCodes_result.getFEHLKLASSETXT());
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
                    catch (Exception e)
                    {
                    }
                    /*Reading and Inserting Data into Database Table for EtUdecCodes*/





                    /*Reading and Inserting Data into Database Table for EtGlacc*/
                    /*try
                    {
                        VHLP_SER.EtGlacc EtGlacc = response.body().getD().getResults().get(0).getEtGlacc();
                        if (EtGlacc != null)
                        {
                            List<VHLP_SER.EtGlacc_Result> etGlacc_results = response.body().getD().getResults().get(0).getEtGlacc().getResults();
                            if (etGlacc_results != null && etGlacc_results.size() > 0)
                            {
                                String EtGlacc_sql = "Insert into EtGlacc ( Bukrs, Ktopl, Glacct, GlacctText) values(?,?,?,?);";
                                SQLiteStatement EtGlacc_statement = App_db.compileStatement(EtGlacc_sql);
                                EtGlacc_statement.clearBindings();
                                for (VHLP_SER.EtGlacc_Result result : etGlacc_results)
                                {
                                    EtGlacc_statement.bindString(1, c_e.check_empty(result.getBukrs()));
                                    EtGlacc_statement.bindString(2, c_e.check_empty(result.getKtopl()));
                                    EtGlacc_statement.bindString(3, c_e.check_empty(result.getGlacct()));
                                    EtGlacc_statement.bindString(4, c_e.check_empty(result.getGlacctText()));
                                    EtGlacc_statement.execute();
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }*/
                    /*Reading and Inserting Data into Database Table for EtGlacc*/




                    /*Reading and Inserting Data into Database Table for EtAsset*/
                    /*try
                    {
                        VHLP_SER.EtAsset EtAsset = response.body().getD().getResults().get(0).getEtAsset();
                        if (EtAsset != null)
                        {
                            List<VHLP_SER.EtAsset_Result> etAsset_results = response.body().getD().getResults().get(0).getEtAsset().getResults();
                            if (etAsset_results != null && etAsset_results.size() > 0)
                            {
                                String EtAsset_sql = "Insert into EtAsset ( Bukrs, Anln1, Anlhtxt) values(?,?,?);";
                                SQLiteStatement EtAsset_statement = App_db.compileStatement(EtAsset_sql);
                                EtAsset_statement.clearBindings();
                                for (VHLP_SER.EtAsset_Result result : etAsset_results)
                                {
                                    EtAsset_statement.bindString(1, c_e.check_empty(result.getBukrs()));
                                    EtAsset_statement.bindString(2, c_e.check_empty(result.getAnln1()));
                                    EtAsset_statement.bindString(3, c_e.check_empty(result.getAnlhtxt()));
                                    EtAsset_statement.execute();
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }*/
                    /*Reading and Inserting Data into Database Table for EtAsset*/




                    /*Reading and Inserting Data into Database Table for EtValType*/
                    /*try
                    {
                        VHLP_SER.EtValType EtValType = response.body().getD().getResults().get(0).getEtValType();
                        if (EtValType != null)
                        {
                            List<VHLP_SER.EtValType_Result> etValType_results = response.body().getD().getResults().get(0).getEtValType().getResults();
                            if (etValType_results != null && etValType_results.size() > 0)
                            {
                                String EtValType_sql = "Insert into EtValType ( Batch) values(?);";
                                SQLiteStatement EtValType_statement = App_db.compileStatement(EtValType_sql);
                                EtValType_statement.clearBindings();
                                for (VHLP_SER.EtValType_Result result : etValType_results)
                                {
                                    EtValType_statement.bindString(1, c_e.check_empty(result.getBatch()));
                                    EtValType_statement.execute();
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }*/
                    /*Reading and Inserting Data into Database Table for EtValType*/



                    App_db.setTransactionSuccessful();
                    Get_Response = "success";

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
            Log.v("kiran_VHLP_insert", String.valueOf(endtime - startTime)+" Milliseconds");
        }
        return Get_Response;
    }

}
