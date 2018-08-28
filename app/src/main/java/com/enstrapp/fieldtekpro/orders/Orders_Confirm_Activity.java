package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Orders_Confirm_Activity extends AppCompatActivity {

    TextView orderStatus_tv, orderNo_tv, oprtnStatus_tv, oprtnNo_tv, start_tv, pause_tv, complete_tv;
    TextInputEditText oprtnShrtTxt_tiet, oprtnLngTxt_tiet, ctrlKey_tiet, plant_tiet, wrkCntr_tiet,
            actvyKey_tiet, plannedWrkUnt_tiet, cnfrmWrkUnt_tiet;
    Toolbar toolBar;
    ProgressBar progressBar;
    OrdrOprtnPrcbl oop;
    LinearLayout footer_ll;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Error_Dialog errorDialog = new Error_Dialog();
    String strDt = "", endDt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm_activity);

        DATABASE_NAME = Orders_Confirm_Activity.this.getString(R.string.database_name);
        App_db = Orders_Confirm_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        progressBar = findViewById(R.id.progressBar);
        footer_ll = findViewById(R.id.footer_ll);
        orderStatus_tv = findViewById(R.id.orderStatus_tv);
        orderNo_tv = findViewById(R.id.orderNo_tv);
        oprtnStatus_tv = findViewById(R.id.oprtnStatus_tv);
        oprtnNo_tv = findViewById(R.id.oprtnNo_tv);
        oprtnShrtTxt_tiet = findViewById(R.id.oprtnShrtTxt_tiet);
        oprtnLngTxt_tiet = findViewById(R.id.oprtnLngTxt_tiet);
        ctrlKey_tiet = findViewById(R.id.ctrlKey_tiet);
        plant_tiet = findViewById(R.id.plant_tiet);
        wrkCntr_tiet = findViewById(R.id.wrkCntr_tiet);
        actvyKey_tiet = findViewById(R.id.actvyKey_tiet);
        plannedWrkUnt_tiet = findViewById(R.id.plannedWrkUnt_tiet);
        cnfrmWrkUnt_tiet = findViewById(R.id.cnfrmWrkUnt_tiet);
        toolBar = findViewById(R.id.toolBar);
        start_tv = findViewById(R.id.start_tv);
        pause_tv = findViewById(R.id.pause_tv);
        complete_tv = findViewById(R.id.complete_tv);

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            oop = bundle.getParcelable("cnfoprtn");
            strDt = bundle.getString("strDt");
            endDt = bundle.getString("endDt");
//            orderStatus_tv.setText();
            orderNo_tv.setText(oop.getOrdrId());
