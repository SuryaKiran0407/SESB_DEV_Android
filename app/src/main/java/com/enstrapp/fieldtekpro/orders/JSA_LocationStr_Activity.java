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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class JSA_LocationStr_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    FLOC_ADAPTER adapter;
    LinearLayout no_data_layout;
    ArrayList<HashMap<String, String>> floc_count_list = new ArrayList<HashMap<String, String>>();
    String floc_hirarchy_id = "";
    int request_id = 0;
    String hirarchy_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.functionlocation_activity);

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
        Typeface myCustomFont = Typeface.createFromAsset(JSA_LocationStr_Activity.this.getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        floc_count_list.clear();

        new Get_FLOC_Data().execute();
    }

    private class Get_FLOC_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(JSA_LocationStr_Activity.this,
                    getResources().getString(R.string.loading_floc));
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
                                    cursor.getString(8), cursor.getString(7),
                                    cursor.getString(9),
                                    cursor.getString(10),
                                    cursor.getString(11));
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (type_list.size() > 0) {
                Collections.sort(type_list, new Comparator<Type_Object>() {
                    public int compare(Type_Object o1, Type_Object o2) {
                        return o1.getText().compareTo(o2.getText());
                    }
                });
                title_textview.setText(getResources().getString(R.string.loc_struct) + " (" + type_list.size() + ")");
                adapter = new FLOC_ADAPTER(JSA_LocationStr_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_LocationStr_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.loc_struct) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
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
        private String LocRootRefKey;
        private String ParRootRefKey;
        private String RefID;

        public String getLocRootRefKey() {
            return LocRootRefKey;
        }

        public void setLocRootRefKey(String locRootRefKey) {
            LocRootRefKey = locRootRefKey;
        }

        public String getParRootRefKey() {
            return ParRootRefKey;
        }

        public void setParRootRefKey(String parRootRefKey) {
            ParRootRefKey = parRootRefKey;
        }

        public String getRefID() {
            return RefID;
        }

        public void setRefID(String refID) {
            RefID = refID;
        }

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

        public Type_Object(String loc_type, String loc_text, String status_type,
                           String status_text, String FuncLocID, String PlantID, String text,
                           String parent_key, String LocRootRefKey, String ParRootRefKey,
                           String RefID) {
            this.loc_type = loc_type;
            this.loc_text = loc_text;
            this.status_type = status_type;
            this.status_text = status_text;
            this.FuncLocID = FuncLocID;
            this.PlantID = PlantID;
            this.text = text;
            this.parent_key = parent_key;
            this.LocRootRefKey = LocRootRefKey;
            this.ParRootRefKey = ParRootRefKey;
            this.RefID = RefID;
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<Type_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < type_list.size(); i++) {
                String id = type_list.get(i).getText().toLowerCase();
                String value = type_list.get(i).getLoc_text().toLowerCase();
                String plant = type_list.get(i).getStatus_text().toLowerCase();
                if (id.contains(query) || value.contains(query) || plant.contains(query)) {
                    Type_Object nto = new Type_Object(type_list.get(i).getLoc_type().toString(),
                            type_list.get(i).getLoc_text().toString(),
                            type_list.get(i).getStatus_type().toString(),
                            type_list.get(i).getStatus_text().toString(),
                            type_list.get(i).getFuncLocID().toString(),
                            type_list.get(i).getPlantID().toString(),
                            type_list.get(i).getText().toString(),
                            type_list.get(i).getParent_key().toString(),
                            type_list.get(i).getLocRootRefKey().toString(),
                            type_list.get(i).getParRootRefKey().toString(),
                            type_list.get(i).getRefID().toString()
                    );
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_LocationStr_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new FLOC_ADAPTER(JSA_LocationStr_Activity.this, filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    public class FLOC_ADAPTER extends RecyclerView.Adapter<FLOC_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Type_Object> details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, RefID_textview, name_textview, loc_type_textview,
                    status_textview, LocRootRefKey_textview, ParRootRefKey_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                name_textview = (TextView) view.findViewById(R.id.name_textview);
                loc_type_textview = (TextView) view.findViewById(R.id.loc_type_textview);
                status_textview = (TextView) view.findViewById(R.id.status_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                LocRootRefKey_textview = (TextView) view.findViewById(R.id.LocRootRefKey_textview);
                ParRootRefKey_textview = (TextView) view.findViewById(R.id.ParRootRefKey_textview);
                RefID_textview = (TextView) view.findViewById(R.id.RefID_textview);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
            }
        }

        public FLOC_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.details_list = list;
        }

        @Override
        public FLOC_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jsa_locationstructure_list_data, parent, false);
            return new FLOC_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final FLOC_ADAPTER.MyViewHolder holder, int position) {
            final Type_Object nto = details_list.get(position);
            holder.name_textview.setText(nto.getText());
            holder.loc_type_textview.setText(nto.getLoc_text());
            holder.status_textview.setText(nto.getStatus_text());
            holder.LocRootRefKey_textview.setText(nto.getLocRootRefKey());
            holder.ParRootRefKey_textview.setText(nto.getParRootRefKey());
            holder.RefID_textview.setText(nto.getRefID());
            holder.id_textview.setText(nto.getRefID());
            String hirarchyData = read_hirarchyData(holder.LocRootRefKey_textview.getText().toString());
            if (holder.LocRootRefKey_textview.getText().toString().length() == 0) {
                holder.data_layout.setBackgroundResource(R.drawable.blueborder);
                holder.name_textview.setTextColor(getResources().getColor(R.color.black));
                holder.name_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("loc_type_id", nto.getRefID());
                        intent.putExtra("loc_type_text", nto.getText());
                        setResult(RESULT_OK, intent);
                        JSA_LocationStr_Activity.this.finish();
                    }
                });
            } else {
                if (hirarchyData.equalsIgnoreCase("true")) {
                    holder.data_layout.setBackgroundResource(R.drawable.border_fail);
                    SpannableString content = new SpannableString(nto.getText());
                    content.setSpan(new UnderlineSpan(), 0, nto.getText().length(), 0);
                    holder.name_textview.setText(content);
                    holder.name_textview.setTextColor(getResources().getColor(R.color.red));
                    holder.name_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("loc_type_id", nto.getRefID());
                            intent.putExtra("loc_type_text", nto.getText());
                            setResult(RESULT_OK, intent);
                            JSA_LocationStr_Activity.this.finish();
                        }
                    });
                } else {
                    holder.data_layout.setBackgroundResource(R.drawable.blueborder);
                    holder.name_textview.setTextColor(getResources().getColor(R.color.black));
                    holder.name_textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("loc_type_id", nto.getRefID());
                            intent.putExtra("loc_type_text", nto.getText());
                            setResult(RESULT_OK, intent);
                            JSA_LocationStr_Activity.this.finish();
                        }
                    });
                }
            }

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.LocRootRefKey_textview.getText().toString().length() == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("loc_type_id", nto.getRefID());
                        intent.putExtra("loc_type_text", nto.getText());
                        setResult(RESULT_OK, intent);
                        JSA_LocationStr_Activity.this.finish();
                    } else {
                        String hirarchyData =
                                read_hirarchyData(holder.LocRootRefKey_textview.getText().toString());
                        if (hirarchyData.equalsIgnoreCase("true")) {
                            int floc_count_size = floc_count_list.size();
                            HashMap<String, String> floc_count = new HashMap<String, String>();
                            floc_count.put("floc_level", Integer.toString(floc_count_size + 1));
                            floc_count.put("floc_id", floc_hirarchy_id);
                            floc_count_list.add(floc_count);
                            floc_hirarchy_id = holder.LocRootRefKey_textview.getText().toString();
                            hirarchy_text = holder.name_textview.getText().toString();
                            new Get_FLOC_Data_Hirarchy().execute();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("loc_type_id", nto.getRefID());
                            intent.putExtra("loc_type_text", nto.getText());
                            setResult(RESULT_OK, intent);
                            JSA_LocationStr_Activity.this.finish();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return details_list.size();
        }
    }

    public String read_hirarchyData(String LocRootRefKey_textview) {
        String hirarchy_status = "false";
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEHSLocRev where " +
                    "ParRootRefKey = ?", new String[]{LocRootRefKey_textview});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        hirarchy_status = "true";
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
                hirarchy_status = "false";
            }
        } catch (Exception e) {
            hirarchy_status = "false";
        }
        return hirarchy_status;
    }

    private class Get_FLOC_Data_Hirarchy extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(JSA_LocationStr_Activity.this,
                    getResources().getString(R.string.loading_floc));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEHSLocRev where" +
                        " ParRootRefKey = ?", new String[]{floc_hirarchy_id});
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
                                    cursor.getString(8), cursor.getString(7),
                                    cursor.getString(9),
                                    cursor.getString(10),
                                    cursor.getString(11));
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (type_list.size() > 0) {
                Collections.sort(type_list, new Comparator<Type_Object>() {
                    public int compare(Type_Object o1, Type_Object o2) {
                        return o1.getText().compareTo(o2.getText());
                    }
                });
                title_textview.setText(getResources().getString(R.string.loc_struct) + " : " + hirarchy_text
                        + " (" + type_list.size() + ")");
                adapter = new FLOC_ADAPTER(JSA_LocationStr_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_LocationStr_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.loc_struct) + " : " + hirarchy_text + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            if (floc_count_list.size() == 0) {
                onBackPressed();
            } else {
                if (floc_count_list.size() == 0) {
                    floc_count_list.clear();
                    new Get_FLOC_Data().execute();
                } else if (floc_count_list.size() == 1) {
                    floc_count_list.clear();
                    new Get_FLOC_Data().execute();
                } else {
                    int floc_current_size = floc_count_list.size();
                    int floc_new_count_size = floc_current_size - 1;
                    floc_hirarchy_id = floc_count_list.get(floc_new_count_size).get("floc_id");
                    floc_count_list.remove(floc_new_count_size);
                    new Get_FLOC_Data_Hirarchy().execute();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (floc_count_list.size() == 0) {
                    onBackPressed();
                } else {
                    if (floc_count_list.size() == 0) {
                        floc_count_list.clear();
                        new Get_FLOC_Data().execute();
                    } else if (floc_count_list.size() == 1) {
                        floc_count_list.clear();
                        new Get_FLOC_Data().execute();
                    } else {
                        int floc_current_size = floc_count_list.size();
                        int floc_new_count_size = floc_current_size - 1;
                        floc_hirarchy_id = floc_count_list.get(floc_new_count_size).get("floc_id");
                        floc_count_list.remove(floc_new_count_size);
                        new Get_FLOC_Data_Hirarchy().execute();
                    }
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
