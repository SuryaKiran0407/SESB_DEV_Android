package com.enstrapp.fieldtekpro.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.enstrapp.fieldtekpro.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonthYearPickerDialog extends Activity implements View.OnClickListener {

    DatePicker datePicker;
    Button cancel_button, add_button;
    String request_type = "";
    int request_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datedialog);
        this.setFinishOnTouchOutside(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            request_type = extras.getString("request_type");
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
        }

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);

        int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
        if (daySpinnerId != 0) {
            View daySpinner = datePicker.findViewById(daySpinnerId);
            if (daySpinner != null) {
                daySpinner.setVisibility(View.GONE);
            }
        }

        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            MonthYearPickerDialog.this.finish();
        } else if (v == add_button) {
            String selected_month = "", selected_year = "", selected_month_formatted = "";
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "MM";
            String outputPattern1 = "yyyy";
            String outputPattern2 = "MMM";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            SimpleDateFormat outputFormat2 = new SimpleDateFormat(outputPattern2);
            try {
                Date date = inputFormat.parse(year + "-" + month + "-" + day);
                selected_month = outputFormat.format(date);
                selected_year = outputFormat1.format(date);
                selected_month_formatted = outputFormat2.format(date);
            } catch (ParseException e) {
            }
            Intent intent = new Intent();
            intent.putExtra("selected_month_formatted", selected_month_formatted);
            intent.putExtra("selected_month", selected_month);
            intent.putExtra("selected_year", selected_year);
            setResult(request_id, intent);
            MonthYearPickerDialog.this.finish();
        }
    }

}
