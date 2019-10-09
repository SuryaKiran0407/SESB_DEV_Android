package com.enstrapp.fieldtekpro.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimePickerDialog extends Activity implements View.OnClickListener {

    DatePicker datePicker;
    TimePicker timePicker;
    Button cancel_button, add_button;
    int request_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetimedialog);
        this.setFinishOnTouchOutside(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String request_ids = extras.getString("request_id");
            if (request_ids != null && !request_ids.equals("")) {
                request_id = Integer.parseInt(request_ids);
            }
        }

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);

        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            DateTimePickerDialog.this.finish();
        } else if (v == add_button) {
            /*For Date*/
            String selected_date = "";
            String selected_date_formatted = "";
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "MMM dd, yyyy";
            String outputPattern1 = "yyyyMMdd";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            try {
                Date date = inputFormat.parse(year + "-" + month + "-" + day);
                //selected_date_formatted = year+""+month+""+day;
                selected_date_formatted = outputFormat1.format(date);
                selected_date = outputFormat.format(date);
            } catch (ParseException e) {
            }
            /*For Date*/

            /*For Time*/
            String hr = "";
            String mn = "";
            int hour = timePicker.getCurrentHour();
            if (hour < 10) {
                hr = "0" + hour;
            } else {
                hr = "" + hour;
            }
            int min = timePicker.getCurrentMinute();
            if (min < 10) {
                mn = "0" + min;
            } else {
                mn = "" + min;
            }
            /*For Time*/
            Intent intent = new Intent();
            intent.putExtra("date", selected_date);
            intent.putExtra("date_formatted", selected_date_formatted);
            intent.putExtra("time", hr + ":" + mn + ":" + "00");
            intent.putExtra("time_formatted", hr + "" + mn + "" + "00");
            setResult(request_id, intent);
            DateTimePickerDialog.this.finish();
        }
    }

}