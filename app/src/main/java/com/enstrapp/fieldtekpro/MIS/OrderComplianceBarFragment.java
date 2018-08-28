package com.enstrapp.fieldtekpro.MIS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.enstrapp.fieldtekpro.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrderComplianceBarFragment extends Fragment {

    BarChart barChart;
    ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
    BarData barData;
    BarDataSet barDataSet;
    TextView  compliance_text;
    ImageView filter_iv;
    Order_Analysis_Activity ma;
    FILTER_PLANT_TYPE_Adapter filter_plant_type_adapter;
    WKCENTER_TYPE_ADAPTER wkcenter_type_adapter;

    String swerk_c = "", wrkcnt_c = "", swerk_name_c = "", wrkcnt_name_c = "", swerk_ct = "",
            wrkcnt_name_ct = "", wrkcnt_ct = "", swerk_name_ct = "", swerk_ctp = "",
            swerk_name_ctp = "", wrkcnt_ctp = "", wrkcnt_name_ctp = "", month_year = "";
    String[] names;
    int total_comp = 0, total_tot = 0;
    ProgressDialog progressdialog;
    TextView noData_tv, plant_tv, wrkcntr_tv;

    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ArrayList<Mis_EtOrdArbplTotal_Object> art_fo_c = new ArrayList<Mis_EtOrdArbplTotal_Object>();
    ArrayList<Mis_EtOrdTypeTotal_Object> tt_o = new ArrayList<Mis_EtOrdTypeTotal_Object>();
    ArrayList<Mis_EtOrdTypeTotal_Object> tt_so = new ArrayList<Mis_EtOrdTypeTotal_Object>();
    ArrayList<Mis_EtOrdTypeTotal_Object> filter = new ArrayList<Mis_EtOrdTypeTotal_Object>();
    ArrayList<Mis_EtOrdPlantTotal_Object> pt_o_c = new ArrayList<Mis_EtOrdPlantTotal_Object>();
    ArrayList<Mis_EtOrdArbplTotal_Object> art_o_c = new ArrayList<Mis_EtOrdArbplTotal_Object>();
    ArrayList<Mis_EtOrdTypeTotal_Object> tt_fo = new ArrayList<Mis_EtOrdTypeTotal_Object>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<Float> Compliance = new ArrayList<Float>();

    Dialog plant_wrkcnt_dialog, plant_dialog, wrkcnt_dialog, errordialog;
    ListView listview;
    private static int count = 0;
    boolean changed_c = false, plant_all = false, plant_clear = false, changed_cw = false,
            workcentre_clear = false, changed_w = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mis_order_compliance_fragment, container, false);

        barChart = rootView.findViewById(R.id.bar_chart);
        filter_iv = rootView.findViewById(R.id.filter_imageview);
        noData_tv = rootView.findViewById(R.id.no_data_textview);
        compliance_text = rootView.findViewById(R.id.compliance_text);
        ma=(Order_Analysis_Activity) this.getActivity();

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        getData();

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
            ma.iv_filter.setOnClickListener(new View.OnClickListener() {
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

                    if (!swerk_name_c.equals("")) {
                        plant_tv.setText(swerk_name_c);
                    }

                    if (!wrkcnt_name_c.equals("")) {
                        wrkcntr_tv.setText(wrkcnt_name_c);
                    }

                    clear_all_textview.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            swerk_name_c = "";
                            swerk_c = "";
                            wrkcnt_name_c = "";
                            wrkcnt_c = "";
                            swerk_ct = "";
                            wrkcnt_name_ct = "";
                            wrkcnt_ct = "";
                            swerk_name_ct = "";
                            plant_tv.setText(swerk_name_c);
                            wrkcntr_tv.setText(wrkcnt_name_c);
                            for (int i = 0; i < pt_o_c.size(); i++) {
                                pt_o_c.get(i).setmChecked("n");
                            }
                            if (art_fo_c.size() > 0) {
                                for (int i = 0; i < art_fo_c.size(); i++) {
                                    art_fo_c.get(i).setmChecked("n");
                                }
                            }
                            allPlant();
                            plant_wrkcnt_dialog.dismiss();
                        }
                    });

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
                            if (pt_o_c.size() <= 0) {
                                listview.setVisibility(View.GONE);
                                no_data_textview.setVisibility(View.VISIBLE);
                            } else {
                                listview.setVisibility(View.VISIBLE);
                                no_data_textview.setVisibility(View.GONE);
                                filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o_c);
                                listview.setAdapter(filter_plant_type_adapter);
                            }

                            cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (changed_c) {
                                        if (swerk_c.equals("")) {
                                            if (swerk_ct.equals("")) {
                                                swerk_ctp = "";
                                                swerk_name_ctp = "";
                                                changed_c = false;
                                                plant_dialog.dismiss();
                                            } else if (!swerk_ct.equals("")) {
                                                swerk_ctp = "";
                                                swerk_name_ctp = "";
                                                changed_c = false;
                                                plant_dialog.dismiss();
                                            }
                                        } else {
                                            if (swerk_ct.equals("")) {
                                                changed_c = false;
                                                swerk_ctp = "";
                                                swerk_name_ctp = "";
                                                plant_dialog.dismiss();
                                            } else if (!swerk_ct.equals("")) {
                                                changed_c = false;
                                                swerk_ctp = "";
                                                swerk_name_ctp = "";
                                                plant_dialog.dismiss();
                                            }
                                        }
                                    } else {
                                        if (swerk_c.equals("")) {
                                            if (swerk_ct.equals("")) {
                                                plant_dialog.dismiss();
                                            } else if (!swerk_ct.equals("")) {
                                                plant_dialog.dismiss();
                                            }
                                        } else {
                                            if (swerk_ct.equals("")) {
                                                plant_dialog.dismiss();
                                            } else if (!swerk_ct.equals("")) {
                                                plant_dialog.dismiss();
                                            }
                                        }
                                    }
                                    changed_c = false;
                                    plant_dialog.dismiss();

                                }
                            });

                            ok_filter_button.setOnClickListener(new View.OnClickListener()

                            {
                                @Override
                                public void onClick(View v) {
                                    if (changed_c) {
                                        changed_c = false;
                                        swerk_ct = swerk_ctp;
                                        swerk_name_ct = swerk_name_ctp;
                                        swerk_ctp = "";
                                        swerk_name_ctp = "";
                                        wrkcnt_ct = "";
                                        wrkcnt_name_ct = "";
                                        plant_tv.setText(swerk_name_ct);
                                        wrkcntr_tv.setText(wrkcnt_name_ct);
                                        plant_dialog.dismiss();
                                    } else {
                                        if (swerk_ct.equals("") && swerk_ctp.equals("") && swerk_c.equals(""))
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
                                    swerk_c = "";
                                    swerk_name_c = "";
                                    wrkcnt_name_c = "";
                                    wrkcnt_c = "";
                                    plant_clear = true;
                                    swerk_ct = "";
                                    swerk_name_ct = "";
                                    wrkcnt_name_ct = "";
                                    wrkcnt_ct = "";
                                    for (int i = 0; i < pt_o_c.size(); i++) {
                                        pt_o_c.get(i).setmChecked("n");
                                    }
                                    if (art_fo_c.size() > 0) {
                                        for (int j = 0; j < art_fo_c.size(); j++) {
                                            art_fo_c.get(j).setmChecked("n");
                                        }
                                    }
                                    plant_tv.setText(swerk_name_c);
                                    wrkcntr_tv.setText(wrkcnt_name_c);
                                    filter_plant_type_adapter.notifyDataSetChanged();
                                    listview.setAdapter(filter_plant_type_adapter);
                                }
                            });
                            plant_dialog.show();
                        }
                    });

                    wrkcntr_tv.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (!swerk_c.equals("") && swerk_c != null) {
                                if (swerk_ct.equals("") || swerk_ct == null) {
                                    art_fo_c.clear();
                                    for (int i = 0; i < art_o_c.size(); i++) {
                                        if (art_o_c.get(i).getmSwerk_art().equals(swerk_c)) {
                                            Mis_EtOrdArbplTotal_Object art_foo = new Mis_EtOrdArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o_c.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o_c.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o_c.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o_c.get(i).getmName_art());
                                            art_foo.setmAuart_art(art_o_c.get(i).getmAuartx_art());
                                            art_foo.setmAuart_art(art_o_c.get(i).getmAuartx_art());
                                            art_foo.setmOuts_art(art_o_c.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o_c.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o_c.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o_c.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo_c.add(art_foo);
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
                                    if (art_fo_c.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo_c);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (changed_cw) {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                }
                                            } else {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    ok_filter_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (changed_cw) {
                                                changed_cw = false;
                                                wrkcnt_ct = wrkcnt_ctp;
                                                wrkcnt_name_ct = wrkcnt_name_ctp;
                                                wrkcnt_ctp = "";
                                                wrkcnt_name_ctp = "";
                                                wrkcntr_tv.setText(wrkcnt_name_ct);
                                                wrkcnt_dialog.dismiss();
                                            } else {
                                                if (wrkcnt_ct.equals("") && wrkcnt_ctp.equals("") && wrkcnt_c.equals(""))
                                                    show_error_dialog("Please select WORK CENTRE");
                                                else
                                                    wrkcnt_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcnt_name_c = "";
                                            wrkcnt_c = "";
                                            workcentre_clear = true;
                                            wrkcnt_name_ct = "";
                                            wrkcnt_ct = "";
                                            wrkcnt_name_ctp = "";
                                            wrkcnt_ctp = "";
                                            wrkcntr_tv.setText("");
                                            if (art_fo_c.size() > 0) {
                                                for (int j = 0; j < art_fo_c.size(); j++) {
                                                    art_fo_c.get(j).setmChecked("n");
                                                }
                                            }
                                            wkcenter_type_adapter.notifyDataSetChanged();
                                            listview.setAdapter(wkcenter_type_adapter);
                                        }
                                    });

                                } else {

                                    art_fo_c.clear();
                                    for (int i = 0; i < art_o_c.size(); i++) {
                                        if (art_o_c.get(i).getmSwerk_art().equals(swerk_ct)) {
                                            Mis_EtOrdArbplTotal_Object art_foo = new Mis_EtOrdArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o_c.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o_c.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o_c.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o_c.get(i).getmName_art());
                                            art_foo.setmAuart_art(art_o_c.get(i).getmAuart_art());
                                            art_foo.setmAuartx_art(art_o_c.get(i).getmAuartx_art());
                                            art_foo.setmOuts_art(art_o_c.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o_c.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o_c.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o_c.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo_c.add(art_foo);
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
                                    if (art_fo_c.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo_c);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (changed_cw) {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                }
                                            } else {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
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
                                                changed_cw = false;
                                                wrkcnt_ct = wrkcnt_ctp;
                                                wrkcnt_name_ct = wrkcnt_name_ctp;
                                                wrkcnt_ctp = "";
                                                wrkcnt_name_ctp = "";
                                                wrkcntr_tv.setText(wrkcnt_name_ct);
                                                wrkcnt_dialog.dismiss();
                                            } else {
                                                if (wrkcnt_ct.equals("") && wrkcnt_ctp.equals("") && wrkcnt_c.equals(""))
                                                    show_error_dialog("Please select WORK CENTRE");
                                                else
                                                    wrkcnt_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcnt_name_c = "";
                                            wrkcnt_c = "";
                                            workcentre_clear = true;
                                            wrkcnt_name_ct = "";
                                            wrkcnt_ct = "";
                                            wrkcnt_name_ctp = "";
                                            wrkcnt_ctp = "";
                                            wrkcntr_tv.setText("");
                                            if (art_fo_c.size() > 0) {
                                                for (int j = 0; j < art_fo_c.size(); j++) {
                                                    art_fo_c.get(j).setmChecked("n");
                                                }
                                            }
                                            wkcenter_type_adapter.notifyDataSetChanged();
                                            listview.setAdapter(wkcenter_type_adapter);
                                        }
                                    });


                                }
                                wrkcnt_dialog.show();
                            } else if (swerk_c.equals("") || swerk_c == null) {
                                if (swerk_ct.equals("") || swerk_ct == null) {
                                    show_error_dialog("Please select PLANT");
                                } else {
                                    art_fo_c.clear();
                                    for (int i = 0; i < art_o_c.size(); i++) {
                                        if (art_o_c.get(i).getmSwerk_art().equals(swerk_ct)) {
                                            Mis_EtOrdArbplTotal_Object art_foo = new Mis_EtOrdArbplTotal_Object();
                                            art_foo.setmTotal_art(art_o_c.get(i).getmTotal_art());
                                            art_foo.setmSwerk_art(art_o_c.get(i).getmSwerk_art());
                                            art_foo.setmArbpl_art(art_o_c.get(i).getmArbpl_art());
                                            art_foo.setmName_art(art_o_c.get(i).getmName_art());
                                            art_foo.setmAuart_art(art_o_c.get(i).getmAuart_art());
                                            art_foo.setmAuartx_art(art_o_c.get(i).getmAuartx_art());
                                            art_foo.setmOuts_art(art_o_c.get(i).getmOuts_art());
                                            art_foo.setmInpr_art(art_o_c.get(i).getmInpr_art());
                                            art_foo.setmComp_art(art_o_c.get(i).getmComp_art());
                                            art_foo.setmDele_art(art_o_c.get(i).getmDele_art());
                                            art_foo.setmChecked("n");
                                            art_fo_c.add(art_foo);
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
                                    if (art_fo_c.size() <= 0) {
                                        listview.setVisibility(View.GONE);
                                        no_data_textview.setVisibility(View.VISIBLE);
                                    } else {
                                        listview.setVisibility(View.VISIBLE);
                                        no_data_textview.setVisibility(View.GONE);
                                        wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo_c);
                                        listview.setAdapter(wkcenter_type_adapter);
                                    }

                                    cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (changed_cw) {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        changed_cw = false;
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        changed_cw = false;
                                                        wrkcnt_ctp = "";
                                                        wrkcnt_name_ctp = "";
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                }
                                            } else {
                                                if (wrkcnt_c.equals("")) {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                } else {
                                                    if (wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    } else if (!wrkcnt_ct.equals("")) {
                                                        wrkcnt_dialog.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    ok_filter_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (changed_cw) {
                                                changed_cw = false;
                                                wrkcnt_ct = wrkcnt_ctp;
                                                wrkcnt_name_ct = wrkcnt_name_ctp;
                                                wrkcnt_ctp = "";
                                                wrkcnt_name_ctp = "";
                                                wrkcntr_tv.setText(wrkcnt_name_ct);
                                                wrkcnt_dialog.dismiss();
                                            } else {
                                                if (wrkcnt_ct.equals("") && wrkcnt_ctp.equals("") && wrkcnt_c.equals(""))
                                                    show_error_dialog("Please select WORK CENTRE");
                                                else
                                                    wrkcnt_dialog.dismiss();
                                            }
                                        }
                                    });

                                    clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            wrkcnt_name_c = "";
                                            wrkcnt_c = "";
                                            workcentre_clear = true;
                                            wrkcnt_name_ct = "";
                                            wrkcnt_ct = "";
                                            wrkcnt_name_ctp = "";
                                            wrkcnt_ctp = "";
                                            wrkcntr_tv.setText("");
                                            if (art_fo_c.size() > 0) {
                                                for (int j = 0; j < art_fo_c.size(); j++) {
                                                    art_fo_c.get(j).setmChecked("n");
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

                            if (!swerk_c.equals("")) {
                                if (!swerk_ct.equals("")) {
                                    if (swerk_c.equals(swerk_ct)) {
                                        swerk_ct = "";
                                        wrkcnt_ct = "";
                                        swerk_name_ct = "";
                                        wrkcnt_name_ct = "";
                                        plantWrkcntSelector(swerk_c, wrkcnt_c);
                                        plant_wrkcnt_dialog.dismiss();
                                    } else {
                                        if (wrkcnt_ct.equals("") || wrkcnt_ct == null) {
                                            swerk_c = swerk_ct;
                                            swerk_name_c = swerk_name_ct;
                                            swerk_ct = "";
                                            swerk_name_ct = "";
                                            wrkcnt_c = "";
                                            wrkcnt_name_c = "";
                                            plantWrkcntSelector(swerk_c, wrkcnt_c);
                                            plant_wrkcnt_dialog.dismiss();
                                        } else {
                                            swerk_c = swerk_ct;
                                            wrkcnt_c = wrkcnt_ct;
                                            wrkcnt_name_c = wrkcnt_name_ct;
                                            swerk_name_c = swerk_name_ct;
                                            swerk_ct = "";
                                            swerk_name_ct = "";
                                            wrkcnt_name_ct = "";
                                            wrkcnt_ct = "";
                                            plantWrkcntSelector(swerk_c, wrkcnt_c);
                                            plant_wrkcnt_dialog.dismiss();
                                        }
                                    }
                                } else {
                                    if (!wrkcnt_c.equals("")) {
                                        if (wrkcnt_ct.equals("")) {
                                            plant_wrkcnt_dialog.dismiss();
                                        } else {
                                            if (!wrkcnt_c.equals(wrkcnt_ct)) {
                                                wrkcnt_c = wrkcnt_ct;
                                                wrkcnt_name_c = wrkcnt_name_ct;
                                                wrkcnt_name_ct = "";
                                                wrkcnt_ct = "";
                                                plantWrkcntSelector(swerk_c, wrkcnt_c);
                                                plant_wrkcnt_dialog.dismiss();
                                            } else {
                                                wrkcnt_name_ct = "";
                                                wrkcnt_ct = "";
                                                plant_wrkcnt_dialog.dismiss();
                                            }
                                        }

                                    } else {
                                        wrkcnt_c = wrkcnt_ct;
                                        wrkcnt_name_c = wrkcnt_name_ct;
                                        wrkcnt_ct = "";
                                        wrkcnt_name_ct = "";
                                        plantWrkcntSelector(swerk_c, wrkcnt_c);
                                        plant_wrkcnt_dialog.dismiss();
                                    }
                                }
                            } else {
                                if (swerk_ct.equals("")) {
                                    allPlant();
                                    plant_wrkcnt_dialog.dismiss();
                                } else {
                                    if (wrkcnt_ct.equals("") || wrkcnt_ct == null) {
                                        swerk_c = swerk_ct;
                                        swerk_name_c = swerk_name_ct;
                                        swerk_ct = "";
                                        swerk_name_ct = "";
                                        plantWrkcntSelector(swerk_c, wrkcnt_c);
                                        plant_wrkcnt_dialog.dismiss();
                                    } else {
                                        swerk_c = swerk_ct;
                                        wrkcnt_c = wrkcnt_ct;
                                        wrkcnt_name_c = wrkcnt_name_ct;
                                        swerk_name_c = swerk_name_ct;
                                        swerk_ct = "";
                                        swerk_name_ct = "";
                                        wrkcnt_name_ct = "";
                                        wrkcnt_ct = "";
                                        plantWrkcntSelector(swerk_c, wrkcnt_c);
                                        plant_wrkcnt_dialog.dismiss();
                                    }
                                }
                            }
                        }
                    });

                    close_filter_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!swerk_ct.equals("")) {
                                swerk_ct = "";
                                swerk_name_ct = "";
                            }
                            if (!wrkcnt_ct.equals("")) {
                                wrkcnt_ct = "";
                                wrkcnt_name_ct = "";
                            }
                            if (plant_clear) {
                                allPlant();
                                plant_clear = false;
                            } else if (workcentre_clear) {
                                plantWrkcntSelector(swerk_c, wrkcnt_c);
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



    public void setMonthYear(String month_year){
        this.month_year = month_year;
    }

    public void getData() {
        Log.v("Compliance", "- getData");
        progressdialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage("Loading...");
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();
        pt_o_c.clear();
        art_o_c.clear();

        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdPlantTotal", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdPlantTotal_Object cp = new Mis_EtOrdPlantTotal_Object();
                        cp.setmTotal_pt(cursor.getString(1));
                        cp.setmSwerk_pt(cursor.getString(2));
                        cp.setmName_pt(cursor.getString(3));
                        cp.setmAuart_pt(cursor.getString(4));
                        cp.setmAuartx_pt(cursor.getString(5));
                        cp.setmOuts_pt(cursor.getString(6));
                        cp.setmInpr_pt(cursor.getString(7));
                        cp.setmComp_pt(cursor.getString(8));
                        cp.setmDele_pt(cursor.getString(9));
                        cp.setmChecked("n");
                        pt_o_c.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("EtOrdPlantTotal_exce", "" + e.getMessage());
            barChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }

        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdArbplTotal", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdArbplTotal_Object cp = new Mis_EtOrdArbplTotal_Object();
                        cp.setmTotal_art(cursor.getString(1));
                        cp.setmSwerk_art(cursor.getString(2));
                        cp.setmArbpl_art(cursor.getString(3));
                        cp.setmName_art(cursor.getString(4));
                        cp.setmAuart_art(cursor.getString(5));
                        cp.setmAuartx_art(cursor.getString(6));
                        cp.setmOuts_art(cursor.getString(7));
                        cp.setmInpr_art(cursor.getString(8));
                        cp.setmComp_art(cursor.getString(9));
                        cp.setmDele_art(cursor.getString(10));
                        cp.setmChecked("n");
                        art_o_c.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("EtOrdArbplTotal_exce", "" + e.getMessage());
            barChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }

        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdTypeTotal", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                        cp.setmTotal_tt(cursor.getString(1));
                        cp.setmSwerk_tt(cursor.getString(2));
                        cp.setmArbpl_tt(cursor.getString(3));
                        cp.setmAuart_tt(cursor.getString(4));
                        cp.setmAuartx_tt(cursor.getString(5));
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
            Log.v("Order_pie_typetotal_db", "" + e.getMessage());
            barChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }

        if (swerk_c.equals("")) {
            tt_so.clear();
            filter.clear();
            for (int i = 0; i < tt_o.size(); i++) {
                Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                tt_so.add(cp);
                filter.add(cp);
            }
        } else {
            tt_so.clear();
            filter.clear();
            if (wrkcnt_c.equals("")) {
                for (int i = 0; i < tt_o.size(); i++) {
                    if (tt_o.get(i).getmSwerk_tt().equals(swerk_c)) {
                        Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                        cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                        cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                        cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                        cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                        cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                        cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                        cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                        cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                        cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                        tt_so.add(cp);
                        filter.add(cp);
                    }
                }
            } else {
                tt_so.clear();
                filter.clear();
                for (int i = 0; i < tt_o.size(); i++) {
                    if (tt_o.get(i).getmSwerk_tt().equals(swerk_c) && tt_o.get(i).getmArbpl_tt().equals(wrkcnt_c)) {
                        Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                        cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                        cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                        cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                        cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                        cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                        cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                        cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                        cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                        cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                        tt_so.add(cp);
                        filter.add(cp);
                    }
                }
            }
        }

        if (swerk_c.equals("")) {

        } else {
            for (int i = 0; i < pt_o_c.size(); i++) {
                if (pt_o_c.get(i).getmSwerk_pt().equals(swerk_c)) {
                    pt_o_c.get(i).setmChecked("y");
                    swerk_name_c = pt_o_c.get(i).getmName_pt();
                }
            }
        }

        if (wrkcnt_c.equals("")) {

        } else {
            for (int i = 0; i < art_o_c.size(); i++) {
                if (art_o_c.get(i).getmArbpl_art().equals(wrkcnt_c)) {
                    art_o_c.get(i).setmChecked("y");
                    wrkcnt_name_c = art_o_c.get(i).getmName_art();
                }
            }
        }

        tt_fo.clear();
        for (int i = 0; i < tt_so.size(); i++) {
            String order = tt_so.get(i).getmAuart_tt();
            if (tt_fo.size() > 0) {
                if (contains(tt_fo, order)) {

                } else {
                    for (int j = 0; j < filter.size(); j++) {
                        if (filter.get(j).getmAuart_tt().equals(order)) {
                            if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                            } else {
                                total_comp = total_comp + 0;
                                total_tot = total_tot + 0;
                            }
                        }
                    }
                    Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                    tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                    tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                    tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                    tt_cf_o.setmAuart_tt(order);
                    tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                    tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                    tt_fo.add(tt_cf_o);
                    total_comp = 0;
                    total_tot = 0;
                }
            } else {
                for (int j = 0; j < filter.size(); j++) {
                    if (filter.get(j).getmAuart_tt().equals(order)) {
                        if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                            total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                            total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                        } else {
                            total_comp = total_comp + 0;
                            total_tot = total_tot + 0;
                        }
                    }
                }
                Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                tt_cf_o.setmAuart_tt(order);
                tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                tt_fo.add(tt_cf_o);
                total_comp = 0;
                total_tot = 0;
            }
        }

        barEntries = new ArrayList<BarEntry>();
        int h = 0;
        name.clear();
        Compliance.clear();
        for (int k = 0; k < tt_fo.size(); k++) {
            if (!tt_fo.get(k).getmComp_tt().equals("0")) {
                Compliance.add((Float.parseFloat(tt_fo.get(k).getmComp_tt().trim()) / Float.parseFloat(tt_fo.get(k).getmTotal_tt().trim())) * 100);
            } else {
                Compliance.add(0f);
            }
            if (Compliance.get(k) == 0) {

            } else {
                barEntries.add(new BarEntry(h, Compliance.get(k)));
                h++;
                name.add(tt_fo.get(k).getmAuart_tt());
            }
        }

        if (!(barEntries.size() <= 0)) {
            names = new String[name.size()];
            for (int i = 0; i < name.size(); i++) {
                names[i] = name.get(i);
            }

            barDataSet = new BarDataSet(barEntries, "Compliance");
            barDataSet.setColor(Color.rgb(49, 84, 154));
            barDataSet.setValueTextSize(12f);
            barDataSet.setValueFormatter(new MyValueFormatterPercent());

            Legend l = barChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setTextSize(12);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(7);
            xAxis.setValueFormatter(new MyXAxisValueFormatter(names));

            barData = new BarData(barDataSet);
            if (barEntries.size() == 1) {
                barData.setBarWidth(0.4f);
            } else if (barEntries.size() == 2) {
                barData.setBarWidth(0.5f);
            } else if (barEntries.size() == 3) {
                barData.setBarWidth(0.6f);
            }

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setDrawLabels(false);

            barChart.getDescription().setEnabled(false);
            barChart.setExtraOffsets(2, 2, 2, 2);
            barChart.highlightValues(null);
            barChart.getAxisLeft().setAxisMinimum(0);
            barChart.getAxisLeft().setDrawLabels(true);
            barChart.getAxisLeft().setDrawGridLines(true);
            barChart.getAxisRight().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawAxisLine(true);
            barChart.getAxisRight().setDrawAxisLine(false);
            barChart.getLegend().setEnabled(true);
            barChart.animateY(1000);
            barChart.getXAxis().setLabelCount(Compliance.size(), false);
            barChart.notifyDataSetChanged();//Required if changes are made to pie value
            barChart.setData(barData);
            compliance_text.setText(month_year);
            barChart.invalidate();// for refreshing the chart
        }

        progressdialog.dismiss();
    }

    public void allPlant() {

        tt_so.clear();
        filter.clear();
        for (int i = 0; i < tt_o.size(); i++) {
            Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
            cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
            cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
            cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
            cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
            cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
            cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
            cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
            cp.setmComp_tt(tt_o.get(i).getmComp_tt());
            cp.setmDele_tt(tt_o.get(i).getmDele_tt());
            tt_so.add(cp);
            filter.add(cp);
        }

        tt_fo.clear();
        for (int i = 0; i < tt_so.size(); i++) {
            String order = tt_so.get(i).getmAuart_tt();
            if (tt_fo.size() > 0) {
                if (contains(tt_fo, order)) {

                } else {
                    for (int j = 0; j < filter.size(); j++) {
                        if (filter.get(j).getmAuart_tt().equals(order)) {
                            if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                            } else {
                                total_comp = total_comp + 0;
                                total_tot = total_tot + 0;
                            }
                        }
                    }
                    Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                    tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                    tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                    tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                    tt_cf_o.setmAuart_tt(order);
                    tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                    tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                    tt_fo.add(tt_cf_o);
                    total_comp = 0;
                    total_tot = 0;
                }
            } else {
                for (int j = 0; j < filter.size(); j++) {
                    if (filter.get(j).getmAuart_tt().equals(order)) {
                        if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                            total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                            total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                        } else {
                            total_comp = total_comp + 0;
                            total_tot = total_tot + 0;
                        }
                    }
                }
                Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                tt_cf_o.setmAuart_tt(order);
                tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                tt_fo.add(tt_cf_o);
                total_comp = 0;
                total_tot = 0;
            }
        }

        barEntries = new ArrayList<BarEntry>();
        int h = 0;
        Compliance.clear();
        name.clear();
        for (int k = 0; k < tt_fo.size(); k++) {
            if (!tt_fo.get(k).getmComp_tt().equals("0")) {
                Compliance.add((Float.parseFloat(tt_fo.get(k).getmComp_tt().trim()) / Float.parseFloat(tt_fo.get(k).getmTotal_tt().trim())) * 100);
            } else {
                Compliance.add(0f);
            }
            if (Compliance.get(k) == 0) {

            } else {
                barEntries.add(new BarEntry(h, Compliance.get(k)));
                h++;
                name.add(tt_fo.get(k).getmAuart_tt());
            }
        }

        if (!(barEntries.size() <= 0)) {

            names = new String[name.size()];
            for (int i = 0; i < name.size(); i++) {
                names[i] = name.get(i);
            }

            barDataSet = new BarDataSet(barEntries, "Compliance");
            barDataSet.setColor(Color.rgb(49, 84, 154));
            barDataSet.setValueTextSize(12f);
            barDataSet.setValueFormatter(new MyValueFormatterPercent());

            Legend l = barChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setTextSize(12);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(7);
            xAxis.setValueFormatter(new MyXAxisValueFormatter(names));

            barData = new BarData(barDataSet);
            if (barEntries.size() == 1) {
                barData.setBarWidth(0.4f);
            } else if (barEntries.size() == 2) {
                barData.setBarWidth(0.5f);
            } else if (barEntries.size() == 3) {
                barData.setBarWidth(0.6f);
            }

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setDrawLabels(false);

            barChart.getDescription().setEnabled(false);
            barChart.setExtraOffsets(2, 2, 2, 2);
            barChart.highlightValues(null);
            barChart.getAxisLeft().setAxisMinimum(0);
            barChart.getAxisLeft().setDrawLabels(true);
            barChart.getAxisLeft().setDrawGridLines(true);
            barChart.getAxisRight().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawAxisLine(true);
            barChart.getAxisRight().setDrawAxisLine(false);
            barChart.getLegend().setEnabled(true);
            barChart.animateY(1000);
            barChart.getXAxis().setLabelCount(Compliance.size(), false);
            barChart.notifyDataSetChanged();//Required if changes are made to pie value
            barChart.setData(barData);
            barChart.setVisibility(View.VISIBLE);
            noData_tv.setVisibility(View.GONE);
            barChart.invalidate();// for refreshing the chart
            compliance_text.setText(month_year);
        } else {
            barChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
        }
    }

    public void plantWrkcntSelector(String plant, final String wrkcnt) {


        if (plant.equals("")) {
            tt_so.clear();
            filter.clear();
            for (int i = 0; i < tt_o.size(); i++) {
                Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                tt_so.add(cp);
                filter.add(cp);
            }
        } else {
            tt_so.clear();
            filter.clear();
            if (wrkcnt.equals("")) {
                for (int i = 0; i < tt_o.size(); i++) {
                    if (tt_o.get(i).getmSwerk_tt().equals(plant)) {
                        Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                        cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                        cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                        cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                        cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                        cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                        cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                        cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                        cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                        cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                        tt_so.add(cp);
                        filter.add(cp);
                    }
                }
            } else {
                tt_so.clear();
                filter.clear();
                for (int i = 0; i < tt_o.size(); i++) {
                    if (tt_o.get(i).getmSwerk_tt().equals(plant) && tt_o.get(i).getmArbpl_tt().equals(wrkcnt)) {
                        Mis_EtOrdTypeTotal_Object cp = new Mis_EtOrdTypeTotal_Object();
                        cp.setmTotal_tt(tt_o.get(i).getmTotal_tt());
                        cp.setmSwerk_tt(tt_o.get(i).getmSwerk_tt());
                        cp.setmArbpl_tt(tt_o.get(i).getmArbpl_tt());
                        cp.setmAuart_tt(tt_o.get(i).getmAuart_tt());
                        cp.setmAuartx_tt(tt_o.get(i).getmAuartx_tt());
                        cp.setmOuts_tt(tt_o.get(i).getmOuts_tt());
                        cp.setmInpr_tt(tt_o.get(i).getmInpr_tt());
                        cp.setmComp_tt(tt_o.get(i).getmComp_tt());
                        cp.setmDele_tt(tt_o.get(i).getmDele_tt());
                        tt_so.add(cp);
                        filter.add(cp);
                    }
                }
            }
        }

        if (plant.equals("")) {

        } else {
            for (int i = 0; i < pt_o_c.size(); i++) {
                if (pt_o_c.get(i).getmSwerk_pt().equals(plant)) {
                    pt_o_c.get(i).setmChecked("y");
                    swerk_name_c = pt_o_c.get(i).getmName_pt();
                }
            }
        }

        if (wrkcnt.equals("")) {

        } else {
            for (int i = 0; i < art_o_c.size(); i++) {
                if (art_o_c.get(i).getmArbpl_art().equals(wrkcnt)) {
                    art_o_c.get(i).setmChecked("y");
                    wrkcnt_name_c = art_o_c.get(i).getmName_art();
                }
            }
        }

        tt_fo.clear();
        for (int i = 0; i < tt_so.size(); i++) {
            String order = tt_so.get(i).getmAuart_tt();
            if (tt_fo.size() > 0) {
                if (contains(tt_fo, order)) {

                } else {
                    for (int j = 0; j < filter.size(); j++) {
                        if (filter.get(j).getmAuart_tt().equals(order)) {
                            if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                                total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                                total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                            } else {
                                total_comp = total_comp + 0;
                                total_tot = total_tot + 0;
                            }
                        }
                    }
                    Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                    tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                    tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                    tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                    tt_cf_o.setmAuart_tt(order);
                    tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                    tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                    tt_fo.add(tt_cf_o);
                    total_comp = 0;
                    total_tot = 0;
                }
            } else {
                for (int j = 0; j < filter.size(); j++) {
                    if (filter.get(j).getmAuart_tt().equals(order)) {
                        if (!filter.get(j).getmComp_tt().equals("") && filter.get(j).getmComp_tt() != null) {
                            total_comp = total_comp + Integer.parseInt(filter.get(j).getmComp_tt().trim());
                            total_tot = total_tot + Integer.parseInt(filter.get(j).getmTotal_tt().trim());
                        } else {
                            total_comp = total_comp + 0;
                            total_tot = total_tot + 0;
                        }
                    }
                }
                Mis_EtOrdTypeTotal_Object tt_cf_o = new Mis_EtOrdTypeTotal_Object();
                tt_cf_o.setmTotal_tt(String.valueOf(total_tot));
                tt_cf_o.setmSwerk_tt(tt_so.get(i).getmSwerk_tt());
                tt_cf_o.setmArbpl_tt(tt_so.get(i).getmArbpl_tt());
                tt_cf_o.setmAuart_tt(order);
                tt_cf_o.setmAuartx_tt(tt_so.get(i).getmAuartx_tt());
                tt_cf_o.setmComp_tt(String.valueOf(total_comp));
                tt_fo.add(tt_cf_o);
                total_comp = 0;
                total_tot = 0;
            }
        }

        barEntries = new ArrayList<BarEntry>();
        int h = 0;
        name.clear();
        Compliance.clear();
        for (int k = 0; k < tt_fo.size(); k++) {
            if (!tt_fo.get(k).getmComp_tt().equals("0")) {
                Compliance.add((Float.parseFloat(tt_fo.get(k).getmComp_tt().trim()) / Float.parseFloat(tt_fo.get(k).getmTotal_tt().trim())) * 100);
            } else {
                Compliance.add(0f);
            }

            if (Compliance.get(k) == 0) {

            } else {
                barEntries.add(new BarEntry(h, Compliance.get(k)));
                h++;
                name.add(tt_fo.get(k).getmAuart_tt());
            }
        }

        if (!(barEntries.size() <= 0)) {
            names = new String[name.size()];
            for (int i = 0; i < name.size(); i++) {
                names[i] = name.get(i);
            }

            barDataSet = new BarDataSet(barEntries, "Compliance");
            barDataSet.setColor(Color.rgb(49, 84, 154));
            barDataSet.setValueTextSize(12f);
            barDataSet.setValueFormatter(new MyValueFormatterPercent());

            Legend l = barChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setTextSize(12);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(7);
            xAxis.setValueFormatter(new MyXAxisValueFormatter(names));

            barData = new BarData(barDataSet);
            if (barEntries.size() == 1) {
                barData.setBarWidth(0.4f);
            } else if (barEntries.size() == 2) {
                barData.setBarWidth(0.5f);
            } else if (barEntries.size() == 3) {
                barData.setBarWidth(0.6f);
            }

            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setDrawLabels(false);

            barChart.getDescription().setEnabled(false);
            barChart.setExtraOffsets(2, 2, 2, 2);
            barChart.highlightValues(null);
            barChart.getAxisLeft().setAxisMinimum(0);
            barChart.getAxisLeft().setDrawLabels(true);
            barChart.getAxisLeft().setDrawGridLines(true);
            barChart.getAxisRight().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawAxisLine(true);
            barChart.getAxisRight().setDrawAxisLine(false);
            barChart.getLegend().setEnabled(true);
            barChart.animateY(1000);
            barChart.getXAxis().setLabelCount(Compliance.size(), false);
            barChart.notifyDataSetChanged();//Required if changes are made to pie value
            barChart.setData(barData);
            barChart.invalidate();// for refreshing the chart
            if (!swerk_c.equals("")) {
                if (!wrkcnt_c.equals("")) {
                    compliance_text.setText(swerk_name_c + " - " + wrkcnt_name_c + "\n" + month_year);
                } else {
                    compliance_text.setText(swerk_name_c + "\n" +month_year);
                }
            } else {
                compliance_text.setText(month_year);
            }
            noData_tv.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
        } else {
            barChart.setVisibility(View.GONE);
            noData_tv.setVisibility(View.VISIBLE);
            compliance_text.setText("");
        }


    }
    public class FILTER_PLANT_TYPE_Adapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_EtOrdPlantTotal_Object> mainDataList = null;
        private ArrayList<Mis_EtOrdPlantTotal_Object> arraylist;

        public FILTER_PLANT_TYPE_Adapter(Context context, List<Mis_EtOrdPlantTotal_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtOrdPlantTotal_Object>();
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
        public Mis_EtOrdPlantTotal_Object getItem(int position) {
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

            if (!swerk_ctp.equals("")) {
                for (int i = 0; i < pt_o_c.size(); i++) {
                    if (pt_o_c.get(i).getmSwerk_pt().equals(swerk_ctp)) {
                        holder.radio_status.setChecked(true);
                        pt_o_c.get(i).setmChecked("y");
                    } else {
                        pt_o_c.get(i).setmChecked("n");
                    }
                }
            } else if (!swerk_ct.equals("")) {
                for (int j = 0; j < pt_o_c.size(); j++) {
                    if (pt_o_c.get(j).getmSwerk_pt().equals(swerk_ct)) {
                        holder.radio_status.setChecked(true);
                        pt_o_c.get(j).setmChecked("y");
                    } else {
                        pt_o_c.get(j).setmChecked("n");
                    }
                }
            } else if (swerk_c.equals("")) {
                for (int j = 0; j < pt_o_c.size(); j++) {
                    if (pt_o_c.get(j).getmSwerk_pt().equals(swerk_c)) {
                        holder.radio_status.setChecked(true);
                        pt_o_c.get(j).setmChecked("y");
                    } else {
                        pt_o_c.get(j).setmChecked("n");
                    }
                }
            }


            holder.radio_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String iwerk1 = holder.radio_status.getText().toString();
                    if (holder.radio_status.isChecked()) {
                        for (int i = 0; i < pt_o_c.size(); i++) {
                            String new_iwerk = pt_o_c.get(i).getmSwerk_pt();
                            String new_plant = pt_o_c.get(i).getmName_pt();
                            if (iwerk1.equalsIgnoreCase(new_iwerk)) {
                                pt_o_c.get(i).setmChecked("y");
                                mainDataList.get(i).setmChecked("y");
                                swerk_name_ctp = new_plant;
                                swerk_ctp = new_iwerk;
                                plant_all = false;
                                changed_c = true;
                            } else {
                                String status = pt_o_c.get(i).getmChecked();
                                if (status.equals("y")) {
                                    pt_o_c.get(i).setmChecked("n");
                                    mainDataList.get(i).setmChecked("n");
                                }
                            }
                        }
                        filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(getActivity(), pt_o_c);
                        listview.setAdapter(filter_plant_type_adapter);
                    } else {

                    }

                }
            });
            holder.radio_status.setText(mainDataList.get(position).getmSwerk_pt());
            holder.radio_status.setTag(position);
            if (mainDataList.get(position).getmChecked().equals("y")) {
                holder.radio_status.setChecked(true);
            } else {
                holder.radio_status.setChecked(false);
            }
            holder.Name.setText(mainDataList.get(position).getmName_pt());

            return view;
        }
    }
    /* get filter Plant Adapter */


    public class WKCENTER_TYPE_ADAPTER extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_EtOrdArbplTotal_Object> mainDataList = null;
        private ArrayList<Mis_EtOrdArbplTotal_Object> arraylist;

        public WKCENTER_TYPE_ADAPTER(Context context, List<Mis_EtOrdArbplTotal_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtOrdArbplTotal_Object>();
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
        public Mis_EtOrdArbplTotal_Object getItem(int position) {
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
                holder = ( ViewHolder) view.getTag();
            }

            if (!wrkcnt_ctp.equals("")) {
                for (int i = 0; i < art_fo_c.size(); i++) {
                    if (art_fo_c.get(i).getmArbpl_art().equals(wrkcnt_ctp)) {
                        holder.radio_status.setChecked(true);
                        art_fo_c.get(i).setmChecked("y");
                    } else {
                        art_fo_c.get(i).setmChecked("n");
                    }
                }
            } else if (!wrkcnt_ct.equals("")) {
                for (int j = 0; j < art_fo_c.size(); j++) {
                    if (art_fo_c.get(j).getmArbpl_art().equals(wrkcnt_ct)) {
                        holder.radio_status.setChecked(true);
                        art_fo_c.get(j).setmChecked("y");
                    } else {
                        art_fo_c.get(j).setmChecked("n");
                    }
                }
            } else if (!wrkcnt_c.equals("")) {
                for (int j = 0; j < art_fo_c.size(); j++) {
                    if (art_fo_c.get(j).getmArbpl_art().equals(wrkcnt_c)) {
                        holder.radio_status.setChecked(true);
                        art_fo_c.get(j).setmChecked("y");
                    } else {
                        art_fo_c.get(j).setmChecked("n");
                    }
                }
            }


            holder.radio_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String wkcenter = holder.radio_status.getText().toString();
                    if (holder.radio_status.isChecked()) {
                        for (int i = 0; i < art_fo_c.size(); i++) {
                            String new_wkcenter = art_fo_c.get(i).getmArbpl_art();
                            String new_wrkname = art_fo_c.get(i).getmName_art();
                            if (wkcenter.equalsIgnoreCase(new_wkcenter)) {
                                art_fo_c.get(i).setmChecked("y");
                                mainDataList.get(i).setmChecked("y");
                                wrkcnt_ctp = new_wkcenter;
                                wrkcnt_name_ctp = new_wrkname;
                                changed_cw = true;
                            } else {
                                String status = art_fo_c.get(i).getmChecked();
                                if (status.equals("y")) {
                                    art_fo_c.get(i).setmChecked("n");
                                    mainDataList.get(i).setmChecked("n");
                                }
                            }
                        }
                        wkcenter_type_adapter = new WKCENTER_TYPE_ADAPTER(getActivity(), art_fo_c);
                        listview.setAdapter(wkcenter_type_adapter);
                    } else {

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

    protected void show_error_dialog(String string) {
        errordialog = new Dialog(getActivity());
        errordialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        errordialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errordialog.setCancelable(false);
        errordialog.setCanceledOnTouchOutside(false);
        errordialog.setContentView(R.layout.error_dialog);
        final TextView description_textview = (TextView) errordialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) errordialog.findViewById(R.id.ok_button);
        description_textview.setText(string);
        errordialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errordialog.dismiss();
            }
        });
    }

    private static boolean contains(final List<Mis_EtOrdTypeTotal_Object> transaction, final String search) {
        for (final Mis_EtOrdTypeTotal_Object transactionLine : transaction) {
            if (transactionLine.getmAuart_tt().equals(search)) {
                return true;
            }
        }
        return false;
    }
}
