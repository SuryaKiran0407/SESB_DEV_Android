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

public class Notifications {

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> notifications_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty checkempty = new Check_Empty();


    /* Header Custom_Info Table and Fields Names */
    private static final String TABLE_Custom_Info_Fields = "EtNotifHeader_CustomInfo";
    private static final String KEY_Custom_Info_id = "id";
    private static final String KEY_Custom_Info_uuid = "UUID";
    private static final String KEY_Custom_Info_Qmnum = "Qmnum";
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


    /* Task Custom_Info Table and Fields Names */
    private static final String TABLE_Task_Custom_Info_Fields = "EtNotifTask_CustomInfo";
    private static final String KEY_Task_Custom_Info_id = "id";
    private static final String KEY_Task_Custom_Info_uuid = "UUID";
    private static final String KEY_Task_Custom_Info_Qmnum = "Qmnum";
    private static final String KEY_Task_Custom_Info_Zdoctype = "Zdoctype";
    private static final String KEY_Task_Custom_Info_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_Task_Custom_Info_Tabname = "Tabname";
    private static final String KEY_Task_Custom_Info_Fieldname = "Fieldname";
    private static final String KEY_Task_Custom_Info_Value = "Value";
    private static final String KEY_Task_Custom_Info_Flabel = "Flabel";
    private static final String KEY_Task_Custom_Info_Sequence = "Sequence";
    private static final String KEY_Task_Custom_Info_Length = "Length";
    private static final String KEY_Task_Custom_Info_Datatype = "Datatype";
    /* Task Custom_Info Table and Fields Names */


    /* Activity Custom_Info Table and Fields Names */
    private static final String TABLE_Activity_Custom_Info_Fields = "EtNotifActivity_CustomInfo";
    private static final String KEY_Activity_Custom_Info_id = "id";
    private static final String KEY_Activity_Custom_Info_uuid = "UUID";
    private static final String KEY_Activity_Custom_Info_Qmnum = "Qmnum";
    private static final String KEY_Activity_Custom_Info_ActvKey = "ActvKey";
    private static final String KEY_Activity_Custom_Info_Zdoctype = "Zdoctype";
    private static final String KEY_Activity_Custom_Info_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_Activity_Custom_Info_Tabname = "Tabname";
    private static final String KEY_Activity_Custom_Info_Fieldname = "Fieldname";
    private static final String KEY_Activity_Custom_Info_Value = "Value";
    private static final String KEY_Activity_Custom_Info_Flabel = "Flabel";
    private static final String KEY_Activity_Custom_Info_Sequence = "Sequence";
    private static final String KEY_Activity_Custom_Info_Length = "Length";
    private static final String KEY_Activity_Custom_Info_Datatype = "Datatype";
    /* Activity Custom_Info Table and Fields Names */


    /* GET_DUE_NOTIFICATION_NotifHeader Table and Fields Names */
    private static final String TABLE_GET_DUE_NOTIFICATIONS_NotifHeader = "DUE_NOTIFICATION_NotifHeader";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_ID = "id";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_NotifType = "NotifType";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_NotifShorttxt = "NotifShorttxt";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_FunctionLoc = "FunctionLoc";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Equipment = "Equipment";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Bautl = "Bautl";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_ReportedBy = "ReportedBy";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncStdate = "MalfuncStdate";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEddate = "MalfuncEddate";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncSttime = "MalfuncSttime";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEdtime = "MalfuncEdtime";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_BreakdownInd = "BreakdownInd";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Priority = "Priority";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrp = "Ingrp";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Arbpl = "Arbpl";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Werks = "Werks";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Strmn = "Strmn";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrmn = "Ltrmn";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Aufnr = "Aufnr";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Docs = "Docs";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Altitude = "Altitude";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Latitude = "Latitude";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Longitude = "Longitude";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Closed = "Closed";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Completed = "Completed";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Createdon = "Createdon";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Qmartx = "Qmartx";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Pltxt = "Pltxt";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Eqktx = "Eqktx";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Priokx = "Priokx";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Auftext = "Auftext";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Auarttext = "Auarttext";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Plantname = "Plantname";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Wkctrname = "Wkctrname";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrpname = "Ingrpname";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Maktx = "Maktx";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Xstatus = "Xstatus";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Usr01 = "Usr01";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Usr02 = "Usr02";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Usr03 = "Usr03";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Usr04 = "Usr04";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Usr05 = "Usr05";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_status = "STATUS";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_ParnrVw = "ParnrVw";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_NameVw = "NameVw";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Auswk = "Auswk";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Shift = "Shift";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Noofperson = "Noofperson";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Auswkt = "Auswkt";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Strur = "Strur";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrur = "Ltrur";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_sort_malfc = "sort_malfc";
    private static final String KEY_DUE_NOTIFICATIONS_NotifHeader_Qmdat = "Qmdat";
    /* GET_DUE_NOTIFICATION_NotifHeader Table and Fields Names */


    /* GET_DUE_NOTIFICATION_EtNotifItems_DATA Table and Fields Names */
    private static final String TABLE_DUE_NOTIFICATIONS_EtNotifItems = "DUE_NOTIFICATIONS_EtNotifItems";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ID = "id";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemKey = "ItemKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartGrp = "ItempartGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Partgrptext = "Partgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartCod = "ItempartCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Partcodetext = "Partcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectGrp = "ItemdefectGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectgrptext = "Defectgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectCod = "ItemdefectCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectcodetext = "Defectcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectShtxt = "ItemdefectShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseKey = "CauseKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseGrp = "CauseGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Causegrptext = "Causegrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseCod = "CauseCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Causecodetext = "Causecodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseShtxt = "CauseShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr01 = "Usr01";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr02 = "Usr02";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr03 = "Usr03";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr04 = "Usr04";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr05 = "Usr05";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Status = "Status";
    /* GET_DUE_NOTIFICATION_EtNotifItems_DATA Table and Fields Names */


