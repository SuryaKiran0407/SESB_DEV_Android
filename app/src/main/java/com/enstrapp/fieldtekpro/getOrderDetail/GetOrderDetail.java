package com.enstrapp.fieldtekpro.getOrderDetail;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro.orders.OrdrHeaderPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrMatrlPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrObjectPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrOprtnPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrPermitPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrTagUnTagTextPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWDItemPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWaChkReqPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWcagnsPrcbl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class GetOrderDetail {
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    public static OrdrHeaderPrcbl GetData(Activity activity, String orderUUID, String orderId, String orderStatus, String Iwerk) {

        DATABASE_NAME = activity.getString(R.string.database_name);
        App_db = activity.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();
        ArrayList<Notif_EtDocs_Parcelable> docs_al = new ArrayList<>();
        ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
        ArrayList<OrdrObjectPrcbl> oobp_al = new ArrayList<OrdrObjectPrcbl>();
        ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrPermitPrcbl> wa_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrPermitPrcbl> wd_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrWDItemPrcbl> wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> orstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wwstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wastp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wdstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<OrdrWaChkReqPrcbl> waChkReq_al = new ArrayList<OrdrWaChkReqPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> wwissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> waissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> wdissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdUnTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        String ordrLngTxt = "", statusAll = "", WW_Objnr = "", WA_Objnr = "", WD_Objnr = "",
                WA_Wapinr = "", WA_Objtyp = "", Wcnr = "";
        int count = 0;
        StringBuilder stringbuilder = new StringBuilder();

        {
            Cursor cursor = null;

            /*WorkApproval Data*/
            try {
                cursor = App_db.rawQuery("select * from EtWcmWwData where UUID = ?" + "ORDER BY id DESC", new String[]{orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrPermitPrcbl wwopp = new OrdrPermitPrcbl();
                            wwopp.setAufnr(cursor.getString(2));
                            wwopp.setObjart(cursor.getString(3));
                            wwopp.setWapnr(cursor.getString(4));
                            wwopp.setIwerk(cursor.getString(5));
                            wwopp.setUsage(cursor.getString(6));
                            wwopp.setUsagex(cursor.getString(7));
                            wwopp.setTrain(cursor.getString(8));
                            wwopp.setTrainx(cursor.getString(9));
                            wwopp.setAnlzu(cursor.getString(10));
                            wwopp.setAnlzux(cursor.getString(11));
                            wwopp.setEtape(cursor.getString(12));
                            wwopp.setEtapex(cursor.getString(13));
                            wwopp.setRctime(cursor.getString(14));
                            wwopp.setRcunit(cursor.getString(15));
                            wwopp.setPriok(cursor.getString(16));
                            wwopp.setPriokx(cursor.getString(17));
                            wwopp.setStxt(cursor.getString(18));
                            wwopp.setDatefr(cursor.getString(19));
                            wwopp.setTimefr(cursor.getString(20));
                            wwopp.setDateto(cursor.getString(21));
                            wwopp.setTimeto(cursor.getString(22));
                            WW_Objnr = cursor.getString(23);
                            wwopp.setObjnr(cursor.getString(23));
                            wwopp.setCrea(cursor.getString(24));
                            wwopp.setPrep(cursor.getString(25));
                            wwopp.setComp(cursor.getString(26));
                            wwopp.setAppr(cursor.getString(27));
                            wwopp.setPappr(cursor.getString(28));
                            wwopp.setAction(cursor.getString(29));
                            wwopp.setBegru(cursor.getString(30));
                            wwopp.setBegtx(cursor.getString(31));

                            Cursor cursor1 = null;
                            /*WW Status*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr = ? order by Stonr", new String[]{orderId, WW_Objnr});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                            osp.setAufnr(cursor1.getString(2));
                                            osp.setVornr(cursor1.getString(3));
                                            osp.setObjnr(cursor1.getString(4));
                                            osp.setStsma(cursor1.getString(5));
                                            osp.setInist(cursor1.getString(6));
                                            osp.setStonr(cursor1.getString(7));
                                            osp.setHsonr(cursor1.getString(8));
                                            osp.setNsonr(cursor1.getString(9));
                                            osp.setStat(cursor1.getString(10));
                                            osp.setAct(cursor1.getString(11));
                                            osp.setTxt04(cursor1.getString(12));
                                            osp.setTxt30(cursor1.getString(13));
                                            osp.setAction(cursor1.getString(14));
                                            wwstp_al.add(osp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }

                            /*WW Issue approvals*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtWcmWcagns where Objnr = ? and Objart = ? order by Hilvl", new String[]{WW_Objnr, "WW"});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                            owp.setAufnr(cursor1.getString(2));
                                            owp.setObjnr(cursor1.getString(3));
                                            owp.setCounter(cursor1.getString(4));
                                            owp.setObjart(cursor1.getString(5));
                                            owp.setObjtyp(cursor1.getString(6));
                                            owp.setPmsog(cursor1.getString(7));
                                            owp.setGntxt(cursor1.getString(8));
                                            owp.setGeniakt(cursor1.getString(9));
                                            owp.setGenvname(cursor1.getString(10));
                                            owp.setAction(cursor1.getString(11));
                                            owp.setWerks(cursor1.getString(12));
                                            owp.setCrname(cursor1.getString(13));
                                            if (!cursor1.getString(14).equals(""))
                                                owp.setHilvl(Integer.parseInt(cursor1.getString(14)));
                                            owp.setProcflg(cursor1.getString(15));
                                            owp.setDirection(cursor1.getString(16));
                                            owp.setCopyflg(cursor1.getString(17));
                                            owp.setMandflg(cursor1.getString(18));
                                            owp.setDeacflg(cursor1.getString(19));
                                            owp.setStatus(cursor1.getString(20));
                                            owp.setAsgnflg(cursor1.getString(21));
                                            owp.setAutoflg(cursor1.getString(22));
                                            owp.setAgent(cursor1.getString(23));
                                            owp.setValflg(cursor1.getString(24));
                                            owp.setWcmuse(cursor1.getString(25));
                                            owp.setGendatum(cursor1.getString(26));
                                            owp.setGentime(cursor1.getString(27));
                                            wwissuep_al.add(owp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }

                            /*Permit Applications Data WA*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtWcmWaData where Refobj = ?" + "ORDER BY id DESC", new String[]{WW_Objnr});

                                if (!(cursor1 != null && cursor1.getCount() > 0))
                                    cursor1 = App_db.rawQuery("select * from EtWcmWaData where Refobj = ?" + "ORDER BY id DESC", new String[]{generateRefobj(orderId)});

                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            waChkReq_al = new ArrayList<OrdrWaChkReqPrcbl>();
                                            waissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
                                            wastp_al = new ArrayList<NotifOrdrStatusPrcbl>();
                                            wd_al = new ArrayList<OrdrPermitPrcbl>();
                                            OrdrPermitPrcbl opp = new OrdrPermitPrcbl();
                                            opp.setAufnr(cursor1.getString(2));
                                            opp.setObjart(cursor1.getString(3));
                                            WA_Wapinr = cursor1.getString(4);
                                            opp.setWapinr(cursor1.getString(4));
                                            opp.setIwerk(cursor1.getString(5));
                                            WA_Objtyp = cursor1.getString(6);
                                            opp.setObjtyp(cursor1.getString(6));
                                            opp.setApplName(getApplName(cursor1.getString(6), Iwerk));
                                            opp.setUsage(cursor1.getString(7));
                                            opp.setUsagex(cursor1.getString(8));
                                            opp.setTrain(cursor1.getString(9));
                                            opp.setTrainx(cursor1.getString(10));
                                            opp.setAnlzu(cursor1.getString(11));
                                            opp.setAnlzux(cursor1.getString(12));
                                            opp.setEtape(cursor1.getString(13));
                                            opp.setEtapex(cursor1.getString(14));
                                            opp.setStxt(cursor1.getString(15));
                                            opp.setDatefr(dateDisplayFormat(cursor1.getString(16)));
                                            opp.setTimefr(timeDisplayFormat(cursor1.getString(17)));
                                            opp.setDateto(dateDisplayFormat(cursor1.getString(18)));
                                            opp.setTimeto(timeDisplayFormat(cursor1.getString(19)));
                                            opp.setPriok(cursor1.getString(20));
                                            opp.setPriokx(cursor1.getString(21));
                                            opp.setRctime(cursor1.getString(22));
                                            opp.setRcunit(cursor1.getString(23));
                                            WA_Objnr = cursor1.getString(24);
                                            opp.setObjnr(cursor1.getString(24));
                                            opp.setRefobj(cursor1.getString(25));
                                            opp.setCrea(cursor1.getString(26));
                                            opp.setPrep(cursor1.getString(27));
                                            opp.setComp(cursor1.getString(28));
                                            opp.setAppr(cursor1.getString(29));
                                            opp.setAction(cursor1.getString(30));
                                            opp.setBegru(cursor1.getString(31));
                                            opp.setBegtx(cursor1.getString(32));
                                            if (!cursor1.getString(33).equals(""))
                                                opp.setExtperiod(Integer.parseInt(cursor1.getString(33)));

                                            Cursor cursor2 = null;

                                            /*Wa Status Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr = ? order by Stonr", new String[]{orderId, WA_Objnr});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                                            osp.setAufnr(cursor2.getString(2));
                                                            osp.setVornr(cursor2.getString(3));
                                                            osp.setObjnr(cursor2.getString(4));
                                                            osp.setStsma(cursor2.getString(5));
                                                            osp.setInist(cursor2.getString(6));
                                                            osp.setStonr(cursor2.getString(7));
                                                            osp.setHsonr(cursor2.getString(8));
                                                            osp.setNsonr(cursor2.getString(9));
                                                            osp.setStat(cursor2.getString(10));
                                                            osp.setAct(cursor2.getString(11));
                                                            osp.setTxt04(cursor2.getString(12));
                                                            osp.setTxt30(cursor2.getString(13));
                                                            osp.setAction(cursor2.getString(14));
                                                            wastp_al.add(osp);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*Wa Issue Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWcagns where Objnr = ? and Objart = ? order by Hilvl", new String[]{WA_Objnr, "WA"});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                                            owp.setAufnr(cursor2.getString(2));
                                                            owp.setObjnr(cursor2.getString(3));
                                                            owp.setCounter(cursor2.getString(4));
                                                            owp.setObjart(cursor2.getString(5));
                                                            owp.setObjtyp(cursor2.getString(6));
                                                            owp.setPmsog(cursor2.getString(7));
                                                            owp.setGntxt(cursor2.getString(8));
                                                            owp.setGeniakt(cursor2.getString(9));
                                                            owp.setGenvname(cursor2.getString(10));
                                                            owp.setAction(cursor2.getString(11));
                                                            owp.setWerks(cursor2.getString(12));
                                                            owp.setCrname(cursor2.getString(13));
                                                            if (!cursor2.getString(14).equals(""))
                                                                owp.setHilvl(Integer.parseInt(cursor2.getString(14)));
                                                            owp.setProcflg(cursor2.getString(15));
                                                            owp.setDirection(cursor2.getString(16));
                                                            owp.setCopyflg(cursor2.getString(17));
                                                            owp.setMandflg(cursor2.getString(18));
                                                            owp.setDeacflg(cursor2.getString(19));
                                                            owp.setStatus(cursor2.getString(20));
                                                            owp.setAsgnflg(cursor2.getString(21));
                                                            owp.setAutoflg(cursor2.getString(22));
                                                            owp.setAgent(cursor2.getString(23));
                                                            owp.setValflg(cursor2.getString(24));
                                                            owp.setWcmuse(cursor2.getString(25));
                                                            owp.setGendatum(dateDisplayFormat(cursor2.getString(26)));
                                                            owp.setGentime(timeDisplayFormat(cursor2.getString(27)));
                                                            waissuep_al.add(owp);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*Isolation Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWdData where Refobj = ?" + "ORDER BY id DESC", new String[]{WA_Objnr});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
                                                            wdissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
                                                            wdstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
                                                            wdTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
                                                            wdUnTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
                                                            OrdrPermitPrcbl oop = new OrdrPermitPrcbl();
                                                            oop.setAufnr(cursor2.getString(1));
                                                            oop.setObjart(cursor2.getString(2));
                                                            Wcnr = cursor2.getString(3);
                                                            oop.setWcnr(cursor2.getString(3));
                                                            oop.setIwerk(cursor2.getString(4));
                                                            oop.setObjtyp(cursor2.getString(5));
                                                            oop.setUsage(cursor2.getString(6));
                                                            oop.setUsagex(cursor2.getString(7));
                                                            oop.setTrain(cursor2.getString(8));
                                                            oop.setTrainx(cursor2.getString(9));
                                                            oop.setAnlzu(cursor2.getString(10));
                                                            oop.setAnlzux(cursor2.getString(11));
                                                            oop.setEtape(cursor2.getString(12));
                                                            oop.setEtapex(cursor2.getString(13));
                                                            oop.setStxt(cursor2.getString(14));
                                                            oop.setDatefr(dateDisplayFormat(cursor2.getString(15)));
                                                            oop.setTimefr(timeDisplayFormat(cursor2.getString(16)));
                                                            oop.setDateto(dateDisplayFormat(cursor2.getString(17)));
                                                            oop.setTimeto(timeDisplayFormat(cursor2.getString(18)));
                                                            oop.setPriok(cursor2.getString(19));
                                                            oop.setPriokx(cursor2.getString(20));
                                                            oop.setRctime(cursor2.getString(21));
                                                            oop.setRcunit(cursor2.getString(22));
                                                            WD_Objnr = cursor2.getString(23);
                                                            oop.setObjnr(cursor2.getString(23));
                                                            oop.setRefobj(cursor2.getString(24));
                                                            oop.setCrea(cursor2.getString(25));
                                                            oop.setPrep(cursor2.getString(26));
                                                            oop.setComp(cursor2.getString(27));
                                                            oop.setAppr(cursor2.getString(28));
                                                            oop.setAction(cursor2.getString(29));
                                                            oop.setBegru(cursor2.getString(30));
                                                            oop.setBegtx(cursor2.getString(31));

                                                            Cursor cursor3 = null;

                                                            /*Wd Tagging Text*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataTagtext where Aufnr = ?", new String[]{orderId});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrTagUnTagTextPrcbl ttp = new OrdrTagUnTagTextPrcbl();
                                                                            ttp.setAufnr(cursor3.getString(1));
                                                                            ttp.setWcnr(cursor3.getString(2));
                                                                            ttp.setObjtype(cursor3.getString(3));
                                                                            ttp.setFormatCol(cursor3.getString(4));
                                                                            ttp.setTextLine(cursor3.getString(5));
                                                                            ttp.setAction(cursor3.getString(6));
                                                                            wdTagtxt_al.add(ttp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd UnTagging Text*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataUntagtext where Aufnr = ?", new String[]{orderId});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrTagUnTagTextPrcbl uttp = new OrdrTagUnTagTextPrcbl();
                                                                            uttp.setAufnr(cursor3.getString(1));
                                                                            uttp.setWcnr(cursor3.getString(2));
                                                                            uttp.setObjtype(cursor3.getString(3));
                                                                            uttp.setFormatCol(cursor3.getString(4));
                                                                            uttp.setTextLine(cursor3.getString(5));
                                                                            uttp.setAction(cursor3.getString(6));
                                                                            wdUnTagtxt_al.add(uttp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Status Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr = ? order by Stonr", new String[]{orderId, WD_Objnr});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                                                            osp.setAufnr(cursor3.getString(2));
                                                                            osp.setVornr(cursor3.getString(3));
                                                                            osp.setObjnr(cursor3.getString(4));
                                                                            osp.setStsma(cursor3.getString(5));
                                                                            osp.setInist(cursor3.getString(6));
                                                                            osp.setStonr(cursor3.getString(7));
                                                                            osp.setHsonr(cursor3.getString(8));
                                                                            osp.setNsonr(cursor3.getString(9));
                                                                            osp.setStat(cursor3.getString(10));
                                                                            osp.setAct(cursor3.getString(11));
                                                                            osp.setTxt04(cursor3.getString(12));
                                                                            osp.setTxt30(cursor3.getString(13));
                                                                            osp.setAction(cursor3.getString(14));
                                                                            wdstp_al.add(osp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Issue Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWcagns where Objnr = ? and Objart = ? order by Hilvl", new String[]{WD_Objnr, "WD"});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                                                            owp.setAufnr(cursor3.getString(2));
                                                                            owp.setObjnr(cursor3.getString(3));
                                                                            owp.setCounter(cursor3.getString(4));
                                                                            owp.setObjart(cursor3.getString(5));
                                                                            owp.setObjtyp(cursor3.getString(6));
                                                                            owp.setPmsog(cursor3.getString(7));
                                                                            owp.setGntxt(cursor3.getString(8));
                                                                            owp.setGeniakt(cursor3.getString(9));
                                                                            owp.setGenvname(cursor3.getString(10));
                                                                            owp.setAction(cursor3.getString(11));
                                                                            owp.setWerks(cursor3.getString(12));
                                                                            owp.setCrname(cursor3.getString(13));
                                                                            if (!cursor3.getString(14).equals(""))
                                                                                owp.setHilvl(Integer.parseInt(cursor3.getString(14)));
                                                                            owp.setProcflg(cursor3.getString(15));
                                                                            owp.setDirection(cursor3.getString(16));
                                                                            owp.setCopyflg(cursor3.getString(17));
                                                                            owp.setMandflg(cursor3.getString(18));
                                                                            owp.setDeacflg(cursor3.getString(19));
                                                                            owp.setStatus(cursor3.getString(20));
                                                                            owp.setAsgnflg(cursor3.getString(21));
                                                                            owp.setAutoflg(cursor3.getString(22));
                                                                            owp.setAgent(cursor3.getString(23));
                                                                            owp.setValflg(cursor3.getString(24));
                                                                            owp.setWcmuse(cursor3.getString(25));
                                                                            owp.setGendatum(dateDisplayFormat(cursor3.getString(26)));
                                                                            owp.setGentime(timeDisplayFormat(cursor3.getString(27)));
                                                                            wdissuep_al.add(owp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Item Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdItemData where Wcnr = ?", new String[]{Wcnr});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrWDItemPrcbl owip = new OrdrWDItemPrcbl();
                                                                            owip.setWcnr(cursor3.getString(1));
                                                                            owip.setWctim(cursor3.getString(2));
                                                                            owip.setObjnr(cursor3.getString(3));
                                                                            owip.setItmtyp(cursor3.getString(4));
                                                                            owip.setSeq(cursor3.getString(5));
                                                                            owip.setPred(cursor3.getString(6));
                                                                            owip.setSucc(cursor3.getString(7));
                                                                            owip.setCcobj(cursor3.getString(8));
                                                                            owip.setCctyp(cursor3.getString(9));
                                                                            if (cursor3.getString(9).equals("E"))
                                                                                owip.setEquipdDesc(getEquipDesc(cursor3.getString(8)));
                                                                            else
                                                                                owip.setEquipdDesc("");
                                                                            owip.setStxt(cursor3.getString(10));
                                                                            owip.setTggrp(cursor3.getString(11));
                                                                            owip.setTgstep(cursor3.getString(12));
                                                                            owip.setTgproc(cursor3.getString(13));
                                                                            owip.setTgtyp(cursor3.getString(14));
                                                                            owip.setTgseq(cursor3.getString(15));
                                                                            owip.setTgtxt(cursor3.getString(16));
                                                                            owip.setUnstep(cursor3.getString(17));
                                                                            owip.setUnproc(cursor3.getString(18));
                                                                            owip.setUntyp(cursor3.getString(19));
                                                                            owip.setUnseq(cursor3.getString(20));
                                                                            owip.setUntxt(cursor3.getString(21));
                                                                            owip.setPhblflg(cursor3.getString(22));
                                                                            owip.setPhbltyp(cursor3.getString(23));
                                                                            owip.setPhblnr(cursor3.getString(24));
                                                                            owip.setTgflg(cursor3.getString(25));
                                                                            owip.setTgform(cursor3.getString(26));
                                                                            owip.setTgnr(cursor3.getString(27));
                                                                            owip.setUnform(cursor3.getString(28));
                                                                            owip.setUnnr(cursor3.getString(29));
                                                                            owip.setControl(cursor3.getString(30));
                                                                            owip.setLocation(cursor3.getString(31));
                                                                            owip.setRefobj(cursor3.getString(32));
                                                                            owip.setAction(cursor3.getString(33));
                                                                            owip.setBtg(cursor3.getString(34));
                                                                            if (cursor3.getString(34).equals("X"))
                                                                                owip.setTagStatus(true);
                                                                            owip.setEtg(cursor3.getString(35));
                                                                            if (cursor3.getString(35).equals("X"))
                                                                                owip.setUntagStatus(true);
                                                                            owip.setBug(cursor3.getString(36));
                                                                            if (cursor3.getString(36).equals("X"))
                                                                                owip.setTagStatus(true);
                                                                            owip.setEug(cursor3.getString(37));
                                                                            if (cursor3.getString(37).equals("X"))
                                                                                owip.setTagStatus(true);
                                                                            owip.setTgprox(getTgprox(cursor3.getString(13)));
                                                                            wdIt_al.add(owip);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            oop.setWdItemPrcbl_Al(wdIt_al);
                                                            oop.setStatusPrcbl_Al(wdstp_al);
                                                            oop.setWcagnsPrcbl_Al(wdissuep_al);
                                                            oop.setTagText_al(wdTagtxt_al);
                                                            oop.setUnTagText_al(wdUnTagtxt_al);
                                                            wd_al.add(oop);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*WaChkRq Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWaChkReq where Wapinr = ? and Wapityp = ?", new String[]{WA_Wapinr, WA_Objtyp});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            OrdrWaChkReqPrcbl owcrp = new OrdrWaChkReqPrcbl();
                                                            owcrp.setWapinr(cursor2.getString(1));
                                                            owcrp.setWapityp(cursor2.getString(2));
                                                            owcrp.setChkPointType(cursor2.getString(5));
                                                            if (cursor2.getString(5).equalsIgnoreCase("W"))
                                                                owcrp.setWkid(cursor2.getString(3));
                                                            else
                                                                owcrp.setNeedid(cursor2.getString(3));
                                                            owcrp.setValue(cursor2.getString(4));
                                                            owcrp.setDesctext(cursor2.getString(6));
                                                            owcrp.setTplnr(cursor2.getString(7));
                                                            owcrp.setWkgrp(cursor2.getString(8));
                                                            owcrp.setNeedgrp(cursor2.getString(9));
                                                            owcrp.setEqunr(cursor2.getString(10));
                                                            waChkReq_al.add(owcrp);
                                                        }
                                                        while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }
                                            opp.setWaWdPrcbl_Al(wd_al);
                                            opp.setWaChkReqPrcbl_Al(waChkReq_al);
                                            opp.setWcagnsPrcbl_Al(waissuep_al);
                                            opp.setStatusPrcbl_Al(wastp_al);
                                            wa_al.add(opp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }
                            wwopp.setStatusPrcbl_Al(wwstp_al);
                            wwopp.setWcagnsPrcbl_Al(wwissuep_al);
                            wwopp.setWaWdPrcbl_Al(wa_al);
                            ww_al.add(wwopp);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Order Status Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr like ? order by Stonr", new String[]{orderId, "OR%"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                            osp.setAufnr(cursor.getString(2));
                            osp.setVornr(cursor.getString(3));
                            osp.setObjnr(cursor.getString(4));
                            osp.setStsma(cursor.getString(5));
                            osp.setInist(cursor.getString(6));
                            osp.setStonr(cursor.getString(7));
                            osp.setHsonr(cursor.getString(8));
                            osp.setNsonr(cursor.getString(9));
                            osp.setStat(cursor.getString(10));
                            osp.setAct(cursor.getString(11));
                            osp.setTxt04(cursor.getString(12));
                            osp.setTxt30(cursor.getString(13));
                            osp.setAction(cursor.getString(14));
                            orstp_al.add(osp);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Components Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderComponents where UUID = ?" + "ORDER BY id DESC", new String[]{orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                            omp.setOprtnId(cursor.getString(3));
                            Cursor cursor1 = null;
                            try {
                                cursor1 = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where UUID = ? and Vornr = ?", new String[]{orderUUID, cursor.getString(3)});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            omp.setOprtnShrtTxt(cursor1.getString(5));
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    omp.setOprtnShrtTxt("");
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                                omp.setOprtnShrtTxt("");
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }
                            omp.setPartId(cursor.getString(10));
                            omp.setMatrlId(cursor.getString(7));
                            omp.setMatrlTxt(cursor.getString(14));
                            omp.setPlantId(cursor.getString(8));
                            omp.setLocation(cursor.getString(9));
                            omp.setQuantity(cursor.getString(11));
                            omp.setRsnum(cursor.getString(5));
                            omp.setRspos(cursor.getString(6));
                            omp.setPostp(cursor.getString(13));
                            omp.setReceipt(cursor.getString(24));
                            omp.setUnloading(cursor.getString(25));
                            omp.setStatus("");
                            omp_al.add(omp);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Objects Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderOlist where UUID = ?", new String[]{orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrObjectPrcbl oobp = new OrdrObjectPrcbl();
                            oobp.setNotifNo(cursor.getString(5));
                            oobp.setEquipId(cursor.getString(6));
                            oobp.setEquipTxt(cursor.getString(12));
                            oobp.setTplnr(cursor.getString(8));
                            oobp.setAction("");
                            oobp_al.add(oobp);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Operations Data*/
            try {
                cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where UUID = ?" + "ORDER BY id DESC", new String[]{orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String op_id = cursor.getString(3);
                            StringBuilder stringbuilder1 = new StringBuilder();
                            Cursor cursor2 = null;
                            try {
                                cursor2 = App_db.rawQuery("select * from DUE_ORDERS_Longtext where UUID = ? and Activity = ?", new String[]{orderUUID, op_id});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            stringbuilder1.append(cursor2.getString(4));
                                            stringbuilder1.append(System.getProperty("line.separator"));
                                        }
                                        while (cursor2.moveToNext());
                                    }
                                } else {
                                    if (cursor2 != null)
                                        cursor2.close();
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                            oop.setSelected(false);
                            oop.setOrdrId(orderId);
                            oop.setOrdrSatus(orderStatus);
                            oop.setOprtnId(cursor.getString(3));
                            oop.setOprtnShrtTxt(cursor.getString(5));
                            oop.setOprtnLngTxt(stringbuilder1.toString());
                            oop.setDuration(cursor.getString(10));
                            oop.setDurationUnit(cursor.getString(11));
                            oop.setPlantId(cursor.getString(7));
                            oop.setPlantTxt(cursor.getString(22));
                            oop.setWrkCntrId(cursor.getString(6));
                            oop.setWrkCntrTxt(cursor.getString(21));
                            oop.setCntrlKeyId(cursor.getString(8));
                            oop.setCntrlKeyTxt(cursor.getString(23));
                            oop.setAueru(cursor.getString(20));
                            oop.setUsr01(cursor.getString(25));
                            oop.setLarnt(cursor.getString(9));
                            oop.setLarnt_text(cursor.getString(24));
                            oop.setFsavd(cursor.getString(12));
                            oop.setSsedd(cursor.getString(13));
                            oop.setRueck(cursor.getString(19));
                            oop.setUsr02(cursor.getString(26));
                            oop.setUsr03(cursor.getString(27));
                            oop.setUsr04(cursor.getString(28));
                            oop.setStatus("");
                            oop_al.add(oop);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            try {
                cursor = App_db.rawQuery("select * from DUE_ORDERS_EtDocs where Zobjid = ?",
                        new String[]{orderId});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                        Notif_EtDocs_Parcelable docs = new Notif_EtDocs_Parcelable();
                        docs.setZobjid(cursor.getString(2));
                        docs.setZdoctype(cursor.getString(3));
                        docs.setZdoctypeitem(cursor.getString(4));
                        docs.setFilename(cursor.getString(5));
                        docs.setFiletype(cursor.getString(6));
                        docs.setFsize(cursor.getString(7));
                        docs.setContent("");
                        docs.setDocid(cursor.getString(9));
                        docs.setDoctype(cursor.getString(10));
                        docs.setObjtype(cursor.getString(11));
                        docs.setContentX(cursor.getString(12));
                        docs.setStatus("old");
                        docs_al.add(docs);
                    }
                        while (cursor.moveToNext());
                    }

                }
            } catch (Exception e) {

            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Order Header Data*/
            try {
                cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where UUID = ? and Aufnr = ?", new String[]{orderUUID, orderId});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Cursor cursor2 = null;
                            try {
                                count = 0;
                                stringbuilder = new StringBuilder();
                                cursor2 = App_db.rawQuery("select * from DUE_ORDERS_Longtext where UUID = ? and Aufnr = ? and Activity = ?", new String[]{orderUUID, orderId, ""});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            count = count + 1;
                                            stringbuilder.append(cursor2.getString(4));
                                            ordrLngTxt = stringbuilder.toString();
                                            if (count < cursor2.getCount())
                                                stringbuilder.append("\n");
                                        } while (cursor2.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            try {
                                boolean added = false;
                                count = 0;
                                stringbuilder = new StringBuilder();
                                cursor2 = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr like ? order by Stonr", new String[]{orderId, "OR%"});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            count = count + 1;
                                            added = false;
                                            if (!cursor2.getString(7).equals("00") && cursor2.getString(10).startsWith("E")) {
                                                if (cursor2.getString(11).equalsIgnoreCase("X")) {
                                                    stringbuilder.append(cursor2.getString(12));
                                                    added = true;
                                                }

                                            } else if (cursor2.getString(7).equals("00") && cursor2.getString(10).startsWith("E")) {
                                                if (cursor2.getString(11).equalsIgnoreCase("X")) {
                                                    stringbuilder.append(cursor2.getString(12));
                                                    added = true;
                                                }
                                            }
                                            statusAll = stringbuilder.toString();
                                            if (count < cursor2.getCount())
                                                if (added)
                                                    stringbuilder.append(" ");
                                        } while (cursor2.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            ohp = new OrdrHeaderPrcbl();
                            ohp.setOrdrUUId(cursor.getString(1));
                            ohp.setOrdrId(cursor.getString(2));
                            ohp.setOrdrStatus(cursor.getString(39));
                            ohp.setStatusALL(statusAll);
                            ohp.setOrdrTypId(cursor.getString(3));
                            ohp.setOrdrTypName(cursor.getString(28));
                            ohp.setOrdrShrtTxt(cursor.getString(4));
                            ohp.setOrdrLngTxt(ordrLngTxt);
                            ohp.setNotifId(cursor.getString(20));
                            ohp.setFuncLocId(cursor.getString(10));
                            ohp.setFuncLocName(cursor.getString(31));
                            ohp.setEquipNum(cursor.getString(9));
                            ohp.setEquipName(cursor.getString(32));
                            ohp.setPriorityId(cursor.getString(8));
                            ohp.setPriorityTxt(cursor.getString(33));
                            ohp.setPlnrGrpId(cursor.getString(23));
                            ohp.setPlnrGrpName(cursor.getString(37));
                            ohp.setPerRespId(cursor.getString(53));
                            ohp.setPerRespName(cursor.getString(54));
                            ohp.setBasicStart(cursor.getString(14));
                            ohp.setBasicEnd(cursor.getString(13));
                            ohp.setRespCostCntrId(cursor.getString(46));
                            ohp.setSysCondId(cursor.getString(47));
                            ohp.setSysCondName(cursor.getString(48));
                            ohp.setWrkCntrId(cursor.getString(24));
                            ohp.setPlant(cursor.getString(25));
                            ohp.setWrkCntrName(cursor.getString(36));
                            ohp.setIwerk(Iwerk);
                            ohp.setActivitytype_id(cursor.getString(5));
                            ohp.setActivitytype_text(cursor.getString(34));
                            ohp.setPosid(cursor.getString(55));
                            ohp.setRevnr(cursor.getString(56));
                            ohp.setBukrs(getBukrs(cursor.getString(9)));
                            ohp.setOrdrOprtnPrcbls(oop_al);
                            ohp.setOrdrObjectPrcbls(oobp_al);
                            ohp.setOrdrMatrlPrcbls(omp_al);
                            ohp.setOrdrStatusPrcbls(orstp_al);
                            ohp.setOrdrPermitPrcbls(ww_al);
                            ohp.setOrdrDocsPrcbls(docs_al);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return ohp;
        }
    }

    private static String getApplName(String objtyp, String iwerk) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtWcmTypes where Iwerk = ? and Objtyp = ?", new String[]{iwerk, objtyp});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(4);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private static String dateDisplayFormat(String date) {
        if (!date.equalsIgnoreCase("00000000")) {
            String inputPattern = "yyyyMMdd";
            String outputPattern = "MMM dd, yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            try {
                Date d = inputFormat.parse(date);
                return outputFormat.format(d);
            } catch (ParseException e) {
                return date;
            }
        } else {
            return "";
        }
    }

    private static String timeDisplayFormat(String date) {
        String inputPattern = "HHmmss";
        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date d = inputFormat.parse(date);
            return outputFormat.format(d);
        } catch (ParseException e) {
            return date;
        }
    }

    private static String getEquipDesc(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        return cursor.getString(5);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private static String getTgprox(String tagCond) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtWcmTgtyp where Tgproc = ?", new String[]{tagCond});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        return cursor.getString(10);
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private static String getBukrs(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(30);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private static String generateRefobj(String orderId) {
        String ad = "";
        for (int i = 0; i < 12 - orderId.length(); i++) {
            ad = ad + "0";
        }
        return "OR" + ad + orderId;
    }
}
