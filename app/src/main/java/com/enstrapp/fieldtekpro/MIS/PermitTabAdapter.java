package com.enstrapp.fieldtekpro.MIS;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PermitTabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String tabTitles[] = new String[]{"Permit Status", "Approval Status"};

    public PermitTabAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PermitStatusPieFragment();
        } else {
            return new WorkApprovalStatusPieFragment();
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
