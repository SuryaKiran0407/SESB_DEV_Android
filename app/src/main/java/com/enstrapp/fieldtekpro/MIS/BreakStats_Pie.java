package com.enstrapp.fieldtekpro.MIS;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
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

import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment_inspection.Mis_Break_Stat_Object;
import com.enstrapp.fieldtekpro.equipment_inspection.MyMarkerView;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RAKSHITHA V SUVARNA on 15-03-2018.
 */

public class BreakStats_Pie extends Activity {

    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private int TimeOut = 90000;
    String status = "timeout";
    Boolean isInternetPresent = false;
    private static SQLiteDatabase App_db;
    ConnectionDetector cd;
    private final String NAMESPACE = "urn:sap-com:document:sap:soap:functions:mc-style";
    String transmit_type = "", username = "", password = "", header_credentials = "", url_host_ipaddress = "", device_id = "", device_uuid = "", device_serial_number = "", url_link = "";
    ProgressDialog progressdialog;
    TextView heading, no_data, plant_head, plant_button, area_button;
    ImageView backImage, filter_imageview, pfy_imageview;
    int count = 0;

    /*PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;*/
    CombinedChart mChart;
    BarDataSet barDataSet;
    LineDataSet lineDataSet_c, lineDataSet_t;
    BarData barData;
    LineData lineData_c, lineData_t;
    Breakdown_Statistics breakdown_statistics;
    Dialog errordialog, area_dialog, plant_area_dialog, plant_dialog, network_error_dialog;
    ListView listview;
    AREA_ADAPTER area_adapter;
    FILTER_PLANT_TYPE_Adapter filter_plant_type_adapter;

    String sunit = "", smsaus = "", sauszt = "", seqtbr = "";
    float smsaus1, sauszt1;
    ArrayList<String> Spmon = new ArrayList<String>();
    ArrayList<String> SpmonS = new ArrayList<String>();
    ArrayList<String> Sunit = new ArrayList<String>();
    ArrayList<String> Smsaus = new ArrayList<String>();
    ArrayList<String> Sauszt = new ArrayList<String>();
    ArrayList<String> Seqtbr = new ArrayList<String>();
    ArrayList<Float> Smsaus1 = new ArrayList<Float>();
    ArrayList<Float> Sauszt1 = new ArrayList<Float>();


    ArrayList<Mis_Break_Stat_Object> bpt = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bpmt = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bmt = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bwt_f = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bmt_cm = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bwt = new ArrayList<Mis_Break_Stat_Object>();
    ArrayList<Mis_Break_Stat_Object> bpmt_c = new ArrayList<Mis_Break_Stat_Object>();
    String iwerk = "", fy = "", area_t = "", area = "", py = "", pfy = "", sign = "", bt = "", plant_name = "",
            plant_name_t = "", iwerk_t = "", area_w = "", iwerk_tp = "", plant_name_tp = "";
    boolean changed = false, changed_w = false;
    String[] SpmonSS;
    float[] count_m, tot_m2, mttr;
    String currentMonth = "", cMonth_t = "";
    String isDevice_LowMonth = "", isDevice_HighMonth = "", value = "";
    Date date;

