package com.enstrapp.fieldtekpro.Calibration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;

public class Calibration_Activity extends AppCompatActivity {

    ImageView back_imageview;
    LinearLayout equip_layout;
    EditText equipment_edittext;
    int equip_status = 1;
    String work_center_id = "", functionlocation_id = "", functionlocation_text = "", equipment_id = "", equipment_text = "", plant_id = "", plannergroup_id = "", plannergroup_text = "";
    GridView grid;
    //String[] web1 = {"Create Notification","Statistics","Maintenance History","Calibration History", "Calibration Orders","Maintenance Plan"} ;
    //int[] imageId1 = {R.drawable.ic_create_notif_icon,R.drawable.ic_statistics_icon,R.drawable.ic_maitenancehistory_icon,R.drawable.ic_calibrationhistory_icon, R.drawable.ic_purchaseorder_icon,R.drawable.ic_maintenanceplan_icon,};
    String[] web1 = {getString(R.string.calb_ordrs)};
    int[] imageId1 = {R.drawable.ic_purchaseorder_icon};
    String selected_status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration_activity);

        equip_layout = (LinearLayout) findViewById(R.id.equip_layout);
        equipment_edittext = (EditText) findViewById(R.id.equipment_edittext);
        grid = (GridView) findViewById(R.id.gridview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        equip_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent equipment_intent = new Intent(Calibration_Activity.this, Equipment_Activity.class);
                equipment_intent.putExtra("request_id", Integer.toString(equip_status));
                startActivityForResult(equipment_intent, equip_status);
            }
        });

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calibration_Activity.this.finish();
            }
        });

    }


    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == equip_status) {
                equipment_id = data.getStringExtra("equipment_id");
                equipment_text = data.getStringExtra("equipment_text");
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                equipment_edittext.setText(equipment_id);
                plant_id = data.getStringExtra("plant_id");
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                work_center_id = data.getStringExtra("work_center");
                Calibration_Grid_Adapter adapter = new Calibration_Grid_Adapter(Calibration_Activity.this, web1, imageId1);
                grid.invalidateViews();
                grid.setAdapter(adapter);
                grid.setVisibility(View.VISIBLE);
                selected_status = "equip";
                grid_clicklistener();
            }
        }
    }


    public void grid_clicklistener() {
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (web1[+position].equals(getString(R.string.calb_ordrs))) {
                    Intent create_notif_intent = new Intent(Calibration_Activity.this, Calibration_Orders_List_Activity.class);
                    create_notif_intent.putExtra("equipment_id", equipment_id);
                    create_notif_intent.putExtra("plant_id", plant_id);
                    startActivity(create_notif_intent);
                }
            }
        });
    }


}
