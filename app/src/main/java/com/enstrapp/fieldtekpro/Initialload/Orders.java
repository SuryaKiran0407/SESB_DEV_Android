package com.enstrapp.fieldtekpro.Initialload;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

public class Orders {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> orders_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty c_e = new Check_Empty();

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


    public static String Get_DORD_Data(Context context, String transmit_type, String order_number) {
        try {
            DATABASE_NAME = context.getString(R.string.database_name);
            App_db = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equalsIgnoreCase("LOAD")) {
                /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderComponents_FIELDS);
                String CREATE_DUE_ORDERS_EtOrderComponents_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_ORDERS_EtOrderComponents_FIELDS + ""
                        + "( "
                        + DUE_ORDERS_EtOrderComponents_FIELDS_id + " INTEGER PRIMARY KEY,"//0
                        + DUE_ORDERS_EtOrderComponents_FIELDS_uuid + " TEXT,"//1
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Aufnr + " TEXT,"//2
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Zdoctype + " TEXT,"//3
                        + DUE_ORDERS_EtOrderComponents_FIELDS_ZdoctypeItem + " TEXT,"//4
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Tabname + " TEXT,"//5
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Fieldname + " TEXT,"//6
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Value + " TEXT,"//7
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Flabel + " TEXT,"//8
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Sequence + " TEXT,"//9
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Length + " TEXT,"//10
                        + DUE_ORDERS_EtOrderComponents_FIELDS_Datatype + " TEXT,"//11
                        + DUE_ORDERS_EtOrderComponents_FIELDS_OperationID + " TEXT,"//12
                        + DUE_ORDERS_EtOrderComponents_FIELDS_PartID + " TEXT"//13
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderComponents_FIELDS_TABLE);
                /* DUE_ORDERS_EtOrderComponents_FIELDS Table and Fields Names */


