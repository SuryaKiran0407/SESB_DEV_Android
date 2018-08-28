package com.enstrapp.fieldtekpro.MIS;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

public class PermitReportMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewpager;
    PermitTabAdapter tabAdapter;
    ImageButton back_ib;
    ImageView iv_filter;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_permitrep_analysis);

//        toolbar = findViewById(R.id.toolbar);
        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab);
        back_ib = findViewById(R.id.back_ib);
        iv_filter= (ImageView)findViewById(R.id.iv_filter);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        tabAdapter = new PermitTabAdapter(this, getSupportFragmentManager());
        viewpager.setAdapter(tabAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                PermitStatusPieFragment fag1 = (PermitStatusPieFragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                String month_year = fag1.getMonth_year();                                           //Month&Year for Compliance Chart
                WorkApprovalStatusPieFragment fag2 = (WorkApprovalStatusPieFragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                if (position == 1) {

                    fag2.getData();                                                                 //when tab swiped to get the data for bar chart
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewpager);

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setCustomFont(tabLayout);
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

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
