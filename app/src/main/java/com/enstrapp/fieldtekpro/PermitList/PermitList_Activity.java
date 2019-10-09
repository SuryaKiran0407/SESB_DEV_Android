package com.enstrapp.fieldtekpro.PermitList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.getOrderDetail.GetOrderDetail;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.orders.Get_Permit_Detail;
import com.enstrapp.fieldtekpro.orders.Orders_Change_Activity;
import com.enstrapp.fieldtekpro.orders.OrdrHeaderPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrPermitPrcbl;
import com.enstrapp.fieldtekpro.orders.Permits_Add_Update_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermitList_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    RecyclerView recyclerview;
    Button cancel_button, submit_button;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    List<PermitList_Object> permit_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    PERMITLIST_ADAPTER adapter;
    LinearLayout footer_layout;
    Error_Dialog error_dialog = new Error_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Dialog submit_decision_dialog;
    Success_Dialog success_dialog = new Success_Dialog();
    Set<HashMap<String, String>> abc = new HashSet<HashMap<String, String>>();
    String selected_orderstatus = "", selected_Iwerk = "", selected_orderID = "", selected_orderUUID = "",
            woco = "", permit_type = "", permit_no = "", username = "";
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    SearchView search;
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permit_isolation_list_activity);

        search = findViewById(R.id.search);
        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        footer_layout = (LinearLayout) findViewById(R.id.footer_layout);

        title_textview.setText(getString(R.string.permit_list));

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        /* Initializing Shared Preferences */
        app_sharedpreferences = PermitList_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        app_editor = app_sharedpreferences.edit();
        username = app_sharedpreferences.getString("Username", null);
        /* Initializing Shared Preferences */

        new Get_Permit_List_Data().execute();

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        String auth_PI_status = Authorizations.Get_Authorizations_Data(PermitList_Activity.this, "C", "PI");
        if (auth_PI_status.equalsIgnoreCase("true")) {
            footer_layout.setVisibility(View.VISIBLE);
        } else {
            footer_layout.setVisibility(View.GONE);
        }

    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<PermitList_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < permit_list.size(); i++) {
                String ordNo = permit_list.get(i).getOrder_num().toLowerCase();
                String permitNo = permit_list.get(i).getPermit_number().toLowerCase();
                String permitTxt = permit_list.get(i).getPermit_text().toLowerCase();
                String authGrp = permit_list.get(i).getAuth_grp().toLowerCase();
                if (ordNo.contains(query) || permitNo.contains(query) || permitTxt.contains(query) || authGrp.contains(query)) {
                    PermitList_Object nto = new PermitList_Object(permit_list.get(i).getOrder_num(), permit_list.get(i).getPmsog(),
                            permit_list.get(i).getGntxt(), permit_list.get(i).getGenvname(),
                            permit_list.get(i).getCrname(), permit_list.get(i).getObjnr(),
                            permit_list.get(i).getDate_time(), permit_list.get(i).getWerk(),
                            permit_list.get(i).getCounter(), permit_list.get(i).getHilvl(),
                            permit_list.get(i).isGeniakt_status(), permit_list.get(i).getPermit_number(),
                            permit_list.get(i).getFloc_number(), permit_list.get(i).getPermit_text(),
                            permit_list.get(i).getAuth_grp(), permit_list.get(i).getPermit_typ());
                    filteredList.add(nto);
                }
            }

            if (filteredList.size() > 0) {
                adapter = new PERMITLIST_ADAPTER(PermitList_Activity.this, filteredList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PermitList_Activity.this);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
                footer_layout.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
                footer_layout.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    private class Get_Permit_List_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(PermitList_Activity.this, getResources().getString(R.string.loading));
            permit_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String Usgrp = "";
                Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_USER_DATA", null);
                if (cursor1 != null && cursor1.getCount() > 0) {
                    if (cursor1.moveToFirst()) {
                        do {
                            Usgrp = cursor1.getString(15);
                        }
                        while (cursor1.moveToNext());
                    }
                } else {
                    cursor1.close();
                }

                if (Usgrp != null && !Usgrp.equals("")) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.clear();
                    Cursor cursor2 = FieldTekPro_db.rawQuery("select * from EtUsgrpWccp where Usgrp = ?", new String[]{Usgrp});
                    if (cursor2 != null && cursor2.getCount() > 0) {
                        if (cursor2.moveToFirst()) {
                            do {
                                String Pmsog = cursor2.getString(2);
                                list.add(Pmsog);
                            }
                            while (cursor2.moveToNext());
                        }
                    } else {
                        cursor2.close();
                    }

                    Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmWcagns where Objart = ? and Geniakt = ?", new String[]{"WA", "X"});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                HashMap<String, String> hashmap = new HashMap<String, String>();
                                hashmap.put("uuid", cursor.getString(1));
                                hashmap.put("Aufnr", cursor.getString(2));
                                abc.add(hashmap);
                                String EtWcmWcagns_Pmsog = cursor.getString(7);
                                if (list.contains(EtWcmWcagns_Pmsog)) {
                                    String date = cursor.getString(26);
                                    String date_format = "";
                                    if (date != null && !date.equals("")) {
                                        if (date.equalsIgnoreCase("00000000")) {
                                            date_format = "";
                                        } else {
                                            String Date_format = "";
                                            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            Date date1;
                                            try {
                                                date1 = inputFormat.parse(date);
                                                String outputDateStr = outputFormat.format(date1);
                                                Date_format = outputDateStr;
                                            } catch (Exception e) {
                                                Date_format = "";
                                            }
                                            String time = cursor.getString(27);
                                            String time_formatted = "";
                                            if (time != null && !time.equals("")) {
                                                DateFormat inputFormat1 = new SimpleDateFormat("HHmmss");
                                                DateFormat outputFormat1 = new SimpleDateFormat("HH:mm:ss");
                                                Date date2;
                                                try {
                                                    date2 = inputFormat1.parse(date);
                                                    String outputDateStr = outputFormat1.format(date2);
                                                    time_formatted = outputDateStr;
                                                } catch (Exception e) {
                                                    time_formatted = "";
                                                }
                                            }
                                            date_format = Date_format + "  " + time_formatted;
                                        }
                                    } else {
                                        date_format = "";
                                    }
                                    boolean Geniakt_status = false;
                                    String Geniakt = cursor.getString(9);
                                    if (Geniakt.equalsIgnoreCase("X")) {
                                        Geniakt_status = false;
                                    } else {
                                        Geniakt_status = true;
                                    }
                                    String permit_number = "";
                                    String permit_text = "";
                                    String auth_grp = "";
                                    String permit_type = "";
                                    String objnr = cursor.getString(3);
                                    if (objnr != null && !objnr.equals("")) {
                                        try {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWaData where Objnr = ?", new String[]{objnr});
                                            if (cursor3 != null && cursor3.getCount() > 0) {
                                                if (cursor3.moveToFirst()) {
                                                    do {
                                                        permit_number = cursor3.getString(4);
                                                        permit_text = cursor3.getString(15);
                                                        permit_type = cursor3.getString(6);
                                                        auth_grp = cursor3.getString(31) + " - " + cursor3.getString(32);
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            } else {
                                                cursor3.close();
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                    String floc_number = "";
                                    String aufnr = cursor.getString(2);
                                    if (aufnr != null && !aufnr.equals("")) {
                                        try {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{aufnr});
                                            if (cursor3 != null && cursor3.getCount() > 0) {
                                                if (cursor3.moveToFirst()) {
                                                    do {
                                                        floc_number = cursor3.getString(10);
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            } else {
                                                cursor3.close();
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                    String type = cursor.getString(7);
                                    if (type.equalsIgnoreCase("I_TAG") || type.equalsIgnoreCase("I_TAGGED")) {
                                    } else {
                                        PermitList_Object nto = new PermitList_Object(cursor.getString(2), cursor.getString(7), cursor.getString(8), cursor.getString(10), cursor.getString(13), cursor.getString(3), date_format, cursor.getString(12), cursor.getString(4), cursor.getString(14), Geniakt_status, permit_number, floc_number, permit_text, auth_grp, permit_type);
                                        permit_list.add(nto);
                                    }
                                } else {
                                }
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                        cursor.close();
                    }

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
            if (permit_list.size() > 0) {
                adapter = new PERMITLIST_ADAPTER(PermitList_Activity.this, permit_list);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PermitList_Activity.this);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
                footer_layout.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
                footer_layout.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class PermitList_Object {
        private String Order_num;
        private String Pmsog;
        private String Gntxt;
        private String Genvname;
        private String Crname;
        private String Objnr;
        private String date_time;
        private String werk;
        private String counter;
        private String Hilvl;
        private boolean Geniakt_status;
        private String permit_number;
        private String floc_number;
        private String permit_text;
        private String auth_grp;
        private String permit_typ;

        public String getPermit_typ() {
            return permit_typ;
        }

        public void setPermit_typ(String permit_typ) {
            this.permit_typ = permit_typ;
        }

        public String getPermit_text() {
            return permit_text;
        }

        public void setPermit_text(String permit_text) {
            this.permit_text = permit_text;
        }

        public String getAuth_grp() {
            return auth_grp;
        }

        public void setAuth_grp(String auth_grp) {
            this.auth_grp = auth_grp;
        }

        public String getFloc_number() {
            return floc_number;
        }

        public void setFloc_number(String floc_number) {
            this.floc_number = floc_number;
        }

        public String getPermit_number() {
            return permit_number;
        }

        public void setPermit_number(String permit_number) {
            this.permit_number = permit_number;
        }

        public boolean isGeniakt_status() {
            return Geniakt_status;
        }

        public void setGeniakt_status(boolean geniakt_status) {
            Geniakt_status = geniakt_status;
        }

        public String getHilvl() {
            return Hilvl;
        }

        public void setHilvl(String hilvl) {
            Hilvl = hilvl;
        }

        public String getCounter() {
            return counter;
        }

        public void setCounter(String counter) {
            this.counter = counter;
        }

        public String getWerk() {
            return werk;
        }

        public void setWerk(String werk) {
            this.werk = werk;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getObjnr() {
            return Objnr;
        }

        public void setObjnr(String objnr) {
            Objnr = objnr;
        }

        public String getCrname() {
            return Crname;
        }

        public void setCrname(String crname) {
            Crname = crname;
        }

        public String getGenvname() {
            return Genvname;
        }

        public void setGenvname(String genvname) {
            Genvname = genvname;
        }

        public String getGntxt() {
            return Gntxt;
        }

        public void setGntxt(String gntxt) {
            Gntxt = gntxt;
        }

        public String getOrder_num() {
            return Order_num;
        }

        public void setOrder_num(String order_num) {
            Order_num = order_num;
        }

        public String getPmsog() {
            return Pmsog;
        }

        public void setPmsog(String pmsog) {
            Pmsog = pmsog;
        }

        public PermitList_Object(String Order_num, String Pmsog, String Gntxt, String Genvname, String Crname, String Objnr, String date_time, String werk, String counter, String Hilvl, boolean Geniakt_status, String permit_number, String floc_number, String permit_text, String auth_grp, String permit_typ) {
            this.Order_num = Order_num;
            this.Pmsog = Pmsog;
            this.Gntxt = Gntxt;
            this.Genvname = Genvname;
            this.Crname = Crname;
            this.Objnr = Objnr;
            this.date_time = date_time;
            this.werk = werk;
            this.counter = counter;
            this.Hilvl = Hilvl;
            this.Geniakt_status = Geniakt_status;
            this.permit_number = permit_number;
            this.floc_number = floc_number;
            this.permit_text = permit_text;
            this.auth_grp = auth_grp;
            this.permit_typ = permit_typ;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            PermitList_Activity.this.finish();
        } else if (v == cancel_button) {
            PermitList_Activity.this.finish();
        } else if (v == submit_button) {
            cd = new ConnectionDetector(PermitList_Activity.this);
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                submit_decision_dialog = new Dialog(PermitList_Activity.this);
                submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                submit_decision_dialog.setCancelable(false);
                submit_decision_dialog.setCanceledOnTouchOutside(false);
                submit_decision_dialog.setContentView(R.layout.decision_dialog);
                ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
                Glide.with(PermitList_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
                TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
                description_textview.setText(getString(R.string.permit_submit));
                Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
                Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
                submit_decision_dialog.show();
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit_decision_dialog.dismiss();
                        new Get_Token().execute();
                    }
                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit_decision_dialog.dismiss();
                    }
                });
            } else {
                network_connection_dialog.show_network_connection_dialog(PermitList_Activity.this);
            }
        }
    }

    public class PERMITLIST_ADAPTER extends RecyclerView.Adapter<PERMITLIST_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<PermitList_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView auth_grp_textview, floc_id_textview, permit_no_textview, date_time_textview, Objnr, order_num_textview, permit_textview, permit_text_textview, Geniakt, Crname, permit_typ;
            public EditText person_name_edittext;
            public CheckBox permit_issued_checkbox;

            public MyViewHolder(final View view) {
                super(view);
                permit_typ = (TextView) view.findViewById(R.id.permit_typ);
                order_num_textview = (TextView) view.findViewById(R.id.order_num_textview);
                permit_textview = (TextView) view.findViewById(R.id.permit_textview);
                permit_text_textview = (TextView) view.findViewById(R.id.permit_text_textview);
                person_name_edittext = (EditText) view.findViewById(R.id.person_name_edittext);
                Geniakt = (TextView) view.findViewById(R.id.Geniakt);
                permit_issued_checkbox = (CheckBox) view.findViewById(R.id.permit_issued_checkbox);
                Crname = (TextView) view.findViewById(R.id.Crname);
                Objnr = (TextView) view.findViewById(R.id.Objnr);
                date_time_textview = (TextView) view.findViewById(R.id.date_time_textview);
                permit_no_textview = (TextView) view.findViewById(R.id.permit_no_textview);
                floc_id_textview = (TextView) view.findViewById(R.id.floc_id_textview);
                auth_grp_textview = (TextView) view.findViewById(R.id.auth_grp_textview);
                permit_issued_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (permit_issued_checkbox.isChecked()) {
                            int position = (Integer) v.getTag();
                            permit_list.get(position).setGenvname(username);
                            permit_list.get(position).setGeniakt_status(true);
                            permit_issued_checkbox.setChecked(true);
                            Geniakt.setText("");
                            person_name_edittext.setText(username);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                            String currentDateandTime = sdf.format(new Date());
                            date_time_textview.setText(currentDateandTime);
                            permit_list.get(position).setDate_time(currentDateandTime);
                        } else {
                            int position = (Integer) v.getTag();
                            permit_list.get(position).setGenvname("");
                            permit_list.get(position).setGeniakt_status(false);
                            permit_issued_checkbox.setChecked(false);
                            Geniakt.setText("X");
                            person_name_edittext.setText("");
                            date_time_textview.setText("");
                            permit_list.get(position).setDate_time("");
                        }
                    }
                });
                person_name_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int pos = (int) person_name_edittext.getTag();
                        permit_list.get(pos).setGenvname(person_name_edittext.getText().toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }

        public PERMITLIST_ADAPTER(Context mContext, List<PermitList_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.permitlist_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final PermitList_Object nto = type_details_list.get(position);
            SpannableString content = new SpannableString(nto.getOrder_num());
            content.setSpan(new UnderlineSpan(), 0, nto.getOrder_num().length(), 0);
            SpannableString permit_content = new SpannableString(nto.getPermit_number());
            permit_content.setSpan(new UnderlineSpan(), 0, nto.getPermit_number().length(), 0);
            holder.order_num_textview.setText(content);
            holder.permit_textview.setText(nto.getPmsog());
            holder.permit_text_textview.setText(nto.getPermit_text());
            holder.person_name_edittext.setTag(position);
            holder.person_name_edittext.setText(nto.getGenvname());
            holder.Crname.setText(nto.getCrname());
            holder.Objnr.setText(nto.getObjnr());
            holder.date_time_textview.setText(nto.getDate_time());
            holder.permit_no_textview.setText(permit_content);
            holder.floc_id_textview.setText(nto.getFloc_number());
            holder.auth_grp_textview.setText(nto.getAuth_grp());
            holder.permit_issued_checkbox.setTag(position);
            holder.permit_typ.setText(nto.getPermit_typ());
            holder.permit_issued_checkbox.setChecked((type_details_list.get(position).isGeniakt_status() == true ? true : false));
            holder.order_num_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        selected_orderID = holder.order_num_textview.getText().toString();
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{selected_orderID});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals("")) {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    } else {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (selected_orderUUID != null && !selected_orderUUID.equals("")) {
                        new Get_Order_Data().execute();
                    } else {
                        error_dialog.show_error_dialog(PermitList_Activity.this,
                                getString(R.string.no_ordr, selected_orderID));
                    }
                }
            });

            holder.permit_no_textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        selected_orderID = holder.order_num_textview.getText().toString();
                        permit_no = holder.permit_no_textview.getText().toString();
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{selected_orderID});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals("")) {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    } else {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (workComplt(selected_orderID))
                        woco = "X";
                    else
                        woco = "";
                    permit_type = holder.permit_typ.getText().toString();
                    new Get_Permit_Data().execute();
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }


    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(PermitList_Activity.this,
                    getString(R.string.submit_permitlist));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(PermitList_Activity.this);
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
            if (token_status.equalsIgnoreCase("success")) {
                new Post_Permit_List_Data().execute("");
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(PermitList_Activity.this,
                        getString(R.string.unable_permitsub));
            }
        }
    }

    private class Post_Permit_List_Data extends AsyncTask<String, Integer, Void> {
        String response_status = "";
        ArrayList<Model_Permit_Isolation_ItWcmWcagn> ArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                SimpleDateFormat sdf_date = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdf_time = new SimpleDateFormat("HHmmss");
                String currentDate = sdf_date.format(new Date());
                String currentTime = sdf_time.format(new Date());
                for (int i = 0; i < permit_list.size(); i++) {
                    Model_Permit_Isolation_ItWcmWcagn model_itWcmWcagn = new Model_Permit_Isolation_ItWcmWcagn();
                    model_itWcmWcagn.setAufnr(permit_list.get(i).getOrder_num());
                    model_itWcmWcagn.setObjnr(permit_list.get(i).getObjnr());
                    model_itWcmWcagn.setCounter(permit_list.get(i).getCounter());
                    model_itWcmWcagn.setWerks(permit_list.get(i).getWerk());
                    model_itWcmWcagn.setCrname(permit_list.get(i).getCrname());
                    model_itWcmWcagn.setObjart("WA");
                    model_itWcmWcagn.setObjtyp("");
                    model_itWcmWcagn.setPmsog(permit_list.get(i).getPmsog());
                    model_itWcmWcagn.setGntxt("");
                    boolean Geniakt_status = permit_list.get(i).isGeniakt_status();
                    if (Geniakt_status == true) {
                        model_itWcmWcagn.setGeniakt("");
                    } else {
                        model_itWcmWcagn.setGeniakt("X");
                    }
                    model_itWcmWcagn.setGenvname(permit_list.get(i).getGenvname());
                    model_itWcmWcagn.setGendatum(currentDate);
                    model_itWcmWcagn.setGentime(currentTime);
                    String Hilvl = permit_list.get(i).getHilvl();
                    if (Hilvl != null && !Hilvl.equals("")) {
                        int int_Hilvl = Integer.parseInt(Hilvl);
                        model_itWcmWcagn.setHilvl(int_Hilvl);
                    }
                    model_itWcmWcagn.setAction("I");
                    ArrayList.add(model_itWcmWcagn);
                }
                response_status = Permit_Isolation.Post_Data(PermitList_Activity.this, ArrayList, abc);
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
            if (response_status.startsWith("S")) {
                custom_progress_dialog.dismiss_progress_dialog();
                new Get_Permit_List_Data().execute();
                success_dialog.show_success_dialog(PermitList_Activity.this, response_status.substring(1));
            } else if (response_status.startsWith("E")) {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(PermitList_Activity.this, response_status.substring(1));
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(PermitList_Activity.this,
                        getString(R.string.unable_permitsub));
            }
        }
    }

    private class Get_Order_Data extends AsyncTask<Void, Integer, Void> {
        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(PermitList_Activity.this, getResources().getString(R.string.get_order_data));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ohp = GetOrderDetail.GetData(PermitList_Activity.this, selected_orderUUID,
                    selected_orderID, selected_orderstatus, selected_Iwerk);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (ohp != null) {
                Intent ordrIntent = new Intent(PermitList_Activity.this, Orders_Change_Activity.class);
                ordrIntent.putExtra("order", "U");
                ordrIntent.putExtra("ordr_parcel", ohp);
                startActivity(ordrIntent);
            }
        }
    }

    private class Get_Permit_Data extends AsyncTask<Void, Integer, Void> {
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(PermitList_Activity.this, getResources().getString(R.string.get_order_data));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ww_al = Get_Permit_Detail.GetData(PermitList_Activity.this, selected_orderUUID,
                    selected_orderID, selected_orderstatus, selected_Iwerk);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (ww_al != null) {
                Intent ordrIntent = new Intent(PermitList_Activity.this, Permits_Add_Update_Activity.class);
                ordrIntent.putExtra("order", selected_orderID);
                ordrIntent.putExtra("opp", ww_al);
                ordrIntent.putExtra("woco", woco);
                ordrIntent.putExtra("func_loc", funcId(selected_orderID));
                ordrIntent.putExtra("equip", equipId(selected_orderID));
                ordrIntent.putExtra("equip_txt", equipName(selected_orderID));
                ordrIntent.putExtra("iwerk", selected_Iwerk);
                ordrIntent.putExtra("wapinr", permit_no);
                ordrIntent.putExtra("flag", "CH");
                startActivity(ordrIntent);
            }
        }
    }


    private String getIwerk(String equip) {
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
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
            cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Tplnr = ?", new String[]{func});
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

    private boolean workComplt(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Txt04 = ?", new String[]{OrderNo, "WOCO"});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        if (cursor1.getString(11).equalsIgnoreCase("X"))
                            return true;
                        else
                            return false;
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return false;
    }

    private String funcId(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(11);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }

    private String equipId(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(9);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }

    private String equipName(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(32);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }

    private String getPermitName(String PermitType) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from EtWcmTypes where Iwerk = ? and Objtyp = ?", new String[]{selected_Iwerk, PermitType});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(4);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }
}
