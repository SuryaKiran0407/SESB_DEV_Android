package com.enstrapp.fieldtekpro.DashBoard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Alert_Log.Alert_Log_Activity;
import com.enstrapp.fieldtekpro.Calibration.Calibration_Activity;
import com.enstrapp.fieldtekpro.Initialload.InitialLoad_Activity;
import com.enstrapp.fieldtekpro.MIS.Mis_Activity;
import com.enstrapp.fieldtekpro.PermitList.Isolation_List_Activity;
import com.enstrapp.fieldtekpro.PermitList.PermitList_Activity;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.SendMessage.SendMessage_List_Activity;
import com.enstrapp.fieldtekpro.Settings.Settings_Activity;
import com.enstrapp.fieldtekpro.Utilities.Utilities_Activity;
import com.enstrapp.fieldtekpro.equipment_inspection.Equipment_Inspection_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.history.History_Activity;
import com.enstrapp.fieldtekpro.login.Login_Activity;
import com.enstrapp.fieldtekpro.maintenance_plan.Maintenance_Plan_Activity;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notifications_List_Activity;
import com.enstrapp.fieldtekpro.orders.Orders_Activity;

import java.util.ArrayList;

public class DashBoard_Activity extends AppCompatActivity implements View.OnClickListener {

    GridView grid;
    String[] web =
            {
                    "Notifications",
                    "Orders",
                    "Permit List",
                    "Isolation List",
                    "Equipment Inspection",
                    "Calibration",
                    "MIS",
                    "Utilities",
                    "Maintenance Plan",
                    "Alert Log",
                    "User Log",
                    "Send Message",
                    "Settings",
            };
    int[] imageId =
            {
                    R.drawable.ic_notification_icon,
                    R.drawable.ic_orders_icon,
                    R.drawable.ic_permit_list_icon,
                    R.drawable.ic_isolation_list_icon,
                    R.drawable.ic_sort_icon,
                    R.drawable.ic_calibration_icon,
                    R.drawable.ic_mis_icon,
                    R.drawable.ic_utilities_icon,
                    R.drawable.ic_maintenanceplan_icon,
                    R.drawable.ic_change_log_icon,
                    R.drawable.ic_history_icon,
                    R.drawable.ic_send_message_icon,
                    R.drawable.ic_settings_icon,
            };
    ImageView logout_imageview, refresh_imageview;
    Dialog logout_dialog, refresh_message_dialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ArrayList<String> authorization_list = new ArrayList<String>();
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_activity);

        logout_imageview = (ImageView) findViewById(R.id.logout_imageview);
        refresh_imageview = (ImageView) findViewById(R.id.refresh_imageview);


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        /*For Checking Authorizations for each tile*/
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from Authorization_EtBusf", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getString(4).equalsIgnoreCase("X")) {
                            authorization_list.add(cursor.getString(3));
                        }
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }
        /*For Checking Authorizations for each tile*/

        DashBoard_Adapter adapter = new DashBoard_Adapter(DashBoard_Activity.this, web, imageId);
        grid = (GridView) findViewById(R.id.dashboard_gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (web[+position].equals("Orders")) {
                    if (authorization_list.contains("ALL")) {
                        Intent orders_intent = new Intent(DashBoard_Activity.this, Orders_Activity.class);
                        startActivity(orders_intent);
                    } else if (authorization_list.contains("ORD")) {
                        Intent orders_intent = new Intent(DashBoard_Activity.this, Orders_Activity.class);
                        startActivity(orders_intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Orders");
                    }
                } else if (web[+position].equals("Notifications")) {
                    if (authorization_list.contains("ALL")) {
                        Intent notif_intent = new Intent(DashBoard_Activity.this, Notifications_List_Activity.class);
                        startActivity(notif_intent);
                    } else if (authorization_list.contains("NOT")) {
                        Intent notif_intent = new Intent(DashBoard_Activity.this, Notifications_List_Activity.class);
                        startActivity(notif_intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Notifications");
                    }
                } else if (web[+position].equals("Utilities")) {
                    if (authorization_list.contains("ALL")) {
                        Intent utilities_intent = new Intent(DashBoard_Activity.this, Utilities_Activity.class);
                        startActivity(utilities_intent);
                    } else if (authorization_list.contains("UTL")) {
                        Intent utilities_intent = new Intent(DashBoard_Activity.this, Utilities_Activity.class);
                        startActivity(utilities_intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Utilities");
                    }
                } else if (web[+position].equals("Equipment Inspection")) {
                    if (authorization_list.contains("ALL")) {
                        Intent intent = new Intent(DashBoard_Activity.this, Equipment_Inspection_Activity.class);
                        startActivity(intent);
                    } else if (authorization_list.contains("EQIP")) {
                        Intent intent = new Intent(DashBoard_Activity.this, Equipment_Inspection_Activity.class);
                        startActivity(intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Equipment Inspection");
                    }
                } else if (web[+position].equals("User Log")) {
                    if (authorization_list.contains("ALL")) {
                        Intent intent = new Intent(DashBoard_Activity.this, History_Activity.class);
                        startActivity(intent);
                    } else if (authorization_list.contains("UHST")) {
                        Intent intent = new Intent(DashBoard_Activity.this, History_Activity.class);
                        startActivity(intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access History");
                    }
                } else if (web[+position].equals("Settings")) {
                    /*if(authorization_list.contains("ALL"))
                    {
                        Intent intent = new Intent(DashBoard_Activity.this, Settings_Activity.class);
                        startActivity(intent);
                    }
                    else if(authorization_list.contains("SETT"))
                    {
                        Intent intent = new Intent(DashBoard_Activity.this, Settings_Activity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        error_dialog.show_error_dialog(DashBoard_Activity.this,"You do not have authorization to access Settings");
                    }*/
                    Intent intent = new Intent(DashBoard_Activity.this, Settings_Activity.class);
                    startActivity(intent);
                } else if (web[+position].equals("Permit List")) {
                    if (authorization_list.contains("ALL")) {
                        Intent permitlist_intent = new Intent(DashBoard_Activity.this, PermitList_Activity.class);
                        startActivity(permitlist_intent);
                    } else if (authorization_list.contains("WCM")) {
                        Intent permitlist_intent = new Intent(DashBoard_Activity.this, PermitList_Activity.class);
                        startActivity(permitlist_intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Permit List");
                    }
                } else if (web[+position].equals("Isolation List")) {
                    if (authorization_list.contains("ALL")) {
                        Intent isolation_intent = new Intent(DashBoard_Activity.this, Isolation_List_Activity.class);
                        startActivity(isolation_intent);
                    } else if (authorization_list.contains("WCM")) {
                        Intent isolation_intent = new Intent(DashBoard_Activity.this, Isolation_List_Activity.class);
                        startActivity(isolation_intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access Isolation List");
                    }
                } else if (web[+position].equals("Send Message")) {
                    Intent orders_intent = new Intent(DashBoard_Activity.this, SendMessage_List_Activity.class);
                    startActivity(orders_intent);
                } else if (web[+position].equals("MIS")) {
                    if (authorization_list.contains("ALL")) {
                        Intent intent = new Intent(DashBoard_Activity.this, Mis_Activity.class);
                        startActivity(intent);
                    } else if (authorization_list.contains("MIS")) {
                        Intent intent = new Intent(DashBoard_Activity.this, Mis_Activity.class);
                        startActivity(intent);
                    } else {
                        error_dialog.show_error_dialog(DashBoard_Activity.this, "You do not have authorization to access MIS");
                    }
                } else if (web[+position].equals("Alert Log")) {
                    Intent intent = new Intent(DashBoard_Activity.this, Alert_Log_Activity.class);
                    startActivity(intent);
                } else if (web[+position].equals("Calibration")) {
                    Intent intent = new Intent(DashBoard_Activity.this, Calibration_Activity.class);
                    startActivity(intent);
                } else if (web[+position].equals("Maintenance Plan")) {
                    Intent intent = new Intent(DashBoard_Activity.this, Maintenance_Plan_Activity.class);
                    startActivity(intent);
                }
            }
        });

        logout_imageview.setOnClickListener(this);
        refresh_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logout_imageview) {
            logout_dialog = new Dialog(DashBoard_Activity.this);
            logout_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            logout_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logout_dialog.setCancelable(false);
            logout_dialog.setCanceledOnTouchOutside(false);
            logout_dialog.setContentView(R.layout.logout_dialog);
            final TextView favoritetextview = (TextView) logout_dialog.findViewById(R.id.description_textview);
            Button confirm = (Button) logout_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button) logout_dialog.findViewById(R.id.no_button);
            favoritetextview.setText(getString(R.string.logout));
            logout_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout_dialog.dismiss();
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    logout_dialog.dismiss();
                    Intent logout_intent = new Intent(DashBoard_Activity.this, Login_Activity.class);
                    logout_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logout_intent);
                    DashBoard_Activity.this.finish();
                }
            });
        } else if (v == refresh_imageview) {
            cd = new ConnectionDetector(getApplicationContext());
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                refresh_message_dialog = new Dialog(DashBoard_Activity.this);
                refresh_message_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                refresh_message_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                refresh_message_dialog.setCancelable(false);
                refresh_message_dialog.setCanceledOnTouchOutside(false);
                refresh_message_dialog.setContentView(R.layout.decision_dialog);
                final TextView description_textview = (TextView) refresh_message_dialog.findViewById(R.id.description_textview);
                Button confirm = (Button) refresh_message_dialog.findViewById(R.id.yes_button);
                Button cancel = (Button) refresh_message_dialog.findViewById(R.id.no_button);
                description_textview.setText(getString(R.string.refresh_text));
                refresh_message_dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh_message_dialog.dismiss();
                        Intent intialload_intent = new Intent(DashBoard_Activity.this, InitialLoad_Activity.class);
                        intialload_intent.putExtra("From", "");
                        startActivity(intialload_intent);
                        DashBoard_Activity.this.finish();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh_message_dialog.dismiss();
                    }
                });
            } else {
                network_connection_dialog.show_network_connection_dialog(DashBoard_Activity.this);
            }
        }
    }

}
