package com.enstrapp.fieldtekpro.notifications;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Notifications_Status_Display_Activity extends AppCompatActivity {

    String notification_id = "";
    TextView title_textview, no_data_textview;
    ImageView back_imageview;
    RecyclerView recyclerview;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private List<Status_Object> status_list = new ArrayList<>();
    Status_Adapter status_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_status_display_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            notification_id = extras.getString("notification_id");
        }

        DATABASE_NAME = Notifications_Status_Display_Activity.this.getString(R.string.database_name);
        App_db = Notifications_Status_Display_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        title_textview = (TextView) findViewById(R.id.title_textview);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        title_textview.setText(getResources().getString(R.string.status) + " (" + notification_id + ")");

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notifications_Status_Display_Activity.this.finish();
            }
        });

        new Get_Status_Data().execute();
    }

    private class Get_Status_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Notifications_Status_Display_Activity.this,
                    getResources().getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select * from EtNotifStatus where Qmnum = ?",
                        new String[]{notification_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String stats = cursor.getString(11);
                            if (stats.startsWith("I")) {
                                Status_Object olo =
                                        new Status_Object(cursor.getString(11),
                                                cursor.getString(12),
                                                cursor.getString(13),
                                                cursor.getString(14));
                                status_list.add(olo);
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
            if (status_list.size() > 0) {
                status_adapter =
                        new Status_Adapter(Notifications_Status_Display_Activity.this,
                                status_list);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(Notifications_Status_Display_Activity.this);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(status_adapter);
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
            progressDialog.dismiss_progress_dialog();
        }
    }

    public class Status_Object {
        private String STAT;
        private String ACT;
        private String TXT04;
        private String TXT30;

        public String getSTAT() {
            return STAT;
        }

        public void setSTAT(String STAT) {
            this.STAT = STAT;
        }

        public String getACT() {
            return ACT;
        }

        public void setACT(String ACT) {
            this.ACT = ACT;
        }

        public String getTXT04() {
            return TXT04;
        }

        public void setTXT04(String TXT04) {
            this.TXT04 = TXT04;
        }

        public String getTXT30() {
            return TXT30;
        }

        public void setTXT30(String TXT30) {
            this.TXT30 = TXT30;
        }

        public Status_Object(String STAT, String ACT, String TXT04, String TXT30) {
            this.STAT = STAT;
            this.ACT = ACT;
            this.TXT04 = TXT04;
            this.TXT30 = TXT30;
        }
    }

    public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Status_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txt04_textview, txt30_textview, act_textview;
            ImageView status_imageview;

            public MyViewHolder(View view) {
                super(view);
                txt04_textview = (TextView) view.findViewById(R.id.txt04_textview);
                txt30_textview = (TextView) view.findViewById(R.id.txt30_textview);
                act_textview = (TextView) view.findViewById(R.id.act_textview);
                status_imageview = (ImageView) view.findViewById(R.id.status_imageview);
            }
        }

        public Status_Adapter(Context mContext, List<Status_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.status_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Status_Object olo = list_data.get(position);
            holder.txt04_textview.setText(olo.getTXT04());
            holder.txt30_textview.setText(olo.getTXT30());
            holder.act_textview.setText(olo.getACT());
            if (holder.act_textview.getText().toString().equalsIgnoreCase("X")) {
                holder.status_imageview.setImageResource(R.drawable.ic_tickmark_enabled);
            } else {
                holder.status_imageview.setImageResource(R.drawable.ic_tickmark_disabled);
            }
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }
}
