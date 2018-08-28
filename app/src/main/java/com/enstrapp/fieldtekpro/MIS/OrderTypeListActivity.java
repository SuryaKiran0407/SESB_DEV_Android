package com.enstrapp.fieldtekpro.MIS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;


public class OrderTypeListActivity extends AppCompatActivity {

    TextView header, no_data, searchview_textview;
    public SearchView search;
    EditText search_edittext;
    RecyclerView listView;
    Mis_Order_Analysis_Data_Adapter mis_order_adapter;
    private static String DATABASE_NAME = "";
    SQLiteDatabase FieldTekPro_db;
    ProgressDialog progressdialog;
    ArrayList<Mis_EtOrdRep_Object> re_o = new ArrayList<Mis_EtOrdRep_Object>();
    ArrayList<Mis_EtOrdRep_Object> rep_object = new ArrayList<Mis_EtOrdRep_Object>();
    ArrayList<Mis_EtOrdRep_Object> re_fo = new ArrayList<Mis_EtOrdRep_Object>();
    ArrayList<Mis_EtOrdRep_Object> re_foa = new ArrayList<Mis_EtOrdRep_Object>();
    ArrayList<Mis_EtOrdArbplTotal_Object> art_fo = new ArrayList<Mis_EtOrdArbplTotal_Object>();
    ArrayList<Mis_EtOrdPlantTotal_Object> pt_o = new ArrayList<Mis_EtOrdPlantTotal_Object>();
    ArrayList<Mis_EtOrdArbplTotal_Object> art_o = new ArrayList<Mis_EtOrdArbplTotal_Object>();
    int c = 1;
    private static int count = 0;

    String swerk = "", heading = "", value = "", phase = "", value1 = "", wrkcnt = "";
    Toolbar toolbar;
    ImageButton back_ib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_ordrtypye_list);
        back_ib = findViewById(R.id.back_ib);

        header = findViewById(R.id.header);
        no_data = findViewById(R.id.no_data_textview);
