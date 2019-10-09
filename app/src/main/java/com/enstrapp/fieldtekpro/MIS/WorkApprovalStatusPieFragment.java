package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.DateTime.MonthYearPickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class WorkApprovalStatusPieFragment extends Fragment {
    String plantName_s = "", wrkcntrName_s = "",
            swerk_s = "", wrkcntr_s = "", swerkT_s = "", wrkcntrNameW_s = "", wrkcntrW_s = "",
            plantNameT_s = "", swerkTp_s = "", plantNameTp_s = "", wrkcntrT_s = "",
            wrkcntrNameT_s = "", monthYear_s = "", selected_month = "", selected_year = "";
    ;
    int req_monthyear = 8;

    String total_ppr = "", red = "", yellow = "", green = "", total_permit = "", crea = "", prep = "", clsd = "", reje = "",
            total_ppr_f = "", red_f = "", yellow_f = "", green_f = "", total_permit_f = "", crea_f = "", prep_f = "", clsd_f = "", reje_f = "";
    int total_permit1, crea1, prep1, clsd1, reje1, total_ppr1, red1, yellow1, green1;
    String yel = "", gre = "", re = "", Arbpl = "";
    String total = "", plant_name = "", iwerk = "", wrkcnt = "", wrkcnt_name = "",
            plant_name_t = "", iwerk_t = "", wrkcnt_t = "", wrkcnt_name_t = "", wrkcnt_w = "", wrkcnt_name_w = "", iwerk_tp = "", plant_name_tp = "";
    List<Integer> my_colors = new ArrayList<>();
    PieChart pieChart1;

    Dialog network_error_dialog, plant_wrkcnt_dialog, wrkcnt_dialog;
    Button total_permits, approved_permits, monthYear_bt, getReport_bt;
    int red_color, green_color, yellow_color;
    ListView label_list1;
    SimpleDateFormat inputFormat, outputFormat;
    int req_stdate = 8;
    Boolean isInternetPresent = false;
    TextView no_data, total_record;
    boolean plant_all = false, changed = false, changed_w = false, plant_clear = false, workcentre_clear = false;
    PieData pieData1;
    ConnectionDetector cd;
    boolean plantAll_b = false, changed_b = false, plantClear_b = false, changedW_b = false,
            wrkcntrClear_b = false;
    Button plant_bt, wrkcntr_bt;
    TextView noData_tv;
    ImageView filter_iv;
    PermitReportMainActivity ma;
    TextView plant_tv, wrkcntr_tv;
    Dialog filter_dialog, plant_dialog, wrkcntr_dialog, error_dialog;
    ProgressDialog progressdialog;
    ListView listview;
    //PieChart pieChart;
    PieDataSet DataSet1;
    PieDataSet pieDataSet;
    ListView label_list;
    SQLiteDatabase App_db;
    private String DATABASE_NAME = "";
    private static int count = 0;
    ArrayList<Mis_EtPermitApprWerks_Object> pt_o = new ArrayList<Mis_EtPermitApprWerks_Object>();
    ArrayList<Mis_EtPermitApprArbpl_Object> art_o = new ArrayList<Mis_EtPermitApprArbpl_Object>();
    ArrayList<Mis_EtNotifArbplTotal_Object> art_fo = new ArrayList<Mis_EtNotifArbplTotal_Object>();
    public static final ArrayList<Mis_EtPermitApprArbpl_Object> EtPermitApprArbpl = new ArrayList<Mis_EtPermitApprArbpl_Object>();
    public static final ArrayList<Mis_EtPermitApprWerks_Object> EtPermitApprWerks = new ArrayList<Mis_EtPermitApprWerks_Object>();
    public static final ArrayList<Mis_EtPermitTotalArbpl_Object> EtPermitTotalArbpl = new ArrayList<Mis_EtPermitTotalArbpl_Object>();
    public static final ArrayList<Mis_EtPermitTotalWerks_Object> EtPermitTotalWerks = new ArrayList<Mis_EtPermitTotalWerks_Object>();
    ArrayList<Label_List_Object> llo = new ArrayList<Label_List_Object>();
    FILTER_PLANT_TYPE_Adapter filter_plant_type_adapter;
    WKCENTER_TYPE_ADAPTER wkcenter_type_adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mis_permitrep_analysis_fragment, container, false);

        monthYear_bt = rootView.findViewById(R.id.monthYear_bt);
        pieChart1 = rootView.findViewById(R.id.pieChart);
        label_list1 = rootView.findViewById(R.id.label_list);
        noData_tv = rootView.findViewById(R.id.no_data_textview);
        ma = (PermitReportMainActivity) this.getActivity();
        //total_record=rootView.findViewById(R.id.total_record);

        DATABASE_NAME = getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);

        inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        outputFormat = new SimpleDateFormat("MMM, yyyy", Locale.getDefault());
        SimpleDateFormat month_format = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat year_format = new SimpleDateFormat("yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(day + "-" + month + "-" + year);
            String datee = outputFormat.format(date);
            monthYear_bt.setText(datee);
            monthYear_s = datee;
            selected_month = month_format.format(date);
            selected_year = year_format.format(date);
        } catch (Exception e) {
        }

        monthYear_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MonthYearPickerDialog.class);
                startActivityForResult(intent, req_monthyear);
            }
        });
        getData();
        return rootView;
    }

    public void getData() {

        Log.v("WorkApproval", "- getData");
        progressdialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(getResources().getString(
                R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();
        EtPermitApprArbpl.clear();
        EtPermitApprWerks.clear();
        EtPermitTotalArbpl.clear();
        EtPermitTotalWerks.clear();
        art_o.clear();
        pt_o.clear();


        try {
            Cursor cursor = App_db.rawQuery("select * from EtPermitApprWerks", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtPermitApprWerks_Object cp = new Mis_EtPermitApprWerks_Object();
                        cp.setIwerk(cursor.getString(1));
                        cp.setName(cursor.getString(2));
                        cp.setTotal(cursor.getString(3));
                        cp.setRed(cursor.getString(4));
                        cp.setYellow(cursor.getString(5));
                        cp.setGreen(cursor.getString(6));
                        cp.setChecked("n");
                        pt_o.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("EtNotifPlantTotal_exce", "" + e.getMessage());
            pieChart1.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            llo.clear();
            progressdialog.dismiss();
        }
        try {
            Cursor cursor = App_db.rawQuery("select * from EtPermitApprArbpl", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtPermitApprArbpl_Object cp = new Mis_EtPermitApprArbpl_Object();
                        cp.setIwerk(cursor.getString(1));
                        cp.setArbpl(cursor.getString(2));
                        cp.setName(cursor.getString(3));
                        cp.setTotal(cursor.getString(4));
                        cp.setRed(cursor.getString(5));
                        cp.setYellow(cursor.getString(6));
                        cp.setGreen(cursor.getString(7));

                        cp.setChecked("n");
                        art_o.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("EtNotifArbplTotal_exce", "" + e.getMessage());
            pieChart1.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            llo.clear();
            progressdialog.dismiss();
        }

        try {
            Cursor cursor1 = App_db.rawQuery("select * from EtPermitWa", null);
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    red1 = 0;
                    yellow1 = 0;
                    green1 = 0;
                    do {
                        if (cursor1.getString(6).equals("X")) {
                            red1 = red1 + 1;
                        } else if (cursor1.getString(7).equals("X")) {
                            yellow1 = yellow1 + 1;
                        } else if (cursor1.getString(8).equals("X")) {
                            green1 = green1 + 1;
                        }

                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
            }
        } catch (Exception e) {
            Log.v("EtNotifPlantTotal_exce", "" + e.getMessage());
            pieChart1.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            llo.clear();
            progressdialog.dismiss();
        }


       /* try {
            if (total_ppr_f != null && !total_ppr_f.equals("")) {
                total_ppr1 = Integer.parseInt(total_ppr_f.trim());
            } else {
                total_ppr1 = 0;
            }
            if (red_f != null && !red_f.equals("")) {
                red1 = Integer.parseInt(red_f.trim());
            } else {
                red1 = 0;
            }
            if (yellow_f != null && !yellow_f.equals("")) {
                yellow1 = Integer.parseInt(yellow_f.trim());
            } else {
                yellow1 = 0;
            }
            if (green_f != null && !green_f.equals("")) {
                green1 = Integer.parseInt(green_f.trim());
            } else {
                green1 = 0;
            }
        } catch (NumberFormatException nfe) {
        }*/

        final List<PieEntry> entries = new ArrayList<>();
        my_colors.clear();
        yel = "";
        gre = "";
        re = "";
        llo.clear();
        if (red1 != 0) {
            entries.add(new PieEntry(red1, getString(R.string.noissue)));
            my_colors.add(Color.rgb(255, 0, 0));
            re = "R";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.noissue));
            llo_o.setColor(Color.rgb(255, 0, 0));
            llo.add(llo_o);
        } else {
            red_color = 0;
            re = "";
        }
        if (yellow1 != 0) {
            entries.add(new PieEntry(yellow1, getString(R.string.partissue)));
            my_colors.add(Color.rgb(255, 255, 0));
            yel = "Y";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.partissue));
            llo_o.setColor(Color.rgb(255, 255, 0));
            llo.add(llo_o);
        } else {
            yellow_color = 0;
            yel = "";
        }
        if (green1 != 0) {
            entries.add(new PieEntry(green1, getString(R.string.issue)));
            my_colors.add(Color.rgb(0, 128, 0));
            gre = "G";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.issue));
            llo_o.setColor(Color.rgb(0, 128, 0));
            llo.add(llo_o);
        } else {
            green_color = 0;
            gre = "";
        }
        DataSet1 = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : my_colors) colors.add(c);

        DataSet1.setColors(colors);
        DataSet1.setSliceSpace(2f);
        DataSet1.setValueLinePart1OffsetPercentage(80.f);
        DataSet1.setSelectionShift(5f);
        DataSet1.setValueLinePart1Length(0.2f);
        DataSet1.setValueLinePart2Length(0.4f);
        DataSet1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        DataSet1.setValueFormatter(new MyValueFormatter());

        pieChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int index = (int) h.getX();
                String label = entries.get(index).getLabel();

                cd = new ConnectionDetector(getContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (label.equals(getString(R.string.noissue))) {
                        String value = "Red";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.noissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals(getString(R.string.partissue))) {
                        String value = "Yellow";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.partissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals(getString(R.string.issue))) {
                        String value = "Green";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.issue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    }
                } else {
                    noInternet();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Legend legend = pieChart1.getLegend();
        legend.setTextSize(14);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setEnabled(false);

        pieData1 = new PieData(DataSet1);
        pieData1.setValueTextSize(14);
        pieChart1.setUsePercentValues(false);


        Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
        label_list1.setAdapter(ll_a);

        pieChart1.setExtraOffsets(5, 5, 5, 5);
        pieChart1.highlightValues(null);
        pieChart1.animateY(1000);
        pieChart1.setScrollContainer(true);
        pieChart1.setVerticalScrollBarEnabled(true);
        pieChart1.getDescription().setEnabled(false);
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setRotationEnabled(true);
        pieChart1.setDrawEntryLabels(false);
        pieChart1.setDragDecelerationFrictionCoef(0.95f);
        if (!iwerk.equals("") && wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name);
            //   total_record.setText("Total Count : " + total_ppr_f);
        } else if (!iwerk.equals("") && !wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name + " " + wrkcnt_name);
