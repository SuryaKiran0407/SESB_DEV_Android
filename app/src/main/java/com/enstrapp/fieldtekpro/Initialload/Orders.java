package com.enstrapp.fieldtekpro.Initialload;

import android.content.Context;
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

public class Orders {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty checkempty = new Check_Empty();


    /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderComponents_FIELDS = "DUE_ORDERS_EtOrderComponents_FIELDS";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_id = "id";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_uuid = "UUID";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Aufnr = "Aufnr";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Zdoctype = "Zdoctype";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_ZdoctypeItem = "ZdoctypeItem";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Tabname = "Tabname";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Fieldname = "Fieldname";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Value = "Value";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Flabel = "Flabel";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Sequence = "Sequence";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Length = "Length";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_Datatype = "Datatype";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_OperationID = "OperationID";
    private static final String DUE_ORDERS_EtOrderComponents_FIELDS_PartID = "PartID";
    /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */


    /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */
    private static final String TABLE_DUE_ORDERS_EtOrderOperations_FIELDS = "DUE_ORDERS_EtOrderOperations_FIELDS";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_id = "id";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_uuid = "UUID";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Aufnr = "Aufnr";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Zdoctype = "Zdoctype";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_ZdoctypeItem = "ZdoctypeItem";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Tabname = "Tabname";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Fieldname = "Fieldname";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Value = "Value";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Flabel = "Flabel";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Sequence = "Sequence";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Length = "Length";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Datatype = "Datatype";
    private static final String DUE_ORDERS_EtOrderOperations_FIELDS_Operationid = "OperationID";
    /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */


    /* Header Custom_Info Table and Fields Names */
    private static final String TABLE_Custom_Info_Fields = "EtOrderHeader_CustomInfo";
    private static final String KEY_Custom_Info_id = "id";
    private static final String KEY_Custom_Info_uuid = "UUID";
    private static final String KEY_Custom_Info_Aufnr = "Aufnr";
    private static final String KEY_Custom_Info_Zdoctype = "Zdoctype";
    private static final String KEY_Custom_Info_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_Custom_Info_Tabname = "Tabname";
    private static final String KEY_Custom_Info_Fieldname = "Fieldname";
    private static final String KEY_Custom_Info_Value = "Value";
    private static final String KEY_Custom_Info_Flabel = "Flabel";
    private static final String KEY_Custom_Info_Sequence = "Sequence";
    private static final String KEY_Custom_Info_Length = "Length";
    private static final String KEY_Custom_Info_Datatype = "Datatype";
    /* Header Custom_Info Table and Fields Names */


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
    private static final String KEY_DUE_ORDERS_EtDocs_Contentx = "Contentx";
	/* GET_DUE_ORDERS_EtDocs Table and Fields Names */

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
    /* EtWcmWdItemData Table and Fields Names */

    /* EtWcmWcagns Table and Fields Names */
    private static final String TABLE_GET_EtWcmWcagns = "EtWcmWcagns";
    private static final String KEY_GET_EtWcmWcagns_ID = "id";
    private static final String KEY_GET_EtWcmWcagns_UUID = "UUID";
    private static final String KEY_GET_EtWcmWcagns_Aufnr = "Aufnr";
    private static final String KEY_GET_EtWcmWcagns_Objnr = "Objnr";
    private static final String KEY_GET_EtWcmWcagns_Counter = "Counter";
    private static final String KEY_GET_EtWcmWcagns_Objart = "Objart";
    private static final String KEY_GET_EtWcmWcagns_Objtyp = "Objtyp";
    private static final String KEY_GET_EtWcmWcagns_Pmsog = "Pmsog";
    private static final String KEY_GET_EtWcmWcagns_Gntxt = "Gntxt";
    private static final String KEY_GET_EtWcmWcagns_Geniakt = "Geniakt";
    private static final String KEY_GET_EtWcmWcagns_Genvname = "Genvname";
    private static final String KEY_GET_EtWcmWcagns_Action = "Action";
    private static final String KEY_GET_EtWcmWcagns_Werks = "Werks";
    private static final String KEY_GET_EtWcmWcagns_Crname = "Crname";
    private static final String KEY_GET_EtWcmWcagns_Hilvl = "Hilvl";
    private static final String KEY_GET_EtWcmWcagns_Procflg = "Procflg";
    private static final String KEY_GET_EtWcmWcagns_Direction = "Direction";
    private static final String KEY_GET_EtWcmWcagns_Copyflg = "Copyflg";
    private static final String KEY_GET_EtWcmWcagns_Mandflg = "Mandflg";
    private static final String KEY_GET_EtWcmWcagns_Deacflg = "Deacflg";
    private static final String KEY_GET_EtWcmWcagns_Status = "Status";
    private static final String KEY_GET_EtWcmWcagns_Asgnflg = "Asgnflg";
    private static final String KEY_GET_EtWcmWcagns_Autoflg = "Autoflg";
    private static final String KEY_GET_EtWcmWcagns_Agent = "Agent";
    private static final String KEY_GET_EtWcmWcagns_Valflg = "Valflg";
    private static final String KEY_GET_EtWcmWcagns_Wcmuse = "Wcmuse";
    private static final String KEY_GET_EtWcmWcagns_Gendatum = "Gendatum";
    private static final String KEY_GET_EtWcmWcagns_Gentime = "Gentime";
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
	/* EtOrderComponents and Fields Names */


    public static String Get_DORD_Data(Context context, String transmit_type, String order_number)
    {
        try
        {
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD"))
            {
                /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DUE_ORDERS_EtOrderComponents_FIELDS);
                String CREATE_DUE_ORDERS_EtOrderComponents_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_DUE_ORDERS_EtOrderComponents_FIELDS+ ""
                        + "( "
                        + DUE_ORDERS_EtOrderComponents_FIELDS_id+ " INTEGER PRIMARY KEY,"//0
                        + DUE_ORDERS_EtOrderComponents_FIELDS_uuid+ " TEXT,"//1
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Aufnr+ " TEXT,"//2
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Zdoctype+ " TEXT,"//3
                        + DUE_ORDERS_EtOrderComponents_FIELDS_ZdoctypeItem+ " TEXT,"//4
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Tabname+ " TEXT,"//5
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Fieldname+ " TEXT,"//6
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Value+ " TEXT,"//7
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Flabel+ " TEXT,"//8
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Sequence+ " TEXT,"//9
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Length+ " TEXT,"//10
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Datatype+ " TEXT,"//11
                        + DUE_ORDERS_EtOrderComponents_FIELDS_OperationID+ " TEXT,"//12
                        + DUE_ORDERS_EtOrderComponents_FIELDS_PartID+ " TEXT"//13
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderComponents_FIELDS_TABLE);
		        /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */


                /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DUE_ORDERS_EtOrderOperations_FIELDS);
                String CREATE_DUE_ORDERS_EtOrderOperations_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_DUE_ORDERS_EtOrderOperations_FIELDS+ ""
                        + "( "
                        + DUE_ORDERS_EtOrderOperations_FIELDS_id+ " INTEGER PRIMARY KEY,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_uuid+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Aufnr+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Zdoctype+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_ZdoctypeItem+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Tabname+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Fieldname+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Value+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Flabel+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Sequence+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Length+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Datatype+ " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Operationid+ " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderOperations_FIELDS_TABLE);
		        /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */

