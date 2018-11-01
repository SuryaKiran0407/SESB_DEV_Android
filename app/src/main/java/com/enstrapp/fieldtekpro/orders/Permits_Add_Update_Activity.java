package com.enstrapp.fieldtekpro.orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog1;
import com.enstrapp.fieldtekpro.DateTime.TimePickerDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Permits_Add_Update_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView title_tv, wrkReq_tv;
    String applId = "", shrtTxt = "", funcLoc = "", iwerk = "", priority = "", wapinr = "", woco = "",
            priority_id = "", priority_txt = "", equipId = "", equipName = "", flag = "", orderId = "",
            Refobj = "", OrdrUUID = "";
    String[] usage = new String[2];
    TextInputEditText shortTxt_tiet, funcLoc_tiet, frmDt_tiet, frmTm_tiet, toDt_tiet, toTm_tiet,
            extntn_tiet, priority_tiet, auth_tiet, usage_tiet;
    ImageView frmDt_iv, frmTm_iv, toDt_iv, toTm_iv, extntn_iv, priority_iv, auth_iv, usage_iv,
            issueStatus1_iv, issueStatus2_iv, issueStatus3_iv;
    CheckBox setPrep_cb, completed_cb;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<>();
    ArrayList<OrdrPermitPrcbl> wa_al = new ArrayList<>();
    OrdrPermitPrcbl opp = new OrdrPermitPrcbl();
    static final int AUTH = 287;
    static final int PRIORITY = 6;
    static final int FRMDT = 7;
    static final int FRMTM = 8;
    static final int TODT = 9;
    static final int TOTM = 10;
    static final int WrkReq = 8663;
    static final int ISSUE = 863;
    static final int STATUS = 873;
    static final int ISO = 1080;
    ArrayList<OrdrWaChkReqPrcbl> owrp_al = new ArrayList<>();
    ArrayList<OrdrWcagnsPrcbl> wcg_al = new ArrayList<>();
    ArrayList<NotifOrdrStatusPrcbl> status_al = new ArrayList<>();
    ArrayList<OrdrPermitPrcbl> iso_al = new ArrayList<>();
    Button cancel_bt, save_bt, status_bt;
    LinearLayout issuePermit_ll, extn_ll, footer_ll;
    View status_view;
    ImageButton attachments_ib, back_ib;
    ImageView menu_imageview;
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Error_Dialog errorDialog = new Error_Dialog();
    OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    boolean permitExist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_permits);

        back_ib = findViewById(R.id.back_iv);
        menu_imageview = findViewById(R.id.menu_imageview);
        status_view = findViewById(R.id.status_view);
        status_bt = findViewById(R.id.status_bt);
        save_bt = findViewById(R.id.save_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        wrkReq_tv = findViewById(R.id.wrkReq_tv);
        title_tv = findViewById(R.id.title_tv);
        shortTxt_tiet = findViewById(R.id.shortTxt_tiet);
        funcLoc_tiet = findViewById(R.id.funcLoc_tiet);
        frmDt_tiet = findViewById(R.id.frmDt_tiet);
        frmTm_tiet = findViewById(R.id.frmTm_tiet);
        toDt_tiet = findViewById(R.id.toDt_tiet);
        toTm_tiet = findViewById(R.id.toTm_tiet);
        extntn_tiet = findViewById(R.id.extntn_tiet);
        priority_tiet = findViewById(R.id.priority_tiet);
        usage_tiet = findViewById(R.id.usage_tiet);
        frmDt_iv = findViewById(R.id.frmDt_iv);
        frmTm_iv = findViewById(R.id.frmTm_iv);
        toDt_iv = findViewById(R.id.toDt_iv);
        toTm_iv = findViewById(R.id.toTm_iv);
        extntn_iv = findViewById(R.id.extntn_iv);
        priority_iv = findViewById(R.id.priority_iv);
        auth_tiet = findViewById(R.id.auth_tiet);
        auth_iv = findViewById(R.id.auth_iv);
        setPrep_cb = findViewById(R.id.setPrep_cb);
        completed_cb = findViewById(R.id.completed_cb);
        issuePermit_ll = findViewById(R.id.issuePermit_ll);
        issueStatus1_iv = findViewById(R.id.issueStatus1_iv);
        issueStatus2_iv = findViewById(R.id.issueStatus2_iv);
        issueStatus3_iv = findViewById(R.id.issueStatus3_iv);
        extn_ll = findViewById(R.id.extn_ll);
        footer_ll = findViewById(R.id.footer_ll);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = Permits_Add_Update_Activity.this
                .openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        final int time = Integer.parseInt(sdf.format(new Date()));

        opp = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ww_al = extras.getParcelableArrayList("opp");
            flag = extras.getString("flag");
            orderId = extras.getString("order");
            OrdrUUID = extras.getString("UUID");
            Refobj = extras.getString("Refobj");
            if (ww_al != null && ww_al.size() > 0) {
                wapinr = extras.getString("wapinr");
                if (ww_al.get(0).getWaWdPrcbl_Al() != null)
                    wa_al = ww_al.get(0).getWaWdPrcbl_Al();
                if (wa_al.size() > 0)
                    for (OrdrPermitPrcbl oop : wa_al) {
                        if (oop.getWapinr().equals(wapinr))
                            opp = oop;
                    }
                if (opp != null) {
                    orderId = opp.getAufnr();
                    title_tv.setText(opp.getApplName());
                    funcLoc = extras.getString("func_loc");
                    funcLoc_tiet.setText(extras.getString("func_loc"));
                    shortTxt_tiet.setText(opp.getStxt());
                    iwerk = extras.getString("iwerk");
                    applId = opp.getObjtyp();
                    woco = extras.getString("woco");
                    equipId = extras.getString("equip");
                    equipName = extras.getString("equip_txt");
                    priority_id = extras.getString("priority_id");
                    priority_txt = extras.getString("priority_txt");
                    if (applId.equalsIgnoreCase("1"))
                        menu_imageview.setVisibility(View.VISIBLE);
                    else
                        menu_imageview.setVisibility(View.GONE);

                    if (!wapinr.startsWith("WA"))
                        if (isExtension(String.valueOf(opp.getExtperiod()), applId)) {
                            extn_ll.setVisibility(VISIBLE);
                            extntn_iv.setEnabled(true);
                            extntn_tiet.setText("" + opp.getExtperiod());
                        } else {
                            extn_ll.setVisibility(GONE);
                        }

                    if (opp.getStatusPrcbl_Al() != null && opp.getStatusPrcbl_Al().size() > 0) {
                        status_bt.setVisibility(VISIBLE);
                        status_view.setVisibility(VISIBLE);
                    }
                    if (opp.getPriokx() != null)
                        priority_tiet.setText(getString(R.string.hypen_text, opp.getPriok(),
                                opp.getPriokx()));
                    usage_tiet.setText(getString(R.string.hypen_text, opp.getUsage(), opp.getUsagex()));
                    auth_tiet.setText(getString(R.string.hypen_text, opp.getBegru(), opp.getBegtx()));
                    frmDt_tiet.setText(opp.getDatefr());
                    frmTm_tiet.setText(opp.getTimefr());
                    toDt_tiet.setText(opp.getDateto());
                    toTm_tiet.setText(opp.getTimeto());
                    if (opp.getComp() != null && opp.getComp().equals("X")) {
                        completed_cb.setEnabled(false);
                        completed_cb.setChecked(true);
                        setPrep_cb.setEnabled(false);
                        setPrep_cb.setChecked(true);
                        shortTxt_tiet.setEnabled(false);
                        save_bt.setVisibility(GONE);
                        status_view.setVisibility(GONE);
                    } else if (opp.getPrep() != null && opp.getPrep().equals("X")) {
                        completed_cb.setChecked(false);
                        setPrep_cb.setChecked(true);
                        save_bt.setVisibility(VISIBLE);
                        shortTxt_tiet.setEnabled(false);
                    } else {
                        save_bt.setVisibility(VISIBLE);
                        completed_cb.setChecked(false);
                        setPrep_cb.setChecked(false);
                    }
                    owrp_al = opp.getWaChkReqPrcbl_Al();

                    if (opp.getAppr() != null && !opp.getAppr().equals(""))
                        if (opp.getAppr().equals("G")) {
                            issuePermit_ll.setVisibility(VISIBLE);
                            issueStatus1_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                            issueStatus2_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                            issueStatus3_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                        } else if (opp.getAppr().equals("Y")) {
                            issuePermit_ll.setVisibility(VISIBLE);
                            issueStatus1_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                            issueStatus2_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                            issueStatus3_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                        } else if (opp.getAppr().equals("R")) {
                            issuePermit_ll.setVisibility(VISIBLE);
                            issueStatus1_iv.setImageResource(R.drawable.ic_create_circle_icon);
                            issueStatus2_iv.setImageResource(R.drawable.ic_create_circle_icon);
                            issueStatus3_iv.setImageResource(R.drawable.ic_create_circle_icon);
                        }
                } else {
                    title_tv.setText(extras.getString("permit_txt"));
                    applId = extras.getString("permit_id");
                    if (applId.equals("1"))
                        menu_imageview.setVisibility(View.VISIBLE);
                    shrtTxt = extras.getString("shrt_txt");
                    funcLoc = extras.getString("func_loc");
                    iwerk = extras.getString("iwerk");
                    equipId = extras.getString("equip");
                    equipName = extras.getString("equip_txt");
                    funcLoc_tiet.setText(extras.getString("func_loc"));
                    shortTxt_tiet.setText(extras.getString("shrt_txt"));
                    if (opp != null) {
                    } else {
                        opp = new OrdrPermitPrcbl();
                    }
                    opp.setApplName(extras.getString("permit_txt"));
                    if (extras.getString("priority_id") != null) {
                        priority_id = extras.getString("priority_id");
                        priority_txt = extras.getString("priority_txt");
                        opp.setPriok(extras.getString("priority_id"));
                        opp.setPriokx(extras.getString("priority_txt"));
                        priority_tiet.setText(getString(R.string.hypen_text, extras.getString("priority_id"),
                                extras.getString("priority_txt")));
                    }
                    if (time >= 8) {
                        frmTm_tiet.setText("08:00:00");
                        toTm_tiet.setText("08:00:00");
                        frmDt_tiet.setText(df.format(c.getTime()));
                        toDt_tiet.setText(GetFutureDate());
                    } else if (time < 8) {
                        frmTm_tiet.setText("08:00:00");
                        toTm_tiet.setText("08:00:00");
                        frmDt_tiet.setText(GetPreviousDate());
                        toDt_tiet.setText(df.format(c.getTime()));
                    }

                    usage = GetUsage("WA", applId);
                    opp.setUsage(usage[0]);
                    opp.setUsagex(usage[1]);
                    if (opp.getUsagex() != null && !opp.getUsagex().equals(""))
                        usage_tiet.setText(getString(R.string.hypen_text, opp.getUsage(), opp.getUsagex()));
                    else
                        usage_tiet.setText(opp.getUsage());

                }
            }
        }
        if (wapinr.startsWith("WA")) {
            if (applId.equals("1")) {
                setPrep_cb.setVisibility(GONE);
                completed_cb.setVisibility(GONE);
            } else {
                setPrep_cb.setVisibility(VISIBLE);
                completed_cb.setVisibility(VISIBLE);
            }
        } else {
            setPrep_cb.setVisibility(VISIBLE);
            completed_cb.setVisibility(VISIBLE);
        }

        shortTxt_tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                opp.setStxt("");
                if (shortTxt_tiet.getText().toString() != null &&
                        !shortTxt_tiet.getText().toString().equals("")) {
                    opp.setStxt(shortTxt_tiet.getText().toString());
                } else {
                    opp.setStxt("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        frmDt_iv.setOnClickListener(this);
        frmTm_iv.setOnClickListener(this);
        toDt_iv.setOnClickListener(this);
        toTm_iv.setOnClickListener(this);
        extntn_iv.setOnClickListener(this);
        priority_iv.setOnClickListener(this);
        auth_iv.setOnClickListener(this);
        wrkReq_tv.setOnClickListener(this);
        save_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        issuePermit_ll.setOnClickListener(this);
        status_bt.setOnClickListener(this);
        setPrep_cb.setOnClickListener(this);
        completed_cb.setOnClickListener(this);
        menu_imageview.setOnClickListener(this);
        back_ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.back_iv):
                setResult(RESULT_CANCELED);
                Permits_Add_Update_Activity.this.finish();
                break;

            case (R.id.auth_iv):
                if (!setPrep_cb.isChecked()) {
                    final Intent authIntent =
                            new Intent(Permits_Add_Update_Activity.this,
                                    Authorization_Activity.class);
                    startActivityForResult(authIntent, AUTH);
                }
                break;

            case (R.id.priority_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent priorityIntent =
                            new Intent(Permits_Add_Update_Activity.this,
                                    Priority_Activity.class);
                    startActivityForResult(priorityIntent, PRIORITY);
                }
                break;

            case (R.id.frmDt_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent frmDtIntent = new Intent(Permits_Add_Update_Activity.this,
                            DatePickerDialog1.class);
                    frmDtIntent.putExtra("givenDate", frmDt_tiet.getText().toString());
                    startActivityForResult(frmDtIntent, FRMDT);
                }
                break;

            case (R.id.toDt_iv):
                if (!completed_cb.isChecked()) {
                    Intent toDtIntent = new Intent(Permits_Add_Update_Activity.this,
                            DatePickerDialog1.class);
                    toDtIntent.putExtra("givenDate", toDt_tiet.getText().toString());
                    toDtIntent.putExtra("startDate", frmDt_tiet.getText().toString());
                    startActivityForResult(toDtIntent, TODT);
                }
                break;

            case (R.id.frmTm_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent frmTmIntent = new Intent(Permits_Add_Update_Activity.this,
                            TimePickerDialog.class);
                    startActivityForResult(frmTmIntent, FRMTM);
                }
                break;

            case (R.id.toTm_iv):
                if (!completed_cb.isChecked()) {
                    Intent toTmIntent = new Intent(Permits_Add_Update_Activity.this,
                            TimePickerDialog.class);
                    startActivityForResult(toTmIntent, TOTM);
                }
                break;

            case (R.id.wrkReq_tv):
                if (opp.getBegru() != null && !opp.getBegru().equals("")) {
                    String prep = "";
                    if (setPrep_cb.isChecked())
                        prep = "X";
                    else
                        prep = "";
                    Intent wrkReqIntent = new Intent(Permits_Add_Update_Activity.this,
                            WorkRequirements_Activity.class);
                    wrkReqIntent.putExtra("iwerk", iwerk);
                    wrkReqIntent.putExtra("usage_id", opp.getUsage());
                    wrkReqIntent.putExtra("owrp_al", owrp_al);
                    wrkReqIntent.putExtra("prep", prep);
                    wrkReqIntent.putExtra("woco", woco);
                    startActivityForResult(wrkReqIntent, WrkReq);
                } else {
                    errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                            getString(R.string.auth_mandate));
                }
                break;

            case (R.id.issuePermit_ll):
                String prep = "";
                if (setPrep_cb.isChecked())
                    prep = "X";
                else
                    prep = "";
                Intent issueIntent = new Intent(Permits_Add_Update_Activity.this,
                        PermitIssue_Activity.class);
                issueIntent.putExtra("wcg_al", opp.getWcagnsPrcbl_Al());
                issueIntent.putExtra("prep", prep);
                issueIntent.putExtra("woco", woco);
                startActivityForResult(issueIntent, ISSUE);
                break;

            case (R.id.status_bt):
                Intent statusIntent = new Intent(Permits_Add_Update_Activity.this,
                        Order_Status_Activity.class);
                statusIntent.putExtra("statusObject", opp.getStatusPrcbl_Al());
                statusIntent.putExtra("woco", woco);
                statusIntent.putExtra("type", "WA");
                statusIntent.putExtra("heading", "Permit Status");
                startActivityForResult(statusIntent, STATUS);
                break;

            case (R.id.attachments_ib):
                Intent attachIntent = new Intent(Permits_Add_Update_Activity.this,
                        OrdersConfirmAttachment_Activity.class);
                attachIntent.putExtra("ordrId", wapinr);
                startActivity(attachIntent);
                break;

            case (R.id.extntn_iv):
                if (!completed_cb.isChecked()) {
                    if (isExtension(String.valueOf(opp.getExtperiod()), applId))
                        confirmationDialog(getString(R.string.permit_ext),
                                applId);
                }
                break;

            case (R.id.menu_imageview):
                LayoutInflater layoutInflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.orders_permit_isolation_menu,
                        null);
                final PopupWindow popupWindow = new PopupWindow(popupView, 600,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                LinearLayout isolation_ll = popupView.findViewById(R.id.isolation_ll);
                LinearLayout attachments_ll = popupView.findViewById(R.id.attachments_ll);
                LinearLayout jsa_ll = popupView.findViewById(R.id.jsa_ll);
                if (opp.getWaWdPrcbl_Al() != null && !opp.getWaWdPrcbl_Al().equals("")) {
                    if (opp.getWaWdPrcbl_Al().size() > 0) {
                        if (opp.getWaWdPrcbl_Al().get(0).getWcnr() != null &&
                                !opp.getWaWdPrcbl_Al().get(0).getWcnr().equals("")) {
                            if (!opp.getWaWdPrcbl_Al().get(0).getWcnr().startsWith("WD")) {
                                String auth_status = Authorizations
                                        .Get_Authorizations_Data(Permits_Add_Update_Activity.this,
                                                "D", "U");
                                if (auth_status.equalsIgnoreCase("true")) {
                                    isolation_ll.setVisibility(View.VISIBLE);
                                } else {
                                    isolation_ll.setVisibility(View.GONE);
                                }
                            } else {
                                String auth_status = Authorizations
                                        .Get_Authorizations_Data(Permits_Add_Update_Activity.this,
                                                "D", "I");
                                if (auth_status.equalsIgnoreCase("true")) {
                                    isolation_ll.setVisibility(View.VISIBLE);
                                } else {
                                    isolation_ll.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            String auth_status = Authorizations
                                    .Get_Authorizations_Data(Permits_Add_Update_Activity.this,
                                            "D", "I");
                            if (auth_status.equalsIgnoreCase("true")) {
                                isolation_ll.setVisibility(View.VISIBLE);
                            } else {
                                isolation_ll.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        String auth_status = Authorizations
                                .Get_Authorizations_Data(Permits_Add_Update_Activity.this,
                                        "D", "I");
                        if (auth_status.equalsIgnoreCase("true")) {
                            isolation_ll.setVisibility(View.VISIBLE);
                        } else {
                            isolation_ll.setVisibility(View.GONE);
                        }
                    }
                } else {
                    String auth_status = Authorizations
                            .Get_Authorizations_Data(Permits_Add_Update_Activity.this,
                                    "D", "I");
                    if (auth_status.equalsIgnoreCase("true")) {
                        isolation_ll.setVisibility(View.VISIBLE);
                    } else {
                        isolation_ll.setVisibility(View.GONE);
                    }
                }
                popupWindow.showAsDropDown(v);
                if (wapinr.startsWith("WA")) {
                    if (applId.equals("1")) {
                        attachments_ll.setVisibility(GONE);
                    } else {
                        attachments_ll.setVisibility(VISIBLE);
                    }
                } else {
                    attachments_ll.setVisibility(VISIBLE);
                }

                isolation_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (auth_tiet.getText().toString() != null &&
                                !auth_tiet.getText().toString().equals("")) {
                            popupWindow.dismiss();
                            Intent isoIntent =
                                    new Intent(Permits_Add_Update_Activity.this,
                                            Isolation_Add_Update_Activity.class);
                            isoIntent.putExtra("ww_al", ww_al);
                            isoIntent.putExtra("iso", opp.getWaWdPrcbl_Al());
                            isoIntent.putExtra("func_loc", funcLoc_tiet.getText().toString());
                            isoIntent.putExtra("shrtTxt", shortTxt_tiet.getText().toString());
                            isoIntent.putExtra("auth", opp.getBegru());
                            isoIntent.putExtra("auth_txt", opp.getBegtx());
                            isoIntent.putExtra("iwerk", iwerk);
                            isoIntent.putExtra("wapinr", wapinr);
                            isoIntent.putExtra("priority_id", priority_id);
                            isoIntent.putExtra("priority_txt", priority_txt);
                            isoIntent.putExtra("equip", equipId);
                            isoIntent.putExtra("equip_txt", equipName);
                            isoIntent.putExtra("flag", flag);
                            isoIntent.putExtra("woco", woco);
                            isoIntent.putExtra("order", orderId);
                            if (opp.getObjnr() != null)
                                isoIntent.putExtra("objnr", opp.getObjnr());
                            startActivityForResult(isoIntent, ISO);
                        } else {
                            errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                                    getString(R.string.auth_mandate));
                        }
                    }
                });

                jsa_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent intent = new Intent(Permits_Add_Update_Activity.this,
                                JSA_List_Activity.class);
                        intent.putExtra("aufnr", orderId);
                        intent.putExtra("wapinr", wapinr);
                        intent.putExtra("iwerk", iwerk);
                        intent.putExtra("equipId", equipId);
                        startActivity(intent);
                    }
                });

                attachments_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent attachIntent = new Intent(Permits_Add_Update_Activity.this,
                                OrdersConfirmAttachment_Activity.class);
                        attachIntent.putExtra("wapinr", wapinr);
                        startActivity(attachIntent);
                    }
                });
                break;

            case (R.id.setPrep_cb):
                if (setPrep_cb.isChecked()) {
                    if (applId.equals("1")) {
                        if (!isAttached(wapinr)) {
                            shortTxt_tiet.setEnabled(true);
                            setPrep_cb.setChecked(false);
                            errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                                    getString(R.string.attch_jsa));
                        } else {
                            shortTxt_tiet.setEnabled(false);
                            opp.setPrep("X");
                        }
                    } else {
                        shortTxt_tiet.setEnabled(false);
                        opp.setPrep("X");
                    }
                } else {
                    completed_cb.setChecked(false);
                    opp.setComp("");
                    shortTxt_tiet.setEnabled(true);
                    opp.setPrep("");
                }
                break;

            case (R.id.completed_cb):
                if (completed_cb.isChecked()) {
                    if (setPrep_cb.isChecked()) {
                        opp.setComp("X");
                        setPrep_cb.setEnabled(false);
                    } else {
                        errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                                getString(R.string.check_setprep));
                        opp.setComp("");
                        completed_cb.setChecked(false);
                    }
                } else {
                    opp.setComp("");
                }
                break;

            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                Permits_Add_Update_Activity.this.finish();
                break;

            case (R.id.save_bt):
                boolean GWP = false;
                if (applId.equals("1"))
                    GWP = true;
                else
                    GWP = false;
                if (!GWP)
                    if (owrp_al != null) {
                        if (owrp_al.size() > 0) {
                            for (OrdrWaChkReqPrcbl owchp : owrp_al) {
                                if (owchp.getWapinr() != null && !owchp.getWapinr().equals("")) {
                                } else {
                                    owchp.setWapinr(wapinr);
                                    owchp.setWapityp(applId);
                                }
                            }
                            if (opp.getWapinr() != null && !opp.getWapinr().equals("")) {
                                if (opp.getWapinr().startsWith("WA")) {
                                    permitExist = false;
                                    opp.setAction("I");
                                    if (flag.equals("CH"))
                                        opp.setRefobj(Refobj);
                                } else {
                                    permitExist = true;
                                    opp.setAction("U");
                                }
                            } else {
                                opp.setWapinr(wapinr);
                                if (opp.getWapinr().startsWith("WA")) {
                                    permitExist = false;
                                    opp.setAction("I");
                                    if (flag.equals("CH"))
                                        opp.setRefobj(Refobj);
                                } else {
                                    permitExist = true;
                                    opp.setAction("U");
                                }
                            }
                            opp.setAufnr(orderId);
                            if (completed_cb.isChecked()) {
                                opp.setPrep("");
                                opp.setCrea("");
                                opp.setComp("X");
                            } else if (setPrep_cb.isChecked()) {
                                opp.setPrep("X");
                                opp.setCrea("");
                                opp.setComp("");
                            } else {
                                opp.setPrep("");
                                opp.setCrea("X");
                                opp.setComp("");
                            }
                            if (opp.getObjtyp() != null && !opp.getObjtyp().equals("")) {
                            } else {
                                opp.setObjtyp(applId);
                            }
                            opp.setStxt(shortTxt_tiet.getText().toString());
                            opp.setIwerk(iwerk);
                            opp.setDatefr(frmDt_tiet.getText().toString());
                            opp.setDateto(toDt_tiet.getText().toString());
                            opp.setTimefr(frmTm_tiet.getText().toString());
                            opp.setTimeto(toTm_tiet.getText().toString());
                            opp.setWaChkReqPrcbl_Al(owrp_al);
                            if (wa_al.size() > 0) {
                                ArrayList<OrdrPermitPrcbl> rm_al = new ArrayList<>();
                                rm_al.addAll(wa_al);
                                for (OrdrPermitPrcbl oop : rm_al) {
                                    if (oop.getWapinr().equals(wapinr))
                                        wa_al.remove(oop);
                                }
                            } else {
                                wa_al = new ArrayList<>();
                            }
                            ww_al.get(0).setWaWdPrcbl_Al(wa_al);
                            if (flag.equals("CH")) {
                                cd = new ConnectionDetector(Permits_Add_Update_Activity.this);
                                isInternetPresent = cd.isConnectingToInternet();
                                if (isInternetPresent) {
                                    ohp = new OrdrHeaderPrcbl();
                                    ArrayList<OrdrPermitPrcbl> ww_A = new ArrayList<>();
                                    ArrayList<OrdrPermitPrcbl> wa_A = new ArrayList<>();
                                    wa_A.add(opp);
                                    for (int i = 0; i < ww_al.get(0).getWaWdPrcbl_Al().size(); i++) {
                                        OrdrPermitPrcbl wcg = new OrdrPermitPrcbl();
                                        ArrayList<OrdrWcagnsPrcbl> wcg_al = new ArrayList<>();
                                        if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al() != null)
                                            for (int j = 0; j < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al().size(); j++) {

                                                OrdrWcagnsPrcbl wg = ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al().get(j);
                                                wcg_al.add(wg);
                                            }
                                        if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al() != null)
                                            for (int k = 0; k < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().size(); k++) {
                                                if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al() != null)
                                                    for (int l = 0; l < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().size(); l++) {
                                                        OrdrWcagnsPrcbl wg = ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l);
                                                        wcg_al.add(wg);
                                                    }
                                            }
                                        wcg.setWcagnsPrcbl_Al(wcg_al);
                                        wa_A.add(wcg);
                                    }

                                    ww_A.addAll(ww_al);
                                    ww_A.get(0).setWaWdPrcbl_Al(wa_A);
                                    ohp.setOrdrPermitPrcbls(ww_A);

                                    if (permitExist)
                                        confirmationDialog(getString(R.string.permit_chan_submit));
                                    else
                                        confirmationDialog(getString(R.string.permit_crea_submit));
                                } else {
                                    network_connection_dialog
                                            .show_network_connection_dialog(
                                                    Permits_Add_Update_Activity.this);
                                }
                            } else {
                                wa_al.add(opp);
                                ww_al.get(0).setWaWdPrcbl_Al(wa_al);
                                Intent saveIntent = new Intent();
                                saveIntent.putExtra("opp", ww_al);
                                setResult(RESULT_OK, saveIntent);
                                Permits_Add_Update_Activity.this.finish();
                            }

                        } else {
                            errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                                    getString(R.string.wrk_req_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                                getString(R.string.wrk_req_mandate));
                    }
                else {
                    if (auth_tiet.getText().toString() != null && !auth_tiet.getText().toString().equals("")) {
                        if (owrp_al != null) {
                            if (owrp_al.size() > 0) {
                                for (OrdrWaChkReqPrcbl owchp : owrp_al) {
                                    if (owchp.getWapinr() != null && !owchp.getWapinr().equals("")) {
                                    } else {
                                        owchp.setWapinr(wapinr);
                                        owchp.setWapityp(applId);
                                    }
                                }
                            }
                        }
                        if (opp.getWapinr() != null && !opp.getWapinr().equals("")) {
                            if (opp.getWapinr().startsWith("WA")) {
                                permitExist = false;
                                opp.setAction("I");
                                if (flag.equals("CH"))
                                    opp.setRefobj(Refobj);
                            } else {
                                permitExist = true;
                                opp.setAction("U");
                            }
                        } else {
                            opp.setWapinr(wapinr);
                            if (wapinr.startsWith("WA")) {
                                permitExist = false;
                                opp.setAction("I");
                                if (flag.equals("CH"))
                                    opp.setRefobj(Refobj);
                            } else {
                                permitExist = true;
                                opp.setAction("U");
                            }
                        }
                        opp.setAufnr(orderId);
                        if (completed_cb.isChecked()) {
                            opp.setPrep("");
                            opp.setCrea("");
                            opp.setComp("X");
                        } else if (setPrep_cb.isChecked()) {
                            opp.setPrep("X");
                            opp.setCrea("");
                            opp.setComp("");
                        } else {
                            opp.setPrep("");
                            opp.setCrea("X");
                            opp.setComp("");
                        }
                        if (opp.getObjtyp() != null && !opp.getObjtyp().equals("")) {
                        } else {
                            opp.setObjtyp(applId);
                        }
                        opp.setStxt(shortTxt_tiet.getText().toString());
                        opp.setIwerk(iwerk);
                        opp.setDatefr(frmDt_tiet.getText().toString());
                        opp.setDateto(toDt_tiet.getText().toString());
                        opp.setTimefr(frmTm_tiet.getText().toString());
                        opp.setTimeto(toTm_tiet.getText().toString());
                        opp.setWaChkReqPrcbl_Al(owrp_al);
                        if (wa_al.size() > 0) {
                            ArrayList<OrdrPermitPrcbl> rm_al = new ArrayList<>();
                            rm_al.addAll(wa_al);
                            for (OrdrPermitPrcbl oop : rm_al) {
                                if (oop.getWapinr().equals(wapinr))
                                    wa_al.remove(oop);
                            }
                        } else {
                            wa_al = new ArrayList<>();
                        }
                        ww_al.get(0).setWaWdPrcbl_Al(wa_al);
                        if (flag.equals("CH")) {
                            cd = new ConnectionDetector(Permits_Add_Update_Activity.this);
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                ohp = new OrdrHeaderPrcbl();
                                ArrayList<OrdrPermitPrcbl> ww_A = new ArrayList<>();
                                ArrayList<OrdrPermitPrcbl> wa_A = new ArrayList<>();
                                wa_A.add(opp);
                                for (int i = 0; i < ww_al.get(0).getWaWdPrcbl_Al().size(); i++) {
                                    OrdrPermitPrcbl wcg = new OrdrPermitPrcbl();
                                    ArrayList<OrdrWcagnsPrcbl> wcg_al = new ArrayList<>();
                                    if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al() != null)
                                        for (int j = 0; j < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al().size(); j++) {
                                            OrdrWcagnsPrcbl wg = ww_al.get(0).getWaWdPrcbl_Al().get(i).getWcagnsPrcbl_Al().get(j);
                                            wcg_al.add(wg);
                                        }
                                    if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al() != null)
                                        for (int k = 0; k < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().size(); k++) {
                                            if (ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al() != null)
                                                for (int l = 0; l < ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().size(); l++) {
                                                    OrdrWcagnsPrcbl wg = ww_al.get(0).getWaWdPrcbl_Al().get(i).getWaWdPrcbl_Al().get(k).getWcagnsPrcbl_Al().get(l);
                                                    wcg_al.add(wg);
                                                }
                                        }
                                    wcg.setWcagnsPrcbl_Al(wcg_al);
                                    wa_A.add(wcg);
                                }
                                ww_A.addAll(ww_al);
                                ww_A.get(0).setWaWdPrcbl_Al(wa_A);
                                ohp.setOrdrPermitPrcbls(ww_A);
                                if (permitExist)
                                    confirmationDialog(getString(R.string.permit_chan_submit));
                                else
                                    confirmationDialog(getString(R.string.permit_crea_submit));
                            } else {
                                network_connection_dialog.show_network_connection_dialog(Permits_Add_Update_Activity.this);
                            }
                        } else {
                            wa_al.add(opp);
                            ww_al.get(0).setWaWdPrcbl_Al(wa_al);
                            Intent saveIntent = new Intent();
                            saveIntent.putExtra("opp", ww_al);
                            setResult(RESULT_OK, saveIntent);
                            Permits_Add_Update_Activity.this.finish();
                        }
                    } else {
                        errorDialog.show_error_dialog(Permits_Add_Update_Activity.this, getString(R.string.auth_mandate));
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (AUTH):
                if (resultCode == RESULT_OK) {
                    opp.setBegru(data.getStringExtra("auth_id"));
                    opp.setBegtx(data.getStringExtra("auth_txt"));
                    auth_tiet.setText(getString(R.string.hypen_text, data.getStringExtra("auth_id"),
                            data.getStringExtra("auth_txt")));
                }
                break;

            case (PRIORITY):
                if (resultCode == RESULT_OK) {
                    opp.setPriok(data.getStringExtra("priority_type_id"));
                    opp.setPriokx(data.getStringExtra("priority_type_text"));
                    priority_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("priority_type_id"),
                            data.getStringExtra("priority_type_text")));
                }
                break;

            case (FRMDT):
                if (resultCode == RESULT_OK) {
                    opp.setDatefr(data.getStringExtra("selectedDate"));
                    frmDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TODT):
                if (resultCode == RESULT_OK) {
                    opp.setDateto(data.getStringExtra("selectedDate"));
                    toDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TOTM):
                if (resultCode == RESULT_OK) {
                    opp.setTimeto(data.getStringExtra("selectedTime"));
                    toTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;

            case (FRMTM):
                if (resultCode == RESULT_OK) {
                    opp.setTimefr(data.getStringExtra("selectedTime"));
                    frmTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;

            case (WrkReq):
                if (resultCode == RESULT_OK) {
                    owrp_al = data.getParcelableArrayListExtra("owrp_al");
                }
                break;

            case (STATUS):
                if (resultCode == RESULT_OK) {
                    status_al = data.getParcelableArrayListExtra("status_object");
                    opp.setStatusPrcbl_Al(status_al);
                }
                break;

            case (ISO):
                if (resultCode == RESULT_OK) {
                    iso_al = data.getParcelableArrayListExtra("iso_al");
                    opp.setWaWdPrcbl_Al(iso_al);
                }
                break;

            case (ISSUE):
                if (resultCode == RESULT_OK) {
                    wcg_al = data.getParcelableArrayListExtra("wcg_al");
                    String appr = data.getStringExtra("appr");
                    if (appr.equals("X"))
                        opp.setAppr("R");
                    else
                        opp.setAppr("G");

                    if (opp.getAppr().equals("G")) {
                        issueStatus1_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                        issueStatus2_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                        issueStatus3_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                    } else if (opp.getAppr().equals("Y")) {
                        issueStatus1_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                        issueStatus2_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                        issueStatus3_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                    } else if (opp.getAppr().equals("R")) {
                        issueStatus1_iv.setImageResource(R.drawable.ic_create_circle_icon);
                        issueStatus2_iv.setImageResource(R.drawable.ic_create_circle_icon);
                        issueStatus3_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    }
                    opp.setWcagnsPrcbl_Al(wcg_al);
                }
                break;

        }
    }

    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (permitExist)
                customProgressDialog.show_progress_dialog(Permits_Add_Update_Activity.this,
                        getResources().getString(R.string.permit_chan_progress));
            else
                customProgressDialog.show_progress_dialog(Permits_Add_Update_Activity.this,
                        getResources().getString(R.string.permit_crea_progress));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(Permits_Add_Update_Activity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.equals("success"))
                new PostPermit().execute("");
            else
                errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                        getResources().getString(R.string.unble_to_process));
        }
    }

    private class PostPermit extends AsyncTask<String, Integer, Void> {
        String[] Response = new String[2];
        ArrayList<WcdDup_Object> wcdDup_al = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (permitExist)
                customProgressDialog.show_progress_dialog(Permits_Add_Update_Activity.this,
                        getResources().getString(R.string.permit_chan_progress));
            else
                customProgressDialog.show_progress_dialog(Permits_Add_Update_Activity.this,
                        getResources().getString(R.string.permit_crea_progress));
        }

        @Override
        protected Void doInBackground(String... transmit) {
            String transmit_type = transmit[0];
            Response = new Permit_Create_Change()
                    .Change_Permit(Permits_Add_Update_Activity.this, ohp, transmit_type,
                            "WCMMP", orderId, "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response[0].startsWith("S")) {
                dismissActivity(Permits_Add_Update_Activity.this, Response[0].substring(2));
            } else if (Response[0].startsWith("E")) {
                errorDialog.show_error_dialog(Permits_Add_Update_Activity.this,
                        Response[0].substring(2));
            } else if (Response[0].startsWith("W")) {
                try {
                    JSONArray wcd_Json = new JSONArray(Response[1]);
                    if (wcd_Json.length() > 0) {
                        for (int i = 0; i < wcd_Json.length(); i++) {
                            WcdDup_Object wdo = new WcdDup_Object(wcd_Json.getJSONObject(i)
                                    .getString("Aufnr"),
                                    wcd_Json.getJSONObject(i).getString("Stxt"),
                                    wcd_Json.getJSONObject(i).getString("Sysst"),
                                    wcd_Json.getJSONObject(i).getString("Wcnr"));
                            wcdDup_al.add(wdo);
                        }
                        if (wcdDup_al.size() > 0) {
                            final Dialog aa = new Dialog(Permits_Add_Update_Activity.this);
                            aa.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                                RecyclerView.LayoutManager layoutManager =
                                        new LinearLayoutManager(Permits_Add_Update_Activity.this);
                                list_recycleview.setLayoutManager(layoutManager);
                                WcdDupAdapter adapter =
                                        new WcdDupAdapter(Permits_Add_Update_Activity.this,
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
            } else {
                errorDialog.show_error_dialog(Permits_Add_Update_Activity.this, Response[0]);
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

    private String GetFutureDate() {
        String new_date = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            new_date = new SimpleDateFormat("MMM dd, yyyy").format(cal.getTime());

        } catch (Exception e) {
        }
        return new_date;
    }

    private String GetPreviousDate() {
        String new_date = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            new_date = new SimpleDateFormat("MMM dd, yyyy").format(cal.getTime());

        } catch (Exception e) {
        }
        return new_date;
    }

    private String[] GetUsage(String objart, String appl_type) {
        Cursor cursor = null;
        String[] usage = new String[2];
        usage[0] = "";
        usage[1] = "";
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtWcmWcco where Iwerk = ? and" +
                    " Objart = ? and Objtyp = ?", new String[]{iwerk, objart, appl_type});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        usage[0] = cursor.getString(4);
                    }
                    while (cursor.moveToNext());
                }
            }
            if (!usage[0].equals("")) {
                cursor = FieldTekPro_db.rawQuery("select * from EtWcmUsages where Iwerk = ?" +
                        " and Objart = ? and Use = ?", new String[]{iwerk, objart, usage[0]});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            usage[1] = cursor.getString(4);
                        }
                        while (cursor.moveToNext());
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return usage;
    }

    private void confirmationDialog(String message, final String type) {
        final Dialog cancel_dialog =
                new Dialog(Permits_Add_Update_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview =
                cancel_dialog.findViewById(R.id.description_textview);
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
                int extperiod = opp.getExtperiod();
                extntn_tiet.setText(String.valueOf((extperiod) + 24));
                try {
                    String to_date = toDt_tiet.getText().toString();
                    String to_time = toTm_tiet.getText().toString();
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
                    String date_time = to_date + " " + to_time;
                    Date date = dateFormat2.parse(date_time);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR, 24);
                    String new_date = new SimpleDateFormat("MMM dd, yyyy").format(calendar.getTime());
                    String new_time = new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());
                    toDt_tiet.setText(new_date);
                    toTm_tiet.setText(new_time);
                } catch (ParseException e) {
                }
                opp.setExtperiod(Integer.parseInt(extntn_tiet.getText().toString()));
                cancel_dialog.dismiss();
            }
        });
    }

    private boolean isExtension(String extperiod, String appl_type) {
        int ext = 0;
        if (appl_type.equals("8")) {
            return false;
        } else if (appl_type.equals("9")) {
            ext = Integer.parseInt(extperiod);
            return (ext < 696);
        } else {
            ext = Integer.parseInt(extperiod);
            return (ext < 144);
        }
    }

    private boolean isAttached(String wapinr) {
        String act = "";
        boolean attach = false;

        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from Orders_Attachments where " +
                    "Object_id = ?", new String[]{wapinr});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        act = cursor1.getString(4);
                        if (act != null && !act.equalsIgnoreCase("")) {
                            attach = true;
                        } else {
                            attach = false;
                        }
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                try {
                    cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtDocs where" +
                            " Zobjid = ?", new String[]{wapinr});
                    if (cursor1 != null && cursor1.getCount() > 0) {
                        if (cursor1.moveToFirst()) {
                            do {
                                attach = true;
                            }
                            while (cursor1.moveToNext());
                        }
                    } else {
                        cursor1.close();
                        attach = false;
                    }
                } catch (Exception e) {
                    attach = false;
                } finally {
                    cursor1.close();
                }
            }
        } catch (Exception e) {
            attach = false;
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }

        if (!attach)
            if (opp.getStatusPrcbl_Al() != null) {
                for (NotifOrdrStatusPrcbl osp : opp.getStatusPrcbl_Al()) {
                    if (osp.getTxt04().equals("JSAA") && osp.getAct()
                            .equalsIgnoreCase("X"))
                        attach = true;
                }
            }
        return attach;
    }

    private void confirmationDialog(String message) {
        final Dialog cancel_dialog =
                new Dialog(Permits_Add_Update_Activity.this, R.style.PauseDialog);
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

    public String dismissActivity(final Activity activity, String message) {
        final Dialog success_dialog = new Dialog(activity);
        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
        TextView description_textview =
                success_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.success_checkmark).into(imageview);
        success_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success_dialog.dismiss();
                new Get_Permit_Data().execute();
            }
        });
        return null;
    }

    private class Get_Permit_Data extends AsyncTask<Void, Integer, Void> {
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(Permits_Add_Update_Activity.this,
                    getResources().getString(R.string.get_order_data));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ww_al = Get_Permit_Detail.GetData(Permits_Add_Update_Activity.this, OrdrUUID,
                    orderId, "", iwerk);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (ww_al != null) {

                Intent saveIntent = new Intent();
                saveIntent.putExtra("opp", ww_al);
                setResult(RESULT_OK, saveIntent);
                Permits_Add_Update_Activity.this.finish();

            }
        }
    }
}
