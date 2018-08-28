package com.enstrapp.fieldtekpro.Settings;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Auto_Sync.Auto_Sync_BackgroundService;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.login.Login_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.ArrayList;
import java.util.List;


public class Settings_Activity extends AppCompatActivity implements OnClickListener
{

    CheckBox initialload_cb, refresh_cb;
    EditText pushIntrvl_et;
    SharedPreferences FieldTekPro_SharedPref;
    Editor FieldTekPro_SharedPrefeditor;
    Button reset_bt, submit_bt;
    TextView reset_passcode_textview;
    LinearLayout passcode_layout;
    Error_Dialog error_dialog = new Error_Dialog();
    Dialog decision_dialog;
    private static String DATABASE_NAME = "";
    private SQLiteDatabase FieldTekPro_db;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    ImageView back_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        initialload_cb = findViewById(R.id.initialload_cb);
        refresh_cb = findViewById(R.id.refresh_cb);
        pushIntrvl_et = findViewById(R.id.pushIntrvl_et);
        reset_bt = findViewById(R.id.reset_bt);
        submit_bt = findViewById(R.id.submit_bt);
        reset_passcode_textview = (TextView)findViewById(R.id.reset_passcode_textview);
        passcode_layout = (LinearLayout)findViewById(R.id.passcode_layout);
        back_imageview = (ImageView)findViewById(R.id.back_imageview);

