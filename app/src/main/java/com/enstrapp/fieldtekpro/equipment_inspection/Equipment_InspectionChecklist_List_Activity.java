package com.enstrapp.fieldtekpro.equipment_inspection;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Interface.REST_Interface;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login;
import com.enstrapp.fieldtekpro.login.Rest_Model_Login_Device;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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

public class Equipment_InspectionChecklist_List_Activity extends AppCompatActivity implements View.OnClickListener {

    String equipment_id = "";
    TextView title_textview, searchview_textview, no_data_textview;
    ImageView back_imageview;
    SearchView search;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    SwipeRefreshLayout swiperefreshlayout;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    RecyclerView list_recycleview;
    FloatingActionButton refresh_fab_button, start_insp_button;
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
    Response<Equipment_InspChk_SER> response;
    Response<Equipment_InspChk_SER_REST> response_REST;
    LinearLayout no_data_layout;
    Dialog decision_dialog;
    FloatingActionMenu floatingActionMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_inspectionchecklist_list_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            equipment_id = extras.getString("equipment_id");
        }


        title_textview = (TextView) findViewById(R.id.title_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        search = (SearchView) findViewById(R.id.search);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        list_recycleview = (RecyclerView) findViewById(R.id.history_list_recycleview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        start_insp_button = (FloatingActionButton) findViewById(R.id.start_insp_button);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);


        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(Equipment_InspectionChecklist_List_Activity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));


        DATABASE_NAME = Equipment_InspectionChecklist_List_Activity.this.getString(R.string.database_name);
        FieldTekPro_db = Equipment_InspectionChecklist_List_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


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
                floatingActionMenu.close(true);
                Refresh_Data();
            }
        });


        String auth_status = Authorizations.Get_Authorizations_Data(Equipment_InspectionChecklist_List_Activity.this, "I", "PS");
        if (auth_status.equalsIgnoreCase("true"))
        {
            start_insp_button.setVisibility(View.VISIBLE);
        }
        else
        {
            start_insp_button.setVisibility(View.GONE);
        }

        start_insp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(Equipment_InspectionChecklist_List_Activity.this, Equipment_StartInspection_Activity.class);
                intent.putExtra("equipment_id", equipment_id);
                startActivity(intent);
            }
        });



        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            String webservice_type = getString(R.string.webservice_type);
            if(webservice_type.equalsIgnoreCase("odata"))
            {
                new Get_History_Data().execute();
            }
            else
            {
                new Get_History_Data_REST().execute();
            }
        }
        else
        {
            network_connection_dialog.show_network_connection_dialog(Equipment_InspectionChecklist_List_Activity.this);
        }


        back_imageview.setOnClickListener(this);

    }


    @Override
    public void onResume()
    {
        super.onResume();
        /*cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            String webservice_type = getString(R.string.webservice_type);
            if(webservice_type.equalsIgnoreCase("odata"))
            {
                new Get_History_Data().execute();
            }
            else
            {
                new Get_History_Data_REST().execute();
            }
        }
        else
        {
            network_connection_dialog.show_network_connection_dialog(Equipment_InspectionChecklist_List_Activity.this);
        }*/
    }


    public void Refresh_Data() {
        cd = new ConnectionDetector(Equipment_InspectionChecklist_List_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(Equipment_InspectionChecklist_List_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Equipment_InspectionChecklist_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
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
                    String webservice_type = getString(R.string.webservice_type);
                    if(webservice_type.equalsIgnoreCase("odata"))
                    {
                        new Get_History_Data().execute();
                    }
                    else
                    {
                        new Get_History_Data_REST().execute();
                    }
                }
            });
        } else {
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(Equipment_InspectionChecklist_List_Activity.this);
        }
    }



    public class Get_History_Data extends AsyncTask<Void, Integer, Void> {
        String url_link = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_InspectionChecklist_List_Activity.this, getResources().getString(R.string.loading_insp_checklist));
            history_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                /* Initializing Shared Preferences */
                app_sharedpreferences = Equipment_InspectionChecklist_List_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username", null);
                password = app_sharedpreferences.getString("Password", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EI", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Equipment_InspectionChecklist_List_Activity.this.getString(R.string.ip_address);
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
                Call<Equipment_InspChk_SER> call = service.getINSPListData(url_link, basic, map);
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
                        Equipment_InspChk_SER rs = response.body();
                        /*Reading Response Data and Parsing to Serializable*/

                        /*Converting GSON Response to JSON Data for Parsing*/
                        String response_data = new Gson().toJson(rs.getD().getResults().get(0).getEtImrg().getResults());
                        /*Converting GSON Response to JSON Data for Parsing*/

                        /*Converting Response JSON Data to JSONArray for Reading*/
                        JSONArray response_data_jsonArray = new JSONArray(response_data);
                        /*Converting Response JSON Data to JSONArray for Reading*/

                        /*Reading Data by using FOR Loop*/
                        if (response_data_jsonArray.length() > 0) {
                            for (int i = 0; i < response_data_jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                                String date = jsonObject.optString("Idate");
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
                                History_List_Object olo = new History_List_Object(jsonObject.optString("Mdocm"), jsonObject.optString("Point"), jsonObject.optString("Pttxt"), date_formateed, jsonObject.optString("Readc"), jsonObject.optString("Desic"), jsonObject.optString("Msehl"), jsonObject.optString("Readr"), jsonObject.optString("Vlcod"), jsonObject.optString("Mdtxt"));
                                history_list.add(olo);
                            }
                            if (history_list.size() > 0) {
                                history_adapter = new History_Adapter(Equipment_InspectionChecklist_List_Activity.this, history_list);
                                list_recycleview.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_InspectionChecklist_List_Activity.this);
                                list_recycleview.setLayoutManager(layoutManager);
                                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                                list_recycleview.setAdapter(history_adapter);
                                search.setOnQueryTextListener(listener);
                                no_data_textview.setVisibility(View.GONE);
                                search.setVisibility(View.VISIBLE);
                                swiperefreshlayout.setVisibility(View.VISIBLE);
                                title_textview.setText(getString(R.string.insp_clist) + " : " + equipment_id + " (" + history_list.size() + ")");
                                custom_progress_dialog.dismiss_progress_dialog();
                                no_data_layout.setVisibility(View.GONE);
                            } else {
                                title_textview.setText(getString(R.string.insp_clist) + " : " + equipment_id + " (0)");
                                no_data_textview.setVisibility(View.VISIBLE);
                                search.setVisibility(View.GONE);
                                swiperefreshlayout.setVisibility(View.GONE);
                                custom_progress_dialog.dismiss_progress_dialog();
                                no_data_layout.setVisibility(View.VISIBLE);
                            }
                        } else {
                            title_textview.setText(getString(R.string.insp_clist) + " (0)");
                            no_data_textview.setVisibility(View.VISIBLE);
                            search.setVisibility(View.GONE);
                            swiperefreshlayout.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                            custom_progress_dialog.dismiss_progress_dialog();
                        }
                        /*Reading Data by using FOR Loop*/
                    } catch (Exception e) {
                    }
                }
            } else {
                title_textview.setText(getString(R.string.insp_clist) + " : " + equipment_id + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                custom_progress_dialog.dismiss_progress_dialog();
            }
        }
    }




    public class Get_History_Data_REST extends AsyncTask<Void, Integer, Void>
    {
        String url_link = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_InspectionChecklist_List_Activity.this, getResources().getString(R.string.loading_insp_checklist));
            history_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                /* Initializing Shared Preferences */
                app_sharedpreferences = Equipment_InspectionChecklist_List_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                app_editor = app_sharedpreferences.edit();
                username = app_sharedpreferences.getString("Username", null);
                password = app_sharedpreferences.getString("Password", null);
                String webservice_type = app_sharedpreferences.getString("webservice_type", null);
                /* Initializing Shared Preferences */
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Get_SYNC_MAP_DATA where Zdoctype = ? and Zactivity = ? and Endpoint = ?", new String[]{"EI", "RD", webservice_type});
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToNext();
                    url_link = cursor.getString(5);
                }
                String URL = Equipment_InspectionChecklist_List_Activity.this.getString(R.string.ip_address);

                Rest_Model_Login_Device modelLoginDeviceRest = new Rest_Model_Login_Device();
                modelLoginDeviceRest.setMUSER(username.toUpperCase().toString());
                modelLoginDeviceRest.setDEVICEID(device_id);
                modelLoginDeviceRest.setDEVICESNO(device_serial_number);
                modelLoginDeviceRest.setUDID(device_uuid);
                modelLoginDeviceRest.setiVTRANSMITTYPE("MISR");
                modelLoginDeviceRest.setiVCOMMIT("X");
                modelLoginDeviceRest.seteRROR("");

                Rest_Model_Login modelLoginRest = new Rest_Model_Login();
                modelLoginRest.setIv_transmit_type("LOAD");
                modelLoginRest.setIv_user(username);
                modelLoginRest.setIv_equnr(equipment_id);
                modelLoginRest.setIs_device(modelLoginDeviceRest);

                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(120000, TimeUnit.MILLISECONDS).writeTimeout(120000, TimeUnit.SECONDS).readTimeout(120000, TimeUnit.SECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).client(client).build();
                REST_Interface service = retrofit.create(REST_Interface.class);
                String credentials = username + ":" + password;
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<Equipment_InspChk_SER_REST> call = service.postINSPListData(url_link, basic, modelLoginRest);
                response_REST = call.execute();
                response_status_code = response_REST.code();
                if (response_status_code == 200)
                {
                    if (response_REST.isSuccessful() && response_REST.body() != null)
                    {
                        try
                        {
                            Equipment_InspChk_SER_REST rs = response_REST.body();
                            String response_data = new Gson().toJson(response_REST.body().getEtImrg());
                            JSONArray response_data_jsonArray = new JSONArray(response_data);
                            if (response_data_jsonArray.length() > 0)
                            {
                                for (int i = 0; i < response_data_jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = new JSONObject(response_data_jsonArray.getJSONObject(i).toString());
                                    String date = jsonObject.optString("IDATE");
                                    String date_formateed = "";
                                    if (date != null && !date.equals(""))
                                    {
                                        if (date.equals("00000000"))
                                        {
                                            date_formateed = "";
                                        }
                                        else
                                        {
                                            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                                            Date date1;
                                            try
                                            {
                                                date1 = inputFormat.parse(date);
                                                String outputDateStr = outputFormat.format(date1);
                                                date_formateed = outputDateStr;
                                            }
                                            catch (Exception e)
                                            {
                                                date_formateed = "";
                                            }
                                        }
                                    }
                                    else
                                    {
                                        date_formateed = "";
                                    }
                                    History_List_Object olo = new History_List_Object(jsonObject.optString("MDOCM"), jsonObject.optString("POINT"), jsonObject.optString("PTTXT"), date_formateed, jsonObject.optString("READC"), jsonObject.optString("DESIC"), jsonObject.optString("MSEHL"), jsonObject.optString("READR"), jsonObject.optString("VLCOD"), jsonObject.optString("MDTXT"));
                                    history_list.add(olo);
                                }
                            }
                            else
                            {
                            }
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (history_list.size() > 0)
            {
                history_adapter = new History_Adapter(Equipment_InspectionChecklist_List_Activity.this, history_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_InspectionChecklist_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(history_adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                swiperefreshlayout.setVisibility(View.VISIBLE);
                title_textview.setText("M. Readings"+ " : " + equipment_id + " (" + history_list.size() + ")");
                custom_progress_dialog.dismiss_progress_dialog();
                no_data_layout.setVisibility(View.GONE);
            }
            else
            {
                title_textview.setText("M. Readings" + " : " + equipment_id + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                swiperefreshlayout.setVisibility(View.GONE);
                custom_progress_dialog.dismiss_progress_dialog();
                no_data_layout.setVisibility(View.VISIBLE);
            }
        }
    }



    public class History_Adapter extends RecyclerView.Adapter<History_Adapter.MyViewHolder> {
        private Context mContext;
        private List<History_List_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView notes_textview, result_textview, person_textview, unit_textview, target_value_textview, reading_textview, date_textview, measdoc_textview, point_textview, desc_textview;

            public MyViewHolder(View view) {
                super(view);
                measdoc_textview = (TextView) view.findViewById(R.id.measdoc_textview);
                point_textview = (TextView) view.findViewById(R.id.point_textview);
                desc_textview = (TextView) view.findViewById(R.id.desc_textview);
                date_textview = (TextView) view.findViewById(R.id.date_textview);
                reading_textview = (TextView) view.findViewById(R.id.reading_textview);
                target_value_textview = (TextView) view.findViewById(R.id.target_value_textview);
                unit_textview = (TextView) view.findViewById(R.id.unit_textview);
                person_textview = (TextView) view.findViewById(R.id.person_textview);
                result_textview = (TextView) view.findViewById(R.id.result_textview);
                notes_textview = (TextView) view.findViewById(R.id.notes_textview);
            }
        }

        public History_Adapter(Context mContext, List<History_List_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipments_inspectionchecklist_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final History_List_Object olo = orders_list_data.get(position);
            holder.measdoc_textview.setText(olo.getMdocm());
            holder.point_textview.setText(olo.getPoint());
            holder.desc_textview.setText(olo.getPttxt());
            holder.date_textview.setText(olo.getDate_formateed());
            holder.reading_textview.setText(olo.getReadc());
            holder.target_value_textview.setText(olo.getDesic());
            holder.unit_textview.setText(olo.getMsehl());
            holder.person_textview.setText(olo.getReadr());
            holder.result_textview.setText(olo.getVlcod());
            holder.notes_textview.setText(olo.getNotes());
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
                String Notif_type = history_list.get(i).getMdocm().toLowerCase();
                String notif_no = history_list.get(i).getPoint().toLowerCase();
                String Aufnr = history_list.get(i).getPttxt().toLowerCase();
                if (Notif_type.contains(query) || notif_no.contains(query) || Aufnr.contains(query)) {
                    History_List_Object nto = new History_List_Object(history_list.get(i).getMdocm().toString(), history_list.get(i).getPoint().toString(), history_list.get(i).getPttxt().toString(), history_list.get(i).getDate_formateed().toString(), history_list.get(i).getReadc().toString(), history_list.get(i).getDesic().toString(), history_list.get(i).getMsehl().toString(), history_list.get(i).getReadr().toString(), history_list.get(i).getVlcod().toString(), history_list.get(i).getNotes().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_InspectionChecklist_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                history_adapter = new History_Adapter(Equipment_InspectionChecklist_List_Activity.this, filteredList);
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
        private String Mdocm;
        private String Point;
        private String Pttxt;
        private String date_formateed;
        private String Readc;
        private String Desic;
        private String Msehl;
        private String Readr;
        private String Vlcod;
        private String Notes;

        public String getVlcod() {
            return Vlcod;
        }

        public void setVlcod(String vlcod) {
            Vlcod = vlcod;
        }

        public String getNotes() {
            return Notes;
        }

        public void setNotes(String notes) {
            Notes = notes;
        }

        public String getReadr() {
            return Readr;
        }

        public void setReadr(String readr) {
            Readr = readr;
        }

        public String getMsehl() {
            return Msehl;
        }

        public void setMsehl(String msehl) {
            Msehl = msehl;
        }

        public String getDesic() {
            return Desic;
        }

        public void setDesic(String desic) {
            Desic = desic;
        }

        public String getReadc() {
            return Readc;
        }

        public void setReadc(String readc) {
            Readc = readc;
        }

        public String getDate_formateed() {
            return date_formateed;
        }

        public void setDate_formateed(String date_formateed) {
            this.date_formateed = date_formateed;
        }

        public String getPttxt() {
            return Pttxt;
        }

        public void setPttxt(String pttxt) {
            Pttxt = pttxt;
        }

        public String getPoint() {
            return Point;
        }

        public void setPoint(String point) {
            Point = point;
        }

        public String getMdocm() {
            return Mdocm;
        }

        public void setMdocm(String mdocm) {
            Mdocm = mdocm;
        }

        public History_List_Object(String Mdocm, String Point, String Pttxt, String date_formateed, String Readc, String Desic, String Msehl, String Readr, String Vlcod, String Notes) {
            this.Mdocm = Mdocm;
            this.Point = Point;
            this.Pttxt = Pttxt;
            this.date_formateed = date_formateed;
            this.Readc = Readc;
            this.Desic = Desic;
            this.Msehl = Msehl;
            this.Readr = Readr;
            this.Vlcod = Vlcod;
            this.Notes = Notes;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            Equipment_InspectionChecklist_List_Activity.this.finish();
        }
    }

}
