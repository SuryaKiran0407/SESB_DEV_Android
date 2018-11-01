package com.enstrapp.fieldtekpro.equipment_inspection;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Equipment_History_Activity extends AppCompatActivity implements View.OnClickListener {

    String equipment_id = "";
    TextView title_textview, searchview_textview, no_data_textview;
    ImageView back_imageview;
    SearchView search;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    SwipeRefreshLayout swiperefreshlayout;
    RecyclerView list_recycleview;
    FloatingActionButton refresh_fab_button;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String password = "", username = "", device_serial_number = "", device_id = "", device_uuid = "";
    SharedPreferences app_sharedpreferences;
    SharedPreferences.Editor app_editor;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    private List<History_List_Object> history_list = new ArrayList<>();
    History_Adapter history_adapter;
    int response_status_code = 0;
    Response<EquipmentHistory_SER> response;
    LinearLayout no_data_layout;
    Dialog decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equipment_id = extras.getString("equipment_id");
        }

        title_textview = (TextView) findViewById(R.id.title_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        search = (SearchView) findViewById(R.id.search);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        list_recycleview = (RecyclerView) findViewById(R.id.history_list_recycleview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);


        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(Equipment_History_Activity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));


        DATABASE_NAME = Equipment_History_Activity.this.getString(R.string.database_name);
        FieldTekPro_db = Equipment_History_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            new Get_History_Data().execute();
        } else {
            network_connection_dialog.show_network_connection_dialog(Equipment_History_Activity.this);
        }


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


        back_imageview.setOnClickListener(this);

    }


    public void Refresh_Data() {
        cd = new ConnectionDetector(Equipment_History_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(Equipment_History_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Equipment_History_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
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
                    new Get_History_Data().execute();
                }
            });
        } else {
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(Equipment_History_Activity.this);
        }
    }


    public class Get_History_Data extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Equipment_History_Activity.this, getResources().getString(R.string.loading_history));
            history_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                /* Initializing Shared Preferences */
                app_sharedpreferences = Equipment_History_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username", null);
                password = app_sharedpreferences.getString("Password", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EH", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Equipment_History_Activity.this.getString(R.string.ip_address);
                Map<String, String> map = new HashMap<>();
                map.put("IvUser", username);
                map.put("Muser", username.toUpperCase().toString());
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("IvTransmitType", "LOAD");
                map.put("Equnr", equipment_id);
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<EquipmentHistory_SER> call = service.getEQUIPHistoryData(url_link, basic, map);
                response = call.execute();
                response_status_code = response.code();
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
            if (response_status_code == 200) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        /*Reading Response Data and Parsing to Serializable*/
                        EquipmentHistory_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults().get(0).getNavEquipHistory().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                        if (response_data_jsonArray.length() > 0) {
                            for (int i = 0; i < response_data_jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                                String date = jsonObject.optString("Ausvn");
                                String date_formateed = "";
                                if (date != null && !date.equals("")) {
                                    if (date.equals("00000000")) {
                                        date_formateed = "";
                                    } else {
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(date);
                                            String outputDateStr = outputFormat.format(date1);
                                            date_formateed = outputDateStr;
                                        } catch (Exception e) {
                                            date_formateed = "";
                                        }
                                    }
                                } else {
                                    date_formateed = "";
                                }
                                String Ausbs_date = jsonObject.optString("Ausbs");
                                String Ausbs_date_formateed = "";
                                if (Ausbs_date != null && !Ausbs_date.equals("")) {
                                    if (Ausbs_date.equals("00000000")) {
                                        Ausbs_date_formateed = "";
                                    } else {
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(Ausbs_date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Ausbs_date_formateed = outputDateStr;
                                        } catch (Exception e) {
                                            Ausbs_date_formateed = "";
                                        }
                                    }
                                } else {
                                    Ausbs_date_formateed = "";
                                }
                                String Qmdab_date = jsonObject.optString("Qmdab");
                                String Qmdab_date_formateed = "";
                                if (Qmdab_date != null && !Qmdab_date.equals("")) {
                                    if (Qmdab_date.equals("00000000")) {
                                        Qmdab_date_formateed = "";
                                    } else {
                                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(Qmdab_date);
                                            String outputDateStr = outputFormat.format(date1);
                                            Qmdab_date_formateed = outputDateStr;
                                        } catch (Exception e) {
                                            Qmdab_date_formateed = "";
                                        }
                                    }
                                } else {
                                    Qmdab_date_formateed = "";
                                }
                                String Qmart = jsonObject.optString("Qmart");
                                String Qmartx = "";
                                try {
                                    Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_NOTIFICATION_TYPES where Qmart = ?", new String[]{Qmart});
                                    if (cursor != null && cursor.getCount() > 0) {
                                        if (cursor.moveToFirst()) {
                                            do {
                                                Qmartx = cursor.getString(2);
                                            }
                                            while (cursor.moveToNext());
                                        }
                                    } else {
                                        cursor.close();
                                    }
                                } catch (Exception e) {
                                }
                                String Priok = jsonObject.optString("Priok");
                                String Priokx = "";
                                try {
                                    Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_NOTIFICATION_PRIORITY where Priok = ?", new String[]{Priok});
                                    if (cursor != null && cursor.getCount() > 0) {
                                        if (cursor.moveToFirst()) {
                                            do {
                                                Priokx = cursor.getString(2);
                                            }
                                            while (cursor.moveToNext());
                                        }
                                    } else {
                                        cursor.close();
                                    }
                                } catch (Exception e) {
                                }
                                History_List_Object olo = new History_List_Object(Qmart + " - " + Qmartx, jsonObject.optString("Qmnum"), date_formateed, Ausbs_date_formateed, jsonObject.optString("Aufnr"), Priok + " - " + Priokx, jsonObject.optString("Qmtxt"), Qmdab_date_formateed);
                                history_list.add(olo);
                            }
                            if (history_list.size() > 0) {
                                history_adapter = new History_Adapter(Equipment_History_Activity.this, history_list);
                                list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_History_Activity.this);
                                list_recycleview.setLayoutManager(layoutManager);
                                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                list_recycleview.setAdapter(history_adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                search.setVisibility(View.VISIBLE);
                                swiperefreshlayout.setVisibility(View.VISIBLE);
                                title_textview.setText(getString(R.string.equip_history1) + " : " + equipment_id + " (" + history_list.size() + ")");
                                progressDialog.dismiss_progress_dialog();
                                no_data_layout.setVisibility(View.GONE);
                            } else {
                                title_textview.setText(getString(R.string.equip_history1) + " : " + equipment_id + " (0)");
                                no_data_textview.setVisibility(View.VISIBLE);
                                search.setVisibility(View.GONE);
                                swiperefreshlayout.setVisibility(View.GONE);
                                progressDialog.dismiss_progress_dialog();
                                no_data_layout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            title_textview.setText(getString(R.string.equip_history1) + " (0)");
                            no_data_textview.setVisibility(View.VISIBLE);
                            search.setVisibility(View.GONE);
                            swiperefreshlayout.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss_progress_dialog();
                        }
                        /*Reading Data by using FOR Loop*/
                    } catch (Exception e) {
                    }
                }
            } else {
                title_textview.setText(getString(R.string.equip_history1) + " : " + equipment_id + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                progressDialog.dismiss_progress_dialog();
            }
        }
    }


    public class History_Adapter extends RecyclerView.Adapter<History_Adapter.MyViewHolder> {
        private Context mContext;
        private List<History_List_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView comp_date_textview, short_text_textview, priority_textview, notif_type, notif_no, mal_stdate_textview, enddate_textview, order_num_textview;

            public MyViewHolder(View view) {
                super(view);
                notif_type = (TextView) view.findViewById(R.id.notif_type_textview);
                notif_no = (TextView) view.findViewById(R.id.notif_no_textview);
                mal_stdate_textview = (TextView) view.findViewById(R.id.mal_stdate_textview);
                enddate_textview = (TextView) view.findViewById(R.id.enddate_textview);
                order_num_textview = (TextView) view.findViewById(R.id.order_num_textview);
                priority_textview = (TextView) view.findViewById(R.id.priority_textview);
                short_text_textview = (TextView) view.findViewById(R.id.short_text_textview);
                comp_date_textview = (TextView) view.findViewById(R.id.comp_date_textview);
            }
        }

        public History_Adapter(Context mContext, List<History_List_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_history_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final History_List_Object olo = orders_list_data.get(position);
            holder.notif_type.setText(olo.getNotif_type());
            holder.notif_no.setText(olo.getnotif_no());
            holder.mal_stdate_textview.setText(olo.getmal_stdate());
            holder.enddate_textview.setText(olo.getenddate());
            holder.order_num_textview.setText(olo.getAufnr());
            holder.priority_textview.setText(olo.getPriok());
            holder.short_text_textview.setText(olo.getQmtxt());
            holder.comp_date_textview.setText(olo.getQmdab());
        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<History_List_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < history_list.size(); i++) {
                String Notif_type = history_list.get(i).getNotif_type().toLowerCase();
                String notif_no = history_list.get(i).getnotif_no().toLowerCase();
                String Aufnr = history_list.get(i).getAufnr().toLowerCase();
                String Qmtxt = history_list.get(i).getQmtxt().toLowerCase();
                if (Notif_type.contains(query) || notif_no.contains(query) || Aufnr.contains(query) || Qmtxt.contains(query)) {
                    History_List_Object nto = new History_List_Object(history_list.get(i).getNotif_type().toString(), history_list.get(i).getnotif_no().toString(), history_list.get(i).getmal_stdate().toString(), history_list.get(i).getenddate().toString(), history_list.get(i).getAufnr().toString(), history_list.get(i).getPriok().toString(), history_list.get(i).getQmtxt().toString(), history_list.get(i).getQmdab().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_History_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                history_adapter = new History_Adapter(Equipment_History_Activity.this, filteredList);
                list_recycleview.setAdapter(history_adapter);
                history_adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_textview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_textview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    public class History_List_Object {
        private String notif_type;
        private String notif_no;
        private String mal_stdate;
        private String enddate;
        private String Aufnr;
        private String Priok;
        private String Qmdab;
        private String Qmtxt;

        public String getQmdab() {
            return Qmdab;
        }

        public void setQmdab(String qmdab) {
            Qmdab = qmdab;
        }

        public String getQmtxt() {
            return Qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            Qmtxt = qmtxt;
        }

        public String getPriok() {
            return Priok;
        }

        public void setPriok(String priok) {
            Priok = priok;
        }

        public String getNotif_type() {
            return notif_type;
        }

        public void setNotif_type(String notif_type) {
            this.notif_type = notif_type;
        }

        public String getnotif_no() {
            return notif_no;
        }

        public void setnotif_no(String notif_no) {
            notif_no = notif_no;
        }

        public String getmal_stdate() {
            return mal_stdate;
        }

        public void setmal_stdate(String mal_stdate) {
            mal_stdate = mal_stdate;
        }

        public String getenddate() {
            return enddate;
        }

        public void setenddate(String enddate) {
            enddate = enddate;
        }

        public String getAufnr() {
            return Aufnr;
        }

        public void setAufnr(String Aufnr) {
            Aufnr = Aufnr;
        }

        public History_List_Object(String notif_type, String notif_no, String mal_stdate, String enddate, String Aufnr, String Priok, String Qmtxt, String Qmdab) {
            this.notif_type = notif_type;
            this.notif_no = notif_no;
            this.mal_stdate = mal_stdate;
            this.enddate = enddate;
            this.Aufnr = Aufnr;
            this.Priok = Priok;
            this.Qmtxt = Qmtxt;
            this.Qmdab = Qmdab;
        }

    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            Equipment_History_Activity.this.finish();
        }
    }

}
