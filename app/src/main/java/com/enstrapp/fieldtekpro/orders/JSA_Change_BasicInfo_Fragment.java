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

import com.enstrapp.fieldtekpro.R;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_BasicInfo_Fragment extends Fragment {

    EditText order_num_edittext, remarks_edittext, title_edittext, job_edittext;
    String rasid = "";
    LinearLayout title_layout, remark_layout, job_layout;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ImageView job_imageview;

    public JSA_Change_BasicInfo_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_basicinfo_fragment, container, false);

        order_num_edittext = (EditText) rootView.findViewById(R.id.order_num_edittext);
        remarks_edittext = (EditText) rootView.findViewById(R.id.remarks_edittext);
        title_edittext = (EditText) rootView.findViewById(R.id.title_edittext);
        job_edittext = (EditText) rootView.findViewById(R.id.job_edittext);
        title_layout = (LinearLayout) rootView.findViewById(R.id.title_layout);
        remark_layout = (LinearLayout) rootView.findViewById(R.id.remark_layout);
        job_layout = (LinearLayout) rootView.findViewById(R.id.job_layout);
        job_imageview = (ImageView) rootView.findViewById(R.id.job_imageview);

        job_imageview.setVisibility(View.GONE);
        title_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        title_edittext.setEnabled(false);
        title_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));
        remark_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        remarks_edittext.setEnabled(false);
        remarks_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));
        job_layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        job_edittext.setEnabled(false);
        job_edittext.setTextColor(getResources().getColor(R.color.dark_grey2));

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            rasid = bundle.getString("rasid");
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSAHdr where Rasid =" +
                        " ?", new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            title_edittext.setText(cursor.getString(6));
                            remarks_edittext.setText(cursor.getString(10));
                            job_edittext.setText(cursor.getString(7) + " - "
                                    + cursor.getString(15));
                            order_num_edittext.setText(cursor.getString(2));
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
