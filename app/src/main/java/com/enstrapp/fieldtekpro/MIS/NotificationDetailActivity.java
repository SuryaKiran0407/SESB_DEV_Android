package com.enstrapp.fieldtekpro.MIS;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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



public class NotificationDetailActivity extends AppCompatActivity {

    TextView phase, swerk, arbpl, qmart, qmnum, qmtxt, ernam, equnr, eqktx, priok, strmn, ltrmn, auswk, tplnr, ausvn, ausbs;
    String phase_ = "", swerk_ = "", arbpl_ = "", qmart_ = "", qmnum_ = "", qmtxt_ = "", ernam_ = "", equnr_ = "", eqktx_ = "",
            priok_ = "", strmn_ = "", ltrmn_ = "", auswk_ = "", tplnr_ = "", ausvn_ = "", ausbs_ = "", msaus_ = "", wrkcnt = "",
            heading = "";
    CheckBox msaus;

    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ArrayList<HashMap<String, String>> priority_dropdown_list = new ArrayList<HashMap<String, String>>();
    ImageButton back_ib;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_notif_data_details);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        back_ib = findViewById(R.id.back_ib);
        phase = findViewById(R.id.phase);
        swerk = findViewById(R.id.swerk);
        arbpl = findViewById(R.id.arbpl);
        qmart = findViewById(R.id.qmart);
        qmnum = findViewById(R.id.qmnum);
        qmtxt = findViewById(R.id.qmtxt);
        ernam = findViewById(R.id.ernam);
        equnr = findViewById(R.id.equnr);
        eqktx = findViewById(R.id.eqktx);
        priok = findViewById(R.id.priok);
        strmn = findViewById(R.id.strmn);
        ltrmn = findViewById(R.id.ltrmn);
        auswk = findViewById(R.id.auswk);
        tplnr = findViewById(R.id.tplnr);
        msaus = findViewById(R.id.msaus);
        ausvn = findViewById(R.id.ausvn);
        ausbs = findViewById(R.id.ausbs);



        Intent intent = getIntent();
        phase_ = intent.getExtras().getString("phase");
        swerk_ = intent.getExtras().getString("iwerk");
        wrkcnt = intent.getExtras().getString("wrkcnt");
        qmart_ = intent.getExtras().getString("qmart");
        qmnum_ = intent.getExtras().getString("qmnum");
        qmtxt_ = intent.getExtras().getString("qmtxt");
        ernam_ = intent.getExtras().getString("ernam");
        equnr_ = intent.getExtras().getString("equnr");
        eqktx_ = intent.getExtras().getString("eqktx");
        priok_ = intent.getExtras().getString("priok");
        strmn_ = intent.getExtras().getString("strmn");
        ltrmn_ = intent.getExtras().getString("ltrmn");
        auswk_ = intent.getExtras().getString("auswk");
        tplnr_ = intent.getExtras().getString("tplnr");
        msaus_ = intent.getExtras().getString("msaus");
        ausvn_ = intent.getExtras().getString("ausvn");
        ausbs_ = intent.getExtras().getString("ausbs");
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
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String notif = heading;
        String[] noty = heading.split("\\(");
        String qmart_h = noty[0];

        Log.v("priok", "" + priok_);
        if (!phase_.equals("") || phase_ != null || !phase_.equals("null")) {
            if (phase_.equals("1")) {
                phase.setText("Outstanding");
            } else if (phase_.equals("3")) {
                phase.setText("Inprogress");
            } else if (phase_.equals("4")) {
                phase.setText("Completed");
            } else {
                phase.setText("");
            }
        }

        swerk.setText(swerk_);
        arbpl.setText(wrkcnt);
        qmart.setText(qmart_h);
        qmnum.setText(qmnum_);
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

        auswk.setText(auswk_);
        tplnr.setText(tplnr_);

        if (msaus_ != null || !msaus_.equals("") || !msaus_.equals("null"))

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
        }
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
                Log.v("Notif_data_detail_date", "" + e.getMessage());
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
