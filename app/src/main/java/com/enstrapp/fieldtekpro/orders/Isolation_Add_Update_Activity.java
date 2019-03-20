package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Isolation_Add_Update_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_tv, title_textview, no_data_textview;
    FloatingActionButton fab;
    TabLayout tabLayout;
    Orders_Tab_Adapter tabAdapter;
    ViewPager viewPager;
    ArrayList<OrdrPermitPrcbl> iso_al = new ArrayList<>();
    OrdrPermitPrcbl iso = new OrdrPermitPrcbl();
    ArrayList<OrdrWDItemPrcbl> wdip_al = new ArrayList<OrdrWDItemPrcbl>();
    String funcId = "", shrtTxt = "", authId = "", authTxt = "", iwerk = "", wapinr = "", priority_id = "",
            priority_txt = "", equipId = "", equipName = "", wcnr = "", templte_wcnr = "", flag = "",
            orderId = "", switch_tab = "", woco = "", order = "", Objnr = "";
    Button cancel_bt, save_bt, status_bt;
    View status_view;
    boolean wcnrCreated = false, setPrep = false;
    ImageButton back_iv;
    ImageView menu_iv;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    Error_Dialog errorDialog = new Error_Dialog();
    Dialog create_wcm_appl_type_dialog;
    RecyclerView list_recycleview;
    LinearLayout no_data_layout;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    ArrayList<OrdrPermitPrcbl> wd_al = new ArrayList<>();
    TemplateAdapter adapter;
    boolean template_ol = false;
    boolean template_wcnr = false;
    ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<>();
    static final int STATUS = 873;
    ArrayList<NotifOrdrStatusPrcbl> status_al = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.isolation_create_change);

        menu_iv = findViewById(R.id.menu_iv);
        back_iv = findViewById(R.id.back_iv);
        title_tv = findViewById(R.id.title_tv);
        fab = findViewById(R.id.fab);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        cancel_bt = findViewById(R.id.cancel_bt);
        save_bt = findViewById(R.id.save_bt);
        status_bt = findViewById(R.id.status_bt);
        status_view = findViewById(R.id.status_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Objnr = extras.getString("objnr");
            orderId = extras.getString("order");
            woco = extras.getString("woco");
            switch_tab = extras.getString("switch_tab");
            switch_tab = extras.getString("switch_tab");
            iso_al = extras.getParcelableArrayList("iso");
            funcId = extras.getString("func_loc");
            authId = extras.getString("auth");
            authTxt = extras.getString("auth_txt");
            iwerk = extras.getString("iwerk");
            wapinr = extras.getString("wapinr");
            shrtTxt = extras.getString("shrtTxt");
            priority_id = extras.getString("priority_id");
            priority_txt = extras.getString("priority_txt");
            equipId = extras.getString("equip");
            equipName = extras.getString("equip_txt");
            flag = extras.getString("flag");
            if (iso_al != null && iso_al.size() > 0)
                if (iso_al.get(0).getStatusPrcbl_Al() != null)
                    if (iso_al.get(0).getStatusPrcbl_Al().size() > 0) {
                        status_bt.setVisibility(VISIBLE);
                        status_view.setVisibility(VISIBLE);
                    } else {
                        status_bt.setVisibility(GONE);
                        status_view.setVisibility(GONE);
                    }
        }

        DATABASE_NAME = Isolation_Add_Update_Activity.this.getString(R.string.database_name);
        App_db = Isolation_Add_Update_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        if (iso_al != null) {
            if (iso_al.size() > 0) {
                iso = iso_al.get(0);
                if (!iso.getWcnr().startsWith("WD")) {
                    menu_iv.setVisibility(GONE);
                    wcnrCreated = true;
                    wcnr = iso.getWcnr();
                } else {
                    wcnrCreated = false;
                    wcnr = "WD0001";
                }
                if (iso.getComp() != null && !iso.getComp().equals("")) {
                    if (iso.getComp().equals("X")) {
                        setPrep = true;
                        save_bt.setVisibility(GONE);
                        status_view.setVisibility(GONE);
                    } else {
                        save_bt.setVisibility(VISIBLE);
                    }
                } else if (iso.getPrep() != null) {
                    if (iso.getPrep().equals("X"))
                        setPrep = true;
                    else
                        setPrep = false;
                } else {
                    setPrep = false;
                }
                if (iso.getWdItemPrcbl_Al() != null)
                    wdip_al.addAll(iso.getWdItemPrcbl_Al());
            } else {
                setPrep = false;
                wcnrCreated = false;
                wcnr = "WD0001";
            }
        } else {
            setPrep = false;
            wcnrCreated = false;
            wcnr = "WD0001";
        }

        /*if (woco != null)
            if (woco.equalsIgnoreCase("X")) {
                save_bt.setVisibility(GONE);
                status_view.setVisibility(GONE);
            } else {
                save_bt.setVisibility(VISIBLE);
            }*/
        fab.hide();
        if (wcnrCreated)
            viewPager.setOffscreenPageLimit(4);
        else
            viewPager.setOffscreenPageLimit(3);

        tabAdapter = new Orders_Tab_Adapter(this, getSupportFragmentManager());
        tabAdapter.addFragment(new IsolationList_Fragment(), getResources().getString(R.string.isolation_list));
        if (wcnrCreated)
            tabAdapter.addFragment(new IsolationIssue_Fragment(), getResources().getString(R.string.issue_permit));
        tabAdapter.addFragment(new IsolationAdditional_Fragment(), getResources().getString(R.string.add_txt));
        tabAdapter.addFragment(new IsolationSwitiching_Fragment(), getResources().getString(R.string.switch_screen));
        viewPager.setAdapter(tabAdapter);
        if (switch_tab != null && !switch_tab.equalsIgnoreCase(""))
            viewPager.setCurrentItem(3);
        tabLayout.setupWithViewPager(viewPager);
        setCustomFont(tabLayout);

        if (wcnrCreated)
            setupTabIcons();

        save_bt.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        menu_iv.setOnClickListener(this);
        status_bt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.save_bt):
                if (iso.getUsage() != null && !iso.getUsage().equals("")) {
                    if (wcnrCreated) {
                        if (setPrep) {
                            iso.setPrep("X");
                            iso.setCrea("");
                        } else {
                            iso.setPrep("");
                            iso.setCrea("X");
                        }
                        iso.setWdItemPrcbl_Al(wdip_al);
                        iso.setAction("U");
                    } else {
                        iso_al = new ArrayList<>();
                        iso.setAufnr(order);
                        if (Objnr != null && !Objnr.equalsIgnoreCase(""))
                            iso.setRefobj(Objnr);
                        else
                            iso.setRefobj(wapinr);
                        if (template_wcnr)
                            iso.setWcnr(templte_wcnr);
                        else
                            iso.setWcnr(wcnr);
                        if (setPrep) {
                            iso.setPrep("X");
                            iso.setCrea("");
                        } else {
                            iso.setCrea("X");
                            iso.setPrep("");
                        }
                        iso.setAction("I");
                        iso.setIwerk(iwerk);
                        iso.setWdItemPrcbl_Al(wdip_al);
                        iso_al.add(iso);
                    }
                    if (flag.equals("CH")) {
                        if (!wcnrCreated) {
                            if (wapinr.startsWith("WA")) {
                                Intent saveIntent = new Intent();
                                saveIntent.putExtra("iso_al", iso_al);
                                setResult(RESULT_OK, saveIntent);
                                Isolation_Add_Update_Activity.this.finish();
                            } else {
                                cd = new ConnectionDetector(Isolation_Add_Update_Activity.this);
                                isInternetPresent = cd.isConnectingToInternet();
                                if (isInternetPresent) {
                                    if (wcnrCreated)
                                        confirmationDialog(getString(R.string.isolation_change));
                                    else
                                        confirmationDialog(getString(R.string.isolation_create));
                                } else {
                                    network_connection_dialog.show_network_connection_dialog(Isolation_Add_Update_Activity.this);
                                }
                            }
                        } else {
                            cd = new ConnectionDetector(Isolation_Add_Update_Activity.this);
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                if (wcnrCreated)
                                    confirmationDialog(getString(R.string.isolation_change));
                                else
                                    confirmationDialog(getString(R.string.isolation_create));
                            } else {
                                network_connection_dialog.show_network_connection_dialog(Isolation_Add_Update_Activity.this);
                            }
                        }


                    } else {
                        Intent saveIntent = new Intent();
                        saveIntent.putExtra("iso_al", iso_al);
                        setResult(RESULT_OK, saveIntent);
                        Isolation_Add_Update_Activity.this.finish();
                    }
                } else {
                    errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, getString(R.string.usage_mandate));
                }
                break;

            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                Isolation_Add_Update_Activity.this.finish();
                break;

            case (R.id.back_iv):
                setResult(RESULT_CANCELED);
                Isolation_Add_Update_Activity.this.finish();
                break;

            case (R.id.status_bt):
                Intent statusIntent = new Intent(Isolation_Add_Update_Activity.this, Order_Status_Activity.class);
                statusIntent.putExtra("statusObject", iso.getStatusPrcbl_Al());
