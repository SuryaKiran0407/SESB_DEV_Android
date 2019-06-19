package com.enstrapp.fieldtekpro.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BOM_Reservation_Activity extends Activity implements View.OnClickListener {

    String Lgort = "", storage_location = "", costcenter_id = "", costcenter_text = "", Requirement_date = "", movement_type_id = "", movement_type_text = "", Plant = "", BOM = "", Component = "", Component_text = "", Quantity = "", Unit = "", req_date = "";
    ImageView back_imageview;
    Button reserve_button,cancel_button;
    LinearLayout ordernumber_layout, movement_type_layout, requirement_date_layout, costcenter_layout;
    EditText ordernumber_edittext, movement_type_edittext, plant_edittext, requirement_date_edittext, quantity_edittext, costcenter_edittext;
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    int req_stdate = 0;
    private static SharedPreferences FieldTekPro_SharedPref;
    private static SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Dialog submit_decision_dialog, decision_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utilities_bom_reservation_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            BOM = extras.getString("BOM");
            Component = extras.getString("Components");
            Component_text = extras.getString("Components_text");
            Quantity = extras.getString("Quantity");
            Unit = extras.getString("Unit");
            Plant = extras.getString("Plant");
            Lgort = extras.getString("Lgort");
            if (Lgort != null && !Lgort.equals("")) {
                storage_location = Lgort;
            }
        }

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        FieldTekPro_SharedPref = BOM_Reservation_Activity.this.getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();

        try {
            Cursor cursor = null;
            cursor = FieldTekPro_db.rawQuery("select * from GET_STOCK_DATA where Matnr = ?", new String[]{Component});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        storage_location = cursor.getString(4);
                    }
                    while (cursor.moveToNext());
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        }

        plant_edittext = (EditText) findViewById(R.id.plant_edittext);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        movement_type_edittext = (EditText) findViewById(R.id.movement_type_edittext);
        movement_type_layout = (LinearLayout) findViewById(R.id.movement_type_layout);
        reserve_button = (Button) findViewById(R.id.reserve_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        quantity_edittext = (EditText) findViewById(R.id.quantity_edittext);
        requirement_date_edittext = (EditText) findViewById(R.id.requirement_date_edittext);
        requirement_date_layout = (LinearLayout) findViewById(R.id.requirement_date_layout);
        costcenter_layout = (LinearLayout) findViewById(R.id.costcenter_layout);
        costcenter_edittext = (EditText) findViewById(R.id.costcenter_edittext);
        ordernumber_layout = (LinearLayout) findViewById(R.id.ordernumber_layout);
        ordernumber_edittext = (EditText) findViewById(R.id.ordernumber_edittext);

        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date = new Date();
        String formattedDate = dateFormat.format(date.getTime());
        requirement_date_edittext.setText(formattedDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Requirement_date = sdf1.format(date.getTime());

        plant_edittext.setText(Plant);

        back_imageview.setOnClickListener(this);
        requirement_date_layout.setOnClickListener(this);
        movement_type_layout.setOnClickListener(this);
        costcenter_layout.setOnClickListener(this);
        reserve_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

        String auth_status = Authorizations.Get_Authorizations_Data(BOM_Reservation_Activity.this, "R", "I");
        if (auth_status.equalsIgnoreCase("true")) {
            reserve_button.setVisibility(View.VISIBLE);
        } else {
            reserve_button.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            BOM_Reservation_Activity.this.finish();
        } else if(v == cancel_button)
        {
            BOM_Reservation_Activity.this.finish();
        }
        else if (v == requirement_date_layout) {
            Intent intent = new Intent(BOM_Reservation_Activity.this, DatePickerDialog.class);
            intent.putExtra("request_id", Integer.toString(req_stdate));
            startActivityForResult(intent, req_stdate);
        } else if (v == movement_type_layout) {
            Intent movement_type_intent = new Intent(BOM_Reservation_Activity.this, Movement_Type_Activity.class);
            startActivityForResult(movement_type_intent, 1);
        } else if (v == costcenter_layout) {
            Intent cost_center_intent = new Intent(BOM_Reservation_Activity.this, CostCenter_Type_Activity.class);
            cost_center_intent.putExtra("plant", Plant);
            startActivityForResult(cost_center_intent, 2);
        } else if (v == reserve_button) {
            if (movement_type_id != null && !movement_type_id.equals("")) {
                if (quantity_edittext.getText().toString() != null && !quantity_edittext.getText().toString().equals("")) {
                    int quantity_value = Integer.parseInt(quantity_edittext.getText().toString());
                    if (quantity_value == 0) {
                        error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                                getString(R.string.qunt_morezero));
                    } else {
                        if (movement_type_id.equalsIgnoreCase("261")) {
                            if (ordernumber_edittext.getText().toString() != null && !ordernumber_edittext.getText().toString().equals("")) {
                                post_bom_reservation();
                            } else {
                                error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                                        getString(R.string.ordr_noenter));
                            }
                        } else {
                            if (costcenter_id != null && !costcenter_id.equals("")) {
                                post_bom_reservation();
                            } else {
                                error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                                        getString(R.string.slct_cstcntr));
                            }
                        }
                    }
                } else {
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                            getString(R.string.ent_quant));
                }
            } else {
                error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                        getString(R.string.slct_mvtype));
            }
        }
    }


    private void post_bom_reservation() {
        cd = new ConnectionDetector(BOM_Reservation_Activity.this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            submit_decision_dialog = new Dialog(BOM_Reservation_Activity.this);
            submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            submit_decision_dialog.setCancelable(false);
            submit_decision_dialog.setCanceledOnTouchOutside(false);
            submit_decision_dialog.setContentView(R.layout.decision_dialog);
            ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
            Glide.with(BOM_Reservation_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
            TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
            description_textview.setText(getResources().getString(R.string.perform_reservation));
            Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
            Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
            submit_decision_dialog.show();
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit_decision_dialog.dismiss();
                    String webservice_type = getString(R.string.webservice_type);
                    if(webservice_type.equalsIgnoreCase("odata"))
                    {
                        new Get_Token().execute();
                    }
                    else
                    {
                        new POST_BOM_Reservation_REST().execute();
                    }
                }
            });
            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit_decision_dialog.dismiss();
                }
            });
        }
        else
        {
            decision_dialog = new Dialog(BOM_Reservation_Activity.this);
            decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            decision_dialog.setCancelable(false);
            decision_dialog.setCanceledOnTouchOutside(false);
            decision_dialog.setContentView(R.layout.offline_decision_dialog);
            ImageView imageView1 = (ImageView) decision_dialog.findViewById(R.id.imageView1);
            Glide.with(BOM_Reservation_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
            TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
            Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
            Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
            Button connect_button = (Button) decision_dialog.findViewById(R.id.connect_button);
            description_textview.setText(getString(R.string.resvr_offline));
            confirm.setText(getString(R.string.yes));
            cancel.setText(getString(R.string.no));
            decision_dialog.show();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                }
            });
            connect_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decision_dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    startActivity(intent);
                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UUID uniqueKey = UUID.randomUUID();
                    try {
                        String sql = "Insert into BOM_RESERVE_HEADER (UUID, BOM_ID, PLANT, REQUIREMENT_DATE, MOVEMENT_TYPE_ID, MOVEMENT_TYPE_TEXT, ORDER_NUMBER, COST_CENTER_ID, COST_CENTER_TEXT, Quantity, Unit, Lgort) values(?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement statement = FieldTekPro_db.compileStatement(sql);
                        FieldTekPro_db.beginTransaction();
                        statement.clearBindings();
                        statement.bindString(1, uniqueKey.toString());
                        statement.bindString(2, Component);
                        statement.bindString(3, Plant);
                        statement.bindString(4, Requirement_date);
                        statement.bindString(5, movement_type_id);
                        statement.bindString(6, movement_type_text);
                        statement.bindString(7, "");
                        statement.bindString(8, costcenter_id);
                        statement.bindString(9, costcenter_text);
                        statement.bindString(10, quantity_edittext.getText().toString());
                        statement.bindString(11, "");
                        statement.bindString(12, storage_location);
                        statement.execute();
                        FieldTekPro_db.setTransactionSuccessful();
                        FieldTekPro_db.endTransaction();
                    } catch (Exception e) {
                    }

                    try {
                        DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                        DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                        Date todaysdate = new Date();
                        String date = date_format.format(todaysdate.getTime());
                        String time = time_format.format(todaysdate.getTime());

                        String sql11 = "Insert into Alert_Log (DATE, TIME, DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID, STATUS, UUID, MESSAGE, LOG_UUID) values(?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement statement11 = FieldTekPro_db.compileStatement(sql11);
                        FieldTekPro_db.beginTransaction();
                        statement11.clearBindings();
                        statement11.bindString(1, date);
                        statement11.bindString(2, time);
                        statement11.bindString(3, "Reservation");
                        statement11.bindString(4, "Create");
                        statement11.bindString(5, "");
                        statement11.bindString(6, Component);
                        statement11.bindString(7, "Fail");
                        statement11.bindString(8, uniqueKey.toString());
                        statement11.bindString(9, "");
                        statement11.bindString(10, uniqueKey.toString());
                        statement11.execute();
                        FieldTekPro_db.setTransactionSuccessful();
                        FieldTekPro_db.endTransaction();
                    } catch (Exception e) {
                    }

                    decision_dialog.dismiss();
                    Toast.makeText(BOM_Reservation_Activity.this, getString(R.string.resvr_svdoffline, Component), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 0) {
                Requirement_date = data.getStringExtra("date");
                req_date = data.getStringExtra("date_formatted");
                requirement_date_edittext.setText(req_date);
            } else if (requestCode == 1) {
                movement_type_id = data.getStringExtra("movement_type_id");
                movement_type_text = data.getStringExtra("movement_type_text");
                movement_type_edittext.setText(movement_type_id + " - " + movement_type_text);
                if (movement_type_id.equalsIgnoreCase("261")) {
                    ordernumber_layout.setVisibility(View.VISIBLE);
                    ordernumber_edittext.setText("");
                } else {
                    ordernumber_layout.setVisibility(View.GONE);
                    ordernumber_edittext.setText("");
                }
            } else if (requestCode == 2) {
                costcenter_id = data.getStringExtra("costcenter_id");
                costcenter_text = data.getStringExtra("costcenter_text");
                costcenter_edittext.setText(costcenter_id + " - " + costcenter_text);
            }
        }
    }


    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(BOM_Reservation_Activity.this, getResources().getString(R.string.bom_reservation_inprogress));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(BOM_Reservation_Activity.this);
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
                new Get_Quantity_Availability_Check().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(BOM_Reservation_Activity.this,
                        getString(R.string.bomresvr_unable));
            }
        }
    }


    private class Get_Quantity_Availability_Check extends AsyncTask<Void, Integer, Void> {
        String stock_availability_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                stock_availability_status = Material_Availability_Check.material_availability_check(BOM_Reservation_Activity.this, BOM, Component, Component_text, quantity_edittext.getText().toString(), Unit, Plant, storage_location, Requirement_date);
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
            if (stock_availability_status != null && !stock_availability_status.equals("")) {
                if (stock_availability_status.startsWith("S")) {
                    new POST_BOM_Reservation().execute();
                } else if (stock_availability_status.startsWith("E")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, stock_availability_status.substring(1).toString());
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, stock_availability_status.substring(1).toString());
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(BOM_Reservation_Activity.this, getString(R.string.bomresvr_unable));
            }
        }
    }


    private class POST_BOM_Reservation extends AsyncTask<Void, Integer, Void> {
        String bom_reservation_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                bom_reservation_status = BOM_Reservation.post_bom_reservation(BOM_Reservation_Activity.this, BOM, Component, Component_text, quantity_edittext.getText().toString(), Unit, Plant, storage_location, Requirement_date, movement_type_id, costcenter_id, ordernumber_edittext.getText().toString());
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
            if (bom_reservation_status != null && !bom_reservation_status.equals("")) {
                if (bom_reservation_status.startsWith("S")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    final Dialog success_dialog = new Dialog(BOM_Reservation_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText(bom_reservation_status.substring(1));
                    Glide.with(BOM_Reservation_Activity.this).load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            success_dialog.dismiss();
                        }
                    });
                } else if (bom_reservation_status.startsWith("E")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, bom_reservation_status.substring(1).toString());
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, "Unable to process BOM Reservation. Please try again.");
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(BOM_Reservation_Activity.this, "Unable to process BOM Reservation. Please try again.");
            }
        }
    }




    private class POST_BOM_Reservation_REST extends AsyncTask<Void, Integer, Void>
    {
        String bom_reservation_status = "";

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(BOM_Reservation_Activity.this, getResources().getString(R.string.bom_reservation_inprogress));
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                bom_reservation_status = BOM_Reservation_REST.post_bom_reservation(BOM_Reservation_Activity.this, BOM, Component, Component_text, quantity_edittext.getText().toString(), Unit, Plant, storage_location, Requirement_date, movement_type_id, costcenter_id, ordernumber_edittext.getText().toString());
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
            if (bom_reservation_status != null && !bom_reservation_status.equals(""))
            {
                if (bom_reservation_status.startsWith("S"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    final Dialog success_dialog = new Dialog(BOM_Reservation_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText(bom_reservation_status.substring(1));
                    Glide.with(BOM_Reservation_Activity.this).load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            success_dialog.dismiss();
                        }
                    });
                }
                else if (bom_reservation_status.startsWith("E"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, bom_reservation_status.substring(1).toString());
                }
                else
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(BOM_Reservation_Activity.this, "Unable to process BOM Reservation. Please try again.");
                }
            }
            else
            {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(BOM_Reservation_Activity.this, "Unable to process BOM Reservation. Please try again.");
            }
        }
    }


}
