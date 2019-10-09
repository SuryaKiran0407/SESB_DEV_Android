package com.enstrapp.fieldtekpro.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.util.ArrayList;
import java.util.List;

public class Utilities_Activity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Stock_List_Activity chatFragment;
    BOM_List_Activity callsFragment;
    private Toolbar toolbar;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    private List bom_list, stock_list;
    String[] tabTitle = {"BOM", "Stock"};
    int[] unreadCount = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utilities_activity);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Utilities_Activity.this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

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
                Utilities_Activity.this.finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);

        bom_list = new ArrayList<>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomHeader", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        bom_list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }
        unreadCount[0] = bom_list.size();

        stock_list = new ArrayList<>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        stock_list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }
        unreadCount[1] = stock_list.size();

        try {
            setupTabIcons();
        } catch (Exception e) {
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);
                int currentPage = position;
                if (currentPage == 0) {
                    BOM_List_Activity tab1 = (BOM_List_Activity) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                    if (tab1 != null) {
                        String data = tab1.getData();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void refreshMyData() {
        bom_list = new ArrayList<>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomHeader", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        bom_list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }

        stock_list = new ArrayList<>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        stock_list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }

        tabLayout.setupWithViewPager(viewPager);
        View view = LayoutInflater.from(Utilities_Activity.this).inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView badgeTv = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText("BOM");
        badgeTv.setText("(" + bom_list.size() + ")");
        tabLayout.getTabAt(0).setCustomView(view);

        View view1 = LayoutInflater.from(Utilities_Activity.this).inflate(R.layout.custom_tab, null);
        TextView tv_title1 = (TextView) view1.findViewById(R.id.tv_title);
        TextView badgeTv1 = (TextView) view1.findViewById(R.id.tv_count);
        tv_title1.setText("Stock");
        badgeTv1.setText("(" + stock_list.size() + ")");
        tabLayout.getTabAt(1).setCustomView(view1);
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment = new BOM_List_Activity();
        chatFragment = new Stock_List_Activity();
        adapter.addFragment(callsFragment, "BOM");
        adapter.addFragment(chatFragment, "Stock");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if (unreadCount[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("(" + unreadCount[pos] + ")");
        } else {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("(0)");
        }
        return view;
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }

}
