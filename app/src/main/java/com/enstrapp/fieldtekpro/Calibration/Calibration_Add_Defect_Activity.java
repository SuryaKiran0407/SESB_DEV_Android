package com.enstrapp.fieldtekpro.Calibration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.HashMap;

public class Calibration_Add_Defect_Activity extends AppCompatActivity implements View.OnClickListener {

    String order_id = "", Insp_Lot = "", damage_id = "", damage_text = "", damage_code_id = "", damage_code_text = "";
    Button object_part_button, object_part_code_button, defect_grp_button, defect_code_button, cancel_button, submit_button;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Dialog object_part_dialog, object_part_code_dialog, damage_dialog, damage_code_dialog;
    ArrayList<HashMap<String, String>> object_part_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> object_part_code_list = new ArrayList<HashMap<String, String>>();
    OBJECT_PART_ADAPTER object_part_adapter;
    Activity activity;
    private static LayoutInflater inflater = null;
    static ArrayList<HashMap<String, String>> data;
    String object_part_id = "", object_part_text = "", object_part_code_id = "", object_part_code_text = "";
    Error_Dialog error_dialog = new Error_Dialog();
    OBJECT_PART_CODE_ADAPTER object_part_code_adapter;
    ProgressDialog progressdialog;
    ArrayList<HashMap<String, String>> damage_list = new ArrayList<HashMap<String, String>>();
    DAMAGE_ADAPTER damage_adapter;
    ArrayList<HashMap<String, String>> damage_code_list = new ArrayList<HashMap<String, String>>();
    DAMAGE_CODE_ADAPTER damage_code_adapter;
    EditText defect_description_edittext, no_of_defects_edittext;
    ImageView back_imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration_add_defect_activity);

        DATABASE_NAME = getApplicationContext().getString(R.string.database_name);
        App_db = getApplicationContext().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Insp_Lot = extras.getString("Insp_Lot");
            order_id = extras.getString("order_id");
        }

        object_part_button = (Button) findViewById(R.id.object_part_button);
        object_part_code_button = (Button) findViewById(R.id.object_part_code_button);
        defect_grp_button = (Button) findViewById(R.id.defect_grp_button);
        defect_code_button = (Button) findViewById(R.id.defect_code_button);
        no_of_defects_edittext = (EditText) findViewById(R.id.no_of_defects_edittext);
        defect_description_edittext = (EditText) findViewById(R.id.defect_description_edittext);
        submit_button = (Button) findViewById(R.id.submit_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        object_part_button.setOnClickListener(this);
        object_part_code_button.setOnClickListener(this);
        defect_grp_button.setOnClickListener(this);
        defect_code_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            Calibration_Add_Defect_Activity.this.finish();
        } else if (v == cancel_button) {
            Calibration_Add_Defect_Activity.this.finish();
        } else if (v == submit_button) {
            if (object_part_id != null && !object_part_id.equals("")) {
                if (object_part_code_id != null && !object_part_code_id.equals("")) {
                    if (damage_id != null && !damage_id.equals("")) {
                        if (damage_code_id != null && !damage_code_id.equals("")) {
                            Intent intent = new Intent();
                            intent.putExtra("object_part_id", object_part_id);
                            intent.putExtra("object_part_text", object_part_text);
                            intent.putExtra("object_part_code_id", object_part_code_id);
                            intent.putExtra("object_part_code_text", object_part_code_text);
                            intent.putExtra("defect_id", damage_id);
                            intent.putExtra("defect_text", damage_text);
                            intent.putExtra("defect_code_id", damage_code_id);
                            intent.putExtra("defect_code_text", damage_code_text);
                            intent.putExtra("defect_description", defect_description_edittext.getText().toString());
                            intent.putExtra("num_defects", no_of_defects_edittext.getText().toString());
                            setResult(1, intent);
                            Calibration_Add_Defect_Activity.this.finish();//finishing activity
                        } else {
                            error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this,
                                    getString(R.string.pls_slctdfctcod));
                        }
                    } else {
                        error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this,
                                getString(R.string.pls_dfctgrp));
                    }
                } else {
                    error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this,
                            getString(R.string.notifcause_selectobjprtcode));
                }
            } else {
                error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this, getString(R.string.notifcause_selectobjprt));
            }
        } else if (v == object_part_button) {
            object_part_list.clear();
            try {
                Cursor cursor = App_db.rawQuery("select DISTINCT Codegruppe, Kurztext from Get_NOTIFCODES_ObjectCodes", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("Codegruppe", cursor.getString(0));
                            map.put("Kurztext", cursor.getString(1));
                            object_part_list.add(map);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            if (object_part_list.size() > 0) {
                object_part_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Black_NoTitleBar);
                object_part_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                object_part_dialog.setCancelable(true);
                object_part_dialog.setCanceledOnTouchOutside(false);
                object_part_dialog.setContentView(R.layout.listview_activity);
                object_part_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ListView priority_listview = (ListView) object_part_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) object_part_dialog.findViewById(R.id.no_data_textview);
                ImageView back_imageview = (ImageView) object_part_dialog.findViewById(R.id.back_imageview);
                TextView title = (TextView) object_part_dialog.findViewById(R.id.title);
                title.setText(getResources().getString(R.string.obj_part1));
                priority_listview.setVisibility(View.VISIBLE);
                no_data_textview.setVisibility(View.GONE);
                object_part_adapter = new OBJECT_PART_ADAPTER(Calibration_Add_Defect_Activity.this, object_part_list);
                priority_listview.setAdapter(object_part_adapter);
                object_part_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        object_part_dialog.dismiss();
                    }
                });
            } else {
                object_part_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                object_part_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                object_part_dialog.setCancelable(true);
                object_part_dialog.setCanceledOnTouchOutside(false);
                object_part_dialog.setContentView(R.layout.f4_list_activity);
                object_part_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ListView priority_listview = (ListView) object_part_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) object_part_dialog.findViewById(R.id.no_data_textview);
                ImageView back_imageview = (ImageView) object_part_dialog.findViewById(R.id.back_imageview);
                TextView title = (TextView) object_part_dialog.findViewById(R.id.title);
                title.setText(getResources().getString(R.string.obj_part1));
                priority_listview.setVisibility(View.GONE);
                no_data_textview.setVisibility(View.VISIBLE);
                object_part_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        object_part_dialog.dismiss();
                    }
                });
            }
        } else if (v == object_part_code_button) {
            if (object_part_id != null && !object_part_id.equals("")) {
                object_part_code_list.clear();
                try {
                    Cursor cursor = App_db.rawQuery("select * from Get_NOTIFCODES_ObjectCodes where Codegruppe = ?", new String[]{object_part_id});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("Code", cursor.getString(5));
                                map.put("Kurztext1", cursor.getString(6));
                                object_part_code_list.add(map);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                        cursor.close();
                    }
                } catch (Exception e) {
                }
                if (object_part_code_list.size() > 0) {
                    object_part_code_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
                    object_part_code_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    object_part_code_dialog.setCancelable(true);
                    object_part_code_dialog.setCanceledOnTouchOutside(false);
                    object_part_code_dialog.setContentView(R.layout.listview_activity);
                    object_part_code_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                    ListView priority_listview = (ListView) object_part_code_dialog.findViewById(R.id.listview);
                    TextView no_data_textview = (TextView) object_part_code_dialog.findViewById(R.id.no_data_textview);
                    ImageView back_imageview = (ImageView) object_part_code_dialog.findViewById(R.id.back_imageview);
                    TextView title = (TextView) object_part_code_dialog.findViewById(R.id.title);
                    title.setText(getResources().getString(R.string.obj_part_code1));
                    priority_listview.setVisibility(View.VISIBLE);
                    no_data_textview.setVisibility(View.GONE);
                    object_part_code_adapter = new OBJECT_PART_CODE_ADAPTER(Calibration_Add_Defect_Activity.this, object_part_code_list);
                    priority_listview.setAdapter(object_part_code_adapter);//OBJECT_PART_CODE_ADAPTER object_part_code_adapter
                    object_part_code_dialog.show();
                    back_imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            object_part_code_dialog.dismiss();
                        }
                    });
                } else {
                    object_part_code_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
                    object_part_code_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    object_part_code_dialog.setCancelable(true);
                    object_part_code_dialog.setCanceledOnTouchOutside(false);
                    object_part_code_dialog.setContentView(R.layout.listview_activity);
                    object_part_code_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                    ListView priority_listview = (ListView) object_part_code_dialog.findViewById(R.id.listview);
                    TextView no_data_textview = (TextView) object_part_code_dialog.findViewById(R.id.no_data_textview);
                    ImageView back_imageview = (ImageView) object_part_code_dialog.findViewById(R.id.back_imageview);
                    TextView title = (TextView) object_part_code_dialog.findViewById(R.id.title);
                    title.setText(getResources().getString(R.string.obj_part_code1));
                    priority_listview.setVisibility(View.GONE);
                    no_data_textview.setVisibility(View.VISIBLE);
                    object_part_code_dialog.show();
                    back_imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            object_part_code_dialog.dismiss();
                        }
                    });
                }
            } else {
                error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this,
                        getString(R.string.notifcause_selectobjprt));
            }
        } else if (v == defect_grp_button) {
            new Get_Damage_Data().execute();
        } else if (v == defect_code_button) {
            if (damage_id != null && !damage_id.equals("")) {
                new Get_Damage_Code_Data().execute();
            } else {
                error_dialog.show_error_dialog(Calibration_Add_Defect_Activity.this,
                        getString(R.string.pls_dfctgrp));
            }
        }
    }


    public class OBJECT_PART_ADAPTER extends BaseAdapter {
        public OBJECT_PART_ADAPTER(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data = d;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.f4_list_data, null);

            final TextView id_textview = (TextView) vi.findViewById(R.id.id_textview);
            final TextView text_textview = (TextView) vi.findViewById(R.id.text_textview);
            final LinearLayout data_layout = (LinearLayout) vi.findViewById(R.id.data_layout);

            HashMap<String, String> dropdownlist = new HashMap<String, String>();
            dropdownlist = data.get(position);

            id_textview.setText(dropdownlist.get("Codegruppe"));
            text_textview.setText(dropdownlist.get("Kurztext"));

            data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    object_part_dialog.dismiss();
                    object_part_id = id_textview.getText().toString();
                    object_part_text = text_textview.getText().toString();
                    object_part_button.setText(object_part_id + " - " + object_part_text);
                    object_part_code_id = "";
                    object_part_code_text = "";
                    object_part_code_button.setText("");
                }
            });

            return vi;
        }
    }

    public class OBJECT_PART_CODE_ADAPTER extends BaseAdapter {
        public OBJECT_PART_CODE_ADAPTER(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data = d;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.f4_list_data, null);

            final TextView id_textview = (TextView) vi.findViewById(R.id.id_textview);
            final TextView text_textview = (TextView) vi.findViewById(R.id.text_textview);
            final LinearLayout data_layout = (LinearLayout) vi.findViewById(R.id.data_layout);

            HashMap<String, String> dropdownlist = new HashMap<String, String>();
            dropdownlist = data.get(position);

            id_textview.setText(dropdownlist.get("Code"));
            text_textview.setText(dropdownlist.get("Kurztext1"));

            data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    object_part_code_dialog.dismiss();
                    object_part_code_id = id_textview.getText().toString();
                    object_part_code_text = text_textview.getText().toString();
                    object_part_code_button.setText(object_part_code_id + " - " + object_part_code_text);
                }
            });

            return vi;
        }
    }


    private class Get_Damage_Data extends AsyncTask<Void, Integer, Void> {
        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(Calibration_Add_Defect_Activity.this, AlertDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
            damage_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select DISTINCT Codegruppe, Kurztext from Get_NOTIFCODES_ItemCodes", null);
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("Codegruppe", cursor.getString(0));
                            map.put("Kurztext", cursor.getString(1));
                            damage_list.add(map);
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
            if (damage_list.size() > 0) {
                damage_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                damage_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                damage_dialog.setCancelable(true);
                damage_dialog.setCanceledOnTouchOutside(false);
                damage_dialog.setContentView(R.layout.listview_activity);
                damage_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ImageView back_imageview = (ImageView) damage_dialog.findViewById(R.id.back_imageview);
                ListView damage_listview = (ListView) damage_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) damage_dialog.findViewById(R.id.no_data_textview);
                TextView title = (TextView) damage_dialog.findViewById(R.id.title);
                title.setText("Defect Group");
                damage_listview.setVisibility(View.VISIBLE);
                no_data_textview.setVisibility(View.GONE);
                damage_adapter = new DAMAGE_ADAPTER(Calibration_Add_Defect_Activity.this, damage_list);
                damage_listview.setAdapter(damage_adapter);//
                damage_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        damage_dialog.dismiss();
                    }
                });
            } else {
                damage_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                damage_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                damage_dialog.setCancelable(true);
                damage_dialog.setCanceledOnTouchOutside(false);
                damage_dialog.setContentView(R.layout.listview_activity);
                damage_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ImageView back_imageview = (ImageView) damage_dialog.findViewById(R.id.back_imageview);
                ListView damage_listview = (ListView) damage_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) damage_dialog.findViewById(R.id.no_data_textview);
                TextView title = (TextView) damage_dialog.findViewById(R.id.title);
                title.setText("Defect Group");
                damage_listview.setVisibility(View.GONE);
                no_data_textview.setVisibility(View.VISIBLE);
                damage_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        damage_dialog.dismiss();
                    }
                });
            }
            progressdialog.dismiss();
        }
    }

    public class DAMAGE_ADAPTER extends BaseAdapter {
        public DAMAGE_ADAPTER(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data = d;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.f4_list_data, null);

            final TextView id_textview = (TextView) vi.findViewById(R.id.id_textview);
            final TextView text_textview = (TextView) vi.findViewById(R.id.text_textview);
            final LinearLayout data_layout = (LinearLayout) vi.findViewById(R.id.data_layout);

            HashMap<String, String> dropdownlist = new HashMap<String, String>();
            dropdownlist = data.get(position);

            id_textview.setText(dropdownlist.get("Codegruppe"));
            text_textview.setText(dropdownlist.get("Kurztext"));

            data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    damage_id = id_textview.getText().toString();
                    damage_text = text_textview.getText().toString();
                    damage_code_id = "";
                    damage_code_text = "";
                    //defect_code_button.setText("");
                    defect_grp_button.setText(damage_id + " - " + damage_text);
                    damage_dialog.dismiss();
                }
            });

            return vi;
        }
    }


    private class Get_Damage_Code_Data extends AsyncTask<Void, Integer, Void> {
        @SuppressWarnings("deprecation")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdialog = new ProgressDialog(Calibration_Add_Defect_Activity.this, AlertDialog.THEME_HOLO_LIGHT);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressdialog.setMessage(getResources().getString(R.string.loading));
            progressdialog.setCancelable(false);
            progressdialog.setCanceledOnTouchOutside(false);
            progressdialog.show();
            damage_code_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = App_db.rawQuery("select * from Get_NOTIFCODES_ItemCodes where Codegruppe = ?", new String[]{damage_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("Code", cursor.getString(5));
                            map.put("Kurztext1", cursor.getString(6));
                            damage_code_list.add(map);
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
            if (damage_code_list.size() > 0) {
                damage_code_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                damage_code_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                damage_code_dialog.setCancelable(true);
                damage_code_dialog.setCanceledOnTouchOutside(false);
                damage_code_dialog.setContentView(R.layout.listview_activity);
                damage_code_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ImageView back_imageview = (ImageView) damage_code_dialog.findViewById(R.id.back_imageview);
                ListView damagecode_listview = (ListView) damage_code_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) damage_code_dialog.findViewById(R.id.no_data_textview);
                TextView title = (TextView) damage_code_dialog.findViewById(R.id.title);
                title.setText("Defect Code");
                damagecode_listview.setVisibility(View.VISIBLE);
                no_data_textview.setVisibility(View.GONE);
                damage_code_adapter = new DAMAGE_CODE_ADAPTER(Calibration_Add_Defect_Activity.this, damage_code_list);
                damagecode_listview.setAdapter(damage_code_adapter);//
                damage_code_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        damage_code_dialog.dismiss();
                    }
                });
            } else {
                damage_code_dialog = new Dialog(Calibration_Add_Defect_Activity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                damage_code_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                damage_code_dialog.setCancelable(true);
                damage_code_dialog.setCanceledOnTouchOutside(false);
                damage_code_dialog.setContentView(R.layout.listview_activity);
                damage_code_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                ImageView back_imageview = (ImageView) damage_code_dialog.findViewById(R.id.back_imageview);
                ListView damagecode_listview = (ListView) damage_code_dialog.findViewById(R.id.listview);
                TextView no_data_textview = (TextView) damage_code_dialog.findViewById(R.id.no_data_textview);
                TextView title = (TextView) damage_code_dialog.findViewById(R.id.title);
                title.setText("Defect Code");
                damagecode_listview.setVisibility(View.GONE);
                no_data_textview.setVisibility(View.VISIBLE);
                damage_code_dialog.show();
                back_imageview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        damage_code_dialog.dismiss();
                    }
                });
            }
            progressdialog.dismiss();
        }
    }

    public class DAMAGE_CODE_ADAPTER extends BaseAdapter {
        public DAMAGE_CODE_ADAPTER(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data = d;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return data.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.f4_list_data, null);

            final TextView id_textview = (TextView) vi.findViewById(R.id.id_textview);
            final TextView text_textview = (TextView) vi.findViewById(R.id.text_textview);
            final LinearLayout data_layout = (LinearLayout) vi.findViewById(R.id.data_layout);

            HashMap<String, String> dropdownlist = new HashMap<String, String>();
            dropdownlist = data.get(position);

            id_textview.setText(dropdownlist.get("Code"));
            text_textview.setText(dropdownlist.get("Kurztext1"));

            data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    defect_code_button.setText(id_textview.getText().toString() + " - " + text_textview.getText().toString());
                    damage_code_id = id_textview.getText().toString();
                    damage_code_text = text_textview.getText().toString();
                    damage_code_dialog.dismiss();
                }
            });

            return vi;
        }
    }

}
