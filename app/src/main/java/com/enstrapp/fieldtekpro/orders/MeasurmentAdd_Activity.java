package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog;
import com.enstrapp.fieldtekpro.DateTime.TimePickerDialog;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeasurmentAdd_Activity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText mesurPnt_tiet, reding_tiet, date_tiet, time_tiet, valuation_tiet, notes_tiet;
    ImageView mesurPnt_iv, date_iv, time_iv, valuation_iv;
    RadioGroup result_rdg;
    CheckBox creAftTsk_Cb;
    Button cancel_bt, submit_bt;
    static final int DATE = 2349;
    static final int TIME = 2749;
    static final int MPTT = 2759;
    static final int VALUA = 2859;
    String equip = "";
    Measurement_Parceble mpo = new Measurement_Parceble();
    Error_Dialog errorDialog = new Error_Dialog();
    RadioButton normal_rb, alaram_rb, critical_rb;
    ImageButton back_ib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurmentadd_activity);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            equip = bundle.getString("equip");
        }

        back_ib = findViewById(R.id.back_ib);
        cancel_bt = findViewById(R.id.cancel_bt);
        normal_rb = findViewById(R.id.normal_rb);
        alaram_rb = findViewById(R.id.alaram_rb);
        critical_rb = findViewById(R.id.critical_rb);
        submit_bt = findViewById(R.id.submit_bt);
        creAftTsk_Cb = findViewById(R.id.creAftTsk_Cb);
        result_rdg = findViewById(R.id.result_rdg);
        mesurPnt_iv = findViewById(R.id.mesurPnt_iv);
        date_iv = findViewById(R.id.date_iv);
        time_iv = findViewById(R.id.time_iv);
        valuation_iv = findViewById(R.id.valuation_iv);
        mesurPnt_tiet = findViewById(R.id.mesurPnt_tiet);
        reding_tiet = findViewById(R.id.reding_tiet);
        date_tiet = findViewById(R.id.date_tiet);
        time_tiet = findViewById(R.id.time_tiet);
        valuation_tiet = findViewById(R.id.valuation_tiet);
        notes_tiet = findViewById(R.id.notes_tiet);

        cancel_bt.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        mesurPnt_iv.setOnClickListener(this);
        date_iv.setOnClickListener(this);
        time_iv.setOnClickListener(this);
        valuation_iv.setOnClickListener(this);
        creAftTsk_Cb.setOnClickListener(this);
        back_ib.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        String formattedDate1 = df1.format(c.getTime());
        time_tiet.setText(formattedDate1);
        date_tiet.setText(formattedDate);

        reding_tiet.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(10,
                2)});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                MeasurmentAdd_Activity.this.finish();
                break;

            case (R.id.submit_bt):
                if (mesurPnt_tiet.getText().toString() != null &&
                        !mesurPnt_tiet.getText().toString().equals("")) {
                    if (date_tiet.getText().toString() != null &&
                            !date_tiet.getText().toString().equals("")) {
                        if (time_tiet.getText().toString() != null &&
                                !time_tiet.getText().toString().equals("")) {
                            if (normal_rb.isChecked())
                                mpo.setAtbez("OK");
                            else if (alaram_rb.isChecked())
                                mpo.setAtbez("POK");
                            else if (critical_rb.isChecked())
                                mpo.setAtbez("NOK");
                            mpo.setMdtxt(notes_tiet.getText().toString());
                            mpo.setReadc(reding_tiet.getText().toString());

                            Intent submitIntent = new Intent();
                            submitIntent.putExtra("mpo", mpo);
                            setResult(RESULT_OK, submitIntent);
                            MeasurmentAdd_Activity.this.finish();
                        } else {
                            errorDialog.show_error_dialog(MeasurmentAdd_Activity.this,
                                    getString(R.string.time_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(MeasurmentAdd_Activity.this,
                                getString(R.string.date_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(MeasurmentAdd_Activity.this,
                            getString(R.string.measurment_mandate));
                }
                break;

            case (R.id.creAftTsk_Cb):
                if (creAftTsk_Cb.isChecked()) {
                    mpo.setDocaf("X");
                } else {
                    mpo.setDocaf("");
                }
                break;

            case (R.id.mesurPnt_iv):
                Intent mpTtLIntent = new Intent(MeasurmentAdd_Activity.this,
                        MeasurementPointList_Activity.class);
                mpTtLIntent.putExtra("equip", equip);
                startActivityForResult(mpTtLIntent, MPTT);
                break;

            case (R.id.date_iv):
                Intent stDtIntent = new Intent(MeasurmentAdd_Activity.this,
                        DatePickerDialog.class);
                startActivityForResult(stDtIntent, DATE);
                break;

            case (R.id.time_iv):
                Intent stTmIntent = new Intent(MeasurmentAdd_Activity.this,
                        TimePickerDialog.class);
                startActivityForResult(stTmIntent, TIME);
                break;

            case (R.id.valuation_iv):
                Intent valuIntent = new Intent(MeasurmentAdd_Activity.this,
                        Valuation_Activity.class);
                valuIntent.putExtra("equip", equip);
                startActivityForResult(valuIntent, VALUA);
                break;

            case (R.id.back_ib):
                setResult(RESULT_CANCELED);
                MeasurmentAdd_Activity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (MPTT):
                if (resultCode == RESULT_OK) {
                    mpo.setPoint(data.getStringExtra("mptt_id"));
                    mpo.setPttxt(data.getStringExtra("mptt_txt"));
                    mesurPnt_tiet
                            .setText(getString(R.string.hypen_text, data.getStringExtra("mptt_id"),
                                    data.getStringExtra("mptt_txt")));
                }
                break;

            case (DATE):
                if (resultCode == RESULT_OK) {
                    mpo.setIdate(data.getStringExtra("selectedDate"));
                    date_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TIME):
                if (resultCode == RESULT_OK) {
                    mpo.setItime(data.getStringExtra("selectedTime"));
                    time_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;

            case (VALUA):
                if (resultCode == RESULT_OK) {
                    mpo.setVlcod(data.getStringExtra("valu_id"));
                    valuation_tiet
                            .setText(getString(R.string.hypen_text, data.getStringExtra("valu_id"),
                                    data.getStringExtra("valu_txt")));
                }
                break;
        }
    }

    /*decimal places logic*/
    public class DecimalDigitsInputFilter implements InputFilter {
        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," +
                    (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }
    /*decimal places logic*/
}