		/* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
		/* Initializing Shared Preferences */

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Settings_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
        if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals(""))
        {
            passcode_layout.setVisibility(View.VISIBLE);
        }
        else
        {
            passcode_layout.setVisibility(View.GONE);
        }

        reset_bt.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        reset_passcode_textview.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
            if (InitialLoad != null && !InitialLoad.equals("")) {
                if (InitialLoad.equalsIgnoreCase("Checked")) {
                    initialload_cb.setChecked(true);
                } else {
                    initialload_cb.setChecked(false);
                }
            } else {
                initialload_cb.setChecked(false);
            }

            String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
            if (Refresh != null && !Refresh.equals("")) {
                if (Refresh.equalsIgnoreCase("Checked")) {
                    refresh_cb.setChecked(true);
                } else {
                    refresh_cb.setChecked(false);
                }
            } else {
                refresh_cb.setChecked(false);
            }

            String PushInterval = FieldTekPro_SharedPref.getString("FieldTekPro_PushInterval", null);
            if (PushInterval != null && !PushInterval.equals("")) {
                pushIntrvl_et.setText(FieldTekPro_SharedPref.getString("FieldTekPro_PushInterval", null));
            } else {
                pushIntrvl_et.setText("");
            }

        } catch (Exception e) {
        }

    }


    @Override
    public void onClick(View v)
    {
        if(v == back_imageview)
        {
            Settings_Activity.this.finish();
        }
        else if(v == reset_passcode_textview)
        {
            decision_dialog = new Dialog(Settings_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageview = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            TextView description_textview = (TextView)decision_dialog.findViewById(R.id.description_textview);
            Glide.with(Settings_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
            Button confirm = (Button)decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button)decision_dialog.findViewById(R.id.no_button);
            description_textview.setText("All the data will be erased. Are you sure you want to reset the passcode?");
            decision_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decision_dialog.dismiss();
                }
            });
            confirm.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    decision_dialog.dismiss();
                    custom_progress_dialog.show_progress_dialog(Settings_Activity.this,getResources().getString(R.string.loading));
                    SharedPreferences settings = Settings_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
                    settings.edit().clear().commit();
                    Cursor c = FieldTekPro_db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
                    List<String> tables = new ArrayList<>();
                    while (c.moveToNext())
                    {
                        tables.add(c.getString(0));
                    }
                    for (String table : tables)
                    {
                        String dropQuery = "DROP TABLE IF EXISTS " + table;
                        FieldTekPro_db.execSQL(dropQuery);
                    }
                    custom_progress_dialog.dismiss_progress_dialog();
                    Settings_Activity.this.finish();
                    Intent intent = new Intent(Settings_Activity.this, Login_Activity.class);
                    startActivity(intent);
                }
            });
        }
        else if (v == reset_bt)
        {
            final Dialog reset_dialog = new Dialog(Settings_Activity.this);
            reset_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            reset_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            reset_dialog.setCancelable(false);
            reset_dialog.setCanceledOnTouchOutside(false);
            reset_dialog.setContentView(R.layout.network_error_dialog);
            final TextView description_textview = (TextView) reset_dialog.findViewById(R.id.description_textview);
            Button ok_button = (Button) reset_dialog.findViewById(R.id.ok_button);
            Button cancel_button = (Button) reset_dialog.findViewById(R.id.cancel_button);
            description_textview.setText(getResources().getString(R.string.reset_settings));
            reset_dialog.show();
            ok_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset_dialog.dismiss();
                    initialload_cb.setChecked(false);
                    refresh_cb.setChecked(false);
                }
            });
            cancel_button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset_dialog.dismiss();
                }
            });
        }
        else if (v == submit_bt)
        {
            if (pushIntrvl_et.getText().toString() != null && !pushIntrvl_et.getText().toString().equals(""))
            {
                if (pushIntrvl_et.getText().toString().equalsIgnoreCase("0") || pushIntrvl_et.getText().toString().equalsIgnoreCase("00") || pushIntrvl_et.getText().toString().equalsIgnoreCase("000")) {
                    error_dialog.show_error_dialog(Settings_Activity.this,"Please Enter Valid Push Interval");
                } else {
                    final Dialog decision_dialog = new Dialog(Settings_Activity.this);
                    decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    decision_dialog.setCancelable(false);
                    decision_dialog.setCanceledOnTouchOutside(false);
                    decision_dialog.setContentView(R.layout.network_error_dialog);
                    final TextView favoritetextview = (TextView) decision_dialog.findViewById(R.id.description_textview);
                    Button confirm = (Button) decision_dialog.findViewById(R.id.ok_button);
                    Button cancel = (Button) decision_dialog.findViewById(R.id.cancel_button);
                    favoritetextview.setText("Do you want to submit the changes?");
                    decision_dialog.show();
                    cancel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            decision_dialog.dismiss();
                        }
                    });
                    confirm.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            decision_dialog.dismiss();
                            if (initialload_cb.isChecked()) {
                                FieldTekPro_SharedPrefeditor.putString("FieldTekPro_InitialLoad", "Checked");
                            } else {
                                FieldTekPro_SharedPrefeditor.putString("FieldTekPro_InitialLoad", "");
                            }
                            if (refresh_cb.isChecked()) {
                                FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Refresh", "Checked");
                            } else {
                                FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Refresh", "");
                            }
                            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_PushInterval", pushIntrvl_et.getText().toString());
                            FieldTekPro_SharedPrefeditor.commit();
                            String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
                            if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals(""))
                            {
                                startService(new Intent(Settings_Activity.this, Auto_Sync_BackgroundService.class));
                            }
                            Settings_Activity.this.finish();
                        }
                    });
                }
            }
            else
            {
                final Dialog decision_dialog = new Dialog(Settings_Activity.this);
                decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                decision_dialog.setCancelable(false);
                decision_dialog.setCanceledOnTouchOutside(false);
                decision_dialog.setContentView(R.layout.network_error_dialog);
                final TextView favoritetextview = (TextView) decision_dialog.findViewById(R.id.description_textview);
                Button confirm = (Button) decision_dialog.findViewById(R.id.ok_button);
                Button cancel = (Button) decision_dialog.findViewById(R.id.cancel_button);
                favoritetextview.setText("Do you want to submit the changes?");
                decision_dialog.show();
                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision_dialog.dismiss();
                    }
                });
                confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        decision_dialog.dismiss();
                        if (initialload_cb.isChecked()) {
                            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_InitialLoad", "Checked");
                        } else {
                            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_InitialLoad", "");
                        }

                        if (refresh_cb.isChecked()) {
                            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Refresh", "Checked");
                        } else {
                            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Refresh", "");
                        }
                        FieldTekPro_SharedPrefeditor.putString("FieldTekPro_PushInterval", "");
                        FieldTekPro_SharedPrefeditor.commit();
                        Settings_Activity.this.finish();
                    }
                });
            }
        }
    }


}
