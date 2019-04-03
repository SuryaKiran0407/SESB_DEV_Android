package com.enstrapp.fieldtekpro.notifications;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.Initialload.Notifications;
import com.enstrapp.fieldtekpro.Initialload.Orders;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Notifications_List_Activity extends AppCompatActivity implements View.OnClickListener {

    public SearchView search;
    TextView searchview_textview, title_textview;
    RecyclerView list_recycleview;
    SwipeRefreshLayout swiperefreshlayout;
    FloatingActionButton create_fab_button, filter_fab_button, refresh_fab_button, scan_fab_button,
            sort_fab_button;
    FloatingActionMenu floatingActionMenu;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private List<Notif_List_Object> notifications_list = new ArrayList<>();
    private List<Notif_List_Object> notifications_list_ad = new ArrayList<>();
    Notif_Adapter notif_adapter;
    ImageView back_imageview;
    Map<String, String> notif_release_status;
    String username = "", person_responsible_id = "", pers_resp_status = "", sort_selected = "",
            DORD_Status = "", scan_clicked_equip = "", attachment_clicked_status = "",
            filt_selected_wckt_ids = "", filt_wckt_text = "", filt_wckt_ids = "",
            filt_selected_status_ids = "", filt_status_ids = "", filt_selected_prior_ids = "",
            filt_priority_ids = "", filt_selected_notif_ids = "", filt_notification_ids = "",
            notif_postpone_status = "", notif_complete_status = "", DNOT_Status = "";
    Dialog decision_dialog;
    LinearLayout no_data_layout;
    Success_Dialog success_dialog = new Success_Dialog();
    int fil_notif_type = 0, fil_prior_type = 1, fil_status_type = 2, fil_wckt_type = 3,
            scan_status = 4;
    Button filt_notif_type_button, filt_priority_type_button, filt_status_type_button,
            filt_workcenter_type_button;
    private static final int ZXING_CAMERA_PERMISSION = 0;
    EditText searchEditText;
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    boolean per_resp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_list_activity);

        search = (SearchView) findViewById(R.id.search);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);
        list_recycleview = (RecyclerView) findViewById(R.id.recyclerview);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        title_textview = (TextView) findViewById(R.id.title_textview);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        create_fab_button = (FloatingActionButton) findViewById(R.id.create_fab_button);
        filter_fab_button = (FloatingActionButton) findViewById(R.id.filter_fab_button);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        scan_fab_button = (FloatingActionButton) findViewById(R.id.scan_fab_button);
        sort_fab_button = (FloatingActionButton) findViewById(R.id.sort_fab_button);

        scan_clicked_equip = "";

        DATABASE_NAME = Notifications_List_Activity.this.getString(R.string.database_name);
        App_db = Notifications_List_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        FieldTekPro_SharedPref = Notifications_List_Activity.this
                .getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        username = FieldTekPro_SharedPref.getString("Username", null);

        create_fab_button.setOnClickListener(this);
        refresh_fab_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        filter_fab_button.setOnClickListener(this);
        scan_fab_button.setOnClickListener(this);
        sort_fab_button.setOnClickListener(this);

        /*Authorization For Create Notification */
        String auth_create_status = Authorizations
                .Get_Authorizations_Data(Notifications_List_Activity.this,
                        "Q", "I");
        if (auth_create_status.equalsIgnoreCase("true")) {
            create_fab_button.setVisibility(View.VISIBLE);
        } else {
            create_fab_button.setVisibility(View.GONE);
        }
        /*Authorization For Create Notification */

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh_Notifications_Data();
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent,
                R.color.red, R.color.blue, R.color.black, R.color.orange);

        try {
            Cursor cursor = App_db.rawQuery("select * from GET_USER_DATA", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        /*String pernr = cursor.getString(9);
                        person_responsible_id = pernr.substring(2);*/
                        person_responsible_id = cursor.getString(9);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Get_Notifications_List_Data().execute();
    }

    private class Get_Notifications_List_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notifications_list.clear();
            notifications_list_ad.clear();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.fetching_notifications));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String teco_status = "", rel_status = "", nopo_status = "";

                /*Authorization For Notification Complete (NOCO)*/
                String auth_noco_status = Authorizations
                        .Get_Authorizations_Data(Notifications_List_Activity.this,
                                "Q", "S");
                if (auth_noco_status.equalsIgnoreCase("true")) {
                    teco_status = "X";
                }
                /*Authorization For Notification Complete (NOCO) */

                /*Authorization For Notification Release (REL)*/
                String auth_rel_status = Authorizations
                        .Get_Authorizations_Data(Notifications_List_Activity.this,
                                "Q", "R");
                if (auth_rel_status.equalsIgnoreCase("true")) {
                    rel_status = "X";
                }
                /*Authorization For Notification Release (REL)*/

                /*Authorization For Notification Postpone (NOPO)*/
                String auth_nopo_status = Authorizations
                        .Get_Authorizations_Data(Notifications_List_Activity.this,
                                "Q", "PP");
                if (auth_nopo_status.equalsIgnoreCase("true")) {
                    nopo_status = "X";
                }
                /*Authorization For Notification Postpone (NOPO)*/

                Cursor cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader",
                        null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String startdate = cursor.getString(18);
                            String StartDate_format = "";
                            String startdate_time = cursor.getString(18) + " " +
                                    cursor.getString(51);
                            if (startdate.equals("00000000")) {
                                startdate = "";
                                StartDate_format = "";
                                startdate_time = "";
                            } else {
                                DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                Date date1;
                                try {
                                    date1 = inputFormat.parse(startdate);
                                    String outputDateStr = outputFormat.format(date1);
                                    StartDate_format = outputDateStr;
                                } catch (ParseException e) {
                                    startdate = "";
                                    StartDate_format = "";
                                    startdate_time = "";
                                }
                            }
                            Notif_List_Object olo =
                                    new Notif_List_Object(cursor.getString(1),
                                            cursor.getString(3),
                                            cursor.getString(4),
                                            cursor.getString(14),
                                            cursor.getString(31),
                                            startdate, cursor.getString(44),
                                            cursor.getString(21),
                                            StartDate_format, cursor.getString(17),
                                            startdate_time, cursor.getString(45),
                                            cursor.getString(2),
                                            cursor.getString(16),
                                            cursor.getString(6),
                                            teco_status, rel_status, nopo_status);
                            notifications_list.add(olo);
                            notifications_list_ad.add(olo);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (notifications_list_ad.size() > 0) {
                Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                    public int compare(Notif_List_Object o1, Notif_List_Object o2) {
                        return o2.getStartdate_time().compareTo(o1.getStartdate_time());
                    }
                });

                /*if(per_resp)
                CollectionUtils.filter(notifications_list, new Predicate()
                {
                    @Override
                    public boolean evaluate(Object o)
                    {
                        return ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                    }
                });*/

                searchEditText.setText("");
                if (scan_clicked_equip != null && !scan_clicked_equip.equals("")) {
                    searchEditText.setText(scan_clicked_equip);
                    CollectionUtils.filter(notifications_list, new Predicate() {
                        @Override
                        public boolean evaluate(Object o) {
                            return ((Notif_List_Object) o).getEquipment().matches(scan_clicked_equip);
                        }
                    });
                }
                //filterData(notifications_list_ad);
                notif_adapter = new Notif_Adapter(Notifications_List_Activity.this,
                        notifications_list_ad);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(Notifications_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(notif_adapter);
                search.setOnQueryTextListener(listener);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.VISIBLE);
                title_textview.setText(getString(R.string.notifications) + " (" + notifications_list_ad.size() + ")");
            } else {
                title_textview.setText(getString(R.string.notifications) + " (0)");
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
            }
            scan_clicked_equip = "";
            progressDialog.dismiss_progress_dialog();
            swiperefreshlayout.setRefreshing(false);
        }
    }

    public class Notif_List_Object {
        private String UUID;
        private String notif_id;
        private String short_text;
        private String priority_id;
        private String priority_text;
        private String startdate;
        private String status;
        private String docs_status;
        private String startdate_format;
        private String plant;
        private String startdate_time;
        private String ParnrVw;
        private String NotifType;
        private String Arbpl;
        private String Equipment;
        private String teco_status;
        private String rel_status;
        private String nopo_status;

        public String getTeco_status() {
            return teco_status;
        }

        public void setTeco_status(String teco_status) {
            this.teco_status = teco_status;
        }

        public String getRel_status() {
            return rel_status;
        }

        public void setRel_status(String rel_status) {
            this.rel_status = rel_status;
        }

        public String getNopo_status() {
            return nopo_status;
        }

        public void setNopo_status(String nopo_status) {
            this.nopo_status = nopo_status;
        }

        public Notif_List_Object(String UUID, String notif_id, String short_text, String priority_id,
                                 String priority_text, String startdate, String status,
                                 String docs_status, String startdate_format, String plant,
                                 String startdate_time, String ParnrVw, String NotifType,
                                 String Arbpl, String Equipment, String teco_status,
                                 String rel_status, String nopo_status) {
            this.UUID = UUID;
            this.notif_id = notif_id;
            this.short_text = short_text;
            this.priority_id = priority_id;
            this.priority_text = priority_text;
            this.startdate = startdate;
            this.status = status;
            this.docs_status = docs_status;
            this.startdate_format = startdate_format;
            this.plant = plant;
            this.startdate_time = startdate_time;
            this.ParnrVw = ParnrVw;
            this.NotifType = NotifType;
            this.Arbpl = Arbpl;
            this.Equipment = Equipment;
            this.teco_status = teco_status;
            this.rel_status = rel_status;
            this.nopo_status = nopo_status;
        }

        public String getEquipment() {
            return Equipment;
        }

        public void setEquipment(String equipment) {
            Equipment = equipment;
        }

        public String getArbpl() {
            return Arbpl;
        }

        public void setArbpl(String arbpl) {
            Arbpl = arbpl;
        }

        public String getNotifType() {
            return NotifType;
        }

        public void setNotifType(String notifType) {
            NotifType = notifType;
        }

        public String getParnrVw() {
            return ParnrVw;
        }

        public void setParnrVw(String parnrVw) {
            ParnrVw = parnrVw;
        }

        public String getStartdate_time() {
            return startdate_time;
        }

        public void setStartdate_time(String startdate_time) {
            this.startdate_time = startdate_time;
        }

        public String getplant() {
            return plant;
        }

        public void setplant(String plant) {
            this.plant = plant;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public String getnotif_id() {
            return notif_id;
        }

        public void setnotif_id(String notif_id) {
            this.notif_id = notif_id;
        }

        public String getshort_text() {
            return short_text;
        }

        public void setshort_text(String short_text) {
            this.short_text = short_text;
        }

        public String getpriority_id() {
            return priority_id;
        }

        public void setpriority_id(String priority_id) {
            this.priority_id = priority_id;
        }

        public String getpriority_text() {
            return priority_text;
        }

        public void setpriority_text(String priority_text) {
            this.priority_text = priority_text;
        }

        public String getstartdate() {
            return startdate;
        }

        public void setstartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getstatus() {
            return status;
        }

        public void setstatus(String status) {
            this.status = status;
        }

        public String getdocs_status() {
            return docs_status;
        }

        public void setdocs_status(String docs_status) {
            this.docs_status = docs_status;
        }

        public String getstartdate_format() {
            return startdate_format;
        }

        public void setstartdate_format(String startdate_format) {
            this.startdate_format = startdate_format;
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            //final List<Notif_List_Object> filteredList = new ArrayList<>();
            notifications_list_ad.clear();
            for (int i = 0; i < notifications_list.size(); i++) {
                String notif_id = notifications_list.get(i).getnotif_id().toLowerCase();
                String short_text = notifications_list.get(i).getshort_text().toLowerCase();
                String plant = notifications_list.get(i).getplant().toLowerCase();
                String Equipment = notifications_list.get(i).getEquipment().toLowerCase();
                if (notif_id.contains(query) || short_text.contains(query) || plant.contains(query)
                        || Equipment.contains(query)) {
                    Notif_List_Object blo = notifications_list.get(i);
                    notifications_list_ad.add(blo);
                }
            }
            filterData(notifications_list_ad);
            if (notifications_list_ad.size() > 0) {
                if (notif_adapter != null) {
                    notif_adapter.notifyDataSetChanged();
                    title_textview.setText(getString(R.string.notifications) + " (" + notifications_list_ad.size() + ")");
                    no_data_layout.setVisibility(View.GONE);
                    swiperefreshlayout.setVisibility(View.VISIBLE);
                } else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Notifications_List_Activity.this);
                    list_recycleview.setLayoutManager(layoutManager);
                    notif_adapter = new Notif_Adapter(Notifications_List_Activity.this, notifications_list_ad);
                    list_recycleview.setAdapter(notif_adapter);
                    title_textview.setText(getString(R.string.notifications) + " (" + notifications_list_ad.size() + ")");
                    no_data_layout.setVisibility(View.GONE);
                    swiperefreshlayout.setVisibility(View.VISIBLE);
                }
            } else {
                no_data_layout.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    public class Notif_Adapter extends RecyclerView.Adapter<Notif_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Notif_List_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView teco_auth_textview, rel_auth_textview, nopo_auth_textview,
                    ParnrVw_textview, uuid_textview, plant_textview, id_textview,
                    description_textview, date_time_textview, status_type_textview,
                    priority_textview;
            ImageView attachment_imageview, status_imageview;
            LinearLayout release_layout, teco_layout, postpone_layout;
            FrameLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                description_textview = (TextView) view.findViewById(R.id.description_textview);
                date_time_textview = (TextView) view.findViewById(R.id.date_time_textview);
                status_type_textview = (TextView) view.findViewById(R.id.status_type_textview);
                attachment_imageview = (ImageView) view.findViewById(R.id.attachment_imageview);
                priority_textview = (TextView) view.findViewById(R.id.priority_textview);
                plant_textview = (TextView) view.findViewById(R.id.plant_textview);
                release_layout = (LinearLayout) view.findViewById(R.id.release_layout);
                teco_layout = (LinearLayout) view.findViewById(R.id.teco_layout);
                uuid_textview = (TextView) view.findViewById(R.id.uuid_textview);
                postpone_layout = (LinearLayout) view.findViewById(R.id.postpone_layout);
                data_layout = (FrameLayout) view.findViewById(R.id.data_layout);
                status_imageview = (ImageView) view.findViewById(R.id.status_imageview);
                ParnrVw_textview = (TextView) view.findViewById(R.id.ParnrVw_textview);
                teco_auth_textview = (TextView) view.findViewById(R.id.teco_auth_textview);
                rel_auth_textview = (TextView) view.findViewById(R.id.rel_auth_textview);
                nopo_auth_textview = (TextView) view.findViewById(R.id.nopo_auth_textview);
            }
        }

        public Notif_Adapter(Context mContext, List<Notif_List_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Notif_List_Object olo = orders_list_data.get(position);
            if (!olo.getstatus().equals("OFL"))
                holder.status_type_textview.setText(olo.getstatus());
            else
                holder.status_type_textview.setText("");
            holder.id_textview.setText(olo.getnotif_id());
            holder.description_textview.setText(olo.getshort_text());
            holder.date_time_textview.setText(olo.getstartdate_format());
            //holder.status_type_textview.setText(olo.getstatus());
            holder.priority_textview.setText(olo.getpriority_text());
            holder.plant_textview.setText(olo.getplant());
            holder.uuid_textview.setText(olo.getUUID());
            holder.ParnrVw_textview.setText(olo.getParnrVw());
            holder.teco_auth_textview.setText(olo.getTeco_status());
            holder.rel_auth_textview.setText(olo.getRel_status());
            holder.nopo_auth_textview.setText(olo.getNopo_status());

            if (holder.priority_textview.getText().toString().contains("Critical")) {
                holder.priority_textview.setVisibility(View.VISIBLE);
                holder.priority_textview.setBackground(getResources()
                        .getDrawable(R.drawable.critical_round));
            } else if (holder.priority_textview.getText().toString().contains("Very")) {
                holder.priority_textview.setVisibility(View.VISIBLE);
                holder.priority_textview.setBackground(getResources()
                        .getDrawable(R.drawable.vhigh_round));
            } else if (holder.priority_textview.getText().toString().contains("High")) {
                holder.priority_textview.setVisibility(View.VISIBLE);
                holder.priority_textview.setBackground(getResources()
                        .getDrawable(R.drawable.high_round));
            } else if (holder.priority_textview.getText().toString().contains("Medium")) {
                holder.priority_textview.setVisibility(View.VISIBLE);
                holder.priority_textview.setBackground(getResources().getDrawable(R.drawable.medium_round));
            } else if (holder.priority_textview.getText().toString().contains("Low")) {
                holder.priority_textview.setVisibility(View.VISIBLE);
                holder.priority_textview.setBackground(getResources()
                        .getDrawable(R.drawable.low_round));
            } else {
                holder.priority_textview.setVisibility(View.GONE);
            }

            if (olo.getdocs_status().equalsIgnoreCase("X")) {
                holder.attachment_imageview.setVisibility(View.VISIBLE);
            } else {
                holder.attachment_imageview.setVisibility(View.GONE);
            }

            if (holder.id_textview.getText().toString().startsWith("NOT")) {
//                holder.status_type_textview.setBackground(getResources().getDrawable(R.drawable.offline_status));
               /* holder.status_type_textview
                        .setTextColor(getResources().getColor(R.color.light_grey2));*/
                holder.teco_layout.setVisibility(View.GONE);
                holder.release_layout.setVisibility(View.GONE);
                holder.postpone_layout.setVisibility(View.GONE);
                holder.status_imageview.setVisibility(View.GONE);
            } else {
                holder.status_imageview.setVisibility(View.VISIBLE);
                if (holder.status_type_textview.getText().toString()
                        .equalsIgnoreCase("OSNO")) {
                    holder.status_type_textview
                            .setBackground(getResources().getDrawable(R.drawable.yellow_circle));
                    holder.status_type_textview.setTextColor(getResources()
                            .getColor(R.color.light_grey2));
                    holder.teco_layout.setVisibility(View.VISIBLE);
                    holder.release_layout.setVisibility(View.VISIBLE);
                    holder.postpone_layout.setVisibility(View.VISIBLE);
                    if (holder.teco_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.teco_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.teco_layout.setVisibility(View.GONE);
                    }
                    if (holder.rel_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.release_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.release_layout.setVisibility(View.GONE);
                    }
                    if (holder.nopo_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.postpone_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.postpone_layout.setVisibility(View.GONE);
                    }
                } else if (holder.status_type_textview.getText().toString()
                        .equalsIgnoreCase("NOPR")) {
                    holder.status_type_textview.setBackground(getResources()
                            .getDrawable(R.drawable.blue_circle));
                    holder.status_type_textview.setTextColor(getResources()
                            .getColor(R.color.white));
                    holder.release_layout.setVisibility(View.GONE);
                    holder.postpone_layout.setVisibility(View.GONE);
                    if (holder.teco_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.teco_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.teco_layout.setVisibility(View.GONE);
                    }
                } else if (holder.status_type_textview.getText().toString()
                        .equalsIgnoreCase("NOCO")) {
                    holder.status_type_textview.setBackground(getResources()
                            .getDrawable(R.drawable.green_circle));
                    holder.status_type_textview.setTextColor(getResources()
                            .getColor(R.color.white));
                    holder.teco_layout.setVisibility(View.GONE);
                    holder.release_layout.setVisibility(View.GONE);
                    holder.postpone_layout.setVisibility(View.GONE);
                } else if (holder.status_type_textview.getText().toString()
                        .equalsIgnoreCase("NOPO")) {
                    holder.status_type_textview.setBackground(getResources()
                            .getDrawable(R.drawable.orange_circle));
                    holder.status_type_textview.setTextColor(getResources().getColor(R.color.white));
                    holder.teco_layout.setVisibility(View.VISIBLE);
                    holder.release_layout.setVisibility(View.VISIBLE);
                    holder.postpone_layout.setVisibility(View.GONE);
                    if (holder.teco_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.teco_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.teco_layout.setVisibility(View.GONE);
                    }
                    if (holder.rel_auth_textview.getText().toString()
                            .equalsIgnoreCase("X")) {
                        holder.release_layout.setVisibility(View.VISIBLE);
                    } else {
                        holder.release_layout.setVisibility(View.GONE);
                    }
                } else {
                    holder.status_type_textview.setBackground(getResources()
                            .getDrawable(R.drawable.yellow_circle));
                    holder.status_type_textview.setTextColor(getResources()
                            .getColor(R.color.light_grey2));
                    holder.teco_layout.setVisibility(View.GONE);
                    holder.release_layout.setVisibility(View.GONE);
                    holder.postpone_layout.setVisibility(View.GONE);
                }
            }

            holder.status_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent status_intent = new Intent(Notifications_List_Activity.this,
                            Notifications_Status_Display_Activity.class);
                    status_intent.putExtra("notification_id",
                            holder.id_textview.getText().toString());
                    startActivity(status_intent);
                }
            });

            /*For Notification Complete, when clicked on particular notification number*/
            holder.teco_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String notif_number = holder.id_textview.getText().toString();
                    cd = new ConnectionDetector(Notifications_List_Activity.this);
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        decision_dialog = new Dialog(Notifications_List_Activity.this);
                        decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        decision_dialog.setCancelable(false);
                        decision_dialog.setCanceledOnTouchOutside(false);
                        decision_dialog.setContentView(R.layout.decision_dialog);
                        ImageView imageview = decision_dialog.findViewById(R.id.imageView1);
                        TextView description_textview = decision_dialog
                                .findViewById(R.id.description_textview);
                        Glide.with(Notifications_List_Activity.this)
                                .load(R.drawable.error_dialog_gif).into(imageview);
                        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                        description_textview.setText(getString(R.string.notifcomplete_notifno,
                                notif_number));
                        decision_dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                new Post_Notification_Complete().execute(notif_number);
                            }
                        });
                        decision_dialog.show();
                    } else {
                        String object_damage_status = "false";
                        try {
                            Cursor cursor = App_db.rawQuery("select * from " +
                                            "DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?",
                                    new String[]{notif_number});
                            if (cursor != null && cursor.getCount() > 0) {
                                object_damage_status = "true";
                            } else {
                                cursor.close();
                                object_damage_status = "false";
                            }
                        } catch (Exception e) {
                            object_damage_status = "false";
                        }
                        if (object_damage_status.equalsIgnoreCase("true")) {
                            String mal_dat_tim_status = "false";
                            try {
                                Cursor cursor = App_db.rawQuery("select * from " +
                                                "DUE_NOTIFICATION_NotifHeader where Qmnum = ?",
                                        new String[]{notif_number});
                                if (cursor != null && cursor.getCount() > 0) {
                                    if(cursor.moveToFirst()) {
                                        do {
                                            String mal_date = cursor.getString(10);
                                            String mal_time = cursor.getString(12);
                                            if ((mal_date != null && !mal_date.equals("")) &&
                                                    (mal_time != null && !mal_time.equals(""))) {
                                                mal_dat_tim_status = "true";
                                            } else {
                                                mal_dat_tim_status = "false";
                                            }
                                        }
                                        while (cursor.moveToNext());
                                    }
                                } else {
                                    cursor.close();
                                    mal_dat_tim_status = "false";
                                }
                            } catch (Exception e) {
                                object_damage_status = "false";
                            }
                            if (mal_dat_tim_status.equalsIgnoreCase("true")) {
                                decision_dialog = new Dialog(Notifications_List_Activity.this);
                                decision_dialog.getWindow()
                                        .setBackgroundDrawableResource(android.R.color.transparent);
                                decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                decision_dialog.setCancelable(false);
                                decision_dialog.setCanceledOnTouchOutside(false);
                                decision_dialog.setContentView(R.layout.offline_decision_dialog);
                                TextView description_textview = decision_dialog
                                        .findViewById(R.id.description_textview);
                                Button confirm = decision_dialog.findViewById(R.id.yes_button);
                                Button cancel = decision_dialog.findViewById(R.id.no_button);
                                Button connect_button = decision_dialog
                                        .findViewById(R.id.connect_button);
                                description_textview.setText(getString(R.string.notifcomp_offline));
                                confirm.setText(getString(R.string.yes));
                                cancel.setText(getString(R.string.no));
                                decision_dialog.show();
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        decision_dialog.dismiss();
                                        progressDialog
                                                .show_progress_dialog(Notifications_List_Activity.this,
                                                        getResources().getString(R.string.loading));
                                        ContentValues cv = new ContentValues();
                                        cv.put("Xstatus", "NOCO");
                                        cv.put("STATUS", "NOCO");
                                        App_db.update("DUE_NOTIFICATION_NotifHeader", cv,
                                                "Qmnum=" + notif_number, null);

                                        DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                        DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                        Date todaysdate = new Date();
                                        String date = date_format.format(todaysdate.getTime());
                                        String time = time_format.format(todaysdate.getTime());

                                        UUID uniqueKey = UUID.randomUUID();

                                        String sql11 = "Insert into Alert_Log (DATE, TIME, " +
                                                "DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID," +
                                                " STATUS, UUID, MESSAGE, LOG_UUID,OBJECT_TXT) " +
                                                "values(?,?,?,?,?,?,?,?,?,?,?);";
                                        SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                        App_db.beginTransaction();
                                        statement11.clearBindings();
                                        statement11.bindString(1, date);
                                        statement11.bindString(2, time);
                                        statement11.bindString(3, "Notification");
                                        statement11.bindString(4, "Complete");
                                        statement11.bindString(5, username.toUpperCase().toString());
                                        statement11.bindString(6, notif_number);
                                        statement11.bindString(7, "Fail");
                                        statement11.bindString(8, holder.uuid_textview.getText().toString());
                                        statement11.bindString(9, "");
                                        statement11.bindString(10, uniqueKey.toString());
                                        statement11.bindString(11,  holder.description_textview.getText().toString() );
                                        statement11.execute();
                                        App_db.setTransactionSuccessful();
                                        App_db.endTransaction();

                                        progressDialog.dismiss_progress_dialog();
                                        new Get_Notifications_List_Data().execute();
                                    }
                                });
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
                            } else {
                                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                                        getString(R.string.notifmalfendmandate));
                            }
                        } else {
                            error_dialog.show_error_dialog(Notifications_List_Activity.this,
                                    getString(R.string.notifobjdmg_mandate));
                        }
                    }
                }
            });
            /*For Notification Complete, when clicked on particular notification number*/

            /*For Notification Release, when clicked on particular notification number*/
            holder.release_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String notif_number = holder.id_textview.getText().toString();
                    cd = new ConnectionDetector(Notifications_List_Activity.this);
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        decision_dialog = new Dialog(Notifications_List_Activity.this);
                        decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        decision_dialog.setCancelable(false);
                        decision_dialog.setCanceledOnTouchOutside(false);
                        decision_dialog.setContentView(R.layout.decision_dialog);
                        ImageView imageview = decision_dialog.findViewById(R.id.imageView1);
                        TextView description_textview = decision_dialog
                                .findViewById(R.id.description_textview);
                        Glide.with(Notifications_List_Activity.this)
                                .load(R.drawable.error_dialog_gif).into(imageview);
                        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                        description_textview.setText(getString(R.string.notifrel_notifno,
                                notif_number));
                        decision_dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                new Post_Notification_Release().execute(notif_number);
                            }
                        });
                        decision_dialog.show();
                    } else {
                        decision_dialog = new Dialog(Notifications_List_Activity.this);
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
                        description_textview.setText(getString(R.string.notifrel_offline));
                        confirm.setText(getString(R.string.yes));
                        cancel.setText(getString(R.string.no));
                        decision_dialog.show();
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                progressDialog
                                        .show_progress_dialog(Notifications_List_Activity.this,
                                                getResources().getString(R.string.loading));
                                ContentValues cv = new ContentValues();
                                cv.put("Xstatus", "NOPR");
                                cv.put("STATUS", "NOPR");
                                App_db.update("DUE_NOTIFICATION_NotifHeader", cv,
                                        "Qmnum=" + notif_number, null);

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
                                statement11.bindString(3, "Notification");
                                statement11.bindString(4, "Release");
                                statement11.bindString(5, username.toUpperCase().toString());
                                statement11.bindString(6, notif_number);
                                statement11.bindString(7, "Fail");
                                statement11.bindString(8, holder.uuid_textview.getText().toString());
                                statement11.bindString(9, "");
                                statement11.bindString(10, uniqueKey.toString());
                                statement11.bindString(11,  holder.description_textview.getText().toString());
                                statement11.execute();
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();

                                progressDialog.dismiss_progress_dialog();
                                new Get_Notifications_List_Data().execute();
                            }
                        });
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
                    }
                }
            });
            /*For Notification Release, when clicked on particular notification number*/


            /*For Notification Postpone, when clicked on particular notification number*/
            holder.postpone_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String notif_number = holder.id_textview.getText().toString();
                    cd = new ConnectionDetector(Notifications_List_Activity.this);
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        decision_dialog = new Dialog(Notifications_List_Activity.this);
                        decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        decision_dialog.setCancelable(false);
                        decision_dialog.setCanceledOnTouchOutside(false);
                        decision_dialog.setContentView(R.layout.decision_dialog);
                        ImageView imageview = decision_dialog.findViewById(R.id.imageView1);
                        TextView description_textview = decision_dialog
                                .findViewById(R.id.description_textview);
                        Glide.with(Notifications_List_Activity.this)
                                .load(R.drawable.error_dialog_gif).into(imageview);
                        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                        description_textview.setText(getString(R.string.notifpostpone_notifno,
                                notif_number));
                        decision_dialog.show();
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                            }
                        });
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                new Post_Notification_Postpone().execute(notif_number);
                            }
                        });
                        decision_dialog.show();
                    } else {
                        decision_dialog = new Dialog(Notifications_List_Activity.this);
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
                        Button connect_button = decision_dialog
                                .findViewById(R.id.connect_button);
                        description_textview.setText(getString(R.string.notifpostpone_offline));
                        confirm.setText(getString(R.string.yes));
                        cancel.setText(getString(R.string.no));
                        decision_dialog.show();
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                decision_dialog.dismiss();
                                progressDialog
                                        .show_progress_dialog(Notifications_List_Activity.this,
                                                getResources().getString(R.string.loading));
                                ContentValues cv = new ContentValues();
                                cv.put("Xstatus", "NOPO");
                                cv.put("STATUS", "NOPO");
                                App_db.update("DUE_NOTIFICATION_NotifHeader", cv,
                                        "Qmnum=" + notif_number, null);

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
                                statement11.bindString(3, "Notification");
                                statement11.bindString(4, "Postpone");
                                statement11.bindString(5, username.toUpperCase().toString());
                                statement11.bindString(6, notif_number);
                                statement11.bindString(7, "Fail");
                                statement11.bindString(8, holder.uuid_textview.getText().toString());
                                statement11.bindString(9, "");
                                statement11.bindString(10, uniqueKey.toString());
                                statement11.bindString(11,  holder.description_textview.getText().toString());
                                statement11.execute();
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();

                                progressDialog.dismiss_progress_dialog();
                                new Get_Notifications_List_Data().execute();
                            }
                        });
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
                                intent.setClassName("com.android.settings"
                                        , "com.android.settings.wifi.WifiSettings");
                                startActivity(intent);
                            }
                        });
                    }
                }
            });
            /*For Notification Postpone, when clicked on particular notification number*/

            /*When Particular Notification is clicked*/
            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Get_Notification_Data().execute(holder.id_textview.getText().toString(),
                            holder.uuid_textview.getText().toString());
                }
            });
            /*When Particular Notification is clicked*/
        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == create_fab_button) {
            floatingActionMenu.close(true);
            Intent intent = new Intent(Notifications_List_Activity.this,
                    Notifications_Create_Activity.class);
            startActivity(intent);
        } else if (v == refresh_fab_button) {
            floatingActionMenu.close(true);
            Refresh_Notifications_Data();
        } else if (v == back_imageview) {
            Notifications_List_Activity.this.finish();
        } else if (v == sort_fab_button) {
            floatingActionMenu.close(true);
            if (notifications_list_ad.size() > 0) {
                final Dialog dialog = new Dialog(Notifications_List_Activity.this,
                        R.style.AppThemeDialog_Dark);
                dialog.getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.notifcation_order_sort);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                TextView sort_clear_textview = (TextView) dialog.findViewById(R.id.clearAll);
                final RadioGroup sort_radioGroup = (RadioGroup) dialog.findViewById(R.id.sort_rdg);
                final RadioButton sortAtoZ = (RadioButton) dialog.findViewById(R.id.sortAtoZ);
                final RadioButton sortZtoA = (RadioButton) dialog.findViewById(R.id.sortZtoA);
                final RadioButton criticaltoLow = dialog.findViewById(R.id.criticaltoLow);
                final RadioButton lowtoCritical = dialog.findViewById(R.id.lowtoCritical);
                final RadioButton stDt1to9 = (RadioButton) dialog.findViewById(R.id.stDt1to9);
                final RadioButton stDt9to1 = (RadioButton) dialog.findViewById(R.id.stDt9to1);
                final RadioButton No1to9 = (RadioButton) dialog.findViewById(R.id.No1to9);
                final RadioButton No9to1 = (RadioButton) dialog.findViewById(R.id.No9to1);
                TextView text_textview = (TextView) dialog.findViewById(R.id.text_textview);
                text_textview.setText(getResources().getString(R.string.notifNo));
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
                        Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                            @Override
                            public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                return lhs.getstartdate_format().compareTo(rhs.getstartdate_format());
                            }
                        });
                        notif_adapter.notifyDataSetChanged();
                        sort_selected = "";
                        dialog.dismiss();
                    }
                });
                sort_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.sortAtoZ) {
                            sort_selected = "description_sort1";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return rhs.getshort_text().compareTo(lhs.getshort_text());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.sortZtoA) {
                            sort_selected = "description_sort2";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return lhs.getshort_text().compareTo(rhs.getshort_text());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.criticaltoLow) {
                            sort_selected = "priority_sort1";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return rhs.getpriority_id().compareTo(lhs.getpriority_id());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.lowtoCritical) {
                            sort_selected = "priority_sort2";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return lhs.getpriority_id().compareTo(rhs.getpriority_id());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.stDt1to9) {
                            sort_selected = "date_sort1";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return rhs.getstartdate_format().compareTo(lhs.getstartdate_format());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.stDt9to1) {
                            sort_selected = "date_sort2";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return lhs.getstartdate_format().compareTo(rhs.getstartdate_format());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.No1to9) {
                            sort_selected = "id_sort1";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return rhs.getnotif_id().compareTo(lhs.getnotif_id());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else if (checkedId == R.id.No9to1) {
                            sort_selected = "id_sort2";
                            Collections.sort(notifications_list_ad, new Comparator<Notif_List_Object>() {
                                @Override
                                public int compare(Notif_List_Object rhs, Notif_List_Object lhs) {
                                    return lhs.getnotif_id().compareTo(rhs.getnotif_id());
                                }
                            });
                            notif_adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                        getString(R.string.notif_filter_nosort));
            }
        } else if (v == scan_fab_button) {
            scan_clicked_equip = "";
            floatingActionMenu.close(true);
            if (ContextCompat.checkSelfPermission(Notifications_List_Activity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Notifications_List_Activity.this,
                        new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
            } else {
                Intent intent = new Intent(Notifications_List_Activity.this,
                        Barcode_Scanner_Activity.class);
                startActivityForResult(intent, scan_status);
            }
        } else if (v == filter_fab_button) {
            floatingActionMenu.close(true);
            final Dialog dialog = new Dialog(Notifications_List_Activity.this,
                    R.style.AppThemeDialog_Dark);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.notifications_filter_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            filt_notif_type_button = (Button) dialog.findViewById(R.id.notif_type_button);
            filt_notif_type_button.setText(filt_notification_ids);
            filt_priority_type_button = (Button) dialog.findViewById(R.id.priority_type_button);
            filt_priority_type_button.setText(filt_priority_ids);
            filt_status_type_button = (Button) dialog.findViewById(R.id.status_type_button);
            filt_status_type_button.setText(filt_status_ids);
            filt_workcenter_type_button = (Button) dialog.findViewById(R.id.workcenter_type_button);
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
                            new Intent(Notifications_List_Activity.this,
                                    Notifications_Type_Select_Activity.class);
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
                            new Intent(Notifications_List_Activity.this,
                                    Notifications_Priority_Select_Activity.class);
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
                            new Intent(Notifications_List_Activity.this,
                                    Notifications_Status_Select_Activity.class);
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
                            new Intent(Notifications_List_Activity.this,
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
                    dialog.dismiss();
                    per_resp = false;
                    new Get_Notifications_List_Data().execute();
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
                                    !attachment_clicked_status.equals("")) ||
                            (pers_resp_status != null && !pers_resp_status.equals(""))) {


                        filt_selected_notif_ids = filt_notification_ids.trim();
                        if (filt_selected_notif_ids.contains(",")) {
                            filt_selected_notif_ids = filt_selected_notif_ids
                                    .replace(",", "|");
                            filt_selected_notif_ids = filt_selected_notif_ids
                                    .substring(0, filt_selected_notif_ids.length() - 1);
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
                            filt_selected_status_ids = filt_selected_status_ids.replace(",",
                                    "|");
                            filt_selected_status_ids = filt_selected_status_ids.substring(0,
                                    filt_selected_status_ids.length() - 1);
                        }
                        filt_selected_wckt_ids = filt_wckt_ids.trim();
                        if (filt_selected_wckt_ids.contains(",")) {
                            filt_selected_wckt_ids = filt_selected_wckt_ids.replace(",",
                                    "|");
                            filt_selected_wckt_ids = filt_selected_wckt_ids.substring(0,
                                    filt_selected_wckt_ids.length() - 1);
                        }
                        final StringBuffer filt_selected_persresp_ids = new StringBuffer();
                        if (pers_resp_checkbox.isChecked()) {
                            per_resp = true;
                            filt_selected_persresp_ids.append(person_responsible_id);
                        }

                        notifications_list_ad.clear();
                        notifications_list_ad.addAll(notifications_list);
                        filterData(notifications_list_ad);
                        if (notifications_list.size() > 0) {
                            no_data_layout.setVisibility(View.GONE);
                            swiperefreshlayout.setVisibility(View.VISIBLE);
                            title_textview.setText(getString(R.string.notifications) + " (" + notifications_list_ad.size() + ")");
                            notif_adapter.notifyDataSetChanged();
                        } else {
                            no_data_layout.setVisibility(View.VISIBLE);
                            title_textview.setText(getString(R.string.notifications));
                            swiperefreshlayout.setVisibility(View.GONE);
                        }
                        dialog.dismiss();
                    } else {
                        error_dialog.show_error_dialog(Notifications_List_Activity.this,
                                getString(R.string.notif_filter_atleast));
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Notifications_List_Activity.this,
                            Barcode_Scanner_Activity.class);
                    startActivityForResult(intent, 4);
                } else {
                    Toast.makeText(Notifications_List_Activity.this,
                            getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == fil_notif_type) {
                filt_notification_ids = data.getStringExtra("notification_ids");
                filt_notif_type_button.setText(filt_notification_ids);
            } else if (requestCode == fil_prior_type) {
                filt_priority_ids = data.getStringExtra("ids");
                filt_priority_type_button.setText(filt_priority_ids);
            } else if (requestCode == fil_status_type) {
                filt_status_ids = data.getStringExtra("ids");
                filt_status_type_button.setText(filt_status_ids);
            } else if (requestCode == fil_wckt_type) {
                filt_wckt_text = data.getStringExtra("ids_text");
                filt_wckt_ids = data.getStringExtra("ids");
                filt_workcenter_type_button.setText(filt_wckt_ids);
            } else if (requestCode == scan_status) {
                final String message = data.getStringExtra("MESSAGE");
                scan_clicked_equip = message;
                searchEditText.setText(message);
            }
        }
    }

    public void Refresh_Notifications_Data() {
        cd = new ConnectionDetector(Notifications_List_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(Notifications_List_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Notifications_List_Activity.this)
                    .load(R.drawable.error_dialog_gif).into(imageview);
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
                    new Get_Notifications_Refresh_Data().execute();
                }
            });
        } else {
            //showing network error and navigating to wifi settings.
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(Notifications_List_Activity.this);
        }
    }

    public class Get_Notifications_Refresh_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.refreshing_notifications));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                DNOT_Status = Notifications.Get_DNOT_Data(Notifications_List_Activity.this,
                        "REFR");
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss_progress_dialog();
            new Get_Notifications_List_Data().execute();
        }
    }

    /*Posting Notification Release to Backend Server*/
    private class Post_Notification_Release extends AsyncTask<String, Integer, Void> {
        String notification_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.notif_release_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                notification_id = params[0];
                notif_release_status = Notification_Release
                        .Get_Notif_Release_Data(Notifications_List_Activity.this, params[0]);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (notif_release_status != null && !notif_release_status.equals("")) {
                if (notif_release_status.get("response_status")
                        .equalsIgnoreCase("success")) {
                    String aufnr = notif_release_status.get("response_data");
                    new Get_DORD_Data().execute(aufnr, notification_id);
                } else if (notif_release_status.get("response_status")
                        .contains("already released")) {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            getString(R.string.notif_alreadyreleased, notification_id));
                } else if (notif_release_status.get("response_status").startsWith("E")) {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            notif_release_status.get("response_status").substring(1));
                } else {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            getString(R.string.notifrel_unable));
                }
            } else {
                progressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                        getString(R.string.notifrel_unable));
            }
        }
    }
    /*Posting Notification Release to Backend Server*/

    /*Getting Order Data After Notification Release*/
    private class Get_DORD_Data extends AsyncTask<String, Integer, Void> {
        String notification_id = "", aufnr = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                aufnr = params[0];
                notification_id = params[1];
                DORD_Status = Orders.Get_DORD_Data(Notifications_List_Activity.this,
                        "Single_Ord", aufnr);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss_progress_dialog();
            success_dialog.show_success_dialog(Notifications_List_Activity.this,
                    getString(R.string.notifrel_creatorder, notification_id, aufnr));
            new Get_Notifications_List_Data().execute();
        }
    }
    /*Getting Order Data After Notification Release*/

    /*Posting Notification Postpone to Backend Server*/
    private class Post_Notification_Postpone extends AsyncTask<String, Integer, Void> {
        String notification_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.notif_postpone_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                notification_id = params[0];
                notif_postpone_status = Notification_Postpone
                        .Get_Notif_Postpone_Data(Notifications_List_Activity.this, params[0]);
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
            if (notif_postpone_status != null && !notif_postpone_status.equals("")) {
                if (notif_postpone_status.equalsIgnoreCase("success")) {
                    progressDialog.dismiss_progress_dialog();
                    success_dialog.show_success_dialog(Notifications_List_Activity.this,
                            getString(R.string.notifpostpone_success, notification_id));
                    new Get_Notifications_List_Data().execute();
                } else if (notif_postpone_status.contains("already postponed")) {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            getString(R.string.notifpostpone_already, notification_id));
                } else if (notif_postpone_status.startsWith("E")) {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            notif_postpone_status.substring(0));
                } else {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            getString(R.string.notifpostpone_fail));
                }
            } else {
                progressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                        getString(R.string.notifpostpone_fail));
            }
        }
    }
    /*Posting Notification Postpone to Backend Server*/

    private class Get_Token extends AsyncTask<String, Integer, Void> {
        String token_status = "", notification_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.notif_complete_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                notification_id = params[0];
                token_status = Token.Get_Token(Notifications_List_Activity.this);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (token_status.equalsIgnoreCase("success")) {
                progressDialog.dismiss_progress_dialog();
                new Post_Notification_Complete().execute(notification_id);
            } else {
                progressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                        getString(R.string.notifcomplete_fail));
            }
        }
    }

    /*Posting Notification Complete to Backend Server*/
    private class Post_Notification_Complete extends AsyncTask<String, Integer, Void> {
        String notification_id = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.notif_complete_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                notification_id = params[0];
                notif_complete_status = Notification_Complete
                        .Get_NOCO_Data(Notifications_List_Activity.this, params[0]);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (notif_complete_status != null && !notif_complete_status.equals("")) {
                if (notif_complete_status.equalsIgnoreCase("success")) {
                    ContentValues cv = new ContentValues();
                    cv.put("Xstatus", "NOCO");
                    cv.put("STATUS", "NOCO");
                    App_db.update("DUE_NOTIFICATION_NotifHeader", cv, "Qmnum=?",
                            new String[]{notification_id});
                    progressDialog.dismiss_progress_dialog();
                    success_dialog.show_success_dialog(Notifications_List_Activity.this,
                            getString(R.string.notifcomplete_success, notification_id));
                    new Get_Notifications_List_Data().execute();
                } else if (notif_complete_status.startsWith("E")) {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            notif_complete_status.substring(1));
                } else {
                    progressDialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_List_Activity.this,
                            notif_complete_status);
                }
            } else {
                progressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_List_Activity.this,
                        getString(R.string.notifcomplete_fail));
            }
        }
    }
    /*Posting Notification Complete to Backend Server*/

    private class Get_Notification_Data extends AsyncTask<String, Integer, Void> {
        String notification_id = "", notification_uuid = "";
        NotifHeaderPrcbl nhp;
        ArrayList<NotifCausCodActvPrcbl> activity_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
        ArrayList<NotifCausCodActvPrcbl> causecode_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
        ArrayList<Notif_EtDocs_Parcelable> etdocs_parcablearray = new ArrayList<Notif_EtDocs_Parcelable>();
        ArrayList<NotifTaskPrcbl> tasks_parcablearray = new ArrayList<NotifTaskPrcbl>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_withnum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_withoutnum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_array = new ArrayList<Notif_Status_WithNum_Prcbl>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_List_Activity.this,
                    getResources().getString(R.string.fetching_notifcation));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                notification_id = params[0];
                notification_uuid = params[1];

                /*Fetching Data for Notification Activity*/
                Cursor cursor = null;
                try {
                    activity_parcablearray.clear();
                    cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifActvs " +
                            "where UUID = ?", new String[]{notification_uuid});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                NotifCausCodActvPrcbl nap = new NotifCausCodActvPrcbl();
                                nap.setQmnum(cursor.getString(2));
                                nap.setItmKey(cursor.getString(3));
                                nap.setItmPrtGrp("");
                                nap.setPrtGrpTxt(cursor.getString(4));//Object Part
                                nap.setItmPrtCod(cursor.getString(8));//Damage
                                nap.setPrtCodTxt("");
                                nap.setDefectGrpTxt("");
                                nap.setItmDefectCod("");
                                nap.setDefectCodTxt("");
                                nap.setItmDefectShTxt("");
                                nap.setCausKey(cursor.getString(13));
                                nap.setActvKey(cursor.getString(14));
                                nap.setActvGrp(cursor.getString(15));
                                nap.setActGrpTxt(cursor.getString(16));
                                nap.setActvCod(cursor.getString(17));
                                nap.setActvCodTxt(cursor.getString(18));
                                nap.setActvShTxt(cursor.getString(19));
                                nap.setStartDate(cursor.getString(20));
                                nap.setStartTime(cursor.getString(21));
                                nap.setEndDate(cursor.getString(22));
                                nap.setEndTime(cursor.getString(23));
                                nap.setUsr05(cursor.getString(28));
                                nap.setFields(cursor.getString(29));
                                nap.setStatus(cursor.getString(30));
                                nap.setAction(cursor.getString(30));


                                /*Fetching Data for ItmPrtGrp, ItmDefectGrp from EtNotifItems*/
                                Cursor cursor1 = null;
                                try {
                                    cursor1 = App_db.rawQuery("select * from " +
                                            "DUE_NOTIFICATIONS_EtNotifItems where UUID = ? and " +
                                            "ItemKey = ?", new String[]{notification_uuid,
                                            cursor.getString(3)});
                                    if (cursor1 != null && cursor1.getCount() > 0) {
                                        if (cursor1.moveToFirst()) {
                                            do {
                                                nap.setItmPrtGrp(cursor1.getString(4));
                                                nap.setItmDefectGrp(cursor1.getString(8));
                                            }
                                            while (cursor1.moveToNext());
                                        }
                                    } else {
                                        cursor.close();
                                        nap.setItmPrtGrp("");
                                        nap.setItmDefectGrp("");
                                    }
                                } catch (Exception e) {
                                    if (cursor1 != null) {
                                        cursor1.close();
                                    }
                                    nap.setItmPrtGrp("");
                                    nap.setItmDefectGrp("");
                                } finally {
                                    if (cursor1 != null) {
                                        cursor1.close();
                                    }
                                }
                                /*Fetching Data for ItmPrtGrp, ItmDefectGrp from EtNotifItems*/

                                /*Adding Activity Data to Header Object*/
                                activity_parcablearray.add(nap);
                                /*Adding Activity Data to Header Object*/
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                        cursor.close();
                    }
                } catch (Exception e) {
                    if (cursor != null) {
                        cursor.close();
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                /*Fetching Data for Notification Activity*/

                /*Fetching Data for Notification Status for Displaying in Notification Header*/
                Cursor stats_cursor = null;
                StringBuilder not_stBuilder = new StringBuilder();
                try {
                    stats_cursor = App_db.rawQuery("select * from EtNotifStatus where UUID = ?",
                            new String[]{notification_uuid});
                    if (stats_cursor != null && stats_cursor.getCount() > 0) {
                        if (stats_cursor.moveToFirst()) {
                            do {
                                String act = stats_cursor.getString(12);
                                String Stonr = stats_cursor.getString(8);
                                String Stat = stats_cursor.getString(11);
                                if (!Stonr.equals("00") && Stat.startsWith("E")) {
                                    if (act.equalsIgnoreCase("X")) {
                                        String txt04 = stats_cursor.getString(13);
                                        not_stBuilder.append(txt04);
                                        not_stBuilder.append(" ");
                                    }
                                } else if (Stonr.equals("00") && Stat.startsWith("E")) {
                                    if (act.equalsIgnoreCase("X")) {
                                        String txt04 = stats_cursor.getString(13);
                                        not_stBuilder.append(txt04);
                                        not_stBuilder.append(" ");
                                    }
                                }
                            }
                            while (stats_cursor.moveToNext());
                        }
                    } else {
                        if (stats_cursor != null) {
                            stats_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (stats_cursor != null) {
                        stats_cursor.close();
                    }
                } finally {
                    if (stats_cursor != null) {
                        stats_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status for Displaying in Notification Header*/

                /*Fetching Data for Notification EtDocs*/
                Cursor EtDocs_cursor = null;
                try {
                    EtDocs_cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_EtDocs " +
                            "where Zobjid = ?", new String[]{notification_id});
                    if (EtDocs_cursor != null && EtDocs_cursor.getCount() > 0) {
                        if (EtDocs_cursor.moveToFirst()) {
                            do {
                                Notif_EtDocs_Parcelable nap = new Notif_EtDocs_Parcelable();
                                nap.setZobjid(EtDocs_cursor.getString(2));
                                nap.setZdoctype(EtDocs_cursor.getString(3));
                                nap.setZdoctypeitem(EtDocs_cursor.getString(4));
                                nap.setFilename(EtDocs_cursor.getString(5));
                                nap.setFiletype(EtDocs_cursor.getString(6));
                                nap.setFsize(EtDocs_cursor.getString(7));
//                                nap.setContent(EtDocs_cursor.getString(8));
                                nap.setDocid(EtDocs_cursor.getString(9));
                                nap.setDoctype(EtDocs_cursor.getString(10));
                                nap.setObjtype(EtDocs_cursor.getString(11));
                                nap.setFilepath(EtDocs_cursor.getString(12));
                                nap.setStatus(EtDocs_cursor.getString(13));
                                nap.setContentX(EtDocs_cursor.getString(14));
                                etdocs_parcablearray.add(nap);
                            }
                            while (EtDocs_cursor.moveToNext());
                        }
                    } else {
                        if (EtDocs_cursor != null) {
                            EtDocs_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (EtDocs_cursor != null) {
                        EtDocs_cursor.close();
                    }
                } finally {
                    if (EtDocs_cursor != null) {
                        EtDocs_cursor.close();
                    }
                }
                /*Fetching Data for Notification EtDocs*/

                /*Fetching Data for Notification Causecode*/
                Cursor Causecode_cursor = null;
                try {
                    causecode_parcablearray.clear();
                    Causecode_cursor = App_db.rawQuery("select * from " +
                                    "DUE_NOTIFICATIONS_EtNotifItems where UUID = ?",
                            new String[]{notification_uuid});
                    if (Causecode_cursor != null && Causecode_cursor.getCount() > 0) {
                        if (Causecode_cursor.moveToFirst()) {
                            do {
                                NotifCausCodActvPrcbl nccp = new NotifCausCodActvPrcbl();
                                nccp.setQmnum(Causecode_cursor.getString(2));
                                nccp.setItmKey(Causecode_cursor.getString(3));
                                nccp.setItmPrtGrp(Causecode_cursor.getString(4));
                                nccp.setPrtGrpTxt(Causecode_cursor.getString(5));
                                nccp.setItmPrtCod(Causecode_cursor.getString(6));
                                nccp.setPrtCodTxt(Causecode_cursor.getString(7));
                                nccp.setItmDefectGrp(Causecode_cursor.getString(8));
                                nccp.setDefectGrpTxt(Causecode_cursor.getString(9));
                                nccp.setItmDefectCod(Causecode_cursor.getString(10));
                                nccp.setDefectCodTxt(Causecode_cursor.getString(11));
                                nccp.setItmDefectShTxt(Causecode_cursor.getString(12));
                                nccp.setCausKey(Causecode_cursor.getString(13));
                                nccp.setCausGrp(Causecode_cursor.getString(14));
                                nccp.setCausGrpTxt(Causecode_cursor.getString(15));
                                nccp.setCausCod(Causecode_cursor.getString(16));
                                nccp.setCausCodTxt(Causecode_cursor.getString(17));
                                nccp.setCausShTxt(Causecode_cursor.getString(18));
                                nccp.setUsr01(Causecode_cursor.getString(19));
                                nccp.setUsr02(Causecode_cursor.getString(20));
                                nccp.setUsr03(Causecode_cursor.getString(21));
                                nccp.setUsr04(Causecode_cursor.getString(22));
                                nccp.setUsr05(Causecode_cursor.getString(23));
                                nccp.setStatus(Causecode_cursor.getString(24));
                                causecode_parcablearray.add(nccp);
                            }
                            while (Causecode_cursor.moveToNext());
                        }
                    } else {
                        if (Causecode_cursor != null) {
                            Causecode_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (Causecode_cursor != null) {
                        Causecode_cursor.close();
                    }
                } finally {
                    if (Causecode_cursor != null) {
                        Causecode_cursor.close();
                    }
                }
                /*Fetching Data for Notification Causecode*/

                /*Fetching Data for Notification Tasks*/
                Cursor Tasks_cursor = null;
                try {
                    tasks_parcablearray.clear();
                    Tasks_cursor = App_db.rawQuery("select * from " +
                                    "DUE_NOTIFICATION_EtNotifTasks where UUID = ?",
                            new String[]{notification_uuid});
                    if (Tasks_cursor != null && Tasks_cursor.getCount() > 0) {
                        if (Tasks_cursor.moveToFirst()) {
                            do {
                                NotifTaskPrcbl nccp = new NotifTaskPrcbl();
                                nccp.setQmnum(Tasks_cursor.getString(2));
                                nccp.setItemKey(Tasks_cursor.getString(13));
                                nccp.setItempartGrp(Tasks_cursor.getString(4));
                                nccp.setPartgrptext(Tasks_cursor.getString(5));
                                nccp.setItempartCod(Tasks_cursor.getString(6));
                                nccp.setPartcodetext(Tasks_cursor.getString(7));
                                nccp.setItemdefectGrp(Tasks_cursor.getString(8));
                                nccp.setDefectgrptext(Tasks_cursor.getString(9));
                                nccp.setItemdefectCod(Tasks_cursor.getString(10));
                                nccp.setDefectcodetext(Tasks_cursor.getString(11));
                                nccp.setItemdefectShtxt(Tasks_cursor.getString(12));
                                nccp.setTaskKey(Tasks_cursor.getString(13));
                                nccp.setTaskGrp(Tasks_cursor.getString(14));
                                nccp.setTaskgrptext(Tasks_cursor.getString(15));
                                nccp.setTaskCod(Tasks_cursor.getString(16));
                                nccp.setTaskcodetext(Tasks_cursor.getString(17));
                                nccp.setTaskShtxt(Tasks_cursor.getString(18));
                                nccp.setPster(Tasks_cursor.getString(19));
                                nccp.setPeter(Tasks_cursor.getString(20));
                                nccp.setPstur(Tasks_cursor.getString(21));
                                nccp.setPetur(Tasks_cursor.getString(22));
                                nccp.setParvw(Tasks_cursor.getString(23));
                                nccp.setParnr(Tasks_cursor.getString(24));
                                nccp.setErlnam(Tasks_cursor.getString(25));
                                nccp.setErldat(Tasks_cursor.getString(26));
                                nccp.setErlzeit(Tasks_cursor.getString(27));
                                nccp.setRelease(Tasks_cursor.getString(28));
                                nccp.setComplete(Tasks_cursor.getString(29));
                                nccp.setSuccess(Tasks_cursor.getString(30));
                                nccp.setUserStatus(Tasks_cursor.getString(31));
                                nccp.setSysStatus(Tasks_cursor.getString(32));
                                nccp.setSmsttxt(Tasks_cursor.getString(33));
                                nccp.setSmastxt(Tasks_cursor.getString(34));
                                nccp.setUsr01(Tasks_cursor.getString(35));
                                nccp.setUsr02(Tasks_cursor.getString(36));
                                nccp.setUsr03(Tasks_cursor.getString(37));
                                nccp.setUsr04(Tasks_cursor.getString(38));
                                nccp.setUsr05(Tasks_cursor.getString(39));
                                nccp.setAction(Tasks_cursor.getString(40));
                                tasks_parcablearray.add(nccp);
                            }
                            while (Tasks_cursor.moveToNext());
                        }
                    } else {
                        if (Tasks_cursor != null) {
                            Tasks_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (Tasks_cursor != null) {
                        Tasks_cursor.close();
                    }
                } finally {
                    if (Tasks_cursor != null) {
                        Tasks_cursor.close();
                    }
                }
                /*Fetching Data for Notification Tasks*/

                /*Fetching Data for Notification Status With Number*/
                Cursor status_withnum_cursor = null;
                try {
                    status_withnum_array.clear();
                    status_withnum_cursor = App_db.rawQuery("select * from EtNotifStatus where" +
                            " Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_withnum_cursor != null && status_withnum_cursor.getCount() > 0) {
                        if (status_withnum_cursor.moveToFirst()) {
                            do {
                                String Stonr = status_withnum_cursor.getString(8);
                                String Stat = status_withnum_cursor.getString(11);
                                if (!Stonr.equals("00") && Stat.startsWith("E")) {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_withnum_cursor.getString(2));
                                    nap.setAufnr(status_withnum_cursor.getString(3));
                                    nap.setObjnr(status_withnum_cursor.getString(4));
                                    nap.setManum(status_withnum_cursor.getString(5));
                                    nap.setStsma(status_withnum_cursor.getString(6));
                                    nap.setInist(status_withnum_cursor.getString(7));
                                    nap.setStonr(status_withnum_cursor.getString(8));
                                    nap.setHsonr(status_withnum_cursor.getString(9));
                                    nap.setNsonr(status_withnum_cursor.getString(10));
                                    nap.setStat(status_withnum_cursor.getString(11));
                                    nap.setAct(status_withnum_cursor.getString(12));
                                    nap.setTxt04(status_withnum_cursor.getString(13));
                                    nap.setTxt30(status_withnum_cursor.getString(14));
                                    String Act_Status = status_withnum_cursor.getString(12);
                                    if (Act_Status.equalsIgnoreCase("X")) {
                                        nap.setAct_Status("true");
                                    } else {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_withnum_array.add(nap);
                                }
                            }
                            while (status_withnum_cursor.moveToNext());
                        }
                    } else {
                        if (status_withnum_cursor != null) {
                            status_withnum_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (status_withnum_cursor != null) {
                        status_withnum_cursor.close();
                    }
                } finally {
                    if (status_withnum_cursor != null) {
                        status_withnum_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status With Number*/

                /*Fetching Data for Notification Status Without Number*/
                Cursor status_withoutnum_cursor = null;
                try {
                    status_withoutnum_array.clear();
                    status_withoutnum_cursor = App_db.rawQuery("select * from EtNotifStatus " +
                            "where Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_withoutnum_cursor != null && status_withoutnum_cursor.getCount() > 0) {
                        if (status_withoutnum_cursor.moveToFirst()) {
                            do {
                                String Stonr = status_withoutnum_cursor.getString(8);
                                String Stat = status_withoutnum_cursor.getString(11);
                                if (Stonr.equals("00") && Stat.startsWith("E")) {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_withoutnum_cursor.getString(2));
                                    nap.setAufnr(status_withoutnum_cursor.getString(3));
                                    nap.setObjnr(status_withoutnum_cursor.getString(4));
                                    nap.setManum(status_withoutnum_cursor.getString(5));
                                    nap.setStsma(status_withoutnum_cursor.getString(6));
                                    nap.setInist(status_withoutnum_cursor.getString(7));
                                    nap.setStonr(status_withoutnum_cursor.getString(8));
                                    nap.setHsonr(status_withoutnum_cursor.getString(9));
                                    nap.setNsonr(status_withoutnum_cursor.getString(10));
                                    nap.setStat(status_withoutnum_cursor.getString(11));
                                    nap.setAct(status_withoutnum_cursor.getString(12));
                                    nap.setTxt04(status_withoutnum_cursor.getString(13));
                                    nap.setTxt30(status_withoutnum_cursor.getString(14));
                                    String Act_Status = status_withoutnum_cursor.getString(12);
                                    if (Act_Status.equalsIgnoreCase("X")) {
                                        nap.setAct_Status("true");
                                    } else {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_withoutnum_array.add(nap);
                                }
                            }
                            while (status_withoutnum_cursor.moveToNext());
                        }
                    } else {
                        if (status_withoutnum_cursor != null) {
                            status_withoutnum_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (status_withoutnum_cursor != null) {
                        status_withoutnum_cursor.close();
                    }
                } finally {
                    if (status_withoutnum_cursor != null) {
                        status_withoutnum_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status Without Number*/

                /*Fetching Data for Notification Status System_Status Number*/
                Cursor status_system_cursor = null;
                try {
                    status_systemstatus_array.clear();
                    status_system_cursor = App_db.rawQuery("select * from EtNotifStatus where" +
                            " Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_system_cursor != null && status_system_cursor.getCount() > 0) {
                        if (status_system_cursor.moveToFirst()) {
                            do {
                                String Stat = status_system_cursor.getString(11);
                                if (Stat.startsWith("I")) {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_system_cursor.getString(2));
                                    nap.setAufnr(status_system_cursor.getString(3));
                                    nap.setObjnr(status_system_cursor.getString(4));
                                    nap.setManum(status_system_cursor.getString(5));
                                    nap.setStsma(status_system_cursor.getString(6));
                                    nap.setInist(status_system_cursor.getString(7));
                                    nap.setStonr(status_system_cursor.getString(8));
                                    nap.setHsonr(status_system_cursor.getString(9));
                                    nap.setNsonr(status_system_cursor.getString(10));
                                    nap.setStat(status_system_cursor.getString(11));
                                    nap.setAct(status_system_cursor.getString(12));
                                    nap.setTxt04(status_system_cursor.getString(13));
                                    nap.setTxt30(status_system_cursor.getString(14));
                                    String Act_Status = status_system_cursor.getString(12);
                                    if (Act_Status.equalsIgnoreCase("X")) {
                                        nap.setAct_Status("true");
                                    } else {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_systemstatus_array.add(nap);
                                }
                            }
                            while (status_system_cursor.moveToNext());
                        }
                    } else {
                        if (status_system_cursor != null) {
                            status_system_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (status_system_cursor != null) {
                        status_system_cursor.close();
                    }
                } finally {
                    if (status_system_cursor != null) {
                        status_system_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status System_Status Number*/

                /*Fetching Data for Notification Header*/
                Cursor headerdata_cursor = null;
                try {
                    headerdata_cursor = App_db.rawQuery("select * from " +
                                    "DUE_NOTIFICATION_NotifHeader where UUID = ?",
                            new String[]{notification_uuid});
                    if (headerdata_cursor != null && headerdata_cursor.getCount() > 0) {
                        if (headerdata_cursor.moveToFirst()) {
                            do {
                                nhp = new NotifHeaderPrcbl();
                                nhp.setUUID(notification_uuid);
                                nhp.setNotf_Status(not_stBuilder.toString());
                                nhp.setNotifType(headerdata_cursor.getString(2));
                                nhp.setQmnum(headerdata_cursor.getString(3));
                                nhp.setNotifShrtTxt(headerdata_cursor.getString(4));
                                nhp.setFuncLoc(headerdata_cursor.getString(5));
                                nhp.setEquip(headerdata_cursor.getString(6));
                                nhp.setBautl(headerdata_cursor.getString(7));
                                nhp.setReportedBy(headerdata_cursor.getString(8));
                                nhp.setMalfuncStDt(headerdata_cursor.getString(9));
                                nhp.setMalfuncEdDt(headerdata_cursor.getString(10));
                                nhp.setMalfuncSttm(headerdata_cursor.getString(11));
                                nhp.setMalfuncEdtm(headerdata_cursor.getString(12));
                                nhp.setBrkDwnInd(headerdata_cursor.getString(13));
                                nhp.setPriority(headerdata_cursor.getString(14));
                                nhp.setIngrp(headerdata_cursor.getString(15));
                                nhp.setArbpl(headerdata_cursor.getString(16));
                                nhp.setWerks(headerdata_cursor.getString(17));
                                nhp.setStrmn(headerdata_cursor.getString(18));
                                nhp.setLtrmn(headerdata_cursor.getString(19));
                                nhp.setAufnr(headerdata_cursor.getString(20));
                                nhp.setDocs(headerdata_cursor.getString(21));
                                nhp.setAltitude(headerdata_cursor.getString(22));
                                nhp.setLatitude(headerdata_cursor.getString(23));
                                nhp.setLongitude(headerdata_cursor.getString(24));
                                nhp.setClosed(headerdata_cursor.getString(25));
                                nhp.setCompleted(headerdata_cursor.getString(26));
                                nhp.setCreatedOn(headerdata_cursor.getString(27));
                                nhp.setQmartx(headerdata_cursor.getString(28));
                                nhp.setPltxt(headerdata_cursor.getString(29));
                                nhp.setEquipTxt(headerdata_cursor.getString(30));
                                nhp.setPriorityTxt(headerdata_cursor.getString(31));
                                nhp.setAufTxt(headerdata_cursor.getString(32));
                                nhp.setAuartTxt(headerdata_cursor.getString(33));
                                nhp.setPlantTxt(headerdata_cursor.getString(34));
                                nhp.setWrkCntrTxt(headerdata_cursor.getString(35));
                                nhp.setIngrpName(headerdata_cursor.getString(36));
                                nhp.setMaktx(headerdata_cursor.getString(37));
                                nhp.setXStatus(headerdata_cursor.getString(44));
                                nhp.setUsr01(headerdata_cursor.getString(39));
                                nhp.setUsr02(headerdata_cursor.getString(40));
                                nhp.setUsr03(headerdata_cursor.getString(41));
                                nhp.setUsr04(headerdata_cursor.getString(42));
                                nhp.setUsr05(headerdata_cursor.getString(43));
                                nhp.setParnrVw(headerdata_cursor.getString(45));
                                nhp.setNameVw(headerdata_cursor.getString(46));
                                nhp.setAuswk(headerdata_cursor.getString(47));
                                nhp.setShift(headerdata_cursor.getString(48));
                                nhp.setNoOfPerson(headerdata_cursor.getString(49));
                                nhp.setAuswkt(headerdata_cursor.getString(50));
                                nhp.setStrur(headerdata_cursor.getString(51));
                                nhp.setLtrur(headerdata_cursor.getString(52));
                                nhp.setQmdat(headerdata_cursor.getString(54));
                                if (headerdata_cursor.getString(6) != null &&
                                        !headerdata_cursor.getString(6).equals(""))
                                    nhp.setIwerk(getIwerk(headerdata_cursor.getString(6)));
                                else if (headerdata_cursor.getString(5) != null &&
                                        !headerdata_cursor.getString(5).equals(""))
                                    nhp.setIwerk(getfuncIwerk(headerdata_cursor.getString(5)));
                                else
                                    nhp.setIwerk("");
                                nhp.setActvPrcblAl(activity_parcablearray);
                                nhp.setCausCodPrcblAl(causecode_parcablearray);
                                nhp.setEtDocsParcelables(etdocs_parcablearray);
                                nhp.setStatus_withNum_prcbls(status_withnum_array);
                                nhp.setStatus_withoutNum_prcbls(status_withoutnum_array);
                                nhp.setStatus_systemstatus_prcbls(status_systemstatus_array);
                                nhp.setNotifTaskPrcbls(tasks_parcablearray);
                            }
                            while (headerdata_cursor.moveToNext());
                        }
                    } else {
                        if (headerdata_cursor != null) {
                            headerdata_cursor.close();
                        }
                    }
                } catch (Exception e) {
                    if (headerdata_cursor != null) {
                        headerdata_cursor.close();
                    }
                } finally {
                    if (headerdata_cursor != null) {
                        headerdata_cursor.close();
                    }
                }
                /*Fetching Data for Notification Header*/
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss_progress_dialog();
            if (nhp != null) {
                Intent ordrIntent = new Intent(Notifications_List_Activity.this,
                        Notifications_Change_Activity.class);
                ordrIntent.putExtra("notif_status", "U");
                ordrIntent.putExtra("notif_parcel", nhp);
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

    private void filterData(List<Notif_List_Object> notifications_list_ad) {
        if (filt_notification_ids != null && !filt_notification_ids.equals("")) {
            if (filt_priority_ids != null && !filt_priority_ids.equals("")) {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids);

                                    }
                                });
                            }
                        }
                    }
                } else {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids);

                                    }
                                });
                            }
                        }
                    }
                }
            } else {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids);

                                    }
                                });
                            }
                        }
                    }
                } else {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getNotifType().matches(filt_selected_notif_ids);

                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        else
        {
            if (filt_priority_ids != null && !filt_priority_ids.equals("")) {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids);

                                    }
                                });
                            }
                        }
                    }
                } else {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getpriority_id().matches(filt_selected_prior_ids);

                                    }
                                });
                            }
                        }
                    }
                }
            } else {
                if (filt_status_ids != null && !filt_status_ids.equals("")) {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getstatus().matches(filt_selected_status_ids);

                                    }
                                });
                            }
                        }
                    }
                } else {
                    if (filt_wckt_ids != null && !filt_wckt_ids.equals("")) {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getArbpl().matches(filt_selected_wckt_ids);

                                    }
                                });
                            }
                        }
                    } else {
                        if (attachment_clicked_status != null && !attachment_clicked_status.equals("")) {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status)
                                                && ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
                                    }
                                });
                            } else {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getdocs_status().matches(attachment_clicked_status);
                                    }
                                });
                            }
                        } else {
                            if (pers_resp_status != null && !pers_resp_status.equals("")) {
                                CollectionUtils.filter(notifications_list_ad, new Predicate() {
                                    @Override
                                    public boolean evaluate(Object o) {
                                        return ((Notif_List_Object) o).getParnrVw().matches(person_responsible_id);
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

