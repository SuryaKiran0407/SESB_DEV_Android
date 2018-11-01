package com.enstrapp.fieldtekpro.history;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History_Activity extends AppCompatActivity {

    SearchView search;
    RecyclerView history_list_recycleview;
    SwipeRefreshLayout swiperefreshlayout;
    TextView searchview_textview, title_textview;
    FloatingActionButton refresh_fab_button;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    String password = "", username = "", device_serial_number = "", device_id = "", device_uuid = "";
    SharedPreferences app_sharedpreferences;
    SharedPreferences.Editor app_editor;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private List<History_List_Object> history_list = new ArrayList<>();
    LinearLayout no_data_layout;
    History_Adapter history_adapter;
    int response_status_code = 0;
    Response<History_SER> response;
    ImageView back_imageview;
    Dialog decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        history_list_recycleview = (RecyclerView) findViewById(R.id.history_list_recycleview);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        search = (SearchView) findViewById(R.id.search);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(History_Activity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);

        DATABASE_NAME = History_Activity.this.getString(R.string.database_name);
        App_db = History_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            //If Internet is available, call Get History Asynctask
            new Get_History_Data().execute();
        } else {
            //showing network error and navigating to wifi settings.
            network_connection_dialog.show_network_connection_dialog(History_Activity.this);
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

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History_Activity.this.finish();
            }
        });

    }


    public void Refresh_Data() {
        cd = new ConnectionDetector(History_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(History_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(History_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
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
            //showing network error and navigating to wifi settings.
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(History_Activity.this);
        }
    }


    public class Get_History_Data extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(History_Activity.this, getResources().getString(R.string.loading_history));
            history_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                /* Initializing Shared Preferences */
                app_sharedpreferences = History_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username", null);
                password = app_sharedpreferences.getString("Password", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                device_id = Settings.Secure.getString(History_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                device_serial_number = Build.SERIAL;
                String androidId = "" + Settings.Secure.getString(History_Activity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
                device_uuid = deviceUuid.toString();
                /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
                Cursor cursor = App_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"D2", "F4", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = History_Activity.this.getString(R.string.ip_address);
                Map<String, String> map = new HashMap<>();
                map.put("IvUser", username);
                map.put("Muser", username.toUpperCase().toString());
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("IvTransmitType", "LOAD");
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<History_SER> call = service.getHistoryData(url_link, basic, map);
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
                        History_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                        if (response_data_jsonArray.length() > 0) {
                            for (int i = 0; i < response_data_jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                                String date = jsonObject.optString("Zdate");
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
                                        } catch (ParseException e) {
                                            date_formateed = "";
                                        }
                                    }
                                } else {
                                    date_formateed = "";
                                }
                                String time = jsonObject.optString("Time");
                                String time_formatted = "";
                                if (time != null && !time.equals("")) {
                                    if (time.equals("000000")) {
                                        time_formatted = "";
                                    } else {
                                        DateFormat inputFormat = new SimpleDateFormat("HHmmss");
                                        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
                                        Date date1;
                                        try {
                                            date1 = inputFormat.parse(time);
                                            String outputDateStr = outputFormat.format(date1);
                                            time_formatted = outputDateStr;
                                        } catch (ParseException e) {
                                            time_formatted = "";
                                        }
                                    }
                                } else {
                                    time_formatted = "";
                                }
                                History_List_Object olo = new History_List_Object(jsonObject.optString("ZdoctypeText"), jsonObject.optString("ZactivityText"), date_formateed, time_formatted, jsonObject.optString("Zobjid"));
                                history_list.add(olo);
                            }
                            if (history_list.size() > 0) {
                                history_adapter = new History_Adapter(History_Activity.this, history_list);
                                history_list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(History_Activity.this);
                                history_list_recycleview.setLayoutManager(layoutManager);
                                history_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                history_list_recycleview.setAdapter(history_adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_layout.setVisibility(View.GONE);
                                search.setVisibility(View.VISIBLE);
                                swiperefreshlayout.setVisibility(View.VISIBLE);
                                title_textview.setText(getString(R.string.user_log) + " (" + history_list.size() + ")");
                                progressDialog.dismiss_progress_dialog();
                            } else {
                                title_textview.setText(getString(R.string.user_log) + " (0)");
                                no_data_layout.setVisibility(View.VISIBLE);
                                search.setVisibility(View.GONE);
                                swiperefreshlayout.setVisibility(View.GONE);
                                progressDialog.dismiss_progress_dialog();
                            }
                        } else {
                            title_textview.setText(getString(R.string.user_log) + " (0)");
                            no_data_layout.setVisibility(View.VISIBLE);
                            search.setVisibility(View.GONE);
                            swiperefreshlayout.setVisibility(View.GONE);
                            progressDialog.dismiss_progress_dialog();
                        }
                        /*Reading Data by using FOR Loop*/
                    } catch (Exception e) {
                    }
                }
            } else {
                title_textview.setText(getString(R.string.user_log) + " (0)");
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
                progressDialog.dismiss_progress_dialog();
            }
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<History_List_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < history_list.size(); i++) {
                String notif_id = history_list.get(i).getZobjid().toLowerCase();
                String short_text = history_list.get(i).getZactivityText().toLowerCase();
                String plant = history_list.get(i).getZdoctypeText().toLowerCase();
                if (notif_id.contains(query) || short_text.contains(query) || plant.contains(query)) {
                    History_List_Object blo = new History_List_Object(history_list.get(i).getZdoctypeText().toString(), history_list.get(i).getZactivityText().toString(), history_list.get(i).getZdate().toString(), history_list.get(i).getTime().toString(), history_list.get(i).getZobjid().toString());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(History_Activity.this);
                history_list_recycleview.setLayoutManager(layoutManager);
                history_adapter = new History_Adapter(History_Activity.this, filteredList);
                history_list_recycleview.setAdapter(history_adapter);
                history_adapter.notifyDataSetChanged();
                no_data_layout.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.VISIBLE);
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


    public class History_Adapter extends RecyclerView.Adapter<History_Adapter.MyViewHolder> {
        private Context mContext;
        private List<History_List_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView object_id_textview, doc_type_textview, activity_textview, date_textview;

            public MyViewHolder(View view) {
                super(view);
                object_id_textview = (TextView) view.findViewById(R.id.object_id_textview);
                doc_type_textview = (TextView) view.findViewById(R.id.doc_type_textview);
                activity_textview = (TextView) view.findViewById(R.id.activity_textview);
                date_textview = (TextView) view.findViewById(R.id.date_textview);
            }
        }

        public History_Adapter(Context mContext, List<History_List_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final History_List_Object olo = orders_list_data.get(position);
            holder.object_id_textview.setText(olo.getZobjid());
            holder.doc_type_textview.setText(olo.getZdoctypeText());
            holder.activity_textview.setText(olo.getZactivityText());
            holder.date_textview.setText(olo.getZdate() + "  " + olo.getTime());
        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }


    public class History_List_Object {
        private String ZdoctypeText;
        private String ZactivityText;
        private String Zdate;
        private String Time;
        private String Zobjid;

        public String getZdoctypeText() {
            return ZdoctypeText;
        }

        public void setZdoctypeText(String zdoctypeText) {
            ZdoctypeText = zdoctypeText;
        }

        public String getZactivityText() {
            return ZactivityText;
        }

        public void setZactivityText(String zactivityText) {
            ZactivityText = zactivityText;
        }

        public String getZdate() {
            return Zdate;
        }

        public void setZdate(String zdate) {
            Zdate = zdate;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getZobjid() {
            return Zobjid;
        }

        public void setZobjid(String zobjid) {
            Zobjid = zobjid;
        }

        public History_List_Object(String ZdoctypeText, String ZactivityText, String Zdate, String Time, String Zobjid) {
            this.ZdoctypeText = ZdoctypeText;
            this.ZactivityText = ZactivityText;
            this.Zdate = Zdate;
            this.Time = Time;
            this.Zobjid = Zobjid;
        }

    }


}
