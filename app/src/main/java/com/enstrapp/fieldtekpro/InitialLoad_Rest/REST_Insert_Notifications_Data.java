package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.enstrapp.fieldtekpro.Initialload.Notifications_SER;
import com.enstrapp.fieldtekpro.R;
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
                        EtNotifHeader_statement.bindString(9,etNotifHeaderResult.getMALFUNCSTDATE());
                        EtNotifHeader_statement.bindString(10,etNotifHeaderResult.getMALFUNCEDDATE());
                        EtNotifHeader_statement.bindString(11,etNotifHeaderResult.getMALFUNCSTTIME());
                        EtNotifHeader_statement.bindString(12,etNotifHeaderResult.getMALFUNCEDTIME());
                        EtNotifHeader_statement.bindString(13,checkempty.check_empty(etNotifHeaderResult.getBREAKDOWNIND()));
                        EtNotifHeader_statement.bindString(14,checkempty.check_empty(etNotifHeaderResult.getPRIORITY()));
                        EtNotifHeader_statement.bindString(15,checkempty.check_empty(etNotifHeaderResult.getINGRP()));
                        EtNotifHeader_statement.bindString(16,checkempty.check_empty(etNotifHeaderResult.getARBPL()));
                        EtNotifHeader_statement.bindString(17,checkempty.check_empty(etNotifHeaderResult.getWERKS()));
                        EtNotifHeader_statement.bindString(18,etNotifHeaderResult.getSTRMN());
                        EtNotifHeader_statement.bindString(19,etNotifHeaderResult.getLTRMN());
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
                        EtNotifHeader_statement.bindString(51,etNotifHeaderResult.getSTRUR());
                        EtNotifHeader_statement.bindString(52,etNotifHeaderResult.getLTRUR());
                        EtNotifHeader_statement.bindString(53,checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTDATE())+" "+checkempty.check_empty(etNotifHeaderResult.getMALFUNCSTTIME()));
                        EtNotifHeader_statement.bindString(54,etNotifHeaderResult.getQMDAT());
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
                        EtNotifItems_statement.bindString(3,etNotifItemsResult.getITEMKEY());
                        EtNotifItems_statement.bindString(4,checkempty.check_empty(etNotifItemsResult.getITEMPARTGRP()));
                        EtNotifItems_statement.bindString(5,checkempty.check_empty(etNotifItemsResult.getPARTGRPTEXT()));
                        EtNotifItems_statement.bindString(6,etNotifItemsResult.getITEMPARTCOD());
                        EtNotifItems_statement.bindString(7,checkempty.check_empty(etNotifItemsResult.getPARTCODETEXT()));
                        EtNotifItems_statement.bindString(8,checkempty.check_empty(etNotifItemsResult.getITEMDEFECTGRP()));
                        EtNotifItems_statement.bindString(9,checkempty.check_empty(etNotifItemsResult.getDEFECTGRPTEXT()));
                        EtNotifItems_statement.bindString(10,etNotifItemsResult.getITEMDEFECTCOD());
                        EtNotifItems_statement.bindString(11,checkempty.check_empty(etNotifItemsResult.getDEFECTCODETEXT()));
                        EtNotifItems_statement.bindString(12,checkempty.check_empty(etNotifItemsResult.getITEMDEFECTSHTXT()));
                        EtNotifItems_statement.bindString(13,etNotifItemsResult.getCAUSEKEY());
                        EtNotifItems_statement.bindString(14,checkempty.check_empty(etNotifItemsResult.getCAUSEGRP()));
                        EtNotifItems_statement.bindString(15,checkempty.check_empty(etNotifItemsResult.getCAUSEGRPTEXT()));
                        EtNotifItems_statement.bindString(16,etNotifItemsResult.getCAUSECOD());
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
            /*try
            {
                Notifications_SER.EtNotifActvs etNotifActvs = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifActvs = notification_response.getD().getResults().get(0).getEtNotifActvs();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifActvs = notification_response.getD().getEtNotifActvs();
                }
                if (etNotifActvs != null)
                {
                    List<Notifications_SER.EtNotifActvs_Result> etNotifActvs_results = etNotifActvs.getResults();
                    if (etNotifActvs_results != null && etNotifActvs_results.size() > 0)
                    {
                        String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, StartDate, StartTime, EndDate, EndTime, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                        EtNotifActvs_statement.clearBindings();
                        for (Notifications_SER.EtNotifActvs_Result etNotifActvsResult : etNotifActvs_results)
                        {
                            EtNotifActvs_statement.bindString(1,checkempty.check_empty(etNotifActvsResult.getQmnum()));
                            EtNotifActvs_statement.bindString(2,checkempty.check_empty(etNotifActvsResult.getQmnum()));
                            EtNotifActvs_statement.bindString(3,etNotifActvsResult.getItemKey());
                            EtNotifActvs_statement.bindString(4,checkempty.check_empty(etNotifActvsResult.getItempartGrp()));
                            EtNotifActvs_statement.bindString(5,checkempty.check_empty(etNotifActvsResult.getPartgrptext()));
                            EtNotifActvs_statement.bindString(6,checkempty.check_empty(etNotifActvsResult.getItempartCod()));
                            EtNotifActvs_statement.bindString(7,checkempty.check_empty(etNotifActvsResult.getPartcodetext()));
                            EtNotifActvs_statement.bindString(8,checkempty.check_empty(etNotifActvsResult.getItemdefectGrp()));
                            EtNotifActvs_statement.bindString(9,checkempty.check_empty(etNotifActvsResult.getDefectgrptext()));
                            EtNotifActvs_statement.bindString(10,checkempty.check_empty(etNotifActvsResult.getItemdefectCod()));
                            EtNotifActvs_statement.bindString(11,checkempty.check_empty(etNotifActvsResult.getDefectcodetext()));
                            EtNotifActvs_statement.bindString(12,checkempty.check_empty(etNotifActvsResult.getItemdefectShtxt()));
                            EtNotifActvs_statement.bindString(13,etNotifActvsResult.getCauseKey());
                            EtNotifActvs_statement.bindString(14,etNotifActvsResult.getActvKey());
                            EtNotifActvs_statement.bindString(15,checkempty.check_empty(etNotifActvsResult.getActvGrp()));
                            EtNotifActvs_statement.bindString(16,checkempty.check_empty(etNotifActvsResult.getActgrptext()));
                            EtNotifActvs_statement.bindString(17,etNotifActvsResult.getActvCod());
                            EtNotifActvs_statement.bindString(18,checkempty.check_empty(etNotifActvsResult.getActcodetext()));
                            EtNotifActvs_statement.bindString(19,checkempty.check_empty(etNotifActvsResult.getActvShtxt()));
                            EtNotifActvs_statement.bindString(20,etNotifActvsResult.getStartDate());
                            EtNotifActvs_statement.bindString(21,etNotifActvsResult.getStartTime());
                            EtNotifActvs_statement.bindString(22,etNotifActvsResult.getEndDate());
                            EtNotifActvs_statement.bindString(23,etNotifActvsResult.getEndTime());
                            EtNotifActvs_statement.bindString(24,checkempty.check_empty(etNotifActvsResult.getUsr01()));
                            EtNotifActvs_statement.bindString(25,checkempty.check_empty(etNotifActvsResult.getUsr02()));
                            EtNotifActvs_statement.bindString(26,checkempty.check_empty(etNotifActvsResult.getUsr03()));
                            EtNotifActvs_statement.bindString(27,checkempty.check_empty(etNotifActvsResult.getUsr04()));
                            EtNotifActvs_statement.bindString(28,checkempty.check_empty(etNotifActvsResult.getUsr05()));
                            EtNotifActvs_statement.bindString(24,"U");
                            EtNotifActvs_statement.execute();
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }*/
            /*Reading and Inserting Data into Database Table for EtNotifActvs*/




            /*Reading and Inserting Data into Database Table for EtNotifTasks*/
            /*try
            {
                Notifications_SER.EtNotifTasks etNotifTasks = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifTasks = notification_response.getD().getResults().get(0).getEtNotifTasks();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifTasks = notification_response.getD().getEtNotifTasks();
                }
                if (etNotifTasks != null)
                {
                    List<Notifications_SER.EtNotifTasks_Result> etNotifTasks_results = etNotifTasks.getResults();
                    if (etNotifTasks_results != null && etNotifTasks_results.size() > 0)
                    {
                        String EtNotifTasks_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt,TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext, TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr,Erlnam, Erldat, Erlzeit, Release,Complete,Success,UserStatus, SysStatus, Smsttxt, Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtNotifTasks_statement = App_db.compileStatement(EtNotifTasks_sql);
                        EtNotifTasks_statement.clearBindings();
                        for (Notifications_SER.EtNotifTasks_Result etNotifTasksResult : etNotifTasks_results)
                        {
                            EtNotifTasks_statement.bindString(1,checkempty.check_empty(etNotifTasksResult.getQmnum()));
                            EtNotifTasks_statement.bindString(2,checkempty.check_empty(etNotifTasksResult.getQmnum()));
                            EtNotifTasks_statement.bindString(3,etNotifTasksResult.getItemKey());
                            EtNotifTasks_statement.bindString(4,checkempty.check_empty(etNotifTasksResult.getItempartGrp()));
                            EtNotifTasks_statement.bindString(5,checkempty.check_empty(etNotifTasksResult.getPartgrptext()));
                            EtNotifTasks_statement.bindString(6,checkempty.check_empty(etNotifTasksResult.getItempartCod()));
                            EtNotifTasks_statement.bindString(7,checkempty.check_empty(etNotifTasksResult.getPartcodetext()));
                            EtNotifTasks_statement.bindString(8,checkempty.check_empty(etNotifTasksResult.getItemdefectGrp()));
                            EtNotifTasks_statement.bindString(9,checkempty.check_empty(etNotifTasksResult.getDefectgrptext()));
                            EtNotifTasks_statement.bindString(10,checkempty.check_empty(etNotifTasksResult.getItemdefectCod()));
                            EtNotifTasks_statement.bindString(11,checkempty.check_empty(etNotifTasksResult.getDefectcodetext()));
                            EtNotifTasks_statement.bindString(12,checkempty.check_empty(etNotifTasksResult.getItemdefectShtxt()));
                            EtNotifTasks_statement.bindString(13,etNotifTasksResult.getTaskKey());
                            EtNotifTasks_statement.bindString(14,checkempty.check_empty(etNotifTasksResult.getTaskGrp()));
                            EtNotifTasks_statement.bindString(15,checkempty.check_empty(etNotifTasksResult.getTaskgrptext()));
                            EtNotifTasks_statement.bindString(16,etNotifTasksResult.getTaskCod());
                            EtNotifTasks_statement.bindString(17,checkempty.check_empty(etNotifTasksResult.getTaskcodetext()));
                            EtNotifTasks_statement.bindString(18,checkempty.check_empty(etNotifTasksResult.getTaskShtxt()));
                            EtNotifTasks_statement.bindString(19,checkempty.check_empty(etNotifTasksResult.getPster()));
                            EtNotifTasks_statement.bindString(20,checkempty.check_empty(etNotifTasksResult.getPeter()));
                            EtNotifTasks_statement.bindString(21,checkempty.check_empty(etNotifTasksResult.getPstur()));
                            EtNotifTasks_statement.bindString(22,checkempty.check_empty(etNotifTasksResult.getPetur()));
                            EtNotifTasks_statement.bindString(23,checkempty.check_empty(etNotifTasksResult.getParvw()));
                            EtNotifTasks_statement.bindString(24,checkempty.check_empty(etNotifTasksResult.getParnr()));
                            EtNotifTasks_statement.bindString(25,checkempty.check_empty(etNotifTasksResult.getErlnam()));
                            EtNotifTasks_statement.bindString(26,checkempty.check_empty(etNotifTasksResult.getErldat()));
                            EtNotifTasks_statement.bindString(27,checkempty.check_empty(etNotifTasksResult.getErlzeit()));
                            EtNotifTasks_statement.bindString(28,checkempty.check_empty(etNotifTasksResult.getRelease()));
                            EtNotifTasks_statement.bindString(29,checkempty.check_empty(etNotifTasksResult.getComplete()));
                            EtNotifTasks_statement.bindString(30,checkempty.check_empty(etNotifTasksResult.getSuccess()));
                            EtNotifTasks_statement.bindString(31,checkempty.check_empty(etNotifTasksResult.getUserStatus()));
                            EtNotifTasks_statement.bindString(32,checkempty.check_empty(etNotifTasksResult.getSysStatus()));
                            EtNotifTasks_statement.bindString(33,checkempty.check_empty(etNotifTasksResult.getSmsttxt()));
                            EtNotifTasks_statement.bindString(34,checkempty.check_empty(etNotifTasksResult.getSmastxt()));
                            EtNotifTasks_statement.bindString(35,checkempty.check_empty(etNotifTasksResult.getUsr01()));
                            EtNotifTasks_statement.bindString(36,checkempty.check_empty(etNotifTasksResult.getUsr02()));
                            EtNotifTasks_statement.bindString(37,checkempty.check_empty(etNotifTasksResult.getUsr03()));
                            EtNotifTasks_statement.bindString(38,checkempty.check_empty(etNotifTasksResult.getUsr04()));
                            EtNotifTasks_statement.bindString(39,checkempty.check_empty(etNotifTasksResult.getUsr05()));
                            EtNotifTasks_statement.bindString(40,"U");
                            EtNotifTasks_statement.execute();
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }*/
            /*Reading and Inserting Data into Database Table for EtNotifTasks*/





            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/
            /*try
            {
                Notifications_SER.EtNotifLongtext etNotifLongtext = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifLongtext = notification_response.getD().getResults().get(0).getEtNotifLongtext();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifLongtext = notification_response.getD().getEtNotifLongtext();
                }
                if (etNotifLongtext != null)
                {
                    List<Notifications_SER.EtNotifLongtext_Result> etNotifLongtext_results = etNotifLongtext.getResults();
                    if (etNotifLongtext_results != null && etNotifLongtext_results.size() > 0)
                    {
                        String EtNotifLongtext_sql = "Insert into DUE_NOTIFICATIONS_EtNotifLongtext (UUID, Qmnum, Objtype, TextLine, Objkey) values(?,?,?,?,?);";
                        SQLiteStatement EtNotifLongtext_statement = App_db.compileStatement(EtNotifLongtext_sql);
                        EtNotifLongtext_statement.clearBindings();
                        for (Notifications_SER.EtNotifLongtext_Result etNotifLongtextResult : etNotifLongtext_results)
                        {
                            EtNotifLongtext_statement.bindString(1,checkempty.check_empty(etNotifLongtextResult.getQmnum()));
                            EtNotifLongtext_statement.bindString(2,checkempty.check_empty(etNotifLongtextResult.getQmnum()));
                            EtNotifLongtext_statement.bindString(3,checkempty.check_empty(etNotifLongtextResult.getObjtype()));
                            EtNotifLongtext_statement.bindString(4,checkempty.check_empty(etNotifLongtextResult.getTextLine()));
                            EtNotifLongtext_statement.bindString(5,etNotifLongtextResult.getObjkey());
                            EtNotifLongtext_statement.execute();
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }*/
            /*Reading and Inserting Data into Database Table for EtNotifLongtext*/




            /*Reading and Inserting Data into Database Table for EtNotifStatus*/
            /*try
            {
                Notifications_SER.EtNotifStatus etNotifStatus = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etNotifStatus = notification_response.getD().getResults().get(0).getEtNotifStatus();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etNotifStatus = notification_response.getD().getEtNotifStatus();
                }
                if (etNotifStatus != null)
                {
                    List<Notifications_SER.EtNotifStatus_Result> etNotifStatus_results = etNotifStatus.getResults();
                    if (etNotifStatus_results != null && etNotifStatus_results.size() > 0)
                    {
                        String EtNotifStatus_sql = "Insert into EtNotifStatus (UUID,Qmnum,Aufnr,Objnr,Manum,Stsma,Inist,Stonr,Hsonr,Nsonr,Stat,Act,Txt04,Txt30,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtNotifStatus_statement = App_db.compileStatement(EtNotifStatus_sql);
                        EtNotifStatus_statement.clearBindings();
                        for (Notifications_SER.EtNotifStatus_Result etNotifStatusResult : etNotifStatus_results)
                        {
                            EtNotifStatus_statement.bindString(1,checkempty.check_empty(etNotifStatusResult.getQmnum()));
                            EtNotifStatus_statement.bindString(2,checkempty.check_empty(etNotifStatusResult.getQmnum()));
                            EtNotifStatus_statement.bindString(3,checkempty.check_empty(etNotifStatusResult.getAufnr()));
                            EtNotifStatus_statement.bindString(4,checkempty.check_empty(etNotifStatusResult.getObjnr()));
                            EtNotifStatus_statement.bindString(5,checkempty.check_empty(etNotifStatusResult.getManum()));
                            EtNotifStatus_statement.bindString(6,checkempty.check_empty(etNotifStatusResult.getStsma()));
                            EtNotifStatus_statement.bindString(7,checkempty.check_empty(etNotifStatusResult.getInist()));
                            EtNotifStatus_statement.bindString(8,checkempty.check_empty(etNotifStatusResult.getStonr()));
                            EtNotifStatus_statement.bindString(9,checkempty.check_empty(etNotifStatusResult.getHsonr()));
                            EtNotifStatus_statement.bindString(10,checkempty.check_empty(etNotifStatusResult.getNsonr()));
                            EtNotifStatus_statement.bindString(11,checkempty.check_empty(etNotifStatusResult.getStat()));
                            EtNotifStatus_statement.bindString(12,checkempty.check_empty(etNotifStatusResult.getAct()));
                            EtNotifStatus_statement.bindString(13,checkempty.check_empty(etNotifStatusResult.getTxt04()));
                            EtNotifStatus_statement.bindString(14,checkempty.check_empty(etNotifStatusResult.getTxt30()));
                            EtNotifStatus_statement.bindString(15,"");
                            EtNotifStatus_statement.execute();
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }*/
            /*Reading and Inserting Data into Database Table for EtNotifStatus*/





            /*Reading and Inserting Data into Database Table for EtDocs*/
            /*try
            {
                Notifications_SER.EtDocs etDocs = null;
                if(type.equalsIgnoreCase("DUNOT"))
                {
                    etDocs = notification_response.getD().getResults().get(0).getEtDocs();
                }
                else if(type.equalsIgnoreCase("CRNOT"))
                {
                    etDocs = notification_response.getD().getEtDocs();
                }
                if (etDocs != null)
                {
                    List<Notifications_SER.EtDocs_Result> etDocs_results = etDocs.getResults();
                    if (etDocs_results != null && etDocs_results.size() > 0)
                    {
                        String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                        EtDocs_statement.clearBindings();
                        for (Notifications_SER.EtDocs_Result etDocsResult : etDocs_results)
                        {
                            EtDocs_statement.bindString(1,checkempty.check_empty(etDocsResult.getZobjid()));
                            EtDocs_statement.bindString(2,checkempty.check_empty(etDocsResult.getZobjid()));
                            EtDocs_statement.bindString(3,checkempty.check_empty(etDocsResult.getZdoctype()));
                            EtDocs_statement.bindString(4,checkempty.check_empty(etDocsResult.getZdoctypeItem()));
                            EtDocs_statement.bindString(5,checkempty.check_empty(etDocsResult.getFilename()));
                            EtDocs_statement.bindString(6,checkempty.check_empty(etDocsResult.getFiletype()));
                            EtDocs_statement.bindString(7,checkempty.check_empty(etDocsResult.getFsize()));
                            EtDocs_statement.bindString(8,checkempty.check_empty(etDocsResult.getContent()));
                            EtDocs_statement.bindString(9,checkempty.check_empty(etDocsResult.getDocId()));
                            EtDocs_statement.bindString(10,checkempty.check_empty(etDocsResult.getDocType()));
                            EtDocs_statement.bindString(11,checkempty.check_empty(etDocsResult.getObjtype()));
                            EtDocs_statement.bindString(12,"");
                            EtDocs_statement.bindString(13,"Old");
                            EtDocs_statement.execute();
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }*/
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
