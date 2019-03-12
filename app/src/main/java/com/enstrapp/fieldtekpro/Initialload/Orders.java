package com.enstrapp.fieldtekpro.Initialload;

import android.content.ContentValues;
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
import okhttp3.ResponseBody;
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
            Call<ResponseBody> call = service.getORDERDetails(url_link, basic, map);
            Response<ResponseBody> response = call.execute();
            int response_status_code = response.code();
            if (response_status_code == 200) {
                Get_Response = response.body().string();
              /*  if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                        App_db.beginTransaction();
                        try {
                            *//*EtOrderHeader*//*
                            if (response.body().getD().getResults().get(0).getEtOrderHeader() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderHeader().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderHeader().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderHeader_Result orderHeader_result : response.body().getD().getResults()
                                            .get(0).getEtOrderHeader().getResults()) {
                                        values.put("UUID", orderHeader_result.getAufnr());
                                        values.put("Aufnr", orderHeader_result.getAufnr());
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
                                        values.put("Ausvn", orderHeader_result.getAusvn());
                                        values.put("Ausbs", orderHeader_result.getAusbs());
                                        values.put("Auswk", orderHeader_result.getAuswk());
                                        values.put("Qmnam", orderHeader_result.getQmnam());
                                        values.put("ParnrVw", orderHeader_result.getParnrVw());
                                        values.put("NameVw", orderHeader_result.getNameVw());
                                        values.put("Posid", orderHeader_result.getPosid());
                                        values.put("Revnr", orderHeader_result.getRevnr());
                                        App_db.insert("DUE_ORDERS_EtOrderHeader", null, values);
                                        if (orderHeader_result.getEtOrderHeaderFields() != null)
                                            if (orderHeader_result.getEtOrderHeaderFields().getResults() != null
                                                    && orderHeader_result.getEtOrderHeaderFields().getResults().size() > 0) {
                                                ContentValues ValuesHCf = new ContentValues();
                                                for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields_result : orderHeader_result.getEtOrderHeaderFields().getResults()) {
                                                    ValuesHCf.put("UUID", orderHeader_result.getAufnr());
                                                    ValuesHCf.put("Aufnr", orderHeader_result.getAufnr());
                                                    ValuesHCf.put("Zdoctype", etOrderHeaderFields_result.getZdoctype());
                                                    ValuesHCf.put("ZdoctypeItem", etOrderHeaderFields_result.getZdoctypeItem());
                                                    ValuesHCf.put("Tabname", etOrderHeaderFields_result.getTabname());
                                                    ValuesHCf.put("Fieldname", etOrderHeaderFields_result.getFieldname());
                                                    ValuesHCf.put("Value", etOrderHeaderFields_result.getValue());
                                                    ValuesHCf.put("Flabel", etOrderHeaderFields_result.getFlabel());
                                                    ValuesHCf.put("Sequence", etOrderHeaderFields_result.getSequence());
                                                    ValuesHCf.put("Length", etOrderHeaderFields_result.getLength());
                                                    ValuesHCf.put("Datatype", etOrderHeaderFields_result.getDatatype());
                                                    App_db.insert("EtOrderHeader_CustomInfo", null, ValuesHCf);
                                                }
                                            }

                                    }
                                }
                            *//*EtOrderOperations*//*
                            if (response.body().getD().getResults().get(0).getEtOrderOperations() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderOperations().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderOperations().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderOperations_Result orderOperations : response.body().getD().getResults()
                                            .get(0).getEtOrderOperations().getResults()) {
                                        values.put("UUID", orderOperations.getAufnr());
                                        values.put("Aufnr", orderOperations.getAufnr());
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

                                        if (orderOperations.getEtOrderOperationsFields() != null)
                                            if (orderOperations.getEtOrderOperationsFields().getResults() != null
                                                    && orderOperations.getEtOrderOperationsFields().getResults().size() > 0) {
                                                ContentValues ValuesOCf = new ContentValues();
                                                for (Orders_SER.EtOrderHeaderFields_Result etOrderHeaderFields : orderOperations.getEtOrderOperationsFields().getResults()) {
                                                    ValuesOCf.put("UUID", orderOperations.getAufnr());
                                                    ValuesOCf.put("Aufnr", orderOperations.getAufnr());
                                                    ValuesOCf.put("Zdoctype", etOrderHeaderFields.getZdoctype());
                                                    ValuesOCf.put("ZdoctypeItem", etOrderHeaderFields.getZdoctypeItem());
                                                    ValuesOCf.put("Tabname", etOrderHeaderFields.getTabname());
                                                    ValuesOCf.put("Fieldname", etOrderHeaderFields.getFieldname());
                                                    ValuesOCf.put("Value", etOrderHeaderFields.getValue());
                                                    ValuesOCf.put("Flabel", etOrderHeaderFields.getFlabel());
                                                    ValuesOCf.put("Sequence", etOrderHeaderFields.getSequence());
                                                    ValuesOCf.put("Length", etOrderHeaderFields.getLength());
                                                    ValuesOCf.put("Datatype", etOrderHeaderFields.getDatatype());
                                                    ValuesOCf.put("OperationID", orderOperations.getVornr());
                                                    App_db.insert("DUE_ORDERS_EtOrderOperations_FIELDS", null, ValuesOCf);
                                                }
                                            }
                                    }
                                }

                            *//*EtOrderLongtext*//*
                            if (response.body().getD().getResults().get(0).getEtOrderLongtext() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderLongtext().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderLongtext().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderLongtext_Result orderLongtext_result : response.body().getD().getResults()
                                            .get(0).getEtOrderLongtext().getResults()) {
                                        values.put("UUID", orderLongtext_result.getAufnr());
                                        values.put("Aufnr", orderLongtext_result.getAufnr());
                                        values.put("Activity", orderLongtext_result.getActivity());
                                        values.put("TextLine", orderLongtext_result.getTextLine());
                                        values.put("Tdid", orderLongtext_result.getTdid());
                                        App_db.insert("DUE_ORDERS_Longtext", null, values);
                                    }
                                }

                            *//*EtOrderOlist*//*
                            if (response.body().getD().getResults().get(0).getEtOrderOlist() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderOlist().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtOrderOlist().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderOlist_Result orderOlist_result : response.body().getD().getResults()
                                            .get(0).getEtOrderOlist().getResults()) {
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

                            *//*EtOrderStatus*//*
                            if (response.body().getD().getResults().get(0).getEtOrderStatus() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderStatus().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderStatus().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderStatus_Result orderStatus_result : response.body().getD().getResults()
                                            .get(0).getEtOrderStatus().getResults()) {
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

                            *//*EtDocs*//*
                            if (response.body().getD().getResults().get(0).getEtDocs() != null)
                                if (response.body().getD().getResults().get(0).getEtDocs().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtDocs().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtDocs_Result etDocs_result : response.body().getD().getResults()
                                            .get(0).getEtDocs().getResults()) {
                                        values.put("UUID", etDocs_result.getZobjid());
                                        values.put("Zobjid", etDocs_result.getZobjid());
                                        values.put("Zdoctype", etDocs_result.getZdoctype());
                                        values.put("ZdoctypeItem", etDocs_result.getZdoctypeItem());
                                        values.put("Filename", etDocs_result.getFilename());
                                        values.put("Filetype", etDocs_result.getFiletype());
                                        values.put("Fsize", etDocs_result.getFsize());
                                        values.put("Content", etDocs_result.getContent());
                                        values.put("DocId", etDocs_result.getDocID());
                                        values.put("DocType", etDocs_result.getDocType());
                                        values.put("Objtype", etDocs_result.getObjtype());
                                        App_db.insert("DUE_ORDERS_EtDocs", null, values);
                                    }
                                }

                            *//*EtWcmWwData*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWwData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWwData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWwData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWwData_Result wcmWwData_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWwData().getResults()) {
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

                            *//*EtWcmWaData*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWaData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWaData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWaData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWaData_Result wcmWaData_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWaData().getResults()) {
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

                            *//*EtWcmWaChkReq*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWaChkReq() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWaChkReq().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWaChkReq_Result wcmWaChkReq_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWaChkReq().getResults()) {
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

                            *//*EtWcmWdData*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWdData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWdData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWdData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWdData_Result wcmWdData_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWdData().getResults()) {
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

                                        *//*EtWcmWdDataTagtext*//*
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

                                        *//*EtWcmWdDataUntagtext*//*
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

                            *//*EtWcmWdItemData*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWdItemData() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWdItemData().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtWcmWdItemData().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWdItemData_Result wcmWdItemData_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWdItemData().getResults()) {
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

                            *//*EtWcmWcagns*//*
                            if (response.body().getD().getResults().get(0).getEtWcmWcagns() != null)
                                if (response.body().getD().getResults().get(0).getEtWcmWcagns().getResults() != null
                                        && response.body().getD().getResults().get(0).getEtWcmWcagns().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtWcmWcagns_Result wcmWcagns_result : response.body().getD().getResults()
                                            .get(0).getEtWcmWcagns().getResults()) {
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

                            *//*EtOrderComponenets*//*
                            if (response.body().getD().getResults().get(0).getEtOrderComponents() != null)
                                if (response.body().getD().getResults().get(0).getEtOrderComponents().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtOrderComponents().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtOrderComponents_Result etOrderComponents_result : response.body().getD().getResults()
                                            .get(0).getEtOrderComponents().getResults()) {
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

                            *//*EtImrg*//*
                            if (response.body().getD().getResults().get(0).getEtImrg() != null)
                                if (response.body().getD().getResults().get(0).getEtImrg().getResults() != null &&
                                        response.body().getD().getResults().get(0).getEtImrg().getResults().size() > 0) {
                                    ContentValues values = new ContentValues();
                                    for (Orders_SER.EtImrg_Result etImrg_result : response.body().getD().getResults()
                                            .get(0).getEtImrg().getResults()) {
                                        values.put("UUID", etImrg_result.getAufnr());
                                        values.put("Qmnum", etImrg_result.getQmnum());
                                        values.put("Aufnr", etImrg_result.getAufnr());
                                        values.put("Vornr", etImrg_result.getVornr());
                                        values.put("Mdocm", etImrg_result.getMdocm());
                                        values.put("Point", etImrg_result.getPoint());
                                        values.put("Mpobj", etImrg_result.getMpobj());
                                        values.put("Mpobt", etImrg_result.getMpobt());
                                        values.put("Psort", etImrg_result.getPsort());
                                        values.put("Pttxt", etImrg_result.getPttxt());
                                        values.put("Atinn", etImrg_result.getAtinn());
                                        values.put("Idate", etImrg_result.getIdate());
                                        values.put("Itime", etImrg_result.getItime());
                                        values.put("Mdtxt", etImrg_result.getMdtxt());
                                        values.put("Readr", etImrg_result.getReadr());
                                        values.put("Atbez", etImrg_result.getAtbez());
                                        values.put("Msehi", etImrg_result.getMsehi());
                                        values.put("Msehl", etImrg_result.getMsehl());
                                        values.put("Readc", etImrg_result.getReadc());
                                        values.put("Desic", etImrg_result.getDesic());
                                        values.put("Prest", etImrg_result.getPrest());
                                        values.put("Docaf", etImrg_result.getDocaf());
                                        values.put("Codct", etImrg_result.getCodct());
                                        values.put("Codgr", etImrg_result.getCodgr());
                                        values.put("Vlcod", etImrg_result.getVlcod());
                                        values.put("Action", etImrg_result.getAction());
                                        values.put("Equnr", etImrg_result.getEqunr());
                                        App_db.insert("Orders_EtImrg", null, values);
                                    }
                                }

                            App_db.setTransactionSuccessful();
                            Get_Response = "success";
                        } finally {
                            App_db.endTransaction();
                        }
                    }
                }*/
            }
        } catch (Exception ex) {
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