//                statusIntent.putExtra("woco", woco);
                statusIntent.putExtra("type", "WA");
                statusIntent.putExtra("heading", "Isolation Status");
                startActivityForResult(statusIntent, STATUS);
                break;

            case (R.id.menu_iv):
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.isolation_menu, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, 470, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                LinearLayout template_ll = popupView.findViewById(R.id.template_ll);
                LinearLayout existTemplate_ll = popupView.findViewById(R.id.existTemplate_ll);
                popupWindow.showAsDropDown(v);
                template_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cd = new ConnectionDetector(Isolation_Add_Update_Activity.this);
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            template_ol = true;
                            new GET_TEMPLATE().execute();
                        } else {
                            network_connection_dialog.show_network_connection_dialog(Isolation_Add_Update_Activity.this);
                        }
                    }
                });

                existTemplate_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        template_ol = false;
                        new GetTemplates().execute();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case (STATUS):
                if (resultCode == RESULT_OK) {
                    status_al = data.getParcelableArrayListExtra("status_object");
                    iso.setStatusPrcbl_Al(status_al);
                }
                break;
        }
    }

    public View prepareTabView() {
        View view = getLayoutInflater().inflate(R.layout.iso_custum_tab, null);
        ImageView issue_iv = view.findViewById(R.id.issue_iv);
        if (iso.getAppr() != null)
            if (iso.getAppr().equalsIgnoreCase("G"))
                issue_iv.setImageResource(R.drawable.ic_completed_circle_icon);
            else if (iso.getAppr().equalsIgnoreCase("Y"))
                issue_iv.setImageResource(R.drawable.ic_pending_circle_icon);
            else if (iso.getAppr().equalsIgnoreCase("R"))
                issue_iv.setImageResource(R.drawable.ic_create_circle_icon);

        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(1).setCustomView(prepareTabView());
    }

    public void setCustomFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }

    private class GET_TEMPLATE extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getResources().getString(R.string.fetch_templates));
        }

        @Override
        protected Void doInBackground(Void... voids) {
           Response = new Isolation_Template().Get_Data(Isolation_Add_Update_Activity.this, "", "WCDTL", equipId, funcId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("success")) {
                new GetTemplates().execute();
            } else if (Response.startsWith("E"))
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, Response);
            else
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, Response);
        }
    }

    private class GetTemplates extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wd_al = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = null;
            try {
                if (template_ol)
                    cursor = App_db.rawQuery("select * from Tmp_EtWcmWdData", null);
                else
                    cursor = App_db.rawQuery("select * from EtWcmWdData", null);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            OrdrPermitPrcbl wdp = new OrdrPermitPrcbl();
                            wdp.setAufnr(cursor.getString(1));
                            wdp.setWcnr(cursor.getString(3));
                            wdp.setStxt(cursor.getString(14));
                            wdp.setBegtx(cursor.getString(31));
                            wdp.setUsagex(cursor.getString(7));
                            wd_al.add(wdp);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {

            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (wd_al.size() > 0) {
                wcnrTmplts();
            } else {
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, getString(R.string.no_template));
            }
        }
    }

    private void wcnrTmplts() {
        create_wcm_appl_type_dialog = new Dialog(Isolation_Add_Update_Activity.this, R.style.AppThemeDialog_Dark);
        create_wcm_appl_type_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create_wcm_appl_type_dialog.setCancelable(true);
        create_wcm_appl_type_dialog.setCanceledOnTouchOutside(false);
        create_wcm_appl_type_dialog.setContentView(R.layout.wd_template_dialog);
        create_wcm_appl_type_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
        title_textview = create_wcm_appl_type_dialog.findViewById(R.id.title_textview);
        no_data_textview = create_wcm_appl_type_dialog.findViewById(R.id.no_data_textview);
        ImageView back_imageview = create_wcm_appl_type_dialog.findViewById(R.id.back_imageview);
        //LinearLayout search_ll = create_wcm_appl_type_dialog.findViewById(R.id.search_ll);
        Button yes_button = create_wcm_appl_type_dialog.findViewById(R.id.yes_button);
        Button no_button = create_wcm_appl_type_dialog.findViewById(R.id.no_button);
//        search_ll.setVisibility(View.GONE);
//        search = create_wcm_appl_type_dialog.findViewById(R.id.search);
        list_recycleview = create_wcm_appl_type_dialog.findViewById(R.id.recyclerview);
        adapter = new TemplateAdapter(Isolation_Add_Update_Activity.this, wd_al);
        list_recycleview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Isolation_Add_Update_Activity.this);
        list_recycleview.setLayoutManager(layoutManager);
        list_recycleview.setItemAnimator(new DefaultItemAnimator());
        list_recycleview.setAdapter(adapter);
        no_data_textview.setVisibility(GONE);
        list_recycleview.setVisibility(VISIBLE);
