package com.enstrapp.fieldtekpro.MIS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


import com.enstrapp.fieldtekpro.R;


public class Mis_Activity extends AppCompatActivity {
    GridView grid;
    String[] web = {"Permit Report", "Notification Analysis", "Breakdown Statistics", "Order Analysis"};
    int[] imageId = {R.drawable.permit_mis_icon, R.drawable.notif_analysis_mis_icon, R.drawable.breakdown_mis_icon, R.drawable.order_analysis_ico};
    ImageView back_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_home);

        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        Mis_Adapter adapter = new Mis_Adapter(Mis_Activity.this, web, imageId);
        grid = (GridView) findViewById(R.id.mis_gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (web[+position].equals("Permit Report")) {
                    Intent orders_intent = new Intent(Mis_Activity.this, PermitReportMainActivity.class);
                    startActivity(orders_intent);

                } else if (web[+position].equals("Notification Analysis")) {
                    Intent orders_intent = new Intent(Mis_Activity.this, Notification_Analysis_Activity.class);
                    startActivity(orders_intent);
                } else if (web[+position].equals("Breakdown Statistics")) {
                    Intent mis_intent = new Intent(Mis_Activity.this, BreakStats_Pie.class);
                    startActivity(mis_intent);
                } else if (web[+position].equals("Order Analysis")) {
                    Intent mis_intent = new Intent(Mis_Activity.this, Order_Analysis_Activity.class);
                    startActivity(mis_intent);
                }
            }
        });


        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mis_Activity.this.finish();
            }
        });

    }
}
