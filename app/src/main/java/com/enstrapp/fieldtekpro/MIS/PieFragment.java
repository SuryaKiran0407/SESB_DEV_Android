package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.login.Login_Activity;
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

public class PieFragment extends Fragment {
    String plantName_s = "", wrkcntrName_s = "",
            swerk_s = "", wrkcntr_s = "", swerkT_s = "", wrkcntrNameW_s = "", wrkcntrW_s = "",
            plantNameT_s = "", swerkTp_s = "", plantNameTp_s = "", wrkcntrT_s = "",
            wrkcntrNameT_s = "";
    boolean changed_b = false, plantClear_b = false, changedW_b = false,
            wrkcntrClear_b = false;
    Button getReport_bt;
    String out_s = "", inpr_s = "", comp_s = "", dele_s = "", monthYear_s = "", selected_month = "", selected_year = "";
    float out_f, inpr_f, comp_f;
    String NotifAnalysis_Status = "";
    int req_monthyear = 0;
    boolean plantAll_b = false;
    Button monthYear_bt;
    Notification_Analysis_Activity ma;

    TextView noData_tv, plant_tv, wrkcntr_tv;
    private static int count = 0;
    ListView listview;
    Dialog filter_dialog, plant_dialog, wrkcntr_dialog, error_dialog;
    SimpleDateFormat inputFormat, outputFormat;
    ProgressDialog progressdialog;
    ListView label_list;
    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;
    FILTER_PLANT_TYPE_Adapter filter_plant_type_adapter;
    WKCENTER_TYPE_ADAPTER wkcenter_type_adapter;


    SQLiteDatabase FieldTekPro_db;
    private String DATABASE_NAME = "";
    ArrayList<Label_List_Object> llo = new ArrayList<Label_List_Object>();