    private static String DATABASE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_break_stats_pie);

        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        heading = (TextView) findViewById(R.id.mis_brk_stats_text);
        no_data = (TextView) findViewById(R.id.no_data_textview);
        plant_head = (TextView) findViewById(R.id.brk_pie_heading);
        pfy_imageview = (ImageView) findViewById(R.id.pfy);

        username = FieldTekPro_SharedPref.getString("FieldTekPro_Username", null);
        password = FieldTekPro_SharedPref.getString("FieldTekPro_Password", null);
        header_credentials = FieldTekPro_SharedPref.getString("header_credentials", null);
        url_host_ipaddress = FieldTekPro_SharedPref.getString("FieldTekPro_Host_Url", null);

        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences
                ("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        DATABASE_NAME = getString(R.string.database_name);
        App_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        final String androidId = "" +
                Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32)
                | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();

        mChart = (CombinedChart) findViewById(R.id.bar_chart_break);
        filter_imageview = (ImageView) findViewById(R.id.filter_imageview);


        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);

        if (month > 3) {
            String fyl = String.valueOf(year + 1);
            fy = fyl.substring(2, 4);
        } else {
            String fyl = String.valueOf(year);
            fy = fyl.substring(2, 4);
        }


        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMM yyyy";
        String isDevice_LowPattern = "yyyy-MM-dd";
        String currentMonth_Pattern = "yyyyMM";
        String cMonth_Pattern = "MMMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        SimpleDateFormat isDevice_LowFormat = new SimpleDateFormat(isDevice_LowPattern);
        SimpleDateFormat currentMonth_Format = new SimpleDateFormat(currentMonth_Pattern);
        SimpleDateFormat cMonth_Format = new SimpleDateFormat(cMonth_Pattern);
        if (day == 1) {
            if (month == 1) {
                year = calendar.get(Calendar.YEAR) - 1;
                month = calendar.get(Calendar.MONTH) + 12;
            } else {
                month = calendar.get(Calendar.MONTH);
            }
            if (month > 03) {
                try {
                    int from_year = calendar.get(Calendar.YEAR);
                    Date date = inputFormat.parse(from_year + "-" + 04 + "-" + day);
                    String datee = outputFormat.format(date);
                    isDevice_LowMonth = isDevice_LowFormat.format(date);
//                    from_date.setText(datee);
                } catch (ParseException e) {
                    Log.v("from_Date_exception", "" + e.getMessage());
                }
            } else {
                try {
                    int from_year = calendar.get(Calendar.YEAR) - 1;
                    Date date = inputFormat.parse(from_year + "-" + 04 + "-" + day);
                    String datee = outputFormat.format(date);
                    isDevice_LowMonth = isDevice_LowFormat.format(date);
//                from_date.setText(datee);
                } catch (ParseException e) {
                    Log.v("from_Date_exception", "" + e.getMessage());
                }
            }
        } else {
            if (month > 03) {
                try {
                    int from_year = calendar.get(Calendar.YEAR);
                    Date date = inputFormat.parse(from_year + "-" + 04 + "-" + day);
                    String datee = outputFormat.format(date);
                    isDevice_LowMonth = isDevice_LowFormat.format(date);
                } catch (ParseException e) {
                    Log.v("from_Date_exception", "" + e.getMessage());
                }
            } else {
                try {
                    int from_year = calendar.get(Calendar.YEAR) - 1;
                    Date date = inputFormat.parse(from_year + "-" + 04 + "-" + day);
                    String datee = outputFormat.format(date);
                    isDevice_LowMonth = isDevice_LowFormat.format(date);
//                from_date.setText(datee);
                } catch (ParseException e) {
                    Log.v("from_Date_exception", "" + e.getMessage());
                }
            }
        }

        int day_to = calendar.get(Calendar.DATE) - 1;
        try {
            Date date = inputFormat.parse(year + "-" + month + "-" + day_to);
            String datee = outputFormat.format(date);
            isDevice_HighMonth = isDevice_LowFormat.format(date);
            currentMonth = currentMonth_Format.format(date);
            cMonth_t = cMonth_Format.format(date);
            Log.v("current_month", "" + currentMonth + "..year.." + year + "..month.." + month + "...day.." + day_to);
        } catch (ParseException e) {
            Log.v("to_Date_exception", "" + e.getMessage());
        }
        py = "";
        pfy = "";
        sign = "I";
        bt = "BT";

        backImage = (ImageView) findViewById(R.id.back_imageview);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        filter_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant_area_dialog = new Dialog(BreakStats_Pie.this, R.style.AppThemeDialog_Dark);
                Window window = plant_area_dialog.getWindow();
                plant_area_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                plant_area_dialog.setContentView(R.layout.work_plant_filter_dialog);
                plant_area_dialog.setCancelable(true);
                plant_area_dialog.setCanceledOnTouchOutside(false);
                TextView area_selector = (TextView) plant_area_dialog.findViewById(R.id.area_selector);
                area_selector.setText("Select Area");
                area_selector.setVisibility(View.GONE);
                TextView clear_all_textview = (TextView) plant_area_dialog.findViewById(R.id.clear_all_textview);
                clear_all_textview.setVisibility(View.GONE);
                plant_button = (TextView) plant_area_dialog.findViewById(R.id.plant_tv);
                area_button = (TextView) plant_area_dialog.findViewById(R.id.wrkcntr_tv);
                Button close_filter_button = (Button) plant_area_dialog.findViewById(R.id.close_filter_button);
                Button filter_button = (Button) plant_area_dialog.findViewById(R.id.filter_button);

                area_button.setVisibility(View.GONE);

                if (!plant_name.equals("")) {
                    plant_button.setText(plant_name);
                } else {

                }
                if (!area.equals("")) {
                    area_button.setText(area);
                }

                clear_all_textview.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//                        plant_all = true;
                        plant_name = "";
                        iwerk = "";
                        area = "";
                        iwerk_t = "";
                        area_w = "";
                        plant_name_t = "";
                        plant_button.setText(plant_name);
                        area_button.setText(area);
                        for (int i = 0; i < bpt.size(); i++) {
                            bpt.get(i).setmChecked("n");
                        }
                        if (bwt_f.size() > 0) {
                            for (int i = 0; i < bwt_f.size(); i++) {
                                bwt_f.get(i).setmChecked("n");
                            }
                        }
                        plant_area_dialog.dismiss();
                    }
                });

                plant_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        plant_dialog = new Dialog(BreakStats_Pie.this, R.style.AppThemeDialog_Dark);
                        plant_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        plant_dialog.setCancelable(true);
                        plant_dialog.setCanceledOnTouchOutside(false);
                        plant_dialog.setContentView(R.layout.permit_plant_dialog);
                        plant_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                        TextView clear_all_textview = (TextView) plant_dialog.findViewById(R.id.clear_all_textview);
                        clear_all_textview.setVisibility(View.GONE);
                        Button cancel_filter_button = (Button) plant_dialog.findViewById(R.id.cancel_filter_button);
                        Button ok_filter_button = (Button) plant_dialog.findViewById(R.id.ok_filter_button);
                        TextView no_data_textview = (TextView) plant_dialog.findViewById(R.id.no_data_textview);
                        listview = (ListView) plant_dialog.findViewById(R.id.listview);
                        if (listview != null) {
                            listview.setAdapter(null);
                        }
                        if (bpt.size() <= 0) {
                            listview.setVisibility(View.GONE);
                            no_data_textview.setVisibility(View.VISIBLE);
                        } else {
                            listview.setVisibility(View.VISIBLE);
                            no_data_textview.setVisibility(View.GONE);
                            filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(BreakStats_Pie.this, bpt);
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
                                    area_t = "";
                                    plant_button.setText(plant_name_t);
                                    area_button.setText(area_t);
                                    plant_dialog.dismiss();
                                } else {
                                    plant_dialog.dismiss();
                                }
                            }
                        });
                        clear_all_textview.setOnClickListener(new View.OnClickListener()

                        {
                            @Override
                            public void onClick(View v) {
//                                plant_all = true;
                                iwerk = "";
                                plant_name = "";
                                area = "";
                                iwerk_t = "";
                                plant_name_t = "";
                                area_t = "";
                                area_w = "";
                                for (int i = 0; i < bpt.size(); i++) {
                                    bpt.get(i).setmChecked("n");
                                }
                                if (bwt_f.size() > 0) {
                                    for (int j = 0; j < bwt_f.size(); j++) {
                                        bwt_f.get(j).setmChecked("n");
                                    }
                                }
                                plant_button.setText(plant_name);
                                area_button.setText(area);
                                filter_plant_type_adapter.notifyDataSetChanged();
                                listview.setAdapter(filter_plant_type_adapter);
                            }
                        });
                        plant_dialog.show();
                    }
                });

                area_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!iwerk.equals("") && iwerk != null) {
                            if (iwerk_t.equals("") || iwerk_t == null) {
                                bwt_f.clear();
                                for (int i = 0; i < bwt.size(); i++) {
                                    if (bwt.get(i).getIwerk().equals(iwerk)) {
                                        Mis_Break_Stat_Object bwt_fo = new Mis_Break_Stat_Object();
                                        bwt_fo.setIwerk(bwt.get(i).getIwerk());
                                        bwt_fo.setWarea(bwt.get(i).getWarea());
                                        bwt_fo.setmChecked("n");
                                        bwt_f.add(bwt_fo);
                                    }
                                }

                                area_dialog = new Dialog(BreakStats_Pie.this, R.style.AppThemeDialog_Dark);
                                area_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                area_dialog.setCancelable(true);
                                area_dialog.setCanceledOnTouchOutside(false);
                                area_dialog.setContentView(R.layout.permit_plant_dialog);
                                area_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) area_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText(getString(R.string.area_slct));
                                TextView clear_all_textview = (TextView) area_dialog.findViewById(R.id.clear_all_textview);
                                clear_all_textview.setVisibility(View.GONE);
                                Button cancel_filter_button = (Button) area_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) area_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) area_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) area_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (bwt_f.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    area_adapter = new AREA_ADAPTER(BreakStats_Pie.this, bwt_f);
                                    listview.setAdapter(area_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
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
                                            area_t = area_w;
                                            area_w = "";
                                            area_button.setText(area_t);
                                            area_dialog.dismiss();
                                        } else {
                                            area_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        area = "";
                                        area_t = "";
                                        area_w = "";
                                        area_button.setText("");
                                        if (bwt_f.size() > 0) {
                                            for (int j = 0; j < bwt_f.size(); j++) {
                                                bwt_f.get(j).setmChecked("n");
                                            }
                                        }
                                        area_adapter.notifyDataSetChanged();
                                        listview.setAdapter(area_adapter);
                                    }
                                });

                            } else {

                                bwt_f.clear();
                                for (int i = 0; i < bwt.size(); i++) {
                                    if (bwt.get(i).getIwerk().equals(area_t)) {
                                        Mis_Break_Stat_Object bwt_fo = new Mis_Break_Stat_Object();
                                        bwt_fo.setIwerk(bwt.get(i).getIwerk());
                                        bwt_fo.setWarea(bwt.get(i).getWarea());
                                        bwt_fo.setmChecked("n");
                                        bwt_f.add(bwt_fo);
                                    }
                                }

                                area_dialog = new Dialog(BreakStats_Pie.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                area_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                area_dialog.setCancelable(true);
                                area_dialog.setCanceledOnTouchOutside(false);
                                area_dialog.setContentView(R.layout.permit_plant_dialog);
                                area_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) area_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText(getString(R.string.area_slct));
                                TextView clear_all_textview = (TextView) area_dialog.findViewById(R.id.clear_all_textview);
                                clear_all_textview.setVisibility(View.GONE);
                                Button cancel_filter_button = (Button) area_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) area_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) area_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) area_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (bwt_f.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    area_adapter = new AREA_ADAPTER(BreakStats_Pie.this, bwt_f);
                                    listview.setAdapter(area_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
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
                                            area_t = area_w;
                                            area_w = "";
                                            area_button.setText(area_t);
                                            area_dialog.dismiss();
                                        } else {
                                            area_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        area = "";
                                        area_t = "";
                                        area_w = "";
                                        area_button.setText("");
                                        if (bwt_f.size() > 0) {
                                            for (int j = 0; j < bwt_f.size(); j++) {
                                                bwt_f.get(j).setmChecked("n");
                                            }
                                        }
                                        area_adapter.notifyDataSetChanged();
                                        listview.setAdapter(area_adapter);
                                    }
                                });


                            }
                            area_dialog.show();
                        } else if (iwerk.equals("") || iwerk == null) {
                            if (iwerk_t.equals("") || iwerk_t == null) {
                                show_error_dialog(getString(R.string.slct_palnt));
                            } else {
                                bwt_f.clear();
                                for (int i = 0; i < bwt.size(); i++) {
                                    if (bwt.get(i).getIwerk().equals(iwerk_t)) {
                                        Mis_Break_Stat_Object bwt_fo = new Mis_Break_Stat_Object();
                                        bwt_fo.setIwerk(bwt.get(i).getIwerk());
                                        bwt_fo.setWarea(bwt.get(i).getWarea());
                                        bwt_fo.setmChecked("n");
                                        bwt_f.add(bwt_fo);
                                    }
                                }

                                area_dialog = new Dialog(BreakStats_Pie.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                area_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                area_dialog.setCancelable(true);
                                area_dialog.setCanceledOnTouchOutside(false);
                                area_dialog.setContentView(R.layout.permit_plant_dialog);
                                area_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                                TextView select_wrkcnt = (TextView) area_dialog.findViewById(R.id.select_notif);
                                select_wrkcnt.setText(getString(R.string.area_slct));
                                TextView clear_all_textview = (TextView) area_dialog.findViewById(R.id.clear_all_textview);
                                clear_all_textview.setVisibility(View.GONE);
                                Button cancel_filter_button = (Button) area_dialog.findViewById(R.id.cancel_filter_button);
                                Button ok_filter_button = (Button) area_dialog.findViewById(R.id.ok_filter_button);
                                TextView no_data_textview = (TextView) area_dialog.findViewById(R.id.no_data_textview);
                                listview = (ListView) area_dialog.findViewById(R.id.listview);
                                if (listview != null) {
                                    listview.setAdapter(null);
                                }
                                if (bwt_f.size() <= 0) {
                                    listview.setVisibility(View.GONE);
                                    no_data_textview.setVisibility(View.VISIBLE);
                                } else {
                                    listview.setVisibility(View.VISIBLE);
                                    no_data_textview.setVisibility(View.GONE);
                                    area_adapter = new AREA_ADAPTER(BreakStats_Pie.this, bwt_f);
                                    listview.setAdapter(area_adapter);
                                }

                                cancel_filter_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (changed_w) {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_w = "";
                                                    changed_w = false;
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    changed_w = false;
                                                    area_w = "";
                                                    area_dialog.dismiss();
                                                }
                                            }
                                        } else {
                                            if (area.equals("")) {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                }
                                            } else {
                                                if (area_t.equals("")) {
                                                    area_dialog.dismiss();
                                                } else if (!area_t.equals("")) {
                                                    area_dialog.dismiss();
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
                                            area_t = area_w;
                                            area_w = "";
                                            area_button.setText(area_t);
                                            area_dialog.dismiss();
                                        } else {
                                            area_dialog.dismiss();
                                        }
                                    }
                                });

                                clear_all_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        area = "";
                                        area_t = "";
                                        area_w = "";
                                        area_button.setText("");
                                        if (bwt_f.size() > 0) {
                                            for (int j = 0; j < bwt_f.size(); j++) {
                                                bwt_f.get(j).setmChecked("n");
                                            }
                                        }
                                        area_adapter.notifyDataSetChanged();
                                        listview.setAdapter(area_adapter);
                                    }
                                });
                                area_dialog.show();
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
                                    area_t = "";
                                    plant_name_t = "";
                                    wAreas(iwerk, area);
                                    plant_area_dialog.dismiss();
                                } else {
                                    if (area_t.equals("") || area_t == null) {
                                        iwerk = iwerk_t;
                                        plant_name = plant_name_t;
                                        iwerk_t = "";
                                        plant_name_t = "";
                                        area = "";
                                        wAreas(iwerk, area);
                                        plant_area_dialog.dismiss();
                                    } else {
                                        iwerk = iwerk_t;
                                        area = area_t;
                                        plant_name = plant_name_t;
                                        iwerk_t = "";
                                        plant_name_t = "";
                                        area_t = "";
                                        wAreas(iwerk, area);
                                        plant_area_dialog.dismiss();
                                    }
                                }
                            } else {
                                if (!area.equals("")) {
                                    if (area_t.equals("")) {
                                        plant_area_dialog.dismiss();
                                    } else {
                                        if (!area.equals(area_t)) {
                                            area = area_t;
                                            area_t = "";
                                            wAreas(iwerk, area);
                                            plant_area_dialog.dismiss();
                                        } else {
                                            area_t = "";
                                            plant_area_dialog.dismiss();
                                        }
                                    }

                                } else {
                                    area = area_t;
                                    area_t = "";
                                    wAreas(iwerk, area);
                                    plant_area_dialog.dismiss();
                                }
                            }
                        } else {
                            if (iwerk_t.equals("")) {
//                                allPlant();
                                plant_area_dialog.dismiss();
                            } else {
                                if (area_t.equals("") || area_t == null) {
                                    iwerk = iwerk_t;
                                    plant_name = plant_name_t;
                                    iwerk_t = "";
                                    plant_name_t = "";
                                    wAreas(iwerk, area);
                                    plant_area_dialog.dismiss();
                                } else {
                                    iwerk = iwerk_t;
                                    area = area_t;
                                    plant_name = plant_name_t;
                                    iwerk_t = "";
                                    plant_name_t = "";
                                    area_t = "";
                                    wAreas(iwerk, area);
                                    plant_area_dialog.dismiss();
                                }
                            }
                        }
                    }
                });

                close_filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!iwerk.equals("")) {
                            iwerk_t = "";
                            plant_name_t = "";
                        }
                        if (!area_t.equals("")) {
                            area_t = "";
                        }
                        plant_area_dialog.dismiss();
                    }
                });
                plant_area_dialog.show();


            }
        });

        pfy_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    int pf = Integer.parseInt(fy);
                    pf = pf - 1;
                    fy = String.valueOf(pf);
                    show_confirmation_dialg(getString(R.string.fy_data, fy));

                } else {
                    noInternet();
                }
            }
        });

        breakdown_statistics = new Breakdown_Statistics();
        breakdown_statistics.execute();
    }

    private class Breakdown_Statistics extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(BreakStats_Pie.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                /* Initializing Shared Preferences */
                FieldTekPro_SharedPref = getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
                username = FieldTekPro_SharedPref.getString("Username", null);
                password = FieldTekPro_SharedPref.getString("Password", null);
                String webservice_type = FieldTekPro_SharedPref.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"R1", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                } else {
                }
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                device_serial_number = Build.SERIAL;
                String androidId = "" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
                device_uuid = deviceUuid.toString();
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                String URL = getString(R.string.ip_address);


                Map<String, String> map = new HashMap<>();
                map.put("Muser", username.toUpperCase().toString());
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("IvTransmitType", "MISR");
                map.put("DateSign", sign);
                map.put("DateOption", bt);
                map.put("DateLow", "");
                map.put("DateHigh", "");
                map.put("IvLyear", pfy);
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<BreakStatsPie_SER> call = service.getBreakStatsPieAnalysis(url_link, basic, map);
                Response<BreakStatsPie_SER> response = call.execute();
                int response_status_code = response.code();
                Log.v("kiran_BreakStatsPie", response_status_code + "...");

                if (response_status_code == 200) {
                    if (response.isSuccessful() && response.body() != null) {

                        /*Reading Response Data and Parsing to Serializable*/
                        BreakStatsPie_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                        for (int i = 0; i < response_data_jsonArray.length(); i++) {

                            /*Reading Data by using FOR Loop*/
                            JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());

                            /*Reading and Inserting Data into Database Table for EsPermitTotal UUID*/
                            // App_db.beginTransaction();

                            /*Reading and Inserting Data into Database Table for EsPermitTotal*/
                            if (jsonObject.has("EtBkdnMonthTotal")) {
                                try {
                                    String EtBkdnMonthTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnMonthTotal().getResults());


                                    JSONArray jsonArray = new JSONArray(EtBkdnMonthTotal_response_data);
                                    if (jsonArray.length() > 0) {
                                        bmt.clear();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            Mis_Break_Stat_Object bpt_o = new Mis_Break_Stat_Object();

                                            bpt_o.setIwerk(jsonArray.getJSONObject(j).optString("Iwerk"));
                                            bpt_o.setArbpl(jsonArray.getJSONObject(j).optString("Warea"));
                                            bpt_o.setSpmon(jsonArray.getJSONObject(j).optString("Arbpl"));
                                            bpt_o.setSunit(jsonArray.getJSONObject(j).optString("Spmon"));
                                            bpt_o.setSmsaus(jsonArray.getJSONObject(j).optString("Sunit"));
                                            bpt_o.setCount(jsonArray.getJSONObject(j).optString("Smsaus"));
                                            bpt_o.setTotM2(jsonArray.getJSONObject(j).optString("Count"));
                                            bpt_o.setTotM3(jsonArray.getJSONObject(j).optString("TotM2"));
                                            bpt_o.setBdpmrat(jsonArray.getJSONObject(j).optString("TotM3"));
                                            bpt_o.setWitHrs(jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                            bpt_o.setWitoutHrs(jsonArray.getJSONObject(j).optString("WitHrs"));
                                            bpt_o.setMttrHours(jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                            bpt_o.setMtbrHours(jsonArray.getJSONObject(j).optString("MttrHours"));
                                            bpt_o.setName(jsonArray.getJSONObject(j).optString("MtbrHours"));
                                            bpt_o.setName(jsonArray.getJSONObject(j).optString("Name"));

                                            bmt.add(bpt_o);

                                        }
                                    }


                                } catch (Exception e) {
                                }

                            } else {

                            }

                            if (jsonObject.has("EtBkdnPlantTotal")) {
                                try {
                                    String EtBkdnPlantTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnPlantTotal().getResults());
                                    JSONArray jsonArray = new JSONArray(EtBkdnPlantTotal_response_data);
                                    if (jsonArray.length() > 0) {
                                        bpt.clear();

                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            Mis_Break_Stat_Object bpt_o = new Mis_Break_Stat_Object();
                                            bpt_o.setIwerk(jsonArray.getJSONObject(j).optString("Iwerk"));
                                            bpt_o.setWarea(jsonArray.getJSONObject(j).optString("Warea"));
                                            bpt_o.setArbpl(jsonArray.getJSONObject(j).optString("Arbpl"));
                                            bpt_o.setSpmon(jsonArray.getJSONObject(j).optString("Spmon"));
                                            bpt_o.setSunit(jsonArray.getJSONObject(j).optString("Sunit"));
                                            bpt_o.setSmsaus(jsonArray.getJSONObject(j).optString("Smsaus"));
                                            bpt_o.setCount(jsonArray.getJSONObject(j).optString("Count"));
                                            bpt_o.setTotM2(jsonArray.getJSONObject(j).optString("TotM2"));
                                            bpt_o.setTotM3(jsonArray.getJSONObject(j).optString("TotM3"));
                                            bpt_o.setBdpmrat(jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                            bpt_o.setWitHrs(jsonArray.getJSONObject(j).optString("WitHrs"));
                                            bpt_o.setWitoutHrs(jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                            bpt_o.setMttrHours(jsonArray.getJSONObject(j).optString("MttrHours"));
                                            bpt_o.setMtbrHours(jsonArray.getJSONObject(j).optString("MtbrHours"));
                                            bpt_o.setName(jsonArray.getJSONObject(j).optString("Name"));

                                            bpt.add(bpt_o);
                                        }
                                    }

                                } catch (Exception e) {
                                }
                            }


                            if (jsonObject.has("EtBkdnPmonthTotal")) {
                                try {
                                    String EtBkdnPmonthTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnPmonthTotal().getResults());
                                    JSONArray jsonArray = new JSONArray(EtBkdnPmonthTotal_response_data);
                                    if (jsonArray.length() > 0) {
                                        bpmt.clear();
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            Mis_Break_Stat_Object bpmt_o = new Mis_Break_Stat_Object();

                                            bpmt_o.setIwerk(jsonArray.getJSONObject(j).optString("Iwerk"));
                                            bpmt_o.setWarea(jsonArray.getJSONObject(j).optString("Warea"));
                                            bpmt_o.setSpmon(jsonArray.getJSONObject(j).optString("Spmon"));
                                            bpmt_o.setSunit(jsonArray.getJSONObject(j).optString("Sunit"));
                                            bpmt_o.setSmsaus(jsonArray.getJSONObject(j).optString("Smsaus"));
                                            bpmt_o.setCount(jsonArray.getJSONObject(j).optString("Count"));
                                            bpmt_o.setTotM2(jsonArray.getJSONObject(j).optString("TotM2"));
                                            bpmt_o.setTotM3(jsonArray.getJSONObject(j).optString("TotM3"));
                                            bpmt_o.setBdpmrat(jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                            bpmt_o.setWitHrs(jsonArray.getJSONObject(j).optString("WitHrs"));
                                            bpmt_o.setWitoutHrs(jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                            bpmt_o.setMttrHours(jsonArray.getJSONObject(j).optString("MttrHours"));
                                            bpmt_o.setMtbrHours(jsonArray.getJSONObject(j).optString("MtbrHours"));
                                            bpmt_o.setName(jsonArray.getJSONObject(j).optString("Name"));

                                            bpmt.add(bpmt_o);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }

                            if (jsonObject.has("EtBkdnWareaTotal")) {
                                try {
                                    String EtBkdnWareaTotal_response_data = new Gson().toJson(rs.getD().getResults().get(i).getEtBkdnWareaTotal().getResults());
                                    JSONArray jsonArray = new JSONArray(EtBkdnWareaTotal_response_data);
                                    if (jsonArray.length() > 0) {
                                        bwt.clear();
                                        for (int j = 0; j < jsonArray.length(); j++) {

                                            Mis_Break_Stat_Object bwt_o = new Mis_Break_Stat_Object();
                                            bwt_o.setIwerk(jsonArray.getJSONObject(j).optString("Iwerk"));
                                            bwt_o.setWarea(jsonArray.getJSONObject(j).optString("Warea"));
                                            bwt_o.setArbpl(jsonArray.getJSONObject(j).optString("Arbpl"));
                                            bwt_o.setSpmon(jsonArray.getJSONObject(j).optString("Spmon"));
                                            bwt_o.setSunit(jsonArray.getJSONObject(j).optString("Sunit"));
                                            bwt_o.setSmsaus(jsonArray.getJSONObject(j).optString("Smsaus"));
                                            bwt_o.setCount(jsonArray.getJSONObject(j).optString("Count"));
                                            bwt_o.setTotM2(jsonArray.getJSONObject(j).optString("TotM2"));
                                            bwt_o.setTotM3(jsonArray.getJSONObject(j).optString("TotM3"));
                                            bwt_o.setBdpmrat(jsonArray.getJSONObject(j).optString("Bdpmrat"));
                                            bwt_o.setWitHrs(jsonArray.getJSONObject(j).optString("WitHrs"));
                                            bwt_o.setWitoutHrs(jsonArray.getJSONObject(j).optString("WitoutHrs"));
                                            bwt_o.setMttrHours(jsonArray.getJSONObject(j).optString("MttrHours"));
                                            bwt_o.setMtbrHours(jsonArray.getJSONObject(j).optString("MtbrHours"));
                                            bwt_o.setName(jsonArray.getJSONObject(j).optString("Name"));

                                            bwt.add(bwt_o);
                                        }
                                    }

                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                status = "success";

            } catch (Exception e) {
                status = "exception";
                Log.v("sashi_exception", "" + e.getMessage());
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           /* if (!py.equals("X"))
            {
            }
            else
            {

            }*/
            Log.v("akshra_status", status + "...");
            if (status != null && !status.equals("")) {
                if (!status.equals("success")) {
                    mChart.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                } else {
                    mChart.setVisibility(View.VISIBLE);
                    no_data.setVisibility(View.GONE);
                    // pfy_imageview.setVisibility(View.GONE);

                    if (bpt.size() <= 0) {
                        mChart.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        progressdialog.dismiss();
                    } else {
                        mChart.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                        try {
                            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                            ArrayList<Entry> line_c = new ArrayList<Entry>();
                            ArrayList<Entry> line_t = new ArrayList<Entry>();
                            String outputPattern = "MMM yyyy";
                            String inputPattern = "yyyyMM";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            SpmonS.clear();
                            bpmt_c.clear();
                                /*if (area.equals("")) {
                                    for (int l = 0; l < bpmt.size(); l++) {
                                        if (bpmt.get(l).getIwerk().equals(iwerk)) {
                                            Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                                            bmpt_o.setName(bpmt.get(l).getName());
                                            bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                                            bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                                            bmpt_o.setCount(bpmt.get(l).getCount());
                                            bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                                            bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                                            bpmt_c.add(bmpt_o);
                                            Log.v("break_pie", "" + bpmt.get(l).getSpmon());
                                        }
                                    }

                                } else {
                                    for (int l = 0; l < bpmt.size(); l++) {
                                        if (bpmt.get(l).getIwerk().equals(iwerk) && bpmt.get(l).getWarea().equals(area)) {
                                            Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                                            bmpt_o.setName(bpmt.get(l).getName());
                                            bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                                            bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                                            bmpt_o.setCount(bpmt.get(l).getCount());
                                            bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                                            bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                                            bpmt_c.add(bmpt_o);
                                            Log.v("break_pie", "" + bpmt.get(l).getSpmon());
                                        }
                                    }
                                }*/

                            String work_iwerk = bpmt.get(0).getIwerk();
                            for (int l = 0; l < bpmt.size(); l++) {
                                if (bpmt.get(l).getIwerk().equals(work_iwerk)) {

                                    Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                                    bmpt_o.setName(bpmt.get(l).getName());
                                    bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                                    //bmpt_o.setTotM2(bpmt.get(l).getMtbrHours());
                                    iwerk = bpmt.get(l).getIwerk();


                                    bmpt_o.setCount(bpmt.get(l).getCount());
                                    bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                                    bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                                    bpmt_c.add(bmpt_o);
                                    Log.v("break_pie", "" + bpmt.get(l).getSpmon());
                                    /*  }*/
                                }
                            }
                            for (int k = 0; k < bpt.size(); k++) {
                                if (bpt.get(k).getIwerk().equals(iwerk)) {
                                    plant_name = bpt.get(k).getName();
                                }
                            }

                            mttr = new float[bpmt_c.size()];
                            count_m = new float[bpmt_c.size()];
                            //tot_m2 = new float[bpmt_c.size()];

                            SpmonSS = new String[bpmt_c.size()];
                            for (int j = 0; j < bpmt_c.size(); j++) {
                                try {
                                    if (bpmt_c.get(j).getCount() != null && !bpmt_c.get(j).getCount().equals("")) {
                                        count_m[j] = Float.parseFloat(bpmt_c.get(j).getCount());
                                    } else {
                                        count_m[j] = 0;
                                    }
                                    if (bpmt_c.get(j).getMttrHours() != null && !bpmt_c.get(j).getMttrHours().equals("")) {
                                        mttr[j] = Float.parseFloat(bpmt_c.get(j).getMttrHours());
                                    } else {
                                        mttr[j] = 0;
                                    }
                                        /*if (bpmt_c.get(j).getTotM2() != null && !bpmt_c.get(j).getTotM2().equals("")) {
                                            tot_m2[j] = Float.parseFloat(bpmt_c.get(j).getTotM2());
                                        } else {
                                            tot_m2[j] = 0;
                                        }*/
                                    if (bpmt_c.get(j).getSpmon() != null && !bpmt_c.get(j).getSpmon().equals("")) {
                                        try {
                                            date = inputFormat.parse(bpmt_c.get(j).getSpmon().substring(1));
                                            String dateS = outputFormat.format(date);
                                            SpmonS.add(dateS);
                                            SpmonSS[j] = "" + dateS;
                                        } catch (ParseException e) {

                                        }
                                    }
                                } catch (NumberFormatException nfe) {
                                    Log.v("Sashi_int", "Could not parse " + nfe);
                                }
                                entries.add(new BarEntry(j, mttr[j]));
                                line_c.add(new Entry(j, count_m[j]));
                                // line_t.add(new Entry(j, tot_m2[j]));
                            }
                                /*barDataSet = new BarDataSet(entries, "MTTR");
                                lineDataSet_c = new LineDataSet(line_c, "No# of BD");
                                *//*Drawable drawable = ContextCompat.getDrawable(Mis_Break_Stats_Pie.this, R.drawable.fade_blue);
                                lineDataSet_c.setFillDrawable(drawable);
                                lineDataSet_c.setDrawFilled(true);*//*
                                lineDataSet_c.setColor(Color.rgb(201, 26, 41));
                                if (line_c.size() == 1) {
                                    lineDataSet_c.setDrawCircles(true);
                                    lineDataSet_c.setCircleColor(Color.WHITE);
                                    lineDataSet_c.setCircleColorHole(Color.rgb(201, 26, 41));
                                } else {
                                    lineDataSet_c.setDrawCircles(false);
                                }
                                lineDataSet_c.setLineWidth(2);
                                lineDataSet_c.setDrawValues(false);
                                lineDataSet_c.setValueFormatter(new MyValueFormatter());
                                lineDataSet_t = new LineDataSet(line_t, "BD Hrs");
                                lineDataSet_t.setColor(Color.BLACK);
                                if (line_t.size() == 1) {
                                    lineDataSet_t.setDrawCircles(true);
                                    lineDataSet_t.setCircleColor(Color.WHITE);
                                    lineDataSet_t.setCircleColorHole(Color.BLACK);
                                } else {
                                    lineDataSet_t.setDrawCircles(false);
                                }
                                lineDataSet_t.setLineWidth(2);
                                lineDataSet_t.setValueFormatter(new MyValueFormatter());
                                lineDataSet_t.setDrawValues(false);

                                final ArrayList<Integer> colors = new ArrayList<Integer>();

                                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                                    colors.add(c);

                                for (int c : ColorTemplate.COLORFUL_COLORS)
                                    colors.add(c);

                                for (int c : ColorTemplate.JOYFUL_COLORS)
                                    colors.add(c);

                                for (int c : ColorTemplate.LIBERTY_COLORS)
                                    colors.add(c);

                                for (int c : ColorTemplate.PASTEL_COLORS)
                                    colors.add(c);

                                barDataSet.setColors(Color.rgb(49, 84, 154));
                                barDataSet.setValueFormatter(new MyValueFormatter());

                                barData = new BarData(barDataSet);
                                if (entries.size() == 1) {
                                    barData.setBarWidth(0.1f);
                                } else if (entries.size() == 2) {
                                    barData.setBarWidth(0.2f);
                                } else if (entries.size() == 3) {
                                    barData.setBarWidth(0.3f);
                                };
                                barData.setValueTextSize(12);
                                lineData_c = new LineData();
                                lineData_c.addDataSet(lineDataSet_c);
                                lineData_c.addDataSet(lineDataSet_t);
//                                lineData_c.setHighlightEnabled(true);
//                                lineData_c.setDrawValues(false);
                                lineDataSet_c.setDrawHighlightIndicators(false);
                                lineDataSet_c.setDrawHorizontalHighlightIndicator(false);

                                CombinedData data = new CombinedData();

                                data.setData(lineData_c);
                                data.setData(barData);

                                MyMarkerView mv = new MyMarkerView(Mis_Break_Stats_Pie.this, R.layout.markerview);
                                mv.setChartView(mChart);
                                mChart.setMarker(mv);

                                Legend l = mChart.getLegend();
                                l.setWordWrapEnabled(true);
                                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                                l.setDrawInside(true);
                                l.setTextSize(12);


                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setDrawGridLines(false);
                                xAxis.setGranularity(1f); // only intervals of 1 day
                                xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
                                xAxis.setAxisMaximum(data.getXMax()+0.5f);
                                xAxis.setAxisMinimum(data.getXMin()-0.5f);
//                                xAxis.setSpaceMin(barData.getBarWidth() / 2f);
//                                xAxis.setSpaceMax(barData.getBarWidth() / 2f);
                                YAxis rightAxis = mChart.getAxisRight();
                                rightAxis.setDrawLabels(false);

                                mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR,
*//*CombinedChart.DrawOrder.LINE, *//*
                                        CombinedChart.DrawOrder.LINE});
                                mChart.getDescription().setEnabled(false);
                                mChart.setExtraOffsets(2, 2, 2, 2);
                                mChart.setMinOffset(15.f);
//                                mChart.setVisibleXRange(-1,entries.size()+1);
//                    mChart.getXAxis().setAxisMinimum(-1);
//                    mChart.getXAxis().setAxisMaximum(mttr.length+1);
                                mChart.getAxisLeft().setAxisMinimum(0);
                                mChart.getAxisLeft().setDrawLabels(true);
                                mChart.getAxisLeft().setDrawGridLines(true);
                                mChart.getAxisRight().setDrawGridLines(false);
                                mChart.getAxisLeft().setDrawAxisLine(true);
                                mChart.getAxisRight().setDrawAxisLine(false);
                                mChart.getXAxis().setLabelRotationAngle(90);
                                mChart.getLegend().setEnabled(true);
                                mChart.animateY(1000);
                                mChart.getXAxis().setLabelCount(bpmt_c.size(), false);
                                mChart.notifyDataSetChanged();//Required if changes are made to pie value
                                mChart.setData(data);
                                mChart.invalidate();// for refreshing the chart
                                plant_head.setText(plant_name);
                                heading.setText("MTTR Monthly Trend - FY" + fy);
                                progressdialog.dismiss();
                                if (area.equals("")) {
                                    plant_head.setText(plant_name);
                                } else {
                                    plant_head.setText(plant_name + " - " + area);
                                }*/

                            barDataSet = new BarDataSet(entries, "MTTR");
                            lineDataSet_c = new LineDataSet(line_c, "No# of BD");
                            lineDataSet_c.setColor(Color.rgb(201, 26, 41));
                            if (line_c.size() == 1) {
                                lineDataSet_c.setDrawCircles(true);
                                lineDataSet_c.setCircleColor(Color.WHITE);
                                lineDataSet_c.setCircleColorHole(Color.rgb(201, 26, 41));
                            } else {
                                lineDataSet_c.setDrawCircles(false);
                            }
                            lineDataSet_c.setLineWidth(2);
                            lineDataSet_c.setValueFormatter(new MyValueFormatter());
                            lineDataSet_c.setDrawValues(false);
                              /*  lineDataSet_t = new LineDataSet(line_t, "BD Hrs");
                                lineDataSet_t.setColor(Color.BLACK);
                                if (line_t.size() == 1) {
                                    lineDataSet_t.setDrawCircles(true);
                                    lineDataSet_t.setCircleColor(Color.WHITE);
                                    lineDataSet_t.setCircleColorHole(Color.BLACK);
                                } else {
                                    lineDataSet_t.setDrawCircles(false);
                                }
                                lineDataSet_t.setLineWidth(2);
                                lineDataSet_t.setValueFormatter(new MyValueFormatter());
                                lineDataSet_t.setDrawValues(false);*/

                            final ArrayList<Integer> colors = new ArrayList<Integer>();

                            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.COLORFUL_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.JOYFUL_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.LIBERTY_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.PASTEL_COLORS)
                                colors.add(c);

                            barDataSet.setColors(Color.rgb(49, 84, 154));
                            barDataSet.setValueFormatter(new MyValueFormatter());

                            barData = new BarData(barDataSet);
                            if (entries.size() == 1) {
                                barData.setBarWidth(0.1f);
                            } else if (entries.size() == 2) {
                                barData.setBarWidth(0.2f);
                            } else if (entries.size() == 3) {
                                barData.setBarWidth(0.3f);
                            }
                            ;
                            barData.setValueTextSize(12);
                            lineData_c = new LineData();
                            lineData_c.addDataSet(lineDataSet_c);
                            // lineData_c.addDataSet(lineDataSet_t);
//                        lineData_c.setHighlightEnabled(true);
                            lineDataSet_c.setDrawHighlightIndicators(false);
                            lineDataSet_c.setDrawHorizontalHighlightIndicator(false);

                            CombinedData data = new CombinedData();

                            data.setData(lineData_c);
                            data.setData(barData);

                            MyMarkerView mv = new MyMarkerView(BreakStats_Pie.this, R.layout.markerview);
                            mv.setChartView(mChart);
                            mChart.setMarker(mv);

                            Legend l = mChart.getLegend();
                            l.setWordWrapEnabled(true);
                            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                            l.setDrawInside(true);
                            l.setTextSize(12);


                            XAxis xAxis = mChart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setDrawGridLines(false);
                            xAxis.setGranularity(1f); // only intervals of 1 day
                        /*if (entries.size()==1){
                            xAxis.setAxisMaximum(data.getXMax()+0.5f);
                            xAxis.setAxisMinimum(data.getXMin()-0.5f);
                        }else{
                            xAxis.setAxisMaximum(data.getXMax()+0.25f);
                            xAxis.setAxisMinimum(data.getXMin()-0.25f);
                        }*/
                            xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                            xAxis.setAxisMinimum(data.getXMin() - 0.25f);

//                        xAxis.setXOffset(.25f);
                            xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
//                        xAxis.setSpaceMin(barData.getBarWidth() / 2f);
//                        xAxis.setSpaceMax(barData.getBarWidth() / 2f);
                            YAxis rightAxis = mChart.getAxisRight();
                            rightAxis.setDrawLabels(false);

                            mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR,
/*CombinedChart.DrawOrder.LINE, */
                                    CombinedChart.DrawOrder.LINE});
                            mChart.getDescription().setEnabled(false);
                            mChart.setExtraOffsets(2, 2, 2, 2);
//                        mChart.setViewPortOffsets(3,0,3,0);
//                        mChart.setMinOffset(15.f);
//                        mChart.setVisibleXRangeMaximum(entries.size()+1);
//                    mChart.getXAxis().setAxisMinimum(0);
//                    mChart.getXAxis().setAxisMaximum(mttr.length+1);
                            mChart.getAxisLeft().setAxisMinimum(0);
                            mChart.getAxisLeft().setDrawLabels(true);
                            mChart.getAxisLeft().setDrawGridLines(true);
                            mChart.getAxisRight().setDrawGridLines(false);
                            mChart.getAxisLeft().setDrawAxisLine(true);
                            mChart.getAxisRight().setDrawAxisLine(false);
                            mChart.getXAxis().setLabelRotationAngle(90);
                            mChart.getLegend().setEnabled(true);
                            mChart.animateY(1000);
//                        mChart.getXAxis().setLabelCount(bpmt_c.size(), false);
                            mChart.notifyDataSetChanged();//Required if changes are made to pie value
                            mChart.invalidate();// for refreshing the chart
                            if (area.equals("")) {
                                plant_head.setText(plant_name);
                            } else {
                                plant_head.setText(plant_name + " - " + area);
                            }
                            heading.setText(getString(R.string.monthly_mttrfy, fy));
                            progressdialog.dismiss();
                            mChart.setData(data);
                        } catch (NumberFormatException nfe) {
                            Log.v("Sashi_int", "Could not parse " + nfe);
                        }
                    }

                }
            }
        }
           /* if (status != null && !status.equals("")) {
                if (!status.equals("success")) {
                    barChart.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    progressdialog.dismiss();
                } else {
                    if (iwerk.equals("") || bpmt.size() <= 0 || iwerk == null) {
                        mChart.setVisibility(View.GONE);
                        no_data.setVisibility(View.VISIBLE);
                        progressdialog.dismiss();
                    } else {
                        mChart.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                        try {
                            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                            ArrayList<Entry> line_c = new ArrayList<Entry>();
                            ArrayList<Entry> line_t = new ArrayList<Entry>();
                            String outputPattern = "MMM yyyy";
                            String inputPattern = "yyyyMM";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            SpmonS.clear();
                            bpmt_c.clear();

                            for (int l = 0; l < bpmt.size(); l++) {
                                if (bpmt.get(l).getIwerk().equals(iwerk)) {
                                    Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                                    bmpt_o.setName(bpmt.get(l).getName());
                                    bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                                    bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                                    bmpt_o.setCount(bpmt.get(l).getCount());
                                    bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                                    bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                                    bpmt_c.add(bmpt_o);
                                    Log.v("break_pie", "" + bpmt.get(l).getSpmon());
                                }
                            }

                            mttr = new float[bpmt_c.size()];
                            count_m = new float[bpmt_c.size()];
                            tot_m2 = new float[bpmt_c.size()];

                            SpmonSS = new String[bpmt_c.size()];
                            for (int j = 0; j < bpmt_c.size(); j++) {
                                try {
                                    if (bpmt_c.get(j).getCount() != null && !bpmt_c.get(j).getCount().equals("")) {
                                        count_m[j] = Float.parseFloat(bpmt_c.get(j).getCount());
                                    } else {
                                        count_m[j] = 0;
                                    }
                                    if (bpmt_c.get(j).getMttrHours() != null && !bpmt_c.get(j).getMttrHours().equals("")) {
                                        mttr[j] = Float.parseFloat(bpmt_c.get(j).getMttrHours());
                                    } else {
                                        mttr[j] = 0;
                                    }
                                    if (bpmt_c.get(j).getTotM2() != null && !bpmt_c.get(j).getTotM2().equals("")) {
                                        tot_m2[j] = Float.parseFloat(bpmt_c.get(j).getTotM2());
                                    } else {
                                        tot_m2[j] = 0;
                                    }
                                    if (bpmt_c.get(j).getSpmon() != null && !bpmt_c.get(j).getSpmon().equals("")) {
                                        try {
                                            date = inputFormat.parse(bpmt_c.get(j).getSpmon());
                                            String dateS = outputFormat.format(date);
                                            SpmonS.add(dateS);
                                            SpmonSS[j] = "" + dateS;
                                        } catch (ParseException e) {

                                        }
                                    }
                                } catch (NumberFormatException nfe) {
                                    Log.v("Sashi_int", "Could not parse " + nfe);
                                }
                                entries.add(new BarEntry(j, mttr[j]));
                                line_c.add(new Entry(j, count_m[j]));
                                line_t.add(new Entry(j, tot_m2[j]));
                            }
                            barDataSet = new BarDataSet(entries, "MTTR");
                            lineDataSet_c = new LineDataSet(line_c, "No# of BD");
                            lineDataSet_c.setColor(Color.rgb(201, 26, 41));
                            lineDataSet_c.setDrawCircles(false);
                            lineDataSet_c.setLineWidth(2);
                            lineDataSet_c.setDrawValues(false);
                            lineDataSet_t = new LineDataSet(line_t, "BD Hrs");
                            lineDataSet_t.setColor(Color.BLACK);
                            lineDataSet_t.setDrawCircles(false);
                            lineDataSet_t.setLineWidth(2);
                            lineDataSet_t.setDrawValues(false);

                            final ArrayList<Integer> colors = new ArrayList<Integer>();

                            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.COLORFUL_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.JOYFUL_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.LIBERTY_COLORS)
                                colors.add(c);

                            for (int c : ColorTemplate.PASTEL_COLORS)
                                colors.add(c);

                            barDataSet.setColors(Color.rgb(49, 84, 154));
                            barDataSet.setValueFormatter(new MyValueFormatter());

                            barData = new BarData(barDataSet);
                            barData.setBarWidth(0.5f);
                            barData.setValueTextSize(12);
                            lineData_c = new LineData();
                            lineData_c.addDataSet(lineDataSet_c);
                            lineData_c.addDataSet(lineDataSet_t);
                            lineDataSet_c.setDrawHighlightIndicators(true);
                            lineDataSet_c.setDrawHorizontalHighlightIndicator(true);

                            CombinedData data = new CombinedData();

                            data.setData(lineData_c);
                            data.setData(barData);

                            Legend l = mChart.getLegend();
                            l.setWordWrapEnabled(true);
                            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                            l.setDrawInside(true);
                            l.setTextSize(12);


                            XAxis xAxis = mChart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setDrawGridLines(false);
                            xAxis.setGranularity(1f); // only intervals of 1 day
                            xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
                            xAxis.setSpaceMin(barData.getBarWidth() / 2f);
                            xAxis.setSpaceMax(barData.getBarWidth() / 2f);
                            YAxis rightAxis = mChart.getAxisRight();
                            rightAxis.setDrawLabels(false);

                            mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR,
*//*CombinedChart.DrawOrder.LINE, *//*
CombinedChart.DrawOrder.LINE});
                    mChart.getDescription().setEnabled(false);
                    mChart.setExtraOffsets(2, 2, 2, 2);
                    mChart.getAxisLeft().setAxisMinimum(0);
                    mChart.getAxisLeft().setDrawLabels(true);
                    mChart.getAxisLeft().setDrawGridLines(true);
                    mChart.getAxisRight().setDrawGridLines(false);
                    mChart.getAxisLeft().setDrawAxisLine(true);
                    mChart.getAxisRight().setDrawAxisLine(false);
                    mChart.getXAxis().setLabelRotationAngle(90);
                    mChart.getLegend().setEnabled(true);
                    mChart.animateY(1000);
                    mChart.getXAxis().setLabelCount(bpmt_c.size(), false);
                    mChart.notifyDataSetChanged();//Required if changes are made to pie value
                    mChart.invalidate();// for refreshing the chart
                    plant_head.setText(plant_name);
                    heading.setText("MTTR Monthly Trend - FY" + fy);
                    progressdialog.dismiss();
                    mChart.setData(data);

                } catch (NumberFormatException nfe) {
                    Log.v("Sashi_int", "Could not parse " + nfe);
                }
            }
                    }
                }

            } else {
                barChart.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                progressdialog.dismiss();
            }*/
    }


/*
    private class Breakdown_Statistics extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(Mis_Break_Stats_Pie.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(
                    R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (iwerk.equals("") || bpmt.size() <= 0 || iwerk == null) {
                mChart.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                progressdialog.dismiss();
            } else {
                mChart.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);
                try {
                    ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
                    ArrayList<Entry> line_c = new ArrayList<Entry>();
                    ArrayList<Entry> line_t = new ArrayList<Entry>();
                    String outputPattern = "MMM yyyy";
                    String inputPattern = "yyyyMM";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                    SpmonS.clear();
                    bpmt_c.clear();

                    for (int l = 0; l < bpmt.size(); l++) {
                        if (bpmt.get(l).getIwerk().equals(iwerk)) {
                            Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                            bmpt_o.setName(bpmt.get(l).getName());
                            bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                            bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                            bmpt_o.setCount(bpmt.get(l).getCount());
                            bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                            bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                            bpmt_c.add(bmpt_o);
                            Log.v("break_pie", "" + bpmt.get(l).getSpmon());
                        }
                    }

                    mttr = new float[bpmt_c.size()];
                    count_m = new float[bpmt_c.size()];
                    tot_m2 = new float[bpmt_c.size()];

                    SpmonSS = new String[bpmt_c.size()];
                    for (int j = 0; j < bpmt_c.size(); j++) {
                        try {
                            if (bpmt_c.get(j).getCount() != null && !bpmt_c.get(j).getCount().equals("")) {
                                count_m[j] = Float.parseFloat(bpmt_c.get(j).getCount());
                            } else {
                                count_m[j] = 0;
                            }
                            if (bpmt_c.get(j).getMttrHours() != null && !bpmt_c.get(j).getMttrHours().equals("")) {
                                mttr[j] = Float.parseFloat(bpmt_c.get(j).getMttrHours());
                            } else {
                                mttr[j] = 0;
                            }
                            if (bpmt_c.get(j).getTotM2() != null && !bpmt_c.get(j).getTotM2().equals("")) {
                                tot_m2[j] = Float.parseFloat(bpmt_c.get(j).getTotM2());
                            } else {
                                tot_m2[j] = 0;
                            }
                            if (bpmt_c.get(j).getSpmon() != null && !bpmt_c.get(j).getSpmon().equals("")) {
                                try {
                                    date = inputFormat.parse(bpmt_c.get(j).getSpmon());
                                    String dateS = outputFormat.format(date);
                                    SpmonS.add(dateS);
                                    SpmonSS[j] = "" + dateS;
                                } catch (ParseException e) {

                                }
                            }
                        } catch (NumberFormatException nfe) {
                            Log.v("Sashi_int", "Could not parse " + nfe);
                        }
                        entries.add(new BarEntry(j, mttr[j]));
                        line_c.add(new Entry(j, count_m[j]));
                        line_t.add(new Entry(j, tot_m2[j]));
                    }
                    barDataSet = new BarDataSet(entries, "MTTR");
                    lineDataSet_c = new LineDataSet(line_c, "No# of BD");
                    lineDataSet_c.setColor(Color.rgb(201, 26, 41));
                    lineDataSet_c.setDrawCircles(false);
                    lineDataSet_c.setLineWidth(2);
                    lineDataSet_c.setDrawValues(false);
                    lineDataSet_t = new LineDataSet(line_t, "BD Hrs");
                    lineDataSet_t.setColor(Color.BLACK);
                    lineDataSet_t.setDrawCircles(false);
                    lineDataSet_t.setLineWidth(2);
                    lineDataSet_t.setDrawValues(false);

                    final ArrayList<Integer> colors = new ArrayList<Integer>();

                    for (int c : ColorTemplate.VORDIPLOM_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.COLORFUL_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.JOYFUL_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.LIBERTY_COLORS)
                        colors.add(c);

                    for (int c : ColorTemplate.PASTEL_COLORS)
                        colors.add(c);

                    barDataSet.setColors(Color.rgb(49, 84, 154));
                    barDataSet.setValueFormatter(new MyValueFormatter());

                    barData = new BarData(barDataSet);
                    barData.setBarWidth(0.5f);
                    barData.setValueTextSize(12);
                    lineData_c = new LineData();
                    lineData_c.addDataSet(lineDataSet_c);
                    lineData_c.addDataSet(lineDataSet_t);
                    lineDataSet_c.setDrawHighlightIndicators(true);
                    lineDataSet_c.setDrawHorizontalHighlightIndicator(true);

                    CombinedData data = new CombinedData();

                    data.setData(lineData_c);
                    data.setData(barData);

                    Legend l = mChart.getLegend();
                    l.setWordWrapEnabled(true);
                    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                    l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    l.setDrawInside(true);
                    l.setTextSize(12);


                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setGranularity(1f); // only intervals of 1 day
                    xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
                    xAxis.setSpaceMin(barData.getBarWidth() / 2f);
                    xAxis.setSpaceMax(barData.getBarWidth() / 2f);
                    YAxis rightAxis = mChart.getAxisRight();
                    rightAxis.setDrawLabels(false);

                    mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, */
    /*CombinedChart.DrawOrder.LINE, *//*
CombinedChart.DrawOrder.LINE});
                    mChart.getDescription().setEnabled(false);
                    mChart.setExtraOffsets(2, 2, 2, 2);
                    mChart.getAxisLeft().setAxisMinimum(0);
                    mChart.getAxisLeft().setDrawLabels(true);
                    mChart.getAxisLeft().setDrawGridLines(true);
                    mChart.getAxisRight().setDrawGridLines(false);
                    mChart.getAxisLeft().setDrawAxisLine(true);
                    mChart.getAxisRight().setDrawAxisLine(false);
                    mChart.getXAxis().setLabelRotationAngle(90);
                    mChart.getLegend().setEnabled(true);
                    mChart.animateY(1000);
                    mChart.getXAxis().setLabelCount(bpmt_c.size(), false);
                    mChart.notifyDataSetChanged();//Required if changes are made to pie value
                    mChart.invalidate();// for refreshing the chart
                    plant_head.setText(plant_name);
                    heading.setText("MTTR Monthly Trend - FY" + fy);
                    progressdialog.dismiss();
                    mChart.setData(data);

                } catch (NumberFormatException nfe) {
                    Log.v("Sashi_int", "Could not parse " + nfe);
                }
            }
        }*/

    public void wAreas(String iwerk, String warea) {
        try {
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            ArrayList<Entry> line_c = new ArrayList<Entry>();
            ArrayList<Entry> line_t = new ArrayList<Entry>();
            String outputPattern = "MMM yyyy";
            String inputPattern = "yyyyMM";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            SpmonS.clear();
            bpmt_c.clear();

            if (!warea.equals("")) {
                for (int l = 0; l < bpmt.size(); l++) {
                    if (bpmt.get(l).getIwerk().equals(iwerk) && bpmt.get(l).getWarea().equals(warea)) {
                        Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                        bmpt_o.setName(bpmt.get(l).getName());
                        bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                        //bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                        bmpt_o.setCount(bpmt.get(l).getCount());
                        bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                        bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                        bpmt_c.add(bmpt_o);
                    }
                }
            } else {
                for (int l = 0; l < bpmt.size(); l++) {
                    if (bpmt.get(l).getIwerk().equals(iwerk)) {
                        Mis_Break_Stat_Object bmpt_o = new Mis_Break_Stat_Object();
                        bmpt_o.setName(bpmt.get(l).getName());
                        bmpt_o.setMttrHours(bpmt.get(l).getMttrHours());
                        //bmpt_o.setTotM2(bpmt.get(l).getTotM2());
                        bmpt_o.setCount(bpmt.get(l).getCount());
                        bmpt_o.setIwerk(bpmt.get(l).getIwerk());
                        bmpt_o.setSpmon(bpmt.get(l).getSpmon());
                        bpmt_c.add(bmpt_o);
                    }
                }
            }

            if (bpmt_c.size() <= 0) {
                no_data.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.GONE);
                plant_head.setText("");
                plant_head.setVisibility(View.GONE);
                heading.setVisibility(View.GONE);
            } else {
                no_data.setVisibility(View.GONE);
                mChart.setVisibility(View.VISIBLE);
                plant_head.setVisibility(View.VISIBLE);
                heading.setVisibility(View.VISIBLE);

                mttr = new float[bpmt_c.size()];
                count_m = new float[bpmt_c.size()];
                //tot_m2 = new float[bpmt_c.size()];

                SpmonSS = new String[bpmt_c.size()];
                for (int j = 0; j < bpmt_c.size(); j++) {
                    try {
                        if (bpmt_c.get(j).getCount() != null && !bpmt_c.get(j).getCount().equals("")) {
                            count_m[j] = Float.parseFloat(bpmt_c.get(j).getCount());
                        } else {
                            count_m[j] = 0;
                        }
                        if (bpmt_c.get(j).getMttrHours() != null && !bpmt_c.get(j).getMttrHours().equals("")) {
                            mttr[j] = Float.parseFloat(bpmt_c.get(j).getMttrHours());
                        } else {
                            mttr[j] = 0;
                        }
                        /*if (bpmt_c.get(j).getTotM2() != null && !bpmt_c.get(j).getTotM2().equals("")) {
                            tot_m2[j] = Float.parseFloat(bpmt_c.get(j).getTotM2());
                        } else {
                            tot_m2[j] = 0;
                        }*/
                        if (bpmt_c.get(j).getSpmon() != null && !bpmt_c.get(j).getSpmon().equals("")) {
                            try {
                                date = inputFormat.parse(bpmt_c.get(j).getSpmon().substring(1));
                                String dateS = outputFormat.format(date);
                                SpmonS.add(dateS);
                                SpmonSS[j] = "" + dateS;
                            } catch (ParseException e) {

                            }
                        }
                    } catch (NumberFormatException nfe) {
                        Log.v("Sashi_int", "Could not parse " + nfe);
                    }
                    entries.add(new BarEntry(j, mttr[j]));
                    line_c.add(new Entry(j, count_m[j]));
                    //line_t.add(new Entry(j, tot_m2[j]));
                }
                barDataSet = new BarDataSet(entries, "MTTR");
                lineDataSet_c = new LineDataSet(line_c, "No# of BD");
                lineDataSet_c.setColor(Color.rgb(201, 26, 41));
                if (line_c.size() == 1) {
                    lineDataSet_c.setDrawCircles(true);
                    lineDataSet_c.setCircleColor(Color.WHITE);
                    lineDataSet_c.setCircleColorHole(Color.rgb(201, 26, 41));
                } else {
                    lineDataSet_c.setDrawCircles(false);
                }
                lineDataSet_c.setLineWidth(2);
                lineDataSet_c.setDrawValues(false);
                lineDataSet_c.setValueFormatter(new MyValueFormatter());
                /*lineDataSet_t = new LineDataSet(line_t, "BD Hrs");
                lineDataSet_t.setColor(Color.BLACK);
                if (line_t.size() == 1) {
                    lineDataSet_t.setDrawCircles(true);
                    lineDataSet_t.setCircleColor(Color.WHITE);
                    lineDataSet_t.setCircleColorHole(Color.BLACK);
                } else {
                    lineDataSet_t.setDrawCircles(false);
                }
                lineDataSet_t.setLineWidth(2);
                lineDataSet_t.setDrawValues(false);
                lineDataSet_t.setValueFormatter(new MyValueFormatter());*/

                final ArrayList<Integer> colors = new ArrayList<Integer>();

                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);

                barDataSet.setColors(Color.rgb(49, 84, 154));
                barDataSet.setValueFormatter(new MyValueFormatter());

                barData = new BarData(barDataSet);
                if (entries.size() == 1) {
                    barData.setBarWidth(0.1f);
                } else if (entries.size() == 2) {
                    barData.setBarWidth(0.2f);
                } else if (entries.size() == 3) {
                    barData.setBarWidth(0.3f);
                }
                ;
                barData.setValueTextSize(12);
                lineData_c = new LineData();
                lineData_c.addDataSet(lineDataSet_c);
                //lineData_c.addDataSet(lineDataSet_t);
//                                lineData_c.setHighlightEnabled(true);
//                                lineData_c.setDrawValues(false);
                lineDataSet_c.setDrawHighlightIndicators(false);
                lineDataSet_c.setDrawHorizontalHighlightIndicator(false);

                CombinedData data = new CombinedData();

                data.setData(lineData_c);
                data.setData(barData);

                MyMarkerView mv = new MyMarkerView(BreakStats_Pie.this, R.layout.markerview);
                mv.setChartView(mChart);
                mChart.setMarker(mv);

                Legend l = mChart.getLegend();
                l.setWordWrapEnabled(true);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(true);
                l.setTextSize(12);


                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1f); // only intervals of 1 day
                xAxis.setValueFormatter(new MyXAxisValueFormatter(SpmonSS));
                if (entries.size() == 1) {
                    xAxis.setAxisMaximum(data.getXMax() + 0.5f);
                    xAxis.setAxisMinimum(data.getXMin() - 0.5f);
                } else {
                    xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                    xAxis.setAxisMinimum(data.getXMin() - 0.25f);
                }
//                xAxis.setSpaceMin(barData.getBarWidth() / 2f);
//                xAxis.setSpaceMax(barData.getBarWidth() / 2f);
                YAxis rightAxis = mChart.getAxisRight();
                rightAxis.setDrawLabels(false);

                mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR,
/*CombinedChart.DrawOrder.LINE, */
                        CombinedChart.DrawOrder.LINE});
                mChart.getDescription().setEnabled(false);
                mChart.setExtraOffsets(2, 2, 2, 2);
