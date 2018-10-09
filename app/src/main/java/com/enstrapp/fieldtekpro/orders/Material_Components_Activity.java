package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Material_Components_Activity extends AppCompatActivity {

    TabLayout matrl_tl;
    ViewPager matrl_vp;
    String equip = "", iwerk = "";
    ImageView back_iv;
    TextView title_tv;
    String[] tabTitle = {"BOM", "General"};
    int[] unreadCount = {0, 0};
    private List bom_list, general_list;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Material_General_Fragment chatFragment;
    Material_BOM_Fragment callsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_activity);

        back_iv = findViewById(R.id.back_iv);
        title_tv = findViewById(R.id.title_tv);
        title_tv.setText(getResources().getString(R.string.material));

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            equip = bundle.getString("equipment");
            iwerk = bundle.getString("iwerk");
        }


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Material_Components_Activity.this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);


        bom_list = new ArrayList<>();
        general_list = new ArrayList<>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomItem where Bom =?", new String[]{equip});
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


        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA where Werks = ?", new String[]{iwerk});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        general_list.add(cursor.getString(1));
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
                try {
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA", null);
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                general_list.add(cursor1.getString(1));
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        unreadCount[1] = general_list.size();

        matrl_vp = findViewById(R.id.viewPager);
        matrl_vp.setOffscreenPageLimit(2);

        matrl_tl = findViewById(R.id.tabLayout);
        matrl_tl.setupWithViewPager(matrl_vp);

        setupViewPager(matrl_vp);

        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
        }

        matrl_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setCustomFont(matrl_tl);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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


    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        callsFragment = new Material_BOM_Fragment();
        chatFragment = new Material_General_Fragment();
        adapter.addFragment(callsFragment, "BOM");
        adapter.addFragment(chatFragment, "General");
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
            matrl_tl.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }



    public void refreshMyData(int size)
    {
        View view1 = LayoutInflater.from(Material_Components_Activity.this).inflate(R.layout.custom_tab, null);
        TextView tv_title1 = (TextView) view1.findViewById(R.id.tv_title);
        TextView badgeTv1 = (TextView) view1.findViewById(R.id.tv_count);
        tv_title1.setText("General");
        badgeTv1.setText("("+size+")");
        matrl_tl.getTabAt(1).setCustomView(null);
        matrl_tl.getTabAt(1).setCustomView(view1);
    }


    private static String makeFragmentName(int viewPagerId, int index)
    {
        return "android:switcher:" + viewPagerId + ":" + index;
    }



}