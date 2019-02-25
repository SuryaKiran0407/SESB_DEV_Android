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

    String order_id = "";
    TextView no_data_textview;
    RecyclerView recyclerview;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Data_Adapter data_adapter;
    ArrayList<Orders_Operations_Parcelable> orders_operations_parcables = new ArrayList<Orders_Operations_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
    ArrayList<Start_Calibration_Parcelable> selected_start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();

    public Calibration_Operations_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calibration_operations_fragment, container, false);

        Calibration_Orders_Operations_List_Activity activity = (Calibration_Orders_Operations_List_Activity) getActivity();
        order_id = activity.getorder_id();

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, getActivity().MODE_PRIVATE, null);

        no_data_textview = (TextView) rootView.findViewById(R.id.no_data_textview);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);

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
                Cursor cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where Aufnr = ?" + "ORDER BY id ASC", new String[]{order_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            String operation_id = cursor.getString(3);

                            int total_count = 0;
                            ArrayList<String> list = new ArrayList<String>();
                            try {
                                Cursor cursor1 = App_db.rawQuery("select * from EtQinspData where Aufnr = ? and Vornr = ?", new String[]{order_id, operation_id});
                                total_count = cursor1.getCount();
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            String Mbewertg = cursor1.getString(23);
                                            if (Mbewertg.equals("R")) {
                                            } else if (Mbewertg.equals("A")) {
                                                list.add(Mbewertg);
                                            } else {
                                            }
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    cursor1.close();
                                }
                            } catch (Exception e) {
                            }

                            StringBuilder stringbuilder1 = new StringBuilder();
                            try {
                                Cursor cursor11 = App_db.rawQuery("select * from DUE_ORDERS_Longtext where Aufnr = ? and Activity = ?", new String[]{order_id, operation_id});
                                if (cursor11 != null && cursor11.getCount() > 0) {
                                    if (cursor11.moveToFirst()) {
                                        do {
                                            stringbuilder1.append(cursor11.getString(4));
                                            stringbuilder1.append(System.getProperty("line.separator"));
                                        }
                                        while (cursor11.moveToNext());
                                    }
                                } else {
                                    cursor11.close();
                                }
                            } catch (Exception e) {
                            }


                            start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();
                            try {
                                Cursor cursor2 = App_db.rawQuery("select * from EtQinspData where Aufnr = ? and Vornr = ?", new String[]{order_id, operation_id});
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
                                                    start_calibration_parcelables.add(start_calibration_parcelable);
                                                }
                                            } else {
                                                /*String result = cursor2.getString(15);
                                                String Auswmenge1 = cursor2.getString(35);
                                                String Plant = cursor2.getString(41);
                                                try
                                                {
                                                    Cursor cursor1 = null;
                                                    cursor1 = App_db.rawQuery("select * from EtInspCodes Where Werks = ? AND Auswahlmge = ?", new String[]{Plant, Auswmenge1});
                                                    if (cursor1 != null && cursor1.getCount() > 0)
                                                    {
                                                        if (cursor1.moveToFirst())
                                                        {
                                                            do
                                                            {
                                                                String value = cursor1.getString(8);
                                                                if(value.equalsIgnoreCase("A"))
                                                                {
                                                                    Valuation = "A";
                                                                }
                                                                else
                                                                {
                                                                    Valuation = "";
                                                                }
                                                            }
                                                            while (cursor1.moveToNext());
                                                        }
                                                    }
                                                    else
                                                    {
                                                        cursor1.close();
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                }*/
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
                                                start_calibration_parcelables.add(start_calibration_parcelable);
                                            }
                                        }
                                        while (cursor2.moveToNext());
                                    }
                                } else {
                                    cursor2.close();
                                }
                            } catch (Exception e) {
                            }

                            if (total_count == list.size()) {
                                /*Operation_Object olo = new Operation_Object(cursor.getString(3), cursor.getString(5),stringbuilder1.toString(), cursor.getString(10), cursor.getString(11),cursor.getString(7) ,cursor.getString(22),cursor.getString(6), cursor.getString(21), cursor.getString(8), cursor.getString(23), "A", cursor.getString(20), cursor.getString(25));
                                operation_list.add(olo);*/
                                Orders_Operations_Parcelable orders_operations_parcable = new Orders_Operations_Parcelable();
                                orders_operations_parcable.setOperation_id(cursor.getString(3));
                                orders_operations_parcable.setOperations_shorttext(cursor.getString(5));
                                orders_operations_parcable.setOperations_longtext(stringbuilder1.toString());
                                orders_operations_parcable.setDuration(cursor.getString(10));
                                orders_operations_parcable.setDuration_unit(cursor.getString(11));
                                orders_operations_parcable.setPlant_id(cursor.getString(7));
                                orders_operations_parcable.setPlant_text(cursor.getString(22));
                                orders_operations_parcable.setWorkcenter_id(cursor.getString(6));
                                orders_operations_parcable.setWorkcenter_text(cursor.getString(21));
                                orders_operations_parcable.setControlkey_id(cursor.getString(8));
                                orders_operations_parcable.setControlkey_text(cursor.getString(23));
                                orders_operations_parcable.setStatus("A");
                                orders_operations_parcable.setAueru(cursor.getString(20));
                                orders_operations_parcable.setUsr01(cursor.getString(25));
                                orders_operations_parcable.setStart_calibration_parcelables(start_calibration_parcelables);
                                orders_operations_parcables.add(orders_operations_parcable);
                            } else {
                                /*Operation_Object olo = new Operation_Object(cursor.getString(3), cursor.getString(5),stringbuilder1.toString(), cursor.getString(10), cursor.getString(11),cursor.getString(7) ,cursor.getString(22),cursor.getString(6), cursor.getString(21), cursor.getString(8), cursor.getString(23), "R", cursor.getString(20), cursor.getString(25));
                                operation_list.add(olo);*/
                                Orders_Operations_Parcelable orders_operations_parcable = new Orders_Operations_Parcelable();
                                orders_operations_parcable.setOperation_id(cursor.getString(3));
                                orders_operations_parcable.setOperations_shorttext(cursor.getString(5));
                                orders_operations_parcable.setOperations_longtext(stringbuilder1.toString());
                                orders_operations_parcable.setDuration(cursor.getString(10));
                                orders_operations_parcable.setDuration_unit(cursor.getString(11));
                                orders_operations_parcable.setPlant_id(cursor.getString(7));
                                orders_operations_parcable.setPlant_text(cursor.getString(22));
                                orders_operations_parcable.setWorkcenter_id(cursor.getString(6));
                                orders_operations_parcable.setWorkcenter_text(cursor.getString(21));
                                orders_operations_parcable.setControlkey_id(cursor.getString(8));
                                orders_operations_parcable.setControlkey_text(cursor.getString(23));
                                orders_operations_parcable.setStatus("R");
                                orders_operations_parcable.setAueru(cursor.getString(20));
                                orders_operations_parcable.setUsr01(cursor.getString(25));
                                orders_operations_parcable.setStart_calibration_parcelables(start_calibration_parcelables);
                                orders_operations_parcables.add(orders_operations_parcable);
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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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
            progressDialog.dismiss_progress_dialog();
        }
    }


    public class Data_Adapter extends RecyclerView.Adapter<Data_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Orders_Operations_Parcelable> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView op_id_textview, op_text_textview, MBEWERTG_textview;
            View status_view;
            LinearLayout layout;

            public MyViewHolder(View view) {
                super(view);
                op_id_textview = (TextView) view.findViewById(R.id.op_id_textview);
                op_text_textview = (TextView) view.findViewById(R.id.op_text_textview);
                MBEWERTG_textview = (TextView) view.findViewById(R.id.MBEWERTG_textview);
                status_view = (View) view.findViewById(R.id.status_view);
                layout = (LinearLayout) view.findViewById(R.id.layout);
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
            holder.op_id_textview.setText(olo.getOperation_id());
            holder.op_text_textview.setText(olo.getOperations_shorttext());
            holder.MBEWERTG_textview.setText(olo.getStatus());
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
                    Intent intent = new Intent(getActivity(), Calibration_Start_Inspection_Activity.class);
                    intent.putExtra("start_calib_data", list_data.get(position).getStart_calibration_parcelables());
                    intent.putExtra("position", Integer.toString(position));
                    intent.putExtra("order_id", order_id);
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
            }
        }
    }


    public List<Orders_Operations_Parcelable> getOperationData() {
        return orders_operations_parcables;
    }

}
