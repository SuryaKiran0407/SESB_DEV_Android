package com.enstrapp.fieldtekpro.DateTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.enstrapp.fieldtekpro.R;

public class TimePickerDialog extends Activity implements View.OnClickListener {

    TimePicker timePicker;
    Button cancel_button, add_button;
    String request_type = "";
    int request_id = 0;
    String givendate = "", startdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_dialog);
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

        timePicker = findViewById(R.id.timePicker);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        add_button = (Button) findViewById(R.id.add_button);

        /*if (givendate != null && !givendate.equals("") && !givendate.equals("00000000")) {
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
        }*/
        cancel_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            TimePickerDialog.this.finish();
        } else if (v == add_button) {
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
            Intent intent = new Intent();
            intent.putExtra("selectedTime", hr + ":" + mn + ":" + "00");
            setResult(RESULT_OK, intent);
            TimePickerDialog.this.finish();
        }
    }
}
