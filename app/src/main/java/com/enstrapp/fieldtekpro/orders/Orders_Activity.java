package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Orders;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.getOrderDetail.GetOrderDetail;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notifications_WorkCenter_Select_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static android.view.View.GONE;

public class Orders_Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    TextView orders_tv;
    ImageButton back_iv;
    String orderUUID = "", orderId = "", orderStatus = "", DO_STATUS = "", statusId = "",
            priorityId = "", ordrTypID = "", ordrStDt = "", ordrEdDt = "", Equipment = "",
            FuncLoc = "",shrtTxt = "";
    public SearchView search;
    TextView searchview_textview, title_tv;
    RecyclerView list_recycleview;
    SwipeRefreshLayout swiperefreshlayout;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    TextView ordrType_tv, status_tv, priority_tv, wrkCntr_tv;
    Button strtDt_bt, endDt_bt;
    private List<Orders_Object> ordersList = new ArrayList<>();
    private List<Orders_Object> ordersList_ad = new ArrayList<>();
    ImageView back_imageview;
    Dialog decision_dialog;
    OrdersAdapter ordersAdapter;
    LinearLayout no_data_layout;
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    static final int ORDR_TYP = 1;
    static final int WRKCNTR_ID = 2;
    static final int PRIORITY = 3;
    static final int STATUS_ID = 4;
    static final int FRMDT = 5;
    static final int TODT = 6;
    String TYP = "", Iwerk = "";
    SysStsAdapter sysStsAdapter;
    static final int EQUIP_SCAN = 217;
    FloatingActionButton map_fab_button, create_fab_button, filter_fab_button, refresh_fab_button,
            sort_fab_button,scan_fab_button;
    FloatingActionMenu floatingActionMenu;
    String person_responsible_id = "", pers_resp_status = "", sort_selected = "",
            attachment_clicked_status = "", filt_selected_wckt_ids = "", filt_wckt_text = "",
            filt_wckt_ids = "", filt_selected_status_ids = "", filt_status_ids = "",
            filt_selected_prior_ids = "", filt_priority_ids = "", filt_selected_notif_ids = "",
            filt_notification_ids = "";
    Button filt_notif_type_button, filt_priority_type_button, filt_status_type_button,
            filt_workcenter_type_button;
    static final int fil_notif_type = 7, fil_prior_type = 8, fil_status_type = 9,
            fil_wckt_type = 10, scan_status = 11;
    String equipment_id = "", functionlocation_id = "";
    boolean clearAll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equipment_id = extras.getString("equipment_id");
            functionlocation_id = extras.getString("functionlocation_id");
        }

        search = findViewById(R.id.orders_sv);
        orders_tv = findViewById(R.id.orders_tv);
        back_iv = findViewById(R.id.back_iv);
        swiperefreshlayout = findViewById(R.id.orders_srl);
        floatingActionMenu = findViewById(R.id.floatingActionMenu);
        create_fab_button = findViewById(R.id.create_fab_button);
        filter_fab_button = findViewById(R.id.filter_fab_button);
        sort_fab_button = findViewById(R.id.sort_fab_button);
        refresh_fab_button = findViewById(R.id.refresh_fab_button);
        list_recycleview = findViewById(R.id.orders_rv);
        no_data_layout = findViewById(R.id.ordersNoData_ll);
        title_tv = findViewById(R.id.orders_tv);
        map_fab_button = (FloatingActionButton) findViewById(R.id.map_fab_button);
        scan_fab_button = findViewById(R.id.scan_fab_button);

        DATABASE_NAME = Orders_Activity.this.getString(R.string.database_name);
        App_db = Orders_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text",
                null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);

        create_fab_button.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        refresh_fab_button.setOnClickListener(this);
        sort_fab_button.setOnClickListener(this);
        filter_fab_button.setOnClickListener(this);
        map_fab_button.setOnClickListener(this);
        scan_fab_button.setOnClickListener(this);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh_Orders_Data();
            }
        });
        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent,
                R.color.red, R.color.blue, R.color.black, R.color.orange);

        try {
            Cursor cursor = App_db.rawQuery("select * from GET_USER_DATA", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        person_responsible_id = cursor.getString(9);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }

        /*Authorization For Order Create*/
        String auth_create_status = Authorizations
                .Get_Authorizations_Data(Orders_Activity.this, "W", "I");
        if (auth_create_status.equalsIgnoreCase("true")) {
            create_fab_button.setVisibility(View.VISIBLE);
        } else {
            create_fab_button.setVisibility(View.GONE);
        }
        /*Authorization For Order Create*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Get_Order_List().execute();
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            ordersList_ad.clear();
           // final List<Orders_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < ordersList.size(); i++) {
                if (ordersList.get(i).getOrderId().toLowerCase().contains(query) ||
                        ordersList.get(i).getOrderShortText().toLowerCase().contains(query) ||
                        ordersList.get(i).getOrderStatus().toLowerCase().contains(query) ||
                        ordersList.get(i).getPriorityText().toLowerCase().contains(query)) {
                    Orders_Object blo = ordersList.get(i);
                    ordersList_ad.add(blo);
                }
            }
            filterData( ordersList_ad);
            if (ordersList_ad.size() > 0) {
                if (ordersAdapter != null) {
                    ordersAdapter.notifyDataSetChanged();
                    no_data_layout.setVisibility(GONE);
                    swiperefreshlayout.setVisibility(View.VISIBLE);
                    title_tv.setText(getString(R.string.orders_count, String.valueOf(ordersList_ad.size())));
                } else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Orders_Activity.this);
                    list_recycleview.setLayoutManager(layoutManager);
                    ordersAdapter = new OrdersAdapter(Orders_Activity.this, ordersList_ad);
                    list_recycleview.setAdapter(ordersAdapter);
                    no_data_layout.setVisibility(GONE);
                    swiperefreshlayout.setVisibility(View.VISIBLE);
                    title_tv.setText(getString(R.string.orders_count, String.valueOf(ordersList_ad.size())));
                }
            } else {
                title_tv.setText(getString(R.string.orders));
                no_data_layout.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.create_fab_button):
                floatingActionMenu.close(true);
                Intent creatIntent = new Intent(Orders_Activity.this,
                        Orders_Create_Activity.class);
                creatIntent.putExtra("order", "C");
                startActivity(creatIntent);
                break;

            case (R.id.refresh_fab_button):
                floatingActionMenu.close(true);
                Refresh_Orders_Data();
                break;


            case (R.id.map_fab_button):
                floatingActionMenu.close(true);
                if (ordersList.size() > 0) {
                    Intent intent = new Intent(Orders_Activity.this,
                            Orders_MapView_Activity.class);
                    startActivity(intent);
                } else {
                    error_dialog.show_error_dialog(Orders_Activity.this,
                            getString(R.string.ordrs_formap));
                }
                break;

            case (R.id.sort_fab_button):
                floatingActionMenu.close(true);
                if (ordersList_ad.size() > 0) {
                    final Dialog dialog =
                            new Dialog(Orders_Activity.this, R.style.AppThemeDialog_Dark);
                    dialog.getWindow()
                            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.notifcation_order_sort);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    TextView sort_clear_textview = (TextView) dialog.findViewById(R.id.clearAll);
                    final RadioGroup sort_radioGroup = dialog.findViewById(R.id.sort_rdg);
                    final RadioButton sortAtoZ = (RadioButton) dialog.findViewById(R.id.sortAtoZ);
                    final RadioButton sortZtoA = (RadioButton) dialog.findViewById(R.id.sortZtoA);
                    final RadioButton criticaltoLow = dialog.findViewById(R.id.criticaltoLow);
                    final RadioButton lowtoCritical = dialog.findViewById(R.id.lowtoCritical);
                    final RadioButton stDt1to9 = (RadioButton) dialog.findViewById(R.id.stDt1to9);
                    final RadioButton stDt9to1 = (RadioButton) dialog.findViewById(R.id.stDt9to1);
                    final RadioButton No1to9 = (RadioButton) dialog.findViewById(R.id.No1to9);
                    final RadioButton No9to1 = (RadioButton) dialog.findViewById(R.id.No9to1);
                    TextView text_textview = (TextView) dialog.findViewById(R.id.text_textview);
                    text_textview.setText(getResources().getString(R.string.order_number));
                    String Sort_Status = sort_selected;
                    if (Sort_Status != null && !Sort_Status.equals("")) {
                        if (Sort_Status.equalsIgnoreCase("description_sort1")) {
                            sortAtoZ.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("description_sort2")) {
                            sortZtoA.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("priority_sort1")) {
                            criticaltoLow.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("priority_sort2")) {
                            lowtoCritical.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("date_sort1")) {
                            stDt1to9.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("date_sort2")) {
                            stDt9to1.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("id_sort1")) {
                            No1to9.setChecked(true);
                        } else if (Sort_Status.equalsIgnoreCase("id_sort2")) {
                            No9to1.setChecked(true);
                        }
                    } else {
                        sortAtoZ.setChecked(false);
                        sortZtoA.setChecked(false);
                        criticaltoLow.setChecked(false);
                        lowtoCritical.setChecked(false);
                        stDt1to9.setChecked(false);
                        stDt9to1.setChecked(false);
                        No1to9.setChecked(false);
                        No9to1.setChecked(false);
                    }
                    dialog.show();
                    sort_clear_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sortAtoZ.setChecked(false);
                            sortZtoA.setChecked(false);
                            criticaltoLow.setChecked(false);
                            lowtoCritical.setChecked(false);
                            stDt1to9.setChecked(false);
                            stDt9to1.setChecked(false);
                            No1to9.setChecked(false);
                            No9to1.setChecked(false);
                            Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                @Override
                                public int compare(Orders_Object rhs, Orders_Object lhs) {
                                    return lhs.getBasicStartDate()
                                            .compareTo(rhs.getBasicStartDate());
                                }
                            });
                            ordersAdapter.notifyDataSetChanged();
                            sort_selected = "";
                            dialog.dismiss();
                        }
                    });
                    sort_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.sortAtoZ) {
                                sort_selected = "description_sort1";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return rhs.getOrderShortText()
                                                .compareTo(lhs.getOrderShortText());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.sortZtoA) {
                                sort_selected = "description_sort2";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return lhs.getOrderShortText()
                                                .compareTo(rhs.getOrderShortText());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.criticaltoLow) {
                                sort_selected = "priority_sort1";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return rhs.getPriority()
                                                .compareTo(lhs.getPriority());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.lowtoCritical) {
                                sort_selected = "priority_sort2";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return lhs.getPriority().compareTo(rhs.getPriority());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.stDt1to9) {
                                sort_selected = "date_sort1";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return rhs.getBasicStartDate()
                                                .compareTo(lhs.getBasicStartDate());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.stDt9to1) {
                                sort_selected = "date_sort2";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return lhs.getBasicStartDate()
                                                .compareTo(rhs.getBasicStartDate());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.No1to9) {
                                sort_selected = "id_sort1";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return rhs.getOrderId().compareTo(lhs.getOrderId());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else if (checkedId == R.id.No9to1) {
                                sort_selected = "id_sort2";
                                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                                    @Override
                                    public int compare(Orders_Object rhs, Orders_Object lhs) {
                                        return lhs.getOrderId().compareTo(rhs.getOrderId());
                                    }
                                });
                                ordersAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    error_dialog.show_error_dialog(Orders_Activity.this,
                            getString(R.string.ordrs_forsort));
                }
                break;

            case (R.id.filter_fab_button):
                floatingActionMenu.close(true);
                final Dialog dialog =
                        new Dialog(Orders_Activity.this, R.style.AppThemeDialog_Dark);
                dialog.getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.notifications_filter_dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                TextView type_textview = (TextView) dialog.findViewById(R.id.type_textview);
                type_textview.setText(getResources().getString(R.string.order_type));
                filt_notif_type_button = (Button) dialog.findViewById(R.id.notif_type_button);
                filt_notif_type_button.setText(filt_notification_ids);
                filt_priority_type_button = (Button) dialog.findViewById(R.id.priority_type_button);
                filt_priority_type_button.setText(filt_priority_ids);
                filt_status_type_button = (Button) dialog.findViewById(R.id.status_type_button);
                filt_status_type_button.setText(filt_status_ids);
                filt_workcenter_type_button = dialog.findViewById(R.id.workcenter_type_button);
                filt_workcenter_type_button.setText(filt_wckt_ids);
                TextView clearAll_textview = (TextView) dialog.findViewById(R.id.clearAll_textview);
                final CheckBox attachments_checkbox = dialog.findViewById(R.id.attachments_checkbox);
                final CheckBox pers_resp_checkbox = dialog.findViewById(R.id.pers_resp_checkbox);
                if (attachment_clicked_status.equalsIgnoreCase("X")) {
                    attachments_checkbox.setChecked(true);
                } else {
                    attachments_checkbox.setChecked(false);
                }
                if (pers_resp_status.equalsIgnoreCase("X")) {
                    pers_resp_checkbox.setChecked(true);
                } else {
                    pers_resp_checkbox.setChecked(false);
                }
                Button filterBt = (Button) dialog.findViewById(R.id.filterBt);
                Button closeBt = (Button) dialog.findViewById(R.id.closeBt);
                dialog.show();
                filt_notif_type_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent notification_type_intent =
                                new Intent(Orders_Activity.this,
                                        Orders_Type_Select_Activity.class);
                        notification_type_intent.putExtra("request_id",
                                Integer.toString(fil_notif_type));
                        notification_type_intent.putExtra("filt_notification_ids",
                                filt_notification_ids);
                        startActivityForResult(notification_type_intent, fil_notif_type);
                    }
                });
                filt_priority_type_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent notification_type_intent =
                                new Intent(Orders_Activity.this,
                                        Orders_Priority_Select_Activity.class);
                        notification_type_intent.putExtra("request_id",
                                Integer.toString(fil_prior_type));
                        notification_type_intent.putExtra("filt_ids", filt_priority_ids);
                        startActivityForResult(notification_type_intent, fil_prior_type);
                    }
                });
                filt_status_type_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent notification_type_intent =
                                new Intent(Orders_Activity.this,
                                        Orders_Status_Select_Activity.class);
                        notification_type_intent.putExtra("request_id",
                                Integer.toString(fil_status_type));
                        notification_type_intent.putExtra("filt_ids", filt_status_ids);
                        startActivityForResult(notification_type_intent, fil_status_type);
                    }
                });
                filt_workcenter_type_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent notification_type_intent =
                                new Intent(Orders_Activity.this,
                                        Notifications_WorkCenter_Select_Activity.class);
                        notification_type_intent.putExtra("request_id",
                                Integer.toString(fil_wckt_type));
                        notification_type_intent.putExtra("filt_text", filt_wckt_text);
                        notification_type_intent.putExtra("filt_ids", filt_wckt_ids);
                        startActivityForResult(notification_type_intent, fil_wckt_type);
                    }
                });
                clearAll_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pers_resp_status = "";
                        filt_notification_ids = "";
                        filt_priority_ids = "";
                        filt_status_ids = "";
                        filt_wckt_text = "";
                        filt_wckt_ids = "";
                        attachment_clicked_status = "";
                        clearAll=true;
                        dialog.dismiss();
                        new Get_Order_List().execute();
                    }
                });
                closeBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                filterBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attachments_checkbox.isChecked()) {
                            attachment_clicked_status = "X";
                        } else {
                            attachment_clicked_status = "";
                        }
                        if (pers_resp_checkbox.isChecked()) {
                            pers_resp_status = "X";
                        } else {
                            pers_resp_status = "";
                        }
                        if ((filt_notification_ids != null && !filt_notification_ids.equals("")) ||
                                (filt_priority_ids != null && !filt_priority_ids.equals("")) ||
                                (filt_status_ids != null && !filt_status_ids.equals("")) ||
                                (filt_wckt_ids != null && !filt_wckt_ids.equals("")) ||
                                (attachment_clicked_status != null &&
                                        !attachment_clicked_status.equals("") ||
                                        (pers_resp_status != null && !pers_resp_status.equals("")))) {

                            filt_selected_notif_ids = filt_notification_ids.trim();
                            if (filt_selected_notif_ids.contains(",")) {
                                filt_selected_notif_ids = filt_selected_notif_ids.replace(",",
                                        "|");
                                filt_selected_notif_ids = filt_selected_notif_ids.substring(0,
                                        filt_selected_notif_ids.length() - 1);
                            }

                            filt_selected_prior_ids = filt_priority_ids.trim();
                            if (filt_selected_prior_ids.contains(",")) {
                                filt_selected_prior_ids = filt_selected_prior_ids.replace(",",
                                        "|");
                                filt_selected_prior_ids = filt_selected_prior_ids.substring(0,
                                        filt_selected_prior_ids.length() - 1);
                            }

                            filt_selected_status_ids = filt_status_ids.trim();
                            if (filt_selected_status_ids.contains(",")) {
                                filt_selected_status_ids = filt_selected_status_ids
                                        .replace(",", "|");
                                filt_selected_status_ids = filt_selected_status_ids
                                        .substring(0, filt_selected_status_ids.length() - 1);
                            }

                            filt_selected_wckt_ids = filt_wckt_ids.trim();
                            if (filt_selected_wckt_ids.contains(",")) {
                                filt_selected_wckt_ids = filt_selected_wckt_ids
                                        .replace(",", "|");
                                filt_selected_wckt_ids = filt_selected_wckt_ids
                                        .substring(0, filt_selected_wckt_ids.length() - 1);
                            }

                            final StringBuffer filt_selected_persresp_ids = new StringBuffer();
                            if (pers_resp_checkbox.isChecked()) {
                                filt_selected_persresp_ids.append(person_responsible_id);
                            }

                           /* CollectionUtils.filter(ordersList, new Predicate() {
                                @Override
                                public boolean evaluate(Object o) {
                                    return ((Orders_Object) o).getOrderType()
                                            .matches(filt_selected_notif_ids) &&
                                            ((Orders_Object) o).getPriority()
                                                    .matches(filt_selected_prior_ids) &&
                                            ((Orders_Object) o).getOrderStatus()
                                                    .matches(filt_selected_status_ids) &&
                                            ((Orders_Object) o).getWorkCenter()
                                                    .matches(filt_selected_wckt_ids) &&
                                            ((Orders_Object) o).getAttachment()
                                                    .matches(attachment_clicked_status) &&
                                            ((Orders_Object) o).getPernar()
                                                    .matches(filt_selected_persresp_ids.toString());
                                }
                            });*/
                            ordersList_ad.clear();
                            ordersList_ad.addAll(ordersList);
                            filterData(ordersList_ad);

                            if (ordersList_ad.size() > 0) {
                                no_data_layout.setVisibility(View.GONE);
                                swiperefreshlayout.setVisibility(View.VISIBLE);
                                title_tv.setText(getString(R.string.orders_count, String.valueOf(ordersList_ad.size())));
                                ordersAdapter.notifyDataSetChanged();
                            } else {
                                no_data_layout.setVisibility(View.VISIBLE);
                                title_tv.setText(getString(R.string.orders));
                                swiperefreshlayout.setVisibility(View.GONE);
                            }
                            dialog.dismiss();
                        } else {
                            error_dialog.show_error_dialog(Orders_Activity.this,
                                    getString(R.string.notif_filter_atleast));
                        }
                    }
                });
                break;

            case (R.id.scan_fab_button):
                Intent scanintent = new Intent(Orders_Activity.this, Barcode_Scanner_Activity.class);
                startActivityForResult(scanintent, EQUIP_SCAN);
                break;

            case (R.id.back_iv):
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (floatingActionMenu.isOpened()) {
            floatingActionMenu.close(true);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void Refresh_Orders_Data() {
        cd = new ConnectionDetector(Orders_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(Orders_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Orders_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
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
                    new Get_Orders_Refresh_Data().execute();
                }
            });
        } else {
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(Orders_Activity.this);
        }
    }

    private class Get_Orders_Refresh_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Orders_Activity.this, getResources().getString(R.string.refresh_orders));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                DO_STATUS = Orders.Get_DORD_Data(Orders_Activity.this, "REFR",
                        "");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss_progress_dialog();
            new Get_Order_List().execute();
        }
    }

    private class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
        private Context mContext;
        private List<Orders_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView uuid_tv, plant_tv, orderId_tv, shortText_tv, basicStart_tv, status_tv,
                    work_complete_tv, woco_status_tv, priority_tv, attachment_tv, release_tv,
                    usr02_tv, pernr_status, uuid_textview, edDt_tv, equipment_tv, funcLoc_tv,
                    auth_rel_status, auth_cnf_status, auth_teco_status, auth_woco_status;
            ImageView attachment_iv, status_iv, woco_iv, release_iv;
            LinearLayout layout, release_layout, teco_layout, cnf_ll;
            FrameLayout orders_fl;

            public MyViewHolder(View view) {
                super(view);
                orderId_tv = view.findViewById(R.id.id_textview);
                shortText_tv = view.findViewById(R.id.description_textview);
                basicStart_tv = view.findViewById(R.id.date_time_textview);
                status_tv = view.findViewById(R.id.status_type_textview);
                attachment_iv = view.findViewById(R.id.attachment_iv);
                priority_tv = view.findViewById(R.id.priority_textview);
                plant_tv = view.findViewById(R.id.plant_textview);
                release_layout = view.findViewById(R.id.release_layout);
                cnf_ll = view.findViewById(R.id.cnf_ll);
                teco_layout = view.findViewById(R.id.teco_layout);
                layout = view.findViewById(R.id.layout);
                uuid_tv = view.findViewById(R.id.uuid_textview);
                orders_fl = view.findViewById(R.id.orders_fl);
                work_complete_tv = view.findViewById(R.id.work_complete_tv);
                woco_status_tv = view.findViewById(R.id.woco_status_tv);
                status_iv = view.findViewById(R.id.status_iv);
                woco_iv = view.findViewById(R.id.woco_iv);
                attachment_tv = view.findViewById(R.id.attachment_tv);
                release_tv = view.findViewById(R.id.release_tv);
                release_iv = view.findViewById(R.id.release_iv);
                usr02_tv = view.findViewById(R.id.usr02_tv);
                uuid_textview = view.findViewById(R.id.uuid_textview);
                edDt_tv = view.findViewById(R.id.edDt_tv);
                equipment_tv = view.findViewById(R.id.equipment_tv);
                funcLoc_tv = view.findViewById(R.id.funcLoc_tv);
                auth_rel_status = (TextView) view.findViewById(R.id.auth_rel_status);
                auth_cnf_status = (TextView) view.findViewById(R.id.auth_cnf_status);
                auth_teco_status = (TextView) view.findViewById(R.id.auth_teco_status);
                auth_woco_status = (TextView) view.findViewById(R.id.auth_woco_status);
                pernr_status = (TextView) view.findViewById(R.id.pernr_status);
            }
        }

        public OrdersAdapter(Context mContext, List<Orders_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_listview_const, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Orders_Object olo = orders_list_data.get(position);
            holder.orderId_tv.setText(olo.getOrderId());
            holder.shortText_tv.setText(olo.getOrderShortText());
            shrtTxt = holder.shortText_tv.getText().toString();
            holder.basicStart_tv.setText(dateFormat(olo.getBasicStartDate()));
            if (!olo.getOrderStatus().equals("OFL"))
                holder.status_tv.setText(olo.getOrderStatus());
            else
                holder.status_tv.setText("");
            holder.priority_tv.setText(olo.getPriorityText());
            holder.plant_tv.setText(olo.getOrderPlant());
            holder.uuid_tv.setText(olo.getUUID());
            holder.woco_status_tv.setText(olo.getWocoStatus());
            holder.work_complete_tv.setText(olo.getWorkCmplt());
            holder.attachment_tv.setText(olo.getAttachment());
            holder.attachment_tv.setTag(position);
            holder.usr02_tv.setText(olo.getUsr02());
            holder.uuid_textview.setText(olo.getUUID());
            holder.edDt_tv.setText(olo.getEndDate());
            holder.equipment_tv.setText(olo.getEquipment());
            holder.funcLoc_tv.setText(olo.getFunctionLocation());
            holder.release_layout.setTag(position);
            holder.auth_rel_status.setText(olo.getRel_status());
            holder.auth_cnf_status.setText(olo.getCnf_status());
            holder.auth_teco_status.setText(olo.getTeco_status());
            holder.auth_woco_status.setText(olo.getWoco_status());
            holder.pernr_status.setText(olo.getPernar());

            if (holder.priority_tv.getText().toString().contains("Critical")) {
                holder.priority_tv.setVisibility(View.VISIBLE);
                holder.priority_tv.setBackground(getDrawable(R.drawable.critical_round));
            } else if (holder.priority_tv.getText().toString().contains("High")) {
                holder.priority_tv.setVisibility(View.VISIBLE);
                holder.priority_tv.setBackground(getDrawable(R.drawable.high_round));
            } else if (holder.priority_tv.getText().toString().contains("Medium")) {
                holder.priority_tv.setVisibility(View.VISIBLE);
                holder.priority_tv.setBackground(getDrawable(R.drawable.medium_round));
            } else if (holder.priority_tv.getText().toString().contains("Low")) {
                holder.priority_tv.setVisibility(View.VISIBLE);
                holder.priority_tv.setBackground(getDrawable(R.drawable.low_round));
            } else {
                holder.priority_tv.setVisibility(View.GONE);
            }

            if (holder.status_tv.getText().toString().equalsIgnoreCase("CRTD")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
                holder.teco_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
                if (holder.auth_rel_status.getText().toString().equalsIgnoreCase("X")) {
                    holder.release_layout.setVisibility(View.VISIBLE);
                } else {
                    holder.release_layout.setVisibility(View.GONE);
                }
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CANC")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.red_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("REL")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.blue_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                if (holder.auth_cnf_status.getText().toString().equalsIgnoreCase("X")) {
                    holder.cnf_ll.setVisibility(View.VISIBLE);
                } else {
                    holder.cnf_ll.setVisibility(View.GONE);
                }
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("ISSU")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.lightgren_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("DLFL")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.red_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CLSD")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.green_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
            } else if (holder.status_tv.getText().toString().equalsIgnoreCase("CNF")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.orange_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.white));
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
                if (holder.auth_teco_status.getText().toString().equalsIgnoreCase("X")) {
                    holder.teco_layout.setVisibility(View.VISIBLE);
                } else {
                    holder.teco_layout.setVisibility(View.GONE);
                }
            }else   if (holder.orderId_tv.getText().toString().startsWith("ORD")) {
                holder.status_tv.setBackground(getDrawable(R.drawable.offline_status));
//                    holder.status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
                holder.teco_layout.setVisibility(GONE);
                holder.release_layout.setVisibility(GONE);
                holder.cnf_ll.setVisibility(GONE);

            }
            else {
                holder.status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                holder.status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.cnf_ll.setVisibility(View.GONE);
            }

            if (!(olo.getWorkCmplt().equals("X"))) {
                holder.woco_iv.setVisibility(View.VISIBLE);
            } else {
                holder.woco_iv.setVisibility(View.GONE);
            }

            if (holder.woco_status_tv.getText().toString().equals("X")) {
                if (holder.auth_woco_status.getText().toString()
                        .equalsIgnoreCase("X")) {
                    holder.woco_iv.setVisibility(View.VISIBLE);
                    holder.woco_iv.setImageDrawable(getDrawable(R.drawable.ic_success_icon));
                    holder.woco_iv.setEnabled(false);
                } else {
                    holder.woco_iv.setVisibility(View.GONE);
                }
            }

            holder.woco_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderId = holder.orderId_tv.getText().toString();
                    confirmationDialog(getString(R.string.work_compl), "WOCO",
                            "");
                }
            });

            if (olo.getAttachment().equals("X"))
                holder.attachment_iv.setVisibility(View.VISIBLE);
            else
                holder.attachment_iv.setVisibility(View.GONE);

            holder.orders_fl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderUUID = holder.uuid_tv.getText().toString();
                    orderId = holder.orderId_tv.getText().toString();
                    orderStatus = holder.status_tv.getText().toString();
                    if (holder.equipment_tv.getText().toString().equals(""))
                        Iwerk = getfuncIwerk(holder.funcLoc_tv.getText().toString());
                    else
                        Iwerk = getIwerk(holder.equipment_tv.getText().toString());

                    new Get_Order_Data().execute();
                }
            });

            holder.release_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderId = holder.orderId_tv.getText().toString();
                    orderUUID = holder.uuid_textview.getText().toString();
                    ordrStDt = holder.basicStart_tv.getText().toString();
                    ordrEdDt = holder.edDt_tv.getText().toString();
                    Equipment = holder.equipment_tv.getText().toString();
                    FuncLoc = holder.funcLoc_tv.getText().toString();
                    confirmationDialog(getString(R.string.release_cnfrm), "REL",
                            holder.pernr_status.getText().toString());
                }
            });

            holder.cnf_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderId = holder.orderId_tv.getText().toString();
                    orderUUID = holder.uuid_textview.getText().toString();
                    ordrStDt = holder.basicStart_tv.getText().toString();
                    ordrEdDt = holder.edDt_tv.getText().toString();
                    Equipment = holder.equipment_tv.getText().toString();
                    FuncLoc = holder.funcLoc_tv.getText().toString();
                    confirmationDialog(getString(R.string.tk_cnfrm), "CNF",
                            "");
                }
            });

            holder.teco_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderId = holder.orderId_tv.getText().toString();
                    orderUUID = holder.uuid_textview.getText().toString();
                    ordrStDt = holder.basicStart_tv.getText().toString();
                    ordrEdDt = holder.edDt_tv.getText().toString();
                    Equipment = holder.equipment_tv.getText().toString();
                    FuncLoc = holder.funcLoc_tv.getText().toString();
                    confirmationDialog(getString(R.string.complet_order), "TECO",
                            "");
                }
            });

            holder.status_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderId = holder.orderId_tv.getText().toString();
                    getStatus();
                }
            });
        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }

    private void getStatus() {
        ArrayList<NotifOrdrStatusPrcbl> sysStatus_al = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Objnr " +
                    "like ? order by Stonr", new String[]{orderId, "OR%"});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        NotifOrdrStatusPrcbl osp = new NotifOrdrStatusPrcbl();
                        if (cursor.getString(10).startsWith("I")) {
                            if (cursor.getString(11).equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            osp.setTxt04(cursor.getString(12));
                            osp.setTxt30(cursor.getString(13));
                            sysStatus_al.add(osp);
                        }
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        final Dialog aa = new Dialog(Orders_Activity.this);
        aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
        aa.setCancelable(false);
        aa.setCanceledOnTouchOutside(false);
        aa.setContentView(R.layout.status_dialog);
        aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
        Button yes_button = (Button) aa.findViewById(R.id.yes_button);
        TextView no_data = (TextView) aa.findViewById(R.id.no_data_textview);
        RecyclerView list_recycleview = aa.findViewById(R.id.list_recycleview);

        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aa.dismiss();
            }
        });
        if (sysStatus_al.size() > 0) {
            no_data.setVisibility(View.GONE);
            list_recycleview.setVisibility(View.VISIBLE);
            sysStsAdapter = new SysStsAdapter(Orders_Activity.this, sysStatus_al);
            list_recycleview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(Orders_Activity.this);
            list_recycleview.setLayoutManager(layoutManager);
            list_recycleview.setItemAnimator(new DefaultItemAnimator());
            list_recycleview.setAdapter(sysStsAdapter);
            aa.show();

        } else {
            no_data.setVisibility(View.VISIBLE);
            list_recycleview.setVisibility(View.GONE);
        }
        aa.show();
    }

    public class SysStsAdapter extends RecyclerView.Adapter<SysStsAdapter.MyViewHolder> {
        private Context mContext;
        private List<NotifOrdrStatusPrcbl> sysStsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txt04_tv, txt30_tv;
            CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                txt04_tv = (TextView) view.findViewById(R.id.txt04_tv);
                txt30_tv = (TextView) view.findViewById(R.id.txt30_tv);
                checkBox = view.findViewById(R.id.checkBox);
            }
        }

        public SysStsAdapter(Context mContext, List<NotifOrdrStatusPrcbl> list) {
            this.mContext = mContext;
            this.sysStsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.status_with_cb_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final NotifOrdrStatusPrcbl sso = sysStsList.get(position);
            holder.txt04_tv.setText(sso.getTxt04());
            holder.txt30_tv.setText(sso.getTxt30());
            holder.checkBox.setChecked(sso.isSelected());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.setChecked(true);
                }
            });
        }

        @Override
        public int getItemCount() {
            return sysStsList.size();
        }
    }

    private class Orders_Object {
        private String UUID;
        private String orderId;
        private String priorityText;
        private String basicStartDate;
        private String orderShortText;
        private String workCmplt;
        private String wocoStatus;
        private String orderStatus;
        private String orderPlant;
        private String workCenter;
        private String orderType;
        private String priority;
        private String attachment;
        private String usr02;
        private String endDate;
        private String equipment;
        private String functionLocation;
        private String pernar;
        private String rel_status;
        private String cnf_status;
        private String teco_status;
        private String woco_status;

        public Orders_Object(String UUID, String orderId, String priorityText, String basicStartDate,
                             String orderShortText, String wocoStatus, String orderStatus,
                             String orderPlant, String workCenter, String orderType, String priority,
                             String attachment, String workCmplt, String usr02, String endDate,
                             String equipment, String functionLocation, String pernar,
                             String rel_status, String cnf_status, String teco_status,
                             String woco_status) {
            this.UUID = UUID;
            this.orderId = orderId;
            this.priorityText = priorityText;
            this.basicStartDate = basicStartDate;
            this.orderShortText = orderShortText;
            this.wocoStatus = wocoStatus;
            this.orderStatus = orderStatus;
            this.orderPlant = orderPlant;
            this.workCenter = workCenter;
            this.orderType = orderType;
            this.priority = priority;
            this.attachment = attachment;
            this.workCmplt = workCmplt;
            this.usr02 = usr02;
            this.endDate = endDate;
            this.equipment = equipment;
            this.functionLocation = functionLocation;
            this.pernar = pernar;
            this.rel_status = rel_status;
            this.cnf_status = cnf_status;
            this.teco_status = teco_status;
            this.woco_status = woco_status;
        }

        public String getWoco_status() {
            return woco_status;
        }

        public void setWoco_status(String woco_status) {
            this.woco_status = woco_status;
        }

        public String getTeco_status() {
            return teco_status;
        }

        public void setTeco_status(String teco_status) {
            this.teco_status = teco_status;
        }

        public String getCnf_status() {
            return cnf_status;
        }

        public void setCnf_status(String cnf_status) {
            this.cnf_status = cnf_status;
        }

        public String getRel_status() {
            return rel_status;
        }

        public void setRel_status(String rel_status) {
            this.rel_status = rel_status;
        }

        public String getPernar() {
            return pernar;
        }

        public void setPernar(String pernar) {
            this.pernar = pernar;
        }

        public String getFunctionLocation() {
            return functionLocation;
        }

        public void setFunctionLocation(String functionLocation) {
            this.functionLocation = functionLocation;
        }

        public String getEquipment() {
            return equipment;
        }

        public void setEquipment(String equipment) {
            this.equipment = equipment;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getUsr02() {
            return usr02;
        }

        public void setUsr02(String usr02) {
            this.usr02 = usr02;
        }

        public String getWorkCmplt() {
            return workCmplt;
        }

        public void setWorkCmplt(String workCmplt) {
            this.workCmplt = workCmplt;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
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

        public String getWocoStatus() {
            return wocoStatus;
        }

        public void setWocoStatus(String wocoStatus) {
            this.wocoStatus = wocoStatus;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderPlant() {
            return orderPlant;
        }

        public void setOrderPlant(String orderPlant) {
            this.orderPlant = orderPlant;
        }

        public String getWorkCenter() {
            return workCenter;
        }

        public void setWorkCenter(String workCenter) {
            this.workCenter = workCenter;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }
    }

    private String dateFormat(String date) {
        if (!date.equals("00000000")) {
            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
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

    private class Get_Order_List extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!clearAll)
                customProgressDialog.show_progress_dialog(Orders_Activity.this, getString(R.string.loading));
            ordersList.clear();
            ordersList_ad.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String rel_status = "", cnf_status = "", teco_status = "", woco_status = "";

            /*Authorization For Order Release (REL)*/
            String auth_rel_status = Authorizations
                    .Get_Authorizations_Data(Orders_Activity.this, "W", "R");
            if (auth_rel_status.equalsIgnoreCase("true")) {
                rel_status = "X";
            }
            /*Authorization For Order Release (REL)*/

            /*Authorization For Order Confirm (CNF)*/
            String auth_cnf_status = Authorizations
                    .Get_Authorizations_Data(Orders_Activity.this, "W", "TK");
            if (auth_cnf_status.equalsIgnoreCase("true")) {
                cnf_status = "X";
            }
            /*Authorization For Order Confirm (CNF)*/

            /*Authorization For Order TECO*/
            String auth_teco_status = Authorizations
                    .Get_Authorizations_Data(Orders_Activity.this, "W", "Z");
            if (auth_teco_status.equalsIgnoreCase("true")) {
                teco_status = "X";
            }
            /*Authorization For Order TECO*/

            /*Authorization For Order WOCO*/
            String auth_woco_status = Authorizations
                    .Get_Authorizations_Data(Orders_Activity.this, "W", "WC");
            if (auth_woco_status.equalsIgnoreCase("true")) {
                woco_status = "X";
            }
            /*Authorization For Order WOCO*/

            Cursor cursor = null;
            try {
                if (equipment_id != null && !equipment_id.equals("")) {
                    cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where" +
                            " Equnr = ? Order by Priok", new String[]{equipment_id});
                } else if (functionlocation_id != null && !functionlocation_id.equals("")) {
                    cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where" +
                            " Strno = ? Order by Priok", new String[]{functionlocation_id});
                } else {
                    cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader Order by" +
                            " Priok", null);
                }
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String workComplt = "";
                            String woco = "";
                            Cursor cursor1 = null;
                            try {
                                cursor1 = App_db.rawQuery("select * from EtOrderStatus where" +
                                                " Aufnr = ? and Txt04 = ?",
                                        new String[]{cursor.getString(2), "WOCO"});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            String work_complete_status = cursor1
                                                    .getString(11);
                                            if (work_complete_status
                                                    .equalsIgnoreCase("X")) {
                                                workComplt = "X";
                                                woco = "X";
                                            } else {
                                                workComplt = "";
                                                woco = "";
                                            }
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    workComplt = "X";
                                    woco = "";
                                }
                            } catch (Exception e) {
                                workComplt = "X";
                                woco = "";
                            } finally {
                                if (cursor1 != null)
                                    cursor1.close();
                            }
                            Orders_Object olo = new Orders_Object(cursor.getString(1),
                                    cursor.getString(2), cursor.getString(33),
                                    cursor.getString(14), cursor.getString(4),
                                    woco, cursor.getString(39),
                                    cursor.getString(25), cursor.getString(24),
                                    cursor.getString(3), cursor.getString(8),
                                    cursor.getString(15), workComplt,
                                    cursor.getString(41), cursor.getString(13),
                                    cursor.getString(9), cursor.getString(10),
                                    cursor.getString(53), rel_status, cnf_status,
                                    teco_status, woco_status);
                            ordersList.add(olo);
                            ordersList_ad.add(olo);
                        }
                        while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                if (cursor != null)
                    cursor.close();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!clearAll)
                customProgressDialog.dismiss_progress_dialog();
            else
                clearAll = true;
            if (ordersList_ad.size() > 0) {
                Collections.sort(ordersList_ad, new Comparator<Orders_Object>() {
                    public int compare(Orders_Object o1, Orders_Object o2) {
                        return o2.getBasicStartDate().compareTo(o1.getBasicStartDate());
                    }
                });

                /*if (pers_resp_status.equalsIgnoreCase("X"))
                    CollectionUtils.filter(ordersList, new Predicate() {
                        @Override
                        public boolean evaluate(Object o) {
                            return ((Orders_Object) o).getPernar().matches(person_responsible_id);
                        }
                    });*/
                //filterData(ordersList_ad);
                ordersAdapter = new OrdersAdapter(Orders_Activity.this, ordersList_ad);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(Orders_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(ordersAdapter);
                search.setOnQueryTextListener(listener);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.VISIBLE);
                title_tv.setText(getString(R.string.orders_count, String.valueOf(ordersList_ad.size())));
            } else {
                title_tv.setText(getString(R.string.orders));
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.GONE);
            }
            swiperefreshlayout.setRefreshing(false);
        }
    }

    private class Get_Order_Data extends AsyncTask<Void, Integer, Void> {
        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Orders_Activity.this,
                    getResources().getString(R.string.get_order_data));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ohp = GetOrderDetail.GetData(Orders_Activity.this, orderUUID, orderId,
                    orderStatus, Iwerk);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (ohp != null) {
                Intent ordrIntent = new Intent(Orders_Activity.this,
                        Orders_Change_Activity.class);
                ordrIntent.putExtra("order", "U");
                ordrIntent.putExtra("ordr_parcel", ohp);
                startActivity(ordrIntent);
            }
        }
    }

    private String getIwerk(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?",
                    new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(29);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private String getfuncIwerk(String func) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtFuncEquip where Tplnr = ?",
                    new String[]{func});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(14);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }

    private void confirmationDialog(String message, final String type, final String pernr) {
        final Dialog cancel_dialog = new Dialog(Orders_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.decision_dialog);
        ImageView imageView1 = (ImageView) cancel_dialog.findViewById(R.id.imageView1);
        Glide.with(Orders_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
        final TextView description_textview = cancel_dialog.findViewById(R.id.description_textview);
        description_textview.setText(message);
        Button confirm = (Button) cancel_dialog.findViewById(R.id.yes_button);
        Button cancel = (Button) cancel_dialog.findViewById(R.id.no_button);
        cancel_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(Orders_Activity.this);
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    cancel_dialog.dismiss();
                    if (type.equals("REL")) {
                        if (pernr != null && !pernr.equals("")) {
                            new POST_REL().execute();
                        } else {
                            error_dialog.show_error_dialog(Orders_Activity.this,
                                    getString(R.string.prsnresp_select));
                        }
                    } else if (type.equals("WOCO")) {
                        TYP = "WOCO";
                        new GetToken().execute();
                    } else if (type.equals("TECO")) {
                        TYP = "TECO";
                        new GetToken().execute();
                    } else if (type.equals("CNF")) {
                        Intent intent = new Intent(Orders_Activity.this,
                                OrderTkConfirmActivity.class);
                        intent.putExtra("uuid", orderUUID);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("equip", Equipment);
                        intent.putExtra("funcLoc", FuncLoc);
                        intent.putExtra("stDt", ordrStDt);
                        intent.putExtra("edDt", ordrEdDt);
                        startActivity(intent);
                    }
                } else {
                    if (type.equals("REL")) {
                        if (pernr != null && !pernr.equals("")) {
                            cancel_dialog.dismiss();
                            decision_dialog = new Dialog(Orders_Activity.this);
                            decision_dialog.getWindow()
                                    .setBackgroundDrawableResource(android.R.color.transparent);
                            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            decision_dialog.setCancelable(false);
                            decision_dialog.setCanceledOnTouchOutside(false);
                            decision_dialog.setContentView(R.layout.offline_decision_dialog);
                            TextView description_textview = decision_dialog
                                    .findViewById(R.id.description_textview);
                            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                            Button connect_button = decision_dialog.findViewById(R.id.connect_button);
                            description_textview.setText(getString(R.string.ordrrel_offline));
                            confirm.setText(getString(R.string.yes));
                            cancel.setText(getString(R.string.no));
                            decision_dialog.show();
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    decision_dialog.dismiss();
                                }
                            });
                            connect_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    decision_dialog.dismiss();
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.setClassName("com.android.settings",
                                            "com.android.settings.wifi.WifiSettings");
                                    startActivity(intent);
                                }
                            });
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    decision_dialog.dismiss();
                                    progressDialog.show_progress_dialog(Orders_Activity.this,
                                            getResources().getString(R.string.loading));
                                    ContentValues cv = new ContentValues();
                                    cv.put("Xstatus", "REL");
                                    App_db.update("DUE_ORDERS_EtOrderHeader", cv,
                                            "Aufnr=" + orderId, null);

                                    DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                    DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                    Date todaysdate = new Date();
                                    String date = date_format.format(todaysdate.getTime());
                                    String time = time_format.format(todaysdate.getTime());

                                    UUID uniqueKey = UUID.randomUUID();

                                    String sql11 = "Insert into Alert_Log (DATE, TIME, " +
                                            "DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID," +
                                            " STATUS, UUID, MESSAGE, LOG_UUID,OBJECT_TXT)" +
                                            " values(?,?,?,?,?,?,?,?,?,?,?);";
                                    SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                    App_db.beginTransaction();
                                    statement11.clearBindings();
                                    statement11.bindString(1, date);
                                    statement11.bindString(2, time);
                                    statement11.bindString(3, "Order");
                                    statement11.bindString(4, "Release");
                                    statement11.bindString(5, "");
                                    statement11.bindString(6, orderId);
                                    statement11.bindString(7, "Fail");
                                    statement11.bindString(8, "");
                                    statement11.bindString(9, "");
                                    statement11.bindString(10, uniqueKey.toString());
                                    statement11.bindString(11,shrtTxt);
                                    statement11.execute();
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();

                                    progressDialog.dismiss_progress_dialog();
                                    new Get_Order_List().execute();
                                }
                            });
                        } else {
                            cancel_dialog.dismiss();
                            error_dialog.show_error_dialog(Orders_Activity.this,
                                    getString(R.string.prsnresp_select));
                        }
                    } else if (type.equals("CNF")) {
                        cancel_dialog.dismiss();
                        Intent intent = new Intent(Orders_Activity.this,
                                OrderTkConfirmActivity.class);
                        intent.putExtra("uuid", orderUUID);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("equip", Equipment);
                        intent.putExtra("funcLoc", FuncLoc);
                        intent.putExtra("stDt", ordrStDt);
                        intent.putExtra("edDt", ordrEdDt);
                        startActivity(intent);
                    } else if (type.equals("TECO")) {
                        cancel_dialog.dismiss();
                        decision_dialog = new Dialog(Orders_Activity.this);
                        decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        decision_dialog.setCancelable(false);
                        decision_dialog.setCanceledOnTouchOutside(false);
                        decision_dialog.setContentView(R.layout.offline_decision_dialog);
                        TextView description_textview = decision_dialog
                                .findViewById(R.id.description_textview);
                        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                        Button connect_button = decision_dialog.findViewById(R.id.connect_button);
                        description_textview.setText(getString(R.string.ordrcomp_offline));
                        confirm.setText(getString(R.string.yes));
                        cancel.setText(getString(R.string.no));
                        decision_dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                            }
                        });
                        connect_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.setClassName("com.android.settings",
                                        "com.android.settings.wifi.WifiSettings");
                                startActivity(intent);
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                progressDialog.show_progress_dialog(Orders_Activity.this,
                                        getResources().getString(R.string.loading));
                                ContentValues cv = new ContentValues();
                                cv.put("Xstatus", "CLSD");
                                App_db.update("DUE_ORDERS_EtOrderHeader", cv,
                                        "Aufnr=" + orderId, null);

                                DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                Date todaysdate = new Date();
                                String date = date_format.format(todaysdate.getTime());
                                String time = time_format.format(todaysdate.getTime());

                                UUID uniqueKey = UUID.randomUUID();
                                String sql11 = "Insert into Alert_Log (DATE, TIME, " +
                                        "DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID, STATUS," +
                                        " UUID, MESSAGE, LOG_UUID,OBJECT_TXT) values(?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                App_db.beginTransaction();
                                statement11.clearBindings();
                                statement11.bindString(1, date);
                                statement11.bindString(2, time);
                                statement11.bindString(3, "Order");
                                statement11.bindString(4, "TECO");
                                statement11.bindString(5, "");
                                statement11.bindString(6, orderId);
                                statement11.bindString(7, "Fail");
                                statement11.bindString(8, "");
                                statement11.bindString(9, "");
                                statement11.bindString(10, uniqueKey.toString());
                                statement11.bindString(11, shrtTxt);
                                statement11.execute();
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();

                                progressDialog.dismiss_progress_dialog();
                                new Get_Order_List().execute();
                            }
                        });
                    }
                }
            }
        });
    }

    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (TYP.equals("WOCO"))
                customProgressDialog.show_progress_dialog(Orders_Activity.this,
                        getResources().getString(R.string.work_compl_inprg));
            else if (TYP.equals("TECO"))
                customProgressDialog.show_progress_dialog(Orders_Activity.this,
                        getResources().getString(R.string.compl_inprog));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Orders_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Response.equals("success")) {
                if (TYP.equals("WOCO"))
                    new POST_WOCO().execute();
                else if (TYP.equals("TECO"))
                    new POST_TECO().execute();
            } else {
                customProgressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Orders_Activity.this,
                        getResources().getString(R.string.unable_change));
            }
        }
    }

    public class POST_WOCO extends AsyncTask<Void, Integer, Void> {
        String[] Response = new String[0];
        ArrayList<WcdDup_Object> wcdDup_al = new ArrayList<>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        ArrayList<HashMap<String, String>> operation_custom_info_arraylist = new ArrayList<>();
        ArrayList<HashMap<String, String>> material_custom_info_arraylist = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Order_Create_Change().Post_Create_Order(Orders_Activity.this,
                    null, "", "CHORD", orderId, "WOCO",
                    header_custominfo, operation_custom_info_arraylist,
                    material_custom_info_arraylist, "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();

            if (Response[0].startsWith("S")) {
                successDialog.show_success_dialog(Orders_Activity.this,
                        Response[0].substring(2));
                new Get_Order_List().execute();
            } else if (Response[0].startsWith("E"))
                error_dialog.show_error_dialog(Orders_Activity.this,
                        Response[0].substring(2));
            else
                error_dialog.show_error_dialog(Orders_Activity.this, Response[0]);

        }
    }

    private class POST_REL extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Orders_Activity.this,
                    getResources().getString(R.string.release_order));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Order_Rel().Get_Data(Orders_Activity.this, "",
                    "X", "RLORD", orderId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("S")) {
                successDialog.show_success_dialog(Orders_Activity.this,
                        Response.substring(2));
                new Get_Order_List().execute();
            } else if (Response.startsWith("E"))
                error_dialog.show_error_dialog(Orders_Activity.this, Response.substring(2));
            else
                error_dialog.show_error_dialog(Orders_Activity.this, Response);
        }
    }

    private class POST_TECO extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();
            ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
            cop.setAufnr(orderId);
            cop.setStatus("TECO");
            cop_al.add(cop);
            if (cop_al.size() > 0)
                Response = new Order_CConfirmation().Get_Data(Orders_Activity.this, cop_al,
                        null, "", "CCORD", orderId, "TECO",
                        "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("S")) {
                successDialog.show_success_dialog(Orders_Activity.this,
                        Response.substring(2));
                new Get_Order_List().execute();
            } else if (Response.startsWith("E"))
                error_dialog.show_error_dialog(Orders_Activity.this, Response.substring(2));
            else
                error_dialog.show_error_dialog(Orders_Activity.this, Response);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (STATUS_ID):
                if (resultCode == RESULT_OK) {
                    if (statusId.equals(""))
                        statusId = statusId + data.getStringExtra("status_id");
                    else
                        statusId = statusId + "," + data.getStringExtra("status_id");

                    status_tv.setText(statusId);
                }
                break;

            case (ORDR_TYP):
                if (resultCode == RESULT_OK) {
                    if (ordrTypID.equals(""))
                        ordrTypID = ordrTypID + data.getStringExtra("ordrTypId");
                    else
                        ordrTypID = ordrTypID + "," + data.getStringExtra("ordrTypId");

                    ordrType_tv.setText(ordrTypID);
                }
                break;

            case (PRIORITY):
                if (resultCode == RESULT_OK) {
                    if (priorityId.equals(""))
                        priorityId = priorityId + data.getStringExtra("priority_type_id");
                    else
                        priorityId = priorityId + "," + data
                                .getStringExtra("priority_type_id");

                    priority_tv.setText(priorityId);
                }
                break;

            case (WRKCNTR_ID):
                if (resultCode == RESULT_OK) {
                    wrkCntr_tv.setText(data.getStringExtra("workcenter_id"));
                }
                break;

            case (FRMDT):
                if (resultCode == RESULT_OK) {
                    strtDt_bt.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TODT):
                if (resultCode == RESULT_OK) {
                    endDt_bt.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (fil_notif_type):
                if (data != null && !data.equals("")) {
                    filt_notification_ids = data.getStringExtra("notification_ids");
                    filt_notif_type_button.setText(filt_notification_ids);
                }
                break;

            case (fil_prior_type):
                if (data != null && !data.equals("")) {
                    filt_priority_ids = data.getStringExtra("ids");
                    filt_priority_type_button.setText(filt_priority_ids);
                }
                break;

            case (fil_status_type):
                if (data != null && !data.equals("")) {
                    filt_status_ids = data.getStringExtra("ids");
                    filt_status_type_button.setText(filt_status_ids);
                }
                break;


            case (fil_wckt_type):
                if (data != null && !data.equals("")) {
                    filt_wckt_text = data.getStringExtra("ids_text");
                    filt_wckt_ids = data.getStringExtra("ids");
                    filt_workcenter_type_button.setText(filt_wckt_ids);
                }
                break;

            case (scan_status):
                if (data != null && !data.equals("")) {
                    final String message = data.getStringExtra("MESSAGE");
                }
                break;
            case (EQUIP_SCAN):
                if (resultCode == RESULT_OK) {
                    searchview_textview.setText(data.getStringExtra("MESSAGE"));
                }
                break;
        }
    }

    private void filterData(List<Orders_Object> ordersList_ad) {
        if ((filt_notification_ids != null && !filt_notification_ids.equals(""))) {
            if (filt_priority_ids != null && !filt_priority_ids.equals("")) {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids);
                                    }
                                });
                            }
                        }
                    }
                }else
                {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids);
                                    }
                                });
                            }
                        }
                    }
                }
            }
            else
            {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids);
                                    }
                                });
                            }
                        }
                    }
                }else
                {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderType()
                                                .matches(filt_selected_notif_ids);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }else {
            if (filt_priority_ids != null && !filt_priority_ids.equals("")) {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids);
                                    }
                                });
                            }
                        }
                    }
                }else
                {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPriority()
                                                        .matches(filt_selected_prior_ids);
                                    }
                                });
                            }
                        }
                    }
                }
            }
            else
            {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getOrderStatus()
                                                        .matches(filt_selected_status_ids);
                                    }
                                });
                            }
                        }
                    }
                }else
                {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getWorkCenter()
                                                        .matches(filt_selected_wckt_ids) ;
                                    }
                                });
                            }
                        }
                    }else
                    {
                        if (attachment_clicked_status != null &&
                                !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status) &&
                                                ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }else
                            {
                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getAttachment()
                                                        .matches(attachment_clicked_status);
                                    }
                                });
                            }
                        }else
                        {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {

                                CollectionUtils.filter(ordersList_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Orders_Object) o).getPernar()
                                                        .matches(person_responsible_id);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }


    }
}