//        search_edittext = findViewById(R.id.search_edittext);
        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search);
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*mis_order_adapter = new Mis_order_Analysis_Data_Adapter(OrderTypeListActivity.this, rep_object);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(mis_order_adapter);*/

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Intent intent = getIntent();
        swerk = intent.getExtras().getString("iwerk");
        heading = intent.getExtras().getString("heading");
        value = intent.getExtras().getString("value");
        phase = intent.getExtras().getString("phase");
        value1 = intent.getExtras().getString("value1");
        wrkcnt = intent.getExtras().getString("wrkcnt");
        header.setText(heading);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        search.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        Typeface myCustomFont = Typeface.createFromAsset(OrderTypeListActivity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(15);
        getData();
        search.setOnQueryTextListener(listener);
//        search_edittext.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                s = s.toString().toLowerCase();
//                final ArrayList<Mis_EtOrdRep_Object> filtered = new ArrayList<Mis_EtOrdRep_Object>();
//
//                for (Mis_EtOrdRep_Object filter:rep_object) {
//
////                    final String text = filter.getmQmnum_re().toLowerCase();
//                    if (filter.getmQmnum_re().toLowerCase().contains(s)) {
//                        filtered.add(filter);
//                        no_data.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//                    }else if(filter.getmEqunr_re().toLowerCase().contains(s)){
//                        filtered.add(filter);
//                        no_data.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//                    }else if(filter.getmEqktx_re().toLowerCase().contains(s)){
//                        filtered.add(filter);
//                        no_data.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//                    } else if (filter.getmQmtxt_re().toLowerCase().contains(s)) {
//                        filtered.add(filter);
//                        no_data.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//                    }else{
//                        no_data.setVisibility(View.VISIBLE);
//                        listView.setVisibility(View.GONE);
//                    }
//                }
//                listView.setLayoutManager(new LinearLayoutManager(OrderTypeListActivity.this));
//                mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(NotifTypeListActivity.this, filtered);
//                listView.setAdapter(mis_order_adapter);
//                mis_order_adapter.orderDataSetChanged();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    private void getData() {
        progressdialog = new ProgressDialog(OrderTypeListActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(getResources().getString(
                R.string.loading));
        progressdialog.setCancelable(false);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.show();

        rep_object.clear();
        re_o.clear();
        re_fo.clear();
        re_foa.clear();
        /*if (listView != null) {
            listView.setAdapter(null);
        }*/

        ArrayList<Mis_EtOrdTypeTotal_Object> filter = new ArrayList<Mis_EtOrdTypeTotal_Object>();
        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdPlantTotal", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdPlantTotal_Object cp = new Mis_EtOrdPlantTotal_Object();
                        cp.setmTotal_pt(cursor.getString(1));
                        cp.setmSwerk_pt(cursor.getString(2));
                        cp.setmName_pt(cursor.getString(3));
                        cp.setmAuart_pt(cursor.getString(4));
                        cp.setmAuartx_pt(cursor.getString(5));
                        cp.setmOuts_pt(cursor.getString(6));
                        cp.setmInpr_pt(cursor.getString(7));
                        cp.setmComp_pt(cursor.getString(8));
                        cp.setmDele_pt(cursor.getString(9));
                        cp.setmChecked("n");
                        pt_o.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("Order_pie_plant_db", "" + e.getMessage());
            listView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }

        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdArbplTotal", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdArbplTotal_Object cp = new Mis_EtOrdArbplTotal_Object();
                        cp.setmTotal_art(cursor.getString(1));
                        cp.setmSwerk_art(cursor.getString(2));
                        cp.setmArbpl_art(cursor.getString(3));
                        cp.setmName_art(cursor.getString(4));
                        cp.setmAuart_art(cursor.getString(5));
                        cp.setmAuartx_art(cursor.getString(6));
                        cp.setmOuts_art(cursor.getString(7));
                        cp.setmInpr_art(cursor.getString(8));
                        cp.setmComp_art(cursor.getString(9));
                        cp.setmDele_art(cursor.getString(10));
                        cp.setmChecked("n");
                        art_o.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("Order_pie_workcnt_db", "" + e.getMessage());
            listView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }

        try {
            Cursor cursor = FieldTekPro_db.rawQuery("select * from EtOrdRep", null);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Mis_EtOrdRep_Object cp = new Mis_EtOrdRep_Object();
                        cp.setmPhase_re(cursor.getString(1));
                        cp.setmSwerk_re(cursor.getString(2));
                        cp.setmArbpl_re(cursor.getString(3));
                        cp.setmAuart_re(cursor.getString(4));
                        cp.setmAufnr_re(cursor.getString(5));
                        cp.setmQmart_re(cursor.getString(6));
                        cp.setmQmnum_re(cursor.getString(7));
                        cp.setmKtext_re(cursor.getString(8));
                        cp.setmErnam_re(cursor.getString(9));
                        cp.setmEqunr_re(cursor.getString(10));
                        cp.setmEqktx_re(cursor.getString(11));
                        cp.setmPriok_re(cursor.getString(12));
                        cp.setmGstrp_re(cursor.getString(13));
                        cp.setmGltrp_re(cursor.getString(14));
                        cp.setmTplnr_re(cursor.getString(15));
                        cp.setmIdat2_re(cursor.getString(16));


                        re_o.add(cp);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
            Log.v("Order_data_db", "" + e.getMessage());
            progressdialog.dismiss();
        }

        if (re_o.size() == 0) {
            listView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        } else {
            listView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);

            try {
                c = 1;
                for (int i = 0; i < re_o.size(); i++) {
                    if (re_o.get(i).getmAuart_re().equals(value1) && re_o.get(i).getmPhase_re().equals(phase)) {
                        Mis_EtOrdRep_Object re_so = new Mis_EtOrdRep_Object();
                        re_so.setmNo_re(c);
                        re_so.setmPhase_re(re_o.get(i).getmPhase_re());
                        re_so.setmSwerk_re(re_o.get(i).getmSwerk_re());
                        re_so.setmArbpl_re(re_o.get(i).getmArbpl_re());
                        re_so.setmAuart_re(re_o.get(i).getmAuart_re());
                        re_so.setmAufnr_re(re_o.get(i).getmAufnr_re());
                        re_so.setmQmart_re(re_o.get(i).getmQmart_re());
                        re_so.setmQmnum_re(re_o.get(i).getmQmnum_re());
                        re_so.setmKtext_re(re_o.get(i).getmKtext_re());
                        re_so.setmErnam_re(re_o.get(i).getmErnam_re());
                        re_so.setmEqunr_re(re_o.get(i).getmEqunr_re());
                        re_so.setmEqktx_re(re_o.get(i).getmEqktx_re());
                        re_so.setmPriok_re(re_o.get(i).getmPriok_re());
                        re_so.setmGstrp_re(re_o.get(i).getmGstrp_re());
                        re_so.setmGltrp_re(re_o.get(i).getmGltrp_re());
                        re_so.setmTplnr_re(re_o.get(i).getmTplnr_re());
                        re_so.setmIdat2_re(re_o.get(i).getmIdat2_re());
                        rep_object.add(re_so);
                        c++;
                    }
                }

                if (swerk.equals("")) {
                    mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, rep_object);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    listView.setLayoutManager(mLayoutManager);
                    listView.setItemAnimator(new DefaultItemAnimator());
                    listView.setAdapter(mis_order_adapter);
                    /*mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, rep_object);
                    listView.setAdapter(mis_order_adapter);*/
//                    mis_order_adapter.orderDataSetChanged();
                    progressdialog.dismiss();
                } else {

                    c = 1;
                    for (int i = 0; i < rep_object.size(); i++) {
                        if (rep_object.get(i).getmSwerk_re().equals(swerk)) {
                            Mis_EtOrdRep_Object re_swe = new Mis_EtOrdRep_Object();
                            re_swe.setmNo_re(c);
                            re_swe.setmPhase_re(rep_object.get(i).getmPhase_re());
                            re_swe.setmSwerk_re(rep_object.get(i).getmSwerk_re());
                            re_swe.setmArbpl_re(rep_object.get(i).getmArbpl_re());
                            re_swe.setmAuart_re(rep_object.get(i).getmAuart_re());
                            re_swe.setmAufnr_re(rep_object.get(i).getmAufnr_re());
                            re_swe.setmQmart_re(rep_object.get(i).getmQmart_re());
                            re_swe.setmQmnum_re(rep_object.get(i).getmQmnum_re());
                            re_swe.setmKtext_re(rep_object.get(i).getmKtext_re());
                            re_swe.setmErnam_re(rep_object.get(i).getmErnam_re());
                            re_swe.setmEqunr_re(rep_object.get(i).getmEqunr_re());
                            re_swe.setmEqktx_re(rep_object.get(i).getmEqktx_re());
                            re_swe.setmPriok_re(rep_object.get(i).getmPriok_re());
                            re_swe.setmGstrp_re(rep_object.get(i).getmGstrp_re());
                            re_swe.setmGltrp_re(rep_object.get(i).getmGltrp_re());
                            re_swe.setmTplnr_re(rep_object.get(i).getmTplnr_re());
                            re_swe.setmIdat2_re(rep_object.get(i).getmIdat2_re());
                            re_fo.add(re_swe);
                            c++;
                        }
                    }
                    if (wrkcnt.equals("")) {
                        mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, re_fo);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        listView.setLayoutManager(mLayoutManager);
                        listView.setItemAnimator(new DefaultItemAnimator());
                        listView.setAdapter(mis_order_adapter);
                        /*mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, re_fo);
                        listView.setAdapter(mis_order_adapter);*/
//                        mis_order_adapter.orderDataSetChanged();

                        progressdialog.dismiss();
                    } else {
                        c = 1;
                        for (int j = 0; j < re_fo.size(); j++) {
                            if (re_fo.get(j).getmArbpl_re().equals(wrkcnt)) {
                                Mis_EtOrdRep_Object re_swe = new Mis_EtOrdRep_Object();
                                re_swe.setmNo_re(c);
                                re_swe.setmPhase_re(re_fo.get(j).getmPhase_re());
                                re_swe.setmSwerk_re(re_fo.get(j).getmSwerk_re());
                                re_swe.setmArbpl_re(re_fo.get(j).getmArbpl_re());
                                re_swe.setmAuart_re(re_fo.get(j).getmAuart_re());
                                re_swe.setmAufnr_re(re_fo.get(j).getmAufnr_re());
                                re_swe.setmQmart_re(re_fo.get(j).getmQmart_re());
                                re_swe.setmQmnum_re(re_fo.get(j).getmQmnum_re());
                                re_swe.setmKtext_re(re_fo.get(j).getmKtext_re());
                                re_swe.setmErnam_re(re_fo.get(j).getmErnam_re());
                                re_swe.setmEqunr_re(re_fo.get(j).getmEqunr_re());
                                re_swe.setmEqktx_re(re_fo.get(j).getmEqktx_re());
                                re_swe.setmPriok_re(re_fo.get(j).getmPriok_re());
                                re_swe.setmGstrp_re(re_fo.get(j).getmGstrp_re());
                                re_swe.setmGltrp_re(re_fo.get(j).getmGltrp_re());
                                re_swe.setmTplnr_re(re_fo.get(j).getmTplnr_re());
                                re_swe.setmIdat2_re(re_fo.get(j).getmIdat2_re());


                                re_foa.add(re_swe);
                                c++;
                            }
                        }
                        mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, re_foa);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        listView.setLayoutManager(mLayoutManager);
                        listView.setItemAnimator(new DefaultItemAnimator());
                        listView.setAdapter(mis_order_adapter);
                        /*mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, re_foa);
                        listView.setAdapter(mis_order_adapter);*/