    /* GET_DUE_NOTIFICATION_EtNotifItems_Fields_DATA Table and Fields Names */
    private static final String TABLE_GET_DUE_NOTIFICATION_EtNotifItems_Fields = "EtNotifItems_CustomInfo";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ID = "id";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ItemKey = "ItemKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_CauseKey = "CauseKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Zdoctype = "Zdoctype";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Tabname = "Tabname";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Fieldname = "Fieldname";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Value = "Value";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Flabel = "Flabel";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Sequence = "Sequence";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Length = "Length";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Datatype = "Datatype";
    /* GET_DUE_NOTIFICATION_EtNotifItems_Fields_DATA Table and Fields Names */


    /*GET_DUE_NOTIFICATION_EtNotifActvs_DATA Table and Fields Names*/
    private static final String TABLE_GET_DUE_NOTIFICATION_EtNotifActvs = "DUE_NOTIFICATION_EtNotifActvs";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_id = "id";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemKey = "ItemKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartGrp = "ItempartGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partgrptext = "Partgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartCod = "ItempartCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partcodetext = "Partcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectGrp = "ItemdefectGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectgrptext = "Defectgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectCod = "ItemdefectCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectcodetext = "Defectcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectShtxt = "ItemdefectShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_CauseKey = "CauseKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvKey = "ActvKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvGrp = "ActvGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actgrptext = "Actgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvCod = "ActvCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actcodetext = "Actcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvShtxt = "ActvShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartDate = "StartDate";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartTime = "StartTime";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndDate = "EndDate";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndTime = "EndTime";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr01 = "Usr01";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr02 = "Usr02";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr03 = "Usr03";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr04 = "Usr04";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr05 = "Usr05";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Fields = "Fields";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifActvs_Action = "Action";
    /*GET_DUE_NOTIFICATION_EtNotifActvs_DATA Table and Fields Names*/


    /* GET_DUE_NOTIFICATIONS_EtNotifLongtext Table and Fields Names */
    private static final String TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext = "DUE_NOTIFICATIONS_EtNotifLongtext";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_ID = "id";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_Objtype = "Objtype";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_TextLine = "TextLine";
    private static final String KEY_DUE_NOTIFICATIONS_LONGTEXT_Objkey = "Objkey";
    /* GET_DUE_NOTIFICATIONS_EtNotifLongtext Table and Fields Names */


    /* EtNotifStatus Table and Fields Names */
    private static final String TABLE_EtNotifStatus = "EtNotifStatus";
    private static final String KEY_EtNotifStatus_ID = "ID";
    private static final String KEY_EtNotifStatus_UUID = "UUID";
    private static final String KEY_EtNotifStatus_Qmnum = "Qmnum";
    private static final String KEY_EtNotifStatus_Aufnr = "Aufnr";
    private static final String KEY_EtNotifStatus_Objnr = "Objnr";
    private static final String KEY_EtNotifStatus_Manum = "Manum";
    private static final String KEY_EtNotifStatus_Stsma = "Stsma";
    private static final String KEY_EtNotifStatus_Inist = "Inist";
    private static final String KEY_EtNotifStatus_Stonr = "Stonr";
    private static final String KEY_EtNotifStatus_Hsonr = "Hsonr";
    private static final String KEY_EtNotifStatus_Nsonr = "Nsonr";
    private static final String KEY_EtNotifStatus_Stat = "Stat";
    private static final String KEY_EtNotifStatus_Act = "Act";
    private static final String KEY_EtNotifStatus_Txt04 = "Txt04";
    private static final String KEY_EtNotifStatus_Txt30 = "Txt30";
    private static final String KEY_EtNotifStatus_Action = "Action";
    /* EtNotifStatus Table and Fields Names */


    /* GET_DUE_NOTIFICATION_EtDocs Table and Fields Names */
    private static final String TABLE_GET_DUE_NOTIFICATION_EtDocs = "DUE_NOTIFICATION_EtDocs";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_ID = "id";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Zobjid = "Zobjid";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Zdoctype = "Zdoctype";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_ZdoctypeItem = "ZdoctypeItem";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Filename = "Filename";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Filetype = "Filetype";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Fsize = "Fsize";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Content = "Content";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_DocId = "DocId";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_DocType = "DocType";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Objtype = "Objtype";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Filepath = "Filepath";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Status = "Status";
    private static final String KEY_DUE_NOTIFICATIONS_EtDocs_Contentx = "Contentx";
    /* GET_DUE_NOTIFICATION_EtDocs Table and Fields Names */


    /*GET_DUE_NOTIFICATION_EtNotifTasks_DATA Table and Fields Names*/
    private static final String TABLE_GET_DUE_NOTIFICATION_EtNotifTasks = "DUE_NOTIFICATION_EtNotifTasks";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_id = "id";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_UUID = "UUID";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Qmnum = "Qmnum";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemKey = "ItemKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartGrp = "ItempartGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partgrptext = "Partgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartCod = "ItempartCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partcodetext = "Partcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectGrp = "ItemdefectGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectgrptext = "Defectgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectCod = "ItemdefectCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectcodetext = "Defectcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectShtxt = "ItemdefectShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskKey = "TaskKey";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskGrp = "TaskGrp";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskgrptext = "Taskgrptext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskCod = "TaskCod";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskcodetext = "Taskcodetext";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskShtxt = "TaskShtxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pster = "Pster";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Peter = "Peter";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pstur = "Pstur";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Petur = "Petur";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parvw = "Parvw";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parnr = "Parnr";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlnam = "Erlnam";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erldat = "Erldat";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlzeit = "Erlzeit";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Release = "Release";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Complete = "Complete";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Success = "Success";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_UserStatus = "UserStatus";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_SysStatus = "SysStatus";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smsttxt = "Smsttxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smastxt = "Smastxt";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr01 = "Usr01";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr02 = "Usr02";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr03 = "Usr03";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr04 = "Usr04";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr05 = "Usr05";
    private static final String KEY_DUE_NOTIFICATIONS_EtNotifTasks_Action = "Action";
    /*GET_DUE_NOTIFICATION_EtNotifTasks_DATA Table and Fields Names*/


