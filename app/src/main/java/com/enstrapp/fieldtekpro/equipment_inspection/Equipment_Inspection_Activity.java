package com.enstrapp.fieldtekpro.equipment_inspection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.GPS.GPSTracker;
import com.enstrapp.fieldtekpro.GPS.Location_Checker;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;
import com.enstrapp.fieldtekpro.notifications.Notifications_Create_Activity;
import com.enstrapp.fieldtekpro.orders.Orders_Activity;

public class Equipment_Inspection_Activity extends AppCompatActivity {

    ImageView back_imageview, equipmentscan_imageview;
    LinearLayout floc_layout, equip_layout;
    EditText floc_edittext, equipment_edittext;
    int floc_status = 0, equip_status = 1;
    String work_center_id = "",planing_plant = "",maintainance_plant = "", functionlocation_id = "", functionlocation_text = "", equipment_id = "", equipment_text = "", plant_id = "", plannergroup_id = "", plannergroup_text = "";
    GridView grid;
    String[] web, web1;
    int[] imageId = {R.drawable.ic_create_notif_icon, R.drawable.ic_purchaseorder_icon,};
    int[] imageId1 = {R.drawable.ic_create_notif_icon, R.drawable.ic_statistics_icon, R.drawable.ic_history_icon1, R.drawable.ic_purchaseorder_icon, R.drawable.ic_checklist_icon1, R.drawable.ic_geotag_icon, R.drawable.ic_maintenanceplan_icon,};
    String selected_status = "";
    private static final int ZXING_CAMERA_PERMISSION = 3;
    int barcode_scan = 3;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_inspection_activity);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Equipment_Inspection_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        web = new String[]{getString(R.string.create_notification), getString(R.string.orders)};
        web1 = new String[]{getString(R.string.create_notification), getString(R.string.stats),
                getString(R.string.history), getString(R.string.orders),
                getString(R.string.inspc_chklist), getString(R.string.geo_tag),
                getString(R.string.maintenanceplan)};
        floc_layout = (LinearLayout) findViewById(R.id.floc_layout);
        equip_layout = (LinearLayout) findViewById(R.id.equip_layout);
        floc_edittext = (EditText) findViewById(R.id.floc_edittext);
        equipment_edittext = (EditText) findViewById(R.id.equipment_edittext);
        grid = (GridView) findViewById(R.id.gridview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        equipmentscan_imageview = (ImageView) findViewById(R.id.equipmentscan_imageview);


        floc_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Equipment_Inspection_Activity.this, FunctionLocation_Activity.class);
                intent.putExtra("request_id", Integer.toString(floc_status));
                startActivityForResult(intent, floc_status);
            }
        });


        equip_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent equipment_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Activity.class);
                equipment_intent.putExtra("request_id", Integer.toString(equip_status));
                equipment_intent.putExtra("functionlocation_id", floc_edittext.getText().toString());
                startActivityForResult(equipment_intent, equip_status);
            }
        });


        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipment_Inspection_Activity.this.finish();
            }
        });


        equipmentscan_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Equipment_Inspection_Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Equipment_Inspection_Activity.this, new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(Equipment_Inspection_Activity.this, Barcode_Scanner_Activity.class);
                    startActivityForResult(intent, barcode_scan);
                }
            }
        });

    }


    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == floc_status) {
                functionlocation_id = data.getStringExtra("functionlocation_id");
                functionlocation_text = data.getStringExtra("functionlocation_text");
                equipment_id = "";
                equipment_text = "";
                equipment_edittext.setText("");
                floc_edittext.setText(functionlocation_id);
                plannergroup_id = data.getStringExtra("ingrp_id");
                plannergroup_text = "";
                work_center_id = data.getStringExtra("work_center");
                Equipment_Inspection_Grid_Adapter adapter = new Equipment_Inspection_Grid_Adapter(Equipment_Inspection_Activity.this, web, imageId);
                grid.invalidateViews();
                grid.setAdapter(adapter);
                grid.setVisibility(View.VISIBLE);
                selected_status = "floc";
                planing_plant = data.getStringExtra("iwerk");
                maintainance_plant = data.getStringExtra("plant_id");
                grid_clicklistener();
            } else if (requestCode == equip_status) {
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
                planing_plant = data.getStringExtra("iwerk");
                maintainance_plant = data.getStringExtra("plant_id");
                grid_clicklistener();
            } else if (requestCode == barcode_scan) {
                String message = data.getStringExtra("MESSAGE");
                equipment_edittext.setText(message);
                if (message != null && !message.equals("")) {
                    try {
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{message});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    equipment_id = message;
                                    equipment_text = cursor.getString(5);
                                    functionlocation_id = cursor.getString(1);
                                    plant_id = cursor.getString(29);
                                    plannergroup_id = cursor.getString(13);
                                    plannergroup_text = "";
                                    floc_edittext.setText(functionlocation_id);
                                    work_center_id = cursor.getString(11);
                                    planing_plant = cursor.getString(29);
                                    maintainance_plant =cursor.getString(10);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }
                    try {
                        if (functionlocation_id != null && !functionlocation_id.equals("")) {
                            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Tplnr = ?", new String[]{functionlocation_id});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        functionlocation_text = cursor.getString(2);
                                        floc_edittext.setText(functionlocation_id);
                                    }
                                    while (cursor.moveToNext());
                                }
                            } else {
                                cursor.close();
                            }
                        }
                    } catch (Exception e) {
                    }
                    Equipment_Inspection_Grid_Adapter adapter = new Equipment_Inspection_Grid_Adapter(Equipment_Inspection_Activity.this, web1, imageId1);
                    grid.invalidateViews();
                    grid.setAdapter(adapter);
                    grid.setVisibility(View.VISIBLE);
                    selected_status = "equip";
                    grid_clicklistener();
                }
            }
        }
    }


    public void grid_clicklistener() {
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selected_status.equalsIgnoreCase("floc")) {
                    if (web[+position].equals(getString(R.string.create_notification))) {
                        Intent create_notif_intent = new Intent(Equipment_Inspection_Activity.this, Notifications_Create_Activity.class);
                        create_notif_intent.putExtra("equipment_id", equipment_id);
                        create_notif_intent.putExtra("equipment_text", equipment_text);
                        create_notif_intent.putExtra("functionlocation_id", functionlocation_id);
                        create_notif_intent.putExtra("functionlocation_text", functionlocation_text);
                        create_notif_intent.putExtra("plant_id", plant_id);
                        create_notif_intent.putExtra("plannergroup_id", plannergroup_id);
                        create_notif_intent.putExtra("plannergroup_text", plannergroup_text);
                        create_notif_intent.putExtra("work_center_id", work_center_id);
                        create_notif_intent.putExtra("maintnce_plant", maintainance_plant);
                        create_notif_intent.putExtra("planing_plant", planing_plant);
                        startActivity(create_notif_intent);
                    } else if (web[+position].equals(getString(R.string.orders))) {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Orders_Activity.class);
                        orders_intent.putExtra("equipment_id", equipment_id);
                        orders_intent.putExtra("functionlocation_id", functionlocation_id);
                        startActivity(orders_intent);
                    }
                } else {
                    if (web1[+position].equals(getString(R.string.create_notification))) {
                        Intent create_notif_intent = new Intent(Equipment_Inspection_Activity.this, Notifications_Create_Activity.class);
                        create_notif_intent.putExtra("equipment_id", equipment_id);
                        create_notif_intent.putExtra("equipment_text", equipment_text);
                        create_notif_intent.putExtra("functionlocation_id", functionlocation_id);
                        create_notif_intent.putExtra("functionlocation_text", functionlocation_text);
                        create_notif_intent.putExtra("plant_id", plant_id);
                        create_notif_intent.putExtra("plannergroup_id", plannergroup_id);
                        create_notif_intent.putExtra("plannergroup_text", plannergroup_text);
                        create_notif_intent.putExtra("work_center_id", work_center_id);
                        create_notif_intent.putExtra("maintnce_plant", maintainance_plant);
                        create_notif_intent.putExtra("planing_plant", planing_plant);
                        startActivity(create_notif_intent);
                    } else if (web1[+position].equals(getString(R.string.stats))) {
                        Intent Statistics_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Inspection_Breakdown_Statics_Activity.class);
                        Statistics_intent.putExtra("equipment_id", equipment_id);
                        Statistics_intent.putExtra("equipment_text", equipment_text);
                        startActivity(Statistics_intent);
                    } else if (web1[+position].equals(getString(R.string.history))) {
                        Intent history_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_History_Activity.class);
                        history_intent.putExtra("equipment_id", equipment_id);
                        startActivity(history_intent);
                    } else if (web1[+position].equals(getString(R.string.orders))) {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Orders_Activity.class);
                        orders_intent.putExtra("equipment_id", equipment_id);
                        orders_intent.putExtra("functionlocation_id", functionlocation_id);
                        startActivity(orders_intent);
                    } else if (web1[+position].equals(getString(R.string.inspc_chklist))) {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_InspectionChecklist_List_Activity.class);
                        orders_intent.putExtra("equipment_id", equipment_id);
                        orders_intent.putExtra("functionlocation_id", functionlocation_id);
                        startActivity(orders_intent);
                    } else if (web1[+position].equals(getString(R.string.geo_tag))) {
                        GPSTracker gps = new GPSTracker(Equipment_Inspection_Activity.this);
                        if (gps.canGetLocation()) {
                            //double latitude = gps.getLatitude();
                            //double longitude = gps.getLongitude();
                            Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_GEO_Tag_Activity.class);
                            orders_intent.putExtra("equipment_id", equipment_id);
                            orders_intent.putExtra("equipment_text", equipment_text);
                            orders_intent.putExtra("functionlocation_id", functionlocation_id);
                            startActivity(orders_intent);
                        } else {
                            Location_Checker lc = new Location_Checker();
                            lc.location_checker(Equipment_Inspection_Activity.this);
                        }
                    } else if (web1[+position].equals(getString(R.string.maintenanceplan))) {
                        Intent orders_intent = new Intent(Equipment_Inspection_Activity.this, Equipment_Maintenance_Plan_Activity.class);
                        orders_intent.putExtra("equipment_id", equipment_id);
                        orders_intent.putExtra("equipment_text", equipment_text);
                        orders_intent.putExtra("functionlocation_id", functionlocation_id);
                        startActivity(orders_intent);
                    }
                }
            }
        });
    }


}
