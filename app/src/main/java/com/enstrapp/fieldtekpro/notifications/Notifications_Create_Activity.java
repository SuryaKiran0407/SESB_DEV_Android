package com.enstrapp.fieldtekpro.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Notifications_Create_Activity extends AppCompatActivity implements View.OnClickListener {

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    TabLayout tablayout;
    ViewPager viewpager;
    Notifications_Tab_Adapter orders_ta;
    Button cancel_button, submit_button;
    Map<String, String> notif_create_status;
    String longtext = "", primary_user_resp = "", workcenter_id = "", workcenter_text = "",
            notification_type_id = "", notification_type_text = "", notif_text = "",
            functionlocation_id = "", functionlocation_text = "", equipment_id = "",
            equipment_text = "", priority_type_id = "", priority_type_text = "",
            plannergroup_id = "", plannergroup_text = "", Reported_by = "", personresponsible_id = "",
            personresponsible_text = "", req_st_date = "", req_st_time = "", req_end_date = "",
            req_end_time = "", mal_st_date = "", mal_st_time = "", mal_end_date = "", mal_end_time = "",
            effect_id = "", effect_text = "", plant_id = "",maintnce_plant = "",planing_plant = "";
    Error_Dialog error_dialog = new Error_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    FloatingActionButton fab;
    ArrayList<Model_Notif_Causecode> causecodeArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Activity> ActivityArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Attachments> AttachmentsArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Longtext> LongtextsArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Task> TasksArrayList = new ArrayList<>();
    ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
    ArrayList<Model_CustomInfo> object_custominfo = new ArrayList<>();
    ArrayList<Model_CustomInfo> activity_custominfo = new ArrayList<>();
    ArrayList<Model_CustomInfo> task_custominfo = new ArrayList<>();
    ImageView back_iv;
    Dialog submit_decision_dialog, decision_dialog;
    private List<Notif_Dup_List_Object> notification_duplicate_list = new ArrayList<>();
    Notification_Duplicate_Adapter notification_duplicate_adapter;
    ArrayList<HashMap<String, String>> header_custom_info_arraylist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_orders_create_change_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equipment_id = extras.getString("equipment_id");
            equipment_text = extras.getString("equipment_text");
            functionlocation_id = extras.getString("functionlocation_id");
            functionlocation_text = extras.getString("functionlocation_text");
            plant_id = extras.getString("plant_id");
            plannergroup_id = extras.getString("plannergroup_id");
            plannergroup_text = extras.getString("plannergroup_text");
            workcenter_id = extras.getString("work_center_id");
            maintnce_plant = extras.getString("maintnce_plant");
            planing_plant = extras.getString("planing_plant");
        }

        DATABASE_NAME = Notifications_Create_Activity.this.getString(R.string.database_name);
        App_db = Notifications_Create_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        cancel_button = findViewById(R.id.cancel_button);
        submit_button = findViewById(R.id.save_button);
        fab = findViewById(R.id.fab);
        back_iv = findViewById(R.id.back_iv);

        orders_ta = new Notifications_Tab_Adapter(this, getSupportFragmentManager());
        orders_ta.addFragment(new Notifications_Create_Header_Fragment(), getResources().getString(R.string.header));
        orders_ta.addFragment(new Notifications_Create_Causecode_Fragment(), getResources().getString(R.string.causecode));
        orders_ta.addFragment(new Notifications_Create_Activity_Fragment(), getResources().getString(R.string.activity));
        orders_ta.addFragment(new Notifications_Create_Task_Fragment(), getResources().getString(R.string.task));
        orders_ta.addFragment(new Notifications_Create_Attachments_Fragment(), getResources().getString(R.string.attachments));
        viewpager.setOffscreenPageLimit(5);
        viewpager.setAdapter(orders_ta);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.hide();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        fab.show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tablayout.setupWithViewPager(viewpager);

        fab.hide();

        setCustomFont();

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_iv.setOnClickListener(this);
    }

    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tablayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(),
                            "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            Notifications_Create_Activity.this.finish();
        } else if (v == back_iv) {
            Notifications_Create_Activity.this.finish();
        } else if (v == submit_button) {
            /*Fetching Header Data*/
            Notifications_Create_Header_Fragment header_tab =
                    (Notifications_Create_Header_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
            Notifications_Create_Header_Object header_data = header_tab.getData();
            notification_type_id = header_data.getNotification_type_id();
            notification_type_text = header_data.getNotification_type_text();
            notif_text = header_data.getNotifshtTxt();
            functionlocation_id = header_data.getFunctionlocation_id();
            functionlocation_text = header_data.getFunctionlocation_text();
            equipment_id = header_data.getEquipment_id();
            equipment_text = header_data.getEquipment_text();
            plant_id = header_data.getPlant_id();
            workcenter_id = header_data.getWorkcenter_id();
            workcenter_text = header_data.getWorkcenter_text();
            priority_type_id = header_data.getPriority_type_id();
            priority_type_text = header_data.getPriority_type_text();
            plannergroup_id = header_data.getPlannergroup_id();
            plannergroup_text = header_data.getPlannergroup_text();
            Reported_by = header_data.getReportedby();
            primary_user_resp = header_data.getPrimUsrResp();
            personresponsible_id = header_data.getPersonresponsible_id();
            personresponsible_text = header_data.getPersonresponsible_text();
            req_st_date = header_data.getReq_stdate_date_formated();
            req_st_time = header_data.getReq_stdate_time_formatted();
            req_end_date = header_data.getReq_end_date_formatted();
            req_end_time = header_data.getReq_end_time_formatted();
            mal_st_date = header_data.getMal_st_date_formatted();
            mal_st_time = header_data.getMal_st_time_formatted();
            mal_end_date = header_data.getMal_end_date_formatted();
            mal_end_time = header_data.getMal_end_time_formatted();
            effect_id = header_data.getEffect_id();
            effect_text = header_data.getEffect_text();
            longtext = header_data.getLongtext_text();

            header_custominfo.clear();
            header_custom_info_arraylist = header_data.getCustom_info_arraylist();
            if (header_custom_info_arraylist.size() > 0) {
                for (int i = 0; i < header_custom_info_arraylist.size(); i++) {
                    Model_CustomInfo mnc = new Model_CustomInfo();
                    mnc.setZdoctype(header_custom_info_arraylist.get(i).get("Zdoctype"));
                    mnc.setZdoctypeItem(header_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                    mnc.setTabname(header_custom_info_arraylist.get(i).get("Tabname"));
                    mnc.setFieldname(header_custom_info_arraylist.get(i).get("Fieldname"));
                    mnc.setDatatype(header_custom_info_arraylist.get(i).get("Datatype"));
                    String datatype = header_custom_info_arraylist.get(i).get("Datatype");
                    if (datatype.equalsIgnoreCase("DATS")) {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "MMM dd, yyyy";
                        String outputPattern = "yyyyMMdd";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try {
                            Date date = inputFormat.parse(value);
                            String formatted_date = outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        } catch (Exception e) {
                            mnc.setValue("");
                        }
                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "HH:mm:ss";
                        String outputPattern = "HHmmss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try {
                            Date date = inputFormat.parse(value);
                            String formatted_date = outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        } catch (Exception e) {
                            mnc.setValue("");
                        }
                    } else {
                        mnc.setValue(header_custom_info_arraylist.get(i).get("Value"));
                    }
                    mnc.setFlabel(header_custom_info_arraylist.get(i).get("Flabel"));
                    mnc.setSequence(header_custom_info_arraylist.get(i).get("Sequence"));
                    mnc.setLength(header_custom_info_arraylist.get(i).get("Length"));
                    header_custominfo.add(mnc);
                }
            }
            /*Fetching Header Data*/

            /*Adding Notification Longtext to Arraylist*/
            if (longtext != null && !longtext.equals("")) {
                if (longtext.contains("\n")) {
                    String[] longtext_array = longtext.split("\n");
                    for (int i = 0; i < longtext_array.length; i++) {
                        Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                        mnc.setQmnum("");
                        mnc.setObjkey("");
                        mnc.setObjtype("");
                        mnc.setTextLine(longtext_array[i]);
                        LongtextsArrayList.add(mnc);
                    }
                } else {
                    Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                    mnc.setQmnum("");
                    mnc.setObjkey("");
                    mnc.setObjtype("");
                    mnc.setTextLine(longtext);
                    LongtextsArrayList.add(mnc);
                }
            }
            /*Adding Notification Longtext to Arraylist*/

            /*Fetching Cause Code Data*/
            causecodeArrayList = new ArrayList<>();
            Notifications_Create_Causecode_Fragment causecode_fragment =
                    (Notifications_Create_Causecode_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
            List<Notifications_Create_Causecode_Fragment.Cause_Code_Object> causecode_list =
                    causecode_fragment.getCauseCodeData();
            for (int i = 0; i < causecode_list.size(); i++) {
                Model_Notif_Causecode mnc = new Model_Notif_Causecode();
                mnc.setQmnum("");
                mnc.setItemKey(causecode_list.get(i).getitem_key());
                mnc.setItempartGrp(causecode_list.get(i).getobject_part_id());
                mnc.setPartgrptext(causecode_list.get(i).getobject_part_text());
                mnc.setItempartCod(causecode_list.get(i).getobjectcode_id());
                mnc.setPartcodetext(causecode_list.get(i).getobject_code_text());
                mnc.setItemdefectGrp(causecode_list.get(i).getevent_id());
                mnc.setDefectgrptext(causecode_list.get(i).getevent_text());
                mnc.setItemdefectCod(causecode_list.get(i).geteventcode_id());
                mnc.setDefectcodetext(causecode_list.get(i).geteventcode_text());
                mnc.setItemdefectShtxt(causecode_list.get(i).getevent_desc());
                mnc.setCauseKey(causecode_list.get(i).getCause_key());
                mnc.setCauseGrp(causecode_list.get(i).getcause_id());
                mnc.setCausegrptext(causecode_list.get(i).getcause_text());
                mnc.setCauseCod(causecode_list.get(i).getcausecode_id());
                mnc.setCausecodetext(causecode_list.get(i).getcausecode_text());
                mnc.setCauseShtxt(causecode_list.get(i).getcause_desc());
                mnc.setUsr01("");
                mnc.setUsr02("");
                mnc.setUsr03("");
                mnc.setUsr04("");
                mnc.setUsr05("");
                mnc.setAction("I");

                object_custominfo.clear();
                /*Fetching Object Custom Fields*/
                ArrayList<HashMap<String, String>> selected_object_custominfo =
                        causecode_list.get(i).getSelected_object_custom_info_arraylist();
                if (selected_object_custominfo.size() > 0) {
                    for (int j = 0; j < selected_object_custominfo.size(); j++) {
                        Model_CustomInfo model_customInfo = new Model_CustomInfo();
                        model_customInfo.setZdoctype(selected_object_custominfo.get(j).get("Zdoctype"));
                        model_customInfo.setZdoctypeItem(selected_object_custominfo.get(j).get("ZdoctypeItem"));
                        model_customInfo.setTabname(selected_object_custominfo.get(j).get("Tabname"));
                        model_customInfo.setFieldname(selected_object_custominfo.get(j).get("Fieldname"));
                        model_customInfo.setDatatype(selected_object_custominfo.get(j).get("Datatype"));
                        String datatype = selected_object_custominfo.get(j).get("Datatype");
                        if (datatype.equalsIgnoreCase("DATS")) {
                            String value = selected_object_custominfo.get(j).get("Value");
                            String inputPattern = "MMM dd, yyyy";
                            String outputPattern = "yyyyMMdd";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else if (datatype.equalsIgnoreCase("TIMS")) {
                            String value = selected_object_custominfo.get(j).get("Value");
                            String inputPattern = "HH:mm:ss";
                            String outputPattern = "HHmmss";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else {
                            model_customInfo.setValue(selected_object_custominfo.get(j).get("Value"));
                        }
                        model_customInfo.setFlabel(selected_object_custominfo.get(j).get("Flabel"));
                        model_customInfo.setSequence(selected_object_custominfo.get(j).get("Sequence"));
                        model_customInfo.setLength(selected_object_custominfo.get(j).get("Length"));
                        object_custominfo.add(model_customInfo);
                    }
                }
                mnc.setItNotifItemsFields(object_custominfo);
                /*Fetching Object Custom Fields*/

                /*Fetching Cause Custom Fields*/
                ArrayList<HashMap<String, String>> selected_cause_custominfo =
                        causecode_list.get(i).getSelected_cause_custom_info_arraylist();
                if (selected_cause_custominfo.size() > 0) {
                    for (int j = 0; j < selected_cause_custominfo.size(); j++) {
                        Model_CustomInfo model_customInfo = new Model_CustomInfo();
                        model_customInfo.setZdoctype(selected_cause_custominfo.get(j).get("Zdoctype"));
                        model_customInfo.setZdoctypeItem(selected_cause_custominfo.get(j).get("ZdoctypeItem"));
                        model_customInfo.setTabname(selected_cause_custominfo.get(j).get("Tabname"));
                        model_customInfo.setFieldname(selected_cause_custominfo.get(j).get("Fieldname"));
                        model_customInfo.setDatatype(selected_cause_custominfo.get(j).get("Datatype"));
                        String datatype = selected_cause_custominfo.get(j).get("Datatype");
                        if (datatype.equalsIgnoreCase("DATS")) {
                            String value = selected_cause_custominfo.get(j).get("Value");
                            String inputPattern = "MMM dd, yyyy";
                            String outputPattern = "yyyyMMdd";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else if (datatype.equalsIgnoreCase("TIMS")) {
                            String value = selected_cause_custominfo.get(j).get("Value");
                            String inputPattern = "HH:mm:ss";
                            String outputPattern = "HHmmss";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else {
                            model_customInfo.setValue(selected_cause_custominfo.get(j).get("Value"));
                        }
                        model_customInfo.setFlabel(selected_cause_custominfo.get(j).get("Flabel"));
                        model_customInfo.setSequence(selected_cause_custominfo.get(j).get("Sequence"));
                        model_customInfo.setLength(selected_cause_custominfo.get(j).get("Length"));
                        object_custominfo.add(model_customInfo);
                    }
                }
                mnc.setItNotifItemsFields(object_custominfo);
                causecodeArrayList.add(mnc);
                /*Fetching Cause Custom Fields*/
            }
            /*Fetching Cause Code Data*/

            /*Fetching Activity Data*/
            ActivityArrayList = new ArrayList<>();
            Notifications_Create_Activity_Fragment activity_fragment =
                    (Notifications_Create_Activity_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
            List<Notifications_Create_Activity_Fragment.Activity_Object> activity_list =
                    activity_fragment.getActivityData();
            for (int i = 0; i < activity_list.size(); i++) {
                Model_Notif_Activity mnc = new Model_Notif_Activity();
                mnc.setQmnum("");
                mnc.setActvKey(activity_list.get(i).getActivity_itemkey());
                mnc.setItemKey(activity_list.get(i).getCause_itemkey());
                mnc.setActvGrp(activity_list.get(i).getCodegroup_id());
                mnc.setActvCod(activity_list.get(i).getCode_id());
                mnc.setActcodetext(activity_list.get(i).getCode_text());
                mnc.setActvShtxt(activity_list.get(i).getActivity_shtxt());
                mnc.setStartDate(activity_list.get(i).getSt_date());
                mnc.setEndDate(activity_list.get(i).getEnd_date());
                mnc.setStartTime(activity_list.get(i).getSt_time());
                mnc.setEndTime(activity_list.get(i).getEnd_time());
                mnc.setUsr05("");
                mnc.setAction("I");

                activity_custominfo.clear();
                /*Fetching Activity Custom Fields*/
                ArrayList<HashMap<String, String>> selected_activity_custominfo =
                        activity_list.get(i).getSelected_activity_custom_info_arraylist();
                if (selected_activity_custominfo.size() > 0) {
                    for (int j = 0; j < selected_activity_custominfo.size(); j++) {
                        Model_CustomInfo model_customInfo = new Model_CustomInfo();
                        model_customInfo.setZdoctype(selected_activity_custominfo.get(j).get("Zdoctype"));
                        model_customInfo.setZdoctypeItem(selected_activity_custominfo.get(j).get("ZdoctypeItem"));
                        model_customInfo.setTabname(selected_activity_custominfo.get(j).get("Tabname"));
                        model_customInfo.setFieldname(selected_activity_custominfo.get(j).get("Fieldname"));
                        model_customInfo.setDatatype(selected_activity_custominfo.get(j).get("Datatype"));
                        String datatype = selected_activity_custominfo.get(j).get("Datatype");
                        if (datatype.equalsIgnoreCase("DATS")) {
                            String value = selected_activity_custominfo.get(j).get("Value");
                            String inputPattern = "MMM dd, yyyy";
                            String outputPattern = "yyyyMMdd";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else if (datatype.equalsIgnoreCase("TIMS")) {
                            String value = selected_activity_custominfo.get(j).get("Value");
                            String inputPattern = "HH:mm:ss";
                            String outputPattern = "HHmmss";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                            try {
                                Date date = inputFormat.parse(value);
                                String formatted_date = outputFormat.format(date);
                                model_customInfo.setValue(formatted_date);
                            } catch (Exception e) {
                                model_customInfo.setValue("");
                            }
                        } else {
                            model_customInfo.setValue(selected_activity_custominfo.get(j).get("Value"));
                        }
                        model_customInfo.setFlabel(selected_activity_custominfo.get(j).get("Flabel"));
                        model_customInfo.setSequence(selected_activity_custominfo.get(j).get("Sequence"));
                        model_customInfo.setLength(selected_activity_custominfo.get(j).get("Length"));
                        activity_custominfo.add(model_customInfo);
                    }
                }
                mnc.setItNotifActvsFields(activity_custominfo);
                ActivityArrayList.add(mnc);
                /*Fetching Activity Custom Fields*/
            }
            /*Fetching Activity Data*/

            /*Fetching Task Data*/
            TasksArrayList = new ArrayList<>();
            Notifications_Create_Task_Fragment task_fragment =
                    (Notifications_Create_Task_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.viewpager, 3));
            List<Notifications_Create_Task_Fragment.Task_Object> task_list = task_fragment.getTaskData();
            for (int i = 0; i < task_list.size(); i++) {
                Model_Notif_Task mnc = new Model_Notif_Task();
                mnc.setQmnum("");
                mnc.setItemKey(task_list.get(i).getItem_key());
                mnc.setTaskKey(task_list.get(i).getItem_key());
                mnc.setTaskGrp(task_list.get(i).getTaskcodegroup_id());
                mnc.setTaskgrptext(task_list.get(i).getTaskcodegroup_text());
                mnc.setTaskCod(task_list.get(i).getTaskcode_id());
                mnc.setTaskcodetext(task_list.get(i).getTaskcode_text());
                mnc.setTaskShtxt(task_list.get(i).getTask_text());
                mnc.setPster(task_list.get(i).getPlanned_st_date_formatted());
                mnc.setPeter(task_list.get(i).getPlanned_end_date_formatted());
                mnc.setPstur(task_list.get(i).getPlanned_st_time_formatted());
                mnc.setPetur(task_list.get(i).getPlanned_end_time_formatted());
                mnc.setParvw(task_list.get(i).getTaskprocessor_id());
                mnc.setParnr(task_list.get(i).getTask_responsible());
                mnc.setErlnam(task_list.get(i).getCompletedby());
                mnc.setErldat(task_list.get(i).getCompletion_date_formatted());
                mnc.setErlzeit(task_list.get(i).getCompletion_time_formatted());
                String release = task_list.get(i).getRelease_status();
                if (release.equalsIgnoreCase("X")) {
                    mnc.setRelease("X");
                } else {
                    mnc.setRelease("");
                }
                String Complete = task_list.get(i).getCompleted_status();
                if (Complete.equalsIgnoreCase("X")) {
                    mnc.setComplete("X");
                } else {
                    mnc.setComplete("");
                }
                String Success = task_list.get(i).getSuccess_status();
                if (Success.equalsIgnoreCase("X")) {
                    mnc.setSuccess("X");
                } else {
                    mnc.setSuccess("");
                }
                mnc.setAction("I");

                task_custominfo = new ArrayList<>();
                /*Fetching Task Custom Fields*/
                ArrayList<HashMap<String, String>> selected_task_custominfo =
                        task_list.get(i).getSelected_tasks_custom_info_arraylist();
                if(selected_task_custominfo!=null) {
                    if (selected_task_custominfo.size() > 0) {
                        for (int j = 0; j < selected_task_custominfo.size(); j++) {
                            Model_CustomInfo model_customInfo = new Model_CustomInfo();
                            model_customInfo.setZdoctype(selected_task_custominfo.get(j).get("Zdoctype"));
                            model_customInfo.setZdoctypeItem(selected_task_custominfo.get(j).get("ZdoctypeItem"));
                            model_customInfo.setTabname(selected_task_custominfo.get(j).get("Tabname"));
                            model_customInfo.setFieldname(selected_task_custominfo.get(j).get("Fieldname"));
                            model_customInfo.setDatatype(selected_task_custominfo.get(j).get("Datatype"));
                            String datatype = selected_task_custominfo.get(j).get("Datatype");
                            if (datatype.equalsIgnoreCase("DATS")) {
                                String value = selected_task_custominfo.get(j).get("Value");
                                String inputPattern = "MMM dd, yyyy";
                                String outputPattern = "yyyyMMdd";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date = outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                } catch (Exception e) {
                                    model_customInfo.setValue("");
                                }
                            } else if (datatype.equalsIgnoreCase("TIMS")) {
                                String value = selected_task_custominfo.get(j).get("Value");
                                String inputPattern = "HH:mm:ss";
                                String outputPattern = "HHmmss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date = outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                } catch (Exception e) {
                                    model_customInfo.setValue("");
                                }
                            } else {
                                model_customInfo.setValue(selected_task_custominfo.get(j).get("Value"));
                            }
                            model_customInfo.setFlabel(selected_task_custominfo.get(j).get("Flabel"));
                            model_customInfo.setSequence(selected_task_custominfo.get(j).get("Sequence"));
                            model_customInfo.setLength(selected_task_custominfo.get(j).get("Length"));
                            task_custominfo.add(model_customInfo);
                        }
                    }
                    mnc.setItNotfTaskFields(task_custominfo);
                    TasksArrayList.add(mnc);
                }  /*Fetching Task Custom Fields*/
            }
            /*Fetching Task Data*/

            /*Fetching Attachments Data*/
            AttachmentsArrayList = new ArrayList<>();
            Notifications_Create_Attachments_Fragment attachment_fragment =
                    (Notifications_Create_Attachments_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.viewpager, 4));
            List<Notif_EtDocs_Parcelable> attachment_list = attachment_fragment.getAttachmentsData();
            for (int i = 0; i < attachment_list.size(); i++) {
                Model_Notif_Attachments mnc = new Model_Notif_Attachments();
                mnc.setObjtype(attachment_list.get(i).getObjtype());
                mnc.setZobjid(attachment_list.get(i).getZobjid());
                mnc.setZdoctype(attachment_list.get(i).getZdoctype());
                mnc.setZdoctypeItem(attachment_list.get(i).getZdoctypeitem());
                mnc.setFilename(attachment_list.get(i).getFilename());
                mnc.setFiletype(attachment_list.get(i).getFiletype());
                mnc.setFsize(attachment_list.get(i).getFsize());
                mnc.setContent(attachment_list.get(i).getContent());
                mnc.setDocId(attachment_list.get(i).getDocid());
                mnc.setDocType(attachment_list.get(i).getDoctype());
                AttachmentsArrayList.add(mnc);
            }
            /*Fetching Attachments Data*/

            if (notification_type_id != null && !notification_type_id.equals("")) {
                if (notif_text != null && !notif_text.equals("")) {
                    if ((equipment_id != null && !equipment_id.equals("")) ||
                            (functionlocation_id != null && !functionlocation_id.equals(""))) {
                        if (workcenter_id != null && !workcenter_id.equals("")) {
                            if (priority_type_id != null && !priority_type_id.equals("")) {
                                if (plannergroup_id != null && !plannergroup_id.equals("")) {
                                    if (req_st_date != null && !req_st_date.equals("") &&
                                            req_st_time != null && !req_st_time.equals("")) {
                                        cd = new ConnectionDetector(Notifications_Create_Activity.this);
                                        isInternetPresent = cd.isConnectingToInternet();
                                        if (isInternetPresent) {
                                            submit_decision_dialog =
                                                    new Dialog(Notifications_Create_Activity.this);
                                            submit_decision_dialog.getWindow()
                                                    .setBackgroundDrawableResource(android.R.color.transparent);
                                            submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            submit_decision_dialog.setCancelable(false);
                                            submit_decision_dialog.setCanceledOnTouchOutside(false);
                                            submit_decision_dialog.setContentView(R.layout.decision_dialog);
                                            ImageView imageView1 = submit_decision_dialog
                                                    .findViewById(R.id.imageView1);
                                            Glide.with(Notifications_Create_Activity.this)
                                                    .load(R.drawable.error_dialog_gif).into(imageView1);
                                            TextView description_textview = submit_decision_dialog
                                                    .findViewById(R.id.description_textview);
                                            description_textview
                                                    .setText(getString(R.string.notifcreate_submitdialog));
                                            Button ok_button = submit_decision_dialog
                                                    .findViewById(R.id.yes_button);
                                            Button cancel_button = submit_decision_dialog
                                                    .findViewById(R.id.no_button);
                                            submit_decision_dialog.show();
                                            ok_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    submit_decision_dialog.dismiss();
                                                    new Get_Token().execute();
                                                }
                                            });
                                            cancel_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    submit_decision_dialog.dismiss();
                                                }
                                            });
                                        } else {
                                            decision_dialog =
                                                    new Dialog(Notifications_Create_Activity.this);
                                            decision_dialog.getWindow()
                                                    .setBackgroundDrawableResource(android.R.color.transparent);
                                            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            decision_dialog.setCancelable(false);
                                            decision_dialog.setCanceledOnTouchOutside(false);
                                            decision_dialog.setContentView(R.layout.offline_decision_dialog);
                                            TextView description_textview = decision_dialog
                                                    .findViewById(R.id.description_textview);
                                            Button confirm = decision_dialog.findViewById(R.id.yes_button);
                                            Button cancel = decision_dialog.findViewById(R.id.no_button);
                                            Button connect_button = decision_dialog
                                                    .findViewById(R.id.connect_button);
                                            description_textview
                                                    .setText(getString(R.string.notifcreate_offline));
                                            confirm.setText(getString(R.string.yes));
                                            cancel.setText(getString(R.string.no));
                                            decision_dialog.show();
                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    custom_progress_dialog
                                                            .show_progress_dialog(Notifications_Create_Activity.this, getResources().getString(R.string.loading));
                                                    String timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
                                                    UUID uniqueKey = UUID.randomUUID();
                                                    try {
                                                        App_db.beginTransaction();
                                                        String EtNotifHeader_sql =
                                                                "Insert into DUE_NOTIFICATION_NotifHeader" +
                                                                        " (UUID,NotifType,Qmnum,NotifShorttxt," +
                                                                        "FunctionLoc,Equipment,Bautl,ReportedBy," +
                                                                        "MalfuncStdate,MalfuncEddate,MalfuncSttime," +
                                                                        "MalfuncEdtime,BreakdownInd,Priority,Ingrp," +
                                                                        "Arbpl,Werks,Strmn,Ltrmn,Aufnr,Docs," +
                                                                        "Altitude,Latitude,Longitude,Closed," +
                                                                        "Completed,Createdon,Qmartx,Pltxt,Eqktx," +
                                                                        "Priokx,Auftext,Auarttext,Plantname," +
                                                                        "Wkctrname,Ingrpname,Maktx,Xstatus,Usr01," +
                                                                        "Usr02,Usr03,Usr04,Usr05,STATUS,ParnrVw," +
                                                                        "NameVw,Auswk,Shift,Noofperson,Auswkt," +
                                                                        " Strur, Ltrur, sort_malfc, Qmdat)" +
                                                                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtNotifHeader_statement = App_db
                                                                .compileStatement(EtNotifHeader_sql);
                                                        EtNotifHeader_statement.clearBindings();
                                                        EtNotifHeader_statement.bindString(1, uniqueKey.toString());
                                                        EtNotifHeader_statement.bindString(2, notification_type_id);
                                                        EtNotifHeader_statement.bindString(3, "NOT_" + timeStamp);
                                                        EtNotifHeader_statement.bindString(4, notif_text);
                                                        EtNotifHeader_statement.bindString(5, functionlocation_id);
                                                        EtNotifHeader_statement.bindString(6, equipment_id);
                                                        EtNotifHeader_statement.bindString(7, "");
                                                        EtNotifHeader_statement.bindString(8, Reported_by);
                                                        EtNotifHeader_statement.bindString(9, mal_st_date);
                                                        EtNotifHeader_statement.bindString(10, mal_end_date);
                                                        EtNotifHeader_statement.bindString(11, mal_st_time);
                                                        EtNotifHeader_statement.bindString(12, mal_end_time);
                                                        EtNotifHeader_statement.bindString(13, "");
                                                        EtNotifHeader_statement.bindString(14, priority_type_id);
                                                        EtNotifHeader_statement.bindString(15, plannergroup_id);
                                                        EtNotifHeader_statement.bindString(16, workcenter_id);
                                                        EtNotifHeader_statement.bindString(17, plant_id);
                                                        EtNotifHeader_statement.bindString(18, req_st_date);
                                                        EtNotifHeader_statement.bindString(19, req_end_date);
                                                        EtNotifHeader_statement.bindString(20, "");
                                                        if (AttachmentsArrayList.size() > 0) {
                                                            EtNotifHeader_statement.bindString(21, "X");
                                                        } else {
                                                            EtNotifHeader_statement.bindString(21, "");
                                                        }
                                                        EtNotifHeader_statement.bindString(22, "");
                                                        EtNotifHeader_statement.bindString(23, "");
                                                        EtNotifHeader_statement.bindString(24, "");
                                                        EtNotifHeader_statement.bindString(25, "");
                                                        EtNotifHeader_statement.bindString(26, "");
                                                        EtNotifHeader_statement.bindString(27, "");
                                                        EtNotifHeader_statement.bindString(28, notification_type_text);
                                                        EtNotifHeader_statement.bindString(29, functionlocation_text);
                                                        EtNotifHeader_statement.bindString(30, equipment_text);
                                                        EtNotifHeader_statement.bindString(31, priority_type_text);
                                                        EtNotifHeader_statement.bindString(32, "");
                                                        EtNotifHeader_statement.bindString(33, "");
                                                        EtNotifHeader_statement.bindString(34, "");
                                                        EtNotifHeader_statement.bindString(35, workcenter_text);
                                                        EtNotifHeader_statement.bindString(36, plannergroup_text);
                                                        EtNotifHeader_statement.bindString(37, "");
                                                        EtNotifHeader_statement.bindString(38, "OFL");
                                                        EtNotifHeader_statement.bindString(39, primary_user_resp);
                                                        EtNotifHeader_statement.bindString(40, "");
                                                        EtNotifHeader_statement.bindString(41, "");
                                                        EtNotifHeader_statement.bindString(42, "");
                                                        EtNotifHeader_statement.bindString(43, "");
                                                        EtNotifHeader_statement.bindString(44, "OFL");
                                                        EtNotifHeader_statement.bindString(45, personresponsible_id);
                                                        EtNotifHeader_statement.bindString(46, personresponsible_text);
                                                        EtNotifHeader_statement.bindString(47, effect_id);
                                                        EtNotifHeader_statement.bindString(48, "");
                                                        EtNotifHeader_statement.bindString(49, "");
                                                        EtNotifHeader_statement.bindString(50, effect_text);
                                                        EtNotifHeader_statement.bindString(51, req_st_time);
                                                        EtNotifHeader_statement.bindString(52, req_end_time);
                                                        EtNotifHeader_statement.bindString(53, mal_st_date + " " + mal_st_time);
                                                        EtNotifHeader_statement.bindString(54, "");
                                                        EtNotifHeader_statement.execute();
                                                        App_db.setTransactionSuccessful();
                                                        App_db.endTransaction();
                                                    } catch (Exception e) {
                                                    }

                                                    try {
                                                        if (longtext != null && !longtext.equals("")) {
                                                            App_db.beginTransaction();
                                                            if (longtext.contains("\n")) {
                                                                String EtNotifLongtext_sql =
                                                                        "Insert into DUE_NOTIFICATIONS_EtNotifLongtext" +
                                                                                " (UUID, Qmnum, Objtype, TextLine," +
                                                                                " Objkey) values(?,?,?,?,?);";
                                                                SQLiteStatement EtNotifLongtext_statement = App_db
                                                                        .compileStatement(EtNotifLongtext_sql);
                                                                EtNotifLongtext_statement.clearBindings();
                                                                String[] longtext_array = longtext.split("\n");
                                                                for (int i = 0; i < longtext_array.length; i++) {
                                                                    EtNotifLongtext_statement.bindString(1, uniqueKey.toString());
                                                                    EtNotifLongtext_statement.bindString(2, "NOT_" + timeStamp);
                                                                    EtNotifLongtext_statement.bindString(3, "");
                                                                    EtNotifLongtext_statement.bindString(4, longtext_array[i]);
                                                                    EtNotifLongtext_statement.bindString(5, "");
                                                                    EtNotifLongtext_statement.execute();
                                                                }
                                                            } else {
                                                                String EtNotifLongtext_sql =
                                                                        "Insert into DUE_NOTIFICATIONS_EtNotifLongtext" +
                                                                                " (UUID, Qmnum, Objtype, TextLine," +
                                                                                " Objkey) values(?,?,?,?,?);";
                                                                SQLiteStatement EtNotifLongtext_statement = App_db
                                                                        .compileStatement(EtNotifLongtext_sql);
                                                                EtNotifLongtext_statement.clearBindings();
                                                                EtNotifLongtext_statement.bindString(1, uniqueKey.toString());
                                                                EtNotifLongtext_statement.bindString(2, "NOT_" + timeStamp);
                                                                EtNotifLongtext_statement.bindString(3, "");
                                                                EtNotifLongtext_statement.bindString(4, longtext);
                                                                EtNotifLongtext_statement.bindString(5, "");
                                                                EtNotifLongtext_statement.execute();
                                                            }
                                                            App_db.setTransactionSuccessful();
                                                            App_db.endTransaction();
                                                        }
                                                    } catch (Exception e) {
                                                    }

                                                    try {
                                                        String EtNotifItems_sql =
                                                                "Insert into DUE_NOTIFICATIONS_EtNotifItems" +
                                                                        " (UUID, Qmnum, ItemKey, ItempartGrp," +
                                                                        " Partgrptext, ItempartCod, Partcodetext," +
                                                                        " ItemdefectGrp, Defectgrptext," +
                                                                        " ItemdefectCod, Defectcodetext," +
                                                                        " ItemdefectShtxt, CauseKey, CauseGrp," +
                                                                        " Causegrptext, CauseCod, Causecodetext," +
                                                                        " CauseShtxt, Usr01, Usr02, Usr03, Usr04," +
                                                                        " Usr05, Status) values(?,?,?,?,?,?,?,?," +
                                                                        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtNotifItems_statement = App_db
                                                                .compileStatement(EtNotifItems_sql);
                                                        EtNotifItems_statement.clearBindings();
                                                        Notifications_Create_Causecode_Fragment causecode_fragment =
                                                                (Notifications_Create_Causecode_Fragment) getSupportFragmentManager()
                                                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                                                        List<Notifications_Create_Causecode_Fragment.Cause_Code_Object> causecode_list =
                                                                causecode_fragment.getCauseCodeData();
                                                        if (causecode_list.size() > 0) {
                                                            App_db.beginTransaction();
                                                            for (int i = 0; i < causecode_list.size(); i++) {
                                                                EtNotifItems_statement.bindString(1, uniqueKey.toString());
                                                                EtNotifItems_statement.bindString(2, "NOT_" + timeStamp);
                                                                EtNotifItems_statement.bindString(3, causecode_list.get(i).getitem_key());
                                                                EtNotifItems_statement.bindString(4, causecode_list.get(i).getobject_part_id());
                                                                EtNotifItems_statement.bindString(5, causecode_list.get(i).getobject_part_text());
                                                                EtNotifItems_statement.bindString(6, causecode_list.get(i).getobjectcode_id());
                                                                EtNotifItems_statement.bindString(7, causecode_list.get(i).getobject_code_text());
                                                                EtNotifItems_statement.bindString(8, causecode_list.get(i).getevent_id());
                                                                EtNotifItems_statement.bindString(9, causecode_list.get(i).getevent_text());
                                                                EtNotifItems_statement.bindString(10, causecode_list.get(i).geteventcode_id());
                                                                EtNotifItems_statement.bindString(11, causecode_list.get(i).geteventcode_text());
                                                                EtNotifItems_statement.bindString(12, causecode_list.get(i).getevent_desc());
                                                                EtNotifItems_statement.bindString(13, causecode_list.get(i).getCause_key());
                                                                EtNotifItems_statement.bindString(14, causecode_list.get(i).getcause_id());
                                                                EtNotifItems_statement.bindString(15, causecode_list.get(i).getcause_text());
                                                                EtNotifItems_statement.bindString(16, causecode_list.get(i).getcausecode_id());
                                                                EtNotifItems_statement.bindString(17, causecode_list.get(i).getcausecode_text());
                                                                EtNotifItems_statement.bindString(18, causecode_list.get(i).getcause_desc());
                                                                EtNotifItems_statement.bindString(19, "");
                                                                EtNotifItems_statement.bindString(20, "");
                                                                EtNotifItems_statement.bindString(21, "");
                                                                EtNotifItems_statement.bindString(22, "");
                                                                EtNotifItems_statement.bindString(23, "");
                                                                EtNotifItems_statement.bindString(24, "I");
                                                                EtNotifItems_statement.execute();
                                                            }
                                                            App_db.setTransactionSuccessful();
                                                            App_db.endTransaction();
                                                        }
                                                    } catch (Exception e) {
                                                    }

                                                    try {
                                                        String EtNotifActvs_sql =
                                                                "Insert into DUE_NOTIFICATION_EtNotifActvs" +
                                                                        " (UUID, Qmnum, ItemKey, ItempartGrp," +
                                                                        " Partgrptext, ItempartCod, Partcodetext," +
                                                                        " ItemdefectGrp, Defectgrptext, ItemdefectCod," +
                                                                        " Defectcodetext, ItemdefectShtxt, CauseKey," +
                                                                        " ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext," +
                                                                        " ActvShtxt, Usr01, Usr02, Usr03, Usr04, Usr05," +
                                                                        " Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                                                                        "?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtNotifActvs_statement = App_db
                                                                .compileStatement(EtNotifActvs_sql);
                                                        EtNotifActvs_statement.clearBindings();
                                                        Notifications_Create_Activity_Fragment activity_fragment =
                                                                (Notifications_Create_Activity_Fragment) getSupportFragmentManager()
                                                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
                                                        List<Notifications_Create_Activity_Fragment.Activity_Object> activity_list =
                                                                activity_fragment.getActivityData();
                                                        if (activity_list.size() > 0) {
                                                            App_db.beginTransaction();
                                                            for (int i = 0; i < activity_list.size(); i++) {
                                                                EtNotifActvs_statement.bindString(1, uniqueKey.toString());
                                                                EtNotifActvs_statement.bindString(2, "NOT_" + timeStamp);
                                                                EtNotifActvs_statement.bindString(3, activity_list.get(i).getCause_itemkey());
                                                                EtNotifActvs_statement.bindString(4, "");
                                                                EtNotifActvs_statement.bindString(5, "");
                                                                EtNotifActvs_statement.bindString(6, "");
                                                                EtNotifActvs_statement.bindString(7, "");
                                                                EtNotifActvs_statement.bindString(8, "");
                                                                EtNotifActvs_statement.bindString(9, "");
                                                                EtNotifActvs_statement.bindString(10, "");
                                                                EtNotifActvs_statement.bindString(11, "");
                                                                EtNotifActvs_statement.bindString(12, "");
                                                                EtNotifActvs_statement.bindString(13, "");
                                                                EtNotifActvs_statement.bindString(14, activity_list.get(i).getActivity_itemkey());
                                                                EtNotifActvs_statement.bindString(15, activity_list.get(i).getCodegroup_id());
                                                                EtNotifActvs_statement.bindString(16, "");
                                                                EtNotifActvs_statement.bindString(17, activity_list.get(i).getCode_id());
                                                                EtNotifActvs_statement.bindString(18, activity_list.get(i).getCode_text());
                                                                EtNotifActvs_statement.bindString(19, activity_list.get(i).getActivity_shtxt());
                                                                EtNotifActvs_statement.bindString(20, activity_list.get(i).getSt_date());
                                                                EtNotifActvs_statement.bindString(21, activity_list.get(i).getEnd_date());
                                                                EtNotifActvs_statement.bindString(22, activity_list.get(i).getSt_time());
                                                                EtNotifActvs_statement.bindString(23, activity_list.get(i).getEnd_time());
                                                                EtNotifActvs_statement.bindString(24, "");
                                                                EtNotifActvs_statement.bindString(25, "");
                                                                EtNotifActvs_statement.bindString(26, "I");
                                                                EtNotifActvs_statement.execute();
                                                            }
                                                            App_db.setTransactionSuccessful();
                                                            App_db.endTransaction();
                                                        }
                                                    } catch (Exception e) {
                                                    }

                                                    try {
                                                        String EgtNotifTAsk_sql = "Insert into DUE_NOTIFICATION_EtNotifTasks" +
                                                                "(UUID, Qmnum, ItemKey, ItempartGrp,Partgrptext, ItempartCod, Partcodetext, " +
                                                                "ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, " +
                                                                "ItemdefectShtxt, TaskKey, TaskGrp, Taskgrptext, TaskCod, Taskcodetext," +
                                                                "TaskShtxt, Pster, Peter, Pstur, Petur, Parvw, Parnr, Erlnam, Erldat, " +
                                                                "Erlzeit, Release, Complete, Success, UserStatus, SysStatus, Smsttxt, " +
                                                                "Smastxt, Usr01, Usr02, Usr03, Usr04, Usr05, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

                                                        SQLiteStatement EtTasks_statement = App_db.compileStatement(EgtNotifTAsk_sql);
                                                        EtTasks_statement.clearBindings();
                                                        Notifications_Create_Task_Fragment task_fragment =
                                                                (Notifications_Create_Task_Fragment) getSupportFragmentManager()
                                                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 3));
                                                        List<Notifications_Create_Task_Fragment.Task_Object> task_list =
                                                                task_fragment.getTaskData();
                                                        if (task_list.size() > 0) {
                                                            App_db.beginTransaction();
                                                            for (int i = 0; i < task_list.size(); i++) {
                                                                EtTasks_statement.bindString(1, uniqueKey.toString());
                                                                EtTasks_statement.bindString(2,"NOT_" + timeStamp );
                                                                EtTasks_statement.bindString(3, task_list.get(i).getItem_key());
                                                                EtTasks_statement.bindString(4, " ");
                                                                EtTasks_statement.bindString(5, " ");
                                                                EtTasks_statement.bindString(6, " ");
                                                                EtTasks_statement.bindString(7, " ");
                                                                EtTasks_statement.bindString(8, " ");
                                                                EtTasks_statement.bindString(9, " ");
                                                                EtTasks_statement.bindString(10, " ");
                                                                EtTasks_statement.bindString(11, " ");
                                                                EtTasks_statement.bindString(12, " ");
                                                                EtTasks_statement.bindString(13, task_list.get(i).getItem_key());
                                                                EtTasks_statement.bindString(14, task_list.get(i).getTaskcodegroup_id());
                                                                EtTasks_statement.bindString(15, task_list.get(i).getTaskcodegroup_text());
                                                                EtTasks_statement.bindString(16, task_list.get(i).getTaskcode_id());
                                                                EtTasks_statement.bindString(17, task_list.get(i).getTaskcode_text());
                                                                EtTasks_statement.bindString(18, task_list.get(i).getTask_text());
                                                                EtTasks_statement.bindString(19, task_list.get(i).getPlanned_st_date_formatted());
                                                                EtTasks_statement.bindString(20, task_list.get(i).getPlanned_end_date_formatted());
                                                                EtTasks_statement.bindString(21, task_list.get(i).getPlanned_st_time_formatted());
                                                                EtTasks_statement.bindString(22, task_list.get(i).getPlanned_end_time_formatted());
                                                                EtTasks_statement.bindString(23, task_list.get(i).getTaskprocessor_id());
                                                                EtTasks_statement.bindString(24, task_list.get(i).getTask_responsible());
                                                                EtTasks_statement.bindString(25, task_list.get(i).getCompletedby());
                                                                EtTasks_statement.bindString(26, task_list.get(i).getCompletion_date_formatted());
                                                                EtTasks_statement.bindString(27, task_list.get(i).getCompletion_time_formatted());
                                                                EtTasks_statement.bindString(28, " ");
                                                                EtTasks_statement.bindString(29, " ");
                                                                EtTasks_statement.bindString(30, task_list.get(i).getRelease_status());
                                                                EtTasks_statement.bindString(31, task_list.get(i).getCompleted_status());
                                                                EtTasks_statement.bindString(32, task_list.get(i).getSuccess_status());
                                                                EtTasks_statement.bindString(33, " ");
                                                                EtTasks_statement.bindString(34, " ");
                                                                EtTasks_statement.bindString(35, " ");
                                                                EtTasks_statement.bindString(36, " ");
                                                                EtTasks_statement.bindString(37, " ");
                                                                EtTasks_statement.bindString(38, " ");
                                                                EtTasks_statement.bindString(39, " ");
                                                                EtTasks_statement.bindString(40, " ");
                                                                EtTasks_statement.execute();
                                                            }
                                                            App_db.setTransactionSuccessful();
                                                            App_db.endTransaction();
                                                        }
                                                    }
                                                    catch (Exception e)
                                                    {
                                                        Log.v("task response",""+e.getMessage());

                                                    }
                                                    try {
                                                        String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs" +
                                                                "(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, " +
                                                                "Filetype, Fsize, Content, DocId, DocType, Objtype," +
                                                                " Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                                        EtDocs_statement.clearBindings();
                                                        Notifications_Create_Attachments_Fragment attachment_fragment =
                                                                (Notifications_Create_Attachments_Fragment) getSupportFragmentManager()
                                                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 4));
                                                        List<Notif_EtDocs_Parcelable> attachment_list =
                                                                attachment_fragment.getAttachmentsData();
                                                        if (attachment_list.size() > 0) {
                                                            App_db.beginTransaction();
                                                            for (int i = 0; i < attachment_list.size(); i++) {
                                                                EtDocs_statement.bindString(1, uniqueKey.toString());
                                                                EtDocs_statement.bindString(2, "NOT_" + timeStamp);
                                                                EtDocs_statement.bindString(3, attachment_list.get(i).getZdoctype());
                                                                EtDocs_statement.bindString(4, attachment_list.get(i).getZdoctypeitem());
                                                                EtDocs_statement.bindString(5, attachment_list.get(i).getFilename());
                                                                EtDocs_statement.bindString(6, attachment_list.get(i).getFiletype());
                                                                EtDocs_statement.bindString(7, attachment_list.get(i).getFsize());
                                                                EtDocs_statement.bindString(8, "");
                                                                EtDocs_statement.bindString(9, attachment_list.get(i).getDocid());
                                                                EtDocs_statement.bindString(10, attachment_list.get(i).getDoctype());
                                                                EtDocs_statement.bindString(11, attachment_list.get(i).getObjtype());
                                                                EtDocs_statement.bindString(12, attachment_list.get(i).getFilepath());
                                                                EtDocs_statement.bindString(13, "New");
                                                                EtDocs_statement.execute();
                                                            }
                                                            App_db.setTransactionSuccessful();
                                                            App_db.endTransaction();
                                                        }
                                                    } catch (Exception e) {
                                                    }

                                                    try {
                                                        DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                                        DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                                        Date todaysdate = new Date();
                                                        String date = date_format.format(todaysdate.getTime());
                                                        String time = time_format.format(todaysdate.getTime());

                                                        String sql11 = "Insert into Alert_Log (DATE, TIME," +
                                                                " DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID," +
                                                                " STATUS, UUID, MESSAGE, LOG_UUID,OBJECT_TXT) values(?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                                        App_db.beginTransaction();
                                                        statement11.clearBindings();
                                                        statement11.bindString(1, date);
                                                        statement11.bindString(2, time);
                                                        statement11.bindString(3, "Notification");
                                                        statement11.bindString(4, "Create");
                                                        statement11.bindString(5, "");
                                                        statement11.bindString(6, "NOT_" + timeStamp);
                                                        statement11.bindString(7, "Fail");
                                                        statement11.bindString(8, uniqueKey.toString());
                                                        statement11.bindString(9, "");
                                                        statement11.bindString(10, uniqueKey.toString());
                                                        statement11.bindString(11, notif_text);
                                                        statement11.execute();
                                                        App_db.setTransactionSuccessful();
                                                        App_db.endTransaction();
                                                    } catch (Exception e) {
                                                    }

                                                    custom_progress_dialog.dismiss_progress_dialog();
                                                    decision_dialog.dismiss();
                                                    Notifications_Create_Activity.this.finish();
                                                }
                                            });
                                            cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    decision_dialog.dismiss();
                                                }
                                            });
                                            connect_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    decision_dialog.dismiss();
                                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                                    intent.setClassName("com.android.settings",
                                                            "com.android.settings.wifi.WifiSettings");
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    } else {
                                        error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                                getString(R.string.notifcreate_reqstrtdtmandate));
                                    }
                                } else {
                                    error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                            getString(R.string.plndGrp_mandate));
                                }
                            } else {
                                error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                        getString(R.string.priority_mandate));
                            }
                        } else {
                            error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                    getString(R.string.wrkCntr_mandate));
                        }
                    } else {
                        error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                getResources().getString(R.string.equipFunc_mandate));
                    }
                } else {
                    error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                            getString(R.string.notification_shrttxtmandate));
                }
            } else {
                error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                        getString(R.string.notification_typemandate));
            }
        }
    }

    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_Create_Activity.this,
                    getResources().getString(R.string.create_notif_inprogress));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(Notifications_Create_Activity.this);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (token_status.equalsIgnoreCase("success")) {
                custom_progress_dialog.dismiss_progress_dialog();
                new Post_Create_Notification().execute("");
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                        getString(R.string.notification_unabletocreate));
            }
        }
    }

    /*Posting Notification Create to Backend Server*/
    private class Post_Create_Notification extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_Create_Activity.this,
                    getResources().getString(R.string.create_notif_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String transmit_type = params[0];
                notif_create_status = Notifications_Create
                        .Post_NotifCreate_Data(Notifications_Create_Activity.this,
                                transmit_type, notification_type_id, notif_text, functionlocation_id,
                                equipment_id, equipment_text, priority_type_id, priority_type_text,
                                plannergroup_id, plannergroup_text, Reported_by, personresponsible_id,
                                personresponsible_text, req_st_date, req_st_time, req_end_date,
                                req_end_time, mal_st_date, mal_st_time, mal_end_date, mal_end_time,
                                effect_id, effect_text, plant_id, workcenter_id, primary_user_resp,
                                causecodeArrayList, ActivityArrayList, AttachmentsArrayList,
                                LongtextsArrayList, TasksArrayList, header_custominfo);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (notif_create_status.get("response_status") != null &&
                    !notif_create_status.get("response_status").equals("")) {
                if (notif_create_status.get("response_status").equalsIgnoreCase("Duplicate")) {
                    String duplicate_data = notif_create_status.get("response_data");
                    if (duplicate_data != null && !duplicate_data.equals("")) {
                        try {
                            notification_duplicate_list.clear();
                            JSONArray jsonArray = new JSONArray(duplicate_data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String Priok = jsonArray.getJSONObject(i).optString("Priok");
                                String Priok_text = "";
                                if (Priok != null && !Priok.equals("")) {
                                    try {
                                        Cursor cursor = App_db.rawQuery("select *" +
                                                        " from GET_NOTIFICATION_PRIORITY where Priok = ?",
                                                new String[]{Priok});
                                        if (cursor != null && cursor.getCount() > 0) {
                                            if (cursor.moveToFirst()) {
                                                do {
                                                    Priok_text = Priok + " - " + cursor.getString(2);
                                                }
                                                while (cursor.moveToNext());
                                            }
                                        } else {
                                            cursor.close();
                                            Priok_text = "";
                                        }
                                    } catch (Exception e) {
                                        Priok_text = "";
                                    }
                                } else {
                                    Priok_text = "";
                                }
                                Notif_Dup_List_Object olo = new Notif_Dup_List_Object(Priok_text,
                                        jsonArray.getJSONObject(i).optString("Qmnum"),
                                        jsonArray.getJSONObject(i).optString("Qmtxt"));
                                notification_duplicate_list.add(olo);
                            }
                            if (notification_duplicate_list.size() > 0) {
                                Collections.sort(notification_duplicate_list, new Comparator<Notif_Dup_List_Object>() {
                                    public int compare(Notif_Dup_List_Object o1, Notif_Dup_List_Object o2) {
                                        return o2.getQMNUM().compareTo(o1.getQMNUM());
                                    }
                                });
                            }
                            custom_progress_dialog.dismiss_progress_dialog();
                            final Dialog aa = new Dialog(Notifications_Create_Activity.this,
                                    R.style.AppThemeDialog_Dark);
                            aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            aa.setCancelable(false);
                            aa.setCanceledOnTouchOutside(false);
                            aa.setContentView(R.layout.notifications_duplicate_dialog);
                            Button yes_button = (Button) aa.findViewById(R.id.yes_button);
                            Button no_button = (Button) aa.findViewById(R.id.no_button);
                            TextView title_textView = (TextView) aa.findViewById(R.id.title_textview);
                            RecyclerView recyclerview = (RecyclerView) aa.findViewById(R.id.recyclerview);
                            notification_duplicate_adapter =
                                    new Notification_Duplicate_Adapter(Notifications_Create_Activity.this,
                                            notification_duplicate_list);
                            recyclerview.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(Notifications_Create_Activity.this);
                            recyclerview.setLayoutManager(layoutManager);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());
                            recyclerview.setAdapter(notification_duplicate_adapter);
                            title_textView.setText(getString(R.string.dup_notif)
                                    + " (" + notification_duplicate_list.size() + ")");
                            aa.show();
                            no_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    aa.dismiss();
                                }
                            });
                            yes_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    aa.dismiss();
                                    new Post_Create_Notification().execute("FUNC");
                                }
                            });
                        } catch (Exception e) {
                        }
                    } else {
                        custom_progress_dialog.dismiss_progress_dialog();
                        error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                                getString(R.string.notification_dupnotfound));
                    }
                } else if (notif_create_status.get("response_status").equalsIgnoreCase("success")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    final Dialog success_dialog = new Dialog(Notifications_Create_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText(getString(R.string.notification_createsuccess,
                            notif_create_status.get("response_data")));
                    Glide.with(Notifications_Create_Activity.this)
                            .load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            success_dialog.dismiss();
                            Notifications_Create_Activity.this.finish();
                        }
                    });
                } else if (notif_create_status.get("response_status").startsWith("E")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                            notif_create_status.get("response_status").substring(1));
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                            getString(R.string.notification_unabletocreate));
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_Create_Activity.this,
                        getString(R.string.notification_unabletocreate));
            }
        }
    }
    /*Posting Notification Create to Backend Server*/

    /*RecyclerView Adapter Duplicate Notification Create*/
    public class Notification_Duplicate_Adapter extends
            RecyclerView.Adapter<Notification_Duplicate_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Notif_Dup_List_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView notif_no_textview, notif_text_textview, priority_textview;
            public MyViewHolder(View view) {
                super(view);
                notif_no_textview = (TextView) view.findViewById(R.id.notif_no_textview);
                notif_text_textview = (TextView) view.findViewById(R.id.notif_text_textview);
                priority_textview = (TextView) view.findViewById(R.id.priority_textview);
            }
        }

        public Notification_Duplicate_Adapter(Context mContext, List<Notif_Dup_List_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_duplicate_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Notif_Dup_List_Object olo = list_data.get(position);
            holder.notif_no_textview.setText(olo.getQMNUM());
            holder.notif_text_textview.setText(olo.getQMTXT());
            holder.priority_textview.setText(olo.getPRIOK());
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }
    /*RecyclerView Adapter Duplicate Notification Create*/

    /*Objects for  Duplicate Notification Create*/
    public class Notif_Dup_List_Object {
        private String PRIOK;
        private String QMNUM;
        private String QMTXT;

        public String getPRIOK() {
            return PRIOK;
        }

        public void setPRIOK(String PRIOK) {
            this.PRIOK = PRIOK;
        }

        public String getQMNUM() {
            return QMNUM;
        }

        public void setQMNUM(String QMNUM) {
            this.QMNUM = QMNUM;
        }

        public String getQMTXT() {
            return QMTXT;
        }

        public void setQMTXT(String QMTXT) {
            this.QMTXT = QMTXT;
        }

        public Notif_Dup_List_Object(String PRIOK, String QMNUM, String QMTXT) {
            this.PRIOK = PRIOK;
            this.QMNUM = QMNUM;
            this.QMTXT = QMTXT;
        }
    }
    /*Objects for  Duplicate Notification Create*/

    protected void animateFab(final boolean selected) {
        fab.clearAnimation();
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon
                if (selected) {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    fab.setImageResource(R.drawable.ic_delete_icon);
                } else {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    fab.setImageResource(R.drawable.ic_add_white_24px);
                }
                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        fab.startAnimation(shrink);
    }

    public void updateTabDataCount() {
        Notifications_Create_Causecode_Fragment causecode_fragment =
                (Notifications_Create_Causecode_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
        if (causecode_fragment.causeSize() > 0) {
            tablayout.getTabAt(1).setText(getString(R.string.causecode_p, causecode_fragment.causeSize()));
        } else {
            tablayout.getTabAt(1).setText(getResources().getString(R.string.cause_code1));
        }

        Notifications_Create_Activity_Fragment activity_fragment =
                (Notifications_Create_Activity_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
        if (activity_fragment.activitySize() > 0) {
            tablayout.getTabAt(2).setText(getString(R.string.activity_p, activity_fragment.activitySize()));
        } else {
            tablayout.getTabAt(2).setText(getResources().getString(R.string.activity));
        }

        Notifications_Create_Task_Fragment task_fragment =
                (Notifications_Create_Task_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 3));
        if (task_fragment.taskSize() > 0) {
            tablayout.getTabAt(3).setText(getString(R.string.task_p, task_fragment.taskSize()));
        } else {
            tablayout.getTabAt(3).setText(getResources().getString(R.string.task));
        }

        Notifications_Create_Attachments_Fragment attachment_fragment =
                (Notifications_Create_Attachments_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 4));
        if (attachment_fragment.AttachmentSize() > 0) {
            tablayout.getTabAt(4).setText(getString(R.string.attachment_p, attachment_fragment.AttachmentSize()));
        } else {
            tablayout.getTabAt(4).setText(getResources().getString(R.string.attachments));
        }
        setCustomFont();
    }
}
