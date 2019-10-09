package com.enstrapp.fieldtekpro.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDialog extends Activity implements View.OnClickListener {

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

        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            DatePickerDialog.this.finish();
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
                //selected_date = year+"-"+month+"-"+day;
                selected_date = outputFormat1.format(date);
                selected_date_formatted = outputFormat.format(date);
            } catch (ParseException e) {
            }
            /*For Date*/

            Intent intent = new Intent();
            intent.putExtra("date", selected_date);
            intent.putExtra("date_formatted", selected_date_formatted);
            setResult(RESULT_OK, intent);
            DatePickerDialog.this.finish();
        }
    }

}
