package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSA_Hazards_List_Activity extends AppCompatActivity implements View.OnClickListener
{

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    RecyclerView list_recycleview;
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    TYPE_ADAPTER adapter;
    int request_id = 0;
    String hazardcategory_id = "", hazard_data = "";
    Button cancel_button, submit_button;
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permit_isolation_list_activity);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            hazardcategory_id = bundle.getString("hazardcategory_id");
            hazard_data = bundle.getString("hazard_data");
        }

        title_textview = (TextView)findViewById(R.id.title_textview);
        no_data_textview = (TextView)findViewById(R.id.no_data_textview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);
        list_recycleview = (RecyclerView)findViewById(R.id.recyclerview);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        submit_button = (Button)findViewById(R.id.submit_button);

        submit_button.setText("Add");

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        if (hazard_data != null && !hazard_data.equals(""))
        {
            Gson gson = new Gson();
            Type type = new TypeToken<List<JSA_Hazards_List_Activity.Type_Object>>() {}.getType();
            type_list = gson.fromJson(hazard_data, type);
            if (type_list.size() > 0)
            {
                title_textview.setText(getResources().getString(R.string.hazard)+" ("+type_list.size()+")");
                adapter = new TYPE_ADAPTER(JSA_Hazards_List_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JSA_Hazards_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            }
            else
            {
                title_textview.setText(getResources().getString(R.string.hazard)+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
        }
        else
        {
            new Get_Types().execute();
        }
    }


    private class Get_Types extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(JSA_Hazards_List_Activity.this,getResources().getString(R.string.loading));
            type_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEHSHazard", null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            String hazard_id = cursor.getString(1);
                            if(hazard_id.contains(hazardcategory_id))
                            {
                                Type_Object nto = new Type_Object(cursor.getString(1), cursor.getString(2), false);
                                type_list.add(nto);
                            }
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
                title_textview.setText(getResources().getString(R.string.hazard)+" ("+type_list.size()+")");
                adapter = new TYPE_ADAPTER(JSA_Hazards_List_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JSA_Hazards_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            }
            else
            {
                title_textview.setText(getResources().getString(R.string.hazard)+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder>
    {
        private Context mContext;
        private List<Type_Object> type_details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView id_textview, value_textview;
            CheckBox checkbox;
            public MyViewHolder(View view)
            {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                checkbox = (CheckBox)view.findViewById(R.id.checkbox);
                checkbox.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(checkbox.isChecked())
                        {
                            int position = (Integer) v.getTag();
                            type_list.get(position).setSelected_status(true);
                        }
                        else
                        {
                            int position = (Integer) v.getTag();
                            type_list.get(position).setSelected_status(false);
                        }
                    }
                });
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_checkbox_list_data, parent, false);
            return new TYPE_ADAPTER.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final TYPE_ADAPTER.MyViewHolder holder, int position)
        {
            final Type_Object nto = type_details_list.get(position);
            holder.id_textview.setText(nto.getWork_id());
            holder.value_textview.setText(nto.getWork_text());
            holder.checkbox.setTag(position);
            holder.checkbox.setChecked((type_details_list.get(position).selected_status == true ? true : false));
        }
        @Override
        public int getItemCount()
        {
            return type_details_list.size();
        }
    }


    public class Type_Object
    {
        private String work_id;
        private String work_text;
        private Boolean selected_status;
        public Type_Object(String work_id, String work_text, boolean selected_status)
        {
            this.work_id = work_id;
            this.work_text = work_text;
            this.selected_status = selected_status;
        }
        public Boolean getSelected_status() {
            return selected_status;
        }
        public void setSelected_status(Boolean selected_status) {
            this.selected_status = selected_status;
        }
        public String getWork_id() {
            return work_id;
        }
        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }
        public String getWork_text() {
            return work_text;
        }
        public void setWork_text(String work_text) {
            this.work_text = work_text;
        }
    }


    @Override
    public void onClick(View v)
    {
        if (v == back_imageview)
        {
            JSA_Hazards_List_Activity.this.finish();
        }
        else if (v == cancel_button)
        {
            JSA_Hazards_List_Activity.this.finish();
        }
        else if (v == submit_button)
        {
            ArrayList arrayList = new ArrayList();
            for(int i = 0; i < type_list.size(); i++)
            {
                boolean checked_status = type_list.get(i).getSelected_status();
                if(checked_status)
                {
                    arrayList.add(type_list.get(i).getWork_id());
                }
            }
            if(arrayList.size() > 0)
            {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Type_Object>>() {}.getType();
                String selected_json = gson.toJson(type_list, type);
                Intent intent=new Intent();
                intent.putExtra("hazard_data",selected_json);
                setResult(RESULT_OK,intent);
                JSA_Hazards_List_Activity.this.finish();
            }
            else
            {
                error_dialog.show_error_dialog(JSA_Hazards_List_Activity.this,"Please Check Atleast One Hazard");
            }
        }
    }


}
