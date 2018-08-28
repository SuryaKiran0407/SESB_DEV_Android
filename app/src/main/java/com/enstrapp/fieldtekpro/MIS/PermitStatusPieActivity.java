package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enstrapp on 28/02/18.
 */

public class PermitStatusPieActivity extends AppCompatActivity {

    String total = "", plant_name = "", iwerk = "", wrkcnt = "", wrkcnt_name = "",
            plant_name_t = "", iwerk_t = "", wrkcnt_t = "", wrkcnt_name_t = "", wrkcnt_w = "", wrkcnt_name_w = "", iwerk_tp = "", plant_name_tp = "";
    int values_count = 0;
    Toolbar toolbar;
    TextView header_tv, no_data;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    PieDataSet DataSet;
    ListView listView;
    ListView label_list1;
    String Iwerk_plant = "", Name_plant = "", plant = "", value = "", workcenter = "", application = "", header = "";
    int[] mycolors1 = {Color.rgb(153, 0, 153), Color.rgb(255, 215, 0), Color.rgb(0, 128, 255), Color.rgb(0, 128, 0), Color.rgb(255, 127, 80), Color.rgb(0, 186, 0), Color.rgb(255, 0, 0)};
    ArrayList<String> wapnr_list = new ArrayList<>();
    TextView total_record, no_data_textview;
    public static final ArrayList<Mis_EtPermitWa_Object> EtPermitWa_f = new ArrayList<>();
    public static final ArrayList<Mis_EtPermitWa_Object> EtPermitWa = new ArrayList<Mis_EtPermitWa_Object>();
    public static final ArrayList<Mis_EtPermitWa_Object> EtPermitWa_wa = new ArrayList<Mis_EtPermitWa_Object>();
    public static final ArrayList<Mis_EtPermitWa_Object> EtPermitWa_c = new ArrayList<Mis_EtPermitWa_Object>();
    public static final ArrayList<Mis_EtPermitWd_Object> EtPermitWd = new ArrayList<Mis_EtPermitWd_Object>();
    public static final ArrayList<Mis_EtPermitWw_Object> EtPermitWw = new ArrayList<Mis_EtPermitWw_Object>();
    ArrayList<String> wapinr_list_c = new ArrayList<>();
    ArrayList<String> wapinr_list = new ArrayList<>();
    HashMap<String, String> ObjDesc = new HashMap<String, String>();
    ArrayList<Label_List_Object> llo = new ArrayList<Label_List_Object>();
    String plant_name1 = "";
    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ImageButton back_ib;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_permitanalysis_type_activity);

