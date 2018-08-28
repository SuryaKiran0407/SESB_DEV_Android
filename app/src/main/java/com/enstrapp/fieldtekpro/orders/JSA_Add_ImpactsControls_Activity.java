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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JSA_Add_ImpactsControls_Activity extends AppCompatActivity implements View.OnClickListener
{


    EditText hazard_edittext, impacts_edittext, controls_edittext;
    ImageView impacts_imageview, controls_imageview, hazard_imageview, back_imageview;
    Button cancel_button, submit_button;
    Error_Dialog error_dialog = new Error_Dialog();
    String controls_data = "", impact_data = "", control_id = "", control_text = "", hazardtype_id = "", hazardtype_text = "", hazards_list = "";
    int hazardtype = 0, impactstype = 1, controlstype = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_impactscontrols_activity);


        impacts_edittext = (EditText)findViewById(R.id.impacts_edittext);
        controls_edittext = (EditText)findViewById(R.id.controls_edittext);
        hazard_edittext = (EditText)findViewById(R.id.hazard_edittext);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        submit_button = (Button)findViewById(R.id.submit_button);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);
        impacts_imageview = (ImageView)findViewById(R.id.impacts_imageview);
        controls_imageview = (ImageView)findViewById(R.id.controls_imageview);
        hazard_imageview = (ImageView)findViewById(R.id.hazard_imageview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            hazards_list = bundle.getString("hazards_list");
        }

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        impacts_imageview.setOnClickListener(this);
        controls_imageview.setOnClickListener(this);
        hazard_imageview.setOnClickListener(this);


    }


    @Override
    public void onClick(View v)
    {
        if(v == back_imageview)
        {
            JSA_Add_ImpactsControls_Activity.this.finish();
        }
        else if(v == cancel_button)
        {
            JSA_Add_ImpactsControls_Activity.this.finish();
        }
        else if(v == hazard_imageview)
        {
            Intent intent = new Intent(JSA_Add_ImpactsControls_Activity.this,JSA_ImpactsControls_Hazards_Activity.class);
            intent.putExtra("hazards_list",hazards_list);
            startActivityForResult(intent,hazardtype);
        }
        else if(v == impacts_imageview)
        {
            if (hazardtype_id != null && !hazardtype_id.equals(""))
            {
                Intent intent = new Intent(JSA_Add_ImpactsControls_Activity.this,JSA_Impacts_Activity.class);
                intent.putExtra("hazardcategory_id",hazardtype_id);
                intent.putExtra("impact_data",impact_data);
                startActivityForResult(intent,impactstype);
            }
            else
            {
                error_dialog.show_error_dialog(JSA_Add_ImpactsControls_Activity.this,"Please Select Hazard");
            }
        }
        else if(v == controls_imageview)
        {
            if (hazardtype_id != null && !hazardtype_id.equals(""))
            {
                if (impact_data != null && !impact_data.equals(""))
                {
                    Intent intent = new Intent(JSA_Add_ImpactsControls_Activity.this,JSA_Controls_Activity.class);
                    intent.putExtra("hazardcategory_id",hazardtype_id);
                    intent.putExtra("controls_data",controls_data);
                    startActivityForResult(intent,controlstype);
                }
                else
                {
                    error_dialog.show_error_dialog(JSA_Add_ImpactsControls_Activity.this,"Please Select Impact");
                }
            }
            else
            {
                error_dialog.show_error_dialog(JSA_Add_ImpactsControls_Activity.this,"Please Select Hazard");
            }
        }
        else if(v == submit_button)
        {
            if (hazardtype_id != null && !hazardtype_id.equals(""))
            {
                if (impact_data != null && !impact_data.equals(""))
                {
                    Intent intent=new Intent();
                    intent.putExtra("hazardtype_id",hazardtype_id);
                    intent.putExtra("hazardtype_text",hazardtype_text);
                    intent.putExtra("impact_data",impact_data);
                    intent.putExtra("controls_data",controls_data);
                    setResult(RESULT_OK,intent);
                    JSA_Add_ImpactsControls_Activity.this.finish();
                }
                else
                {
                    error_dialog.show_error_dialog(JSA_Add_ImpactsControls_Activity.this,"Please Select Atleast One Impact");
                }
            }
            else
            {
                error_dialog.show_error_dialog(JSA_Add_ImpactsControls_Activity.this,"Please Select Hazard");
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == hazardtype)
            {
                hazardtype_id = data.getStringExtra("hazardtype_id");
                hazardtype_text = data.getStringExtra("hazardtype_text");
                hazard_edittext.setText(hazardtype_id+" - "+hazardtype_text);
            }
            else if(requestCode == impactstype)
            {
                impact_data = data.getStringExtra("impact_data");
                Gson gson = new Gson();
                Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {}.getType();
                List<JSA_Impacts_Activity.Type_Object> objectList = gson.fromJson(impact_data, type);
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < objectList.size(); i++)
                {
                    boolean checked_status = objectList.get(i).getSelected_status();
                    if(checked_status)
                    {
                        stringBuilder.append(objectList.get(i).getText());
                        stringBuilder.append(", ");
                    }
                }
                impacts_edittext.setText(stringBuilder.toString());
            }
            else if(requestCode == controlstype)
            {
                controls_data = data.getStringExtra("controls_data");
                Gson gson = new Gson();
                Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {}.getType();
                List<JSA_Impacts_Activity.Type_Object> objectList = gson.fromJson(controls_data, type);
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < objectList.size(); i++)
                {
                    boolean checked_status = objectList.get(i).getSelected_status();
                    if(checked_status)
                    {
                        stringBuilder.append(objectList.get(i).getText());
                        stringBuilder.append(", ");
                    }
                }
                controls_edittext.setText(stringBuilder.toString());
            }
        }
    }


}
