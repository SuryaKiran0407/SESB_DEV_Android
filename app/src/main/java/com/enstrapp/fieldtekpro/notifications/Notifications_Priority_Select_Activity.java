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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Notifications_Priority_Select_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    SearchView search;
    RecyclerView list_recycleview;
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    TYPE_ADAPTER adapter;
    int request_id = 0;
    Button cancel_button, submit_button;
    StringBuffer filter_sb;
    Error_Dialog error_dialog = new Error_Dialog();
    String filt_ids = "";
    ArrayList<String> filt_array = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_type_select_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filt_ids = extras.getString("filt_ids");
            if (filt_ids != null && !filt_ids.equals("")) {
                String[] ids = filt_ids.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i].toString();
                    if (id != null && !id.equals("")) {
                        filt_array.add(id);
                    }
                }
            }
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
        }

        filter_sb = new StringBuffer();

        title_textview = (TextView) findViewById(R.id.title_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        search = (SearchView) findViewById(R.id.search);
        list_recycleview = (RecyclerView) findViewById(R.id.list_recycleview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        int id = search.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        new Get_Types().execute();
    }


    private class Get_Types extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog
                    .show_progress_dialog(Notifications_Priority_Select_Activity.this,
                            getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from " +
                        "GET_NOTIFICATION_PRIORITY", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String id = cursor.getString(1);
                            if (filt_array.contains(id)) {
                                Type_Object nto = new Type_Object(cursor.getString(1),
                                        cursor.getString(2), true);
                                type_list.add(nto);
                            } else {
                                Type_Object nto = new Type_Object(cursor.getString(1),
                                        cursor.getString(2), false);
                                type_list.add(nto);
                            }
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
                title_textview.setText(getResources().getString(R.string.priority) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(Notifications_Priority_Select_Activity.this,
                        type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(Notifications_Priority_Select_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.priority) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
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
                String id = type_list.get(i).getId().toLowerCase();
                String value = type_list.get(i).getText().toLowerCase();
                if (id.contains(query) || value.contains(query)) {
                    Type_Object nto = new Type_Object(type_list.get(i).getId(),
                            type_list.get(i).getText(),
                            type_list.get(i).isChecked_status());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(Notifications_Priority_Select_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(Notifications_Priority_Select_Activity.this,
                        filteredList);
                title_textview.setText(getResources().getString(R.string.priority) + " (" + type_list.size() + ")");
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(getResources().getString(R.string.priority) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
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
            public TextView id_textview, value_textview;
            CheckBox checkbox;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkbox.isChecked()) {
                            type_details_list.get((Integer) v.getTag()).setChecked_status(true);
                            for (int i = 0; i < type_list.size(); i++) {
                                if (type_details_list
                                        .get((Integer)v.getTag()).getId()
                                        .equals(type_list.get(i).getId())){
                                    type_list.get(i).setChecked_status(true);
                                }
                            }
                        } else {
                            type_details_list.get((Integer) v.getTag()).setChecked_status(false);
                            for (int i = 0; i < type_list.size(); i++) {
                                if (type_details_list
                                        .get((Integer)v.getTag()).getId()
                                        .equals(type_list.get(i).getId())){
                                    type_list.get(i).setChecked_status(false);
                                }
                            }
                        }
                    }
                });
            }
        }

        public TYPE_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public TYPE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_type_select_list_data, parent,
                            false);
            return new TYPE_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final TYPE_ADAPTER.MyViewHolder holder, final int position) {
            final Type_Object nto = type_details_list.get(position);
            holder.id_textview.setText(nto.getId());
            holder.value_textview.setText(nto.getText());
           /* holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkbox.isChecked()) {
                        type_details_list.get(position).setChecked_status(true);
                    } else {
                        type_details_list.get(position).setChecked_status(false);
                    }
                }
            });*/
            holder.checkbox.setTag(position);
            holder.checkbox.setChecked((type_details_list.get(position)
                    .isChecked_status() == true ? true : false));
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public class Type_Object {
        private String id;
        private String text;
        private boolean checked_status;

        public Type_Object(String id, String text, boolean checked_status) {
            this.id = id;
            this.text = text;
            this.checked_status = checked_status;
        }

        public boolean isChecked_status() {
            return checked_status;
        }

        public void setChecked_status(boolean checked_status) {
            this.checked_status = checked_status;
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
    public void onClick(View v) {
        if (v == back_imageview) {
            Notifications_Priority_Select_Activity.this.finish();
        } else if (v == cancel_button) {
            Notifications_Priority_Select_Activity.this.finish();
        } else if (v == submit_button) {
            ArrayList<String> selected_array = new ArrayList<String>();
            for (Type_Object bean : type_list) {
                if (bean.isChecked_status()) {
                    filter_sb.append(bean.getId());
                    filter_sb.append(",");
                    selected_array.add(bean.getId());
                }
            }
            if (selected_array.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("ids", filter_sb.toString());
                setResult(request_id, intent);
                Notifications_Priority_Select_Activity.this.finish();
            } else {
                error_dialog.show_error_dialog(Notifications_Priority_Select_Activity.this,
                        getString(R.string.atleastone_priority));
            }
        }
    }
}
