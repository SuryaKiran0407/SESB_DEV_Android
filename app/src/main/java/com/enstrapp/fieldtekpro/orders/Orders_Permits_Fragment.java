package com.enstrapp.fieldtekpro.orders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enstrapp.fieldtekpro_sesb_dev.R;

public class Orders_Permits_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.order_permit_fragment, container,
                false);
        return rootView;
    }
}