//                    mChart.getXAxis().setAxisMinimum(0);
//                    mChart.getXAxis().setAxisMaximum(mttr.length+1);
                mChart.getAxisLeft().setAxisMinimum(0);
                mChart.getAxisLeft().setDrawLabels(true);
                mChart.getAxisLeft().setDrawGridLines(true);
                mChart.getAxisRight().setDrawGridLines(false);
                mChart.getAxisLeft().setDrawAxisLine(true);
                mChart.getAxisRight().setDrawAxisLine(false);
                mChart.getXAxis().setLabelRotationAngle(90);
                mChart.getLegend().setEnabled(true);
                mChart.animateY(1000);
                mChart.getXAxis().setLabelCount(bpmt_c.size(), false);
                mChart.notifyDataSetChanged();//Required if changes are made to pie value
                plant_head.setText(plant_name);
                heading.setText(getString(R.string.monthly_mttrfy, fy));
                progressdialog.dismiss();
                mChart.setData(data);
                mChart.invalidate();// for refreshing the chart

                if (warea.equals("")) {
                    plant_head.setText(plant_name);
                } else {
                    plant_head.setText(plant_name + " - " + warea);
                }
            }
        } catch (NumberFormatException nfe) {
            Log.v("Sashi_int", "Could not parse " + nfe);
        }
    }

    public String checkempty(String string) {
        if (string.equalsIgnoreCase("anyType{}")) {
            String ss = "";
            return ss;
        } else {
            return string;
        }
    }

    public void noInternet() {
        network_error_dialog = new Dialog(BreakStats_Pie.this);
        network_error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        network_error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        network_error_dialog.setCancelable(true);
        network_error_dialog.setCanceledOnTouchOutside(false);
        network_error_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) network_error_dialog.findViewById(R.id.description_textview);
        final TextView description_textview1 = (TextView) network_error_dialog.findViewById(R.id.description_textview1);
        Button ok_button = (Button) network_error_dialog.findViewById(R.id.ok_button);
        Button cancel_button = (Button) network_error_dialog.findViewById(R.id.cancel_button);
        description_textview.setText(getResources()
                .getString(R.string.no_network));
        description_textview1.setText(getResources()
                .getString(R.string.connect_internet));
        ok_button.setText(getResources().getString(
                R.string.connect));
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


    protected void show_error_dialog(String string) {
        errordialog = new Dialog(BreakStats_Pie.this);
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

    public class AREA_ADAPTER extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_Break_Stat_Object> mainDataList = null;
        private ArrayList<Mis_Break_Stat_Object> arraylist;

        public AREA_ADAPTER(Context context, List<Mis_Break_Stat_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_Break_Stat_Object>();
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
        public Mis_Break_Stat_Object getItem(int position) {
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

                holder.Name.setVisibility(View.GONE);


                if (!area_w.equals("")) {
                    for (int i = 0; i < bwt_f.size(); i++) {
                        if (bwt_f.get(i).getWarea().equals(area_w)) {
                            holder.radio_status.setChecked(true);
                            bwt_f.get(i).setmChecked("y");
                        } else {
                            bwt_f.get(i).setmChecked("n");
                        }
                    }
                } else if (!area_t.equals("")) {
                    for (int j = 0; j < bwt_f.size(); j++) {
                        if (bwt_f.get(j).getWarea().equals(area_t)) {
                            holder.radio_status.setChecked(true);
                            bwt_f.get(j).setmChecked("y");
                        } else {
                            bwt_f.get(j).setmChecked("n");
                        }
                    }
                } else if (!area.equals("")) {
                    for (int j = 0; j < bwt_f.size(); j++) {
                        if (bwt_f.get(j).getWarea().equals(area)) {
                            holder.radio_status.setChecked(true);
                            bwt_f.get(j).setmChecked("y");
                        } else {
                            bwt_f.get(j).setmChecked("n");
                        }
                    }
                }

                holder.radio_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String wkcenter = holder.radio_status.getText().toString();
                        if (holder.radio_status.isChecked()) {
                            for (int i = 0; i < bwt_f.size(); i++) {
                                String new_area = bwt_f.get(i).getWarea();
//                                String new_wrkname = bwt.get(i).getmName_art();
                                if (wkcenter.equalsIgnoreCase(new_area)) {
                                    bwt_f.get(i).setmChecked("y");
                                    mainDataList.get(i).setmChecked("y");
                                    area_w = new_area;
                                    changed_w = true;
                                } else {
                                    String status = bwt_f.get(i).getmChecked();
                                    if (status.equals("y")) {
                                        bwt_f.get(i).setmChecked("n");
                                        mainDataList.get(i).setmChecked("n");
                                    }
                                }
                            }
                            area_adapter = new AREA_ADAPTER(BreakStats_Pie.this, bwt_f);
                            listview.setAdapter(area_adapter);
                        } else {

                        }
                    }
                });
                holder.radio_status.setText(mainDataList.get(position).getWarea());
                holder.radio_status.setTag(position);
                if (mainDataList.get(position).getmChecked().equals("y")) {
                    holder.radio_status.setChecked(true);
                } else {
                    holder.radio_status.setChecked(false);
                }
