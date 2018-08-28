package com.enstrapp.fieldtekpro.MIS;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class OrderTabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String tabTitles[] = new String[]{"Analysis", "Compliance"};

    public OrderTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OrderPieFragment();
        } else {
            return new OrderComplianceBarFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return tabTitles[0];
        } else {
            return tabTitles[1];
        }
    }
}