                /* Creating Header Custom_Info Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Custom_Info_Fields);
                String Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_Custom_Info_Fields+ ""
                        + "( "
                        + KEY_Custom_Info_id+ " INTEGER PRIMARY KEY,"
                        + KEY_Custom_Info_uuid+ " TEXT,"
                        + KEY_Custom_Info_Aufnr+ " TEXT,"
                        + KEY_Custom_Info_Zdoctype+ " TEXT,"
                        + KEY_Custom_Info_ZdoctypeItem+ " TEXT,"
                        + KEY_Custom_Info_Tabname+ " TEXT,"
                        + KEY_Custom_Info_Fieldname+ " TEXT,"
                        + KEY_Custom_Info_Value+ " TEXT,"
                        + KEY_Custom_Info_Flabel+ " TEXT,"
                        + KEY_Custom_Info_Sequence+ " TEXT,"
                        + KEY_Custom_Info_Length+ " TEXT,"
                        + KEY_Custom_Info_Datatype+ " TEXT"
                        + ")";
                App_db.execSQL(Custom_Info_Fields_TABLE);
		        /* Creating Header Custom_Info Table with Fields */


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
                        + KEY_DUE_ORDERS_EtOrderHeader_Revnr + " TEXT"
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
                        + KEY_DUE_ORDERS_EtOrderOperations_Action + " TEXT"
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
                        + KEY_DUE_ORDERS_EtDocs_Contentx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_DUE_ORDERS_EtDocs);
		        /* Creating DUE_ORDERS_EtDocs Table with Fields */

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
                        + KEY_GET_EtWcmWaChkReq_Tplnr + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Wkgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Needgrp + " TEXT,"
                        + KEY_GET_EtWcmWaChkReq_Equnr + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWaChkReq);
		        /* Creating EtWcmWaChkReq Table with Fields */

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
                        + KEY_GET_EtWcmWdData_Begtx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdData);
		        /* Creating EtWcmWdData Table with Fields */

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
                        + KEY_GET_EtWcmWdItemData_Eug + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWdItemData);
		        /* Creating EtWcmWdItemData Table with Fields */

		        /* Creating EtWcmWcagns Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_EtWcmWcagns);
                String CREATE_TABLE_GET_EtWcmWcagns = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_EtWcmWcagns + ""
                        + "( "
                        + KEY_GET_EtWcmWcagns_ID + " INTEGER PRIMARY KEY,"
                        + KEY_GET_EtWcmWcagns_UUID + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Aufnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Objnr + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Counter + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Objart + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Objtyp + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Pmsog + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gntxt + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Geniakt + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Genvname + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Action + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Werks + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Crname + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Hilvl + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Procflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Direction + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Copyflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Mandflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Deacflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Status + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Asgnflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Autoflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Agent + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Valflg + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Wcmuse + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gendatum + " TEXT,"
                        + KEY_GET_EtWcmWcagns_Gentime + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_GET_EtWcmWcagns);
		        /* Creating EtWcmWcagns Table with Fields */

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
                        + KEY_DUE_ORDERS_EtOrderComponentsp_Ablad + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderComponents_TABLE);
		        /* Creating EtOrderComponents Table with Fields */
            } else if (transmit_type.equalsIgnoreCase("Single_Ord")) {
            } else {
                App_db.execSQL("delete from Orders_TKConfirm");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader");
                App_db.execSQL("delete from EtOrderHeader_CustomInfo");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations_FIELDS");
                App_db.execSQL("delete from DUE_ORDERS_Longtext");
                App_db.execSQL("delete from EtOrderOlist");
                App_db.execSQL("delete from EtOrderStatus");
                App_db.execSQL("delete from DUE_ORDERS_EtDocs");
                App_db.execSQL("delete from EtWcmWwData");
                App_db.execSQL("delete from EtWcmWaData");
                App_db.execSQL("delete from EtWcmWaChkReq");
                App_db.execSQL("delete from EtWcmWdData");
                App_db.execSQL("delete from EtWcmWdItemData");
                App_db.execSQL("delete from EtWcmWcagns");
                App_db.execSQL("delete from EtOrderComponents");
                App_db.execSQL("delete from DUE_ORDERS_EtOrderComponents_FIELDS");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = context.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
		    /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"C2", "DO", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
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
            Map<String, String> map = new HashMap<>();
            map.put("IvUser", username.toUpperCase().toString());
            map.put("Muser", username.toUpperCase().toString());
            map.put("Deviceid", device_id);
            map.put("Devicesno", device_serial_number);
            map.put("Udid", device_uuid);
            map.put("Operation", "DUORD");
            map.put("IvAufnr", order_number);
            map.put("IvTransmitType", transmit_type);
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.getORDERDetails(url_link, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    orders_uuid_list.clear();

                    /*Reading Response Data and Parsing to Serializable*/
                    Orders_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/

                    /*Converting GSON Response to JSON Data for Parsing*/
                    String response_data = new Gson().toJson(rs.getD().getResults());
                    /*Converting GSON Response to JSON Data for Parsing*/

                    /*Converting Response JSON Data to JSONArray for Reading*/
                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/

                    /*Reading Data by using FOR Loop*/
                    for (int i = 0; i < response_data_jsonArray.length(); i++) {
                        /*Reading Data by using FOR Loop*/
                        JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());


                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/
                        if (jsonObject.has("EtOrderHeader")) {
                            try {
                                String EtOrderHeader_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderHeader().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderHeader_response_data);
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    String Aufnr = jsonArray.getJSONObject(j).optString("Aufnr");
                                    HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                                    UUID uniqueKey = UUID.randomUUID();
                                    uuid_hashmap.put("UUID", uniqueKey.toString());
                                    uuid_hashmap.put("Aufnr", checkempty.check_empty(Aufnr));
                                    orders_uuid_list.add(uuid_hashmap);
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader UUID*/

                        App_db.beginTransaction();

                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/
                        if (jsonObject.has("EtOrderHeader")) {
                            try {
                                String EtOrderHeader_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderHeader().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderHeader_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtOrderHeader_sql = "Insert into DUE_ORDERS_EtOrderHeader (UUID, Aufnr, Auart, Ktext, Ilart, Ernam, Erdat, Priok, Equnr, Strno, TplnrInt, Bautl, Gltrp, Gstrp, Docs, Permits, Altitude, Latitude, Longitude, Qmnum, Closed, Completed, Ingrp, Arbpl, Werks, Bemot, Aueru, Auarttext, Qmartx, Qmtxt, Pltxt, Eqktx, Priokx , Ilatx, Plantname, Wkctrname, Ingrpname, Maktx, Xstatus, Usr01, Usr02, Usr03, Usr04, Usr05, Kokrs, Kostl, Anlzu, Anlzux, Ausvn, Ausbs, Auswk, Qmnam, ParnrVw, NameVw, Posid, Revnr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderHeader_statement = App_db.compileStatement(EtOrderHeader_sql);
                                    EtOrderHeader_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtOrderHeader_statement.bindString(1, uuid);
                                            EtOrderHeader_statement.bindString(2, Aufnr);
                                            EtOrderHeader_statement.bindString(3, jsonArray.getJSONObject(j).optString("Auart"));
                                            EtOrderHeader_statement.bindString(4, jsonArray.getJSONObject(j).optString("Ktext"));
                                            EtOrderHeader_statement.bindString(5, jsonArray.getJSONObject(j).optString("Ilart"));
                                            EtOrderHeader_statement.bindString(6, jsonArray.getJSONObject(j).optString("Ernam"));
                                            EtOrderHeader_statement.bindString(7, jsonArray.getJSONObject(j).optString("Erdat"));
                                            EtOrderHeader_statement.bindString(8, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtOrderHeader_statement.bindString(9, jsonArray.getJSONObject(j).optString("Equnr"));
                                            EtOrderHeader_statement.bindString(10, jsonArray.getJSONObject(j).optString("Strno"));
                                            EtOrderHeader_statement.bindString(11, jsonArray.getJSONObject(j).optString("TplnrInt"));
                                            EtOrderHeader_statement.bindString(12, jsonArray.getJSONObject(j).optString("Bautl"));
                                            EtOrderHeader_statement.bindString(13, jsonArray.getJSONObject(j).optString("Gltrp"));
                                            EtOrderHeader_statement.bindString(14, jsonArray.getJSONObject(j).optString("Gstrp"));
                                            EtOrderHeader_statement.bindString(15, jsonArray.getJSONObject(j).optString("Docs"));
                                            EtOrderHeader_statement.bindString(16, jsonArray.getJSONObject(j).optString("Permits"));
                                            EtOrderHeader_statement.bindString(17, jsonArray.getJSONObject(j).optString("Altitude"));
                                            EtOrderHeader_statement.bindString(18, jsonArray.getJSONObject(j).optString("Latitude"));
                                            EtOrderHeader_statement.bindString(19, jsonArray.getJSONObject(j).optString("Longitude"));
                                            EtOrderHeader_statement.bindString(20, jsonArray.getJSONObject(j).optString("Qmnum"));
                                            EtOrderHeader_statement.bindString(21, jsonArray.getJSONObject(j).optString("Closed"));
                                            EtOrderHeader_statement.bindString(22, jsonArray.getJSONObject(j).optString("Completed"));
                                            EtOrderHeader_statement.bindString(23, jsonArray.getJSONObject(j).optString("Ingrp"));
                                            EtOrderHeader_statement.bindString(24, jsonArray.getJSONObject(j).optString("Arbpl"));
                                            EtOrderHeader_statement.bindString(25, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtOrderHeader_statement.bindString(26, jsonArray.getJSONObject(j).optString("Bemot"));
                                            EtOrderHeader_statement.bindString(27, jsonArray.getJSONObject(j).optString("Aueru"));
                                            EtOrderHeader_statement.bindString(28, jsonArray.getJSONObject(j).optString("Auarttext"));
                                            EtOrderHeader_statement.bindString(29, jsonArray.getJSONObject(j).optString("Qmartx"));
                                            EtOrderHeader_statement.bindString(30, jsonArray.getJSONObject(j).optString("Qmtxt"));
                                            EtOrderHeader_statement.bindString(31, jsonArray.getJSONObject(j).optString("Pltxt"));
                                            EtOrderHeader_statement.bindString(32, jsonArray.getJSONObject(j).optString("Eqktx"));
                                            EtOrderHeader_statement.bindString(33, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtOrderHeader_statement.bindString(34, jsonArray.getJSONObject(j).optString("Ilatx"));
                                            EtOrderHeader_statement.bindString(35, jsonArray.getJSONObject(j).optString("Plantname"));
                                            EtOrderHeader_statement.bindString(36, jsonArray.getJSONObject(j).optString("Wkctrname"));
                                            EtOrderHeader_statement.bindString(37, jsonArray.getJSONObject(j).optString("Ingrpname"));
                                            EtOrderHeader_statement.bindString(38, jsonArray.getJSONObject(j).optString("Maktx"));
                                            EtOrderHeader_statement.bindString(39, jsonArray.getJSONObject(j).optString("Xstatus"));
                                            EtOrderHeader_statement.bindString(40, jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtOrderHeader_statement.bindString(41, jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtOrderHeader_statement.bindString(42, jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtOrderHeader_statement.bindString(43, jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtOrderHeader_statement.bindString(44, jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtOrderHeader_statement.bindString(45, jsonArray.getJSONObject(j).optString("Kokrs"));
                                            EtOrderHeader_statement.bindString(46, jsonArray.getJSONObject(j).optString("Kostl"));
                                            EtOrderHeader_statement.bindString(47, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtOrderHeader_statement.bindString(48, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtOrderHeader_statement.bindString(49, jsonArray.getJSONObject(j).optString("Ausvn"));
                                            EtOrderHeader_statement.bindString(50, jsonArray.getJSONObject(j).optString("Ausbs"));
                                            EtOrderHeader_statement.bindString(51, jsonArray.getJSONObject(j).optString("Auswk"));
                                            EtOrderHeader_statement.bindString(52, jsonArray.getJSONObject(j).optString("Qmnam"));
                                            EtOrderHeader_statement.bindString(53, jsonArray.getJSONObject(j).optString("ParnrVw"));
                                            EtOrderHeader_statement.bindString(54, jsonArray.getJSONObject(j).optString("NameVw"));
                                            EtOrderHeader_statement.bindString(55, jsonArray.getJSONObject(j).optString("Posid"));
                                            EtOrderHeader_statement.bindString(56, jsonArray.getJSONObject(j).optString("Revnr"));
                                            EtOrderHeader_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtOrderHeaderFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql1 = "Insert into EtOrderHeader_CustomInfo (UUID,Aufnr,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,Aufnr);
                                                        statement1.bindString(3,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderHeader*/


                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/
                        if (jsonObject.has("EtOrderOperations")) {
                            try {
                                String EtOrderOperations_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderOperations().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderOperations_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtOrderOperations_sql = "Insert into DUE_ORDERS_EtOrderOperations (UUID,Aufnr,Vornr,Uvorn,Ltxa1,Arbpl,Werks,Steus,Larnt,Dauno,Daune,Fsavd,Ssedd,Pernr,Asnum,Plnty,Plnal,Plnnr,Rueck,Aueru,ArbplText,WerksText,SteusText,LarntText,Usr01,Usr02,Usr03,Usr04,Usr05,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderOperations_statement = App_db.compileStatement(EtOrderOperations_sql);
                                    EtOrderOperations_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtOrderOperations_statement.bindString(1, uuid);
                                            EtOrderOperations_statement.bindString(2, Aufnr);
                                            EtOrderOperations_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                            EtOrderOperations_statement.bindString(4, jsonArray.getJSONObject(j).optString("Uvorn"));
                                            EtOrderOperations_statement.bindString(5, jsonArray.getJSONObject(j).optString("Ltxa1"));
                                            EtOrderOperations_statement.bindString(6, jsonArray.getJSONObject(j).optString("Arbpl"));
                                            EtOrderOperations_statement.bindString(7, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtOrderOperations_statement.bindString(8, jsonArray.getJSONObject(j).optString("Steus"));
                                            EtOrderOperations_statement.bindString(9, jsonArray.getJSONObject(j).optString("Larnt"));
                                            EtOrderOperations_statement.bindString(10, jsonArray.getJSONObject(j).optString("Dauno"));
                                            EtOrderOperations_statement.bindString(11, jsonArray.getJSONObject(j).optString("Daune"));
                                            EtOrderOperations_statement.bindString(12, jsonArray.getJSONObject(j).optString("Fsavd"));
                                            EtOrderOperations_statement.bindString(13, jsonArray.getJSONObject(j).optString("Ssedd"));
                                            EtOrderOperations_statement.bindString(14, jsonArray.getJSONObject(j).optString("Pernr"));
                                            EtOrderOperations_statement.bindString(15, jsonArray.getJSONObject(j).optString("Asnum"));
                                            EtOrderOperations_statement.bindString(16, jsonArray.getJSONObject(j).optString("Plnty"));
                                            EtOrderOperations_statement.bindString(17, jsonArray.getJSONObject(j).optString("Plnal"));
                                            EtOrderOperations_statement.bindString(18, jsonArray.getJSONObject(j).optString("Plnnr"));
                                            EtOrderOperations_statement.bindString(19, jsonArray.getJSONObject(j).optString("Rueck"));
                                            EtOrderOperations_statement.bindString(20, jsonArray.getJSONObject(j).optString("Aueru"));
                                            EtOrderOperations_statement.bindString(21, jsonArray.getJSONObject(j).optString("ArbplText"));
                                            EtOrderOperations_statement.bindString(22, jsonArray.getJSONObject(j).optString("WerksText"));
                                            EtOrderOperations_statement.bindString(23, jsonArray.getJSONObject(j).optString("SteusText"));
                                            EtOrderOperations_statement.bindString(24, jsonArray.getJSONObject(j).optString("LarntText"));
                                            EtOrderOperations_statement.bindString(25, jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtOrderOperations_statement.bindString(26, jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtOrderOperations_statement.bindString(27, jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtOrderOperations_statement.bindString(28, jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtOrderOperations_statement.bindString(29, jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtOrderOperations_statement.bindString(30, jsonArray.getJSONObject(j).optString("Action"));
                                            EtOrderOperations_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtOrderOperationsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql1 = "Insert into DUE_ORDERS_EtOrderOperations_FIELDS (UUID, Aufnr, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype, OperationID) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,Aufnr);
                                                        statement1.bindString(3,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.bindString(12,jsonArray.getJSONObject(j).optString("Vornr"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderOperations*/


                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/
                        if (jsonObject.has("EtOrderLongtext")) {
                            try {
                                String EtOrderLongtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderLongtext().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderLongtext_response_data);
                                if (jsonArray.length() > 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine, Tdid) values(?,?,?,?,?);";
                                            SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                                            EtOrderLongtext_statement.clearBindings();
                                            EtOrderLongtext_statement.bindString(1, uuid);
                                            EtOrderLongtext_statement.bindString(2, Aufnr);
                                            EtOrderLongtext_statement.bindString(3, jsonArray.getJSONObject(j).optString("Activity"));
                                            EtOrderLongtext_statement.bindString(4, jsonArray.getJSONObject(j).optString("TextLine"));
                                            EtOrderLongtext_statement.bindString(5, jsonArray.getJSONObject(j).optString("Tdid"));
                                            EtOrderLongtext_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderLongtext*/


                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/
                        if (jsonObject.has("EtOrderOlist")) {
                            try {
                                String EtOrderOlist_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderOlist().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderOlist_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtOrderOlist_sql = "Insert into EtOrderOlist (UUID, Aufnr, Obknr, Obzae, Qmnum, Equnr, Strno, Tplnr, Bautl, Qmtxt, Pltxt, Eqktx, Maktx, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderOlist_statement = App_db.compileStatement(EtOrderOlist_sql);
                                    EtOrderOlist_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtOrderOlist_statement.bindString(1, uuid);
                                            EtOrderOlist_statement.bindString(2, Aufnr);
                                            EtOrderOlist_statement.bindString(3, jsonArray.getJSONObject(j).optString("Obknr"));
                                            EtOrderOlist_statement.bindString(4, jsonArray.getJSONObject(j).optString("Obzae"));
                                            EtOrderOlist_statement.bindString(5, jsonArray.getJSONObject(j).optString("Qmnum"));
                                            EtOrderOlist_statement.bindString(6, jsonArray.getJSONObject(j).optString("Equnr"));
                                            EtOrderOlist_statement.bindString(7, jsonArray.getJSONObject(j).optString("Strno"));
                                            EtOrderOlist_statement.bindString(8, jsonArray.getJSONObject(j).optString("Tplnr"));
                                            EtOrderOlist_statement.bindString(9, jsonArray.getJSONObject(j).optString("Bautl"));
                                            EtOrderOlist_statement.bindString(10, jsonArray.getJSONObject(j).optString("Qmtxt"));
                                            EtOrderOlist_statement.bindString(11, jsonArray.getJSONObject(j).optString("Pltxt"));
                                            EtOrderOlist_statement.bindString(12, jsonArray.getJSONObject(j).optString("Eqktx"));
                                            EtOrderOlist_statement.bindString(13, jsonArray.getJSONObject(j).optString("Maktx"));
                                            EtOrderOlist_statement.bindString(14, jsonArray.getJSONObject(j).optString("Action"));
                                            EtOrderOlist_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderOlist*/


                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/
                        if (jsonObject.has("EtOrderStatus")) {
                            try {
                                String EtOrderStatus_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderStatus().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderStatus_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtOrderStatus_sql = "Insert into EtOrderStatus (UUID,Aufnr, Vornr, Objnr, Stsma, Inist, Stonr, Hsonr, Nsonr,Stat, Act, Txt04, Txt30, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderStatus_statement = App_db.compileStatement(EtOrderStatus_sql);
                                    EtOrderStatus_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtOrderStatus_statement.bindString(1, uuid);
                                            EtOrderStatus_statement.bindString(2, Aufnr);
                                            EtOrderStatus_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                            EtOrderStatus_statement.bindString(4, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtOrderStatus_statement.bindString(5, jsonArray.getJSONObject(j).optString("Stsma"));
                                            EtOrderStatus_statement.bindString(6, jsonArray.getJSONObject(j).optString("Inist"));
                                            EtOrderStatus_statement.bindString(7, jsonArray.getJSONObject(j).optString("Stonr"));
                                            EtOrderStatus_statement.bindString(8, jsonArray.getJSONObject(j).optString("Hsonr"));
                                            EtOrderStatus_statement.bindString(9, jsonArray.getJSONObject(j).optString("Nsonr"));
                                            EtOrderStatus_statement.bindString(10, jsonArray.getJSONObject(j).optString("Stat"));
                                            EtOrderStatus_statement.bindString(11, jsonArray.getJSONObject(j).optString("Act"));
                                            EtOrderStatus_statement.bindString(12, jsonArray.getJSONObject(j).optString("Txt04"));
                                            EtOrderStatus_statement.bindString(13, jsonArray.getJSONObject(j).optString("Txt30"));
                                            EtOrderStatus_statement.bindString(14, jsonArray.getJSONObject(j).optString("Action"));
                                            EtOrderStatus_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderStatus*/


                        /*Reading and Inserting Data into Database Table for EtDocs*/
                        if (jsonObject.has("EtDocs")) {
                            try {
                                String EtDocs_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtDocs().getResults());
                                JSONArray jsonArray = new JSONArray(EtDocs_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtDocs_sql = "Insert into DUE_ORDERS_EtDocs (UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Contentx) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                    EtDocs_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtDocs_statement.bindString(1, "");
                                        EtDocs_statement.bindString(2, jsonArray.getJSONObject(j).optString("Zobjid"));
                                        EtDocs_statement.bindString(3, jsonArray.getJSONObject(j).optString("Zdoctype"));
                                        EtDocs_statement.bindString(4, jsonArray.getJSONObject(j).optString("ZdoctypeItem"));
                                        EtDocs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Filename"));
                                        EtDocs_statement.bindString(6, jsonArray.getJSONObject(j).optString("Filetype"));
                                        EtDocs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Fsize"));
                                        EtDocs_statement.bindString(8, jsonArray.getJSONObject(j).optString("Content"));
                                        EtDocs_statement.bindString(9, jsonArray.getJSONObject(j).optString("DocId"));
                                        EtDocs_statement.bindString(10, jsonArray.getJSONObject(j).optString("DocType"));
                                        EtDocs_statement.bindString(11, jsonArray.getJSONObject(j).optString("Objtype"));
                                        EtDocs_statement.bindString(12, jsonArray.getJSONObject(j).optString("Contentx"));
                                        EtDocs_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtDocs*/


                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/
                        if (jsonObject.has("EtWcmWwData")) {
                            try {
                                String EtWcmWwData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWwData().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWwData_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtWcmWwData_sql = "Insert into EtWcmWwData (UUID, Aufnr, Objart, Wapnr, Iwerk, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Rctime, Rcunit, Priok, Priokx, Stxt, Datefr, Timefr, Dateto, Timeto, Objnr, Crea, Prep, Comp, Appr, Pappr, Action, Begru, Begtx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWwData_statement = App_db.compileStatement(EtWcmWwData_sql);
                                    EtWcmWwData_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtWcmWwData_statement.bindString(1, uuid);
                                            EtWcmWwData_statement.bindString(2, Aufnr);
                                            EtWcmWwData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWwData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapnr"));
                                            EtWcmWwData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWwData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Usage"));
                                            EtWcmWwData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usagex"));
                                            EtWcmWwData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Train"));
                                            EtWcmWwData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Trainx"));
                                            EtWcmWwData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtWcmWwData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtWcmWwData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Etape"));
                                            EtWcmWwData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etapex"));
                                            EtWcmWwData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Rctime"));
                                            EtWcmWwData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Rcunit"));
                                            EtWcmWwData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtWcmWwData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtWcmWwData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWwData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Datefr"));
                                            EtWcmWwData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Timefr"));
                                            EtWcmWwData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Dateto"));
                                            EtWcmWwData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Timeto"));
                                            EtWcmWwData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWwData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Crea"));
                                            EtWcmWwData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Prep"));
                                            EtWcmWwData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Comp"));
                                            EtWcmWwData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Appr"));
                                            EtWcmWwData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Pappr"));
                                            EtWcmWwData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWwData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Begru"));
                                            EtWcmWwData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begtx"));
                                            EtWcmWwData_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWwData*/


                         /*Reading and Inserting Data into Database Table for EtWcmWaData*/
                        if (jsonObject.has("EtWcmWaData")) {
                            try {
                                String EtWcmWaData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWaData().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWaData_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtWcmWaData_sql = "Insert into EtWcmWaData (UUID, Aufnr, Objart, Wapinr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx, Extperiod) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWaData_statement = App_db.compileStatement(EtWcmWaData_sql);
                                    EtWcmWaData_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtWcmWaData_statement.bindString(1, uuid);
                                            EtWcmWaData_statement.bindString(2, Aufnr);
                                            EtWcmWaData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWaData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Wapinr"));
                                            EtWcmWaData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Iwerk"));
                                            EtWcmWaData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWaData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usage"));
                                            EtWcmWaData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Usagex"));
                                            EtWcmWaData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Train"));
                                            EtWcmWaData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Trainx"));
                                            EtWcmWaData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzu"));
                                            EtWcmWaData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Anlzux"));
                                            EtWcmWaData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etape"));
                                            EtWcmWaData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Etapex"));
                                            EtWcmWaData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Stxt"));
                                            EtWcmWaData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Datefr"));
                                            EtWcmWaData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Timefr"));
                                            EtWcmWaData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Dateto"));
                                            EtWcmWaData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Timeto"));
                                            EtWcmWaData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Priok"));
                                            EtWcmWaData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Priokx"));
                                            EtWcmWaData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Rctime"));
                                            EtWcmWaData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Rcunit"));
                                            EtWcmWaData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWaData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Refobj"));
                                            EtWcmWaData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Crea"));
                                            EtWcmWaData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Prep"));
                                            EtWcmWaData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Comp"));
                                            EtWcmWaData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Appr"));
                                            EtWcmWaData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWaData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begru"));
                                            EtWcmWaData_statement.bindString(32, jsonArray.getJSONObject(j).optString("Begtx"));
                                            EtWcmWaData_statement.bindString(33, jsonArray.getJSONObject(j).optString("Extperiod"));
                                            EtWcmWaData_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWaData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/
                        if (jsonObject.has("EtWcmWaChkReq")) {
                            try {
                                String EtWcmWaChkReq_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWaChkReq().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWaChkReq_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtWcmWaChkReq_sql = "Insert into EtWcmWaChkReq (Wapinr, Wapityp, Needid, Value, ChkPointType, Desctext, Tplnr, Wkgrp, Needgrp, Equnr) values(?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWaChkReq_statement = App_db.compileStatement(EtWcmWaChkReq_sql);
                                    EtWcmWaChkReq_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtWcmWaChkReq_statement.bindString(1, jsonArray.getJSONObject(j).optString("Wapinr"));
                                        EtWcmWaChkReq_statement.bindString(2, jsonArray.getJSONObject(j).optString("Wapityp"));
                                        EtWcmWaChkReq_statement.bindString(5, jsonArray.getJSONObject(j).optString("ChkPointType"));
                                        if (jsonArray.getJSONObject(j).optString("ChkPointType").equalsIgnoreCase("W"))
                                            EtWcmWaChkReq_statement.bindString(3, jsonArray.getJSONObject(j).optString("Wkid"));
                                        else
                                            EtWcmWaChkReq_statement.bindString(3, jsonArray.getJSONObject(j).optString("Needid"));
                                        EtWcmWaChkReq_statement.bindString(4, jsonArray.getJSONObject(j).optString("Value"));
                                        EtWcmWaChkReq_statement.bindString(6, jsonArray.getJSONObject(j).optString("Desctext"));
                                        EtWcmWaChkReq_statement.bindString(7, jsonArray.getJSONObject(j).optString("Tplnr"));
                                        EtWcmWaChkReq_statement.bindString(8, jsonArray.getJSONObject(j).optString("Wkgrp"));
                                        EtWcmWaChkReq_statement.bindString(9, jsonArray.getJSONObject(j).optString("Needgrp"));
                                        EtWcmWaChkReq_statement.bindString(10, jsonArray.getJSONObject(j).optString("Equnr"));
                                        EtWcmWaChkReq_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWaChkReq*/


                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/
                        if (jsonObject.has("EtWcmWdData")) {
                            try {
                                String EtWcmWdData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWdData_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtWcmWdData_sql = "Insert into EtWcmWdData (Aufnr, Objart, Wcnr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWdData_statement = App_db.compileStatement(EtWcmWdData_sql);
                                    EtWcmWdData_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        EtWcmWdData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Aufnr"));
                                        EtWcmWdData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Objart"));
                                        EtWcmWdData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Wcnr"));
                                        EtWcmWdData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Iwerk"));
                                        EtWcmWdData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objtyp"));
                                        EtWcmWdData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Usage"));
                                        EtWcmWdData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Usagex"));
                                        EtWcmWdData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Train"));
                                        EtWcmWdData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Trainx"));
                                        EtWcmWdData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Anlzu"));
                                        EtWcmWdData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Anlzux"));
                                        EtWcmWdData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Etape"));
                                        EtWcmWdData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Etapex"));
                                        EtWcmWdData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Stxt"));
                                        EtWcmWdData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Datefr"));
                                        EtWcmWdData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Timefr"));
                                        EtWcmWdData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Dateto"));
                                        EtWcmWdData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Timeto"));
                                        EtWcmWdData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Priok"));
                                        EtWcmWdData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Priokx"));
                                        EtWcmWdData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Rctime"));
                                        EtWcmWdData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Rcunit"));
                                        EtWcmWdData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Objnr"));
                                        EtWcmWdData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Refobj"));
                                        EtWcmWdData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Crea"));
                                        EtWcmWdData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Prep"));
                                        EtWcmWdData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Comp"));
                                        EtWcmWdData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Appr"));
                                        EtWcmWdData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Action"));
                                        EtWcmWdData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Begru"));
                                        EtWcmWdData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Begtx"));
                                        if (jsonArray.getJSONObject(j).has("EtWcmWdDataTagtext")) {
                                            try {
                                                String EtWcmWdDataTagtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults().get(j).getEtWcmWdDataTagtext().getResults());
                                                JSONArray jsonArray1 = new JSONArray(EtWcmWdDataTagtext_response_data);
                                                if (jsonArray1.length() > 0) {
                                                    String EtWcmWdDataTagtext_sql = "Insert into EtWcmWdDataTagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                    SQLiteStatement EtWcmWdDataTagtext_statement = App_db.compileStatement(EtWcmWdDataTagtext_sql);
                                                    EtWcmWdDataTagtext_statement.clearBindings();
                                                    for (int k = 0; k < jsonArray1.length(); k++) {
                                                        EtWcmWdDataTagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                        EtWcmWdDataTagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                        EtWcmWdDataTagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                        EtWcmWdDataTagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                        EtWcmWdDataTagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                        EtWcmWdDataTagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                        EtWcmWdDataTagtext_statement.execute();
                                                    }
                                                }
                                            } catch (Exception e) {
                                            }
                                        }
                                        if (jsonArray.getJSONObject(j).has("EtWcmWdDataUntagtext")) {
                                            try {
                                                String EtWcmWdDataUntagtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdData().getResults().get(j).getEtWcmWdDataUntagtext().getResults());
                                                JSONArray jsonArray1 = new JSONArray(EtWcmWdDataUntagtext_response_data);
                                                if (jsonArray1.length() > 0) {
                                                    String EtWcmWdDataUntagtext_sql = "Insert into EtWcmWdDataUntagtext (Aufnr, Wcnr, Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                    SQLiteStatement EtWcmWdDataUntagtext_statement = App_db.compileStatement(EtWcmWdDataUntagtext_sql);
                                                    EtWcmWdDataUntagtext_statement.clearBindings();
                                                    for (int k = 0; k < jsonArray1.length(); k++) {
                                                        EtWcmWdDataUntagtext_statement.bindString(1, jsonArray1.getJSONObject(k).optString("Aufnr"));
                                                        EtWcmWdDataUntagtext_statement.bindString(2, jsonArray1.getJSONObject(k).optString("Wcnr"));
                                                        EtWcmWdDataUntagtext_statement.bindString(3, jsonArray1.getJSONObject(k).optString("Objtype"));
                                                        EtWcmWdDataUntagtext_statement.bindString(4, jsonArray1.getJSONObject(k).optString("FormatCol"));
                                                        EtWcmWdDataUntagtext_statement.bindString(5, jsonArray1.getJSONObject(k).optString("TextLine"));
                                                        EtWcmWdDataUntagtext_statement.bindString(6, jsonArray1.getJSONObject(k).optString("Action"));
                                                        EtWcmWdDataUntagtext_statement.execute();
                                                    }
                                                }
                                            } catch (Exception e) {
                                            }
                                        }
                                        EtWcmWdData_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWdData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/
                        if (jsonObject.has("EtWcmWdItemData")) {
                            try {
                                String EtWcmWdItemData_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWdItemData().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWdItemData_response_data);
                                if (jsonArray.length() > 0) {
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String EtWcmWdItemData_sql = "Insert into EtWcmWdItemData (Wcnr, Wcitm, Objnr, Itmtyp, Seq, Pred, Succ, Ccobj, Cctyp, Stxt, Tggrp, Tgstep, Tgproc, Tgtyp, Tgseq, Tgtxt, Unstep, Unproc, Untyp, Unseq, Untxt, Phblflg, Phbltyp, Phblnr, Tgflg, Tgform, Tgnr, Unform, Unnr, Control, Location, Refobj, Action, Btg, Etg, Bug, Eug) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtWcmWdItemData_statement = App_db.compileStatement(EtWcmWdItemData_sql);
                                        EtWcmWdItemData_statement.clearBindings();
                                        EtWcmWdItemData_statement.bindString(1, jsonArray.getJSONObject(j).optString("Wcnr"));
                                        EtWcmWdItemData_statement.bindString(2, jsonArray.getJSONObject(j).optString("Wcitm"));
                                        EtWcmWdItemData_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                        EtWcmWdItemData_statement.bindString(4, jsonArray.getJSONObject(j).optString("Itmtyp"));
                                        EtWcmWdItemData_statement.bindString(5, jsonArray.getJSONObject(j).optString("Seq"));
                                        EtWcmWdItemData_statement.bindString(6, jsonArray.getJSONObject(j).optString("Pred"));
                                        EtWcmWdItemData_statement.bindString(7, jsonArray.getJSONObject(j).optString("Succ"));
                                        EtWcmWdItemData_statement.bindString(8, jsonArray.getJSONObject(j).optString("Ccobj"));
                                        EtWcmWdItemData_statement.bindString(9, jsonArray.getJSONObject(j).optString("Cctyp"));
                                        EtWcmWdItemData_statement.bindString(10, jsonArray.getJSONObject(j).optString("Stxt"));
                                        EtWcmWdItemData_statement.bindString(11, jsonArray.getJSONObject(j).optString("Tggrp"));
                                        EtWcmWdItemData_statement.bindString(12, jsonArray.getJSONObject(j).optString("Tgstep"));
                                        EtWcmWdItemData_statement.bindString(13, jsonArray.getJSONObject(j).optString("Tgproc"));
                                        EtWcmWdItemData_statement.bindString(14, jsonArray.getJSONObject(j).optString("Tgtyp"));
                                        EtWcmWdItemData_statement.bindString(15, jsonArray.getJSONObject(j).optString("Tgseq"));
                                        EtWcmWdItemData_statement.bindString(16, jsonArray.getJSONObject(j).optString("Tgtxt"));
                                        EtWcmWdItemData_statement.bindString(17, jsonArray.getJSONObject(j).optString("Unstep"));
                                        EtWcmWdItemData_statement.bindString(18, jsonArray.getJSONObject(j).optString("Unproc"));
                                        EtWcmWdItemData_statement.bindString(19, jsonArray.getJSONObject(j).optString("Untyp"));
                                        EtWcmWdItemData_statement.bindString(20, jsonArray.getJSONObject(j).optString("Unseq"));
                                        EtWcmWdItemData_statement.bindString(21, jsonArray.getJSONObject(j).optString("Untxt"));
                                        EtWcmWdItemData_statement.bindString(22, jsonArray.getJSONObject(j).optString("Phblflg"));
                                        EtWcmWdItemData_statement.bindString(23, jsonArray.getJSONObject(j).optString("Phbltyp"));
                                        EtWcmWdItemData_statement.bindString(24, jsonArray.getJSONObject(j).optString("Phblnr"));
                                        EtWcmWdItemData_statement.bindString(25, jsonArray.getJSONObject(j).optString("Tgflg"));
                                        EtWcmWdItemData_statement.bindString(26, jsonArray.getJSONObject(j).optString("Tgform"));
                                        EtWcmWdItemData_statement.bindString(27, jsonArray.getJSONObject(j).optString("Tgnr"));
                                        EtWcmWdItemData_statement.bindString(28, jsonArray.getJSONObject(j).optString("Unform"));
                                        EtWcmWdItemData_statement.bindString(29, jsonArray.getJSONObject(j).optString("Unnr"));
                                        EtWcmWdItemData_statement.bindString(30, jsonArray.getJSONObject(j).optString("Control"));
                                        EtWcmWdItemData_statement.bindString(31, jsonArray.getJSONObject(j).optString("Location"));
                                        EtWcmWdItemData_statement.bindString(32, jsonArray.getJSONObject(j).optString("Refobj"));
                                        EtWcmWdItemData_statement.bindString(33, jsonArray.getJSONObject(j).optString("Action"));
                                        EtWcmWdItemData_statement.bindString(34, jsonArray.getJSONObject(j).optString("Btg"));
                                        EtWcmWdItemData_statement.bindString(35, jsonArray.getJSONObject(j).optString("Etg"));
                                        EtWcmWdItemData_statement.bindString(36, jsonArray.getJSONObject(j).optString("Bug"));
                                        EtWcmWdItemData_statement.bindString(37, jsonArray.getJSONObject(j).optString("Eug"));
                                        EtWcmWdItemData_statement.execute();
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWdItemData*/


                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/
                        if (jsonObject.has("EtWcmWcagns")) {
                            try {
                                String EtWcmWcagns_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtWcmWcagns().getResults());
                                JSONArray jsonArray = new JSONArray(EtWcmWcagns_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtWcmWcagns_sql = "Insert into EtWcmWcagns (UUID, Aufnr, Objnr, Counter, Objart, Objtyp, Pmsog, Gntxt, Geniakt, Genvname, Action, Werks, Crname, Hilvl, Procflg, Direction, Copyflg, Mandflg, Deacflg, Status, Asgnflg, Autoflg, Agent, Valflg, Wcmuse, Gendatum, Gentime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWcagns_statement = App_db.compileStatement(EtWcmWcagns_sql);
                                    EtWcmWcagns_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtWcmWcagns_statement.bindString(1, uuid);
                                            EtWcmWcagns_statement.bindString(2, Aufnr);
                                            EtWcmWcagns_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objnr"));
                                            EtWcmWcagns_statement.bindString(4, jsonArray.getJSONObject(j).optString("Counter"));
                                            EtWcmWcagns_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objart"));
                                            EtWcmWcagns_statement.bindString(6, jsonArray.getJSONObject(j).optString("Objtyp"));
                                            EtWcmWcagns_statement.bindString(7, jsonArray.getJSONObject(j).optString("Pmsog"));
                                            EtWcmWcagns_statement.bindString(8, jsonArray.getJSONObject(j).optString("Gntxt"));
                                            EtWcmWcagns_statement.bindString(9, jsonArray.getJSONObject(j).optString("Geniakt"));
                                            EtWcmWcagns_statement.bindString(10, jsonArray.getJSONObject(j).optString("Genvname"));
                                            EtWcmWcagns_statement.bindString(11, jsonArray.getJSONObject(j).optString("Action"));
                                            EtWcmWcagns_statement.bindString(12, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtWcmWcagns_statement.bindString(13, jsonArray.getJSONObject(j).optString("Crname"));
                                            EtWcmWcagns_statement.bindString(14, jsonArray.getJSONObject(j).optString("Hilvl"));
                                            EtWcmWcagns_statement.bindString(15, jsonArray.getJSONObject(j).optString("Procflg"));
                                            EtWcmWcagns_statement.bindString(16, jsonArray.getJSONObject(j).optString("Direction"));
                                            EtWcmWcagns_statement.bindString(17, jsonArray.getJSONObject(j).optString("Copyflg"));
                                            EtWcmWcagns_statement.bindString(18, jsonArray.getJSONObject(j).optString("Mandflg"));
                                            EtWcmWcagns_statement.bindString(19, jsonArray.getJSONObject(j).optString("Deacflg"));
                                            EtWcmWcagns_statement.bindString(20, jsonArray.getJSONObject(j).optString("Status"));
                                            EtWcmWcagns_statement.bindString(21, jsonArray.getJSONObject(j).optString("Asgnflg"));
                                            EtWcmWcagns_statement.bindString(22, jsonArray.getJSONObject(j).optString("Autoflg"));
                                            EtWcmWcagns_statement.bindString(23, jsonArray.getJSONObject(j).optString("Agent"));
                                            EtWcmWcagns_statement.bindString(24, jsonArray.getJSONObject(j).optString("Valflg"));
                                            EtWcmWcagns_statement.bindString(25, jsonArray.getJSONObject(j).optString("Wcmuse"));
                                            EtWcmWcagns_statement.bindString(26, jsonArray.getJSONObject(j).optString("Gendatum"));
                                            EtWcmWcagns_statement.bindString(27, jsonArray.getJSONObject(j).optString("Gentime"));
                                            EtWcmWcagns_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtWcmWcagns*/


                        /*Reading and Inserting Data into Database Table for EtOrderComponents*/
                        if (jsonObject.has("EtOrderComponents")) {
                            try {
                                String EtOrderComponents_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtOrderComponents().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderComponents_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtOrderComponents_sql = "Insert into EtOrderComponents (UUID, Aufnr, Vornr, Uvorn, Rsnum, Rspos, Matnr, Werks, Lgort, Posnr, Bdmng, Meins, Postp, MatnrText, WerksText, LgortText, PostpText, Usr01, Usr02, Usr03, Usr04, Usr05, Action, Wempf, Ablad) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtOrderComponents_statement = App_db.compileStatement(EtOrderComponents_sql);
                                    EtOrderComponents_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtOrderComponents_statement.bindString(1, uuid);
                                            EtOrderComponents_statement.bindString(2, Aufnr);
                                            EtOrderComponents_statement.bindString(3, jsonArray.getJSONObject(j).optString("Vornr"));
                                            EtOrderComponents_statement.bindString(4, jsonArray.getJSONObject(j).optString("Uvorn"));
                                            EtOrderComponents_statement.bindString(5, jsonArray.getJSONObject(j).optString("Rsnum"));
                                            EtOrderComponents_statement.bindString(6, jsonArray.getJSONObject(j).optString("Rspos"));
                                            EtOrderComponents_statement.bindString(7, jsonArray.getJSONObject(j).optString("Matnr"));
                                            EtOrderComponents_statement.bindString(8, jsonArray.getJSONObject(j).optString("Werks"));
                                            EtOrderComponents_statement.bindString(9, jsonArray.getJSONObject(j).optString("Lgort"));
                                            EtOrderComponents_statement.bindString(10, jsonArray.getJSONObject(j).optString("Posnr"));
                                            EtOrderComponents_statement.bindString(11, jsonArray.getJSONObject(j).optString("Bdmng"));
                                            EtOrderComponents_statement.bindString(12, jsonArray.getJSONObject(j).optString("Meins"));
                                            EtOrderComponents_statement.bindString(13, jsonArray.getJSONObject(j).optString("Postp"));
                                            EtOrderComponents_statement.bindString(14, jsonArray.getJSONObject(j).optString("MatnrText"));
                                            EtOrderComponents_statement.bindString(15, jsonArray.getJSONObject(j).optString("WerksText"));
                                            EtOrderComponents_statement.bindString(16, jsonArray.getJSONObject(j).optString("LgortText"));
                                            EtOrderComponents_statement.bindString(17, jsonArray.getJSONObject(j).optString("PostpText"));
                                            EtOrderComponents_statement.bindString(18, jsonArray.getJSONObject(j).optString("Usr01"));
                                            EtOrderComponents_statement.bindString(19, jsonArray.getJSONObject(j).optString("Usr02"));
                                            EtOrderComponents_statement.bindString(20, jsonArray.getJSONObject(j).optString("Usr03"));
                                            EtOrderComponents_statement.bindString(21, jsonArray.getJSONObject(j).optString("Usr04"));
                                            EtOrderComponents_statement.bindString(22, jsonArray.getJSONObject(j).optString("Usr05"));
                                            EtOrderComponents_statement.bindString(23, jsonArray.getJSONObject(j).optString("Action"));
                                            EtOrderComponents_statement.bindString(24, jsonArray.getJSONObject(j).optString("Wempf"));
                                            EtOrderComponents_statement.bindString(25, jsonArray.getJSONObject(j).optString("Ablad"));
                                            EtOrderComponents_statement.execute();


                                            try
                                            {
                                                String Fields = jsonArray.getJSONObject(j).optString("EtOrderComponentsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if(EtNotifHeader_Fields_jsonArray.length() > 0)
                                                {
                                                    String sql1 = "Insert into DUE_ORDERS_EtOrderComponents_FIELDS (UUID, Aufnr, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype, OperationID, PartID) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for(int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++)
                                                    {
                                                        statement1.bindString(1,uuid);
                                                        statement1.bindString(2,Aufnr);
                                                        statement1.bindString(3,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11,EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.bindString(12,jsonArray.getJSONObject(j).optString("Vornr"));
                                                        statement1.bindString(13,jsonArray.getJSONObject(j).optString("Posnr"));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                            }


                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtOrderComponents*/

                        /*Reading and Inserting Data into Database Table for EtImrg*/
                        if (jsonObject.has("EtImrg")) {
                            try {
                                String EtOrderComponents_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtImrg().getResults());
                                JSONArray jsonArray = new JSONArray(EtOrderComponents_response_data);
                                if (jsonArray.length() > 0) {
                                    String EtImrg_sql = "Insert into Orders_EtImrg (UUID,Qmnum, Aufnr, Vornr, Mdocm, Point, Mpobj, Mpobt, Psort,Pttxt, Atinn, Idate, Itime, Mdtxt,Readr,Atbez,Msehi,Msehl,Readc,Desic,Prest,Docaf, Codct, Codgr, Vlcod, Action, Equnr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtImrg_statement = App_db.compileStatement(EtImrg_sql);
                                    EtImrg_statement.clearBindings();
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        String uuid = "";
                                        String Aufnr = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Aufnr"));
                                        for (int k = 0; k < orders_uuid_list.size(); k++) {
                                            String old_aufnr = orders_uuid_list.get(k).get("Aufnr");
                                            if (Aufnr.equals(old_aufnr)) {
                                                uuid = orders_uuid_list.get(k).get("UUID");
                                            }
                                        }
                                        if (uuid != null && !uuid.equals("")) {
                                            EtImrg_statement.bindString(1, uuid);
                                            EtImrg_statement.bindString(2, jsonArray.getJSONObject(j).optString("Qmnum"));
                                            EtImrg_statement.bindString(3, jsonArray.getJSONObject(j).optString("Aufnr"));
                                            EtImrg_statement.bindString(4, jsonArray.getJSONObject(j).optString("Vornr"));
                                            EtImrg_statement.bindString(5, jsonArray.getJSONObject(j).optString("Mdocm"));
                                            EtImrg_statement.bindString(6, jsonArray.getJSONObject(j).optString("Point"));
                                            EtImrg_statement.bindString(7, jsonArray.getJSONObject(j).optString("Mpobj"));
                                            EtImrg_statement.bindString(8, jsonArray.getJSONObject(j).optString("Mpobt"));
                                            EtImrg_statement.bindString(9, jsonArray.getJSONObject(j).optString("Psort"));
                                            EtImrg_statement.bindString(10, jsonArray.getJSONObject(j).optString("Pttxt"));
                                            EtImrg_statement.bindString(11, jsonArray.getJSONObject(j).optString("Atinn"));
                                            EtImrg_statement.bindString(12, jsonArray.getJSONObject(j).optString("Idate"));
                                            EtImrg_statement.bindString(13, jsonArray.getJSONObject(j).optString("Itime"));
                                            EtImrg_statement.bindString(14, jsonArray.getJSONObject(j).optString("Mdtxt"));
                                            EtImrg_statement.bindString(15, jsonArray.getJSONObject(j).optString("Readr"));
                                            EtImrg_statement.bindString(16, jsonArray.getJSONObject(j).optString("Atbez"));
                                            EtImrg_statement.bindString(17, jsonArray.getJSONObject(j).optString("Msehi"));
                                            EtImrg_statement.bindString(18, jsonArray.getJSONObject(j).optString("Msehl"));
                                            EtImrg_statement.bindString(19, jsonArray.getJSONObject(j).optString("Readc"));
                                            EtImrg_statement.bindString(20, jsonArray.getJSONObject(j).optString("Desic"));
                                            EtImrg_statement.bindString(21, jsonArray.getJSONObject(j).optString("Prest"));
                                            EtImrg_statement.bindString(22, jsonArray.getJSONObject(j).optString("Docaf"));
                                            EtImrg_statement.bindString(23, jsonArray.getJSONObject(j).optString("Codct"));
                                            EtImrg_statement.bindString(24, jsonArray.getJSONObject(j).optString("Codgr"));
                                            EtImrg_statement.bindString(25, jsonArray.getJSONObject(j).optString("Vlcod"));
                                            EtImrg_statement.bindString(26, jsonArray.getJSONObject(j).optString("Action"));
                                            EtImrg_statement.bindString(27, jsonArray.getJSONObject(j).optString("Equnr"));
                                            EtImrg_statement.execute();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        /*Reading and Inserting Data into Database Table for EtImrg*/


                    }
                    /*Reading Data by using FOR Loop*/

                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            } else {
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }

}
