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

public class DatePickerDialog1 extends Activity implements View.OnClickListener {

    DatePicker datePicker;
    Button cancel_button, add_button;
    String request_type = "";
    int request_id = 0;
    String givendate = "", startdate = "";

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
            givendate = extras.getString("givenDate");
            startdate = extras.getString("startDate");
        }

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);

        if (givendate != null && !givendate.equals("") && !givendate.equals("00000000")) {
            try {
                String[] dk = new SimpleDateFormat("dd-MM-yyyy")
                        .format(new SimpleDateFormat("MMM dd, yyyy")
                                .parse(givendate)).split("-");
                datePicker.updateDate(Integer.parseInt(dk[2]), Integer.parseInt(dk[1]) - 1, Integer.parseInt(dk[0]));
                if (startdate != null && !startdate.equals("") && !startdate.equals("00000000"))
                    datePicker.setMinDate(new SimpleDateFormat("MMM dd, yyyy")
                            .parse(startdate).getTime());
            } catch (Exception e) {
            }
        }
        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            DatePickerDialog1.this.finish();
        } else if (v == add_button) {
            /*For Date*/
            String selected_date = "";
            String selected_date_formatted = "";
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "MMM dd, yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            try {
                Date date = inputFormat.parse(year + "-" + month + "-" + day);
                //selected_date = year+"-"+month+"-"+day;
                selected_date_formatted = outputFormat.format(date);
            } catch (ParseException e) {
            }
            /*For Date*/

            Intent intent = new Intent();
            intent.putExtra("selectedDate", selected_date_formatted);
            setResult(RESULT_OK, intent);
            DatePickerDialog1.this.finish();
        }
    }

}
