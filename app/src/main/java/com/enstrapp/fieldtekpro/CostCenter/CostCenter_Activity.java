package com.enstrapp.fieldtekpro.CostCenter;

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

public class CostCenter_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<CostCenter_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    TYPE_ADAPTER adapter;
    LinearLayout no_data_layout;
    int request_id = 0;
    String plant_id = "", bukrs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            plant_id = extras.getString("werk");
            bukrs = extras.getString("bukrs");
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
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

        new Get_Types().execute();
    }


    private class Get_Types extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(CostCenter_Activity.this, getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_LIST_COST_CENTER where Werks = ? and Bukrs = ?", new String[]{plant_id, bukrs});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            CostCenter_Object nto = new CostCenter_Object(cursor.getString(2), cursor.getString(3));
                            type_list.add(nto);
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
            if (type_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.cost_center) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(CostCenter_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CostCenter_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                title_textview.setText(getResources().getString(R.string.cost_center) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<CostCenter_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < type_list.size(); i++) {
                String id = type_list.get(i).getCost_id().toLowerCase();
                String value = type_list.get(i).getCost_text().toLowerCase();
                if (id.contains(query) || value.contains(query)) {
                    CostCenter_Object nto = new CostCenter_Object(type_list.get(i).getCost_id().toString(), type_list.get(i).getCost_text().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CostCenter_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(CostCenter_Activity.this, filteredList);
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


    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<CostCenter_Object> notification_type_details_list;

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

        public TYPE_ADAPTER(Context mContext, List<CostCenter_Object> list) {
            this.mContext = mContext;
            this.notification_type_details_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final CostCenter_Object nto = notification_type_details_list.get(position);
            holder.id_textview.setText(nto.getCost_id());
            holder.value_textview.setText(nto.getCost_text());

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("kostl_id", holder.id_textview.getText().toString());
                    intent.putExtra("kostl_text", holder.value_textview.getText().toString());
                    setResult(RESULT_OK, intent);
                    CostCenter_Activity.this.finish();//finishing activity
                }
            });

        }

        @Override
        public int getItemCount() {
            return notification_type_details_list.size();
        }
    }


    public class CostCenter_Object {
        private String cost_id;
        private String cost_text;

        public CostCenter_Object() {
        }

        public CostCenter_Object(String cost_id, String cost_text) {
            this.cost_id = cost_id;
            this.cost_text = cost_text;
        }

        public String getCost_id() {
            return cost_id;
        }

        public void setCost_id(String cost_id) {
            this.cost_id = cost_id;
        }

        public String getCost_text() {
            return cost_text;
        }

        public void setCost_text(String cost_text) {
            this.cost_text = cost_text;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            onBackPressed();
        }
    }


}
