package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.ViewPagerAdapter;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WorkRequirements_Activity extends AppCompatActivity {

    ImageButton back_iv;
    ArrayList<OrdrWaChkReqPrcbl> owrp_al = new ArrayList<>();
    ArrayList<OrdrWaChkReqPrcbl> work_al = new ArrayList<>();
    ArrayList<OrdrWaChkReqPrcbl> requ_al = new ArrayList<>();
    LinearLayout footer;
    Button cancel_bt, save_bt;
    String iwerk = "", usageId = "", prep = "", woco = "";
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView title_tv;
    ViewPagerAdapter viewPagerAdapter;
    Error_Dialog errorDialog = new Error_Dialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity);

        back_iv = findViewById(R.id.back_iv);
        footer = findViewById(R.id.footer);
        cancel_bt = findViewById(R.id.cancel_bt);
        save_bt = findViewById(R.id.save_bt);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        title_tv = findViewById(R.id.title_tv);

        title_tv.setText(R.string.work_req_slno);

        footer.setVisibility(View.VISIBLE);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = WorkRequirements_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            owrp_al = extras.getParcelableArrayList("owrp_al");
            iwerk = extras.getString("iwerk");
            usageId = extras.getString("usage_id");
            prep = extras.getString("prep");
            woco = extras.getString("woco");
            if (woco != null)
                if (woco.equals("X"))
                    footer.setVisibility(GONE);
                else
                    footer.setVisibility(VISIBLE);
        }

        setCustomFont(tabLayout);

        if (owrp_al != null) {
            if (owrp_al.size() > 0) {
                work_al.clear();
                requ_al.clear();
                for (OrdrWaChkReqPrcbl workReq : owrp_al) {
                    if (workReq.getWkid() != null && !workReq.getWkid().equals("")) {
                        if (workReq.getValue().equals("Y"))
                            workReq.setYes_checked(true);
                        else if (workReq.getValue().equals("N"))
                            workReq.setNo_checked(true);
                        work_al.add(workReq);
                    } else {
                        if (workReq.getValue().equals("Y"))
                            workReq.setYes_checked(true);
                        else if (workReq.getValue().equals("N"))
                            workReq.setNo_checked(true);
                        requ_al.add(workReq);
                    }
                }
                viewPager.setOffscreenPageLimit(2);
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPagerAdapter.addFragment(new Work_Fragment(), getResources().getString(R.string.work));
                viewPagerAdapter.addFragment(new Requirements_Fragment(), getResources().getString(R.string.requirement));
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
            } else {
                new Get_Works().execute();
            }
        } else {
            new Get_Works().execute();
        }

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                WorkRequirements_Activity.this.finish();
            }
        });

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                WorkRequirements_Activity.this.finish();
            }
        });

        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean work = false;
                boolean req = false;
                for (OrdrWaChkReqPrcbl owcp : work_al) {
                    if (owcp.getValue().equals("Y"))
                        work = true;
                }
                for (OrdrWaChkReqPrcbl owcp : requ_al) {
                    if (owcp.getValue().equals("Y"))
                        req = true;
                }
                if (work) {
                    if (req) {
                        owrp_al.clear();
                        owrp_al.addAll(work_al);
                        owrp_al.addAll(requ_al);
                        Intent intent = new Intent();
                        intent.putExtra("owrp_al", owrp_al);
                        setResult(RESULT_OK, intent);
                        WorkRequirements_Activity.this.finish();
                    } else {
                        errorDialog.show_error_dialog(WorkRequirements_Activity.this, getString(R.string.requ_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(WorkRequirements_Activity.this, getString(R.string.work_mandate));
                }
                owrp_al.clear();

            }
        });

    }

    private class Get_Works extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(WorkRequirements_Activity.this, getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmWork where Iwerk = ? and Wapiuse = ? and Dpflg = ?", new String[]{iwerk, usageId, "X"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrWaChkReqPrcbl wo = new OrdrWaChkReqPrcbl();
                            wo.setWkid(cursor.getString(5));
                            wo.setDesctext(cursor.getString(6));
                            wo.setWkgrp(cursor.getString(7));
                            wo.setValue("");
                            wo.setChkPointType("W");
                            owrp_al.add(wo);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (owrp_al.size() > 0) {
                new Get_Requirements().execute();
            } else {
                new Get_Requirements().execute();
            }
        }
    }

    private class Get_Requirements extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWCMReqm where Iwerk = ? and Wapiuse = ? and Dpflg = ?", new String[]{iwerk, usageId, "X"});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrWaChkReqPrcbl ro = new OrdrWaChkReqPrcbl();
                            ro.setNeedid(cursor.getString(5));
                            ro.setDesctext(cursor.getString(6));
                            ro.setNeedgrp(cursor.getString(7));
                            ro.setValue("");
                            ro.setChkPointType("R");
                            ro.setYes_checked(false);
                            ro.setNo_checked(false);
                            owrp_al.add(ro);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (owrp_al.size() > 0) {
                SetViewPager();
            } else {
                SetViewPager();
            }
        }
    }

    private void SetViewPager() {
        work_al.clear();
        requ_al.clear();
        for (OrdrWaChkReqPrcbl workReq : owrp_al) {
            if (workReq.getWkid() != null && !workReq.getWkgrp().equals("")) {
                work_al.add(workReq);
            } else {
                requ_al.add(workReq);
            }
        }
        viewPager.setOffscreenPageLimit(2);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Work_Fragment(), getResources().getString(R.string.work));
        viewPagerAdapter.addFragment(new Requirements_Fragment(), getResources().getString(R.string.requirement));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setCustomFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }
}
