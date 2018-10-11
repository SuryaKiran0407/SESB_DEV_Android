package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Material_Add_Update_Activity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText oprtnId_tiet, component_tiet, quantity_tiet, plant_tiet, location_tiet,
            reservation_tiet, receipt_tiet, unloading_tiet;
    Button cancel_bt, submit_bt;
    Toolbar toolBar;
    ImageView oprtnId_iv, component_iv, quantity_iv;
    OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
    Error_Dialog errorDialog = new Error_Dialog();
    ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<>();
    ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<>();
    String equip = "", iwerk = "", AVAIL_STATUS = "", MatrlId = "", OprtnId = "", OprtnShrtTxt = "";
    static final int OPRTN_LIST = 300;
    static final int COMPNT_LIST = 301;
    static final int MATERIAL_CUSTOMINFO = 302;
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    boolean availSubmit = false;
    Bundle bundle;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    String plant_id = "", loc_id = "";

    /*Written By SuryaKiran for Custom Info*/
    Button material_custominfo_button;
    ArrayList<HashMap<String, String>> selected_material_custom_info_arraylist = new ArrayList<>();
    /*Written By SuryaKiran for Custom Info*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_material_create_change);

        oprtnId_tiet = findViewById(R.id.oprtnId_tiet);
        component_tiet = findViewById(R.id.component_tiet);
        quantity_tiet = findViewById(R.id.quantity_tiet);
        plant_tiet = findViewById(R.id.plant_tiet);
        location_tiet = findViewById(R.id.location_tiet);
        reservation_tiet = findViewById(R.id.reservation_tiet);
        receipt_tiet = findViewById(R.id.receipt_tiet);
        unloading_tiet = findViewById(R.id.unloading_tiet);
        cancel_bt = findViewById(R.id.cancel_bt);
        submit_bt = findViewById(R.id.submit_bt);
        toolBar = findViewById(R.id.toolBar);
        oprtnId_iv = findViewById(R.id.oprtnId_iv);
        component_iv = findViewById(R.id.component_iv);
        quantity_iv = findViewById(R.id.quantity_iv);


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Material_Add_Update_Activity.this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);


        /*Written By SuryaKiran for Custom Info*/
        material_custominfo_button = (Button)findViewById(R.id.material_custominfo_button);
        selected_material_custom_info_arraylist.clear();
        /*Written By SuryaKiran for Custom Info*/


        omp_al.clear();
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("type_matrl").equalsIgnoreCase("C")) {
                plant_tiet.setText(bundle.getString("ordrPlant"));
                oop_al = bundle.getParcelableArrayList("oprtn_list");
                equip = bundle.getString("ordrEquip");
                iwerk = bundle.getString("iwerk");
            } else {
                omp = bundle.getParcelable("cnfmatrl");
                equip = bundle.getString("ordrEquip");
                iwerk = bundle.getString("iwerk");
                oop_al = bundle.getParcelableArrayList("oprtn_list");
                oprtnId_tiet.setText(getResources().getString(R.string.hypen_text, omp.getOprtnId(), omp.getOprtnShrtTxt()));
                MatrlId = omp.getMatrlId();
                OprtnId = omp.getOprtnId();
                OprtnShrtTxt = omp.getOprtnShrtTxt();
                component_tiet.setText(omp.getMatrlTxt());
                quantity_tiet.setText(omp.getQuantity());

                String plant_text = "";
                try
                {
                    String plant_id = omp.getPlantId();
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_PLANTS where Werks = ?", new String[]{plant_id});
                    if (cursor1 != null && cursor1.getCount() > 0)
                    {
                        if (cursor1.moveToFirst())
                        {
                            do
                            {
                                plant_text = cursor1.getString(2);
                            }
                            while (cursor1.moveToNext());
                        }
                    }
                    else
                    {
                        cursor1.close();
                    }
                }
                catch (Exception e)
                {
                }
                plant_tiet.setText(omp.getPlantId()+" - "+plant_text);
                plant_id = omp.getPlantId();


                String loc_text = "";
                try
                {
                    String loc_id = omp.getLocation();
                    Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_SLOC where Lgort = ?", new String[]{loc_id});
                    if (cursor1 != null && cursor1.getCount() > 0)
                    {
                        if (cursor1.moveToFirst())
                        {
                            do
                            {
                                loc_text = cursor1.getString(4);
                            }
                            while (cursor1.moveToNext());
                        }
                    }
                    else
                    {
                        cursor1.close();
                    }
                }
                catch (Exception e)
                {
                }
                location_tiet.setText(omp.getLocation()+" - "+loc_text);
                loc_id = omp.getLocation();


                reservation_tiet.setText(getResources().getString(R.string.hypen_text, omp.getRsnum(), omp.getRspos()));
                receipt_tiet.setText(omp.getReceipt());
                unloading_tiet.setText(omp.getUnloading());
                submit_bt.setText(getResources().getString(R.string.update));


                /*Written By SuryaKiran for Custom Info*/
                selected_material_custom_info_arraylist = (ArrayList<HashMap<String, String>>)getIntent().getSerializableExtra("selected_material_custom_info_arraylist");
                /*Written By SuryaKiran for Custom Info*/

            }
        }

        oprtnId_iv.setOnClickListener(this);
        component_iv.setOnClickListener(this);
        quantity_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        material_custominfo_button.setOnClickListener(this);
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

            case (R.id.material_custominfo_button):
                Intent custominfo_intent = new Intent(Material_Add_Update_Activity.this, CustomInfo_Activity.class);
                custominfo_intent.putExtra("Zdoctype","W");
                custominfo_intent.putExtra("ZdoctypeItem","WC");
                custominfo_intent.putExtra("custom_info_arraylist",selected_material_custom_info_arraylist);
                custominfo_intent.putExtra("request_id", Integer.toString(MATERIAL_CUSTOMINFO));
                startActivityForResult(custominfo_intent,MATERIAL_CUSTOMINFO);
                break;

            case (R.id.oprtnId_iv):
                Intent oprtnIntent = new Intent(Material_Add_Update_Activity.this, Operations_List.class);
                oprtnIntent.putExtra("oprtn_list", oop_al);
                startActivityForResult(oprtnIntent, OPRTN_LIST);
                break;

            case (R.id.component_iv):
                Intent compIntent = new Intent(Material_Add_Update_Activity.this, Material_Components_Activity.class);
                compIntent.putExtra("equipment", equip);
                compIntent.putExtra("iwerk", iwerk);
                startActivityForResult(compIntent, COMPNT_LIST);
                break;

            case (R.id.quantity_iv):
                if (oprtnId_tiet.getText().toString() != null && !oprtnId_tiet.getText().toString().equals("")) {
                    if (component_tiet.getText().toString() != null && !component_tiet.getText().toString().equals("")) {
                        if (quantity_tiet.getText().toString() != null && !quantity_tiet.getText().toString().equals("")) {
                            if (!quantity_tiet.getText().toString().equals("0")) {
                                availSubmit = false;
                                new GetToken().execute();
                            } else {
                                errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.zero_checker));
                            }
                        } else {
                            errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.quantity_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.component_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.operation_mandate));
                }

                break;

            case (R.id.submit_bt):
                if (oprtnId_tiet.getText().toString() != null && !oprtnId_tiet.getText().toString().equals("")) {
                    if (component_tiet.getText().toString() != null && !component_tiet.getText().toString().equals("")) {
                        if (quantity_tiet.getText().toString() != null && !quantity_tiet.getText().toString().equals("")) {
                            if (!quantity_tiet.getText().toString().equals("0")) {
                                availSubmit = true;
                                cd = new ConnectionDetector(Material_Add_Update_Activity.this);
                                isInternetPresent = cd.isConnectingToInternet();
                                if (isInternetPresent)
                                {
                                    //new GetToken().execute();
                                    if (submit_bt.getText().toString().startsWith("U")) {
                                        addMatrlObject(true, true);
                                    } else
                                        addMatrl();
                                }
                                else
                                {
                                    if (submit_bt.getText().toString().startsWith("U")) {
                                        addMatrlObject(true, true);
                                    } else
                                        addMatrl();
                                }
                            } else {
                                errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.zero_checker));
                            }
                        } else {
                            errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.quantity_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.component_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.operation_mandate));
                }
                break;

            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (OPRTN_LIST):
                if (resultCode == RESULT_OK) {
                    OprtnId = (data.getStringExtra("oprtn_id"));
                    OprtnShrtTxt = (data.getStringExtra("oprtn_txt"));
                    oprtnId_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("oprtn_id"),
                            data.getStringExtra("oprtn_txt")));
                }
                break;

            case (COMPNT_LIST):
                if (resultCode == RESULT_OK) {
                    MatrlId = (data.getStringExtra("component"));
                    component_tiet.setText(data.getStringExtra("component_txt"));

                    String plant_text = "";
                    try
                    {
                        String plant_id = data.getStringExtra("plant");
                        Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_PLANTS where Werks = ?", new String[]{plant_id});
                        if (cursor1 != null && cursor1.getCount() > 0)
                        {
                            if (cursor1.moveToFirst())
                            {
                                do
                                {
                                    plant_text = cursor1.getString(2);
                                }
                                while (cursor1.moveToNext());
                            }
                        }
                        else
                        {
                            cursor1.close();
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    plant_tiet.setText(data.getStringExtra("plant")+" - "+plant_text);
                    plant_id = data.getStringExtra("plant");


                    String loc_text = "";
                    try
                    {
                        String loc_id = data.getStringExtra("location");
                        Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_SLOC where Lgort = ?", new String[]{loc_id});
                        if (cursor1 != null && cursor1.getCount() > 0)
                        {
                            if (cursor1.moveToFirst())
                            {
                                do
                                {
                                    loc_text = cursor1.getString(4);
                                }
                                while (cursor1.moveToNext());
                            }
                        }
                        else
                        {
                            cursor1.close();
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    location_tiet.setText(data.getStringExtra("location")+" - "+loc_text);
                    loc_id = data.getStringExtra("location");

                }
                break;


            /*Written By SuryaKiran for Custom Info Added onActivityResult*/
            case (MATERIAL_CUSTOMINFO):
                if (resultCode == RESULT_OK)
                {
                    selected_material_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("selected_custom_info_arraylist");
                }
                break;
            /*Written By SuryaKiran for Custom Info Added onActivityResult*/

        }
    }

    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.availabilty));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Material_Add_Update_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Response.equals("success"))
                new AvailabilityCheck().execute();
            else {
                customProgressDialog.dismiss_progress_dialog();
                errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.unable_avail));
            }
        }
    }

    public class AvailabilityCheck extends AsyncTask<Void, Integer, Void> {
        String Date = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Date cDate = new Date();
                Date = new SimpleDateFormat("yyyyMMdd").format(cDate);
                AVAIL_STATUS = Material_Availability_Check.material_availability_check(Material_Add_Update_Activity.this,
                        "", MatrlId, component_tiet.getText().toString()
                        , quantity_tiet.getText().toString(), Date, plant_id
                        , loc_id);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            customProgressDialog.dismiss_progress_dialog();
            if (AVAIL_STATUS.substring(1).startsWith("S")) {
                if (availSubmit)
                    if (submit_bt.getText().toString().startsWith("U")) {
                        addMatrlObject(true, true);
                    } else
                        addMatrl();
                else
                    successDialog.show_success_dialog(Material_Add_Update_Activity.this, AVAIL_STATUS.substring(2));

            } else if (AVAIL_STATUS.substring(1).startsWith("E")) {
                errorDialog.show_error_dialog(Material_Add_Update_Activity.this, AVAIL_STATUS.substring(2));
            } else {
                errorDialog.show_error_dialog(Material_Add_Update_Activity.this, getResources().getString(R.string.unable_avail));
            }
        }
    }

    public void addMatrl() {
        final Dialog submit_decision_dialog = new Dialog(Material_Add_Update_Activity.this);
        submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        submit_decision_dialog.setCancelable(false);
        submit_decision_dialog.setCanceledOnTouchOutside(false);
        submit_decision_dialog.setContentView(R.layout.decision_dialog);
        ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
        Glide.with(Material_Add_Update_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
        TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
        description_textview.setText(getResources().getString(R.string.add_another_comp));
        Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
        Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
        submit_decision_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_decision_dialog.dismiss();
                addMatrlObject(false, false);
                component_tiet.setText("");
                quantity_tiet.setText("");
                plant_tiet.setText(bundle.getString("ordrPlant"));
                location_tiet.setText("");
                receipt_tiet.setText("");
                unloading_tiet.setText("");
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_decision_dialog.dismiss();
                addMatrlObject(true, false);
            }
        });
    }

    private void addMatrlObject(boolean submit, boolean update)
    {
        OrdrMatrlPrcbl omp_d = new OrdrMatrlPrcbl();
        omp_d.setOprtnId(OprtnId);
        omp_d.setOprtnShrtTxt(OprtnShrtTxt);
        omp_d.setMatrlId(MatrlId);
        omp_d.setMatrlTxt(component_tiet.getText().toString());
        omp_d.setPlantId(plant_id);
        omp_d.setQuantity(quantity_tiet.getText().toString());
        omp_d.setLocation(loc_id);
        omp_d.setReceipt(receipt_tiet.getText().toString());
        omp_d.setUnloading(unloading_tiet.getText().toString());
        omp_al.add(omp_d);
        if (submit) {
            if (update) {
                Intent intent = new Intent();
                omp_d.setPartId(omp.getPartId());
                omp_d.setStatus(omp.getStatus());
                omp_d.setRspos(omp.getRspos());
                omp_d.setRsnum(omp.getRsnum());
                omp_d.setAufnr(omp.getAufnr());
                omp_d.setLocationTxt(omp.getLocationTxt());
                omp_d.setPlantTxt(omp.getPlantTxt());
                omp_d.setPostpTxt(omp.getPostpTxt());
                omp_d.setMeins(omp.getMeins());
                if (!omp_d.getStatus().equals("I"))
                    omp_d.setStatus("U");
                intent.putExtra("omp_prcbl", omp_d);
                intent.putExtra("selected_material_custom_info_arraylist", selected_material_custom_info_arraylist);
                setResult(RESULT_OK, intent);
                Material_Add_Update_Activity.this.finish();
                omp_d = null;
                omp_al = null;
            } else {
                for (OrdrMatrlPrcbl omp:omp_al)
                    omp.setStatus("I");

                Intent intent = new Intent();
                intent.putExtra("omp_prcbl_al", omp_al);
                intent.putExtra("selected_material_custom_info_arraylist", selected_material_custom_info_arraylist);
                setResult(RESULT_OK, intent);
                Material_Add_Update_Activity.this.finish();
                omp_d = null;
                omp_al = null;
            }
        }

    }
}
