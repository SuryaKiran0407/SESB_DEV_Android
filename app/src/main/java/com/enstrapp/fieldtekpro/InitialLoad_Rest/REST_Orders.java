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
import com.enstrapp.fieldtekpro.R;
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


    /* Order TKConfirm Attachments */
    private static final String TABLE_Orders_Attachments = "Orders_Attachments";
    private static final String KEY_Orders_Attachments_ID = "ID";
    private static final String KEY_Orders_Attachments_UUID = "UUID";
    private static final String KEY_Orders_Attachments_OBJECTID = "Object_id";
    private static final String KEY_Orders_Attachments_OBJECTTYPE = "Object_type";
    private static final String KEY_Orders_Attachments_file_path = "file_path";
    private static final String KEY_Orders_Attachments_jsaid = "jsa_id";
    /*  Order TKConfirm Attachments */

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
	/* GET_DUE_ORDERS_EtDocs Table and Fields Names */



    /* GET_DUE_ORDERS_WA_EtDocs_Dummy Table and Fields Names */
    private static final String TABLE_GET_DUE_ORDERS_WA_EtDocs_Dummy = "DUE_ORDERS_WA_EtDocs_Dummy";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_ID = "id";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Zobjid = "Zobjid";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Zdoctype = "Zdoctype";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Filename = "Filename";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Filetype = "Filetype";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Fsize = "Fsize";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Content = "Content";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_DocId = "DocId";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_DocType = "DocType";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Objtype = "Objtype";
    private static final String KEY_DUE_ORDERS_WA_EtDocs_Dummy_Status = "Status";
    /* GET_DUE_ORDERS_WA_EtDocs_Dummy Table and Fields Names */


    /* EtWcmWwData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWwData = "EtWcmWwData";
    private static final String KEY_GET_EtWcmWwData_ID = "id";
    private static final String KEY_GET_EtWcmWwData_UUID = "UUID";
    private static final String KEY_GET_EtWcmWwData_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWwData_Objart = "Objart";
    private static final String KEY_GET_EtWcmWwData_Wapnr = "Wapnr";
    private static final String KEY_GET_EtWcmWwData_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWwData_Usage = "Usage";
    private static final String KEY_GET_EtWcmWwData_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWwData_Train = "Train";
    private static final String KEY_GET_EtWcmWwData_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWwData_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWwData_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWwData_Etape = "Etape";
    private static final String KEY_GET_EtWcmWwData_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWwData_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWwData_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWwData_Priok = "Priok";
    private static final String KEY_GET_EtWcmWwData_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWwData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWwData_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWwData_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWwData_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWwData_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWwData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWwData_Crea = "Crea";
    private static final String KEY_GET_EtWcmWwData_Prep = "Prep";
    private static final String KEY_GET_EtWcmWwData_Comp = "Comp";
    private static final String KEY_GET_EtWcmWwData_Appr = "Appr";
    private static final String KEY_GET_EtWcmWwData_Pappr = "Pappr";
    private static final String KEY_GET_EtWcmWwData_Action = "Action";
    private static final String KEY_GET_EtWcmWwData_Begru = "Begru";
    private static final String KEY_GET_EtWcmWwData_Begtx = "Begtx";
	/* EtWcmWwData Table and Fields Names */


    /* EtWcmWaData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWaData = "EtWcmWaData";
    private static final String KEY_GET_EtWcmWaData_ID = "id";
    private static final String KEY_GET_EtWcmWaData_UUID = "UUID";
    private static final String KEY_GET_EtWcmWaData_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWaData_Objart = "Objart";
    private static final String KEY_GET_EtWcmWaData_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWaData_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWaData_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWaData_Usage = "Usage";
    private static final String KEY_GET_EtWcmWaData_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWaData_Train = "Train";
    private static final String KEY_GET_EtWcmWaData_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWaData_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWaData_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWaData_Etape = "Etape";
    private static final String KEY_GET_EtWcmWaData_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWaData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWaData_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWaData_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWaData_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWaData_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWaData_Priok = "Priok";
    private static final String KEY_GET_EtWcmWaData_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWaData_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWaData_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWaData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWaData_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWaData_Crea = "Crea";
    private static final String KEY_GET_EtWcmWaData_Prep = "Prep";
    private static final String KEY_GET_EtWcmWaData_Comp = "Comp";
    private static final String KEY_GET_EtWcmWaData_Appr = "Appr";
    private static final String KEY_GET_EtWcmWaData_Action = "Action";
    private static final String KEY_GET_EtWcmWaData_Begru = "Begru";
    private static final String KEY_GET_EtWcmWaData_Begtx = "Begtx";
    private static final String KEY_GET_EtWcmWaData_Extperiod = "Extperiod";
	/* EtWcmWaData Table and Fields Names */



    /* EtWcmWaChkReq Table and Fields Names */
    private static final String TABLE_GET_EtWcmWaChkReq = "EtWcmWaChkReq";
    private static final String KEY_GET_EtWcmWaChkReq_ID = "id";
    private static final String KEY_GET_EtWcmWaChkReq_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWaChkReq_Wapityp = "Wapityp";
    private static final String KEY_GET_EtWcmWaChkReq_Needid = "Needid";
    private static final String KEY_GET_EtWcmWaChkReq_Value = "Value";
    private static final String KEY_GET_EtWcmWaChkReq_ChkPointType = "ChkPointType";
    private static final String KEY_GET_EtWcmWaChkReq_Desctext = "Desctext";
    private static final String KEY_GET_EtWcmWaChkReq_Wkgrp = "Wkgrp";
    private static final String KEY_GET_EtWcmWaChkReq_Needgrp = "Needgrp";
    private static final String KEY_GET_EtWcmWaChkReq_Tplnr = "Tplnr";
    private static final String KEY_GET_EtWcmWaChkReq_Equnr = "Equnr";
    private static final String KEY_GET_EtWcmWaChkReq_NeedidComments = "NeedidComments";
    private static final String KEY_GET_EtWcmWaChkReq_Person_resp = "Person_resp";
    private static final String KEY_GET_EtWcmWaChkReq_HotworkRel = "HotworkRel";
    private static final String KEY_GET_EtWcmWaChkReq_PartNo = "PartNo";
	/* EtWcmWaChkReq Table and Fields Names */


    /* EtWcmWdData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdData = "EtWcmWdData";
    private static final String KEY_GET_EtWcmWdData_ID = "id";
    private static final String KEY_GET_EtWcmWdData_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWdData_Objart = "Objart";
    private static final String KEY_GET_EtWcmWdData_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdData_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWdData_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWdData_Usage = "Usage";
    private static final String KEY_GET_EtWcmWdData_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWdData_Train = "Train";
    private static final String KEY_GET_EtWcmWdData_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWdData_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWdData_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWdData_Etape = "Etape";
    private static final String KEY_GET_EtWcmWdData_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWdData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdData_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWdData_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWdData_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWdData_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWdData_Priok = "Priok";
    private static final String KEY_GET_EtWcmWdData_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWdData_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWdData_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWdData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdData_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdData_Crea = "Crea";
    private static final String KEY_GET_EtWcmWdData_Prep = "Prep";
    private static final String KEY_GET_EtWcmWdData_Comp = "Comp";
    private static final String KEY_GET_EtWcmWdData_Appr = "Appr";
    private static final String KEY_GET_EtWcmWdData_Action = "Action";
    private static final String KEY_GET_EtWcmWdData_Begru = "Begru";
    private static final String KEY_GET_EtWcmWdData_Begtx = "Begtx";
    private static final String KEY_GET_EtWcmWdData_Pappr = "Pappr";
    private static final String KEY_GET_EtWcmWdData_ZzIniKey = "ZzIniKey";
    private static final String KEY_GET_EtWcmWdData_ZzMlbKey = "ZzMlbKey";
    private static final String KEY_GET_EtWcmWdData_ZzIssKey = "ZzIssKey";
	/* EtWcmWdData Table and Fields Names */


    /* EtWcmWdDataTagtext Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataTagtext = "EtWcmWdDataTagtext";
    private static final String KEY_EtWcmWdDataTagtext_ID = "id";
    private static final String KEY_EtWcmWdDataTagtext_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataTagtext_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataTagtext_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataTagtext_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataTagtext_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataTagtext_Action = "Action";
	/* EtWcmWdDataTagtext Table and Fields Names */



    /* EtWcmWdDataTagtext_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataTagtext_Dummy = "EtWcmWdDataTagtext_Dummy";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_ID = "id";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataTagtext_Dummy_Action = "Action";
    /* EtWcmWdDataTagtext Table and Fields Names */


    /* EtWcmWdDataUntagtext Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataUntagtext = "EtWcmWdDataUntagtext";
    private static final String KEY_EtWcmWdDataUntagtext_ID = "id";
    private static final String KEY_EtWcmWdDataUntagtext_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataUntagtext_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataUntagtext_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataUntagtext_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataUntagtext_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataUntagtext_Action = "Action";
	/* EtWcmWdDataUntagtext Table and Fields Names */


    /* EtWcmWdDataUntagtext_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdDataUntagtext_Dummy = "EtWcmWdDataUntagtext_Dummy";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_ID = "id";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_Aufnr = "Aufnr";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_Wcnr = "Wcnr";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_Objtype = "Objtype";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_FormatCol = "FormatCol";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_TextLine = "TextLine ";
    private static final String KEY_EtWcmWdDataUntagtext_Dummy_Action = "Action";
    /* EtWcmWdDataUntagtext Table and Fields Names */


    /* EtWcmWdItemData Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdItemData = "EtWcmWdItemData";
    private static final String KEY_GET_EtWcmWdItemData_ID = "id";
    private static final String KEY_GET_EtWcmWdItemData_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdItemData_Wcitm = "Wcitm";
    private static final String KEY_GET_EtWcmWdItemData_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdItemData_Itmtyp = "Itmtyp";
    private static final String KEY_GET_EtWcmWdItemData_Seq = "Seq";
    private static final String KEY_GET_EtWcmWdItemData_Pred = "Pred";
    private static final String KEY_GET_EtWcmWdItemData_Succ = "Succ";
    private static final String KEY_GET_EtWcmWdItemData_Ccobj = "Ccobj";
    private static final String KEY_GET_EtWcmWdItemData_Cctyp = "Cctyp";
    private static final String KEY_GET_EtWcmWdItemData_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdItemData_Tggrp = "Tggrp";
    private static final String KEY_GET_EtWcmWdItemData_Tgstep = "Tgstep";
    private static final String KEY_GET_EtWcmWdItemData_Tgproc = "Tgproc";
    private static final String KEY_GET_EtWcmWdItemData_Tgtyp = "Tgtyp";
    private static final String KEY_GET_EtWcmWdItemData_Tgseq = "Tgseq";
    private static final String KEY_GET_EtWcmWdItemData_Tgtxt = "Tgtxt";
    private static final String KEY_GET_EtWcmWdItemData_Unstep = "Unstep";
    private static final String KEY_GET_EtWcmWdItemData_Unproc = "Unproc";
    private static final String KEY_GET_EtWcmWdItemData_Untyp = "Untyp";
    private static final String KEY_GET_EtWcmWdItemData_Unseq = "Unseq";
    private static final String KEY_GET_EtWcmWdItemData_Untxt = "Untxt";
    private static final String KEY_GET_EtWcmWdItemData_Phblflg = "Phblflg";
    private static final String KEY_GET_EtWcmWdItemData_Phbltyp = "Phbltyp";
    private static final String KEY_GET_EtWcmWdItemData_Phblnr = "Phblnr";
    private static final String KEY_GET_EtWcmWdItemData_Tgflg = "Tgflg";
    private static final String KEY_GET_EtWcmWdItemData_Tgform = "Tgform";
    private static final String KEY_GET_EtWcmWdItemData_Tgnr = "Tgnr";
    private static final String KEY_GET_EtWcmWdItemData_Unform = "Unform";
    private static final String KEY_GET_EtWcmWdItemData_Unnr = "Unnr";
    private static final String KEY_GET_EtWcmWdItemData_Control = "Control";
    private static final String KEY_GET_EtWcmWdItemData_Location = "Location";
    private static final String KEY_GET_EtWcmWdItemData_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdItemData_Action = "Action";
    private static final String KEY_GET_EtWcmWdItemData_Btg = "Btg";
    private static final String KEY_GET_EtWcmWdItemData_Etg = "Etg";
    private static final String KEY_GET_EtWcmWdItemData_Bug = "Bug";
    private static final String KEY_GET_EtWcmWdItemData_Eug = "Eug";
    private static final String KEY_GET_EtWcmWdItemData_Btug = "Btug";
    private static final String KEY_GET_EtWcmWdItemData_Etug = "Etug";
    private static final String KEY_GET_EtWcmWdItemData_Button = "Button";
    private static final String KEY_GET_EtWcmWdItemData_CurrStatus = "CurrStatus";
    /* EtWcmWdItemData Table and Fields Names */



    /* EtWcmWdItemData_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdItemData_Dummy = "EtWcmWdItemData_Dummy";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Wcitm = "Wcitm";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Itmtyp = "Itmtyp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Seq = "Seq";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Pred = "Pred";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Succ = "Succ";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Ccobj = "Ccobj";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Cctyp = "Cctyp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tggrp = "Tggrp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgstep = "Tgstep";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgproc = "Tgproc";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgtyp = "Tgtyp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgseq = "Tgseq";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgtxt = "Tgtxt";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Unstep = "Unstep";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Unproc = "Unproc";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Untyp = "Untyp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Unseq = "Unseq";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Untxt = "Untxt";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Phblflg = "Phblflg";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Phbltyp = "Phbltyp";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Phblnr = "Phblnr";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgflg = "Tgflg";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgform = "Tgform";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Tgnr = "Tgnr";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Unform = "Unform";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Unnr = "Unnr";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Control = "Control";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Location = "Location";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Action = "Action";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Btg = "Btg";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Etg = "Etg";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Bug = "Bug";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Eug = "Eug";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Btug = "Btug";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Etug = "Etug";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_Button = "Button";
    private static final String KEY_GET_EtWcmWdItemData_Dummy_CurrStatus = "CurrStatus";
    /* EtWcmWdItemData_Dummy Table and Fields Names */

    /* EtWcmWcagns Table and Fields Names */
    private static final String TABLE_GET_EtWcmWcagns = "EtWcmWcagns";
    private static final String KEY_GET_EtWcmWcagns_ID = "id";
    private static final String KEY_GET_EtWcmWcagns_UUID = "UUID";
    private static final String KEY_GET_EtWcmWcagns_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWcagns_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWcagns_Wapnr = "Wapnr";
    private static final String KEY_GET_EtWcmWcagns_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWcagns_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWcagns_Wcitm = "Wcitm";
    private static final String KEY_GET_EtWcmWcagns_CountApp = "CountApp";
    private static final String KEY_GET_EtWcmWcagns_CountCycle = "CountCycle";
    private static final String KEY_GET_EtWcmWcagns_ApproverType = "ApproverType";
    private static final String KEY_GET_EtWcmWcagns_ApproverID = "ApproverID";
    private static final String KEY_GET_EtWcmWcagns_ApproverName = "ApproverName";
    private static final String KEY_GET_EtWcmWcagns_Gendatum = "Gendatum";
    private static final String KEY_GET_EtWcmWcagns_Gentime = "Gentime";
    private static final String KEY_GET_EtWcmWcagns_Genvname = "Genvname";
    private static final String KEY_GET_EtWcmWcagns_Genvdate = "Genvdate";
    private static final String KEY_GET_EtWcmWcagns_Gentim = "Gentim";
    private static final String KEY_GET_EtWcmWcagns_DyGnert = "DyGnert";
    private static final String KEY_GET_EtWcmWcagns_Action = "Action";
	/* EtWcmWcagns Table and Fields Names */


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



    /* EtOrderCost and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderCost = "EtOrderCost";
    private static final String KEY_DUE_ORDERS_EtOrderCost_ID = "id";
    private static final String KEY_DUE_ORDERS_EtOrderCost_UUID = "UUID";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Aufnr = "Aufnr";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Text = "Text";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Estcost = "Estcost";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Plancost = "Plancost";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Actcost = "Actcost";
    private static final String KEY_DUE_ORDERS_EtOrderCost_Currency = "Currency";
    /* EtOrderCost and Fields Names */



    /* EtWcmWwData_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWwData_Dummy = "EtWcmWwData_Dummy";
    private static final String KEY_GET_EtWcmWwData_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWwData_Dummy_UUID = "UUID";
    private static final String KEY_GET_EtWcmWwData_Dummy_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Objart = "Objart";
    private static final String KEY_GET_EtWcmWwData_Dummy_Wapnr = "Wapnr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWwData_Dummy_Usage = "Usage";
    private static final String KEY_GET_EtWcmWwData_Dummy_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWwData_Dummy_Train = "Train";
    private static final String KEY_GET_EtWcmWwData_Dummy_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWwData_Dummy_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWwData_Dummy_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWwData_Dummy_Etape = "Etape";
    private static final String KEY_GET_EtWcmWwData_Dummy_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWwData_Dummy_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWwData_Dummy_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWwData_Dummy_Priok = "Priok";
    private static final String KEY_GET_EtWcmWwData_Dummy_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWwData_Dummy_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWwData_Dummy_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWwData_Dummy_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWwData_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Crea = "Crea";
    private static final String KEY_GET_EtWcmWwData_Dummy_Prep = "Prep";
    private static final String KEY_GET_EtWcmWwData_Dummy_Comp = "Comp";
    private static final String KEY_GET_EtWcmWwData_Dummy_Appr = "Appr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Pappr = "Pappr";
    private static final String KEY_GET_EtWcmWwData_Dummy_Action = "Action";
    private static final String KEY_GET_EtWcmWwData_Dummy_Begru = "Begru";
    private static final String KEY_GET_EtWcmWwData_Dummy_Begtx = "Begtx";
    /* EtWcmWwData_Dummy Table and Fields Names */


    /* EtWcmWcagns_IssuePermit_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWcagns_IssuePermit_Dummy = "EtWcmWcagns_IssuePermit_Dummy";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_UUID = "UUID";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wapnr = "Wapnr";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wcitm = "Wcitm";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_CountApp = "CountApp";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_CountCycle = "CountCycle";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverType = "ApproverType";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverID = "ApproverID";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverName = "ApproverName";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gendatum = "Gendatum";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gentime = "Gentime";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Genvname = "Genvname";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Genvdate = "Genvdate";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gentim = "Gentim";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_DyGnert = "DyGnert";
    private static final String KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Action = "Action";
    /* EtWcmWcagns_IssuePermit_Dummy Table and Fields Names */



    /* EtWcmWcagns_Approvals_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWcagns_Approvals_Dummy = "EtWcmWcagns_Approvals_Dummy";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_Wapnr = "Wapnr";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_CountCycle = "CountCycle";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverType = "ApproverType";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverID = "ApproverID";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverName = "ApproverName";
    private static final String KEY_GET_EtWcmWcagns_Approvals_Dummy_Action = "Action";
    /* EtWcmWcagns_Approvals_Dummy Table and Fields Names */



    /* OrdrWcmWAHDR Table and Fields Names */
    private static final String TABLE_GET_OrdrWcmWAHDR = "OrdrWcmWAHDR";
    private static final String KEY_GET_OrdrWcmWAHDR_ID = "id";
    private static final String KEY_GET_OrdrWcmWAHDR_Wapinr = "Wapinr";
    private static final String KEY_GET_OrdrWcmWAHDR_HotworkRel = "HotworkRel";
    private static final String KEY_GET_OrdrWcmWAHDR_Whgt10Mts = "Whgt10Mts";
    private static final String KEY_GET_OrdrWcmWAHDR_VesselNo = "VesselNo";
    private static final String KEY_GET_OrdrWcmWAHDR_Action = "Action";
    /* OrdrWcmWAHDR Table and Fields Names */



    /* OrdrWcmWAHDR_Dummy Table and Fields Names */
    private static final String TABLE_GET_OrdrWcmWAHDR_Dummy = "OrdrWcmWAHDR_Dummy";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_ID = "id";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_Wapinr = "Wapinr";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_HotworkRel = "HotworkRel";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_Whgt10Mts = "Whgt10Mts";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_VesselNo = "VesselNo";
    private static final String KEY_GET_OrdrWcmWAHDR_Dummy_Action = "Action";
    /* OrdrWcmWAHDR Table and Fields Names */



    /* EtWcmWaData_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWaData_Dummy = "EtWcmWaData_Dummy";
    private static final String KEY_GET_EtWcmWaData_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWaData_Dummy_UUID = "UUID";
    private static final String KEY_GET_EtWcmWaData_Dummy_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Objart = "Objart";
    private static final String KEY_GET_EtWcmWaData_Dummy_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWaData_Dummy_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWaData_Dummy_Usage = "Usage";
    private static final String KEY_GET_EtWcmWaData_Dummy_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWaData_Dummy_Train = "Train";
    private static final String KEY_GET_EtWcmWaData_Dummy_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWaData_Dummy_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWaData_Dummy_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWaData_Dummy_Etape = "Etape";
    private static final String KEY_GET_EtWcmWaData_Dummy_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWaData_Dummy_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWaData_Dummy_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWaData_Dummy_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWaData_Dummy_Priok = "Priok";
    private static final String KEY_GET_EtWcmWaData_Dummy_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWaData_Dummy_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWaData_Dummy_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWaData_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWaData_Dummy_Crea = "Crea";
    private static final String KEY_GET_EtWcmWaData_Dummy_Prep = "Prep";
    private static final String KEY_GET_EtWcmWaData_Dummy_Comp = "Comp";
    private static final String KEY_GET_EtWcmWaData_Dummy_Appr = "Appr";
    private static final String KEY_GET_EtWcmWaData_Dummy_Action = "Action";
    private static final String KEY_GET_EtWcmWaData_Dummy_Begru = "Begru";
    private static final String KEY_GET_EtWcmWaData_Dummy_Begtx = "Begtx";
    private static final String KEY_GET_EtWcmWaData_Dummy_Extperiod = "Extperiod";
    /* EtWcmWaData_Dummy Table and Fields Names */



    /* EtWcmWaChkReq_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWaChkReq_Dummy = "EtWcmWaChkReq_Dummy";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Wapinr = "Wapinr";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Wapityp = "Wapityp";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Needid = "Needid";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Value = "Value";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_ChkPointType = "ChkPointType";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Desctext = "Desctext";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Wkgrp = "Wkgrp";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Needgrp = "Needgrp";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Tplnr = "Tplnr";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Equnr = "Equnr";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_NeedidComments = "NeedidComments";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_Person_resp = "Person_resp";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_HotworkRel = "HotworkRel";
    private static final String KEY_GET_EtWcmWaChkReq_Dummy_PartNo = "PartNo";
    /* EtWcmWaChkReq_Dummy Table and Fields Names */



    /* EtWcmWdData_Dummy Table and Fields Names */
    private static final String TABLE_GET_EtWcmWdData_Dummy = "EtWcmWdData_Dummy";
    private static final String KEY_GET_EtWcmWdData_Dummy_ID = "id";
    private static final String KEY_GET_EtWcmWdData_Dummy_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Objart = "Objart";
    private static final String KEY_GET_EtWcmWdData_Dummy_Wcnr = "Wcnr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Iwerk = "Iwerk";
    private static final String KEY_GET_EtWcmWdData_Dummy_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWdData_Dummy_Usage = "Usage";
    private static final String KEY_GET_EtWcmWdData_Dummy_Usagex = "Usagex";
    private static final String KEY_GET_EtWcmWdData_Dummy_Train = "Train";
    private static final String KEY_GET_EtWcmWdData_Dummy_Trainx = "Trainx";
    private static final String KEY_GET_EtWcmWdData_Dummy_Anlzu = "Anlzu";
    private static final String KEY_GET_EtWcmWdData_Dummy_Anlzux = "Anlzux";
    private static final String KEY_GET_EtWcmWdData_Dummy_Etape = "Etape";
    private static final String KEY_GET_EtWcmWdData_Dummy_Etapex = "Etapex";
    private static final String KEY_GET_EtWcmWdData_Dummy_Stxt = "Stxt";
    private static final String KEY_GET_EtWcmWdData_Dummy_Datefr = "Datefr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Timefr = "Timefr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Dateto = "Dateto";
    private static final String KEY_GET_EtWcmWdData_Dummy_Timeto = "Timeto";
    private static final String KEY_GET_EtWcmWdData_Dummy_Priok = "Priok";
    private static final String KEY_GET_EtWcmWdData_Dummy_Priokx = "Priokx";
    private static final String KEY_GET_EtWcmWdData_Dummy_Rctime = "Rctime";
    private static final String KEY_GET_EtWcmWdData_Dummy_Rcunit = "Rcunit";
    private static final String KEY_GET_EtWcmWdData_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Refobj = "Refobj";
    private static final String KEY_GET_EtWcmWdData_Dummy_Crea = "Crea";
    private static final String KEY_GET_EtWcmWdData_Dummy_Prep = "Prep";
    private static final String KEY_GET_EtWcmWdData_Dummy_Comp = "Comp";
    private static final String KEY_GET_EtWcmWdData_Dummy_Appr = "Appr";
    private static final String KEY_GET_EtWcmWdData_Dummy_Action = "Action";
    private static final String KEY_GET_EtWcmWdData_Dummy_Begru = "Begru";
    private static final String KEY_GET_EtWcmWdData_Dummy_Begtx = "Begtx";
    private static final String KEY_GET_EtWcmWdData_Dummy_Pappr = "Pappr";
    private static final String KEY_GET_EtWcmWdData_Dummy_ZzIniKey = "ZzIniKey";
    private static final String KEY_GET_EtWcmWdData_Dummy_ZzMlbKey = "ZzMlbKey";
    private static final String KEY_GET_EtWcmWdData_Dummy_ZzIssKey = "ZzIssKey";
    /* EtWcmWdData_Dummy Table and Fields Names */



    /* EtOrderCatalog Table and Fields Names */
    private static final String TABLE_GET_EtOrderCatalog = "EtOrderCatalog";
    private static final String KEY_GET_EtOrderCatalog_ID = "id";
    private static final String KEY_GET_EtOrderCatalog_Aufnr = "Aufnr";
    private static final String KEY_GET_EtOrderCatalog_Wapinr = "Wapinr";
    private static final String KEY_GET_EtOrderCatalog_Objnr = "Objnr";
    private static final String KEY_GET_EtOrderCatalog_Counter = "Counter";
    private static final String KEY_GET_EtOrderCatalog_Sfield = "Sfield";
    private static final String KEY_GET_EtOrderCatalog_Cattyp = "Cattyp";
    private static final String KEY_GET_EtOrderCatalog_Usagex = "Codegrp";
    private static final String KEY_GET_EtOrderCatalog_ShortText = "ShortText";
    private static final String KEY_GET_EtOrderCatalog_Code = "Code";
    private static final String KEY_GET_EtOrderCatalog_Description = "Description";
    private static final String KEY_GET_EtOrderCatalog_Stxt = "Stxt";
    private static final String KEY_GET_EtOrderCatalog_Ltxtflg = "Ltxtflg";
    private static final String KEY_GET_EtOrderCatalog_Chkbox = "Chkbox";
    private static final String KEY_GET_EtOrderCatalog_Sign = "Sign";
    private static final String KEY_GET_EtOrderCatalog_Val = "Val";
    private static final String KEY_GET_EtOrderCatalog_Crname = "Crname";
    private static final String KEY_GET_EtOrderCatalog_Crdate = "Crdate";
    private static final String KEY_GET_EtOrderCatalog_Crtime = "Crtime";
    private static final String KEY_GET_EtOrderCatalog_Action = "Action";
    /* EtOrderCatalog Table and Fields Names */



    /* EtOrderCatalog Table and Fields Names */
    private static final String TABLE_GET_EtOrderCatalog_Dummy = "EtOrderCatalog_Dummy";
    private static final String KEY_GET_EtOrderCatalog_Dummy_ID = "id";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Aufnr = "Aufnr";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Wapinr = "Wapinr";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Objnr = "Objnr";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Counter = "Counter";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Sfield = "Sfield";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Cattyp = "Cattyp";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Usagex = "Codegrp";
    private static final String KEY_GET_EtOrderCatalog_Dummy_ShortText = "ShortText";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Code = "Code";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Description = "Description";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Stxt = "Stxt";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Ltxtflg = "Ltxtflg";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Chkbox = "Chkbox";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Sign = "Sign";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Val = "Val";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Crname = "Crname";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Crdate = "Crdate";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Crtime = "Crtime";
    private static final String KEY_GET_EtOrderCatalog_Dummy_Action = "Action";
    /* EtOrderCatalog_Dummy Table and Fields Names */


    public static String Get_DORD_Data(Context context, String transmit_type, String order_number)
    {
        try
        {
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD"))
            {
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrderCatalog);
                String CREATE_TABLE_GET_EtOrderCatalog = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtOrderCatalog + ""
                        + "( "
                        + KEY_GET_EtOrderCatalog_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrderCatalog_Aufnr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Wapinr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Objnr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Counter + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Sfield + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Cattyp + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Usagex + " TEXT,"
                        + KEY_GET_EtOrderCatalog_ShortText + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Code + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Description + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Stxt + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Ltxtflg + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Chkbox + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Sign + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Val + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Crname + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Crdate + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Crtime + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrderCatalog);


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtOrderCatalog_Dummy);
                String CREATE_TABLE_GET_EtOrderCatalog_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtOrderCatalog_Dummy + ""
                        + "( "
                        + KEY_GET_EtOrderCatalog_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtOrderCatalog_Dummy_Aufnr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Wapinr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Counter + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Sfield + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Cattyp + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Usagex + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_ShortText + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Code + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Description + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Stxt + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Ltxtflg + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Chkbox + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Sign + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Val + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Crname + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Crdate + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Crtime + " TEXT,"
                        + KEY_GET_EtOrderCatalog_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtOrderCatalog_Dummy);


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


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataTagtext);
                String CREATE_TABLE_EtWcmWdDataTagtext = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataTagtext + ""
                        + "( "
                        + KEY_EtWcmWdDataTagtext_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWdDataTagtext_Aufnr + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Wcnr + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Objtype + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_FormatCol + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_TextLine + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWdDataTagtext);



                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataTagtext_Dummy);
                String CREATE_TABLE_EtWcmWdDataTagtext_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataTagtext_Dummy + ""
                        + "( "
                        + KEY_EtWcmWdDataTagtext_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWdDataTagtext_Dummy_Aufnr + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Dummy_Wcnr + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Dummy_Objtype + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Dummy_FormatCol + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Dummy_TextLine + " TEXT,"
                        + KEY_EtWcmWdDataTagtext_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWdDataTagtext_Dummy);


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_OrdrWcmWAHDR);
                String CREATE_TABLE_GET_OrdrWcmWAHDR = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_OrdrWcmWAHDR + ""
                        + "( "
                        + KEY_GET_OrdrWcmWAHDR_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_OrdrWcmWAHDR_Wapinr + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_HotworkRel + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Whgt10Mts + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_VesselNo + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_OrdrWcmWAHDR);



                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_OrdrWcmWAHDR_Dummy);
                String CREATE_TABLE_GET_OrdrWcmWAHDR_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_OrdrWcmWAHDR_Dummy + ""
                        + "( "
                        + KEY_GET_OrdrWcmWAHDR_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_OrdrWcmWAHDR_Dummy_Wapinr + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Dummy_HotworkRel + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Dummy_Whgt10Mts + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Dummy_VesselNo + " TEXT,"
                        + KEY_GET_OrdrWcmWAHDR_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_OrdrWcmWAHDR_Dummy);


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataUntagtext);
                String CREATE_TABLE_EtWcmWdDataUntagtext = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataUntagtext + ""
                        + "( "
                        + KEY_EtWcmWdDataUntagtext_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWdDataUntagtext_Aufnr + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Wcnr + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Objtype + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_FormatCol + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_TextLine + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWdDataUntagtext);



                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdDataUntagtext_Dummy);
                String CREATE_TABLE_EtWcmWdDataUntagtext_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdDataUntagtext_Dummy + ""
                        + "( "
                        + KEY_EtWcmWdDataUntagtext_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_Aufnr + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_Wcnr + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_Objtype + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_FormatCol + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_TextLine + " TEXT,"
                        + KEY_EtWcmWdDataUntagtext_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtWcmWdDataUntagtext_Dummy);


		        /* Orders TKConfirm Attachments */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Orders_Attachments);
                String CREATE_TABLE_Orders_Attachments = "CREATE TABLE IF NOT EXISTS " + TABLE_Orders_Attachments + ""
                        + "( "
                        + KEY_Orders_Attachments_ID + " INTEGER PRIMARY KEY,"
                        + KEY_Orders_Attachments_UUID + " TEXT,"
                        + KEY_Orders_Attachments_OBJECTID + " TEXT,"
                        + KEY_Orders_Attachments_OBJECTTYPE + " TEXT,"
                        + KEY_Orders_Attachments_file_path + " TEXT,"
                        + KEY_Orders_Attachments_jsaid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_Orders_Attachments);
		        /* Orders TKConfirm Attachments */


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
                        + KEY_DUE_ORDERS_EtOrderHeader_IdealHours + " TEXT"
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
                        + KEY_DUE_ORDERS_EtOrderOperations_ActualCond + " TEXT"
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
                        + KEY_DUE_ORDERS_EtDocs_Objtype + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_DUE_ORDERS_EtDocs);
		        /* Creating DUE_ORDERS_EtDocs Table with Fields */



                /* Creating DUE_ORDERS_WA_EtDocs_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_ORDERS_WA_EtDocs_Dummy);
                String CREATE_TABLE_GET_DUE_ORDERS_WA_EtDocs_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_ORDERS_WA_EtDocs_Dummy + ""
                        + "( "
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Zobjid + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Zdoctype + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_ZdoctypeItem + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Filename + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Filetype + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Fsize + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Content + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_DocId + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_DocType + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Objtype + " TEXT,"
                        + KEY_DUE_ORDERS_WA_EtDocs_Dummy_Status + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_DUE_ORDERS_WA_EtDocs_Dummy);
                /* Creating DUE_ORDERS_WA_EtDocs_Dummy Table with Fields */



		        /* Creating CREATE_TABLE_GET_EtWcmWwData Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWwData);
                String CREATE_TABLE_GET_EtWcmWwData = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWwData + ""
                        + "( "
                        + KEY_GET_EtWcmWwData_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWwData_UUID + " TEXT,"
                        + KEY_GET_EtWcmWwData_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Objart + " TEXT,"
                        + KEY_GET_EtWcmWwData_Wapnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWwData_Usage + " TEXT,"
                        + KEY_GET_EtWcmWwData_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWwData_Train + " TEXT,"
                        + KEY_GET_EtWcmWwData_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWwData_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWwData_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWwData_Etape + " TEXT,"
                        + KEY_GET_EtWcmWwData_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWwData_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWwData_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWwData_Priok + " TEXT,"
                        + KEY_GET_EtWcmWwData_Priokx + " TEXT,"
                        + KEY_GET_EtWcmWwData_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWwData_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dateto + " TEXT, "
                        + KEY_GET_EtWcmWwData_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWwData_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Crea + " TEXT,"
                        + KEY_GET_EtWcmWwData_Prep + " TEXT,"
                        + KEY_GET_EtWcmWwData_Comp + " TEXT,"
                        + KEY_GET_EtWcmWwData_Appr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Pappr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Action + " TEXT,"
                        + KEY_GET_EtWcmWwData_Begru + " TEXT,"
                        + KEY_GET_EtWcmWwData_Begtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWwData);
		        /* Creating CREATE_TABLE_GET_EtWcmWwData Table with Fields */



                /* Creating CREATE_TABLE_GET_EtWcmWwData_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWwData_Dummy);
                String CREATE_TABLE_GET_EtWcmWwData_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWwData_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWwData_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWwData_Dummy_UUID + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Objart + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Wapnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Usage + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Train + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Etape + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Priok + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Priokx + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Dateto + " TEXT, "
                        + KEY_GET_EtWcmWwData_Dummy_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Crea + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Prep + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Comp + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Appr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Pappr + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Action + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Begru + " TEXT,"
                        + KEY_GET_EtWcmWwData_Dummy_Begtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWwData_Dummy);
                /* Creating CREATE_TABLE_GET_EtWcmWwData_Dummy Table with Fields */




		        /* Creating EtWcmWaData Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWaData);
                String CREATE_TABLE_GET_EtWcmWaDataa = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWaData + ""
                        + "( "
                        + KEY_GET_EtWcmWaData_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWaData_UUID + " TEXT,"
                        + KEY_GET_EtWcmWaData_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Objart + " TEXT,"
                        + KEY_GET_EtWcmWaData_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWaData_Objtyp + " TEXT,"
                        + KEY_GET_EtWcmWaData_Usage + " TEXT,"
                        + KEY_GET_EtWcmWaData_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWaData_Train + " TEXT,"
                        + KEY_GET_EtWcmWaData_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWaData_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWaData_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWaData_Etape + " TEXT,"
                        + KEY_GET_EtWcmWaData_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWaData_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWaData_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dateto + " TEXT,"
                        + KEY_GET_EtWcmWaData_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWaData_Priok + " TEXT,"
                        + KEY_GET_EtWcmWaData_Priokx + " TEXT, "
                        + KEY_GET_EtWcmWaData_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWaData_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWaData_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWaData_Crea + " TEXT,"
                        + KEY_GET_EtWcmWaData_Prep + " TEXT,"
                        + KEY_GET_EtWcmWaData_Comp + " TEXT,"
                        + KEY_GET_EtWcmWaData_Appr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Action + " TEXT,"
                        + KEY_GET_EtWcmWaData_Begru + " TEXT,"
                        + KEY_GET_EtWcmWaData_Begtx + " TEXT,"
                        + KEY_GET_EtWcmWaData_Extperiod + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWaDataa);
		        /* Creating EtWcmWaData Table with Fields */




                /* Creating EtWcmWaData_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWaData_Dummy);
                String CREATE_TABLE_GET_EtWcmWaData_Dummya = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWaData_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWaData_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWaData_Dummy_UUID + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Objart + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Objtyp + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Usage + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Train + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Etape + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Dateto + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Priok + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Priokx + " TEXT, "
                        + KEY_GET_EtWcmWaData_Dummy_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Crea + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Prep + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Comp + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Appr + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Action + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Begru + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Begtx + " TEXT,"
                        + KEY_GET_EtWcmWaData_Dummy_Extperiod + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWaData_Dummya);
                /* Creating EtWcmWaData_Dummy Table with Fields */


		        /* Creating EtWcmWaChkReq Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWaChkReq);
                String CREATE_TABLE_GET_EtWcmWaChkReq = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWaChkReq + ""
                        + "( "
                        + KEY_GET_EtWcmWaChkReq_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWaChkReq_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Wapityp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Needid + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Value + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_ChkPointType + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Desctext + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Wkgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Needgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Tplnr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Equnr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_NeedidComments + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Person_resp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_HotworkRel + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_PartNo + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWaChkReq);
		        /* Creating EtWcmWaChkReq Table with Fields */



                /* Creating EtWcmWaChkReq_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWaChkReq_Dummy);
                String CREATE_TABLE_GET_EtWcmWaChkReq_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWaChkReq_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWaChkReq_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Wapityp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Needid + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Value + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_ChkPointType + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Desctext + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Wkgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Needgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Tplnr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Equnr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_NeedidComments + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_Person_resp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_HotworkRel + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Dummy_PartNo + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWaChkReq_Dummy);
                /* Creating EtWcmWaChkReq_Dummy Table with Fields */



		        /* Creating EtWcmWdData Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdData);
                String CREATE_TABLE_GET_EtWcmWdData = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdData + ""
                        + "( "
                        + KEY_GET_EtWcmWdData_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWdData_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Objart + " TEXT,"
                        + KEY_GET_EtWcmWdData_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWdData_Objtyp + " TEXT,"
                        + KEY_GET_EtWcmWdData_Usage + " TEXT,"
                        + KEY_GET_EtWcmWdData_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWdData_Train + " TEXT,"
                        + KEY_GET_EtWcmWdData_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWdData_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWdData_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWdData_Etape + " TEXT,"
                        + KEY_GET_EtWcmWdData_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWdData_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWdData_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dateto + " TEXT,"
                        + KEY_GET_EtWcmWdData_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWdData_Priok + " TEXT,"
                        + KEY_GET_EtWcmWdData_Priokx + " TEXT, "
                        + KEY_GET_EtWcmWdData_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWdData_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWdData_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWdData_Crea + " TEXT,"
                        + KEY_GET_EtWcmWdData_Prep + " TEXT,"
                        + KEY_GET_EtWcmWdData_Comp + " TEXT,"
                        + KEY_GET_EtWcmWdData_Appr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Action + " TEXT,"
                        + KEY_GET_EtWcmWdData_Begru + " TEXT,"
                        + KEY_GET_EtWcmWdData_Begtx + " TEXT,"
                        + KEY_GET_EtWcmWdData_Pappr + " TEXT,"
                        + KEY_GET_EtWcmWdData_ZzIniKey + " TEXT,"
                        + KEY_GET_EtWcmWdData_ZzMlbKey + " TEXT,"
                        + KEY_GET_EtWcmWdData_ZzIssKey + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdData);
		        /* Creating EtWcmWdData Table with Fields */



                /* Creating EtWcmWdData_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdData_Dummy);
                String CREATE_TABLE_GET_EtWcmWdData_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdData_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWdData_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWdData_Dummy_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Objart + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Iwerk + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Objtyp + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Usage + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Usagex + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Train + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Trainx + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Anlzu + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Anlzux + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Etape + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Etapex + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Datefr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Timefr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Dateto + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Timeto + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Priok + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Priokx + " TEXT, "
                        + KEY_GET_EtWcmWdData_Dummy_Rctime + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Rcunit + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Crea + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Prep + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Comp + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Appr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Action + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Begru + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Begtx + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_Pappr + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_ZzIniKey + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_ZzMlbKey + " TEXT,"
                        + KEY_GET_EtWcmWdData_Dummy_ZzIssKey + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdData_Dummy);
                /* Creating EtWcmWdData_Dummy Table with Fields */


		        /* Creating EtWcmWdItemData Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdItemData);
                String CREATE_TABLE_GET_EtWcmWdItemData = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdItemData + ""
                        + "( "
                        + KEY_GET_EtWcmWdItemData_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWdItemData_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Wcitm + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Itmtyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Seq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Pred + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Succ + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Ccobj + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Cctyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tggrp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgstep + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgproc + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgtyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgseq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgtxt + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Unstep + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Unproc + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Untyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Unseq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Untxt + " TEXT, "
                        + KEY_GET_EtWcmWdItemData_Phblflg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Phbltyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Phblnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgflg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgform + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Tgnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Unform + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Unnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Control + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Location + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Action + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Btg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Etg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Bug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Eug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Btug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Etug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Button + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_CurrStatus + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdItemData);
		        /* Creating EtWcmWdItemData Table with Fields */



                /* Creating EtWcmWdItemData_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWdItemData_Dummy);
                String CREATE_TABLE_GET_EtWcmWdItemData_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWdItemData_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWdItemData_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Wcitm + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Itmtyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Seq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Pred + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Succ + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Ccobj + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Cctyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Stxt + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tggrp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgstep + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgproc + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgtyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgseq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgtxt + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Unstep + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Unproc + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Untyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Unseq + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Untxt + " TEXT, "
                        + KEY_GET_EtWcmWdItemData_Dummy_Phblflg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Phbltyp + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Phblnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgflg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgform + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Tgnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Unform + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Unnr + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Control + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Location + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Refobj + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Action + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Btg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Etg + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Bug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Eug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Btug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Etug + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_Button + " TEXT,"
                        + KEY_GET_EtWcmWdItemData_Dummy_CurrStatus + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdItemData_Dummy);
                /* Creating EtWcmWdItemData_Dummy Table with Fields */



		        /* Creating EtWcmWcagns Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWcagns);
                String CREATE_TABLE_GET_EtWcmWcagns = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWcagns + ""
                        + "( "
                        + KEY_GET_EtWcmWcagns_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWcagns_UUID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Wapnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Wcitm + " TEXT,"
                        + KEY_GET_EtWcmWcagns_CountApp + " TEXT,"
                        + KEY_GET_EtWcmWcagns_CountCycle + " TEXT,"
                        + KEY_GET_EtWcmWcagns_ApproverType + " TEXT,"
                        + KEY_GET_EtWcmWcagns_ApproverID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_ApproverName + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gendatum + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gentime + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Genvname + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Genvdate + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gentim + " TEXT,"
                        + KEY_GET_EtWcmWcagns_DyGnert + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWcagns);
		        /* Creating EtWcmWcagns Table with Fields */




                /* Creating EtWcmWcagns_IssuePermit_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWcagns_IssuePermit_Dummy);
                String CREATE_TABLE_GET_EtWcmWcagns_IssuePermit_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWcagns_IssuePermit_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_UUID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wapnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wapinr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wcnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Wcitm + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_CountApp + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_CountCycle + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverType + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_ApproverName + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gendatum + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gentime + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Genvname + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Genvdate + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Gentim + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_DyGnert + " TEXT,"
                        + KEY_GET_EtWcmWcagns_IssuePermit_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWcagns_IssuePermit_Dummy);
                /* Creating EtWcmWcagns_IssuePermit_Dummy Table with Fields */



                /* Creating EtWcmWcagns_Approvals_Dummy Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWcagns_Approvals_Dummy);
                String CREATE_TABLE_GET_EtWcmWcagns_Approvals_Dummy = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWcagns_Approvals_Dummy + ""
                        + "( "
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_Wapnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_CountCycle + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverType + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_ApproverName + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Approvals_Dummy_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWcagns_Approvals_Dummy);
                /* Creating EtWcmWcagns_Approvals_Dummy Table with Fields */



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


                /* EtOrderCost Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderCost);
                String CREATE_TABLE_DUE_ORDERS_EtOrderCost = "CREATE TABLE IF NOT EXISTS " + TABLE_DUE_ORDERS_EtOrderCost + ""
                        + "( "
                        + KEY_DUE_ORDERS_EtOrderCost_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_ORDERS_EtOrderCost_UUID + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Aufnr + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Text + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Estcost + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Plancost + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Actcost + " TEXT,"
                        + KEY_DUE_ORDERS_EtOrderCost_Currency + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_DUE_ORDERS_EtOrderCost);
                /* EtOrderCost Table and Fields Names */

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
                App_db.execSQL("delete from EtWcmWwData");
                App_db.execSQL("delete from EtWcmWwData_Dummy");
                App_db.execSQL("delete from EtWcmWaData");
                App_db.execSQL("delete from EtWcmWaData_Dummy");
                App_db.execSQL("delete from OrdrWcmWAHDR");
                App_db.execSQL("delete from OrdrWcmWAHDR_Dummy");
                App_db.execSQL("delete from EtWcmWaChkReq_Dummy");
                App_db.execSQL("delete from EtWcmWaChkReq");
                App_db.execSQL("delete from EtWcmWdData");
                App_db.execSQL("delete from EtWcmWdData_Dummy");
                App_db.execSQL("delete from EtWcmWdItemData");
                App_db.execSQL("delete from EtWcmWdItemData_Dummy");
                App_db.execSQL("delete from EtWcmWcagns");
                App_db.execSQL("delete from EtWcmWcagns_IssuePermit_Dummy");
                App_db.execSQL("delete from EtWcmWcagns_Approvals_Dummy");
                App_db.execSQL("delete from EtWcmWdData_Dummy");
                App_db.execSQL("delete from EtWcmWdItemData_Dummy");
                App_db.execSQL("delete from EtOrderComponents");
                App_db.execSQL("delete from EtOrderCost");
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
            modelLoginRest.setIv_transmit_type("LOAD");
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
