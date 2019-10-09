package com.enstrapp.fieldtekpro.equipment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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
import java.util.HashMap;
import java.util.List;

public class Equipment_Activity extends Activity implements View.OnClickListener {

    String functionlocation_id = "";
    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<EQUI_Object> equi_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    EQUI_ADAPTER adapter;
    LinearLayout no_data_layout;
    ArrayList<HashMap<String, String>> equi_count_list = new ArrayList<HashMap<String, String>>();
    String equi_hirarchy_id = "";
    int request_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            functionlocation_id = extras.getString("functionlocation_id");
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
        Typeface myCustomFont = Typeface.createFromAsset(Equipment_Activity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        equi_count_list.clear();

        new Get_EQUI_Data().execute();
    }


    private class Get_EQUI_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_Activity.this, getResources().getString(R.string.loading_equip));
            equi_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = null;
                if (functionlocation_id != null && !functionlocation_id.equals("")) {
                    cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Tplnr = ? and Level = ?", new String[]{functionlocation_id, "1"});
                } else {
                    cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Level = ?", new String[]{"1"});
                }
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            EQUI_Object nto = new EQUI_Object(cursor.getString(1), cursor.getString(2), cursor.getString(11), cursor.getString(10), cursor.getString(6), cursor.getString(3), cursor.getString(5), cursor.getString(21), cursor.getString(22), cursor.getString(13), cursor.getString(12), cursor.getString(9), cursor.getString(29), cursor.getString(30));
                            equi_list.add(nto);
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
            if (equi_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.equip) + " (" + equi_list.size() + ")");
                adapter = new EQUI_ADAPTER(Equipment_Activity.this, equi_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.equip) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class EQUI_Object {
        private String floc_id;
        private String floc_name;
        private String Work_Center;
        private String Plant;
        private String Category;
        private String equip_no;
        private String equip_text;
        private String level;
        private String sequi;
        private String Kostl;
        private String Eqart;
        private String iwerk;
        private String bukrs;
        private String ingrp;

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getBukrs() {
            return bukrs;
        }

        public void setBukrs(String bukrs) {
            this.bukrs = bukrs;
        }

        public String getKostl() {
            return Kostl;
        }

        public void setKostl(String kostl) {
            Kostl = kostl;
        }

        public String getEqart() {
            return Eqart;
        }

        public void setEqart(String eqart) {
            Eqart = eqart;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
        }

        public String getFloc_id() {
            return floc_id;
        }

        public void setFloc_id(String floc_id) {
            this.floc_id = floc_id;
        }

        public String getFloc_name() {
            return floc_name;
        }

        public void setFloc_name(String floc_name) {
            this.floc_name = floc_name;
        }

        public String getWork_Center() {
            return Work_Center;
        }

        public void setWork_Center(String work_Center) {
            Work_Center = work_Center;
        }

        public String getPlant() {
            return Plant;
        }

        public void setPlant(String plant) {
            Plant = plant;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public String getEquip_no() {
            return equip_no;
        }

        public void setEquip_no(String equip_no) {
            this.equip_no = equip_no;
        }

        public String getEquip_text() {
            return equip_text;
        }

        public void setEquip_text(String equip_text) {
            this.equip_text = equip_text;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSequi() {
            return sequi;
        }

        public void setSequi(String sequi) {
            this.sequi = sequi;
        }

        public EQUI_Object(String floc_id, String floc_name, String Work_Center, String Plant, String Category, String equip_no, String equip_text, String level, String sequi, String ingrp, String Kostl, String Eqart, String iwerk, String bukrs) {
            this.floc_id = floc_id;
            this.floc_name = floc_name;
            this.Work_Center = Work_Center;
            this.Plant = Plant;
            this.Category = Category;
            this.equip_no = equip_no;
            this.equip_text = equip_text;
            this.level = level;
            this.sequi = sequi;
            this.ingrp = ingrp;
            this.Kostl = Kostl;
            this.Eqart = Eqart;
            this.iwerk = iwerk;
            this.bukrs = bukrs;
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<EQUI_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < equi_list.size(); i++) {
                String id = equi_list.get(i).getEquip_no().toLowerCase();
                String value = equi_list.get(i).getEquip_text().toLowerCase();
                String plant = equi_list.get(i).getPlant().toLowerCase();
                String workcenter = equi_list.get(i).getWork_Center().toLowerCase();
                String Eqart = equi_list.get(i).getEqart().toLowerCase();
                if (id.contains(query) || value.contains(query) || plant.contains(query) || workcenter.contains(query) || Eqart.contains(query)) {
                    EQUI_Object nto = new EQUI_Object(equi_list.get(i).getFloc_id().toString(), equi_list.get(i).getFloc_name().toString(), equi_list.get(i).getWork_Center().toString(), equi_list.get(i).getPlant().toString(), equi_list.get(i).getCategory().toString(), equi_list.get(i).getEquip_no().toString(), equi_list.get(i).getEquip_text().toString(), equi_list.get(i).getLevel().toString(), equi_list.get(i).getSequi().toString(), equi_list.get(i).getIngrp().toString(), equi_list.get(i).getKostl().toString(), equi_list.get(i).getEqart().toString(), equi_list.get(i).getIwerk().toString(), equi_list.get(i).getBukrs().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                title_textview.setText(getResources().getString(R.string.equip) + " (" + filteredList.size() + ")");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new EQUI_ADAPTER(Equipment_Activity.this, filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.equip) + " (0)");
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


    public class EQUI_ADAPTER extends RecyclerView.Adapter<EQUI_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<EQUI_Object> details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView eqart_textview, floc_textview, id_textview, value_textview, textview1, textview2, textview3, textview4, textview5, textview6, textview7;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                textview1 = (TextView) view.findViewById(R.id.textview1);
                textview2 = (TextView) view.findViewById(R.id.textview2);
                textview3 = (TextView) view.findViewById(R.id.textview3);
                textview4 = (TextView) view.findViewById(R.id.textview4);
                textview5 = (TextView) view.findViewById(R.id.textview5);
                textview6 = (TextView) view.findViewById(R.id.textview6);
                textview7 = (TextView) view.findViewById(R.id.textview7);
                floc_textview = (TextView) view.findViewById(R.id.floc_textview);
                eqart_textview = (TextView) view.findViewById(R.id.eqart_textview);
            }
        }

        public EQUI_ADAPTER(Context mContext, List<EQUI_Object> list) {
            this.mContext = mContext;
            this.details_list = list;
        }

        @Override
        public EQUI_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_list_data, parent, false);
            return new EQUI_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final EQUI_ADAPTER.MyViewHolder holder, int position) {
            final EQUI_Object nto = details_list.get(position);
            SpannableString spanString = new SpannableString(nto.getEquip_no());
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            holder.id_textview.setText(spanString);
            //holder.id_textview.setText(nto.getEquip_no());
            holder.value_textview.setText(nto.getEquip_text());
            holder.textview1.setText(nto.getSequi());
            holder.floc_textview.setText(nto.getFloc_id());
            holder.textview2.setText(nto.getFloc_id());
            holder.textview3.setText(nto.getFloc_name());
            holder.textview4.setText(nto.getWork_Center());
            holder.textview5.setText(nto.getPlant());
            holder.textview6.setText(nto.getLevel());
            holder.textview7.setText(nto.getIngrp());
            holder.eqart_textview.setText(nto.getEqart());
            if (holder.textview1.getText().toString().equalsIgnoreCase("X") || holder.textview1.getText().toString().equalsIgnoreCase("true")) {
                holder.data_layout.setBackgroundResource(R.drawable.border_fail);
                SpannableString content = new SpannableString(nto.getEquip_no());
                content.setSpan(new UnderlineSpan(), 0, nto.getEquip_no().length(), 0);
                holder.id_textview.setText(content);
                holder.id_textview.setTextColor(getResources().getColor(R.color.red));
                holder.id_textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("equipment_id", holder.id_textview.getText().toString());
                        intent.putExtra("equipment_text", holder.value_textview.getText().toString());
                        intent.putExtra("functionlocation_id", holder.textview2.getText().toString());
                        intent.putExtra("functionlocation_text", holder.textview3.getText().toString());
                        intent.putExtra("plant_id", holder.textview5.getText().toString());
                        intent.putExtra("ingrp_id", holder.textview7.getText().toString());
                        intent.putExtra("work_center", holder.textview4.getText().toString());
                        intent.putExtra("cost_center", nto.getKostl());
                        intent.putExtra("iwerk", nto.getIwerk());
                        intent.putExtra("bukrs", nto.getBukrs());
                        intent.putExtra("wrkCntr_id", holder.textview4.getText().toString());
                        intent.putExtra("kostl_id", nto.getKostl());
                        setResult(RESULT_OK, intent);
                        Equipment_Activity.this.finish();
                    }
                });
            } else {
                holder.id_textview.setText(nto.getEquip_no());
                holder.id_textview.setTextColor(getResources().getColor(R.color.black));
                //holder.layout.setBackgroundResource(0);
                holder.data_layout.setBackgroundResource(R.drawable.blueborder);
            }
            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.textview1.getText().toString().equalsIgnoreCase("X") || holder.textview1.getText().toString().equalsIgnoreCase("true")) {
                        int equi_count_size = equi_count_list.size();
                        HashMap<String, String> equi_count = new HashMap<String, String>();
                        equi_count.put("equi_level", Integer.toString(equi_count_size + 1));
                        equi_count.put("equi_id", equi_hirarchy_id);
                        equi_count_list.add(equi_count);
                        equi_hirarchy_id = holder.id_textview.getText().toString();
                        new Get_EQUI_Data_Hirarchy().execute();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("equipment_id", holder.id_textview.getText().toString());
                        intent.putExtra("equipment_text", holder.value_textview.getText().toString());
                        intent.putExtra("functionlocation_id", holder.textview2.getText().toString());
                        intent.putExtra("functionlocation_text", holder.textview3.getText().toString());
                        intent.putExtra("plant_id", holder.textview5.getText().toString());
                        intent.putExtra("ingrp_id", holder.textview7.getText().toString());
                        intent.putExtra("work_center", holder.textview4.getText().toString());
                        intent.putExtra("cost_center", nto.getKostl());
                        intent.putExtra("iwerk", nto.getIwerk());
                        intent.putExtra("bukrs", nto.getBukrs());
                        intent.putExtra("wrkCntr_id", holder.textview4.getText().toString());
                        intent.putExtra("kostl_id", nto.getKostl());
                        setResult(RESULT_OK, intent);
                        Equipment_Activity.this.finish();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return details_list.size();
        }
    }


    private class Get_EQUI_Data_Hirarchy extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_Activity.this, getResources().getString(R.string.loading_equip));
            equi_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Hequi = ?", new String[]{equi_hirarchy_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            EQUI_Object nto = new EQUI_Object(cursor.getString(1), cursor.getString(2), cursor.getString(11), cursor.getString(10), cursor.getString(6), cursor.getString(3), cursor.getString(5), cursor.getString(21), cursor.getString(22), cursor.getString(13), cursor.getString(12), cursor.getString(9), cursor.getString(29), cursor.getString(30));
                            equi_list.add(nto);
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
            if (equi_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.equip) + " : " + equi_hirarchy_id + " (" + equi_list.size() + ")");
                adapter = new EQUI_ADAPTER(Equipment_Activity.this, equi_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.equip) + " (0)");
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
            if (equi_count_list.size() == 0) {
                onBackPressed();
            } else {
                if (equi_count_list.size() == 0) {
                    equi_count_list.clear();
                    new Get_EQUI_Data().execute();
                } else if (equi_count_list.size() == 1) {
                    equi_count_list.clear();
                    new Get_EQUI_Data().execute();
                } else {
                    int equi_current_size = equi_count_list.size();
                    int equi_new_count_size = equi_current_size - 1;
                    equi_hirarchy_id = equi_count_list.get(equi_new_count_size).get("equi_id");
                    equi_count_list.remove(equi_new_count_size);
                    new Get_EQUI_Data_Hirarchy().execute();
                }
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (equi_count_list.size() == 0) {
                    onBackPressed();
                } else {
                    if (equi_count_list.size() == 0) {
                        equi_count_list.clear();
                        new Get_EQUI_Data().execute();
                    } else if (equi_count_list.size() == 1) {
                        equi_count_list.clear();
                        new Get_EQUI_Data().execute();
                    } else {
                        int equi_current_size = equi_count_list.size();
                        int equi_new_count_size = equi_current_size - 1;
                        equi_hirarchy_id = equi_count_list.get(equi_new_count_size).get("equi_id");
                        equi_count_list.remove(equi_new_count_size);
                        new Get_EQUI_Data_Hirarchy().execute();
                    }
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

