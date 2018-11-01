package com.enstrapp.fieldtekpro.MIS;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class OrderDetailActivity extends AppCompatActivity {

    TextView phase, swerk, arbpl, aufnr, qmart, qmnum, qmtxt, ernam, equnr, eqktx, priok, strmn, ltrmn, auswk, tplnr, ausvn, ausbs;
    String phase_ = "", swerk_ = "", arbpl_ = "", qmart_ = "", qmnum_ = "", qmtxt_ = "", ernam_ = "", equnr_ = "", eqktx_ = "", aufnr_ = "",
            priok_ = "", strmn_ = "", ltrmn_ = "", auswk_ = "", tplnr_ = "", ausvn_ = "", ausbs_ = "", msaus_ = "", wrkcnt = "",
            heading = "";
    CheckBox msaus;

    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ArrayList<HashMap<String, String>> priority_dropdown_list = new ArrayList<HashMap<String, String>>();
    Toolbar toolbar;
    ImageButton back_ib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_order_data_details);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        back_ib = findViewById(R.id.back_ib);
        aufnr = findViewById(R.id.qmnum);
        equnr = findViewById(R.id.equnr);
        eqktx = findViewById(R.id.eqktx);
        qmtxt = findViewById(R.id.qmtxt);
        swerk = findViewById(R.id.swerk);
        arbpl = findViewById(R.id.arbpl);
        phase = findViewById(R.id.phase);
        qmart = findViewById(R.id.qmart);
        ernam = findViewById(R.id.ernam);
        strmn = findViewById(R.id.strmn);
        ltrmn = findViewById(R.id.ltrmn);
        tplnr = findViewById(R.id.tplnr);
        priok = findViewById(R.id.priok);

        Intent intent = getIntent();
        aufnr_ = intent.getExtras().getString("Aufnr");
        equnr_ = intent.getExtras().getString("Equnr");
        eqktx_ = intent.getExtras().getString("Eqktx");
        qmtxt_ = intent.getExtras().getString("Ktext");
        swerk_ = intent.getExtras().getString("iwerk");
        arbpl_ = intent.getExtras().getString("Arbpl");
        phase_ = intent.getExtras().getString("phase");
        qmart_ = intent.getExtras().getString("Auart");
        ernam_ = intent.getExtras().getString("Ernam");
        strmn_ = intent.getExtras().getString("Gstrp");
        ltrmn_ = intent.getExtras().getString("Gltrp");
        tplnr_ = intent.getExtras().getString("Tplnr");
        priok_ = intent.getExtras().getString("Priok");

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        heading = intent.getExtras().getString("heading");

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

        String notif = heading;
        String[] noty = heading.split("\\(");
        String qmart_h = noty[0];

        Log.v("priok", "" + priok_);
        if (!phase_.equals("") || phase_ != null || !phase_.equals("null")) {
            if (phase_.equals("0")) {
                phase.setText(getString(R.string.outstanding));
            } else if (phase_.equals("2")) {
                phase.setText(getString(R.string.inprgs));
            } else if (phase_.equals("3")) {
                phase.setText(getString(R.string.compltd));
            } else {
                phase.setText("");
            }
        }

        swerk.setText(swerk_);
        arbpl.setText(arbpl_);
        qmart.setText(qmart_h);
        aufnr.setText(aufnr_);
        qmtxt.setText(qmtxt_);
        ernam.setText(ernam_);
        equnr.setText(equnr_);
        eqktx.setText(eqktx_);


        for (int i = 0; i < priority_dropdown_list.size(); i++) {
            if (priok_.equals("") || priok_ == null) {
                priok.setText("");
            } else if (priok_.equals(priority_dropdown_list.get(i).get("Priok"))) {
                priok.setText(priority_dropdown_list.get(i).get("Priokx"));
            }
        }


        if (!strmn_.equals("") || strmn_ != null || !strmn_.equals("null"))

        {
            strmn.setText(formatDate(strmn_));
        }
        if (!ltrmn_.equals("") || ltrmn_ != null || !ltrmn_.equals("null"))

        {
            ltrmn.setText(formatDate(ltrmn_));
        }

//        auswk.setText(auswk_);
        tplnr.setText(tplnr_);

        /*if (msaus_ != null || !msaus_.equals("") || !msaus_.equals("null"))

        {
            if (msaus_.equals("X")) {
                msaus.setChecked(true);
            } else {
                msaus.setChecked(false);
            }
        } else

        {
            msaus.setChecked(false);
        }

        if (!ausvn_.equals("") || ausvn_ != null || !ausvn_.equals("null"))

        {
            ausvn.setText(formatDate(ausvn_));
        }

        if (!ausbs_.equals("") || ausbs_ != null || !ausbs_.equals("null"))

        {
            ausbs.setText(formatDate(ausbs_));
        }*/
    }

    private String formatDate(String date) {
        String datee = "";
        if (date.equals("0000-00-00") || date.equals("")) {

        } else {
            String inputPattern = "yyyyMMdd";
            String outputPattern = "MMM dd, yyyy";

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date dates = inputFormat.parse(date);
                datee = outputFormat.format(dates);
            } catch (ParseException e) {
                Log.v("Order_data_detail_date", "" + e.getMessage());
            }
            return datee;
        }
        return datee;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
