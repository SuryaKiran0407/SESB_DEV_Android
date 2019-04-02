package com.enstrapp.fieldtekpro.Calibration;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Utilities.ViewPagerAdapter;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Calibration_Orders_Operations_List_Activity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Calibration_Operations_Fragment operations_fragment;
    Calibration_UsageDecision_Fragment usageDecision_fragment;
    Calibration_Defects_Fragment defects_fragment;
    private Toolbar toolbar;
    String[] tabTitle;
    String order_id = "", plant_id = "", equip_id = "";
    Button cancel_button, submit_button;
    Dialog submit_decision_dialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    TextView title_textview;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    RelativeLayout footer;
    ImageView home_imageview;
    Boolean yes = false;
    ArrayList<EquiList> equi_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration_orders_operations_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            order_id = extras.getString("order_id");
            plant_id = extras.getString("plant_id");
            equip_id = extras.getString("equip_id");
        }

        tabTitle = new String[]{getString(R.string.operations),
                getString(R.string.usg_decs), getString(R.string.defects)};
        home_imageview = findViewById(R.id.home_imageview);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.submit_button);
        title_textview = (TextView) findViewById(R.id.title_textview);
        footer = (RelativeLayout) findViewById(R.id.footer);

        home_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calibration_Orders_Operations_List_Activity.this.finish();
            }
        });

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        try {
            setupTabIcons();
        } catch (Exception e) {
        }

        DATABASE_NAME = getApplicationContext().getString(R.string.database_name);
        App_db = getApplicationContext().openOrCreateDatabase(DATABASE_NAME, getApplicationContext().MODE_PRIVATE, null);

        String Prueflos = "";
        String equi_id = "";
        String equip_txt = "";

        try {
            Cursor cursor1 = null;
            cursor1 = App_db.rawQuery("select * from EtQinspData  Where Aufnr = ? group by Equnr", new String[]{order_id});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        EquiList eql = new EquiList(cursor1.getString(21), equipName(cursor1.getString(21)));
                        equi_list.add(eql);
                        Prueflos = cursor1.getString(1);

                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                Prueflos = "";
            }
        } catch (Exception e) {
            Prueflos = "";
        }
        if (Prueflos != null && !Prueflos.equals("")) {
            title_textview.setText(getString(R.string.lot_no, Prueflos));
        }
        String vkatart = "";
        try {
            Cursor cursor1 = null;
            cursor1 = App_db.rawQuery("select * from EtQudData Where Aufnr = ?", new String[]{order_id});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        vkatart = cursor1.getString(8);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                vkatart = "";
            }
        } catch (Exception e) {
            vkatart = "";
        }
        if (vkatart != null && !vkatart.equals("")) {
            footer.setVisibility(View.GONE);
        }
        setupViewPager(viewPager, equi_list);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (equi_list.size() > 1) {
            yes = true;
        }

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);

    }

    public class EquiList {
        private String Equnr;
        private String Eqtxt;
        ArrayList<EquiList> arrayList = new ArrayList<>();

        public ArrayList<EquiList> getArrayList() {
            return arrayList;
        }

        public void setArrayList(ArrayList<EquiList> arrayList) {
            this.arrayList = arrayList;
        }

        public String getEqunr() {
            return Equnr;
        }

        public void setEqunr(String equnr) {
            Equnr = equnr;
        }

        public String getEqtxt() {
            return Eqtxt;
        }

        public void setEqtxt(String eqtxt) {
            Eqtxt = eqtxt;
        }

        public EquiList(String equnr, String eqtxt) {
            Equnr = equnr;
            Eqtxt = eqtxt;
        }
    }

    private void setupViewPager(final ViewPager viewPager, List<EquiList> equi_list) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        operations_fragment = new Calibration_Operations_Fragment();
        usageDecision_fragment = new Calibration_UsageDecision_Fragment();
        defects_fragment = new Calibration_Defects_Fragment();
        if (this.equi_list.size() > 1) {
            adapter.addFragment(operations_fragment, getString(R.string.equipments));
        } else {
            adapter.addFragment(operations_fragment, getString(R.string.operations));

        }
        adapter.addFragment(usageDecision_fragment, getString(R.string.usg_decs));
        adapter.addFragment(defects_fragment, getString(R.string.defects));
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(tabTitle[pos]);
        return view;
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == cancel_button) {
            Calibration_Orders_Operations_List_Activity.this.finish();
        } else if (v == submit_button) {
            cd = new ConnectionDetector(Calibration_Orders_Operations_List_Activity.this);
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                submit_decision_dialog = new Dialog(Calibration_Orders_Operations_List_Activity.this);
                submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                submit_decision_dialog.setCancelable(false);
                submit_decision_dialog.setCanceledOnTouchOutside(false);
                submit_decision_dialog.setContentView(R.layout.decision_dialog);
                ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
                Glide.with(Calibration_Orders_Operations_List_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
                TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
                description_textview.setText(getString(R.string.submit_calb));
                Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
                Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
                submit_decision_dialog.show();
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit_decision_dialog.dismiss();
                        new Get_Token().execute();
                    }
                });
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit_decision_dialog.dismiss();
                    }
                });
            } else {
                network_connection_dialog.show_network_connection_dialog(Calibration_Orders_Operations_List_Activity.this);
            }
        }
    }

    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Calibration_Orders_Operations_List_Activity.this, getResources().getString(R.string.save_calibration));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(Calibration_Orders_Operations_List_Activity.this);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (token_status.equalsIgnoreCase("success")) {
                new Fetching_Calibration_Data().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Calibration_Orders_Operations_List_Activity.this,
                        getString(R.string.unable_calib));
            }
        }
    }

    private class Fetching_Calibration_Data extends AsyncTask<Void, Integer, Void> {
        ArrayList<Model_Notif_Calibration_Operations> calib_operations_ArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Calibration_UsageDecision> calib_usagedecision_ArrayList = new ArrayList<>();
        ArrayList<Model_Notif_Calibration_Defects> calib_defects_ArrayList = new ArrayList<>();
        Map<String, String> calibration_submit_status;
        String Prueflos = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                DateFormat date_format = new SimpleDateFormat("yyyyMMdd");
                DateFormat time_format = new SimpleDateFormat("HHmmss");
                Date todaysdate = new Date();
                String date = date_format.format(todaysdate.getTime());
                String time = time_format.format(todaysdate.getTime());

                /*Fetching Operations Data*/
                Calibration_Operations_Fragment operations_tab = (Calibration_Operations_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                List<Orders_Operations_Parcelable> operations_data = operations_tab.getOperationData();
                for (int i = 0; i < operations_data.size(); i++) {
                    ArrayList<Start_Calibration_Parcelable> Start_calibration_parcelables = operations_data.get(i).getStart_calibration_parcelables();
                    if (Start_calibration_parcelables.size() > 0) {
                        for (int j = 0; j < Start_calibration_parcelables.size(); j++) {
                            Model_Notif_Calibration_Operations model_notif_calibration_operations = new Model_Notif_Calibration_Operations();
                            model_notif_calibration_operations.setAufnr(order_id);
                            Prueflos = Start_calibration_parcelables.get(j).getPrueflos();
                            model_notif_calibration_operations.setPrueflos(Start_calibration_parcelables.get(j).getPrueflos());
                            model_notif_calibration_operations.setVornr(operations_data.get(i).getOperation_id());
                            model_notif_calibration_operations.setPlnty("");
                            model_notif_calibration_operations.setPlnnr("");
                            model_notif_calibration_operations.setPlnkn("");
                            model_notif_calibration_operations.setMerknr(Start_calibration_parcelables.get(j).getMerknr());
                            model_notif_calibration_operations.setWerks(operations_data.get(i).getPlant_id());
                            model_notif_calibration_operations.setQuantitat(Start_calibration_parcelables.get(j).getQUANTITAT());
                            model_notif_calibration_operations.setQualitat(Start_calibration_parcelables.get(j).getQUALITAT());
                            model_notif_calibration_operations.setQpmkZaehl("");
                            model_notif_calibration_operations.setMsehi(Start_calibration_parcelables.get(j).getMSEHI());
                            model_notif_calibration_operations.setMsehl(Start_calibration_parcelables.get(j).getMSEHL());
                            model_notif_calibration_operations.setVerwmerkm(Start_calibration_parcelables.get(j).getVERWMERKM());
                            model_notif_calibration_operations.setKurztext(Start_calibration_parcelables.get(j).getKURZTEXT());
                            model_notif_calibration_operations.setResult(Start_calibration_parcelables.get(j).getRESULT());
                            model_notif_calibration_operations.setSollwert("");
                            model_notif_calibration_operations.setToleranzob(Start_calibration_parcelables.get(j).getTOLERANZOB());
                            model_notif_calibration_operations.setToleranzub(Start_calibration_parcelables.get(j).getTOLERANZUB());
                            model_notif_calibration_operations.setRueckmelnr("");
                            model_notif_calibration_operations.setSatzstatus("");
                            model_notif_calibration_operations.setEqunr(Start_calibration_parcelables.get(j).getEQUNR());
                            model_notif_calibration_operations.setPruefbemkt(Start_calibration_parcelables.get(j).getPRUEFBEMKT());
                            model_notif_calibration_operations.setMbewertg(Start_calibration_parcelables.get(j).getValuation());
                            model_notif_calibration_operations.setPruefer(Start_calibration_parcelables.get(j).getPRUEFER());
                            model_notif_calibration_operations.setPruefdatuv(Start_calibration_parcelables.get(j).getPruefdatuv());
                            model_notif_calibration_operations.setPruefdatub(Start_calibration_parcelables.get(j).getPruefdatub());
                            model_notif_calibration_operations.setPruefzeitv(Start_calibration_parcelables.get(j).getPruefzeitv());
                            model_notif_calibration_operations.setPruefzeitb(Start_calibration_parcelables.get(j).getPruefzeitb());
                            model_notif_calibration_operations.setIststpumf(Integer.parseInt(Start_calibration_parcelables.get(j).getISTSTPUMF()));
                            model_notif_calibration_operations.setAnzfehleh(0);
                            model_notif_calibration_operations.setAnzwertg(Integer.parseInt(Start_calibration_parcelables.get(j).getANZWERTG()));
                            model_notif_calibration_operations.setKtextmat("");
                            model_notif_calibration_operations.setKatab1("");
                            model_notif_calibration_operations.setKatalgart1("");
                            model_notif_calibration_operations.setAuswmenge1(Start_calibration_parcelables.get(j).getAUSWMENGE1());
                            model_notif_calibration_operations.setCodetext("");
                            model_notif_calibration_operations.setXstatus("");
                            model_notif_calibration_operations.setAction("I");
                            model_notif_calibration_operations.setUdid(Start_calibration_parcelables.get(j).getUuid());
                            calib_operations_ArrayList.add(model_notif_calibration_operations);
                        }
                    }
                }
                /*Fetching Operations Data*/

                /*Fetching Usage Decision Data*/
                Calibration_UsageDecision_Fragment header_tab = (Calibration_UsageDecision_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                Calibration_Usage_Decision_Object header_data = header_tab.getUsageDecisionData();
                String udcode_id = header_data.getUdcode_id();
                String udcode_text = header_data.getUdcode_text();
                String quality_score = header_data.getQuality_score();
                String notes = header_data.getNotes();
                Model_Notif_Calibration_UsageDecision model_notif_calibration_usageDecision = new Model_Notif_Calibration_UsageDecision();
                model_notif_calibration_usageDecision.setPrueflos(Prueflos);
                model_notif_calibration_usageDecision.setAufnr(order_id);
                model_notif_calibration_usageDecision.setEqunr(equip_id);
                model_notif_calibration_usageDecision.setVcode(udcode_id);
                model_notif_calibration_usageDecision.setUdtext(notes);
                model_notif_calibration_usageDecision.setAction("I");
                calib_usagedecision_ArrayList.add(model_notif_calibration_usageDecision);
                /*Fetching Usage Decision Data*/

                /*Fetching Defects Data*/
                Calibration_Defects_Fragment defects_fragment = (Calibration_Defects_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
                List<Calibration_Defects_Fragment.Defects_Object> defects_list = defects_fragment.getDefectsData();
                if (defects_list.size() > 0) {
                    for (int i = 0; i < defects_list.size(); i++) {
                        Model_Notif_Calibration_Defects model_notif_calibration_defects = new Model_Notif_Calibration_Defects();
                        model_notif_calibration_defects.setPrueflos(Prueflos);
                        model_notif_calibration_defects.setAufnr(order_id);
                        model_notif_calibration_defects.setFegrp(defects_list.get(i).getDefect_id());
                        model_notif_calibration_defects.setFecod(defects_list.get(i).getDefect_code_id());
                        model_notif_calibration_defects.setAnzfehler(defects_list.get(i).getNum_defects());
                        model_notif_calibration_defects.setOtgrp(defects_list.get(i).getObject_part_id());
                        model_notif_calibration_defects.setOteil(defects_list.get(i).getObject_part_code_id());
                        model_notif_calibration_defects.setFetxt(defects_list.get(i).getDefect_description());
                        model_notif_calibration_defects.setAction("I");
                        calib_defects_ArrayList.add(model_notif_calibration_defects);
                    }
                }
                /*Fetching Defects Data*/

                calibration_submit_status = Calibration_Save.Post_Calibration_Data(Calibration_Orders_Operations_List_Activity.this, calib_operations_ArrayList, calib_usagedecision_ArrayList, calib_defects_ArrayList, order_id);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            custom_progress_dialog.dismiss_progress_dialog();
            if (calibration_submit_status.get("response_status") != null &&
                    calibration_submit_status.get("response_status").startsWith("S")) {
                Success_Dialog successDialog = new Success_Dialog();
                successDialog.dismissActivity(Calibration_Orders_Operations_List_Activity.this,
                        calibration_submit_status.get("response_status").substring(1));
            } else if (calibration_submit_status.get("response_status") != null &&
                    calibration_submit_status.get("response_status").startsWith("E")) {
                error_dialog.show_error_dialog(Calibration_Orders_Operations_List_Activity.this,
                        calibration_submit_status.get("response_status").substring(1));
            } else {
                error_dialog.show_error_dialog(Calibration_Orders_Operations_List_Activity.this,
                        getString(R.string.unable_prcscalb));
            }
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    private String equipName(String order_id) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where  Equnr" +
                    " = ?", new String[]{equip_id});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(5);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }
}
