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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSA_Impacts_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    RecyclerView list_recycleview;
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    TYPE_ADAPTER adapter;
    int request_id = 0;
    String hazardcategory_id = "", impact_data = "";
    Button cancel_button, submit_button;
    Error_Dialog error_dialog = new Error_Dialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permit_isolation_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hazardcategory_id = extras.getString("hazardcategory_id");
            impact_data = extras.getString("impact_data");
        }

        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        list_recycleview = (RecyclerView) findViewById(R.id.recyclerview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);

        submit_button.setText("Add");

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        if (impact_data != null && !impact_data.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {
            }.getType();
            type_list = gson.fromJson(impact_data, type);
            if (type_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.impacts) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(JSA_Impacts_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JSA_Impacts_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.impacts) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
        } else {
            new Get_Types().execute();
        }

    }

    private class Get_Types extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog
                    .show_progress_dialog(JSA_Impacts_Activity.this, getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEHSHazimp where" +
                        " HazardCode = ?", new String[]{hazardcategory_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Type_Object nto = new Type_Object(cursor.getString(2),
                                    cursor.getString(3), false);
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
                title_textview.setText(getResources().getString(R.string.impacts) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(JSA_Impacts_Activity.this, type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(JSA_Impacts_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.impacts) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }

    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Type_Object> notification_type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, value_textview;
            LinearLayout data_layout;
            CheckBox checkbox;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkbox.isChecked()) {
                            int position = (Integer) v.getTag();
                            type_list.get(position).setSelected_status(true);
                        } else {
                            int position = (Integer) v.getTag();
                            type_list.get(position).setSelected_status(false);
                        }
                    }
                });
            }
        }

        public TYPE_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.notification_type_details_list = list;
        }

        @Override
        public TYPE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.f4_checkbox_list_data, parent, false);
            return new TYPE_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TYPE_ADAPTER.MyViewHolder holder, int position) {
            final Type_Object nto = notification_type_details_list.get(position);
            holder.id_textview.setText(nto.getId());
            holder.value_textview.setText(nto.getText());
            holder.checkbox.setTag(position);
            holder.checkbox.setChecked((notification_type_details_list.get(position)
                    .selected_status == true ? true : false));
        }

        @Override
        public int getItemCount() {
            return notification_type_details_list.size();
        }
    }

    public class Type_Object {
        private String id;
        private String text;
        private Boolean selected_status;

        public Boolean getSelected_status() {
            return selected_status;
        }

        public void setSelected_status(Boolean selected_status) {
            this.selected_status = selected_status;
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

        public Type_Object(String id, String text, boolean selected_status) {
            this.id = id;
            this.text = text;
            this.selected_status = selected_status;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            JSA_Impacts_Activity.this.finish();
        } else if (v == cancel_button) {
            JSA_Impacts_Activity.this.finish();
        } else if (v == submit_button) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < type_list.size(); i++) {
                boolean checked_status = type_list.get(i).getSelected_status();
                if (checked_status) {
                    arrayList.add(type_list.get(i).getId());
                }
            }
            if (arrayList.size() > 0) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Type_Object>>() {
                }.getType();
                String selected_json = gson.toJson(type_list, type);
                Intent intent = new Intent();
                intent.putExtra("impact_data", selected_json);
                setResult(RESULT_OK, intent);
                JSA_Impacts_Activity.this.finish();
            } else {
                error_dialog.show_error_dialog(JSA_Impacts_Activity.this,
                        getString(R.string.jsa_impctatleast));
            }
        }
    }
}