    ArrayList<Mis_EtNotifPlantTotal_Object> pt_o = new ArrayList<>();
    ArrayList<Mis_EtNotifArbplTotal_Object> art_o = new ArrayList<>();
    ArrayList<Mis_EtNotifTypeTotal_Object> tt_o = new ArrayList<>();
    ArrayList<Mis_EtNotifArbplTotal_Object> art_fo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mis_notif_analysis_fragment, container, false);

        monthYear_bt = rootView.findViewById(R.id.monthYear_bt);
        pieChart = rootView.findViewById(R.id.pieChart);
        label_list = rootView.findViewById(R.id.label_list);
        noData_tv = rootView.findViewById(R.id.no_data_textview);
        ma = (Notification_Analysis_Activity) this.getActivity();
        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

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

        monthYear_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MonthYearPickerDialog.class);
                startActivityForResult(intent, req_monthyear);
            }
        });



        String webservice_type = getString(R.string.webservice_type);
        if(webservice_type.equalsIgnoreCase("odata"))
        {
            new Get_NotifAnalysis_Data_().execute(selected_month, selected_year);
        }
        else
        {
            new Get_NotifAnalysis_Data_REST().execute(selected_month, selected_year);
        }



        return rootView;
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
        else {
            ma.iv_filter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    filter_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                    Window window = filter_dialog.getWindow();
                    filter_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                    filter_dialog.setContentView(R.layout.work_plant_filter_dialog);
                    filter_dialog.setCancelable(true);
                    filter_dialog.setCanceledOnTouchOutside(false);
                    TextView clear_all_textview = filter_dialog.findViewById(R.id.clear_all_textview);
                    plant_tv = filter_dialog.findViewById(R.id.plant_tv);
                    wrkcntr_tv = filter_dialog.findViewById(R.id.wrkcntr_tv);
                    Button close_filter_button = filter_dialog.findViewById(R.id.close_filter_button);
                    Button filter_button = filter_dialog.findViewById(R.id.filter_button);

                    if (!plantName_s.equals("")) {
                        plant_tv.setText(plantName_s);
                    }

                    if (!wrkcntrName_s.equals("")) {
                        wrkcntr_tv.setText(wrkcntrName_s);
                    }

                    clear_all_textview.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plantAll_b = true;
                            plantName_s = "";
                            swerk_s = "";
                            wrkcntrName_s = "";
                            wrkcntr_s = "";
                            swerkT_s = "";
                            wrkcntrNameW_s = "";
                            wrkcntrW_s = "";
                            plantNameT_s = "";
                            plant_tv.setText(plantName_s);
                            wrkcntr_tv.setText(wrkcntrName_s);
                            for (int i = 0; i < pt_o.size(); i++) {
                                pt_o.get(i).setmChecked("n");
                            }

                            if (art_fo.size() > 0) {
                                for (int i = 0; i < art_fo.size(); i++) {
                                    art_fo.get(i).setmChecked("n");
                                }
                            }
                            allPlant();
                            filter_dialog.dismiss();
                        }
                    });

                    plant_tv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plant_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
                            plant_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            plant_dialog.setCancelable(true);
                            plant_dialog.setCanceledOnTouchOutside(false);
                            plant_dialog.setContentView(R.layout.permit_plant_dialog);
                            plant_dialog.getWindow().getAttributes().windowAnimations =
                                    R.style.ErrorDialog;
                            TextView clear_all_textview = plant_dialog
                                    .findViewById(R.id.clear_all_textview);
                            Button cancel_filter_button = plant_dialog
                                    .findViewById(R.id.cancel_filter_button);
                            Button ok_filter_button = plant_dialog.findViewById(R.id.ok_filter_button);
                            TextView no_data_textview = plant_dialog
                                    .findViewById(R.id.no_data_textview);
                            listview = plant_dialog.findViewById(R.id.listview);

                            if (listview != null) {
                                listview.setAdapter(null);
                            }

                            if (pt_o.size() <= 0) {
                                listview.setVisibility(View.GONE);
                                no_data_textview.setVisibility(View.VISIBLE);
                            } else {
                                listview.setVisibility(View.VISIBLE);
                                no_data_textview.setVisibility(View.GONE);
                                filter_plant_type_adapter =
                                        new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o);
                                listview.setAdapter(filter_plant_type_adapter);
                            }

                            clear_all_textview.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    plantAll_b = true;
                                    swerk_s = "";
                                    plantName_s = "";
                                    wrkcntrName_s = "";
                                    wrkcntr_s = "";
                                    swerkT_s = "";
                                    plantNameT_s = "";
                                    wrkcntrNameT_s = "";
                                    plantClear_b = true;
                                    wrkcntrT_s = "";
                                    wrkcntrW_s = "";
                                    wrkcntrNameW_s = "";

                                    for (int i = 0; i < pt_o.size(); i++) {
                                        pt_o.get(i).setmChecked("n");
                                    }

                                    if (art_fo.size() > 0) {
                                        for (int j = 0; j < art_fo.size(); j++) {
                                            art_fo.get(j).setmChecked("n");
                                        }
                                    }
                                    plant_tv.setText(plantName_s);
                                    wrkcntr_tv.setText(wrkcntrName_s);
                                    filter_plant_type_adapter.notifyDataSetChanged();
                                    listview.setAdapter(filter_plant_type_adapter);
                                }
                            });

                            cancel_filter_button.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (changed_b) {

                                        if (swerk_s.equals("")) {

                                            if (swerkT_s.equals("")) {
                                                swerkTp_s = "";
                                                plantNameTp_s = "";
                                                changed_b = false;
                                                plant_dialog.dismiss();
                                            } else if (!swerkT_s.equals("")) {
                                                swerkTp_s = "";
                                                plantNameTp_s = "";
                                                changed_b = false;
                                                plant_dialog.dismiss();
                                            }
                                        } else {

                                            if (swerkT_s.equals("")) {
                                                changed_b = false;
                                                swerkTp_s = "";
                                                plantNameTp_s = "";
                                                plant_dialog.dismiss();
                                            } else if (!swerkT_s.equals("")) {
                                                changed_b = false;
                                                swerkTp_s = "";
                                                plantNameTp_s = "";
                                                plant_dialog.dismiss();
                                            }
                                        }
                                    } else {

                                        if (swerk_s.equals("")) {

                                            if (swerkT_s.equals("")) {
                                                plant_dialog.dismiss();
                                            } else if (!swerkT_s.equals("")) {
                                                plant_dialog.dismiss();
                                            }
                                        } else {

                                            if (swerkT_s.equals("")) {
                                                plant_dialog.dismiss();
                                            } else if (!swerkT_s.equals("")) {
                                                plant_dialog.dismiss();
                                            }
                                        }
                                    }
                                    changed_b = false;
                                    plant_dialog.dismiss();
                                }
                            });

                            ok_filter_button.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (changed_b) {
                                        changed_b = false;
                                        swerkT_s = swerkTp_s;
                                        plantNameT_s = plantNameTp_s;
                                        swerkTp_s = "";
                                        plantNameTp_s = "";
                                        wrkcntrT_s = "";
                                        wrkcntrNameT_s = "";
                                        plant_tv.setText(plantNameT_s);
                                        wrkcntr_tv.setText(wrkcntrNameT_s);
                                        plant_dialog.dismiss();
                                    } else {

                                        if (swerkT_s.equals("") && swerkTp_s.equals("")
                                                && swerk_s.equals(""))
                                            show_error_dialog(getString(R.string.slct_palnt));
                                        else
                                            plant_dialog.dismiss();
                                    }
                                }
                            });
                            plant_dialog.show();
                        }
                    });

                    wrkcntr_tv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!swerk_s.equals("") && swerk_s != null) {

                                if (swerkT_s.equals("") || swerkT_s == null) {
                                    art_fo.clear();

                                    for (int i = 0; i < art_o.size(); i++) {

                                        if (art_o.get(i).getmSwerk_art().equals(swerk_s)) {
                                            Mis_EtNotifArbplTotal_Object art_foo =
                                                    new Mis_EtNotifArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o.get(i).getmName_art());
                                            art_foo.setmQmart_art(art_o.get(i).getmQmart_art());
                                            art_foo.setmQmartx_art(art_o.get(i).getmQmartx_art());
                                            art_foo.setmOuts_art(art_o.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo.add(art_foo);
                                        }
                                    }

                                    wrkcntr_dialog = new Dialog(getActivity(),
                                            R.style.AppThemeDialog_Dark);
                                    wrkcntr_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    wrkcntr_dialog.setCancelable(true);
                                    wrkcntr_dialog.setCanceledOnTouchOutside(false);
                                    wrkcntr_dialog.setContentView(R.layout.permit_plant_dialog);
                                    wrkcntr_dialog.getWindow().getAttributes().windowAnimations =
                                            R.style.ErrorDialog;
                                    TextView select_wrkcnt = wrkcntr_dialog
                                            .findViewById(R.id.select_notif);
                                    select_wrkcnt.setText(getString(R.string.slct_wrkcntr));
                                    TextView clear_all_textview = wrkcntr_dialog
                                            .findViewById(R.id.clear_all_textview);
                                    Button cancel_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.cancel_filter_button);
                                    Button ok_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.ok_filter_button);
                                    TextView no_data_textview = wrkcntr_dialog
                                            .findViewById(R.id.no_data_textview);
                                    listview = wrkcntr_dialog.findViewById(R.id.listview);

                                    if (listview != null) {
                                        listview.setAdapter(null);
                                    }

                                    if (art_fo.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter =
                                                new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            } else {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    ok_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {
                                                changedW_b = false;
                                                wrkcntrT_s = wrkcntrW_s;
                                                wrkcntrNameT_s = wrkcntrNameW_s;
                                                wrkcntrW_s = "";
                                                wrkcntrNameW_s = "";
                                                wrkcntr_tv.setText(wrkcntrNameT_s);
                                                wrkcntr_dialog.dismiss();
                                            } else {

                                                if (wrkcntrT_s.equals("") && wrkcntrW_s.equals("")
                                                        && wrkcntr_s.equals(""))
                                                    show_error_dialog(getString(R.string.wrkCntr_mandate));
                                                else
                                                    wrkcntr_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcntrName_s = "";
                                            wrkcntr_s = "";
                                            wrkcntrNameT_s = "";
                                            wrkcntrT_s = "";
                                            wrkcntrClear_b = true;
                                            wrkcntrNameW_s = "";
                                            wrkcntrW_s = "";
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

                                        if (art_o.get(i).getmSwerk_art().equals(swerkT_s)) {
                                            Mis_EtNotifArbplTotal_Object art_foo =
                                                    new Mis_EtNotifArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o.get(i).getmName_art());
                                            art_foo.setmQmart_art(art_o.get(i).getmQmart_art());
                                            art_foo.setmQmartx_art(art_o.get(i).getmQmartx_art());
                                            art_foo.setmOuts_art(art_o.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo.add(art_foo);
                                        }
                                    }

                                    wrkcntr_dialog = new Dialog(getActivity(),
                                            R.style.AppThemeDialog_Dark);
                                    wrkcntr_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    wrkcntr_dialog.setCancelable(true);
                                    wrkcntr_dialog.setCanceledOnTouchOutside(false);
                                    wrkcntr_dialog.setContentView(R.layout.permit_plant_dialog);
                                    wrkcntr_dialog.getWindow().getAttributes().windowAnimations =
                                            R.style.ErrorDialog;
                                    TextView select_wrkcnt = wrkcntr_dialog
                                            .findViewById(R.id.select_notif);
                                    select_wrkcnt.setText(getString(R.string.slct_wrkcntr));
                                    TextView clear_all_textview = wrkcntr_dialog
                                            .findViewById(R.id.clear_all_textview);
                                    Button cancel_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.cancel_filter_button);
                                    Button ok_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.ok_filter_button);
                                    TextView no_data_textview = wrkcntr_dialog
                                            .findViewById(R.id.no_data_textview);
                                    listview = wrkcntr_dialog.findViewById(R.id.listview);

                                    if (listview != null) {
                                        listview.setAdapter(null);
                                    }

                                    if (art_fo.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter =
                                                new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            } else {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    ok_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {
                                                changedW_b = false;
                                                wrkcntrT_s = wrkcntrW_s;
                                                wrkcntrNameT_s = wrkcntrNameW_s;
                                                wrkcntrW_s = "";
                                                wrkcntrNameW_s = "";
                                                wrkcntr_tv.setText(wrkcntrNameT_s);
                                                wrkcntr_dialog.dismiss();
                                            } else {

                                                if (wrkcntrT_s.equals("") && wrkcntrW_s.equals("")
                                                        && wrkcntr_s.equals(""))
                                                    show_error_dialog(getString(R.string.wrkCntr_mandate));
                                                else
                                                    wrkcntr_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcntrName_s = "";
                                            wrkcntr_s = "";
                                            wrkcntrNameT_s = "";
                                            wrkcntrT_s = "";
                                            wrkcntrClear_b = true;
                                            wrkcntrNameW_s = "";
                                            wrkcntrW_s = "";
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
                                wrkcntr_dialog.show();
                            } else if (swerk_s.equals("") || swerk_s == null) {

                                if (swerkT_s.equals("") || swerkT_s == null) {
                                    show_error_dialog(getString(R.string.slct_palnt));
                                } else {
                                    art_fo.clear();

                                    for (int i = 0; i < art_o.size(); i++) {

                                        if (art_o.get(i).getmSwerk_art().equals(swerkT_s)) {
                                            Mis_EtNotifArbplTotal_Object art_foo =
                                                    new Mis_EtNotifArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o.get(i).getmName_art());
                                            art_foo.setmQmart_art(art_o.get(i).getmQmart_art());
                                            art_foo.setmQmartx_art(art_o.get(i).getmQmartx_art());
                                            art_foo.setmOuts_art(art_o.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo.add(art_foo);
                                        }
                                    }

                                    wrkcntr_dialog = new Dialog(getActivity(),
                                            R.style.AppThemeDialog_Dark);
                                    wrkcntr_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    wrkcntr_dialog.setCancelable(true);
                                    wrkcntr_dialog.setCanceledOnTouchOutside(false);
                                    wrkcntr_dialog.setContentView(R.layout.permit_plant_dialog);
                                    wrkcntr_dialog.getWindow().getAttributes().windowAnimations =
                                            R.style.ErrorDialog;
                                    TextView select_wrkcnt = wrkcntr_dialog
                                            .findViewById(R.id.select_notif);
                                    select_wrkcnt.setText(getString(R.string.slct_wrkcntr));
                                    TextView clear_all_textview = wrkcntr_dialog
                                            .findViewById(R.id.clear_all_textview);
                                    Button cancel_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.cancel_filter_button);
                                    Button ok_filter_button = wrkcntr_dialog
                                            .findViewById(R.id.ok_filter_button);
                                    TextView no_data_textview = wrkcntr_dialog
                                            .findViewById(R.id.no_data_textview);
                                    listview = wrkcntr_dialog.findViewById(R.id.listview);

                                    if (listview != null) {
                                        listview.setAdapter(null);
                                    }

                                    if (art_fo.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter =
                                                new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        changedW_b = false;
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        changedW_b = false;
                                                        wrkcntrW_s = "";
                                                        wrkcntrNameW_s = "";
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            } else {

                                                if (wrkcntr_s.equals("")) {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                } else {

                                                    if (wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    } else if (!wrkcntrT_s.equals("")) {
                                                        wrkcntr_dialog.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    ok_filter_button.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changedW_b) {
                                                changedW_b = false;
                                                wrkcntrT_s = wrkcntrW_s;
                                                wrkcntrNameT_s = wrkcntrNameW_s;
                                                wrkcntrW_s = "";
                                                wrkcntrNameW_s = "";
                                                wrkcntr_tv.setText(wrkcntrNameT_s);
                                                wrkcntr_dialog.dismiss();
                                            } else {

                                                if (wrkcntrT_s.equals("") && wrkcntrW_s.equals("")
                                                        && wrkcntr_s.equals(""))
                                                    show_error_dialog(getString(R.string.wrkCntr_mandate));
                                                else
                                                    wrkcntr_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcntrName_s = "";
                                            wrkcntr_s = "";
                                            wrkcntrNameT_s = "";
                                            wrkcntrT_s = "";
                                            wrkcntrClear_b = true;
                                            wrkcntrNameW_s = "";
                                            wrkcntrW_s = "";
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
                                    wrkcntr_dialog.show();
                                }
                            }
                        }
                    });

                    close_filter_button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!swerkT_s.equals("")) {
                                swerkT_s = "";
                                plantNameT_s = "";
                            }

                            if (!wrkcntrT_s.equals("")) {
                                wrkcntrT_s = "";
                                wrkcntrNameT_s = "";
                            }

                            if (plantClear_b) {
                                allPlant();
                                plantClear_b = false;
                            } else if (wrkcntrClear_b) {
                                plantWrkcntSelector(swerk_s, wrkcntr_s);
                                wrkcntrClear_b = false;
                            }
                            filter_dialog.dismiss();
                        }
                    });

                    filter_button.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!swerk_s.equals("")) {

                                if (!swerkT_s.equals("")) {

                                    if (swerk_s.equals(swerkT_s)) {
                                        swerkT_s = "";
                                        wrkcntrT_s = "";
                                        plantNameT_s = "";
                                        wrkcntrNameT_s = "";
                                        plantWrkcntSelector(swerk_s, wrkcntr_s);
                                        filter_dialog.dismiss();
                                    } else {

                                        if (wrkcntrT_s.equals("") || wrkcntrT_s == null) {
                                            swerk_s = swerkT_s;
                                            plantName_s = plantNameT_s;
                                            swerkT_s = "";
                                            plantNameT_s = "";
                                            wrkcntr_s = "";
                                            wrkcntrName_s = "";
                                            plantWrkcntSelector(swerk_s, wrkcntr_s);
                                            filter_dialog.dismiss();
                                        } else {
                                            swerk_s = swerkT_s;
                                            wrkcntr_s = wrkcntrT_s;
                                            wrkcntrName_s = wrkcntrNameT_s;
                                            plantName_s = plantNameT_s;
                                            swerkT_s = "";
                                            plantNameT_s = "";
                                            wrkcntrNameT_s = "";
                                            wrkcntrT_s = "";
                                            plantWrkcntSelector(swerk_s, wrkcntr_s);
                                            filter_dialog.dismiss();
                                        }
                                    }
                                } else {

                                    if (!wrkcntr_s.equals("")) {

                                        if (wrkcntrT_s.equals("")) {
                                            filter_dialog.dismiss();
                                        } else {

                                            if (!wrkcntr_s.equals(wrkcntrT_s)) {
                                                wrkcntr_s = wrkcntrT_s;
                                                wrkcntrName_s = wrkcntrNameT_s;
                                                wrkcntrNameT_s = "";
                                                wrkcntrT_s = "";
                                                plantWrkcntSelector(swerk_s, wrkcntr_s);
                                                filter_dialog.dismiss();
                                            } else {
                                                wrkcntrNameT_s = "";
                                                wrkcntrT_s = "";
                                                filter_dialog.dismiss();
                                            }
                                        }

                                    } else {
                                        wrkcntr_s = wrkcntrT_s;
                                        wrkcntrName_s = wrkcntrNameT_s;
                                        wrkcntrT_s = "";
                                        wrkcntrNameT_s = "";
                                        plantWrkcntSelector(swerk_s, wrkcntr_s);
                                        filter_dialog.dismiss();
                                    }
                                }
                            } else {

                                if (swerkT_s.equals("")) {
                                    allPlant();
                                    filter_dialog.dismiss();
                                } else {

                                    if (wrkcntrT_s.equals("") || wrkcntrT_s == null) {
                                        swerk_s = swerkT_s;
                                        plantName_s = plantNameT_s;
                                        swerkT_s = "";
                                        plantNameT_s = "";
                                        plantWrkcntSelector(swerk_s, wrkcntr_s);
                                        filter_dialog.dismiss();
                                    } else {
                                        swerk_s = swerkT_s;
                                        wrkcntr_s = wrkcntrT_s;
                                        wrkcntrName_s = wrkcntrNameT_s;
                                        plantName_s = plantNameT_s;
                                        swerkT_s = "";
                                        plantNameT_s = "";
                                        wrkcntrNameT_s = "";
                                        wrkcntrT_s = "";
                                        plantWrkcntSelector(swerk_s, wrkcntr_s);
                                        filter_dialog.dismiss();
                                    }
                                }
                            }
                        }
                    });
                    filter_dialog.show();
                }
            });
        }
    }

    public void allPlant() {
        try {
            if (out_s != null && !out_s.equals("")) {
                out_f = Float.parseFloat(out_s.trim());
            } else {
                out_f = 0;
            }
            if (inpr_s != null && !inpr_s.equals("")) {
                inpr_f = Float.parseFloat(inpr_s.trim());
            } else {
                inpr_f = 0;
            }
            if (comp_s != null && !comp_s.equals("")) {
                comp_f = Float.parseFloat(comp_s.trim());
            } else {
                comp_f = 0;
            }
        } catch (NumberFormatException nfe) {
            Log.v("String_to_ Float", "" + nfe.getMessage());
            pieChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);

            progressdialog.dismiss();
        }

        llo.clear();
        final List<PieEntry> entries = new ArrayList<>();
        if (out_f == 0) {

        } else {
            entries.add(new PieEntry(out_f, getString(R.string.outstanding)));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.outstanding));
            llo.add(llo_o);
        }
        if (inpr_f == 0) {

        } else {
            entries.add(new PieEntry(inpr_f, getString(R.string.inprgs)));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.inprgs));
            llo.add(llo_o);
        }
        if (comp_f == 0) {

        } else {
            entries.add(new PieEntry(comp_f, getString(R.string.compltd)));
            Label_List_Object llo_o = new Label_List_Object();
            llo_o.setLabel(getString(R.string.compltd));
            llo.add(llo_o);
        }

        pieDataSet = new PieDataSet(entries, "");

        final int[] MY_COLORS = new int[entries.size()];

        for (int j = 0; j < entries.size(); j++) {
            if (entries.get(j).getLabel().equals(getString(R.string.outstanding))) {
                MY_COLORS[j] = Color.rgb(255, 255, 0);
                llo.get(j).setColor(Color.rgb(255, 255, 0));
            } else if (entries.get(j).getLabel().equals(getString(R.string.inprgs))) {
                MY_COLORS[j] = Color.rgb(51, 51, 255);
                llo.get(j).setColor(Color.rgb(51, 51, 255));
            } else if (entries.get(j).getLabel().equals(getString(R.string.compltd))) {
                MY_COLORS[j] = Color.rgb(50, 205, 50);
                llo.get(j).setColor(Color.rgb(50, 205, 50));
            }
        }

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS) colors.add(c);

        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueLinePart1OffsetPercentage(80.f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueFormatter(new MyValueFormatter());

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int index = (int) h.getX();
                String label = entries.get(index).getLabel();

                if (label.equals(getString(R.string.outstanding))) {
                    String value = getString(R.string.outstanding);
                    String phase = "1";
                    Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                    intent.putExtra("value", value);
                    intent.putExtra("phase", phase);
                    intent.putExtra("iwerk", "");
                    intent.putExtra("wrkcntr_s", "");
                    startActivity(intent);
                } else if (label.equals(getString(R.string.inprgs))) {
                    String value = getString(R.string.inprgs);
                    String phase = "3";
                    Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                    intent.putExtra("value", value);
                    intent.putExtra("phase", phase);
                    intent.putExtra("iwerk", "");
                    intent.putExtra("wrkcntr_s", "");
                    startActivity(intent);
                } else if (label.equals(getString(R.string.compltd))) {
                    String value = getString(R.string.compltd);
                    String phase = "4";
                    Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                    intent.putExtra("value", value);
                    intent.putExtra("phase", phase);
                    intent.putExtra("iwerk", "");
                    intent.putExtra("wrkcntr_s", "");
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

        Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
        label_list.setAdapter(ll_a);

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
        pieChart.setCenterText(getString(R.string.mis_notif_analysis));
        pieChart.setHoleColor(Color.LTGRAY);
        pieChart.setTransparentCircleColor(Color.LTGRAY);
        pieChart.setCenterTextSize(14);
        pieChart.notifyDataSetChanged();//Required if changes are made to pie value
        pieChart.invalidate();// for refreshing the chart
        progressdialog.dismiss();
        pieChart.setData(pieData);
    }

    protected void show_error_dialog(String string) {
        error_dialog = new Dialog(getActivity());
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setCancelable(false);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.setContentView(R.layout.error_dialog);
        final TextView description_textview = error_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) error_dialog.findViewById(R.id.ok_button);
        description_textview.setText(string);
        error_dialog.show();
        ok_button.setOnClickListener(new OnClickListener() {
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

        public WKCENTER_TYPE_ADAPTER(Context context,
                                     List<Mis_EtNotifArbplTotal_Object> mainDataList) {
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

            if (!wrkcntrW_s.equals("")) {

                for (int i = 0; i < art_fo.size(); i++) {

                    if (art_fo.get(i).getmArbpl_art().equals(wrkcntrW_s)) {
                        holder.radio_status.setChecked(true);
                        art_fo.get(i).setmChecked("y");
                    } else {
                        art_fo.get(i).setmChecked("n");
                    }
                }
            } else if (!wrkcntrT_s.equals("")) {

                for (int j = 0; j < art_fo.size(); j++) {

                    if (art_fo.get(j).getmArbpl_art().equals(wrkcntrT_s)) {
                        holder.radio_status.setChecked(true);
                        art_fo.get(j).setmChecked("y");
                    } else {
                        art_fo.get(j).setmChecked("n");
                    }
                }
            } else if (!wrkcntr_s.equals("")) {

                for (int j = 0; j < art_fo.size(); j++) {

                    if (art_fo.get(j).getmArbpl_art().equals(wrkcntr_s)) {
                        holder.radio_status.setChecked(true);
                        art_fo.get(j).setmChecked("y");
                    } else {
                        art_fo.get(j).setmChecked("n");
                    }
                }
            }

            holder.radio_status.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String wkcenter = holder.radio_status.getText().toString();

                    if (holder.radio_status.isChecked()) {

                        for (int i = 0; i < art_fo.size(); i++) {
                            String new_wkcenter = art_fo.get(i).getmArbpl_art();
                            String new_wrkname = art_fo.get(i).getmName_art();

                            if (wkcenter.equalsIgnoreCase(new_wkcenter)) {
                                art_fo.get(i).setmChecked("y");
                                mainDataList.get(i).setmChecked("y");
                                wrkcntrW_s = new_wkcenter;
                                wrkcntrNameW_s = new_wrkname;
                                changedW_b = true;
                            } else {
                                String status_s = art_fo.get(i).getmChecked();

                                if (status_s.equals("y")) {
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
            });
            holder.radio_status.setText(mainDataList.get(position).getmArbpl_art());
            holder.radio_status.setTag(position);

            if (mainDataList.get(position).getmChecked().equals("y"))
                holder.radio_status.setChecked(true);
            else
                holder.radio_status.setChecked(false);

            holder.Name.setText(mainDataList.get(position).getmName_art());

            return view;
        }
    }

    public void plantWrkcntSelector(String plant, final String wrkcntr_s) {

        if (wrkcntr_s.equals("")) {

            if (pt_o.size() == 0) {
                pieChart.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            } else {
                pieChart.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);

                for (int i = 0; i < pt_o.size(); i++) {

                    if (pt_o.get(i).getmSwerk_pt().equals(plant)) {
                        try {

                            if (pt_o.get(i).getmOuts_pt() != null
                                    && !pt_o.get(i).getmOuts_pt().equals(""))
                                out_f = Float.parseFloat(pt_o.get(i).getmOuts_pt().trim());
                            else
                                out_f = 0;

                            if (pt_o.get(i).getmInpr_pt() != null
                                    && !pt_o.get(i).getmInpr_pt().equals(""))
                                inpr_f = Float.parseFloat(pt_o.get(i).getmInpr_pt().trim());
                            else
                                inpr_f = 0;

                            if (pt_o.get(i).getmComp_pt() != null
                                    && !pt_o.get(i).getmComp_pt().equals(""))
                                comp_f = Float.parseFloat(pt_o.get(i).getmComp_pt().trim());
                            else
                                comp_f = 0;

                        } catch (NumberFormatException nfe) {
                            Log.v("String_to_ Float", "" + nfe.getMessage());
                            pieChart.setVisibility(View.GONE);
                            noData_tv.setVisibility(View.VISIBLE);
                            progressdialog.dismiss();
                        }
                        break;
                    }
                }
                llo.clear();
                final List<PieEntry> entries = new ArrayList<>();

                if (out_f == 0) {

                } else {
                    entries.add(new PieEntry(out_f, getString(R.string.outstanding)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.outstanding));
                    llo.add(llo_o);
                }

                if (inpr_f == 0) {

                } else {
                    entries.add(new PieEntry(inpr_f, getString(R.string.inprgs)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.inprgs));
                    llo.add(llo_o);
                }

                if (comp_f == 0) {

                } else {
                    entries.add(new PieEntry(comp_f, getString(R.string.compltd)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.compltd));
                    llo.add(llo_o);
                }

                pieDataSet = new PieDataSet(entries, "");

                final int[] MY_COLORS = new int[entries.size()];

                for (int j = 0; j < entries.size(); j++) {

                    if (entries.get(j).getLabel().equals(getString(R.string.outstanding))) {
                        MY_COLORS[j] = Color.rgb(255, 255, 0);
                        llo.get(j).setColor(Color.rgb(255, 255, 0));
                    } else if (entries.get(j).getLabel().equals(getString(R.string.inprgs))) {
                        MY_COLORS[j] = Color.rgb(51, 51, 255);
                        llo.get(j).setColor(Color.rgb(51, 51, 255));
                    } else if (entries.get(j).getLabel().equals(getString(R.string.compltd))) {
                        MY_COLORS[j] = Color.rgb(50, 205, 50);
                        llo.get(j).setColor(Color.rgb(50, 205, 50));
                    }
                }

                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : MY_COLORS) colors.add(c);

                pieDataSet.setColors(colors);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setFormSize(5f);
                pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                pieDataSet.setSelectionShift(5f);
                pieDataSet.setValueLinePart1Length(0.2f);
                pieDataSet.setValueLinePart2Length(0.4f);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setValueFormatter(new MyValueFormatter());

                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        int index = (int) h.getX();
                        String label = entries.get(index).getLabel();

                        if (label.equals(getString(R.string.outstanding))) {
                            String value = getString(R.string.outstanding);
                            String phase = "1";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);

                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.inprgs))) {
                            String value = getString(R.string.inprgs);
                            String phase = "3";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            intent.putExtra("phase", phase);
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.compltd))) {
                            String value = getString(R.string.compltd);
                            String phase = "4";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            intent.putExtra("phase", phase);
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


                Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
                label_list.setAdapter(ll_a);

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
                pieChart.setCenterText(plantName_s);
                pieChart.setHoleColor(Color.LTGRAY);
                pieChart.setTransparentCircleColor(Color.LTGRAY);
                pieChart.setCenterTextSize(14);
                pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                pieChart.invalidate();// for refreshing the chart
                progressdialog.dismiss();
                pieChart.setData(pieData);
            }
        } else {
            if (art_fo.size() <= 0) {
                pieChart.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            } else {
                pieChart.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);
                for (int i = 0; i < art_fo.size(); i++) {
                    if (art_fo.get(i).getmSwerk_art().equals(plant) && art_fo.get(i).getmArbpl_art().equals(wrkcntr_s)) {
                        try {
                            if (art_fo.get(i).getmOuts_art() != null && !art_fo.get(i).getmOuts_art().equals("")) {
                                out_f = Float.parseFloat(art_fo.get(i).getmOuts_art().trim());
                            } else {
                                out_f = 0;
                            }
                            if (art_fo.get(i).getmInpr_art() != null && !art_fo.get(i).getmInpr_art().equals("")) {
                                inpr_f = Float.parseFloat(art_fo.get(i).getmInpr_art().trim());
                            } else {
                                inpr_f = 0;
                            }
                            if (art_fo.get(i).getmComp_art() != null && !art_fo.get(i).getmComp_art().equals("")) {
                                comp_f = Float.parseFloat(art_fo.get(i).getmComp_art().trim());
                            } else {
                                comp_f = 0;
                            }
                        } catch (Exception e) {
                            Log.v("Notif_number_conv", "" + e.getMessage());
                            pieChart.setVisibility(View.GONE);
                            noData_tv.setVisibility(View.VISIBLE);
                            progressdialog.dismiss();
                        }
                    }
                }
                llo.clear();
                final List<PieEntry> entries = new ArrayList<>();
                if (out_f == 0) {

                } else {
                    entries.add(new PieEntry(out_f, getString(R.string.outstanding)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.outstanding));
                    llo.add(llo_o);
                }
                if (inpr_f == 0) {

                } else {
                    entries.add(new PieEntry(inpr_f, getString(R.string.inprgs)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.inprgs));
                    llo.add(llo_o);
                }
                if (comp_f == 0) {

                } else {
                    entries.add(new PieEntry(comp_f, getString(R.string.compltd)));
                    Label_List_Object llo_o = new Label_List_Object();
                    llo_o.setLabel(getString(R.string.compltd));
                    llo.add(llo_o);
                }

                pieDataSet = new PieDataSet(entries, "");

                final int[] MY_COLORS = new int[entries.size()];

                for (int j = 0; j < entries.size(); j++) {
                    if (entries.get(j).getLabel().equals(getString(R.string.outstanding))) {
                        MY_COLORS[j] = Color.rgb(255, 255, 0);
                        llo.get(j).setColor(Color.rgb(255, 255, 0));
                    } else if (entries.get(j).getLabel().equals(getString(R.string.inprgs))) {
                        MY_COLORS[j] = Color.rgb(51, 51, 255);
                        llo.get(j).setColor(Color.rgb(51, 51, 255));
                    } else if (entries.get(j).getLabel().equals(getString(R.string.compltd))) {
                        MY_COLORS[j] = Color.rgb(50, 205, 50);
                        llo.get(j).setColor(Color.rgb(50, 205, 50));
                    }
                }

                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : MY_COLORS) colors.add(c);

                pieDataSet.setColors(colors);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setFormSize(5f);
                pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                pieDataSet.setSelectionShift(5f);
                pieDataSet.setValueLinePart1Length(0.2f);
                pieDataSet.setValueLinePart2Length(0.4f);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setValueFormatter(new MyValueFormatter());

                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        int index = (int) h.getX();
                        String label = entries.get(index).getLabel();

                        if (label.equals(getString(R.string.outstanding))) {
                            String value = getString(R.string.outstanding);
                            String phase = "1";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);
                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.inprgs))) {
                            String value = getString(R.string.inprgs);
                            String phase = "3";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            intent.putExtra("phase", phase);
                            startActivity(intent);
                        } else if (label.equals(getString(R.string.compltd))) {
                            String value = getString(R.string.compltd);
                            String phase = "4";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            if (swerk_s.equals("")) {
                                intent.putExtra("iwerk", "");
                            } else {
                                intent.putExtra("iwerk", swerk_s);
                            }
                            if (wrkcntr_s.equals("")) {
                                intent.putExtra("wrkcntr_s", "");
                            } else {
                                intent.putExtra("wrkcntr_s", wrkcntr_s);
                            }
                            intent.putExtra("phase", phase);
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


                Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
                label_list.setAdapter(ll_a);

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
                pieChart.setCenterText(plantName_s + " - " + wrkcntrName_s);
                pieChart.setHoleColor(Color.LTGRAY);
                pieChart.setTransparentCircleColor(Color.LTGRAY);
                pieChart.setCenterTextSize(14);
                pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                pieChart.invalidate();// for refreshing the chart
                /*progressdialog.dismiss();*/
                pieChart.setData(pieData);
            }
        }

    }

    public class FILTER_PLANT_TYPE_Adapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_EtNotifPlantTotal_Object> mainDataList = null;
        private ArrayList<Mis_EtNotifPlantTotal_Object> arraylist;

        public FILTER_PLANT_TYPE_Adapter(Context context,
                                         List<Mis_EtNotifPlantTotal_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtNotifPlantTotal_Object>();
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
        public Mis_EtNotifPlantTotal_Object getItem(int position) {
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
                holder.radiogroup = view.findViewById(R.id.radiogroup);
                holder.radio_status = view.findViewById(R.id.radio_status);
                holder.Name = view.findViewById(R.id.Name);
                holder.Total = view.findViewById(R.id.Total);
                holder.Red = view.findViewById(R.id.Red);
                holder.Yellow = view.findViewById(R.id.Yellow);
                holder.Green = view.findViewById(R.id.Green);
                holder.key = view.findViewById(R.id.key);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (!swerkTp_s.equals("")) {

                for (int i = 0; i < pt_o.size(); i++) {

                    if (pt_o.get(i).getmSwerk_pt().equals(swerkTp_s)) {
                        holder.radio_status.setChecked(true);
                        pt_o.get(i).setmChecked("y");
                    } else {
                        pt_o.get(i).setmChecked("n");
                    }
                }
            } else if (!swerkT_s.equals("")) {

                for (int j = 0; j < pt_o.size(); j++) {

                    if (pt_o.get(j).getmSwerk_pt().equals(swerkT_s)) {
                        holder.radio_status.setChecked(true);
                        pt_o.get(j).setmChecked("y");
                    } else {
                        pt_o.get(j).setmChecked("n");
                    }
                }
            } else if (swerk_s.equals("")) {

                for (int j = 0; j < pt_o.size(); j++) {

                    if (pt_o.get(j).getmSwerk_pt().equals(swerk_s)) {
                        holder.radio_status.setChecked(true);
                        pt_o.get(j).setmChecked("y");
                    } else {
                        pt_o.get(j).setmChecked("n");
                    }
                }
            }

            holder.radio_status.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String iwerk1 = holder.radio_status.getText().toString();

                    if (holder.radio_status.isChecked()) {

                        for (int i = 0; i < pt_o.size(); i++) {
                            String new_iwerk = pt_o.get(i).getmSwerk_pt();
                            String new_plant = pt_o.get(i).getmName_pt();

                            if (iwerk1.equalsIgnoreCase(new_iwerk)) {
                                pt_o.get(i).setmChecked("y");
                                mainDataList.get(i).setmChecked("y");
                                plantNameTp_s = new_plant;
                                swerkTp_s = new_iwerk;
                                plantAll_b = false;
                                changed_b = true;
                            } else {
                                String status_s = pt_o.get(i).getmChecked();

                                if (status_s.equals("y")) {
                                    pt_o.get(i).setmChecked("n");
                                    mainDataList.get(i).setmChecked("n");
                                }
                            }
                        }
                        filter_plant_type_adapter =
                                new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o);
                        listview.setAdapter(filter_plant_type_adapter);
                    } else {

                    }
                }
            });
            holder.radio_status.setText(mainDataList.get(position).getmSwerk_pt());
            holder.radio_status.setTag(position);
            if (mainDataList.get(position).getmChecked().equals("y"))
                holder.radio_status.setChecked(true);
            else
                holder.radio_status.setChecked(false);

            holder.Name.setText(mainDataList.get(position).getmName_pt());

            return view;
        }
    }


    public class Get_NotifAnalysis_Data_ extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(
                    R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String month = params[0];
                String year = params[1];
                NotifAnalysis_Status = NotifAnalysis.Get_NotifAnalysis_Data(getActivity(), "MISR", month, year);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v("kiran_NotifAnalysis", NotifAnalysis_Status + "...");
            {
                if (NotifAnalysis_Status.equals("success")) {
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from EsNotifTotal", null);
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                out_s = cursor1.getString(2);
                                inpr_s = cursor1.getString(3);
                                comp_s = cursor1.getString(4);
                                dele_s = cursor1.getString(5);
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                    pieChart.setVisibility(View.VISIBLE);
                    noData_tv.setVisibility(View.GONE);
                    plantAll_b = true;
                    pt_o.clear();
                    art_o.clear();
                    tt_o.clear();
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
                        Log.v("EtNotifPlantTotal_exce", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
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
                        Log.v("EtNotifArbplTotal_exce", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
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
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                        Log.v("Notif_pie_typetotal_db", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        progressdialog.dismiss();
                    }

                    try {
                        if (out_s != null && !out_s.equals("")) {
                            out_f = Float.parseFloat(out_s.trim());
                        } else {
                            out_f = 0;
                        }
                        if (inpr_s != null && !inpr_s.equals("")) {
                            inpr_f = Float.parseFloat(inpr_s.trim());
                        } else {
                            inpr_f = 0;
                        }
                        if (comp_s != null && !comp_s.equals("")) {
                            comp_f = Float.parseFloat(comp_s.trim());
                        } else {
                            comp_f = 0;
                        }
                    } catch (NumberFormatException nfe) {
                        Log.v("String_to_ Float", "" + nfe.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
                        progressdialog.dismiss();
                    }

                    llo.clear();
                    final List<PieEntry> entries = new ArrayList<>();
                    if (out_f == 0) {

                    } else {
                        entries.add(new PieEntry(out_f, getString(R.string.outstanding)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.outstanding));
                        llo.add(llo_o);
                    }
                    if (inpr_f == 0) {

                    } else {
                        entries.add(new PieEntry(inpr_f, getString(R.string.inprgs)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.inprgs));
                        llo.add(llo_o);
                    }
                    if (comp_f == 0) {

                    } else {
                        entries.add(new PieEntry(comp_f, getString(R.string.compltd)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.compltd));
                        llo.add(llo_o);
                    }

                    pieDataSet = new PieDataSet(entries, "");

                    final int[] MY_COLORS = new int[entries.size()];

                    for (int j = 0; j < entries.size(); j++) {
                        if (entries.get(j).getLabel().equals(getString(R.string.outstanding))) {
                            MY_COLORS[j] = Color.rgb(255, 255, 0);                  //yellow
                            llo.get(j).setColor(Color.rgb(255, 255, 0));
                        } else if (entries.get(j).getLabel().equals(getString(R.string.inprgs))) {
                            MY_COLORS[j] = Color.rgb(51, 51, 255);                  //blue
                            llo.get(j).setColor(Color.rgb(51, 51, 255));
                        } else if (entries.get(j).getLabel().equals(getString(R.string.compltd))) {
                            MY_COLORS[j] = Color.rgb(50, 205, 50);                 //green
                            llo.get(j).setColor(Color.rgb(50, 205, 50));
                        }
                    }

                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);

                    pieDataSet.setColors(colors);
                    pieDataSet.setSliceSpace(2f);
                    pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                    pieDataSet.setSelectionShift(5f);
                    pieDataSet.setValueLinePart1Length(0.2f);
                    pieDataSet.setValueLinePart2Length(0.4f);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setValueFormatter(new MyValueFormatter());

                    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            int index = (int) h.getX();
                            String label = entries.get(index).getLabel();

                            if (label.equals(getString(R.string.outstanding))) {
                                String value = getString(R.string.outstanding);
                                String phase = "1";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.inprgs))) {
                                String value = getString(R.string.inprgs);
                                String phase = "3";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.compltd))) {
                                String value = getString(R.string.compltd);
                                String phase = "4";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
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

                    pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(14);
                    pieChart.setUsePercentValues(false);


                    Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
                    label_list.setAdapter(ll_a);

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
                    pieChart.setCenterText(getString(R.string.mis_notif_analysis));
                    pieChart.setHoleColor(Color.LTGRAY);
                    pieChart.setTransparentCircleColor(Color.LTGRAY);
                    pieChart.setCenterTextSize(14);
                    pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                    pieChart.invalidate();// for refreshing the chart
                    progressdialog.dismiss();
                    pieChart.setData(pieData);
                    label_list.setVisibility(View.VISIBLE);
                } else {
                    pieChart.setVisibility(View.GONE);
                    label_list.setVisibility(View.GONE);
                    noData_tv.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                }
            }
        }
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
                String webservice_type = getString(R.string.webservice_type);
                if(webservice_type.equalsIgnoreCase("odata"))
                {
                    new Get_NotifAnalysis_Data_().execute(selected_month, selected_year);
                }
                else
                {
                    new Get_NotifAnalysis_Data_REST().execute(selected_month, selected_year);
                }
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

            if (listV == null)
                listV = LayoutInflater.from(getContext())
                        .inflate(R.layout.label_list_adapter, parent, false);

            llo_o = getItem(position);
            ImageView label_color = (ImageView) listV.findViewById(R.id.label_color);
            label_color.setBackgroundColor(llo_o.getColor());

            final TextView label_name = (TextView) listV.findViewById(R.id.label_name);
            label_name.setText(llo_o.getLabel());

            label_name.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (plantAll_b) {

                        if (label_name.getText().equals(getString(R.string.outstanding))) {
                            String value = getString(R.string.outstanding);
                            String phase = "1";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            startActivity(intent);
                        } else if (label_name.getText().equals(getString(R.string.inprgs))) {
                            String value = getString(R.string.inprgs);
                            String phase = "3";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            startActivity(intent);
                        } else if (label_name.getText().equals(getString(R.string.compltd))) {
                            String value = getString(R.string.compltd);
                            String phase = "4";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            startActivity(intent);
                        }
                    } else {

                        if (label_name.getText().equals(getString(R.string.outstanding))) {
                            String value = getString(R.string.outstanding);
                            String phase = "1";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            intent.putExtra("phase", phase);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            startActivity(intent);
                        } else if (label_name.getText().equals(getString(R.string.inprgs))) {
                            String value = getString(R.string.inprgs);
                            String phase = "3";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            intent.putExtra("phase", phase);
                            startActivity(intent);
                        } else if (label_name.getText().equals(getString(R.string.compltd))) {
                            String value = getString(R.string.compltd);
                            String phase = "4";
                            Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                            intent.putExtra("total", out_f);
                            intent.putExtra("inpr", inpr_f);
                            intent.putExtra("comp", comp_f);
                            intent.putExtra("value", value);
                            intent.putExtra("iwerk", "");
                            intent.putExtra("wrkcntr_s", "");
                            intent.putExtra("phase", phase);
                            startActivity(intent);
                        }
                    }
                }
            });
            return listV;
        }
    }


    public String getMonth_year() {
        return selected_year;
    }





    public class Get_NotifAnalysis_Data_REST extends AsyncTask<String, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressdialog = new ProgressDialog(getContext(), ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
        }

        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String month = params[0];
                String year = params[1];
                NotifAnalysis_Status = NotifAnalysis_REST.Get_NotifAnalysis_Data(getActivity(), "MISR", month, year);
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v("kiran_NotifAnalysis", NotifAnalysis_Status + "...");
            {
                if (NotifAnalysis_Status.equals("success")) {
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from EsNotifTotal", null);
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                out_s = cursor1.getString(2);
                                inpr_s = cursor1.getString(3);
                                comp_s = cursor1.getString(4);
                                dele_s = cursor1.getString(5);
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                    pieChart.setVisibility(View.VISIBLE);
                    noData_tv.setVisibility(View.GONE);
                    plantAll_b = true;
                    pt_o.clear();
                    art_o.clear();
                    tt_o.clear();
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
                        Log.v("EtNotifPlantTotal_exce", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
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
                        Log.v("EtNotifArbplTotal_exce", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
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
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                        Log.v("Notif_pie_typetotal_db", "" + e.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        progressdialog.dismiss();
                    }

                    try {
                        if (out_s != null && !out_s.equals("")) {
                            out_f = Float.parseFloat(out_s.trim());
                        } else {
                            out_f = 0;
                        }
                        if (inpr_s != null && !inpr_s.equals("")) {
                            inpr_f = Float.parseFloat(inpr_s.trim());
                        } else {
                            inpr_f = 0;
                        }
                        if (comp_s != null && !comp_s.equals("")) {
                            comp_f = Float.parseFloat(comp_s.trim());
                        } else {
                            comp_f = 0;
                        }
                    } catch (NumberFormatException nfe) {
                        Log.v("String_to_ Float", "" + nfe.getMessage());
                        pieChart.setVisibility(View.GONE);
                        noData_tv.setVisibility(View.VISIBLE);
                        llo.clear();
                        progressdialog.dismiss();
                    }

                    llo.clear();
                    final List<PieEntry> entries = new ArrayList<>();
                    if (out_f == 0) {

                    } else {
                        entries.add(new PieEntry(out_f, getString(R.string.outstanding)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.outstanding));
                        llo.add(llo_o);
                    }
                    if (inpr_f == 0) {

                    } else {
                        entries.add(new PieEntry(inpr_f, getString(R.string.inprgs)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.inprgs));
                        llo.add(llo_o);
                    }
                    if (comp_f == 0) {

                    } else {
                        entries.add(new PieEntry(comp_f, getString(R.string.compltd)));
                        Label_List_Object llo_o = new Label_List_Object();
                        llo_o.setLabel(getString(R.string.compltd));
                        llo.add(llo_o);
                    }

                    pieDataSet = new PieDataSet(entries, "");

                    final int[] MY_COLORS = new int[entries.size()];

                    for (int j = 0; j < entries.size(); j++) {
                        if (entries.get(j).getLabel().equals(getString(R.string.outstanding))) {
                            MY_COLORS[j] = Color.rgb(255, 255, 0);                  //yellow
                            llo.get(j).setColor(Color.rgb(255, 255, 0));
                        } else if (entries.get(j).getLabel().equals(getString(R.string.inprgs))) {
                            MY_COLORS[j] = Color.rgb(51, 51, 255);                  //blue
                            llo.get(j).setColor(Color.rgb(51, 51, 255));
                        } else if (entries.get(j).getLabel().equals(getString(R.string.compltd))) {
                            MY_COLORS[j] = Color.rgb(50, 205, 50);                 //green
                            llo.get(j).setColor(Color.rgb(50, 205, 50));
                        }
                    }

                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    for (int c : MY_COLORS) colors.add(c);

                    pieDataSet.setColors(colors);
                    pieDataSet.setSliceSpace(2f);
                    pieDataSet.setValueLinePart1OffsetPercentage(80.f);
                    pieDataSet.setSelectionShift(5f);
                    pieDataSet.setValueLinePart1Length(0.2f);
                    pieDataSet.setValueLinePart2Length(0.4f);
                    pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    pieDataSet.setValueFormatter(new MyValueFormatter());

                    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {
                            int index = (int) h.getX();
                            String label = entries.get(index).getLabel();

                            if (label.equals(getString(R.string.outstanding))) {
                                String value = getString(R.string.outstanding);
                                String phase = "1";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.inprgs))) {
                                String value = getString(R.string.inprgs);
                                String phase = "3";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
                                startActivity(intent);
                            } else if (label.equals(getString(R.string.compltd))) {
                                String value = getString(R.string.compltd);
                                String phase = "4";
                                Intent intent = new Intent(getActivity(), StatusPieActivity.class);
                                intent.putExtra("value", value);
                                intent.putExtra("phase", phase);
                                intent.putExtra("iwerk", "");
                                intent.putExtra("wrkcntr_s", "");
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

                    pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(14);
                    pieChart.setUsePercentValues(false);


                    Label_Adapter ll_a = new Label_Adapter(getActivity(), llo);
                    label_list.setAdapter(ll_a);

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
                    pieChart.setCenterText(getString(R.string.mis_notif_analysis));
                    pieChart.setHoleColor(Color.LTGRAY);
                    pieChart.setTransparentCircleColor(Color.LTGRAY);
                    pieChart.setCenterTextSize(14);
                    pieChart.notifyDataSetChanged();//Required if changes are made to pie value
                    pieChart.invalidate();// for refreshing the chart
                    progressdialog.dismiss();
                    pieChart.setData(pieData);
                    label_list.setVisibility(View.VISIBLE);
                } else {
                    pieChart.setVisibility(View.GONE);
                    label_list.setVisibility(View.GONE);
                    noData_tv.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                }
            }
        }
    }




}
