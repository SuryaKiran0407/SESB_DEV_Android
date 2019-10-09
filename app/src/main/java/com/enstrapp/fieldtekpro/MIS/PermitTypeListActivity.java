package com.enstrapp.fieldtekpro.MIS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Enstrapp on 28/02/18.
 */

public class PermitTypeListActivity extends AppCompatActivity {

    TextView searchview_textview;
    public SearchView search;
    ProgressDialog progressdialog;
    String Iwerk_plant = "", Name_plant = "", plant = "", value = "", workcenter = "", application = "", header = "";
    Toolbar toolbar;
    private static int count = 0;
    ArrayList<String> wapinr_list_c = new ArrayList<String>();
    ArrayList<Mis_EtPermitWd_Object> EtPermitWd = new ArrayList<Mis_EtPermitWd_Object>();
    ArrayList<Mis_EtPermitWd_Object> EtPermitWd_f = new ArrayList<Mis_EtPermitWd_Object>();
    private static String DATABASE_NAME = "";
    private SQLiteDatabase FieldTekPro_db;
    RecyclerView listView;
    TextView header_tv, no_data;
    TextView no_data_textview, title, header1;
    PERMIT_WD_ADAPTER permit_wd_adapter;
    String name;
    ImageButton back_ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_permittype_list);

//        toolbar = findViewById(R.id.toolbar);
        header_tv = findViewById(R.id.header_tv);
        title = (TextView) findViewById(R.id.title);
        back_ib = findViewById(R.id.back_ib);
        no_data = findViewById(R.id.no_data_textview);
//        search_edittext = findViewById(R.id.search_edittext);
        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search);


       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
