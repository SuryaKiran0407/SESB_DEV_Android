package com.enstrapp.fieldtekpro.Calibration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Calibration_Start_Inspection_Fragment extends Fragment implements View.OnClickListener {

    ImageView back_imageview;
    Button cancel_button, add_button, stdatetime_button, enddatetime_button;
    String selected_data_position = "", order_id = "", equip_id = "", username = "", start_date = "", start_date_formatted = "", start_time = "", end_date = "", end_date_formatted = "", end_time = "";
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Data_Adapter data_adapter;
    RecyclerView recyclerview;
    LinearLayout no_data_layout, date_time_layout, footer_layout;
    private static SharedPreferences app_sharedpreferences;
    private static SharedPreferences.Editor app_editor;
    String vkatart = "";
    Calibration_Operation_Activity activity;
    ArrayList<Start_Calibration_Parcelable> selected_start_calibration_parcelables = new ArrayList<Start_Calibration_Parcelable>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calibration_start_inspection_activity, container, false);

        activity = (Calibration_Operation_Activity) getActivity();
        equip_id = activity.equip_id;
        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        /* Initializing Shared Preferences */
        app_sharedpreferences = getActivity().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        app_editor = app_sharedpreferences.edit();
        username = app_sharedpreferences.getString("Username", null);
        /* Initializing Shared Preferences */

        back_imageview = (ImageView) rootView.findViewById(R.id.back_imageview);
        cancel_button = (Button) rootView.findViewById(R.id.cancel_button);
        add_button = (Button) rootView.findViewById(R.id.add_button);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        no_data_layout = (LinearLayout) rootView.findViewById(R.id.no_data_layout);
        date_time_layout = (LinearLayout) rootView.findViewById(R.id.date_time_layout);
        footer_layout = (LinearLayout) rootView.findViewById(R.id.footer_layout);
        stdatetime_button = (Button) rootView.findViewById(R.id.stdatetime_button);
        enddatetime_button = (Button) rootView.findViewById(R.id.enddatetime_button);

        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        stdatetime_button.setOnClickListener(this);
        enddatetime_button.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");
        start_date = date.format(c.getTime());
        start_time = time.format(c.getTime());
        end_date = date.format(c.getTime());
        end_time = time.format(c.getTime());

        if (activity.start_calibration_parcelables.size() > 0) {
            for (Start_Calibration_Parcelable sCP : activity.start_calibration_parcelables) {
                if (sCP.getPRUEFER() == null || sCP.getPRUEFER().equals(""))
                    sCP.setPRUEFER(username);
                if (sCP.getANZWERTG() == null || sCP.getANZWERTG().equals(""))
                    if (sCP.getISTSTPUMF() != null && !sCP.getISTSTPUMF().equals(""))
                        sCP.setANZWERTG(sCP.getISTSTPUMF());
                if (sCP.getPruefdatuv() != null && !sCP.getPruefdatuv().equals(""))
                    start_date = sCP.getPruefdatuv();
                if (sCP.getPruefzeitv() != null && !sCP.getPruefzeitv().equals(""))
                    start_time = sCP.getPruefzeitv();
                if (sCP.getPruefdatub() != null && !sCP.getPruefdatub().equals(""))
                    end_date = sCP.getPruefdatub();
                if (sCP.getPruefzeitb() != null && !sCP.getPruefzeitb().equals(""))
                    end_time = sCP.getPruefzeitb();
                stdatetime_button.setText(formatDate(start_date) + "\n" + formatTime(start_time));
                enddatetime_button.setText(formatDate(end_date) + "\n" + formatTime(end_time));
            }
            data_adapter = new Data_Adapter(getActivity(), activity.start_calibration_parcelables);
            recyclerview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(data_adapter);
            no_data_layout.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            footer_layout.setVisibility(View.VISIBLE);
            date_time_layout.setVisibility(View.VISIBLE);
            data_adapter.notifyDataSetChanged();
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
            footer_layout.setVisibility(View.GONE);
            date_time_layout.setVisibility(View.GONE);
        }

        try {
            Cursor cursor1 = null;
            cursor1 = App_db.rawQuery("select * from EtQudData Where Aufnr = ?", new String[]{order_id});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        vkatart = cursor1.getString(8);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                vkatart = "";
            }
        } catch (Exception e) {
            vkatart = "";
        }
        if (vkatart != null && !vkatart.equals("")) {
            footer_layout.setVisibility(View.GONE);
            stdatetime_button.setEnabled(false);
            enddatetime_button.setEnabled(false);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            activity.removeFragment();
        } else if (v == cancel_button) {
            activity.removeFragment();
        } else if (v == stdatetime_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(0));
            startActivityForResult(intent, 0);
        } else if (v == enddatetime_button) {
            Intent intent = new Intent(getActivity(), DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(2));
            startActivityForResult(intent, 2);
        } else if (v == add_button) {
            for (int i = 0; i < activity.start_calibration_parcelables.size(); i++) {
                Start_Calibration_Parcelable start_calibration_parcelable = new Start_Calibration_Parcelable();
                start_calibration_parcelable.setMerknr(activity.start_calibration_parcelables.get(i).getMerknr());
                start_calibration_parcelable.setPrueflos(activity.start_calibration_parcelables.get(i).getPrueflos());
                start_calibration_parcelable.setVorglfnr(activity.start_calibration_parcelables.get(i).getVorglfnr());
                start_calibration_parcelable.setVERWMERKM(activity.start_calibration_parcelables.get(i).getVERWMERKM());
                start_calibration_parcelable.setMSEHI(activity.start_calibration_parcelables.get(i).getMSEHI());
                start_calibration_parcelable.setKURZTEXT(activity.start_calibration_parcelables.get(i).getKURZTEXT());
                start_calibration_parcelable.setQUALITAT(activity.start_calibration_parcelables.get(i).getQUALITAT());
                start_calibration_parcelable.setQUANTITAT(activity.start_calibration_parcelables.get(i).getQUANTITAT());
                start_calibration_parcelable.setRESULT(activity.start_calibration_parcelables.get(i).getRESULT());
                start_calibration_parcelable.setPRUEFBEMKT(activity.start_calibration_parcelables.get(i).getPRUEFBEMKT());
                start_calibration_parcelable.setTOLERANZUB(activity.start_calibration_parcelables.get(i).getTOLERANZUB());
                start_calibration_parcelable.setTOLERANZOB(activity.start_calibration_parcelables.get(i).getTOLERANZOB());
                start_calibration_parcelable.setANZWERTG(activity.start_calibration_parcelables.get(i).getANZWERTG());
                start_calibration_parcelable.setISTSTPUMF(activity.start_calibration_parcelables.get(i).getISTSTPUMF());
                start_calibration_parcelable.setMSEHL(activity.start_calibration_parcelables.get(i).getMSEHL());
                start_calibration_parcelable.setAUSWMENGE1(activity.start_calibration_parcelables.get(i).getAUSWMENGE1());
                start_calibration_parcelable.setWERKS(activity.start_calibration_parcelables.get(i).getWERKS());
                start_calibration_parcelable.setPRUEFER(activity.start_calibration_parcelables.get(i).getPRUEFER());
                start_calibration_parcelable.setValuation(activity.start_calibration_parcelables.get(i).getValuation());
                start_calibration_parcelable.setUuid(activity.start_calibration_parcelables.get(i).getUuid());
                start_calibration_parcelable.setEQUNR(equip_id);
                start_calibration_parcelable.setPruefdatuv(start_date);
                start_calibration_parcelable.setPruefdatub(end_date);
                start_calibration_parcelable.setPruefzeitv(start_time);
                start_calibration_parcelable.setPruefzeitb(end_time);
                selected_start_calibration_parcelables.add(start_calibration_parcelable);
            }
            int data_position = activity.Position;
            activity.orders_operations_parcables.get(data_position).setStart_calibration_parcelables(selected_start_calibration_parcelables);
            ArrayList<String> valuation_list = new ArrayList<String>();
            if (valuation_list.size() == activity.start_calibration_parcelables.size()) {
                activity.orders_operations_parcables.get(data_position).setStatus("A");
            } else {
                activity.orders_operations_parcables.get(data_position).setStatus("R");
            }
            activity.removeFragment();
        }
    }

    public class Data_Adapter extends RecyclerView.Adapter<Data_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Start_Calibration_Parcelable> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView uuid_textview, Valuation_textview, Plant_textview, Auswmenge1_textview, range_unit_textview, characteristic_textview1, unit_type_textview, Qualitat_textview, range_textview, inspect_textview;
            public LinearLayout result_layout1, result_layout2, range_layout, layout;
            public Button result2_button;
            public EditText reading_edittext, notes_edittext, inspector_edittext, inspected_edittext;

            public MyViewHolder(View view) {
                super(view);
                characteristic_textview1 = (TextView) view.findViewById(R.id.characteristic_textview1);
                unit_type_textview = (TextView) view.findViewById(R.id.unit_type_textview);
                Qualitat_textview = (TextView) view.findViewById(R.id.Qualitat_textview);
                result_layout1 = (LinearLayout) view.findViewById(R.id.result_layout1);
                result_layout2 = (LinearLayout) view.findViewById(R.id.result_layout2);
                result2_button = (Button) view.findViewById(R.id.result2_button);
                reading_edittext = (EditText) view.findViewById(R.id.reading_edittext);
                range_layout = (LinearLayout) view.findViewById(R.id.range_layout);
                notes_edittext = (EditText) view.findViewById(R.id.notes_edittext);
                range_textview = (TextView) view.findViewById(R.id.range_textview);
                inspector_edittext = (EditText) view.findViewById(R.id.inspector_edittext);
                inspected_edittext = (EditText) view.findViewById(R.id.inspected_edittext);
                inspect_textview = (TextView) view.findViewById(R.id.inspect_textview);
                range_unit_textview = (TextView) view.findViewById(R.id.range_unit_textview);
                Auswmenge1_textview = (TextView) view.findViewById(R.id.Auswmenge1_textview);
                Plant_textview = (TextView) view.findViewById(R.id.Plant_textview);
                Valuation_textview = (TextView) view.findViewById(R.id.Valuation_textview);
                uuid_textview = (TextView) view.findViewById(R.id.uuid_textview);
                layout = (LinearLayout) view.findViewById(R.id.layout);
            }
        }

        public Data_Adapter(Context mContext, List<Start_Calibration_Parcelable> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public Data_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calibration_start_inspection_list_data, parent, false);
            return new Data_Adapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Data_Adapter.MyViewHolder holder, final int position) {
            final Start_Calibration_Parcelable olo = list_data.get(position);
            holder.characteristic_textview1.setText(olo.getVERWMERKM() + "  /  " + olo.getKURZTEXT());
            holder.unit_type_textview.setText(olo.getMSEHI());
            holder.Valuation_textview.setText(olo.getValuation());
            holder.uuid_textview.setText(olo.getUuid());
            holder.Qualitat_textview.setText(olo.getQUALITAT());
            if (holder.Qualitat_textview.getText().toString().equalsIgnoreCase("X")) {
                holder.result_layout2.setVisibility(View.VISIBLE);
                holder.result_layout1.setVisibility(View.GONE);
                holder.result2_button.setText(olo.getRESULT());
                holder.reading_edittext.setText("");
                holder.range_layout.setVisibility(View.GONE);
                if (holder.Valuation_textview.getText().toString().equalsIgnoreCase("A")) {
                    holder.result2_button.setBackgroundColor(getResources().getColor(R.color.dark_green));
                } else {
                    holder.result2_button.setBackgroundColor(getResources().getColor(R.color.red));
                }
            } else {
                holder.result_layout2.setVisibility(View.GONE);
                holder.result_layout1.setVisibility(View.VISIBLE);
                holder.result2_button.setText("");
                holder.reading_edittext.setText(olo.getRESULT());
                holder.range_layout.setVisibility(View.VISIBLE);
                if (holder.Valuation_textview.getText().toString().equalsIgnoreCase("A")) {
                    holder.unit_type_textview.setBackgroundColor(getResources().getColor(R.color.dark_green));
                    holder.reading_edittext.setBackground(getResources().getDrawable(R.drawable.accepted_border));
                } else {
                    holder.unit_type_textview.setBackgroundColor(getResources().getColor(R.color.red));
                    holder.reading_edittext.setBackground(getResources().getDrawable(R.drawable.rejected_border));
                }
            }
            holder.notes_edittext.setText(olo.getPRUEFBEMKT());
            holder.range_textview.setText(olo.getTOLERANZUB() + " - " + olo.getTOLERANZOB() + "  " + olo.getMSEHL());
            String inspector = olo.getPRUEFER();
            if (inspector != null && !inspector.equals("")) {
                holder.inspector_edittext.setText(olo.getPRUEFER());
            } else {
                holder.inspector_edittext.setText(username.toUpperCase());
            }
            holder.inspected_edittext.setText(olo.getANZWERTG());
            holder.inspect_textview.setText(olo.getISTSTPUMF());
            holder.Auswmenge1_textview.setText(olo.getAUSWMENGE1());
            holder.Plant_textview.setText(olo.getWERKS());
            holder.result2_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent result_intent = new Intent(getActivity(), Calibration_Start_Result_Activity.class);
                    result_intent.putExtra("Position", Integer.toString(position));
                    result_intent.putExtra("Auswmenge1", holder.Auswmenge1_textview.getText().toString());
                    result_intent.putExtra("Werk", holder.Plant_textview.getText().toString());
                    startActivityForResult(result_intent, 1);
                }
            });
            holder.inspected_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    activity.start_calibration_parcelables.get(position).setANZWERTG(holder.inspected_edittext.getText().toString());
                }
            });
            holder.inspector_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    activity.start_calibration_parcelables.get(position).setPRUEFER(holder.inspector_edittext.getText().toString());
                }
            });
            holder.notes_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    activity.start_calibration_parcelables.get(position).setPRUEFBEMKT(holder.notes_edittext.getText().toString());
                }
            });
            holder.reading_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    if (holder.Qualitat_textview.getText().toString().equalsIgnoreCase("X")) {
                    } else {
                        String from = olo.getTOLERANZUB();
                        String to = olo.getTOLERANZOB();
                        String entered_value = holder.reading_edittext.getText().toString();
                        if ((entered_value != null && !entered_value.equals(""))
                                && (from != null && !from.equals(""))
                                && (to != null && !to.equals(""))) {
                            Float floatt = Float.parseFloat(entered_value);
                            Float floatt1 = Float.parseFloat(from);
                            Float floatt2 = Float.parseFloat(to);
                            if (floatt >= floatt1 && floatt <= floatt2) {
                                holder.unit_type_textview.setBackgroundColor(getResources().getColor(R.color.dark_green));
                                holder.reading_edittext.setBackground(getResources().getDrawable(R.drawable.accepted_border));
                                activity.start_calibration_parcelables.get(position).setValuation("A");
                                activity.start_calibration_parcelables.get(position).setRESULT(entered_value);
                            } else {
                                holder.unit_type_textview.setBackgroundColor(getResources().getColor(R.color.red));
                                holder.reading_edittext.setBackground(getResources().getDrawable(R.drawable.rejected_border));
                                activity.start_calibration_parcelables.get(position).setValuation("R");
                                activity.start_calibration_parcelables.get(position).setRESULT(entered_value);
                            }
                        } else {
                            holder.unit_type_textview.setBackgroundColor(getResources().getColor(R.color.red));
                            holder.reading_edittext.setBackground(getResources().getDrawable(R.drawable.rejected_border));
                            activity.start_calibration_parcelables.get(position).setValuation("R");
                            activity.start_calibration_parcelables.get(position).setRESULT(entered_value);
                        }
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
            if (vkatart != null && !vkatart.equals("")) {
                holder.result2_button.setEnabled(false);
                holder.reading_edittext.setEnabled(false);
                holder.notes_edittext.setEnabled(false);
                holder.inspector_edittext.setEnabled(false);
                holder.inspected_edittext.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 1) {
                String result_id = data.getStringExtra("result_id");
                String result_text = data.getStringExtra("result_text");
                String result_Bewertung = data.getStringExtra("result_Bewertung");
                String Position = data.getStringExtra("Position");
                int selected_position = Integer.parseInt(Position);
                activity.start_calibration_parcelables.get(selected_position).setRESULT(result_id);
                if (result_Bewertung.equalsIgnoreCase("A")) {
                    activity.start_calibration_parcelables.get(selected_position).setValuation("A");
                } else {
                    activity.start_calibration_parcelables.get(selected_position).setValuation("R");
                }
                data_adapter = new Data_Adapter(getActivity(), activity.start_calibration_parcelables);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(data_adapter);
            } else if (requestCode == 0) {
                start_date = data.getStringExtra("date_formatted");
                start_time = data.getStringExtra("time_formatted");
                stdatetime_button.setText(formatDate(start_date) + "\n" + formatTime(start_time));
            } else if (requestCode == 2) {
                end_date = data.getStringExtra("date_formatted");
                end_time = data.getStringExtra("time_formatted");
                enddatetime_button.setText(formatDate(end_date) + "\n" + formatTime(end_time));
            }
        }
    }

    private String formatDate(String date) {
        String inputPattern = "yyyyMMdd";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date date1 = inputFormat.parse(date);
            return outputFormat.format(date1);
        } catch (Exception e) {
            return "";
        }
    }

    private String formatTime(String time) {
        String inputPattern = "HHmmss";
        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date time1 = inputFormat.parse(time);
            return outputFormat.format(time1);
        } catch (Exception e) {
            return "";
        }
    }
}
