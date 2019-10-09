package com.enstrapp.fieldtekpro.notifications;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.DateTime.DateTimePickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Notifications_Tasks_Add_Activity extends FragmentActivity implements View.OnClickListener {

    int request_id = 0;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Error_Dialog error_dialog = new Error_Dialog();
    ImageView back_imageview;
    EditText completedby_edittext,
            task_text_edittext, responsible_edittext;
    int taskcodegroup = 1, taskcode = 2, taskprocessor = 3,
            planned_st_datetime = 4, planned_end_datetime = 5;
    String completion_time_formatted = "", completion_time = "", completion_date_formatted = "",
            completion_date = "", planned_end_time_formatted = "", planned_end_time = "",
            planned_end_date_formatted = "", planned_end_date = "", planned_st_time_formatted = "",
            planned_st_time = "", planned_st_date_formatted = "", planned_st_date = "",
            taskcodegroup_id = "", taskcodegroup_text = "", taskcode_id = "", taskcode_text = "",
            taskprocessor_id = "", taskprocessor_text = "";
    RadioGroup status_radiogroup;
    RadioButton release_radiobutton, completed_radiobutton, success_radiobutton;
    Button cancel_button, add_button;
    String completedby = "", completed_status = "", success_status = "", release_status = "",
            position = "", itemkey = "", causekey = "", status = "I", task_text = "",
            task_responsible = "";
    Button completion_datetime_button,end_datetime_button,start_datetime_button,tasks_processor_button,tasks_code_button,tasks_codegroup_button;
    ArrayList<HashMap<String, String>> selected_tasks_custom_info_arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_tasks_add_activity);

        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        task_text_edittext = (EditText) findViewById(R.id.task_text_edittext);
        responsible_edittext = (EditText) findViewById(R.id.responsible_edittext);
        status_radiogroup = (RadioGroup) findViewById(R.id.status_radiogroup);
        release_radiobutton = (RadioButton) findViewById(R.id.release_radiobutton);
        completed_radiobutton = (RadioButton) findViewById(R.id.completed_radiobutton);
        success_radiobutton = (RadioButton) findViewById(R.id.success_radiobutton);
        completedby_edittext = (EditText) findViewById(R.id.completedby_edittext);
        add_button = (Button) findViewById(R.id.add_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        tasks_codegroup_button = (Button) findViewById(R.id.tasks_codegroup_button);
        tasks_code_button = (Button) findViewById(R.id.tasks_code_button);
        tasks_processor_button = (Button) findViewById(R.id.tasks_processor_button);
        start_datetime_button = (Button) findViewById(R.id.start_datetime_button);
        end_datetime_button = (Button) findViewById(R.id.end_datetime_button);
        completion_datetime_button = (Button) findViewById(R.id.completion_datetime_button);

        selected_tasks_custom_info_arraylist.clear();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
            position = extras.getString("position");
            if (position != null && !position.equals("")) {
                add_button.setText("Update");
                status = extras.getString("status");
                itemkey = extras.getString("item_key");
                taskcodegroup_id = extras.getString("taskcodegroup_id");
                taskcodegroup_text = extras.getString("taskcodegroup_text");
                tasks_codegroup_button.setText(taskcodegroup_id + " - " + taskcodegroup_text);
                taskcode_id = extras.getString("taskcode_id");
                taskcode_text = extras.getString("taskcode_text");
                tasks_code_button.setText(taskcode_id + " - " + taskcode_text);
                task_text = extras.getString("task_text");
                task_text_edittext.setText(task_text);
                taskprocessor_id = extras.getString("taskprocessor_id");
                taskprocessor_text = extras.getString("taskprocessor_text");
                tasks_processor_button.setText(taskprocessor_id + "-" + taskprocessor_text);
                task_responsible = extras.getString("task_responsible");
                responsible_edittext.setText(task_responsible);
                planned_st_date_formatted = extras.getString("planned_st_date_formatted");
                if (planned_st_date_formatted != null && !planned_st_date_formatted.equals("")) {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(planned_st_date_formatted);
                        planned_st_date = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                planned_st_time_formatted = extras.getString("planned_st_time_formatted");
                if (planned_st_time_formatted != null && !planned_st_time_formatted.equals("")) {
                    try {
                        String inputPattern = "HHmmss";
                        String outputPattern = "HH:mm:ss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(planned_st_time_formatted);
                        planned_st_time = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                start_datetime_button.setText(planned_st_date + "  -  " + planned_st_time);
                planned_end_date_formatted = extras.getString("planned_end_date_formatted");
                if (planned_end_date_formatted != null && !planned_end_date_formatted.equals("")) {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(planned_end_date_formatted);
                        planned_end_date = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                planned_end_time_formatted = extras.getString("planned_end_time_formatted");
                if (planned_end_time_formatted != null && !planned_end_time_formatted.equals("")) {
                    try {
                        String inputPattern = "HHmmss";
                        String outputPattern = "HH:mm:ss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(planned_end_time_formatted);
                        planned_end_time = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                end_datetime_button.setText(planned_end_date + "  -  " + planned_end_time);
                release_status = extras.getString("release_status");
                completed_status = extras.getString("completed_status");
                success_status = extras.getString("success_status");
                if (release_status.equalsIgnoreCase("X")) {
                    release_radiobutton.setChecked(true);
                    completed_radiobutton.setChecked(false);
                    success_radiobutton.setChecked(false);
                } else if (completed_status.equalsIgnoreCase("X")) {
                    release_radiobutton.setChecked(false);
                    completed_radiobutton.setChecked(true);
                    success_radiobutton.setChecked(false);
                } else if (success_status.equalsIgnoreCase("X")) {
                    release_radiobutton.setChecked(false);
                    completed_radiobutton.setChecked(false);
                    success_radiobutton.setChecked(true);
                } else {
                    release_radiobutton.setChecked(false);
                    completed_radiobutton.setChecked(false);
                    success_radiobutton.setChecked(false);
                }


                completion_date_formatted = extras.getString("completion_date_formatted");
                if (completion_date_formatted != null && !completion_date_formatted.equals("")) {
                    try {
                        String inputPattern = "yyyyMMdd";
                        String outputPattern = "MMM dd, yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(completion_date_formatted);
                        completion_date = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                completion_time_formatted = extras.getString("completion_time_formatted");
                if (completion_time_formatted != null && !completion_time_formatted.equals("")) {
                    try {
                        String inputPattern = "HHmmss";
                        String outputPattern = "HH:mm:ss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Date date = inputFormat.parse(completion_time_formatted);
                        completion_time = outputFormat.format(date);
                    } catch (ParseException e) {
                    }
                }
                completion_datetime_button.setText(completion_date + "  -  " + completion_time);
                completedby = extras.getString("completedby");
                completedby_edittext.setText(completedby);
            }
            else
            {
                try {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                    String formattedDate = df.format(c.getTime());
                    SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                    String formattedDate_format = df1.format(c.getTime());
                    SimpleDateFormat time_df = new SimpleDateFormat("HH:mm:ss");
                    String formattedTime = time_df.format(c.getTime());
                    SimpleDateFormat time_df1 = new SimpleDateFormat("HHmmss");
                    String formattedTime_format = time_df1.format(c.getTime());
                    planned_st_date = formattedDate;
                    planned_st_date_formatted = formattedDate_format;
                    start_datetime_button.setText(formattedDate + "  -  " + formattedTime);

                    c.add(Calendar.DATE, 1);
                    String formattedDate_new = df.format(c.getTime());
                    String formattedDate_format_new = df1.format(c.getTime());
                    planned_end_date = formattedDate_new;
                    planned_end_date_formatted = formattedDate_format_new;
                    end_datetime_button.setText(planned_end_date + "  -  " + formattedTime);

                    planned_st_time = formattedTime;
                    planned_end_time = formattedTime;
                    planned_st_time_formatted = formattedTime_format;
                    planned_end_time_formatted = formattedTime_format;
                } catch (Exception e) {
                }
            }
        }

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        tasks_codegroup_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);
        tasks_code_button.setOnClickListener(this);
        tasks_processor_button.setOnClickListener(this);
        start_datetime_button.setOnClickListener(this);
        end_datetime_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         if (v == cancel_button) {
            Notifications_Tasks_Add_Activity.this.finish();
        } else if (v == back_imageview) {
            Notifications_Tasks_Add_Activity.this.finish();
        } else if (v == tasks_codegroup_button) {
            Intent intent = new Intent(Notifications_Tasks_Add_Activity.this,
                    Notifications_Tasks_TaskCodeGroup_Activity.class);
            intent.putExtra("request_id", Integer.toString(taskcodegroup));
            startActivityForResult(intent, taskcodegroup);
        } else if (v == tasks_code_button) {
            if (taskcodegroup_id != null && !taskcodegroup_id.equals("")) {
                Intent intent = new Intent(Notifications_Tasks_Add_Activity.this,
                        Notifications_Tasks_TaskCode_Activity.class);
                intent.putExtra("request_id", Integer.toString(taskcode));
                intent.putExtra("taskcodegroup_id", taskcodegroup_id);
                startActivityForResult(intent, taskcode);
            } else {
                error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                        getString(R.string.select_taskcodegrp));
            }
        } else if (v == tasks_processor_button) {
            Intent intent = new Intent(Notifications_Tasks_Add_Activity.this,
                    Notifications_Tasks_TaskProcessor_Activity.class);
            intent.putExtra("request_id", Integer.toString(taskprocessor));
            startActivityForResult(intent, taskprocessor);
        } else if (v == start_datetime_button) {
            Intent intent = new Intent(Notifications_Tasks_Add_Activity.this,
                    DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(planned_st_datetime));
            startActivityForResult(intent, planned_st_datetime);
        } else if (v == end_datetime_button) {
            Intent intent = new Intent(Notifications_Tasks_Add_Activity.this,
                    DateTimePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(planned_end_datetime));
            startActivityForResult(intent, planned_end_datetime);
        } else if (v == add_button) {
            if (taskcodegroup_id != null && !taskcodegroup_id.equals("")) {
                if (taskcode_id != null && !taskcode_id.equals("")) {
                    if (task_text_edittext.getText().toString() != null &&
                            !task_text_edittext.getText().toString().equals("")) {
                        if (planned_st_date != null && !planned_st_date.equals("")) {
                            if (planned_end_date != null && !planned_end_date.equals("")) {
                                Intent intent = new Intent();
                                intent.putExtra("taskcodegroup_id", taskcodegroup_id);
                                intent.putExtra("taskcodegroup_text", taskcodegroup_text);
                                intent.putExtra("taskcode_id", taskcode_id);
                                intent.putExtra("taskcode_text", taskcode_text);
                                intent.putExtra("task_text",
                                        task_text_edittext.getText().toString());
                                intent.putExtra("taskprocessor_id", taskprocessor_id);
                                intent.putExtra("taskprocessor_text", taskprocessor_text);
                                intent.putExtra("task_responsible",
                                        responsible_edittext.getText().toString());
                                intent.putExtra("planned_st_date", planned_st_date);
                                intent.putExtra("planned_st_date_formatted",
                                        planned_st_date_formatted);
                                intent.putExtra("planned_st_time", planned_st_time);
                                intent.putExtra("planned_st_time_formatted",
                                        planned_st_time_formatted);
                                intent.putExtra("planned_end_date", planned_end_date);
                                intent.putExtra("planned_end_date_formatted",
                                        planned_end_date_formatted);
                                intent.putExtra("planned_end_time", planned_end_time);
                                intent.putExtra("planned_end_time_formatted",
                                        planned_end_time_formatted);
                                if (release_radiobutton.isChecked()) {
                                    intent.putExtra("release_status", "X");
                                } else {
                                    intent.putExtra("release_status", "");
                                }
                                if (completed_radiobutton.isChecked()) {
                                    intent.putExtra("completed_status", "X");
                                } else {
                                    intent.putExtra("completed_status", "");
                                }
                                if (success_radiobutton.isChecked()) {
                                    intent.putExtra("success_status", "X");
                                } else {
                                    intent.putExtra("success_status", "");
                                }
                                intent.putExtra("completion_date", completion_date);
                                intent.putExtra("completion_date_formatted",
                                        completion_date_formatted);
                                intent.putExtra("completion_time", completion_time);
                                intent.putExtra("completion_time_formatted",
                                        completion_time_formatted);
                                intent.putExtra("completedby",
                                        completedby_edittext.getText().toString());
                                intent.putExtra("task_itemkey", itemkey);
                                intent.putExtra("position", position);
                                intent.putExtra("status", status);
                                intent.putExtra("selected_tasks_custom_info_arraylist",
                                        selected_tasks_custom_info_arraylist);
                                setResult(request_id, intent);
                                Notifications_Tasks_Add_Activity.this.finish();
                            } else {
                                error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                                        getString(R.string.select_plannenddt_time));
                            }
                        } else {
                            error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                                    getString(R.string.select_plannstrtdt_time));
                        }
                    } else {
                        error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                                getString(R.string.enter_tasktxt));
                    }
                } else {
                    error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                            getString(R.string.enter_taskcode));
                }
            } else {
                error_dialog.show_error_dialog(Notifications_Tasks_Add_Activity.this,
                        getString(R.string.enter_taskcodegrp));
            }
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == taskcodegroup) {
                taskcodegroup_id = data.getStringExtra("taskcodegroup_id");
                taskcodegroup_text = data.getStringExtra("taskcodegroup_text");
                tasks_codegroup_button.setText(taskcodegroup_id + " - " + taskcodegroup_text);
                taskcode_id = "";
                taskcode_text = "";
                tasks_code_button.setText("");
            } else if (requestCode == taskcode) {
                taskcode_id = data.getStringExtra("taskcode_id");
                taskcode_text = data.getStringExtra("taskcode_text");
                tasks_code_button.setText(taskcode_id + " - " + taskcode_text);
            } else if (requestCode == taskprocessor) {
                taskprocessor_id = data.getStringExtra("taskprocessor_id");
                taskprocessor_text = data.getStringExtra("taskprocessor_text");
                tasks_processor_button.setText(taskprocessor_id + "-" + taskprocessor_text);
            } else if (requestCode == planned_st_datetime) {
                planned_st_date = data.getStringExtra("date");
                planned_st_date_formatted = data.getStringExtra("date_formatted");
                planned_st_time = data.getStringExtra("time");
                planned_st_time_formatted = data.getStringExtra("time_formatted");
                start_datetime_button.setText(planned_st_date + "  -  " + planned_st_time);
            } else if (requestCode == planned_end_datetime) {
                planned_end_date = data.getStringExtra("date");
                planned_end_date_formatted = data.getStringExtra("date_formatted");
                planned_end_time = data.getStringExtra("time");
                planned_end_time_formatted = data.getStringExtra("time_formatted");
                end_datetime_button.setText(planned_end_date + "  -  " + planned_end_time);
            }
        }
    }
}
