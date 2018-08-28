package com.enstrapp.fieldtekpro.Auto_Sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Orders;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Local_PushMessage.Local_PushMessage;
import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.BOM_Reservation;
import com.enstrapp.fieldtekpro.Utilities.Material_Availability_Check;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Activity;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Attachments;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Causecode;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Longtext;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Status;
import com.enstrapp.fieldtekpro.notifications.Model_Notif_Task;
import com.enstrapp.fieldtekpro.notifications.Notification_Complete;
import com.enstrapp.fieldtekpro.notifications.Notification_Postpone;
import com.enstrapp.fieldtekpro.notifications.Notification_Release;
import com.enstrapp.fieldtekpro.notifications.Notifications_Change;
import com.enstrapp.fieldtekpro.notifications.Notifications_Create;
import com.enstrapp.fieldtekpro.orders.ConfirmOrder_Prcbl;
import com.enstrapp.fieldtekpro.orders.Measurement_Parceble;
import com.enstrapp.fieldtekpro.orders.Order_CConfirmation;
import com.enstrapp.fieldtekpro.orders.Order_Create_Change;
import com.enstrapp.fieldtekpro.orders.Order_Rel;
import com.enstrapp.fieldtekpro.orders.OrdrHeaderPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrMatrlPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrObjectPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrOprtnPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrPermitPrcbl;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Auto_Sync_BackgroundService extends Service
{

    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    SQLiteDatabase FieldTekPro_db;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling
    public static final int notify = 60000;
    String DATABASE_NAME = "";
    Context context;
    Local_PushMessage local_pushMessage = new Local_PushMessage();
    String movement_type_id = "", costcenter_id = "", Lgort = "", Unit = "", Plant = "", quantity = "", date = "";


    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        String time_interval = FieldTekPro_SharedPref.getString("FieldTekPro_PushInterval", null);
        if(time_interval != null && !time_interval.equals(""))
        {
            if (mTimer != null)
            {
            }
            else
            {
                int minutes = Integer.parseInt(time_interval);
                long millis = minutes * 60 * 1000;
                mTimer = new Timer();   //recreate new
                mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, millis);   //Schedule task*/
            }
        }
        return START_STICKY;
    }


    @Override
    public void onCreate()
    {
        context = getApplicationContext();
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task*/
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
    }


    class TimeDisplay extends TimerTask
    {
        @Override
        public void run()
        {
            // run on another thread
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
					/* Fetching SQLite Database */
                    DATABASE_NAME = getString(R.string.database_name);
                    FieldTekPro_db = Auto_Sync_BackgroundService.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
					/* Fetching SQLite Database */

                    cd = new ConnectionDetector(getApplicationContext());
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent)
                    {
                        new Get_Alert_Log_Data().execute();
                    }
                }
            });
        }
    }


    private class Get_Alert_Log_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Alert_Log ORDER BY ID ASC",null);
                if(cursor!=null && cursor.getCount()> 0)
                {
                    cursor.moveToNext();
                    do
                    {
                        String document_type = cursor.getString(3);
                        String activity_type = cursor.getString(4);
                        String status = cursor.getString(7);
                        if(status.equalsIgnoreCase("Fail"))
                        {
                            String selected_uuid = cursor.getString(10);
                            String OBJECT_ID = cursor.getString(6);
                            if(document_type.equalsIgnoreCase("Notification") && activity_type.equalsIgnoreCase("Release"))
                            {
                                new Post_Notification_Release().execute(OBJECT_ID,selected_uuid);
                            }
                            else if(document_type.equalsIgnoreCase("Notification") && activity_type.equalsIgnoreCase("Postpone"))
                            {
                                new Post_Notification_Postpone().execute(OBJECT_ID,selected_uuid);
                            }
                            else if(document_type.equalsIgnoreCase("Notification") && activity_type.equalsIgnoreCase("Complete"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Notif_complete");
                            }
                            else if(document_type.equalsIgnoreCase("Notification") && activity_type.equalsIgnoreCase("Create"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Notif_create");
                            }
                            else if(document_type.equalsIgnoreCase("Notification") && activity_type.equalsIgnoreCase("Change"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Notif_change");
                            }
                            else if(document_type.equalsIgnoreCase("Reservation") && activity_type.equalsIgnoreCase("Create"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Bom_Resv");
                            }
                            else if(document_type.equalsIgnoreCase("Order") && activity_type.equalsIgnoreCase("Release"))
                            {
                                new Post_Order_Release().execute(OBJECT_ID,selected_uuid);
                            }
                            else if(document_type.equalsIgnoreCase("Order") && activity_type.equalsIgnoreCase("Create"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Ord_Create");
                            }
                            else if(document_type.equalsIgnoreCase("Order") && activity_type.equalsIgnoreCase("Change"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Ord_Change");
                            }
                            else if(document_type.equalsIgnoreCase("Order") && activity_type.equalsIgnoreCase("Time Confirmation"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Ord_tkconfirm");
                            }
                            else if(document_type.equalsIgnoreCase("Order") && activity_type.equalsIgnoreCase("TECO"))
                            {
                                new Get_Token().execute(OBJECT_ID,selected_uuid,"Ord_teco");
                            }
                        }
                    }
                    while
                    (
                        cursor.moveToNext()
                    );
                }
                else
                {
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }
    }


    private class Get_Token extends AsyncTask<String, Integer, Void>
    {
        String token_status = "", id = "", log_uuid = "", post_type = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                id = params[0];
                log_uuid = params[1];
                post_type = params[2];
                token_status = Token.Get_Token(context);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if(token_status.equalsIgnoreCase("success"))
            {
                if(post_type.equalsIgnoreCase("Notif_complete"))
                {
                    new Post_Notification_Complete().execute(id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Notif_create"))
                {
                    new Post_Notification_Create().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Notif_change"))
                {
                    new Post_Notification_Change().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Bom_Resv"))
                {
                    new Get_Quantity_Availability_Check().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Ord_Create"))
                {
                    new Post_Order_Create().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Ord_Change"))
                {
                    new Post_Order_Change().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Ord_tkconfirm"))
                {
                    new Post_Order_TKConfirmation().execute("", id, log_uuid);
                }
                else if(post_type.equalsIgnoreCase("Ord_teco"))
                {
                    new Post_Order_TECO().execute("", id, log_uuid);
                }
            }
            else
            {
            }
        }
    }


    /*Posting Order TECO to Backend Server*/
    private class Post_Order_TECO extends AsyncTask<String, Integer, Void>
    {
        String Response = "";
        String order_id = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            order_id = params[1];
            log_uuid = params[2];
            ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();
            ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
            cop.setAufnr(order_id);
            cop.setStatus("TECO");
            cop_al.add(cop);
            if (cop_al.size() > 0)
                Response = new Order_CConfirmation().Get_Data(context, cop_al, null, "", "CCORD", order_id, "TECO");
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            if (Response.startsWith("S"))
            {
                ContentValues cv = new ContentValues();
                cv.put("STATUS","Success");
                FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                local_pushMessage.send_local_pushmessage(context,Response.substring(1));
            }
            else if (Response.startsWith("E"))
            {
            }
            else
            {
            }
        }
    }
    /*Posting Order TECO to Backend Server*/


    /*Posting Order TKConfirm to Backend Server*/
    private class Post_Order_TKConfirmation extends AsyncTask<String, Integer, Void>
    {
        String order_id = "", log_uuid = "";
        ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();
        ArrayList<Measurement_Parceble> mpo_al = new ArrayList<>();
        String Response = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                order_id = params[1];
                log_uuid = params[2];

                /*Fetching Confirmation Data*/
                Cursor cursor = null;
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from Orders_TKConfirm where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
                                cop.setAufnr(cursor.getString(2));
                                cop.setVornr(cursor.getString(3));
                                cop.setConfNo("");
                                cop.setConfText(cursor.getString(5));
                                cop.setActWork("0");
                                cop.setUnWork("");
                                cop.setPlanWork("0");
                                cop.setLearr("");
                                cop.setBemot("");
                                cop.setGrund("");
                                cop.setLeknw(cursor.getString(12));
                                cop.setAueru(cursor.getString(13));
                                cop.setAusor("");
                                cop.setPernr("");
                                cop.setLoart("");
                                cop.setStatus("");
                                cop.setRsnum("");
                                cop.setPostgDate("");
                                cop.setPlant("");
                                cop.setWorkCntr("");
                                cop.setExecStartDate(cursor.getString(21));
                                cop.setExecStartTime(cursor.getString(22));
                                cop.setExecFinDate(cursor.getString(23));
                                cop.setExecFinTime(cursor.getString(24));
                                cop.setPernr(cursor.getString(25));
                                cop_al.add(cop);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                } catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Fetching Confirmation Data*/

                Response = new Order_CConfirmation().Get_Data(context, cop_al, mpo_al,"", "CCORD", order_id, "ORCC");
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (Response.startsWith("S"))
            {
                FieldTekPro_db.execSQL("delete from Orders_TKConfirm where Aufnr = ?", new String[]{order_id});
                ContentValues cv = new ContentValues();
                cv.put("STATUS","Success");
                FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                local_pushMessage.send_local_pushmessage(context,Response.substring(1));
            }
            else if (Response.startsWith("E"))
            {
            }
            else
            {
            }
        }
    }
    /*Posting Order TKConfirm to Backend Server*/


    /*Posting Order Change to Backend Server*/
    private class Post_Order_Change extends AsyncTask<String, Integer, Void>
    {
        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();
        String order_id = "", log_uuid = "", ordrLngTxt = "";
        String[] Response = new String[2];
        ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
        ArrayList<OrdrObjectPrcbl> oobp_al = new ArrayList<OrdrObjectPrcbl>();
        ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> orstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        ArrayList<HashMap<String, String>> operation_custom_info_arraylist = new ArrayList<>();
        ArrayList<HashMap<String, String>> material_custom_info_arraylist = new ArrayList<>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                order_id = params[1];
                log_uuid = params[2];

                /*Fetching Operations Data*/
                Cursor cursor = null;
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                String op_id = cursor.getString(3);
                                StringBuilder stringbuilder1 = new StringBuilder();
                                Cursor cursor2 = null;
                                try
                                {
                                    cursor2 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ?", new String[]{order_id, op_id});
                                    if (cursor2 != null && cursor2.getCount() > 0)
                                    {
                                        if (cursor2.moveToFirst())
                                        {
                                            do
                                            {
                                                stringbuilder1.append(cursor2.getString(4));
                                                stringbuilder1.append(System.getProperty("line.separator"));
                                            }
                                            while (cursor2.moveToNext());
                                        }
                                    }
                                    else
                                    {
                                        if (cursor2 != null)
                                        {
                                            cursor2.close();
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                finally
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }

                                OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                                oop.setOrdrId(order_id);
                                oop.setOrdrSatus("");
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
                                oop.setFsavd(cursor.getString(12));
                                oop.setSsedd(cursor.getString(13));
                                oop.setRueck(cursor.getString(19));
                                oop.setStatus(cursor.getString(30));
                                oop_al.add(oop);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                } catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Fetching Operations Data*/


                /*Components Data*/
                try {
                    cursor = FieldTekPro_db.rawQuery("select * from EtOrderComponents where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                                omp.setOprtnId(cursor.getString(3));
                                Cursor cursor1 = null;
                                try
                                {
                                    cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where Aufnr = ? and Vornr = ?", new String[]{order_id, cursor.getString(3)});
                                    if (cursor1 != null && cursor1.getCount() > 0) {
                                        if (cursor1.moveToFirst())
                                        {
                                            do
                                            {
                                                omp.setOprtnShrtTxt(cursor1.getString(5));
                                            }
                                            while (cursor1.moveToNext());
                                        }
                                    }
                                    else
                                    {
                                        omp.setOprtnShrtTxt("");
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
                                    omp.setOprtnShrtTxt("");
                                }
                                finally
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
                                }
                                omp.setAufnr(order_id);
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
                                omp.setStatus(cursor.getString(23));
                                omp_al.add(omp);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Components Data*/


                /*Objects Data*/
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from EtOrderOlist where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                OrdrObjectPrcbl oobp = new OrdrObjectPrcbl();
                                oobp.setNotifNo(cursor.getString(5));
                                oobp.setEquipId(cursor.getString(6));
                                oobp.setEquipTxt(cursor.getString(12));
                                oobp.setTplnr(cursor.getString(8));
                                oobp.setAction(cursor.getString(14));
                                oobp_al.add(oobp);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Objects Data*/


                /*Order Status Data*/
                try {
                    cursor = FieldTekPro_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr like ? order by Stonr", new String[]{order_id, "OR%"});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
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
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Order Status Data*/


                /*Fetching Order Header Data*/
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                Cursor cursor2 = null;
                                try
                                {
                                    StringBuilder longtext_stringbuilder = new StringBuilder();
                                    cursor2 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ?", new String[]{order_id,""});
                                    if (cursor2 != null && cursor2.getCount() > 0)
                                    {
                                        if (cursor2.moveToFirst())
                                        {
                                            do
                                            {
                                                longtext_stringbuilder.append(cursor2.getString(4));
                                                longtext_stringbuilder.append(System.getProperty("line.separator"));
                                                ordrLngTxt = longtext_stringbuilder.toString();
                                            }
                                            while (cursor2.moveToNext());
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                finally
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                ohp.setOrdrId(order_id);
                                ohp.setOrdrStatus(cursor.getString(39));
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
                                ohp.setIwerk("");
                                ohp.setPosid(cursor.getString(55));
                                ohp.setRevnr(cursor.getString(56));
                                ohp.setBukrs("");
                                ohp.setOrdrOprtnPrcbls(oop_al);
                                ohp.setOrdrObjectPrcbls(oobp_al);
                                ohp.setOrdrMatrlPrcbls(omp_al);
                                ohp.setOrdrStatusPrcbls(orstp_al);
                                ohp.setOrdrPermitPrcbls(ww_al);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                 /*Fetching Order Header Data*/


                Response = new Order_Create_Change().Post_Create_Order(context, ohp, "LOAD", "CHORD", ohp.getOrdrId(), "", header_custominfo,operation_custom_info_arraylist,material_custom_info_arraylist);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (Response[0].startsWith("S"))
            {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for(int i = 0; i < sp.length; i++)
                {
                    if(i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                ContentValues cv = new ContentValues();
                cv.put("STATUS","Success");
                cv.put("OBJECT_ID",Response[1].toString());
                FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                local_pushMessage.send_local_pushmessage(context,Response[0].substring(1));
            }
            else if (Response[0].startsWith("E"))
            {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for(int i = 0; i < sp.length; i++)
                {
                    if(i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                local_pushMessage.send_local_pushmessage(context,"For order "+order_id+" :"+response.toString());
            }
            else
            {
            }
        }
    }
    /*Posting Order Change to Backend Server*/


    /*Posting Order Create to Backend Server*/
    private class Post_Order_Create extends AsyncTask<String, Integer, Void>
    {
        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();
        String order_id = "", log_uuid = "", ordrLngTxt = "";
        String[] Response = new String[2];
        ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
        ArrayList<OrdrObjectPrcbl> oobp_al = new ArrayList<OrdrObjectPrcbl>();
        ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> orstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        ArrayList<HashMap<String, String>> operation_custom_info_arraylist = new ArrayList<>();
        ArrayList<HashMap<String, String>> material_custom_info_arraylist = new ArrayList<>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                order_id = params[1];
                log_uuid = params[2];

                /*Fetching Operations Data*/
                Cursor cursor = null;
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                String op_id = cursor.getString(3);
                                StringBuilder stringbuilder1 = new StringBuilder();
                                Cursor cursor2 = null;
                                try
                                {
                                    cursor2 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ?", new String[]{order_id, op_id});
                                    if (cursor2 != null && cursor2.getCount() > 0)
                                    {
                                        if (cursor2.moveToFirst())
                                        {
                                            do
                                            {
                                                stringbuilder1.append(cursor2.getString(4));
                                                stringbuilder1.append(System.getProperty("line.separator"));
                                            }
                                            while (cursor2.moveToNext());
                                        }
                                    }
                                    else
                                    {
                                        if (cursor2 != null)
                                        {
                                            cursor2.close();
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                finally
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }

                                OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                                oop.setSelected(false);
                                oop.setOrdrId("");
                                oop.setOrdrSatus("");
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
                                oop.setFsavd(cursor.getString(12));
                                oop.setSsedd(cursor.getString(13));
                                oop.setRueck(cursor.getString(19));
                                oop.setStatus("I");
                                oop_al.add(oop);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Fetching Operations Data*/


                /*Components Data*/
                try {
                    cursor = FieldTekPro_db.rawQuery("select * from EtOrderComponents where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                                omp.setOprtnId(cursor.getString(3));
                                Cursor cursor1 = null;
                                try
                                {
                                    cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where Aufnr = ? and Vornr = ?", new String[]{order_id, cursor.getString(3)});
                                    if (cursor1 != null && cursor1.getCount() > 0) {
                                        if (cursor1.moveToFirst())
                                        {
                                            do
                                            {
                                                omp.setOprtnShrtTxt(cursor1.getString(5));
                                            }
                                            while (cursor1.moveToNext());
                                        }
                                    }
                                    else
                                    {
                                        omp.setOprtnShrtTxt("");
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
                                    omp.setOprtnShrtTxt("");
                                }
                                finally
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
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
                                omp.setStatus("I");
                                omp_al.add(omp);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Components Data*/


                /*Fetching Order Header Data*/
                try
                {
                    cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{order_id});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                Cursor cursor2 = null;
                                try
                                {
                                    StringBuilder longtext_stringbuilder = new StringBuilder();
                                    cursor2 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ?", new String[]{order_id,""});
                                    if (cursor2 != null && cursor2.getCount() > 0)
                                    {
                                        if (cursor2.moveToFirst())
                                        {
                                            do
                                            {
                                                longtext_stringbuilder.append(cursor2.getString(4));
                                                longtext_stringbuilder.append(System.getProperty("line.separator"));
                                                ordrLngTxt = longtext_stringbuilder.toString();
                                            }
                                            while (cursor2.moveToNext());
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                finally
                                {
                                    if (cursor2 != null)
                                    {
                                        cursor2.close();
                                    }
                                }
                                ohp.setOrdrUUId("");
                                ohp.setOrdrId("");
                                ohp.setOrdrStatus(cursor.getString(39));
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
                                ohp.setIwerk("");
                                ohp.setPosid(cursor.getString(55));
                                ohp.setRevnr(cursor.getString(56));
                                ohp.setBukrs("");
                                ohp.setOrdrOprtnPrcbls(oop_al);
                                ohp.setOrdrObjectPrcbls(oobp_al);
                                ohp.setOrdrMatrlPrcbls(omp_al);
                                ohp.setOrdrStatusPrcbls(orstp_al);
                                ohp.setOrdrPermitPrcbls(ww_al);
                            }
                            while (cursor.moveToNext());
                        }
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                 /*Fetching Order Header Data*/


                Response = new Order_Create_Change().Post_Create_Order(context, ohp, "LOAD", "CRORD", "", "", header_custominfo,operation_custom_info_arraylist,material_custom_info_arraylist);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (Response[0].startsWith("S"))
            {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for(int i = 0; i < sp.length; i++)
                {
                    if(i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                FieldTekPro_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtOrderStatus where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from DUE_ORDERS_EtDocs where Zobjid = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtWcmWwData where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtWcmWaData where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtWcmWdData where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtWcmWcagns where Aufnr = ?", new String[]{order_id});
                FieldTekPro_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{order_id});
                ContentValues cv = new ContentValues();
                cv.put("STATUS","Success");
                cv.put("OBJECT_ID",Response[1].toString());
                FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                local_pushMessage.send_local_pushmessage(context,Response[0].substring(1));
            }
            else if (Response[0].startsWith("E"))
            {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for(int i = 0; i < sp.length; i++)
                {
                    if(i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                local_pushMessage.send_local_pushmessage(context,"For order "+order_id+" :"+response.toString());
            }
            else
            {
            }
        }
    }
    /*Posting Order Create to Backend Server*/



    /*Posting Order Release to Backend Server*/
    private class Post_Order_Release extends AsyncTask<String, Integer, Void>
    {
        String Response = "", orderId = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            orderId = params[0];
            log_uuid = params[1];
            Response = new Order_Rel().Get_Data(context, "", "X", "RLORD", orderId);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Response.startsWith("S"))
            {
                ContentValues cv = new ContentValues();
                cv.put("STATUS","Success");
                FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                local_pushMessage.send_local_pushmessage(context,"order "+orderId+" is released successfully.");
            }
            else if (Response.startsWith("E"))
            {
            }
            else
            {
            }
        }
    }
    /*Posting Order Release to Backend Server*/


    /*Posting Material Availability Check for BOM Reservation to Backend Server*/
    private class Get_Quantity_Availability_Check extends AsyncTask<String, Integer, Void>
    {
        String stock_availability_status = "", matnr_id = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                movement_type_id = ""; costcenter_id = ""; Lgort = ""; Unit = ""; Plant = ""; quantity = ""; date = "";
                matnr_id = params[1];
                log_uuid = params[2];
                Cursor cursor = FieldTekPro_db.rawQuery("select * from BOM_RESERVE_HEADER where BOM_ID = ?",new String[]{matnr_id});
                if(cursor!=null && cursor.getCount()> 0)
                {
                    cursor.moveToNext();
                    do
                    {
                        date = cursor.getString(4);
                        quantity = cursor.getString(10);
                        Plant = cursor.getString(3);
                        Unit  = cursor.getString(11);
                        Lgort = cursor.getString(12);
                        movement_type_id = cursor.getString(5);
                        costcenter_id = cursor.getString(8);
                    }
                    while
                    (
                        cursor.moveToNext()
                    );
                }
                else
                {
                }
            }
            catch (Exception e)
            {
            }
            try
            {
                stock_availability_status = Material_Availability_Check.material_availability_check(context,"",matnr_id,"",quantity,Unit,Plant,Lgort, date);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (stock_availability_status != null && !stock_availability_status.equals(""))
            {
                if(stock_availability_status.startsWith("S"))
                {
                    new POST_BOM_Reservation().execute(matnr_id, log_uuid);
                }
                else if(stock_availability_status.startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Material Availability Check for BOM Reservation to Backend Server*/


    /*Posting BOM Reservation to Backend Server*/
    private class POST_BOM_Reservation extends AsyncTask<String, Integer, Void>
    {
        String log_uuid = "", bom_reservation_status = "", matnr_id = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            matnr_id = params[0];
            log_uuid = params[1];
            try
            {
                bom_reservation_status = BOM_Reservation.post_bom_reservation(context,"",matnr_id,"",quantity,Unit,Plant,Lgort, date, movement_type_id, costcenter_id);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (bom_reservation_status != null && !bom_reservation_status.equals(""))
            {
                if(bom_reservation_status.startsWith("S"))
                {
                    FieldTekPro_db.execSQL("delete from BOM_RESERVE_HEADER where BOM_ID = ?", new String[]{matnr_id});
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS","Success");
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context,"For Material "+matnr_id+" ,"+bom_reservation_status.substring(1).toString());
                }
                else if(bom_reservation_status.startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting BOM Reservation to Backend Server*/


    /*Posting Notification Change to Backend Server*/
    private class Post_Notification_Change extends AsyncTask<String, Integer, Void>
    {
        ArrayList<Model_Notif_Causecode> causecodeArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Activity> ActivityArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Attachments> AttachmentsArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Longtext> LongtextsArrayList = new ArrayList<>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        ArrayList<Model_Notif_Task> TasksArrayList = new ArrayList<>();
        Map<String, String> notif_change_status;
        String primary_user_resp = "", workcenter_id = "", plant_id = "", effect_text = "", effect_id = "", mal_end_time = "", mal_end_date = "", mal_st_time = "", mal_st_date = "", req_end_time = "", req_end_date = "", req_st_time = "", req_st_date = "", personresponsible_text = "", personresponsible_id = "", Reported_by = "", plannergroup_text = "", plannergroup_id = "", priority_type_text = "", priority_type_id = "", equipment_text = "", equipment_id = "", functionlocation_id = "", notif_text = "", notification_type_id = "", notification_id = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String transmit_type = params[0];
                notification_id = params[1];
                log_uuid = params[2];

                ArrayList<Model_Notif_Status> statusArrayList = new ArrayList<>();

                /*Fetching Notification Header Data*/
                Cursor headerdata_cursor = null;
                headerdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notification_id});
                if (headerdata_cursor != null && headerdata_cursor.getCount() > 0)
                {
                    if (headerdata_cursor.moveToFirst())
                    {
                        do
                        {
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
                }
                else
                {
                    if (headerdata_cursor != null)
                    {
                        headerdata_cursor.close();
                    }
                }
                /*Fetching Notification Header Data*/


                /*Fetching Notification Long Text*/
                Cursor longtextdata_cursor = null;
                longtextdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                if (longtextdata_cursor != null && longtextdata_cursor.getCount() > 0)
                {
                    if (longtextdata_cursor.moveToFirst())
                    {
                        do
                        {
                            Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                            mnc.setQmnum(notification_id);
                            mnc.setObjkey("");
                            mnc.setObjtype("");
                            mnc.setTextLine(longtextdata_cursor.getString(4));
                            LongtextsArrayList.add(mnc);
                        }
                        while (longtextdata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (longtextdata_cursor != null)
                    {
                        longtextdata_cursor.close();
                    }
                }
                /*Fetching Notification Long Text*/


                /*Fetching Notification Cause Code*/
                Cursor causecodedata_cursor = null;
                causecodedata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                if (causecodedata_cursor != null && causecodedata_cursor.getCount() > 0)
                {
                    if (causecodedata_cursor.moveToFirst())
                    {
                        do
                        {
                            Model_Notif_Causecode mnc = new Model_Notif_Causecode();
                            mnc.setQmnum(notification_id);
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
                }
                else
                {
                    if (causecodedata_cursor != null)
                    {
                        causecodedata_cursor.close();
                    }
                }
                /*Fetching Notification Cause Code*/


                /*Fetching Notification Activity*/
                Cursor activitydata_cursor = null;
                activitydata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notification_id});
                if (activitydata_cursor != null && activitydata_cursor.getCount() > 0)
                {
                    if (activitydata_cursor.moveToFirst())
                    {
                        do
                        {
                            Model_Notif_Activity mnc = new Model_Notif_Activity();
                            mnc.setQmnum(notification_id);
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
                }
                else
                {
                    if (activitydata_cursor != null)
                    {
                        activitydata_cursor.close();
                    }
                }
                /*Fetching Notification Activity*/


                /*Fetching Notification Attachments*/
                Cursor attachmentsdata_cursor = null;
                attachmentsdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                if (attachmentsdata_cursor != null && attachmentsdata_cursor.getCount() > 0)
                {
                    if (attachmentsdata_cursor.moveToFirst())
                    {
                        do
                        {
                            Model_Notif_Attachments mnc = new Model_Notif_Attachments();
                            mnc.setObjtype(attachmentsdata_cursor.getString(11));
                            mnc.setZobjid(notification_id);
                            mnc.setZdoctype(attachmentsdata_cursor.getString(3));
                            mnc.setZdoctypeItem(attachmentsdata_cursor.getString(4));
                            mnc.setFilename(attachmentsdata_cursor.getString(5));
                            mnc.setFiletype(attachmentsdata_cursor.getString(6));
                            mnc.setFsize(attachmentsdata_cursor.getString(7));
                            mnc.setDocId(attachmentsdata_cursor.getString(9));
                            mnc.setDocType(attachmentsdata_cursor.getString(10));
                            String status = attachmentsdata_cursor.getString(13);
                            if(status.equalsIgnoreCase("New"))
                            {
                                try
                                {
                                    String file_path = attachmentsdata_cursor.getString(12);
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(file_path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096*8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1)
                                    {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    String encodeddata = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                    mnc.setContent(encodeddata);
                                }
                                catch (Exception e)
                                {
                                    mnc.setContent("");
                                }
                            }
                            else
                            {
                                mnc.setContent("");
                            }
                            AttachmentsArrayList.add(mnc);
                        }
                        while (attachmentsdata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (attachmentsdata_cursor != null)
                    {
                        attachmentsdata_cursor.close();
                    }
                }
                /*Fetching Notification Attachments*/

                notif_change_status = Notifications_Change.Post_NotifChange_Data(context,transmit_type,notification_id,notification_type_id, notif_text,functionlocation_id, equipment_id, equipment_text,priority_type_id, priority_type_text, plannergroup_id, plannergroup_text, Reported_by, personresponsible_id, personresponsible_text, req_st_date, req_st_time, req_end_date, req_end_time, mal_st_date, mal_st_time, mal_end_date, mal_end_time,effect_id, effect_text, plant_id, workcenter_id, primary_user_resp, causecodeArrayList, ActivityArrayList, AttachmentsArrayList, LongtextsArrayList, statusArrayList,TasksArrayList, header_custominfo);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_change_status.get("response_status") != null && !notif_change_status.get("response_status").equals(""))
            {
                if(notif_change_status.get("response_status").equalsIgnoreCase("Duplicate"))
                {
                }
                else if(notif_change_status.get("response_status").equalsIgnoreCase("success"))
                {
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS","Success");
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context,"Notification "+notif_change_status.get("response_data")+" has been changed successfully.");
                }
                else if(notif_change_status.get("response_status").startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Notification Change to Backend Server*/



    /*Posting Notification Create to Backend Server*/
    private class Post_Notification_Create extends AsyncTask<String, Integer, Void>
    {
        ArrayList<Model_Notif_Causecode> causecodeArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Activity> ActivityArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Attachments> AttachmentsArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Longtext> LongtextsArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Task> TasksArrayList = new ArrayList<>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        Map<String, String> notif_create_status;
        String primary_user_resp = "", workcenter_id = "", plant_id = "", effect_text = "", effect_id = "", mal_end_time = "", mal_end_date = "", mal_st_time = "", mal_st_date = "", req_end_time = "", req_end_date = "", req_st_time = "", req_st_date = "", personresponsible_text = "", personresponsible_id = "", Reported_by = "", plannergroup_text = "", plannergroup_id = "", priority_type_text = "", priority_type_id = "", equipment_text = "", equipment_id = "", functionlocation_id = "", notif_text = "", notification_type_id = "", notification_id = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String transmit_type = params[0];
                notification_id = params[1];
                log_uuid = params[2];

                /*Fetching Notification Header Data*/
                Cursor headerdata_cursor = null;
                headerdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{notification_id});
                if (headerdata_cursor != null && headerdata_cursor.getCount() > 0)
                {
                    if (headerdata_cursor.moveToFirst())
                    {
                        do
                        {
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
                }
                else
                {
                    if (headerdata_cursor != null)
                    {
                        headerdata_cursor.close();
                    }
                }
                /*Fetching Notification Header Data*/


                /*Fetching Notification Long Text*/
                Cursor longtextdata_cursor = null;
                longtextdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?", new String[]{notification_id});
                if (longtextdata_cursor != null && longtextdata_cursor.getCount() > 0)
                {
                    if (longtextdata_cursor.moveToFirst())
                    {
                        do
                        {
                            Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                            mnc.setQmnum("");
                            mnc.setObjkey("");
                            mnc.setObjtype("");
                            mnc.setTextLine(longtextdata_cursor.getString(4));
                            LongtextsArrayList.add(mnc);
                        }
                        while (longtextdata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (longtextdata_cursor != null)
                    {
                        longtextdata_cursor.close();
                    }
                }
                /*Fetching Notification Long Text*/


                /*Fetching Notification Cause Code*/
                Cursor causecodedata_cursor = null;
                causecodedata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[]{notification_id});
                if (causecodedata_cursor != null && causecodedata_cursor.getCount() > 0)
                {
                    if (causecodedata_cursor.moveToFirst())
                    {
                        do
                        {
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
                            mnc.setAction("I");
                            causecodeArrayList.add(mnc);
                        }
                        while (causecodedata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (causecodedata_cursor != null)
                    {
                        causecodedata_cursor.close();
                    }
                }
                /*Fetching Notification Cause Code*/


                /*Fetching Notification Activity*/
                Cursor activitydata_cursor = null;
                activitydata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[]{notification_id});
                if (activitydata_cursor != null && activitydata_cursor.getCount() > 0)
                {
                    if (activitydata_cursor.moveToFirst())
                    {
                        do
                        {
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
                            mnc.setAction("I");
                            ActivityArrayList.add(mnc);
                        }
                        while (activitydata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (activitydata_cursor != null)
                    {
                        activitydata_cursor.close();
                    }
                }
                /*Fetching Notification Activity*/


                /*Fetching Notification Attachments*/
                Cursor attachmentsdata_cursor = null;
                attachmentsdata_cursor = FieldTekPro_db.rawQuery("select * from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                if (attachmentsdata_cursor != null && attachmentsdata_cursor.getCount() > 0)
                {
                    if (attachmentsdata_cursor.moveToFirst())
                    {
                        do
                        {
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
                            if(status.equalsIgnoreCase("New"))
                            {
                                try
                                {
                                    String file_path = attachmentsdata_cursor.getString(12);
                                    byte[] byteArray = null;
                                    InputStream inputStream = new FileInputStream(file_path);
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    byte[] b = new byte[4096*8];
                                    int bytesRead = 0;
                                    while ((bytesRead = inputStream.read(b)) != -1)
                                    {
                                        bos.write(b, 0, bytesRead);
                                    }
                                    byteArray = bos.toByteArray();
                                    String encodeddata = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                    mnc.setContent(encodeddata);
                                }
                                catch (Exception e)
                                {
                                    mnc.setContent("");
                                }
                            }
                            else
                            {
                                mnc.setContent("");
                            }
                            AttachmentsArrayList.add(mnc);
                        }
                        while (attachmentsdata_cursor.moveToNext());
                    }
                }
                else
                {
                    if (attachmentsdata_cursor != null)
                    {
                        attachmentsdata_cursor.close();
                    }
                }
                /*Fetching Notification Attachments*/

                notif_create_status = Notifications_Create.Post_NotifCreate_Data(context,transmit_type,notification_type_id, notif_text,functionlocation_id, equipment_id, equipment_text,priority_type_id, priority_type_text, plannergroup_id, plannergroup_text, Reported_by, personresponsible_id, personresponsible_text, req_st_date, req_st_time, req_end_date, req_end_time, mal_st_date, mal_st_time, mal_end_date, mal_end_time,effect_id, effect_text, plant_id, workcenter_id, primary_user_resp, causecodeArrayList, ActivityArrayList, AttachmentsArrayList, LongtextsArrayList, TasksArrayList, header_custominfo);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_create_status.get("response_status") != null && !notif_create_status.get("response_status").equals(""))
            {
                if(notif_create_status.get("response_status").equalsIgnoreCase("Duplicate"))
                {
                    String duplicate_data = notif_create_status.get("response_data");
                    if (duplicate_data != null && ! duplicate_data.equals(""))
                    {
                        try
                        {
                            StringBuffer sb = new StringBuffer();
                            JSONArray jsonArray = new JSONArray(duplicate_data);
                            if(jsonArray.length() > 7)
                            {
                                for(int i = 0; i < 7; i++)
                                {
                                    String Qmnum = jsonArray.getJSONObject(i).optString("Qmnum");
                                    sb.append(Qmnum);
                                    sb.append(", ");
                                }
                            }
                            else
                            {
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    String Qmnum = jsonArray.getJSONObject(i).optString("Qmnum");
                                    sb.append(Qmnum);
                                    sb.append(", ");
                                }
                            }
                            Context context = getBaseContext();
                            Notification.Builder notif;
                            NotificationManager nm;
                            notif = new Notification.Builder(getApplicationContext());
                            Bitmap icon;
                            icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_app_icon);
                            notif.setSmallIcon(R.drawable.ic_app_icon)
                                    .setLargeIcon(icon)
                                    .setContentTitle("Duplicate Notifications")
                                    .setStyle(new Notification.BigTextStyle().bigText("For Equipment "+equipment_id+", You have multiple notifications : "+sb.toString()+". Do you want to create Notification?"))
                                    .setContentText("Duplicate Notifications");
                            Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            notif.setSound(path);
                            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            Date now = new Date();
                            int push_id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));

                            Intent yesReceive = new Intent();
                            yesReceive.setAction(AppConstant.YES_ACTION);
                            yesReceive.putExtra("uuid", log_uuid);
                            yesReceive.putExtra("notification_id", notification_id);
                            yesReceive.putExtra("push_id", push_id+"");
                            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
                            notif.addAction(R.drawable.ic_tickmark_enabled, "Yes", pendingIntentYes);

                            Intent yesReceive2 = new Intent();
                            yesReceive2.setAction(AppConstant.STOP_ACTION);
                            yesReceive2.putExtra("uuid", log_uuid);
                            yesReceive2.putExtra("notification_id", notification_id);
                            yesReceive2.putExtra("push_id", push_id+"");
                            PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(context, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
                            notif.addAction(R.drawable.ic_delete_icon_red, "No", pendingIntentYes2);

                            nm.notify(push_id, notif.getNotification());
                        }
                        catch(Exception e)
                        {
                        }
                    }
                    else
                    {
                    }
                }
                else if(notif_create_status.get("response_status").equalsIgnoreCase("success"))
                {
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_NotifHeader where Qmnum = ?",new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?",new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?",new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifLongtext where Qmnum = ?",new String[]{notification_id});
                    FieldTekPro_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ?",new String[]{notification_id});
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS","Success");
                    cv.put("OBJECT_ID",notif_create_status.get("response_data"));
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context,"Notification "+notif_create_status.get("response_data")+" has been created successfully.");
                }
                else if(notif_create_status.get("response_status").startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Notification Create to Backend Server*/



    /*Posting Notification Complete to Backend Server*/
    private class Post_Notification_Complete extends AsyncTask<String, Integer, Void>
    {
        String notification_id = "", notif_complete_status = "",log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                notification_id = params[0];
                log_uuid = params[1];
                notif_complete_status = Notification_Complete.Get_NOCO_Data(context,params[0]);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_complete_status != null && !notif_complete_status.equals(""))
            {
                if(notif_complete_status.equalsIgnoreCase("success"))
                {
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS","Success");
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context,"Notification "+notification_id+" is completed successfully.");
                }
                else if(notif_complete_status.startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Notification Complete to Backend Server*/


    /*Posting Notification Postpone to Backend Server*/
    private class Post_Notification_Postpone extends AsyncTask<String, Integer, Void>
    {
        String notification_id = "", notif_postpone_status = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                notification_id = params[0];
                log_uuid = params[1];
                notif_postpone_status = Notification_Postpone.Get_Notif_Postpone_Data(context,params[0]);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_postpone_status != null && !notif_postpone_status.equals(""))
            {
                if(notif_postpone_status.equalsIgnoreCase("success"))
                {
                    ContentValues cv = new ContentValues();
                    cv.put("STATUS","Success");
                    FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
                    local_pushMessage.send_local_pushmessage(context,"Notification "+notification_id+" is postponed successfully.");
                }
                else if(notif_postpone_status.contains("already postponed"))
                {
                }
                else if(notif_postpone_status.startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Notification Postpone to Backend Server*/


    /*Posting Notification Release to Backend Server*/
    private class Post_Notification_Release extends AsyncTask<String, Integer, Void>
    {
        String notification_id = "", log_uuid = "";
        Map<String, String> notif_release_status;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                notification_id = params[0];
                log_uuid = params[1];
                notif_release_status = Notification_Release.Get_Notif_Release_Data(context,params[0]);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_release_status != null && !notif_release_status.equals(""))
            {
                if(notif_release_status.get("response_status").equalsIgnoreCase("success"))
                {
                    String aufnr = notif_release_status.get("response_data");
                    new Get_DORD_Data().execute(aufnr, notification_id, log_uuid);
                }
                else if(notif_release_status.get("response_status").contains("already released"))
                {
                }
                else if(notif_release_status.get("response_status").startsWith("E"))
                {
                }
                else
                {
                }
            }
            else
            {
            }
        }
    }
    /*Posting Notification Release to Backend Server*/


    /*Getting Order Data After Notification Release*/
    private class Get_DORD_Data extends AsyncTask<String, Integer, Void>
    {
        String notification_id = "", aufnr = "", log_uuid = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                aufnr = params[0];
                notification_id = params[1];
                log_uuid = params[2];
                String DORD_Status = Orders.Get_DORD_Data(context,"Single_Ord",aufnr);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            ContentValues cv = new ContentValues();
            cv.put("STATUS","Success");
            FieldTekPro_db.update("Alert_Log", cv, "LOG_UUID = ?", new String[]{log_uuid});
            local_pushMessage.send_local_pushmessage(context,"Notification "+notification_id+" is released successfully."+"\n"+"Order "+aufnr+" has been created successfully.");
        }
    }
    /*Getting Order Data After Notification Release*/

}
