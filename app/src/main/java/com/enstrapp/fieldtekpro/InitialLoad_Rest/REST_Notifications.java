package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class REST_Notifications
{

    private static String password = "", url_link = "", username = "", device_serial_number = "", device_id = "", device_uuid = "", Get_Response = "";
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static Check_Empty checkempty = new Check_Empty();



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


    public static String Get_DNOT_Data(Activity activity, String transmit_type, String Qmnum)
    {
        try
        {
            DATABASE_NAME = activity.getString(R.string.database_name);
            App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
            if(transmit_type.equals("LOAD"))
            {
                /* Creating GET_DUE_NOTIFICATION_NotifHeader Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_DUE_NOTIFICATIONS_NotifHeader);
                String CREATE_DUE_NOTIFICATION_NotifHeader_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_DUE_NOTIFICATIONS_NotifHeader+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NotifType+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmnum+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NotifShorttxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_FunctionLoc+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Equipment+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Bautl+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ReportedBy+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncStdate+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEddate+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncSttime+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_MalfuncEdtime+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_BreakdownInd+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Priority+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Arbpl+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Werks+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Strmn+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrmn+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Aufnr+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Docs+ " TEXT, "
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Altitude+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Latitude+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Longitude+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Closed+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Completed+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Createdon+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmartx+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Pltxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Eqktx+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Priokx+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auftext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auarttext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Plantname+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Wkctrname+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ingrpname+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Maktx+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Xstatus+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr01+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr02+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr03+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr04+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Usr05+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_status + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_ParnrVw+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_NameVw+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auswk+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Shift+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Noofperson+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Auswkt + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Strur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Ltrur + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_sort_malfc + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_NotifHeader_Qmdat+ " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_NotifHeader_TABLE);
		        /* Creating GET_DUE_NOTIFICATION_NotifHeader Table with Fields */


		        /* Creating DUE_NOTIFICATIONS_EtNotifItems Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_DUE_NOTIFICATIONS_EtNotifItems);
                String CREATE_DUE_NOTIFICATIONS_EtNotifItems = "CREATE TABLE IF NOT EXISTS "+ TABLE_DUE_NOTIFICATIONS_EtNotifItems+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Qmnum+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Partgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItempartCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Partcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Defectcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_ItemdefectShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Causegrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Causecodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_CauseShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr01+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr02+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr03+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr04+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Usr05+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifItems_Status + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATIONS_EtNotifItems);
		        /* Creating DUE_NOTIFICATIONS_EtNotifItems Table with Fields */

		        /* Creating GET_DUE_NOTIFICATION_EtNotifActvs Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtNotifActvs);
                String CREATE_DUE_NOTIFICATION_EtNotifActvs_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtNotifActvs+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_id+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Qmnum+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItempartCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Partcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Defectcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ItemdefectShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_CauseKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Actcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_ActvShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartDate+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_StartTime+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndDate+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_EndTime+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr01+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr02+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr03+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr04+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Usr05+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Fields+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifActvs_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifActvs_TABLE);
		        /* Creating GET_DUE_NOTIFICATION_EtNotifActvs Table with Fields */


		        /* Creating GET_DUE_NOTIFICATION_EtNotifLongtext Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext);
                String CREATE_DUE_NOTIFICATION_EtNotifLongtext_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_DUE_NOTIFICATIONS_EtNotifLongtext+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Qmnum+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Objtype+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_TextLine+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_LONGTEXT_Objkey + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifLongtext_TABLE);
		        /* Creating GET_DUE_NOTIFICATION_EtNotifLongtext Table with Fields */

		        /* Creating EtNotifStatus Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_EtNotifStatus);
                String CREATE_TABLE_EtNotifStatus = "CREATE TABLE IF NOT EXISTS "+ TABLE_EtNotifStatus + ""
                        + "( "
                        + KEY_EtNotifStatus_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_EtNotifStatus_UUID+ " TEXT,"
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
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtDocs);
                String CREATE_DUE_NOTIFICATIONS_EtDocs_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtDocs+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtDocs_ID+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Zobjid+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Zdoctype+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_ZdoctypeItem+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filename+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filetype+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Fsize+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Content+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_DocId+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_DocType+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Objtype + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Filepath + " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtDocs_Status + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATIONS_EtDocs_TABLE);
		        /* Creating DUE_NOTIFICATIONS_EtDocs Table with Fields */

		        /* Creating GET_DUE_NOTIFICATION_EtNotifTasks Table with Fields */
                App_db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtNotifTasks);
                String CREATE_DUE_NOTIFICATION_EtNotifTasks_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_GET_DUE_NOTIFICATION_EtNotifTasks+ ""
                        + "( "
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_id+ " INTEGER PRIMARY KEY,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_UUID+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Qmnum+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItempartCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Partcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Defectcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_ItemdefectShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskKey+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskGrp+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskgrptext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskCod+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Taskcodetext+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_TaskShtxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pster+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Peter+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Pstur+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Petur+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parvw+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Parnr+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlnam+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erldat+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Erlzeit+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Release+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Complete+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Success+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_UserStatus+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_SysStatus+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smsttxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Smastxt+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr01+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr02+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr03+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr04+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Usr05+ " TEXT,"
                        + KEY_DUE_NOTIFICATIONS_EtNotifTasks_Action + " TEXT"
                        + ")";
                App_db.execSQL(CREATE_DUE_NOTIFICATION_EtNotifTasks_TABLE);
		        /* Creating GET_DUE_NOTIFICATION_EtNotifTasks Table with Fields */
            }
            else if(transmit_type.equals("Single"))
            {
                App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{Qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{Qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{Qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{Qmnum});
                App_db.execSQL("delete from EtNotifStatus where Qmnum = ?", new String[]{Qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{Qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks where Qmnum = ?", new String[]{Qmnum});
            }
            else
            {
                App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader");
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs");
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext");
                App_db.execSQL("delete from EtNotifStatus");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs");
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks");
            }
            FieldTekPro_SharedPref = activity.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
            FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
            username = FieldTekPro_SharedPref.getString("Username",null);
            password = FieldTekPro_SharedPref.getString("Password",null);
            String webservice_type = FieldTekPro_SharedPref.getString("webservice_type",null);
            if(transmit_type.equals("Single"))
            {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"Q", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
            }
            else
            {
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?",new String[]{"C1", "DN", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
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
            Call<REST_Notifications_SER> call = service.postNOTIFDetails(url_link, basic, modelLoginRest);
            Response<REST_Notifications_SER> response = call.execute();
            int response_status_code = response.code();
            Log.v("kiran_DNOT_code",response_status_code+"...");
            if(response_status_code == 200)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String notif_insert_status = REST_Insert_Notifications_Data.Insert_Notif_Data(activity, response.body(), "","DUNOT");
                    if(notif_insert_status.equalsIgnoreCase("Success"))
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
            Log.v("kiran_DNOT_ex",ex.getMessage()+"...");
            Get_Response = "exception";
        }
        finally
        {
        }
        return Get_Response;
    }

}
