package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro_sesb_dev.R;

public class JSA_BasicInfo_Fragment extends Fragment implements View.OnClickListener {

    EditText order_num_edittext, remarks_edittext, title_edittext, job_edittext;
    ImageView job_imageview;
    int job_type = 0;
    String job_type_id = "", job_type_text = "";

    public JSA_BasicInfo_Fragment() {
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
        job_imageview = (ImageView) rootView.findViewById(R.id.job_imageview);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            order_num_edittext.setText(bundle.getString("aufnr"));
        }

        job_imageview.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == job_imageview) {
            Intent intent = new Intent(getActivity(), JSA_JobType_Activity.class);
            intent.putExtra("request_id", Integer.toString(job_type));
            startActivityForResult(intent, job_type);
        }
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
        JSA_Add_Activity jsa_add_activity = (JSA_Add_Activity) this.getActivity();
        jsa_add_activity.add_fab.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == job_type) {
                job_type_id = data.getStringExtra("job_type_id");
                job_type_text = data.getStringExtra("job_type_text");
                job_edittext.setText(job_type_id + " - " + job_type_text);
            }
        }
    }

    public JSA_BasicInfo_Object getData() {
        return new JSA_BasicInfo_Object(title_edittext.getText().toString(),
                remarks_edittext.getText().toString(), job_type_id, job_type_text,
                order_num_edittext.getText().toString());
    }
}
