package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Operations_Add_Update_Activity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
    TextInputEditText start_date_edittext, end_date_edittext, oprtnShrtTxt_tiet, duration_tiet, durationUnit_tiet, plant_tiet, wrkCntr_tiet;
    ImageView duration_iv, wrkCntr_iv;
    OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();

    String plant_id = "", ordrWrkCntr = "";
    Button cancel_bt, submit_bt, operations_custominfo_button;
    static final int DURATION = 110;
    static final int PLANT_WRKCNTR = 120;
    static final int OPERATION_CUSTOMINFO = 130;
    Error_Dialog errorDialog = new Error_Dialog();
    Bundle bundle;

    /*Written By SuryaKiran for Custom Info*/
    ArrayList<HashMap<String, String>> selected_operation_custom_info_arraylist = new ArrayList<>();
    /*Written By SuryaKiran for Custom Info*/

    TextInputLayout start_date_layout, end_date_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_operation_create_change);

        oprtnShrtTxt_tiet = findViewById(R.id.oprtnShrtTxt_tiet);
        duration_tiet = findViewById(R.id.duration_tiet);
        durationUnit_tiet = findViewById(R.id.durationUnit_tiet);
        plant_tiet = findViewById(R.id.plant_tiet);
        wrkCntr_tiet = findViewById(R.id.wrkCntr_tiet);
        duration_iv = findViewById(R.id.duration_iv);
        wrkCntr_iv = findViewById(R.id.wrkCntr_iv);
        toolBar = findViewById(R.id.toolBar);
        submit_bt = findViewById(R.id.submit_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        start_date_layout = (TextInputLayout)findViewById(R.id.start_date_layout);
        end_date_layout = (TextInputLayout)findViewById(R.id.end_date_layout);
        start_date_edittext = (TextInputEditText)findViewById(R.id.start_date_edittext);
        end_date_edittext = (TextInputEditText)findViewById(R.id.end_date_edittext);

        /*Written By SuryaKiran for Custom Info*/
        operations_custominfo_button = (Button)findViewById(R.id.operations_custominfo_button);
        /*Written By SuryaKiran for Custom Info*/

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        /*Written By SuryaKiran for Custom Info*/
        selected_operation_custom_info_arraylist.clear();
        /*Written By SuryaKiran for Custom Info*/

        bundle = getIntent().getExtras();

        if (bundle != null)
        {
            plant_id = "";
            if (bundle.getString("type_oprtn").equalsIgnoreCase("C"))
            {
                plant_tiet.setText(bundle.getString("ordrPlant"));
                wrkCntr_tiet.setText(bundle.getString("ordrWrkCntr"));
                plant_id = bundle.getString("ordrPlant");
                oop.setPlantId(bundle.getString("ordrPlant"));
                oop.setPlantTxt(bundle.getString("ordrPlantName"));
                oop.setWrkCntrId(bundle.getString("ordrWrkCntr"));
                oop.setWrkCntrTxt(bundle.getString("ordrWrkCntrName"));
            }
            else
            {
                oop = bundle.getParcelable("cnfoprtn");
                oprtnShrtTxt_tiet.setText(oop.getOprtnShrtTxt());
                duration_tiet.setText(oop.getDuration());
                plant_id = oop.getPlantId();
                plant_tiet.setText(oop.getPlantId());
                wrkCntr_tiet.setText(oop.getWrkCntrId());
                durationUnit_tiet.setText(oop.getDurationUnit());
                submit_bt.setText(getResources().getString(R.string.update));


                String action = oop.getStatus();
                if(action.equalsIgnoreCase("I"))
                {
                }
                else
                {
                    String StartDate_format = "", starttime_format = "";
                    String startdate = oop.getFsavd();
                    if (startdate.equals("00000000"))
                    {
                        start_date_layout.setVisibility(View.GONE);
                        start_date_edittext.setText("");
                    }
                    else
                    {
                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                        Date date1;
                        try
                        {
                            date1 = inputFormat.parse(startdate);
                            String outputDateStr = outputFormat.format(date1);
                            StartDate_format = outputDateStr;

                            String starttime = oop.getUsr02();
                            if (starttime.equals("000000"))
                            {
                                starttime_format = "";
                            }
                            else
                            {
                                String inputPattern1 = "HHmmss";
                                String outputPattern1 = "HH:mm:ss";
                                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                                SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                                Date date2 = inputFormat1.parse(starttime);
                                starttime_format = outputFormat1.format(date2);
                            }
                        }
                        catch (ParseException e)
                        {
                            StartDate_format = "";
                            starttime_format = "";
                        }
                        start_date_edittext.setText(StartDate_format+"   "+starttime_format);
                        start_date_layout.setVisibility(View.VISIBLE);
                    }


                    String EndDate_format = "", endtime_format = "";
                    String enddate = oop.getSsedd();
                    if (enddate.equals("00000000"))
                    {
                        end_date_layout.setVisibility(View.GONE);
                        end_date_edittext.setText("");
                    }
                    else
                    {
                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                        Date date1;
                        try
                        {
                            date1 = inputFormat.parse(enddate);
                            String outputDateStr = outputFormat.format(date1);
                            EndDate_format = outputDateStr;

                            String endtime = oop.getUsr03();
                            if (endtime.equals("000000"))
                            {
                                endtime_format = "";
                            }
                            else
                            {
                                String inputPattern1 = "HHmmss";
                                String outputPattern1 = "HH:mm:ss";
                                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                                SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                                Date date2 = inputFormat1.parse(endtime);
                                endtime_format = outputFormat1.format(date2);
                            }
                        }
                        catch (ParseException e)
                        {
                            EndDate_format = "";
                            endtime_format = "";
                        }
                        end_date_edittext.setText(EndDate_format+"   "+endtime_format);
                        end_date_layout.setVisibility(View.VISIBLE);
                    }

                }


                /*Written By SuryaKiran for Custom Info*/
                selected_operation_custom_info_arraylist = (ArrayList<HashMap<String, String>>)getIntent().getSerializableExtra("selected_operation_custom_info_arraylist");
                /*Written By SuryaKiran for Custom Info*/

            }
        }

        duration_iv.setOnClickListener(this);
        wrkCntr_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);

        /*Written By SuryaKiran for Custom Info*/
        operations_custominfo_button.setOnClickListener(this);
        /*Written By SuryaKiran for Custom Info*/
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*Written By SuryaKiran for Custom Info OnCLick*/
            case (R.id.operations_custominfo_button):
                Intent custominfo_intent = new Intent(Operations_Add_Update_Activity.this, CustomInfo_Activity.class);
                custominfo_intent.putExtra("Zdoctype","W");
                custominfo_intent.putExtra("ZdoctypeItem","WO");
                custominfo_intent.putExtra("custom_info_arraylist",selected_operation_custom_info_arraylist);
                custominfo_intent.putExtra("request_id", Integer.toString(OPERATION_CUSTOMINFO));
                startActivityForResult(custominfo_intent,OPERATION_CUSTOMINFO);
                break;
            /*Written By SuryaKiran for Custom Info OnCLick*/


            case (R.id.duration_iv):
                Intent durationIntent = new Intent(Operations_Add_Update_Activity.this, Duration_Activity.class);
                startActivityForResult(durationIntent, DURATION);
                break;

            case (R.id.wrkCntr_iv):
                Intent wrkCntrIntent = new Intent(Operations_Add_Update_Activity.this, WrkCntrPlant_Activity.class);
                wrkCntrIntent.putExtra("werks", plant_id);
                startActivityForResult(wrkCntrIntent, PLANT_WRKCNTR);
                break;

            case (R.id.submit_bt):
                if (bundle.getString("type_oprtn").equalsIgnoreCase("C")) {
                    if (!oprtnShrtTxt_tiet.getText().toString().equals("")) {
                        oop.setOprtnShrtTxt(oprtnShrtTxt_tiet.getText().toString());
                        if (duration_tiet.getText().toString().equals(""))
                            oop.setDuration("0");
                        else
                            oop.setDuration(duration_tiet.getText().toString());
                        oop.setOprtnLngTxt(oprtnShrtTxt_tiet.getText().toString());
                        oop.setDurationUnit(durationUnit_tiet.getText().toString());
                        oop.setStatus("I");
                        Intent intent = new Intent();
                        intent.putExtra("oop", oop);
                        intent.putExtra("selected_operation_custom_info_arraylist", selected_operation_custom_info_arraylist);
                        setResult(RESULT_OK, intent);
                        Operations_Add_Update_Activity.this.finish();
                    } else {
                        errorDialog.show_error_dialog(Operations_Add_Update_Activity.this, getResources().getString(R.string.oprtnTxt_mandate));
                    }
                } else {
                    if (!oprtnShrtTxt_tiet.getText().toString().equals("")) {
                        oop.setOprtnShrtTxt(oprtnShrtTxt_tiet.getText().toString());
                        oop.setDuration(duration_tiet.getText().toString());
                        oop.setOprtnLngTxt(oprtnShrtTxt_tiet.getText().toString());
                        oop.setDurationUnit(durationUnit_tiet.getText().toString());
                        if (!oop.getStatus().equals("I"))
                            oop.setStatus("U");
                        Intent intent = new Intent();
                        intent.putExtra("oop", oop);
                        intent.putExtra("selected_operation_custom_info_arraylist", selected_operation_custom_info_arraylist);
                        setResult(RESULT_OK, intent);
                        Operations_Add_Update_Activity.this.finish();
                    } else {
                        errorDialog.show_error_dialog(Operations_Add_Update_Activity.this, getResources().getString(R.string.oprtnTxt_mandate));
                    }
                }
                break;

            case (R.id.cancel_bt):
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                Operations_Add_Update_Activity.this.finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (DURATION):
                if (resultCode == RESULT_OK) {
                    oop.setDurationUnit(data.getStringExtra("duration_unit"));
                    durationUnit_tiet.setText(data.getStringExtra("duration_unit"));
                }
                break;
            case (PLANT_WRKCNTR):
                if (resultCode == RESULT_OK) {
                    oop.setPlantId(data.getStringExtra("plant_id"));
                    oop.setPlantTxt(data.getStringExtra("plant_txt"));
                    oop.setWrkCntrId(data.getStringExtra("wrkCntr_id"));
                    oop.setWrkCntrTxt(data.getStringExtra("wrkCntr_txt"));
                    plant_tiet.setText(data.getStringExtra("plant_id"));
                    wrkCntr_tiet.setText(data.getStringExtra("wrkCntr_id"));
                }
                break;


            /*Written By SuryaKiran for Custom Info Added onActivityResult*/
            case (OPERATION_CUSTOMINFO):
                if (resultCode == RESULT_OK)
                {
                    selected_operation_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("selected_custom_info_arraylist");
                }
                break;
            /*Written By SuryaKiran for Custom Info Added onActivityResult*/


        }
    }
}