                /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_ORDERS_EtOrderOperations_FIELDS);
                String CREATE_DUE_ORDERS_EtOrderOperations_FIELDS_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_ORDERS_EtOrderOperations_FIELDS + ""
                        + "( "
                        + DUE_ORDERS_EtOrderOperations_FIELDS_id + " INTEGER PRIMARY KEY,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_uuid + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Aufnr + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Zdoctype + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_ZdoctypeItem + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Tabname + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Fieldname + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Value + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Flabel + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Sequence + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Length + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Datatype + " TEXT,"
                        + DUE_ORDERS_EtOrderOperations_FIELDS_Operationid + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_ORDERS_EtOrderOperations_FIELDS_TABLE);
                /* DUE_ORDERS_EtOrderOperations_FIELDS Table and Fields Names */

                /* Creating Header Custom_Info Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Custom_Info_Fields);
                String Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Custom_Info_Fields + ""
                        + "( "
                        + KEY_Custom_Info_id + " INTEGER PRIMARY KEY,"
                        + KEY_Custom_Info_uuid + " TEXT,"
                        + KEY_Custom_Info_Aufnr + " TEXT,"
                        + KEY_Custom_Info_Zdoctype + " TEXT,"
                        + KEY_Custom_Info_ZdoctypeItem + " TEXT,"
                        + KEY_Custom_Info_Tabname + " TEXT,"
                        + KEY_Custom_Info_Fieldname + " TEXT,"
                        + KEY_Custom_Info_Value + " TEXT,"
                        + KEY_Custom_Info_Flabel + " TEXT,"
                        + KEY_Custom_Info_Sequence + " TEXT,"
                        + KEY_Custom_Info_Length + " TEXT,"
                        + KEY_Custom_Info_Datatype + " TEXT"
                        + ")";
                App_db.execSQL(Custom_Info_Fields_TABLE);
                /* Creating Header Custom_Info Table with Fields */


                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Orders_Tkconfirm);
                String CREATE_TABLE_Orders_Tkconfirm = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Orders_Tkconfirm + ""
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
                String CREATE_TABLE_EtWcmWdDataTagtext = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWdDataTagtext + ""
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
                String CREATE_TABLE_EtWcmWdDataUntagtext = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWdDataUntagtext + ""
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
                String CREATE_TABLE_Orders_Attachments = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Orders_Attachments + ""
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
                String CREATE_DUE_ORDERS_EtOrderHeader_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_ORDERS_EtOrderHeader + ""
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
                String CREATE_DUE_ORDERS_EtOrderOperations_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_ORDERS_EtOrderOperations + ""
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
                String CREATE_TABLE_GET_DUE_ORDERS_Longtext = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_ORDERS_Longtext + ""
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
                String CREATE_TABLE_GET_EtOrderOlist = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtOrderOlist + ""
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
                String CREATE_TABLE_EtOrderStatus = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtOrderStatus + ""
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
                String CREATE_TABLE_GET_DUE_ORDERS_EtDocs = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_ORDERS_EtDocs + ""
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
                String CREATE_TABLE_GET_EtWcmWwData = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWwData + ""
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
                String CREATE_TABLE_GET_EtWcmWaDataa = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWaData + ""
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
                String CREATE_TABLE_GET_EtWcmWaChkReq = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWaChkReq + ""
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
                String CREATE_TABLE_GET_EtWcmWdData = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWdData + ""
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
                String CREATE_TABLE_GET_EtWcmWdItemData = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWdItemData + ""
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
                String CREATE_TABLE_GET_EtWcmWcagns = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_EtWcmWcagns + ""
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
                String CREATE_DUE_ORDERS_EtOrderComponents_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_ORDERS_EtOrderComponents + ""
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
            FieldTekPro_SharedPref = context
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                            " ? and Zactivity = ? and Endpoint = ?",
                    new String[]{"C2", "DO", webservice_type});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                url_link = cursor.getString(5);
            }
            /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
            device_id = Settings.Secure
                    .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            device_serial_number = Build.SERIAL;
            String androidId = "" + Settings.Secure
                    .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180000, TimeUnit.MILLISECONDS)
                    .writeTimeout(180000, TimeUnit.MILLISECONDS)
                    .readTimeout(180000, TimeUnit.MILLISECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64
                    .encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Orders_SER> call = service.getORDERDetails(url_link, basic, map);
            Response<Orders_SER> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    orders_uuid_list.clear();
                    List<Orders_SER.Result> results = response.body().getD().getResults();
                    App_db.beginTransaction();

                    if (results != null && results.size() > 0) {

                        /*EtOrderHeader*/
                        Orders_SER.EtOrderHeader etOrderHeader = results.get(0).getEtOrderHeader();
                        if (etOrderHeader != null) {
                            List<Orders_SER.EtOrderHeader_Result> etOrderHeaderResults = etOrderHeader.getResults();
                            if (etOrderHeaderResults != null && etOrderHeaderResults.size() > 0) {
                                for (Orders_SER.EtOrderHeader_Result eO : etOrderHeaderResults) {
                                    HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                                    UUID uniqueKey = UUID.randomUUID();
                                    uuid_hashmap.put("UUID", uniqueKey.toString());
                                    uuid_hashmap.put("Aufnr", c_e.check_empty(eO.getAufnr()));
                                    orders_uuid_list.add(uuid_hashmap);
                                }
                                for (Orders_SER.EtOrderHeader_Result eO : etOrderHeaderResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eO.getAufnr())) {
                                            String EtOrderHeader_sql = "Insert into DUE_ORDERS_EtOrderHeader " +
                                                    "(UUID, Aufnr, Auart, Ktext, Ilart, Ernam, Erdat, Priok," +
                                                    " Equnr, Strno, TplnrInt, Bautl, Gltrp, Gstrp, Docs, " +
                                                    "Permits, Altitude, Latitude, Longitude, Qmnum, Closed, " +
                                                    "Completed, Ingrp, Arbpl, Werks, Bemot, Aueru, Auarttext," +
                                                    " Qmartx, Qmtxt, Pltxt, Eqktx, Priokx , Ilatx, Plantname, " +
                                                    "Wkctrname, Ingrpname, Maktx, Xstatus, Usr01, Usr02, Usr03," +
                                                    " Usr04, Usr05, Kokrs, Kostl, Anlzu, Anlzux, Ausvn, Ausbs," +
                                                    " Auswk, Qmnam, ParnrVw, NameVw, Posid, Revnr) values(?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtOrderHeader_statement = App_db.compileStatement(EtOrderHeader_sql);
                                            EtOrderHeader_statement.clearBindings();
                                            EtOrderHeader_statement.bindString(1, uD.get("UUID"));
                                            EtOrderHeader_statement.bindString(2, c_e.check_empty(eO.getAufnr()));
                                            EtOrderHeader_statement.bindString(3, c_e.check_empty(eO.getAuart()));
                                            EtOrderHeader_statement.bindString(4, c_e.check_empty(eO.getKtext()));
                                            EtOrderHeader_statement.bindString(5, c_e.check_empty(eO.getIlart()));
                                            EtOrderHeader_statement.bindString(6, c_e.check_empty(eO.getErnam()));
                                            EtOrderHeader_statement.bindString(7, c_e.check_empty(eO.getErdat()));
                                            EtOrderHeader_statement.bindString(8, c_e.check_empty(eO.getPriok()));
                                            EtOrderHeader_statement.bindString(9, c_e.check_empty(eO.getEqunr()));
                                            EtOrderHeader_statement.bindString(10, c_e.check_empty(eO.getStrno()));
                                            EtOrderHeader_statement.bindString(11, c_e.check_empty(eO.getTplnrInt()));
                                            EtOrderHeader_statement.bindString(12, c_e.check_empty(eO.getBautl()));
                                            EtOrderHeader_statement.bindString(13, c_e.check_empty(eO.getGltrp()));
                                            EtOrderHeader_statement.bindString(14, c_e.check_empty(eO.getGstrp()));
                                            EtOrderHeader_statement.bindString(15, c_e.check_empty(eO.getDocs()));
                                            EtOrderHeader_statement.bindString(16, c_e.check_empty(eO.getPermits()));
                                            EtOrderHeader_statement.bindString(17, c_e.check_empty(eO.getAltitude()));
                                            EtOrderHeader_statement.bindString(18, c_e.check_empty(eO.getLatitude()));
                                            EtOrderHeader_statement.bindString(19, c_e.check_empty(eO.getLongitude()));
                                            EtOrderHeader_statement.bindString(20, c_e.check_empty(eO.getQmnum()));
                                            EtOrderHeader_statement.bindString(21, c_e.check_empty(eO.getClosed()));
                                            EtOrderHeader_statement.bindString(22, c_e.check_empty(eO.getCompleted()));
                                            EtOrderHeader_statement.bindString(23, c_e.check_empty(eO.getIngrp()));
                                            EtOrderHeader_statement.bindString(24, c_e.check_empty(eO.getArbpl()));
                                            EtOrderHeader_statement.bindString(25, c_e.check_empty(eO.getWerks()));
                                            EtOrderHeader_statement.bindString(26, c_e.check_empty(eO.getBemot()));
                                            EtOrderHeader_statement.bindString(27, c_e.check_empty(eO.getAueru()));
                                            EtOrderHeader_statement.bindString(28, c_e.check_empty(eO.getAuarttext()));
                                            EtOrderHeader_statement.bindString(29, c_e.check_empty(eO.getQmartx()));
                                            EtOrderHeader_statement.bindString(30, c_e.check_empty(eO.getQmtxt()));
                                            EtOrderHeader_statement.bindString(31, c_e.check_empty(eO.getPltxt()));
                                            EtOrderHeader_statement.bindString(32, c_e.check_empty(eO.getEqktx()));
                                            EtOrderHeader_statement.bindString(33, c_e.check_empty(eO.getPriokx()));
                                            EtOrderHeader_statement.bindString(34, c_e.check_empty(eO.getIlatx()));
                                            EtOrderHeader_statement.bindString(35, c_e.check_empty(eO.getPlantname()));
                                            EtOrderHeader_statement.bindString(36, c_e.check_empty(eO.getWkctrname()));
                                            EtOrderHeader_statement.bindString(37, c_e.check_empty(eO.getIngrpname()));
                                            EtOrderHeader_statement.bindString(38, c_e.check_empty(eO.getMaktx()));
                                            EtOrderHeader_statement.bindString(39, c_e.check_empty(eO.getXstatus()));
                                            EtOrderHeader_statement.bindString(40, c_e.check_empty(eO.getUsr01()));
                                            EtOrderHeader_statement.bindString(41, c_e.check_empty(eO.getUsr02()));
                                            EtOrderHeader_statement.bindString(42, c_e.check_empty(eO.getUsr03()));
                                            EtOrderHeader_statement.bindString(43, c_e.check_empty(eO.getUsr04()));
                                            EtOrderHeader_statement.bindString(44, c_e.check_empty(eO.getUsr05()));
                                            EtOrderHeader_statement.bindString(45, c_e.check_empty(eO.getKokrs()));
                                            EtOrderHeader_statement.bindString(46, c_e.check_empty(eO.getKostl()));
                                            EtOrderHeader_statement.bindString(47, c_e.check_empty(eO.getAnlzu()));
                                            EtOrderHeader_statement.bindString(48, c_e.check_empty(eO.getAnlzux()));
                                            EtOrderHeader_statement.bindString(49, c_e.check_empty(eO.getAusvn()));
                                            EtOrderHeader_statement.bindString(50, c_e.check_empty(eO.getAusbs()));
                                            EtOrderHeader_statement.bindString(51, c_e.check_empty(eO.getAuswk()));
                                            EtOrderHeader_statement.bindString(52, c_e.check_empty(eO.getQmnam()));
                                            EtOrderHeader_statement.bindString(53, c_e.check_empty(eO.getParnrVw()));
                                            EtOrderHeader_statement.bindString(54, c_e.check_empty(eO.getNameVw()));
                                            EtOrderHeader_statement.bindString(55, c_e.check_empty(eO.getPosid()));
                                            EtOrderHeader_statement.bindString(56, c_e.check_empty(eO.getRevnr()));
                                            EtOrderHeader_statement.execute();

                                            Orders_SER.EtOrderHeaderFields etOrderHeaderFields = eO.getEtOrderHeaderFields();
                                            if (etOrderHeaderFields != null) {
                                                List<Orders_SER.EtOrderHeaderFields_Result> etOrderHeaderFieldsResults = etOrderHeaderFields.getResults();
                                                if (etOrderHeaderFieldsResults != null && etOrderHeaderFieldsResults.size() > 0) {
                                                    String sql1 = "Insert into EtOrderHeader_CustomInfo (UUID,Aufnr,Zdoctype,ZdoctypeItem,Tabname," +
                                                            "Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (Orders_SER.EtOrderHeaderFields_Result eHF : etOrderHeaderFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eO.getAufnr()));
                                                        statement1.bindString(3, c_e.check_empty(eHF.getZdoctype()));
                                                        statement1.bindString(4, c_e.check_empty(eHF.getZdoctypeItem()));
                                                        statement1.bindString(5, c_e.check_empty(eHF.getTabname()));
                                                        statement1.bindString(6, c_e.check_empty(eHF.getFieldname()));
                                                        statement1.bindString(7, c_e.check_empty(eHF.getValue()));
                                                        statement1.bindString(8, c_e.check_empty(eHF.getFlabel()));
                                                        statement1.bindString(9, c_e.check_empty(eHF.getSequence()));
                                                        statement1.bindString(10, c_e.check_empty(eHF.getLength()));
                                                        statement1.bindString(11, c_e.check_empty(eHF.getDatatype()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtOrderOperations*/
                        Orders_SER.EtOrderOperations etOrderOperations = results.get(0).getEtOrderOperations();
                        if (etOrderOperations != null) {
                            List<Orders_SER.EtOrderOperations_Result> etOrderOperationsResults = etOrderOperations.getResults();
                            if (etOrderOperationsResults != null && etOrderOperationsResults.size() > 0) {
                                for (Orders_SER.EtOrderOperations_Result eOO : etOrderOperationsResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eOO.getAufnr())) {
                                            String EtOrderOperations_sql = "Insert into DUE_ORDERS_EtOrderOperations " +
                                                    "(UUID,Aufnr,Vornr,Uvorn,Ltxa1,Arbpl,Werks,Steus,Larnt,Dauno," +
                                                    "Daune,Fsavd,Ssedd,Pernr,Asnum,Plnty,Plnal,Plnnr,Rueck,Aueru," +
                                                    "ArbplText,WerksText,SteusText,LarntText,Usr01,Usr02,Usr03,Usr04," +
                                                    "Usr05,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtOrderOperations_statement = App_db.compileStatement(EtOrderOperations_sql);
                                            EtOrderOperations_statement.clearBindings();
                                            EtOrderOperations_statement.bindString(1, uD.get("UUID"));
                                            EtOrderOperations_statement.bindString(2, c_e.check_empty(eOO.getAufnr()));
                                            EtOrderOperations_statement.bindString(3, c_e.check_empty(eOO.getVornr()));
                                            EtOrderOperations_statement.bindString(4, c_e.check_empty(eOO.getUvorn()));
                                            EtOrderOperations_statement.bindString(5, c_e.check_empty(eOO.getLtxa1()));
                                            EtOrderOperations_statement.bindString(6, c_e.check_empty(eOO.getArbpl()));
                                            EtOrderOperations_statement.bindString(7, c_e.check_empty(eOO.getWerks()));
                                            EtOrderOperations_statement.bindString(8, c_e.check_empty(eOO.getSteus()));
                                            EtOrderOperations_statement.bindString(9, c_e.check_empty(eOO.getLarnt()));
                                            EtOrderOperations_statement.bindString(10, c_e.check_empty(eOO.getDauno()));
                                            EtOrderOperations_statement.bindString(11, c_e.check_empty(eOO.getDaune()));
                                            EtOrderOperations_statement.bindString(12, c_e.check_empty(eOO.getFsavd()));
                                            EtOrderOperations_statement.bindString(13, c_e.check_empty(eOO.getSsedd()));
                                            EtOrderOperations_statement.bindString(14, c_e.check_empty(eOO.getPernr()));
                                            EtOrderOperations_statement.bindString(15, c_e.check_empty(eOO.getAsnum()));
                                            EtOrderOperations_statement.bindString(16, c_e.check_empty(eOO.getPlnty()));
                                            EtOrderOperations_statement.bindString(17, c_e.check_empty(eOO.getPlnal()));
                                            EtOrderOperations_statement.bindString(18, c_e.check_empty(eOO.getPlnnr()));
                                            EtOrderOperations_statement.bindString(19, c_e.check_empty(eOO.getRueck()));
                                            EtOrderOperations_statement.bindString(20, c_e.check_empty(eOO.getAueru()));
                                            EtOrderOperations_statement.bindString(21, c_e.check_empty(eOO.getArbplText()));
                                            EtOrderOperations_statement.bindString(22, c_e.check_empty(eOO.getWerksText()));
                                            EtOrderOperations_statement.bindString(23, c_e.check_empty(eOO.getSteusText()));
                                            EtOrderOperations_statement.bindString(24, c_e.check_empty(eOO.getLarntText()));
                                            EtOrderOperations_statement.bindString(25, c_e.check_empty(eOO.getUsr01()));
                                            EtOrderOperations_statement.bindString(26, c_e.check_empty(eOO.getUsr02()));
                                            EtOrderOperations_statement.bindString(27, c_e.check_empty(eOO.getUsr03()));
                                            EtOrderOperations_statement.bindString(28, c_e.check_empty(eOO.getUsr04()));
                                            EtOrderOperations_statement.bindString(29, c_e.check_empty(eOO.getUsr05()));
                                            EtOrderOperations_statement.bindString(30, c_e.check_empty(eOO.getAction()));
                                            EtOrderOperations_statement.execute();

                                            Orders_SER.EtOrderHeaderFields etOrderOperationFields = eOO.getEtOrderOperationsFields();
                                            if (etOrderOperationFields != null) {
                                                List<Orders_SER.EtOrderHeaderFields_Result> etOrderOperationFieldsResults = etOrderOperationFields.getResults();
                                                if (etOrderOperationFieldsResults != null && etOrderOperationFieldsResults.size() > 0) {
                                                    String sql1 = "Insert into DUE_ORDERS_EtOrderOperations_FIELDS (UUID, Aufnr, Zdoctype, ZdoctypeItem, " +
                                                            "Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype, OperationID) " +
                                                            "values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (Orders_SER.EtOrderHeaderFields_Result eOF : etOrderOperationFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eOO.getAufnr()));
                                                        statement1.bindString(3, c_e.check_empty(eOF.getZdoctype()));
                                                        statement1.bindString(4, c_e.check_empty(eOF.getZdoctypeItem()));
                                                        statement1.bindString(5, c_e.check_empty(eOF.getTabname()));
                                                        statement1.bindString(6, c_e.check_empty(eOF.getFieldname()));
                                                        statement1.bindString(7, c_e.check_empty(eOF.getValue()));
                                                        statement1.bindString(8, c_e.check_empty(eOF.getFlabel()));
                                                        statement1.bindString(9, c_e.check_empty(eOF.getSequence()));
                                                        statement1.bindString(10, c_e.check_empty(eOF.getLength()));
                                                        statement1.bindString(11, c_e.check_empty(eOF.getDatatype()));
                                                        statement1.bindString(12, c_e.check_empty(eOO.getVornr()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtOrderLongtext*/
                        Orders_SER.EtOrderLongtext etOrderLongtext = results.get(0).getEtOrderLongtext();
                        if (etOrderLongtext != null) {
                            List<Orders_SER.EtOrderLongtext_Result> etOrderLongtextResults = etOrderLongtext.getResults();
                            if (etOrderLongtextResults != null && etOrderLongtextResults.size() > 0) {
                                for (Orders_SER.EtOrderLongtext_Result eOL : etOrderLongtextResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eOL.getAufnr())) {
                                            String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext " +
                                                    "(UUID, Aufnr, Activity, TextLine, Tdid) values(?,?,?,?,?);";
                                            SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                                            EtOrderLongtext_statement.clearBindings();
                                            EtOrderLongtext_statement.bindString(1, uD.get("UUID"));
                                            EtOrderLongtext_statement.bindString(2, c_e.check_empty(eOL.getAufnr()));
                                            EtOrderLongtext_statement.bindString(3, c_e.check_empty(eOL.getActivity()));
                                            EtOrderLongtext_statement.bindString(4, c_e.check_empty(eOL.getTextLine()));
                                            EtOrderLongtext_statement.bindString(5, c_e.check_empty(eOL.getTdid()));
                                            EtOrderLongtext_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtOrderOlist*/
                        Orders_SER.EtOrderOlist etOrderOlist = results.get(0).getEtOrderOlist();
                        if (etOrderOlist != null) {
                            List<Orders_SER.EtOrderOlist_Result> etOrderOlistResults = etOrderOlist.getResults();
                            if (etOrderOlistResults != null && etOrderOlistResults.size() > 0) {
                                for (Orders_SER.EtOrderOlist_Result eOL : etOrderOlistResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eOL.getAufnr())) {
                                            String EtOrderOlist_sql = "Insert into EtOrderOlist (UUID, Aufnr," +
                                                    " Obknr, Obzae, Qmnum, Equnr, Strno, Tplnr, Bautl, Qmtxt," +
                                                    " Pltxt, Eqktx, Maktx, Action) values(?,?,?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?);";
                                            SQLiteStatement EtOrderOlist_statement = App_db.compileStatement(EtOrderOlist_sql);
                                            EtOrderOlist_statement.clearBindings();
                                            EtOrderOlist_statement.bindString(1, uD.get("UUID"));
                                            EtOrderOlist_statement.bindString(2, c_e.check_empty(eOL.getAufnr()));
                                            EtOrderOlist_statement.bindString(3, c_e.check_empty(eOL.getObknr()));
                                            EtOrderOlist_statement.bindString(4, c_e.check_empty(eOL.getObzae()));
                                            EtOrderOlist_statement.bindString(5, c_e.check_empty(eOL.getQmnum()));
                                            EtOrderOlist_statement.bindString(6, c_e.check_empty(eOL.getEqunr()));
                                            EtOrderOlist_statement.bindString(7, c_e.check_empty(eOL.getStrno()));
                                            EtOrderOlist_statement.bindString(8, c_e.check_empty(eOL.getTplnr()));
                                            EtOrderOlist_statement.bindString(9, c_e.check_empty(eOL.getBautl()));
                                            EtOrderOlist_statement.bindString(10, c_e.check_empty(eOL.getQmtxt()));
                                            EtOrderOlist_statement.bindString(11, c_e.check_empty(eOL.getPltxt()));
                                            EtOrderOlist_statement.bindString(12, c_e.check_empty(eOL.getEqktx()));
                                            EtOrderOlist_statement.bindString(13, c_e.check_empty(eOL.getMaktx()));
                                            EtOrderOlist_statement.bindString(14, c_e.check_empty(eOL.getAction()));
                                            EtOrderOlist_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtOrderStatus*/
                        Orders_SER.EtOrderStatus etOrderStatus = results.get(0).getEtOrderStatus();
                        if (etOrderStatus != null) {
                            List<Orders_SER.EtOrderStatus_Result> etOrderStatusResults = etOrderStatus.getResults();
                            if (etOrderStatusResults != null && etOrderStatusResults.size() > 0) {
                                for (Orders_SER.EtOrderStatus_Result eOS : etOrderStatusResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eOS.getAufnr())) {
                                            String EtOrderStatus_sql = "Insert into EtOrderStatus (UUID,Aufnr, " +
                                                    "Vornr, Objnr, Stsma, Inist, Stonr, Hsonr, Nsonr,Stat, Act," +
                                                    " Txt04, Txt30, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtOrderStatus_statement = App_db.compileStatement(EtOrderStatus_sql);
                                            EtOrderStatus_statement.clearBindings();
                                            EtOrderStatus_statement.bindString(1, uD.get("UUID"));
                                            EtOrderStatus_statement.bindString(2, c_e.check_empty(eOS.getAufnr()));
                                            EtOrderStatus_statement.bindString(3, c_e.check_empty(eOS.getVornr()));
                                            EtOrderStatus_statement.bindString(4, c_e.check_empty(eOS.getObjnr()));
                                            EtOrderStatus_statement.bindString(5, c_e.check_empty(eOS.getStsma()));
                                            EtOrderStatus_statement.bindString(6, c_e.check_empty(eOS.getInist()));
                                            EtOrderStatus_statement.bindString(7, c_e.check_empty(eOS.getStonr()));
                                            EtOrderStatus_statement.bindString(8, c_e.check_empty(eOS.getHsonr()));
                                            EtOrderStatus_statement.bindString(9, c_e.check_empty(eOS.getNsonr()));
                                            EtOrderStatus_statement.bindString(10, c_e.check_empty(eOS.getStat()));
                                            EtOrderStatus_statement.bindString(11, c_e.check_empty(eOS.getAct()));
                                            EtOrderStatus_statement.bindString(12, c_e.check_empty(eOS.getTxt04()));
                                            EtOrderStatus_statement.bindString(13, c_e.check_empty(eOS.getTxt30()));
                                            EtOrderStatus_statement.bindString(14, c_e.check_empty(eOS.getAction()));
                                            EtOrderStatus_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtDocs*/
                        Orders_SER.EtDocs etDocs = results.get(0).getEtDocs();
                        if (etDocs != null) {
                            List<Orders_SER.EtDocs_Result> etDocsResults = etDocs.getResults();
                            if (etDocsResults != null && etDocsResults.size() > 0) {
                                for (Orders_SER.EtDocs_Result eD : etDocsResults) {
                                    String EtDocs_sql = "Insert into DUE_ORDERS_EtDocs(UUID, Zobjid, Zdoctype," +
                                            " ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId," +
                                            " DocType, Objtype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                    EtDocs_statement.clearBindings();
                                    EtDocs_statement.bindString(1, "");
                                    EtDocs_statement.bindString(2, c_e.check_empty(eD.getZobjid()));
                                    EtDocs_statement.bindString(3, c_e.check_empty(eD.getZdoctype()));
                                    EtDocs_statement.bindString(4, c_e.check_empty(eD.getZdoctypeItem()));
                                    EtDocs_statement.bindString(5, c_e.check_empty(eD.getFilename()));
                                    EtDocs_statement.bindString(6, c_e.check_empty(eD.getFiletype()));
                                    EtDocs_statement.bindString(7, c_e.check_empty(eD.getFsize()));
                                    EtDocs_statement.bindString(8, c_e.check_empty(eD.getContent()));
                                    EtDocs_statement.bindString(9, c_e.check_empty(eD.getDocID()));
                                    EtDocs_statement.bindString(10, c_e.check_empty(eD.getDocType()));
                                    EtDocs_statement.bindString(11, c_e.check_empty(eD.getObjtype()));
                                    EtDocs_statement.execute();
                                }
                            }
                        }

                        /*EtWcmWwData*/
                        Orders_SER.EtWcmWwData etWcmWwData = results.get(0).getEtWcmWwData();
                        if (etWcmWwData != null) {
                            List<Orders_SER.EtWcmWwData_Result> etWcmWwDataResults = etWcmWwData.getResults();
                            if (etWcmWwDataResults != null && etWcmWwDataResults.size() > 0) {
                                for (Orders_SER.EtWcmWwData_Result eWW : etWcmWwDataResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eWW.getAufnr())) {
                                            String EtWcmWwData_sql = "Insert into EtWcmWwData (UUID, Aufnr, Objart," +
                                                    " Wapnr, Iwerk, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape," +
                                                    " Etapex, Rctime, Rcunit, Priok, Priokx, Stxt, Datefr, Timefr, Dateto," +
                                                    " Timeto, Objnr, Crea, Prep, Comp, Appr, Pappr, Action, Begru, Begtx)" +
                                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWwData_statement = App_db.compileStatement(EtWcmWwData_sql);
                                            EtWcmWwData_statement.clearBindings();
                                            EtWcmWwData_statement.bindString(1, uD.get("UUID"));
                                            EtWcmWwData_statement.bindString(2, c_e.check_empty(eWW.getAufnr()));
                                            EtWcmWwData_statement.bindString(3, c_e.check_empty(eWW.getObjart()));
                                            EtWcmWwData_statement.bindString(4, c_e.check_empty(eWW.getWapnr()));
                                            EtWcmWwData_statement.bindString(5, c_e.check_empty(eWW.getIwerk()));
                                            EtWcmWwData_statement.bindString(6, c_e.check_empty(eWW.getUsage()));
                                            EtWcmWwData_statement.bindString(7, c_e.check_empty(eWW.getUsagex()));
                                            EtWcmWwData_statement.bindString(8, c_e.check_empty(eWW.getTrain()));
                                            EtWcmWwData_statement.bindString(9, c_e.check_empty(eWW.getTrainx()));
                                            EtWcmWwData_statement.bindString(10, c_e.check_empty(eWW.getAnlzu()));
                                            EtWcmWwData_statement.bindString(11, c_e.check_empty(eWW.getAnlzux()));
                                            EtWcmWwData_statement.bindString(12, c_e.check_empty(eWW.getEtape()));
                                            EtWcmWwData_statement.bindString(13, c_e.check_empty(eWW.getEtapex()));
                                            EtWcmWwData_statement.bindString(14, c_e.check_empty(eWW.getRctime()));
                                            EtWcmWwData_statement.bindString(15, c_e.check_empty(eWW.getRcunit()));
                                            EtWcmWwData_statement.bindString(16, c_e.check_empty(eWW.getPriok()));
                                            EtWcmWwData_statement.bindString(17, c_e.check_empty(eWW.getPriokx()));
                                            EtWcmWwData_statement.bindString(18, c_e.check_empty(eWW.getStxt()));
                                            EtWcmWwData_statement.bindString(19, c_e.check_empty(eWW.getDatefr()));
                                            EtWcmWwData_statement.bindString(20, c_e.check_empty(eWW.getTimefr()));
                                            EtWcmWwData_statement.bindString(21, c_e.check_empty(eWW.getDateto()));
                                            EtWcmWwData_statement.bindString(22, c_e.check_empty(eWW.getTimeto()));
                                            EtWcmWwData_statement.bindString(23, c_e.check_empty(eWW.getObjnr()));
                                            EtWcmWwData_statement.bindString(24, c_e.check_empty(eWW.getCrea()));
                                            EtWcmWwData_statement.bindString(25, c_e.check_empty(eWW.getPrep()));
                                            EtWcmWwData_statement.bindString(26, c_e.check_empty(eWW.getComp()));
                                            EtWcmWwData_statement.bindString(27, c_e.check_empty(eWW.getAppr()));
                                            EtWcmWwData_statement.bindString(28, c_e.check_empty(eWW.getPappr()));
                                            EtWcmWwData_statement.bindString(29, c_e.check_empty(eWW.getAction()));
                                            EtWcmWwData_statement.bindString(30, c_e.check_empty(eWW.getBegru()));
                                            EtWcmWwData_statement.bindString(31, c_e.check_empty(eWW.getBegtx()));
                                            EtWcmWwData_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtWcmWaData*/
                        Orders_SER.EtWcmWaData etWcmWaData = results.get(0).getEtWcmWaData();
                        if (etWcmWaData != null) {
                            List<Orders_SER.EtWcmWaData_Result> etWcmWaDataResults = etWcmWaData.getResults();
                            if (etWcmWaDataResults != null && etWcmWaDataResults.size() > 0) {
                                for (Orders_SER.EtWcmWaData_Result eWA : etWcmWaDataResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eWA.getAufnr())) {
                                            String EtWcmWaData_sql = "Insert into EtWcmWaData (UUID, Aufnr, Objart," +
                                                    " Wapinr, Iwerk, Objtyp, Usage, Usagex, Train, Trainx, Anlzu, " +
                                                    "Anlzux, Etape, Etapex, Stxt, Datefr, Timefr, Dateto, Timeto, " +
                                                    "Priok, Priokx, Rctime, Rcunit, Objnr, Refobj, Crea, Prep, " +
                                                    "Comp, Appr, Action, Begru, Begtx, Extperiod) values(?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWaData_statement = App_db.compileStatement(EtWcmWaData_sql);
                                            EtWcmWaData_statement.clearBindings();
                                            EtWcmWaData_statement.bindString(1, uD.get("UUID"));
                                            EtWcmWaData_statement.bindString(2, c_e.check_empty(eWA.getAufnr()));
                                            EtWcmWaData_statement.bindString(3, c_e.check_empty(eWA.getObjart()));
                                            EtWcmWaData_statement.bindString(4, c_e.check_empty(eWA.getWapinr()));
                                            EtWcmWaData_statement.bindString(5, c_e.check_empty(eWA.getIwerk()));
                                            EtWcmWaData_statement.bindString(6, c_e.check_empty(eWA.getObjtyp()));
                                            EtWcmWaData_statement.bindString(7, c_e.check_empty(eWA.getUsage()));
                                            EtWcmWaData_statement.bindString(8, c_e.check_empty(eWA.getUsagex()));
                                            EtWcmWaData_statement.bindString(9, c_e.check_empty(eWA.getTrain()));
                                            EtWcmWaData_statement.bindString(10, c_e.check_empty(eWA.getTrainx()));
                                            EtWcmWaData_statement.bindString(11, c_e.check_empty(eWA.getAnlzu()));
                                            EtWcmWaData_statement.bindString(12, c_e.check_empty(eWA.getAnlzux()));
                                            EtWcmWaData_statement.bindString(13, c_e.check_empty(eWA.getEtape()));
                                            EtWcmWaData_statement.bindString(14, c_e.check_empty(eWA.getEtapex()));
                                            EtWcmWaData_statement.bindString(15, c_e.check_empty(eWA.getStxt()));
                                            EtWcmWaData_statement.bindString(16, c_e.check_empty(eWA.getDatefr()));
                                            EtWcmWaData_statement.bindString(17, c_e.check_empty(eWA.getTimefr()));
                                            EtWcmWaData_statement.bindString(18, c_e.check_empty(eWA.getDateto()));
                                            EtWcmWaData_statement.bindString(19, c_e.check_empty(eWA.getTimeto()));
                                            EtWcmWaData_statement.bindString(20, c_e.check_empty(eWA.getPriok()));
                                            EtWcmWaData_statement.bindString(21, c_e.check_empty(eWA.getPriokx()));
                                            EtWcmWaData_statement.bindString(22, c_e.check_empty(eWA.getRctime()));
                                            EtWcmWaData_statement.bindString(23, c_e.check_empty(eWA.getRcunit()));
                                            EtWcmWaData_statement.bindString(24, c_e.check_empty(eWA.getObjnr()));
                                            EtWcmWaData_statement.bindString(25, c_e.check_empty(eWA.getRefobj()));
                                            EtWcmWaData_statement.bindString(26, c_e.check_empty(eWA.getCrea()));
                                            EtWcmWaData_statement.bindString(27, c_e.check_empty(eWA.getPrep()));
                                            EtWcmWaData_statement.bindString(28, c_e.check_empty(eWA.getComp()));
                                            EtWcmWaData_statement.bindString(29, c_e.check_empty(eWA.getAppr()));
                                            EtWcmWaData_statement.bindString(30, c_e.check_empty(eWA.getAction()));
                                            EtWcmWaData_statement.bindString(31, c_e.check_empty(eWA.getBegru()));
                                            EtWcmWaData_statement.bindString(32, c_e.check_empty(eWA.getBegtx()));
                                            EtWcmWaData_statement.bindString(33, c_e.check_empty(String.valueOf(eWA.getExtperiod())));
                                            EtWcmWaData_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtWcmWaChkReq*/
                        Orders_SER.EtWcmWaChkReq etWcmWaChkReq = results.get(0).getEtWcmWaChkReq();
                        if (etWcmWaChkReq != null) {
                            List<Orders_SER.EtWcmWaChkReq_Result> etWcmWaChkReqResults = etWcmWaChkReq.getResults();
                            if (etWcmWaChkReqResults != null && etWcmWaChkReqResults.size() > 0) {
                                for (Orders_SER.EtWcmWaChkReq_Result eWR : etWcmWaChkReqResults) {
                                    String EtWcmWaChkReq_sql = "Insert into EtWcmWaChkReq (Wapinr, Wapityp, Needid, " +
                                            "Value, ChkPointType, Desctext, Tplnr, Wkgrp, Needgrp, Equnr) " +
                                            "values(?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWaChkReq_statement = App_db.compileStatement(EtWcmWaChkReq_sql);
                                    EtWcmWaChkReq_statement.clearBindings();
                                    EtWcmWaChkReq_statement.bindString(1, c_e.check_empty(eWR.getWapinr()));
                                    EtWcmWaChkReq_statement.bindString(2, c_e.check_empty(eWR.getWapityp()));
                                    EtWcmWaChkReq_statement.bindString(5, c_e.check_empty(eWR.getChkPointType()));
                                    if (c_e.check_empty(eWR.getChkPointType()).equalsIgnoreCase("W"))
                                        EtWcmWaChkReq_statement.bindString(3, c_e.check_empty(eWR.getWkid()));
                                    else
                                        EtWcmWaChkReq_statement.bindString(3, c_e.check_empty(eWR.getNeedid()));
                                    EtWcmWaChkReq_statement.bindString(4, c_e.check_empty(eWR.getValue()));
                                    EtWcmWaChkReq_statement.bindString(6, c_e.check_empty(eWR.getDesctext()));
                                    EtWcmWaChkReq_statement.bindString(7, c_e.check_empty(eWR.getTplnr()));
                                    EtWcmWaChkReq_statement.bindString(8, c_e.check_empty(eWR.getWkgrp()));
                                    EtWcmWaChkReq_statement.bindString(9, c_e.check_empty(eWR.getNeedgrp()));
                                    EtWcmWaChkReq_statement.bindString(10, c_e.check_empty(eWR.getEqunr()));
                                    EtWcmWaChkReq_statement.execute();
                                }
                            }
                        }

                        /*EtWcmWdData*/
                        Orders_SER.EtWcmWdData etWcmWdData = results.get(0).getEtWcmWdData();
                        if (etWcmWdData != null) {
                            List<Orders_SER.EtWcmWdData_Result> etWcmWdDataResults = etWcmWdData.getResults();
                            if (etWcmWdDataResults != null && etWcmWdDataResults.size() > 0) {
                                for (Orders_SER.EtWcmWdData_Result eWD : etWcmWdDataResults) {
                                    String EtWcmWdData_sql = "Insert into EtWcmWdData (Aufnr, Objart, Wcnr, Iwerk," +
                                            " Objtyp, Usage, Usagex, Train, Trainx, Anlzu, Anlzux, Etape, Etapex," +
                                            " Stxt, Datefr, Timefr, Dateto, Timeto, Priok, Priokx, Rctime, Rcunit, " +
                                            "Objnr, Refobj, Crea, Prep, Comp, Appr, Action, Begru, Begtx) " +
                                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWdData_statement = App_db.compileStatement(EtWcmWdData_sql);
                                    EtWcmWdData_statement.clearBindings();
                                    EtWcmWdData_statement.bindString(1, c_e.check_empty(eWD.getAufnr()));
                                    EtWcmWdData_statement.bindString(2, c_e.check_empty(eWD.getObjart()));
                                    EtWcmWdData_statement.bindString(3, c_e.check_empty(eWD.getWcnr()));
                                    EtWcmWdData_statement.bindString(4, c_e.check_empty(eWD.getIwerk()));
                                    EtWcmWdData_statement.bindString(5, c_e.check_empty(eWD.getObjtyp()));
                                    EtWcmWdData_statement.bindString(6, c_e.check_empty(eWD.getUsage()));
                                    EtWcmWdData_statement.bindString(7, c_e.check_empty(eWD.getUsagex()));
                                    EtWcmWdData_statement.bindString(8, c_e.check_empty(eWD.getTrain()));
                                    EtWcmWdData_statement.bindString(9, c_e.check_empty(eWD.getTrainx()));
                                    EtWcmWdData_statement.bindString(10, c_e.check_empty(eWD.getAnlzu()));
                                    EtWcmWdData_statement.bindString(11, c_e.check_empty(eWD.getAnlzux()));
                                    EtWcmWdData_statement.bindString(12, c_e.check_empty(eWD.getEtape()));
                                    EtWcmWdData_statement.bindString(13, c_e.check_empty(eWD.getEtapex()));
                                    EtWcmWdData_statement.bindString(14, c_e.check_empty(eWD.getStxt()));
                                    EtWcmWdData_statement.bindString(15, c_e.check_empty(eWD.getDatefr()));
                                    EtWcmWdData_statement.bindString(16, c_e.check_empty(eWD.getTimefr()));
                                    EtWcmWdData_statement.bindString(17, c_e.check_empty(eWD.getDateto()));
                                    EtWcmWdData_statement.bindString(18, c_e.check_empty(eWD.getTimeto()));
                                    EtWcmWdData_statement.bindString(19, c_e.check_empty(eWD.getPriok()));
                                    EtWcmWdData_statement.bindString(20, c_e.check_empty(eWD.getPriokx()));
                                    EtWcmWdData_statement.bindString(21, c_e.check_empty(eWD.getRctime()));
                                    EtWcmWdData_statement.bindString(22, c_e.check_empty(eWD.getRcunit()));
                                    EtWcmWdData_statement.bindString(23, c_e.check_empty(eWD.getObjnr()));
                                    EtWcmWdData_statement.bindString(24, c_e.check_empty(eWD.getRefobj()));
                                    EtWcmWdData_statement.bindString(25, c_e.check_empty(eWD.getCrea()));
                                    EtWcmWdData_statement.bindString(26, c_e.check_empty(eWD.getPrep()));
                                    EtWcmWdData_statement.bindString(27, c_e.check_empty(eWD.getComp()));
                                    EtWcmWdData_statement.bindString(28, c_e.check_empty(eWD.getAppr()));
                                    EtWcmWdData_statement.bindString(29, c_e.check_empty(eWD.getAction()));
                                    EtWcmWdData_statement.bindString(30, c_e.check_empty(eWD.getBegru()));
                                    EtWcmWdData_statement.bindString(31, c_e.check_empty(eWD.getBegtx()));

                                    Orders_SER.EtWcmWdDataTagtext etWcmWdDataTagtext = eWD.getEtWcmWdDataTagtext();
                                    if (etWcmWdDataTagtext != null) {
                                        List<Orders_SER.EtWcmWdDataTagtext_Result> etWcmWdDataTagtextResults = etWcmWdDataTagtext.getResults();
                                        if (etWcmWdDataTagtextResults != null && etWcmWdDataTagtextResults.size() > 0) {
                                            for (Orders_SER.EtWcmWdDataTagtext_Result eWDT : etWcmWdDataTagtextResults) {
                                                String EtWcmWdDataTagtext_sql = "Insert into EtWcmWdDataTagtext (Aufnr, Wcnr," +
                                                        " Objtype, FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                SQLiteStatement EtWcmWdDataTagtext_statement = App_db.compileStatement(EtWcmWdDataTagtext_sql);
                                                EtWcmWdDataTagtext_statement.clearBindings();
                                                EtWcmWdDataTagtext_statement.bindString(1, c_e.check_empty(eWDT.getAufnr()));
                                                EtWcmWdDataTagtext_statement.bindString(2, c_e.check_empty(eWDT.getWcnr()));
                                                EtWcmWdDataTagtext_statement.bindString(3, c_e.check_empty(eWDT.getObjtype()));
                                                EtWcmWdDataTagtext_statement.bindString(4, c_e.check_empty(eWDT.getFormatCol()));
                                                EtWcmWdDataTagtext_statement.bindString(5, c_e.check_empty(eWDT.getTextLine()));
                                                EtWcmWdDataTagtext_statement.bindString(6, c_e.check_empty(eWDT.getAction()));
                                                EtWcmWdDataTagtext_statement.execute();
                                            }
                                        }
                                    }

                                    Orders_SER.EtWcmWdDataUntagtext etWcmWdDataUntagtext = eWD.getEtWcmWdDataUntagtext();
                                    if (etWcmWdDataUntagtext != null) {
                                        List<Orders_SER.EtWcmWdDataTagtext_Result> etWcmWdDataTagtextResults = etWcmWdDataUntagtext.getResults();
                                        if (etWcmWdDataTagtextResults != null && etWcmWdDataTagtextResults.size() > 0) {
                                            for (Orders_SER.EtWcmWdDataTagtext_Result eWDT : etWcmWdDataTagtextResults) {
                                                String EtWcmWdDataUntagtext_sql = "Insert into EtWcmWdDataUntagtext (Aufnr, Wcnr, Objtype, " +
                                                        "FormatCol, TextLine, Action) values(?,?,?,?,?,?);";
                                                SQLiteStatement EtWcmWdDataUntagtext_statement = App_db.compileStatement(EtWcmWdDataUntagtext_sql);
                                                EtWcmWdDataUntagtext_statement.clearBindings();
                                                EtWcmWdDataUntagtext_statement.bindString(1, c_e.check_empty(eWDT.getAufnr()));
                                                EtWcmWdDataUntagtext_statement.bindString(2, c_e.check_empty(eWDT.getWcnr()));
                                                EtWcmWdDataUntagtext_statement.bindString(3, c_e.check_empty(eWDT.getObjtype()));
                                                EtWcmWdDataUntagtext_statement.bindString(4, c_e.check_empty(eWDT.getFormatCol()));
                                                EtWcmWdDataUntagtext_statement.bindString(5, c_e.check_empty(eWDT.getTextLine()));
                                                EtWcmWdDataUntagtext_statement.bindString(6, c_e.check_empty(eWDT.getAction()));
                                                EtWcmWdDataUntagtext_statement.execute();
                                            }
                                        }
                                    }
                                    EtWcmWdData_statement.execute();
                                }
                            }
                        }

                        /*EtWcmWdItemData*/
                        Orders_SER.EtWcmWdItemData etWcmWdItemData = results.get(0).getEtWcmWdItemData();
                        if (etWcmWdItemData != null) {
                            List<Orders_SER.EtWcmWdItemData_Result> etWcmWdItemDataResults = etWcmWdItemData.getResults();
                            if (etWcmWdItemDataResults != null && etWcmWdItemDataResults.size() > 0) {
                                for (Orders_SER.EtWcmWdItemData_Result eWDI : etWcmWdItemDataResults) {
                                    String EtWcmWdItemData_sql = "Insert into EtWcmWdItemData (Wcnr, Wcitm, Objnr, " +
                                            "Itmtyp, Seq, Pred, Succ, Ccobj, Cctyp, Stxt, Tggrp, Tgstep, Tgproc, " +
                                            "Tgtyp, Tgseq, Tgtxt, Unstep, Unproc, Untyp, Unseq, Untxt, Phblflg, " +
                                            "Phbltyp, Phblnr, Tgflg, Tgform, Tgnr, Unform, Unnr, Control, Location, " +
                                            "Refobj, Action, Btg, Etg, Bug, Eug) values(?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                            "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement EtWcmWdItemData_statement = App_db.compileStatement(EtWcmWdItemData_sql);
                                    EtWcmWdItemData_statement.clearBindings();
                                    EtWcmWdItemData_statement.bindString(1, c_e.check_empty(eWDI.getWcnr()));
                                    EtWcmWdItemData_statement.bindString(2, c_e.check_empty(eWDI.getWcitm()));
                                    EtWcmWdItemData_statement.bindString(3, c_e.check_empty(eWDI.getObjnr()));
                                    EtWcmWdItemData_statement.bindString(4, c_e.check_empty(eWDI.getItmtyp()));
                                    EtWcmWdItemData_statement.bindString(5, c_e.check_empty(eWDI.getSeq()));
                                    EtWcmWdItemData_statement.bindString(6, c_e.check_empty(eWDI.getPred()));
                                    EtWcmWdItemData_statement.bindString(7, c_e.check_empty(eWDI.getSucc()));
                                    EtWcmWdItemData_statement.bindString(8, c_e.check_empty(eWDI.getCcobj()));
                                    EtWcmWdItemData_statement.bindString(9, c_e.check_empty(eWDI.getCctyp()));
                                    EtWcmWdItemData_statement.bindString(10, c_e.check_empty(eWDI.getStxt()));
                                    EtWcmWdItemData_statement.bindString(11, c_e.check_empty(eWDI.getTggrp()));
                                    EtWcmWdItemData_statement.bindString(12, c_e.check_empty(eWDI.getTgstep()));
                                    EtWcmWdItemData_statement.bindString(13, c_e.check_empty(eWDI.getTgproc()));
                                    EtWcmWdItemData_statement.bindString(14, c_e.check_empty(eWDI.getTgtyp()));
                                    EtWcmWdItemData_statement.bindString(15, c_e.check_empty(eWDI.getTgseq()));
                                    EtWcmWdItemData_statement.bindString(16, c_e.check_empty(eWDI.getTgtxt()));
                                    EtWcmWdItemData_statement.bindString(17, c_e.check_empty(eWDI.getUnstep()));
                                    EtWcmWdItemData_statement.bindString(18, c_e.check_empty(eWDI.getUnproc()));
                                    EtWcmWdItemData_statement.bindString(19, c_e.check_empty(eWDI.getUntyp()));
                                    EtWcmWdItemData_statement.bindString(20, c_e.check_empty(eWDI.getUnseq()));
                                    EtWcmWdItemData_statement.bindString(21, c_e.check_empty(eWDI.getUntxt()));
                                    EtWcmWdItemData_statement.bindString(22, c_e.check_empty(eWDI.getPhblflg()));
                                    EtWcmWdItemData_statement.bindString(23, c_e.check_empty(eWDI.getPhbltyp()));
                                    EtWcmWdItemData_statement.bindString(24, c_e.check_empty(eWDI.getPhblnr()));
                                    EtWcmWdItemData_statement.bindString(25, c_e.check_empty(eWDI.getTgflg()));
                                    EtWcmWdItemData_statement.bindString(26, c_e.check_empty(eWDI.getTgform()));
                                    EtWcmWdItemData_statement.bindString(27, c_e.check_empty(eWDI.getTgnr()));
                                    EtWcmWdItemData_statement.bindString(28, c_e.check_empty(eWDI.getUnform()));
                                    EtWcmWdItemData_statement.bindString(29, c_e.check_empty(eWDI.getUnnr()));
                                    EtWcmWdItemData_statement.bindString(30, c_e.check_empty(eWDI.getControl()));
                                    EtWcmWdItemData_statement.bindString(31, c_e.check_empty(eWDI.getLocation()));
                                    EtWcmWdItemData_statement.bindString(32, c_e.check_empty(eWDI.getRefobj()));
                                    EtWcmWdItemData_statement.bindString(33, c_e.check_empty(eWDI.getAction()));
                                    EtWcmWdItemData_statement.bindString(34, c_e.check_empty(eWDI.getBtg()));
                                    EtWcmWdItemData_statement.bindString(35, c_e.check_empty(eWDI.getEtg()));
                                    EtWcmWdItemData_statement.bindString(36, c_e.check_empty(eWDI.getBug()));
                                    EtWcmWdItemData_statement.bindString(37, c_e.check_empty(eWDI.getEug()));
                                    EtWcmWdItemData_statement.execute();
                                }
                            }
                        }

                        /*EtWcmWcagns*/
                        Orders_SER.EtWcmWcagns etWcmWcagns = results.get(0).getEtWcmWcagns();
                        if (etWcmWcagns != null) {
                            List<Orders_SER.EtWcmWcagns_Result> etWcmWcagnsResults = etWcmWcagns.getResults();
                            if (etWcmWcagnsResults != null && etWcmWcagnsResults.size() > 0) {
                                for (Orders_SER.EtWcmWcagns_Result eWCG : etWcmWcagnsResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eWCG.getAufnr())) {
                                            String EtWcmWcagns_sql = "Insert into EtWcmWcagns (UUID, Aufnr, Objnr, " +
                                                    "Counter, Objart, Objtyp, Pmsog, Gntxt, Geniakt, Genvname, " +
                                                    "Action, Werks, Crname, Hilvl, Procflg, Direction, Copyflg, " +
                                                    "Mandflg, Deacflg, Status, Asgnflg, Autoflg, Agent, Valflg," +
                                                    " Wcmuse, Gendatum, Gentime)" +
                                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtWcmWcagns_statement = App_db.compileStatement(EtWcmWcagns_sql);
                                            EtWcmWcagns_statement.clearBindings();
                                            EtWcmWcagns_statement.bindString(1, uD.get("UUID"));
                                            EtWcmWcagns_statement.bindString(2, c_e.check_empty(eWCG.getAufnr()));
                                            EtWcmWcagns_statement.bindString(3, c_e.check_empty(eWCG.getObjnr()));
                                            EtWcmWcagns_statement.bindString(4, c_e.check_empty(eWCG.getCounter()));
                                            EtWcmWcagns_statement.bindString(5, c_e.check_empty(eWCG.getObjart()));
                                            EtWcmWcagns_statement.bindString(6, c_e.check_empty(eWCG.getObjtyp()));
                                            EtWcmWcagns_statement.bindString(7, c_e.check_empty(eWCG.getPmsog()));
                                            EtWcmWcagns_statement.bindString(8, c_e.check_empty(eWCG.getGntxt()));
                                            EtWcmWcagns_statement.bindString(9, c_e.check_empty(eWCG.getGeniakt()));
                                            EtWcmWcagns_statement.bindString(10, c_e.check_empty(eWCG.getGenvname()));
                                            EtWcmWcagns_statement.bindString(11, c_e.check_empty(eWCG.getAction()));
                                            EtWcmWcagns_statement.bindString(12, c_e.check_empty(eWCG.getWerks()));
                                            EtWcmWcagns_statement.bindString(13, c_e.check_empty(eWCG.getCrname()));
                                            EtWcmWcagns_statement.bindString(14, c_e.check_empty(String.valueOf(eWCG.getHilvl())));
                                            EtWcmWcagns_statement.bindString(15, c_e.check_empty(eWCG.getProcflg()));
                                            EtWcmWcagns_statement.bindString(16, c_e.check_empty(eWCG.getDirection()));
                                            EtWcmWcagns_statement.bindString(17, c_e.check_empty(eWCG.getCopyflg()));
                                            EtWcmWcagns_statement.bindString(18, c_e.check_empty(eWCG.getMandflg()));
                                            EtWcmWcagns_statement.bindString(19, c_e.check_empty(eWCG.getDeacflg()));
                                            EtWcmWcagns_statement.bindString(20, c_e.check_empty(eWCG.getStatus()));
                                            EtWcmWcagns_statement.bindString(21, c_e.check_empty(eWCG.getAsgnflg()));
                                            EtWcmWcagns_statement.bindString(22, c_e.check_empty(eWCG.getAutoflg()));
                                            EtWcmWcagns_statement.bindString(23, c_e.check_empty(eWCG.getAgent()));
                                            EtWcmWcagns_statement.bindString(24, c_e.check_empty(eWCG.getValflg()));
                                            EtWcmWcagns_statement.bindString(25, c_e.check_empty(eWCG.getWcmuse()));
                                            EtWcmWcagns_statement.bindString(26, c_e.check_empty(eWCG.getGendatum()));
                                            EtWcmWcagns_statement.bindString(27, c_e.check_empty(eWCG.getGentime()));
                                            EtWcmWcagns_statement.execute();
                                        }
                                    }
                                }
                            }
                        }

                        /*EtOrderComponenets*/
                        Orders_SER.EtOrderComponents etOrderComponents = results.get(0).getEtOrderComponents();
                        if (etOrderComponents != null) {
                            List<Orders_SER.EtOrderComponents_Result> etOrderComponentsResults = etOrderComponents.getResults();
                            if (etOrderComponentsResults != null && etOrderComponentsResults.size() > 0) {
                                for (Orders_SER.EtOrderComponents_Result eOC : etOrderComponentsResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eOC.getAufnr())) {
                                            String EtOrderComponents_sql = "Insert into EtOrderComponents (UUID, Aufnr, Vornr," +
                                                    " Uvorn, Rsnum, Rspos, Matnr, Werks, Lgort, Posnr, Bdmng, Meins, Postp, " +
                                                    "MatnrText, WerksText, LgortText, PostpText, Usr01, Usr02, Usr03, Usr04, " +
                                                    "Usr05, Action, Wempf, Ablad) " +
                                                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtOrderComponents_statement = App_db.compileStatement(EtOrderComponents_sql);
                                            EtOrderComponents_statement.clearBindings();
                                            EtOrderComponents_statement.bindString(1, uD.get("UUID"));
                                            EtOrderComponents_statement.bindString(2, c_e.check_empty(eOC.getAufnr()));
                                            EtOrderComponents_statement.bindString(3, c_e.check_empty(eOC.getVornr()));
                                            EtOrderComponents_statement.bindString(4, c_e.check_empty(eOC.getUvorn()));
                                            EtOrderComponents_statement.bindString(5, c_e.check_empty(eOC.getRsnum()));
                                            EtOrderComponents_statement.bindString(6, c_e.check_empty(eOC.getRspos()));
                                            EtOrderComponents_statement.bindString(7, c_e.check_empty(eOC.getMatnr()));
                                            EtOrderComponents_statement.bindString(8, c_e.check_empty(eOC.getWerks()));
                                            EtOrderComponents_statement.bindString(9, c_e.check_empty(eOC.getLgort()));
                                            EtOrderComponents_statement.bindString(10, c_e.check_empty(eOC.getPosnr()));
                                            EtOrderComponents_statement.bindString(11, c_e.check_empty(eOC.getBdmng()));
                                            EtOrderComponents_statement.bindString(12, c_e.check_empty(eOC.getMeins()));
                                            EtOrderComponents_statement.bindString(13, c_e.check_empty(eOC.getPostp()));
                                            EtOrderComponents_statement.bindString(14, c_e.check_empty(eOC.getMatnrText()));
                                            EtOrderComponents_statement.bindString(15, c_e.check_empty(eOC.getWerksText()));
                                            EtOrderComponents_statement.bindString(16, c_e.check_empty(eOC.getLgortText()));
                                            EtOrderComponents_statement.bindString(17, c_e.check_empty(eOC.getPostpText()));
                                            EtOrderComponents_statement.bindString(18, c_e.check_empty(eOC.getUsr01()));
                                            EtOrderComponents_statement.bindString(19, c_e.check_empty(eOC.getUsr02()));
                                            EtOrderComponents_statement.bindString(20, c_e.check_empty(eOC.getUsr03()));
                                            EtOrderComponents_statement.bindString(21, c_e.check_empty(eOC.getUsr04()));
                                            EtOrderComponents_statement.bindString(22, c_e.check_empty(eOC.getUsr05()));
                                            EtOrderComponents_statement.bindString(23, c_e.check_empty(eOC.getAction()));
                                            EtOrderComponents_statement.bindString(24, c_e.check_empty(eOC.getWempf()));
                                            EtOrderComponents_statement.bindString(25, c_e.check_empty(eOC.getAblad()));
                                            EtOrderComponents_statement.execute();

                                            Orders_SER.EtOrderHeaderFields etOrderComponentFields = eOC.getEtOrderComponentsFields();
                                            if (etOrderComponentFields != null) {
                                                List<Orders_SER.EtOrderHeaderFields_Result> etOrderComponentFieldResults = etOrderComponentFields.getResults();
                                                if (etOrderComponentFieldResults != null && etOrderComponentFieldResults.size() > 0) {
                                                    String sql1 = "Insert into DUE_ORDERS_EtOrderComponents_FIELDS (UUID, Aufnr, Zdoctype, ZdoctypeItem," +
                                                            " Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype, OperationID, PartID)" +
                                                            " values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (Orders_SER.EtOrderHeaderFields_Result eCF : etOrderComponentFieldResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eOC.getAufnr()));
                                                        statement1.bindString(3, c_e.check_empty(eCF.getZdoctype()));
                                                        statement1.bindString(4, c_e.check_empty(eCF.getZdoctypeItem()));
                                                        statement1.bindString(5, c_e.check_empty(eCF.getTabname()));
                                                        statement1.bindString(6, c_e.check_empty(eCF.getFieldname()));
                                                        statement1.bindString(7, c_e.check_empty(eCF.getValue()));
                                                        statement1.bindString(8, c_e.check_empty(eCF.getFlabel()));
                                                        statement1.bindString(9, c_e.check_empty(eCF.getSequence()));
                                                        statement1.bindString(10, c_e.check_empty(eCF.getLength()));
                                                        statement1.bindString(11, c_e.check_empty(eCF.getDatatype()));
                                                        statement1.bindString(12, c_e.check_empty(eOC.getVornr()));
                                                        statement1.bindString(13, c_e.check_empty(eOC.getPosnr()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*EtImrg*/
                        Orders_SER.EtImrg etImrg = results.get(0).getEtImrg();
                        if (etImrg != null) {
                            List<Orders_SER.EtImrg_Result> etImrgResults = etImrg.getResults();
                            if (etImrgResults != null && etImrgResults.size() > 0) {
                                for (Orders_SER.EtImrg_Result eIM : etImrgResults) {
                                    for (HashMap<String, String> uD : orders_uuid_list) {
                                        if (uD.get("Aufnr").equals(eIM.getAufnr())) {
                                            String EtImrg_sql = "Insert into Orders_EtImrg (UUID,Qmnum, Aufnr, Vornr, " +
                                                    "Mdocm, Point, Mpobj, Mpobt, Psort,Pttxt, Atinn, Idate, Itime, " +
                                                    "Mdtxt,Readr,Atbez,Msehi,Msehl,Readc,Desic,Prest,Docaf, Codct, " +
                                                    "Codgr, Vlcod, Action, Equnr) " +
                                                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtImrg_statement = App_db.compileStatement(EtImrg_sql);
                                            EtImrg_statement.bindString(1, uD.get("UUID"));
                                            EtImrg_statement.bindString(2, c_e.check_empty(eIM.getQmnum()));
                                            EtImrg_statement.bindString(3, c_e.check_empty(eIM.getAufnr()));
                                            EtImrg_statement.bindString(4, c_e.check_empty(eIM.getVornr()));
                                            EtImrg_statement.bindString(5, c_e.check_empty(eIM.getMdocm()));
                                            EtImrg_statement.bindString(6, c_e.check_empty(eIM.getPoint()));
                                            EtImrg_statement.bindString(7, c_e.check_empty(eIM.getMpobj()));
                                            EtImrg_statement.bindString(8, c_e.check_empty(eIM.getMpobt()));
                                            EtImrg_statement.bindString(9, c_e.check_empty(eIM.getPsort()));
                                            EtImrg_statement.bindString(10, c_e.check_empty(eIM.getPttxt()));
                                            EtImrg_statement.bindString(11, c_e.check_empty(eIM.getAtinn()));
                                            EtImrg_statement.bindString(12, c_e.check_empty(eIM.getIdate()));
                                            EtImrg_statement.bindString(13, c_e.check_empty(eIM.getItime()));
                                            EtImrg_statement.bindString(14, c_e.check_empty(eIM.getMdtxt()));
                                            EtImrg_statement.bindString(15, c_e.check_empty(eIM.getReadr()));
                                            EtImrg_statement.bindString(16, c_e.check_empty(eIM.getAtbez()));
                                            EtImrg_statement.bindString(17, c_e.check_empty(eIM.getMsehi()));
                                            EtImrg_statement.bindString(18, c_e.check_empty(eIM.getMsehl()));
                                            EtImrg_statement.bindString(19, c_e.check_empty(eIM.getReadc()));
                                            EtImrg_statement.bindString(20, c_e.check_empty(eIM.getDesic()));
                                            EtImrg_statement.bindString(21, c_e.check_empty(eIM.getPrest()));
                                            EtImrg_statement.bindString(22, c_e.check_empty(eIM.getDocaf()));
                                            EtImrg_statement.bindString(23, c_e.check_empty(eIM.getCodct()));
                                            EtImrg_statement.bindString(24, c_e.check_empty(eIM.getCodgr()));
                                            EtImrg_statement.bindString(25, c_e.check_empty(eIM.getVlcod()));
                                            EtImrg_statement.bindString(26, c_e.check_empty(eIM.getAction()));
                                            EtImrg_statement.bindString(27, c_e.check_empty(eIM.getEqunr()));
                                            EtImrg_statement.execute();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                    Get_Response = "success";
                }
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
