package com.enstrapp.fieldtekpro.orders;

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

public class JSA_LocationStructure_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    TYPE_ADAPTER adapter;
    LinearLayout no_data_layout;
    int request_id = 0;

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

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text",
                null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/metropolis_medium.ttf");
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
            custom_progress_dialog.show_progress_dialog(JSA_LocationStructure_Activity.this,
                    getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEHSLocRev where " +
                        "ParRootRefKey = ?", new String[]{""});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String loc_text = "";
                            String loc_type = cursor.getString(1);
                            if (loc_type.endsWith("CP")) {
                                loc_text = "Company";
                            } else if (loc_type.endsWith("DV")) {
                                loc_text = "Division";
                            } else if (loc_type.endsWith("EQ")) {
                                loc_text = "Equipment";
                            } else if (loc_type.endsWith("FL")) {
                                loc_text = "Functional Location";
                            } else if (loc_type.endsWith("LO")) {
                                loc_text = "Location";
                            } else if (loc_type.endsWith("OF")) {
                                loc_text = "Office";
                            } else if (loc_type.endsWith("PL")) {
                                loc_text = "Plant";
                            } else if (loc_type.endsWith("PR")) {
                                loc_text = "Projects";
                            } else if (loc_type.endsWith("ST")) {
                                loc_text = "Site";
                            }
                            String status_text = "";
                            String status_type = cursor.getString(2);
                            if (status_type.equalsIgnoreCase("01")) {
                                status_text = "New";
                            } else if (status_type.equalsIgnoreCase("02")) {
                                status_text = "Active";
                            }
                            Type_Object nto = new Type_Object(cursor.getString(1),
                                    loc_text, status_type, status_text,
                                    cursor.getString(3), cursor.getString(5),
                                    cursor.getString(8), cursor.getString(7));
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
                title_textview.setText(getResources().getString(R.string.loc_struct) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(JSA_LocationStructure_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_LocationStructure_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                title_textview.setText(getResources().getString(R.string.loc_struct) + " (0)");
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
            final List<Type_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < type_list.size(); i++) {
                String Loc_text = type_list.get(i).getLoc_text().toLowerCase();
                String Text = type_list.get(i).getText().toLowerCase();
                String FuncLocID = type_list.get(i).getFuncLocID().toLowerCase();
                String PlantID = type_list.get(i).getPlantID().toLowerCase();
                if (Loc_text.contains(query) || Text.contains(query) || FuncLocID.contains(query)
                        || PlantID.contains(query)) {
                    Type_Object nto = new Type_Object(type_list.get(i).getLoc_type().toString(),
                            type_list.get(i).getLoc_text().toString(),
                            type_list.get(i).getStatus_type().toString(),
                            type_list.get(i).getStatus_text().toString(),
                            type_list.get(i).getFuncLocID().toString(),
                            type_list.get(i).getPlantID().toString(),
                            type_list.get(i).getText().toString(),
                            type_list.get(i).getParent_key().toString()
                    );
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_LocationStructure_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(JSA_LocationStructure_Activity.this,
                        filteredList);
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
        private List<Type_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name_textview, loc_type_textview, status_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                name_textview = (TextView) view.findViewById(R.id.name_textview);
                loc_type_textview = (TextView) view.findViewById(R.id.loc_type_textview);
                status_textview = (TextView) view.findViewById(R.id.status_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
            }
        }

        public TYPE_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public TYPE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jsa_locationstructure_list_data, parent, false);
            return new TYPE_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TYPE_ADAPTER.MyViewHolder holder, int position) {
            final Type_Object nto = type_details_list.get(position);
            holder.name_textview.setText(nto.getText());
            holder.loc_type_textview.setText(nto.getLoc_text());
            holder.status_textview.setText(nto.getStatus_text());

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("loc_type_id", nto.getParent_key());
                    intent.putExtra("loc_type_text", nto.getText());
                    setResult(request_id, intent);
                    JSA_LocationStructure_Activity.this.finish();//finishing activity
                }
            });

        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public class Type_Object {
        private String loc_type;
        private String loc_text;
        private String status_type;
        private String status_text;
        private String FuncLocID;
        private String PlantID;
        private String text;
        private String parent_key;

        public String getParent_key() {
            return parent_key;
        }

        public void setParent_key(String parent_key) {
            this.parent_key = parent_key;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPlantID() {
            return PlantID;
        }

        public void setPlantID(String plantID) {
            PlantID = plantID;
        }

        public String getFuncLocID() {
            return FuncLocID;
        }

        public void setFuncLocID(String funcLocID) {
            FuncLocID = funcLocID;
        }

        public String getLoc_type() {
            return loc_type;
        }

        public void setLoc_type(String loc_type) {
            this.loc_type = loc_type;
        }

        public String getLoc_text() {
            return loc_text;
        }

        public void setLoc_text(String loc_text) {
            this.loc_text = loc_text;
        }

        public String getStatus_type() {
            return status_type;
        }

        public void setStatus_type(String status_type) {
            this.status_type = status_type;
        }

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }

        public Type_Object(String loc_type, String loc_text, String status_type, String status_text,
                           String FuncLocID, String PlantID, String text, String parent_key) {
            this.loc_type = loc_type;
            this.loc_text = loc_text;
            this.status_type = status_type;
            this.status_text = status_text;
            this.FuncLocID = FuncLocID;
            this.PlantID = PlantID;
            this.text = text;
            this.parent_key = parent_key;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            onBackPressed();
        }
    }
}