//        no_data_layout.setVisibility(View.GONE);
        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_wcm_appl_type_dialog.dismiss();
            }
        });
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_wcm_appl_type_dialog.dismiss();
            }
        });
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = false;
                String wcnr = "";
                for (int i = 0; i < wd_al.size(); i++) {
                    if (wd_al.get(i).isSelected()) {
                        selected = true;
                        wcnr = wd_al.get(i).getWcnr();
                    }
                }

                if (selected) {
                    create_wcm_appl_type_dialog.dismiss();
                    getTemplate(wcnr);
                } else {
                    errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, getString(R.string.select_template));
                }
            }
        });
        create_wcm_appl_type_dialog.show();
    }

    public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrPermitPrcbl> walist;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView wcnr_tv, desc_tv, auth_tv, usage_tv, order_tv;
            LinearLayout data_ll, ordr_ll;
            CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                wcnr_tv = view.findViewById(R.id.wcnr_tv);
                desc_tv = view.findViewById(R.id.desc_tv);
                auth_tv = view.findViewById(R.id.auth_tv);
                usage_tv = view.findViewById(R.id.usage_tv);
                order_tv = view.findViewById(R.id.order_tv);
                checkBox = view.findViewById(R.id.checkBox);
                ordr_ll = view.findViewById(R.id.ordr_ll);
                data_ll = view.findViewById(R.id.data_ll);
            }
        }

        public TemplateAdapter(Context mContext, ArrayList<OrdrPermitPrcbl> list) {
            this.mContext = mContext;
            this.walist = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wd_template_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final OrdrPermitPrcbl wapo = walist.get(position);
            holder.wcnr_tv.setText(wapo.getWcnr());
            holder.desc_tv.setText(wapo.getStxt());
            holder.auth_tv.setText(wapo.getBegtx());
            holder.usage_tv.setText(wapo.getUsagex());
            if (template_ol) {
                holder.ordr_ll.setVisibility(GONE);
            } else {
                holder.ordr_ll.setVisibility(VISIBLE);
                holder.order_tv.setText(wapo.getAufnr());
            }
            holder.checkBox.setTag(position);
            if (wapo.isSelected())
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        walist.get((Integer) v.getTag()).setSelected(true);
                        for (int i = 0; i < walist.size(); i++) {
                            if (walist.get(i).getWcnr().equals(holder.wcnr_tv.getText().toString()))
                                walist.get(i).setSelected(true);
                            else
                                walist.get(i).setSelected(false);
                        }
                    } else
                        walist.get((Integer) v.getTag()).setSelected(false);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return walist.size();
        }
    }

    private void getTemplate(String Wcnr) {
        wdip_al = new ArrayList<>();
        OrdrPermitPrcbl wdo = new OrdrPermitPrcbl();
        ArrayList<OrdrWDItemPrcbl> wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        ArrayList<OrdrTagUnTagTextPrcbl> wdUnTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
        Cursor cursor2 = null;
        try {
            if (template_ol)
                cursor2 = App_db.rawQuery("select * from Tmp_EtWcmWdData where Wcnr = ?", new String[]{Wcnr});
            else
                cursor2 = App_db.rawQuery("select * from EtWcmWdData where Wcnr = ?", new String[]{Wcnr});
            if (cursor2 != null && cursor2.getCount() > 0) {
                if (cursor2.moveToFirst()) {
                    do {
                        wdIt_al = new ArrayList<OrdrWDItemPrcbl>();
                        wdTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
                        wdUnTagtxt_al = new ArrayList<OrdrTagUnTagTextPrcbl>();
                        wdo.setAufnr("");
                        wdo.setObjart(cursor2.getString(2));
                        if (template_ol)
                            wdo.setWcnr("WD0001");
                        else {
                            wdo.setWcnr(cursor2.getString(3));
                            template_wcnr = true;
                            templte_wcnr = cursor2.getString(3);
                        }
                        wdo.setIwerk(cursor2.getString(4));
                        wdo.setObjtyp(cursor2.getString(5));
                        wdo.setUsage(cursor2.getString(6));
                        wdo.setUsagex(cursor2.getString(7));
                        wdo.setTrain(cursor2.getString(8));
                        wdo.setTrainx(cursor2.getString(9));
                        wdo.setAnlzu(cursor2.getString(10));
                        wdo.setAnlzux(cursor2.getString(11));
                        wdo.setEtape(cursor2.getString(12));
                        wdo.setEtapex(cursor2.getString(13));
                        wdo.setStxt(cursor2.getString(14));
                        wdo.setDatefr("");
                        wdo.setTimefr("");
                        wdo.setDateto("");
                        wdo.setTimeto("");
                        wdo.setPriok(cursor2.getString(19));
                        wdo.setPriokx(cursor2.getString(20));
                        wdo.setRctime(cursor2.getString(21));
                        wdo.setRcunit(cursor2.getString(22));
                        wdo.setObjnr(cursor2.getString(23));
                        wdo.setRefobj(wapinr);
                        wdo.setCrea("X");
                        wdo.setPrep("");
                        wdo.setComp("");
                        wdo.setAppr(cursor2.getString(28));
                        wdo.setAction("I");
                        wdo.setBegru(cursor2.getString(30));
                        wdo.setBegtx(cursor2.getString(31));

                        Cursor cursor3 = null;

                        /*Wd Tagging Text*/
                        try {
                            if (template_ol)
                                cursor3 = App_db.rawQuery("select * from Tmp_EtWcmWdDataTagtext where Wcnr = ?", new String[]{Wcnr});
                            else
                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataTagtext where Wcnr = ?", new String[]{Wcnr});
                            if (cursor3 != null && cursor3.getCount() > 0) {
                                if (cursor3.moveToFirst()) {
                                    do {
                                        OrdrTagUnTagTextPrcbl ttp = new OrdrTagUnTagTextPrcbl();
                                        ttp.setAufnr(cursor3.getString(1));
                                        if (template_ol)
                                            ttp.setWcnr("WD0001");
                                        else
                                            ttp.setWcnr(cursor3.getString(2));
                                        ttp.setObjtype(cursor3.getString(3));
                                        ttp.setFormatCol(cursor3.getString(4));
                                        ttp.setTextLine(cursor3.getString(5));
                                        ttp.setAction(cursor3.getString(6));
                                        wdTagtxt_al.add(ttp);
                                    }
                                    while (cursor3.moveToNext());
                                }
                            }
                        } catch (Exception e) {
                            if (cursor3 != null)
                                cursor3.close();
                        } finally {
                            if (cursor3 != null)
                                cursor3.close();
                        }

                        wdo.setTagText_al(wdTagtxt_al);

                        /*Wd UnTagging Text*/
                        try {
                            if (template_ol)
                                cursor3 = App_db.rawQuery("select * from Tmp_EtWcmWdDataUntagtext where Wcnr = ?", new String[]{Wcnr});
                            else
                                cursor3 = App_db.rawQuery("select * from EtWcmWdDataUntagtext where Wcnr = ?", new String[]{Wcnr});
                            if (cursor3 != null && cursor3.getCount() > 0) {
                                if (cursor3.moveToFirst()) {
                                    do {
                                        OrdrTagUnTagTextPrcbl uttp = new OrdrTagUnTagTextPrcbl();
                                        uttp.setAufnr(cursor3.getString(1));
                                        if (template_ol)
                                            uttp.setWcnr("WD0001");
                                        else
                                            uttp.setWcnr(cursor3.getString(2));
                                        uttp.setObjtype(cursor3.getString(3));
                                        uttp.setFormatCol(cursor3.getString(4));
                                        uttp.setTextLine(cursor3.getString(5));
                                        uttp.setAction(cursor3.getString(6));
                                        wdUnTagtxt_al.add(uttp);
                                    }
                                    while (cursor3.moveToNext());
                                }
                            }
                        } catch (Exception e) {
                            if (cursor3 != null)
                                cursor3.close();
                        } finally {
                            if (cursor3 != null)
                                cursor3.close();
                        }

                        wdo.setUnTagText_al(wdUnTagtxt_al);

                        /*Wd Item Data*/
                        try {
                            if (template_ol)
                                cursor3 = App_db.rawQuery("select * from Tmp_EtWcmWdItemData where Wcnr = ?", new String[]{Wcnr});
                            else
                                cursor3 = App_db.rawQuery("select * from EtWcmWdItemData where Wcnr = ?", new String[]{Wcnr});
                            if (cursor3 != null && cursor3.getCount() > 0) {
                                if (cursor3.moveToFirst()) {
                                    do {
                                        OrdrWDItemPrcbl owip = new OrdrWDItemPrcbl();
                                        if (template_ol)
                                            owip.setWcnr("WD0001");
                                        else
                                            owip.setWcnr(cursor3.getString(1));
                                        owip.setWctim(generateWDItemNo(wdIt_al.size()));
                                        owip.setObjnr(cursor3.getString(3));
                                        owip.setItmtyp(cursor3.getString(4));
                                        owip.setSeq(cursor3.getString(5));
                                        owip.setPred(cursor3.getString(6));
                                        owip.setSucc(cursor3.getString(7));
                                        owip.setCcobj(cursor3.getString(8));
                                        owip.setCctyp(cursor3.getString(9));
                                        if (cursor3.getString(9).equals("E"))
                                            owip.setEquipdDesc(getEquipDesc(cursor3.getString(8)));
                                        else
                                            owip.setEquipdDesc("");
                                        owip.setStxt(cursor3.getString(10));
                                        owip.setTggrp(cursor3.getString(11));
                                        owip.setTgstep(cursor3.getString(12));
                                        owip.setTgproc(cursor3.getString(13));
                                        owip.setTgtyp(cursor3.getString(14));
                                        owip.setTgseq(cursor3.getString(15));
                                        owip.setTgtxt(cursor3.getString(16));
                                        owip.setUnstep(cursor3.getString(17));
                                        owip.setUnproc(cursor3.getString(18));
                                        owip.setUntyp(cursor3.getString(19));
                                        owip.setUnseq(cursor3.getString(20));
                                        owip.setUntxt(cursor3.getString(21));
                                        owip.setPhblflg(cursor3.getString(22));
                                        owip.setPhbltyp(cursor3.getString(23));
                                        owip.setPhblnr(cursor3.getString(24));
                                        owip.setTgflg(cursor3.getString(25));
                                        owip.setTgform(cursor3.getString(26));
                                        owip.setTgnr(cursor3.getString(27));
                                        owip.setUnform(cursor3.getString(28));
                                        owip.setUnnr(cursor3.getString(29));
                                        owip.setControl(cursor3.getString(30));
                                        owip.setLocation(cursor3.getString(31));
                                        owip.setRefobj("");
                                        owip.setAction("I");
                                        owip.setBtg(cursor3.getString(34));
                                        if (cursor3.getString(34).equals("X"))
                                            owip.setTagStatus(true);
                                        owip.setEtg(cursor3.getString(35));
                                        if (cursor3.getString(35).equals("X"))
                                            owip.setTagStatus(true);
                                        owip.setBug(cursor3.getString(36));
                                        if (cursor3.getString(36).equals("X"))
                                            owip.setTagStatus(true);
                                        owip.setEug(cursor3.getString(37));
                                        if (cursor3.getString(37).equals("X"))
                                            owip.setTagStatus(true);
                                        owip.setTgprox(getTgprox(cursor3.getString(13)));
                                        wdIt_al.add(owip);
                                        wdip_al.add(owip);
                                    }
                                    while (cursor3.moveToNext());
                                }
                            }
                        } catch (Exception e) {
                            if (cursor3 != null)
                                cursor3.close();
                        } finally {
                            if (cursor3 != null)
                                cursor3.close();
                        }
                        wdo.setWdItemPrcbl_Al(wdIt_al);
                    } while (cursor2.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor2 != null)
                cursor2.close();
        } finally {
            if (cursor2 != null)
                cursor2.close();
        }

        if (wdo != null) {
            iso = wdo;
            menu_iv.setVisibility(GONE);

            IsolationList_Fragment isoList = (IsolationList_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewPager, 0));
            isoList.refreshData();
            IsolationAdditional_Fragment isoAddText = (IsolationAdditional_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewPager, 1));
            isoAddText.refreshData();
            IsolationSwitiching_Fragment isoSwitch = (IsolationSwitiching_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewPager, 2));
            isoSwitch.refreshData();
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    private String generateWDItemNo(int WditemNo) {
        return String.valueOf(WditemNo = WditemNo + 1);
    }

    private String getTgprox(String tagCond) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtWcmTgtyp where Tgproc = ?", new String[]{tagCond});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        return cursor.getString(10);
                    } while (cursor.moveToNext());
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

    private String getEquipDesc(String equip) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        return cursor.getString(5);
                    } while (cursor.moveToNext());
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

    private String dateDisplayFormat(String date) {
        String inputPattern = "yyyyMMdd";
        String outputPattern = "MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date d = inputFormat.parse(date);
            return outputFormat.format(d);
        } catch (ParseException e) {
            return date;
        }
    }

    private String timeDisplayFormat(String date) {
        String inputPattern = "HHmmss";
        String outputPattern = "HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            Date d = inputFormat.parse(date);
            return outputFormat.format(d);
        } catch (ParseException e) {
            return date;
        }
    }

    private void confirmationDialog(String message) {
        final Dialog cancel_dialog = new Dialog(Isolation_Add_Update_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) cancel_dialog.findViewById(R.id.description_textview);
        description_textview.setText(message);
        Button confirm = (Button) cancel_dialog.findViewById(R.id.ok_button);
        Button cancel = (Button) cancel_dialog.findViewById(R.id.cancel_button);
        cancel_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
                new GetToken().execute();
            }
        });
    }

    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (wcnrCreated)
                customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getString(R.string.iso_chg_progress));
            else
                customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getString(R.string.iso_cre_progress));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Isolation_Add_Update_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.equals("success"))
                new PostPermit().execute("");
            else
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, getResources().getString(R.string.unble_to_process));
        }
    }

    private class PostPermit extends AsyncTask<String, Integer, Void> {
        String[] Response = new String[2];
        ArrayList<WcdDup_Object> wcdDup_al = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (wcnrCreated)
                customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getString(R.string.iso_chg_progress));
            else
                customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getString(R.string.iso_cre_progress));
        }

        @Override
        protected Void doInBackground(String... transmit) {
            String transmit_type = transmit[0];
            Response = new Isolation_Create_Change().Change_Permit(Isolation_Add_Update_Activity.this, iso_al, transmit_type, "WCMMP", orderId, "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response[0].startsWith("S")) {
                dismissActivity(Isolation_Add_Update_Activity.this, Response[0].substring(2));
            } else if (Response[0].startsWith("E")) {
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, Response[0].substring(2));
            } else if (Response[0].startsWith("W")) {
                try {
                    JSONArray wcd_Json = new JSONArray(Response[1]);
                    if (wcd_Json.length() > 0) {
                        for (int i = 0; i < wcd_Json.length(); i++) {
                            WcdDup_Object wdo = new WcdDup_Object(wcd_Json.getJSONObject(i).getString("Aufnr"),
                                    wcd_Json.getJSONObject(i).getString("Stxt"),
                                    wcd_Json.getJSONObject(i).getString("Sysst"),
                                    wcd_Json.getJSONObject(i).getString("Wcnr"));
                            wcdDup_al.add(wdo);
                        }
                        if (wcdDup_al.size() > 0) {
                            final Dialog aa = new Dialog(Isolation_Add_Update_Activity.this);
                            aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            aa.setCancelable(false);
                            aa.setCanceledOnTouchOutside(false);
                            aa.setContentView(R.layout.notifications_duplicate_dialog);
                            aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                            Button yes_button = (Button) aa.findViewById(R.id.yes_button);
                            Button no_button = (Button) aa.findViewById(R.id.no_button);
                            TextView title_textView = (TextView) aa.findViewById(R.id.title_textview);
                            TextView text_msg = (TextView) aa.findViewById(R.id.text_msg);
                            RecyclerView list_recycleview = (RecyclerView) aa.findViewById(R.id.recyclerview);
                            title_textView.setText(getString(R.string.open_iso));
                            text_msg.setText("Do you want to continue?");
                            yes_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new PostPermit().execute("FUNC");
                                    aa.dismiss();
                                }
                            });
                            no_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    aa.dismiss();
                                }
                            });
                            if (wcdDup_al.size() > 0) {
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Isolation_Add_Update_Activity.this);
                                list_recycleview.setLayoutManager(layoutManager);
                                WcdDupAdapter adapter = new WcdDupAdapter(Isolation_Add_Update_Activity.this, wcdDup_al);
                                list_recycleview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                list_recycleview.setVisibility(VISIBLE);
                            } else {
                                list_recycleview.setVisibility(GONE);
                            }
                            aa.show();
                        }
                    }
                } catch (Exception e) {
                }
            } else {
                errorDialog.show_error_dialog(Isolation_Add_Update_Activity.this, Response[0]);
            }
        }
    }

    private class WcdDupAdapter extends RecyclerView.Adapter<WcdDupAdapter.MyViewHolder> {
        private Context mContext;
        private List<WcdDup_Object> wcdDup_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView order_tv, wcnr_tv, desc_tv, status_tv;

            public MyViewHolder(View view) {
                super(view);
                order_tv = view.findViewById(R.id.order_tv);
                wcnr_tv = view.findViewById(R.id.wcnr_tv);
                desc_tv = view.findViewById(R.id.desc_tv);
                status_tv = view.findViewById(R.id.status_tv);
            }
        }

        public WcdDupAdapter(Context mContext, List<WcdDup_Object> list) {
            this.mContext = mContext;
            this.wcdDup_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wcd_dup_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final WcdDup_Object olo = wcdDup_data.get(position);
            holder.order_tv.setText(olo.getAufnr());
            holder.wcnr_tv.setText(olo.getWcnr());
            holder.desc_tv.setText(olo.getStxt());
            holder.status_tv.setText(olo.getSysst());
        }

        @Override
        public int getItemCount() {
            return wcdDup_data.size();
        }

    }

    public String dismissActivity(final Activity activity, String message) {
        final Dialog success_dialog = new Dialog(activity);
        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.success_checkmark).into(imageview);
        success_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success_dialog.dismiss();
                new Get_Isolation_Data().execute();

            }
        });
        return null;
    }

    private class Get_Isolation_Data extends AsyncTask<Void, Integer, Void> {
        ArrayList<OrdrPermitPrcbl> wd_al = new ArrayList<OrdrPermitPrcbl>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Isolation_Add_Update_Activity.this, getResources().getString(R.string.get_order_data));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wd_al = Get_Isolation_Detail.GetData(Isolation_Add_Update_Activity.this, orderId,
                    Objnr, iwerk);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (wd_al != null) {
                Intent saveIntent = new Intent();
                saveIntent.putExtra("iso_al", wd_al);
                setResult(RESULT_OK, saveIntent);
                Isolation_Add_Update_Activity.this.finish();
            }
        }
    }
}
