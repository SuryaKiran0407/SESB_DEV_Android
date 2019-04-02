package com.enstrapp.fieldtekpro.Calibration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.ViewPagerAdapter;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Calibration_Operation_Activity extends AppCompatActivity implements View.OnClickListener {

    String order_id = "", equip_id = "";
    TextView no_data_textview;
    RecyclerView recyclerview;
    private ViewPager viewPager;
    Button cancel_button, send_button;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    LinearLayout no_data_layout;
    RelativeLayout layout;
    int Position;
    ImageView back_imageview;
    private static String DATABASE_NAME = "";
    Data_Adapter data_adapter;
    ArrayList<Orders_Operations_Parcelable> orders_operations_parcables = new ArrayList<Orders_Operations_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> selected_start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
    FragmentTransaction transaction;
    Calibration_Start_Inspection_Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operations_list_activity);

        no_data_textview = findViewById(R.id.no_data_textview1);
        recyclerview = findViewById(R.id.recyclerview);
        no_data_layout = findViewById(R.id.no_data_layout);
        back_imageview = findViewById(R.id.back_imageview);
        layout = findViewById(R.id.layout);
        cancel_button = findViewById(R.id.cancel_button);
        send_button = findViewById(R.id.send_button);

        orders_operations_parcables.clear();
        start_calibration_parcelables.clear();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            order_id = extras.getString("order_id");
            equip_id = extras.getString("equip_id");
            orders_operations_parcables = extras.getParcelableArrayList("operations");
        }

        if (orders_operations_parcables.size() > 0) {
            data_adapter = new Data_Adapter(Calibration_Operation_Activity.this, orders_operations_parcables);
            recyclerview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_Operation_Activity.this);
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(data_adapter);
            no_data_textview.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
        } else {
            no_data_textview.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }

        DATABASE_NAME = getApplicationContext().getString(R.string.database_name);
        App_db = getApplicationContext().openOrCreateDatabase(DATABASE_NAME, getApplicationContext().MODE_PRIVATE, null);

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        send_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            Calibration_Operation_Activity.this.finish();
        } else if (v == cancel_button) {
            Calibration_Operation_Activity.this.finish();
        } else if (v == send_button) {
            Intent intent = new Intent();
            intent.putExtra("orders_operations_parcables", orders_operations_parcables);
            intent.putExtra("equip_id", equip_id);
            setResult(0, intent);
            Calibration_Operation_Activity.this.finish();
        }
    }

    public class Data_Adapter extends RecyclerView.Adapter<Data_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Orders_Operations_Parcelable> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView op_id_textview, op_text_textview, MBEWERTG_textview;
            View status_view;
            LinearLayout layout, layout1;

            public MyViewHolder(View view) {
                super(view);
                op_id_textview = (TextView) view.findViewById(R.id.op_id_textview);
                op_text_textview = (TextView) view.findViewById(R.id.op_text_textview);
                MBEWERTG_textview = (TextView) view.findViewById(R.id.MBEWERTG_textview);
                status_view = (View) view.findViewById(R.id.status_view);
                layout = (LinearLayout) view.findViewById(R.id.layout);
                layout1 = (LinearLayout) view.findViewById(R.id.layout1);
            }
        }

        public Data_Adapter(Context mContext, List<Orders_Operations_Parcelable> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public Data_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calibration_operations_list_data, parent, false);
            return new Data_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Data_Adapter.MyViewHolder holder, final int position) {
            final Orders_Operations_Parcelable olo = list_data.get(position);
            holder.layout1.setVisibility(View.VISIBLE);
            holder.status_view.setVisibility(View.VISIBLE);
            holder.op_id_textview.setText(olo.getOperation_id());
            holder.op_text_textview.setText(olo.getOperations_shorttext());
          /*  holder.MBEWERTG_textview.setText(olo.getStatus());
            if (holder.MBEWERTG_textview.getText().toString().equalsIgnoreCase("R")) {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.red));
            } else if (holder.MBEWERTG_textview.getText().toString().equalsIgnoreCase("A")) {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.dark_green));
            } else {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.red));
            }*/
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Position = position;
                    start_calibration_parcelables = new ArrayList<>();
                    start_calibration_parcelables.addAll(olo.getStart_calibration_parcelables());
                    fragment = new Calibration_Start_Inspection_Fragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.layout, fragment);
                    transaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 0) {
                ArrayList<HashMap<String, String>> start_inspection_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("start_inspection_arraylist");
                if (start_inspection_arraylist.size() > 0) {
                    selected_start_calibration_parcelables.clear();
                    for (int i = 0; i < start_inspection_arraylist.size(); i++) {
                        Start_Calibration_Parcelable start_calibration_parcelable = new Start_Calibration_Parcelable();
                        start_calibration_parcelable.setMerknr(start_inspection_arraylist.get(i).get("Merknr"));
                        start_calibration_parcelable.setPrueflos(start_inspection_arraylist.get(i).get("prueflos"));
                        start_calibration_parcelable.setVorglfnr(start_inspection_arraylist.get(i).get("vorglfnr"));
                        start_calibration_parcelable.setVERWMERKM(start_inspection_arraylist.get(i).get("VERWMERKM"));
                        start_calibration_parcelable.setMSEHI(start_inspection_arraylist.get(i).get("MSEHI"));
                        start_calibration_parcelable.setKURZTEXT(start_inspection_arraylist.get(i).get("KURZTEXT"));
                        start_calibration_parcelable.setQUALITAT(start_inspection_arraylist.get(i).get("QUALITAT"));
                        start_calibration_parcelable.setQUANTITAT(start_inspection_arraylist.get(i).get("QUANTITAT"));
                        start_calibration_parcelable.setRESULT(start_inspection_arraylist.get(i).get("RESULT"));
                        start_calibration_parcelable.setPRUEFBEMKT(start_inspection_arraylist.get(i).get("PRUEFBEMKT"));
                        start_calibration_parcelable.setTOLERANZUB(start_inspection_arraylist.get(i).get("TOLERANZUB"));
                        start_calibration_parcelable.setTOLERANZOB(start_inspection_arraylist.get(i).get("TOLERANZOB"));
                        start_calibration_parcelable.setANZWERTG(start_inspection_arraylist.get(i).get("ANZWERTG"));
                        start_calibration_parcelable.setISTSTPUMF(start_inspection_arraylist.get(i).get("ISTSTPUMF"));
                        start_calibration_parcelable.setMSEHL(start_inspection_arraylist.get(i).get("MSEHL"));
                        start_calibration_parcelable.setAUSWMENGE1(start_inspection_arraylist.get(i).get("AUSWMENGE1"));
                        start_calibration_parcelable.setWERKS(start_inspection_arraylist.get(i).get("WERKS"));
                        start_calibration_parcelable.setPRUEFER(start_inspection_arraylist.get(i).get("PRUEFER"));
                        start_calibration_parcelable.setValuation(start_inspection_arraylist.get(i).get("Valuation"));
                        start_calibration_parcelable.setUuid(start_inspection_arraylist.get(i).get("Uuid"));
                        start_calibration_parcelable.setEQUNR(equip_id);
                        start_calibration_parcelable.setPruefdatuv(start_inspection_arraylist.get(i).get("Pruefdatuv"));
                        start_calibration_parcelable.setPruefdatub(start_inspection_arraylist.get(i).get("Pruefdatub"));
                        start_calibration_parcelable.setPruefzeitv(start_inspection_arraylist.get(i).get("Pruefzeitv"));
                        start_calibration_parcelable.setPruefzeitb(start_inspection_arraylist.get(i).get("Pruefzeitb"));
                        selected_start_calibration_parcelables.add(start_calibration_parcelable);
                    }
                    String data_position = data.getStringExtra("data_position");
                    int pos = Integer.parseInt(data_position);
                    String start_inspection_result = data.getStringExtra("start_inspection_result");
                    orders_operations_parcables.get(pos).setStart_calibration_parcelables(selected_start_calibration_parcelables);
                    orders_operations_parcables.get(pos).setStatus(start_inspection_result);
                    if (orders_operations_parcables.size() > 0) {
                        data_adapter = new Data_Adapter(Calibration_Operation_Activity.this, orders_operations_parcables);
                        recyclerview.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Calibration_Operation_Activity.this);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(data_adapter);
                        no_data_textview.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                    } else {
                        no_data_textview.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    public ArrayList<Start_Calibration_Parcelable> getStart_calibration_parcelables() {
        return start_calibration_parcelables;
    }


    public List<Orders_Operations_Parcelable> getOperationData() {
        return orders_operations_parcables;
    }

    public void removeFragment() {
        if (fragment.isVisible()) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }
}
