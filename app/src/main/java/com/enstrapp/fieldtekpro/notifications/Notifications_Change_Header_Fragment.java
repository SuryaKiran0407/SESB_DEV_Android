package com.enstrapp.fieldtekpro.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;
import com.enstrapp.fieldtekpro.orders.Orders_Change_Activity;
import com.enstrapp.fieldtekpro.orders.OrdrHeaderPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrMatrlPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrObjectPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrOprtnPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrPermitPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrTagUnTagTextPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWDItemPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWaChkReqPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrWcagnsPrcbl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Notifications_Change_Header_Fragment extends Fragment implements View.OnClickListener {

    TextInputLayout status_til;
    LinearLayout notif_Date_layout, ord_num_layout, effect_layout, malFunctnEnDt_layout,
            malFunctnStDt_layout, reqEnDt_layout, reqStDt_layout, personResp_layout,
            plannerGroup_layout, notiftype_layout, floc_layout, workCenter_layout, priority_layout;
    EditText status_tiet, notifshtTxt_edittext, effect_edittext, malFunctnEnDt_edittext,
            malFunctnStDt_edittext, reqEnDt_edittext, reqStDt_edittext, personResp_edittext,
            primUsrResp_edittext, reportedby_edittext, plannerGroup_edittext, priority_edittext,
            workCenter_edittext, notiftyp_edittext, floc_edittext, flocname_edittext,
            equipid_edittext, equiptext_edittext;
    int custom_info = 14, longtext = 13, effect = 12, malf_end_date = 11, malf_st_date = 10,
            req_enddate = 9, req_stdate = 8, personResp = 7, planner_group = 6,
            functionlocation_type = 1, equipment_type = 2, barcode_scan = 3, workcenter_type = 4,
            notification_priority = 5;
    String selected_orderstatus = "", selected_Iwerk = "", selected_orderID = "",
            selected_orderUUID = "", longtext_text = "", effect_id = "", effect_text = "",
            mal_end_date = "", mal_end_time = "", mal_end_date_formatted = "",
            mal_end_time_formatted = "", mal_st_date = "", mal_st_time = "",
            mal_st_date_formatted = "", mal_st_time_formatted = "", req_end_date = "",
            req_end_date_formatted = "", req_end_time = "", req_end_time_formatted = "",
            req_stdate_date = "", req_stdate_date_formated = "", req_stdate_time_formatted = "",
            req_stdate_time = "", personresponsible_id = "", personresponsible_text = "",
            priority_type_id = "", priority_type_text = "", workcenter_id = "",
            workcenter_text = "", plannergroup_text = "", plannergroup_id = "", plant_id = "",
            notification_type_id = "", notification_type_text = "", functionlocation_id = "",
            functionlocation_text = "", equipment_id = "", equipment_text = "";
    ImageView notiftype_dropdown_iv, equipmentsearch_imageview, equipmentscan_imageview,
            longtext_imageview;
    private static final int ZXING_CAMERA_PERMISSION = 3;
    Error_Dialog error_dialog = new Error_Dialog();
    NotifHeaderPrcbl nhp = new NotifHeaderPrcbl();
    TextView ord_num_edittext, notif_Date_edittext;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    String created_aufnr = "";
    Button header_custominfo_button;
    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<>();

    public Notifications_Change_Header_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_header_fragment, container,
                false);

        notiftype_layout = (LinearLayout) rootView.findViewById(R.id.notiftype_layout);
        notiftyp_edittext = (EditText) rootView.findViewById(R.id.notiftyp_edittext);
        floc_layout = (LinearLayout) rootView.findViewById(R.id.floc_layout);
        floc_edittext = (EditText) rootView.findViewById(R.id.floc_edittext);
        flocname_edittext = (EditText) rootView.findViewById(R.id.flocname_edittext);
        equipmentsearch_imageview = (ImageView) rootView.findViewById(R.id.equipmentsearch_imageview);
        equipmentscan_imageview = (ImageView) rootView.findViewById(R.id.equipmentscan_imageview);
        equipid_edittext = (EditText) rootView.findViewById(R.id.equipid_edittext);
        equiptext_edittext = (EditText) rootView.findViewById(R.id.equiptext_edittext);
        workCenter_edittext = (EditText) rootView.findViewById(R.id.workCenter_edittext);
        workCenter_layout = (LinearLayout) rootView.findViewById(R.id.workCenter_layout);
        priority_layout = (LinearLayout) rootView.findViewById(R.id.priority_layout);
        priority_edittext = (EditText) rootView.findViewById(R.id.priority_edittext);
        plannerGroup_layout = (LinearLayout) rootView.findViewById(R.id.plannerGroup_layout);
        plannerGroup_edittext = (EditText) rootView.findViewById(R.id.plannerGroup_edittext);
        reportedby_edittext = (EditText) rootView.findViewById(R.id.reportedby_edittext);
        primUsrResp_edittext = (EditText) rootView.findViewById(R.id.primUsrResp_edittext);
        personResp_layout = (LinearLayout) rootView.findViewById(R.id.personResp_layout);
        personResp_edittext = (EditText) rootView.findViewById(R.id.personResp_edittext);
        reqStDt_layout = (LinearLayout) rootView.findViewById(R.id.reqStDt_layout);
        reqStDt_edittext = (EditText) rootView.findViewById(R.id.reqStDt_edittext);
        reqEnDt_layout = (LinearLayout) rootView.findViewById(R.id.reqEnDt_layout);
        reqEnDt_edittext = (EditText) rootView.findViewById(R.id.reqEnDt_edittext);
        malFunctnStDt_layout = (LinearLayout) rootView.findViewById(R.id.malFunctnStDt_layout);
        malFunctnStDt_edittext = (EditText) rootView.findViewById(R.id.malFunctnStDt_edittext);
        malFunctnEnDt_layout = (LinearLayout) rootView.findViewById(R.id.malFunctnEnDt_layout);
        malFunctnEnDt_edittext = (EditText) rootView.findViewById(R.id.malFunctnEnDt_edittext);
        effect_layout = (LinearLayout) rootView.findViewById(R.id.effect_layout);
        effect_edittext = (EditText) rootView.findViewById(R.id.effect_edittext);
        notifshtTxt_edittext = (EditText) rootView.findViewById(R.id.notifshtTxt_edittext);
        status_til = (TextInputLayout) rootView.findViewById(R.id.status_til);
        status_tiet = (EditText) rootView.findViewById(R.id.status_tiet);
        notiftype_dropdown_iv = (ImageView) rootView.findViewById(R.id.notiftype_dropdown_iv);
        ord_num_layout = (LinearLayout) rootView.findViewById(R.id.ord_num_layout);
        ord_num_edittext = (TextView) rootView.findViewById(R.id.ord_num_edittext);
        notif_Date_layout = (LinearLayout) rootView.findViewById(R.id.notif_Date_layout);
        notif_Date_edittext = (TextView) rootView.findViewById(R.id.notif_Date_edittext);
        longtext_imageview = (ImageView) rootView.findViewById(R.id.longtext_imageview);
        header_custominfo_button = (Button) rootView.findViewById(R.id.header_custominfo_button);

        status_til.setVisibility(View.GONE);
        notiftype_dropdown_iv.setVisibility(View.GONE);
        notiftype_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME,
                getActivity().MODE_PRIVATE, null);

        selected_custom_info_arraylist.clear();

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            nhp = bundle.getParcelable("notif_parcel");

            status_tiet.setText(nhp.getNotf_Status());
            notification_type_id = nhp.getNotifType();
            notification_type_text = nhp.getQmartx();
            notiftyp_edittext.setText(getString(R.string.hypen_text,
                    nhp.getNotifType(), nhp.getQmartx()));
            notiftyp_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));
            notifshtTxt_edittext.setText(nhp.getNotifShrtTxt());
            functionlocation_id = nhp.getFuncLoc();
            functionlocation_text = nhp.getPltxt();
            floc_edittext.setText(nhp.getFuncLoc());
            flocname_edittext.setText(functionlocation_text);
            equipment_id = nhp.getEquip();
            equipment_text = nhp.getEquipTxt();
            equipid_edittext.setText(equipment_id);
            equiptext_edittext.setText(equipment_text);
            plant_id = nhp.getWerks();
            plannergroup_id = nhp.getIngrp();
            plannergroup_text = nhp.getIngrpName();
            plannerGroup_edittext.setText(plannergroup_id + "-" + plannergroup_text);
            workcenter_id = nhp.getArbpl();
            workcenter_text = nhp.getWrkCntrTxt();
            workCenter_edittext.setText(workcenter_id + "-" +workcenter_text);
            priority_type_id = nhp.getPriority();
            priority_type_text = nhp.getPriorityTxt();
            priority_edittext.setText(getString(R.string.hypen_text, priority_type_id,
                    priority_type_text));
            reportedby_edittext.setText(nhp.getReportedBy());
            primUsrResp_edittext.setText(nhp.getUsr01());
            personresponsible_id = nhp.getParnrVw();
            personresponsible_text = nhp.getNameVw();
            personResp_edittext.setText(getString(R.string.hypen_text, personresponsible_id,
                    personresponsible_text));

            String req_stdate = nhp.getStrmn();
            if (req_stdate != null && !req_stdate.equals("")) {
                if (req_stdate.equalsIgnoreCase("00000000")) {
                    req_stdate_date = "";
                    req_stdate_date_formated = "";
                    req_stdate_time = "";
                    req_stdate_time_formatted = "";
                } else {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(req_stdate);
                        String selected_date = outputFormat.format(date);
                        String req_sttime = nhp.getStrur();
                        String req_starttime = "";
                        if (req_sttime != null && !req_sttime.equals("")) {
                            String inputPattern1 = "HHmmss";
                            String outputPattern1 = "HH:mm:ss";
                            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                            Date date1 = inputFormat1.parse(req_sttime);
                            req_starttime = outputFormat1.format(date1);
                        }
                        req_stdate_date = selected_date;
                        req_stdate_date_formated = req_stdate;
                        req_stdate_time = req_starttime;
                        req_stdate_time_formatted = req_sttime;
                        reqStDt_edittext.setText(getString(R.string.hypen_text, req_stdate_date,
                                req_stdate_time));
                    } catch (ParseException e) {
                    }
                }
            }

            String req_eddate = nhp.getLtrmn();
            if (req_eddate != null && !req_eddate.equals("")) {
                if (req_eddate.equalsIgnoreCase("00000000")) {
                    req_end_date = "";
                    req_end_date_formatted = "";
                    req_end_time = "";
                    req_end_time_formatted = "";
                } else {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(req_eddate);
                        String selected_date = outputFormat.format(date);
                        String req_edtime = nhp.getLtrur();
                        String req_endtime = "";
                        if (req_edtime != null && !req_edtime.equals("")) {
                            String inputPattern1 = "HHmmss";
                            String outputPattern1 = "HH:mm:ss";
                            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                            Date date1 = inputFormat1.parse(req_edtime);
                            req_endtime = outputFormat1.format(date1);
                        }
                        req_end_date = selected_date;
                        req_end_date_formatted = req_eddate;
                        req_end_time = req_endtime;
                        req_end_time_formatted = req_edtime;
                        reqEnDt_edittext.setText(getString(R.string.hypen_text, req_end_date,
                                req_end_time));
                    } catch (ParseException e) {
                    }
                }
            }

            String mal_stdate = nhp.getMalfuncStDt();
            if (mal_stdate != null && !mal_stdate.equals("")) {
                if (mal_stdate.equalsIgnoreCase("00000000")) {
                    mal_st_date = "";
                    mal_st_date_formatted = "";
                    mal_st_time = "";
                    mal_st_time_formatted = "";
                } else {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(mal_stdate);
                        String selected_date = outputFormat.format(date);
                        String mal_sttime = nhp.getMalfuncSttm();
                        String mal_start_time = "";
                        if (mal_sttime != null && !mal_sttime.equals("")) {
                            String inputPattern1 = "HHmmss";
                            String outputPattern1 = "HH:mm:ss";
                            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                            Date date1 = inputFormat1.parse(mal_sttime);
                            mal_start_time = outputFormat1.format(date1);
                        }
                        mal_st_date = selected_date;
                        mal_st_date_formatted = mal_stdate;
                        mal_st_time = mal_start_time;
                        mal_st_time_formatted = mal_sttime;
                        malFunctnStDt_edittext.setText(getString(R.string.hypen_text, mal_st_date,
                                mal_st_time));
                    } catch (ParseException e) {
                    }
                }
            }

            String mal_enddate = nhp.getMalfuncEdDt();
            if (mal_enddate != null && !mal_enddate.equals("")) {
                if (mal_enddate.equalsIgnoreCase("00000000")) {
                    mal_end_date = "";
                    mal_end_date_formatted = "";
                    mal_end_time = "";
                    mal_end_time_formatted = "";
                } else {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(mal_enddate);
                        String selected_date = outputFormat.format(date);
                        String mal_ed_time = nhp.getMalfuncEdtm();
                        String mal_endtime = "";
                        if (mal_ed_time != null && !mal_ed_time.equals("")) {
                            String inputPattern1 = "HHmmss";
                            String outputPattern1 = "HH:mm:ss";
                            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                            Date date1 = inputFormat1.parse(mal_ed_time);
                            mal_endtime = outputFormat1.format(date1);
                        }
                        mal_end_date = selected_date;
                        mal_end_date_formatted = mal_enddate;
                        mal_end_time = mal_endtime;
                        mal_end_time_formatted = mal_ed_time;
                        malFunctnEnDt_edittext.setText(getString(R.string.hypen_text, mal_end_date,
                                mal_end_time));
                    } catch (ParseException e) {
                    }
                }
            }

            effect_id = nhp.getAuswk();
            effect_text = nhp.getAuswkt();
            effect_edittext.setText(getString(R.string.hypen_text, effect_id, effect_text));

            final String aufnr = nhp.getAufnr();
            selected_orderID = aufnr;
            if (aufnr != null && !aufnr.equals("")) {
                ord_num_layout.setVisibility(View.VISIBLE);
                SpannableString content = new SpannableString(aufnr);
                content.setSpan(new UnderlineSpan(), 0, aufnr.length(), 0);
                ord_num_edittext.setText(content);
                ord_num_edittext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Cursor cursor = App_db.rawQuery("select * from" +
                                            " DUE_ORDERS_EtOrderHeader where Aufnr = ?",
                                    new String[]{aufnr});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        selected_orderUUID = cursor.getString(1);
                                        String equnr = cursor.getString(9);
                                        if (equnr != null && !equnr.equals("")) {
                                            selected_Iwerk =
                                                    getIwerk(cursor.getString(9));
                                        } else {
                                            selected_Iwerk =
                                                    getfuncIwerk(cursor.getString(10));
                                        }
                                        selected_orderstatus = cursor.getString(39);
                                    }
                                    while (cursor.moveToNext());
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (selected_orderUUID != null && !selected_orderUUID.equals("")) {
                            new Get_Order_Data().execute();
                        } else {
                            error_dialog.show_error_dialog(getActivity(),
                                    getString(R.string.notification_noorderfound, aufnr));
                        }
                    }
                });
            } else {
                ord_num_layout.setVisibility(View.GONE);
            }

            String Qmdat = nhp.getQmdat();
            if (Qmdat != null && !Qmdat.equals("")) {
                try {
                    String inputPattern = "yyyyMMdd";
                    String outputPattern = "MMM dd, yyyy";
                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                    Date date = inputFormat.parse(Qmdat);
                    String selected_date = outputFormat.format(date);
                    notif_Date_layout.setVisibility(View.VISIBLE);
                    notif_Date_edittext.setText(selected_date);
                } catch (ParseException e) {
                }
            } else {
                notif_Date_layout.setVisibility(View.GONE);
            }

            try {
                Cursor cursor = App_db.rawQuery("select * from EtNotifHeader_CustomInfo where" +
                                " Zdoctype = ? and ZdoctypeItem = ? and Qmnum = ? order by Sequence",
                        new String[]{"Q", "QH", nhp.getQmnum()});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Fieldname", cursor.getString(6));
                            custom_info_hashMap.put("ZdoctypeItem", cursor.getString(4));
                            custom_info_hashMap.put("Datatype", cursor.getString(11));
                            custom_info_hashMap.put("Tabname", cursor.getString(5));
                            custom_info_hashMap.put("Zdoctype", cursor.getString(3));
                            custom_info_hashMap.put("Sequence", cursor.getString(9));
                            custom_info_hashMap.put("Flabel", cursor.getString(8));
                            custom_info_hashMap.put("Length", cursor.getString(10));

                            String datatype = cursor.getString(11);
                            String value = cursor.getString(7);
                            if (datatype.equalsIgnoreCase("DATS")) {
                                if (value.equalsIgnoreCase("00000000")) {
                                    custom_info_hashMap.put("Value", "");
                                } else {
                                    String inputPattern = "yyyyMMdd";
                                    String outputPattern = "MMM dd, yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                    try {
                                        Date date = inputFormat.parse(value);
                                        String formatted_date = outputFormat.format(date);
                                        custom_info_hashMap.put("Value", formatted_date);
                                    } catch (Exception e) {
                                        custom_info_hashMap.put("Value", "");
                                    }
                                }

                            } else if (datatype.equalsIgnoreCase("TIMS")) {
                                if (value.equalsIgnoreCase("000000")) {
                                    custom_info_hashMap.put("Value", "");
                                } else {
                                    String inputPattern = "HHmmss";
                                    String outputPattern = "HH:mm:ss";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                    try {
                                        Date date = inputFormat.parse(value);
                                        String formatted_time = outputFormat.format(date);
                                        custom_info_hashMap.put("Value", formatted_time);
                                    } catch (Exception e) {
                                        custom_info_hashMap.put("Value", "");
                                    }
                                }
                            } else {
                                custom_info_hashMap.put("Value", cursor.getString(7));
                            }
                            custom_info_hashMap.put("Spras", "");
                            selected_custom_info_arraylist.add(custom_info_hashMap);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();

                    Cursor cursor1 = App_db.rawQuery("select * from GET_CUSTOM_FIELDS where" +
                                    " Zdoctype = ? and ZdoctypeItem = ? order by Sequence",
                            new String[]{"Q", "QH"});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                                custom_info_hashMap.put("Fieldname", cursor1.getString(1));
                                custom_info_hashMap.put("ZdoctypeItem", cursor1.getString(2));
                                custom_info_hashMap.put("Datatype", cursor1.getString(3));
                                custom_info_hashMap.put("Tabname", cursor1.getString(4));
                                custom_info_hashMap.put("Zdoctype", cursor1.getString(5));
                                custom_info_hashMap.put("Sequence", cursor1.getString(6));
                                custom_info_hashMap.put("Flabel", cursor1.getString(7));
                                custom_info_hashMap.put("Spras", cursor1.getString(8));
                                custom_info_hashMap.put("Length", cursor1.getString(9));
                                custom_info_hashMap.put("Value", "");
                                selected_custom_info_arraylist.add(custom_info_hashMap);
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                }
            } catch (Exception e) {
            }
        }
        floc_layout.setOnClickListener(this);
        equipmentsearch_imageview.setOnClickListener(this);
        equipmentscan_imageview.setOnClickListener(this);
        workCenter_layout.setOnClickListener(this);
        priority_layout.setOnClickListener(this);
        plannerGroup_layout.setOnClickListener(this);
        personResp_layout.setOnClickListener(this);
        reqStDt_layout.setOnClickListener(this);
        reqEnDt_layout.setOnClickListener(this);
        malFunctnStDt_layout.setOnClickListener(this);
        malFunctnEnDt_layout.setOnClickListener(this);
        effect_layout.setOnClickListener(this);
        longtext_imageview.setOnClickListener(this);
        header_custominfo_button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            String qmnum = nhp.getQmnum();
            Cursor cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader" +
                    " where Qmnum = ?", new String[]{qmnum});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        created_aufnr = cursor.getString(20);
                        nhp.setAufnr(created_aufnr);
                        ord_num_layout.setVisibility(View.VISIBLE);
                        SpannableString content = new SpannableString(created_aufnr);
                        content.setSpan(new UnderlineSpan(), 0, created_aufnr.length(), 0);
                        ord_num_edittext.setText(content);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
            ord_num_edittext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Cursor cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader" +
                                " where Aufnr = ?", new String[]{created_aufnr});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals("")) {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    } else {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (selected_orderUUID != null && !selected_orderUUID.equals("")) {
                        new Get_Order_Data().execute();
                    } else {
                        error_dialog.show_error_dialog(getActivity(),
                                getString(R.string.notification_noorderfound, created_aufnr));
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v == header_custominfo_button) {
            Intent custominfo_intent = new Intent(getActivity(), CustomInfo_Activity.class);
            custominfo_intent.putExtra("Zdoctype", "Q");
            custominfo_intent.putExtra("ZdoctypeItem", "QH");
            custominfo_intent.putExtra("custom_info_arraylist",
                    selected_custom_info_arraylist);
            custominfo_intent.putExtra("request_id", Integer.toString(custom_info));
            startActivityForResult(custominfo_intent, custom_info);
        } else if (v == longtext_imageview) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_LongText_Activity.class);
            notification_type_intent.putExtra("request_id", Integer.toString(longtext));
            notification_type_intent.putExtra("qmnum", nhp.getQmnum());
            notification_type_intent.putExtra("longtext_new", longtext_text);
            startActivityForResult(notification_type_intent, longtext);
        } else if (v == floc_layout) {
            Intent notification_type_intent = new Intent(getActivity(),
                    FunctionLocation_Activity.class);
            notification_type_intent.putExtra("request_id",
                    Integer.toString(functionlocation_type));
            startActivityForResult(notification_type_intent, functionlocation_type);
        } else if (v == equipmentsearch_imageview) {
            Intent equipment_intent = new Intent(getActivity(), Equipment_Activity.class);
            equipment_intent.putExtra("request_id", Integer.toString(equipment_type));
            equipment_intent.putExtra("functionlocation_id",
                    floc_edittext.getText().toString());
            startActivityForResult(equipment_intent, equipment_type);
        } else if (v == equipmentscan_imageview) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
            } else {
                Intent intent = new Intent(getActivity(), Barcode_Scanner_Activity.class);
                startActivityForResult(intent, barcode_scan);
            }
        } else if (v == workCenter_layout) {
            if ((equipment_id != null && !equipment_id.equals("")) ||
                    (functionlocation_id != null && !functionlocation_id.equals(""))) {
                Intent notification_type_intent = new Intent(getActivity(),
                        WorkCenter_Type_Activity.class);
                notification_type_intent.putExtra("request_id",
                        Integer.toString(workcenter_type));
                notification_type_intent.putExtra("plant_id", plant_id);
                startActivityForResult(notification_type_intent, workcenter_type);
            } else {
                error_dialog.show_error_dialog(getActivity(),
                        getString(R.string.equipFunc_mandate));
            }
        } else if (v == priority_layout) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_Priority_Activity.class);
            notification_type_intent.putExtra("request_id",
                    Integer.toString(notification_priority));
            startActivityForResult(notification_type_intent, notification_priority);
        } else if (v == plannerGroup_layout) {
            if (equiptext_edittext.getText().toString() != null &&
                    !equiptext_edittext.getText().toString().equals("")) {
                Intent planner_group_intent = new Intent(getActivity(),
                        Notifications_PlannerGroup_Activity.class);
                planner_group_intent.putExtra("equip_id",
                        equipid_edittext.getText().toString());
                planner_group_intent.putExtra("plant_id", nhp.getIwerk());
                planner_group_intent.putExtra("request_id",
                        Integer.toString(planner_group));
                startActivityForResult(planner_group_intent, planner_group);
            } else {
                error_dialog.show_error_dialog(getActivity(),
                        getString(R.string.equipFunc_mandate));
            }
        } else if (v == personResp_layout) {
            if (plant_id != null && !plant_id.equals("")) {
                if (workcenter_id != null && !workcenter_id.equals("")) {
                    Intent planner_group_intent = new Intent(getActivity(),
                            Notifications_Personresponsible_Activity.class);
                    planner_group_intent.putExtra("workcenter_id", workcenter_id);
                    planner_group_intent.putExtra("plant_id", plant_id);
                    planner_group_intent.putExtra("equip_id",
                            equipid_edittext.getText().toString());
                    planner_group_intent.putExtra("request_id", Integer.toString(personResp));
                    startActivityForResult(planner_group_intent, personResp);
                } else {
                    error_dialog.show_error_dialog(getActivity(),
                            getString(R.string.wrkCntr_mandate));
                }
            } else {
                error_dialog.show_error_dialog(getActivity(),
                        getString(R.string.equipFunc_mandate));
            }
        } else if (v == reqStDt_layout) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(req_stdate));
            startActivityForResult(intent, req_stdate);
        } else if (v == reqEnDt_layout) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(req_enddate));
            startActivityForResult(intent, req_enddate);
        } else if (v == malFunctnStDt_layout) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(malf_st_date));
            startActivityForResult(intent, malf_st_date);
        } else if (v == malFunctnEnDt_layout) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(malf_end_date));
            startActivityForResult(intent, malf_end_date);
        } else if (v == effect_layout) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_Effect_Activity.class);
            notification_type_intent.putExtra("request_id", Integer.toString(effect));
            startActivityForResult(notification_type_intent, effect);
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == longtext) {
                longtext_text = data.getStringExtra("longtext_new");
            } else if (requestCode == functionlocation_type) {
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                plant_id = data.getStringExtra("plant_id");
                equipment_id = "";
                equipment_text = "";
                equipid_edittext.setText("");
                equiptext_edittext.setText("");
                floc_edittext.setText(functionlocation_id);
                flocname_edittext.setText(functionlocation_text);
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = plnrGrpName(plannergroup_id, plant_id);
                if (plannergroup_id != null && !plannergroup_id.equals(""))
                    if (plannergroup_text != null && !plannergroup_text.equals(""))
                        plannerGroup_edittext.setText(getString(R.string.hypen_text, plannergroup_id,
                                plannergroup_text));
                    else
                        plannerGroup_edittext.setText(plannergroup_id);
                else
                    plannerGroup_edittext.setText("");
                workcenter_id = data.getStringExtra("work_center");
                workCenter_edittext.setText(workcenter_id);
            } else if (requestCode == equipment_type) {
                equipment_id = data.getStringExtra("equipment_id");
                equipment_text = data.getStringExtra("equipment_text");
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = funcLocName(data.getStringExtra("functionlocation_id"));
                equipid_edittext.setText(equipment_id);
                equiptext_edittext.setText(equipment_text);
                floc_edittext.setText(functionlocation_id);
                flocname_edittext.setText(functionlocation_text);
                plant_id = data.getStringExtra("plant_id");
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                workcenter_id = data.getStringExtra("work_center");
                workcenter_text = wrkCntrName(workcenter_id, plant_id);
                if (workcenter_id != null && !workcenter_id.equals(""))
                    if (workcenter_text != null && !workcenter_text.equals(""))
                        workCenter_edittext.setText(getString(R.string.hypen_text, workcenter_id,
                                workcenter_text));
                    else
                        workCenter_edittext.setText(workcenter_id);
                else
                    workCenter_edittext.setText("");
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = plnrGrpName(plannergroup_id, plant_id);
                if (plannergroup_id != null && !plannergroup_id.equals(""))
                    if (plannergroup_text != null && !plannergroup_text.equals(""))
                        plannerGroup_edittext.setText(getString(R.string.hypen_text, plannergroup_id,
                                plannergroup_text));
                    else
                        plannerGroup_edittext.setText(plannergroup_id);
                else
                    plannerGroup_edittext.setText("");
            } else if (requestCode == barcode_scan) {
                String message = data.getStringExtra("MESSAGE");
                equipid_edittext.setText(message);
                if (message != null && !message.equals("")) {
                    try {
                        Cursor cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?",
                                new String[]{message});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    equipment_id = message;
                                    equipment_text = cursor.getString(5);
                                    functionlocation_id = cursor.getString(1);
                                    plant_id = cursor.getString(29);
                                    plannergroup_id = cursor.getString(13);
                                    plannergroup_text = "";
                                    floc_edittext.setText(functionlocation_id);
                                    workcenter_id = cursor.getString(11);
                                    workCenter_edittext.setText(workcenter_id);
                                    floc_edittext.setText(functionlocation_id);
                                    plannergroup_id = data.getStringExtra("ingrp_id");
                                    plannergroup_text = plnrGrpName(plannergroup_id, plant_id);
                                    if (plannergroup_id != null && !plannergroup_id.equals(""))
                                        if (plannergroup_text != null && !plannergroup_text.equals(""))
                                            plannerGroup_edittext.setText(getString(R.string.hypen_text, plannergroup_id,
                                                    plannergroup_text));
                                        else
                                            plannerGroup_edittext.setText(plannergroup_id);
                                    else
                                        plannerGroup_edittext.setText("");
                                    equiptext_edittext.setText(equipment_text);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (functionlocation_id != null && !functionlocation_id.equals("")) {
                            Cursor cursor = App_db.rawQuery("select * from EtFuncEquip where" +
                                    " Tplnr = ?", new String[]{functionlocation_id});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        functionlocation_text = cursor.getString(2);
                                        flocname_edittext.setText(functionlocation_text);
                                    }
                                    while (cursor.moveToNext());
                                }
                            } else {
                                cursor.close();
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } else if (requestCode == workcenter_type) {
                workcenter_id = data.getStringExtra("workcenter_id");
                workcenter_text = data.getStringExtra("workcenter_text");
                workCenter_edittext.setText(workcenter_id);
            } else if (requestCode == notification_priority) {
                priority_type_id = data.getStringExtra("priority_type_id");
                priority_type_text = data.getStringExtra("priority_type_text");
                priority_edittext.setText(getString(R.string.hypen_text, priority_type_id,
                        priority_type_text));
            } else if (requestCode == planner_group) {
                plannergroup_id = data.getStringExtra("plannergroup_id");
                plannergroup_text = data.getStringExtra("plannergroup_text");
                plannerGroup_edittext.setText(getString(R.string.hypen_text, plannergroup_id,
                        plannergroup_text));
            } else if (requestCode == personResp) {
                personresponsible_id = data.getStringExtra("personresponsible_id");
                personresponsible_text = data.getStringExtra("personresponsible_text");
                personResp_edittext.setText(getString(R.string.hypen_text, personresponsible_id,
                        personresponsible_text));
            } else if (requestCode == req_stdate) {
                req_stdate_date = data.getStringExtra("date");
                req_stdate_date_formated = data.getStringExtra("date_formatted");
                req_stdate_time = data.getStringExtra("time");
                req_stdate_time_formatted = data.getStringExtra("time_formatted");
                reqStDt_edittext.setText(getString(R.string.hypen_text, req_stdate_date,
                        req_stdate_time));
            } else if (requestCode == req_enddate) {
                req_end_date = data.getStringExtra("date");
                req_end_date_formatted = data.getStringExtra("date_formatted");
                req_end_time = data.getStringExtra("time");
                req_end_time_formatted = data.getStringExtra("time_formatted");
                reqEnDt_edittext.setText(getString(R.string.hypen_text, req_end_date,
                        req_end_time));
            } else if (requestCode == malf_st_date) {
                mal_st_date = data.getStringExtra("date");
                mal_st_date_formatted = data.getStringExtra("date_formatted");
                mal_st_time = data.getStringExtra("time");
                mal_st_time_formatted = data.getStringExtra("time_formatted");
                malFunctnStDt_edittext.setText(getString(R.string.hypen_text, mal_st_date,
                        mal_st_time));
            } else if (requestCode == malf_end_date) {
                mal_end_date = data.getStringExtra("date");
                mal_end_date_formatted = data.getStringExtra("date_formatted");
                mal_end_time = data.getStringExtra("time");
                mal_end_time_formatted = data.getStringExtra("time_formatted");
                malFunctnEnDt_edittext.setText(getString(R.string.hypen_text, mal_end_date,
                        mal_end_time));
            } else if (requestCode == effect) {
                effect_id = data.getStringExtra("effect_id");
                effect_text = data.getStringExtra("effect_text");
                effect_edittext.setText(getString(R.string.hypen_text, effect_id, effect_text));
            } else if (requestCode == custom_info) {
                selected_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data
                        .getSerializableExtra("selected_custom_info_arraylist");
            }
        }
    }

    private class Get_Order_Data extends AsyncTask<Void, Integer, Void> {
        OrdrHeaderPrcbl ohp;
        ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
        ArrayList<OrdrObjectPrcbl> oobp_al = new ArrayList<OrdrObjectPrcbl>();
        ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrPermitPrcbl> wa_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrPermitPrcbl> wd_al = new ArrayList<OrdrPermitPrcbl>();
        ArrayList<OrdrWDItemPrcbl> wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> orstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wwstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wastp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<NotifOrdrStatusPrcbl> wdstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
        ArrayList<OrdrWaChkReqPrcbl> waChkReq_al = new ArrayList<OrdrWaChkReqPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> wwissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> waissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrWcagnsPrcbl> wdissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdUnTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        String ordrLngTxt = "", statusAll = "", WW_Objnr = "", WA_Objnr = "", WD_Objnr = "",
                WA_Wapinr = "", WA_Objtyp = "", Wcnr = "";
        int count = 0;
        StringBuilder stringbuilder = new StringBuilder();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = null;
            /*WorkApproval Data*/
            try {
                cursor = App_db.rawQuery("select * from EtWcmWwData where UUID = ?"
                        + "ORDER BY id DESC", new String[]{selected_orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrPermitPrcbl wwopp = new OrdrPermitPrcbl();
                            wwopp.setAufnr(cursor.getString(2));
                            wwopp.setObjart(cursor.getString(3));
                            wwopp.setWapnr(cursor.getString(4));
                            wwopp.setIwerk(cursor.getString(5));
                            wwopp.setUsage(cursor.getString(6));
                            wwopp.setUsagex(cursor.getString(7));
                            wwopp.setTrain(cursor.getString(8));
                            wwopp.setTrainx(cursor.getString(9));
                            wwopp.setAnlzu(cursor.getString(10));
                            wwopp.setAnlzux(cursor.getString(11));
                            wwopp.setEtape(cursor.getString(12));
                            wwopp.setEtapex(cursor.getString(13));
                            wwopp.setRctime(cursor.getString(14));
                            wwopp.setRcunit(cursor.getString(15));
                            wwopp.setPriok(cursor.getString(16));
                            wwopp.setPriokx(cursor.getString(17));
                            wwopp.setStxt(cursor.getString(18));
                            wwopp.setDatefr(cursor.getString(19));
                            wwopp.setTimefr(cursor.getString(20));
                            wwopp.setDateto(cursor.getString(21));
                            wwopp.setTimeto(cursor.getString(22));
                            WW_Objnr = cursor.getString(23);
                            wwopp.setObjnr(cursor.getString(23));
                            wwopp.setCrea(cursor.getString(24));
                            wwopp.setPrep(cursor.getString(25));
                            wwopp.setComp(cursor.getString(26));
                            wwopp.setAppr(cursor.getString(27));
                            wwopp.setPappr(cursor.getString(28));
                            wwopp.setAction(cursor.getString(29));
                            wwopp.setBegru(cursor.getString(30));
                            wwopp.setBegtx(cursor.getString(31));

                            Cursor cursor1 = null;
                            /*WW Status*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtOrderStatus where" +
                                                " Aufnr = ? and Objnr = ? order by Stonr",
                                        new String[]{selected_orderID, WW_Objnr});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                            osp.setAufnr(cursor1.getString(2));
                                            osp.setVornr(cursor1.getString(3));
                                            osp.setObjnr(cursor1.getString(4));
                                            osp.setStsma(cursor1.getString(5));
                                            osp.setInist(cursor1.getString(6));
                                            osp.setStonr(cursor1.getString(7));
                                            osp.setHsonr(cursor1.getString(8));
                                            osp.setNsonr(cursor1.getString(9));
                                            osp.setStat(cursor1.getString(10));
                                            osp.setAct(cursor1.getString(11));
                                            osp.setTxt04(cursor1.getString(12));
                                            osp.setTxt30(cursor1.getString(13));
                                            osp.setAction(cursor1.getString(14));
                                            wwstp_al.add(osp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }

                            /*WW Issue approvals*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtWcmWcagns where" +
                                                " Objnr = ? and Objart = ? order by Hilvl",
                                        new String[]{WW_Objnr, "WW"});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                            owp.setAufnr(cursor1.getString(2));
                                            owp.setObjnr(cursor1.getString(3));
                                            owp.setCounter(cursor1.getString(4));
                                            owp.setObjart(cursor1.getString(5));
                                            owp.setObjtyp(cursor1.getString(6));
                                            owp.setPmsog(cursor1.getString(7));
                                            owp.setGntxt(cursor1.getString(8));
                                            owp.setGeniakt(cursor1.getString(9));
                                            owp.setGenvname(cursor1.getString(10));
                                            owp.setAction(cursor1.getString(11));
                                            owp.setWerks(cursor1.getString(12));
                                            owp.setCrname(cursor1.getString(13));
                                            if (!cursor1.getString(14).equals(""))
                                                owp.setHilvl(Integer.parseInt(cursor1.getString(14)));
                                            owp.setProcflg(cursor1.getString(15));
                                            owp.setDirection(cursor1.getString(16));
                                            owp.setCopyflg(cursor1.getString(17));
                                            owp.setMandflg(cursor1.getString(18));
                                            owp.setDeacflg(cursor1.getString(19));
                                            owp.setStatus(cursor1.getString(20));
                                            owp.setAsgnflg(cursor1.getString(21));
                                            owp.setAutoflg(cursor1.getString(22));
                                            owp.setAgent(cursor1.getString(23));
                                            owp.setValflg(cursor1.getString(24));
                                            owp.setWcmuse(cursor1.getString(25));
                                            owp.setGendatum(cursor1.getString(26));
                                            owp.setGentime(cursor1.getString(27));
                                            wwissuep_al.add(owp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }

                            /*Permit Applications Data WA*/
                            try {
                                cursor1 = App_db.rawQuery("select * from EtWcmWaData where" +
                                        " Refobj = ?" + "ORDER BY id DESC", new String[]{WW_Objnr});

                                if (!(cursor1 != null && cursor1.getCount() > 0))
                                    cursor1 = App_db.rawQuery("select * from EtWcmWaData where" +
                                                    " Refobj = ?" + "ORDER BY id DESC",
                                            new String[]{generateRefobj(selected_orderID)});

                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            waChkReq_al = new ArrayList<OrdrWaChkReqPrcbl>();
                                            waissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
                                            wastp_al = new ArrayList<NotifOrdrStatusPrcbl>();
                                            wd_al = new ArrayList<OrdrPermitPrcbl>();
                                            OrdrPermitPrcbl opp = new OrdrPermitPrcbl();
                                            opp.setAufnr(cursor1.getString(2));
                                            opp.setObjart(cursor1.getString(3));
                                            WA_Wapinr = cursor1.getString(4);
                                            opp.setWapinr(cursor1.getString(4));
                                            opp.setIwerk(cursor1.getString(5));
                                            WA_Objtyp = cursor1.getString(6);
                                            opp.setObjtyp(cursor1.getString(6));
                                            opp.setApplName(getApplName(cursor1.getString(6), selected_Iwerk));
                                            opp.setUsage(cursor1.getString(7));
                                            opp.setUsagex(cursor1.getString(8));
                                            opp.setTrain(cursor1.getString(9));
                                            opp.setTrainx(cursor1.getString(10));
                                            opp.setAnlzu(cursor1.getString(11));
                                            opp.setAnlzux(cursor1.getString(12));
                                            opp.setEtape(cursor1.getString(13));
                                            opp.setEtapex(cursor1.getString(14));
                                            opp.setStxt(cursor1.getString(15));
                                            opp.setDatefr(dateDisplayFormat(cursor1.getString(16)));
                                            opp.setTimefr(timeDisplayFormat(cursor1.getString(17)));
                                            opp.setDateto(dateDisplayFormat(cursor1.getString(18)));
                                            opp.setTimeto(timeDisplayFormat(cursor1.getString(19)));
                                            opp.setPriok(cursor1.getString(20));
                                            opp.setPriokx(cursor1.getString(21));
                                            opp.setRctime(cursor1.getString(22));
                                            opp.setRcunit(cursor1.getString(23));
                                            WA_Objnr = cursor1.getString(24);
                                            opp.setObjnr(cursor1.getString(24));
                                            opp.setRefobj(cursor1.getString(25));
                                            opp.setCrea(cursor1.getString(26));
                                            opp.setPrep(cursor1.getString(27));
                                            opp.setComp(cursor1.getString(28));
                                            opp.setAppr(cursor1.getString(29));
                                            opp.setAction(cursor1.getString(30));
                                            opp.setBegru(cursor1.getString(31));
                                            opp.setBegtx(cursor1.getString(32));
                                            if (!cursor1.getString(33).equals(""))
                                                opp.setExtperiod(Integer.parseInt(cursor1.getString(33)));

                                            Cursor cursor2 = null;

                                            /*Wa Status Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtOrderStatus where" +
                                                                " Aufnr = ? and Objnr = ? order by Stonr",
                                                        new String[]{selected_orderID, WA_Objnr});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                                            osp.setAufnr(cursor2.getString(2));
                                                            osp.setVornr(cursor2.getString(3));
                                                            osp.setObjnr(cursor2.getString(4));
                                                            osp.setStsma(cursor2.getString(5));
                                                            osp.setInist(cursor2.getString(6));
                                                            osp.setStonr(cursor2.getString(7));
                                                            osp.setHsonr(cursor2.getString(8));
                                                            osp.setNsonr(cursor2.getString(9));
                                                            osp.setStat(cursor2.getString(10));
                                                            osp.setAct(cursor2.getString(11));
                                                            osp.setTxt04(cursor2.getString(12));
                                                            osp.setTxt30(cursor2.getString(13));
                                                            osp.setAction(cursor2.getString(14));
                                                            wastp_al.add(osp);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*Wa Issue Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWcagns where" +
                                                                " Objnr = ? and Objart = ? order by Hilvl",
                                                        new String[]{WA_Objnr, "WA"});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                                            owp.setAufnr(cursor2.getString(2));
                                                            owp.setObjnr(cursor2.getString(3));
                                                            owp.setCounter(cursor2.getString(4));
                                                            owp.setObjart(cursor2.getString(5));
                                                            owp.setObjtyp(cursor2.getString(6));
                                                            owp.setPmsog(cursor2.getString(7));
                                                            owp.setGntxt(cursor2.getString(8));
                                                            owp.setGeniakt(cursor2.getString(9));
                                                            owp.setGenvname(cursor2.getString(10));
                                                            owp.setAction(cursor2.getString(11));
                                                            owp.setWerks(cursor2.getString(12));
                                                            owp.setCrname(cursor2.getString(13));
                                                            if (!cursor2.getString(14).equals(""))
                                                                owp.setHilvl(Integer.parseInt(cursor2.getString(14)));
                                                            owp.setProcflg(cursor2.getString(15));
                                                            owp.setDirection(cursor2.getString(16));
                                                            owp.setCopyflg(cursor2.getString(17));
                                                            owp.setMandflg(cursor2.getString(18));
                                                            owp.setDeacflg(cursor2.getString(19));
                                                            owp.setStatus(cursor2.getString(20));
                                                            owp.setAsgnflg(cursor2.getString(21));
                                                            owp.setAutoflg(cursor2.getString(22));
                                                            owp.setAgent(cursor2.getString(23));
                                                            owp.setValflg(cursor2.getString(24));
                                                            owp.setWcmuse(cursor2.getString(25));
                                                            owp.setGendatum(cursor2.getString(26));
                                                            owp.setGentime(cursor2.getString(27));
                                                            waissuep_al.add(owp);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*Isolation Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWdData where" +
                                                                " Refobj = ?" + "ORDER BY id DESC",
                                                        new String[]{WA_Objnr});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
                                                            wdissuep_al = new ArrayList<OrdrWcagnsPrcbl>();
                                                            wdstp_al = new ArrayList<NotifOrdrStatusPrcbl>();
                                                            OrdrPermitPrcbl oop = new OrdrPermitPrcbl();
                                                            oop.setAufnr(cursor2.getString(1));
                                                            oop.setObjart(cursor2.getString(2));
                                                            Wcnr = cursor2.getString(3);
                                                            oop.setWcnr(cursor2.getString(3));
                                                            oop.setIwerk(cursor2.getString(4));
                                                            oop.setObjtyp(cursor2.getString(5));
                                                            oop.setUsage(cursor2.getString(6));
                                                            oop.setUsagex(cursor2.getString(7));
                                                            oop.setTrain(cursor2.getString(8));
                                                            oop.setTrainx(cursor2.getString(9));
                                                            oop.setAnlzu(cursor2.getString(10));
                                                            oop.setAnlzux(cursor2.getString(11));
                                                            oop.setEtape(cursor2.getString(12));
                                                            oop.setEtapex(cursor2.getString(13));
                                                            oop.setStxt(cursor2.getString(14));
                                                            oop.setDatefr(dateDisplayFormat(cursor2.getString(15)));
                                                            oop.setTimefr(timeDisplayFormat(cursor2.getString(16)));
                                                            oop.setDateto(dateDisplayFormat(cursor2.getString(17)));
                                                            oop.setTimeto(timeDisplayFormat(cursor2.getString(18)));
                                                            oop.setPriok(cursor2.getString(19));
                                                            oop.setPriokx(cursor2.getString(20));
                                                            oop.setRctime(cursor2.getString(21));
                                                            oop.setRcunit(cursor2.getString(22));
                                                            WD_Objnr = cursor2.getString(23);
                                                            oop.setObjnr(cursor2.getString(23));
                                                            oop.setRefobj(cursor2.getString(24));
                                                            oop.setCrea(cursor2.getString(25));
                                                            oop.setPrep(cursor2.getString(26));
                                                            oop.setComp(cursor2.getString(27));
                                                            oop.setAppr(cursor2.getString(28));
                                                            oop.setAction(cursor2.getString(29));
                                                            oop.setBegru(cursor2.getString(30));
                                                            oop.setBegtx(cursor2.getString(31));

                                                            Cursor cursor3 = null;

                                                            /*Wd Tagging Text*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataTagtext" +
                                                                        " where Aufnr = ?", new String[]{selected_orderID});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrTagUnTagTextPrcbl ttp = new OrdrTagUnTagTextPrcbl();
                                                                            ttp.setAufnr(cursor3.getString(1));
                                                                            ttp.setWcnr(cursor3.getString(2));
                                                                            ttp.setObjtype(cursor3.getString(3));
                                                                            ttp.setFormatCol(cursor3.getString(4));
                                                                            ttp.setTextLine(cursor3.getString(5));
                                                                            ttp.setAction(cursor3.getString(6));
                                                                            wdTagtxt_al.add(ttp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd UnTagging Text*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataUntagtext" +
                                                                        " where Aufnr = ?", new String[]{selected_orderID});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrTagUnTagTextPrcbl uttp = new OrdrTagUnTagTextPrcbl();
                                                                            uttp.setAufnr(cursor3.getString(1));
                                                                            uttp.setWcnr(cursor3.getString(2));
                                                                            uttp.setObjtype(cursor3.getString(3));
                                                                            uttp.setFormatCol(cursor3.getString(4));
                                                                            uttp.setTextLine(cursor3.getString(5));
                                                                            uttp.setAction(cursor3.getString(6));
                                                                            wdUnTagtxt_al.add(uttp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Status Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtOrderStatus" +
                                                                                " where Aufnr = ? and Objnr = ? order by Stonr",
                                                                        new String[]{selected_orderID, WD_Objnr});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                                                                            osp.setAufnr(cursor3.getString(2));
                                                                            osp.setVornr(cursor3.getString(3));
                                                                            osp.setObjnr(cursor3.getString(4));
                                                                            osp.setStsma(cursor3.getString(5));
                                                                            osp.setInist(cursor3.getString(6));
                                                                            osp.setStonr(cursor3.getString(7));
                                                                            osp.setHsonr(cursor3.getString(8));
                                                                            osp.setNsonr(cursor3.getString(9));
                                                                            osp.setStat(cursor3.getString(10));
                                                                            osp.setAct(cursor3.getString(11));
                                                                            osp.setTxt04(cursor3.getString(12));
                                                                            osp.setTxt30(cursor3.getString(13));
                                                                            osp.setAction(cursor3.getString(14));
                                                                            wdstp_al.add(osp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Issue Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWcagns where" +
                                                                                " Objnr = ? and Objart = ? order by Hilvl",
                                                                        new String[]{WD_Objnr, "WD"});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrWcagnsPrcbl owp = new OrdrWcagnsPrcbl();
                                                                            owp.setAufnr(cursor3.getString(2));
                                                                            owp.setObjnr(cursor3.getString(3));
                                                                            owp.setCounter(cursor3.getString(4));
                                                                            owp.setObjart(cursor3.getString(5));
                                                                            owp.setObjtyp(cursor3.getString(6));
                                                                            owp.setPmsog(cursor3.getString(7));
                                                                            owp.setGntxt(cursor3.getString(8));
                                                                            owp.setGeniakt(cursor3.getString(9));
                                                                            owp.setGenvname(cursor3.getString(10));
                                                                            owp.setAction(cursor3.getString(11));
                                                                            owp.setWerks(cursor3.getString(12));
                                                                            owp.setCrname(cursor3.getString(13));
                                                                            if (!cursor3.getString(14).equals(""))
                                                                                owp.setHilvl(Integer.parseInt(cursor3.getString(14)));
                                                                            owp.setProcflg(cursor3.getString(15));
                                                                            owp.setDirection(cursor3.getString(16));
                                                                            owp.setCopyflg(cursor3.getString(17));
                                                                            owp.setMandflg(cursor3.getString(18));
                                                                            owp.setDeacflg(cursor3.getString(19));
                                                                            owp.setStatus(cursor3.getString(20));
                                                                            owp.setAsgnflg(cursor3.getString(21));
                                                                            owp.setAutoflg(cursor3.getString(22));
                                                                            owp.setAgent(cursor3.getString(23));
                                                                            owp.setValflg(cursor3.getString(24));
                                                                            owp.setWcmuse(cursor3.getString(25));
                                                                            owp.setGendatum(cursor3.getString(26));
                                                                            owp.setGentime(cursor3.getString(27));
                                                                            wdissuep_al.add(owp);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            /*Wd Item Data*/
                                                            try {
                                                                cursor3 = App_db.rawQuery("select * from EtWcmWdItemData" +
                                                                        " where Wcnr = ?", new String[]{Wcnr});
                                                                if (cursor3 != null && cursor3.getCount() > 0) {
                                                                    if (cursor3.moveToFirst()) {
                                                                        do {
                                                                            OrdrWDItemPrcbl owip = new OrdrWDItemPrcbl();
                                                                            owip.setWcnr(cursor3.getString(1));
                                                                            owip.setWctim(cursor3.getString(2));
                                                                            owip.setObjnr(cursor3.getString(3));
                                                                            owip.setItmtyp(cursor3.getString(4));
                                                                            owip.setSeq(cursor3.getString(5));
                                                                            owip.setPred(cursor3.getString(6));
                                                                            owip.setSucc(cursor3.getString(7));
                                                                            owip.setCcobj(cursor3.getString(8));
                                                                            owip.setCctyp(cursor3.getString(9));
                                                                            owip.setStxt(cursor3.getString(10));
                                                                            owip.setTggrp(cursor3.getString(11));
                                                                            owip.setTgstep(cursor3.getString(12));
                                                                            owip.setTgproc(cursor3.getString(13));
                                                                            owip.setTgtyp(cursor3.getString(14));
                                                                            owip.setTgseq(cursor3.getString(15));
                                                                            owip.setTgtxt(cursor3.getString(16));
                                                                            owip.setUnstep(cursor3.getString(17));
                                                                            owip.setUnproc(cursor3.getString(18));
                                                                            owip.setUntyp(cursor3.getString(19));
                                                                            owip.setUnseq(cursor3.getString(20));
                                                                            owip.setUntxt(cursor3.getString(21));
                                                                            owip.setPhblflg(cursor3.getString(22));
                                                                            owip.setPhbltyp(cursor3.getString(23));
                                                                            owip.setPhblnr(cursor3.getString(24));
                                                                            owip.setTgflg(cursor3.getString(25));
                                                                            owip.setTgform(cursor3.getString(26));
                                                                            owip.setTgnr(cursor3.getString(27));
                                                                            owip.setUnform(cursor3.getString(28));
                                                                            owip.setUnnr(cursor3.getString(29));
                                                                            owip.setControl(cursor3.getString(30));
                                                                            owip.setLocation(cursor3.getString(31));
                                                                            owip.setRefobj(cursor3.getString(32));
                                                                            owip.setAction(cursor3.getString(33));
                                                                            owip.setBtg(cursor3.getString(34));
                                                                            owip.setEtg(cursor3.getString(35));
                                                                            owip.setBug(cursor3.getString(36));
                                                                            owip.setEug(cursor3.getString(37));
                                                                            wdIt_al.add(owip);
                                                                        }
                                                                        while (cursor3.moveToNext());
                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            } finally {
                                                                if (cursor3 != null)
                                                                    cursor3.close();
                                                            }

                                                            oop.setWdItemPrcbl_Al(wdIt_al);
                                                            oop.setStatusPrcbl_Al(wdstp_al);
                                                            oop.setWcagnsPrcbl_Al(wdissuep_al);
                                                            wd_al.add(oop);
                                                        } while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }

                                            /*WaChkRq Data*/
                                            try {
                                                cursor2 = App_db.rawQuery("select * from EtWcmWaChkReq where" +
                                                                " Wapinr = ? and Wapityp = ?",
                                                        new String[]{WA_Wapinr, WA_Objtyp});
                                                if (cursor2 != null && cursor2.getCount() > 0) {
                                                    if (cursor2.moveToFirst()) {
                                                        do {
                                                            OrdrWaChkReqPrcbl owcrp = new OrdrWaChkReqPrcbl();
                                                            owcrp.setWapinr(cursor2.getString(1));
                                                            owcrp.setWapityp(cursor2.getString(2));
                                                            owcrp.setChkPointType(cursor2.getString(5));
                                                            if (cursor2.getString(5).equalsIgnoreCase("W"))
                                                                owcrp.setWkid(cursor2.getString(3));
                                                            else
                                                                owcrp.setNeedid(cursor2.getString(3));
                                                            owcrp.setValue(cursor2.getString(4));
                                                            owcrp.setDesctext(cursor2.getString(6));
                                                            owcrp.setTplnr(cursor2.getString(7));
                                                            owcrp.setWkgrp(cursor2.getString(8));
                                                            owcrp.setNeedgrp(cursor2.getString(9));
                                                            owcrp.setEqunr(cursor2.getString(10));
                                                            waChkReq_al.add(owcrp);
                                                        }
                                                        while (cursor2.moveToNext());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            } finally {
                                                if (cursor2 != null)
                                                    cursor2.close();
                                            }
                                            opp.setWaWdPrcbl_Al(wd_al);
                                            opp.setWaChkReqPrcbl_Al(waChkReq_al);
                                            opp.setWcagnsPrcbl_Al(waissuep_al);
                                            opp.setStatusPrcbl_Al(wastp_al);
                                            wa_al.add(opp);
                                        } while (cursor1.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor.close();
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }
                            wwopp.setStatusPrcbl_Al(wwstp_al);
                            wwopp.setWcagnsPrcbl_Al(wwissuep_al);
                            wwopp.setWaWdPrcbl_Al(wa_al);
                            ww_al.add(wwopp);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Order Status Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderStatus where" +
                                " Aufnr = ? and Objnr like ? order by Stonr",
                        new String[]{selected_orderID, "OR%"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                            osp.setAufnr(cursor.getString(2));
                            osp.setVornr(cursor.getString(3));
                            osp.setObjnr(cursor.getString(4));
                            osp.setStsma(cursor.getString(5));
                            osp.setInist(cursor.getString(6));
                            osp.setStonr(cursor.getString(7));
                            osp.setHsonr(cursor.getString(8));
                            osp.setNsonr(cursor.getString(9));
                            osp.setStat(cursor.getString(10));
                            osp.setAct(cursor.getString(11));
                            osp.setTxt04(cursor.getString(12));
                            osp.setTxt30(cursor.getString(13));
                            osp.setAction(cursor.getString(14));
                            orstp_al.add(osp);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Components Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderComponents where" +
                                " UUID = ?" + "ORDER BY id DESC",
                        new String[]{selected_orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                            omp.setOprtnId(cursor.getString(3));
                            Cursor cursor1 = null;
                            try {
                                cursor1 = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations" +
                                                " where UUID = ? and Vornr = ?",
                                        new String[]{selected_orderUUID, cursor.getString(3)});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            omp.setOprtnShrtTxt(cursor1.getString(5));
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    omp.setOprtnShrtTxt("");
                                }
                            } catch (Exception e) {
                                if (cursor1 != null)
                                    cursor1.close();
                                omp.setOprtnShrtTxt("");
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }
                            omp.setPartId(cursor.getString(10));
                            omp.setMatrlId(cursor.getString(7));
                            omp.setMatrlTxt(cursor.getString(14));
                            omp.setPlantId(cursor.getString(8));
                            omp.setLocation(cursor.getString(9));
                            omp.setQuantity(cursor.getString(11));
                            omp.setRsnum(cursor.getString(5));
                            omp.setRspos(cursor.getString(6));
                            omp.setPostp(cursor.getString(13));
                            omp.setReceipt(cursor.getString(24));
                            omp.setUnloading(cursor.getString(25));
                            omp.setStatus("");
                            omp_al.add(omp);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Objects Data*/
            try {
                cursor = App_db.rawQuery("select * from EtOrderOlist" +
                        " where UUID = ?", new String[]{selected_orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrObjectPrcbl oobp = new OrdrObjectPrcbl();
                            oobp.setNotifNo(cursor.getString(5));
                            oobp.setEquipId(cursor.getString(6));
                            oobp.setEquipTxt(cursor.getString(12));
                            oobp.setTplnr(cursor.getString(8));
                            oobp_al.add(oobp);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Operations Data*/
            try {
                cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where" +
                        " UUID = ?" + "ORDER BY id DESC", new String[]{selected_orderUUID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String op_id = cursor.getString(3);
                            StringBuilder stringbuilder1 = new StringBuilder();
                            Cursor cursor2 = null;
                            try {
                                cursor2 = App_db.rawQuery("select * from DUE_ORDERS_Longtext" +
                                                " where UUID = ? and Activity = ?",
                                        new String[]{selected_orderUUID, op_id});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            stringbuilder1.append(cursor2.getString(4));
                                            stringbuilder1.append(System.getProperty("line.separator"));
                                        }
                                        while (cursor2.moveToNext());
                                    }
                                } else {
                                    if (cursor2 != null)
                                        cursor2.close();
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                            oop.setSelected(false);
                            oop.setOrdrId(selected_orderID);
                            oop.setOrdrSatus(selected_orderstatus);
                            oop.setOprtnId(cursor.getString(3));
                            oop.setOprtnShrtTxt(cursor.getString(5));
                            oop.setOprtnLngTxt(stringbuilder1.toString());
                            oop.setDuration(cursor.getString(10));
                            oop.setDurationUnit(cursor.getString(11));
                            oop.setPlantId(cursor.getString(7));
                            oop.setPlantTxt(cursor.getString(22));
                            oop.setWrkCntrId(cursor.getString(6));
                            oop.setWrkCntrTxt(cursor.getString(21));
                            oop.setCntrlKeyId(cursor.getString(8));
                            oop.setCntrlKeyTxt(cursor.getString(23));
                            oop.setAueru(cursor.getString(20));
                            oop.setUsr01(cursor.getString(25));
                            oop.setLarnt(cursor.getString(9));
                            oop.setFsavd(cursor.getString(12));
                            oop.setSsedd(cursor.getString(13));
                            oop.setRueck(cursor.getString(19));
                            oop.setStatus("");
                            oop_al.add(oop);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }

            /*Order Header Data*/
            try {
                cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where" +
                                " UUID = ? and Aufnr = ?",
                        new String[]{selected_orderUUID, selected_orderID});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Cursor cursor2 = null;
                            try {
                                count = 0;
                                stringbuilder = new StringBuilder();
                                cursor2 = App_db.rawQuery("select * from DUE_ORDERS_Longtext" +
                                                " where UUID = ? and Aufnr = ? and Activity = ?",
                                        new String[]{selected_orderUUID, selected_orderID, ""});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            count = count + 1;
                                            stringbuilder.append(cursor2.getString(4));
                                            ordrLngTxt = stringbuilder.toString();
                                            if (count < cursor2.getCount())
                                                stringbuilder.append("\n");
                                        } while (cursor2.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            try {
                                boolean added = false;
                                count = 0;
                                stringbuilder = new StringBuilder();
                                cursor2 = App_db.rawQuery("select * from EtOrderStatus where" +
                                                " Aufnr = ? and Objnr like ? order by Stonr",
                                        new String[]{selected_orderID, "OR%"});
                                if (cursor2 != null && cursor2.getCount() > 0) {
                                    if (cursor2.moveToFirst()) {
                                        do {
                                            count = count + 1;
                                            added = false;
                                            if (!cursor2.getString(7).equals("00") &&
                                                    cursor2.getString(10).startsWith("E")) {
                                                if (cursor2.getString(11).equalsIgnoreCase("X")) {
                                                    stringbuilder.append(cursor2.getString(12));
                                                    added = true;
                                                }

                                            } else if (cursor2.getString(7).equals("00") &&
                                                    cursor2.getString(10).startsWith("E")) {
                                                if (cursor2.getString(11).equalsIgnoreCase("X")) {
                                                    stringbuilder.append(cursor2.getString(12));
                                                    added = true;
                                                }
                                            }
                                            statusAll = stringbuilder.toString();
                                            if (count < cursor2.getCount())
                                                if (added)
                                                    stringbuilder.append(" ");
                                        } while (cursor2.moveToNext());
                                    }
                                }
                            } catch (Exception e) {
                                if (cursor2 != null)
                                    cursor2.close();
                            } finally {
                                if (cursor2 != null)
                                    cursor2.close();
                            }

                            ohp = new OrdrHeaderPrcbl();
                            ohp.setOrdrUUId(cursor.getString(1));
                            ohp.setOrdrId(cursor.getString(2));
                            ohp.setOrdrStatus(cursor.getString(39));
                            ohp.setStatusALL(statusAll);
                            ohp.setOrdrTypId(cursor.getString(3));
                            ohp.setOrdrTypName(cursor.getString(28));
                            ohp.setOrdrShrtTxt(cursor.getString(4));
                            ohp.setOrdrLngTxt(ordrLngTxt);
                            ohp.setNotifId(cursor.getString(20));
                            ohp.setFuncLocId(cursor.getString(10));
                            ohp.setFuncLocName(cursor.getString(31));
                            ohp.setEquipNum(cursor.getString(9));
                            ohp.setEquipName(cursor.getString(32));
                            ohp.setPriorityId(cursor.getString(8));
                            ohp.setPriorityTxt(cursor.getString(33));
                            ohp.setPlnrGrpId(cursor.getString(23));
                            ohp.setPlnrGrpName(cursor.getString(37));
                            ohp.setPerRespId(cursor.getString(53));
                            ohp.setPerRespName(cursor.getString(54));
                            ohp.setBasicStart(cursor.getString(14));
                            ohp.setBasicEnd(cursor.getString(13));
                            ohp.setRespCostCntrId(cursor.getString(46));
                            ohp.setSysCondId(cursor.getString(47));
                            ohp.setSysCondName(cursor.getString(48));
                            ohp.setWrkCntrId(cursor.getString(24));
                            ohp.setPlant(cursor.getString(25));
                            ohp.setWrkCntrName(cursor.getString(36));
                            ohp.setIwerk(selected_Iwerk);
                            ohp.setPosid(cursor.getString(55));
                            ohp.setRevnr(cursor.getString(56));
                            ohp.setBukrs(getBukrs(cursor.getString(9)));
                            ohp.setActivitytype_id(cursor.getString(5));
                            ohp.setActivitytype_text(cursor.getString(34));
                            ohp.setOrdrOprtnPrcbls(oop_al);
                            ohp.setOrdrObjectPrcbls(oobp_al);
                            ohp.setOrdrMatrlPrcbls(omp_al);
                            ohp.setOrdrStatusPrcbls(orstp_al);
                            ohp.setOrdrPermitPrcbls(ww_al);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (ohp != null) {
                Intent ordrIntent = new Intent(getActivity(), Orders_Change_Activity.class);
                ordrIntent.putExtra("order", "U");
                ordrIntent.putExtra("ordr_parcel", ohp);
                startActivity(ordrIntent);
            }
        }
    }

    private String generateRefobj(String orderId) {
        String ad = "";
        for (int i = 0; i < 12 - orderId.length(); i++) {
            ad = ad + "0";
        }
        return "OR" + ad + orderId;
    }

    private String getApplName(String objtyp, String iwerk) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtWcmTypes where Iwerk = ? and" +
                    " Objtyp = ?", new String[]{iwerk, objtyp});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(4);
                }
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

    private String getIwerk(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(29);
                }
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

    private String getfuncIwerk(String func) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtFuncEquip where Tplnr = ?",
                    new String[]{func});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(14);
                }
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

    private String getBukrs(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(30);
                }
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

    public Notifications_Create_Header_Object getData() {
        return new Notifications_Create_Header_Object(notification_type_id, notification_type_text,
                notifshtTxt_edittext.getText().toString(), floc_edittext.getText().toString(),
                functionlocation_text, equipid_edittext.getText().toString(), equipment_text,
                workcenter_id, workcenter_text, priority_type_id, priority_type_text,
                plannergroup_id, plannergroup_text, reportedby_edittext.getText().toString(),
                primUsrResp_edittext.getText().toString(), personresponsible_id,
                personresponsible_text, req_stdate_date_formated, req_stdate_time_formatted,
                req_end_date_formatted, req_end_time_formatted, mal_st_date_formatted,
                mal_st_time_formatted, mal_end_date_formatted, mal_end_time_formatted,
                effect_id, effect_text, plant_id, longtext_text, selected_custom_info_arraylist);
    }

    private String funcLocName(String funcLocId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtFuncEquip where Tplnr = ?",
                    new String[]{funcLocId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(2);
                }
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
    private String wrkCntrName(String wrkCntrId, String plant) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_WKCTR where Arbpl = ? and Werks" +
                    " = ?", new String[]{wrkCntrId, plant});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(8);
                }
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
    private String plnrGrpName(String plnrGrpId, String iwerk) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_EtIngrp where Ingrp = ? and" +
                    " Iwerk = ?", new String[]{plnrGrpId, iwerk});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(3);
                }
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
}
