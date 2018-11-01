package com.enstrapp.fieldtekpro.PermitList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.ViewPagerAdapter;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Isolation_List_Activity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Isolation_List_TagTagged_Fragment chatFragment;
    Isolation_List_UnTagUnTagged_Fragment callsFragment;
    private Toolbar toolbar;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    private List bom_list, stock_list;
    String[] tabTitle = {getString(R.string.tag_tagged), getString(R.string.untag_untagged)};
    int[] unreadCount = {0, 0};
    TextView title_textview;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    ImageView filter_imageview;
    Button filter_func_loc_button, filter_maint_plant_button;
    int functionlocation_type = 1, maintplant_type = 2;
    String filter_functionlocation_id = "", filter_plant_id = "";
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isolationlist_activity);

        custom_progress_dialog.show_progress_dialog(Isolation_List_Activity.this, getResources().getString(R.string.loading));

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Isolation_List_Activity.this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        title_textview = (TextView) findViewById(R.id.title_textview);
        title_textview.setText(getResources().getString(R.string.isolation_list));
        filter_imageview = (ImageView) findViewById(R.id.filter_imageview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(null);
        toolbar.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        toolbar.setContentInsetsAbsolute(0, 0);
        ImageView home_imageview = (ImageView) toolbar.findViewById(R.id.home_imageview);
        home_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Isolation_List_Activity.this.finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);

        bom_list = new ArrayList<>();
        try {
            String Usgrp = "";
            Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_USER_DATA", null);
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        Usgrp = cursor1.getString(15);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
            }
            if (Usgrp != null && !Usgrp.equals("")) {
                ArrayList<String> list = new ArrayList<String>();
                list.clear();
                Cursor cursor2 = FieldTekPro_db.rawQuery("select * from EtUsgrpWccp where Usgrp = ?", new String[]{Usgrp});
                if (cursor2 != null && cursor2.getCount() > 0) {
                    if (cursor2.moveToFirst()) {
                        do {
                            String Pmsog = cursor2.getString(2);
                            list.add(Pmsog);
                        }
                        while (cursor2.moveToNext());
                    }
                } else {
                    cursor2.close();
                }

                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmWcagns where Objart = ? and Geniakt = ?", new String[]{"WD", "X"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String EtWcmWcagns_Pmsog = cursor.getString(7);
                            if (list.contains(EtWcmWcagns_Pmsog)) {
                                String date = cursor.getString(26);
                                String date_format = "";
                                if (date != null && !date.equals("")) {
                                    if (date.equalsIgnoreCase("00000000")) {
                                        date_format = "";
                                    } else {
                                        String Date_format = "";
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Date_format = outputDateStr;
                                        } catch (Exception e) {
                                            Date_format = "";
                                        }
                                        String time = cursor.getString(27);
                                        String time_formatted = "";
                                        if (time != null && !time.equals("")) {
                                            DateFormat inputFormat1 = new SimpleDateFormat("HHmmss");
                                            DateFormat outputFormat1 = new SimpleDateFormat("HH:mm:ss");
                                            Date date2;
                                            try {
                                                date2 = inputFormat1.parse(date);
                                                String outputDateStr = outputFormat1.format(date2);
                                                time_formatted = outputDateStr;
                                            } catch (Exception e) {
                                                time_formatted = "";
                                            }
                                        }
                                        date_format = Date_format + "  " + time_formatted;
                                    }
                                } else {
                                    date_format = "";
                                }
                                boolean Geniakt_status = false;
                                String Geniakt = cursor.getString(9);
                                if (Geniakt.equalsIgnoreCase("X")) {
                                    Geniakt_status = false;
                                } else {
                                    Geniakt_status = true;
                                }
                                String permit_number = "";
                                String permit_text = "";
                                String auth_grp = "";
                                String permit_type = "";
                                String ref_obj = "";
                                String objnr = cursor.getString(3);
                                String wcnr = "";
                                if (objnr != null && !objnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdData where Objnr = ?", new String[]{objnr});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    wcnr = cursor3.getString(3);
                                                    permit_text = cursor3.getString(14);
                                                    permit_type = cursor3.getString(6);
                                                    auth_grp = cursor3.getString(30) + " - " + cursor3.getString(31);
                                                    ref_obj = cursor3.getString(24);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                String tagged = "";
                                String untagged = "";
                                if (objnr != null && !objnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdItemData where Wcnr = ? and (Btg = ? or Etg = ? or Btg = ? or Etg = ?) and (Bug = ? and Eug = ?)", new String[]{wcnr, "X", "X", "", "", "", ""});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    if (cursor3.getString(34).equals("X")) {
                                                        tagged = "TAGGED";
                                                    } else if (cursor3.getString(35).equals("X")) {
                                                        tagged = "TAGGED";
                                                    } else {
                                                        tagged = "TAGGED";
                                                    }
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                if (ref_obj != null && !ref_obj.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWaData where Objnr = ?", new String[]{ref_obj});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    permit_number = cursor3.getString(4);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                String floc_number = "";
                                String aufnr = cursor.getString(2);
                                if (aufnr != null && !aufnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{aufnr});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    floc_number = cursor3.getString(10);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                if (tagged != null && !tagged.equals("")) {
                                    bom_list.add(cursor.getString(2));
                                }
                            } else {
                            }
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            }
        } catch (Exception e) {
        }
        unreadCount[0] = bom_list.size();


        stock_list = new ArrayList<>();
        try {
            String Usgrp = "";
            Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_USER_DATA", null);
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        Usgrp = cursor1.getString(15);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
            }
            if (Usgrp != null && !Usgrp.equals("")) {
                ArrayList<String> list = new ArrayList<String>();
                list.clear();
                Cursor cursor2 = FieldTekPro_db.rawQuery("select * from EtUsgrpWccp where Usgrp = ?", new String[]{Usgrp});
                if (cursor2 != null && cursor2.getCount() > 0) {
                    if (cursor2.moveToFirst()) {
                        do {
                            String Pmsog = cursor2.getString(2);
                            list.add(Pmsog);
                        }
                        while (cursor2.moveToNext());
                    }
                } else {
                    cursor2.close();
                }

                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmWcagns where Objart = ? and Geniakt = ?", new String[]{"WD", "X"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String EtWcmWcagns_Pmsog = cursor.getString(7);
                            if (list.contains(EtWcmWcagns_Pmsog)) {
                                String date = cursor.getString(26);
                                String date_format = "";
                                if (date != null && !date.equals("")) {
                                    if (date.equalsIgnoreCase("00000000")) {
                                        date_format = "";
                                    } else {
                                        String Date_format = "";
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Date_format = outputDateStr;
                                        } catch (Exception e) {
                                            Date_format = "";
                                        }
                                        String time = cursor.getString(27);
                                        String time_formatted = "";
                                        if (time != null && !time.equals("")) {
                                            DateFormat inputFormat1 = new SimpleDateFormat("HHmmss");
                                            DateFormat outputFormat1 = new SimpleDateFormat("HH:mm:ss");
                                            Date date2;
                                            try {
                                                date2 = inputFormat1.parse(date);
                                                String outputDateStr = outputFormat1.format(date2);
                                                time_formatted = outputDateStr;
                                            } catch (Exception e) {
                                                time_formatted = "";
                                            }
                                        }
                                        date_format = Date_format + "  " + time_formatted;
                                    }
                                } else {
                                    date_format = "";
                                }
                                boolean Geniakt_status = false;
                                String Geniakt = cursor.getString(9);
                                if (Geniakt.equalsIgnoreCase("X")) {
                                    Geniakt_status = false;
                                } else {
                                    Geniakt_status = true;
                                }
                                String permit_number = "";
                                String permit_text = "";
                                String auth_grp = "";
                                String permit_type = "";
                                String ref_obj = "";
                                String objnr = cursor.getString(3);
                                String wcnr = "";
                                if (objnr != null && !objnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdData where Objnr = ?", new String[]{objnr});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    wcnr = cursor3.getString(3);
                                                    permit_text = cursor3.getString(14);
                                                    permit_type = cursor3.getString(6);
                                                    auth_grp = cursor3.getString(30) + " - " + cursor3.getString(31);
                                                    ref_obj = cursor3.getString(24);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                String tagged = "";
                                String untagged = "";
                                if (objnr != null && !objnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdItemData where Wcnr = ? and (Bug = ? or Eug = ?)", new String[]{wcnr, "X", "X"});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    if (cursor3.getString(36).equals("X")) {
                                                        untagged = "UNTAGGED";
                                                    } else if (cursor3.getString(37).equals("X")) {
                                                        untagged = "UNTAGGED";
                                                    } else {
                                                        untagged = "UNTAGGED";
                                                    }
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                if (ref_obj != null && !ref_obj.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWaData where Objnr = ?", new String[]{ref_obj});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    permit_number = cursor3.getString(4);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                String floc_number = "";
                                String aufnr = cursor.getString(2);
                                if (aufnr != null && !aufnr.equals("")) {
                                    try {
                                        Cursor cursor3 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{aufnr});
                                        if (cursor3 != null && cursor3.getCount() > 0) {
                                            if (cursor3.moveToFirst()) {
                                                do {
                                                    floc_number = cursor3.getString(10);
                                                }
                                                while (cursor3.moveToNext());
                                            }
                                        } else {
                                            cursor3.close();
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                if (untagged != null && !untagged.equals("")) {
                                    stock_list.add(cursor.getString(2));
                                }
                            } else {
                            }
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            }
        } catch (Exception e) {
        }
        unreadCount[1] = stock_list.size();

        try {
            setupTabIcons();
        } catch (Exception e) {
        }


        custom_progress_dialog.dismiss_progress_dialog();

        filter_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == filter_imageview) {
            final Dialog dialog = new Dialog(Isolation_List_Activity.this, R.style.AppThemeDialog_Dark);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.isolation_list_filter_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            filter_func_loc_button = (Button) dialog.findViewById(R.id.func_loc_button);
            filter_func_loc_button.setText(filter_functionlocation_id);
            filter_maint_plant_button = (Button) dialog.findViewById(R.id.maint_plant_button);
            filter_maint_plant_button.setText(filter_plant_id);
            TextView clearAll_textview = (TextView) dialog.findViewById(R.id.clearAll_textview);
            Button closeBt = (Button) dialog.findViewById(R.id.closeBt);
            Button filterBt = (Button) dialog.findViewById(R.id.filterBt);
            closeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            filter_func_loc_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent notification_type_intent = new Intent(Isolation_List_Activity.this, FunctionLocation_Activity.class);
                    notification_type_intent.putExtra("request_id", Integer.toString(functionlocation_type));
                    startActivityForResult(notification_type_intent, functionlocation_type);
                }
            });
            filter_maint_plant_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent notification_type_intent = new Intent(Isolation_List_Activity.this, Isolation_Filter_Plant_Activity.class);
                    notification_type_intent.putExtra("request_id", Integer.toString(maintplant_type));
                    startActivityForResult(notification_type_intent, maintplant_type);
                }
            });
            filterBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (filter_functionlocation_id != null && !filter_functionlocation_id.equals("") || filter_plant_id != null && !filter_plant_id.equals("")) {
                        Isolation_List_TagTagged_Fragment comP3 = (Isolation_List_TagTagged_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                        comP3.refresh_tag_tagged_data(filter_functionlocation_id, filter_plant_id);
                        Isolation_List_UnTagUnTagged_Fragment comP4 = (Isolation_List_UnTagUnTagged_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
                        comP4.refresh_untag_untagged_data(filter_functionlocation_id, filter_plant_id);
                        dialog.dismiss();
                    } else {
                        error_dialog.show_error_dialog(Isolation_List_Activity.this,
                                getString(R.string.funcloc_plant));
                    }
                }
            });
            clearAll_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filter_functionlocation_id = "";
                    filter_plant_id = "";
                    filter_func_loc_button.setText("");
                    filter_maint_plant_button.setText("");
                    dialog.dismiss();
                    Isolation_List_TagTagged_Fragment comP1 = (Isolation_List_TagTagged_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                    comP1.refresh_tag_tagged_data("", "");
                    Isolation_List_UnTagUnTagged_Fragment comP2 = (Isolation_List_UnTagUnTagged_Fragment) viewPager.getAdapter().instantiateItem(viewPager, 1);
                    comP2.refresh_untag_untagged_data("", "");
                }
            });
            dialog.show();
        }
    }


    public void refreshMyData(int size1) {
        tabLayout.setupWithViewPager(viewPager);
        View view = LayoutInflater.from(Isolation_List_Activity.this).inflate(R.layout.custom_tab1, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView badgeTv = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText("Tag/Tagged");
        badgeTv.setText("(" + size1 + ")");
        tabLayout.getTabAt(0).setCustomView(view);
    }


    public void refreshMyData1(int size1) {
        tabLayout.setupWithViewPager(viewPager);
        View view = LayoutInflater.from(Isolation_List_Activity.this).inflate(R.layout.custom_tab1, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView badgeTv = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText("Untag/Untagged");
        badgeTv.setText("(" + size1 + ")");
        tabLayout.getTabAt(1).setCustomView(view);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == functionlocation_type) {
                filter_functionlocation_id = data.getStringExtra("functionlocation_id");
                filter_plant_id = data.getStringExtra("plant_id");
                filter_func_loc_button.setText(filter_functionlocation_id);
                filter_maint_plant_button.setText(filter_plant_id);
            } else if (requestCode == maintplant_type) {
                filter_plant_id = data.getStringExtra("plant_id");
                filter_maint_plant_button.setText(filter_plant_id);
            }
        }
    }


    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        chatFragment = new Isolation_List_TagTagged_Fragment();
        callsFragment = new Isolation_List_UnTagUnTagged_Fragment();
        adapter.addFragment(chatFragment, getString(R.string.tag_tagged));
        adapter.addFragment(callsFragment, getString(R.string.untag_untagged));
        viewPager.setAdapter(adapter);
    }


    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab1, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        tv_count.setVisibility(View.GONE);
        /*if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("("+unreadCount[pos]+")");
        }
        else
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("(0)");
        }*/
        return view;
    }


    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }


}
