package com.enstrapp.fieldtekpro.notifications;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v4.app.FragmentActivity;

import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Notifications_Activity_Add_Activity extends FragmentActivity implements View.OnClickListener {

    EditText text_edittext, event_edittext, objectpart_edittext;
    String activity_itemkey = "", status = "I", position = "", enddate_time_formatted = "",
            enddate_time = "", enddate_date_formated = "", enddate_date = "",
            stdate_time_formatted = "", stdate_time = "", stdate_date_formated = "",
            stdate_date = "", code_id = "", code_text = "", codegroup_text = "", codegroup_id = "",
            cause_text = "", cause_itemkey = "", functionlocation_id = "", equipment_id = "",
            catelog_profile = "";
    int request_id = 0;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Error_Dialog error_dialog = new Error_Dialog();
    int itemkey_type = 1, codegroup_type = 2, code_type = 3,
            start_datetime = 4, end_datetime = 5;
    Button cancel_button, add_button;
    ImageView back_imageview;
    ArrayList<HashMap<String, String>> causecode_array_list = new ArrayList<HashMap<String, String>>();
    Button end_datetime_button,start_datetime_button,code_button,codegroup_button,itemkey_button;
    ArrayList<HashMap<String, String>> selected_activity_custom_info_arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity_add_activity);

        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        objectpart_edittext = (EditText) findViewById(R.id.objectpart_edittext);
        event_edittext = (EditText) findViewById(R.id.event_edittext);
        text_edittext = (EditText) findViewById(R.id.text_edittext);
        itemkey_button = (Button) findViewById(R.id.itemkey_button);
        codegroup_button = (Button) findViewById(R.id.codegroup_button);
        code_button = (Button) findViewById(R.id.code_button);
        start_datetime_button = (Button) findViewById(R.id.start_datetime_button);
        end_datetime_button = (Button) findViewById(R.id.end_datetime_button);

        selected_activity_custom_info_arraylist.clear();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            functionlocation_id = extras.getString("functionlocation_id");
            equipment_id = extras.getString("equipment_id");
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals(""))
            {
                request_id = Integer.parseInt(request_ids);
            }
            causecode_array_list = (ArrayList<HashMap<String, String>>) getIntent()
                    .getSerializableExtra("causecode_array_list");
            position = extras.getString("position");
            if (position != null && !position.equals(""))
            {
                activity_itemkey = extras.getString("activity_itemkey");
                add_button.setText("Update");
                status = extras.getString("status");
                cause_itemkey = extras.getString("cause_itemkey");
                cause_text = extras.getString("cause_text");
                itemkey_button.setText(cause_itemkey + " - " + cause_text);
                itemkey_button.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
                itemkey_button.setEnabled(false);
                objectpart_edittext.setText(extras.getString("objectpartcode_id"));
                event_edittext.setText(extras.getString("event_id"));
                codegroup_id = extras.getString("codegroup_id");
                codegroup_text = extras.getString("codegroup_text");
                codegroup_button.setText(codegroup_id + " - " + codegroup_text);
                code_id = extras.getString("code_id");
                code_text = extras.getString("code_text");
                code_button.setText(code_id + " - " + code_text);
                text_edittext.setText(extras.getString("shttext"));

                stdate_date_formated = extras.getString("stdate_date");
                if (stdate_date_formated != null && !stdate_date_formated.equals("")) {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(stdate_date_formated);
                        stdate_date = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }

                enddate_date_formated = extras.getString("enddate_date");
                if (enddate_date_formated != null && !enddate_date_formated.equals("")) {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(enddate_date_formated);
                        enddate_date = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }

                stdate_time_formatted = extras.getString("stdate_time");
                if (stdate_time_formatted != null && !stdate_time_formatted.equals("")) {
                    try {
                        String inputPattern = "HHmmss";
                        String outputPattern = "HH:mm:ss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(stdate_time_formatted);
                        stdate_time = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }

                enddate_time_formatted = extras.getString("enddate_time");
                if (enddate_time_formatted != null && !enddate_time_formatted.equals("")) {
                    try {
                        String inputPattern = "HHmmss";
                        String outputPattern = "HH:mm:ss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(enddate_time_formatted);
                        enddate_time = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }

                start_datetime_button.setText(getString(R.string.hypen_text, stdate_date,
                        stdate_time));
                end_datetime_button.setText(getString(R.string.hypen_text, enddate_date,
                        enddate_time));
            }
            else
            {
                try
                {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                    String formattedDate = df.format(c.getTime());
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                    String formattedDate_format = df1.format(c.getTime());
                    SimpleDateFormat time_df = new SimpleDateFormat("HH:mm:ss");
                    String formattedTime = time_df.format(c.getTime());
                    SimpleDateFormat time_df1 = new SimpleDateFormat("HHmmss");
                    String formattedTime_format = time_df1.format(c.getTime());
                    start_datetime_button.setText(formattedDate + "  -  " + formattedTime);
                    stdate_date = formattedDate;
                    stdate_date_formated = formattedDate_format;
                    stdate_time = formattedTime;
                    stdate_time_formatted = formattedTime_format;

                    c.add(Calendar.DATE, 1);
                    String formattedDate_new = df.format(c.getTime());
                    String formattedDate_format_new = df1.format(c.getTime());
                    enddate_date = formattedDate_new;
                    enddate_date_formated = formattedDate_format_new;
                    enddate_time = formattedTime;
                    enddate_time_formatted = formattedTime_format;
                    end_datetime_button.setText(enddate_date + "  -  " + formattedTime);
                }
                catch (Exception e)
                {
                }
            }
        }
        else
        {
            try
            {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                String formattedDate = df.format(c.getTime());
                SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                String formattedDate_format = df1.format(c.getTime());
                SimpleDateFormat time_df = new SimpleDateFormat("HH:mm:ss");
                String formattedTime = time_df.format(c.getTime());
                SimpleDateFormat time_df1 = new SimpleDateFormat("HHmmss");
                String formattedTime_format = time_df1.format(c.getTime());
                start_datetime_button.setText(formattedDate + "  -  " + formattedTime);
                stdate_date = formattedDate;
                stdate_date_formated = formattedDate_format;
                stdate_time = formattedTime;
                stdate_time_formatted = formattedTime_format;

                c.add(Calendar.DATE, 1);
                String formattedDate_new = df.format(c.getTime());
                String formattedDate_format_new = df1.format(c.getTime());
                enddate_date = formattedDate_new;
                enddate_date_formated = formattedDate_format_new;
                enddate_time = formattedTime;
                enddate_time_formatted = formattedTime_format;
                end_datetime_button.setText(enddate_date + "  -  " + formattedTime);
            }
            catch (Exception e)
            {
            }
        }

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        try
        {
            if (equipment_id != null && !equipment_id.equals("")) {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where " +
                        "Equnr = ?", new String[]{equipment_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            catelog_profile = cursor.getString(6);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } else if (functionlocation_id != null && !functionlocation_id.equals("")) {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where " +
                        "Tplnr = ?", new String[]{functionlocation_id});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            catelog_profile = cursor.getString(10);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } else {
                catelog_profile = "";
            }
        } catch (Exception e) {
        }

        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        itemkey_button.setOnClickListener(this);
        codegroup_button.setOnClickListener(this);
        code_button.setOnClickListener(this);
        start_datetime_button.setOnClickListener(this);
        end_datetime_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == add_button) {
            if (cause_itemkey != null && !cause_itemkey.equals("")) {
                if (codegroup_id != null && !codegroup_id.equals("")) {
                    if (code_id != null && !code_id.equals("")) {
                        Intent intent = new Intent();
                        intent.putExtra("cause_itemkey", cause_itemkey);
                        intent.putExtra("cause_text", cause_text);
                        intent.putExtra("objectpartcode_id",
                                objectpart_edittext.getText().toString());
                        intent.putExtra("event_id", event_edittext.getText().toString());
                        intent.putExtra("codegroup_id", codegroup_id);
                        intent.putExtra("codegroup_text", codegroup_text);
                        intent.putExtra("code_id", code_id);
                        intent.putExtra("code_text", code_text);
                        intent.putExtra("shttext", text_edittext.getText().toString());
                        intent.putExtra("start_date", stdate_date);
                        intent.putExtra("start_date_formatted", stdate_date_formated);
                        intent.putExtra("start_time", stdate_time);
                        intent.putExtra("start_time_formatted", stdate_time_formatted);
                        intent.putExtra("end_date", enddate_date);
                        intent.putExtra("end_date_formatted", enddate_date_formated);
                        intent.putExtra("end_time", enddate_time);
                        intent.putExtra("end_time_formatted", enddate_time_formatted);
                        intent.putExtra("position", position);
                        intent.putExtra("status", status);
                        intent.putExtra("activity_itemkey", activity_itemkey);
                        intent.putExtra("selected_activity_custom_info_arraylist",
                                selected_activity_custom_info_arraylist);
                        setResult(request_id, intent);
                        Notifications_Activity_Add_Activity.this.finish();
                    } else {
                        error_dialog.show_error_dialog(Notifications_Activity_Add_Activity.this,
                                getString(R.string.notifactivity_selectcode));
                    }
                } else {
                    error_dialog.show_error_dialog(Notifications_Activity_Add_Activity.this,
                            getString(R.string.notifactivity_selectcodegrp));
                }
            } else {
                error_dialog.show_error_dialog(Notifications_Activity_Add_Activity.this,
                        getString(R.string.notifactivity_selectitemkey));
            }
        } else if (v == back_imageview) {
            Notifications_Activity_Add_Activity.this.finish();
        } else if (v == cancel_button) {
            Notifications_Activity_Add_Activity.this.finish();
        } else if (v == itemkey_button) {
            Intent itemkey_intent = new Intent(Notifications_Activity_Add_Activity.this,
                    Notifications_Activity_ItemKey_Activity.class);
            itemkey_intent.putExtra("causecode_array_list", causecode_array_list);
            startActivityForResult(itemkey_intent, itemkey_type);
        } else if (v == codegroup_button) {
            Intent objectpart_intent = new Intent(Notifications_Activity_Add_Activity.this,
                    Notifications_Activity_Codegroup_Activity.class);
            objectpart_intent.putExtra("catelog_profile", catelog_profile);
            objectpart_intent.putExtra("request_id", Integer.toString(codegroup_type));
            startActivityForResult(objectpart_intent, codegroup_type);
        } else if (v == code_button) {
            if (codegroup_id != null && !codegroup_id.equals("")) {
                Intent objectpart_intent = new Intent(Notifications_Activity_Add_Activity.this,
                        Notifications_Activity_Code_Activity.class);
                objectpart_intent.putExtra("codegroup_id", codegroup_id);
                objectpart_intent.putExtra("catelog_profile", catelog_profile);
                objectpart_intent.putExtra("request_id", Integer.toString(code_type));
                startActivityForResult(objectpart_intent, code_type);
            } else {
                error_dialog.show_error_dialog(Notifications_Activity_Add_Activity.this,
                        getString(R.string.notifactivity_selectcodegrp));
            }
        } else if (v == start_datetime_button) {
            Intent intent = new Intent(Notifications_Activity_Add_Activity.this,
                    DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(start_datetime));
            startActivityForResult(intent, start_datetime);
        } else if (v == end_datetime_button) {
            Intent intent = new Intent(Notifications_Activity_Add_Activity.this,
                    DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(end_datetime));
            startActivityForResult(intent, end_datetime);
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == itemkey_type) {
                cause_itemkey = data.getStringExtra("itemkey");
                cause_text = data.getStringExtra("text");
                itemkey_button.setText(cause_itemkey + " - " + cause_text);
                objectpart_edittext.setText(data.getStringExtra("objpart_id"));
                event_edittext.setText(data.getStringExtra("event_id"));
            } else if (requestCode == codegroup_type) {
                codegroup_id = data.getStringExtra("codegroup_id");
                codegroup_text = data.getStringExtra("codegroup_text");
                codegroup_button.setText(codegroup_id + " - " + codegroup_text);
                code_id = "";
                code_text = "";
                code_button.setText("");
            } else if (requestCode == code_type) {
                code_id = data.getStringExtra("code_id");
                code_text = data.getStringExtra("code_text");
                code_button.setText(code_id + " - " + code_text);
            } else if (requestCode == start_datetime) {
                stdate_date = data.getStringExtra("date");
                stdate_date_formated = data.getStringExtra("date_formatted");
                stdate_time = data.getStringExtra("time");
                stdate_time_formatted = data.getStringExtra("time_formatted");
                start_datetime_button.setText(stdate_date + "  -  " + stdate_time);
            } else if (requestCode == end_datetime) {
                enddate_date = data.getStringExtra("date");
                enddate_date_formated = data.getStringExtra("date_formatted");
                enddate_time = data.getStringExtra("time");
                enddate_time_formatted = data.getStringExtra("time_formatted");
                end_datetime_button.setText(enddate_date + "  -  " + enddate_time);
            }
        }
    }
}
