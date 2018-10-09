package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog;
import com.enstrapp.fieldtekpro.DateTime.TimePickerDialog;
import com.enstrapp.fieldtekpro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConfirmation extends AppCompatActivity implements View.OnClickListener {

    LinearLayout footer;
    ImageButton back_ib;
    TextInputEditText strtDt_tiet, strtTm_tiet, endDt_tiet, endTm_tiet, cnfrmTxt_tiet, employee_tiet;
    ImageView stDt_iv, stTm_iv, endDt_iv, endTm_iv;
    CheckBox noRmngWrk_Cb, fnlCnfrm_Cb;
    String type = "", slctStDt = "", slctStTm = "", slctEdDt = "", slctEdTm = "", slctCntrmTxt = "",
            slctEmply = "", slctNoRmWrk = "", slctFnlCnfrm = "";
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Button cancel_bt, save_bt;
    static final int EDDT = 548;
    static final int STDT = 648;
    static final int EDTM = 748;
    static final int STTM = 588;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeconfirmation);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            type = bundle.getString("type");
            slctStDt = bundle.getString("stDt");
            slctEdDt = bundle.getString("edDt");
            slctStTm = bundle.getString("stTm");
            slctEdTm = bundle.getString("edTm");
            slctNoRmWrk = bundle.getString("noRmWrk");
            slctFnlCnfrm = bundle.getString("fnlCnfrm");
            slctCntrmTxt = bundle.getString("cntrmTxt");
            slctEmply = bundle.getString("emply");
        }

        DATABASE_NAME = TimeConfirmation.this.getString(R.string.database_name);
        App_db = TimeConfirmation.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        footer = findViewById(R.id.footer);
        back_ib = findViewById(R.id.back_ib);
        strtDt_tiet = findViewById(R.id.strtDt_tiet);
        strtTm_tiet = findViewById(R.id.strtTm_tiet);
        endDt_tiet = findViewById(R.id.endDt_tiet);
        endTm_tiet = findViewById(R.id.endTm_tiet);
        cnfrmTxt_tiet = findViewById(R.id.cnfrmTxt_tiet);
        employee_tiet = findViewById(R.id.employee_tiet);
        stDt_iv = findViewById(R.id.stDt_iv);
        stTm_iv = findViewById(R.id.stTm_iv);
        endDt_iv = findViewById(R.id.endDt_iv);
        endTm_iv = findViewById(R.id.endTm_iv);
        noRmngWrk_Cb = findViewById(R.id.noRmngWrk_Cb);
        fnlCnfrm_Cb = findViewById(R.id.fnlCnfrm_Cb);
        save_bt = findViewById(R.id.save_bt);
        cancel_bt = findViewById(R.id.cancel_bt);

        cancel_bt.setOnClickListener(this);
        save_bt.setOnClickListener(this);
        endDt_iv.setOnClickListener(this);
        endTm_iv.setOnClickListener(this);
        stDt_iv.setOnClickListener(this);
        stTm_iv.setOnClickListener(this);
        back_ib.setOnClickListener(this);
        getDisplayData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.cancel_bt):
                if (type.equals("D"))
                    TimeConfirmation.this.finish();
                else {
                    setResult(RESULT_CANCELED);
                    TimeConfirmation.this.finish();
                }
                break;

            case (R.id.save_bt):
                Intent intent = new Intent();
                intent.putExtra("stDt", dateFormat(strtDt_tiet.getText().toString()));
                intent.putExtra("edDt", dateFormat(endDt_tiet.getText().toString()));
                intent.putExtra("stTm", timeFormat(strtTm_tiet.getText().toString()));
                intent.putExtra("edTm", timeFormat(endTm_tiet.getText().toString()));
                if (noRmngWrk_Cb.isChecked())
                    intent.putExtra("noRmWrk", "X");
                else
                    intent.putExtra("noRmWrk", "c");
                if (fnlCnfrm_Cb.isChecked())
                    intent.putExtra("fnlCnfrm", "X");
                else
                    intent.putExtra("fnlCnfrm", "c");
                intent.putExtra("cntrmTxt", cnfrmTxt_tiet.getText().toString());
                intent.putExtra("emply", employee_tiet.getText().toString());
                setResult(RESULT_OK, intent);
                TimeConfirmation.this.finish();
                break;

            case (R.id.endDt_iv):
                Intent edDtIntent = new Intent(TimeConfirmation.this, DatePickerDialog.class);
                startActivityForResult(edDtIntent, EDDT);
                break;

            case (R.id.endTm_iv):
                Intent edTmIntent = new Intent(TimeConfirmation.this, TimePickerDialog.class);
                startActivityForResult(edTmIntent, EDTM);
                break;

            case (R.id.stDt_iv):
                Intent stDtIntent = new Intent(TimeConfirmation.this, DatePickerDialog.class);
                startActivityForResult(stDtIntent, STDT);
                break;

            case (R.id.stTm_iv):
                Intent stTmIntent = new Intent(TimeConfirmation.this, TimePickerDialog.class);
                startActivityForResult(stTmIntent, STTM);
                break;

            case (R.id.back_ib):
                if (type.equals("D"))
                    TimeConfirmation.this.finish();
                else {
                    setResult(RESULT_CANCELED);
                    TimeConfirmation.this.finish();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (EDDT):
                if (resultCode == RESULT_OK) {
                    endDt_tiet.setText(data.getStringExtra("date_formatted"));
                }
                break;
            case (STDT):
                if (resultCode == RESULT_OK) {
                    strtDt_tiet.setText(data.getStringExtra("date_formatted"));
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
                break;
        }
    }

    private void getDisplayData() {
        if (type.equals("D")) {
            footer.setVisibility(View.GONE);
            stDt_iv.setEnabled(false);
            endDt_iv.setEnabled(false);
            stTm_iv.setEnabled(false);
            endTm_iv.setEnabled(false);
            noRmngWrk_Cb.setEnabled(false);
            noRmngWrk_Cb.setTextColor(getResources().getColor(R.color.dark_grey));
            fnlCnfrm_Cb.setEnabled(false);
            fnlCnfrm_Cb.setTextColor(getResources().getColor(R.color.dark_grey));
            cnfrmTxt_tiet.setEnabled(false);
            cnfrmTxt_tiet.setTextColor(getResources().getColor(R.color.dark_grey));
            employee_tiet.setEnabled(false);
            employee_tiet.setTextColor(getResources().getColor(R.color.dark_grey));
        }

        if (slctStDt != null && !slctStDt.equals(""))
            strtDt_tiet.setText(dateDisplayFormat(slctStDt));

        if (slctEdDt != null && !slctEdDt.equals(""))
            endDt_tiet.setText(dateDisplayFormat(slctEdDt));

        if (slctStTm != null && !slctStTm.equals(""))
            strtTm_tiet.setText(timeDisplayFormat(slctStTm));

        if (slctEdTm != null && !slctEdTm.equals(""))
            endTm_tiet.setText(timeDisplayFormat(slctEdTm));

        if (slctNoRmWrk != null && !slctNoRmWrk.equals(""))
            if (slctNoRmWrk.equals("X"))
                noRmngWrk_Cb.setChecked(true);
            else
                noRmngWrk_Cb.setChecked(false);
        else
            noRmngWrk_Cb.setChecked(true);

        if (slctFnlCnfrm != null && !slctFnlCnfrm.equals(""))
            if (slctFnlCnfrm.equals("X"))
                fnlCnfrm_Cb.setChecked(true);
            else
                fnlCnfrm_Cb.setChecked(false);
        else
            fnlCnfrm_Cb.setChecked(true);

        if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
            cnfrmTxt_tiet.setText(slctCntrmTxt);

        if (slctEmply != null && !slctEmply.equals(""))
            employee_tiet.setText(slctEmply);

    }

    private String dateDisplayFormat(String date) {
        String inputPattern = "yyyyMMdd";
        String outputPattern = "MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date d = inputFormat.parse(date);
            return outputFormat.format(d);
        } catch (ParseException e) {
            return date;
        }
    }

    private String timeDisplayFormat(String date) {
        String inputPattern = "HHmmss";
        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date d = inputFormat.parse(date);
            return outputFormat.format(d);
        } catch (ParseException e) {
            return date;
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


}
