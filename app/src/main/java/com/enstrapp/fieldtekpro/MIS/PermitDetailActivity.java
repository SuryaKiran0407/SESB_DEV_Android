package com.enstrapp.fieldtekpro.MIS;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by RAKSHITHA V SUVARNA on 20-03-2018.
 */

public class PermitDetailActivity extends AppCompatActivity {

    TextView wcnr_id, name, order, function_location, date_from, date_to, valid_frm_time, to_time, priority;
    TextView Equnr, equip_text;
    SQLiteDatabase FieldTekPro_db;
    ImageButton back_ib;
    private static String DATABASE_NAME = "";
    ArrayList<HashMap<String, String>> priority_dropdown_list = new ArrayList<HashMap<String, String>>();
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_permit_data_details);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        back_ib = findViewById(R.id.back_ib);
        wcnr_id = (TextView) findViewById(R.id.wcnr_id);
        name = (TextView) findViewById(R.id.name);
        order = (TextView) findViewById(R.id.order);
        function_location = (TextView) findViewById(R.id.function_location);
        date_from = (TextView) findViewById(R.id.date_from);
        date_to = (TextView) findViewById(R.id.date_to);
        valid_frm_time = (TextView) findViewById(R.id.valid_frm_time);
        to_time = (TextView) findViewById(R.id.to_time);
        priority = (TextView) findViewById(R.id.priority);
//        toolbar = findViewById(R.id.toolbar);
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_NOTIFICATION_PRIORITY", null);
            Log.v("priok_count", "" + cursor.getCount());
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Priok", cursor.getString(1));
                        map.put("Priokx", cursor.getString(2));
                        Log.v("priok_db", "" + cursor.getString(1) + "...." + cursor.getString(2));
                        priority_dropdown_list.add(map);

                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            wcnr_id.setText(extras
                    .getString("wcnr_id"));
            name.setText(extras
                    .getString("name"));
            order.setText(extras
                    .getString("order"));
            function_location.setText(extras
                    .getString("f_loc"));
            date_from.setText(dateDisplayFormat(extras
                    .getString("date_from")));
            date_to.setText(dateDisplayFormat(extras
                    .getString("date_to")));//valid_to_date
            valid_frm_time.setText(timeDisplayFormat(extras
                    .getString("valid_from_time")));//to_time
            to_time.setText(timeDisplayFormat(extras
                    .getString("to_time")));
            String priok = extras
                    .getString("priority");
            for (int i = 0; i < priority_dropdown_list.size(); i++) {
                if (priok.equals("") || priok == null) {
                    priority.setText("");
                } else if (priok.equals(priority_dropdown_list.get(i).get("Priok"))) {
                    priority.setText(priority_dropdown_list.get(i).get("Priokx"));
                }
            }
            /*Equnr.setText(extras
                    .getString("eq_descr"));
            equip_text.setText(extras
                    .getString("eq_txt"));*/
        }
        // back_imageview.setOnClickListener(this);
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_NOTIFICATION_PRIORITY", null);
            Log.v("priok_count", "" + cursor.getCount());
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("Priok", cursor.getString(1));
                        map.put("Priokx", cursor.getString(2));
                        priority_dropdown_list.add(map);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }




       /* if (!strmn_.equals("") || strmn_ != null || !strmn_.equals("null"))

        {
            strmn.setText(formatDate(strmn_));
        }
        if (!ltrmn_.equals("") || ltrmn_ != null || !ltrmn_.equals("null"))

        {
            ltrmn.setText(formatDate(ltrmn_));
        }

        auswk.setText(auswk_);
        tplnr.setText(tplnr_);*/


    }


    private String dateDisplayFormat(String date) {
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
    }

    private String timeDisplayFormat(String date) {
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

}
