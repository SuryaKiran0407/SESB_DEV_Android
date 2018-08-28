package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notifications_Personresponsible_Activity;

public class JSA_Add_AssessmentTeam_Activity extends AppCompatActivity implements View.OnClickListener
{


    EditText name_edittext, role_edittext;
    ImageView name_imageview, role_imageview, back_imageview;
    Button cancel_button, submit_button;
    String equipId = "", name_id = "", name_text = "", role_id = "", role_text = "";
    int personResp = 0, role = 1;
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_assessmentteam_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            equipId = bundle.getString("equipId");
        }

        role_edittext = (EditText)findViewById(R.id.role_edittext);
        name_edittext = (EditText)findViewById(R.id.name_edittext);
        name_imageview = (ImageView)findViewById(R.id.name_imageview);
        role_imageview = (ImageView)findViewById(R.id.role_imageview);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        submit_button = (Button)findViewById(R.id.submit_button);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);


        name_imageview.setOnClickListener(this);
        role_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        if(v == back_imageview)
        {
            JSA_Add_AssessmentTeam_Activity.this.finish();
        }
        else if(v == cancel_button)
        {
            JSA_Add_AssessmentTeam_Activity.this.finish();
        }
        else if(v == name_imageview)
        {
            Intent intent = new Intent(JSA_Add_AssessmentTeam_Activity.this,Notifications_Personresponsible_Activity.class);
            intent.putExtra("equip_id",equipId);
            intent.putExtra("request_id",Integer.toString(personResp));
            startActivityForResult(intent,personResp);
        }
        else if(v == role_imageview)
        {
            Intent intent = new Intent(JSA_Add_AssessmentTeam_Activity.this,JSA_RolesType_Activity.class);
            intent.putExtra("request_id",Integer.toString(role));
            startActivityForResult(intent,role);
        }
        else if(v == submit_button)
        {
            if (name_edittext.getText().toString() != null && !name_edittext.getText().toString().equals(""))
            {
                if (role_id != null && !role_id.equals(""))
                {
                    Intent intent=new Intent();
                    intent.putExtra("name_id",name_edittext.getText().toString());
                    intent.putExtra("name_text","");
                    intent.putExtra("role_id",role_id);
                    intent.putExtra("role_text",role_text);
                    setResult(RESULT_OK,intent);
                    JSA_Add_AssessmentTeam_Activity.this.finish();
                }
                else
                {
                    error_dialog.show_error_dialog(JSA_Add_AssessmentTeam_Activity.this,"Please Select Role");
                }
            }
            else
            {
                error_dialog.show_error_dialog(JSA_Add_AssessmentTeam_Activity.this,"Please Select Name");
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == personResp)
            {
                name_id = data.getStringExtra("personresponsible_id");
                name_text = data.getStringExtra("personresponsible_text");
                name_edittext.setText(name_id);
            }
            else if(requestCode == role)
            {
                role_id = data.getStringExtra("id");
                role_text = data.getStringExtra("text");
                role_edittext.setText(role_id+" - "+role_text);
            }
        }
    }


}