//                holder.Name.setText(mainDataList.get(position).getmName_art());
            } else {
                holder = (ViewHolder) view.getTag();
            }
            return view;
        }
    }

    public class FILTER_PLANT_TYPE_Adapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        RadioButton mCurrentlyCheckedRB = null;
        int size, pos;
        private List<Mis_Break_Stat_Object> mainDataList = null;
        private ArrayList<Mis_Break_Stat_Object> arraylist;

        public FILTER_PLANT_TYPE_Adapter(Context context, List<Mis_Break_Stat_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_Break_Stat_Object>();
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
        public Mis_Break_Stat_Object getItem(int position) {
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

                if (!iwerk_tp.equals("")) {
                    for (int i = 0; i < bpt.size(); i++) {
                        if (bpt.get(i).getIwerk().equals(iwerk_tp)) {
                            holder.radio_status.setChecked(true);
                            bpt.get(i).setmChecked("y");
                        } else {
                            bpt.get(i).setmChecked("n");
                        }
                    }
                } else if (!iwerk_t.equals("")) {
                    for (int j = 0; j < bpt.size(); j++) {
                        if (bpt.get(j).getIwerk().equals(iwerk_t)) {
                            holder.radio_status.setChecked(true);
                            bpt.get(j).setmChecked("y");
                        } else {
                            bpt.get(j).setmChecked("n");
                        }
                    }
                } else if (iwerk_t.equals("")) {
                    for (int j = 0; j < bpt.size(); j++) {
                        if (bpt.get(j).getIwerk().equals(iwerk)) {
                            holder.radio_status.setChecked(true);
                            bpt.get(j).setmChecked("y");
                        } else {
                            bpt.get(j).setmChecked("n");
                        }
                    }
                }

                holder.radio_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String iwerk1 = holder.radio_status.getText().toString();
                        if (holder.radio_status.isChecked()) {
                            for (int i = 0; i < bpt.size(); i++) {
                                String new_iwerk = bpt.get(i).getIwerk();
                                String new_plant = bpt.get(i).getName();
                                if (iwerk1.equalsIgnoreCase(new_iwerk)) {
                                    bpt.get(i).setmChecked("y");
                                    mainDataList.get(i).setmChecked("y");
                                    plant_name_tp = new_plant;
                                    iwerk_tp = new_iwerk;
//                                    plant_all = false;
                                    changed = true;
                                } else {
                                    String status = bpt.get(i).getmChecked();
                                    if (status.equals("y")) {
                                        bpt.get(i).setmChecked("n");
                                        mainDataList.get(i).setmChecked("n");
                                    }
                                }
                            }
                            filter_plant_type_adapter = new FILTER_PLANT_TYPE_Adapter(BreakStats_Pie.this, bpt);
                            listview.setAdapter(filter_plant_type_adapter);
                        } else {

                        }
                    }
                });
                holder.radio_status.setText(mainDataList.get(position).getIwerk());
                holder.radio_status.setTag(position);
                if (mainDataList.get(position).getmChecked().equals("y")) {
                    holder.radio_status.setChecked(true);
                } else {
                    holder.radio_status.setChecked(false);
                }
                holder.Name.setText(mainDataList.get(position).getName());
            } else {
                holder = (ViewHolder) view.getTag();
            }
            return view;
        }
    }

    protected void show_confirmation_dialg(String string) {
        final Dialog aa = new Dialog(BreakStats_Pie.this);
        aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
        aa.setCancelable(false);
        aa.setCanceledOnTouchOutside(false);
        aa.setContentView(R.layout.network_error_dialog);
        aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
        final TextView favoritetextview = (TextView) aa.findViewById(R.id.description_textview);
        Button confirm = (Button) aa.findViewById(R.id.ok_button);
        Button cancel = (Button) aa.findViewById(R.id.cancel_button);
        favoritetextview.setText(string);
        confirm.setText(getString(R.string.yes));
        cancel.setText(getString(R.string.no));
        aa.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                py = "X";
                pfy = "X";
                sign = "";
                bt = "";
                isDevice_HighMonth = "";
                isDevice_LowMonth = "";
                breakdown_statistics = new Breakdown_Statistics();
                breakdown_statistics.execute();
                pfy_imageview.setVisibility(View.GONE);
                aa.dismiss();
            }
        });
    }


}