    public static String Get_DNOT_Data(Activity activity, String transmit_type) {
        try {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            if (transmit_type.equals("LOAD")) {
                /* Creating Custom_Info Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Custom_Info_Fields);
                String Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Custom_Info_Fields + ""
                        + "( "
                        + KEY_Custom_Info_id + " INTEGER PRIMARY KEY,"
                        + KEY_Custom_Info_uuid + " TEXT,"
                        + KEY_Custom_Info_Qmnum + " TEXT,"
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
                /* Creating Custom_Info Table with Fields */


                /* Task Creating Custom_Info Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Task_Custom_Info_Fields);
                String Task_Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Task_Custom_Info_Fields + ""
                        + "( "
                        + KEY_Task_Custom_Info_id + " INTEGER PRIMARY KEY,"
                        + KEY_Task_Custom_Info_uuid + " TEXT,"
                        + KEY_Task_Custom_Info_Qmnum + " TEXT,"
                        + KEY_Task_Custom_Info_Zdoctype + " TEXT,"
                        + KEY_Task_Custom_Info_ZdoctypeItem + " TEXT,"
                        + KEY_Task_Custom_Info_Tabname + " TEXT,"
                        + KEY_Task_Custom_Info_Fieldname + " TEXT,"
                        + KEY_Task_Custom_Info_Value + " TEXT,"
                        + KEY_Task_Custom_Info_Flabel + " TEXT,"
                        + KEY_Task_Custom_Info_Sequence + " TEXT,"
                        + KEY_Task_Custom_Info_Length + " TEXT,"
                        + KEY_Task_Custom_Info_Datatype + " TEXT"
                        + ")";
                App_db.execSQL(Task_Custom_Info_Fields_TABLE);
                /* Task Creating Custom_Info Table with Fields */


                /* Activity Custom_Info Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_Activity_Custom_Info_Fields);
                String Activity_Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_Activity_Custom_Info_Fields + ""
                        + "( "
                        + KEY_Activity_Custom_Info_id + " INTEGER PRIMARY KEY,"
                        + KEY_Activity_Custom_Info_uuid + " TEXT,"
                        + KEY_Activity_Custom_Info_Qmnum + " TEXT,"
                        + KEY_Activity_Custom_Info_ActvKey + " TEXT,"
                        + KEY_Activity_Custom_Info_Zdoctype + " TEXT,"
                        + KEY_Activity_Custom_Info_ZdoctypeItem + " TEXT,"
                        + KEY_Activity_Custom_Info_Tabname + " TEXT,"
                        + KEY_Activity_Custom_Info_Fieldname + " TEXT,"
                        + KEY_Activity_Custom_Info_Value + " TEXT,"
                        + KEY_Activity_Custom_Info_Flabel + " TEXT,"
                        + KEY_Activity_Custom_Info_Sequence + " TEXT,"
                        + KEY_Activity_Custom_Info_Length + " TEXT,"
                        + KEY_Activity_Custom_Info_Datatype + " TEXT"
                        + ")";
                App_db.execSQL(Activity_Custom_Info_Fields_TABLE);
                /* Activity Custom_Info Table with Fields */


                /* Creating DUE_NOTIFICATIONS_EtNotifItems_Fields Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifItems_Fields);
                String CREATE_DUE_NOTIFICATIONS_EtNotifItems_Fields_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifItems_Fields + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ItemKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_CauseKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Zdoctype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_ZdoctypeItem + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Tabname + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Fieldname + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Value + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Flabel + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Sequence + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Length + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Fields_Datatype + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATIONS_EtNotifItems_Fields_TABLE);
                /* Creating DUE_NOTIFICATIONS_EtNotifItems_Fields Table with Fields */

                /* Creating GET_DUE_NOTIFICATION_NotifHeader Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATIONS_NotifHeader);
                String CREATE_DUE_NOTIFICATION_NotifHeader_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATIONS_NotifHeader + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NotifType + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NotifShorttxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_FunctionLoc + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Equipment + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Bautl + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ReportedBy + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncStdate + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEddate + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncSttime + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEdtime + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_BreakdownInd + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Priority + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Arbpl + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Werks + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Strmn + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrmn + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Aufnr + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Docs + " TEXT, "
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Altitude + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Latitude + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Longitude + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Closed + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Completed + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Createdon + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmartx + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Pltxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Eqktx + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Priokx + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auftext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auarttext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Plantname + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Wkctrname + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrpname + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Maktx + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Xstatus + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr01 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr02 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr03 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr04 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr05 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_status + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ParnrVw + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NameVw + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auswk + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Shift + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Noofperson + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auswkt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Strur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_sort_malfc + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmdat + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_NotifHeader_TABLE);
                /* Creating GET_DUE_NOTIFICATION_NotifHeader Table with Fields */

                /* Creating DUE_NOTIFICATIONS_EtNotifItems Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUE_NOTIFICATIONS_EtNotifItems);
                String CREATE_DUE_NOTIFICATIONS_EtNotifItems = "CREATE TABLE IF NOT EXISTS " + TABLE_DUE_NOTIFICATIONS_EtNotifItems + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Partgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Partcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Causegrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Causecodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr01 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr02 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr03 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr04 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr05 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Status + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATIONS_EtNotifItems);
                /* Creating DUE_NOTIFICATIONS_EtNotifItems Table with Fields */

                /* Creating GET_DUE_NOTIFICATION_EtNotifActvs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifActvs);
                String CREATE_DUE_NOTIFICATION_EtNotifActvs_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifActvs + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_id + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_CauseKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartDate + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartTime + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndDate + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndTime + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr01 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr02 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr03 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr04 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr05 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Fields + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifActvs_TABLE);
                /* Creating GET_DUE_NOTIFICATION_EtNotifActvs Table with Fields */


                /* Creating GET_DUE_NOTIFICATION_EtNotifLongtext Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext);
                String CREATE_DUE_NOTIFICATION_EtNotifLongtext_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Objtype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_TextLine + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Objkey + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifLongtext_TABLE);
                /* Creating GET_DUE_NOTIFICATION_EtNotifLongtext Table with Fields */

                /* Creating EtNotifStatus Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifStatus);
                String CREATE_TABLE_EtNotifStatus = "CREATE TABLE IF NOT EXISTS " + TABLE_EtNotifStatus + ""
                        + "( "
                        + KEY_EtNotifStatus_ID + " INTEGER PRIMARY KEY,"
                        + KEY_EtNotifStatus_UUID + " TEXT,"
                        + KEY_EtNotifStatus_Qmnum + " TEXT,"
                        + KEY_EtNotifStatus_Aufnr + " TEXT,"
                        + KEY_EtNotifStatus_Objnr + " TEXT,"
                        + KEY_EtNotifStatus_Manum + " TEXT,"
                        + KEY_EtNotifStatus_Stsma + " TEXT,"
                        + KEY_EtNotifStatus_Inist + " TEXT,"
                        + KEY_EtNotifStatus_Stonr + " TEXT,"
                        + KEY_EtNotifStatus_Hsonr + " TEXT,"
                        + KEY_EtNotifStatus_Nsonr + " TEXT,"
                        + KEY_EtNotifStatus_Stat + " TEXT,"
                        + KEY_EtNotifStatus_Act + " TEXT,"
                        + KEY_EtNotifStatus_Txt04 + " TEXT,"
                        + KEY_EtNotifStatus_Txt30 + " TEXT,"
                        + KEY_EtNotifStatus_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_TABLE_EtNotifStatus);
                /* Creating EtNotifStatus Table with Fields */

                /* Creating DUE_NOTIFICATIONS_EtDocs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtDocs);
                String CREATE_DUE_NOTIFICATIONS_EtDocs_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtDocs + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtDocs_ID + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Zobjid + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Zdoctype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_ZdoctypeItem + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filename + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filetype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Fsize + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Content + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_DocId + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_DocType + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Objtype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filepath + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Status + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Contentx + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATIONS_EtDocs_TABLE);
                /* Creating DUE_NOTIFICATIONS_EtDocs Table with Fields */

                /* Creating GET_DUE_NOTIFICATION_EtNotifTasks Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifTasks);
                String CREATE_DUE_NOTIFICATION_EtNotifTasks_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GET_DUE_NOTIFICATION_EtNotifTasks + ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_id + " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_UUID + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Qmnum + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskKey + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskGrp + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskgrptext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskCod + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskcodetext + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskShtxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pster + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Peter + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pstur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Petur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parvw + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parnr + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlnam + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erldat + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlzeit + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Release + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Complete + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Success + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_UserStatus + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_SysStatus + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smsttxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smastxt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr01 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr02 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr03 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr04 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr05 + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifTasks_TABLE);
                /* Creating GET_DUE_NOTIFICATION_EtNotifTasks Table with Fields */
            } else {
                App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader");
                App_db.execSQL("delete from EtNotifHeader_CustomInfo");
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems");
                App_db.execSQL("delete from EtNotifItems_CustomInfo");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs");
                App_db.execSQL("delete from EtNotifActivity_CustomInfo");
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext");
                App_db.execSQL("delete from EtNotifStatus");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks");
                App_db.execSQL("delete from EtNotifTask_CustomInfo");
            }
            /* Initializing Shared Preferences */
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"C1", "DN", webservice_type});
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
            map.put("Operation", "DUNOT");
            OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
            Interface service = retrofit.create(Interface.class);
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            Call<Notifications_SER> call = service.getNOTIFICATIONDetails(url_link, basic, map);
            Response<Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_DNOT_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    notifications_uuid_list.clear();

