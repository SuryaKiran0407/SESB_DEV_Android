package com.enstrapp.fieldtekpro.equipment_inspection;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Equipment_Inspection_MeasCodes_Types_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<Notification_Type_Object> notification_type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    NOTIFICATION_TYPE_ADAPTER adapter;
    LinearLayout no_data_layout;
    int request_id = 0;
    String codgr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
            codgr = extras.getString("codgr");
        }

        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        search = (SearchView) findViewById(R.id.search);
        list_recycleview = (RecyclerView) findViewById(R.id.list_recycleview);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);

        back_imageview.setOnClickListener(this);

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

        new Get_Notification_Types().execute();
    }

    private class Get_Notification_Types extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_Inspection_MeasCodes_Types_Activity.this, getResources().getString(R.string.loading));
            notification_type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtMeasCodes where Codegruppe = ?", new String[]{codgr});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Notification_Type_Object nto = new Notification_Type_Object(cursor.getString(3), cursor.getString(4));
                            notification_type_list.add(nto);
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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (notification_type_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.valuation) + " (" + notification_type_list.size() + ")");
                adapter = new NOTIFICATION_TYPE_ADAPTER(Equipment_Inspection_MeasCodes_Types_Activity.this, notification_type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_Inspection_MeasCodes_Types_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.valuation) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<Notification_Type_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < notification_type_list.size(); i++) {
                String id = notification_type_list.get(i).getNotification_id().toLowerCase();
                String value = notification_type_list.get(i).getNotification_text().toLowerCase();
                if (id.contains(query) || value.contains(query)) {
                    Notification_Type_Object nto = new Notification_Type_Object(notification_type_list.get(i).getNotification_id().toString(), notification_type_list.get(i).getNotification_text().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_Inspection_MeasCodes_Types_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new NOTIFICATION_TYPE_ADAPTER(Equipment_Inspection_MeasCodes_Types_Activity.this, filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    public class NOTIFICATION_TYPE_ADAPTER extends RecyclerView.Adapter<NOTIFICATION_TYPE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Notification_Type_Object> notification_type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, value_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
            }
        }

        public NOTIFICATION_TYPE_ADAPTER(Context mContext, List<Notification_Type_Object> list) {
            this.mContext = mContext;
            this.notification_type_details_list = list;
        }

        @Override
        public NOTIFICATION_TYPE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new NOTIFICATION_TYPE_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final NOTIFICATION_TYPE_ADAPTER.MyViewHolder holder, int position) {
            final Notification_Type_Object nto = notification_type_details_list.get(position);
            holder.id_textview.setText(nto.getNotification_id());
            holder.value_textview.setText(nto.getNotification_text());

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("valuation_type_id", holder.id_textview.getText().toString());
                    intent.putExtra("valuation_type_text", holder.value_textview.getText().toString());
                    setResult(request_id, intent);
                    Equipment_Inspection_MeasCodes_Types_Activity.this.finish();//finishing activity
                }
            });

        }

        @Override
        public int getItemCount() {
            return notification_type_details_list.size();
        }
    }

    public class Notification_Type_Object {
        private String Notification_id;
        private String Notification_text;

        public Notification_Type_Object() {
        }

        public Notification_Type_Object(String Notification_id, String Notification_text) {
            this.Notification_id = Notification_id;
            this.Notification_text = Notification_text;
        }

        public String getNotification_id() {
            return Notification_id;
        }

        public void setNotification_id(String Notification_id) {
            this.Notification_id = Notification_id;
        }

        public String getNotification_text() {
            return Notification_text;
        }

        public void setNotification_text(String Notification_text) {
            this.Notification_text = Notification_text;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            onBackPressed();
        }
    }

}
