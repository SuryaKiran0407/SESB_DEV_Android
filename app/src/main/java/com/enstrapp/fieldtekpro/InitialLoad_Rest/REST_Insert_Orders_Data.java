package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.enstrapp.fieldtekpro.checkempty.Check_Empty1;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class REST_Insert_Orders_Data
{



    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private static String Get_Response = "";
    private static Check_Empty checkempty = new Check_Empty();
    private static Check_Empty1 checkempty1 = new Check_Empty1();
    private static long startTime = 0;


    public static String Insert_Order_Data(Context activity, REST_Orders_SER orders_response, String Aufnr, String type)
    {
        try
        {
            DATABASE_NAME =  activity.getString(R.string.database_name);
            App_db  = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);

            if (Aufnr != null && !Aufnr.equals(""))
            {
                App_db.execSQL("delete from Orders_TKConfirm where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{Aufnr});
                App_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{Aufnr});
            }


            startTime = System.currentTimeMillis();
            App_db.execSQL("PRAGMA synchronous=OFF");
            App_db.setLockingEnabled(false);
            App_db.beginTransaction();


            /*Reading and Inserting Data into Database Table for EtOrderHeader*/
            try
            {
                List<REST_Orders_SER.ETORDERHEADER> etOrderHeader = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderHeader = orders_response.getETORDERHEADER();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderHeader = orders_response.getETORDERHEADER();
                }
                if (etOrderHeader != null && etOrderHeader.size() > 0)
                {
                    String EtOrderHeader_sql = "Insert into DUE_ORDERS_EtOrderHeader (UUID, Aufnr, Auart, Ktext, Ilart, Ernam, Erdat, Priok, Equnr, Strno, TplnrInt, Bautl, Gltrp, Gstrp, Docs, Permits, Altitude, Latitude, Longitude, Qmnum, Closed, Completed, Ingrp, Arbpl, Werks, Bemot, Aueru, Auarttext, Qmartx, Qmtxt, Pltxt, Eqktx, Priokx , Ilatx, Plantname, Wkctrname, Ingrpname, Maktx, Xstatus, Usr01, Usr02, Usr03, Usr04, Usr05, Kokrs, Kostl, Anlzu, Anlzux, Ausvn, Ausbs, Auswk, Qmnam, ParnrVw, NameVw, Posid, Revnr, Fistl, Zzpermit, Zzpmistdt, Zzpmisttm, Zzpmiendt, Zzpmientm, Zzpmihr1, IdealHours,RSNUM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderHeader_statement = App_db.compileStatement(EtOrderHeader_sql);
                    EtOrderHeader_statement.clearBindings();
                    for (REST_Orders_SER.ETORDERHEADER etOrderHeader_result : etOrderHeader)
                    {
                        EtOrderHeader_statement.bindString(1, checkempty.check_empty(etOrderHeader_result.getAUFNR()));
                        EtOrderHeader_statement.bindString(2, checkempty.check_empty(etOrderHeader_result.getAUFNR()));
                        EtOrderHeader_statement.bindString(3, checkempty.check_empty(etOrderHeader_result.getAUART()));
                        EtOrderHeader_statement.bindString(4, checkempty.check_empty(etOrderHeader_result.getKTEXT()));
                        EtOrderHeader_statement.bindString(5, etOrderHeader_result.getILART());
                        EtOrderHeader_statement.bindString(6, checkempty.check_empty(etOrderHeader_result.getERNAM()));
                        EtOrderHeader_statement.bindString(7, checkempty.check_empty(etOrderHeader_result.getERDAT()));
                        EtOrderHeader_statement.bindString(8, checkempty.check_empty(etOrderHeader_result.getPRIOK()));
                        EtOrderHeader_statement.bindString(9, checkempty.check_empty(etOrderHeader_result.getEQUNR()));
                        EtOrderHeader_statement.bindString(10, checkempty.check_empty(etOrderHeader_result.getSTRNO()));
                        EtOrderHeader_statement.bindString(11, checkempty.check_empty(etOrderHeader_result.getTPLNRINT()));
                        EtOrderHeader_statement.bindString(12, checkempty.check_empty(etOrderHeader_result.getBAUTL()));
                        EtOrderHeader_statement.bindString(13, checkempty.check_empty(etOrderHeader_result.getGLTRP()));
                        EtOrderHeader_statement.bindString(14, checkempty.check_empty(etOrderHeader_result.getGSTRP()));
                        EtOrderHeader_statement.bindString(15, checkempty.check_empty(etOrderHeader_result.getDOCS()));
                        EtOrderHeader_statement.bindString(16, checkempty.check_empty(etOrderHeader_result.getPERMITS()));
                        EtOrderHeader_statement.bindString(17, "");
                        EtOrderHeader_statement.bindString(18, "");
                        EtOrderHeader_statement.bindString(19, "");
                        EtOrderHeader_statement.bindString(20, checkempty.check_empty(etOrderHeader_result.getQMNUM()));
                        EtOrderHeader_statement.bindString(21, "");
                        EtOrderHeader_statement.bindString(22, "");
                        EtOrderHeader_statement.bindString(23, checkempty.check_empty(etOrderHeader_result.getINGRP()));
                        EtOrderHeader_statement.bindString(24, checkempty.check_empty(etOrderHeader_result.getARBPL()));
                        EtOrderHeader_statement.bindString(25, checkempty.check_empty(etOrderHeader_result.getWERKS()));
                        EtOrderHeader_statement.bindString(26, checkempty.check_empty(etOrderHeader_result.getBEMOT()));
                        EtOrderHeader_statement.bindString(27, checkempty.check_empty(etOrderHeader_result.getAUERU()));
                        EtOrderHeader_statement.bindString(28, checkempty.check_empty(etOrderHeader_result.getAUARTTEXT()));
                        EtOrderHeader_statement.bindString(29, checkempty.check_empty(etOrderHeader_result.getQMARTX()));
                        EtOrderHeader_statement.bindString(30, checkempty.check_empty(etOrderHeader_result.getQMTXT()));
                        EtOrderHeader_statement.bindString(31, checkempty.check_empty(etOrderHeader_result.getPLTXT()));
                        EtOrderHeader_statement.bindString(32, checkempty.check_empty(etOrderHeader_result.getEQKTX()));
                        EtOrderHeader_statement.bindString(33, checkempty.check_empty(etOrderHeader_result.getPRIOKX()));
                        EtOrderHeader_statement.bindString(34, checkempty.check_empty(etOrderHeader_result.getILATX()));
                        EtOrderHeader_statement.bindString(35, checkempty.check_empty(etOrderHeader_result.getPLANTNAME()));
                        EtOrderHeader_statement.bindString(36, checkempty.check_empty(etOrderHeader_result.getWKCTRNAME()));
                        EtOrderHeader_statement.bindString(37, checkempty.check_empty(etOrderHeader_result.getINGRPNAME()));
                        EtOrderHeader_statement.bindString(38, checkempty.check_empty(etOrderHeader_result.getMAKTX()));
                        EtOrderHeader_statement.bindString(39, checkempty.check_empty(etOrderHeader_result.getXSTATUS()));
                        EtOrderHeader_statement.bindString(40, checkempty.check_empty(etOrderHeader_result.getUSR01()));
                        EtOrderHeader_statement.bindString(41, checkempty.check_empty(etOrderHeader_result.getUSR02()));
                        EtOrderHeader_statement.bindString(42, checkempty.check_empty(etOrderHeader_result.getUSR03()));
                        EtOrderHeader_statement.bindString(43, checkempty.check_empty(etOrderHeader_result.getUSR04()));
                        EtOrderHeader_statement.bindString(44, checkempty.check_empty(etOrderHeader_result.getUSR05()));
                        EtOrderHeader_statement.bindString(45, checkempty.check_empty(etOrderHeader_result.getKOSTL()));
                        EtOrderHeader_statement.bindString(46, checkempty.check_empty(etOrderHeader_result.getKOSTL()));
                        EtOrderHeader_statement.bindString(47, checkempty.check_empty(etOrderHeader_result.getANLZU()));
                        EtOrderHeader_statement.bindString(48, checkempty.check_empty(etOrderHeader_result.getANLZUX()));
                        EtOrderHeader_statement.bindString(49, checkempty.check_empty(etOrderHeader_result.getAUSVN()));
                        EtOrderHeader_statement.bindString(50, checkempty.check_empty(etOrderHeader_result.getAUSBS()));
                        EtOrderHeader_statement.bindString(51, checkempty.check_empty(etOrderHeader_result.getAUSWK()));
                        EtOrderHeader_statement.bindString(52, checkempty.check_empty(etOrderHeader_result.getQMNAM()));
                        EtOrderHeader_statement.bindString(53, checkempty.check_empty(etOrderHeader_result.getPARNRVW()));
                        EtOrderHeader_statement.bindString(54, checkempty.check_empty(etOrderHeader_result.getNAMEVW()));
                        EtOrderHeader_statement.bindString(55, checkempty.check_empty(etOrderHeader_result.getPOSID()));
                        EtOrderHeader_statement.bindString(56, checkempty.check_empty(etOrderHeader_result.getREVNR()));
                        EtOrderHeader_statement.bindString(57, checkempty.check_empty(etOrderHeader_result.getFISTL()));
                        EtOrderHeader_statement.bindString(58, checkempty.check_empty(etOrderHeader_result.getZZPERMIT()));
                        EtOrderHeader_statement.bindString(59, checkempty.check_empty(etOrderHeader_result.getZZPMISTDT()));
                        EtOrderHeader_statement.bindString(60, checkempty.check_empty(etOrderHeader_result.getZZPMISTTM()));
                        EtOrderHeader_statement.bindString(61, checkempty.check_empty(etOrderHeader_result.getZZPMIENDT()));
                        EtOrderHeader_statement.bindString(62, checkempty.check_empty(etOrderHeader_result.getZZPMIENTM()));
                        EtOrderHeader_statement.bindString(63, checkempty.check_empty(etOrderHeader_result.getZZPMIHR1()));
                        EtOrderHeader_statement.bindString(64, checkempty.check_empty(etOrderHeader_result.getIDEALHOURS()));
                        EtOrderHeader_statement.bindString(65, checkempty.check_empty(etOrderHeader_result.getRSNUM()));
                        EtOrderHeader_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderHeader*/




            /*Reading and Inserting Data into Database Table for EtOrderOperations*/
            try
            {
                List<REST_Orders_SER.ETORDEROPERATION> etOrderOperations = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderOperations = orders_response.getETORDEROPERATIONS();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderOperations = orders_response.getETORDEROPERATIONS();
                }
                if (etOrderOperations != null && etOrderOperations.size() > 0)
                {
                    String EtOrderOperations_sql = "Insert into DUE_ORDERS_EtOrderOperations (UUID,Aufnr,Vornr,Uvorn,Ltxa1,Arbpl,Werks,Steus,Larnt,Dauno,Daune,Fsavd,Ssedd,Pernr,Asnum,Plnty,Plnal,Plnnr,Rueck,Aueru,ArbplText,WerksText,SteusText,LarntText,Usr01,Usr02,Usr03,Usr04,Usr05,Action, Anlzu, Sortl, IdealCond, ActualCond, SAKTO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderOperations_statement = App_db.compileStatement(EtOrderOperations_sql);
                    EtOrderOperations_statement.clearBindings();
                    for (REST_Orders_SER.ETORDEROPERATION etOrderOperations_result : etOrderOperations)
                    {
                        EtOrderOperations_statement.bindString(1, checkempty.check_empty(etOrderOperations_result.getAUFNR()));
                        EtOrderOperations_statement.bindString(2, checkempty.check_empty(etOrderOperations_result.getAUFNR()));
                        EtOrderOperations_statement.bindString(3, etOrderOperations_result.getVORNR());
                        EtOrderOperations_statement.bindString(4, checkempty.check_empty(etOrderOperations_result.getUvorn()));
                        EtOrderOperations_statement.bindString(5, checkempty.check_empty(etOrderOperations_result.getLTXA1()));
                        EtOrderOperations_statement.bindString(6, checkempty.check_empty(etOrderOperations_result.getARBPL()));
                        EtOrderOperations_statement.bindString(7, checkempty.check_empty(etOrderOperations_result.getWERKS()));
                        EtOrderOperations_statement.bindString(8, checkempty.check_empty(etOrderOperations_result.getSTEUS()));
                        EtOrderOperations_statement.bindString(9, checkempty.check_empty(etOrderOperations_result.getLARNT()));
                        EtOrderOperations_statement.bindString(10, checkempty.check_empty(etOrderOperations_result.getDAUNO()));
                        EtOrderOperations_statement.bindString(11, checkempty.check_empty(etOrderOperations_result.getDAUNE()));
                        EtOrderOperations_statement.bindString(12, checkempty.check_empty(etOrderOperations_result.getFSAVD()));
                        EtOrderOperations_statement.bindString(13, checkempty.check_empty(etOrderOperations_result.getSSEDD()));
                        EtOrderOperations_statement.bindString(14, checkempty.check_empty(etOrderOperations_result.getPernr()));
                        EtOrderOperations_statement.bindString(15, checkempty.check_empty(etOrderOperations_result.getAsnum()));
                        EtOrderOperations_statement.bindString(16, checkempty.check_empty(etOrderOperations_result.getPlnty()));
                        EtOrderOperations_statement.bindString(17, checkempty.check_empty(etOrderOperations_result.getPlnal()));
                        EtOrderOperations_statement.bindString(18, checkempty.check_empty(etOrderOperations_result.getPlnnr()));
                        EtOrderOperations_statement.bindString(19, checkempty.check_empty(etOrderOperations_result.getRUECK()));
                        EtOrderOperations_statement.bindString(20, checkempty.check_empty(etOrderOperations_result.getAUERU()));
                        EtOrderOperations_statement.bindString(21, checkempty.check_empty(etOrderOperations_result.getARBPLTEXT()));
                        EtOrderOperations_statement.bindString(22, checkempty.check_empty(etOrderOperations_result.getWERKSTEXT()));
                        EtOrderOperations_statement.bindString(23, checkempty.check_empty(etOrderOperations_result.getSTEUSTEXT()));
                        EtOrderOperations_statement.bindString(24, checkempty.check_empty(etOrderOperations_result.getLARNTTEXT()));
                        EtOrderOperations_statement.bindString(25, checkempty.check_empty(etOrderOperations_result.getUSR01()));
                        EtOrderOperations_statement.bindString(26, checkempty1.check_empty(etOrderOperations_result.getUSR02()));
                        EtOrderOperations_statement.bindString(27, checkempty1.check_empty(etOrderOperations_result.getUSR03()));
                        EtOrderOperations_statement.bindString(28, checkempty.check_empty(etOrderOperations_result.getUSR04()));
                        EtOrderOperations_statement.bindString(29, checkempty.check_empty(etOrderOperations_result.getUSR05()));
                        EtOrderOperations_statement.bindString(30, checkempty.check_empty(etOrderOperations_result.getAction()));
                        EtOrderOperations_statement.bindString(31, checkempty.check_empty(etOrderOperations_result.getAnlzu()));
                        EtOrderOperations_statement.bindString(32, checkempty.check_empty(etOrderOperations_result.getSortl()));
                        EtOrderOperations_statement.bindString(33, checkempty.check_empty(etOrderOperations_result.getIdealCond()));
                        EtOrderOperations_statement.bindString(34, checkempty.check_empty(etOrderOperations_result.getActualCond()));
                        EtOrderOperations_statement.bindString(35, checkempty1.check_empty(etOrderOperations_result.getSAKTO()));
                        EtOrderOperations_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderOperations*/




            /*Reading and Inserting Data into Database Table for EtOrderLongtext*/
            try
            {
                List<REST_Orders_SER.ETORDERLONGTEXT> etOrderLongtext = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderLongtext = orders_response.getETORDERLONGTEXT();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderLongtext = orders_response.getETORDERLONGTEXT();
                }
                if (etOrderLongtext != null && etOrderLongtext.size() > 0)
                {
                    String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine, Tdid) values(?,?,?,?,?);";
                    SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                    EtOrderLongtext_statement.clearBindings();
                    for (REST_Orders_SER.ETORDERLONGTEXT etOrderLongtext_result : etOrderLongtext)
                    {
                        EtOrderLongtext_statement.bindString(1, checkempty.check_empty(etOrderLongtext_result.getAUFNR()));
                        EtOrderLongtext_statement.bindString(2, checkempty.check_empty(etOrderLongtext_result.getAUFNR()));
                        EtOrderLongtext_statement.bindString(3, etOrderLongtext_result.getACTIVITY()+"");
                        EtOrderLongtext_statement.bindString(4, checkempty.check_empty(etOrderLongtext_result.getTEXTLINE()));
                        EtOrderLongtext_statement.bindString(5, checkempty.check_empty(etOrderLongtext_result.getTdid()));
                        EtOrderLongtext_statement.execute();
                    }
                }

            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderLongtext*/



            /*Reading and Inserting Data into Database Table for EtOrderOlist*/
            try
            {
                List<REST_Orders_SER.ETORDEROLIST> etOrderOlist = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderOlist = orders_response.getETORDEROLIST();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderOlist = orders_response.getETORDEROLIST();
                }
                if (etOrderOlist != null && etOrderOlist.size() > 0)
                {
                    String EtOrderOlist_sql = "Insert into EtOrderOlist (UUID, Aufnr, Obknr, Obzae, Qmnum, Equnr, Strno, Tplnr, Bautl, Qmtxt, Pltxt, Eqktx, Maktx, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderOlist_statement = App_db.compileStatement(EtOrderOlist_sql);
                    EtOrderOlist_statement.clearBindings();
                    for (REST_Orders_SER.ETORDEROLIST etOrderOlistResult : etOrderOlist)
                    {
                        EtOrderOlist_statement.bindString(1, checkempty.check_empty(etOrderOlistResult.getAUFNR()));
                        EtOrderOlist_statement.bindString(2, checkempty.check_empty(etOrderOlistResult.getAUFNR()));
                        EtOrderOlist_statement.bindString(3, checkempty.check_empty(etOrderOlistResult.getOBKNR()));
                        EtOrderOlist_statement.bindString(4, checkempty.check_empty(etOrderOlistResult.getOBZAE()));
                        EtOrderOlist_statement.bindString(5, checkempty.check_empty(etOrderOlistResult.getQMNUM()));
                        EtOrderOlist_statement.bindString(6, checkempty.check_empty(etOrderOlistResult.getEQUNR()));
                        EtOrderOlist_statement.bindString(7, checkempty.check_empty(etOrderOlistResult.getStrno()));
                        EtOrderOlist_statement.bindString(8, checkempty.check_empty(etOrderOlistResult.getTPLNR()));
                        EtOrderOlist_statement.bindString(9, checkempty.check_empty(etOrderOlistResult.getBautl()));
                        EtOrderOlist_statement.bindString(10, checkempty.check_empty(etOrderOlistResult.getQmtxt()));
                        EtOrderOlist_statement.bindString(11, checkempty.check_empty(etOrderOlistResult.getPltxt()));
                        EtOrderOlist_statement.bindString(12, checkempty.check_empty(etOrderOlistResult.getEQKTX()));
                        EtOrderOlist_statement.bindString(13, checkempty.check_empty(etOrderOlistResult.getMaktx()));
                        EtOrderOlist_statement.bindString(14, checkempty.check_empty(etOrderOlistResult.getAction()));
                        EtOrderOlist_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderOlist*/




            /*Reading and Inserting Data into Database Table for EtOrderStatus*/
            try
            {
                List<REST_Orders_SER.ETORDERStatus> etOrderStatus = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderStatus = orders_response.getETORDERSTATUS();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderStatus = orders_response.getETORDERSTATUS();
                }
                if (etOrderStatus != null && etOrderStatus.size() > 0)
                {
                    String EtOrderStatus_sql = "Insert into EtOrderStatus (UUID,Aufnr, Vornr, Objnr, Stsma, Inist, Stonr, Hsonr, Nsonr,Stat, Act, Txt04, Txt30, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderStatus_statement = App_db.compileStatement(EtOrderStatus_sql);
                    EtOrderStatus_statement.clearBindings();
                    for (REST_Orders_SER.ETORDERStatus etOrderStatusResult : etOrderStatus)
                    {
                        EtOrderStatus_statement.bindString(1, checkempty.check_empty(etOrderStatusResult.getAUFNR()));
                        EtOrderStatus_statement.bindString(2, checkempty.check_empty(etOrderStatusResult.getAUFNR()));
                        EtOrderStatus_statement.bindString(3, checkempty.check_empty(etOrderStatusResult.getVornr()));
                        EtOrderStatus_statement.bindString(4, checkempty.check_empty(etOrderStatusResult.getOBJNR()));
                        EtOrderStatus_statement.bindString(5, checkempty.check_empty(etOrderStatusResult.getSTSMA()));
                        EtOrderStatus_statement.bindString(6, checkempty.check_empty(etOrderStatusResult.getINIST()));
                        EtOrderStatus_statement.bindString(7, checkempty.check_empty(etOrderStatusResult.getSTONR()));
                        EtOrderStatus_statement.bindString(8, checkempty.check_empty(etOrderStatusResult.getHSONR()));
                        EtOrderStatus_statement.bindString(9, checkempty.check_empty(etOrderStatusResult.getNSONR()));
                        EtOrderStatus_statement.bindString(10, checkempty.check_empty(etOrderStatusResult.getSTAT()));
                        EtOrderStatus_statement.bindString(11, checkempty.check_empty(etOrderStatusResult.getACT()));
                        EtOrderStatus_statement.bindString(12, checkempty.check_empty(etOrderStatusResult.getTXT04()));
                        EtOrderStatus_statement.bindString(13, checkempty.check_empty(etOrderStatusResult.getTXT30()));
                        EtOrderStatus_statement.bindString(14, checkempty.check_empty(etOrderStatusResult.getAction()));
                        EtOrderStatus_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderStatus*/





            /*Reading and Inserting Data into Database Table for EtOrderComponents*/
            try
            {
                List<REST_Orders_SER.ETORDERCOMPONENTS> etOrderComponents = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etOrderComponents = orders_response.geteTORDERCOMPONENTS();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etOrderComponents = orders_response.geteTORDERCOMPONENTS();
                }
                if (etOrderComponents != null && etOrderComponents.size() > 0)
                {
                    String EtOrderComponents_sql = "Insert into EtOrderComponents (UUID, Aufnr, Vornr, Uvorn, Rsnum, Rspos, Matnr, Werks, Lgort, Posnr, Bdmng, Meins, Postp, MatnrText, WerksText, LgortText, PostpText, Usr01, Usr02, Usr03, Usr04, Usr05, Action, Wempf, Ablad, Charg) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderComponents_statement = App_db.compileStatement(EtOrderComponents_sql);
                    EtOrderComponents_statement.clearBindings();
                    for (REST_Orders_SER.ETORDERCOMPONENTS etOrderComponents_result : etOrderComponents)
                    {
                        EtOrderComponents_statement.bindString(1, checkempty.check_empty(etOrderComponents_result.getAUFNR()));
                        EtOrderComponents_statement.bindString(2, checkempty.check_empty(etOrderComponents_result.getAUFNR()));
                        EtOrderComponents_statement.bindString(3, etOrderComponents_result.getVORNR());
                        EtOrderComponents_statement.bindString(4, checkempty.check_empty(etOrderComponents_result.getUVORN()));
                        EtOrderComponents_statement.bindString(5, checkempty.check_empty(etOrderComponents_result.getRSNUM()));
                        EtOrderComponents_statement.bindString(6, checkempty.check_empty(etOrderComponents_result.getRSPOS()));
                        EtOrderComponents_statement.bindString(7, checkempty.check_empty(etOrderComponents_result.getMATNR()));
                        EtOrderComponents_statement.bindString(8, checkempty.check_empty(etOrderComponents_result.getWERKS()));
                        EtOrderComponents_statement.bindString(9, checkempty.check_empty(etOrderComponents_result.getLGORT()));
                        EtOrderComponents_statement.bindString(10, checkempty.check_empty(etOrderComponents_result.getPOSNR()));
                        EtOrderComponents_statement.bindString(11, checkempty.check_empty(etOrderComponents_result.getBDMNG()));
                        EtOrderComponents_statement.bindString(12, checkempty.check_empty(etOrderComponents_result.getMEINS()));
                        EtOrderComponents_statement.bindString(13, checkempty.check_empty(etOrderComponents_result.getPOSTP()));
                        EtOrderComponents_statement.bindString(14, checkempty.check_empty(etOrderComponents_result.getMATNRTEXT()));
                        EtOrderComponents_statement.bindString(15, checkempty.check_empty(etOrderComponents_result.getWERKSTEXT()));
                        EtOrderComponents_statement.bindString(16, checkempty.check_empty(etOrderComponents_result.getLGORTTEXT()));
                        EtOrderComponents_statement.bindString(17, checkempty.check_empty(etOrderComponents_result.getPOSTPTEXT()));
                        EtOrderComponents_statement.bindString(18, checkempty.check_empty(etOrderComponents_result.getUSR01()));
                        EtOrderComponents_statement.bindString(19, checkempty.check_empty(etOrderComponents_result.getUSR02()));
                        EtOrderComponents_statement.bindString(20, checkempty.check_empty(etOrderComponents_result.getUSR03()));
                        EtOrderComponents_statement.bindString(21, checkempty.check_empty(etOrderComponents_result.getUSR04()));
                        EtOrderComponents_statement.bindString(22, checkempty.check_empty(etOrderComponents_result.getUSR05()));
                        EtOrderComponents_statement.bindString(23, "");
                        EtOrderComponents_statement.bindString(24, checkempty.check_empty(etOrderComponents_result.getWEMPF()));
                        EtOrderComponents_statement.bindString(25, checkempty.check_empty(etOrderComponents_result.getABLAD()));
                        EtOrderComponents_statement.bindString(26, checkempty.check_empty(etOrderComponents_result.getCharg()));
                        EtOrderComponents_statement.execute();
                    }
                }
            }
            catch (Exception e)
            {
            }
            /*Reading and Inserting Data into Database Table for EtOrderComponents*/




            /*Reading and Inserting Data into Database Table for EtDocs*/
            try
            {
                List<REST_Orders_SER.ETDOC> etDocs = null;
                if(type.equalsIgnoreCase("DUORD"))
                {
                    etDocs = orders_response.geteTDOCS();
                }
                else if(type.equalsIgnoreCase("CRORD"))
                {
                    etDocs = orders_response.geteTDOCS();
                }
                if (etDocs != null && etDocs.size() > 0)
                {
                    String EtDocs_sql = "Insert into DUE_ORDERS_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                    EtDocs_statement.clearBindings();
                    for (REST_Orders_SER.ETDOC etDocsResult : etDocs)
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
            Log.v("kiran_DORD_insert", String.valueOf(endtime - startTime)+" Milliseconds");
        }

        return Get_Response;
    }

}
