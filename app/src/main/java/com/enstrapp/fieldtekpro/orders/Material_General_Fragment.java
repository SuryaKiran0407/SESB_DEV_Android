package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Material_General_Fragment extends Fragment {

    TextView title_textview, searchview_textview;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String BOMITEM_status = "", bom_id = "", bom_plant = "";
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    RecyclerView bom_list_recycleview;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private List<STOCK_List_Object> stockListObjects = new ArrayList<>();
    private AlbumsAdapter adapter;
    public SearchView search;
    Dialog decision_dialog;
    Material_Components_Activity ma;
    LinearLayout no_data_layout;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f4_list_fragment, container,
                false);
        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        bom_list_recycleview = rootView.findViewById(R.id.list_recycleview);
        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        search = (SearchView) rootView.findViewById(R.id.search);

        ma = (Material_Components_Activity) this.getActivity();
        new Get_STOCK_List_Data().execute();
        return rootView;

    }

    private class Get_STOCK_List_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            stockListObjects.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = null;
            try {
                cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA where Werks = ?;", new String[]{ma.iwerk});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            STOCK_List_Object cp = new STOCK_List_Object(cursor.getString(1),
                                    cursor.getString(3),
                                    cursor.getString(2),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(7));
                            stockListObjects.add(cp);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    if (cursor != null)
                        cursor.close();
                    try {
                        cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA", null);
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    STOCK_List_Object cp = new STOCK_List_Object(cursor.getString(1),
                                            cursor.getString(3),
                                            cursor.getString(2),
                                            cursor.getString(4),
                                            cursor.getString(5),
                                            cursor.getString(7));
                                    stockListObjects.add(cp);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            } finally {
                if (cursor != null)
                    cursor.close();
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
            if (stockListObjects.size() > 0) {
                Collections.sort(stockListObjects, new Comparator<STOCK_List_Object>() {
                    @Override
                    public int compare(STOCK_List_Object rhs, STOCK_List_Object lhs) {
                        return rhs.getMaterial_Id().compareTo(lhs.getMaterial_Id());
                    }
                });
                adapter = new AlbumsAdapter(getActivity(), stockListObjects);
                bom_list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                bom_list_recycleview.setItemAnimator(new DefaultItemAnimator());
                bom_list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_layout.setVisibility(View.GONE);
                bom_list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_layout.setVisibility(View.VISIBLE);
                bom_list_recycleview.setVisibility(View.GONE);
            }
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<STOCK_List_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < stockListObjects.size(); i++) {
                String bom = stockListObjects.get(i).getMaterial_Id().toLowerCase();
                String bom_desc = stockListObjects.get(i).getMaterial_txt().toLowerCase();
                String plant = stockListObjects.get(i).getUnrestricted().toLowerCase();
                if (bom.contains(query) || bom_desc.contains(query) || plant.contains(query)) {
                    STOCK_List_Object blo = new STOCK_List_Object(stockListObjects.get(i).getMaterial_Id(),
                            stockListObjects.get(i).getMaterial_txt(), stockListObjects.get(i).getPlant(),
                            stockListObjects.get(i).getLocation(), stockListObjects.get(i).getUnrestricted()
                            , stockListObjects.get(i).getValu_typ());
                    filteredList.add(blo);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                bom_list_recycleview.setLayoutManager(layoutManager);
                adapter = new AlbumsAdapter(getActivity(), filteredList);
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

    public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
        private Context mContext;
        private List<STOCK_List_Object> bom_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView matrlId_tv, matrlName_tv, unrestricted_tv, plant_tv, location_tv, valuTyp_tv;
            LinearLayout bom_list_data_layout;

            public MyViewHolder(View view) {
                super(view);
                matrlId_tv = (TextView) view.findViewById(R.id.matrlId_tv);
                matrlName_tv = (TextView) view.findViewById(R.id.matrlName_tv);
                unrestricted_tv = (TextView) view.findViewById(R.id.unrestricted_tv);
                plant_tv = (TextView) view.findViewById(R.id.plant_tv);
                location_tv = (TextView) view.findViewById(R.id.location_tv);
                bom_list_data_layout = (LinearLayout) view.findViewById(R.id.bom_list_data_layout);
                valuTyp_tv = view.findViewById(R.id.valuTyp_tv);
            }
        }

        public AlbumsAdapter(Context mContext, List<STOCK_List_Object> bomlist) {
            this.mContext = mContext;
            this.bom_list_data = bomlist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_general_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final STOCK_List_Object blo = bom_list_data.get(position);
            holder.matrlId_tv.setText(blo.getMaterial_Id());
            holder.matrlName_tv.setText(blo.getMaterial_txt());
            holder.unrestricted_tv.setText(blo.getUnrestricted());
            holder.location_tv.setText(blo.getLocation());
            holder.plant_tv.setText(blo.getPlant());
            holder.valuTyp_tv.setText(blo.getValu_typ());

            holder.bom_list_data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("component", holder.matrlId_tv.getText().toString());
                    intent.putExtra("component_txt", holder.matrlName_tv.getText().toString());
                    intent.putExtra("plant", holder.plant_tv.getText().toString());
                    intent.putExtra("location", holder.location_tv.getText().toString());
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return bom_list_data.size();
        }
    }

    public class STOCK_List_Object {
        private String Material_Id;
        private String Material_txt;
        private String Plant;
        private String Location;
        private String Unrestricted;
        private String Valu_typ;

        public STOCK_List_Object() {
        }

        public STOCK_List_Object(String material_Id, String material_txt, String plant, String location, String unrestricted, String valu_typ) {
            Material_Id = material_Id;
            Material_txt = material_txt;
            Plant = plant;
            Location = location;
            Unrestricted = unrestricted;
            Valu_typ = valu_typ;
        }


        public String getValu_typ() {
            return Valu_typ;
        }

        public void setValu_typ(String valu_typ) {
            Valu_typ = valu_typ;
        }
        public String getMaterial_Id() {
            return Material_Id;
        }

        public void setMaterial_Id(String material_Id) {
            Material_Id = material_Id;
        }

        public String getMaterial_txt() {
            return Material_txt;
        }

        public void setMaterial_txt(String material_txt) {
            Material_txt = material_txt;
        }

        public String getPlant() {
            return Plant;
        }

        public void setPlant(String plant) {
            Plant = plant;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getUnrestricted() {
            return Unrestricted;
        }

        public void setUnrestricted(String unrestricted) {
            Unrestricted = unrestricted;
        }
    }

}