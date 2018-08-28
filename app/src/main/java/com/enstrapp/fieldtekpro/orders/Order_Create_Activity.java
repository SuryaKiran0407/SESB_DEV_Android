package com.enstrapp.fieldtekpro.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;

import com.enstrapp.fieldtekpro.R;

public class Order_Create_Activity extends AppCompatActivity {

    TabLayout order_tl;
    ViewPager order_vp;
    Orders_Tab_Adapter orders_ta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_create_change_activity);

        order_vp = findViewById(R.id.order_vp);
        order_tl = findViewById(R.id.order_tl);

        orders_ta = new Orders_Tab_Adapter(this, getSupportFragmentManager());
        orders_ta.addFragment(new Orders_General_Fragment(), "General");
        orders_ta.addFragment(new Orders_Operation_Fragment(), "Operations");
        orders_ta.addFragment(new Orders_Material_Fragment(), "Material");
        orders_ta.addFragment(new Orders_Permits_Fragment(), "Permits");
        order_vp.setAdapter(orders_ta);
        order_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                PieFragment fag1 = (PieFragment) getSupportFragmentManager()
//                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
//                String month_year = fag1.getMonth_year();
//                ComplianceBarFragment tab1 = (ComplianceBarFragment) getSupportFragmentManager()
//                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
//                if (position == 1) {
//                    tab1.setMonthYear(month_year);
//                    tab1.getData();
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        order_tl.setupWithViewPager(order_vp);
    }
}