                    /*Reading Response Data and Parsing to Serializable*/

                    Notifications_SER rs = response.body();
                    /*Reading Response Data and Parsing to Serializable*/


                    /*Converting GSON Response to JSON Data for Parsing*/

                    String response_data = new Gson().toJson(rs.getD().getResults());
                    /*Converting GSON Response to JSON Data for Parsing*/


                    /*Converting Response JSON Data to JSONArray for Reading*/

                    JSONArray response_data_jsonArray = new JSONArray(response_data);
                    /*Converting Response JSON Data to JSONArray for Reading*/


                    /*Reading Data by using FOR Loop*/

                    if (response_data_jsonArray.length() > 0) {
                        for (int i = 0; i < response_data_jsonArray.length(); i++) {
                            /*Reading Data by using FOR Loop*/

                            JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                            /*Reading and Inserting Data into Database Table for EtNotifHeader UUID*/

                            if (jsonObject.has("EtNotifHeader")) {
                                try {
                                    String EtNotifHeader_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifHeader_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String Aufnr = jsonArray.getJSONObject(j).optString("Qmnum");
                                            HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                                            UUID uniqueKey = UUID.randomUUID();
                                            uuid_hashmap.put("UUID", uniqueKey.toString());
                                            uuid_hashmap.put("Qmnum", checkempty.check_empty(Aufnr));
                                            notifications_uuid_list.add(uuid_hashmap);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifHeader UUID*/


                            App_db.beginTransaction();

                            /*Reading and Inserting Data into Database Table for EtNotifHeader*/

                            if (jsonObject.has("EtNotifHeader")) {
                                try {
                                    String EtNotifHeader_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifHeader().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifHeader_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifHeader_sql = "Insert into DUE_NOTIFICATION_NotifHeader (UUID,NotifType,Qmnum,NotifShorttxt,FunctionLoc,Equipment,Bautl,ReportedBy,MalfuncStdate,MalfuncEddate,MalfuncSttime,MalfuncEdtime,BreakdownInd,Priority,Ingrp,Arbpl,Werks,Strmn,Ltrmn,Aufnr,Docs,Altitude,Latitude,Longitude,Closed,Completed,Createdon,Qmartx,Pltxt,Eqktx,Priokx,Auftext,Auarttext,Plantname,Wkctrname,Ingrpname,Maktx,Xstatus,Usr01,Usr02,Usr03,Usr04,Usr05,STATUS,ParnrVw,NameVw,Auswk,Shift,Noofperson,Auswkt, Strur, Ltrur, sort_malfc, Qmdat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtNotifHeader_statement = App_db.compileStatement(EtNotifHeader_sql);
                                                EtNotifHeader_statement.clearBindings();
                                                EtNotifHeader_statement.bindString(1, uuid);
                                                EtNotifHeader_statement.bindString(2, jsonArray.getJSONObject(j).optString("NotifType"));
                                                EtNotifHeader_statement.bindString(3, Qmnum);
                                                EtNotifHeader_statement.bindString(4, jsonArray.getJSONObject(j).optString("NotifShorttxt"));
                                                EtNotifHeader_statement.bindString(5, jsonArray.getJSONObject(j).optString("FunctionLoc"));
                                                EtNotifHeader_statement.bindString(6, jsonArray.getJSONObject(j).optString("Equipment"));
                                                EtNotifHeader_statement.bindString(7, jsonArray.getJSONObject(j).optString("Bautl"));
                                                EtNotifHeader_statement.bindString(8, jsonArray.getJSONObject(j).optString("ReportedBy"));
                                                EtNotifHeader_statement.bindString(9, jsonArray.getJSONObject(j).optString("MalfuncStdate"));
                                                EtNotifHeader_statement.bindString(10, jsonArray.getJSONObject(j).optString("MalfuncEddate"));
                                                EtNotifHeader_statement.bindString(11, jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                                EtNotifHeader_statement.bindString(12, jsonArray.getJSONObject(j).optString("MalfuncEdtime"));
                                                EtNotifHeader_statement.bindString(13, jsonArray.getJSONObject(j).optString("BreakdownInd"));
                                                EtNotifHeader_statement.bindString(14, jsonArray.getJSONObject(j).optString("Priority"));
                                                EtNotifHeader_statement.bindString(15, jsonArray.getJSONObject(j).optString("Ingrp"));
                                                EtNotifHeader_statement.bindString(16, jsonArray.getJSONObject(j).optString("Arbpl"));
                                                EtNotifHeader_statement.bindString(17, jsonArray.getJSONObject(j).optString("Werks"));
                                                EtNotifHeader_statement.bindString(18, jsonArray.getJSONObject(j).optString("Strmn"));
                                                EtNotifHeader_statement.bindString(19, jsonArray.getJSONObject(j).optString("Ltrmn"));
                                                EtNotifHeader_statement.bindString(20, jsonArray.getJSONObject(j).optString("Aufnr"));
                                                EtNotifHeader_statement.bindString(21, jsonArray.getJSONObject(j).optString("Docs"));
                                                EtNotifHeader_statement.bindString(22, jsonArray.getJSONObject(j).optString("Altitude"));
                                                EtNotifHeader_statement.bindString(23, jsonArray.getJSONObject(j).optString("Latitude"));
                                                EtNotifHeader_statement.bindString(24, jsonArray.getJSONObject(j).optString("Longitude"));
                                                EtNotifHeader_statement.bindString(25, jsonArray.getJSONObject(j).optString("Closed"));
                                                EtNotifHeader_statement.bindString(26, jsonArray.getJSONObject(j).optString("Completed"));
                                                EtNotifHeader_statement.bindString(27, jsonArray.getJSONObject(j).optString("Createdon"));
                                                EtNotifHeader_statement.bindString(28, jsonArray.getJSONObject(j).optString("Qmartx"));
                                                EtNotifHeader_statement.bindString(29, jsonArray.getJSONObject(j).optString("Pltxt"));
                                                EtNotifHeader_statement.bindString(30, jsonArray.getJSONObject(j).optString("Eqktx"));
                                                EtNotifHeader_statement.bindString(31, jsonArray.getJSONObject(j).optString("Priokx"));
                                                EtNotifHeader_statement.bindString(32, jsonArray.getJSONObject(j).optString("Auftext"));
                                                EtNotifHeader_statement.bindString(33, jsonArray.getJSONObject(j).optString("Auarttext"));
                                                EtNotifHeader_statement.bindString(34, jsonArray.getJSONObject(j).optString("Plantname"));
                                                EtNotifHeader_statement.bindString(35, jsonArray.getJSONObject(j).optString("Wkctrname"));
                                                EtNotifHeader_statement.bindString(36, jsonArray.getJSONObject(j).optString("Ingrpname"));
                                                EtNotifHeader_statement.bindString(37, jsonArray.getJSONObject(j).optString("Maktx"));
                                                EtNotifHeader_statement.bindString(38, jsonArray.getJSONObject(j).optString("Xstatus"));
                                                EtNotifHeader_statement.bindString(39, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtNotifHeader_statement.bindString(40, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtNotifHeader_statement.bindString(41, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtNotifHeader_statement.bindString(42, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtNotifHeader_statement.bindString(43, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtNotifHeader_statement.bindString(44, jsonArray.getJSONObject(j).optString("Xstatus"));
                                                EtNotifHeader_statement.bindString(45, jsonArray.getJSONObject(j).optString("ParnrVw"));
                                                EtNotifHeader_statement.bindString(46, jsonArray.getJSONObject(j).optString("NameVw"));
                                                EtNotifHeader_statement.bindString(47, jsonArray.getJSONObject(j).optString("Auswk"));
                                                EtNotifHeader_statement.bindString(48, jsonArray.getJSONObject(j).optString("Shift"));
                                                EtNotifHeader_statement.bindString(49, jsonArray.getJSONObject(j).optString("Noofperson"));
                                                EtNotifHeader_statement.bindString(50, jsonArray.getJSONObject(j).optString("Auswkt"));
                                                EtNotifHeader_statement.bindString(51, jsonArray.getJSONObject(j).optString("Strur"));
                                                EtNotifHeader_statement.bindString(52, jsonArray.getJSONObject(j).optString("Ltrur"));
                                                EtNotifHeader_statement.bindString(53, jsonArray.getJSONObject(j).optString("MalfuncStdate") + " " + jsonArray.getJSONObject(j).optString("MalfuncSttime"));
                                                EtNotifHeader_statement.bindString(54, jsonArray.getJSONObject(j).optString("Qmdat"));
                                                EtNotifHeader_statement.execute();

                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifHeaderFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                    String sql1 = "Insert into EtNotifHeader_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                        statement1.bindString(1, uuid);
                                                        statement1.bindString(2, Qmnum);
                                                        statement1.bindString(3, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }


                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifHeader*/



                            /*Reading and Inserting Data into Database Table for EtNotifItems*/

                            if (jsonObject.has("EtNotifItems")) {
                                try {
                                    String EtNotifItems_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifItems().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifItems_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                                                EtNotifItems_statement.clearBindings();
                                                EtNotifItems_statement.bindString(1, uuid);
                                                EtNotifItems_statement.bindString(2, Qmnum);
                                                EtNotifItems_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                EtNotifItems_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                EtNotifItems_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                EtNotifItems_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                EtNotifItems_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                EtNotifItems_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                EtNotifItems_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                EtNotifItems_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                EtNotifItems_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                EtNotifItems_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                EtNotifItems_statement.bindString(13, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                EtNotifItems_statement.bindString(14, jsonArray.getJSONObject(j).optString("CauseGrp"));
                                                EtNotifItems_statement.bindString(15, jsonArray.getJSONObject(j).optString("Causegrptext"));
                                                EtNotifItems_statement.bindString(16, jsonArray.getJSONObject(j).optString("CauseCod"));
                                                EtNotifItems_statement.bindString(17, jsonArray.getJSONObject(j).optString("Causecodetext"));
                                                EtNotifItems_statement.bindString(18, jsonArray.getJSONObject(j).optString("CauseShtxt"));
                                                EtNotifItems_statement.bindString(19, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtNotifItems_statement.bindString(20, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtNotifItems_statement.bindString(21, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtNotifItems_statement.bindString(22, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtNotifItems_statement.bindString(23, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtNotifItems_statement.bindString(24, "U");
                                                EtNotifItems_statement.execute();

                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifItemsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                    String sql = "Insert into EtNotifItems_CustomInfo (UUID, Qmnum, ItemKey, CauseKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                        statement1.bindString(1, uuid);
                                                        statement1.bindString(2, Qmnum);
                                                        statement1.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                        statement1.bindString(4, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                        statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(12, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(13, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }

                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifItems*/



                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/

                            if (jsonObject.has("EtNotifActvs")) {
                                try {
                                    String EtNotifActvs_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifActvs().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifActvs_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                                                EtNotifActvs_statement.clearBindings();
                                                EtNotifActvs_statement.bindString(1, uuid);
                                                EtNotifActvs_statement.bindString(2, Qmnum);
                                                EtNotifActvs_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                EtNotifActvs_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                EtNotifActvs_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                EtNotifActvs_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                EtNotifActvs_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                EtNotifActvs_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                EtNotifActvs_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                EtNotifActvs_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                EtNotifActvs_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                EtNotifActvs_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                EtNotifActvs_statement.bindString(13, jsonArray.getJSONObject(j).optString("CauseKey"));
                                                EtNotifActvs_statement.bindString(14, jsonArray.getJSONObject(j).optString("ActvKey"));
                                                EtNotifActvs_statement.bindString(15, jsonArray.getJSONObject(j).optString("ActvGrp"));
                                                EtNotifActvs_statement.bindString(16, jsonArray.getJSONObject(j).optString("Actgrptext"));
                                                EtNotifActvs_statement.bindString(17, jsonArray.getJSONObject(j).optString("ActvCod"));
                                                EtNotifActvs_statement.bindString(18, jsonArray.getJSONObject(j).optString("Actcodetext"));
                                                EtNotifActvs_statement.bindString(19, jsonArray.getJSONObject(j).optString("ActvShtxt"));
                                                EtNotifActvs_statement.bindString(20, jsonArray.getJSONObject(j).optString("StartDate"));
                                                EtNotifActvs_statement.bindString(21, jsonArray.getJSONObject(j).optString("StartTime"));
                                                EtNotifActvs_statement.bindString(22, jsonArray.getJSONObject(j).optString("EndDate"));
                                                EtNotifActvs_statement.bindString(23, jsonArray.getJSONObject(j).optString("EndTime"));
                                                EtNotifActvs_statement.bindString(24, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtNotifActvs_statement.bindString(25, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtNotifActvs_statement.bindString(26, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtNotifActvs_statement.bindString(27, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtNotifActvs_statement.bindString(28, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtNotifActvs_statement.bindString(29, "");
                                                EtNotifActvs_statement.bindString(30, "U");
                                                EtNotifActvs_statement.execute();

                                                String ActvKey = jsonArray.getJSONObject(j).optString("ActvKey");
                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifActvsFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                    String sql = "Insert into EtNotifActivity_CustomInfo (UUID, Qmnum, ActvKey, Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                        statement1.bindString(1, uuid);
                                                        statement1.bindString(2, Qmnum);
                                                        statement1.bindString(3, ActvKey);
                                                        statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(12, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }


                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifActvs*/



                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/

                            if (jsonObject.has("EtNotifLongtext")) {
                                try {
                                    String EtNotifLongtext_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifLongtext().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifLongtext_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext (UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                                                SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                                                EtNotifLongtext_statement.clearBindings();
                                                EtNotifLongtext_statement.bindString(1, uuid);
                                                EtNotifLongtext_statement.bindString(2, Qmnum);
                                                EtNotifLongtext_statement.bindString(3, jsonArray.getJSONObject(j).optString("Objtype"));
                                                EtNotifLongtext_statement.bindString(4, jsonArray.getJSONObject(j).optString("TextLine"));
                                                EtNotifLongtext_statement.bindString(5, jsonArray.getJSONObject(j).optString("Objkey"));
                                                EtNotifLongtext_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/



                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/

                            if (jsonObject.has("EtNotifStatus")) {
                                try {
                                    String EtNotifStatus_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifStatus().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifStatus_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum,Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act,Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                                                EtNotifStatus_statement.clearBindings();
                                                EtNotifStatus_statement.bindString(1, uuid);
                                                EtNotifStatus_statement.bindString(2, Qmnum);
                                                EtNotifStatus_statement.bindString(3, jsonArray.getJSONObject(j).optString("Aufnr"));
                                                EtNotifStatus_statement.bindString(4, jsonArray.getJSONObject(j).optString("Objnr"));
                                                EtNotifStatus_statement.bindString(5, jsonArray.getJSONObject(j).optString("Manum"));
                                                EtNotifStatus_statement.bindString(6, jsonArray.getJSONObject(j).optString("Stsma"));
                                                EtNotifStatus_statement.bindString(7, jsonArray.getJSONObject(j).optString("Inist"));
                                                EtNotifStatus_statement.bindString(8, jsonArray.getJSONObject(j).optString("Stonr"));
                                                EtNotifStatus_statement.bindString(9, jsonArray.getJSONObject(j).optString("Hsonr"));
                                                EtNotifStatus_statement.bindString(10, jsonArray.getJSONObject(j).optString("Nsonr"));
                                                EtNotifStatus_statement.bindString(11, jsonArray.getJSONObject(j).optString("Stat"));
                                                EtNotifStatus_statement.bindString(12, jsonArray.getJSONObject(j).optString("Act"));
                                                EtNotifStatus_statement.bindString(13, jsonArray.getJSONObject(j).optString("Txt04"));
                                                EtNotifStatus_statement.bindString(14, jsonArray.getJSONObject(j).optString("Txt30"));
                                                EtNotifStatus_statement.bindString(15, "");
                                                EtNotifStatus_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtNotifStatus*/



                            /*Reading and Inserting Data into Database Table for EtDocs*/

                            if (jsonObject.has("EtDocs")) {
                                try {
                                    String EtDocs_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtDocs().getResults());
                                    JSONArray jsonArray = new JSONArray(EtDocs_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Zobjid"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status, Contentx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                                EtDocs_statement.clearBindings();
                                                EtDocs_statement.bindString(1, uuid);
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
                                                EtDocs_statement.bindString(12, "");
                                                EtDocs_statement.bindString(13, "Old");
                                                EtDocs_statement.bindString(14, jsonArray.getJSONObject(j).optString("Contentx"));
                                                EtDocs_statement.execute();
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtDocs*/



                            /*Reading and Inserting Data into Database Table for EtNotifTasks*/

                            if (jsonObject.has("EtNotifTasks")) {
                                try {
                                    String EtNotifTasks_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtNotifTasks().getResults());
                                    JSONArray jsonArray = new JSONArray(EtNotifTasks_response_data);
                                    if (jsonArray.length() > 0) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            String uuid = "";
                                            String Qmnum = checkempty.check_empty(jsonArray.getJSONObject(j).optString("Qmnum"));
                                            for (int k = 0; k < notifications_uuid_list.size(); k++) {
                                                String old_Qmnum = notifications_uuid_list.get(k).get("Qmnum");
                                                if (Qmnum.equals(old_Qmnum)) {
                                                    uuid = notifications_uuid_list.get(k).get("UUID");
                                                }
                                            }
                                            if (uuid != null && !uuid.equals("")) {
                                                String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext, TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat, Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                                                EtNotifTasks_statement.clearBindings();
                                                EtNotifTasks_statement.bindString(1, uuid);
                                                EtNotifTasks_statement.bindString(2, jsonArray.getJSONObject(j).optString("Qmnum"));
                                                EtNotifTasks_statement.bindString(3, jsonArray.getJSONObject(j).optString("ItemKey"));
                                                EtNotifTasks_statement.bindString(4, jsonArray.getJSONObject(j).optString("ItempartGrp"));
                                                EtNotifTasks_statement.bindString(5, jsonArray.getJSONObject(j).optString("Partgrptext"));
                                                EtNotifTasks_statement.bindString(6, jsonArray.getJSONObject(j).optString("ItempartCod"));
                                                EtNotifTasks_statement.bindString(7, jsonArray.getJSONObject(j).optString("Partcodetext"));
                                                EtNotifTasks_statement.bindString(8, jsonArray.getJSONObject(j).optString("ItemdefectGrp"));
                                                EtNotifTasks_statement.bindString(9, jsonArray.getJSONObject(j).optString("Defectgrptext"));
                                                EtNotifTasks_statement.bindString(10, jsonArray.getJSONObject(j).optString("ItemdefectCod"));
                                                EtNotifTasks_statement.bindString(11, jsonArray.getJSONObject(j).optString("Defectcodetext"));
                                                EtNotifTasks_statement.bindString(12, jsonArray.getJSONObject(j).optString("ItemdefectShtxt"));
                                                EtNotifTasks_statement.bindString(13, jsonArray.getJSONObject(j).optString("TaskKey"));
                                                EtNotifTasks_statement.bindString(14, jsonArray.getJSONObject(j).optString("TaskGrp"));
                                                EtNotifTasks_statement.bindString(15, jsonArray.getJSONObject(j).optString("Taskgrptext"));
                                                EtNotifTasks_statement.bindString(16, jsonArray.getJSONObject(j).optString("TaskCod"));
                                                EtNotifTasks_statement.bindString(17, jsonArray.getJSONObject(j).optString("Taskcodetext"));
                                                EtNotifTasks_statement.bindString(18, jsonArray.getJSONObject(j).optString("TaskShtxt"));
                                                EtNotifTasks_statement.bindString(19, jsonArray.getJSONObject(j).optString("Pster"));
                                                EtNotifTasks_statement.bindString(20, jsonArray.getJSONObject(j).optString("Peter"));
                                                EtNotifTasks_statement.bindString(21, jsonArray.getJSONObject(j).optString("Pstur"));
                                                EtNotifTasks_statement.bindString(22, jsonArray.getJSONObject(j).optString("Petur"));
                                                EtNotifTasks_statement.bindString(23, jsonArray.getJSONObject(j).optString("Parvw"));
                                                EtNotifTasks_statement.bindString(24, jsonArray.getJSONObject(j).optString("Parnr"));
                                                EtNotifTasks_statement.bindString(25, jsonArray.getJSONObject(j).optString("Erlnam"));
                                                EtNotifTasks_statement.bindString(26, jsonArray.getJSONObject(j).optString("Erldat"));
                                                EtNotifTasks_statement.bindString(27, jsonArray.getJSONObject(j).optString("Erlzeit"));
                                                EtNotifTasks_statement.bindString(28, jsonArray.getJSONObject(j).optString("Release"));
                                                EtNotifTasks_statement.bindString(29, jsonArray.getJSONObject(j).optString("Complete"));
                                                EtNotifTasks_statement.bindString(30, jsonArray.getJSONObject(j).optString("Success"));
                                                EtNotifTasks_statement.bindString(31, jsonArray.getJSONObject(j).optString("UserStatus"));
                                                EtNotifTasks_statement.bindString(32, jsonArray.getJSONObject(j).optString("SysStatus"));
                                                EtNotifTasks_statement.bindString(33, jsonArray.getJSONObject(j).optString("Smsttxt"));
                                                EtNotifTasks_statement.bindString(34, jsonArray.getJSONObject(j).optString("Smastxt"));
                                                EtNotifTasks_statement.bindString(35, jsonArray.getJSONObject(j).optString("Usr01"));
                                                EtNotifTasks_statement.bindString(36, jsonArray.getJSONObject(j).optString("Usr02"));
                                                EtNotifTasks_statement.bindString(37, jsonArray.getJSONObject(j).optString("Usr03"));
                                                EtNotifTasks_statement.bindString(38, jsonArray.getJSONObject(j).optString("Usr04"));
                                                EtNotifTasks_statement.bindString(39, jsonArray.getJSONObject(j).optString("Usr05"));
                                                EtNotifTasks_statement.bindString(40, "U");
                                                EtNotifTasks_statement.execute();

                                                String Fields = jsonArray.getJSONObject(j).optString("EtNotifTasksFields");
                                                JSONObject Fields_jsonObject = new JSONObject(Fields);
                                                String Fields_jsonObject_results = Fields_jsonObject.optString("results");
                                                JSONArray EtNotifHeader_Fields_jsonArray = new JSONArray(Fields_jsonObject_results);
                                                if (EtNotifHeader_Fields_jsonArray.length() > 0) {
                                                    String sql1 = "Insert into EtNotifTask_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem,Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (int k = 0; k < EtNotifHeader_Fields_jsonArray.length(); k++) {
                                                        statement1.bindString(1, uuid);
                                                        statement1.bindString(2, Qmnum);
                                                        statement1.bindString(3, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Zdoctype"));
                                                        statement1.bindString(4, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("ZdoctypeItem"));
                                                        statement1.bindString(5, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Tabname"));
                                                        statement1.bindString(6, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Fieldname"));
                                                        statement1.bindString(7, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Value"));
                                                        statement1.bindString(8, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Flabel"));
                                                        statement1.bindString(9, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Sequence"));
                                                        statement1.bindString(10, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Length"));
                                                        statement1.bindString(11, EtNotifHeader_Fields_jsonArray.getJSONObject(k).optString("Datatype"));
                                                        statement1.execute();
                                                    }
                                                }


                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.v("kiran_tasks_ee", e.getMessage() + "...");
                                }
                            }
                            /*Reading and Inserting Data into Database Table for EtDocs*/
                        }
                        /*Reading Data by using FOR Loop*/

                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                        Get_Response = "success";
                    } else {
                        Get_Response = "no data";
                    }
                }
            } else {
            }
        } catch (Exception ex) {
            Log.v("kiran_notif_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        } finally {
        }
        return Get_Response;
    }

}