//        toolbar = findViewById(R.id.toolbar);
        no_data = findViewById(R.id.no_data_textview);
        header_tv = findViewById(R.id.header);
        pieChart = findViewById(R.id.pieChart);
        label_list1 = findViewById(R.id.label_list);
        back_ib = findViewById(R.id.back_ib);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        Intent intent = getIntent();
        value = intent.getExtras().getString("value");
        Iwerk_plant = intent.getExtras().getString("Iwerk_plant");
        Name_plant = intent.getExtras().getString("Name_plant");
        workcenter = intent.getExtras().getString("workcenter");
        application = intent.getExtras().getString("application");
        plant = intent.getExtras().getString("wrk_name");
        if(value.equals("Crea"))
        {
            header="Created" + " Permits";
        }
        else if(value.equals("Prep"))
        {
            header="Prepared"+" Permits";
        }
        else if(value.equals("Clsd"))
        {
            header="Closed"+" Permits";
        }
        else if(value.equals("Reje"))
        {
            header="Rejected"+" Permits";
        }
        else if(value.equals("Red"))
        {
            header="Not Issued"+" Permits";
        }
        else if(value.equals("Yellow"))
        {
            header="Partially Issued"+" Permits";
        }
        else if(value.equals("Green"))
        {
            header="Issued"+" Permits";
        }

        header_tv.setText(header);

        Log.v("hi", "" + value);
        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtPermitWw", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    plant = cursor.getString(1);
                }
            } else {
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }

        if (!plant.equals("")) {
            try {
                ObjDesc.clear();
                cursor = FieldTekPro_db.rawQuery("select * from EtWcmTypes where Iwerk = ?", new String[]{plant});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            ObjDesc.put(cursor.getString(3), cursor.getString(4));
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                }
            } catch (Exception e) {
            } finally {
                cursor.close();
            }
        }

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        new Get_PermitWA_Data().execute();

    }

    private class Get_PermitWA_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(PermitStatusPieActivity.this,getResources().getString(R.string.loading));
            EtPermitWa.clear();
            EtPermitWa_c.clear();
            EtPermitWd.clear();
            EtPermitWw.clear();
            EtPermitWa_wa.clear();
            EtPermitWa_f.clear();
            wapinr_list.clear();
            wapinr_list_c.clear();
            wapnr_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor1 = null;
            try {
                if (!Iwerk_plant.equals("")) {
                    if (!workcenter.equals("")) {
                        if (value.equalsIgnoreCase("Crea")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Crea = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Prep")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Prep = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Clsd")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Clsd = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Reje")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Reje = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Red")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Red = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Yellow")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Yellow = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else if (value.equalsIgnoreCase("Green")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Green = 'X' and Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        }
                    } else {
                        if (value.equalsIgnoreCase("Crea")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Crea = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Prep")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Prep = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Clsd")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Clsd = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Reje")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Reje = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Red")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Red = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Yellow")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Yellow = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        } else if (value.equalsIgnoreCase("Green")) {
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Green = 'X' and Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    }
                } else {
                    if (value.equalsIgnoreCase("Crea")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Crea = 'X'", null);
                    } else if (value.equalsIgnoreCase("Prep")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Prep = 'X'", null);
                    } else if (value.equalsIgnoreCase("Clsd")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Clsd = 'X'", null);
                    } else if (value.equalsIgnoreCase("Reje")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Reje = 'X'", null);
                    } else if (value.equalsIgnoreCase("Red")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Red = 'X'", null);
                    } else if (value.equalsIgnoreCase("Yellow")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Yellow = 'X'", null);
                    } else if (value.equalsIgnoreCase("Green")) {
                        cursor1 = FieldTekPro_db.rawQuery("select * from EtPermitWa where Green = 'X'", null);
                    }
                }
                if (cursor1 != null && cursor1.getCount() > 0) {
                    if (cursor1.moveToFirst()) {
                        do {
                            String objtyp = cursor1.getString(13);
                            if (objtyp.equals("1")) {
                                wapinr_list.add(cursor1.getString(11));
                            }
                            Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                            bean.setIwerk(cursor1.getString(1));
                            bean.setArbpl(cursor1.getString(16));
                            bean.setCrea(cursor1.getString(2));
                            bean.setPrep(cursor1.getString(3));
                            bean.setClsd(cursor1.getString(4));
                            bean.setReje(cursor1.getString(5));
                            bean.setRed(cursor1.getString(6));
                            bean.setYellow(cursor1.getString(7));
                            bean.setGreen(cursor1.getString(8));
                            bean.setWapnr(cursor1.getString(10));
                            bean.setWapinr(cursor1.getString(11));
                            bean.setObjtyp(cursor1.getString(13));
                            bean.setObjnr(cursor1.getString(14));
                            bean.setRefobj(cursor1.getString(25));
                            EtPermitWa.add(bean);
                        }
                        while (cursor1.moveToNext());
                    }
                } else {
                }

                /*if (cursor1 != null && cursor1.getCount() > 0) {
                    if (cursor1.moveToFirst()) {
                        do {
                            wapnr_list.add(cursor1.getString(11));
                        }
                        while (cursor1.moveToNext());
                    }
                } else {
                }*/
            } catch (Exception e) {
                Log.v("kiran_ee123",e.getMessage()+"...");
            } finally {
                cursor1.close();
            }
            /*if (value.equalsIgnoreCase("Red")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Yellow")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Green")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Crea")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Prep")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Clsd")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            } else if (value.equalsIgnoreCase("Reje")) {
                Cursor cursor = null;
                try {
                    if (!Iwerk_plant.equals("")) {
                        if (!workcenter.equals("")) {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{Iwerk_plant, workcenter});
                        } else {
                            cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa where Iwerk = ?", new String[]{Iwerk_plant});
                        }
                    } else {
                        cursor = FieldTekPro_db.rawQuery("select * from  EtPermitWa", null);
                    }
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String objtyp = cursor.getString(13);
                                if (objtyp.equals("1")) {
                                    wapinr_list.add(cursor.getString(11));
                                }
                                Mis_EtPermitWa_Object bean = new Mis_EtPermitWa_Object();
                                bean.setIwerk(cursor.getString(1));
                                bean.setArbpl(cursor.getString(16));
                                bean.setCrea(cursor.getString(2));
                                bean.setPrep(cursor.getString(3));
                                bean.setClsd(cursor.getString(4));
                                bean.setReje(cursor.getString(5));
                                bean.setRed(cursor.getString(6));
                                bean.setYellow(cursor.getString(7));
                                bean.setGreen(cursor.getString(8));
                                bean.setWapnr(cursor.getString(10));
                                bean.setWapinr(cursor.getString(11));
                                bean.setObjtyp(cursor.getString(13));
                                bean.setObjnr(cursor.getString(14));
                                bean.setRefobj(cursor.getString(25));
                                EtPermitWa.add(bean);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                    }
                } catch (Exception e) {
                } finally {
                    cursor.close();
                }
            }*/

            EtPermitWa_wa.addAll(EtPermitWa);
            EtPermitWa_c.addAll(EtPermitWa);

            /*for (Mis_EtPermitWa_Object bean : EtPermitWa) {
                *//*String wapnr = bean.getWapnr();
                Log.v("permit_wapnr", "" + wapnr + "...." + wapnr_list.size());*//*
                for (int i = 0; i < wapnr_list.size(); i++) {
//                    if (wapnr_list.get(i).equals(wapnr)) {
                        Mis_EtPermitWa_Object bean1 = new Mis_EtPermitWa_Object();
                        bean1.setIwerk(bean.getIwerk());
                        bean1.setArbpl(bean.getArbpl());
                        bean1.setCrea(bean.getCrea());
                        bean1.setPrep(bean.getPrep());
                        bean1.setClsd(bean.getClsd());
                        bean1.setReje(bean.getReje());
                        bean1.setRed(bean.getRed());
                        bean1.setYellow(bean.getYellow());
                        bean1.setGreen(bean.getGreen());
                        bean1.setWapnr(bean.getWapnr());
                        bean1.setWapinr(bean.getWapinr());
                        bean1.setObjtyp(bean.getObjtyp());
                        bean1.setObjnr(bean.getObjnr());
                        EtPermitWa_wa.add(bean1);
                        EtPermitWa_c.add(bean1);
//                    }
                }
            }*/
            for (Mis_EtPermitWa_Object bean : EtPermitWa_wa) {
                values_count = 0;
                String obj_type = bean.getObjtyp();
                if (EtPermitWa_f.size() > 0) {
                    if (contains(EtPermitWa_f, obj_type)) {

                    } else {
                        for (int j = 0; j < EtPermitWa_c.size(); j++) {
                            if (EtPermitWa_c.get(j).getObjtyp().equals(obj_type)) {
                                values_count = values_count + 1;
                            }
                        }
                        Mis_EtPermitWa_Object pc = new Mis_EtPermitWa_Object();
                        pc.setCount(String.valueOf(values_count));
                        pc.setObjtyp(obj_type);
                        if (ObjDesc.size() != 0) {
                            for (Map.Entry<String, String> entry : ObjDesc.entrySet()) {
                                if (entry.getKey().equals(obj_type)) {
                                    pc.setObjtyp_txt(entry.getValue());
                                }
                            }
                        }
                        EtPermitWa_f.add(pc);
                    }
                } else {
                    for (int j = 0; j < EtPermitWa_c.size(); j++) {
                        if (EtPermitWa_c.get(j).getObjtyp().equals(obj_type)) {
                            values_count = values_count + 1;
                        }
                    }
                    Mis_EtPermitWa_Object pc = new Mis_EtPermitWa_Object();
                    pc.setCount(String.valueOf(values_count));
                    pc.setObjtyp(obj_type);
                    if (ObjDesc.size() > 0) {
                        for (Map.Entry<String, String> entry : ObjDesc.entrySet()) {
                            if (entry.getKey().equals(obj_type)) {
                                pc.setObjtyp_txt(entry.getValue());
                            }
                        }
                    }
                    EtPermitWa_f.add(pc);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // total_record.setText("Total Count : " + EtPermitWa_wa.size());

            ArrayList<String> permit_wd1 = new ArrayList<String>();

            Cursor cruze = null;
            try {
                cruze = FieldTekPro_db.rawQuery("select * from  EtPermitWd", null);
                if (cruze != null && cruze.getCount() > 0) {
                    if (cruze.moveToFirst()) {
                        do {
                            permit_wd1.add(cruze.getString(12));
                        }
                        while (cruze.moveToNext());
                    }
                } else {
                }
            } catch (Exception e) {

            } finally {
                cruze.close();
            }

            for (int i = 0; i < EtPermitWa_wa.size(); i++) {
                if (EtPermitWa_wa.get(i).getObjtyp().equals("1")) {
                    wapinr_list_c.add(EtPermitWa_wa.get(i).getWapinr());
                }
            }

            for (int i = 0; i < wapinr_list_c.size(); i++) {
                for (int j = 0; j < permit_wd1.size(); j++) {
                    if (wapinr_list_c.get(i).equals(permit_wd1.get(j))) {
                    }
                }
            }

            if (EtPermitWa_f.size() > 0) {
                llo.clear();
                final List<PieEntry> entries = new ArrayList<>();
                for (Mis_EtPermitWa_Object pwcount : EtPermitWa_f) {
                    entries.add(new PieEntry(Float.parseFloat(pwcount.getCount()), pwcount.getObjtyp_txt() + " (" + pwcount.getCount() + ")"));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setobjtyp(pwcount.getObjtyp());
                    llo_o.setLabel(pwcount.getObjtyp_txt() + " (" + pwcount.getCount() + ")");
                    llo.add(llo_o);
                }

                DataSet = new PieDataSet(entries, "");

                for (int j = 0; j < entries.size(); j++) {
                    llo.get(j).setColor(mycolors1[j]);
                }

                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : mycolors1) colors.add(c);

                DataSet.setColors(colors);
                DataSet.setSliceSpace(2f);
                DataSet.setValueLinePart1OffsetPercentage(80.f);
                DataSet.setSelectionShift(5f);
                DataSet.setValueLinePart1Length(0.2f);
                DataSet.setValueLinePart2Length(0.4f);
                DataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                DataSet.setValueFormatter(new MyValueFormatter());

                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        int index = (int) h.getX();
                        String label = EtPermitWa_f.get(index).getObjtyp();
                        String Label_name = EtPermitWa_f.get(index).getObjtyp_txt();
                        if (label.equals("1")) {
                            Intent intent = new Intent(PermitStatusPieActivity.this, PermitTypeListActivity.class);
                            intent.putStringArrayListExtra("wapinr", wapinr_list_c);
                            intent.putExtra("name", Label_name);
                            intent.putExtra("value", value);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onNothingSelected() {
                    }
                });

                Legend legend = pieChart.getLegend();
                legend.setTextSize(14);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setWordWrapEnabled(true);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setEnabled(false);

                pieData = new PieData(DataSet);
                pieData.setValueTextSize(14);
                pieChart.setUsePercentValues(false);

                Label_Adapter ll_a = new Label_Adapter(PermitStatusPieActivity.this, llo);
                label_list1.setAdapter(ll_a);

                pieChart.setExtraOffsets(5, 5, 5, 5);
                pieChart.highlightValues(null);
                pieChart.animateY(1000);
                pieChart.setScrollContainer(true);
                pieChart.setVerticalScrollBarEnabled(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setRotationEnabled(true);
                pieChart.setDrawEntryLabels(false);
                pieChart.setDragDecelerationFrictionCoef(0.95f);
                if (!Iwerk_plant.equals("") && workcenter.equals("")) {
                    pieChart.setCenterText(Name_plant);
                } else if (!Iwerk_plant.equals("") && !workcenter.equals("")) {
                    pieChart.setCenterText(Name_plant + " " + plant_name1);
                } else {
                    pieChart.setCenterText("Permits");
                }
                pieChart.setHoleColor(Color.LTGRAY);
                pieChart.setTransparentCircleColor(Color.LTGRAY);
                pieChart.setCenterTextSize(14);
                pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                pieChart.invalidate();// for refreshing the chart
                pieChart.setData(pieData);

            } else {
                pieChart.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    private class Label_Adapter extends ArrayAdapter<Label_List_Object> {
        Context mContext;
        Label_List_Object llo_o;

        public Label_Adapter(Activity activity, ArrayList<Label_List_Object> label_list_objects) {
            super(activity, 0, label_list_objects);
            mContext = activity;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View listV = convertView;

            if (listV == null) {
                listV = LayoutInflater.from(getContext()).inflate(R.layout.label_list_adapter, parent, false);
            }
            llo_o = getItem(position);

            ImageView label_color = (ImageView) listV.findViewById(R.id.label_color);
            label_color.setBackgroundColor(llo_o.getColor());

            final TextView label_name = (TextView) listV.findViewById(R.id.label_name);
            label_name.setText(llo_o.getLabel());

            label_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (llo.get(position).getobjtyp().equals("1")) {
                        String Label_name = EtPermitWa_f.get(position).getObjtyp_txt();
                        Intent intent = new Intent(PermitStatusPieActivity.this, PermitTypeListActivity.class);
                        intent.putStringArrayListExtra("wapinr", wapinr_list_c);
                        intent.putExtra("name", Label_name);
                        startActivity(intent);
                    }
                }
            });

            return listV;
        }
    }


        private static boolean contains(final List<Mis_EtPermitWa_Object> transaction, final String search) {
            for (final Mis_EtPermitWa_Object transactionLine : transaction) {
                if (transactionLine.getObjtyp().equals(search)) {
                    return true;
                }
            }
            return false;
        }

}




