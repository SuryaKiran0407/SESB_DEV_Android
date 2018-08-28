package com.enstrapp.fieldtekpro.notifications;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;

public class Notifications_Status_Activity extends AppCompatActivity implements View.OnClickListener
{

    TabLayout tablayout;
    ViewPager viewpager;
    Notifications_Tab_Adapter orders_ta;
    Button cancel_button, submit_button;
    ImageView back_iv;
    String qmnum = "", uuid= "", causecode_status = "", malfunc_enddate_status = "";
    NotifHeaderPrcbl nhp = new NotifHeaderPrcbl();
    ArrayList<Notif_Status_WithNum_Prcbl> status_withNum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
    ArrayList<Notif_Status_WithNum_Prcbl> status_withoutNum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
    ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_array = new ArrayList<Notif_Status_WithNum_Prcbl>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_orders_status_activity);

        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        submit_button = (Button)findViewById(R.id.save_button);
        back_iv = (ImageView)findViewById(R.id.back_iv);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            qmnum = extras.getString("qmnum");
            uuid = extras.getString("uuid");
            status_withNum_array =  extras.getParcelableArrayList("status_withNum_array");
            status_withoutNum_array =  extras.getParcelableArrayList("status_withoutNum_array");
            status_systemstatus_array =  extras.getParcelableArrayList("status_systemstatus_array");
            causecode_status = extras.getString("causecode_status");
            malfunc_enddate_status = extras.getString("malfunc_enddate_status");
        }

        orders_ta = new Notifications_Tab_Adapter(this, getSupportFragmentManager());
        orders_ta.addFragment(new Notifications_Status_System_Fragment(), getResources().getString(R.string.system_status));
        orders_ta.addFragment(new Notifications_Status_WithNum_Fragment(), getResources().getString(R.string.with_status_num));
        orders_ta.addFragment(new Notifications_Status_WithoutNum_Fragment(), getResources().getString(R.string.without_status_num));
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(orders_ta);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels)
            {
            }
            @Override
            public void onPageSelected(int position)
            {
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });

        tablayout.setupWithViewPager(viewpager);

        setCustomFont();

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_iv.setOnClickListener(this);
    }


    public void setCustomFont()
    {
        ViewGroup vg = (ViewGroup) tablayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++)
        {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++)
            {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView)
                {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }


    private static String makeFragmentName(int viewPagerId, int index)
    {
        return "android:switcher:" + viewPagerId + ":" + index;
    }


    @Override
    public void onClick(View v)
    {
        if (v == cancel_button)
        {
            Notifications_Status_Activity.this.finish();
        }
        else if (v == back_iv)
        {
            Notifications_Status_Activity.this.finish();
        }
        else if (v == submit_button)
        {
            Notifications_Status_WithNum_Fragment withnum_tab = (Notifications_Status_WithNum_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,1));
            ArrayList<Notif_Status_WithNum_Prcbl> withnum_list = withnum_tab.getstatus_withNumData();
            Notifications_Status_WithoutNum_Fragment withoutnum_tab = (Notifications_Status_WithoutNum_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,2));
            ArrayList<Notif_Status_WithNum_Prcbl> withoutnum_list = withoutnum_tab.getstatus_withoutNumData();
            Notifications_Status_System_Fragment systemstatus_tab = (Notifications_Status_System_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,0));
            ArrayList<Notif_Status_WithNum_Prcbl> systemstatus_list = systemstatus_tab.getstatus_systemstatusData();
            Intent intent = new Intent();
            intent.putExtra("status_withNum_array",withnum_list);
            intent.putExtra("status_withoutNum_array",withoutnum_list);
            intent.putExtra("status_systemstatus_array",systemstatus_list);
            setResult(RESULT_OK, intent);
            Notifications_Status_Activity.this.finish();
        }
    }


}
