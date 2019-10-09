package com.enstrapp.fieldtekpro.orders;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_JobLocation_Fragment extends Fragment {

    EditText op_statu_edittext, loc_struct_edittext;
    ImageView op_statu_imageview, loc_struct_imageview;
    String rasid = "";
    LinearLayout op_status_layout, loc_struct_layout;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public JSA_Change_JobLocation_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_joblocation_fragment, container, false);


        op_statu_edittext = (EditText) rootView.findViewById(R.id.op_statu_edittext);
        op_statu_imageview = (ImageView) rootView.findViewById(R.id.op_statu_imageview);
        loc_struct_edittext = (EditText) rootView.findViewById(R.id.loc_struct_edittext);
        loc_struct_imageview = (ImageView) rootView.findViewById(R.id.loc_struct_imageview);
        op_status_layout = (LinearLayout) rootView.findViewById(R.id.op_status_layout);
        loc_struct_layout = (LinearLayout) rootView.findViewById(R.id.loc_struct_layout);

        op_statu_imageview.setVisibility(View.GONE);
        loc_struct_imageview.setVisibility(View.GONE);

        op_status_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        op_statu_edittext.setEnabled(false);
        op_statu_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));
        loc_struct_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        loc_struct_edittext.setEnabled(false);
        loc_struct_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            rasid = bundle.getString("rasid");
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSAHdr where Rasid = ?",
                        new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            op_statu_edittext.setText(cursor.getString(8) + " - "
                                    + cursor.getString(13));
                            loc_struct_edittext.setText(cursor.getString(9) + " - "
                                    + cursor.getString(14));
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint())
            return;
        JSA_Change_Activity jsa_change_activity = (JSA_Change_Activity) this.getActivity();
        jsa_change_activity.add_fab.hide();
    }
}
