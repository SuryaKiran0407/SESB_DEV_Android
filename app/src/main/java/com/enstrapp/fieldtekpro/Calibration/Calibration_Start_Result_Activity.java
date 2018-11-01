package com.enstrapp.fieldtekpro.Calibration;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Calibration_Start_Result_Activity extends AppCompatActivity {

    String selected_Position = "", Werk = "", Auswmenge1 = "";
    public SearchView search;
    TextView searchview_textview, no_data_textview, title_textview;
    RecyclerView list_recycleview;
    ImageView back_imageview;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private List<Results_Object> results_list = new ArrayList<>();
    Results_Adapter results_adapter;
    LinearLayout no_data_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selected_Position = extras.getString("Position");
            Werk = extras.getString("Werk");
            Auswmenge1 = extras.getString("Auswmenge1");
        }

        search = (SearchView) findViewById(R.id.search);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        list_recycleview = (RecyclerView) findViewById(R.id.list_recycleview);
        title_textview = (TextView) findViewById(R.id.title_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        no_data_layout = (LinearLayout) findViewById(R.id.no_data_layout);

        DATABASE_NAME = this.getString(R.string.database_name);
        App_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);


        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calibration_Start_Result_Activity.this.finish();
            }
        });


        new Get_Results_Data().execute();
    }


    private class Get_Results_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Calibration_Start_Result_Activity.this, getResources().getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select * from EtInspCodes where Auswahlmge = ? and Werks = ?", new String[]{Auswmenge1, Werk});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Results_Object olo = new Results_Object(cursor.getString(6), cursor.getString(7), cursor.getString(8));
                            results_list.add(olo);
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
            if (results_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.result) + " (" + results_list.size() + ")");
                results_adapter = new Results_Adapter(Calibration_Start_Result_Activity.this, results_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_Start_Result_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(results_adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.result) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            progressDialog.dismiss_progress_dialog();
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<Results_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < results_list.size(); i++) {
                String id = results_list.get(i).getId().toLowerCase();
                String value = results_list.get(i).getText().toLowerCase();
                if (id.contains(query) || value.contains(query)) {
                    Results_Object nto = new Results_Object(results_list.get(i).getId().toString(), results_list.get(i).getText().toString(), results_list.get(i).getBewertung().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_Start_Result_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                results_adapter = new Results_Adapter(Calibration_Start_Result_Activity.this, filteredList);
                list_recycleview.setAdapter(results_adapter);
                results_adapter.notifyDataSetChanged();
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


    public class Results_Adapter extends RecyclerView.Adapter<Results_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Results_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, text_textview, textview1;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                text_textview = (TextView) view.findViewById(R.id.text_textview);
                textview1 = (TextView) view.findViewById(R.id.textview1);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
            }
        }

        public Results_Adapter(Context mContext, List<Results_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Results_Object olo = list_data.get(position);
            holder.id_textview.setText(olo.getId());
            holder.text_textview.setText(olo.getText());
            holder.textview1.setText(olo.getBewertung());

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("result_id", holder.id_textview.getText().toString());
                    intent.putExtra("result_text", holder.text_textview.getText().toString());
                    intent.putExtra("result_Bewertung", holder.textview1.getText().toString());
                    intent.putExtra("Position", selected_Position);
                    setResult(1, intent);
                    Calibration_Start_Result_Activity.this.finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }


    public class Results_Object {
        private String id;
        private String text;
        private String Bewertung;

        public String getBewertung() {
            return Bewertung;
        }

        public void setBewertung(String bewertung) {
            Bewertung = bewertung;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Results_Object(String id, String text, String Bewertung) {
            this.id = id;
            this.text = text;
            this.Bewertung = Bewertung;
        }
    }


}
