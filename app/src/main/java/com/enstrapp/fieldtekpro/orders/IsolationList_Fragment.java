package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog1;
import com.enstrapp.fieldtekpro.DateTime.TimePickerDialog;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class IsolationList_Fragment extends Fragment implements View.OnClickListener {

    LinearLayout wcd_ll;
    TextInputEditText shortTxt_tiet, funcLoc_tiet, frmDt_tiet, frmTm_tiet, toDt_tiet, toTm_tiet,
            extntn_tiet, priority_tiet, auth_tiet, usage_tiet;
    ImageView frmDt_iv, frmTm_iv, toDt_iv, toTm_iv, priority_iv, auth_iv, usage_iv;
    CheckBox setPrep_cb, completed_cb;
    TextView wcd_tv;
    Isolation_Add_Update_Activity ma;
    static final int AUTH = 287;
    static final int USAGE = 387;
    static final int PRIORITY = 6;
    static final int FRMDT = 7;
    static final int FRMTM = 8;
    static final int TODT = 9;
    static final int TOTM = 10;
    Error_Dialog errorDialog = new Error_Dialog();
    ArrayList<OrdrWDItemPrcbl> wdip_al = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.iso_list_layout, container,
                false);

        wcd_ll = rootView.findViewById(R.id.wcd_ll);
        usage_iv = rootView.findViewById(R.id.usage_iv);
        wcd_tv = rootView.findViewById(R.id.wcd_tv);
        shortTxt_tiet = rootView.findViewById(R.id.shortTxt_tiet);
        funcLoc_tiet = rootView.findViewById(R.id.funcLoc_tiet);
        frmDt_tiet = rootView.findViewById(R.id.frmDt_tiet);
        frmTm_tiet = rootView.findViewById(R.id.frmTm_tiet);
        toDt_tiet = rootView.findViewById(R.id.toDt_tiet);
        toTm_tiet = rootView.findViewById(R.id.toTm_tiet);
        extntn_tiet = rootView.findViewById(R.id.extntn_tiet);
        priority_tiet = rootView.findViewById(R.id.priority_tiet);
        auth_tiet = rootView.findViewById(R.id.auth_tiet);
        usage_tiet = rootView.findViewById(R.id.usage_tiet);
        frmDt_iv = rootView.findViewById(R.id.frmDt_iv);
        frmTm_iv = rootView.findViewById(R.id.frmTm_iv);
        toDt_iv = rootView.findViewById(R.id.toDt_iv);
        toTm_iv = rootView.findViewById(R.id.toTm_iv);
        priority_iv = rootView.findViewById(R.id.priority_iv);
        auth_iv = rootView.findViewById(R.id.auth_iv);
        setPrep_cb = rootView.findViewById(R.id.setPrep_cb);
        completed_cb = rootView.findViewById(R.id.completed_cb);

        ma = (Isolation_Add_Update_Activity) this.getActivity();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        final int time = Integer.parseInt(sdf.format(new Date()));

        if (ma.iso != null) {
            if (ma.iso.getWcnr() != null) {
                if (!ma.iso.getWcnr().startsWith("WD")) {
                    wcd_ll.setVisibility(VISIBLE);
                    wcd_tv.setText(ma.iso.getWcnr());
                    completed_cb.setVisibility(VISIBLE);
                } else {
                    wcd_ll.setVisibility(GONE);
                    completed_cb.setVisibility(GONE);
                }
            }
            if (ma.iso.getStxt() != null && !ma.iso.getStxt().equals("")) {
                shortTxt_tiet.setText(ma.iso.getStxt());
            } else {
                shortTxt_tiet.setText(ma.shrtTxt);
            }
            funcLoc_tiet.setText(ma.funcId);
            if (ma.iso.getDatefr() != null && !ma.iso.getDatefr().equals(""))
                frmDt_tiet.setText(ma.iso.getDatefr());
            else {
                if (time >= 8) {
                    frmDt_tiet.setText(df.format(c.getTime()));
                } else if (time < 8) {
                    frmDt_tiet.setText(GetPreviousDate());
                }
            }

            if (ma.iso.getTimefr() != null && !ma.iso.getTimefr().equals(""))
                frmTm_tiet.setText(ma.iso.getTimefr());
            else {
                if (time >= 8) {
                    frmTm_tiet.setText("08:00:00");
                } else if (time < 8) {
                    frmTm_tiet.setText("08:00:00");
                }
            }

            if (ma.iso.getDateto() != null && !ma.iso.getDateto().equals(""))
                toDt_tiet.setText(ma.iso.getDateto());
            else {
                if (time >= 8) {
                    toDt_tiet.setText(GetFutureDate());
                } else if (time < 8) {
                    toDt_tiet.setText(df.format(c.getTime()));
                }
            }
            if (ma.iso.getTimeto() != null && !ma.iso.getTimeto().equals(""))
                toTm_tiet.setText(ma.iso.getTimeto());
            else {
                if (time >= 8) {
                    toTm_tiet.setText("08:00:00");
                } else if (time < 8) {
                    toTm_tiet.setText("08:00:00");
                }
            }

            if (ma.iso.getPriokx() != null && !ma.iso.getPriokx().equals("")) {
                priority_tiet.setText(getString(R.string.hypen_text, ma.iso.getPriok(), ma.iso.getPriokx()));
            } else {
                ma.iso.setPriok(ma.priority_id);
                ma.iso.setPriokx(ma.priority_txt);
                priority_tiet.setText(getString(R.string.hypen_text, ma.iso.getPriok(), ma.iso.getPriokx()));
            }
            if (ma.iso.getBegru() != null && !ma.iso.getBegru().equals("")) {
                auth_tiet.setText(getString(R.string.hypen_text, ma.iso.getBegru(), ma.iso.getBegtx()));
            } else {
                auth_tiet.setText(getString(R.string.hypen_text, ma.authId, ma.authTxt));
                ma.iso.setBegru(ma.authId);
                ma.iso.setBegtx(ma.authTxt);
            }
            if (ma.iso.getUsagex() != null && !ma.iso.getUsagex().equals("")) {
                usage_tiet.setText(getString(R.string.hypen_text, ma.iso.getUsage(), ma.iso.getUsagex()));
            }


            if (ma.iso.getComp() != null) {
                if (ma.iso.getComp().equalsIgnoreCase("X")) {
                    completed_cb.setVisibility(VISIBLE);
                    completed_cb.setChecked(true);
                    completed_cb.setEnabled(false);
                    shortTxt_tiet.setEnabled(false);
                    setPrep_cb.setChecked(true);
                    setPrep_cb.setEnabled(false);
                } else {
                    completed_cb.setChecked(false);
                    if (ma.iso.getPrep().equals("X")) {
                        setPrep_cb.setChecked(true);
                        shortTxt_tiet.setEnabled(false);
                    } else {
                        setPrep_cb.setChecked(false);
                    }
                }
            } else if (ma.iso.getPrep() != null) {
                completed_cb.setChecked(false);
                if (ma.iso.getPrep().equals("X")) {
                    setPrep_cb.setChecked(true);
                    shortTxt_tiet.setEnabled(false);
                } else {
                    setPrep_cb.setChecked(false);
                }
            } else {
                completed_cb.setVisibility(GONE);
            }


        } else {
            shortTxt_tiet.setText(ma.shrtTxt);
            funcLoc_tiet.setText(ma.funcId);
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
            if (ma.authTxt != null && !ma.authTxt.equals("")) {
                auth_tiet.setText(getString(R.string.hypen_text, ma.authId, ma.authTxt));
            }
            completed_cb.setVisibility(GONE);
        }

        ma.iso.setStxt(shortTxt_tiet.getText().toString());
        ma.iso.setDatefr(frmDt_tiet.getText().toString());
        ma.iso.setDateto(toDt_tiet.getText().toString());
        ma.iso.setTimefr(frmTm_tiet.getText().toString());
        ma.iso.setTimeto(toTm_tiet.getText().toString());

        shortTxt_tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ma.iso.setStxt("");
                if (shortTxt_tiet.getText().toString() != null && !shortTxt_tiet.getText().toString().equals("")) {
                    ma.iso.setStxt(shortTxt_tiet.getText().toString());
                } else {
                    ma.iso.setStxt("");
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
        auth_iv.setOnClickListener(this);
        usage_iv.setOnClickListener(this);
        setPrep_cb.setOnClickListener(this);
        completed_cb.setOnClickListener(this);
        priority_iv.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint())
            return;
        ma.fab.hide();

        int untagged = 0;
        if (ma.iso.getWdItemPrcbl_Al() != null && ma.iso.getWdItemPrcbl_Al().size() > 0) {

            wdip_al = new ArrayList<>();
            wdip_al.addAll(ma.iso.getWdItemPrcbl_Al());
            if (wdip_al.size() > 0) {
                for (int i = 0; i < wdip_al.size(); i++) {
                    if (wdip_al.get(i).getEug().equals("X"))
                        untagged = untagged + 1;
                }
            }

        } else {
            if (ma.wdip_al != null) {
                if (ma.wdip_al.size() > 0) {
                    wdip_al = new ArrayList<>();
                    wdip_al.addAll(ma.wdip_al);
                    if (wdip_al.size() > 0) {
                        for (int i = 0; i < wdip_al.size(); i++) {
                            if (wdip_al.get(i).getEug().equals("X"))
                                untagged = untagged + 1;
                        }
                    }
                }
            }
        }

        if (ma.iso.getWdItemPrcbl_Al() != null && ma.iso.getWdItemPrcbl_Al().size() > 0) {
            wdip_al = new ArrayList<>();
            wdip_al.addAll(ma.iso.getWdItemPrcbl_Al());
            if (wdip_al.size() > 0) {
                if (untagged == wdip_al.size())
                    completed_cb.setVisibility(VISIBLE);
                else
                    completed_cb.setVisibility(GONE);
            }

        } else {
            if (ma.wdip_al != null) {
                if (ma.wdip_al.size() > 0) {
                    wdip_al = new ArrayList<>();
                    wdip_al.addAll(ma.wdip_al);
                    if (wdip_al.size() > 0) {
                        if (untagged == wdip_al.size())
                            completed_cb.setVisibility(VISIBLE);
                        else
                            completed_cb.setVisibility(GONE);
                    }
                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.auth_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent authIntent = new Intent(getActivity(), Authorization_Activity.class);
                    startActivityForResult(authIntent, AUTH);
                }
                break;

            case (R.id.usage_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent usageIntent = new Intent(getActivity(), Usage_Activity.class);
                    usageIntent.putExtra("iwerk", ma.iwerk);
                    usageIntent.putExtra("objart", "WD");
                    startActivityForResult(usageIntent, USAGE);
                }
                break;

            case (R.id.priority_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent priorityIntent = new Intent(getActivity(), Priority_Activity.class);
                    startActivityForResult(priorityIntent, PRIORITY);
                }
                break;

            case (R.id.frmDt_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent frmDtIntent = new Intent(getActivity(), DatePickerDialog1.class);
                    frmDtIntent.putExtra("givenDate", frmDt_tiet.getText().toString());
                    startActivityForResult(frmDtIntent, FRMDT);
                }
                break;

            case (R.id.toDt_iv):
                if (!completed_cb.isChecked()) {
                    Intent toDtIntent = new Intent(getActivity(), DatePickerDialog1.class);
                    toDtIntent.putExtra("givenDate", toDt_tiet.getText().toString());
                    toDtIntent.putExtra("startDate", frmDt_tiet.getText().toString());
                    startActivityForResult(toDtIntent, TODT);
                }
                break;

            case (R.id.frmTm_iv):
                if (!setPrep_cb.isChecked()) {
                    Intent frmTmIntent = new Intent(getActivity(), TimePickerDialog.class);
                    startActivityForResult(frmTmIntent, FRMTM);
                }
                break;

            case (R.id.toTm_iv):
                if (!completed_cb.isChecked()) {
                    Intent toTmIntent = new Intent(getActivity(), TimePickerDialog.class);
                    startActivityForResult(toTmIntent, TOTM);
                }
                break;

            case (R.id.setPrep_cb):
                if (setPrep_cb.isChecked()) {
                    shortTxt_tiet.setEnabled(false);
                    ma.setPrep = true;
                } else {
                    shortTxt_tiet.setEnabled(true);
                    ma.setPrep = false;
                    completed_cb.setChecked(false);
                    ma.iso.setComp("");
                }
                break;

            case (R.id.completed_cb):
                if (completed_cb.isChecked()) {
                    if (setPrep_cb.isChecked()) {
                        ma.iso.setComp("X");
                        setPrep_cb.setEnabled(false);
                    } else {
                        errorDialog.show_error_dialog(getActivity(),
                                getString(R.string.check_setprep));
                        ma.iso.setComp("");
                        completed_cb.setChecked(false);
                    }
                } else {
                    shortTxt_tiet.setEnabled(true);
                    ma.iso.setComp("");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (USAGE):
                if (resultCode == RESULT_OK) {
                    ma.iso.setUsage(data.getStringExtra("usage_id"));
                    ma.iso.setUsagex(data.getStringExtra("usage_txt"));
                    usage_tiet.setText(getString(R.string.hypen_text, data.getStringExtra("usage_id"),
                            data.getStringExtra("usage_txt")));
                }
                break;


            case (AUTH):
                if (resultCode == RESULT_OK) {
                    ma.iso.setBegru(data.getStringExtra("auth_id"));
                    ma.iso.setBegtx(data.getStringExtra("auth_txt"));
                    auth_tiet.setText(getString(R.string.hypen_text, data.getStringExtra("auth_id"),
                            data.getStringExtra("auth_txt")));
                }
                break;

            case (PRIORITY):
                if (resultCode == RESULT_OK) {
                    ma.iso.setPriok(data.getStringExtra("priority_type_id"));
                    ma.iso.setPriokx(data.getStringExtra("priority_type_text"));
                    priority_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("priority_type_id"),
                            data.getStringExtra("priority_type_text")));
                }
                break;

            case (FRMDT):
                if (resultCode == RESULT_OK) {
                    ma.iso.setDatefr(data.getStringExtra("selectedDate"));
                    frmDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TODT):
                if (resultCode == RESULT_OK) {
                    ma.iso.setDateto(data.getStringExtra("selectedDate"));
                    toDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (TOTM):
                if (resultCode == RESULT_OK) {
                    ma.iso.setTimeto(data.getStringExtra("selectedTime"));
                    toTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;

            case (FRMTM):
                if (resultCode == RESULT_OK) {
                    ma.iso.setTimefr(data.getStringExtra("selectedTime"));
                    frmTm_tiet.setText(data.getStringExtra("selectedTime"));
                }
                break;
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

    public void refreshData() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        final int time = Integer.parseInt(sdf.format(new Date()));

        if (ma.iso != null) {

            if (ma.iso.getWcnr() != null) {
                if (!ma.iso.getWcnr().startsWith("WD")) {
                    wcd_ll.setVisibility(VISIBLE);
                    wcd_tv.setText(ma.iso.getWcnr());
                } else {
                    wcd_ll.setVisibility(GONE);
                }
            }
            if (ma.iso.getStxt() != null && !ma.iso.getStxt().equals("")) {
                shortTxt_tiet.setText(ma.iso.getStxt());
            } else {
                shortTxt_tiet.setText(ma.shrtTxt);
            }
            funcLoc_tiet.setText(ma.funcId);
            if (ma.iso.getDatefr() != null && !ma.iso.getDatefr().equals(""))
                frmDt_tiet.setText(ma.iso.getDatefr());
            else {
                if (time >= 8) {
                    frmDt_tiet.setText(df.format(c.getTime()));
                } else if (time < 8) {
                    frmDt_tiet.setText(GetPreviousDate());
                }
            }

            if (ma.iso.getTimefr() != null && !ma.iso.getTimefr().equals(""))
                frmTm_tiet.setText(ma.iso.getTimefr());
            else {
                if (time >= 8) {
                    frmTm_tiet.setText("08:00:00");
                } else if (time < 8) {
                    frmTm_tiet.setText("08:00:00");
                }
            }

            if (ma.iso.getDateto() != null && !ma.iso.getDateto().equals(""))
                toDt_tiet.setText(ma.iso.getDateto());
            else {
                if (time >= 8) {
                    toDt_tiet.setText(GetFutureDate());
                } else if (time < 8) {
                    toDt_tiet.setText(df.format(c.getTime()));
                }
            }
            if (ma.iso.getTimeto() != null && !ma.iso.getTimeto().equals(""))
                toTm_tiet.setText(ma.iso.getTimeto());
            else {
                if (time >= 8) {
                    toTm_tiet.setText("08:00:00");
                } else if (time < 8) {
                    toTm_tiet.setText("08:00:00");
                }
            }

            if (ma.iso.getPriokx() != null && !ma.iso.getPriokx().equals("")) {
                priority_tiet.setText(getString(R.string.hypen_text, ma.iso.getPriok(), ma.iso.getPriokx()));
            } else {
                ma.iso.setPriok(ma.priority_id);
                ma.iso.setPriokx(ma.priority_txt);
                priority_tiet.setText(getString(R.string.hypen_text, ma.iso.getPriok(), ma.iso.getPriokx()));
            }
            if (ma.iso.getBegru() != null && !ma.iso.getBegru().equals("")) {
                auth_tiet.setText(getString(R.string.hypen_text, ma.iso.getBegru(), ma.iso.getBegtx()));
            } else {
                auth_tiet.setText(getString(R.string.hypen_text, ma.authId, ma.authTxt));
                ma.iso.setBegru(ma.authId);
                ma.iso.setBegtx(ma.authTxt);
            }
            if (ma.iso.getUsagex() != null && !ma.iso.getUsagex().equals("")) {
                usage_tiet.setText(getString(R.string.hypen_text, ma.iso.getUsage(), ma.iso.getUsagex()));
            }
            if (ma.iso.getPrep() != null)
                if (ma.iso.getPrep().equals("X")) {
                    setPrep_cb.setChecked(true);
                } else {
                    setPrep_cb.setChecked(false);
                }
        } else {
            shortTxt_tiet.setText(ma.shrtTxt);
            funcLoc_tiet.setText(ma.funcId);
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
            if (ma.authTxt != null && !ma.authTxt.equals("")) {
                auth_tiet.setText(getString(R.string.hypen_text, ma.authId, ma.authTxt));
            }
        }

        ma.iso.setStxt(shortTxt_tiet.getText().toString());
        ma.iso.setDatefr(frmDt_tiet.getText().toString());
        ma.iso.setDateto(toDt_tiet.getText().toString());
        ma.iso.setTimefr(frmTm_tiet.getText().toString());
        ma.iso.setTimeto(toTm_tiet.getText().toString());
    }
}