//            total_record.setText("Total Count : " + total_ppr_f);
        } else {
            pieChart1.setCenterText("Work Approval Status");
            //  total_record.setText("Total Count : " + total_ppr);
        }
        pieChart1.setHoleColor(Color.LTGRAY);
        pieChart1.setTransparentCircleColor(Color.LTGRAY);
        pieChart1.setCenterTextSize(14);
        pieChart1.notifyDataSetChanged();//Required if changes are made to pie value
        pieChart1.invalidate();// for refreshing the chart
        progressdialog.dismiss();
        pieChart1.setData(pieData1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == req_monthyear) {
                selected_month = data.getStringExtra("selected_month");
                selected_year = data.getStringExtra("selected_year");
                String selected_month_formatted = data.getStringExtra("selected_month_formatted");
                monthYear_bt.setText(selected_month_formatted + "," + " " + selected_year);
                getData();
            }
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
                    if (label_name.getText().toString().equals("Created")) {
                        String value = "Crea";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Created");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals("Prepared")) {
                        String value = "Prep";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Prepared");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals("Closed")) {
                        String value = "Clsd";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Closed");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals("Rejected")) {
                        String value = "Reje";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Rejected");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals(getString(R.string.noissue))) {
                        String value = "Red";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.noissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals(getString(R.string.partissue))) {
                        String value = "Yellow";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.partissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label_name.getText().toString().equals(getString(R.string.issue))) {
                        String value = "Green";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.issue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    }
                }
            });

            return listV;
        }
    }

    public class FILTER_PLANT_TYPE_Adapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_EtPermitApprWerks_Object> mainDataList = null;
        private ArrayList<Mis_EtPermitApprWerks_Object> arraylist;

        public FILTER_PLANT_TYPE_Adapter(Context context, ArrayList<Mis_EtPermitApprWerks_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtPermitApprWerks_Object>();
            this.arraylist.addAll(mainDataList);
        }

        class ViewHolder {
            protected RadioGroup radiogroup;
            protected RadioButton radio_status;
            protected TextView Name;
            protected TextView Total;
            protected TextView Red;
            protected TextView Yellow;
            protected TextView Green;
            protected TextView key;
        }

        @Override
        public int getCount() {
            count = mainDataList.size();
            return count;
        }

        @Override
        public Mis_EtPermitApprWerks_Object getItem(int position) {
            return mainDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.plant_mis_data, null);
                holder.radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                holder.radio_status = (RadioButton) view.findViewById(R.id.radio_status);
                holder.Name = (TextView) view.findViewById(R.id.Name);
                holder.Total = (TextView) view.findViewById(R.id.Total);
                holder.Red = (TextView) view.findViewById(R.id.Red);
                holder.Yellow = (TextView) view.findViewById(R.id.Yellow);
                holder.Green = (TextView) view.findViewById(R.id.Green);
                holder.key = (TextView) view.findViewById(R.id.key);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (pieChart1.getVisibility() == View.VISIBLE) {
                if (!iwerk_tp.equals("")) {
                    for (int i = 0; i < pt_o.size(); i++) {
                        if (pt_o.get(i).getIwerk().equals(iwerk_tp)) {
                            holder.radio_status.setChecked(true);
                            pt_o.get(i).setChecked("y");
                        } else {
                            pt_o.get(i).setChecked("n");
                        }
                    }
                } else if (!iwerk_t.equals("")) {
                    for (int j = 0; j < pt_o.size(); j++) {
                        if (pt_o.get(j).getIwerk().equals(iwerk_t)) {
                            holder.radio_status.setChecked(true);
                            pt_o.get(j).setChecked("y");
                        } else {
                            pt_o.get(j).setChecked("n");
                        }
                    }
                } else if (iwerk.equals("")) {
                    for (int j = 0; j < pt_o.size(); j++) {
                        if (pt_o.get(j).getIwerk().equals(iwerk)) {
                            holder.radio_status.setChecked(true);
                            pt_o.get(j).setChecked("y");
                        } else {
                            pt_o.get(j).setChecked("n");
                        }
                    }
                }
            }

            holder.radio_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pieChart1.getVisibility() == View.VISIBLE) {
                        String iwerk1 = holder.radio_status.getText().toString();
                        if (holder.radio_status.isChecked()) {
                            for (int i = 0; i < pt_o.size(); i++) {
                                String new_iwerk = pt_o.get(i).getIwerk();
                                String new_plant = pt_o.get(i).getName();
                                if (iwerk1.equalsIgnoreCase(new_iwerk)) {
                                    pt_o.get(i).setChecked("y");
                                    mainDataList.get(i).setChecked("y");
                                    plant_name_tp = new_plant;
                                    iwerk_tp = new_iwerk;
                                    plant_all = false;
                                    changed = true;
                                } else {
                                    String status = pt_o.get(i).getChecked();
                                    if (status.equals("y")) {
                                        pt_o.get(i).setChecked("n");
                                        mainDataList.get(i).setChecked("n");
                                    }
                                }
                            }
                            filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o);
                            listview.setAdapter(filter_plant_type_adapter);
                        } else {

                        }
                    }
                }
            });
            holder.radio_status.setText(mainDataList.get(position).getIwerk());
            holder.radio_status.setTag(position);
            if (mainDataList.get(position).getChecked().equals("y")) {
                holder.radio_status.setChecked(true);
            } else {
                holder.radio_status.setChecked(false);
            }
            holder.Name.setText(mainDataList.get(position).getName());

            return view;
        }
    }


    protected void show_error_dialog(String string) {
        error_dialog = new Dialog(getActivity());
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setCancelable(false);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.setContentView(R.layout.error_dialog);
        final TextView description_textview = (TextView) error_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) error_dialog.findViewById(R.id.ok_button);
        description_textview.setText(string);
        error_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error_dialog.dismiss();
            }
        });
    }

    public class WKCENTER_TYPE_ADAPTER extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_EtNotifArbplTotal_Object> mainDataList = null;
        private ArrayList<Mis_EtNotifArbplTotal_Object> arraylist;

        public WKCENTER_TYPE_ADAPTER(Context context, List<Mis_EtNotifArbplTotal_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtNotifArbplTotal_Object>();
            this.arraylist.addAll(mainDataList);
        }

        class ViewHolder {
            protected RadioGroup radiogroup;
            protected RadioButton radio_status;
            protected TextView Name;
            protected TextView Total;
            protected TextView Red;
            protected TextView Yellow;
            protected TextView Green;
            protected TextView key;
        }

        @Override
        public int getCount() {
            count = mainDataList.size();
            return count;
        }

        @Override
        public Mis_EtNotifArbplTotal_Object getItem(int position) {
            return mainDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.plant_mis_data, null);
                holder.radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                holder.radio_status = (RadioButton) view.findViewById(R.id.radio_status);
                holder.Name = (TextView) view.findViewById(R.id.Name);
                holder.Total = (TextView) view.findViewById(R.id.Total);
                holder.Red = (TextView) view.findViewById(R.id.Red);
                holder.Yellow = (TextView) view.findViewById(R.id.Yellow);
                holder.Green = (TextView) view.findViewById(R.id.Green);
                holder.key = (TextView) view.findViewById(R.id.key);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (pieChart1.getVisibility() == View.VISIBLE) {
                if (!wrkcnt_w.equals("")) {
                    for (int i = 0; i < art_fo.size(); i++) {
                        if (art_fo.get(i).getmArbpl_art().equals(wrkcnt_w)) {
                            holder.radio_status.setChecked(true);
                            art_fo.get(i).setmChecked("y");
                        } else {
                            art_fo.get(i).setmChecked("n");
                        }
                    }
                } else if (!wrkcnt_t.equals("")) {
                    for (int j = 0; j < art_fo.size(); j++) {
                        if (art_fo.get(j).getmArbpl_art().equals(wrkcnt_t)) {
                            holder.radio_status.setChecked(true);
                            art_fo.get(j).setmChecked("y");
                        } else {
                            art_fo.get(j).setmChecked("n");
                        }
                    }
                } else if (!wrkcnt.equals("")) {
                    for (int j = 0; j < art_fo.size(); j++) {
                        if (art_fo.get(j).getmArbpl_art().equals(wrkcnt)) {
                            holder.radio_status.setChecked(true);
                            art_fo.get(j).setmChecked("y");
                        } else {
                            art_fo.get(j).setmChecked("n");
                        }
                    }
                }
            }


            holder.radio_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pieChart1.getVisibility() == View.VISIBLE) {
                        String wkcenter = holder.radio_status.getText().toString();
                        if (holder.radio_status.isChecked()) {
                            for (int i = 0; i < art_fo.size(); i++) {
                                String new_wkcenter = art_fo.get(i).getmArbpl_art();
                                String new_wrkname = art_fo.get(i).getmName_art();
                                if (wkcenter.equalsIgnoreCase(new_wkcenter)) {
                                    art_fo.get(i).setmChecked("y");
                                    mainDataList.get(i).setmChecked("y");
                                    wrkcnt_w = new_wkcenter;
                                    wrkcnt_name_w = new_wrkname;
                                    changed_w = true;
                                } else {
                                    String status = art_fo.get(i).getmChecked();
                                    if (status.equals("y")) {
                                        art_fo.get(i).setmChecked("n");
                                        mainDataList.get(i).setmChecked("n");
                                    }
                                }
                            }
                            wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                            listview.setAdapter(wkcenter_type_adapter);
                        } else {

                        }
                    }
                }
            });
            holder.radio_status.setText(mainDataList.get(position).getmArbpl_art());
            holder.radio_status.setTag(position);
            if (mainDataList.get(position).getmChecked().equals("y")) {
                holder.radio_status.setChecked(true);
            } else {
                holder.radio_status.setChecked(false);
            }
            holder.Name.setText(mainDataList.get(position).getmName_art());

            return view;
        }
    }

    public void plantWrkcntSelector(String plant, final String wrkcnt) {

        Cursor cursor1 = null;
        red1 = 0;
        yellow1 = 0;
        green1 = 0;

        try {
            if (wrkcnt != null && !wrkcnt.equals("")) {

                cursor1 = App_db.rawQuery("select * from EtPermitWa where Iwerk = ? and Arbpl = ?", new String[]{plant, wrkcnt});
            } else {
                cursor1 = App_db.rawQuery("select * from EtPermitWa where Iwerk=?", new String[]{plant});

            }

            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        if (cursor1.getString(6).equals("X")) {
                            red1 = red1 + 1;
                        } else if (cursor1.getString(7).equals("X")) {
                            yellow1 = yellow1 + 1;
                        } else if (cursor1.getString(8).equals("X")) {
                            green1 = green1 + 1;
                        }
                    } while (cursor1.moveToNext());
                }

            }


        } catch (Exception e) {
        }


    /*if (total_permits.getVisibility() == View.VISIBLE) {
        total_permits.setTextColor(getResources().getColor(R.color.grey));
        total_permits.setBackgroundDrawable(getResources().getDrawable(R.drawable.underline_icon));
        approved_permits.setBackgroundDrawable(null);
        if (!plant.equals("") && wrkcnt.equals("")) {
            for (int i = 0; i < EtPermitTotalWerks.size(); i++) {
                if (EtPermitTotalWerks.get(i).getIwerk().equals(plant)) {
                    total_permit_f = EtPermitTotalWerks.get(i).getTotal();
                    crea_f = EtPermitTotalWerks.get(i).getCrea();
                    prep_f = EtPermitTotalWerks.get(i).getPrep();
                    clsd_f = EtPermitTotalWerks.get(i).getClsd();
                    reje_f = EtPermitTotalWerks.get(i).getReje();
                }
            }
        } else {
            for (int i = 0; i < EtPermitTotalArbpl.size(); i++) {
                if (EtPermitTotalArbpl.get(i).getmArbpl_art().equals(plant) && EtPermitTotalArbpl.get(i).getmArbpl_art().equals(wrkcnt)) {
                    total_permit_f = EtPermitTotalArbpl.get(i).getmTotal_art();
                    crea_f = EtPermitTotalArbpl.get(i).getmCrea_art();
                    prep_f = EtPermitTotalArbpl.get(i).getmPrep_art();
                    clsd_f = EtPermitTotalArbpl.get(i).getmClsd_art();
                    reje_f = EtPermitTotalArbpl.get(i).getmReje_art();
                }
            }
        }

        total_record.setVisibility(View.VISIBLE);
        pieChart1.setVisibility(View.VISIBLE);
        no_data.setVisibility(View.GONE);
        try {
            if (total_permit_f != null && !total_permit_f.equals("")) {
                total_permit1 = Integer.parseInt(total_permit_f.trim());
            } else {
                total_permit1 = 0;
            }
            if (crea_f != null && !crea_f.equals("")) {
                crea1 = Integer.parseInt(crea_f.trim());
            } else {
                crea1 = 0;
            }
            if (prep_f != null && !prep_f.equals("")) {
                prep1 = Integer.parseInt(prep_f.trim());
            } else {
                prep1 = 0;
            }
            if (clsd_f != null && !clsd_f.equals("")) {
                clsd1 = Integer.parseInt(clsd_f.trim());
            } else {
                clsd1 = 0;
            }
            if (reje_f != null && !reje_f.equals("")) {
                reje1 = Integer.parseInt(reje_f.trim());
            } else {
                reje1 = 0;
            }
        } catch (NumberFormatException nfe) {
        }

        final List<PieEntry> entries = new ArrayList<>();
        my_colors.clear();
        llo.clear();
        if (crea1 != 0) {
            entries.add(new PieEntry(crea1, "Created"));
            my_colors.add(Color.rgb(255, 255, 0));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel("Created");
            llo_o.setColor(Color.rgb(255, 255, 0)); //Yellow
            llo.add(llo_o);

        } else {
        }
        if (prep1 != 0) {
            entries.add(new PieEntry(prep1, "Prepared"));
            my_colors.add(Color.rgb(51, 51, 255));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel("Prepared");
            llo_o.setColor(Color.rgb(51, 51, 255)); //Blue
            llo.add(llo_o);
        } else {
        }
        if (clsd1 != 0) {
            entries.add(new PieEntry(clsd1, "Closed"));
            my_colors.add(Color.rgb(50, 205, 50));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel("Closed");
            llo_o.setColor(Color.rgb(50, 205, 50)); // Green
            llo.add(llo_o);
        } else {
        }
        if (reje1 != 0) {
            entries.add(new PieEntry(reje1, "Rejected"));
            my_colors.add(Color.rgb(239, 51, 64));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel("Rejected");
            llo_o.setColor(Color.rgb(239, 51, 64));// Red
            llo.add(llo_o);
        } else {
        }

        DataSet1 = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : my_colors) colors.add(c);

        DataSet1.setColors(colors);
        DataSet1.setSliceSpace(2f);
        DataSet1.setValueLinePart1OffsetPercentage(80.f);
        DataSet1.setSelectionShift(5f);
        DataSet1.setValueLinePart1Length(0.2f);
        DataSet1.setValueLinePart2Length(0.4f);
        DataSet1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        DataSet1.setValueFormatter(new MyValueFormatter());

        pieChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = (int) h.getX();
                String label = entries.get(index).getLabel();
                cd = new ConnectionDetector(getContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (label.equals("Created")) {
                        String value = "Crea";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Created");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals("Prepared")) {
                        String value = "Prep";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Prepared");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals("Closed")) {
                        String value = "Clsd";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Closed");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals("Rejected")) {
                        String value = "Reje";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", "Rejected");
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    }
                } else {
                    noInternet();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
        Legend legend = pieChart1.getLegend();
        legend.setTextSize(14);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setEnabled(false);

        pieData1 = new PieData(DataSet1);
        pieData1.setValueTextSize(14);
        pieChart1.setUsePercentValues(false);

        Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
        label_list1.setAdapter(ll_a);

        pieChart1.setExtraOffsets(5, 5, 5, 5);
        pieChart1.highlightValues(null);
        pieChart1.animateY(1000);
        pieChart1.setScrollContainer(true);
        pieChart1.setVerticalScrollBarEnabled(true);
        pieChart1.getDescription().setEnabled(false);
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setRotationEnabled(true);
        pieChart1.setDrawEntryLabels(false);
        pieChart1.setDragDecelerationFrictionCoef(0.95f);
        if (!iwerk.equals("") && wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name);
            total_record.setText("Total Count : " + total_permit_f);
        } else if (!iwerk.equals("") && !wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name + " " + wrkcnt_name);
            total_record.setText("Total Count : " + total_permit_f);
        } else {
            pieChart1.setCenterText("Permit Status");
            total_record.setText("Total Count : " + total_permit);
        }
        pieChart1.setHoleColor(Color.LTGRAY);
        pieChart1.setTransparentCircleColor(Color.LTGRAY);
        pieChart1.setCenterTextSize(14);
        pieChart1.notifyDataSetChanged();//Required if changes are made to pie value
        pieChart1.invalidate();// for refreshing the chart
        progressdialog.dismiss();
        pieChart1.setData(pieData1);

    } else {
        approved_permits.setTextColor(getResources().getColor(R.color.grey));
        approved_permits.setBackgroundDrawable(getResources().getDrawable(R.drawable.underline_icon));
        total_permits.setBackgroundDrawable(null);*/
        /*if (!plant.equals("") && wrkcnt.equals("")) {
            for (int i = 0; i < EtPermitApprWerks.size(); i++) {
                if (EtPermitApprWerks.get(i).getIwerk().equals(plant)) {
                    total_ppr_f = EtPermitApprWerks.get(i).getTotal();
                    red_f = EtPermitApprWerks.get(i).getRed();
                    yellow_f = EtPermitApprWerks.get(i).getYellow();
                    green_f = EtPermitApprWerks.get(i).getGreen();
                }
            }
        } else {
            for (int i = 0; i < EtPermitApprArbpl.size(); i++) {
                if (EtPermitApprArbpl.get(i).getIwerk().equals(plant) && EtPermitApprArbpl.get(i).getArbpl().equals(wrkcnt)) {
                    total_ppr_f = EtPermitApprArbpl.get(i).getTotal();
                    red_f = EtPermitApprArbpl.get(i).getRed();
                    yellow_f = EtPermitApprArbpl.get(i).getYellow();
                    green_f = EtPermitApprArbpl.get(i).getGreen();
                }
            }
        }*/
     /* total_record.setVisibility(View.VISIBLE);
        pieChart1.setVisibility(View.VISIBLE);
        noData_tv.setVisibility(View.GONE);
        try {
            if (total_ppr_f != null && !total_ppr_f.equals("")) {
                total_ppr1 = Integer.parseInt(total_ppr_f.trim());
            } else {
                total_ppr1 = 0;
            }
            if (red_f != null && !red_f.equals("")) {
                red1 = Integer.parseInt(red_f.trim());
            } else {
                red1 = 0;
            }
            if (yellow_f != null && !yellow_f.equals("")) {
                yellow1 = Integer.parseInt(yellow_f.trim());
            } else {
                yellow1 = 0;
            }
            if (green_f != null && !green_f.equals("")) {
                green1 = Integer.parseInt(green_f.trim());
            } else {
                green1 = 0;
            }
        } catch (NumberFormatException nfe) {
        }*/

        if (red1 <= 0 && yellow1 <= 0 && green1 <= 0) {
            pieChart1.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            llo.clear();
            label_list1.setAdapter(null);
            progressdialog.dismiss();
        } else {
            final List<PieEntry> entries = new ArrayList<>();
            my_colors.clear();
            yel = "";
            gre = "";
            re = "";
            llo.clear();
            if (red1 != 0) {
                entries.add(new PieEntry(red1, getString(R.string.noissue)));
                my_colors.add(Color.rgb(255, 0, 0));
                re = "R";
                Label_List_Object llo_o = new Label_List_Object();
                llo_o.setLabel(getString(R.string.noissue));
                llo_o.setColor(Color.rgb(255, 0, 0));
                llo.add(llo_o);
            } else {
                red_color = 0;
                re = "";
            }
            if (yellow1 != 0) {
                entries.add(new PieEntry(yellow1, getString(R.string.partissue)));
                my_colors.add(Color.rgb(255, 255, 0));
                yel = "Y";
                Label_List_Object llo_o = new Label_List_Object();
                llo_o.setLabel(getString(R.string.partissue));
                llo_o.setColor(Color.rgb(255, 255, 0));
                llo.add(llo_o);
            } else {
                yellow_color = 0;
                yel = "";
            }
            if (green1 != 0) {
                entries.add(new PieEntry(green1, getString(R.string.issue)));
                my_colors.add(Color.rgb(0, 128, 0));
                gre = "G";
                Label_List_Object llo_o = new Label_List_Object();
                llo_o.setLabel(getString(R.string.issue));
                llo_o.setColor(Color.rgb(0, 128, 0));
                llo.add(llo_o);
            } else {
                green_color = 0;
                gre = "";
            }
            DataSet1 = new PieDataSet(entries, "");

            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int c : my_colors) colors.add(c);

            DataSet1.setColors(colors);
            DataSet1.setSliceSpace(2f);
            DataSet1.setValueLinePart1OffsetPercentage(80.f);
            DataSet1.setSelectionShift(5f);
            DataSet1.setValueLinePart1Length(0.2f);
            DataSet1.setValueLinePart2Length(0.4f);
            DataSet1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            DataSet1.setValueFormatter(new MyValueFormatter());

            pieChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                    int index = (int) h.getX();
                    String label = entries.get(index).getLabel();

                    cd = new ConnectionDetector(getActivity());
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        if (label.equals(getString(R.string.noissue))) {
                            String value = "Red";
                            Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("Iwerk_plant", iwerk);
                            intent.putExtra("Name_plant", plant_name);
                            intent.putExtra("workcenter", wrkcnt);
                            intent.putExtra("application", getString(R.string.noissue));
                            intent.putExtra("wrk_name", wrkcnt_name);
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.partissue))) {
                            String value = "Yellow";
                            Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("Iwerk_plant", iwerk);
                            intent.putExtra("Name_plant", plant_name);
                            intent.putExtra("workcenter", wrkcnt);
                            intent.putExtra("application", getString(R.string.partissue));
                            intent.putExtra("wrk_name", wrkcnt_name);
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.issue))) {
                            String value = "Green";
                            Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("Iwerk_plant", iwerk);
                            intent.putExtra("Name_plant", plant_name);
                            intent.putExtra("workcenter", wrkcnt);
                            intent.putExtra("application", getString(R.string.issue));
                            intent.putExtra("wrk_name", wrkcnt_name);
                            startActivity(intent);
                        }
                    } else {
                        noInternet();
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            });

            Legend legend = pieChart1.getLegend();
            legend.setTextSize(14);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setWordWrapEnabled(true);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setEnabled(false);

            pieData1 = new PieData(DataSet1);
            pieData1.setValueTextSize(14);
            pieChart1.setUsePercentValues(false);


            Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
            label_list1.setAdapter(ll_a);

            pieChart1.setExtraOffsets(5, 5, 5, 5);
            pieChart1.highlightValues(null);
            pieChart1.animateY(1000);
            pieChart1.setScrollContainer(true);
            pieChart1.setVerticalScrollBarEnabled(true);
            pieChart1.getDescription().setEnabled(false);
            pieChart1.setDrawHoleEnabled(true);
            pieChart1.setRotationEnabled(true);
            pieChart1.setDrawEntryLabels(false);
            pieChart1.setDragDecelerationFrictionCoef(0.95f);
            if (wrkcnt != null && !wrkcnt.equals("")) {
                pieChart1.setCenterText(plant_name);
                //total_record.setText("Total Count : " + total_ppr_f);
            } else {
                pieChart1.setCenterText(plant_name + " " + wrkcnt_name);
                //total_record.setText("Total Count : " + total_ppr_f);
            } //total_record.setText("Total Count : " + total_ppr);

            pieChart1.setHoleColor(Color.LTGRAY);
            pieChart1.setTransparentCircleColor(Color.LTGRAY);
            pieChart1.setCenterTextSize(14);
            pieChart1.notifyDataSetChanged();//Required if changes are made to pie value
            pieChart1.invalidate();// for refreshing the chart
            progressdialog.dismiss();
            pieChart1.setData(pieData1);
            noData_tv.setVisibility(View.GONE);
            pieChart1.setVisibility(View.VISIBLE);
        }
    }


    public void noInternet() {
        network_error_dialog = new Dialog(getActivity());
        network_error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        network_error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        network_error_dialog.setCancelable(true);
        network_error_dialog.setCanceledOnTouchOutside(false);
        network_error_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) network_error_dialog.findViewById(R.id.description_textview);
        final TextView description_textview1 = (TextView) network_error_dialog.findViewById(R.id.description_textview1);
        Button ok_button = (Button) network_error_dialog.findViewById(R.id.ok_button);
        Button cancel_button = (Button) network_error_dialog.findViewById(R.id.cancel_button);
        description_textview.setText(getResources().getString(R.string.no_network));
        description_textview1.setText(getResources().getString(R.string.connect_internet));
        ok_button.setText(getResources().getString(R.string.connect));
        description_textview1.setVisibility(View.VISIBLE);
        network_error_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network_error_dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network_error_dialog.dismiss();
            }
        });
    }


    public void allPlant() {

        /*approved_permits.setTextColor(getResources().getColor(R.color.grey));
            approved_permits.setBackgroundDrawable(getResources().getDrawable(R.drawable.underline_icon));
            total_permits.setBackgroundDrawable(null);
            if (total_ppr.equals("") && red.equals("") && yellow.equals("") && green.equals("")) {
               // total_record.setVisibility(View.GONE);
                pieChart1.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
                llo.clear();
                progressdialog.dismiss();
            } else {
               // total_record.setVisibility(View.VISIBLE);
                pieChart1.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);*/
               /* try {
                    if (total_ppr != null && !total_ppr.equals("")) {
                        total_ppr1 = Integer.parseInt(total_ppr.trim());
                    } else {
                        total_ppr1 = 0;
                    }
                    if (red != null && !red.equals("")) {
                        red1 = Integer.parseInt(red.trim());
                    } else {
                        red1 = 0;
                    }
                    if (yellow != null && !yellow.equals("")) {
                        yellow1 = Integer.parseInt(yellow.trim());
                    } else {
                        yellow1 = 0;
                    }
                    if (green != null && !green.equals("")) {
                        green1 = Integer.parseInt(green.trim());
                    } else {
                        green1 = 0;
                    }
                } catch (NumberFormatException nfe) {
                    Log.v("String_to_ Float", "" + nfe.getMessage());
                    pieChart.setVisibility(View.GONE);
                    noData_tv.setVisibility(View.VISIBLE);

                    progressdialog.dismiss();
                }
        llo.clear();
                final List<PieEntry> entries = new ArrayList<>();
               *//* my_colors.clear();
                yel = "";
                gre = "";
                re = "";*//*

                if (red1 != 0) {
                    entries.add(new PieEntry(red1, getString(R.string.noissue)));
                    my_colors.add(Color.rgb(255, 0, 0));
                    re = "R";
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.noissue));
                    llo_o.setColor(Color.rgb(255, 0, 0));
                    llo.add(llo_o);
                } else {
                    red_color = 0;
                    re = "";
                }
                if (yellow1 != 0) {
                    entries.add(new PieEntry(yellow1, getString(R.string.partissue)));
                    my_colors.add(Color.rgb(255, 255, 0));
                    yel = "Y";
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.partissue));
                    llo_o.setColor(Color.rgb(255, 255, 0));
                    llo.add(llo_o);
                } else {
                    yellow_color = 0;
                    yel = "";
                }
                if (green1 != 0) {
                    entries.add(new PieEntry(green1, getString(R.string.issue)));
                    my_colors.add(Color.rgb(0, 128, 0));
                    gre = "G";
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.issue));
                    llo_o.setColor(Color.rgb(0, 128, 0));
                    llo.add(llo_o);
                } else {
                    green_color = 0;
                    gre = "";
                }
        DataSet1 = new PieDataSet(entries, "");

        final int[] MY_COLORS = new int[entries.size()];

        for (int j = 0; j < entries.size(); j++) {
            if (entries.get(j).getLabel().equals("Outstanding")) {
                MY_COLORS[j] = Color.rgb(255, 255, 0);
                llo.get(j).setColor(Color.rgb(255, 255, 0));
            } else if (entries.get(j).getLabel().equals("Inprogress")) {
                MY_COLORS[j] = Color.rgb(51, 51, 255);
                llo.get(j).setColor(Color.rgb(51, 51, 255));
            } else if (entries.get(j).getLabel().equals("Completed")) {
                MY_COLORS[j] = Color.rgb(50, 205, 50);
                llo.get(j).setColor(Color.rgb(50, 205, 50));
            }
        }

                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : my_colors) colors.add(c);

                DataSet1.setColors(colors);
                DataSet1.setSliceSpace(2f);
                DataSet1.setValueLinePart1OffsetPercentage(80.f);
                DataSet1.setSelectionShift(5f);
                DataSet1.setValueLinePart1Length(0.2f);
                DataSet1.setValueLinePart2Length(0.4f);
                DataSet1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                DataSet1.setValueFormatter(new MyValueFormatter());

                pieChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                        int index = (int) h.getX();
                        String label = entries.get(index).getLabel();

                        cd = new ConnectionDetector(getContext());
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            if (label.equals(getString(R.string.noissue))) {
                                String value = "Red";
                                Intent intent = new Intent(getActivity(),PermitStatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("Iwerk_plant", iwerk);
                                intent.putExtra("Name_plant", plant_name);
                                intent.putExtra("workcenter", wrkcnt);
                                intent.putExtra("application", getString(R.string.noissue));
                                intent.putExtra("wrk_name", wrkcnt_name);
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.partissue))) {
                                String value = "Yellow";
                                Intent intent = new Intent(getActivity(),PermitStatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("Iwerk_plant", iwerk);
                                intent.putExtra("Name_plant", plant_name);
                                intent.putExtra("workcenter", wrkcnt);
                                intent.putExtra("application", getString(R.string.partissue));
                                intent.putExtra("wrk_name", wrkcnt_name);
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.issue))) {
                                String value = "Green";
                                Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("Iwerk_plant", iwerk);
                                intent.putExtra("Name_plant", plant_name);
                                intent.putExtra("workcenter", wrkcnt);
                                intent.putExtra("application", getString(R.string.issue));
                                intent.putExtra("wrk_name", wrkcnt_name);
                                startActivity(intent);
                            }
                        } else {
                            noInternet();
                        }
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

                Legend legend = pieChart1.getLegend();
                legend.setTextSize(14);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setWordWrapEnabled(true);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                legend.setEnabled(false);

                pieData1 = new PieData(DataSet1);
                pieData1.setValueTextSize(14);
                pieChart1.setUsePercentValues(false);


                Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
                label_list1.setAdapter(ll_a);

                pieChart1.setExtraOffsets(5, 5, 5, 5);
                pieChart1.highlightValues(null);
                pieChart1.animateY(1000);
                pieChart1.setScrollContainer(true);
                pieChart1.setVerticalScrollBarEnabled(true);
                pieChart1.getDescription().setEnabled(false);
                pieChart1.setDrawHoleEnabled(true);
                pieChart1.setRotationEnabled(true);
                pieChart1.setDrawEntryLabels(false);
                pieChart1.setDragDecelerationFrictionCoef(0.95f);
//                total_record.setText("Total Count :" + total_ppr);
                pieChart1.setCenterText("Work Approval Status");
                pieChart1.setHoleColor(Color.LTGRAY);
                pieChart1.setTransparentCircleColor(Color.LTGRAY);
                pieChart1.setCenterTextSize(14);
                pieChart1.notifyDataSetChanged();//Required if changes are made to pie value
                pieChart1.invalidate();// for refreshing the chart
                progressdialog.dismiss();
                pieChart1.setData(pieData1);*/

        try {
            Cursor cursor1 = App_db.rawQuery("select * from EtPermitWa", null);
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    red1 = 0;
                    yellow1 = 0;
                    green1 = 0;
                    do {
                        if (cursor1.getString(6).equals("X")) {
                            red1 = red1 + 1;
                        } else if (cursor1.getString(7).equals("X")) {
                            yellow1 = yellow1 + 1;
                        } else if (cursor1.getString(8).equals("X")) {
                            green1 = green1 + 1;
                        }

                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
            }
        } catch (Exception e) {
            Log.v("EtNotifPlantTotal_exce", "" + e.getMessage());
            pieChart1.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            llo.clear();
            progressdialog.dismiss();
        }


       /* try {
            if (total_ppr_f != null && !total_ppr_f.equals("")) {
                total_ppr1 = Integer.parseInt(total_ppr_f.trim());
            } else {
                total_ppr1 = 0;
            }
            if (red_f != null && !red_f.equals("")) {
                red1 = Integer.parseInt(red_f.trim());
            } else {
                red1 = 0;
            }
            if (yellow_f != null && !yellow_f.equals("")) {
                yellow1 = Integer.parseInt(yellow_f.trim());
            } else {
                yellow1 = 0;
            }
            if (green_f != null && !green_f.equals("")) {
                green1 = Integer.parseInt(green_f.trim());
            } else {
                green1 = 0;
            }
        } catch (NumberFormatException nfe) {
        }*/

        final List<PieEntry> entries = new ArrayList<>();
        my_colors.clear();
        yel = "";
        gre = "";
        re = "";
        llo.clear();
        if (red1 != 0) {
            entries.add(new PieEntry(red1, getString(R.string.noissue)));
            my_colors.add(Color.rgb(255, 0, 0));
            re = "R";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.noissue));
            llo_o.setColor(Color.rgb(255, 0, 0));
            llo.add(llo_o);
        } else {
            red_color = 0;
            re = "";
        }
        if (yellow1 != 0) {
            entries.add(new PieEntry(yellow1, getString(R.string.partissue)));
            my_colors.add(Color.rgb(255, 255, 0));
            yel = "Y";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.partissue));
            llo_o.setColor(Color.rgb(255, 255, 0));
            llo.add(llo_o);
        } else {
            yellow_color = 0;
            yel = "";
        }
        if (green1 != 0) {
            entries.add(new PieEntry(green1, getString(R.string.issue)));
            my_colors.add(Color.rgb(0, 128, 0));
            gre = "G";
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.issue));
            llo_o.setColor(Color.rgb(0, 128, 0));
            llo.add(llo_o);
        } else {
            green_color = 0;
            gre = "";
        }
        DataSet1 = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : my_colors) colors.add(c);

        DataSet1.setColors(colors);
        DataSet1.setSliceSpace(2f);
        DataSet1.setValueLinePart1OffsetPercentage(80.f);
        DataSet1.setSelectionShift(5f);
        DataSet1.setValueLinePart1Length(0.2f);
        DataSet1.setValueLinePart2Length(0.4f);
        DataSet1.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        DataSet1.setValueFormatter(new MyValueFormatter());

        pieChart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int index = (int) h.getX();
                String label = entries.get(index).getLabel();

                cd = new ConnectionDetector(getContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (label.equals(getString(R.string.noissue))) {
                        String value = "Red";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.noissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals(getString(R.string.partissue))) {
                        String value = "Yellow";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.partissue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    } else if (label.equals(getString(R.string.issue))) {
                        String value = "Green";
                        Intent intent = new Intent(getActivity(), PermitStatusPieActivity.class);
                        intent.putExtra("value", value);
                        intent.putExtra("Iwerk_plant", iwerk);
                        intent.putExtra("Name_plant", plant_name);
                        intent.putExtra("workcenter", wrkcnt);
                        intent.putExtra("application", getString(R.string.issue));
                        intent.putExtra("wrk_name", wrkcnt_name);
                        startActivity(intent);
                    }
                } else {
                    noInternet();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Legend legend = pieChart1.getLegend();
        legend.setTextSize(14);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setEnabled(false);

        pieData1 = new PieData(DataSet1);
        pieData1.setValueTextSize(14);
        pieChart1.setUsePercentValues(false);


        Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
        label_list1.setAdapter(ll_a);

        pieChart1.setExtraOffsets(5, 5, 5, 5);
        pieChart1.highlightValues(null);
        pieChart1.animateY(1000);
        pieChart1.setScrollContainer(true);
        pieChart1.setVerticalScrollBarEnabled(true);
        pieChart1.getDescription().setEnabled(false);
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setRotationEnabled(true);
        pieChart1.setDrawEntryLabels(false);
        pieChart1.setDragDecelerationFrictionCoef(0.95f);
        if (!iwerk.equals("") && wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name);
            total_record.setText("Total Count : " + total_ppr_f);
        } else if (!iwerk.equals("") && !wrkcnt.equals("")) {
            pieChart1.setCenterText(plant_name + " " + wrkcnt_name);
//            total_record.setText("Total Count : " + total_ppr_f);
        } else {
            pieChart1.setCenterText("Work Approval Status");
            //  total_record.setText("Total Count : " + total_ppr);
        }
        pieChart1.setHoleColor(Color.LTGRAY);
        pieChart1.setTransparentCircleColor(Color.LTGRAY);
        pieChart1.setCenterTextSize(14);
        pieChart1.notifyDataSetChanged();//Required if changes are made to pie value
        pieChart1.invalidate();// for refreshing the chart
        progressdialog.dismiss();
        pieChart1.setData(pieData1);
    }


    public String getMonth_year() {
        return monthYear_s;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint())
            return;


        else ma.iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant_wrkcnt_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                Window window = plant_wrkcnt_dialog.getWindow();
                plant_wrkcnt_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                plant_wrkcnt_dialog.setContentView(R.layout.work_plant_filter_dialog);
                plant_wrkcnt_dialog.setCancelable(true);
                plant_wrkcnt_dialog.setCanceledOnTouchOutside(false);
                TextView clear_all_textview = (TextView) plant_wrkcnt_dialog.findViewById(R.id.clear_all_textview);
                plant_tv = (TextView) plant_wrkcnt_dialog.findViewById(R.id.plant_tv);
                wrkcntr_tv = (TextView) plant_wrkcnt_dialog.findViewById(R.id.wrkcntr_tv);
                Button close_filter_button = (Button) plant_wrkcnt_dialog.findViewById(R.id.close_filter_button);
                Button filter_button = (Button) plant_wrkcnt_dialog.findViewById(R.id.filter_button);

                if (pieChart1.getVisibility() == View.VISIBLE) {
                    if (!plant_name.equals("")) {
                        plant_tv.setText(plant_name);
                    }

                    if (!wrkcnt_name.equals("")) {
                        wrkcntr_tv.setText(wrkcnt_name);
                    }

                    clear_all_textview.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            plant_all = true;
                            plant_name = "";
                            iwerk = "";
                            wrkcnt_name = "";
                            wrkcnt = "";
                            iwerk_t = "";
                            wrkcnt_name_w = "";
                            wrkcnt_w = "";
                            plant_name_t = "";
                            plant_tv.setText(plant_name);
                            wrkcntr_tv.setText(wrkcnt_name);
                            for (int i = 0; i < pt_o.size(); i++) {
                                pt_o.get(i).setChecked("n");
                            }
                            if (art_fo.size() > 0) {
                                for (int i = 0; i < art_fo.size(); i++) {
                                    art_fo.get(i).setmChecked("n");
                                }
                            }
                            allPlant();
                            plant_wrkcnt_dialog.dismiss();
                        }
                    });
                }

                plant_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plant_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                        plant_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        plant_dialog.setCancelable(true);
                        plant_dialog.setCanceledOnTouchOutside(false);
                        plant_dialog.setContentView(R.layout.permit_plant_dialog);
                        plant_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                        TextView clear_all_textview = (TextView) plant_dialog.findViewById(R.id.clear_all_textview);
                        Button cancel_filter_button = (Button) plant_dialog.findViewById(R.id.cancel_filter_button);
                        Button ok_filter_button = (Button) plant_dialog.findViewById(R.id.ok_filter_button);
                        TextView no_data_textview = (TextView) plant_dialog.findViewById(R.id.no_data_textview);
                        listview = (ListView) plant_dialog.findViewById(R.id.listview);
                        if (listview != null) {
                            listview.setAdapter(null);
                        }
                        if (pt_o.size() <= 0) {
                            listview.setVisibility(View.GONE);
                            no_data_textview.setVisibility(View.VISIBLE);
                        } else {
                            listview.setVisibility(View.VISIBLE);
                            no_data_textview.setVisibility(View.GONE);
                            filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o);
                            listview.setAdapter(filter_plant_type_adapter);
                        }

                        cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (changed) {
                                    if (iwerk.equals("")) {
                                        if (iwerk_t.equals("")) {
                                            iwerk_tp = "";
                                            plant_name_tp = "";
                                            changed = false;
                                            plant_dialog.dismiss();
                                        } else if (!iwerk_t.equals("")) {
                                            iwerk_tp = "";
                                            plant_name_tp = "";
                                            changed = false;
                                            plant_dialog.dismiss();
                                        }
                                    } else {
                                        if (iwerk_t.equals("")) {
                                            changed = false;
                                            iwerk_tp = "";
                                            plant_name_tp = "";
                                            plant_dialog.dismiss();
                                        } else if (!iwerk_t.equals("")) {
                                            changed = false;
                                            iwerk_tp = "";
                                            plant_name_tp = "";
                                            plant_dialog.dismiss();
                                        }
                                    }
                                } else {
                                    if (iwerk.equals("")) {
                                        if (iwerk_t.equals("")) {
                                            plant_dialog.dismiss();
                                        } else if (!iwerk_t.equals("")) {
                                            plant_dialog.dismiss();
                                        }
                                    } else {
                                        if (iwerk_t.equals("")) {
                                            plant_dialog.dismiss();
                                        } else if (!iwerk_t.equals("")) {
                                            plant_dialog.dismiss();
                                        }
                                    }
                                }
                                changed = false;
                                plant_dialog.dismiss();

                            }
                        });

                        ok_filter_button.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
                                if (changed) {
                                    changed = false;
                                    iwerk_t = iwerk_tp;
                                    plant_name_t = plant_name_tp;
                                    iwerk_tp = "";
                                    plant_name_tp = "";
                                    wrkcnt_t = "";
                                    wrkcnt_name_t = "";
                                    plant_tv.setText(plant_name_t);
                                    wrkcntr_tv.setText(wrkcnt_name_t);
                                    plant_dialog.dismiss();
                                } else {
                                    if (iwerk_t.equals("") && iwerk_tp.equals("") && iwerk.equals(""))
                                        show_error_dialog("Please select PLANT");
                                    else
                                        plant_dialog.dismiss();
                                }
                            }
                        });
                        clear_all_textview.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
                                plant_all = true;
                                iwerk = "";
                                plant_name = "";
                                wrkcnt_name = "";
                                wrkcnt = "";
                                iwerk_t = "";
                                plant_name_t = "";
                                wrkcnt_name_t = "";
                                plant_clear = true;
                                wrkcnt_t = "";
                                wrkcnt_w = "";
                                wrkcnt_name_w = "";
                                for (int i = 0; i < pt_o.size(); i++) {
                                    pt_o.get(i).setChecked("n");
                                }
                                if (art_fo.size() > 0) {
                                    for (int j = 0; j < art_fo.size(); j++) {
                                        art_fo.get(j).setmChecked("n");
                                    }
                                }
                                plant_tv.setText(plant_name);
                                wrkcntr_tv.setText(wrkcnt_name);
                                filter_plant_type_adapter.notifyDataSetChanged();
                                listview.setAdapter(filter_plant_type_adapter);
                            }
                        });
                        plant_dialog.show();
                    }
                });

                wrkcntr_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!iwerk.equals("") && iwerk != null) {
                            if (iwerk_t.equals("") || iwerk_t == null) {
                                art_fo.clear();
                                for (int i = 0; i < art_o.size(); i++) {
                                    if (art_o.get(i).getIwerk().equals(iwerk)) {
                                        Mis_EtNotifArbplTotal_Object art_foo = new Mis_EtNotifArbplTotal_Object();
                                        art_foo.setmSwerk_art(art_o.get(i).getIwerk());
                                        art_foo.setmArbpl_art(art_o.get(i).getArbpl());
                                        art_foo.setmName_art(art_o.get(i).getName());
                                        art_foo.setmChecked("n");
                                        art_fo.add(art_foo);
                                    }
                                }

                                wrkcnt_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                                wrkcnt_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                wrkcnt_dialog.setCancelable(true);
                                wrkcnt_dialog.setCanceledOnTouchOutside(false);
                                wrkcnt_dialog.setContentView(R.layout.permit_plant_dialog);
                                wrkcnt_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) wrkcnt_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText("Select WorkCentre");
                                TextView clear_all_textview = (TextView) wrkcnt_dialog.findViewById(R.id.clear_all_textview);
                                Button cancel_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) wrkcnt_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) wrkcnt_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (art_fo.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                    listview.setAdapter(wkcenter_type_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        }
                                    }
                                });

                                ok_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (changed_w) {
                                            changed_w = false;
                                            wrkcnt_t = wrkcnt_w;
                                            wrkcnt_name_t = wrkcnt_name_w;
                                            wrkcnt_w = "";
                                            wrkcnt_name_w = "";
                                            wrkcntr_tv.setText(wrkcnt_name_t);
                                            wrkcnt_dialog.dismiss();
                                        } else {
                                            if (wrkcnt_t.equals("") && wrkcnt_w.equals("") && wrkcnt.equals(""))
                                                show_error_dialog("Please select WORK CENTRE");
                                            else
                                                wrkcnt_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        wrkcnt_name = "";
                                        wrkcnt = "";
                                        wrkcnt_name_t = "";
                                        wrkcnt_t = "";
                                        workcentre_clear = true;
                                        wrkcnt_name_w = "";
                                        wrkcnt_w = "";
                                        wrkcntr_tv.setText("");
                                        if (art_fo.size() > 0) {
                                            for (int j = 0; j < art_fo.size(); j++) {
                                                art_fo.get(j).setmChecked("n");
                                            }
                                        }
                                        wkcenter_type_adapter.notifyDataSetChanged();
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }
                                });

                            } else {

                                art_fo.clear();
                                for (int i = 0; i < art_o.size(); i++) {
                                    if (art_o.get(i).getIwerk().equals(iwerk_t)) {
                                        Mis_EtNotifArbplTotal_Object art_foo = new Mis_EtNotifArbplTotal_Object();
                                        art_foo.setmSwerk_art(art_o.get(i).getIwerk());
                                        art_foo.setmArbpl_art(art_o.get(i).getArbpl());
                                        art_foo.setmName_art(art_o.get(i).getName());
                                        art_foo.setmChecked("n");
                                        art_fo.add(art_foo);
                                    }
                                }

                                wrkcnt_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                                wrkcnt_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                wrkcnt_dialog.setCancelable(true);
                                wrkcnt_dialog.setCanceledOnTouchOutside(false);
                                wrkcnt_dialog.setContentView(R.layout.permit_plant_dialog);
                                wrkcnt_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) wrkcnt_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText("Select WorkCentre");
                                TextView clear_all_textview = (TextView) wrkcnt_dialog.findViewById(R.id.clear_all_textview);
                                Button cancel_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) wrkcnt_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) wrkcnt_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (art_fo.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                    listview.setAdapter(wkcenter_type_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        }
                                    }
                                });

                                ok_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            changed_w = false;
                                            wrkcnt_t = wrkcnt_w;
                                            wrkcnt_name_t = wrkcnt_name_w;
                                            wrkcnt_w = "";
                                            wrkcnt_name_w = "";
                                            wrkcntr_tv.setText(wrkcnt_name_t);
                                            wrkcnt_dialog.dismiss();
                                        } else {
                                            if (wrkcnt_t.equals("") && wrkcnt_w.equals("") && wrkcnt.equals(""))
                                                show_error_dialog("Please select WORK CENTRE");
                                            else
                                                wrkcnt_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        wrkcnt_name = "";
                                        wrkcnt = "";
                                        wrkcnt_name_t = "";
                                        wrkcnt_t = "";
                                        workcentre_clear = true;
                                        wrkcnt_name_w = "";
                                        wrkcnt_w = "";
                                        wrkcntr_tv.setText("");
                                        if (art_fo.size() > 0) {
                                            for (int j = 0; j < art_fo.size(); j++) {
                                                art_fo.get(j).setmChecked("n");
                                            }
                                        }
                                        wkcenter_type_adapter.notifyDataSetChanged();
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }
                                });


                            }
                            wrkcnt_dialog.show();
                        } else if (iwerk.equals("") || iwerk == null) {
                            if (iwerk_t.equals("") || iwerk_t == null) {
                                show_error_dialog("Please select PLANT");
                            } else {
                                art_fo.clear();
                                for (int i = 0; i < art_o.size(); i++) {
                                    if (art_o.get(i).getIwerk().equals(iwerk_t)) {
                                        Mis_EtNotifArbplTotal_Object art_foo = new Mis_EtNotifArbplTotal_Object();
                                        art_foo.setmSwerk_art(art_o.get(i).getIwerk());
                                        art_foo.setmArbpl_art(art_o.get(i).getArbpl());
                                        art_foo.setmName_art(art_o.get(i).getName());
                                        art_foo.setmChecked("n");
                                        art_fo.add(art_foo);
                                    }
                                }

                                wrkcnt_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                                wrkcnt_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                wrkcnt_dialog.setCancelable(true);
                                wrkcnt_dialog.setCanceledOnTouchOutside(false);
                                wrkcnt_dialog.setContentView(R.layout.permit_plant_dialog);
                                wrkcnt_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) wrkcnt_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText("Select WorkCentre");
                                TextView clear_all_textview = (TextView) wrkcnt_dialog.findViewById(R.id.clear_all_textview);
                                Button cancel_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) wrkcnt_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) wrkcnt_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) wrkcnt_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (art_fo.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                    listview.setAdapter(wkcenter_type_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    changed_w = false;
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    changed_w = false;
                                                    wrkcnt_w = "";
                                                    wrkcnt_name_w = "";
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (wrkcnt.equals("")) {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            } else {
                                                if (wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                } else if (!wrkcnt_t.equals("")) {
                                                    wrkcnt_dialog.dismiss();
                                                }
                                            }
                                        }
                                    }
                                });

                                ok_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            changed_w = false;
                                            wrkcnt_t = wrkcnt_w;
                                            wrkcnt_name_t = wrkcnt_name_w;
                                            wrkcnt_w = "";
                                            wrkcnt_name_w = "";
                                            wrkcntr_tv.setText(wrkcnt_name_t);
                                            wrkcnt_dialog.dismiss();
                                        } else {
                                            if (wrkcnt_t.equals("") && wrkcnt_w.equals("") && wrkcnt.equals(""))
                                                show_error_dialog("Please select WORK CENTRE");
                                            else
                                                wrkcnt_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        wrkcnt_name = "";
                                        wrkcnt = "";
                                        wrkcnt_name_t = "";
                                        wrkcnt_t = "";
                                        workcentre_clear = true;
                                        wrkcnt_name_w = "";
                                        wrkcnt_w = "";
                                        wrkcntr_tv.setText("");
                                        if (art_fo.size() > 0) {
                                            for (int j = 0; j < art_fo.size(); j++) {
                                                art_fo.get(j).setmChecked("n");
                                            }
                                        }
                                        wkcenter_type_adapter.notifyDataSetChanged();
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }
                                });
                                wrkcnt_dialog.show();
                            }
                        }
                    }
                });

                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!iwerk.equals("")) {
                            if (!iwerk_t.equals("")) {
                                if (iwerk.equals(iwerk_t)) {
                                    iwerk_t = "";
                                    wrkcnt_t = "";
                                    plant_name_t = "";
                                    wrkcnt_name_t = "";
                                    plantWrkcntSelector(iwerk, wrkcnt);
                                    plant_wrkcnt_dialog.dismiss();
                                } else {
                                    if (wrkcnt_t.equals("") || wrkcnt_t == null) {
                                        iwerk = iwerk_t;
                                        plant_name = plant_name_t;
                                        iwerk_t = "";
                                        plant_name_t = "";
                                        wrkcnt = "";
                                        wrkcnt_name = "";
                                        plantWrkcntSelector(iwerk, wrkcnt);
                                        plant_wrkcnt_dialog.dismiss();
                                    } else {
                                        iwerk = iwerk_t;
                                        wrkcnt = wrkcnt_t;
                                        wrkcnt_name = wrkcnt_name_t;
                                        plant_name = plant_name_t;
                                        iwerk_t = "";
                                        plant_name_t = "";
                                        wrkcnt_name_t = "";
                                        wrkcnt_t = "";
                                        plantWrkcntSelector(iwerk, wrkcnt);
                                        plant_wrkcnt_dialog.dismiss();
                                    }
                                }
                            } else {
                                if (!wrkcnt.equals("")) {
                                    if (wrkcnt_t.equals("")) {
                                        plant_wrkcnt_dialog.dismiss();
                                    } else {
                                        if (!wrkcnt.equals(wrkcnt_t)) {
                                            wrkcnt = wrkcnt_t;
                                            wrkcnt_name = wrkcnt_name_t;
                                            wrkcnt_name_t = "";
                                            wrkcnt_t = "";
                                            plantWrkcntSelector(iwerk, wrkcnt);
                                            plant_wrkcnt_dialog.dismiss();
                                        } else {
                                            wrkcnt_name_t = "";
                                            wrkcnt_t = "";
                                            plant_wrkcnt_dialog.dismiss();
                                        }
                                    }

                                } else {
                                    wrkcnt = wrkcnt_t;
                                    wrkcnt_name = wrkcnt_name_t;
                                    wrkcnt_t = "";
                                    wrkcnt_name_t = "";
                                    plantWrkcntSelector(iwerk, wrkcnt);
                                    plant_wrkcnt_dialog.dismiss();
                                }
                            }
                        } else {
                            if (iwerk_t.equals("")) {
                                allPlant();
                                plant_wrkcnt_dialog.dismiss();
                            } else {
                                if (wrkcnt_t.equals("") || wrkcnt_t == null) {
                                    iwerk = iwerk_t;
                                    plant_name = plant_name_t;
                                    iwerk_t = "";
                                    plant_name_t = "";
                                    plantWrkcntSelector(iwerk, wrkcnt);
                                    plant_wrkcnt_dialog.dismiss();
                                } else {
                                    iwerk = iwerk_t;
                                    wrkcnt = wrkcnt_t;
                                    wrkcnt_name = wrkcnt_name_t;
                                    plant_name = plant_name_t;
                                    iwerk_t = "";
                                    plant_name_t = "";
                                    wrkcnt_name_t = "";
                                    wrkcnt_t = "";
                                    plantWrkcntSelector(iwerk, wrkcnt);
                                    plant_wrkcnt_dialog.dismiss();
                                }
                            }
                        }
                    }
                });

                close_filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!iwerk_t.equals("")) {
                            iwerk_t = "";
                            plant_name_t = "";
                        }
                        if (!wrkcnt_t.equals("")) {
                            wrkcnt_t = "";
                            wrkcnt_name_t = "";
                        }
                        if (plant_clear) {
                            allPlant();
                            plant_clear = false;
                        } else if (workcentre_clear) {
                            plantWrkcntSelector(iwerk, wrkcnt);
                            workcentre_clear = false;
                        }
                        plant_wrkcnt_dialog.dismiss();
                    }
                });
                plant_wrkcnt_dialog.show();
            }
        });

    }

}
