package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog;
import com.enstrapp.fieldtekpro.DateTime.TimePickerDialog;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Orders_ConfirmPartial_Activity extends AppCompatActivity {

    TextInputEditText ordrNo_tiet, oprtnId_tiet, oprtnShrtTxt_tiet, plannedWrkUnt_tiet, actlWrkUnt_tiet,
            reason_tiet, strtDt_tiet, strtTm_tiet, endDt_tiet, endTm_tiet, cnfrmTxt_tiet, actlUnt_tiet,
            employee_tiet;
    CheckBox noRmngWrk_Cb, fnlCnfrm_Cb;
    Button cancel_bt, submit_bt;
    Toolbar toolBar;
    ImageView actlUnt_iv, reason_iv, endTm_iv, endDt_iv, strtTm_iv, strtDt_iv;
    OrdrOprtnPrcbl oop;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    static final int DURATION = 110;
    static final int REASON = 150;
    String endDt = "", strDt = "", Response = "", ReasonId = "";
    static final int EDDT = 548;
    static final int STDT = 648;
    static final int EDTM = 748;
    static final int STTM = 588;
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Error_Dialog errorDialog = new Error_Dialog();
    ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmpartial_activity);

        DATABASE_NAME = Orders_ConfirmPartial_Activity.this.getString(R.string.database_name);
        App_db = Orders_ConfirmPartial_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        employee_tiet = findViewById(R.id.employee_tiet);
        actlUnt_tiet = findViewById(R.id.actlUnt_tiet);
        actlUnt_iv = findViewById(R.id.actlUnt_iv);
        ordrNo_tiet = findViewById(R.id.ordrNo_tiet);
        oprtnId_tiet = findViewById(R.id.oprtnId_tiet);
        oprtnShrtTxt_tiet = findViewById(R.id.oprtnShrtTxt_tiet);
        plannedWrkUnt_tiet = findViewById(R.id.plannedWrkUnt_tiet);
        actlWrkUnt_tiet = findViewById(R.id.actlWrkUnt_tiet);
        strtDt_tiet = findViewById(R.id.strtDt_tiet);
        strtTm_tiet = findViewById(R.id.strtTm_tiet);
        endDt_tiet = findViewById(R.id.endDt_tiet);
        endTm_tiet = findViewById(R.id.endTm_tiet);
        cnfrmTxt_tiet = findViewById(R.id.cnfrmTxt_tiet);
        noRmngWrk_Cb = findViewById(R.id.noRmngWrk_Cb);
        fnlCnfrm_Cb = findViewById(R.id.fnlCnfrm_Cb);
        cancel_bt = findViewById(R.id.cancel_bt);
        submit_bt = findViewById(R.id.submit_bt);
        toolBar = findViewById(R.id.toolBar);
        reason_iv = findViewById(R.id.reason_iv);
        strtDt_iv = findViewById(R.id.strtDt_iv);
        endTm_iv = findViewById(R.id.endTm_iv);
        endDt_iv = findViewById(R.id.endDt_iv);
        strtTm_iv = findViewById(R.id.strtTm_iv);

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            oop = bundle.getParcelable("time_confrm");
            strDt = bundle.getString("strDt");
            endDt = bundle.getString("endDt");
            ordrNo_tiet.setText(oop.getOrdrId());
            oprtnId_tiet.setText(oop.getOprtnId());
            oprtnShrtTxt_tiet.setText(oop.getOprtnShrtTxt());
            plannedWrkUnt_tiet.setText(oop.getDuration() + "/" + oop.getDurationUnit());
            actlWrkUnt_tiet.setText("");
            strtDt_tiet.setText(dateDisplayFormat(strDt));
            endDt_tiet.setText(dateDisplayFormat(endDt));
            noRmngWrk_Cb.setChecked(true);
            fnlCnfrm_Cb.setChecked(true);
        }


        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        actlUnt_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent durationIntent = new Intent(Orders_ConfirmPartial_Activity.this, Duration_Activity.class);
                startActivityForResult(durationIntent, DURATION);
            }
        });

        reason_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reasonIntent = new Intent(Orders_ConfirmPartial_Activity.this, Reason_Activity.class);
                reasonIntent.putExtra("plant", oop.getPlantId());
                startActivityForResult(reasonIntent, REASON);
            }
        });

        strtDt_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edDtIntent = new Intent(Orders_ConfirmPartial_Activity.this, DatePickerDialog.class);
                startActivityForResult(edDtIntent, STDT);
            }
        });
        endDt_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edDtIntent = new Intent(Orders_ConfirmPartial_Activity.this, DatePickerDialog.class);
                startActivityForResult(edDtIntent, EDDT);
            }
        });
        endTm_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edTmIntent = new Intent(Orders_ConfirmPartial_Activity.this, TimePickerDialog.class);
                startActivityForResult(edTmIntent, EDTM);
            }
        });
        strtTm_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edTmIntent = new Intent(Orders_ConfirmPartial_Activity.this, TimePickerDialog.class);
                startActivityForResult(edTmIntent, STTM);
            }
        });
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog(getString(R.string.oprtn_cnfrm_msg));
            }
        });

        setActualWork(oop.getOrdrId(), oop.getOprtnId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (DURATION):
                if (resultCode == RESULT_OK) {
                    actlUnt_tiet.setText(data.getStringExtra("duration_unit"));
                }
                break;

            case (REASON):
                if (resultCode == RESULT_OK) {
                    ReasonId = data.getStringExtra("reason_unit");
                    reason_tiet.setText(data.getStringExtra("reason_txt"));
                }
                break;

            case (EDDT):
                if (resultCode == RESULT_OK) {
                    endDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;
            case (STDT):
                if (resultCode == RESULT_OK) {
                    strtDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;
            case (EDTM):
                if (resultCode == RESULT_OK) {
                    endTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;
            case (STTM):
                if (resultCode == RESULT_OK) {
                    strtTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActualWork(String ordrId, String oprtnId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{ordrId, oprtnId});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                String status = cursor.getString(5);
                if (status.equals("P")) {
                    double total = 0;
                    try {
                        Cursor cursor1 = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{ordrId, oprtnId});
                        if (cursor1 != null && cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    String sec = cursor1.getString(6);
                                    if (sec != null && !sec.equals("")) {
                                        total = (double) (total + cursor1.getLong(6));
                                    }
                                }
                                while (cursor1.moveToNext());
                            }
                        } else {
                            cursor1.close();
                        }
                    } catch (Exception e) {
                    }

                    if (oop.getDurationUnit().equalsIgnoreCase("MIN")) {
                        double minutes = total / 60;
                        NumberFormat formatter = new DecimalFormat("###.#");
                        String min = formatter.format(minutes);
                        actlWrkUnt_tiet.setText(min + "");
                        actlUnt_tiet.setText("MIN");
                    } else if (oop.getDurationUnit().equalsIgnoreCase("HR")) {
                        double hours = total / 3600;
                        NumberFormat formatter = new DecimalFormat("###.#");
                        String hour = formatter.format(hours);
                        actlWrkUnt_tiet.setText(hour + "");
                        actlUnt_tiet.setText("HR");
                    } else if (oop.getDurationUnit().equalsIgnoreCase("DAY")) {
                        double dayss = (double) total / 86400;
                        NumberFormat formatter = new DecimalFormat("###.#");
                        String days = formatter.format(dayss);
                        actlWrkUnt_tiet.setText(days + "");
                        actlUnt_tiet.setText("DAY");
                    }
                } else if (status.equals("S")) {
                    String start_date = "";
                    try {
                        Cursor cursor1 = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{ordrId, oprtnId});
                        if (cursor1 != null && cursor1.getCount() > 0) {
                            cursor1.moveToNext();
                            start_date = cursor1.getString(4);
                        } else {
                            cursor1.close();
                        }
                    } catch (Exception e) {
                    }

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String pauseDate = sdf.format(cal.getTime());

                    try {
                        String string1 = start_date;
                        Date time1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string1);
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(time1);

                        String string2 = pauseDate;
                        Date time2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string2);
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(time2);

                        Date x = calendar1.getTime();
                        Date xy = calendar2.getTime();
                        long diff = xy.getTime() - x.getTime();

                        double seconds = diff / 1000;

                        double total = 0;
                        try {
                            Cursor cursor1 = App_db.rawQuery("select * from ORDER_CONFIRMATION_TIMER where Order_No = ? and Operation_ID = ?" + " order by id desc", new String[]{ordrId, oprtnId});
                            if (cursor1 != null && cursor1.getCount() > 0) {
                                if (cursor1.moveToFirst()) {
                                    do {
                                        String sec = cursor1.getString(6);
                                        if (sec != null && !sec.equals("")) {
                                            total = (double) (total + cursor1.getLong(6));
                                        }
                                    }
                                    while (cursor1.moveToNext());
                                }
                            } else {
                                cursor1.close();
                            }
                        } catch (Exception e) {
                        }

                        double final_total = (double) (total + seconds);

                        if (oop.getDurationUnit().equalsIgnoreCase("MIN")) {
                            double minutes = final_total / 60;
                            NumberFormat formatter = new DecimalFormat("###.#");
                            String min = formatter.format(minutes);
                            actlWrkUnt_tiet.setText(min + "");
                            actlUnt_tiet.setText("MIN");
                        } else if (oop.getDurationUnit().equalsIgnoreCase("HR")) {
                            double hours = final_total / 3600;
                            NumberFormat formatter = new DecimalFormat("###.#");
                            String hour = formatter.format(hours);
                            actlWrkUnt_tiet.setText(hour + "");
                            actlUnt_tiet.setText("HR");
                        } else if (oop.getDurationUnit().equalsIgnoreCase("DAY")) {
                            double dayss = (double) final_total / 86400;
                            NumberFormat formatter = new DecimalFormat("###.#");
                            String days = formatter.format(dayss);
                            actlWrkUnt_tiet.setText(days + "");
                            actlUnt_tiet.setText("DAY");
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                cursor.close();
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private String dateDisplayFormat(String date) {
        if (!date.equals("00000000")) {
            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
            Date date1;
            try {
                date1 = inputFormat.parse(date);
                return outputFormat.format(date1);
            } catch (ParseException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    private String dateFormat(String date) {
        if (date != null && !date.equals("")) {
            String inputPattern1 = "MMM dd, yyyy";
            String outputPattern1 = "yyyyMMdd";
            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            try {
                Date date1 = inputFormat1.parse(date);
                String Datefr = outputFormat1.format(date1);
                return Datefr;
            } catch (ParseException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    private String timeFormat(String date) {
        String tm = "";
        if(date.equals(""))
            return "000000";
        if (date != null && !date.equals("")) {
            String[] time = date.split(":");
            for (int i = 0; i < time.length; i++) {
                tm = tm + time[i];
            }
            return tm;
        } else {
            return "";
        }
    }


    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Orders_ConfirmPartial_Activity.this, getResources().getString(R.string.confirm_progs));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Orders_ConfirmPartial_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Response.equals("success"))
                new POST_TK_CONFIRM().execute();
            else {
                customProgressDialog.dismiss_progress_dialog();
                errorDialog.show_error_dialog(Orders_ConfirmPartial_Activity.this, getResources().getString(R.string.unable_confirm));
            }
        }
    }

    private class POST_TK_CONFIRM extends AsyncTask<Void, Integer, Void> {

        ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cop_al.clear();
            ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
            cop.setAufnr(oop.getOrdrId());
            cop.setVornr(oop.getOprtnId());
            cop.setConfNo("");
            cop.setConfText(cnfrmTxt_tiet.getText().toString());
            if (!actlWrkUnt_tiet.getText().toString().equals(""))
                cop.setActWork(actlWrkUnt_tiet.getText().toString());
            else
                cop.setActWork("0");
            cop.setUnWork(actlUnt_tiet.getText().toString());
            cop.setPlanWork("0");
            cop.setLearr("");
            cop.setBemot("");
            cop.setGrund(ReasonId);
            if (noRmngWrk_Cb.isChecked())
                cop.setLeknw("X");
            else
                cop.setLeknw("");
            if (fnlCnfrm_Cb.isChecked())
                cop.setAueru("X");
            else
                cop.setAueru("");
            cop.setAusor("");
            cop.setPernr(employee_tiet.getText().toString());
            cop.setLoart("");
            cop.setStatus("");
            cop.setRsnum("");
            cop.setRspos("");
            cop.setPosnr("");
            cop.setMatnr("");
            cop.setBwart("");
            cop.setPlant("");
            cop.setLgort("");
            cop.setErfmg("0");
            cop.setErfme("");
            cop.setExecStartDate(dateFormat(strtDt_tiet.getText().toString()));
            cop.setExecStartTime(timeFormat(strtTm_tiet.getText().toString()));
            cop.setExecFinDate(dateFormat(endDt_tiet.getText().toString()));
            cop.setExecFinTime(timeFormat(endTm_tiet.getText().toString()));
            cop.setPernr("");
            cop_al.add(cop);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Order_CConfirmation().Get_Data(Orders_ConfirmPartial_Activity.this, cop_al, null, "", "CNORD", oop.getOrdrId(), "pc");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("S"))
                dismissActivity(Orders_ConfirmPartial_Activity.this, Response.substring(2));
            else if (Response.startsWith("E"))
                errorDialog.show_error_dialog(Orders_ConfirmPartial_Activity.this, Response.substring(2));
            else
                errorDialog.show_error_dialog(Orders_ConfirmPartial_Activity.this, Response);

        }
    }

    private void confirmationDialog(String message) {
        final Dialog cancel_dialog = new Dialog(Orders_ConfirmPartial_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) cancel_dialog.findViewById(R.id.description_textview);
        description_textview.setText(message);
        Button confirm = (Button) cancel_dialog.findViewById(R.id.ok_button);
        Button cancel = (Button) cancel_dialog.findViewById(R.id.cancel_button);
        cancel_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cd = new ConnectionDetector(Orders_ConfirmPartial_Activity.this);
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    cancel_dialog.dismiss();
                    new GetToken().execute();
//                    new POST_TK_CONFIRM().execute();

                } else {
                    new Network_Connection_Dialog().show_network_connection_dialog(Orders_ConfirmPartial_Activity.this);
                }
            }
        });
    }

    public String dismissActivity(final Activity activity, String message)
    {
        final Dialog success_dialog = new Dialog(activity);
        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.success_checkmark).into(imageview);
        success_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                success_dialog.dismiss();
                activity.finish();
                Intent intent = new Intent(Orders_ConfirmPartial_Activity.this, Orders_Activity.class);
                startActivity(intent);
            }
        });
        return null;
    }
}
