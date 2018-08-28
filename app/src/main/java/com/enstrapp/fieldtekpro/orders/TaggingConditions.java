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

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class TaggingConditions extends AppCompatActivity implements View.OnClickListener {

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
    String iwerk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            iwerk = extras.getString("iwerk");
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

        new Get_Types_Data().execute();
    }

    private class Get_Types_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(TaggingConditions.this, getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = null;
            try {
                cursor = FieldTekPro_db.rawQuery("select * from EtWcmTgtyp where Iwerk = ?", new String[]{iwerk});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Type_Object nto = new Type_Object(cursor.getString(1),
                                    cursor.getString(2), cursor.getString(3),
                                    cursor.getString(4), cursor.getString(5),
                                    cursor.getString(6), cursor.getString(7),
                                    cursor.getString(8), cursor.getString(9),
                                    cursor.getString(10));
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
                title_textview.setText(getResources().getString(R.string.tag_cond) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(TaggingConditions.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TaggingConditions.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                title_textview.setText(getResources().getString(R.string.tag_cond) + " (0)");
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
                String tggrp = type_list.get(i).getTggrp().toLowerCase();
                String tgproc = type_list.get(i).getTgproc().toLowerCase();
                String unproc = type_list.get(i).getUnproc().toLowerCase();
                String tgprocx = type_list.get(i).getTgprocx().toLowerCase();
                if (tggrp.contains(query) || tgproc.contains(query)|| unproc.contains(query)|| tgprocx.contains(query)) {
                    Type_Object nto = new Type_Object(type_list.get(i).getIwerk().toString(), type_list.get(i).getTggrp().toString(),
                            type_list.get(i).getTgproc().toString(), type_list.get(i).getTgtyp().toString(),
                            type_list.get(i).getUnproc().toString(), type_list.get(i).getUntyp().toString(),
                            type_list.get(i).getPhblflg().toString(), type_list.get(i).getTgflg().toString(),
                            type_list.get(i).getUsable().toString(), type_list.get(i).getTgprocx().toString());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TaggingConditions.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(TaggingConditions.this, filteredList);
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
            public TextView og_tv, tagCond_tv, unTagCond_tv, desc_tv;
            LinearLayout data_ll;

            public MyViewHolder(View view) {
                super(view);
                og_tv = view.findViewById(R.id.og_tv);
                tagCond_tv = view.findViewById(R.id.tagCond_tv);
                unTagCond_tv = view.findViewById(R.id.unTagCond_tv);
                desc_tv = view.findViewById(R.id.desc_tv);
                data_ll = view.findViewById(R.id.data_ll);
            }
        }

        public TYPE_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_untag_data_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Type_Object nto = type_details_list.get(position);
            holder.og_tv.setText(nto.getTggrp());
            holder.tagCond_tv.setText(nto.getTgproc());
            holder.unTagCond_tv.setText(nto.getUnproc());
            holder.desc_tv.setText(nto.getTgprocx());
            holder.data_ll.setTag(position);

            holder.data_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("tggrp", holder.og_tv.getText().toString());
                    intent.putExtra("tgproc", holder.tagCond_tv.getText().toString());
                    intent.putExtra("unproc", holder.unTagCond_tv.getText().toString());
                    intent.putExtra("tgprox", holder.desc_tv.getText().toString());
                    intent.putExtra("tgtyp", type_details_list.get((Integer)v.getTag()).getTgtyp());
                    intent.putExtra("untyp", type_details_list.get((Integer)v.getTag()).getUntyp());
                    intent.putExtra("phblflg", type_details_list.get((Integer)v.getTag()).getPhblflg());
                    intent.putExtra("tgflg", type_details_list.get((Integer)v.getTag()).getTgflg());
                    setResult(RESULT_OK, intent);
                    TaggingConditions.this.finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public class Type_Object {
        private String iwerk;
        private String tggrp;
        private String tgproc;
        private String tgtyp;
        private String unproc;
        private String untyp;
        private String phblflg;
        private String tgflg;
        private String usable;
        private String tgprocx;

        public Type_Object(String iwerk, String tggrp, String tgproc, String tgtyp, String unproc, String untyp, String phblflg, String tgflg, String usable, String tgprocx) {
            this.iwerk = iwerk;
            this.tggrp = tggrp;
            this.tgproc = tgproc;
            this.tgtyp = tgtyp;
            this.unproc = unproc;
            this.untyp = untyp;
            this.phblflg = phblflg;
            this.tgflg = tgflg;
            this.usable = usable;
            this.tgprocx = tgprocx;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getTggrp() {
            return tggrp;
        }

        public void setTggrp(String tggrp) {
            this.tggrp = tggrp;
        }

        public String getTgproc() {
            return tgproc;
        }

        public void setTgproc(String tgproc) {
            this.tgproc = tgproc;
        }

        public String getTgtyp() {
            return tgtyp;
        }

        public void setTgtyp(String tgtyp) {
            this.tgtyp = tgtyp;
        }

        public String getUnproc() {
            return unproc;
        }

        public void setUnproc(String unproc) {
            this.unproc = unproc;
        }

        public String getUntyp() {
            return untyp;
        }

        public void setUntyp(String untyp) {
            this.untyp = untyp;
        }

        public String getPhblflg() {
            return phblflg;
        }

        public void setPhblflg(String phblflg) {
            this.phblflg = phblflg;
        }

        public String getTgflg() {
            return tgflg;
        }

        public void setTgflg(String tgflg) {
            this.tgflg = tgflg;
        }

        public String getUsable() {
            return usable;
        }

        public void setUsable(String usable) {
            this.usable = usable;
        }

        public String getTgprocx() {
            return tgprocx;
        }

        public void setTgprocx(String tgprocx) {
            this.tgprocx = tgprocx;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            onBackPressed();
        }
    }
}