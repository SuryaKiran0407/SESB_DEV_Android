package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

public class JSA_Add_JobSteps_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText step_no_edittext, job_step_edittext, pers_resp_edittext;
    ImageView back_imageview;
    Button cancel_button, submit_button;
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_jobsteps_activity);

        step_no_edittext = (EditText) findViewById(R.id.step_no_edittext);
        job_step_edittext = (EditText) findViewById(R.id.job_step_edittext);
        pers_resp_edittext = (EditText) findViewById(R.id.pers_resp_edittext);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            step_no_edittext.setText(bundle.getString("step_no"));
        }

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            JSA_Add_JobSteps_Activity.this.finish();
        } else if (v == cancel_button) {
            JSA_Add_JobSteps_Activity.this.finish();
        } else if (v == submit_button) {
            if (job_step_edittext.getText().toString() != null && !job_step_edittext.getText().toString().equals("")) {
                Intent intent = new Intent();
                intent.putExtra("step_no", step_no_edittext.getText().toString());
                intent.putExtra("jobstep_text", job_step_edittext.getText().toString());
                intent.putExtra("person_responsible_text", pers_resp_edittext.getText().toString());
                setResult(RESULT_OK, intent);
                JSA_Add_JobSteps_Activity.this.finish();
            } else {
                error_dialog.show_error_dialog(JSA_Add_JobSteps_Activity.this,
                        getString(R.string.jsa_jbstepentr));
            }
        }
    }
}
