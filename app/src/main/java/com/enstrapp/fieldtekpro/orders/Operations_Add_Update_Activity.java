package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Operations_Add_Update_Activity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
    TextInputEditText calculationkey_edittext, number_edittext, start_date_edittext, activitytype_edittext, end_date_edittext, oprtnShrtTxt_tiet, duration_tiet,
            costelement_tiet, controlkey_tiet, durationUnit_tiet, plant_tiet, wrkCntr_tiet;
    ImageView calculationkey_imageview, activitytype_imageview, costelement_iv, controlkey_iv, duration_iv, wrkCntr_iv, longtext_imageview;
    OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
    String plant_id = "", longtext_text = "", order_id = "";
    Button cancel_bt, submit_bt, operations_custominfo_button;
    static final int DURATION = 110;
    static final int controlkey = 111;
    static final int PLANT_WRKCNTR = 120;
    static final int COST_ELEMENT = 121;
    static final int ACTIVITY_TYPE = 122;
    static final int CALCUATIONKEY_TYPE = 123;
    static final int OPERATION_CUSTOMINFO = 130;
    Error_Dialog errorDialog = new Error_Dialog();
    Bundle bundle;
    /*Written By SuryaKiran for Custom Info*/
    ArrayList<HashMap<String, String>> selected_operation_custom_info_arraylist = new ArrayList<>();
    /*Written By SuryaKiran for Custom Info*/
    TextInputLayout start_date_layout, end_date_layout;
    private static final int long_text = 150;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_operation_create_change);

        oprtnShrtTxt_tiet = findViewById(R.id.oprtnShrtTxt_tiet);
        number_edittext = findViewById(R.id.number_edittext);
        calculationkey_edittext = findViewById(R.id.calculationkey_edittext);
        duration_tiet = findViewById(R.id.duration_tiet);
        controlkey_tiet = findViewById(R.id.controlkey_tiet);
        costelement_tiet = findViewById(R.id.costelement_tiet);
        durationUnit_tiet = findViewById(R.id.durationUnit_tiet);
        plant_tiet = findViewById(R.id.plant_tiet);
        wrkCntr_tiet = findViewById(R.id.wrkCntr_tiet);
        duration_iv = findViewById(R.id.duration_iv);
        wrkCntr_iv = findViewById(R.id.wrkCntr_iv);
        toolBar = findViewById(R.id.toolBar);
        submit_bt = findViewById(R.id.submit_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        start_date_layout = (TextInputLayout) findViewById(R.id.start_date_layout);
        end_date_layout = (TextInputLayout) findViewById(R.id.end_date_layout);
        start_date_edittext = (TextInputEditText) findViewById(R.id.start_date_edittext);
        end_date_edittext = (TextInputEditText) findViewById(R.id.end_date_edittext);
        activitytype_edittext = (TextInputEditText) findViewById(R.id.activitytype_edittext);
        longtext_imageview = (ImageView) findViewById(R.id.longtext_imageview);
        controlkey_iv = (ImageView) findViewById(R.id.controlkey_iv);
        costelement_iv = (ImageView) findViewById(R.id.costelement_iv);
        activitytype_imageview = (ImageView) findViewById(R.id.activitytype_imageview);
        calculationkey_imageview = (ImageView) findViewById(R.id.calculationkey_imageview);


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Operations_Add_Update_Activity.this.openOrCreateDatabase(DATABASE_NAME,
                Context.MODE_PRIVATE, null);

        /*Written By SuryaKiran for Custom Info*/
        operations_custominfo_button = (Button) findViewById(R.id.operations_custominfo_button);
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
            order_id = bundle.getString("order_id");
            if (bundle.getString("type_oprtn").equalsIgnoreCase("C"))
            {
                plant_tiet.setText(bundle.getString("ordrPlant"));
                wrkCntr_tiet.setText(bundle.getString("ordrWrkCntr"));
                plant_id = bundle.getString("ordrPlant");
                oop.setPlantId(bundle.getString("ordrPlant"));
                oop.setPlantTxt(bundle.getString("ordrPlantName"));
                oop.setWrkCntrId(bundle.getString("ordrWrkCntr"));
                oop.setWrkCntrTxt(bundle.getString("ordrWrkCntrName"));
                try
                {
                    Cursor cursor = FieldTekPro_db.rawQuery("select * from ETLSTAR order by id",null);
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        cursor.moveToFirst();
                        activitytype_edittext.setText(cursor.getString(4)+" - "+cursor.getString(5));
                        oop.setLarnt(cursor.getString(4));
                        oop.setLarnt_text(cursor.getString(5));
                    }
                    else
                    {
                        cursor.close();
                    }
                }
                catch (Exception e)
                {
                }
            }
            else
            {
                oop = bundle.getParcelable("cnfoprtn");
                if (oop.getOprtnLngTxt() != null && !oop.getOprtnLngTxt().equals("")) {
                    //longtext_text = oop.getOprtnLngTxt();
                }
                oprtnShrtTxt_tiet.setText(oop.getOprtnShrtTxt());
                duration_tiet.setText(oop.getDuration());
                plant_id = oop.getPlantId();
                durationUnit_tiet.setText(oop.getDurationUnit());
                submit_bt.setText(getResources().getString(R.string.update));

                String plant_text = "";
                try {
                    String plant_id = oop.getPlantId();
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_PLANTS where " +
                            "Werks = ?", new String[]{plant_id});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                plant_text = cursor1.getString(2);
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                } catch (Exception e) {
                }
                plant_tiet.setText(oop.getPlantId() + " - " + plant_text);

                String loc_text = "";
                try {
                    String loc_id = oop.getWrkCntrId();
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_WKCTR where" +
                            " Arbpl = ?", new String[]{loc_id});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                loc_text = cursor1.getString(8);
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                    }
                } catch (Exception e) {
                }
                wrkCntr_tiet.setText(oop.getWrkCntrId() + " - " + loc_text);

                controlkey_tiet.setText(oop.getCntrlKeyId()+ " - " + oop.getCntrlKeyTxt());

                costelement_tiet.setText(oop.getCostelement());

                activitytype_edittext.setText(oop.getLarnt());

                number_edittext.setText(oop.getUsr02());


                if (oop.getUsr03() != null && !oop.getUsr03().equals(""))
                {
                    String calkey = oop.getUsr03();
                    if(calkey.equalsIgnoreCase("0"))
                    {
                        calculationkey_edittext.setText(oop.getUsr03()+" "+"Maintain Manually");
                    }
                    else if(calkey.equalsIgnoreCase("1"))
                    {
                        calculationkey_edittext.setText(oop.getUsr03()+" "+"Calculation Duration");
                    }
                    else if(calkey.equalsIgnoreCase("2"))
                    {
                        calculationkey_edittext.setText(oop.getUsr03()+" "+"Calculation Work");
                    }
                    else if(calkey.equalsIgnoreCase("3"))
                    {
                        calculationkey_edittext.setText(oop.getUsr03()+" "+"Calculation Number Of Capacities");
                    }
                }

                String action = oop.getStatus();
                if (action.equalsIgnoreCase("I")) {
                }
                else
                {
                    String StartDate_format = "", starttime_format = "";
                    String startdate = oop.getFsavd();
                    if (startdate.equals("00000000")) {
                        start_date_layout.setVisibility(View.GONE);
                        start_date_edittext.setText("");
                    } else {
                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                        Date date1;
                        try {
                            date1 = inputFormat.parse(startdate);
                            String outputDateStr = outputFormat.format(date1);
                            StartDate_format = outputDateStr;

                            /*String starttime = oop.getUsr02();
                            if (starttime.equals("000000")) {
                                starttime_format = "";
                            } else {
                                String inputPattern1 = "HHmmss";
                                String outputPattern1 = "HH:mm:ss";
                                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                                SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                                Date date2 = inputFormat1.parse(starttime);
                                starttime_format = outputFormat1.format(date2);
                            }*/
                        } catch (ParseException e) {
                            StartDate_format = "";
                            starttime_format = "";
                        }
                        //start_date_edittext.setText(StartDate_format + "   " + starttime_format);
                        start_date_edittext.setText(StartDate_format);
                        start_date_layout.setVisibility(View.VISIBLE);
                    }

                    String EndDate_format = "", endtime_format = "";
                    String enddate = oop.getSsedd();
                    if (enddate.equals("00000000")) {
                        end_date_layout.setVisibility(View.GONE);
                        end_date_edittext.setText("");
                    } else {
                        DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                        DateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
                        Date date1;
                        try {
                            date1 = inputFormat.parse(enddate);
                            String outputDateStr = outputFormat.format(date1);
                            EndDate_format = outputDateStr;

                            /*String endtime = oop.getUsr03();
                            if (endtime.equals("000000")) {
                                endtime_format = "";
                            } else {
                                String inputPattern1 = "HHmmss";
                                String outputPattern1 = "HH:mm:ss";
                                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                                SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
                                Date date2 = inputFormat1.parse(endtime);
                                endtime_format = outputFormat1.format(date2);
                            }*/
                        } catch (ParseException e) {
                            EndDate_format = "";
                            endtime_format = "";
                        }
                        //end_date_edittext.setText(EndDate_format + "   " + endtime_format);
                        end_date_edittext.setText(EndDate_format);
                        end_date_layout.setVisibility(View.VISIBLE);
                    }
                }

                /*Written By SuryaKiran for Custom Info*/
                selected_operation_custom_info_arraylist =
                        (ArrayList<HashMap<String, String>>) getIntent()
                                .getSerializableExtra("selected_operation_custom_info_arraylist");
                /*Written By SuryaKiran for Custom Info*/
            }
        }

        duration_iv.setOnClickListener(this);
        wrkCntr_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        longtext_imageview.setOnClickListener(this);
        controlkey_iv.setOnClickListener(this);
        costelement_iv.setOnClickListener(this);
        activitytype_imageview.setOnClickListener(this);
        calculationkey_imageview.setOnClickListener(this);

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

            /*Written By SuryaKiran for Operation Long Text OnCLick*/
            case (R.id.longtext_imageview):
                Intent longtext_intent = new Intent(Operations_Add_Update_Activity.this,Order_LongText_Activity.class);
                longtext_intent.putExtra("aufnr", order_id);
                longtext_intent.putExtra("operation_id", oop.getOprtnId());
                longtext_intent.putExtra("tdid", "");
                longtext_intent.putExtra("longtext_new", longtext_text);
                startActivityForResult(longtext_intent, long_text);
                break;
            /*Written By SuryaKiran for Operation Long Text OnCLick*/


            case (R.id.controlkey_iv):
                Intent controlkeyIntent = new Intent(Operations_Add_Update_Activity.this,ControlKey_Activity.class);
                startActivityForResult(controlkeyIntent, controlkey);
                break;

            case (R.id.duration_iv):
                Intent durationIntent = new Intent(Operations_Add_Update_Activity.this,Duration_Activity.class);
                startActivityForResult(durationIntent, DURATION);
                break;

            case (R.id.costelement_iv):
                Intent intent1 = new Intent(Operations_Add_Update_Activity.this,CostElement_Activity.class);
                intent1.putExtra("werks", plant_id);
                startActivityForResult(intent1, COST_ELEMENT);
                break;


            case (R.id.activitytype_imageview):
                Intent intent2 = new Intent(Operations_Add_Update_Activity.this,ActivityType_Activity.class);
                startActivityForResult(intent2, ACTIVITY_TYPE);
                break;


            case (R.id.calculationkey_imageview):
                Intent intent3 = new Intent(Operations_Add_Update_Activity.this,CalculationKey_Activity.class);
                startActivityForResult(intent3, CALCUATIONKEY_TYPE);
                break;


            case (R.id.wrkCntr_iv):
                Intent wrkCntrIntent = new Intent(Operations_Add_Update_Activity.this,WrkCntrPlant_Activity.class);
                wrkCntrIntent.putExtra("werks", plant_id);
                startActivityForResult(wrkCntrIntent, PLANT_WRKCNTR);
                break;

            case (R.id.submit_bt):
                if (bundle.getString("type_oprtn").equalsIgnoreCase("C"))
                {
                    if (!oprtnShrtTxt_tiet.getText().toString().equals(""))
                    {
                        oop.setOprtnShrtTxt(oprtnShrtTxt_tiet.getText().toString());
                        if (duration_tiet.getText().toString().equals(""))
                            oop.setDuration("0");
                        else
                            oop.setDuration(duration_tiet.getText().toString());
                        oop.setOprtnLngTxt(longtext_text);
                        oop.setDurationUnit(durationUnit_tiet.getText().toString());
                        oop.setStatus("I");
                        oop.setUsr02(number_edittext.getText().toString());
                        Intent intent = new Intent();
                        intent.putExtra("oop", oop);
                        intent.putExtra("selected_operation_custom_info_arraylist",selected_operation_custom_info_arraylist);
                        setResult(RESULT_OK, intent);
                        Operations_Add_Update_Activity.this.finish();
                    }
                    else
                    {
                        errorDialog.show_error_dialog(Operations_Add_Update_Activity.this,
                                getResources().getString(R.string.oprtnTxt_mandate));
                    }
                }
                else
                {
                    if (!oprtnShrtTxt_tiet.getText().toString().equals("")) {
                        oop.setOprtnShrtTxt(oprtnShrtTxt_tiet.getText().toString());
                        oop.setDuration(duration_tiet.getText().toString());
                        oop.setOprtnLngTxt(oop.getOprtnLngTxt());
                        oop.setDurationUnit(durationUnit_tiet.getText().toString());
                        oop.setUsr02(number_edittext.getText().toString());
                        if (!oop.getStatus().equals("I"))
                            oop.setStatus("U");
                        Intent intent = new Intent();
                        intent.putExtra("oop", oop);
                        intent.putExtra("selected_operation_custom_info_arraylist",
                                selected_operation_custom_info_arraylist);
                        setResult(RESULT_OK, intent);
                        Operations_Add_Update_Activity.this.finish();
                    }
                    else
                    {
                        errorDialog.show_error_dialog(Operations_Add_Update_Activity.this,
                                getResources().getString(R.string.oprtnTxt_mandate));
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

            case (controlkey):
                if (resultCode == RESULT_OK)
                {
                    oop.setCntrlKeyId(data.getStringExtra("controlkey_id"));
                    oop.setCntrlKeyTxt(data.getStringExtra("controlkey_text"));
                    controlkey_tiet.setText(data.getStringExtra("controlkey_id")+" - "+data.getStringExtra("controlkey_text"));
                }
                break;


            case (COST_ELEMENT):
                if (resultCode == RESULT_OK)
                {
                    oop.setCostelement(data.getStringExtra("costelement_id"));
                    oop.setCostelement_text(data.getStringExtra("costelement_text"));
                    costelement_tiet.setText(data.getStringExtra("costelement_id")+" - "+data.getStringExtra("costelement_text"));
                }
                break;




            case (CALCUATIONKEY_TYPE):
                if (resultCode == RESULT_OK)
                {
                    oop.setUsr03(data.getStringExtra("calculationkey_id"));
                    oop.setUsr03_text(data.getStringExtra("calculationkey_text"));
                    calculationkey_edittext.setText(data.getStringExtra("calculationkey_id")+" - "+data.getStringExtra("calculationkey_text"));
                }
                break;


            case (ACTIVITY_TYPE):
                if (resultCode == RESULT_OK)
                {
                    oop.setLarnt(data.getStringExtra("activitytype_id"));
                    oop.setLarnt_text(data.getStringExtra("activitytype_text"));
                    activitytype_edittext.setText(data.getStringExtra("activitytype_id")+" - "+data.getStringExtra("activitytype_text"));
                }
                break;


            case (PLANT_WRKCNTR):
                if (resultCode == RESULT_OK) {
                    oop.setPlantId(data.getStringExtra("plant_id"));
                    oop.setPlantTxt(data.getStringExtra("plant_txt"));
                    oop.setWrkCntrId(data.getStringExtra("wrkCntr_id"));
                    oop.setWrkCntrTxt(data.getStringExtra("wrkCntr_txt"));

                    String plant_text = "";
                    try {
                        String plant_id = data.getStringExtra("plant_id");
                        Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_PLANTS " +
                                "where Werks = ?", new String[]{plant_id});
                        if (cursor1 != null && cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    plant_text = cursor1.getString(2);
                                }
                                while (cursor1.moveToNext());
                            }
                        } else {
                            cursor1.close();
                        }
                    } catch (Exception e) {
                    }
                    plant_tiet.setText(data.getStringExtra("plant_id") + " - " + plant_text);

                    String loc_text = "";
                    try {
                        String loc_id = data.getStringExtra("wrkCntr_id");
                        Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_WKCTR where" +
                                " Arbpl = ?", new String[]{loc_id});
                        if (cursor1 != null && cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                do {
                                    loc_text = cursor1.getString(8);
                                }
                                while (cursor1.moveToNext());
                            }
                        } else {
                            cursor1.close();
                        }
                    } catch (Exception e) {
                    }
                    wrkCntr_tiet.setText(data.getStringExtra("wrkCntr_id") + " - " + loc_text);
                }
                break;

            /*Written By SuryaKiran for Custom Info Added onActivityResult*/
            case (OPERATION_CUSTOMINFO):
                if (resultCode == RESULT_OK) {
                    selected_operation_custom_info_arraylist =
                            (ArrayList<HashMap<String, String>>) data
                                    .getSerializableExtra("selected_custom_info_arraylist");
                }
                break;
            /*Written By SuryaKiran for Custom Info Added onActivityResult*/

            /*Written By SuryaKiran for Operation Long Text onActivityResult*/
            case (long_text):
                if (resultCode == RESULT_OK) {
                    longtext_text = data.getStringExtra("longtext_new");
                    oop.setOprtnLngTxt(longtext_text);
                }
                break;
            /*Written By SuryaKiran for Operation Long Text onActivityResult*/

        }
    }
}
