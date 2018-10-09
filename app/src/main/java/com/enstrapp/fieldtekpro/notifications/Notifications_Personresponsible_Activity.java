package com.enstrapp.fieldtekpro.notifications;

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

public class Notifications_Personresponsible_Activity extends AppCompatActivity implements View.OnClickListener
{

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
    String equip_id = "", plant_iwerk = "", workcenter_id = "";
    int request_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            equip_id = extras.getString("equip_id");
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals(""))
            {
                request_id = Integer.parseInt(request_ids);
            }
            if (equip_id != null && !equip_id.equals(""))
            {
            }
            else
            {
                plant_iwerk = extras.getString("plant_id");
                workcenter_id = extras.getString("workcenter_id");
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
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText)search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        new Get_Types_Data().execute();
    }

    private class Get_Types_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_Personresponsible_Activity.this,getResources().getString(R.string.loading));
            type_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            if (equip_id != null && !equip_id.equals(""))
            {
                try
                {
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip_id});
                    if (cursor1 != null && cursor1.getCount() > 0)
                    {
                        if (cursor1.moveToFirst())
                        {
                            do
                            {
                                plant_iwerk = cursor1.getString(10);
                                workcenter_id = cursor1.getString(11);
                            }
                            while (cursor1.moveToNext());
                        }
                    }
                }
                catch(Exception e)
                {
                }
            }
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from GET_EtPernr where Werks = ? and Arbpl = ?", new String[]{plant_iwerk, workcenter_id});
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            Type_Object nto = new Type_Object(cursor.getString(3), cursor.getString(5)+" "+cursor.getString(4));
                            type_list.add(nto);
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
            if (type_list.size() > 0)
            {
                title_textview.setText(getResources().getString(R.string.person_resp)+" ("+type_list.size()+")");
                adapter = new TYPE_ADAPTER(Notifications_Personresponsible_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Notifications_Personresponsible_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            }
            else
            {
                title_textview.setText(getResources().getString(R.string.person_resp)+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener()
    {
        @Override
        public boolean onQueryTextChange(String query)
        {
            query = query.toLowerCase();
            final List<Type_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < type_list.size(); i++)
            {
                String id = type_list.get(i).getId().toLowerCase();
                String value = type_list.get(i).getText().toLowerCase();
                if (id.contains(query) || value.contains(query))
                {
                    Type_Object nto = new Type_Object(type_list.get(i).getId().toString(), type_list.get(i).getText().toString());
                    filteredList.add(nto);
                }
            }
            if(filteredList.size() > 0)
            {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Notifications_Personresponsible_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(Notifications_Personresponsible_Activity.this, filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            }
            else
            {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            return true;
        }
        public boolean onQueryTextSubmit(String query)
        {
            return false;
        }
    };

    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder>
    {
        private Context mContext;
        private List<Type_Object> type_details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView id_textview, value_textview;
            LinearLayout data_layout;
            public MyViewHolder(View view)
            {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout)view.findViewById(R.id.data_layout);
            }
        }
        public TYPE_ADAPTER(Context mContext, List<Type_Object> list)
        {
            this.mContext = mContext;
            this.type_details_list = list;
        }
        @Override
        public TYPE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new TYPE_ADAPTER.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final TYPE_ADAPTER.MyViewHolder holder, int position)
        {
            final Type_Object nto = type_details_list.get(position);
            holder.id_textview.setText(nto.getId());
            holder.value_textview.setText(nto.getText());

            holder.data_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent();
                    intent.putExtra("personresponsible_id",holder.id_textview.getText().toString());
                    intent.putExtra("personresponsible_text",holder.value_textview.getText().toString());
                    setResult(request_id,intent);
                    Notifications_Personresponsible_Activity.this.finish();
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return type_details_list.size();
        }
    }

    public class Type_Object
    {
        private String id;
        private String text;
        public Type_Object(String id, String text)
        {
            this.id = id;
            this.text = text;
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
    }

    @Override
    public void onClick(View v)
    {
        if (v == back_imageview)
        {
            onBackPressed();
        }
    }

}
