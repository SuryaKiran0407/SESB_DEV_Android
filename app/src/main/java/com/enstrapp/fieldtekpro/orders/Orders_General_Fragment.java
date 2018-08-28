package com.enstrapp.fieldtekpro.orders;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enstrapp.fieldtekpro.R;

public class Orders_General_Fragment extends Fragment{

    TextInputEditText edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.order_general_fragment, container,
                false);
        /*edit = rootView.findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return rootView;
    }
}
