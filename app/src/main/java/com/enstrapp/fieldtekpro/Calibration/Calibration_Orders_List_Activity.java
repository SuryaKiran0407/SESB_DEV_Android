package com.enstrapp.fieldtekpro.Calibration;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.Calibration;
import com.enstrapp.fieldtekpro.Initialload.Orders;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calibration_Orders_List_Activity extends AppCompatActivity {
    ImageView back_imageview;
    RecyclerView recyclerview;
    TextView no_data_textview, title_textview;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private List<Orders_List_Object> ordersList = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    OrdersAdapter ordersAdapter;
    Dialog decision_dialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    String equip_id = "", plant = "", DORD_Status = "", Calibration_Status = "";
    FloatingActionButton refresh_fab_button;
    SwipeRefreshLayout swiperefreshlayout;
    SharedPreferences FieldTekPro_SharedPref;
    static String DateFormat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration_orders_list_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equip_id = extras.getString("equipment_id");
            plant = extras.getString("plant_id");
        }

        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        title_textview = (TextView) findViewById(R.id.title_textview);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        FieldTekPro_SharedPref = getApplicationContext()
                .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        DateFormat = FieldTekPro_SharedPref.getString("Date_Format", null);

       // new Get_Calib_Orders_Data().execute();



        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calibration_Orders_List_Activity.this.finish();
            }
        });


        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh_Data();
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent, R.color.red, R.color.blue, R.color.black, R.color.orange);

        refresh_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh_Data();
            }
        });


        refresh_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh_Data();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        new Get_Calib_Orders_Data().execute();
    }


    public void Refresh_Data() {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(Calibration_Orders_List_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Calibration_Orders_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
            description_textview.setText(getString(R.string.refresh_text));
            decision_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                    swiperefreshlayout.setRefreshing(false);
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                    swiperefreshlayout.setRefreshing(false);
                    new Get_Calibration_Data().execute();
                }
            });
        } else {
            network_connection_dialog.show_network_connection_dialog(Calibration_Orders_List_Activity.this);
        }
    }


    private class Get_Calib_Orders_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Calibration_Orders_List_Activity.this, getResources().getString(R.string.loading));
            ordersList.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtQinspData where Equnr = ? group by Aufnr", new String[]{equip_id});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    do {
                        String vkatart = "";
                        try {
                            Cursor cursor1 = null;
                            cursor1 = FieldTekPro_db.rawQuery("select * from EtQudData Where Aufnr = ?", new String[]{cursor.getString(1)});
                            if (cursor1 != null && cursor1.getCount() > 0) {
                                if (cursor1.moveToFirst()) {
                                    do {
                                        vkatart = cursor1.getString(8);
                                        Orders_List_Object olo = new Orders_List_Object(
                                                cursor.getString(1),
                                                cursor.getString(1),
                                                cursor.getString(25),
                                                cursor.getString(32),
                                                cursor.getString(37),
                                                cursor.getString(41),
                                                vkatart);
                                        ordersList.add(olo);
                                    }
                                    while (cursor1.moveToNext());
                                }
                            } else {
                                cursor1.close();
                                vkatart = "";
                            }
                        } catch (Exception e) {
                            vkatart = "";
                        }

                        /*Cursor cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{Aufnr});
                        if (cursor1 != null && cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    Orders_List_Object olo = new Orders_List_Object(
                                            cursor1.getString(2),
                                            cursor1.getString(33),
                                            cursor1.getString(33),
                                            cursor1.getString(4),
                                            cursor1.getString(39),
                                            cursor1.getString(25),
                                            vkatart);
                                    ordersList.add(olo);
                                }
                                while (cursor1.moveToNext());
                            }
                        } else {
                            cursor1.close();
                        }*/

                    }
                    while
                            (
                            cursor.moveToNext()
                            );
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (ordersList.size() > 0) {
                title_textview.setText(getResources().getString(R.string.calibration) + " (" + ordersList.size() + ")");
                ordersAdapter = new OrdersAdapter(Calibration_Orders_List_Activity.this, ordersList);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_Orders_List_Activity.this);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(ordersAdapter);
                ordersAdapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.calibration) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    private class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
        private Context mContext;
        private List<Orders_List_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView shortText_tv, basicStart_tv, status_tv, orderId_tv, priority_tv, woco_status_tv;
            FrameLayout orders_fl;
            LinearLayout layout, release_layout, teco_layout, cnf_ll;
            ImageView status_iv, woco_iv;

            public MyViewHolder(View view) {
                super(view);
                shortText_tv = view.findViewById(R.id.description_textview);
                basicStart_tv = view.findViewById(R.id.date_time_textview);
                status_tv = view.findViewById(R.id.status_type_textview);
                orders_fl = view.findViewById(R.id.orders_fl);
                orderId_tv = view.findViewById(R.id.id_textview);
                priority_tv = view.findViewById(R.id.priority_textview);
                cnf_ll = view.findViewById(R.id.cnf_ll);
                teco_layout = view.findViewById(R.id.teco_layout);
                layout = (LinearLayout) view.findViewById(R.id.layout);
                release_layout = view.findViewById(R.id.release_layout);
                status_iv = view.findViewById(R.id.status_iv);
                woco_status_tv = (TextView) view.findViewById(R.id.woco_status_tv);
                woco_iv = (ImageView) view.findViewById(R.id.woco_iv);
            }
        }

        public OrdersAdapter(Context mContext, List<Orders_List_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_listview_const, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Orders_List_Object olo = orders_list_data.get(position);
            holder.orderId_tv.setText(olo.getOrderId());
            holder.shortText_tv.setText(olo.getOrderShortText());
            holder.basicStart_tv.setText(dateFormat(olo.getBasicStartDate()));
            holder.status_tv.setText(olo.getOrderStatus());
            holder.priority_tv.setText(olo.getPriorityText());
            holder.woco_status_tv.setText(olo.getVkatart());
            holder.priority_tv.setVisibility(View.GONE);
            holder.basicStart_tv.setVisibility(View.VISIBLE);


            holder.teco_layout.setVisibility(View.GONE);
            holder.release_layout.setVisibility(View.GONE);
            holder.cnf_ll.setVisibility(View.GONE);
            holder.status_iv.setVisibility(View.GONE);

            if (holder.status_tv.getText().toString().equalsIgnoreCase("CRTD")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CANC")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.red_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("REL")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.blue_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("ISSU")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.lightgren_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("DLFL")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.red_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CLSD")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.green_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CNF")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.orange_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
            } else {
                holder.status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            }

            if (holder.woco_status_tv.getText().toString() != null && !holder.woco_status_tv.getText().toString().equals("")) {
                holder.woco_iv.setVisibility(View.VISIBLE);
            } else {
                holder.woco_iv.setVisibility(View.GONE);
            }

            holder.orders_fl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent operation_intent = new Intent(Calibration_Orders_List_Activity.this, Calibration_Orders_Operations_List_Activity.class);
                    operation_intent.putExtra("order_id", holder.orderId_tv.getText().toString());
                    operation_intent.putExtra("plant_id", olo.getWerks());
                    operation_intent.putExtra("equip_id", equip_id);
                    startActivity(operation_intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }


    private class Orders_List_Object {
        private String orderId;
        private String priorityText;
        private String basicStartDate;
        private String orderShortText;
        private String orderStatus;
        private String Werks;
        private String vkatart;

        public Orders_List_Object(String orderId, String priorityText, String basicStartDate, String orderShortText, String orderStatus, String Werks, String vkatart) {
            this.orderId = orderId;
            this.priorityText = priorityText;
            this.basicStartDate = basicStartDate;
            this.orderShortText = orderShortText;
            this.orderStatus = orderStatus;
            this.Werks = Werks;
            this.vkatart = vkatart;
        }

        public String getVkatart() {
            return vkatart;
        }

        public void setVkatart(String vkatart) {
            this.vkatart = vkatart;
        }

        public String getWerks() {
            return Werks;
        }

        public void setWerks(String werks) {
            Werks = werks;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPriorityText() {
            return priorityText;
        }

        public void setPriorityText(String priorityText) {
            this.priorityText = priorityText;
        }

        public String getBasicStartDate() {
            return basicStartDate;
        }

        public void setBasicStartDate(String basicStartDate) {
            this.basicStartDate = basicStartDate;
        }

        public String getOrderShortText() {
            return orderShortText;
        }

        public void setOrderShortText(String orderShortText) {
            this.orderShortText = orderShortText;
        }

    }


    private String dateFormat(String date) {
        if (!date.equals("00000000")) {
            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd,yyyy");
            Date date1;
            try {
                date1 = inputFormat.parse(date);
                return outputFormat.format(date1);
            } catch (ParseException e) {
                return "";
            }
        } else {
            return "";
        }
    }


    public class Get_Calibration_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Calibration_Orders_List_Activity.this, getResources().getString(R.string.refresh_calibration));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Calibration_Status = Calibration.Get_Calibration_Data(Calibration_Orders_List_Activity.this, "REFR");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            new Get_DORD_Data().execute();
        }
    }


    public class Get_DORD_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                DORD_Status = Orders.Get_DORD_Data(Calibration_Orders_List_Activity.this, "REFR", "");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v("kiran_DORD_Status", DORD_Status + "...");
            custom_progress_dialog.dismiss_progress_dialog();
            new Get_Calib_Orders_Data().execute();
        }
    }
}
