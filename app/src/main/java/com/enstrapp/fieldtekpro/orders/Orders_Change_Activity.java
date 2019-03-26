package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static android.view.View.GONE;

public class Orders_Change_Activity extends AppCompatActivity {

    TabLayout order_tl;
    ViewPager order_vp;
    Orders_Tab_Adapter orders_ta;
    OrdrHeaderPrcbl ohp;
    FloatingActionButton fab;
    Button orderStatus_bt, orderCancel_bt, orderSave_bt;
    View orderStatus_view;
    Error_Dialog error_dialog = new Error_Dialog();
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    static final int ORDR_STATUS = 1;
    TextView status_tv, orders_tv;
    ImageView back_iv;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    LinearLayout order_footer;
    Dialog decision_dialog;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_create_change_activity);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ohp = bundle.getParcelable("ordr_parcel");
        }

        DATABASE_NAME = Orders_Change_Activity.this.getString(R.string.database_name);
        App_db = Orders_Change_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        fab = findViewById(R.id.fab);
        fab.hide();
        back_iv = findViewById(R.id.back_iv);
        orders_tv = findViewById(R.id.orders_tv);
        status_tv = findViewById(R.id.status_tv);
        orderStatus_bt = findViewById(R.id.orderStatus_bt);
        orderCancel_bt = findViewById(R.id.orderCancel_bt);
        orderSave_bt = findViewById(R.id.orderSave_bt);
        orderStatus_view = findViewById(R.id.orderStatus_view);
        orderStatus_view.setVisibility(View.VISIBLE);
        orderStatus_bt.setVisibility(View.VISIBLE);
        order_vp = findViewById(R.id.order_vp);
        order_tl = findViewById(R.id.order_tl);
        order_footer = (LinearLayout) findViewById(R.id.order_footer);

        orders_tv.setText(ohp.getOrdrId());
        status_tv.setText(ohp.getOrdrStatus());
        status_tv.setVisibility(View.VISIBLE);
        if (status_tv.getText().toString().equalsIgnoreCase("CRTD")) {
            status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
            status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
        } else if (status_tv.getText().toString().equalsIgnoreCase("CANC")) {
            status_tv.setBackground(getDrawable(R.drawable.red_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
        } else if (status_tv.getText().toString().equalsIgnoreCase("REL")) {
            status_tv.setBackground(getDrawable(R.drawable.blue_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
        } else if (status_tv.getText().toString().equalsIgnoreCase("ISSU")) {
            status_tv.setBackground(getDrawable(R.drawable.lightgren_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
        } else if (status_tv.getText().toString().equalsIgnoreCase("DLFL")) {
            status_tv.setBackground(getDrawable(R.drawable.red_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
        } else if (status_tv.getText().toString().equalsIgnoreCase("CLSD")) {
            status_tv.setBackground(getDrawable(R.drawable.green_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
            orderSave_bt.setVisibility(View.GONE);
            orderStatus_view.setVisibility(View.GONE);
        } else if (status_tv.getText().toString().equalsIgnoreCase("CNF")) {
            status_tv.setBackground(getDrawable(R.drawable.orange_circle));
            status_tv.setTextColor(getResources().getColor(R.color.white));
        } else {
            status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
            status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
        }


        orders_ta = new Orders_Tab_Adapter(this, getSupportFragmentManager());
        orders_ta.addFragment(new Orders_CH_General_Fragment(), getResources().getString(R.string.general));
        orders_ta.addFragment(new Orders_CH_Operation_Fragment(), getResources().getString(R.string.operations));
        if (ohp.getOrdrTypId().equals("PM08")) {
            order_vp.setOffscreenPageLimit(5);
        } else {
            order_vp.setOffscreenPageLimit(6);
            orders_ta.addFragment(new Orders_CH_Material_Fragment(), getResources().getString(R.string.material));
        }
        orders_ta.addFragment(new Orders_CH_Permits_Fragment(), getResources().getString(R.string.permit));
        orders_ta.addFragment(new Orders_CH_Object_Fragment(), getResources().getString(R.string.object));
        orders_ta.addFragment(new Orders_CH_Attachments_Fragment(), getResources().getString(R.string.attachments));
        orders_ta.addFragment(new Orders_CH_Map_Fragment(), getResources().getString(R.string.map));


        order_vp.setAdapter(orders_ta);
        order_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.hide();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        fab.hide();
                        break;
                    default:
                        fab.show();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        order_tl.setupWithViewPager(order_vp);

        setCustomFont(order_tl);

        orderSave_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean WBS = false;
                if (ohp.getOrdrTypId() != null && !ohp.getOrdrTypId().equals("")) {
                    if (ohp.getOrdrTypId().equals("PM06"))
                        WBS = true;
                    else
                        WBS = false;
                    if (ohp.getOrdrShrtTxt() != null && !ohp.getOrdrShrtTxt().equals("")) {
                        if ((ohp.getEquipNum() != null && !ohp.getEquipNum().equals("")) ||
                                (ohp.getFuncLocId() != null && !ohp.getFuncLocId().equals(""))) {
                            if (ohp.getPriorityId() != null && !ohp.getPriorityId().equals("")) {
                                if (ohp.getWrkCntrId() != null && !ohp.getWrkCntrId().equals("")) {
                                    if (ohp.getPlnrGrpId() != null &&
                                            !ohp.getPlnrGrpId().equals("")) {
                                        if (WBS) {
                                            if (ohp.getPosid() != null &&
                                                    !ohp.getPosid().equals("")) {
                                                if (ohp.getRevnr() != null &&
                                                        !ohp.getRevnr().equals("")) {
                                                    if (ohp.getOrdrTypId().equals("PM08")) {
                                                        ohp.setEquipNum("");
                                                        ohp.setEquipName("");
                                                        ohp.setOrdrMatrlPrcbls(null);
                                                    }
                                                    cd = new ConnectionDetector(Orders_Change_Activity.this);
                                                    isInternetPresent = cd.isConnectingToInternet();
                                                    if (isInternetPresent) {
                                                        confirmationDialog(getString(R.string.update_order));
                                                    } else {
                                                        if (ohp.getOrdrPermitPrcbls() != null) {
                                                            if (ohp.getOrdrPermitPrcbls().size() > 0) {
                                                                network_connection_dialog.show_network_connection_dialog(Orders_Change_Activity.this);
                                                            } else {
                                                                Insert_Offline_Data();
                                                            }
                                                        } else {
                                                            Insert_Offline_Data();
                                                        }
                                                    }
                                                } else {
                                                    error_dialog.show_error_dialog(Orders_Change_Activity.this, getResources().getString(R.string.revision_mandate));
                                                }
                                            } else {
                                                error_dialog.show_error_dialog(Orders_Change_Activity.this, getResources().getString(R.string.wbs_mandate));
                                            }
                                        } else {
                                            cd = new ConnectionDetector(Orders_Change_Activity.this);
                                            isInternetPresent = cd.isConnectingToInternet();
                                            if (isInternetPresent) {
                                                if (ohp.getOrdrTypId().equals("PM08")) {
                                                    ohp.setEquipNum("");
                                                    ohp.setEquipName("");
                                                    ohp.setOrdrMatrlPrcbls(null);
                                                }
                                                confirmationDialog(getString(R.string.update_order));
                                            } else {
                                                if (ohp.getOrdrPermitPrcbls() != null) {
                                                    if (ohp.getOrdrPermitPrcbls().size() > 0) {
                                                        network_connection_dialog.show_network_connection_dialog(Orders_Change_Activity.this);
                                                    } else {
                                                        Insert_Offline_Data();
                                                    }
                                                } else {
                                                    Insert_Offline_Data();
                                                }
                                            }
                                        }
                                    } else {
                                        error_dialog.show_error_dialog(Orders_Change_Activity.this,
                                                getResources().getString(R.string.plndGrp_mandate));
                                    }
                                } else {
                                    error_dialog.show_error_dialog(Orders_Change_Activity.this,
                                            getResources().getString(R.string.wrkCntr_mandate));
                                }
                            } else {
                                error_dialog.show_error_dialog(Orders_Change_Activity.this,
                                        getResources().getString(R.string.priority_mandate));
                            }
                        } else {
                            error_dialog.show_error_dialog(Orders_Change_Activity.this,
                                    getResources().getString(R.string.equipFunc_mandate));
                        }
                    } else {
                        error_dialog.show_error_dialog(Orders_Change_Activity.this,
                                getResources().getString(R.string.text_mandate));
                    }
                } else {
                    error_dialog.show_error_dialog(Orders_Change_Activity.this,
                            getResources().getString(R.string.ordTyp_mandate));
                }
            }
        });

        orderCancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ohp = null;
                deleteAttachments();
                onBackPressed();
            }
        });

        orderStatus_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statusIntent = new Intent(Orders_Change_Activity.this,
                        Order_Status_Activity.class);
                statusIntent.putExtra("statusObject", ohp.getOrdrStatusPrcbls());
                statusIntent.putExtra("ordrId", ohp.getOrdrId());
                statusIntent.putExtra("status", ohp.getOrdrStatus());
                startActivityForResult(statusIntent, ORDR_STATUS);
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ohp = null;
                deleteAttachments();
                onBackPressed();
            }
        });

        /*Authorization For Change Notification */
        String auth_change_status = Authorizations
                .Get_Authorizations_Data(Orders_Change_Activity.this, "W", "U");
        if (auth_change_status.equalsIgnoreCase("true")) {
            order_footer.setVisibility(View.VISIBLE);
        } else {
            order_footer.setVisibility(View.GONE);
        }
        /*Authorization For Change Notification */
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        deleteAttachments();
        return super.onKeyDown(keyCode, event);
    }

    public void Insert_Offline_Data() {
        decision_dialog = new Dialog(Orders_Change_Activity.this);
        decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        decision_dialog.setCancelable(false);
        decision_dialog.setCanceledOnTouchOutside(false);
        decision_dialog.setContentView(R.layout.offline_decision_dialog);
        TextView description_textview = (TextView) decision_dialog.findViewById(R.id.description_textview);
        Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
        Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
        Button connect_button = (Button) decision_dialog.findViewById(R.id.connect_button);
        description_textview.setText(getString(R.string.ordchg_offline));
        confirm.setText(getString(R.string.yes));
        cancel.setText(getString(R.string.no));
        decision_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog.dismiss();
            }
        });
        connect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decision_dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings",
                        "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgressDialog.show_progress_dialog(Orders_Change_Activity.this,
                        getResources().getString(R.string.loading));
                try {
                    App_db.execSQL("delete from DUE_ORDERS_EtOrderHeader where Aufnr = ?",
                            new String[]{ohp.getOrdrId()});
                    App_db.execSQL("delete from DUE_ORDERS_Longtext where Aufnr = ?", new String[]{ohp.getOrdrId()});
                    App_db.execSQL("delete from DUE_ORDERS_EtOrderOperations where Aufnr = ?", new String[]{ohp.getOrdrId()});
                    App_db.execSQL("delete from EtOrderComponents where Aufnr = ?", new String[]{ohp.getOrdrId()});
                    App_db.execSQL("delete from EtOrderOlist where Aufnr = ?", new String[]{ohp.getOrdrId()});
                } catch (Exception e) {
                }

                try {
                    App_db.beginTransaction();
                    String EtOrderHeader_sql = "Insert into DUE_ORDERS_EtOrderHeader (UUID, Aufnr, Auart, Ktext, Ilart, Ernam, Erdat, Priok, Equnr, Strno, TplnrInt, Bautl, Gltrp, Gstrp, Docs, Permits, Altitude, Latitude, Longitude, Qmnum, Closed, Completed, Ingrp, Arbpl, Werks, Bemot, Aueru, Auarttext, Qmartx, Qmtxt, Pltxt, Eqktx, Priokx , Ilatx, Plantname, Wkctrname, Ingrpname, Maktx, Xstatus, Usr01, Usr02, Usr03, Usr04, Usr05, Kokrs, Kostl, Anlzu, Anlzux, Ausvn, Ausbs, Auswk, Qmnam, ParnrVw, NameVw, Posid, Revnr) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement EtOrderHeader_statement = App_db.compileStatement(EtOrderHeader_sql);
                    EtOrderHeader_statement.clearBindings();
                    EtOrderHeader_statement.bindString(1, ohp.getOrdrUUId());
                    EtOrderHeader_statement.bindString(2, ohp.getOrdrId());
                    EtOrderHeader_statement.bindString(3, ohp.getOrdrTypId());
                    EtOrderHeader_statement.bindString(4, ohp.getOrdrShrtTxt());
                    EtOrderHeader_statement.bindString(5, "");
                    EtOrderHeader_statement.bindString(6, "");
                    EtOrderHeader_statement.bindString(7, "");
                    EtOrderHeader_statement.bindString(8, ohp.getPriorityId());
                    EtOrderHeader_statement.bindString(9, ohp.getEquipNum());
                    EtOrderHeader_statement.bindString(10, ohp.getFuncLocId());
                    EtOrderHeader_statement.bindString(11, "");
                    EtOrderHeader_statement.bindString(12, "");
                    EtOrderHeader_statement.bindString(13, ohp.getBasicEnd());
                    EtOrderHeader_statement.bindString(14, ohp.getBasicStart());
                    EtOrderHeader_statement.bindString(15, "");
                    EtOrderHeader_statement.bindString(16, "");
                    EtOrderHeader_statement.bindString(17, "");
                    EtOrderHeader_statement.bindString(18, "");
                    EtOrderHeader_statement.bindString(19, "");
                    EtOrderHeader_statement.bindString(20, "");
                    EtOrderHeader_statement.bindString(21, "");
                    EtOrderHeader_statement.bindString(22, "");
                    EtOrderHeader_statement.bindString(23, ohp.getPlnrGrpId());
                    EtOrderHeader_statement.bindString(24, ohp.getWrkCntrId());
                    EtOrderHeader_statement.bindString(25, ohp.getPlant());
                    EtOrderHeader_statement.bindString(26, "");
                    EtOrderHeader_statement.bindString(27, "");
                    EtOrderHeader_statement.bindString(28, ohp.getOrdrTypName());
                    EtOrderHeader_statement.bindString(29, "");
                    EtOrderHeader_statement.bindString(30, "");
                    EtOrderHeader_statement.bindString(31, ohp.getFuncLocName());
                    EtOrderHeader_statement.bindString(32, ohp.getEquipName());
                    EtOrderHeader_statement.bindString(33, ohp.getPriorityTxt());
                    EtOrderHeader_statement.bindString(34, "");
                    EtOrderHeader_statement.bindString(35, "");
                    EtOrderHeader_statement.bindString(36, ohp.getWrkCntrName());
                    EtOrderHeader_statement.bindString(37, ohp.getPlnrGrpName());
                    EtOrderHeader_statement.bindString(38, "");
                    EtOrderHeader_statement.bindString(39, ohp.getOrdrStatus());
                    EtOrderHeader_statement.bindString(40, "");
                    EtOrderHeader_statement.bindString(41, "");
                    EtOrderHeader_statement.bindString(42, "");
                    EtOrderHeader_statement.bindString(43, "");
                    EtOrderHeader_statement.bindString(44, "");
                    EtOrderHeader_statement.bindString(45, "");
                    EtOrderHeader_statement.bindString(46, ohp.getRespCostCntrId());
                    EtOrderHeader_statement.bindString(47, ohp.getSysCondId());
                    EtOrderHeader_statement.bindString(48, ohp.getSysCondName());
                    EtOrderHeader_statement.bindString(49, "");
                    EtOrderHeader_statement.bindString(50, "");
                    EtOrderHeader_statement.bindString(51, "");
                    EtOrderHeader_statement.bindString(52, "");
                    EtOrderHeader_statement.bindString(53, ohp.getPerRespId());
                    EtOrderHeader_statement.bindString(54, ohp.getPerRespName());
                    /*EtOrderHeader_statement.bindString(55, ohp.getPosid());
                    EtOrderHeader_statement.bindString(56, ohp.getRevnr());*/
                    EtOrderHeader_statement.execute();
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                } catch (Exception e) {
                }

                try {
                    String long_text = ohp.getOrdrLngTxt();
                    if (long_text != null && !long_text.equals("")) {
                        App_db.beginTransaction();
                        if (long_text.contains("\n")) {
                            String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine) values(?,?,?,?);";
                            SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                            EtOrderLongtext_statement.clearBindings();
                            String[] longtext_array = long_text.split("\n");
                            for (int i = 0; i < longtext_array.length; i++) {
                                EtOrderLongtext_statement.bindString(1, ohp.getOrdrUUId());
                                EtOrderLongtext_statement.bindString(2, ohp.getOrdrId());
                                EtOrderLongtext_statement.bindString(3, "");
                                EtOrderLongtext_statement.bindString(4, longtext_array[i]);
                                EtOrderLongtext_statement.execute();
                            }
                        } else {
                            String EtOrderLongtext_sql = "Insert into DUE_ORDERS_Longtext (UUID, Aufnr, Activity, TextLine) values(?,?,?,?);";
                            SQLiteStatement EtOrderLongtext_statement = App_db.compileStatement(EtOrderLongtext_sql);
                            EtOrderLongtext_statement.clearBindings();
                            EtOrderLongtext_statement.bindString(1, ohp.getOrdrUUId());
                            EtOrderLongtext_statement.bindString(2, ohp.getOrdrId());
                            EtOrderLongtext_statement.bindString(3, "");
                            EtOrderLongtext_statement.bindString(4, long_text);
                            EtOrderLongtext_statement.execute();
                        }
                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                    }
                } catch (Exception e) {
                }

                try {
                    ArrayList<OrdrOprtnPrcbl> operations = ohp.getOrdrOprtnPrcbls();
                    if (operations.size() > 0) {
                        App_db.beginTransaction();
                        String EtOrderOperations_sql = "Insert into DUE_ORDERS_EtOrderOperations (UUID,Aufnr,Vornr,Uvorn,Ltxa1,Arbpl,Werks,Steus,Larnt,Dauno,Daune,Fsavd,Ssedd,Pernr,Asnum,Plnty,Plnal,Plnnr,Rueck,Aueru,ArbplText,WerksText,SteusText,LarntText,Usr01,Usr02,Usr03,Usr04,Usr05,Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtOrderOperations_statement = App_db.compileStatement(EtOrderOperations_sql);
                        EtOrderOperations_statement.clearBindings();
                        for (int i = 0; i < operations.size(); i++) {
                            EtOrderOperations_statement.bindString(1, ohp.getOrdrUUId());
                            EtOrderOperations_statement.bindString(2, ohp.getOrdrId());
                            EtOrderOperations_statement.bindString(3, operations.get(i).getOprtnId());
                            EtOrderOperations_statement.bindString(4, "");
                            EtOrderOperations_statement.bindString(5, operations.get(i).getOprtnShrtTxt());
                            EtOrderOperations_statement.bindString(6, operations.get(i).getWrkCntrId());
                            EtOrderOperations_statement.bindString(7, operations.get(i).getPlantId());
                            EtOrderOperations_statement.bindString(8, "");
                            EtOrderOperations_statement.bindString(9, "");
                            EtOrderOperations_statement.bindString(10, operations.get(i).getDuration());
                            EtOrderOperations_statement.bindString(11, operations.get(i).getDurationUnit());
                            EtOrderOperations_statement.bindString(12, "");
                            EtOrderOperations_statement.bindString(13, "");
                            EtOrderOperations_statement.bindString(14, "");
                            EtOrderOperations_statement.bindString(15, "");
                            EtOrderOperations_statement.bindString(16, "");
                            EtOrderOperations_statement.bindString(17, "");
                            EtOrderOperations_statement.bindString(18, "");
                            EtOrderOperations_statement.bindString(19, "");
                            EtOrderOperations_statement.bindString(20, "");
                            EtOrderOperations_statement.bindString(21, operations.get(i).getWrkCntrTxt());
                            EtOrderOperations_statement.bindString(22, "");
                            EtOrderOperations_statement.bindString(23, "");
                            EtOrderOperations_statement.bindString(24, "");
                            EtOrderOperations_statement.bindString(25, "");
                            EtOrderOperations_statement.bindString(26, "");
                            EtOrderOperations_statement.bindString(27, "");
                            EtOrderOperations_statement.bindString(28, "");
                            EtOrderOperations_statement.bindString(29, "");
                            EtOrderOperations_statement.bindString(30, operations.get(i).getStatus());
                            EtOrderOperations_statement.execute();
                        }
                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                    }
                } catch (Exception e) {
                }


                try {
                    ArrayList<OrdrMatrlPrcbl> matrlPrcbls = ohp.getOrdrMatrlPrcbls();
                    if (matrlPrcbls.size() > 0) {
                        App_db.beginTransaction();
                        String EtOrderComponents_sql = "Insert into EtOrderComponents (UUID, Aufnr, Vornr, Uvorn, Rsnum, Rspos, Matnr, Werks, Lgort, Posnr, Bdmng, Meins, Postp, MatnrText, WerksText, LgortText, PostpText, Usr01, Usr02, Usr03, Usr04, Usr05, Action, Wempf, Ablad) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtOrderComponents_statement = App_db.compileStatement(EtOrderComponents_sql);
                        EtOrderComponents_statement.clearBindings();
                        for (int i = 0; i < matrlPrcbls.size(); i++) {
                            EtOrderComponents_statement.bindString(1, ohp.getOrdrUUId());
                            EtOrderComponents_statement.bindString(2, ohp.getOrdrId());
                            EtOrderComponents_statement.bindString(3, matrlPrcbls.get(i).getOprtnId());
                            EtOrderComponents_statement.bindString(4, "");
                            EtOrderComponents_statement.bindString(5, "");
                            EtOrderComponents_statement.bindString(6, "");
                            EtOrderComponents_statement.bindString(7, matrlPrcbls.get(i).getMatrlId());
                            EtOrderComponents_statement.bindString(8, matrlPrcbls.get(i).getPlantId());
                            EtOrderComponents_statement.bindString(9, matrlPrcbls.get(i).getLocation());
                            EtOrderComponents_statement.bindString(10, matrlPrcbls.get(i).getPartId());
                            EtOrderComponents_statement.bindString(11, matrlPrcbls.get(i).getQuantity());
                            EtOrderComponents_statement.bindString(12, "");
                            EtOrderComponents_statement.bindString(13, "");
                            EtOrderComponents_statement.bindString(14, matrlPrcbls.get(i).getMatrlTxt());
                            EtOrderComponents_statement.bindString(15, "");
                            EtOrderComponents_statement.bindString(16, "");
                            EtOrderComponents_statement.bindString(17, "");
                            EtOrderComponents_statement.bindString(18, "");
                            EtOrderComponents_statement.bindString(19, "");
                            EtOrderComponents_statement.bindString(20, "");
                            EtOrderComponents_statement.bindString(21, "");
                            EtOrderComponents_statement.bindString(22, "");
                            EtOrderComponents_statement.bindString(23, matrlPrcbls.get(i).getStatus());
                            EtOrderComponents_statement.bindString(24, matrlPrcbls.get(i).getReceipt());
                            EtOrderComponents_statement.bindString(25, matrlPrcbls.get(i).getUnloading());
                            EtOrderComponents_statement.execute();
                        }
                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                    }
                } catch (Exception e) {
                }

                try {
                    ArrayList<OrdrObjectPrcbl> objectPrcbls = ohp.getOrdrObjectPrcbls();
                    if (objectPrcbls.size() > 0) {
                        App_db.beginTransaction();
                        String EtOrderOlist_sql = "Insert into EtOrderOlist (UUID, Aufnr, Obknr, Obzae, Qmnum, Equnr, Strno, Tplnr, Bautl, Qmtxt, Pltxt, Eqktx, Maktx, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        SQLiteStatement EtOrderOlist_statement = App_db.compileStatement(EtOrderOlist_sql);
                        EtOrderOlist_statement.clearBindings();
                        for (int i = 0; i < objectPrcbls.size(); i++) {
                            EtOrderOlist_statement.bindString(1, ohp.getOrdrUUId());
                            EtOrderOlist_statement.bindString(2, ohp.getOrdrId());
                            EtOrderOlist_statement.bindString(3, "");
                            EtOrderOlist_statement.bindString(4, "");
                            EtOrderOlist_statement.bindString(5, objectPrcbls.get(i).getNotifNo());
                            EtOrderOlist_statement.bindString(6, objectPrcbls.get(i).getEquipId());
                            EtOrderOlist_statement.bindString(7, "");
                            EtOrderOlist_statement.bindString(8, objectPrcbls.get(i).getTplnr());
                            EtOrderOlist_statement.bindString(9, "");
                            EtOrderOlist_statement.bindString(10, "");
                            EtOrderOlist_statement.bindString(11, "");
                            EtOrderOlist_statement.bindString(12, objectPrcbls.get(i).getEquipTxt());
                            EtOrderOlist_statement.bindString(13, "");
                            EtOrderOlist_statement.bindString(14, objectPrcbls.get(i).getAction());
                            EtOrderOlist_statement.execute();
                        }
                        App_db.setTransactionSuccessful();
                        App_db.endTransaction();
                    }
                } catch (Exception e) {
                }

                try {
                    ArrayList<NotifOrdrStatusPrcbl> ordrStatusPrcbl = ohp.getOrdrStatusPrcbls();
                    if (ordrStatusPrcbl.size() > 0) {
                        for (int i = 0; i < ordrStatusPrcbl.size(); i++) {
                            if (ordrStatusPrcbl.get(i).getAct().equalsIgnoreCase("X")) {
                                String txt04 = ordrStatusPrcbl.get(i).getTxt04();
                                String txt30 = ordrStatusPrcbl.get(i).getTxt30();
                                ContentValues cv = new ContentValues();
                                cv.put("Action", "I");
                                cv.put("Act", "X");
                                App_db.update("EtOrderStatus", cv, "Aufnr = ? and Txt04 = ? and Txt30 = ?", new String[]{ohp.getOrdrId(), txt04, txt30});
                            } else {
                                String txt04 = ordrStatusPrcbl.get(i).getTxt04();
                                String txt30 = ordrStatusPrcbl.get(i).getTxt30();
                                ContentValues cv = new ContentValues();
                                cv.put("Action", "I");
                                cv.put("Act", "");
                                App_db.update("EtOrderStatus", cv, "Aufnr = ? and Txt04 = ? and Txt30 = ?", new String[]{ohp.getOrdrId(), txt04, txt30});
                            }
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    UUID uniqueKey = UUID.randomUUID();
                    DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                    DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                    Date todaysdate = new Date();
                    String date = date_format.format(todaysdate.getTime());
                    String time = time_format.format(todaysdate.getTime());

                    String sql11 = "Insert into Alert_Log (DATE, TIME, DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID, STATUS, UUID, MESSAGE, LOG_UUID,OBJECT_TXT) values(?,?,?,?,?,?,?,?,?,?,?);";
                    SQLiteStatement statement11 = App_db.compileStatement(sql11);
                    App_db.beginTransaction();
                    statement11.clearBindings();
                    statement11.bindString(1, date);
                    statement11.bindString(2, time);
                    statement11.bindString(3, "Order");
                    statement11.bindString(4, "Change");
                    statement11.bindString(5, "");
                    statement11.bindString(6, ohp.getOrdrId());
                    statement11.bindString(7, "Fail");
                    statement11.bindString(8, ohp.getOrdrUUId());
                    statement11.bindString(9, "");
                    statement11.bindString(10, uniqueKey.toString());
                    statement11.bindString(11, ohp.getOrdrShrtTxt());
                    statement11.execute();
                    App_db.setTransactionSuccessful();
                    App_db.endTransaction();
                } catch (Exception e) {
                }

                customProgressDialog.dismiss_progress_dialog();
                decision_dialog.dismiss();
                Orders_Change_Activity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (ORDR_STATUS):
                if (resultCode == RESULT_OK) {
                    ArrayList<NotifOrdrStatusPrcbl> osp = new ArrayList<>();
                    osp = data.getParcelableArrayListExtra("status_object");
                    ohp.setOrdrStatusPrcbls(osp);
                    osp = null;
                }
                break;
        }
    }

    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Orders_Change_Activity.this,
                    getResources().getString(R.string.change_order));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Orders_Change_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.equals("success"))
                new Create_Order().execute("");
            else
                error_dialog.show_error_dialog(Orders_Change_Activity.this,
                        getResources().getString(R.string.unable_change));
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    public class Create_Order extends AsyncTask<String, Integer, Void> {
        String[] Response = new String[2];
        ArrayList<WcdDup_Object> wcdDup_al = new ArrayList<>();
        ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();
        ArrayList<HashMap<String, String>> operation_custom_info_arraylist = new ArrayList<>();
        ArrayList<HashMap<String, String>> material_custom_info_arraylist = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Orders_Change_Activity.this,
                    getResources().getString(R.string.change_order));
            /*Fetching Header Custom Info Data*/
            Orders_CH_General_Fragment header_tab =
                    (Orders_CH_General_Fragment) getSupportFragmentManager()
                            .findFragmentByTag(makeFragmentName(R.id.order_vp, 0));
            ArrayList<HashMap<String, String>> header_custom_info_arraylist =
                    header_tab.getHeaderCustominfoData();
            if (header_custom_info_arraylist.size() > 0) {
                for (int i = 0; i < header_custom_info_arraylist.size(); i++) {
                    Model_CustomInfo mnc = new Model_CustomInfo();
                    mnc.setZdoctype(header_custom_info_arraylist.get(i).get("Zdoctype"));
                    mnc.setZdoctypeItem(header_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                    mnc.setTabname(header_custom_info_arraylist.get(i).get("Tabname"));
                    mnc.setFieldname(header_custom_info_arraylist.get(i).get("Fieldname"));
                    mnc.setDatatype(header_custom_info_arraylist.get(i).get("Datatype"));
                    String datatype = header_custom_info_arraylist.get(i).get("Datatype");
                    if (datatype.equalsIgnoreCase("DATS")) {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "MMM dd, yyyy";
                        String outputPattern = "yyyyMMdd";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try {
                            Date date = inputFormat.parse(value);
                            String formatted_date = outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        } catch (Exception e) {
                            mnc.setValue("");
                        }
                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "HH:mm:ss";
                        String outputPattern = "HHmmss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try {
                            Date date = inputFormat.parse(value);
                            String formatted_date = outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        } catch (Exception e) {
                            mnc.setValue("");
                        }
                    } else {
                        mnc.setValue(header_custom_info_arraylist.get(i).get("Value"));
                    }
                    mnc.setFlabel(header_custom_info_arraylist.get(i).get("Flabel"));
                    mnc.setSequence(header_custom_info_arraylist.get(i).get("Sequence"));
                    mnc.setLength(header_custom_info_arraylist.get(i).get("Length"));
                    header_custominfo.add(mnc);
                }
            }
            /*Fetching Header Custom Info Data*/
        }

        @Override
        protected Void doInBackground(String... transmit) {
            String transmit_type = transmit[0];
            Response = new Order_Create_Change()
                    .Post_Create_Order(Orders_Change_Activity.this, ohp, transmit_type,
                            "CHORD", ohp.getOrdrId(), "",
                            header_custominfo, operation_custom_info_arraylist,
                            material_custom_info_arraylist, "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();

            if (Response[0].startsWith("S")) {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for (int i = 0; i < sp.length; i++) {
                    if (i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                successDialog.dismissActivity(Orders_Change_Activity.this
                        , response.toString());
            } else if (Response[0].startsWith("E")) {
                StringBuilder response = new StringBuilder();
                String[] sp = Response[0].split("\n");
                for (int i = 0; i < sp.length; i++) {
                    if (i >= 1)
                        response.append("\n");
                    response.append(sp[0].substring(2));
                }
                error_dialog.show_error_dialog(Orders_Change_Activity.this,
                        response.toString());
            } else if (Response[0].startsWith("W")) {
                try {
                    JSONArray wcd_Json = new JSONArray(Response[1]);
                    if (wcd_Json.length() > 0) {
                        for (int i = 0; i < wcd_Json.length(); i++) {
                            WcdDup_Object wdo =
                                    new WcdDup_Object(wcd_Json.getJSONObject(i)
                                            .getString("Aufnr"),
                                            wcd_Json.getJSONObject(i).getString("Stxt"),
                                            wcd_Json.getJSONObject(i).getString("Sysst"),
                                            wcd_Json.getJSONObject(i).getString("Wcnr"));
                            wcdDup_al.add(wdo);
                        }
                        if (wcdDup_al.size() > 0) {
                            final Dialog aa = new Dialog(Orders_Change_Activity.this);
                            aa.getWindow()
                                    .setBackgroundDrawableResource(android.R.color.transparent);
                            aa.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            aa.setCancelable(false);
                            aa.setCanceledOnTouchOutside(false);
                            aa.setContentView(R.layout.notifications_duplicate_dialog);
                            aa.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
                            Button yes_button = (Button) aa.findViewById(R.id.yes_button);
                            Button no_button = (Button) aa.findViewById(R.id.no_button);
                            TextView title_textView = aa.findViewById(R.id.title_textview);
                            TextView text_msg = (TextView) aa.findViewById(R.id.text_msg);
                            RecyclerView list_recycleview = aa.findViewById(R.id.recyclerview);
                            title_textView.setText(getString(R.string.open_iso));
                            text_msg.setText("Do you want to continue?");
                            yes_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new Create_Order().execute("FUNC");
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
                                RecyclerView.LayoutManager layoutManager =
                                        new LinearLayoutManager(Orders_Change_Activity.this);
                                list_recycleview.setLayoutManager(layoutManager);
                                WcdDupAdapter adapter =
                                        new WcdDupAdapter(Orders_Change_Activity.this,
                                                wcdDup_al);
                                list_recycleview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                list_recycleview.setVisibility(View.VISIBLE);
                            } else {
                                list_recycleview.setVisibility(GONE);
                            }
                            aa.show();
                        }
                    }
                } catch (Exception e) {

                }
            } else
                error_dialog.show_error_dialog(Orders_Change_Activity.this, Response[0]);
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wcd_dup_list,
                    parent, false);
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

    private void confirmationDialog(String message) {
        final Dialog cancel_dialog =
                new Dialog(Orders_Change_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = cancel_dialog.findViewById(R.id.description_textview);
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

    protected void animateFab(final boolean selected) {
        fab.clearAnimation();
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon
                if (selected) {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    fab.setImageResource(R.drawable.ic_delete_icon);
                } else {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    fab.setImageResource(R.drawable.ic_add_white_24px);
                }

                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f,
                        1f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
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
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(),
                            "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }

    private String dateFormat(String date) {
        if (date != null && !date.equals("")) {
            String inputPattern1 = "MMM dd, yyyy";
            String outputPattern1 = "yyyyMMdd";
            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            try {
                Date date1 = inputFormat1.parse(date);
                String Datefr = outputFormat1.format(date1);
                return Datefr;
            } catch (ParseException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    public void updateTabDataCount() {
        Orders_CH_Operation_Fragment operation_fragment =
                (Orders_CH_Operation_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.order_vp, 1));
        if (operation_fragment.OperationsSize() > 0) {
            order_tl.getTabAt(1).setText(getString(R.string.operation_p, operation_fragment.OperationsSize()));
        } else {
            order_tl.getTabAt(1).setText(getResources().getString(R.string.operations));
        }

        Orders_CH_Material_Fragment material_fragment =
                (Orders_CH_Material_Fragment) getSupportFragmentManager()
                        .findFragmentByTag(makeFragmentName(R.id.order_vp, 2));
        if (material_fragment.MaterialSize() > 0) {
            order_tl.getTabAt(2).setText(getString(R.string.material_p, material_fragment.MaterialSize()));
        } else {
            order_tl.getTabAt(2).setText(getResources().getString(R.string.material));
        }

        setCustomFont(order_tl);
    }

    private void deleteAttachments() {
        try {
            App_db.execSQL("delete from Orders_Attachments");
        } catch (Exception e) {

        }
    }
}
