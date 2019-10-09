package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;


import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class REST_Orders
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";


    /* Order TKConfirm */
    private static final String TABLE_Orders_Tkconfirm = "Orders_TKConfirm";
    private static final String KEY_Orders_Tkconfirm_ID = "ID";//0
    private static final String KEY_Orders_Tkconfirm_UUID = "UUID";//1
    private static final String KEY_Orders_Tkconfirm_Aufnr = "Aufnr";//2
    private static final String KEY_Orders_Tkconfirm_Vornr = "Vornr";//3
    private static final String KEY_Orders_Tkconfirm_ConfNo = "ConfNo";//4
    private static final String KEY_Orders_Tkconfirm_ConfText = "ConfText";//5
    private static final String KEY_Orders_Tkconfirm_ActWork = "ActWork";//6
    private static final String KEY_Orders_Tkconfirm_UnWork = "UnWork";//7
    private static final String KEY_Orders_Tkconfirm_PlanWork = "PlanWork";//8
    private static final String KEY_Orders_Tkconfirm_Learr = "Learr";//9
    private static final String KEY_Orders_Tkconfirm_Bemot = "Bemot";//10
    private static final String KEY_Orders_Tkconfirm_Grund = "Grund";//11
    private static final String KEY_Orders_Tkconfirm_Leknw = "Leknw";//12
    private static final String KEY_Orders_Tkconfirm_Aueru = "Aueru";//13
    private static final String KEY_Orders_Tkconfirm_Ausor = "Ausor";//14
    private static final String KEY_Orders_Tkconfirm_Loart = "Loart";//15
    private static final String KEY_Orders_Tkconfirm_Status = "Status";//16
    private static final String KEY_Orders_Tkconfirm_Rsnum = "Rsnum";//17
    private static final String KEY_Orders_Tkconfirm_PostgDate = "PostgDate";//18
    private static final String KEY_Orders_Tkconfirm_Plant = "Plant";//19
    private static final String KEY_Orders_Tkconfirm_WorkCntr = "WorkCntr";//20
    private static final String KEY_Orders_Tkconfirm_ExecStartDate = "ExecStartDate";//21
    private static final String KEY_Orders_Tkconfirm_ExecStartTime = "ExecStartTime";//22
    private static final String KEY_Orders_Tkconfirm_ExecFinDate = "ExecFinDate";//23
    private static final String KEY_Orders_Tkconfirm_ExecFinTime = "ExecFinTime";//24
    private static final String KEY_Orders_Tkconfirm_Pernr = "Pernr";//25
    /*  Order TKConfirm */


    /*EtImrg Table and Fields Names */
    private static final String TABLE_EtImrg = "Orders_EtImrg";
    private static final String KEY_EtImrg_ID = "ID";
    private static final String KEY_EtImrg_UUID = "UUID";
    private static final String KEY_EtImrg_Qmnum = "Qmnum";
    private static final String KEY_EtImrg_Aufnr = "Aufnr";
    private static final String KEY_EtImrg_Vornr = "Vornr";
    private static final String KEY_EtImrg_Mdocm = "Mdocm";
    private static final String KEY_EtImrg_Point = "Point";
    private static final String KEY_EtImrg_Mpobj = "Mpobj";
    private static final String KEY_EtImrg_Mpobt = "Mpobt";
    private static final String KEY_EtImrg_Psort = "Psort";
    private static final String KEY_EtImrg_Pttxt = "Pttxt";
    private static final String KEY_EtImrg_Atinn = "Atinn";
    private static final String KEY_EtImrg_Idate = "Idate";
    private static final String KEY_EtImrg_Itime = "Itime";
    private static final String KEY_EtImrg_Mdtxt = "Mdtxt";
    private static final String KEY_EtImrg_Readr = "Readr";
    private static final String KEY_EtImrg_Atbez = "Atbez";
    private static final String KEY_EtImrg_Msehi = "Msehi";
    private static final String KEY_EtImrg_Msehl = "Msehl";
    private static final String KEY_EtImrg_Readc = "Readc";
    private static final String KEY_EtImrg_Desic = "Desic";
    private static final String KEY_EtImrg_Prest = "Prest";
    private static final String KEY_EtImrg_Docaf = "Docaf";
    private static final String KEY_EtImrg_Codct = "Codct";
    private static final String KEY_EtImrg_Codgr = "Codgr";
    private static final String KEY_EtImrg_Vlcod = "Vlcod";
    private static final String KEY_EtImrg_Action = "Action";
    private static final String KEY_EtImrg_Equnr = "Equnr";
   /* EtImrg Table and Fields Names */


    /* DUE_ORDERS_EtOrderHeader and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderHeader = "DUE_ORDERS_EtOrderHeader";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_ID = "id";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Aufnr = "Aufnr";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Auart = "Auart";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ktext = "Ktext";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ilart = "Ilart";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ernam = "Ernam";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Erdat = "Erdat";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Priok = "Priok";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Equnr = "Equnr";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Strno = "Strno";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_TplnrInt = "TplnrInt";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Bautl = "Bautl";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Gltrp = "Gltrp";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Gstrp = "Gstrp";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Docs = "Docs";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Permits = "Permits";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Altitude = "Altitude";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Latitude = "Latitude";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Longitude = "Longitude";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Qmnum = "Qmnum";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Closed = "Closed";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Completed = "Completed";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ingrp = "Ingrp";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Arbpl = "Arbpl";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Werks = "Werks";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Bemot = "Bemot";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Aueru = "Aueru";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Auarttext = "Auarttext";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Qmartx = "Qmartx";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Qmtxt = "Qmtxt";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Pltxt = "Pltxt";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Eqktx = "Eqktx";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Priokx = "Priokx";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ilatx = "Ilatx";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Plantname = "Plantname";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Wkctrname = "Wkctrname";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ingrpname = "Ingrpname";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Maktx = "Maktx";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Xstatus = "Xstatus";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Usr01 = "Usr01";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Usr02 = "Usr02";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Usr03 = "Usr03";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Usr04 = "Usr04";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Usr05 = "Usr05";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Kokrs = "Kokrs";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Kostl = "Kostl";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Anlzu = "Anlzu";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Anlzux = "Anlzux";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ausvn = "Ausvn";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Ausbs = "Ausbs";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Auswk = "Auswk";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Qmnam = "Qmnam";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_ParnrVw = "ParnrVw";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_NameVw = "NameVw";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Posid = "Posid";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Revnr = "Revnr";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Fistl = "Fistl";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpermit = "Zzpermit";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpmistdt = "Zzpmistdt";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpmisttm = "Zzpmisttm";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpmiendt = "Zzpmiendt";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpmientm = "Zzpmientm";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_Zzpmihr1 = "Zzpmihr1";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_IdealHours = "IdealHours";
    private static final String KEY_DUE_ORDERS_EtOrderHeader_RSNUM = "RSNUM";
    /* DUE_ORDERS_EtOrderHeader and Fields Names */


    /* DUE_ORDERS_EtOrderOperations and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderOperations = "DUE_ORDERS_EtOrderOperations";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_ID = "id";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Aufnr = "Aufnr";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Vornr = "Vornr";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Uvorn = "Uvorn";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Ltxa1 = "Ltxa1";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Arbpl = "Arbpl";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Werks = "Werks";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Steus = "Steus";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Larnt = "Larnt";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Dauno = "Dauno";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Daune = "Daune";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Fsavd = "Fsavd";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Ssedd = "Ssedd";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Pernr = "Pernr";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Asnum = "Asnum";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Plnty = "Plnty";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Plnal = "Plnal";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Plnnr = "Plnnr";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Rueck = "Rueck";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Aueru = "Aueru";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_ArbplText = "ArbplText";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_WerksText = "WerksText";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_SteusText = "SteusText";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_LarntText = "LarntText";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Usr01 = "Usr01";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Usr02 = "Usr02";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Usr03 = "Usr03";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Usr04 = "Usr04";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Usr05 = "Usr05";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Action = "Action";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Anlzu = "Anlzu";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_Sortl = "Sortl";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_IdealCond = "IdealCond";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_ActualCond = "ActualCond";
    private static final String KEY_DUE_ORDERS_EtOrderOperations_SAKTO = "SAKTO";
    /* DUE_ORDERS_EtOrderOperations and Fields Names */


    /* GET_DUE_ORDERS_Longtext Table and Fields Names */
    private static final String TABLE_GET_DUE_ORDERS_Longtext = "DUE_ORDERS_Longtext";
    private static final String KEY_DUE_ORDERS_Longtext_ID = "id";
    private static final String KEY_DUE_ORDERS_Longtext_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_Longtext_Aufnr = "Aufnr";
    private static final String KEY_DUE_ORDERS_Longtext_Activity = "Activity";
    private static final String KEY_DUE_ORDERS_Longtext_TextLine = "TextLine";
    private static final String KEY_DUE_ORDERS_Longtext_Tdid = "Tdid";
    /* GET_DUE_ORDERS_Longtext Table and Fields Names */


    /* EtOrderOlist Table and Fields Names */
    private static final String TABLE_GET_EtOrderOlist = "EtOrderOlist";
    private static final String KEY_GET_EtOrderOlist_ID = "id";
    private static final String KEY_GET_EtOrderOlist_UUID = "UUID";
    private static final String KEY_GET_EtOrderOlist_Aufnr = "Aufnr";
    private static final String KEY_GET_EtOrderOlist_Obknr = "Obknr";
    private static final String KEY_GET_EtOrderOlist_Obzae = "Obzae";
    private static final String KEY_GET_EtOrderOlist_Qmnum = "Qmnum";
    private static final String KEY_GET_EtOrderOlist_Equnr = "Equnr";
    private static final String KEY_GET_EtOrderOlist_Strno = "Strno";
    private static final String KEY_GET_EtOrderOlist_Tplnr = "Tplnr";
    private static final String KEY_GET_EtOrderOlist_Bautl = "Bautl";
    private static final String KEY_GET_EtOrderOlist_Qmtxt = "Qmtxt";
    private static final String KEY_GET_EtOrderOlist_Pltxt = "Pltxt";
    private static final String KEY_GET_EtOrderOlist_Eqktx = "Eqktx";
    private static final String KEY_GET_EtOrderOlist_Maktx = "Maktx";
    private static final String KEY_GET_EtOrderOlist_Action = "Action";
	/* EtOrderOlist Table and Fields Names */


    /* EtOrderStatus Table and Fields Names */
    private static final String TABLE_EtOrderStatus = "EtOrderStatus";
    private static final String KEY_EtOrderStatus_ID = "ID";
    private static final String KEY_EtOrderStatus_UUID = "UUID";
    private static final String KEY_EtOrderStatus_Aufnr = "Aufnr";
    private static final String KEY_EtOrderStatus_Vornr = "Vornr";
    private static final String KEY_EtOrderStatus_Objnr = "Objnr";
    private static final String KEY_EtOrderStatus_Stsma = "Stsma";
    private static final String KEY_EtOrderStatus_Inist = "Inist";
    private static final String KEY_EtOrderStatus_Stonr = "Stonr";
    private static final String KEY_EtOrderStatus_Hsonr = "Hsonr";
    private static final String KEY_EtOrderStatus_Nsonr = "Nsonr";
    private static final String KEY_EtOrderStatus_Stat = "Stat";
    private static final String KEY_EtOrderStatus_Act = "Act";
    private static final String KEY_EtOrderStatus_Txt04 = "Txt04";
    private static final String KEY_EtOrderStatus_Txt30 = "Txt30";
    private static final String KEY_EtOrderStatus_Action = "Action";
	/* EtOrderStatus Table and Fields Names */


    /* GET_DUE_ORDERS_EtDocs Table and Fields Names */
    private static final String TABLE_GET_DUE_ORDERS_EtDocs = "DUE_ORDERS_EtDocs";
    private static final String KEY_DUE_ORDERS_EtDocs_ID = "id";
    private static final String KEY_DUE_ORDERS_EtDocs_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_EtDocs_Zobjid = "Zobjid";
    private static final String KEY_DUE_ORDERS_EtDocs_Zdoctype = "Zdoctype";
    private static final String KEY_DUE_ORDERS_EtDocs_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_DUE_ORDERS_EtDocs_Filename = "Filename";
    private static final String KEY_DUE_ORDERS_EtDocs_Filetype = "Filetype";
    private static final String KEY_DUE_ORDERS_EtDocs_Fsize = "Fsize";
    private static final String KEY_DUE_ORDERS_EtDocs_Content = "Content";
    private static final String KEY_DUE_ORDERS_EtDocs_DocId = "DocId";
    private static final String KEY_DUE_ORDERS_EtDocs_DocType = "DocType";
    private static final String KEY_DUE_ORDERS_EtDocs_Objtype = "Objtype";
    private static final String KEY_DUE_ORDERS_EtDocs_Filepath = "Filepath";
    private static final String KEY_DUE_ORDERS_EtDocs_Status = "Status";
	/* GET_DUE_ORDERS_EtDocs Table and Fields Names */



    /* EtOrderComponents and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderComponents = "EtOrderComponents";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_ID = "id";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Aufnr = "Aufnr";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Vornr = "Vornr";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Uvorn = "Uvorn";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Rsnum = "Rsnum";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Rspos = "Rspos";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Matnr = "Matnr";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Werks = "Werks";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Lgort = "Lgort";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Posnr = "Posnr";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Bdmng = "Bdmng";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Meins = "Meins";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Postp = "Postp";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_MatnrText = "MatnrText";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_WerksText = "WerksText";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_LgortText = "LgortText";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_PostpText = "PostpText";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Usr01 = "Usr01";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Usr02 = "Usr02";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Usr03 = "Usr03";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Usr04 = "Usr04";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Usr05 = "Usr05";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Action = "Action";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Wempf = "Wempf";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Ablad = "Ablad";
    private static final String KEY_DUE_ORDERS_EtOrderComponentsp_Charg = "Charg";
	/* EtOrderComponents and Fields Names */



    public static String Get_DORD_Data(Context context, String transmit_type, String order_number)
    {
        try
        {
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD"))
            {
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Orders_Tkconfirm);
                String CREATE_TABLE_Orders_Tkconfirm = "CREATE TABLE IF NOT EXISTS " + TABLE_Orders_Tkconfirm + ""
                        + "( "
                        + KEY_Orders_Tkconfirm_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Orders_Tkconfirm_UUID + " TEXT,"
                        + KEY_Orders_Tkconfirm_Aufnr + " TEXT,"
                        + KEY_Orders_Tkconfirm_Vornr + " TEXT,"
                        + KEY_Orders_Tkconfirm_ConfNo + " TEXT,"
                        + KEY_Orders_Tkconfirm_ConfText + " TEXT,"
                        + KEY_Orders_Tkconfirm_ActWork + " TEXT,"
                        + KEY_Orders_Tkconfirm_UnWork + " TEXT,"
                        + KEY_Orders_Tkconfirm_PlanWork + " TEXT,"
                        + KEY_Orders_Tkconfirm_Learr + " TEXT,"
                        + KEY_Orders_Tkconfirm_Bemot + " TEXT,"
                        + KEY_Orders_Tkconfirm_Grund + " TEXT,"
                        + KEY_Orders_Tkconfirm_Leknw + " TEXT,"
                        + KEY_Orders_Tkconfirm_Aueru + " TEXT,"
                        + KEY_Orders_Tkconfirm_Ausor + " TEXT,"
                        + KEY_Orders_Tkconfirm_Loart + " TEXT,"
                        + KEY_Orders_Tkconfirm_Status + " TEXT,"
                        + KEY_Orders_Tkconfirm_Rsnum + " TEXT,"
                        + KEY_Orders_Tkconfirm_PostgDate + " TEXT,"
                        + KEY_Orders_Tkconfirm_Plant + " TEXT,"
                        + KEY_Orders_Tkconfirm_WorkCntr + " TEXT,"
                        + KEY_Orders_Tkconfirm_ExecStartDate + " TEXT,"
                        + KEY_Orders_Tkconfirm_ExecStartTime + " TEXT,"
                        + KEY_Orders_Tkconfirm_ExecFinDate + " TEXT,"
                        + KEY_Orders_Tkconfirm_ExecFinTime + " TEXT,"
                        + KEY_Orders_Tkconfirm_Pernr + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Orders_Tkconfirm);


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtImrg);
                String CREATE_TABLE_EtImrg = "CREATE TABLE IF NOT EXISTS " + TABLE_EtImrg + ""
                        + "( "
                        + KEY_EtImrg_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtImrg_UUID + " TEXT,"
                        + KEY_EtImrg_Qmnum + " TEXT,"
                        + KEY_EtImrg_Aufnr + " TEXT,"
                        + KEY_EtImrg_Vornr + " TEXT,"
                        + KEY_EtImrg_Mdocm + " TEXT,"
                        + KEY_EtImrg_Point + " TEXT,"
                        + KEY_EtImrg_Mpobj + " TEXT,"
                        + KEY_EtImrg_Mpobt + " TEXT,"
                        + KEY_EtImrg_Psort + " TEXT,"
                        + KEY_EtImrg_Pttxt + " TEXT,"
                        + KEY_EtImrg_Atinn + " TEXT,"
                        + KEY_EtImrg_Idate + " TEXT,"
                        + KEY_EtImrg_Itime + " TEXT,"
                        + KEY_EtImrg_Mdtxt + " TEXT,"
                        + KEY_EtImrg_Readr + " TEXT,"
                        + KEY_EtImrg_Atbez + " TEXT,"
                        + KEY_EtImrg_Msehi + " TEXT,"
                        + KEY_EtImrg_Msehl + " TEXT,"
                        + KEY_EtImrg_Readc + " TEXT,"
                        + KEY_EtImrg_Desic + " TEXT,"
                        + KEY_EtImrg_Prest + " TEXT,"
                        + KEY_EtImrg_Docaf + " TEXT,"
                        + KEY_EtImrg_Codct + " TEXT,"
                        + KEY_EtImrg_Codgr + " TEXT,"
                        + KEY_EtImrg_Vlcod + " TEXT,"
                        + KEY_EtImrg_Action + " TEXT,"
                        + KEY_EtImrg_Equnr + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtImrg);


                /* Creating TABLE_DUE_ORDERS_EtOrderHeader Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderHeader);
                String CREATE_DUE_ORDERS_EtOrderHeader_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DUE_ORDERS_EtOrderHeader + ""
                        + "( "
                        + KEY_DUE_ORDERS_EtOrderHeader_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_EtOrderHeader_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Aufnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Auart + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ktext + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ilart + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ernam + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Erdat + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Priok + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Equnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Strno + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_TplnrInt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Bautl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Gltrp + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Gstrp + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Docs + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Permits + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Altitude + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Latitude + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Longitude + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Qmnum + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Closed + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Completed + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ingrp + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Arbpl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Werks + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Bemot + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Aueru + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Auarttext + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Qmartx + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Qmtxt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Pltxt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Eqktx + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Priokx + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ilatx + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Plantname + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Wkctrname + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ingrpname + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Maktx + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Xstatus + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Usr01 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Usr02 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Usr03 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Usr04 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Usr05 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Kokrs + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Kostl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Anlzu + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Anlzux + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ausvn + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Ausbs + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Auswk + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Qmnam + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_ParnrVw + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_NameVw + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Posid + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Revnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Fistl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpermit + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpmistdt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpmisttm + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpmiendt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpmientm + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_Zzpmihr1 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_IdealHours + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderHeader_RSNUM + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderHeader_TABLE);
		        /* Creating TABLE_DUE_ORDERS_EtOrderHeader Table with Fields */


		        /* DUE_ORDERS_EtOrderOperations and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderOperations);
                String CREATE_DUE_ORDERS_EtOrderOperations_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DUE_ORDERS_EtOrderOperations + ""
                        + "( "
                        + KEY_DUE_ORDERS_EtOrderOperations_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_EtOrderOperations_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Aufnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Vornr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Uvorn + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Ltxa1 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Arbpl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Werks + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Steus + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Larnt + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Dauno + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Daune + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Fsavd + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Ssedd + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Pernr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Asnum + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Plnty + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Plnal + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Plnnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Rueck + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Aueru + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_ArbplText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_WerksText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_SteusText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_LarntText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Usr01 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Usr02 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Usr03 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Usr04 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Usr05 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Action + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Anlzu + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_Sortl + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_IdealCond + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_ActualCond + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderOperations_SAKTO + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderOperations_TABLE);
		        /* DUE_ORDERS_EtOrderOperations and Fields Names */


		        /* GET_DUE_ORDERS_Longtext Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_ORDERS_Longtext);
                String CREATE_TABLE_GET_DUE_ORDERS_Longtext = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_ORDERS_Longtext + ""
                        + "( "
                        + KEY_DUE_ORDERS_Longtext_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_Longtext_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_Longtext_Aufnr + " TEXT,"
                        + KEY_DUE_ORDERS_Longtext_Activity + " TEXT,"
                        + KEY_DUE_ORDERS_Longtext_TextLine + " TEXT,"
                        + KEY_DUE_ORDERS_Longtext_Tdid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_DUE_ORDERS_Longtext);
		        /* GET_DUE_ORDERS_Longtext Table and Fields Names */


		        /* Creating EtOrderOlist Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrderOlist);
                String CREATE_TABLE_GET_EtOrderOlist = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtOrderOlist + ""
                        + "( "
                        + KEY_GET_EtOrderOlist_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrderOlist_UUID + " TEXT,"
                        + KEY_GET_EtOrderOlist_Aufnr + " TEXT,"
                        + KEY_GET_EtOrderOlist_Obknr + " TEXT,"
                        + KEY_GET_EtOrderOlist_Obzae + " TEXT,"
                        + KEY_GET_EtOrderOlist_Qmnum + " TEXT,"
                        + KEY_GET_EtOrderOlist_Equnr + " TEXT,"
                        + KEY_GET_EtOrderOlist_Strno + " TEXT,"
                        + KEY_GET_EtOrderOlist_Tplnr + " TEXT,"
                        + KEY_GET_EtOrderOlist_Bautl + " TEXT,"
                        + KEY_GET_EtOrderOlist_Qmtxt + " TEXT,"
                        + KEY_GET_EtOrderOlist_Pltxt + " TEXT,"
                        + KEY_GET_EtOrderOlist_Eqktx + " TEXT,"
                        + KEY_GET_EtOrderOlist_Maktx + " TEXT,"
                        + KEY_GET_EtOrderOlist_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrderOlist);
		        /* Creating EtOrderOlist Table with Fields */


		        /* Creating EtOrderStatus Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtOrderStatus);
                String CREATE_TABLE_EtOrderStatus = "CREATE TABLE IF NOT EXISTS " + TABLE_EtOrderStatus + ""
                        + "( "
                        + KEY_EtOrderStatus_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtOrderStatus_UUID + " TEXT,"
                        + KEY_EtOrderStatus_Aufnr + " TEXT,"
                        + KEY_EtOrderStatus_Vornr + " TEXT,"
                        + KEY_EtOrderStatus_Objnr + " TEXT,"
                        + KEY_EtOrderStatus_Stsma + " TEXT,"
                        + KEY_EtOrderStatus_Inist + " TEXT,"
                        + KEY_EtOrderStatus_Stonr + " TEXT,"
                        + KEY_EtOrderStatus_Hsonr + " TEXT,"
                        + KEY_EtOrderStatus_Nsonr + " TEXT,"
                        + KEY_EtOrderStatus_Stat + " TEXT,"
                        + KEY_EtOrderStatus_Act + " TEXT,"
                        + KEY_EtOrderStatus_Txt04 + " TEXT,"
                        + KEY_EtOrderStatus_Txt30 + " TEXT,"
                        + KEY_EtOrderStatus_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtOrderStatus);
		        /* Creating EtOrderStatus Table with Fields */


		        /* Creating DUE_ORDERS_EtDocs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_ORDERS_EtDocs);
                String CREATE_TABLE_GET_DUE_ORDERS_EtDocs = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_ORDERS_EtDocs + ""
                        + "( "
                        + KEY_DUE_ORDERS_EtDocs_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_EtDocs_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Zobjid + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Zdoctype + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_ZdoctypeItem + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Filename + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Filetype + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Fsize + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Content + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_DocId + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_DocType + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Objtype + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Filepath + " TEXT,"
                        + KEY_DUE_ORDERS_EtDocs_Status + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_DUE_ORDERS_EtDocs);
		        /* Creating DUE_ORDERS_EtDocs Table with Fields */



		        /* Creating EtOrderComponents with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderComponents);
                String CREATE_DUE_ORDERS_EtOrderComponents_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DUE_ORDERS_EtOrderComponents + ""
                        + "( "
                        + KEY_DUE_ORDERS_EtOrderComponentsp_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Aufnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Vornr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Uvorn + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Rsnum + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Rspos + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Matnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Werks + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Lgort + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Posnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Bdmng + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Meins + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Postp + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_MatnrText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_WerksText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_LgortText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_PostpText + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Usr01 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Usr02 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Usr03 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Usr04 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Usr05 + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Action + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Wempf + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Ablad + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Charg + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderComponents_TABLE);
		        /* Creating EtOrderComponents Table with Fields */

            }
            else if (transmit_type.equalsIgnoreCase("Single_Ord"))
            {
            }
            else
            {
                App_db.execSQL("delete from Orders_TKConfirm");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations");
                App_db.execSQL("delete from DUE_ORDERS_Longtext");
                App_db.execSQL("delete from EtOrderOlist");
                App_db.execSQL("delete from EtOrderStatus");
                App_db.execSQL("delete from DUE_ORDERS_EtDocs");
                App_db.execSQL("delete from EtOrderComponents");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = context.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"C2", "DO", webservice_type});
            if (cursor != null && cursor.getCount() > 0)
            {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
            device_uuid = deviceUuid.toString();
		    /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            String URL = context.getString(R.string.ip_address);


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
            Call<REST_Orders_SER> call = service.postORDERDetails(url_link, basic, modelLoginRest);
            Response<REST_Orders_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_DORD_code",response_status_code+"...");
            if (response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String orders_insert_status = REST_Insert_Orders_Data.Insert_Order_Data(context, response.body(), "","DUORD");
                    if(orders_insert_status.equalsIgnoreCase("Success"))
                    {
                        Get_Response = "Success";
                    }
                    else
                    {
                        Get_Response = "Exception";
                    }
                }
            }
            else
            {
            }
        }
        catch (Exception ex)
        {
            Log.v("kiran_ord_exp", ex.getMessage()+"...");
            Get_Response = "Exception";
        }
        finally
        {
        }
        return Get_Response;
    }

}