//            oprtnStatus_tv,
//            if (oop.aueru().equalsIgnoreCase("X"))
            oprtnNo_tv.setText(oop.getOprtnId());
            oprtnShrtTxt_tiet.setText(oop.getOprtnShrtTxt());
            oprtnLngTxt_tiet.setText(oop.getOprtnLngTxt());
            ctrlKey_tiet.setText(oop.getCntrlKeyId());
            plant_tiet.setText(oop.getPlantTxt());
            wrkCntr_tiet.setText(oop.getWrkCntrTxt());
            actvyKey_tiet.setText("");
            plannedWrkUnt_tiet.setText(oop.getDuration() + "/" + oop.getDurationUnit());
            cnfrmWrkUnt_tiet.setText(oop.getUsr01());
        }

        complete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (complete(oop.getOrdrId(), oop.getOprtnId())) {
                    Intent intent = new Intent(Orders_Confirm_Activity.this, Orders_ConfirmPartial_Activity.class);
                    intent.putExtra("time_confrm", oop);
                    intent.putExtra("strDt", strDt);
                    intent.putExtra("endDt", endDt);
                    startActivity(intent);
                } else {
                    errorDialog.show_error_dialog(Orders_Confirm_Activity.this, getString(R.string.oprtn_start));
                }
            }
        });

        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                start_tv.setVisibility(View.GONE);
                pause_tv.setVisibility(View.VISIBLE);
                //view_second.setBackground(getResources().getDrawable(R.drawable.new_icon1));
                oprtnStatus_tv.setText(getResources().getString(R.string.start));

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String startDate = sdf.format(cal.getTime());

                String sql = "Insert into ORDER_CONFIRMATION_TIMER (UUID, Order_No, Operation_ID, Timestamp, STATUS, seconds) values(?,?,?,?,?,?);";
                SQLiteStatement statement = App_db.compileStatement(sql);
                App_db.beginTransaction();
                UUID uniqueKey_uuid = UUID.randomUUID();
                statement.clearBindings();
                statement.bindString(1, uniqueKey_uuid.toString());
                statement.bindString(2, oop.getOrdrId());
                statement.bindString(3, oop.getOprtnId());
                statement.bindString(4, startDate);
                statement.bindString(5, "S");
                statement.bindString(6, "");
                statement.execute();
                App_db.setTransactionSuccessful();
                App_db.endTransaction();
            }
        });

        pause_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_tv.setVisibility(View.VISIBLE);
                pause_tv.setVisibility(View.GONE);
                //view_second.setBackground(getResources().getDrawable(R.drawable.pause_icon1));
                oprtnStatus_tv.setText(getResources().getString(R.string.pause));
                progressBar.setVisibility(View.GONE);

                String start_date = "";
                try {
                    Cursor cursor = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{oop.getOrdrId(), oop.getOprtnId()});
                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToNext();
                        start_date = cursor.getString(4);
                    } else {
                        cursor.close();
                    }
                } catch (Exception e) {
                }

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String pauseDate = sdf.format(cal.getTime());

                try {
                    String string1 = start_date;
                    Date time1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string1);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);

                    String string2 = pauseDate;
                    Date time2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string2);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);

                    Date x = calendar1.getTime();
                    Date xy = calendar2.getTime();
                    long diff = xy.getTime() - x.getTime();

                    double seconds = diff / 1000;

                    String sql = "Insert into ORDER_CONFIRMATION_TIMER (UUID, Order_No, Operation_ID, Timestamp, STATUS, seconds) values(?,?,?,?,?,?);";
                    SQLiteStatement statement = App_db.compileStatement(sql);
                    App_db.beginTransaction();
                    UUID uniqueKey_uuid = UUID.randomUUID();
                    statement.clearBindings();
                    statement.bindString(1, uniqueKey_uuid.toString());
                    statement.bindString(2, oop.getOrdrId());
                    statement.bindString(3, oop.getOprtnId());
                    statement.bindString(4, pauseDate);
                    statement.bindString(5, "P");
                    statement.bindString(6, seconds + "");
                    statement.execute();
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();

                    int total = 0;
                    try {
                        Cursor cursor = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{oop.getOrdrId(), oop.getOprtnId()});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    String sec = cursor.getString(6);
                                    if (sec != null && !sec.equals("")) {
                                        total = (int) (total + cursor.getLong(6));
                                    }
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }

			    /*double minutes = seconds / 60;
                double hours = seconds / 3600;
			    double dayss = seconds / 86400;*/

                } catch (Exception e) {
                }
            }
        });


        if (orderRel(oop.getOrdrId())) {
            String auth_status = Authorizations.Get_Authorizations_Data(Orders_Confirm_Activity.this, "W", "TK");
            if (auth_status.equalsIgnoreCase("true")) {
                footer_ll.setVisibility(View.VISIBLE);
            } else {
                footer_ll.setVisibility(View.GONE);
            }
        } else {
            footer_ll.setVisibility(View.GONE);
        }


        if (oprtnWrkStatus(oop.getOrdrId(), oop.getOprtnId()).equals("P")) {
            start_tv.setVisibility(View.VISIBLE);
            pause_tv.setVisibility(View.GONE);
            oprtnStatus_tv.setText(getResources().getString(R.string.pause));
            progressBar.setVisibility(View.GONE);
        } else if (oprtnWrkStatus(oop.getOrdrId(), oop.getOprtnId()).equals("S")) {
            progressBar.setVisibility(View.VISIBLE);
            start_tv.setVisibility(View.GONE);
            pause_tv.setVisibility(View.VISIBLE);
            oprtnStatus_tv.setText(getResources().getString(R.string.start));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean orderRel(String ordrId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?",
                    new String[]{ordrId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(39).equalsIgnoreCase("REL"))
                            return true;
                        else {
                            cursor.close();
                            Cursor cursor1 = null;
                            try {
                                cursor1 = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?",
                                        new String[]{ordrId});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            if (cursor1.getString(39).equalsIgnoreCase("CNF"))
                                                return false;
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            }catch (Exception e){

                            }
                        }
                    } while (cursor.moveToNext());
                }
            }
        } catch (
                Exception e)

        {
            if (cursor != null)
                cursor.close();
        } finally

        {
            if (cursor != null)
                cursor.close();
        }
        return false;
    }

    private String oprtnWrkStatus(String ordrId, String oprtnId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ?" +
                    " and Operation_ID = ?" + " order by id desc", new String[]{ordrId, oprtnId});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                return cursor.getString(5);
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

    private boolean complete(String orderId, String operationId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{orderId, operationId});
            if (cursor != null && cursor.getCount() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}
