package com.enstrapp.fieldtekpro.Calibration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Calibration_UDCode_List_Activity extends AppCompatActivity implements View.OnClickListener
{

    public SearchView search;
    TextView searchview_textview,no_data_textview, title_textview;
    RecyclerView list_recycleview;
    SwipeRefreshLayout swiperefreshlayout;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private List<UDCodes_Object> udcodes_list = new ArrayList<>();
    UDCodes_Adapter udcodes_adapter;
    ImageView back_imageview;
    String VAUSWAHLMG = "",WERKS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_list_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            VAUSWAHLMG = extras.getString("VAUSWAHLMG");
            WERKS = extras.getString("WERKS");
        }

        search = (SearchView) findViewById( R.id.search);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        list_recycleview = (RecyclerView)findViewById(R.id.list_recycleview);
        swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        title_textview = (TextView) findViewById(R.id.title_textview);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);

        DATABASE_NAME = this.getString(R.string.database_name);
        App_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);

        int id = search.getContext() .getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);

        back_imageview.setOnClickListener(this);

        new Get_UDCodes_Data().execute();

    }


    private class Get_UDCodes_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Calibration_UDCode_List_Activity.this, getResources().getString(R.string.loading));
            udcodes_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                Cursor cursor = App_db.rawQuery("select * from EtUdecCodes where Auswahlmge = ? and Werks = ?", new String[]{VAUSWAHLMG,WERKS});
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            UDCodes_Object olo = new UDCodes_Object(cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(10));
                            udcodes_list.add(olo);
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
            if (udcodes_list.size() > 0)
            {
                title_textview.setText("UD Codes"+" ("+udcodes_list.size()+")");
                udcodes_adapter = new UDCodes_Adapter(Calibration_UDCode_List_Activity.this, udcodes_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_UDCode_List_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(udcodes_adapter);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            }
            else
            {
                title_textview.setText("UD Codes"+" (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
            progressDialog.dismiss_progress_dialog();
        }
    }


    public class UDCodes_Object
    {
        private String CODE;
        private String KURZTEXT1;
        private String BEWERTUNG;
        private String QKENNZAHL;
        public String getCODE() {
            return CODE;
        }
        public void setCODE(String CODE) {
            this.CODE = CODE;
        }
        public String getKURZTEXT1() {
            return KURZTEXT1;
        }
        public void setKURZTEXT1(String KURZTEXT1) {
            this.KURZTEXT1 = KURZTEXT1;
        }
        public String getBEWERTUNG() {
            return BEWERTUNG;
        }
        public void setBEWERTUNG(String BEWERTUNG) {
            this.BEWERTUNG = BEWERTUNG;
        }
        public String getQKENNZAHL() {
            return QKENNZAHL;
        }
        public void setQKENNZAHL(String QKENNZAHL) {
            this.QKENNZAHL = QKENNZAHL;
        }
        public UDCodes_Object(String CODE, String KURZTEXT1, String BEWERTUNG, String QKENNZAHL)
        {
            this.CODE = CODE;
            this.KURZTEXT1 = KURZTEXT1;
            this.BEWERTUNG = BEWERTUNG;
            this.QKENNZAHL = QKENNZAHL;
        }
    }


    public class UDCodes_Adapter extends RecyclerView.Adapter<UDCodes_Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<UDCodes_Object> list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView id_textview, text_textview, textview1, textview2;
            ImageView status_imageview;
            LinearLayout data_layout;
            public MyViewHolder(View view)
            {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                text_textview = (TextView) view.findViewById(R.id.text_textview);
                textview1 = (TextView) view.findViewById(R.id.textview1);
                textview2 = (TextView) view.findViewById(R.id.textview2);
                status_imageview = (ImageView) view.findViewById(R.id.status_imageview);
                data_layout = (LinearLayout)view.findViewById(R.id.data_layout);
            }
        }
        public UDCodes_Adapter(Context mContext, List<UDCodes_Object> list)
        {
            this.mContext = mContext;
            this.list_data = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position)
        {
            final UDCodes_Object olo = list_data.get(position);
            holder.id_textview.setText(olo.getCODE());
            holder.text_textview.setText(olo.getKURZTEXT1());
            holder.textview1.setText(olo.getBEWERTUNG());
            holder.textview2.setText(olo.getQKENNZAHL());
            holder.data_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent();
                    intent.putExtra("udcode_id",holder.id_textview.getText().toString());
                    intent.putExtra("udcode_text",holder.text_textview.getText().toString());
                    intent.putExtra("BEWERTUNG",holder.textview1.getText().toString());
                    intent.putExtra("QKENNZAHL",holder.textview2.getText().toString());
                    setResult(1,intent);
                    Calibration_UDCode_List_Activity.this.finish();//finishing activity
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return list_data.size();
        }
    }


    @Override
    public void onClick(View v)
    {
        if(v == back_imageview)
        {
            Calibration_UDCode_List_Activity.this.finish();
        }
    }


}
