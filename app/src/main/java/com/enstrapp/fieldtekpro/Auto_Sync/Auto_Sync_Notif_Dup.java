package com.enstrapp.fieldtekpro.Auto_Sync;


import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Base64;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Local_PushMessage.Local_PushMessage;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Activity;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Attachments;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Causecode;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Longtext;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Task;
import com.enstrapp.fieldtekpro.notifications.Notifications_Create;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class Auto_Sync_Notif_Dup extends BroadcastReceiver {
    SQLiteDatabase FieldTekPro_db;
    String DATABASE_NAME = "", selected_uuid = "", notification_id = "", push_id = "";
    Context context1;
    Local_PushMessage local_pushMessage = new Local_PushMessage();

    @Override
    public void onReceive(Context context, Intent intent) {
        /* Fetching SQLite Database */
        DATABASE_NAME = context.getString(R.string.database_name);
        FieldTekPro_db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        /* Fetching SQLite Database */

        context1 = context;

        selected_uuid = intent.getStringExtra("uuid");
        notification_id = intent.getStringExtra("notification_id");
        push_id = intent.getStringExtra("push_id");

        String action = intent.getAction();
        if (AppConstant.YES_ACTION.equals(action)) {
            int pushid = Integer.parseInt(push_id);
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) context1.getSystemService(ns);
            nMgr.cancel(pushid);
            new Post_Notification_Create().execute("FUNC", notification_id, selected_uuid);
        } else if (AppConstant.STOP_ACTION.equals(action)) {
            int pushid = Integer.parseInt(push_id);
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) context1.getSystemService(ns);
            nMgr.cancel(pushid);
        }
    }


    /*Posting Notification Create to Backend Server*/
    private class Post_Notification_Create extends AsyncTask<String, Integer, Void> {
        ArrayList<Model_Notif_Causecode> causecodeArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Activity> ActivityArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Attachments> AttachmentsArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Longtext> LongtextsArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Task> TasksArrayList = new ArrayList<>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        Map<String, String> notif_create_status;
        String primary_user_resp = "", workcenter_id = "", plant_id = "", effect_text = "", effect_id = "", mal_end_time = "", mal_end_date = "", mal_st_time = "", mal_st_date = "", req_end_time = "", req_end_date = "", req_st_time = "", req_st_date = "", personresponsible_text = "", personresponsible_id = "", Reported_by = "", plannergroup_text = "", plannergroup_id = "", priority_type_text = "", priority_type_id = "", equipment_text = "", equipment_id = "", functionlocation_id = "", notif_text = "", notification_type_id = "", notification_id = "", log_uuid = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String transmit_type = params[0];
                notification_id = params[1];
                log_uuid = params[2];

                /*Fetching Notification Header Data*/
                Cursor headerdata_cursor = null;
                headerdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notification_id});
                if (headerdata_cursor != null && headerdata_cursor.getCount() > 0) {
                    if (headerdata_cursor.moveToFirst()) {
                        do {
                            notification_type_id = headerdata_cursor.getString(2);
                            notif_text = headerdata_cursor.getString(4);
                            functionlocation_id = headerdata_cursor.getString(5);
                            equipment_id = headerdata_cursor.getString(6);
                            equipment_text = headerdata_cursor.getString(30);
                            priority_type_id = headerdata_cursor.getString(14);
                            priority_type_text = headerdata_cursor.getString(31);
                            plannergroup_id = headerdata_cursor.getString(15);
                            plannergroup_text = headerdata_cursor.getString(36);
                            Reported_by = headerdata_cursor.getString(8);
                            personresponsible_id = headerdata_cursor.getString(45);
                            personresponsible_text = headerdata_cursor.getString(46);
                            req_st_date = headerdata_cursor.getString(18);
                            req_st_time = headerdata_cursor.getString(51);
                            req_end_date = headerdata_cursor.getString(19);
                            req_end_time = headerdata_cursor.getString(52);
                            mal_st_date = headerdata_cursor.getString(9);
                            mal_st_time = headerdata_cursor.getString(11);
                            mal_end_date = headerdata_cursor.getString(10);
                            mal_end_time = headerdata_cursor.getString(12);
                            effect_id = headerdata_cursor.getString(47);
                            effect_text = headerdata_cursor.getString(50);
                            plant_id = headerdata_cursor.getString(17);
                            workcenter_id = headerdata_cursor.getString(16);
                            primary_user_resp = headerdata_cursor.getString(39);
                        }
                        while (headerdata_cursor.moveToNext());
                    }
                } else {
                    if (headerdata_cursor != null) {
                        headerdata_cursor.close();
                    }
                }
                /*Fetching Notification Header Data*/


                /*Fetching Notification Long Text*/
                Cursor longtextdata_cursor = null;
                longtextdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{notification_id});
                if (longtextdata_cursor != null && longtextdata_cursor.getCount() > 0) {
                    if (longtextdata_cursor.moveToFirst()) {
                        do {
                            Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                            mnc.setQmnum("");
                            mnc.setObjkey("");
                            mnc.setObjtype("");
                            mnc.setTextLine(longtextdata_cursor.getString(4));
                            LongtextsArrayList.add(mnc);
                        }
                        while (longtextdata_cursor.moveToNext());
                    }
                } else {
                    if (longtextdata_cursor != null) {
                        longtextdata_cursor.close();
                    }
                }
                /*Fetching Notification Long Text*/


                /*Fetching Notification Cause Code*/
                Cursor causecodedata_cursor = null;
                causecodedata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                if (causecodedata_cursor != null && causecodedata_cursor.getCount() > 0) {
                    if (causecodedata_cursor.moveToFirst()) {
                        do {
                            Model_Notif_Causecode mnc = new Model_Notif_Causecode();
                            mnc.setQmnum("");
                            mnc.setItemKey(causecodedata_cursor.getString(3));
                            mnc.setItempartGrp(causecodedata_cursor.getString(4));
                            mnc.setPartgrptext(causecodedata_cursor.getString(5));
                            mnc.setItempartCod(causecodedata_cursor.getString(6));
                            mnc.setPartcodetext(causecodedata_cursor.getString(7));
                            mnc.setItemdefectGrp(causecodedata_cursor.getString(8));
                            mnc.setDefectgrptext(causecodedata_cursor.getString(9));
                            mnc.setItemdefectCod(causecodedata_cursor.getString(10));
                            mnc.setDefectcodetext(causecodedata_cursor.getString(11));
                            mnc.setItemdefectShtxt(causecodedata_cursor.getString(12));
                            mnc.setCauseKey(causecodedata_cursor.getString(13));
                            mnc.setCauseGrp(causecodedata_cursor.getString(14));
                            mnc.setCausegrptext(causecodedata_cursor.getString(15));
                            mnc.setCauseCod(causecodedata_cursor.getString(16));
                            mnc.setCausecodetext(causecodedata_cursor.getString(17));
                            mnc.setCauseShtxt(causecodedata_cursor.getString(18));
                            mnc.setUsr01("");
                            mnc.setUsr02("");
                            mnc.setUsr03("");
                            mnc.setUsr04("");
                            mnc.setUsr05("");
                            mnc.setAction(causecodedata_cursor.getString(24));
                            causecodeArrayList.add(mnc);
                        }
                        while (causecodedata_cursor.moveToNext());
                    }
                } else {
                    if (causecodedata_cursor != null) {
                        causecodedata_cursor.close();
                    }
                }
                /*Fetching Notification Cause Code*/


                /*Fetching Notification Activity*/
                Cursor activitydata_cursor = null;
                activitydata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notification_id});
                if (activitydata_cursor != null && activitydata_cursor.getCount() > 0) {
                    if (activitydata_cursor.moveToFirst()) {
                        do {
                            Model_Notif_Activity mnc = new Model_Notif_Activity();
                            mnc.setQmnum("");
                            mnc.setActvKey(activitydata_cursor.getString(14));
                            mnc.setItemKey(activitydata_cursor.getString(3));
                            mnc.setActvGrp(activitydata_cursor.getString(15));
                            mnc.setActvCod(activitydata_cursor.getString(17));
                            mnc.setActcodetext(activitydata_cursor.getString(18));
                            mnc.setActvShtxt(activitydata_cursor.getString(19));
                            mnc.setUsr01(activitydata_cursor.getString(20));
                            mnc.setUsr02(activitydata_cursor.getString(21));
                            mnc.setUsr03(activitydata_cursor.getString(22));
                            mnc.setUsr04(activitydata_cursor.getString(23));
                            mnc.setAction(activitydata_cursor.getString(26));
                            ActivityArrayList.add(mnc);
                        }
                        while (activitydata_cursor.moveToNext());
                    }
                } else {
                    if (activitydata_cursor != null) {
                        activitydata_cursor.close();
                    }
                }
                /*Fetching Notification Activity*/
                Cursor taskdata_cursor = null;
                taskdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifTasks where Qmnum = ?", new String[]{notification_id});
                if (taskdata_cursor != null && taskdata_cursor.getCount() > 0) {
                    if (taskdata_cursor.moveToFirst()) {
                        do {
                            Model_Notif_Task mnt = new Model_Notif_Task();
                            mnt.setQmnum("");
                            mnt.setTaskKey(taskdata_cursor.getString(3));
                            mnt.setItemKey(taskdata_cursor.getString(3));
                            mnt.setTaskGrp(taskdata_cursor.getString(14));
                            mnt.setTaskgrptext(taskdata_cursor.getString(15));
                            mnt.setTaskCod(taskdata_cursor.getString(16));
                            mnt.setTaskcodetext(taskdata_cursor.getString(17));
                            mnt.setTaskShtxt(taskdata_cursor.getString(18));
                            mnt.setPster(taskdata_cursor.getString(19));
                            mnt.setPeter(taskdata_cursor.getString(20));
                            mnt.setPstur(taskdata_cursor.getString(21));
                            mnt.setPetur(taskdata_cursor.getString(22));
                            mnt.setRelease(taskdata_cursor.getString(28));
                            mnt.setComplete(taskdata_cursor.getString(29));
                            mnt.setSuccess(taskdata_cursor.getString(30));
                            mnt.setAction("I");
                            TasksArrayList.add(mnt);
                        }
                        while (taskdata_cursor.moveToNext());
                    }
                }else
                {
                    if (taskdata_cursor != null) {
                        taskdata_cursor.close();
                    }
                }

                /*Fetching Notification Attachments*/
                Cursor attachmentsdata_cursor = null;
                attachmentsdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                if (attachmentsdata_cursor != null && attachmentsdata_cursor.getCount() > 0) {
                    if (attachmentsdata_cursor.moveToFirst()) {
                        do {
                            Model_Notif_Attachments mnc = new Model_Notif_Attachments();
                            mnc.setObjtype(attachmentsdata_cursor.getString(11));
                            mnc.setZobjid(attachmentsdata_cursor.getString(2));
                            mnc.setZdoctype(attachmentsdata_cursor.getString(3));
                            mnc.setZdoctypeItem(attachmentsdata_cursor.getString(4));
                            mnc.setFilename(attachmentsdata_cursor.getString(5));
                            mnc.setFiletype(attachmentsdata_cursor.getString(6));
                            mnc.setFsize(attachmentsdata_cursor.getString(7));
                            mnc.setDocId(attachmentsdata_cursor.getString(9));
                            mnc.setDocType(attachmentsdata_cursor.getString(10));
                            String status = attachmentsdata_cursor.getString(13);
                            if (status.equalsIgnoreCase("New")) {
                                try {
                                    String file_path = attachmentsdata_cursor.getString(12);
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(file_path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096 * 8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1) {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    String encodeddata = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                    mnc.setContent(encodeddata);
                                } catch (Exception e) {
                                    mnc.setContent("");
                                }
                            } else {
                                mnc.setContent("");
                            }
                            AttachmentsArrayList.add(mnc);
                        }
                        while (attachmentsdata_cursor.moveToNext());
                    }
                } else {
                    if (attachmentsdata_cursor != null) {
                        attachmentsdata_cursor.close();
                    }
                }
                /*Fetching Notification Attachments*/

                notif_create_status = Notifications_Create.Post_NotifCreate_Data(context1, transmit_type, notification_type_id, notif_text, functionlocation_id, equipment_id, equipment_text, priority_type_id, priority_type_text, plannergroup_id, plannergroup_text, Reported_by, personresponsible_id, personresponsible_text, req_st_date, req_st_time, req_end_date, req_end_time, mal_st_date, mal_st_time, mal_end_date, mal_end_time, effect_id, effect_text, plant_id, workcenter_id, primary_user_resp, causecodeArrayList, ActivityArrayList, AttachmentsArrayList, LongtextsArrayList, TasksArrayList, header_custominfo);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (notif_create_status.get("response_status") != null && !notif_create_status.get("response_status").equals("")) {
                if (notif_create_status.get("response_status").equalsIgnoreCase("Duplicate")) {
                    String duplicate_data = notif_create_status.get("response_data");
                    if (duplicate_data != null && !duplicate_data.equals("")) {
                    } else {
                    }
                } else if (notif_create_status.get("response_status").equalsIgnoreCase("success")) {
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS", "Success");
                    cv.put("OBJECT_ID", notif_create_status.get("response_data"));
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context1,
                            context1.getString(R.string.notif_createauto,
                                    notif_create_status.get("response_data")));
                } else if (notif_create_status.get("response_status").startsWith("E")) {
                } else {
                }
            } else {
            }
        }
    }
    /*Posting Notification Create to Backend Server*/


}