*/

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        /*Intent intent = getIntent();
        value = intent.getExtras().getString("value");
        Iwerk_plant = intent.getExtras().getString("Iwerk_plant");
        Name_plant = intent.getExtras().getString("Name_plant");
        workcenter = intent.getExtras().getString("workcenter");
        application = intent.getExtras().getString("application");
        plant = intent.getExtras().getString("wrk_name");
        header = value + " Permit";
        header_tv.setText(header);*/

        Intent intent = getIntent();
        wapinr_list_c = intent.getExtras().getStringArrayList("wapinr");
        name = intent.getExtras().getString("name");
        header_tv.setText(name);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        search.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        Typeface myCustomFont = Typeface.createFromAsset(PermitTypeListActivity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(15);
        getData();
        search.setOnQueryTextListener(listener);

        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {

        progressdialog = new ProgressDialog(PermitTypeListActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(getResources().getString(
                R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();

        EtPermitWd.clear();
        EtPermitWd_f.clear();

        if (listView != null) {
            listView.setAdapter(null);
        }

        if (wapinr_list_c.size() > 0) {
            Cursor cursor = null;
            try {
                cursor = FieldTekPro_db.rawQuery("select * from EtPermitWd", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Mis_EtPermitWd_Object wd = new Mis_EtPermitWd_Object();
                            wd.setAufnr(cursor.getString(10));
                            wd.setWapinr(cursor.getString(12));
                            wd.setWcnr(cursor.getString(13));
                            wd.setStxt(cursor.getString(17));
                            wd.setDatefr(cursor.getString(18));
                            wd.setTimefr(cursor.getString(19));
                            wd.setDateto(cursor.getString(20));
                            wd.setTimeto(cursor.getString(21));
                            wd.setPriok(cursor.getString(22));
                            wd.setTplnr(cursor.getString(23));
                            wd.setEqunr(cursor.getString(24));
                            wd.setEqktx(cursor.getString(25));
                            EtPermitWd.add(wd);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {

            } finally {
                cursor.close();
            }
        }

        /*if (EtPermitWd.size() > 0) {
            int c = 1;
            for (int i = 0; i < EtPermitWd.size(); i++) {
                String wapinr = EtPermitWd.get(i).getWapinr();
                for (int j = 0; j < wapinr_list_c.size(); j++) {
                    if (wapinr_list_c.get(j).equals(wapinr)) {
                        Mis_EtPermitWd_Object wd = new Mis_EtPermitWd_Object();
                        wd.setSNo(String.valueOf(c) + ") ");
                        wd.setAufnr(EtPermitWd.get(i).getAufnr());
                        wd.setWcnr(EtPermitWd.get(i).getWcnr());
                        wd.setStxt(EtPermitWd.get(i).getStxt());
                        wd.setDatefr(formatDate(EtPermitWd.get(i).getDatefr()));
                        wd.setTimefr(EtPermitWd.get(i).getTimefr());
                        wd.setDateto(formatDate(EtPermitWd.get(i).getDateto()));
                        wd.setTimeto(EtPermitWd.get(i).getTimeto());
                        wd.setPriok(EtPermitWd.get(i).getPriok());
                        wd.setTplnr(EtPermitWd.get(i).getTplnr());
                        wd.setEqunr(EtPermitWd.get(i).getEqunr());
                        wd.setEqktx(EtPermitWd.get(i).getEqktx());
                        EtPermitWd_f.add(wd);
                        c++;
                    }
                }
            }
        }*/

        if (wapinr_list_c.size() > 0) {
            int c = 1;
            for (int i = 0; i < wapinr_list_c.size(); i++) {
                if (EtPermitWd.size() > 0) {
                    for (int j = 0; j < EtPermitWd.size(); j++) {
                        if (EtPermitWd.get(j).getWapinr().equals(wapinr_list_c.get(i))) {
                            Mis_EtPermitWd_Object wd = new Mis_EtPermitWd_Object();
                            wd.setSNo(String.valueOf(c) + ") ");
                            wd.setAufnr(EtPermitWd.get(j).getAufnr());
                            wd.setWcnr(EtPermitWd.get(j).getWcnr());
                            wd.setStxt(EtPermitWd.get(j).getStxt());
                            wd.setDatefr(formatDate(EtPermitWd.get(j).getDatefr()));
                            wd.setTimefr(EtPermitWd.get(j).getTimefr());
                            wd.setDateto(formatDate(EtPermitWd.get(j).getDateto()));
                            wd.setTimeto(EtPermitWd.get(j).getTimeto());
                            wd.setPriok(EtPermitWd.get(j).getPriok());
                            wd.setTplnr(EtPermitWd.get(j).getTplnr());
                            wd.setEqunr(EtPermitWd.get(j).getEqunr());
                            wd.setEqktx(EtPermitWd.get(j).getEqktx());
                            EtPermitWd_f.add(wd);
                            c++;
                        }
                    }
                }
            }

        }

        if (EtPermitWd_f.size() > 0) {

            permit_wd_adapter = new PERMIT_WD_ADAPTER(PermitTypeListActivity.this, EtPermitWd_f);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.setItemAnimator(null);
            listView.setAdapter(permit_wd_adapter);
            search.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
       /* listView.setVisibility(View.VISIBLE);
        no_data.setVisibility(View.GONE);*/

        } else {
            listView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
            progressdialog.dismiss();
        }
    }

    public class PERMIT_WD_ADAPTER extends RecyclerView.Adapter<PERMIT_WD_ADAPTER.ViewHolder> {

        Context mContext;
        LayoutInflater inflater;
        private List<Mis_EtPermitWd_Object> mainDataList = null;
        private ArrayList<Mis_EtPermitWd_Object> arraylist;

        public PERMIT_WD_ADAPTER(Context context, List<Mis_EtPermitWd_Object> mainDataList) {
            mContext = context;
            this.mainDataList = mainDataList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<Mis_EtPermitWd_Object>();
            this.arraylist.addAll(mainDataList);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView wcnr_id;
            protected TextView name;
            protected TextView order;
            protected TextView f_loc;
            protected TextView date_from;
            protected TextView date_to;
            protected LinearLayout permit_wd_layout;
            protected TextView index;
            /*protected TextView valid_frm_date;
            protected TextView valid_from_time;
           protected TextView to_time;
            protected TextView priority;
           protected TextView eq_descr;
          protected TextView eq_txt;
            protected TextView to_date;//serial_no_textview
           protected TextView serial_no_textview;*/


            public ViewHolder(View view) {
                super(view);
                index = (TextView) view.findViewById(R.id.index);
                wcnr_id = (TextView) view.findViewById(R.id.wcnr_id);
                name = (TextView) view.findViewById(R.id.name);
                order = (TextView) view.findViewById(R.id.order);
                f_loc = (TextView) view.findViewById(R.id.function_location);
                date_from = (TextView) view.findViewById(R.id.date_from);
                date_to = (TextView) view.findViewById(R.id.date_to);
                permit_wd_layout = (LinearLayout) view.findViewById(R.id.permit_wd_list_layout);
                // valid_from_time = (TextView) view.findViewById(R.id.valid_frm_time);
                // to_time = (TextView) view.findViewById(R.id.to_time);
                //priority = (TextView) view.findViewById(R.id.priority);
            /*eq_descr = (ImageView) view.findViewById(R.id.notif_rep_detailedview);
            eq_txt = (TextView) view.findViewById(R.id.equip_desc);
            to_date = (ImageView) view.findViewById(R.id.notif_rep_detailedview);
            serial_no_textview = (ImageView) view.findViewById(R.id.notif_rep_detailedview);*/

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mis_permit_data_list, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.index.setText(mainDataList.get(position).getSNo());
            holder.wcnr_id.setText(mainDataList.get(position).getWcnr());
            holder.name.setText(mainDataList.get(position).getStxt());
            holder.order.setText(mainDataList.get(position).getAufnr());
            holder.f_loc.setText(mainDataList.get(position).getTplnr());
            holder.date_from.setText(mainDataList.get(position).getDatefr());
            holder.date_to.setText(mainDataList.get(position).getDateto());
           /* holder.valid_from_time.setText(mainDataList.get(position).getTimefr());
            holder.to_time.setText(mainDataList.get(position).getTimeto());
            holder.priority.setText(mainDataList.get(position).getPriok());
            holder.eq_descr.setText(mainDataList.get(position).getEqktx());
            holder.serial_no_textview.setText(mainDataList.get(position).getSNo());
            holder.eq_txt.setText(mainDataList.get(position).getEqunr());*/


            holder.permit_wd_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PermitTypeListActivity.this, PermitDetailActivity.class);
                    intent.putExtra("wcnr_id", mainDataList.get(position).getWcnr());
                    intent.putExtra("name", mainDataList.get(position).getStxt());
                    intent.putExtra("order", mainDataList.get(position).getAufnr());
                    intent.putExtra("f_loc", mainDataList.get(position).getTplnr());
                    intent.putExtra("date_from", mainDataList.get(position).getDatefr());
                    intent.putExtra("date_to", mainDataList.get(position).getDateto());
                    intent.putExtra("valid_from_time", mainDataList.get(position).getTimefr());
                    intent.putExtra("to_time", mainDataList.get(position).getTimeto());
                    intent.putExtra("priority", mainDataList.get(position).getPriok());
                    intent.putExtra("eq_descr", mainDataList.get(position).getEqktx());
                    intent.putExtra("eq_txt", mainDataList.get(position).getEqunr());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mainDataList.size();
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final ArrayList<Mis_EtPermitWd_Object> filteredList = new ArrayList<>();
            for (Mis_EtPermitWd_Object filter : EtPermitWd_f) {
                String Wcnr = filter.getWcnr().toString();
                String Stxt = filter.getStxt().toString();

                if (Wcnr.contains(query) || Stxt.contains(query)) {
                    filteredList.add(filter);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PermitTypeListActivity.this);
                listView.setLayoutManager(layoutManager);
                permit_wd_adapter = new PERMIT_WD_ADAPTER(PermitTypeListActivity.this, filteredList);
                listView.setAdapter(permit_wd_adapter);
                permit_wd_adapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);
            } else {
                no_data.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String formatDate(String date) {
        String datee = "";
        if (date.equals("0000-00-00") || date.equals("")) {

        } else {
            String inputPattern = "yyyyMMdd";
            String outputPattern = "MMM dd, yyyy";

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date dates = inputFormat.parse(date);
                datee = outputFormat.format(dates);
            } catch (ParseException e) {
                Log.v("permit_data_detail_date", "" + e.getMessage());
            }
            return datee;
        }
        return datee;
    }
}















