package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty1;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class REST_Insert_Notifications_Data
{



    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static String Get_Response = "";
    private static ArrayList<HashMap<String, String>> notifications_uuid_list = new ArrayList<HashMap<String, String>>();
    private static String aufnr = "";
    private static long startTime = 0;
    private static Check_Empty checkempty = new Check_Empty();
    private static Check_Empty1 checkempty1 = new Check_Empty1();



    public static String Insert_Notif_Data(Context activity, REST_Notifications_SER notification_response, String qmnum, String type)
    {



        try
        {
            DATABASE_NAME =  activity.getString(R.string.database_name);
            App_db  = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);



            if (qmnum != null && !qmnum.equals(""))
            {
                App_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{qmnum});
                App_db.execSQL("delete from EtNotifStatus where Qmnum = ?", new String[]{qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{qmnum});
                App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifTasks where Qmnum = ?", new String[]{qmnum});
            }


            startTime = System.currentTimeMillis();
            App_db.execSQL("PRAGMA synchronous=OFF");
            App_db.setLockingEnabled(false);
            App_db.beginTransaction();



            /*Reading and Inserting Data into Database Table for EtNotifHeader*/
            try
            {
                List<REST_Notifications_SER.ETNOTIFHEADER> etNotifHeader = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifHeader = notification_response.geteTNOTIFHEADER();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifHeader = notification_response.geteTNOTIFHEADER();
                }
                if (etNotifHeader != null && etNotifHeader.size() > 0)
                {
                    String EtNotifHeader_sql = "Insert into DUE_NOTIFICATION_NotifHeader (UUID,NotifType,Qmnum,NotifShorttxt,FunctionLoc,Equipment,Bautl,ReportedBy,MalfuncStdate,MalfuncEddate,MalfuncSttime,MalfuncEdtime,BreakdownInd,Priority,Ingrp,Arbpl,Werks,Strmn,Ltrmn,Aufnr,Docs,Altitude,Latitude,Longitude,Closed,Completed,Createdon,Qmartx,Pltxt,Eqktx,Priokx,Auftext,Auarttext,Plantname,Wkctrname,Ingrpname,Maktx,Xstatus,Usr01,Usr02,Usr03,Usr04,Usr05,STATUS,ParnrVw,NameVw,Auswk,Shift,Noofperson,Auswkt, Strur, Ltrur, sort_malfc, Qmdat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtNotifHeader_statement = App_db.compileStatement(EtNotifHeader_sql);
                    EtNotifHeader_statement.clearBindings();
                    for (REST_Notifications_SER.ETNOTIFHEADER etNotifHeaderResult : etNotifHeader)
                    {
                        EtNotifHeader_statement.bindString(1, checkempty.check_empty(etNotifHeaderResult.getQMNUM()));
                        EtNotifHeader_statement.bindString(2,checkempty.check_empty(etNotifHeaderResult.getNOTIFTYPE()));
                        EtNotifHeader_statement.bindString(3,checkempty.check_empty(etNotifHeaderResult.getQMNUM()));
                        EtNotifHeader_statement.bindString(4,checkempty.check_empty(etNotifHeaderResult.getNOTIFSHORTTXT()));
                        EtNotifHeader_statement.bindString(5,checkempty.check_empty(etNotifHeaderResult.getFUNCTIONLOC()));
                        EtNotifHeader_statement.bindString(6,checkempty.check_empty(etNotifHeaderResult.getEQUIPMENT()));
                        EtNotifHeader_statement.bindString(7,checkempty.check_empty(etNotifHeaderResult.getBAUTL()));
                        EtNotifHeader_statement.bindString(8,checkempty.check_empty(etNotifHeaderResult.getREPORTEDBY()));
                        EtNotifHeader_statement.bindString(9,checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTDATE()));
                        EtNotifHeader_statement.bindString(10,checkempty.check_empty(etNotifHeaderResult.getMALFUNCEDDATE()));
                        EtNotifHeader_statement.bindString(11,checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTTIME()));
                        EtNotifHeader_statement.bindString(12,checkempty.check_empty(etNotifHeaderResult.getMALFUNCEDTIME()));
                        EtNotifHeader_statement.bindString(13,checkempty.check_empty(etNotifHeaderResult.getBREAKDOWNIND()));
                        EtNotifHeader_statement.bindString(14,checkempty.check_empty(etNotifHeaderResult.getPRIORITY()));
                        EtNotifHeader_statement.bindString(15,checkempty.check_empty(etNotifHeaderResult.getINGRP()));
                        EtNotifHeader_statement.bindString(16,checkempty.check_empty(etNotifHeaderResult.getARBPL()));
                        EtNotifHeader_statement.bindString(17,checkempty.check_empty(etNotifHeaderResult.getWERKS()));
                        EtNotifHeader_statement.bindString(18,checkempty.check_empty(etNotifHeaderResult.getSTRMN()));
                        EtNotifHeader_statement.bindString(19,checkempty.check_empty(etNotifHeaderResult.getLTRMN()));
                        EtNotifHeader_statement.bindString(20,checkempty.check_empty(etNotifHeaderResult.getAUFNR()));
                        aufnr = checkempty.check_empty(etNotifHeaderResult.getAUFNR());
                        EtNotifHeader_statement.bindString(21,checkempty.check_empty(etNotifHeaderResult.getDOCS()));
                        EtNotifHeader_statement.bindString(22,checkempty.check_empty(etNotifHeaderResult.getALTITUDE()));
                        EtNotifHeader_statement.bindString(23,checkempty.check_empty(etNotifHeaderResult.getLATITUDE()));
                        EtNotifHeader_statement.bindString(24,checkempty.check_empty(etNotifHeaderResult.getLONGITUDE()));
                        EtNotifHeader_statement.bindString(25,checkempty.check_empty(etNotifHeaderResult.getCLOSED()));
                        EtNotifHeader_statement.bindString(26,checkempty.check_empty(etNotifHeaderResult.getCOMPLETED()));
                        EtNotifHeader_statement.bindString(27,checkempty.check_empty(etNotifHeaderResult.getCREATEDON()));
                        EtNotifHeader_statement.bindString(28,checkempty.check_empty(etNotifHeaderResult.getQMARTX()));
                        EtNotifHeader_statement.bindString(29,checkempty.check_empty(etNotifHeaderResult.getPLTXT()));
                        EtNotifHeader_statement.bindString(30,checkempty.check_empty(etNotifHeaderResult.getEQKTX()));
                        EtNotifHeader_statement.bindString(31,checkempty.check_empty(etNotifHeaderResult.getPRIOKX()));
                        EtNotifHeader_statement.bindString(32,checkempty.check_empty(etNotifHeaderResult.getAUFTEXT()));
                        EtNotifHeader_statement.bindString(33,checkempty.check_empty(etNotifHeaderResult.getAUARTTEXT()));
                        EtNotifHeader_statement.bindString(34,checkempty.check_empty(etNotifHeaderResult.getPLANTNAME()));
                        EtNotifHeader_statement.bindString(35,checkempty.check_empty(etNotifHeaderResult.getWKCTRNAME()));
                        EtNotifHeader_statement.bindString(36,checkempty.check_empty(etNotifHeaderResult.getINGRPNAME()));
                        EtNotifHeader_statement.bindString(37,checkempty.check_empty(etNotifHeaderResult.getMAKTX()));
                        EtNotifHeader_statement.bindString(38,checkempty.check_empty(etNotifHeaderResult.getXSTATUS()));
                        EtNotifHeader_statement.bindString(39,checkempty.check_empty(etNotifHeaderResult.getUSR01()));
                        EtNotifHeader_statement.bindString(40,checkempty.check_empty(etNotifHeaderResult.getUSR02()));
                        EtNotifHeader_statement.bindString(41,checkempty.check_empty(etNotifHeaderResult.getUSR03()));
                        EtNotifHeader_statement.bindString(42,checkempty.check_empty(etNotifHeaderResult.getUSR04()));
                        EtNotifHeader_statement.bindString(43,checkempty.check_empty(etNotifHeaderResult.getUSR05()));
                        EtNotifHeader_statement.bindString(44,checkempty.check_empty(etNotifHeaderResult.getXSTATUS()));
                        EtNotifHeader_statement.bindString(45,checkempty.check_empty(etNotifHeaderResult.getPARNRVW()));
                        EtNotifHeader_statement.bindString(46,checkempty.check_empty(etNotifHeaderResult.getNAMEVW()));
                        EtNotifHeader_statement.bindString(47,checkempty.check_empty(etNotifHeaderResult.getAUSWK()));
                        EtNotifHeader_statement.bindString(48,checkempty.check_empty(etNotifHeaderResult.getSHIFT()));
                        EtNotifHeader_statement.bindString(49,checkempty.check_empty(etNotifHeaderResult.getNOOFPERSON()));
                        EtNotifHeader_statement.bindString(50,checkempty.check_empty(etNotifHeaderResult.getAUSWKT()));
                        EtNotifHeader_statement.bindString(51,checkempty.check_empty(etNotifHeaderResult.getSTRUR()));
                        EtNotifHeader_statement.bindString(52,etNotifHeaderResult.getLTRUR());
                        EtNotifHeader_statement.bindString(53,checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTDATE())+" "+checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTTIME()));
                        EtNotifHeader_statement.bindString(54,checkempty.check_empty(etNotifHeaderResult.getQMDAT()));
                        EtNotifHeader_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifHeader*/




            /*Reading and Inserting Data into Database Table for EtNotifItems*/
            try
            {
                List<REST_Notifications_SER.ETNOTIFITEM> etNotifItems = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifItems = notification_response.geteTNOTIFITEMS();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifItems = notification_response.geteTNOTIFITEMS();
                }
                if (etNotifItems != null && etNotifItems.size() > 0)
                {
                    String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                    EtNotifItems_statement.clearBindings();
                    for (REST_Notifications_SER.ETNOTIFITEM etNotifItemsResult : etNotifItems)
                    {
                        EtNotifItems_statement.bindString(1,checkempty.check_empty(etNotifItemsResult.getQMNUM()));
                        EtNotifItems_statement.bindString(2,checkempty.check_empty(etNotifItemsResult.getQMNUM()));
                        EtNotifItems_statement.bindString(3,checkempty1.check_empty(etNotifItemsResult.getITEMKEY()));
                        EtNotifItems_statement.bindString(4,checkempty.check_empty(etNotifItemsResult.getITEMPARTGRP()));
                        EtNotifItems_statement.bindString(5,checkempty.check_empty(etNotifItemsResult.getPARTGRPTEXT()));
                        EtNotifItems_statement.bindString(6,checkempty1.check_empty(etNotifItemsResult.getITEMPARTCOD()));
                        EtNotifItems_statement.bindString(7,checkempty.check_empty(etNotifItemsResult.getPARTCODETEXT()));
                        EtNotifItems_statement.bindString(8,checkempty.check_empty(etNotifItemsResult.getITEMDEFECTGRP()));
                        EtNotifItems_statement.bindString(9,checkempty.check_empty(etNotifItemsResult.getDEFECTGRPTEXT()));
                        EtNotifItems_statement.bindString(10,checkempty1.check_empty(etNotifItemsResult.getITEMDEFECTCOD()));
                        EtNotifItems_statement.bindString(11,checkempty.check_empty(etNotifItemsResult.getDEFECTCODETEXT()));
                        EtNotifItems_statement.bindString(12,checkempty.check_empty(etNotifItemsResult.getITEMDEFECTSHTXT()));
                        EtNotifItems_statement.bindString(13,checkempty1.check_empty(etNotifItemsResult.getCAUSEKEY()));
                        EtNotifItems_statement.bindString(14,checkempty.check_empty(etNotifItemsResult.getCAUSEGRP()));
                        EtNotifItems_statement.bindString(15,checkempty.check_empty(etNotifItemsResult.getCAUSEGRPTEXT()));
                        EtNotifItems_statement.bindString(16,checkempty1.check_empty(etNotifItemsResult.getCAUSECOD()));
                        EtNotifItems_statement.bindString(17,checkempty.check_empty(etNotifItemsResult.getCAUSECODETEXT()));
                        EtNotifItems_statement.bindString(18,checkempty.check_empty(etNotifItemsResult.getCAUSESHTXT()));
                        EtNotifItems_statement.bindString(19,"");
                        EtNotifItems_statement.bindString(20,"");
                        EtNotifItems_statement.bindString(21,"");
                        EtNotifItems_statement.bindString(22,"");
                        EtNotifItems_statement.bindString(23,"");
                        EtNotifItems_statement.bindString(24,"U");
                        EtNotifItems_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifItems*/



            /*Reading and Inserting Data into Database Table for EtNotifActvs*/
            try
            {
                List<REST_Notifications_SER.ETNOTIFACTVS> etNotifActvs = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifActvs = notification_response.getEtnotifactvs();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifActvs = notification_response.getEtnotifactvs();
                }
                if (etNotifActvs != null && etNotifActvs.size() > 0)
                {
                    String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                    EtNotifActvs_statement.clearBindings();
                    for (REST_Notifications_SER.ETNOTIFACTVS etNotifActvsResult : etNotifActvs)
                    {
                        EtNotifActvs_statement.bindString(1,checkempty.check_empty(etNotifActvsResult.getQMNUM()));
                        EtNotifActvs_statement.bindString(2,checkempty.check_empty(etNotifActvsResult.getQMNUM()));
                        EtNotifActvs_statement.bindString(3,checkempty1.check_empty(etNotifActvsResult.getITEMKEY()));
                        EtNotifActvs_statement.bindString(4,checkempty.check_empty(etNotifActvsResult.getITEMPARTCOD()));
                        EtNotifActvs_statement.bindString(5,checkempty.check_empty(etNotifActvsResult.getPARTGRPTEXT()));
                        EtNotifActvs_statement.bindString(6,checkempty.check_empty(etNotifActvsResult.getITEMPARTCOD()));
                        EtNotifActvs_statement.bindString(7,checkempty.check_empty(etNotifActvsResult.getPARTGRPTEXT()));
                        EtNotifActvs_statement.bindString(8,checkempty.check_empty(etNotifActvsResult.getITEMDEFECTGRP()));
                        EtNotifActvs_statement.bindString(9,checkempty.check_empty(etNotifActvsResult.getDEFECTGRPTEXT()));
                        EtNotifActvs_statement.bindString(10,checkempty.check_empty(etNotifActvsResult.getITEMDEFECTCOD()));
                        EtNotifActvs_statement.bindString(11,checkempty.check_empty(etNotifActvsResult.getDEFECTCODETEXT()));
                        EtNotifActvs_statement.bindString(12,checkempty.check_empty(etNotifActvsResult.getITEMDEFECTSHTXT()));
                        EtNotifActvs_statement.bindString(13,checkempty1.check_empty(etNotifActvsResult.getCAUSEKEY()));
                        EtNotifActvs_statement.bindString(14,checkempty1.check_empty(etNotifActvsResult.getACTKEY()));
                        EtNotifActvs_statement.bindString(15,checkempty.check_empty(etNotifActvsResult.getACTVGRP()));
                        EtNotifActvs_statement.bindString(16,checkempty.check_empty(etNotifActvsResult.getACTGRPTEXT()));
                        EtNotifActvs_statement.bindString(17,checkempty1.check_empty(etNotifActvsResult.getACTVCOD()));
                        EtNotifActvs_statement.bindString(18,checkempty.check_empty(etNotifActvsResult.getACTCODETEXT()));
                        EtNotifActvs_statement.bindString(19,checkempty.check_empty(etNotifActvsResult.getACTVSHTXT()));
                        EtNotifActvs_statement.bindString(20,checkempty.check_empty(etNotifActvsResult.getSTARTDATE()));
                        EtNotifActvs_statement.bindString(21,checkempty.check_empty(etNotifActvsResult.getSTARTTIME()));
                        EtNotifActvs_statement.bindString(22,checkempty.check_empty(etNotifActvsResult.getENDDATE()));
                        EtNotifActvs_statement.bindString(23,checkempty.check_empty(etNotifActvsResult.getENDTIME()));
                        EtNotifActvs_statement.bindString(24,checkempty.check_empty(etNotifActvsResult.getUSR01()));
                        EtNotifActvs_statement.bindString(25,checkempty.check_empty(etNotifActvsResult.getUSR02()));
                        EtNotifActvs_statement.bindString(26,checkempty.check_empty(etNotifActvsResult.getUSR03()));
                        EtNotifActvs_statement.bindString(27,checkempty.check_empty(etNotifActvsResult.getUSR04()));
                        EtNotifActvs_statement.bindString(28,checkempty.check_empty(etNotifActvsResult.getUSR05()));
                        EtNotifActvs_statement.bindString(24,"U");
                        EtNotifActvs_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifActvs*/




            /*Reading and Inserting Data into Database Table for EtNotifTasks*/
            try
            {
                List<REST_Notifications_SER.ET_NOTIF_TASKS> etNotifTasks = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifTasks = notification_response.getEt_notif_tasks();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifTasks = notification_response.getEt_notif_tasks();
                }
                if (etNotifTasks != null && etNotifTasks.size() > 0)
                {
                    String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext, TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat, Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                    EtNotifTasks_statement.clearBindings();
                    for (REST_Notifications_SER.ET_NOTIF_TASKS etNotifTasksResult : etNotifTasks)
                    {
                        EtNotifTasks_statement.bindString(1,checkempty.check_empty(etNotifTasksResult.getQMNUM()));
                        EtNotifTasks_statement.bindString(2,checkempty.check_empty(etNotifTasksResult.getQMNUM()));
                        EtNotifTasks_statement.bindString(3,checkempty1.check_empty(etNotifTasksResult.getITEMKEY()));
                        EtNotifTasks_statement.bindString(4,checkempty.check_empty(etNotifTasksResult.getITEMPARTGRP()));
                        EtNotifTasks_statement.bindString(5,checkempty.check_empty(etNotifTasksResult.getPARTGRPTEXT()));
                        EtNotifTasks_statement.bindString(6,checkempty.check_empty(etNotifTasksResult.getITEMDEFECTCOD()));
                        EtNotifTasks_statement.bindString(7,checkempty.check_empty(etNotifTasksResult.getPARTCODETEXT()));
                        EtNotifTasks_statement.bindString(8,checkempty.check_empty(etNotifTasksResult.getITEMDEFECTGRP()));
                        EtNotifTasks_statement.bindString(9,checkempty.check_empty(etNotifTasksResult.getDEFECTGRPTEXT()));
                        EtNotifTasks_statement.bindString(10,checkempty.check_empty(etNotifTasksResult.getITEMDEFECTCOD()));
                        EtNotifTasks_statement.bindString(11,checkempty.check_empty(etNotifTasksResult.getDEFECTCODETEXT()));
                        EtNotifTasks_statement.bindString(12,checkempty.check_empty(etNotifTasksResult.getITEMDEFECTSHTXT()));
                        EtNotifTasks_statement.bindString(13,checkempty1.check_empty(etNotifTasksResult.getTASKKEY()));
                        EtNotifTasks_statement.bindString(14,checkempty.check_empty(etNotifTasksResult.getTASKGRP()));
                        EtNotifTasks_statement.bindString(15,checkempty.check_empty(etNotifTasksResult.getTASKGRPTEXT()));
                        EtNotifTasks_statement.bindString(16,checkempty1.check_empty(etNotifTasksResult.getTASKCOD()));
                        EtNotifTasks_statement.bindString(17,checkempty.check_empty(etNotifTasksResult.getTASKCODETEXT()));
                        EtNotifTasks_statement.bindString(18,checkempty.check_empty(etNotifTasksResult.getTASKSHTXT()));
                        EtNotifTasks_statement.bindString(19,checkempty.check_empty(etNotifTasksResult.getPSTER()));
                        EtNotifTasks_statement.bindString(20,checkempty.check_empty(etNotifTasksResult.getPETER()));
                        EtNotifTasks_statement.bindString(21,checkempty.check_empty(etNotifTasksResult.getPSTUR()));
                        EtNotifTasks_statement.bindString(22,checkempty.check_empty(etNotifTasksResult.getPETUR()));
                        EtNotifTasks_statement.bindString(23,checkempty.check_empty(etNotifTasksResult.getPARVW()));
                        EtNotifTasks_statement.bindString(24,checkempty.check_empty(etNotifTasksResult.getPARNR()));
                        EtNotifTasks_statement.bindString(25,checkempty.check_empty(etNotifTasksResult.getERLNAM()));
                        EtNotifTasks_statement.bindString(26,checkempty.check_empty(etNotifTasksResult.getERLDAT()));
                        EtNotifTasks_statement.bindString(27,checkempty.check_empty(etNotifTasksResult.getERLZEIT()));
                        EtNotifTasks_statement.bindString(28,checkempty.check_empty(etNotifTasksResult.getRELEASE()));
                        EtNotifTasks_statement.bindString(29,checkempty.check_empty(etNotifTasksResult.getCOMPLETE()));
                        EtNotifTasks_statement.bindString(30,checkempty.check_empty(etNotifTasksResult.getSUCCESS()));
                        EtNotifTasks_statement.bindString(31,checkempty.check_empty(etNotifTasksResult.getUSERSTATUS()));
                        EtNotifTasks_statement.bindString(32,checkempty.check_empty(etNotifTasksResult.getSYSSTATUS()));
                        EtNotifTasks_statement.bindString(33,checkempty.check_empty(etNotifTasksResult.getSMSTTXT()));
                        EtNotifTasks_statement.bindString(34,checkempty.check_empty(etNotifTasksResult.getSMASTXT()));
                        EtNotifTasks_statement.bindString(35,checkempty.check_empty(etNotifTasksResult.getUSR01()));
                        EtNotifTasks_statement.bindString(36,checkempty.check_empty(etNotifTasksResult.getUSR02()));
                        EtNotifTasks_statement.bindString(37,checkempty.check_empty(etNotifTasksResult.getUSR03()));
                        EtNotifTasks_statement.bindString(38,checkempty.check_empty(etNotifTasksResult.getUSR04()));
                        EtNotifTasks_statement.bindString(39,checkempty.check_empty(etNotifTasksResult.getUSR05()));
                        EtNotifTasks_statement.bindString(40,"U");
                        EtNotifTasks_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifTasks*/





            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
            try
            {
                List<REST_Notifications_SER.ETNOTIFLONGTEXT> etNotifLongtext = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifLongtext = notification_response.geteTNOTIFLONGTEXT();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifLongtext = notification_response.geteTNOTIFLONGTEXT();
                }
                if (etNotifLongtext != null && etNotifLongtext.size() > 0)
                {
                    String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext (UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                    SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                    EtNotifLongtext_statement.clearBindings();
                    for (REST_Notifications_SER.ETNOTIFLONGTEXT etNotifLongtextResult : etNotifLongtext)
                    {
                        EtNotifLongtext_statement.bindString(1,checkempty.check_empty(etNotifLongtextResult.getQMNUM()));
                        EtNotifLongtext_statement.bindString(2,checkempty.check_empty(etNotifLongtextResult.getQMNUM()));
                        EtNotifLongtext_statement.bindString(3,checkempty.check_empty(etNotifLongtextResult.getOBJTYPE()));
                        EtNotifLongtext_statement.bindString(4,checkempty.check_empty(etNotifLongtextResult.getTEXTLINE()));
                        EtNotifLongtext_statement.bindString(5,checkempty.check_empty(etNotifLongtextResult.getOBJKEY()));
                        EtNotifLongtext_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/




            /*Reading and Inserting Data into Database Table for EtNotifStatus*/
            try
            {
                List<REST_Notifications_SER.ETNOTIFStatus> etNotifStatus = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifStatus = notification_response.geteTNOTIFSTATUS();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifStatus = notification_response.geteTNOTIFSTATUS();
                }
                if (etNotifStatus != null && etNotifStatus.size() > 0)
                {
                    String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum,Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act,Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                    EtNotifStatus_statement.clearBindings();
                    for (REST_Notifications_SER.ETNOTIFStatus etNotifStatusResult : etNotifStatus)
                    {
                        EtNotifStatus_statement.bindString(1,checkempty.check_empty(etNotifStatusResult.getQMNUM()));
                        EtNotifStatus_statement.bindString(2,checkempty.check_empty(etNotifStatusResult.getQMNUM()));
                        EtNotifStatus_statement.bindString(3,"");
                        EtNotifStatus_statement.bindString(4,checkempty.check_empty(etNotifStatusResult.getOBJNR()));
                        EtNotifStatus_statement.bindString(5,"");
                        EtNotifStatus_statement.bindString(6,checkempty.check_empty(etNotifStatusResult.getSTSMA()));
                        EtNotifStatus_statement.bindString(7,checkempty.check_empty(etNotifStatusResult.getINIST()));
                        EtNotifStatus_statement.bindString(8,checkempty.check_empty(etNotifStatusResult.getSTONR()));
                        EtNotifStatus_statement.bindString(9,checkempty.check_empty(etNotifStatusResult.getHSONR()));
                        EtNotifStatus_statement.bindString(10,checkempty.check_empty(etNotifStatusResult.getNSONR()));
                        EtNotifStatus_statement.bindString(11,checkempty.check_empty(etNotifStatusResult.getSTAT()));
                        EtNotifStatus_statement.bindString(12,checkempty.check_empty(etNotifStatusResult.getACT()));
                        EtNotifStatus_statement.bindString(13,checkempty.check_empty(etNotifStatusResult.getTXT04()));
                        EtNotifStatus_statement.bindString(14,checkempty.check_empty(etNotifStatusResult.getTXT30()));
                        EtNotifStatus_statement.bindString(15,"");
                        EtNotifStatus_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtNotifStatus*/





            /*Reading and Inserting Data into Database Table for EtDocs*/
            try
            {
                List<REST_Notifications_SER.ETDOC> etDocs = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etDocs = notification_response.geteTDOCS();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etDocs = notification_response.geteTDOCS();
                }
                if (etDocs != null && etDocs.size() > 0)
                {
                    String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                    EtDocs_statement.clearBindings();
                    for (REST_Notifications_SER.ETDOC etDocsResult : etDocs)
                    {
                        EtDocs_statement.bindString(1,checkempty.check_empty(etDocsResult.getZOBJID()));
                        EtDocs_statement.bindString(2,checkempty.check_empty(etDocsResult.getZOBJID()));
                        EtDocs_statement.bindString(3,checkempty.check_empty(etDocsResult.getZDOCTYPE()));
                        EtDocs_statement.bindString(4,checkempty.check_empty(etDocsResult.getZDOCTYPEITEM()));
                        EtDocs_statement.bindString(5,checkempty.check_empty(etDocsResult.getFILENAME()));
                        EtDocs_statement.bindString(6,checkempty.check_empty(etDocsResult.getFILETYPE()));
                        EtDocs_statement.bindString(7,checkempty.check_empty(etDocsResult.getFSIZE()));
                        EtDocs_statement.bindString(8,checkempty.check_empty(etDocsResult.getCONTENT()));
                        EtDocs_statement.bindString(9,checkempty.check_empty(etDocsResult.getDOCID()));
                        EtDocs_statement.bindString(10,checkempty.check_empty(etDocsResult.getDOCTYPE()));
                        EtDocs_statement.bindString(11,checkempty.check_empty(etDocsResult.getOBJTYPE()));
                        EtDocs_statement.bindString(12,"");
                        EtDocs_statement.bindString(13,"Old");
                        EtDocs_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtDocs*/



            App_db.setTransactionSuccessful();
            Get_Response = "Success";
        }
        catch(Exception e)
        {
            Get_Response = "Exception";
        }
        finally
        {
            App_db.endTransaction();
            App_db.setLockingEnabled(true);
            App_db.execSQL("PRAGMA synchronous=NORMAL");
            final long endtime = System.currentTimeMillis();
            Log.v("kiran_DNOT_insert", String.valueOf(endtime - startTime)+" Milliseconds");
        }

        return Get_Response;
    }



    public static String Get_Aufnr_number()
    {
        return aufnr;
    }



}