//                        mis_order_adapter.orderDataSetChanged();
                        progressdialog.dismiss();
                    }
                }
            } catch (Exception e) {
                Log.v("list_table", "" + e.getMessage());
                progressdialog.dismiss();
            }
        }
    }

    public class Mis_Order_Analysis_Data_Adapter extends RecyclerView.Adapter<Mis_Order_Analysis_Data_Adapter.ViewHolder> {

        Context mContext;
        LayoutInflater inflater;
        private ArrayList<Mis_EtOrdRep_Object> re_ol;
        private ArrayList<Mis_EtOrdRep_Object> re_f;

        public Mis_Order_Analysis_Data_Adapter(Context context, ArrayList<Mis_EtOrdRep_Object> re_op) {
            mContext = context;
            this.re_ol=re_op;
            inflater = LayoutInflater.from(mContext);
            this.re_f = new ArrayList<Mis_EtOrdRep_Object>();
            this.re_f.addAll(re_op);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            protected TextView notif_no;
            protected TextView equip_no;
            protected TextView equpi_desc;
            protected ImageView notif_rep_detail;
            protected LinearLayout notif_list;
            protected TextView index;
            protected TextView f_loc;

            public ViewHolder(View view) {
                super(view);
                index = (TextView) view.findViewById(R.id.index);
                notif_list = (LinearLayout) view.findViewById(R.id.notif_list);
                notif_no = (TextView) view.findViewById(R.id.notif_no);
                equip_no = (TextView) view.findViewById(R.id.equip_no);
                equpi_desc = (TextView) view.findViewById(R.id.equip_desc);
                notif_rep_detail = (ImageView) view.findViewById(R.id.notif_rep_detailedview);
                f_loc=(TextView) view.findViewById(R.id.tplnr);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mis_orderavailability_details_list, parent, false);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.index.setText(re_ol.get(position).getmNo_re() + ") ");
            holder.notif_no.setText(re_ol.get(position).getmAufnr_re());
            holder.f_loc.setText(re_ol.get(position).getmTplnr_re());
            holder.equip_no.setText(re_ol.get(position).getmEqunr_re());
            holder.equpi_desc.setText(re_ol.get(position).getmEqktx_re());
            holder.notif_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderTypeListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("phase", re_ol.get(position).getmPhase_re());
                    intent.putExtra("iwerk", re_ol.get(position).getmSwerk_re());
                    intent.putExtra("Arbpl", re_ol.get(position).getmArbpl_re());
                    intent.putExtra("Auart", re_ol.get(position).getmAuart_re());
                    intent.putExtra("Aufnr", re_ol.get(position).getmAufnr_re());
                    intent.putExtra("Qmart", re_ol.get(position).getmQmart_re());
                    intent.putExtra("Qmnum", re_ol.get(position).getmQmnum_re());
                    intent.putExtra("Ktext", re_ol.get(position).getmKtext_re());
                    intent.putExtra("Ernam", re_ol.get(position).getmErnam_re());
                    intent.putExtra("Equnr", re_ol.get(position).getmEqunr_re());
                    intent.putExtra("Eqktx", re_ol.get(position).getmEqktx_re());
                    intent.putExtra("Priok", re_ol.get(position).getmPriok_re());
                    intent.putExtra("Gstrp", re_ol.get(position).getmGstrp_re());
                    intent.putExtra("Gltrp", re_ol.get(position).getmGltrp_re());
                    intent.putExtra("Tplnr", re_ol.get(position).getmTplnr_re());
                    intent.putExtra("Idat2", re_ol.get(position).getmIdat2_re());

                    intent.putExtra("heading", heading);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return re_ol.size();
        }

        /*@Override
        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.mis_notif_data_list, null);
                holder.index = (TextView) view.findViewById(R.id.index);
                holder.notif_list = (LinearLayout) view.findViewById(R.id.notif_list);
                holder.notif_no = (TextView) view.findViewById(R.id.notif_no);
                holder.equip_no = (TextView) view.findViewById(R.id.equip_no);
                holder.equpi_desc = (TextView) view.findViewById(R.id.equip_desc);
                holder.notif_rep_detail = (ImageView) view.findViewById(R.id.notif_rep_detailedview);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.index.setText(re_ol.get(position).getmNo_re() + ") ");
            holder.notif_no.setText(re_ol.get(position).getmQmnum_re());
            holder.equip_no.setText(re_ol.get(position).getmEqunr_re());
            holder.equpi_desc.setText(re_ol.get(position).getmEqktx_re());
            holder.notif_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Mis_Notif_Analysis_Data.this, Mis_Notif_Analysis_Data_Details.class);
                    intent.putExtra("phase", re_ol.get(position).getmPhase_re());
                    intent.putExtra("iwerk", re_ol.get(position).getmSwerk_re());
                    intent.putExtra("wrkcnt", re_ol.get(position).getmArbpl_re());
                    intent.putExtra("qmart", re_ol.get(position).getmQmart_re());
                    intent.putExtra("qmnum", re_ol.get(position).getmQmnum_re());
                    intent.putExtra("qmtxt", re_ol.get(position).getmQmtxt_re());
                    intent.putExtra("ernam", re_ol.get(position).getmErnam_re());
                    intent.putExtra("equnr", re_ol.get(position).getmEqunr_re());
                    intent.putExtra("eqktx", re_ol.get(position).getmEqktx_re());
                    intent.putExtra("priok", re_ol.get(position).getmPriok_re());
                    Log.v("priok_data",""+re_ol.get(position).getmPriok_re());
                    intent.putExtra("strmn", re_ol.get(position).getmStrmn_re());
                    intent.putExtra("ltrmn", re_ol.get(position).getmLtrmn_re());
                    intent.putExtra("auswk", re_ol.get(position).getmAuswk_re());
                    intent.putExtra("tplnr", re_ol.get(position).getmTplnr_re());
                    intent.putExtra("msaus", re_ol.get(position).getmMsaus_re());
                    intent.putExtra("ausvn", re_ol.get(position).getmAusvn_re());
                    intent.putExtra("ausbs", re_ol.get(position).getmAusbs_re());
                    intent.putExtra("heading", heading);
                    startActivity(intent);
                }
            });

            return view;
        }*/

        /*public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            re_ol.clear();

            if (charText.length() == 0) {
                GetData();
                notifyDataSetChanged();
            } else {
                for (Mis_EtNotifRep_Object wp : re_f) {
                    if (wp.getmEqunr_re().toLowerCase(Locale.getDefault()).toString().contains(charText)) {
                        re_ol.add(wp);
                        no_data.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    } else if (wp.getmEqktx_re().toLowerCase(Locale.getDefault()).toString().contains(charText)) {
                        re_ol.add(wp);
                        no_data.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    } else if (wp.getmQmnum_re().toLowerCase(Locale.getDefault()).toString().contains(charText)) {
                        re_ol.add(wp);
                        no_data.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    } else if (wp.getmQmtxt_re().toLowerCase(Locale.getDefault()).toString().contains(charText)) {
                        re_ol.add(wp);
                        no_data.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                    } else if (re_ol.size() == 0) {
                        no_data.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    }
                }
                notifyDataSetChanged();
            }
        }*/
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final ArrayList<Mis_EtOrdRep_Object> filteredList = new ArrayList<>();
            for (Mis_EtOrdRep_Object filter : rep_object) {
                String Qmnum = filter.getmQmnum_re().toString();
                String Equnr = filter.getmEqunr_re().toString();
                String Eqktx = filter.getmEqktx_re().toString().toLowerCase();
                String Ktext = filter.getmKtext_re().toString().toLowerCase();/*doubt*/
                if (Qmnum.contains(query) || Equnr.contains(query) || Eqktx.contains(query) || Ktext.contains(query)) {
                    filteredList.add(filter);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OrderTypeListActivity.this);
                listView.setLayoutManager(layoutManager);
                mis_order_adapter = new Mis_Order_Analysis_Data_Adapter(OrderTypeListActivity.this, filteredList);
                listView.setAdapter(mis_order_adapter);
                mis_order_adapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.GONE);
            } else {
                no_data.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
