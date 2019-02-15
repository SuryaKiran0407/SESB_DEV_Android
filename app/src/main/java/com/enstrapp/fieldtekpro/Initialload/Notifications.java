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
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

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

public class Notifications {

    private static String password = "", url_link = "", username = "", device_serial_number = "",
            device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static ArrayList<HashMap<String, String>> notifications_uuid_list = new ArrayList<HashMap<String, String>>();
    private static Check_Empty c_e = new Check_Empty();

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
                String Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Custom_Info_Fields + ""
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
                String Task_Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Task_Custom_Info_Fields + ""
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
                String Activity_Custom_Info_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_Activity_Custom_Info_Fields + ""
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
                String CREATE_DUE_NOTIFICATIONS_EtNotifItems_Fields_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATION_EtNotifItems_Fields + ""
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
                String CREATE_DUE_NOTIFICATION_NotifHeader_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATIONS_NotifHeader + ""
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
                String CREATE_DUE_NOTIFICATIONS_EtNotifItems = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_DUE_NOTIFICATIONS_EtNotifItems + ""
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
                String CREATE_DUE_NOTIFICATION_EtNotifActvs_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATION_EtNotifActvs + ""
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
                String CREATE_DUE_NOTIFICATION_EtNotifLongtext_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext + ""
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
                String CREATE_TABLE_EtNotifStatus = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_EtNotifStatus + ""
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
                String CREATE_DUE_NOTIFICATIONS_EtDocs_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATION_EtDocs + ""
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
                String CREATE_DUE_NOTIFICATION_EtNotifTasks_TABLE = "CREATE TABLE IF NOT EXISTS "
                        + TABLE_GET_DUE_NOTIFICATION_EtNotifTasks + ""
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
            FieldTekPro_SharedPref = activity
                    .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username", null);
            password = FieldTekPro_SharedPref.getString("Password", null);
            String webservice_type = FieldTekPro_SharedPref
                    .getString("webservice_type", null);
            /* Initializing Shared Preferences */
            Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype =" +
                    " ? and Zactivity = ? and Endpoint = ?", new String[]{"C1", "DN", webservice_type});
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
            map.put("Operation", "DUNOT");
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
            Call<Notifications_SER> call = service.getNOTIFICATIONDetails(url_link, basic, map);
            Response<Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_DNOT_code", response_status_code + "...");
            if (response_status_code == 200) {
                if (response.body().getD().getResults() != null && response.body().getD().getResults().size() > 0) {
                    App_db.beginTransaction();
                    try {

                        /*EtNotifHeader*/
                        if (response.body().getD().getResults().get(0).getEtNotifHeader() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifHeader().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifHeader().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifHeader_Result eN : response.body().getD().getResults()
                                        .get(0).getEtNotifHeader().getResults()) {
                                    values.put("UUID", eN.getQmnum());
                                    values.put("NotifType", eN.getNotifType());
                                    values.put("Qmnum", eN.getQmnum());
                                    values.put("NotifShorttxt", eN.getNotifShorttxt());
                                    values.put("FunctionLoc", eN.getFunctionLoc());
                                    values.put("Equipment", eN.getEquipment());
                                    values.put("Bautl", eN.getBautl());
                                    values.put("ReportedBy", eN.getReportedBy());
                                    values.put("MalfuncStdate", eN.getMalfuncStdate());
                                    values.put("MalfuncEddate", eN.getMalfuncEddate());
                                    values.put("MalfuncSttime", eN.getMalfuncSttime());
                                    values.put("MalfuncEdtime", eN.getMalfuncEdtime());
                                    values.put("BreakdownInd", eN.getBreakdownInd());
                                    values.put("Priority", eN.getPriority());
                                    values.put("Ingrp", eN.getIngrp());
                                    values.put("Arbpl", eN.getArbpl());
                                    values.put("Werks", eN.getWerks());
                                    values.put("Strmn", eN.getStrmn());
                                    values.put("Ltrmn", eN.getLtrmn());
                                    values.put("Aufnr", eN.getAufnr());
                                    values.put("Docs", eN.getDocs());
                                    values.put("Altitude", eN.getAltitude());
                                    values.put("Latitude", eN.getLatitude());
                                    values.put("Longitude", eN.getLongitude());
                                    values.put("Closed", eN.getClosed());
                                    values.put("Completed", eN.getCompleted());
                                    values.put("Createdon", eN.getCreatedon());
                                    values.put("Qmartx", eN.getQmartx());
                                    values.put("Pltxt", eN.getPltxt());
                                    values.put("Eqktx", eN.getEqktx());
                                    values.put("Priokx", eN.getPriokx());
                                    values.put("Auftext", eN.getAuftext());
                                    values.put("Auarttext", eN.getAuarttext());
                                    values.put("Plantname", eN.getPlantname());
                                    values.put("Wkctrname", eN.getWkctrname());
                                    values.put("Ingrpname", eN.getIngrpname());
                                    values.put("Maktx", eN.getMaktx());
                                    values.put("Xstatus", eN.getXstatus());
                                    values.put("Usr01", eN.getUsr01());
                                    values.put("Usr02", eN.getUsr02());
                                    values.put("Usr03", eN.getUsr03());
                                    values.put("Usr04", eN.getUsr04());
                                    values.put("Usr05", eN.getUsr05());
                                    values.put("STATUS", eN.getXstatus());
                                    values.put("ParnrVw", eN.getParnrVw());
                                    values.put("NameVw", eN.getNameVw());
                                    values.put("Auswk", eN.getAuswk());
                                    values.put("Shift", eN.getShift());
                                    values.put("Noofperson", eN.getNoofperson());
                                    values.put("Auswkt", eN.getAuswkt());
                                    values.put("Strur", eN.getStrur());
                                    values.put("Ltrur", eN.getLtrur());
                                    values.put("sort_malfc", eN.getStrmn() + " " + eN.getStrur());
                                    values.put("Qmdat", eN.getQmdat());
                                    if (eN.getEtCustomFields() != null)
                                        if (eN.getEtCustomFields().getResults() != null
                                                && eN.getEtCustomFields().getResults().size() > 0) {
                                            ContentValues ValuesHCf = new ContentValues();
                                            for (Notifications_SER.CustomFields_Result customFields_result : eN.getEtCustomFields().getResults()) {
                                                ValuesHCf.put("UUID", eN.getQmnum());
                                               // ValuesHCf.put("Qmnum", eN.getQmnum());
                                                ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                ValuesHCf.put("Value", customFields_result.getValue());
                                                ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                ValuesHCf.put("Length", customFields_result.getLength());
                                                ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                App_db.insert("EtOrderHeader_CustomInfo", null, ValuesHCf);
                                            }
                                        }
                                    App_db.insert("DUE_NOTIFICATION_NotifHeader", null, values);
                                }
                            }
                        }


                       /* Notifications_SER.EtNotifHeader etNotifHeader = results.get(0).getEtNotifHeader();
                        if (etNotifHeader != null) {
                            List<Notifications_SER.EtNotifHeader_Result> etNotifHeaderResults = etNotifHeader.getResults();
                            if (etNotifHeaderResults != null && etNotifHeaderResults.size() > 0) {
                                for (Notifications_SER.EtNotifHeader_Result eN : etNotifHeaderResults) {
                                    HashMap<String, String> uuid_hashmap = new HashMap<String, String>();
                                    UUID uniqueKey = UUID.randomUUID();
                                    uuid_hashmap.put("UUID", uniqueKey.toString());
                                    uuid_hashmap.put("Qmnum", c_e.check_empty(eN.getQmnum()));
                                    notifications_uuid_list.add(uuid_hashmap);
                                }
                                for (Notifications_SER.EtNotifHeader_Result eN : etNotifHeaderResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eN.getQmnum())) {
                                            String EtNotifHeader_sql = "Insert into DUE_NOTIFICATION_NotifHeader " +
                                                    "(UUID,NotifType,Qmnum,NotifShorttxt,FunctionLoc,Equipment," +
                                                    "Bautl,ReportedBy,MalfuncStdate,MalfuncEddate,MalfuncSttime," +
                                                    "MalfuncEdtime,BreakdownInd,Priority,Ingrp,Arbpl,Werks,Strmn," +
                                                    "Ltrmn,Aufnr,Docs,Altitude,Latitude,Longitude,Closed,Completed," +
                                                    "Createdon,Qmartx,Pltxt,Eqktx,Priokx,Auftext,Auarttext," +
                                                    "Plantname,Wkctrname,Ingrpname,Maktx,Xstatus,Usr01,Usr02,Usr03," +
                                                    "Usr04,Usr05,STATUS,ParnrVw,NameVw,Auswk,Shift,Noofperson," +
                                                    "Auswkt, Strur, Ltrur, sort_malfc, Qmdat)" +
                                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifHeader_statement = App_db.compileStatement(EtNotifHeader_sql);
                                            EtNotifHeader_statement.clearBindings();
                                            EtNotifHeader_statement.bindString(1, uD.get("UUID"));
                                            EtNotifHeader_statement.bindString(2, c_e.check_empty(eN.getNotifType()));
                                            EtNotifHeader_statement.bindString(3, c_e.check_empty(eN.getQmnum()));
                                            EtNotifHeader_statement.bindString(4, c_e.check_empty(eN.getNotifShorttxt()));
                                            EtNotifHeader_statement.bindString(5, c_e.check_empty(eN.getFunctionLoc()));
                                            EtNotifHeader_statement.bindString(6, c_e.check_empty(eN.getEquipment()));
                                            EtNotifHeader_statement.bindString(7, c_e.check_empty(eN.getBautl()));
                                            EtNotifHeader_statement.bindString(8, c_e.check_empty(eN.getReportedBy()));
                                            EtNotifHeader_statement.bindString(9, c_e.check_empty(eN.getMalfuncStdate()));
                                            EtNotifHeader_statement.bindString(10, c_e.check_empty(eN.getMalfuncEddate()));
                                            EtNotifHeader_statement.bindString(11, c_e.check_empty(eN.getMalfuncSttime()));
                                            EtNotifHeader_statement.bindString(12, c_e.check_empty(eN.getMalfuncEdtime()));
                                            EtNotifHeader_statement.bindString(13, c_e.check_empty(eN.getBreakdownInd()));
                                            EtNotifHeader_statement.bindString(14, c_e.check_empty(eN.getPriority()));
                                            EtNotifHeader_statement.bindString(15, c_e.check_empty(eN.getIngrp()));
                                            EtNotifHeader_statement.bindString(16, c_e.check_empty(eN.getArbpl()));
                                            EtNotifHeader_statement.bindString(17, c_e.check_empty(eN.getWerks()));
                                            EtNotifHeader_statement.bindString(18, c_e.check_empty(eN.getStrmn()));
                                            EtNotifHeader_statement.bindString(19, c_e.check_empty(eN.getLtrmn()));
                                            EtNotifHeader_statement.bindString(20, c_e.check_empty(eN.getAufnr()));
                                            EtNotifHeader_statement.bindString(21, c_e.check_empty(eN.getDocs()));
                                            EtNotifHeader_statement.bindString(22, c_e.check_empty(eN.getAltitude()));
                                            EtNotifHeader_statement.bindString(23, c_e.check_empty(eN.getLatitude()));
                                            EtNotifHeader_statement.bindString(24, c_e.check_empty(eN.getLongitude()));
                                            EtNotifHeader_statement.bindString(25, c_e.check_empty(eN.getClosed()));
                                            EtNotifHeader_statement.bindString(26, c_e.check_empty(eN.getCompleted()));
                                            EtNotifHeader_statement.bindString(27, c_e.check_empty(eN.getCreatedon()));
                                            EtNotifHeader_statement.bindString(28, c_e.check_empty(eN.getQmartx()));
                                            EtNotifHeader_statement.bindString(29, c_e.check_empty(eN.getPltxt()));
                                            EtNotifHeader_statement.bindString(30, c_e.check_empty(eN.getEqktx()));
                                            EtNotifHeader_statement.bindString(31, c_e.check_empty(eN.getPriokx()));
                                            EtNotifHeader_statement.bindString(32, c_e.check_empty(eN.getAuftext()));
                                            EtNotifHeader_statement.bindString(33, c_e.check_empty(eN.getAuarttext()));
                                            EtNotifHeader_statement.bindString(34, c_e.check_empty(eN.getPlantname()));
                                            EtNotifHeader_statement.bindString(35, c_e.check_empty(eN.getWkctrname()));
                                            EtNotifHeader_statement.bindString(36, c_e.check_empty(eN.getIngrpname()));
                                            EtNotifHeader_statement.bindString(37, c_e.check_empty(eN.getMaktx()));
                                            EtNotifHeader_statement.bindString(38, c_e.check_empty(eN.getXstatus()));
                                            EtNotifHeader_statement.bindString(39, c_e.check_empty(eN.getUsr01()));
                                            EtNotifHeader_statement.bindString(40, c_e.check_empty(eN.getUsr02()));
                                            EtNotifHeader_statement.bindString(41, c_e.check_empty(eN.getUsr03()));
                                            EtNotifHeader_statement.bindString(42, c_e.check_empty(eN.getUsr04()));
                                            EtNotifHeader_statement.bindString(43, c_e.check_empty(eN.getUsr05()));
                                            EtNotifHeader_statement.bindString(44, c_e.check_empty(eN.getXstatus()));
                                            EtNotifHeader_statement.bindString(45, c_e.check_empty(eN.getParnrVw()));
                                            EtNotifHeader_statement.bindString(46, c_e.check_empty(eN.getNameVw()));
                                            EtNotifHeader_statement.bindString(47, c_e.check_empty(eN.getAuswk()));
                                            EtNotifHeader_statement.bindString(48, c_e.check_empty(eN.getShift()));
                                            EtNotifHeader_statement.bindString(49, c_e.check_empty(String.valueOf(eN.getNoofperson())));
                                            EtNotifHeader_statement.bindString(50, c_e.check_empty(eN.getAuswk()));
                                            EtNotifHeader_statement.bindString(51, c_e.check_empty(eN.getStrur()));
                                            EtNotifHeader_statement.bindString(52, c_e.check_empty(eN.getLtrur()));
                                            EtNotifHeader_statement.bindString(53, c_e.check_empty(eN.getMalfuncStdate() + " " + c_e.check_empty(eN.getMalfuncSttime())));
                                            EtNotifHeader_statement.bindString(54, c_e.check_empty(eN.getQmdat()));
                                            EtNotifHeader_statement.execute();

                                            Notifications_SER.CustomFields customFields = eN.getEtCustomFields();
                                            if (customFields != null) {
                                                List<Notifications_SER.CustomFields_Result> customFieldsResults = customFields.getResults();
                                                if (customFieldsResults != null && customFieldsResults.size() > 0) {
                                                    String sql1 = "Insert into EtNotifHeader_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem," +
                                                            "Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype) " +
                                                            "values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (Notifications_SER.CustomFields_Result cF : customFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eN.getQmnum()));
                                                        statement1.bindString(3, c_e.check_empty(cF.getZdoctype()));
                                                        statement1.bindString(4, c_e.check_empty(cF.getZdoctypeItem()));
                                                        statement1.bindString(5, c_e.check_empty(cF.getTabname()));
                                                        statement1.bindString(6, c_e.check_empty(cF.getFieldname()));
                                                        statement1.bindString(7, c_e.check_empty(cF.getValue()));
                                                        statement1.bindString(8, c_e.check_empty(cF.getFlabel()));
                                                        statement1.bindString(9, c_e.check_empty(cF.getSequence()));
                                                        statement1.bindString(10, c_e.check_empty(cF.getLength()));
                                                        statement1.bindString(11, c_e.check_empty(cF.getDatatype()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtNotifItems*/
                        if (response.body().getD().getResults().get(0).getEtNotifItems() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifItems().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifItems().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifItems_Result eNI : response.body().getD().getResults()
                                        .get(0).getEtNotifItems().getResults()) {
                                    values.put("UUID", eNI.getQmnum());
                                    values.put("Qmnum", eNI.getQmnum());
                                    values.put("ItemKey", eNI.getItemKey());
                                    values.put("ItempartGrp", eNI.getItempartGrp());
                                    values.put("Partgrptext", eNI.getPartgrptext());
                                    values.put("ItempartCod", eNI.getItempartCod());
                                    values.put("Partcodetext", eNI.getPartcodetext());
                                    values.put("ItemdefectGrp", eNI.getItemdefectGrp());
                                    values.put("Defectgrptext", eNI.getDefectgrptext());
                                    values.put("ItemdefectCod", eNI.getItemdefectCod());
                                    values.put("Defectcodetext", eNI.getDefectcodetext());
                                    values.put("ItemdefectShtxt", eNI.getItemdefectShtxt());
                                    values.put("CauseKey", eNI.getCauseKey());
                                    values.put("CauseGrp", eNI.getCauseGrp());
                                    values.put("Causegrptext", eNI.getCausegrptext());
                                    values.put("CauseCod", eNI.getCauseCod());
                                    values.put("Causecodetext", eNI.getCausecodetext());
                                    values.put("CauseShtxt", eNI.getCauseShtxt());
                                    values.put("Usr01", eNI.getUsr01());
                                    values.put("Usr02", eNI.getUsr02());
                                    values.put("Usr03", eNI.getUsr03());
                                    values.put("Usr04", eNI.getUsr04());
                                    values.put("Usr05", eNI.getUsr05());
                                    values.put("Status", "U");
                                    if (eNI.getEtCustomFields() != null)
                                        if (eNI.getEtCustomFields().getResults() != null
                                                && eNI.getEtCustomFields().getResults().size() > 0) {
                                            ContentValues ValuesHCf = new ContentValues();
                                            for (Notifications_SER.CustomFields_Result customFields_result : eNI.getEtCustomFields().getResults()) {
                                                ValuesHCf.put("UUID", eNI.getQmnum());
                                                ValuesHCf.put("Qmnum", eNI.getQmnum());
                                                ValuesHCf.put("ItemKey", eNI.getItemKey());
                                                ValuesHCf.put("CauseKey", eNI.getCauseKey());
                                                ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                ValuesHCf.put("Value", customFields_result.getValue());
                                                ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                ValuesHCf.put("Length", customFields_result.getLength());
                                                ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                App_db.insert("EtNotifItems_CustomInfo", null, ValuesHCf);
                                            }
                                        }
                                    App_db.insert("DUE_NOTIFICATIONS_EtNotifItems", null, values);
                                }
                            }
                        }


                       /* Notifications_SER.EtNotifItems etNotifItems = results.get(0).getEtNotifItems();
                        if (etNotifItems != null) {
                            List<Notifications_SER.EtNotifItems_Result> etNotifItemsResults = etNotifItems.getResults();
                            if (etNotifItemsResults != null && etNotifItemsResults.size() > 0) {
                                for (Notifications_SER.EtNotifItems_Result eNI : etNotifItemsResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eNI.getQmnum())) {
                                            String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems" +
                                                    " (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod," +
                                                    " Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod," +
                                                    " Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, " +
                                                    "Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, " +
                                                    "Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                                            EtNotifItems_statement.clearBindings();
                                            EtNotifItems_statement.bindString(1, uD.get("UUID"));
                                            EtNotifItems_statement.bindString(2, c_e.check_empty(eNI.getQmnum()));
                                            EtNotifItems_statement.bindString(3, c_e.check_empty(eNI.getItemKey()));
                                            EtNotifItems_statement.bindString(4, c_e.check_empty(eNI.getItempartGrp()));
                                            EtNotifItems_statement.bindString(5, c_e.check_empty(eNI.getPartgrptext()));
                                            EtNotifItems_statement.bindString(6, c_e.check_empty(eNI.getItempartCod()));
                                            EtNotifItems_statement.bindString(7, c_e.check_empty(eNI.getPartcodetext()));
                                            EtNotifItems_statement.bindString(8, c_e.check_empty(eNI.getItemdefectGrp()));
                                            EtNotifItems_statement.bindString(9, c_e.check_empty(eNI.getDefectgrptext()));
                                            EtNotifItems_statement.bindString(10, c_e.check_empty(eNI.getItemdefectCod()));
                                            EtNotifItems_statement.bindString(11, c_e.check_empty(eNI.getDefectcodetext()));
                                            EtNotifItems_statement.bindString(12, c_e.check_empty(eNI.getItemdefectShtxt()));
                                            EtNotifItems_statement.bindString(13, c_e.check_empty(eNI.getCauseKey()));
                                            EtNotifItems_statement.bindString(14, c_e.check_empty(eNI.getCauseGrp()));
                                            EtNotifItems_statement.bindString(15, c_e.check_empty(eNI.getCausegrptext()));
                                            EtNotifItems_statement.bindString(16, c_e.check_empty(eNI.getCauseCod()));
                                            EtNotifItems_statement.bindString(17, c_e.check_empty(eNI.getCausecodetext()));
                                            EtNotifItems_statement.bindString(18, c_e.check_empty(eNI.getCauseShtxt()));
                                            EtNotifItems_statement.bindString(19, c_e.check_empty(eNI.getUsr01()));
                                            EtNotifItems_statement.bindString(20, c_e.check_empty(eNI.getUsr02()));
                                            EtNotifItems_statement.bindString(21, c_e.check_empty(eNI.getUsr03()));
                                            EtNotifItems_statement.bindString(22, c_e.check_empty(eNI.getUsr04()));
                                            EtNotifItems_statement.bindString(23, c_e.check_empty(eNI.getUsr05()));
                                            EtNotifItems_statement.bindString(24, "U");
                                            EtNotifItems_statement.execute();

                                            Notifications_SER.CustomFields customFields = eNI.getEtCustomFields();
                                            if (customFields != null) {
                                                List<Notifications_SER.CustomFields_Result> customFieldsResults = customFields.getResults();
                                                if (customFieldsResults != null && customFieldsResults.size() > 0) {
                                                    String sql = "Insert into EtNotifItems_CustomInfo (UUID, Qmnum, ItemKey, CauseKey, " +
                                                            "Zdoctype, ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length," +
                                                            " Datatype) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for (Notifications_SER.CustomFields_Result cF : customFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eNI.getQmnum()));
                                                        statement1.bindString(3, c_e.check_empty(eNI.getItemKey()));
                                                        statement1.bindString(4, c_e.check_empty(eNI.getCauseKey()));
                                                        statement1.bindString(5, c_e.check_empty(cF.getZdoctype()));
                                                        statement1.bindString(6, c_e.check_empty(cF.getZdoctypeItem()));
                                                        statement1.bindString(7, c_e.check_empty(cF.getTabname()));
                                                        statement1.bindString(8, c_e.check_empty(cF.getFieldname()));
                                                        statement1.bindString(9, c_e.check_empty(cF.getValue()));
                                                        statement1.bindString(10, c_e.check_empty(cF.getFlabel()));
                                                        statement1.bindString(11, c_e.check_empty(cF.getSequence()));
                                                        statement1.bindString(12, c_e.check_empty(cF.getLength()));
                                                        statement1.bindString(13, c_e.check_empty(cF.getDatatype()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtNotifActvs*/
                        if (response.body().getD().getResults().get(0).getEtNotifActvs() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifActvs().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifActvs().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifActvs_Result eNA : response.body().getD().getResults()
                                        .get(0).getEtNotifActvs().getResults()) {
                                    values.put("UUID", eNA.getQmnum());
                                    values.put("Qmnum", eNA.getQmnum());
                                    values.put("ItemKey", eNA.getItemKey());
                                    values.put("ItempartGrp", eNA.getItempartGrp());
                                    values.put("Partgrptext", eNA.getPartgrptext());
                                    values.put("ItempartCod", eNA.getItempartCod());
                                    values.put("Partcodetext", eNA.getPartcodetext());
                                    values.put("ItemdefectGrp", eNA.getItemdefectGrp());
                                    values.put("Defectgrptext", eNA.getDefectgrptext());
                                    values.put("ItemdefectCod", eNA.getItemdefectCod());
                                    values.put("Defectcodetext", eNA.getDefectcodetext());
                                    values.put("ItemdefectShtxt", eNA.getItemdefectShtxt());
                                    values.put("CauseKey", eNA.getCauseKey());
                                    values.put("ActvKey", eNA.getActvKey());
                                    values.put("ActvGrp", eNA.getActvGrp());
                                    values.put("Actgrptext", eNA.getActgrptext());
                                    values.put("ActvCod", eNA.getActvCod());
                                    values.put("Actcodetext", eNA.getActcodetext());
                                    values.put("ActvShtxt", eNA.getActvShtxt());
                                    values.put("StartDate", eNA.getStartDate());
                                    values.put("StartTime", eNA.getStartTime());
                                    values.put("EndDate", eNA.getEndDate());
                                    values.put("EndTime", eNA.getEndTime());
                                    values.put("Usr01", eNA.getUsr01());
                                    values.put("Usr02", eNA.getUsr02());
                                    values.put("Usr03", eNA.getUsr03());
                                    values.put("Usr04", eNA.getUsr04());
                                    values.put("Usr05", eNA.getUsr05());
                                    values.put("Fields", "");
                                    values.put("Action", "U");
                                    if (eNA.getEtCustomFields() != null)
                                        if (eNA.getEtCustomFields().getResults() != null
                                                && eNA.getEtCustomFields().getResults().size() > 0) {
                                            ContentValues ValuesHCf = new ContentValues();
                                            for (Notifications_SER.CustomFields_Result customFields_result : eNA.getEtCustomFields().getResults()) {
                                                ValuesHCf.put("UUID", eNA.getQmnum());
                                                ValuesHCf.put("Qmnum", eNA.getQmnum());
                                                ValuesHCf.put("ActvKey", eNA.getActvKey());
                                                ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                ValuesHCf.put("Value", customFields_result.getValue());
                                                ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                ValuesHCf.put("Length", customFields_result.getLength());
                                                ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                App_db.insert("EtNotifActivity_CustomInfo", null, ValuesHCf);
                                            }
                                        }

                                    App_db.insert("DUE_NOTIFICATION_EtNotifActvs", null, values);
                                }
                            }
                        }
                        /*Notifications_SER.EtNotifActvs etNotifActvs = results.get(0).getEtNotifActvs();
                        if (etNotifActvs != null) {
                            List<Notifications_SER.EtNotifActvs_Result> etNotifActvsResults = etNotifActvs.getResults();
                            if (etNotifActvsResults != null && etNotifActvsResults.size() > 0) {
                                for (Notifications_SER.EtNotifActvs_Result eNA : etNotifActvsResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eNA.getQmnum())) {
                                            String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey," +
                                                    " ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext," +
                                                    " ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp," +
                                                    " Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, " +
                                                    "EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action)" +
                                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                                            EtNotifActvs_statement.clearBindings();
                                            EtNotifActvs_statement.bindString(1, uD.get("UUID"));
                                            EtNotifActvs_statement.bindString(2, c_e.check_empty(eNA.getQmnum()));
                                            EtNotifActvs_statement.bindString(3, c_e.check_empty(eNA.getItemKey()));
                                            EtNotifActvs_statement.bindString(4, c_e.check_empty(eNA.getItempartGrp()));
                                            EtNotifActvs_statement.bindString(5, c_e.check_empty(eNA.getPartgrptext()));
                                            EtNotifActvs_statement.bindString(6, c_e.check_empty(eNA.getItempartCod()));
                                            EtNotifActvs_statement.bindString(7, c_e.check_empty(eNA.getPartcodetext()));
                                            EtNotifActvs_statement.bindString(8, c_e.check_empty(eNA.getItemdefectGrp()));
                                            EtNotifActvs_statement.bindString(9, c_e.check_empty(eNA.getDefectgrptext()));
                                            EtNotifActvs_statement.bindString(10, c_e.check_empty(eNA.getItemdefectCod()));
                                            EtNotifActvs_statement.bindString(11, c_e.check_empty(eNA.getDefectcodetext()));
                                            EtNotifActvs_statement.bindString(12, c_e.check_empty(eNA.getItemdefectShtxt()));
                                            EtNotifActvs_statement.bindString(13, c_e.check_empty(eNA.getCauseKey()));
                                            EtNotifActvs_statement.bindString(14, c_e.check_empty(eNA.getActvKey()));
                                            EtNotifActvs_statement.bindString(15, c_e.check_empty(eNA.getActvGrp()));
                                            EtNotifActvs_statement.bindString(16, c_e.check_empty(eNA.getActgrptext()));
                                            EtNotifActvs_statement.bindString(17, c_e.check_empty(eNA.getActvCod()));
                                            EtNotifActvs_statement.bindString(18, c_e.check_empty(eNA.getActcodetext()));
                                            EtNotifActvs_statement.bindString(19, c_e.check_empty(eNA.getActvShtxt()));
                                            EtNotifActvs_statement.bindString(20, c_e.check_empty(eNA.getStartDate()));
                                            EtNotifActvs_statement.bindString(21, c_e.check_empty(eNA.getStartTime()));
                                            EtNotifActvs_statement.bindString(22, c_e.check_empty(eNA.getEndDate()));
                                            EtNotifActvs_statement.bindString(23, c_e.check_empty(eNA.getEndTime()));
                                            EtNotifActvs_statement.bindString(24, c_e.check_empty(eNA.getUsr01()));
                                            EtNotifActvs_statement.bindString(25, c_e.check_empty(eNA.getUsr02()));
                                            EtNotifActvs_statement.bindString(26, c_e.check_empty(eNA.getUsr03()));
                                            EtNotifActvs_statement.bindString(27, c_e.check_empty(eNA.getUsr04()));
                                            EtNotifActvs_statement.bindString(28, c_e.check_empty(eNA.getUsr05()));
                                            EtNotifActvs_statement.bindString(29, "");
                                            EtNotifActvs_statement.bindString(30, "U");
                                            EtNotifActvs_statement.execute();

                                            Notifications_SER.CustomFields customFields = eNA.getEtCustomFields();
                                            if (customFields != null) {
                                                List<Notifications_SER.CustomFields_Result> customFieldsResults = customFields.getResults();
                                                if (customFieldsResults != null && customFieldsResults.size() > 0) {
                                                    String sql = "Insert into EtNotifActivity_CustomInfo (UUID, Qmnum, ActvKey, Zdoctype, " +
                                                            "ZdoctypeItem, Tabname, Fieldname, Value, Flabel, Sequence, Length, Datatype)" +
                                                            " values(?,?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql);
                                                    statement1.clearBindings();
                                                    for (Notifications_SER.CustomFields_Result cF : customFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eNA.getQmnum()));
                                                        statement1.bindString(3, c_e.check_empty(eNA.getActvKey()));
                                                        statement1.bindString(4, c_e.check_empty(cF.getZdoctype()));
                                                        statement1.bindString(5, c_e.check_empty(cF.getZdoctypeItem()));
                                                        statement1.bindString(6, c_e.check_empty(cF.getTabname()));
                                                        statement1.bindString(7, c_e.check_empty(cF.getFieldname()));
                                                        statement1.bindString(8, c_e.check_empty(cF.getValue()));
                                                        statement1.bindString(9, c_e.check_empty(cF.getFlabel()));
                                                        statement1.bindString(10, c_e.check_empty(cF.getSequence()));
                                                        statement1.bindString(11, c_e.check_empty(cF.getLength()));
                                                        statement1.bindString(12, c_e.check_empty(cF.getDatatype()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtNotifTasks*/
                        if (response.body().getD().getResults().get(0).getEtNotifTasks() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifTasks().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifTasks().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifTasks_Result eNT : response.body().getD().getResults()
                                        .get(0).getEtNotifTasks().getResults()) {
                                    values.put("UUID", eNT.getQmnum());
                                    values.put("Qmnum", eNT.getQmnum());
                                    values.put("ItemKey", eNT.getItemKey());
                                    values.put("ItempartGrp", eNT.getItempartGrp());
                                    values.put("Partgrptext", eNT.getPartgrptext());
                                    values.put("ItempartCod", eNT.getItempartCod());
                                    values.put("Partcodetext", eNT.getPartcodetext());
                                    values.put("ItemdefectGrp", eNT.getItemdefectGrp());
                                    values.put("Defectgrptext", eNT.getDefectgrptext());
                                    values.put("ItemdefectCod", eNT.getItemdefectCod());
                                    values.put("Defectcodetext", eNT.getDefectcodetext());
                                    values.put("ItemdefectShtxt", eNT.getItemdefectShtxt());
                                    values.put("TaskKey", eNT.getTaskKey());
                                    values.put("TaskGrp", eNT.getTaskGrp());
                                    values.put("Taskgrptext", eNT.getTaskgrptext());
                                    values.put("TaskCod", eNT.getTaskCod());
                                    values.put(" Taskcodetext", eNT.getTaskcodetext());
                                    values.put("TaskShtxt", eNT.getTaskShtxt());
                                    values.put("Pster", eNT.getPster());
                                    values.put("Peter", eNT.getPeter());
                                    values.put("Pstur", eNT.getPstur());
                                    values.put("Petur", eNT.getPetur());
                                    values.put("Parvw", eNT.getParvw());
                                    values.put("Parnr", eNT.getParnr());
                                    values.put("Erlnam", eNT.getErlnam());
                                    values.put("Erldat", eNT.getErldat());
                                    values.put("Erlzeit", eNT.getErlzeit());
                                    values.put("Release", eNT.getRelease());
                                    values.put("Complete", eNT.getComplete());
                                    values.put("Success", eNT.getSuccess());
                                    values.put("UserStatus", eNT.getUserStatus());
                                    values.put("SysStatus", eNT.getSysStatus());
                                    values.put("Smsttxt", eNT.getSmsttxt());
                                    values.put("Smastxt", eNT.getSmastxt());
                                    values.put("Usr01", eNT.getUsr01());
                                    values.put("Usr02", eNT.getUsr02());
                                    values.put("Usr03", eNT.getUsr03());
                                    values.put("Usr04", eNT.getUsr04());
                                    values.put("Usr05", eNT.getUsr05());
                                    values.put("Action", "U");
                                    if (eNT.getEtCustomFields() != null)
                                        if (eNT.getEtCustomFields().getResults() != null
                                                && eNT.getEtCustomFields().getResults().size() > 0) {
                                            ContentValues ValuesHCf = new ContentValues();
                                            for (Notifications_SER.CustomFields_Result customFields_result : eNT.getEtCustomFields().getResults()) {
                                                ValuesHCf.put("UUID", eNT.getQmnum());
                                                ValuesHCf.put("Qmnum", eNT.getQmnum());
                                                ValuesHCf.put("Zdoctype", customFields_result.getZdoctype());
                                                ValuesHCf.put("ZdoctypeItem", customFields_result.getZdoctypeItem());
                                                ValuesHCf.put("Tabname", customFields_result.getTabname());
                                                ValuesHCf.put("Fieldname", customFields_result.getFieldname());
                                                ValuesHCf.put("Value", customFields_result.getValue());
                                                ValuesHCf.put("Flabel", customFields_result.getFlabel());
                                                ValuesHCf.put("Sequence", customFields_result.getSequence());
                                                ValuesHCf.put("Length", customFields_result.getLength());
                                                ValuesHCf.put("Datatype", customFields_result.getDatatype());
                                                App_db.insert("EtNotifTask_CustomInfo", null, ValuesHCf);
                                            }
                                        }

                                    App_db.insert("DUE_NOTIFICATION_EtNotifTasks", null, values);
                                }
                            }
                        }
                        /*Notifications_SER.EtNotifTasks etNotifTasks = results.get(0).getEtNotifTasks();
                        if (etNotifTasks != null) {
                            List<Notifications_SER.EtNotifTasks_Result> etNotifTasksResults = etNotifTasks.getResults();
                            if (etNotifTasksResults != null && etNotifTasksResults.size() > 0) {
                                for (Notifications_SER.EtNotifTasks_Result eNT : etNotifTasksResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eNT.getQmnum())) {
                                            String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum," +
                                                    " ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, " +
                                                    "ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, " +
                                                    "ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext," +
                                                    " TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat," +
                                                    " Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, " +
                                                    "Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action)" +
                                                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                    "?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                                            EtNotifTasks_statement.clearBindings();
                                            EtNotifTasks_statement.bindString(1, uD.get("UUID"));
                                            EtNotifTasks_statement.bindString(2, c_e.check_empty(eNT.getQmnum()));
                                            EtNotifTasks_statement.bindString(3, c_e.check_empty(eNT.getItemKey()));
                                            EtNotifTasks_statement.bindString(4, c_e.check_empty(eNT.getItempartGrp()));
                                            EtNotifTasks_statement.bindString(5, c_e.check_empty(eNT.getPartgrptext()));
                                            EtNotifTasks_statement.bindString(6, c_e.check_empty(eNT.getItempartCod()));
                                            EtNotifTasks_statement.bindString(7, c_e.check_empty(eNT.getPartcodetext()));
                                            EtNotifTasks_statement.bindString(8, c_e.check_empty(eNT.getItemdefectGrp()));
                                            EtNotifTasks_statement.bindString(9, c_e.check_empty(eNT.getDefectgrptext()));
                                            EtNotifTasks_statement.bindString(10, c_e.check_empty(eNT.getItemdefectCod()));
                                            EtNotifTasks_statement.bindString(11, c_e.check_empty(eNT.getDefectcodetext()));
                                            EtNotifTasks_statement.bindString(12, c_e.check_empty(eNT.getItemdefectShtxt()));
                                            EtNotifTasks_statement.bindString(13, c_e.check_empty(eNT.getTaskKey()));
                                            EtNotifTasks_statement.bindString(14, c_e.check_empty(eNT.getTaskGrp()));
                                            EtNotifTasks_statement.bindString(15, c_e.check_empty(eNT.getTaskgrptext()));
                                            EtNotifTasks_statement.bindString(16, c_e.check_empty(eNT.getTaskCod()));
                                            EtNotifTasks_statement.bindString(17, c_e.check_empty(eNT.getTaskcodetext()));
                                            EtNotifTasks_statement.bindString(18, c_e.check_empty(eNT.getTaskShtxt()));
                                            EtNotifTasks_statement.bindString(19, c_e.check_empty(eNT.getPster()));
                                            EtNotifTasks_statement.bindString(20, c_e.check_empty(eNT.getPeter()));
                                            EtNotifTasks_statement.bindString(21, c_e.check_empty(eNT.getPstur()));
                                            EtNotifTasks_statement.bindString(22, c_e.check_empty(eNT.getPetur()));
                                            EtNotifTasks_statement.bindString(23, c_e.check_empty(eNT.getParvw()));
                                            EtNotifTasks_statement.bindString(24, c_e.check_empty(eNT.getParnr()));
                                            EtNotifTasks_statement.bindString(25, c_e.check_empty(eNT.getErlnam()));
                                            EtNotifTasks_statement.bindString(26, c_e.check_empty(eNT.getErldat()));
                                            EtNotifTasks_statement.bindString(27, c_e.check_empty(eNT.getErlzeit()));
                                            EtNotifTasks_statement.bindString(28, c_e.check_empty(eNT.getRelease()));
                                            EtNotifTasks_statement.bindString(29, c_e.check_empty(eNT.getComplete()));
                                            EtNotifTasks_statement.bindString(30, c_e.check_empty(eNT.getSuccess()));
                                            EtNotifTasks_statement.bindString(31, c_e.check_empty(eNT.getUserStatus()));
                                            EtNotifTasks_statement.bindString(32, c_e.check_empty(eNT.getSysStatus()));
                                            EtNotifTasks_statement.bindString(33, c_e.check_empty(eNT.getSmsttxt()));
                                            EtNotifTasks_statement.bindString(34, c_e.check_empty(eNT.getSmastxt()));
                                            EtNotifTasks_statement.bindString(35, c_e.check_empty(eNT.getUsr01()));
                                            EtNotifTasks_statement.bindString(36, c_e.check_empty(eNT.getUsr02()));
                                            EtNotifTasks_statement.bindString(37, c_e.check_empty(eNT.getUsr03()));
                                            EtNotifTasks_statement.bindString(38, c_e.check_empty(eNT.getUsr04()));
                                            EtNotifTasks_statement.bindString(39, c_e.check_empty(eNT.getUsr05()));
                                            EtNotifTasks_statement.bindString(40, "U");
                                            EtNotifTasks_statement.execute();

                                            Notifications_SER.CustomFields customFields = eNT.getEtCustomFields();
                                            if (customFields != null) {
                                                List<Notifications_SER.CustomFields_Result> customFieldsResults = customFields.getResults();
                                                if (customFieldsResults != null && customFieldsResults.size() > 0) {
                                                    String sql1 = "Insert into EtNotifTask_CustomInfo (UUID,Qmnum,Zdoctype,ZdoctypeItem," +
                                                            "Tabname,Fieldname,Value,Flabel,Sequence,Length,Datatype)" +
                                                            " values(?,?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement1 = App_db.compileStatement(sql1);
                                                    statement1.clearBindings();
                                                    for (Notifications_SER.CustomFields_Result cFR : customFieldsResults) {
                                                        statement1.bindString(1, uD.get("UUID"));
                                                        statement1.bindString(2, c_e.check_empty(eNT.getQmnum()));
                                                        statement1.bindString(3, c_e.check_empty(cFR.getZdoctype()));
                                                        statement1.bindString(4, c_e.check_empty(cFR.getZdoctypeItem()));
                                                        statement1.bindString(5, c_e.check_empty(cFR.getTabname()));
                                                        statement1.bindString(6, c_e.check_empty(cFR.getFieldname()));
                                                        statement1.bindString(7, c_e.check_empty(cFR.getValue()));
                                                        statement1.bindString(8, c_e.check_empty(cFR.getFlabel()));
                                                        statement1.bindString(9, c_e.check_empty(cFR.getSequence()));
                                                        statement1.bindString(10, c_e.check_empty(cFR.getLength()));
                                                        statement1.bindString(11, c_e.check_empty(cFR.getDatatype()));
                                                        statement1.execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtNotifLongtext*/
                        if (response.body().getD().getResults().get(0).getEtNotifLongtext() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifLongtext().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifLongtext().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifLongtext_Result eNL : response.body().getD().getResults()
                                        .get(0).getEtNotifLongtext().getResults()) {
                                    values.put("UUID", eNL.getQmnum());
                                    values.put("Qmnum", eNL.getQmnum());
                                    values.put("Objtype", eNL.getObjtype());
                                    values.put("TextLine", eNL.getTextLine());
                                    values.put("Objkey", eNL.getObjkey());
                                    App_db.insert("DUE_NOTIFICATIONS_EtNotifLongtext", null, values);
                                }
                            }
                        }
                        /*Notifications_SER.EtNotifLongtext etNotifLongtext = results.get(0).getEtNotifLongtext();
                        if (etNotifLongtext != null) {
                            List<Notifications_SER.EtNotifLongtext_Result> etNotifLongtextResults = etNotifLongtext.getResults();
                            if (etNotifLongtextResults != null && etNotifLongtextResults.size() > 0) {
                                for (Notifications_SER.EtNotifLongtext_Result eNL : etNotifLongtextResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eNL.getQmnum())) {
                                            String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext " +
                                                    "(UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                                            SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                                            EtNotifLongtext_statement.clearBindings();
                                            EtNotifLongtext_statement.bindString(1, uD.get("UUID"));
                                            EtNotifLongtext_statement.bindString(2, c_e.check_empty(eNL.getQmnum()));
                                            EtNotifLongtext_statement.bindString(3, c_e.check_empty(eNL.getObjtype()));
                                            EtNotifLongtext_statement.bindString(4, c_e.check_empty(eNL.getTextLine()));
                                            EtNotifLongtext_statement.bindString(5, c_e.check_empty(eNL.getObjkey()));
                                            EtNotifLongtext_statement.execute();
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtNotifStatus*/
                        if (response.body().getD().getResults().get(0).getEtNotifStatus() != null) {
                            if (response.body().getD().getResults().get(0).getEtNotifStatus().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtNotifStatus().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtNotifStatus_Result eNS : response.body().getD().getResults()
                                        .get(0).getEtNotifStatus().getResults()) {
                                    values.put("UUID", "");
                                    values.put("Qmnum", eNS.getQmnum());
                                    values.put("Aufnr", eNS.getAufnr());
                                    values.put("Objnr", eNS.getObjnr());
                                    values.put("Manum", eNS.getManum());
                                    values.put("Stsma", eNS.getStsma());
                                    values.put("Inist", eNS.getInist());
                                    values.put("Stonr", eNS.getStonr());
                                    values.put("Hsonr", eNS.getHsonr());
                                    values.put("Nsonr", eNS.getNsonr());
                                    values.put("Stat", eNS.getStat());
                                    values.put("Act", eNS.getAct());
                                    values.put("Txt04", eNS.getTxt04());
                                    values.put("Txt30", eNS.getTxt30());
                                    values.put("Action", eNS.getAction());
                                    App_db.insert("EtNotifStatus", null, values);
                                }
                            }
                        }
                       /* Notifications_SER.EtNotifStatus etNotifStatus = results.get(0).getEtNotifStatus();
                        if (etNotifStatus != null) {
                            List<Notifications_SER.EtNotifStatus_Result> etNotifStatusResults = etNotifStatus.getResults();
                            if (etNotifStatusResults != null && etNotifStatusResults.size() > 0) {
                                for (Notifications_SER.EtNotifStatus_Result eNS : etNotifStatusResults) {
                                    for (HashMap<String, String> uD : notifications_uuid_list) {
                                        if (uD.get("Qmnum").equals(eNS.getQmnum())) {
                                            String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum," +
                                                    "Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act," +
                                                    "Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                            SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                                            EtNotifStatus_statement.clearBindings();
                                            EtNotifStatus_statement.bindString(1, uD.get("UUID"));
                                            EtNotifStatus_statement.bindString(2, c_e.check_empty(eNS.getQmnum()));
                                            EtNotifStatus_statement.bindString(3, c_e.check_empty(eNS.getAufnr()));
                                            EtNotifStatus_statement.bindString(4, c_e.check_empty(eNS.getObjnr()));
                                            EtNotifStatus_statement.bindString(5, c_e.check_empty(eNS.getManum()));
                                            EtNotifStatus_statement.bindString(6, c_e.check_empty(eNS.getStsma()));
                                            EtNotifStatus_statement.bindString(7, c_e.check_empty(eNS.getInist()));
                                            EtNotifStatus_statement.bindString(8, c_e.check_empty(eNS.getStonr()));
                                            EtNotifStatus_statement.bindString(9, c_e.check_empty(eNS.getHsonr()));
                                            EtNotifStatus_statement.bindString(10, c_e.check_empty(eNS.getNsonr()));
                                            EtNotifStatus_statement.bindString(11, c_e.check_empty(eNS.getStat()));
                                            EtNotifStatus_statement.bindString(12, c_e.check_empty(eNS.getAct()));
                                            EtNotifStatus_statement.bindString(13, c_e.check_empty(eNS.getTxt04()));
                                            EtNotifStatus_statement.bindString(14, c_e.check_empty(eNS.getTxt30()));
                                            EtNotifStatus_statement.bindString(15, "");
                                            EtNotifStatus_statement.execute();
                                        }
                                    }
                                }
                            }
                        }*/

                        /*EtDocs*/
                        if (response.body().getD().getResults().get(0).getEtDocs() != null) {
                            if (response.body().getD().getResults().get(0).getEtDocs().getResults() != null
                                    && response.body().getD().getResults().get(0).getEtDocs().getResults().size() > 0) {
                                ContentValues values = new ContentValues();
                                for (Notifications_SER.EtDocs_Result eD : response.body().getD().getResults()
                                        .get(0).getEtDocs().getResults()) {
                                    values.put("UUID", eD.getZobjid());
                                    values.put("Zobjid", eD.getZobjid());
                                    values.put("Zdoctype", eD.getZdoctype());
                                    values.put("ZdoctypeItem", eD.getZdoctypeItem());
                                    values.put("Filename", eD.getFilename());
                                    values.put("Filetype", eD.getFiletype());
                                    values.put("Fsize", eD.getFsize());
                                    values.put("Content", eD.getContent());
                                    values.put("DocId", eD.getDocId());
                                    values.put("DocType", eD.getDocType());
                                    values.put("Objtype", eD.getObjtype());
                                    values.put("Filepath", "");
                                    values.put("Status", "Old");
                                    values.put("Contentx", eD.getContentX());
                                    App_db.insert("DUE_NOTIFICATION_EtDocs", null, values);
                                }
                            }
                        }
                        /*Notifications_SER.EtDocs etDocs = results.get(0).getEtDocs();
                        if (etDocs != null) {
                            List<Notifications_SER.EtDocs_Result> etDocsResults = etDocs.getResults();
                            for (Notifications_SER.EtDocs_Result eD : etDocsResults) {
                                for (HashMap<String, String> uD : notifications_uuid_list) {
                                    if (uD.get("Qmnum").equals(eD.getZobjid())) {
                                        String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, " +
                                                "Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, " +
                                                "DocId, DocType, Objtype, Filepath, Status, Contentx) " +
                                                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                        EtDocs_statement.clearBindings();
                                        EtDocs_statement.bindString(1, uD.get("UUID"));
                                        EtDocs_statement.bindString(2, c_e.check_empty(eD.getZobjid()));
                                        EtDocs_statement.bindString(3, c_e.check_empty(eD.getZdoctype()));
                                        EtDocs_statement.bindString(4, c_e.check_empty(eD.getZdoctypeItem()));
                                        EtDocs_statement.bindString(5, c_e.check_empty(eD.getFilename()));
                                        EtDocs_statement.bindString(6, c_e.check_empty(eD.getFiletype()));
                                        EtDocs_statement.bindString(7, c_e.check_empty(eD.getFsize()));
                                        EtDocs_statement.bindString(8, c_e.check_empty(eD.getContent()));
                                        EtDocs_statement.bindString(9, c_e.check_empty(eD.getDocId()));
                                        EtDocs_statement.bindString(10, c_e.check_empty(eD.getDocType()));
                                        EtDocs_statement.bindString(11, c_e.check_empty(eD.getObjtype()));
                                        EtDocs_statement.bindString(12, "");
                                        EtDocs_statement.bindString(13, "Old");
                                        EtDocs_statement.bindString(14, c_e.check_empty(eD.getContentX()));
                                        EtDocs_statement.execute();
                                    }
                                }
                            }
                        }*/
                        App_db.setTransactionSuccessful();
                        Get_Response = "success";
                    }finally {
                        App_db.endTransaction();
                    }
                } else {
                        Get_Response = "no data";
                    }
                }

        } catch (Exception ex) {
            Log.v("kiran_notif_ex", ex.getMessage() + "...");
            Get_Response = "exception";
        }
        return Get_Response;
    }
}
