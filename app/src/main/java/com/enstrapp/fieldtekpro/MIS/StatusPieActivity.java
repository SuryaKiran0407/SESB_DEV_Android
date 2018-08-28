package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatusPieActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView header_tv, no_data;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ListView listView;
    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ProgressDialog progressdialog;
    int total;
    boolean plant_all = false;

    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    ArrayList<Label_List_Object> llo = new ArrayList<Label_List_Object>();

    ArrayList<Mis_EtNotifPlantTotal_Object> pt_o = new ArrayList<Mis_EtNotifPlantTotal_Object>();
    ArrayList<Mis_EtNotifTypeTotal_Object> tt_o = new ArrayList<Mis_EtNotifTypeTotal_Object>();
    ArrayList<Mis_EtNotifTypeTotal_Object> swerk_p = new ArrayList<Mis_EtNotifTypeTotal_Object>();
    ArrayList<Mis_EtNotifArbplTotal_Object> art_o = new ArrayList<Mis_EtNotifArbplTotal_Object>();
    ArrayList<Mis_EtNotifTypeTotal_Object> tt_fo = new ArrayList<Mis_EtNotifTypeTotal_Object>();
    ArrayList<Mis_EtNotifArbplTotal_Object> art_fo = new ArrayList<Mis_EtNotifArbplTotal_Object>();
    ArrayList<Mis_EtNotifTypeTotal_Object> filter = new ArrayList<Mis_EtNotifTypeTotal_Object>();

    ArrayList<Integer> Outs1 = new ArrayList<Integer>();
    ArrayList<Integer> Inpr1 = new ArrayList<Integer>();
    ArrayList<Integer> Comp1 = new ArrayList<Integer>();
    ImageButton back_ib;


    String wrkcnt = "", swerk = "", value = "", phase = "", header = "", plant_name = "", wrkcnt_name = "", label = "", part1 = "",
            swerk_txt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_notifanalysis_type_activity);

        no_data = findViewById(R.id.no_data_textview);
        header_tv = findViewById(R.id.header);
        pieChart = findViewById(R.id.pieChart);
        listView = findViewById(R.id.label_list);
        back_ib = findViewById(R.id.back_ib);


        Intent intent = getIntent();
        wrkcnt = intent.getExtras().getString("wrkcntr_s");
        swerk = intent.getExtras().getString("iwerk");
        value = intent.getExtras().getString("value");
        phase = intent.getExtras().getString("phase");
        header = value + " Notifications";
        header_tv.setText(header);
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Log.v("hi", "" + value);
        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Load_Notif_Reports();
    }

    private void Load_Notif_Reports() {

        progressdialog = new ProgressDialog(StatusPieActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(getResources().getString(
                R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();

        pt_o.clear();
        if (value.equals("") && value == null) {
            pieChart.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        } else {
            pieChart.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);

            ArrayList<Mis_EtNotifTypeTotal_Object> filter = new ArrayList<Mis_EtNotifTypeTotal_Object>();
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtNotifPlantTotal", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Mis_EtNotifPlantTotal_Object cp = new Mis_EtNotifPlantTotal_Object();
                            cp.setmTotal_pt(cursor.getString(1));
                            cp.setmSwerk_pt(cursor.getString(2));
                            cp.setmName_pt(cursor.getString(3));
                            cp.setmQmart_pt(cursor.getString(4));
                            cp.setmQmartx_pt(cursor.getString(5));
                            cp.setmOuts_pt(cursor.getString(6));
                            cp.setmInpr_pt(cursor.getString(7));
                            cp.setmComp_pt(cursor.getString(8));
                            cp.setmDele_pt(cursor.getString(9));
                            cp.setmChecked("n");
                            pt_o.add(cp);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
                Log.v("Notif_pie_plant_db", "" + e.getMessage());
                pieChart.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                progressdialog.dismiss();
            }

            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtNotifTypeTotal", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Mis_EtNotifTypeTotal_Object cp = new Mis_EtNotifTypeTotal_Object();
                            cp.setmTotal_tt(cursor.getString(1));
                            cp.setmSwerk_tt(cursor.getString(2));
                            cp.setmArbpl_tt(cursor.getString(3));
                            cp.setmQmart_tt(cursor.getString(4));
                            cp.setmQmartx_tt(cursor.getString(5));
                            cp.setmOuts_tt(cursor.getString(6));
                            cp.setmInpr_tt(cursor.getString(7));
                            cp.setmComp_tt(cursor.getString(8));
                            cp.setmDele_tt(cursor.getString(9));
                            tt_o.add(cp);
                            filter.add(cp);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
                Log.v("Notif_pie_typetotal_db", "" + e.getMessage());
                pieChart.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                progressdialog.dismiss();
            }

            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtNotifArbplTotal", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Mis_EtNotifArbplTotal_Object cp = new Mis_EtNotifArbplTotal_Object();
                            cp.setmTotal_art(cursor.getString(1));
                            cp.setmSwerk_art(cursor.getString(2));
                            cp.setmArbpl_art(cursor.getString(3));
                            cp.setmName_art(cursor.getString(4));
                            cp.setmQmart_art(cursor.getString(5));
                            cp.setmQmartx_art(cursor.getString(6));
                            cp.setmOuts_art(cursor.getString(7));
                            cp.setmInpr_art(cursor.getString(8));
                            cp.setmComp_art(cursor.getString(9));
                            cp.setmDele_art(cursor.getString(10));
                            cp.setmChecked("n");
                            art_o.add(cp);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
                Log.v("Notif_pie_workcnt_db", "" + e.getMessage());
                pieChart.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                progressdialog.dismiss();
            }

            if (swerk.equals("")) {

            } else {
                for (int i = 0; i < pt_o.size(); i++) {
                    if (pt_o.get(i).getmSwerk_pt().equals(swerk)) {
                        pt_o.get(i).setmChecked("y");
                        plant_name = pt_o.get(i).getmName_pt();
                    }
                }
            }

            if (wrkcnt.equals("")) {

            } else {
                for (int i = 0; i < art_o.size(); i++) {
                    if (art_o.get(i).getmArbpl_art().equals(wrkcnt)) {
                        art_o.get(i).setmChecked("y");
                        wrkcnt_name = art_o.get(i).getmName_art();
                    }
                }
            }

            if (swerk.equals("")) {
                try {
                    if (value.equals("Outstanding")) {
                        entries = new ArrayList<PieEntry>();
                        Outs1.clear();
                        swerk_p.clear();
                        llo.clear();
                        tt_fo.clear();

                        for (int i = 0; i < tt_o.size(); i++) {
                            String notif = tt_o.get(i).getmQmart_tt();
                            if (tt_fo.size() > 0) {
                                if (contains(tt_fo, notif)) {

                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmOuts_tt().equals("") && filter.get(j).getmOuts_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmOuts_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                    tt_cf_o.setmOuts_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            } else {
                                for (int j = 0; j < filter.size(); j++) {
                                    if (filter.get(j).getmQmart_tt().equals(notif)) {
                                        if (!filter.get(j).getmOuts_tt().equals("") && filter.get(j).getmOuts_tt() != null) {
                                            total = total + Integer.parseInt(filter.get(j).getmOuts_tt().trim());
                                        } else {
                                            total = total + 0;
                                        }
                                    }
                                }
                                Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                tt_cf_o.setmQmart_tt(notif);
                                tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                tt_cf_o.setmOuts_tt(String.valueOf(total));
                                tt_fo.add(tt_cf_o);
                                total = 0;
                            }
                        }


                        for (int j = 0; j < tt_fo.size(); j++) {
                            if (tt_fo.get(j).getmOuts_tt() != null && !tt_fo.get(j).getmOuts_tt().equals("")) {
                                Outs1.add(Integer.parseInt(tt_fo.get(j).getmOuts_tt().trim()));
                            } else {
                                Outs1.add(0);
                            }
                            if (Outs1.get(j) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Outs1.get(j), tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Outs1.get(j) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Outs1.get(j) + ")");
                                llo.add(llo_o);
                            }
                        }
                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        pieChart.setCenterText(header);
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.setMinOffset(10);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                    if (value.equals("Inprogress")) {
                        entries = new ArrayList<PieEntry>();
                        Inpr1.clear();
                        swerk_p.clear();
                        llo.clear();
                        tt_fo.clear();

                        for (int i = 0; i < tt_o.size(); i++) {
                            String notif = tt_o.get(i).getmQmart_tt();
                            if (tt_fo.size() > 0) {
                                if (contains(tt_fo, notif)) {

                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmInpr_tt().equals("") && filter.get(j).getmInpr_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmInpr_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                    tt_cf_o.setmInpr_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            } else {
                                for (int j = 0; j < filter.size(); j++) {
                                    if (filter.get(j).getmQmart_tt().equals(notif)) {
                                        if (!filter.get(j).getmInpr_tt().equals("") && filter.get(j).getmInpr_tt() != null) {
                                            total = total + Integer.parseInt(filter.get(j).getmInpr_tt().trim());
                                        } else {
                                            total = total + 0;
                                        }
                                    }
                                }
                                Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                tt_cf_o.setmQmart_tt(notif);
                                tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                tt_cf_o.setmInpr_tt(String.valueOf(total));
                                tt_fo.add(tt_cf_o);
                                total = 0;
                            }
                        }

                        for (int j = 0; j < tt_fo.size(); j++) {
                            if (tt_fo.get(j).getmInpr_tt() != null && !tt_fo.get(j).getmInpr_tt().equals("")) {
                                Inpr1.add(Integer.parseInt(tt_fo.get(j).getmInpr_tt().trim()));
                            } else {
                                Inpr1.add(0);
                            }
                            if (Inpr1.get(j) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Inpr1.get(j), tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Inpr1.get(j) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Inpr1.get(j) + ")");
                                llo.add(llo_o);
                            }
                        }
                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        pieChart.setCenterText(header);
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                    if (value.equals("Completed")) {
                        entries = new ArrayList<PieEntry>();
                        Comp1.clear();
                        swerk_p.clear();
                        llo.clear();

                        tt_fo.clear();
                        for (int i = 0; i < tt_o.size(); i++) {
                            String notif = tt_o.get(i).getmQmart_tt();
                            if (tt_fo.size() > 0) {
                                if (contains(tt_fo, notif)) {

                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                    tt_cf_o.setmComp_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            } else {
                                for (int j = 0; j < filter.size(); j++) {
                                    if (filter.get(j).getmQmart_tt().equals(notif)) {
                                        if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                            total = total + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                        } else {
                                            total = total + 0;
                                        }
                                    }
                                }
                                Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                tt_cf_o.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                                tt_cf_o.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                                tt_cf_o.setmQmart_tt(notif);
                                tt_cf_o.setmQmartx_tt(tt_o.get(i).getmQmartx_tt());
                                tt_cf_o.setmComp_tt(String.valueOf(total));
                                tt_fo.add(tt_cf_o);
                                total = 0;
                            }
                        }

                        for (int j = 0; j < tt_fo.size(); j++) {
                            if (tt_fo.get(j).getmComp_tt() != null && !tt_fo.get(j).getmComp_tt().equals("")) {
                                Comp1.add(Integer.parseInt(tt_fo.get(j).getmComp_tt().trim()));
                            } else {
                                Comp1.add(0);
                            }
                            if (Comp1.get(j) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Comp1.get(j), tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Comp1.get(j) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(j).getmQmart_tt() + "-" + tt_fo.get(j).getmQmartx_tt() + " (" + Comp1.get(j) + ")");
                                llo.add(llo_o);
                            }
                        }

                        final String[] qty = new String[entries.size()];
                        for (int i = 0; i < entries.size(); i++) {
                            String la = entries.get(i).getLabel();
                            String[] parts = la.split("-");
                            String part1 = parts[0];
                            qty[i] = part1;
                        }

                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        pieChart.setCenterText(header);
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);/*
                                intent.putExtra("month", month);
                                intent.putExtra("year", year);
                                intent.putExtra("plant_id", plant_id);*/
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });


                    }
                } catch (NumberFormatException nfe) {
                    Log.v("Number_Exception", "Could not parse " + nfe);
                    pieChart.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                }
            } else {
                ArrayList<Mis_EtNotifTypeTotal_Object> tt_swerk = new ArrayList<Mis_EtNotifTypeTotal_Object>();
                plant_all = false;
                try {
                    for (int k = 0; k < pt_o.size(); k++) {
                        if (pt_o.get(k).getmSwerk_pt().equals(swerk)) {
                            swerk_txt = pt_o.get(k).getmName_pt();
                        }
                    }
                    if (value.equals("Outstanding")) {
                        entries = new ArrayList<PieEntry>();
                        Outs1.clear();
                        swerk_p.clear();
                        tt_swerk.clear();
                        llo.clear();
                        tt_fo.clear();
                        filter.clear();

                        if (wrkcnt.equals("")) {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk)) {
                                    tt_so.setmOuts_tt(tt_o.get(j).getmOuts_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_swerk.add(tt_so);
                                    filter.add(tt_so);
                                }
                            }

                            for (int i = 0; i < tt_swerk.size(); i++) {
                                String notif = tt_swerk.get(i).getmQmart_tt();
                                if (tt_fo.size() > 0) {
                                    if (contains(tt_fo, notif)) {

                                    } else {
                                        for (int j = 0; j < filter.size(); j++) {
                                            if (filter.get(j).getmQmart_tt().equals(notif)) {
                                                if (!filter.get(j).getmOuts_tt().equals("") && filter.get(j).getmOuts_tt() != null) {
                                                    total = total + Integer.parseInt(filter.get(j).getmOuts_tt().trim());
                                                } else {
                                                    total = total + 0;
                                                }
                                            }
                                        }
                                        Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                        tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                        tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                        tt_cf_o.setmQmart_tt(notif);
                                        tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                        tt_cf_o.setmOuts_tt(String.valueOf(total));
                                        tt_fo.add(tt_cf_o);
                                        total = 0;
                                    }
                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmOuts_tt().equals("") && filter.get(j).getmOuts_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmOuts_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                    tt_cf_o.setmOuts_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            }

                        } else {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk) && tt_o.get(j).getmArbpl_tt().equals(wrkcnt)) {
                                    tt_so.setmOuts_tt(tt_o.get(j).getmOuts_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_fo.add(tt_so);
                                }
                            }
                        }

                        for (int k = 0; k < tt_fo.size(); k++) {
                            if (tt_fo.get(k).getmOuts_tt() != null && !tt_fo.get(k).getmOuts_tt().equals("")) {
                                Outs1.add(Integer.parseInt(tt_fo.get(k).getmOuts_tt().trim()));
                            } else {
                                Outs1.add(0);
                            }
                            if (Outs1.get(k) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Outs1.get(k), tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Outs1.get(k) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Outs1.get(k) + ")");
                                llo.add(llo_o);
                            }
                        }
                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c/*%entries.size()*/));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        if (!wrkcnt.equals("")) {
                            pieChart.setCenterText(plant_name + " - " + wrkcnt_name);
                        } else {
                            pieChart.setCenterText(plant_name);
                        }
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);
                                /*intent.putExtra("month", month);
                                intent.putExtra("year", year);
                                intent.putExtra("plant_id", plant_id);*/
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);

                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                    if (value.equals("Inprogress")) {
                        entries = new ArrayList<PieEntry>();
                        Inpr1.clear();
                        swerk_p.clear();
                        tt_swerk.clear();
                        llo.clear();
                        tt_fo.clear();
                        filter.clear();

                        if (wrkcnt.equals("")) {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk)) {
                                    tt_so.setmInpr_tt(tt_o.get(j).getmInpr_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_swerk.add(tt_so);
                                    filter.add(tt_so);
                                }
                            }

                            for (int i = 0; i < tt_swerk.size(); i++) {
                                String notif = tt_swerk.get(i).getmQmart_tt();
                                if (tt_fo.size() > 0) {
                                    if (contains(tt_fo, notif)) {

                                    } else {
                                        for (int j = 0; j < filter.size(); j++) {
                                            if (filter.get(j).getmQmart_tt().equals(notif)) {
                                                if (!filter.get(j).getmInpr_tt().equals("") && filter.get(j).getmInpr_tt() != null) {
                                                    total = total + Integer.parseInt(filter.get(j).getmInpr_tt().trim());
                                                } else {
                                                    total = total + 0;
                                                }
                                            }
                                        }
                                        Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                        tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                        tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                        tt_cf_o.setmQmart_tt(notif);
                                        tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                        tt_cf_o.setmInpr_tt(String.valueOf(total));
                                        tt_fo.add(tt_cf_o);
                                        total = 0;
                                    }
                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmInpr_tt().equals("") && filter.get(j).getmInpr_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmInpr_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                    tt_cf_o.setmInpr_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            }
                        } else {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk) && tt_o.get(j).getmArbpl_tt().equals(wrkcnt)) {
                                    tt_so.setmInpr_tt(tt_o.get(j).getmInpr_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_fo.add(tt_so);
                                }
                            }
                        }


                        for (int k = 0; k < tt_fo.size(); k++) {
                            if (tt_fo.get(k).getmInpr_tt() != null && !tt_fo.get(k).getmInpr_tt().equals("")) {
                                Inpr1.add(Integer.parseInt(tt_fo.get(k).getmInpr_tt().trim()));
                            } else {
                                Inpr1.add(0);
                            }
                            if (Inpr1.get(k) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Inpr1.get(k), tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Inpr1.get(k) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Inpr1.get(k) + ")");
                                llo.add(llo_o);
                            }
                        }
                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        if (!wrkcnt.equals("")) {
                            pieChart.setCenterText(plant_name + " - " + wrkcnt_name);
                        } else {
                            pieChart.setCenterText(plant_name);
                        }
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent( StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);
                                /*intent.putExtra("month", month);
                                intent.putExtra("year", year);
                                intent.putExtra("plant_id", plant_id);*/
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);

                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                    if (value.equals("Completed")) {
                        entries = new ArrayList<PieEntry>();
                        Comp1.clear();
                        swerk_p.clear();
                        tt_swerk.clear();
                        llo.clear();
                        tt_fo.clear();
                        filter.clear();

                        if (wrkcnt.equals("")) {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk)) {
                                    tt_so.setmComp_tt(tt_o.get(j).getmComp_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_swerk.add(tt_so);
                                    filter.add(tt_so);
                                }
                            }

                            for (int i = 0; i < tt_swerk.size(); i++) {
                                String notif = tt_swerk.get(i).getmQmart_tt();
                                if (tt_fo.size() > 0) {
                                    if (contains(tt_fo, notif)) {

                                    } else {
                                        for (int j = 0; j < filter.size(); j++) {
                                            if (filter.get(j).getmQmart_tt().equals(notif)) {
                                                if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                                    total = total + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                                } else {
                                                    total = total + 0;
                                                }
                                            }
                                        }
                                        Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                        tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                        tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                        tt_cf_o.setmQmart_tt(notif);
                                        tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                        tt_cf_o.setmComp_tt(String.valueOf(total));
                                        tt_fo.add(tt_cf_o);
                                        total = 0;
                                    }
                                } else {
                                    for (int j = 0; j < filter.size(); j++) {
                                        if (filter.get(j).getmQmart_tt().equals(notif)) {
                                            if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                                total = total + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                            } else {
                                                total = total + 0;
                                            }
                                        }
                                    }
                                    Mis_EtNotifTypeTotal_Object tt_cf_o = new Mis_EtNotifTypeTotal_Object();
                                    tt_cf_o.setmSwerk_tt(tt_swerk.get(i).getmSwerk_tt());
                                    tt_cf_o.setmArbpl_tt(tt_swerk.get(i).getmArbpl_tt());
                                    tt_cf_o.setmQmart_tt(notif);
                                    tt_cf_o.setmQmartx_tt(tt_swerk.get(i).getmQmartx_tt());
                                    tt_cf_o.setmComp_tt(String.valueOf(total));
                                    tt_fo.add(tt_cf_o);
                                    total = 0;
                                }
                            }

                        } else {
                            for (int j = 0; j < tt_o.size(); j++) {
                                Mis_EtNotifTypeTotal_Object tt_so = new Mis_EtNotifTypeTotal_Object();
                                if (tt_o.get(j).getmSwerk_tt().equals(swerk) && tt_o.get(j).getmArbpl_tt().equals(wrkcnt)) {
                                    tt_so.setmComp_tt(tt_o.get(j).getmComp_tt());
                                    tt_so.setmQmart_tt(tt_o.get(j).getmQmart_tt());
                                    tt_so.setmQmartx_tt(tt_o.get(j).getmQmartx_tt());
                                    tt_fo.add(tt_so);
                                }
                            }
                        }


                        for (int k = 0; k < tt_fo.size(); k++) {
                            if (tt_fo.get(k).getmComp_tt() != null && !tt_fo.get(k).getmComp_tt().equals("")) {
                                Comp1.add(Integer.parseInt(tt_fo.get(k).getmComp_tt().trim()));
                            } else {
                                Comp1.add(0);
                            }
                            if (Comp1.get(k) == 0) {

                            } else {
                                entries.add(new PieEntry((float) Comp1.get(k), tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Comp1.get(k) + ")"));
                                Label_List_Object llo_o = new Label_List_Object();
                                llo_o.setLabel(tt_fo.get(k).getmQmart_tt() + "-" + tt_fo.get(k).getmQmartx_tt() + " (" + Comp1.get(k) + ")");
                                llo.add(llo_o);
                            }
                        }
                        final ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.JOYFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.COLORFUL_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.LIBERTY_COLORS)
                            colors.add(c);

                        for (int c : ColorTemplate.PASTEL_COLORS)
                            colors.add(c);

                        for (int c = 0; c < entries.size(); c++) {
                            llo.get(c).setColor(colors.get(c));
                        }

                        pieDataSet = new PieDataSet(entries, "");
                        pieDataSet.setColors(colors);
                        pieDataSet.setSliceSpace(2f);
                        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                        pieDataSet.setSelectionShift(5f);
                        pieDataSet.setValueLinePart1Length(0.2f);
                        pieDataSet.setValueLinePart2Length(0.4f);
                        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                        pieDataSet.setValueFormatter(new MyValueFormatter());

                        Legend legend = pieChart.getLegend();
                        legend.setTextSize(12);
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setWordWrapEnabled(true);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        legend.setEnabled(false);

                        Label_Adapter ll_a = new Label_Adapter(StatusPieActivity.this, llo);
                        listView.setAdapter(ll_a);

                        pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(14);

                        pieChart.setUsePercentValues(false);
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
                        if (!wrkcnt.equals("")) {
                            pieChart.setCenterText(plant_name + " - " + wrkcnt_name);
                        } else {
                            pieChart.setCenterText(plant_name);
                        }
                        pieChart.setHoleColor(Color.LTGRAY);
                        pieChart.setTransparentCircleColor(Color.LTGRAY);
                        pieChart.setCenterTextSize(14);
                        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                        pieChart.invalidate();// for refreshing the chart
                        progressdialog.dismiss();
                        pieChart.setData(pieData);

                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                int index = (int) h.getX();

                                label = entries.get(index).getLabel();
                                String[] parts = label.split("-");
                                part1 = parts[0];

                                Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                                intent.putExtra("value1", part1);
                                /*intent.putExtra("month", month);
                                intent.putExtra("year", year);
                                intent.putExtra("plant_id", plant_id);*/
                                intent.putExtra("iwerk", swerk);
                                intent.putExtra("heading", label);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("wrkcnt", wrkcnt);
                                startActivity(intent);
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                } catch (NumberFormatException nfe) {
                    Log.v("Number_Exception", "Could not parse " + nfe);
                    pieChart.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                }
            }
        }
    }


    private static boolean contains(final List<Mis_EtNotifTypeTotal_Object> transaction, final String search) {
        for (final Mis_EtNotifTypeTotal_Object transactionLine : transaction) {
            if (transactionLine.getmQmart_tt().equals(search)) {
                return true;
            }
        }
        return false;
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
        public View getView(int position, View convertView, ViewGroup parent) {

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
                    label = label_name.getText().toString();
                    String[] parts = label.split("-");
                    part1 = parts[0];
                    String part2 = parts[1];
                    if (plant_all) {
                        Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                        intent.putExtra("value1", part1);
                        /*intent.putExtra("month", month);
                        intent.putExtra("year", year);
                        intent.putExtra("plant_id", plant_id);*/
                        intent.putExtra("iwerk", swerk);
                        intent.putExtra("heading", label);
                        intent.putExtra("value", value);
                        intent.putExtra("phase", phase);
                        intent.putExtra("wrkcnt", wrkcnt);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(StatusPieActivity.this, NotifTypeListActivity.class);
                        intent.putExtra("value1", part1);
                        /*intent.putExtra("month", month);
                        intent.putExtra("year", year);
                        intent.putExtra("plant_id", plant_id);*/
                        intent.putExtra("iwerk", swerk);
                        intent.putExtra("heading", label);
                        intent.putExtra("value", value);
                        intent.putExtra("phase", phase);
                        intent.putExtra("wrkcnt", wrkcnt);
                        startActivity(intent);
                    }
                }
            });

            return listV;
        }
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
