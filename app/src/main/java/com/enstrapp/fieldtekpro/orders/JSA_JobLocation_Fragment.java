package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro.R;

public class JSA_JobLocation_Fragment extends Fragment implements View.OnClickListener {

    EditText op_statu_edittext, loc_struct_edittext;
    ImageView op_statu_imageview, loc_struct_imageview;
    int opstatus_type = 0, locstruc_type = 1;
    String opstatus_type_id = "", opstatus_type_text = "", loc_type_text = "", loc_type_id = "";

    public JSA_JobLocation_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_joblocation_fragment, container, false);


        op_statu_edittext = (EditText) rootView.findViewById(R.id.op_statu_edittext);
        op_statu_imageview = (ImageView) rootView.findViewById(R.id.op_statu_imageview);
        loc_struct_edittext = (EditText) rootView.findViewById(R.id.loc_struct_edittext);
        loc_struct_imageview = (ImageView) rootView.findViewById(R.id.loc_struct_imageview);

        op_statu_imageview.setOnClickListener(this);
        loc_struct_imageview.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == op_statu_imageview) {
            Intent intent = new Intent(getActivity(), JSA_OperationalStatus_Activity.class);
            intent.putExtra("request_id", Integer.toString(opstatus_type));
            startActivityForResult(intent, opstatus_type);
        } else if (v == loc_struct_imageview) {
            Intent intent = new Intent(getActivity(), JSA_LocationStr_Activity.class);
            intent.putExtra("request_id", Integer.toString(locstruc_type));
            startActivityForResult(intent, locstruc_type);
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
            if (requestCode == opstatus_type) {
                opstatus_type_id = data.getStringExtra("opstatus_type_id");
                opstatus_type_text = data.getStringExtra("opstatus_type_text");
                op_statu_edittext.setText(opstatus_type_id + " - " + opstatus_type_text);
            } else if (requestCode == locstruc_type) {
                loc_type_id = data.getStringExtra("loc_type_id");
                loc_type_text = data.getStringExtra("loc_type_text");
                loc_struct_edittext.setText(loc_type_text);
            }
        }
    }

    public JSA_JobLocation_Object getData() {
        return new JSA_JobLocation_Object(opstatus_type_id, opstatus_type_text, loc_type_id,
                loc_type_text);
    }
}
