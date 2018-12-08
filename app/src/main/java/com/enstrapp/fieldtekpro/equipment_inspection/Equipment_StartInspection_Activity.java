package com.enstrapp.fieldtekpro.equipment_inspection;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Equipment_StartInspection_Activity extends AppCompatActivity implements View.OnClickListener {

    String equipment_id = "", valuation_type_id = "", valuation_type_text = "", date_formatted = "", time_formatted = "";
    ImageView datetime_imageview, back_imageview;
    EditText datetime_edittext;
    TextView shift_textview, no_data_textview;
    RecyclerView list_recycleview;
    private static String DATABASE_NAME = "";
    SQLiteDatabase FieldTekPro_db;
    SharedPreferences app_sharedpreferences;
    SharedPreferences.Editor app_editor;
    String username = "", webservice_type = "", password = "", device_id = "", device_uuid = "", device_serial_number = "";
    List<INSP_Object> inspdata_list = new ArrayList<>();
    LinearLayout footer_layout;
    Adapter adapter;
    int valuation_type_status = 0, valuation_position = 0, datetime = 1;
    Button cancel_button, submit_button;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    Dialog submit_decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_start_inspection_activity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equipment_id = extras.getString("equipment_id");
        }


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        app_sharedpreferences = Equipment_StartInspection_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        app_editor = app_sharedpreferences.edit();


        username = app_sharedpreferences.getString("Username", null);
        password = app_sharedpreferences.getString("Password", null);
        webservice_type = app_sharedpreferences.getString("webservice_type", null);


        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();
        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */


        datetime_imageview = (ImageView) findViewById(R.id.datetime_imageview);
        datetime_edittext = (EditText) findViewById(R.id.datetime_edittext);
        shift_textview = (TextView) findViewById(R.id.shift_textview);
        no_data_textview = (TextView) findViewById(R.id.no_data_textview);
        list_recycleview = (RecyclerView) findViewById(R.id.list_recycleview);
        footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat time_df = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = time_df.format(c.getTime());
        datetime_edittext.setText(formattedDate + "  -  " + formattedTime);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String formattedDate1 = df1.format(c.getTime());
        date_formatted = formattedDate1;
        SimpleDateFormat time_df1 = new SimpleDateFormat("HHmmss");
        String formattedTime1 = time_df1.format(c.getTime());
        time_formatted = formattedTime1;


        SimpleDateFormat shift_df = new SimpleDateFormat("HH");
        String shiftTime = shift_df.format(c.getTime());
        int time = Integer.parseInt(shiftTime);
        if (time >= 9 && time < 18) {
            shift_textview.setText("Morning Shift");
        } else {
            shift_textview.setText("Night Shift");
        }


        new Get_Inspection_Measurement_Data().execute();


        datetime_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == datetime_imageview) {
            Intent intent = new Intent(Equipment_StartInspection_Activity.this, DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(datetime));
            startActivityForResult(intent, datetime);
        } else if (v == cancel_button) {
            Equipment_StartInspection_Activity.this.finish();
        } else if (v == back_imageview) {
            Equipment_StartInspection_Activity.this.finish();
        } else if (v == submit_button) {
            cd = new ConnectionDetector(getApplicationContext());
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                ArrayList reading_count = new ArrayList();
                ArrayList result_count = new ArrayList();
                for (int i = 0; i < inspdata_list.size(); i++) {
                    String reading = inspdata_list.get(i).getReading();
                    boolean normal_result = inspdata_list.get(i).isNormal();
                    boolean alarm_result = inspdata_list.get(i).isAlarm();
                    boolean critical_result = inspdata_list.get(i).isCritical();
                    if (reading != null && !reading.equals("")) {
                        reading_count.add(reading);
                    }
                    if (normal_result == true || alarm_result == true || critical_result == true) {
                        result_count.add("true");
                    }
                }
                if (reading_count.size() > 0 || result_count.size() > 0) {
                    submit_decision_dialog = new Dialog(Equipment_StartInspection_Activity.this);
                    submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    submit_decision_dialog.setCancelable(false);
                    submit_decision_dialog.setCanceledOnTouchOutside(false);
                    submit_decision_dialog.setContentView(R.layout.decision_dialog);
                    ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
                    Glide.with(Equipment_StartInspection_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
                    TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
                    description_textview.setText(getString(R.string.submit_inspchklist));
                    Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
                    submit_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submit_decision_dialog.dismiss();
                            new Get_Token().execute();
                        }
                    });
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submit_decision_dialog.dismiss();
                        }
                    });
                } else {
                    error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this,
                            getString(R.string.provide_readin));
                }
            } else {
                network_connection_dialog.show_network_connection_dialog(Equipment_StartInspection_Activity.this);
            }
        }
    }


    private class Get_Inspection_Measurement_Data extends AsyncTask<Void, Integer, Void> {
        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_StartInspection_Activity.this, getResources().getString(R.string.loading));
            inspdata_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = null;
                cursor = FieldTekPro_db.rawQuery("select * from EtEquiMptt Where Equnr = ?", new String[]{equipment_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            INSP_Object oo = new INSP_Object(cursor.getString(4), cursor.getString(8), cursor.getString(13), cursor.getString(19), "", "", "", false, false, false, false, "");
                            inspdata_list.add(oo);
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
            if (inspdata_list.size() > 0) {
                adapter = new Adapter(Equipment_StartInspection_Activity.this, inspdata_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Equipment_StartInspection_Activity.this);
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                no_data_textview.setVisibility(View.GONE);
                custom_progress_dialog.dismiss_progress_dialog();
                no_data_textview.setVisibility(View.GONE);
                footer_layout.setVisibility(View.VISIBLE);
            } else {
                list_recycleview.setVisibility(View.GONE);
                no_data_textview.setVisibility(View.VISIBLE);
                footer_layout.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private Context mContext;
        private List<INSP_Object> orders_list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView Codgr_textview, description_textview, inspection_textview;
            Button valuation_button, unit_type_button;
            CheckBox created_after_checkbox, normal_checkbox, alarm_checkbox, critical_checkbox;
            EditText reading_edittext, notes_edittext;

            public MyViewHolder(View view) {
                super(view);
                inspection_textview = (TextView) view.findViewById(R.id.inspection_textview);
                description_textview = (TextView) view.findViewById(R.id.description_textview);
                unit_type_button = (Button) view.findViewById(R.id.unit_type_button);
                created_after_checkbox = (CheckBox) view.findViewById(R.id.created_after_checkbox);
                valuation_button = (Button) view.findViewById(R.id.valuation_button);
                Codgr_textview = (TextView) view.findViewById(R.id.Codgr_textview);
                normal_checkbox = (CheckBox) view.findViewById(R.id.normal_checkbox);
                alarm_checkbox = (CheckBox) view.findViewById(R.id.alarm_checkbox);
                critical_checkbox = (CheckBox) view.findViewById(R.id.critical_checkbox);
                notes_edittext = (EditText) view.findViewById(R.id.notes_edittext);
                reading_edittext = (EditText) view.findViewById(R.id.reading_edittext);
                reading_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        int pos = (int) reading_edittext.getTag();
                        inspdata_list.get(pos).setReading(reading_edittext.getText().toString());
                    }
                });
                notes_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        int pos = (int) notes_edittext.getTag();
                        inspdata_list.get(pos).setNotes(notes_edittext.getText().toString());
                    }
                });
                created_after_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (created_after_checkbox.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setCreated_after_task(true);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setCreated_after_task(false);
                        }
                    }
                });
                normal_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (normal_checkbox.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setNormal(true);
                            inspdata_list.get(getPosition).setAlarm(false);
                            inspdata_list.get(getPosition).setCritical(false);
                            alarm_checkbox.setChecked(false);
                            critical_checkbox.setChecked(false);
                            normal_checkbox.setChecked(true);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setNormal(false);
                        }
                    }
                });
                alarm_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alarm_checkbox.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setNormal(false);
                            inspdata_list.get(getPosition).setAlarm(true);
                            inspdata_list.get(getPosition).setCritical(false);
                            alarm_checkbox.setChecked(true);
                            normal_checkbox.setChecked(false);
                            critical_checkbox.setChecked(false);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setAlarm(false);
                        }
                    }
                });
                critical_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (critical_checkbox.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setNormal(false);
                            inspdata_list.get(getPosition).setAlarm(false);
                            inspdata_list.get(getPosition).setCritical(true);
                            critical_checkbox.setChecked(true);
                            normal_checkbox.setChecked(false);
                            alarm_checkbox.setChecked(false);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            inspdata_list.get(getPosition).setCritical(false);
                        }
                    }
                });
            }
        }

        public Adapter(Context mContext, List<INSP_Object> list) {
            this.mContext = mContext;
            this.orders_list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_startinspection_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final INSP_Object olo = orders_list_data.get(position);
            holder.inspection_textview.setText(olo.getPoint());
            holder.description_textview.setText(olo.getPttxt());
            holder.unit_type_button.setText(olo.getMsehl());
            holder.Codgr_textview.setText(olo.getCodgr());
            holder.reading_edittext.setTag(position);
            holder.reading_edittext.setText(olo.getReading());
            holder.notes_edittext.setTag(position);
            holder.notes_edittext.setText(olo.getNotes());
            holder.valuation_button.setText(olo.getValuation_type_id() + " - " + olo.getValuation_type_text());
            holder.valuation_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    valuation_position = position;
                    String codgr = holder.Codgr_textview.getText().toString();
                    Intent valuation_intent = new Intent(Equipment_StartInspection_Activity.this, Equipment_Inspection_MeasCodes_Types_Activity.class);
                    valuation_intent.putExtra("codgr", codgr);
                    valuation_intent.putExtra("request_id", Integer.toString(valuation_type_status));
                    startActivityForResult(valuation_intent, valuation_type_status);
                }
            });
            holder.created_after_checkbox.setTag(position);
            holder.created_after_checkbox.setChecked((orders_list_data.get(position).isCreated_after_task() == true ? true : false));
            holder.normal_checkbox.setTag(position);
            holder.normal_checkbox.setChecked((orders_list_data.get(position).isNormal() == true ? true : false));
            holder.alarm_checkbox.setTag(position);
            holder.alarm_checkbox.setChecked((orders_list_data.get(position).isAlarm() == true ? true : false));
            holder.critical_checkbox.setTag(position);
            holder.critical_checkbox.setChecked((orders_list_data.get(position).isCritical() == true ? true : false));
        }

        @Override
        public int getItemCount() {
            return orders_list_data.size();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == valuation_type_status) {
                valuation_type_id = data.getStringExtra("valuation_type_id");
                valuation_type_text = data.getStringExtra("valuation_type_text");
                inspdata_list.get(valuation_position).setValuation_type_id(valuation_type_id);
                inspdata_list.get(valuation_position).setValuation_type_text(valuation_type_text);
                /*adapter = new Adapter(Equipment_StartInspection_Activity.this, inspdata_list);
                list_recycleview.setAdapter(adapter);*/
                adapter.notifyDataSetChanged();
            } else if (requestCode == datetime) {
                String date = data.getStringExtra("date");
                date_formatted = data.getStringExtra("date_formatted");
                String time = data.getStringExtra("time");
                time_formatted = data.getStringExtra("time_formatted");
                datetime_edittext.setText(date + "  -  " + time);
            }
        }
    }

    public class INSP_Object {
        private String Point;
        private String Pttxt;
        private String Msehl;
        private String Codgr;
        private String valuation_type_id;
        private String valuation_type_text;
        private String reading;
        private boolean created_after_task;
        private boolean normal;
        private boolean alarm;
        private boolean critical;
        private String notes;

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public boolean isNormal() {
            return normal;
        }

        public void setNormal(boolean normal) {
            this.normal = normal;
        }

        public boolean isAlarm() {
            return alarm;
        }

        public void setAlarm(boolean alarm) {
            this.alarm = alarm;
        }

        public boolean isCritical() {
            return critical;
        }

        public void setCritical(boolean critical) {
            this.critical = critical;
        }

        public boolean isCreated_after_task() {
            return created_after_task;
        }

        public void setCreated_after_task(boolean created_after_task) {
            this.created_after_task = created_after_task;
        }

        public String getReading() {
            return reading;
        }

        public void setReading(String reading) {
            this.reading = reading;
        }

        public String getValuation_type_id() {
            return valuation_type_id;
        }

        public void setValuation_type_id(String valuation_type_id) {
            this.valuation_type_id = valuation_type_id;
        }

        public String getValuation_type_text() {
            return valuation_type_text;
        }

        public void setValuation_type_text(String valuation_type_text) {
            this.valuation_type_text = valuation_type_text;
        }

        public String getCodgr() {
            return Codgr;
        }

        public void setCodgr(String codgr) {
            Codgr = codgr;
        }

        public String getMsehl() {
            return Msehl;
        }

        public void setMsehl(String msehl) {
            Msehl = msehl;
        }

        public String getPttxt() {
            return Pttxt;
        }

        public void setPttxt(String pttxt) {
            Pttxt = pttxt;
        }

        public String getPoint() {
            return Point;
        }

        public void setPoint(String point) {
            Point = point;
        }

        public INSP_Object(String Point, String Pttxt, String Msehl, String Codgr, String valuation_type_id, String valuation_type_text, String reading, boolean created_after_task, boolean normal, boolean alarm, boolean critical, String notes) {
            this.Point = Point;
            this.Pttxt = Pttxt;
            this.Msehl = Msehl;
            this.Codgr = Codgr;
            this.valuation_type_id = valuation_type_id;
            this.valuation_type_text = valuation_type_text;
            this.reading = reading;
            this.created_after_task = created_after_task;
            this.normal = normal;
            this.alarm = alarm;
            this.critical = critical;
            this.notes = notes;
        }
    }


    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_StartInspection_Activity.this, getResources().getString(R.string.insp_chk_submit));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(Equipment_StartInspection_Activity.this);
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
            if (token_status.equalsIgnoreCase("success")) {
                new Post_Inspection_Checklist().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this,
                        getString(R.string.unable_inspchklst));
            }
        }
    }


    private class Post_Inspection_Checklist extends AsyncTask<String, Integer, Void> {
        Map<String, String> post_insp_check_status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                post_insp_check_status = Post_InspectionChecklist.post_inspection_data(Equipment_StartInspection_Activity.this, inspdata_list, equipment_id, date_formatted, time_formatted);
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
            if (post_insp_check_status.get("response_status") != null && !post_insp_check_status.get("response_status").equals("")) {
                if (post_insp_check_status.get("response_status").equalsIgnoreCase("success")) {
                    String response_data = post_insp_check_status.get("response_data");
                    if (response_data.startsWith("S")) {
                        final Dialog success_dialog = new Dialog(Equipment_StartInspection_Activity.this);
                        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        success_dialog.setCancelable(false);
                        success_dialog.setCanceledOnTouchOutside(false);
                        success_dialog.setContentView(R.layout.error_dialog);
                        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                        TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
                        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                        description_textview.setText(response_data.substring(1));
                        Glide.with(Equipment_StartInspection_Activity.this).load(R.drawable.success_checkmark).into(imageview);
                        success_dialog.show();
                        ok_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                success_dialog.dismiss();
                                Equipment_StartInspection_Activity.this.finish();
                            }
                        });
                    } else if (response_data.startsWith("E")) {
                        custom_progress_dialog.dismiss_progress_dialog();
                        error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this, response_data.substring(1).toString());
                    } else {
                        custom_progress_dialog.dismiss_progress_dialog();
                        error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this, response_data);
                    }
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this,
                            getString(R.string.unable_inspchklst));
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Equipment_StartInspection_Activity.this,
                        getString(R.string.unable_inspchklst));
            }
        }
    }


}
