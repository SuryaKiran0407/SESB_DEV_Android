package com.enstrapp.fieldtekpro.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.BOM;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class BOM_List_DetailedView_Activity extends AppCompatActivity {

    TextView title_textview, no_data_bomlist_textview, searchview_textview;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String BOMITEM_status = "", bom_id = "", bom_plant = "";
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    RecyclerView bom_list_recycleview;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private List<Bom_List_Detailview_Object> bom_details_list = new ArrayList<>();
    private BOM_Details_Adapter adapter;
    SwipeRefreshLayout swiperefreshlayout;
    public SearchView search;
    Dialog decision_dialog;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    FloatingActionButton refresh_fab_button;
    ImageView back_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bom_id = extras.getString("bom_id");
            bom_plant = extras.getString("bom_plant");
        }

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getApplicationContext().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        title_textview = (TextView) findViewById(R.id.title_textview);
        bom_list_recycleview = (RecyclerView) findViewById(R.id.history_list_recycleview);
        no_data_bomlist_textview = (TextView) findViewById(R.id.no_data_textview);
        swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        search = (SearchView) findViewById(R.id.search);
        refresh_fab_button = (FloatingActionButton) findViewById(R.id.refresh_fab_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(BOM_List_DetailedView_Activity.this.getAssets(), "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        new Get_BOMS_Detailed_Data().execute();

        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh_BOM_Data();
            }
        });

        swiperefreshlayout.setColorSchemeResources(R.color.red, R.color.lime, R.color.colorAccent, R.color.red, R.color.blue, R.color.black, R.color.orange);

        refresh_fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh_BOM_Data();
            }
        });

        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BOM_List_DetailedView_Activity.this.finish();
            }
        });

    }


    public void Refresh_BOM_Data() {
        cd = new ConnectionDetector(BOM_List_DetailedView_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            decision_dialog = new Dialog(BOM_List_DetailedView_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Glide.with(BOM_List_DetailedView_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
            description_textview.setText(getString(R.string.refresh_text));
            decision_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                    swiperefreshlayout.setRefreshing(false);
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                    new Get_BOM_CloudSearch_Data().execute();
                }
            });
        } else {
            //showing network error and navigating to wifi settings.
            swiperefreshlayout.setRefreshing(false);
            network_connection_dialog.show_network_connection_dialog(BOM_List_DetailedView_Activity.this);
        }
    }


    private class Get_BOMS_Detailed_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(BOM_List_DetailedView_Activity.this, getResources().getString(R.string.loading));
            bom_details_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomItem where Bom =?", new String[]{bom_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Bom_List_Detailview_Object blo = new Bom_List_Detailview_Object(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                            bom_details_list.add(blo);
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
            if (bom_details_list.size() > 0) {
                title_textview.setText(bom_id + " (" + bom_details_list.size() + ")");
                adapter = new BOM_Details_Adapter(BOM_List_DetailedView_Activity.this, bom_details_list);
                bom_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BOM_List_DetailedView_Activity.this);
                bom_list_recycleview.setLayoutManager(layoutManager);
                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                bom_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_bomlist_textview.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                title_textview.setText(bom_id + " (0)");
                no_data_bomlist_textview.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
            swiperefreshlayout.setRefreshing(false);
        }
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<Bom_List_Detailview_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < bom_details_list.size(); i++) {
                String bom = bom_details_list.get(i).getBom().toLowerCase();
                String Component = bom_details_list.get(i).getComponent().toLowerCase();
                String Component_test = bom_details_list.get(i).getComponent_test().toLowerCase();
                String Quantity = bom_details_list.get(i).getQuantity().toLowerCase();
                String Unit = bom_details_list.get(i).getUnit().toLowerCase();
                if (bom.contains(query) || Component.contains(query) || Component_test.contains(query) || Quantity.contains(query) || Unit.contains(query)) {
                    Bom_List_Detailview_Object blo = new Bom_List_Detailview_Object(bom_details_list.get(i).getBom().toString(), bom_details_list.get(i).getComponent().toString(), bom_details_list.get(i).getComponent_test().toString(), bom_details_list.get(i).getQuantity().toString(), bom_details_list.get(i).getUnit().toString(), bom_details_list.get(i).gethierarchy().toString());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BOM_List_DetailedView_Activity.this);
                bom_list_recycleview.setLayoutManager(layoutManager);
                adapter = new BOM_Details_Adapter(BOM_List_DetailedView_Activity.this, filteredList);
                bom_list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_bomlist_textview.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_bomlist_textview.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    public class BOM_Details_Adapter extends RecyclerView.Adapter<BOM_Details_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Bom_List_Detailview_Object> bom_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView components_textview, components_text_textview, quantity_textview, unit_textview, bom_textview, hierarchy_textview;
            LinearLayout bom_list_data_layout;

            public MyViewHolder(View view) {
                super(view);
                components_textview = (TextView) view.findViewById(R.id.components_textview);
                components_text_textview = (TextView) view.findViewById(R.id.components_text_textview);
                quantity_textview = (TextView) view.findViewById(R.id.quantity_textview);
                unit_textview = (TextView) view.findViewById(R.id.unit_textview);
                bom_textview = (TextView) view.findViewById(R.id.bom_textview);
                hierarchy_textview = (TextView) view.findViewById(R.id.hierarchy_textview);
                bom_list_data_layout = (LinearLayout) view.findViewById(R.id.bom_list_data_layout);
            }
        }

        public BOM_Details_Adapter(Context mContext, List<Bom_List_Detailview_Object> bomlist) {
            this.mContext = mContext;
            this.bom_details_list = bomlist;
        }

        @Override
        public BOM_List_DetailedView_Activity.BOM_Details_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utilities_bom_list_detailedview_cardview_activity, parent, false);
            return new BOM_List_DetailedView_Activity.BOM_Details_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final BOM_List_DetailedView_Activity.BOM_Details_Adapter.MyViewHolder holder, int position) {
            final Bom_List_Detailview_Object blo = bom_details_list.get(position);
            holder.components_textview.setText(blo.getComponent());
            holder.components_text_textview.setText(blo.getComponent_test());
            holder.quantity_textview.setText(blo.getQuantity());
            holder.unit_textview.setText(blo.getUnit());
            holder.bom_textview.setText(blo.getBom());
            holder.hierarchy_textview.setText(blo.gethierarchy());

            /*if(holder.hierarchy_textview.getText().toString().equalsIgnoreCase("X"))
            {
                holder.bom_list_data_layout.setBackgroundResource(R.drawable.border_fail);
                holder.components_textview.setTextColor(getResources().getColor(R.color.red));
            }
            else
            {
                holder.components_textview.setTextColor(getResources().getColor(R.color.black));
            }*/

            holder.bom_list_data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(holder.hierarchy_textview.getText().toString().equalsIgnoreCase("X"))
                    {
                        *//*Intent intent = new Intent(BOM_List_DetailedView_Activity.this, BOM_List_DetailedView_Hirarchy_Activity.class);
                        intent.putExtra("component_id",holder.components_textview.getText().toString());
                        intent.putExtra("bom_plant",bom_plant);
                        startActivity(intent);*//*
                    }
                    else
                    {
                        *//*Intent bom_reservation_intent = new Intent(BOM_List_DetailedView_Activity.this, BOM_Reservation_Activity.class);
                        bom_reservation_intent.putExtra("BOM", holder.bom.getText().toString());
                        bom_reservation_intent.putExtra("Components", holder.components.getText().toString());
                        bom_reservation_intent.putExtra("Components_text", holder.components_text.getText().toString());
                        bom_reservation_intent.putExtra("Quantity", holder.quantity.getText().toString());
                        bom_reservation_intent.putExtra("Unit", holder.unit.getText().toString());
                        bom_reservation_intent.putExtra("Plant", bom_plant);
                        startActivity(bom_reservation_intent);*//*
                    }*/
                    Intent bom_reservation_intent = new Intent(BOM_List_DetailedView_Activity.this, BOM_Reservation_Activity.class);
                    bom_reservation_intent.putExtra("BOM", bom_id);
                    bom_reservation_intent.putExtra("Components", holder.components_textview.getText().toString());
                    bom_reservation_intent.putExtra("Components_text", holder.components_text_textview.getText().toString());
                    bom_reservation_intent.putExtra("Quantity", holder.quantity_textview.getText().toString());
                    bom_reservation_intent.putExtra("Unit", holder.unit_textview.getText().toString());
                    bom_reservation_intent.putExtra("Plant", bom_plant);
                    startActivity(bom_reservation_intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return bom_details_list.size();
        }
    }


    @Override
    public void onBackPressed() {
        BOM_List_DetailedView_Activity.this.finish();
    }


    public class Get_BOM_CloudSearch_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(BOM_List_DetailedView_Activity.this,
                    getString(R.string.referesh_bom, bom_id));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                BOMITEM_status = BOM.Get_BOM_Data(BOM_List_DetailedView_Activity.this, "", bom_id);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            search.setQuery("", false);
            search.clearFocus();
            custom_progress_dialog.dismiss_progress_dialog();
            new Get_BOMS_Detailed_Data().execute();
        }
    }


    public class Bom_List_Detailview_Object {
        private String Bom;
        private String Component;
        private String Component_test;
        private String Quantity;
        private String Unit;
        private String hierarchy;

        public Bom_List_Detailview_Object() {
        }

        public Bom_List_Detailview_Object(String Bom, String Component, String Component_test, String Quantity, String Unit, String hierarchy) {
            this.Bom = Bom;
            this.Component = Component;
            this.Component_test = Component_test;
            this.Quantity = Quantity;
            this.Unit = Unit;
            this.hierarchy = hierarchy;
        }

        public String getBom() {
            return Bom;
        }

        public void setBom(String Bom) {
            this.Bom = Bom;
        }

        public String getComponent() {
            return Component;
        }

        public void setComponent(String Component) {
            this.Component = Component;
        }

        public String getComponent_test() {
            return Component_test;
        }

        public void setComponent_test(String Component_test) {
            this.Component_test = Component_test;
        }

        public String getQuantity() {
            return Quantity;
        }

        public void setQuantity(String Quantity) {
            this.Quantity = Quantity;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String gethierarchy() {
            return hierarchy;
        }

        public void sethierarchy(String hierarchy) {
            this.hierarchy = hierarchy;
        }

    }


}
