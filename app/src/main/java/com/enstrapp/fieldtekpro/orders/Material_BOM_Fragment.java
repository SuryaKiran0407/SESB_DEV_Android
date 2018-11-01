package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Initialload.BOM;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;

public class Material_BOM_Fragment extends Fragment {

    TextView title_textview, searchview_textview;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String BOMITEM_status = "", bom_id = "";
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    RecyclerView bom_list_recycleview;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private List<Bom_List_Detailview_Object> bom_details_list = new ArrayList<>();
    private BOM_Details_Adapter adapter;
    public SearchView search;
    Dialog decision_dialog;
    Material_Components_Activity ma;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    LinearLayout no_data_layout, bottom_panel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f4_list_fragment, container,
                false);

        ma = (Material_Components_Activity) this.getActivity();

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME,
                Context.MODE_PRIVATE, null);
        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        search = (SearchView) rootView.findViewById(R.id.search);
        bottom_panel = (LinearLayout) rootView.findViewById(R.id.bottom_panel);

        bottom_panel.setVisibility(View.GONE);

        bom_id = ma.equip;
        bom_list_recycleview = rootView.findViewById(R.id.list_recycleview);
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text",
                null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        new Get_BOMS_Detailed_Data().execute();
        return rootView;
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
                if (bom.contains(query) || Component.contains(query) ||
                        Component_test.contains(query) || Quantity.contains(query) ||
                        Unit.contains(query)) {
                    Bom_List_Detailview_Object blo =
                            new Bom_List_Detailview_Object(bom_details_list.get(i).getBom(),
                                    bom_details_list.get(i).getComponent(),
                                    bom_details_list.get(i).getComponent_test(),
                                    bom_details_list.get(i).getQuantity(),
                                    bom_details_list.get(i).getUnit(),
                                    bom_details_list.get(i).gethierarchy());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                adapter = new BOM_Details_Adapter(getActivity(), filteredList);
                bom_list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_layout.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_layout.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    private class Get_BOMS_Detailed_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
            bom_details_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomItem where Bom =?",
                        new String[]{bom_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Bom_List_Detailview_Object blo =
                                    new Bom_List_Detailview_Object(cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4),
                                            cursor.getString(5),
                                            cursor.getString(6));
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            custom_progress_dialog.dismiss_progress_dialog();
            if (bom_details_list.size() > 0) {
//                title_textview.setText(bom_id + " (" + bom_details_list.size() + ")");
                adapter = new BOM_Details_Adapter(getActivity(), bom_details_list);
                bom_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                bom_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_layout.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
//                title_textview.setText(bom_id + " (0)");
                no_data_layout.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    new Get_BOM_CloudSearch_Data().execute();
                } else {
                    //showing network error and navigating to wifi settings.
                    network_connection_dialog.show_network_connection_dialog(getActivity());
                }
            }
        }
    }

    private class Get_BOM_Item extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
            bom_details_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtBomItem where Bom =?",
                        new String[]{bom_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Bom_List_Detailview_Object blo =
                                    new Bom_List_Detailview_Object(cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4),
                                            cursor.getString(5),
                                            cursor.getString(6));
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            custom_progress_dialog.dismiss_progress_dialog();
            if (bom_details_list.size() > 0) {
//                title_textview.setText(bom_id + " (" + bom_details_list.size() + ")");
                adapter = new BOM_Details_Adapter(getActivity(), bom_details_list);
                bom_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                bom_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_layout.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
//                title_textview.setText(bom_id + " (0)");
                no_data_layout.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
        }
    }

    public class BOM_Details_Adapter extends RecyclerView.Adapter<BOM_Details_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Bom_List_Detailview_Object> bom_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView components_textview, components_text_textview, quantity_textview,
                    unit_textview, bom_textview, hierarchy_textview;
            LinearLayout bom_list_data_layout;

            public MyViewHolder(View view) {
                super(view);
                components_textview = (TextView) view.findViewById(R.id.components_textview);
                components_text_textview = view.findViewById(R.id.components_text_textview);
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
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.utilities_bom_list_detailedview_cardview_activity, parent,
                            false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Bom_List_Detailview_Object blo = bom_details_list.get(position);
            holder.components_textview.setText(blo.getComponent());
            holder.components_text_textview.setText(blo.getComponent_test());
            holder.quantity_textview.setText(blo.getQuantity());
            holder.unit_textview.setText(blo.getUnit());
            holder.bom_textview.setText(blo.getBom());
            holder.hierarchy_textview.setText(blo.gethierarchy());

            holder.bom_list_data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("component",
                            holder.components_textview.getText().toString());
                    intent.putExtra("component_txt",
                            holder.components_text_textview.getText().toString());
                    intent.putExtra("plant", ma.iwerk);
                    intent.putExtra("location",
                            getLocation(holder.components_textview.getText().toString()));
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return bom_details_list.size();
        }
    }

    public class Get_BOM_CloudSearch_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                BOMITEM_status = BOM.Get_BOM_Data(getActivity(), "", bom_id);
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
            new Get_BOM_Item().execute();
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

        public Bom_List_Detailview_Object(String Bom, String Component, String Component_test,
                                          String Quantity, String Unit, String hierarchy) {
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

    private String getLocation(String component) {
        String loc = "";
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA where Matnr = ?",
                    new String[]{component});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        loc = cursor.getString(4);
                    }
                    while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return loc;
    }
}
