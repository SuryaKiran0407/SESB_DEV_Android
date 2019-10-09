package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JSA_Add_Hazards_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText hazard_edittext, hazard_categories_edittext, job_step_edittext;
    ImageView job_step_imageview, hazard_categories_imageview, hazard_imageview, back_imageview;
    Button cancel_button, submit_button;
    Error_Dialog error_dialog = new Error_Dialog();
    String person_resp = "", hazard_data = "", hazardcategory_id = "", hazardcategory_text = "", jobstep_list = "", jobstep_id = "", jobstep_text = "";
    int jobstepstype = 0, hazard_cat = 1, hazard_list = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_hazards_activity);

        job_step_edittext = (EditText) findViewById(R.id.job_step_edittext);
        hazard_categories_edittext = (EditText) findViewById(R.id.hazard_categories_edittext);
        hazard_edittext = (EditText) findViewById(R.id.hazard_edittext);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        job_step_imageview = (ImageView) findViewById(R.id.job_step_imageview);
        hazard_categories_imageview = (ImageView) findViewById(R.id.hazard_categories_imageview);
        hazard_imageview = (ImageView) findViewById(R.id.hazard_imageview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobstep_list = bundle.getString("jobstep_list");
            if (jobstep_list != null && !jobstep_list.equals("")) {
            } else {
                jobstep_id = bundle.getString("jobstep_id");
                jobstep_text = bundle.getString("jobstep_text");
                person_resp = bundle.getString("person_resp");
                hazardcategory_id = bundle.getString("hazardcategory_id");
                hazardcategory_text = bundle.getString("hazardcategory_text");
                hazard_data = bundle.getString("hazard_array");
                job_step_edittext.setText(bundle.getString("jobstep_id") + " - " + bundle.getString("jobstep_text"));
                job_step_imageview.setVisibility(View.GONE);
                hazard_categories_edittext.setText(bundle.getString("hazardcategory_id") + " - " + bundle.getString("hazardcategory_text"));
                hazard_categories_imageview.setVisibility(View.GONE);
                hazard_edittext.setText(bundle.getString("hazard_data"));
            }
        }

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        job_step_imageview.setOnClickListener(this);
        hazard_categories_imageview.setOnClickListener(this);
        hazard_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            JSA_Add_Hazards_Activity.this.finish();
        } else if (v == cancel_button) {
            JSA_Add_Hazards_Activity.this.finish();
        } else if (v == job_step_imageview) {
            Intent intent = new Intent(JSA_Add_Hazards_Activity.this,
                    JSA_Hazards_JobSteps_Activity.class);
            intent.putExtra("jobstep_list", jobstep_list);
            startActivityForResult(intent, jobstepstype);
        } else if (v == hazard_categories_imageview) {
            Intent intent = new Intent(JSA_Add_Hazards_Activity.this,
                    JSA_Hazards_Categories_Activity.class);
            startActivityForResult(intent, hazard_cat);
        } else if (v == hazard_imageview) {
            if (hazardcategory_id != null && !hazardcategory_id.equals("")) {
                Intent intent = new Intent(JSA_Add_Hazards_Activity.this,
                        JSA_Hazards_List_Activity.class);
                intent.putExtra("hazardcategory_id", hazardcategory_id);
                intent.putExtra("hazard_data", hazard_data);
                startActivityForResult(intent, hazard_list);
            } else {
                error_dialog.show_error_dialog(JSA_Add_Hazards_Activity.this,
                        getString(R.string.jsa_hzrdcatg));
            }
        } else if (v == submit_button) {
            if (jobstep_id != null && !jobstep_id.equals("")) {
                if (hazardcategory_id != null && !hazardcategory_id.equals("")) {
                    if (hazard_data != null && !hazard_data.equals("")) {
                        Intent intent = new Intent();
                        intent.putExtra("jobstep_id", jobstep_id);
                        intent.putExtra("jobstep_text", jobstep_text);
                        intent.putExtra("hazardcategory_id", hazardcategory_id);
                        intent.putExtra("hazardcategory_text", hazardcategory_text);
                        intent.putExtra("hazard_data", hazard_data);
                        intent.putExtra("person_resp", person_resp);
                        setResult(RESULT_OK, intent);
                        JSA_Add_Hazards_Activity.this.finish();
                    } else {
                        error_dialog.show_error_dialog(JSA_Add_Hazards_Activity.this,
                                getString(R.string.jsa_hzrdselect));
                    }
                } else {
                    error_dialog.show_error_dialog(JSA_Add_Hazards_Activity.this,
                            getString(R.string.jsa_hzrdcatg));
                }
            } else {
                error_dialog.show_error_dialog(JSA_Add_Hazards_Activity.this,
                        getString(R.string.jsa_jobstepselect));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == jobstepstype) {
                jobstep_id = data.getStringExtra("jobstep_id");
                jobstep_text = data.getStringExtra("jobstep_text");
                person_resp = data.getStringExtra("person_resp");
                job_step_edittext.setText(jobstep_id + " - " + jobstep_text);
            } else if (requestCode == hazard_cat) {
                hazardcategory_id = data.getStringExtra("hazardcategory_id");
                hazardcategory_text = data.getStringExtra("hazardcategory_text");
                hazard_categories_edittext.setText(hazardcategory_id + " - " + hazardcategory_text);
                hazard_edittext.setText("");
                hazard_data = "";
            } else if (requestCode == hazard_list) {
                hazard_data = data.getStringExtra("hazard_data");
                Gson gson = new Gson();
                Type type = new TypeToken<List<JSA_Hazards_List_Activity.Type_Object>>() {
                }.getType();
                List<JSA_Hazards_List_Activity.Type_Object> objectList = gson.fromJson(hazard_data, type);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < objectList.size(); i++) {
                    boolean checked_status = objectList.get(i).getSelected_status();
                    if (checked_status) {
                        stringBuilder.append(objectList.get(i).getWork_text());
                        stringBuilder.append(", ");
                    }
                }
                hazard_edittext.setText(stringBuilder.toString());
            }
        }
    }
}
