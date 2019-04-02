package com.enstrapp.fieldtekpro.Calibration;

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
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Calibration_Operations_Fragment extends Fragment {

    TextView no_data_textview;
    RecyclerView recyclerview;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Data_Adapter data_adapter;
    Equi_adapter equi_adapter;
    ArrayList<Orders_Operations_Parcelable> orders_operations_parcables = new ArrayList<Orders_Operations_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> selected_start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
    ArrayList<Calibration_Orders_Operations_List_Activity.EquiList> equiLists = new ArrayList<Calibration_Orders_Operations_List_Activity.EquiList>();
    ArrayList<Calibration_Orders_Operations_List_Activity.EquiList> equiLists1 = new ArrayList<Calibration_Orders_Operations_List_Activity.EquiList>();
    Calibration_Orders_Operations_List_Activity activity;

    public Calibration_Operations_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calibration_operations_fragment, container, false);

        activity = (Calibration_Orders_Operations_List_Activity) getActivity();

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, getActivity().MODE_PRIVATE, null);

        no_data_textview = rootView.findViewById(R.id.no_data_textview);
        recyclerview = rootView.findViewById(R.id.recyclerview);

        orders_operations_parcables.clear();
        start_calibration_parcelables.clear();

        new Get_Operations_Data().execute();

        return rootView;
    }

    private class Get_Operations_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = null;
                if (activity.yes) {
                    for (Calibration_Orders_Operations_List_Activity.EquiList equi : activity.equi_list) {
                        cursor = App_db.rawQuery("select * from EtQinspData where Aufnr = ? and" +
                                " Equnr = ? group by Vornr", new String[]{activity.order_id, equi.getEqunr()});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    getData(cursor, equi.getEqunr());
                                } while (cursor.moveToNext());
                            }
                        }
                    }
                } else {
                    cursor = App_db.rawQuery("select * from EtQinspData where Aufnr = ? group by Vornr",
                            new String[]{activity.order_id});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                getData(cursor, "");
                            } while (cursor.moveToNext());
                        }
                    }
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (activity.yes) {
                if (activity.equi_list != null && activity.equi_list.size() > 0) {
                    equi_adapter = new Equi_adapter(getActivity(), activity.equi_list);
                    recyclerview.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(equi_adapter);
                    no_data_textview.setVisibility(View.GONE);
                    recyclerview.setVisibility(View.VISIBLE);
                } else {
                    no_data_textview.setVisibility(View.VISIBLE);
                    recyclerview.setVisibility(View.GONE);
                }
            } else {
                if (orders_operations_parcables.size() > 0) {
                    data_adapter = new Data_Adapter(getActivity(), orders_operations_parcables);
                    recyclerview.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
            progressDialog.dismiss_progress_dialog();
        }
    }

    public class Equi_adapter extends RecyclerView.Adapter<Equi_adapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Calibration_Orders_Operations_List_Activity.EquiList> list_data;
        private List<Orders_Operations_Parcelable> list_data1;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView op_id_textview, op_text_textview, MBEWERTG_textview, op_id_tv, op_txt_tv;
            View status_view;
            LinearLayout layout, layout1;

            public MyViewHolder(View view) {
                super(view);
                op_id_textview = view.findViewById(R.id.op_id_textview);
                op_text_textview = view.findViewById(R.id.op_text_textview);
                op_id_tv = view.findViewById(R.id.op_id_tv);
                op_txt_tv = view.findViewById(R.id.op_txt_tv);
                MBEWERTG_textview = view.findViewById(R.id.MBEWERTG_textview);
                status_view = view.findViewById(R.id.status_view);
                layout = view.findViewById(R.id.layout);
                layout1 = view.findViewById(R.id.layout1);
            }
        }

        public Equi_adapter(Context mContext, ArrayList<Calibration_Orders_Operations_List_Activity.EquiList> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public Equi_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calibration_operations_list_data, parent, false);
            return new Equi_adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Equi_adapter.MyViewHolder holder, final int position) {

            final Calibration_Orders_Operations_List_Activity.EquiList olo = list_data.get(position);
            holder.layout1.setVisibility(View.VISIBLE);
            holder.status_view.setVisibility(View.GONE);
            holder.op_id_tv.setText(R.string.equi_id);
            holder.op_txt_tv.setText(R.string.equi_txt);
            holder.op_id_textview.setText(olo.getEqunr());
            holder.op_text_textview.setText(olo.getEqtxt());
            if (holder.MBEWERTG_textview.getText().toString().equalsIgnoreCase("R")) {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.red));
            } else if (holder.MBEWERTG_textview.getText().toString().equalsIgnoreCase("A")) {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.dark_green));
            } else {
                holder.status_view.setBackgroundColor(getResources().getColor(R.color.red));
            }
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Calibration_Operation_Activity.class);
                    intent.putExtra("position", Integer.toString(position));
                    intent.putExtra("order_id", activity.order_id);
                    intent.putExtra("equip_id", olo.getEqunr());
                    intent.putParcelableArrayListExtra("operations",
                            getEquipOperations(orders_operations_parcables, olo.getEqunr()));
                    startActivityForResult(intent, 0);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_data.size();
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
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calibration_operations_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Orders_Operations_Parcelable olo = list_data.get(position);
            holder.layout1.setVisibility(View.VISIBLE);
            holder.status_view.setVisibility(View.VISIBLE);
            holder.op_id_textview.setText(olo.getOperation_id());
            holder.op_text_textview.setText(olo.getOperations_shorttext());
           /* holder.MBEWERTG_textview.setText(olo.getStatus());
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
                    Intent intent = new Intent(getActivity(), Calibration_Start_Inspection_Activity.class);
                    intent.putExtra("start_calib_data", list_data.get(position).getStart_calibration_parcelables());
                    intent.putExtra("position", Integer.toString(position));
                    intent.putExtra("order_id", activity.order_id);
                    startActivityForResult(intent, 0);
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
                if (!activity.yes) {
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
                            start_calibration_parcelable.setEQUNR(activity.equip_id);
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
                            data_adapter = new Data_Adapter(getActivity(), orders_operations_parcables);
                            recyclerview.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
                } else {
                    ArrayList<Orders_Operations_Parcelable> opl = data.getParcelableArrayListExtra("orders_operations_parcables");
                    String equnr = data.getStringExtra("equip_id");
                    if (opl != null && opl.size() > 0) {
                        ArrayList<Orders_Operations_Parcelable> rml = new ArrayList<>();
                        rml.addAll(orders_operations_parcables);
                        for (Orders_Operations_Parcelable rm : rml)
                            if (rm.getEqunr().equals(equnr))
                                orders_operations_parcables.remove(rm);
                        orders_operations_parcables.addAll(opl);
                    }
                }
            }
        }
    }

    public List<Orders_Operations_Parcelable> getOperationData() {
        return orders_operations_parcables;
    }

    private ArrayList<Orders_Operations_Parcelable> getEquipOperations(
            ArrayList<Orders_Operations_Parcelable> operationsParcelables, String equnr) {
        ArrayList<Orders_Operations_Parcelable> opl = new ArrayList<>();
        if (operationsParcelables != null && operationsParcelables.size() > 0) {
            if (equnr != null && !equnr.equals("")) {
                for (Orders_Operations_Parcelable op : operationsParcelables) {
                    if (op.getEqunr().equals(equnr))
                        opl.add(op);
                }
            }
        }
        if (opl.size() > 0)
            return opl;
        else
            return new ArrayList<Orders_Operations_Parcelable>();
    }

    private void getData(Cursor cursor, String equnr) {
        try {
            boolean status = true;
            start_calibration_parcelables = new ArrayList<>();
            try {
                Cursor cursor2 = null;
                if (activity.yes)
                    cursor2 = App_db.rawQuery("select * from EtQinspData where Aufnr = ? and Equnr = ? and Vornr = ?",
                            new String[]{activity.order_id, equnr, cursor.getString(3)});
                else
                    cursor2 = App_db.rawQuery("select * from EtQinspData where Aufnr = ? and Vornr = ?",
                            new String[]{activity.order_id, cursor.getString(3)});
                if (cursor2 != null && cursor2.getCount() > 0) {
                    if (cursor2.moveToFirst()) {
                        do {
                            UUID uniqueKey = UUID.randomUUID();
                            String Valuation = "R";
                            String QUANTITAT = cursor2.getString(8);
                            if (QUANTITAT.equalsIgnoreCase("X")) {
                                String result = cursor2.getString(15);
                                if (result != null && !result.equals("")) {
                                    String fromm = "", too = "";
                                    String from = cursor2.getString(18);
                                    if (from != null && !from.equals("")) {
                                        if (from.contains(",")) {
                                            String result1 = from.replace(",", ".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            too = df.format(dvd);
                                        } else {
                                            Float dvd = Float.parseFloat(from);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            too = df.format(dvd);
                                        }
                                    } else {
                                        too = "";
                                    }

                                    String to = cursor2.getString(17);
                                    if (to != null && !to.equals("")) {
                                        if (to.contains(",")) {
                                            String result1 = to.replace(",", ".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            fromm = df.format(dvd);
                                        } else {
                                            Float dvd = Float.parseFloat(to);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            fromm = df.format(dvd);
                                        }
                                    } else {
                                        fromm = "";
                                    }

                                    if ((too != null && !too.equals("")) &&
                                            (fromm != null && !fromm.equals(""))) {
                                        if (result.contains(",")) {
                                            String result1 = result.replace(",", ".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            String res = df.format(dvd);
                                            Float floatt = Float.parseFloat(res);
                                            Float floatt1 = Float.parseFloat(too);
                                            Float floatt2 = Float.parseFloat(fromm);
                                            if (floatt >= floatt1 && floatt <= floatt2) {
                                                Valuation = "A";
                                            } else {
                                                Valuation = "R";
                                            }
                                        } else {
                                            Float floatt = Float.parseFloat(result);
                                            Float floatt1 = Float.parseFloat(too);
                                            Float floatt2 = Float.parseFloat(fromm);
                                            if (floatt >= floatt1 && floatt <= floatt2) {
                                                Valuation = "A";
                                            } else {
                                                Valuation = "R";
                                            }
                                        }
                                    } else {
                                        Valuation = cursor2.getString(23);
                                        if (Valuation == null || Valuation.equals(""))
                                            Valuation = "R";
                                    }
                                    Start_Calibration_Parcelable start_calibration_parcelable = new Start_Calibration_Parcelable();
                                    start_calibration_parcelable.setMerknr(cursor2.getString(7));
                                    start_calibration_parcelable.setPrueflos(cursor2.getString(2));
                                    start_calibration_parcelable.setQUANTITAT(cursor2.getString(8));
                                    start_calibration_parcelable.setVorglfnr("");
                                    start_calibration_parcelable.setVERWMERKM(cursor2.getString(13));
                                    start_calibration_parcelable.setMSEHI(cursor2.getString(11));
                                    start_calibration_parcelable.setKURZTEXT(cursor2.getString(14));
                                    start_calibration_parcelable.setQUALITAT(cursor2.getString(9));
                                    start_calibration_parcelable.setRESULT(cursor2.getString(15));
                                    start_calibration_parcelable.setPRUEFBEMKT(cursor2.getString(22));
                                    start_calibration_parcelable.setTOLERANZUB(too);
                                    start_calibration_parcelable.setTOLERANZOB(fromm);
                                    start_calibration_parcelable.setANZWERTG(cursor2.getString(31));
                                    start_calibration_parcelable.setISTSTPUMF(cursor2.getString(29));
                                    start_calibration_parcelable.setMSEHL(cursor2.getString(12));
                                    start_calibration_parcelable.setAUSWMENGE1(cursor2.getString(35));
                                    start_calibration_parcelable.setWERKS(cursor2.getString(41));
                                    start_calibration_parcelable.setPRUEFER(cursor2.getString(24));
                                    start_calibration_parcelable.setValuation(Valuation);
                                    start_calibration_parcelable.setUuid(uniqueKey.toString());
                                    start_calibration_parcelable.setEQUNR(cursor2.getString(21));
                                    start_calibration_parcelables.add(start_calibration_parcelable);
                                } else {
                                    Valuation = "R";

                                    String fromm = "", too = "";
                                    String from = cursor2.getString(18);
                                    if (from != null && !from.equals("")) {
                                        if (from.contains(",")) {
                                            String result1 = from.replace(",", ".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            too = df.format(dvd);
                                        } else {
                                            Float dvd = Float.parseFloat(from);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            too = df.format(dvd);
                                        }
                                    } else {
                                        too = "";
                                    }

                                    String to = cursor2.getString(17);
                                    if (to != null && !to.equals("")) {
                                        if (to.contains(",")) {
                                            String result1 = to.replace(",", ".");
                                            Float dvd = Float.parseFloat(result1);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            fromm = df.format(dvd);
                                        } else {
                                            Float dvd = Float.parseFloat(to);
                                            DecimalFormat df = new DecimalFormat("###.##");
                                            fromm = df.format(dvd);
                                        }
                                    } else {
                                        fromm = "";
                                    }

                                    Start_Calibration_Parcelable start_calibration_parcelable = new Start_Calibration_Parcelable();
                                    start_calibration_parcelable.setMerknr(cursor2.getString(7));
                                    start_calibration_parcelable.setPrueflos(cursor2.getString(2));
                                    start_calibration_parcelable.setQUANTITAT(cursor2.getString(8));
                                    start_calibration_parcelable.setVorglfnr("");
                                    start_calibration_parcelable.setVERWMERKM(cursor2.getString(13));
                                    start_calibration_parcelable.setMSEHI(cursor2.getString(11));
                                    start_calibration_parcelable.setKURZTEXT(cursor2.getString(14));
                                    start_calibration_parcelable.setQUALITAT(cursor2.getString(9));
                                    start_calibration_parcelable.setRESULT(cursor2.getString(15));
                                    start_calibration_parcelable.setPRUEFBEMKT(cursor2.getString(22));
                                    start_calibration_parcelable.setTOLERANZUB(too);
                                    start_calibration_parcelable.setTOLERANZOB(fromm);
                                    start_calibration_parcelable.setANZWERTG(cursor2.getString(31));
                                    start_calibration_parcelable.setISTSTPUMF(cursor2.getString(29));
                                    start_calibration_parcelable.setMSEHL(cursor2.getString(12));
                                    start_calibration_parcelable.setAUSWMENGE1(cursor2.getString(35));
                                    start_calibration_parcelable.setWERKS(cursor2.getString(41));
                                    start_calibration_parcelable.setPRUEFER(cursor2.getString(24));
                                    start_calibration_parcelable.setValuation(Valuation);
                                    start_calibration_parcelable.setUuid(uniqueKey.toString());
                                    start_calibration_parcelable.setEQUNR(cursor2.getString(21));
                                    start_calibration_parcelables.add(start_calibration_parcelable);
                                }
                            } else {
                                Valuation = cursor2.getString(23);
                                Start_Calibration_Parcelable start_calibration_parcelable = new Start_Calibration_Parcelable();
                                start_calibration_parcelable.setMerknr(cursor2.getString(7));
                                start_calibration_parcelable.setPrueflos(cursor2.getString(2));
                                start_calibration_parcelable.setQUANTITAT(cursor2.getString(8));
                                start_calibration_parcelable.setVorglfnr("");
                                start_calibration_parcelable.setVERWMERKM(cursor2.getString(13));
                                start_calibration_parcelable.setMSEHI(cursor2.getString(11));
                                start_calibration_parcelable.setKURZTEXT(cursor2.getString(14));
                                start_calibration_parcelable.setQUALITAT(cursor2.getString(9));
                                start_calibration_parcelable.setRESULT(cursor2.getString(15));
                                start_calibration_parcelable.setPRUEFBEMKT(cursor2.getString(22));
                                start_calibration_parcelable.setTOLERANZUB("");
                                start_calibration_parcelable.setTOLERANZOB("");
                                start_calibration_parcelable.setANZWERTG(cursor2.getString(31));
                                start_calibration_parcelable.setISTSTPUMF(cursor2.getString(29));
                                start_calibration_parcelable.setMSEHL(cursor2.getString(12));
                                start_calibration_parcelable.setAUSWMENGE1(cursor2.getString(35));
                                start_calibration_parcelable.setWERKS(cursor2.getString(41));
                                start_calibration_parcelable.setPRUEFER(cursor2.getString(24));
                                start_calibration_parcelable.setValuation(Valuation);
                                start_calibration_parcelable.setUuid(uniqueKey.toString());
                                start_calibration_parcelable.setEQUNR(cursor2.getString(21));
                                start_calibration_parcelables.add(start_calibration_parcelable);
                            }

                            if (!cursor2.getString(20).equals("5"))
                                status = false;
                        }
                        while (cursor2.moveToNext());
                    }
                } else {
                    cursor2.close();
                }
            } catch (Exception e) {
            }

            Orders_Operations_Parcelable orders_operations_parcable = new Orders_Operations_Parcelable();
            orders_operations_parcable.setOperation_id(cursor.getString(3));
            orders_operations_parcable.setOperations_shorttext(cursor.getString(42));
            orders_operations_parcable.setOperations_longtext("");
            orders_operations_parcable.setDuration("");
            orders_operations_parcable.setDuration_unit("");
            orders_operations_parcable.setPlant_id("");
            orders_operations_parcable.setPlant_text("");
            orders_operations_parcable.setWorkcenter_id("");
            orders_operations_parcable.setWorkcenter_text("");
            orders_operations_parcable.setControlkey_id("");
            orders_operations_parcable.setControlkey_text("");
            if (status)
                orders_operations_parcable.setStatus("5");
            else
                orders_operations_parcable.setStatus("1");
            orders_operations_parcable.setAueru("");
            orders_operations_parcable.setUsr01("");
            orders_operations_parcable.setEqunr(cursor.getString(21));
            orders_operations_parcable.setStart_calibration_parcelables(start_calibration_parcelables);
            orders_operations_parcables.add(orders_operations_parcable);
        } catch (Exception e) {

        }
    }
}
