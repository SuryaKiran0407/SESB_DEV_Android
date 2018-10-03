package com.enstrapp.fieldtekpro.functionlocation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionLocation_Activity extends AppCompatActivity implements View.OnClickListener
{

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<FLOC_Object> floc_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    FLOC_ADAPTER adapter;
    LinearLayout no_data_layout;
    ArrayList<HashMap<String, String>> floc_count_list = new ArrayList<HashMap<String,String>>();
    String floc_hirarchy_id = "";
    int request_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.functionlocation_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals(""))
            {
                request_id = Integer.parseInt(request_ids);
            }
        }

        title_textview = (TextView)findViewById(R.id.title_textview);
        no_data_textview = (TextView)findViewById(R.id.no_data_textview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);
        search = (SearchView)findViewById(R.id.search);
        list_recycleview = (RecyclerView)findViewById(R.id.list_recycleview);
        no_data_layout = (LinearLayout)findViewById(R.id.no_data_layout);

        back_imageview.setOnClickListener(this);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext() .getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(FunctionLocation_Activity.this.getAssets(),"fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText)search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        floc_count_list.clear();

        new Get_FLOC_Data().execute();
    }


    private class Get_FLOC_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(FunctionLocation_Activity.this,getResources().getString(R.string.loading_floc));
            floc_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Level = ?", new String[]{"1"});
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            FLOC_Object nto = new FLOC_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),"X", cursor.getString(14));
                            floc_list.add(nto);
                        }
                        while (cursor.moveToNext());
                    }
                }
                else
                {
                    cursor.close();
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
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (floc_list.size() > 0)
            {
                title_textview.setText(getResources().getString(R.string.func_loc)+" ("+floc_list.size()+")");
                adapter = new FLOC_ADAPTER(FunctionLocation_Activity.this, floc_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FunctionLocation_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
            else
            {
                title_textview.setText(getResources().getString(R.string.func_loc)+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class FLOC_Object
    {
        private String Location_ID;
        private String Location_Name;
        private String Plant;
        private String Work_Center;
        private String Cost_Center;
        private String Category;
        private String Planner_Group;
        private String Stplnr;
        private String Status;
        private String iwerk;
        public String getIwerk() {
            return iwerk;
        }
        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }
        public String getStatus() {
            return Status;
        }
        public void setStatus(String Status) {
            Status = Status;
        }
        public String getLocation_ID() {
            return Location_ID;
        }
        public void setLocation_ID(String location_ID) {
            Location_ID = location_ID;
        }
        public String getLocation_Name() {
            return Location_Name;
        }
        public void setLocation_Name(String location_Name) {
            Location_Name = location_Name;
        }
        public String getPlant() {
            return Plant;
        }
        public void setPlant(String plant) {
            Plant = plant;
        }
        public String getWork_Center() {
            return Work_Center;
        }
        public void setWork_Center(String work_Center) {
            Work_Center = work_Center;
        }
        public String getCost_Center() {
            return Cost_Center;
        }
        public void setCost_Center(String cost_Center) {
            Cost_Center = cost_Center;
        }
        public String getCategory() {
            return Category;
        }
        public void setCategory(String category) {
            Category = category;
        }
        public String getPlanner_Group() {
            return Planner_Group;
        }
        public void setPlanner_Group(String planner_Group) {
            Planner_Group = planner_Group;
        }
        public String getStplnr() {
            return Stplnr;
        }
        public void setStplnr(String stplnr) {
            Stplnr = stplnr;
        }
        public FLOC_Object(String Location_ID, String Location_Name, String Plant, String Work_Center, String Cost_Center, String Category, String Planner_Group, String Status, String iwerk)
        {
            this.Location_ID = Location_ID;
            this.Location_Name = Location_Name;
            this.Plant = Plant;
            this.Work_Center = Work_Center;
            this.Cost_Center = Cost_Center;
            this.Category = Category;
            this.Planner_Group = Planner_Group;
            this.Status = Status;
            this.iwerk = iwerk;
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener()
    {
        @Override
        public boolean onQueryTextChange(String query)
        {
            query = query.toLowerCase();
            final List<FLOC_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < floc_list.size(); i++)
            {
                String id = floc_list.get(i).getLocation_ID().toLowerCase();
                String value = floc_list.get(i).getLocation_Name().toLowerCase();
                String plant = floc_list.get(i).getPlant().toLowerCase();
                if (id.contains(query) || value.contains(query) || plant.contains(query))
                {
                    FLOC_Object nto = new FLOC_Object(floc_list.get(i).getLocation_ID().toString(), floc_list.get(i).getLocation_Name().toString(), floc_list.get(i).getPlant().toString(), floc_list.get(i).getWork_Center().toString(), floc_list.get(i).getCost_Center().toString(), floc_list.get(i).getCategory().toString(), floc_list.get(i).getPlanner_Group().toString(), floc_list.get(i).getStatus().toString(), floc_list.get(i).getIwerk().toString());
                    filteredList.add(nto);
                }
            }
            if(filteredList.size() > 0)
            {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FunctionLocation_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new FLOC_ADAPTER(FunctionLocation_Activity.this, filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
            else
            {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
            }
            return true;
        }
        public boolean onQueryTextSubmit(String query)
        {
            return false;
        }
    };


    public class FLOC_ADAPTER extends RecyclerView.Adapter<FLOC_ADAPTER.MyViewHolder>
    {
        private Context mContext;
        private List<FLOC_Object> details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView id_textview, value_textview, textview1, textview2;
            LinearLayout data_layout;
            public MyViewHolder(View view)
            {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout)view.findViewById(R.id.data_layout);
                textview1 = (TextView)view.findViewById(R.id.textview1);
                textview2 = (TextView)view.findViewById(R.id.textview2);
            }
        }
        public FLOC_ADAPTER(Context mContext, List<FLOC_Object> list)
        {
            this.mContext = mContext;
            this.details_list = list;
        }
        @Override
        public FLOC_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new FLOC_ADAPTER.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final FLOC_ADAPTER.MyViewHolder holder, int position)
        {
            final FLOC_Object nto = details_list.get(position);
            holder.value_textview.setText(nto.getLocation_Name());
            holder.textview1.setText(nto.getStatus());
            holder.textview2.setText(nto.getPlant());
            if(holder.textview1.getText().toString().equalsIgnoreCase("X"))
            {
                holder.data_layout.setBackgroundResource(R.drawable.border_fail);
                SpannableString content = new SpannableString(nto.getLocation_ID());
                content.setSpan(new UnderlineSpan(), 0, nto.getLocation_ID().length(), 0);
                holder.id_textview.setText(content);
                holder.id_textview.setTextColor(getResources().getColor(R.color.red));
                holder.id_textview.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent=new Intent();
                        intent.putExtra("functionlocation_id",holder.id_textview.getText().toString());
                        intent.putExtra("functionlocation_text",holder.value_textview.getText().toString());
                        intent.putExtra("plant_id",holder.textview2.getText().toString());
                        intent.putExtra("kostl_id", nto.getCost_Center());
                        intent.putExtra("iwerk", nto.getIwerk());
                        intent.putExtra("ingrp_id", nto.getPlanner_Group());
                        setResult(RESULT_OK,intent);
                        FunctionLocation_Activity.this.finish();
                    }
                });
            }
            else
            {
                holder.id_textview.setText(nto.getLocation_ID());
                holder.id_textview.setTextColor(getResources().getColor(R.color.black));
                //holder.layout.setBackgroundResource(0);
                holder.data_layout.setBackgroundResource(R.drawable.blueborder);
            }
            holder.data_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(holder.textview1.getText().toString().equalsIgnoreCase("X"))
                    {
                        int floc_count_size = floc_count_list.size();
                        HashMap<String, String> floc_count = new HashMap<String, String>();
                        floc_count.put("floc_level", Integer.toString(floc_count_size+1));
                        floc_count.put("floc_id", floc_hirarchy_id);
                        floc_count_list.add(floc_count);
                        floc_hirarchy_id = holder.id_textview.getText().toString();
                        new Get_FLOC_Data_Hirarchy().execute();
                    }
                    else
                    {
                        Intent intent=new Intent();
                        intent.putExtra("functionlocation_id",holder.id_textview.getText().toString());
                        intent.putExtra("functionlocation_text",holder.value_textview.getText().toString());
                        intent.putExtra("plant_id",holder.textview2.getText().toString());
                        intent.putExtra("kostl_id", nto.getCost_Center());
                        intent.putExtra("iwerk", nto.getIwerk());
                        intent.putExtra("ingrp_id", nto.getPlanner_Group());
                        setResult(RESULT_OK,intent);
                        FunctionLocation_Activity.this.finish();
                    }
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return details_list.size();
        }
    }


    private class Get_FLOC_Data_Hirarchy extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(FunctionLocation_Activity.this,getResources().getString(R.string.loading_floc));
            floc_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Tplma = ?", new String[]{floc_hirarchy_id});
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            FLOC_Object nto = new FLOC_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),cursor.getString(13), cursor.getString(13));
                            floc_list.add(nto);
                        }
                        while (cursor.moveToNext());
                    }
                }
                else
                {
                    cursor.close();
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
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (floc_list.size() > 0)
            {
                title_textview.setText(getResources().getString(R.string.func_loc)+" : "+floc_hirarchy_id+" ("+floc_list.size()+")");
                adapter = new FLOC_ADAPTER(FunctionLocation_Activity.this, floc_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FunctionLocation_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }
            else
            {
                title_textview.setText(getResources().getString(R.string.func_loc)+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    @Override
    public void onClick(View v)
    {
        if (v == back_imageview)
        {
            if(floc_count_list.size() == 0)
            {
                onBackPressed();
            }
            else
            {
                if(floc_count_list.size() == 0)
                {
                    floc_count_list.clear();
                    new Get_FLOC_Data().execute();
                }
                else if(floc_count_list.size() == 1)
                {
                    floc_count_list.clear();
                    new Get_FLOC_Data().execute();
                }
                else
                {
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                if(floc_count_list.size() == 0)
                {
                    onBackPressed();
                }
                else
                {
                    if(floc_count_list.size() == 0)
                    {
                        floc_count_list.clear();
                        new Get_FLOC_Data().execute();
                    }
                    else if(floc_count_list.size() == 1)
                    {
                        floc_count_list.clear();
                        new Get_FLOC_Data().execute();
                    }
                    else
                    {
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
