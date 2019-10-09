package com.enstrapp.fieldtekpro.getOrderDetail;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro_sesb_dev.R;
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
                            omp.setStockcat_id(cursor.getString(13));
                            omp.setStockcat_text(cursor.getString(17));
                            omp.setBatch(cursor.getString(26));
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
                        docs.setContent(cursor.getString(12));
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
                            ohp.setPosid(cursor.getString(42));
                            ohp.setRevnr(cursor.getString(56));
                            ohp.setBukrs(getBukrs(cursor.getString(9)));
                            ohp.setRsnum(cursor.getString(65));
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
