package com.enstrapp.fieldtekpro.equipment_update;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.notifications.Notifications_Create_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;


public class Equipment_Update_Data_Activity extends AppCompatActivity implements OnClickListener
{

    String work_center = "",iwerk = "", equipment_text = "", functionlocation_id = "", equipment_id = "";
    TextView title_textview;
    Button submit_button, cancel_button, floc_id_button, equi_id_button, equi_text_button;
    ImageView back_imageview;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    EditText modelno_edittext, mso_edittext, cym_edittext, cm_edittext, mc_edittext, mpo_edittext, manufacturer_edittext;
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    Network_Connection_Dialog networkConnectionDialog = new Network_Connection_Dialog();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_update_data_activity);


        DATABASE_NAME = Equipment_Update_Data_Activity.this.getString(R.string.database_name);
        FieldTekPro_db = Equipment_Update_Data_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        title_textview = (TextView)findViewById(R.id.title_textview);
        equi_id_button = (Button) findViewById(R.id.equi_id_button);
        equi_text_button = (Button) findViewById(R.id.equi_text_button);
        floc_id_button = (Button) findViewById(R.id.floc_id_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        manufacturer_edittext = (EditText) findViewById(R.id.manufacturer_edittext);
        modelno_edittext = (EditText) findViewById(R.id.modelno_edittext);
        mpo_edittext = (EditText) findViewById(R.id.mpo_edittext);
        mso_edittext = (EditText) findViewById(R.id.mso_edittext);
        mc_edittext = (EditText) findViewById(R.id.mc_edittext);
        cym_edittext = (EditText) findViewById(R.id.cym_edittext);
        cm_edittext = (EditText) findViewById(R.id.cm_edittext);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            equipment_id = extras.getString("equipment_id");
            equipment_text = extras.getString("equipment_text");
            functionlocation_id = extras.getString("functionlocation_id");
            iwerk = extras.getString("iwerk");
            work_center = extras.getString("work_center");


            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equipment_id});;
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            manufacturer_edittext.setText(cursor.getString(8));
                            modelno_edittext.setText(cursor.getString(15));
                            mpo_edittext.setText(cursor.getString(16));
                            mso_edittext.setText(cursor.getString(14));
                            mc_edittext.setText(cursor.getString(31));
                            cym_edittext.setText(cursor.getString(32));
                            cm_edittext.setText(cursor.getString(33));
                        }
                        while (cursor.moveToNext());
                    }
                }
                else
                {
                    cursor.close();
                }
            }
            catch (Exception e)
            {
                Log.v("kiran_ee",e.getMessage()+"////");
            }
        }


        title_textview.setText(equipment_id);
        equi_id_button.setText(equipment_id);
        equi_text_button.setText(equipment_text);
        floc_id_button.setText(functionlocation_id);


        back_imageview.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
    }



    @Override
    public void onClick(View view)
    {
        if(view == back_imageview)
        {
            onBackButtonPressed();
        }
        else if(view == cancel_button)
        {
            onBackButtonPressed();
        }
        else if(view == submit_button)
        {
            cd = new ConnectionDetector(Equipment_Update_Data_Activity.this);
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent)
            {
                String cons_month = cm_edittext.getText().toString();
                int month = Integer.parseInt(cons_month);
                if(month > 0 && month <= 12)
                {
                    String manuf = manufacturer_edittext.getText().toString();
                    if(manuf.equalsIgnoreCase("us"))
                    {
                        error_dialog.show_error_dialog(Equipment_Update_Data_Activity.this,"Please enter valid manufacturer.");
                    }
                    else
                    {
                        final Dialog submit_decision_dialog = new Dialog(Equipment_Update_Data_Activity.this);
                        submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        submit_decision_dialog.setCancelable(false);
                        submit_decision_dialog.setCanceledOnTouchOutside(false);
                        submit_decision_dialog.setContentView(R.layout.decision_dialog);
                        ImageView imageView1 = submit_decision_dialog
                                .findViewById(R.id.imageView1);
                        Glide.with(Equipment_Update_Data_Activity.this)
                                .load(R.drawable.error_dialog_gif).into(imageView1);
                        TextView description_textview = submit_decision_dialog
                                .findViewById(R.id.description_textview);
                        description_textview
                                .setText("Do you want to save the data?");
                        Button ok_button = submit_decision_dialog
                                .findViewById(R.id.yes_button);
                        Button cancel_button = submit_decision_dialog
                                .findViewById(R.id.no_button);
                        submit_decision_dialog.show();
                        ok_button.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                submit_decision_dialog.dismiss();
                                new POST_EQUIPMENT_DATA_REST().execute();
                            }
                        });
                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                submit_decision_dialog.dismiss();
                            }
                        });
                    }
                }
                else
                {
                    error_dialog.show_error_dialog(Equipment_Update_Data_Activity.this,"Please enter valid construction month.");
                }
            }
            else
            {
                networkConnectionDialog.show_network_connection_dialog(Equipment_Update_Data_Activity.this);
            }
        }
    }



    private class POST_EQUIPMENT_DATA_REST extends AsyncTask<Void, Integer, Void>
    {
        String eq_update_status = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Equipment_Update_Data_Activity.this, getResources().getString(R.string.equip_update));
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                eq_update_status = EQ_UPDATE_REST.post_eq_data(Equipment_Update_Data_Activity.this, functionlocation_id, equipment_id, iwerk, work_center, manufacturer_edittext.getText().toString(), modelno_edittext.getText().toString(),
                        mpo_edittext.getText().toString(), mso_edittext.getText().toString(),
                        mc_edittext.getText().toString(), cym_edittext.getText().toString(), cm_edittext.getText().toString());
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (eq_update_status != null && !eq_update_status.equals(""))
            {
                if (eq_update_status.startsWith("S"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    final Dialog success_dialog = new Dialog(Equipment_Update_Data_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText(eq_update_status.substring(1));
                    Glide.with(Equipment_Update_Data_Activity.this).load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            success_dialog.dismiss();
                            Equipment_Update_Data_Activity.this.finish();
                        }
                    });
                }
                else if (eq_update_status.startsWith("E"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Equipment_Update_Data_Activity.this, eq_update_status.substring(1).toString());
                }
                else
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Equipment_Update_Data_Activity.this, "Unable to process Equipment Update. Please try again.");
                }
            }
            else
            {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Equipment_Update_Data_Activity.this, "Unable to process Equipment Update. Please try again.");
            }
        }
    }




    private void onBackButtonPressed()
    {
        final Dialog network_connect_dialog = new Dialog(Equipment_Update_Data_Activity.this);
        network_connect_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        network_connect_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        network_connect_dialog.setCancelable(false);
        network_connect_dialog.setCanceledOnTouchOutside(false);
        network_connect_dialog.setContentView(R.layout.network_connection_dialog);
        ImageView imageview = (ImageView) network_connect_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) network_connect_dialog.findViewById(R.id.description_textview);
        description_textview.setText("Do you want to exit without save?");
        Button connect_button = (Button) network_connect_dialog.findViewById(R.id.connect_button);
        connect_button.setText("Yes");
        Button cancel_button = (Button) network_connect_dialog.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                network_connect_dialog.dismiss();
            }
        });
        connect_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                network_connect_dialog.dismiss();
                Equipment_Update_Data_Activity.this.finish();
            }
        });
        Glide.with(Equipment_Update_Data_Activity.this).load(R.drawable.error_dialog_gif).into(imageview);
        network_connect_dialog.show();
    }




}
