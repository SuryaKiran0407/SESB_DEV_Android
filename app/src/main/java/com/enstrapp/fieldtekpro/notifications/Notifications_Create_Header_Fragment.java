package com.enstrapp.fieldtekpro.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Notifications_Create_Header_Fragment extends Fragment implements View.OnClickListener
{

    EditText notifshtTxt_edittext, primUsrResp_edittext,reportedby_edittext,equipid_edittext;
    int longtext = 13, malf_end_date = 11, malf_st_date = 10,
            req_enddate = 9, req_stdate = 8, personResp = 7, planner_group = 6, notification_type = 0,
            functionlocation_type = 1, equipment_type = 2, barcode_scan = 3, workcenter_type = 4,
            notification_priority = 5;
    String longtext_text = "", mal_end_date = "", mal_end_time = "",
            mal_end_date_formatted = "", mal_end_time_formatted = "", mal_st_date = "",
            mal_st_time = "", mal_st_date_formatted = "", mal_st_time_formatted = "",
            req_end_date = "", req_end_date_formatted = "", req_end_time = "",
            req_end_time_formatted = "", req_stdate_date = "", req_stdate_date_formated = "",
            req_stdate_time_formatted = "", req_stdate_time = "", personresponsible_id = "",
            personresponsible_text = "", priority_type_id = "", priority_type_text = "",
            workcenter_id = "", workcenter_text = "", plannergroup_text = "", plannergroup_id = "",
            plant_id = "", notification_type_id = "", notification_type_text = "",
            functionlocation_id = "", functionlocation_text = "", equipment_id = "", equipment_text = "",iwerk = "",plning_plant = "",maintance_plant = "";
    ImageView equipmentsearch_imageview, equipmentscan_imageview, longtext_imageview;
    private static final int ZXING_CAMERA_PERMISSION = 3;
    Error_Dialog error_dialog = new Error_Dialog();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Button malfuncenddate_button, malfuncstdate_button, reqenddate_button,reqStDt_button, person_resp_button, planningplant_button, plannergroup_type_button, maintenance_plant_button, workcenter_type_button, equiptext_button, flocname_button,func_loc_button, priority_type_button, notification_type_button;
    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<>();
    RadioButton breakdown_no_radiobutton, breakdown_yes_radiobutton;

    public Notifications_Create_Header_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_header_fragment, container, false);

        equipmentsearch_imageview = (ImageView) rootView.findViewById(R.id.equipmentsearch_imageview);
        equipmentscan_imageview = (ImageView) rootView.findViewById(R.id.equipmentscan_imageview);
        equipid_edittext = (EditText) rootView.findViewById(R.id.equipid_edittext);
        reportedby_edittext = (EditText) rootView.findViewById(R.id.reportedby_edittext);
        primUsrResp_edittext = (EditText) rootView.findViewById(R.id.primUsrResp_edittext);
        notifshtTxt_edittext = (EditText) rootView.findViewById(R.id.notifshtTxt_edittext);
        longtext_imageview = (ImageView) rootView.findViewById(R.id.longtext_imageview);
        notification_type_button = (Button) rootView.findViewById(R.id.notification_type_button);
        priority_type_button = (Button) rootView.findViewById(R.id.priority_type_button);
        func_loc_button = (Button) rootView.findViewById(R.id.func_loc_button);
        flocname_button = (Button) rootView.findViewById(R.id.flocname_button);
        equiptext_button = (Button) rootView.findViewById(R.id.equiptext_button);
        workcenter_type_button = (Button) rootView.findViewById(R.id.workcenter_type_button);
        maintenance_plant_button = (Button) rootView.findViewById(R.id.maintenance_plant_button);
        plannergroup_type_button = (Button) rootView.findViewById(R.id.plannergroup_type_button);
        planningplant_button = (Button) rootView.findViewById(R.id.planningplant_button);
        person_resp_button = (Button) rootView.findViewById(R.id.person_resp_button);
        reqStDt_button = (Button) rootView.findViewById(R.id.reqStDt_button);
        reqenddate_button = (Button) rootView.findViewById(R.id.reqenddate_button);
        malfuncstdate_button = (Button) rootView.findViewById(R.id.malfuncstdate_button);
        malfuncenddate_button = (Button) rootView.findViewById(R.id.malfuncenddate_button);
        breakdown_no_radiobutton = (RadioButton) rootView.findViewById(R.id.breakdown_no_radiobutton);
        breakdown_yes_radiobutton = (RadioButton) rootView.findViewById(R.id.breakdown_yes_radiobutton);


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity()
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            try {
                equipment_id = bundle.getString("equipment_id");
                equipment_text = bundle.getString("equipment_text");
                functionlocation_id = bundle.getString("functionlocation_id");
                functionlocation_text = bundle.getString("functionlocation_text");
                if(functionlocation_text != null && !functionlocation_text.equals("")) {
                }else {
                    try {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip " +
                                "where Tplnr = ?", new String[]{functionlocation_id});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    functionlocation_text = cursor.getString(2);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }
                }
                equipid_edittext.setText(equipment_id);
                equiptext_button.setText(equipment_text);
                func_loc_button.setText(functionlocation_id);
                flocname_button.setText(functionlocation_text);
                plant_id = bundle.getString("plant_id");
                plannergroup_id = bundle.getString("plannergroup_id");
                plannergroup_text = bundle.getString("plannergroup_text");
                plning_plant = bundle.getString("planing_plant");
                maintance_plant = bundle.getString("maintnce_plant");
                maintenance_plant_button.setText(maintance_plant);
                planningplant_button.setText(plning_plant);
                if (plannergroup_text == null || plannergroup_text.equals("") ||
                        plannergroup_text.equals("null")) {
                    try {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_EtIngrp " +
                                "where Ingrp = ?", new String[]{plannergroup_id});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    plannergroup_text = cursor.getString(3);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }
                }
                plannergroup_type_button.setText(plannergroup_id + " - " + plannergroup_text);
                workcenter_id = bundle.getString("work_center_id");
                workcenter_text = wrkCntrName(workcenter_id,plant_id);
                if (workcenter_id != null && !workcenter_id.equals(""))
                    if (workcenter_text != null && !workcenter_text.equals(""))
                        workcenter_type_button.setText(getString(R.string.hypen_text, workcenter_id,
                                workcenter_text));
                    else
                        workcenter_type_button.setText(workcenter_id);
                else
                    workcenter_type_button.setText("");
            } catch (Exception e) {
            }
        }

        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
            String formattedDate = df.format(c.getTime());
            SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
            String formattedDate_format = df1.format(c.getTime());
            SimpleDateFormat time_df = new SimpleDateFormat("HH:mm:ss");
            String formattedTime = time_df.format(c.getTime());
            SimpleDateFormat time_df1 = new SimpleDateFormat("HHmmss");
            String formattedTime_format = time_df1.format(c.getTime());
            reqStDt_button.setText(formattedDate + "  -  " + formattedTime);
            req_stdate_date = formattedDate;
            req_stdate_date_formated = formattedDate_format;
            req_stdate_time = formattedTime;
            req_stdate_time_formatted = formattedTime_format;
        } catch (Exception e) {
        }

        notification_type_button.setOnClickListener(this);
        func_loc_button.setOnClickListener(this);
        equipmentsearch_imageview.setOnClickListener(this);
        equipmentscan_imageview.setOnClickListener(this);
        workcenter_type_button.setOnClickListener(this);
        priority_type_button.setOnClickListener(this);
        plannergroup_type_button.setOnClickListener(this);
        person_resp_button.setOnClickListener(this);
        reqStDt_button.setOnClickListener(this);
        reqenddate_button.setOnClickListener(this);
        malfuncstdate_button.setOnClickListener(this);
        malfuncenddate_button.setOnClickListener(this);
        longtext_imageview.setOnClickListener(this);

        selected_custom_info_arraylist.clear();

        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        if (v == longtext_imageview) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_LongText_Activity.class);
            notification_type_intent.putExtra("request_id", Integer.toString(longtext));
            notification_type_intent.putExtra("qmnum", "");
            notification_type_intent.putExtra("longtext_new", longtext_text);
            startActivityForResult(notification_type_intent, longtext);
        } else if (v == notification_type_button) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_Type_Activity.class);
            notification_type_intent.putExtra("request_id",
                    Integer.toString(notification_type));
            startActivityForResult(notification_type_intent, notification_type);
        } else if (v == func_loc_button) {
            Intent notification_type_intent = new Intent(getActivity(),
                    FunctionLocation_Activity.class);
            notification_type_intent.putExtra("request_id",
                    Integer.toString(functionlocation_type));
            startActivityForResult(notification_type_intent, functionlocation_type);
        } else if (v == equipmentsearch_imageview) {
            Intent equipment_intent = new Intent(getActivity(), Equipment_Activity.class);
            equipment_intent.putExtra("request_id", Integer.toString(equipment_type));
            equipment_intent.putExtra("functionlocation_id",
                    func_loc_button.getText().toString());
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
        } else if (v == workcenter_type_button) {
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
        } else if (v == priority_type_button) {
            Intent notification_type_intent = new Intent(getActivity(),
                    Notifications_Priority_Activity.class);
            notification_type_intent.putExtra("request_id",
                    Integer.toString(notification_priority));
            startActivityForResult(notification_type_intent, notification_priority);
        } else if (v == plannergroup_type_button) {
            if (plant_id != null && !plant_id.equals("")) {
                Intent planner_group_intent = new Intent(getActivity(),
                        Notifications_PlannerGroup_Activity.class);
                planner_group_intent.putExtra("equip_id",
                        equipid_edittext.getText().toString());
                planner_group_intent.putExtra("plant_id", iwerk);
                planner_group_intent.putExtra("request_id", Integer.toString(planner_group));
                startActivityForResult(planner_group_intent, planner_group);
            } else {
                error_dialog.show_error_dialog(getActivity(),
                        getString(R.string.equipFunc_mandate));
            }
        } else if (v == person_resp_button) {
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
        } else if (v == reqStDt_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(req_stdate));
            startActivityForResult(intent, req_stdate);
        } else if (v == reqenddate_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(req_enddate));
            startActivityForResult(intent, req_enddate);
        } else if (v == malfuncstdate_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(malf_st_date));
            startActivityForResult(intent, malf_st_date);
        } else if (v == malfuncenddate_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(malf_end_date));
            startActivityForResult(intent, malf_end_date);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == longtext) {
                /*longtext_text = data.getStringExtra("longtext_new");
                if (notifshtTxt_edittext != null && !notifshtTxt_edittext.getText().toString().equals(" ")) {
                    if (longtext_text.length() > 40) {
                        notifshtTxt_edittext.setText(longtext_text.substring(0,
                                Math.min(longtext_text.length(), 40)));
                    } else {
                        notifshtTxt_edittext.setText(longtext_text);
                    }
                }*/
                longtext_text = data.getStringExtra("longtext_new");
                if (notifshtTxt_edittext != null &&
                        notifshtTxt_edittext.getText().toString().equals(""))
                    if (longtext_text != null && !longtext_text.equals("")) {
                        String[] next = longtext_text.split("\n");
                        if (next[0].length() > 40) {
                            notifshtTxt_edittext.setText(next[0].substring(0,
                                    Math.min(next[0].length(), 40)));
                        } else {
                            notifshtTxt_edittext.setText(next[0]);
                        }
                    }
            }else if (requestCode == notification_type) {
                notification_type_id = data.getStringExtra("notification_type_id");
                notification_type_text = data.getStringExtra("notification_type_text");
                notification_type_button.setText(getString(R.string.hypen_text, notification_type_id,
                        notification_type_text));
            } else if (requestCode == functionlocation_type) {
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                plant_id = data.getStringExtra("plant_id");
                iwerk = data.getStringExtra("iwerk");
                workcenter_text = wrkCntrName(workcenter_id, plant_id);
                equipment_id = "";
                equipment_text = "";
                equipid_edittext.setText("");
                equiptext_button.setText("");
                func_loc_button.setText(functionlocation_id);
                flocname_button.setText(functionlocation_text);
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = plnrGrpName(plannergroup_id, iwerk);
                if (plannergroup_id != null && !plannergroup_id.equals(""))
                    if (plannergroup_text != null && !plannergroup_text.equals(""))
                        plannergroup_type_button.setText(getString(R.string.hypen_text, plannergroup_id,
                                plannergroup_text));
                    else
                        plannergroup_type_button.setText(plannergroup_id);
                else
                    plannergroup_type_button.setText("");
                workcenter_id = data.getStringExtra("work_center");
                workcenter_text =data.getStringExtra("workcenter_text");
                if (plant_id != null && !plant_id.equals(""))
                    maintenance_plant_button.setText(plant_id);
                if (iwerk != null && !iwerk.equals(""))
                    planningplant_button.setText(iwerk);
                workcenter_type_button.setText(workcenter_id + "-" + workcenter_text );
            } else if (requestCode == equipment_type) {
                equipment_id = data.getStringExtra("equipment_id");
                equipment_text = data.getStringExtra("equipment_text");
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = funcLocName(data.getStringExtra("functionlocation_id"));
                equipid_edittext.setText(equipment_id);
                equiptext_button.setText(equipment_text);
                func_loc_button.setText(functionlocation_id);
                flocname_button.setText(functionlocation_text);
                plant_id = data.getStringExtra("plant_id");
                if (plant_id != null && !plant_id.equals(""))
                    maintenance_plant_button.setText(plant_id);
                iwerk = data.getStringExtra("iwerk");
                if (iwerk != null && !iwerk.equals(""))
                    planningplant_button.setText(iwerk);
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                workcenter_id = data.getStringExtra("work_center");
                workcenter_text = wrkCntrName(workcenter_id, iwerk);
                if (workcenter_id != null && !workcenter_id.equals(""))
                    if (workcenter_text != null && !workcenter_text.equals(""))
                        workcenter_type_button.setText(getString(R.string.hypen_text, workcenter_id,
                                workcenter_text));
                    else
                        workcenter_type_button.setText(workcenter_id);
                else
                    workcenter_type_button.setText("");
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = plnrGrpName(plannergroup_id, iwerk);
                if (plannergroup_id != null && !plannergroup_id.equals(""))
                    if (plannergroup_text != null && !plannergroup_text.equals(""))
                        plannergroup_type_button.setText(getString(R.string.hypen_text, plannergroup_id,
                                plannergroup_text));
                    else
                        plannergroup_type_button.setText(plannergroup_id);
                else
                    plannergroup_type_button.setText("");
            } else if (requestCode == barcode_scan) {
                String message = data.getStringExtra("MESSAGE");
                equipid_edittext.setText(message);
                if (message != null && !message.equals("")) {
                    try {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where " +
                                "Equnr = ?", new String[]{message});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    equipment_id = message;
                                    equipment_text = cursor.getString(5);
                                    functionlocation_id = cursor.getString(1);
                                    plant_id = cursor.getString(29);
                                    plannergroup_id = cursor.getString(13);
                                    func_loc_button.setText(functionlocation_id);
                                    iwerk = cursor.getString(29);
                                    workcenter_id = cursor.getString(11);
                                    workcenter_text = wrkCntrName(workcenter_id, plant_id);
                                    if (workcenter_id != null && !workcenter_id.equals(""))
                                        if (workcenter_text != null && !workcenter_text.equals(""))
                                            workcenter_type_button.setText(getString(R.string.hypen_text, workcenter_id,
                                                    workcenter_text));
                                        else
                                            workcenter_type_button.setText(workcenter_id);
                                    else
                                        workcenter_type_button.setText("");
                                    if (plant_id != null && !plant_id.equals(""))
                                        maintenance_plant_button.setText(plant_id);
                                    if (iwerk != null && !iwerk.equals(""))
                                        planningplant_button.setText(iwerk);
                                    func_loc_button.setText(functionlocation_id);
                                    plannergroup_text = plnrGrpName(plannergroup_id, iwerk);
                                    if (plannergroup_id != null && !plannergroup_id.equals(""))
                                        if (plannergroup_text != null && !plannergroup_text.equals(""))
                                            plannergroup_type_button.setText(getString(R.string.hypen_text, plannergroup_id,
                                                    plannergroup_text));
                                        else
                                            plannergroup_type_button.setText(plannergroup_id);
                                    else
                                        plannergroup_type_button.setText("");
                                    equiptext_button.setText(equipment_text);
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
                            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip" +
                                    " where Tplnr = ?", new String[]{functionlocation_id});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        functionlocation_text = cursor.getString(2);
                                        flocname_button.setText(functionlocation_text);
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
                workcenter_type_button.setText(workcenter_id + "-" + workcenter_text);
            } else if (requestCode == notification_priority) {
                priority_type_id = data.getStringExtra("priority_type_id");
                priority_type_text = data.getStringExtra("priority_type_text");
                priority_type_button.setText(priority_type_id + " - " + priority_type_text);
            } else if (requestCode == planner_group) {
                plannergroup_id = data.getStringExtra("plannergroup_id");
                plannergroup_text = data.getStringExtra("plannergroup_text");
                plannergroup_type_button.setText(plannergroup_id + " - " + plannergroup_text);
            } else if (requestCode == personResp) {
                personresponsible_id = data.getStringExtra("personresponsible_id");
                personresponsible_text = data.getStringExtra("personresponsible_text");
                person_resp_button.setText(personresponsible_id + " - " + personresponsible_text);
            } else if (requestCode == req_stdate) {
                req_stdate_date = data.getStringExtra("date");
                req_stdate_date_formated = data.getStringExtra("date_formatted");
                req_stdate_time = data.getStringExtra("time");
                req_stdate_time_formatted = data.getStringExtra("time_formatted");
                reqStDt_button.setText(req_stdate_date + "  -  " + req_stdate_time);
            } else if (requestCode == req_enddate) {
                req_end_date = data.getStringExtra("date");
                req_end_date_formatted = data.getStringExtra("date_formatted");
                req_end_time = data.getStringExtra("time");
                req_end_time_formatted = data.getStringExtra("time_formatted");
                reqenddate_button.setText(req_end_date + "  -  " + req_end_time);
            } else if (requestCode == malf_st_date) {
                mal_st_date = data.getStringExtra("date");
                mal_st_date_formatted = data.getStringExtra("date_formatted");
                mal_st_time = data.getStringExtra("time");
                mal_st_time_formatted = data.getStringExtra("time_formatted");
                malfuncstdate_button.setText(mal_st_date + "  -  " + mal_st_time);
            } else if (requestCode == malf_end_date) {
                mal_end_date = data.getStringExtra("date");
                mal_end_date_formatted = data.getStringExtra("date_formatted");
                mal_end_time = data.getStringExtra("time");
                mal_end_time_formatted = data.getStringExtra("time_formatted");
                malfuncenddate_button.setText(mal_end_date + "  -  " + mal_end_time);
            }
        }
    }

    public Notifications_Create_Header_Object getData()
    {
        String breakdown_status = "";
        if(breakdown_yes_radiobutton.isChecked())
        {
            breakdown_status = "X";
        }
        else if(breakdown_no_radiobutton.isChecked())
        {
            breakdown_status = "";
        }
        return new Notifications_Create_Header_Object(notification_type_id, notification_type_text,
                notifshtTxt_edittext.getText().toString(), func_loc_button.getText().toString(),
                functionlocation_text, equipid_edittext.getText().toString(),
                equipment_text, workcenter_id, workcenter_text, priority_type_id, priority_type_text,
                plannergroup_id, plannergroup_text, reportedby_edittext.getText().toString(),
                primUsrResp_edittext.getText().toString(), personresponsible_id,
                personresponsible_text, req_stdate_date_formated, req_stdate_time_formatted,
                req_end_date_formatted, req_end_time_formatted, mal_st_date_formatted,
                mal_st_time_formatted, mal_end_date_formatted, mal_end_time_formatted, "",
                "", plant_id, longtext_text, selected_custom_info_arraylist, breakdown_status);
    }


    private String funcLocName(String funcLocId) {
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Tplnr = ?",
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
            cursor = FieldTekPro_db.rawQuery("select * from GET_WKCTR where Arbpl = ? and Werks" +
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
            cursor = FieldTekPro_db.rawQuery("select * from GET_EtIngrp where Ingrp = ? and" +
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
