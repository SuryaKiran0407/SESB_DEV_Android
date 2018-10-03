package com.enstrapp.fieldtekpro.equipment_inspection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro.GPS.GPSTracker;
import com.enstrapp.fieldtekpro.GPS.Location_Checker;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;
import com.enstrapp.fieldtekpro.login.Login_Activity;
import com.enstrapp.fieldtekpro.notifications.Notifications_Create_Activity;
import com.enstrapp.fieldtekpro.orders.Orders_Activity;

public class Equipment_Inspection_Activity extends AppCompatActivity
{

    ImageView back_imageview;
    LinearLayout floc_layout, equip_layout;
    EditText floc_edittext, equipment_edittext;
    int floc_status = 0, equip_status = 1;
    String work_center_id = "", functionlocation_id = "", functionlocation_text = "", equipment_id = "", equipment_text = "", plant_id = "", plannergroup_id = "", plannergroup_text ="";
    GridView grid;
    String[] web = {"Create Notification","Orders",} ;
    int[] imageId = {R.drawable.ic_create_notif_icon,R.drawable.ic_purchaseorder_icon,};
    String[] web1 = {"Create Notification","Statistics","History","Orders","Inspection Checklist", "Geo Tag", "Maintenance Plan"} ;
    int[] imageId1 = {R.drawable.ic_create_notif_icon,R.drawable.ic_statistics_icon,R.drawable.ic_history_icon1,R.drawable.ic_purchaseorder_icon,R.drawable.ic_checklist_icon1,R.drawable.ic_geotag_icon,R.drawable.ic_maintenanceplan_icon,};
    String selected_status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_inspection_activity);

        floc_layout = (LinearLayout)findViewById(R.id.floc_layout);
        equip_layout = (LinearLayout)findViewById(R.id.equip_layout);
        floc_edittext = (EditText)findViewById(R.id.floc_edittext);
        equipment_edittext = (EditText)findViewById(R.id.equipment_edittext);
        grid = (GridView)findViewById(R.id.gridview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);

        floc_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Equipment_Inspection_Activity.this, FunctionLocation_Activity.class);
                intent.putExtra("request_id", Integer.toString(floc_status));
                startActivityForResult(intent,floc_status);
            }
        });

        equip_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent equipment_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Activity.class);
                equipment_intent.putExtra("request_id", Integer.toString(equip_status));
                equipment_intent.putExtra("functionlocation_id",floc_edittext.getText().toString());
                startActivityForResult(equipment_intent, equip_status);
            }
        });

        back_imageview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Equipment_Inspection_Activity.this.finish();
            }
        });

    }


    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == floc_status)
            {
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                equipment_id = "";
                equipment_text = "";
                equipment_edittext.setText("");
                floc_edittext.setText(functionlocation_id);
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                Equipment_Inspection_Grid_Adapter adapter = new Equipment_Inspection_Grid_Adapter(Equipment_Inspection_Activity.this, web, imageId);
                grid.invalidateViews();
                grid.setAdapter(adapter);
                grid.setVisibility(View.VISIBLE);
                selected_status = "floc";
                grid_clicklistener();
            }
            else if(requestCode == equip_status)
            {
                equipment_id = data.getStringExtra("equipment_id");
                equipment_text = data.getStringExtra("equipment_text");
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                equipment_edittext.setText(equipment_id);
                floc_edittext.setText(functionlocation_id);
                plant_id = data.getStringExtra("plant_id");
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                work_center_id = data.getStringExtra("work_center");
                Equipment_Inspection_Grid_Adapter adapter = new Equipment_Inspection_Grid_Adapter(Equipment_Inspection_Activity.this, web1, imageId1);
                grid.invalidateViews();
                grid.setAdapter(adapter);
                grid.setVisibility(View.VISIBLE);
                selected_status = "equip";
                grid_clicklistener();
            }
        }
    }


    public void grid_clicklistener()
    {
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                if(selected_status.equalsIgnoreCase("floc"))
                {
                    if(web[+ position].equals("Create Notification"))
                    {
                        Intent create_notif_intent = new Intent(Equipment_Inspection_Activity.this, Notifications_Create_Activity.class);
                        create_notif_intent.putExtra("equipment_id",equipment_id);
                        create_notif_intent.putExtra("equipment_text",equipment_text);
                        create_notif_intent.putExtra("functionlocation_id",functionlocation_id);
                        create_notif_intent.putExtra("functionlocation_text",functionlocation_text);
                        create_notif_intent.putExtra("plant_id",plant_id);
                        create_notif_intent.putExtra("plannergroup_id",plannergroup_id);
                        create_notif_intent.putExtra("plannergroup_text",plannergroup_text);
                        create_notif_intent.putExtra("work_center_id",work_center_id);
                        startActivity(create_notif_intent);
                    }
                    else if(web[+ position].equals("Orders"))
                    {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Orders_Activity.class);
                        orders_intent.putExtra("equipment_id",equipment_id);
                        orders_intent.putExtra("functionlocation_id",functionlocation_id);
                        startActivity(orders_intent);
                    }
                }
                else
                {
                    if(web1[+ position].equals("Create Notification"))
                    {
                        Intent create_notif_intent = new Intent(Equipment_Inspection_Activity.this, Notifications_Create_Activity.class);
                        create_notif_intent.putExtra("equipment_id",equipment_id);
                        create_notif_intent.putExtra("equipment_text",equipment_text);
                        create_notif_intent.putExtra("functionlocation_id",functionlocation_id);
                        create_notif_intent.putExtra("functionlocation_text",functionlocation_text);
                        create_notif_intent.putExtra("plant_id",plant_id);
                        create_notif_intent.putExtra("plannergroup_id",plannergroup_id);
                        create_notif_intent.putExtra("plannergroup_text",plannergroup_text);
                        create_notif_intent.putExtra("work_center_id",work_center_id);
                        startActivity(create_notif_intent);
                    }
                    else if(web1[+ position].equals("Statistics"))
                    {
                        Intent Statistics_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Inspection_Breakdown_Statics_Activity.class);
                        Statistics_intent.putExtra("equipment_id",equipment_id);
                        Statistics_intent.putExtra("equipment_text",equipment_text);
                        startActivity(Statistics_intent);
                    }
                    else if(web1[+ position].equals("History"))
                    {
                        Intent history_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_History_Activity.class);
                        history_intent.putExtra("equipment_id",equipment_id);
                        startActivity(history_intent);
                    }
                    else if(web1[+ position].equals("Orders"))
                    {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Orders_Activity.class);
                        orders_intent.putExtra("equipment_id",equipment_id);
                        orders_intent.putExtra("functionlocation_id",functionlocation_id);
                        startActivity(orders_intent);
                    }
                    else if(web1[+ position].equals("Inspection Checklist"))
                    {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_InspectionChecklist_List_Activity.class);
                        orders_intent.putExtra("equipment_id",equipment_id);
                        orders_intent.putExtra("functionlocation_id",functionlocation_id);
                        startActivity(orders_intent);
                    }
                    else if(web1[+ position].equals("Geo Tag"))
                    {
                        GPSTracker gps = new GPSTracker(Equipment_Inspection_Activity.this);
                        if(gps.canGetLocation())
                        {
                            //double latitude = gps.getLatitude();
                            //double longitude = gps.getLongitude();
                            Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_GEO_Tag_Activity.class);
                            orders_intent.putExtra("equipment_id",equipment_id);
                            orders_intent.putExtra("equipment_text",equipment_text);
                            orders_intent.putExtra("functionlocation_id",functionlocation_id);
                            startActivity(orders_intent);
                        }
                        else
                        {
                            Location_Checker lc = new Location_Checker();
                            lc.location_checker(Equipment_Inspection_Activity.this);
                        }
                    }
                    else if(web1[+ position].equals("Maintenance Plan"))
                    {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Maintenance_Plan_Activity.class);
                        orders_intent.putExtra("equipment_id",equipment_id);
                        orders_intent.putExtra("equipment_text",equipment_text);
                        orders_intent.putExtra("functionlocation_id",functionlocation_id);
                        startActivity(orders_intent);
                    }
                }
            }
        });
    }


}
