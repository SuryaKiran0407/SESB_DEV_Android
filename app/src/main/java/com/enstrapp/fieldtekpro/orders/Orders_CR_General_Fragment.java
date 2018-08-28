package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.BarcodeScanner.Barcode_Scanner_Activity;
import com.enstrapp.fieldtekpro.CostCenter.CostCenter_Activity;
import com.enstrapp.fieldtekpro.CustomInfo.CustomInfo_Activity;
import com.enstrapp.fieldtekpro.DateTime.DatePickerDialog1;
import com.enstrapp.fieldtekpro.PersonResponsible.Personresponsible_Activity;
import com.enstrapp.fieldtekpro.PlannerGroup.PlannerGroup_Activity;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.WorkCenter.WorkCenter_Type_Activity;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CR_General_Fragment extends Fragment implements View.OnClickListener {

    TextInputEditText ordrTyp_tiet, ordrLngTxt_tiet, funcLocId_tiet, funcLocName_tiet, equipId_tiet,
            equipName_tiet, wrkCntr_tiet, respCostCntr_tiet, priority_tiet, plannerGroup_tiet,
            personResp_tiet, basicStDt_tiet, basicEnDt_tiet, sysCond_tiet, revision_tiet, wbs_tiet;
    ImageView ordrTyp_iv, funcLoc_iv, equipId_iv, equipIdScan_iv, wrkCntr_iv, respCstCntr_iv,
            priority_iv, plannerGroup_iv, personResp_iv, basicStDt_iv, basicEdDt_iv, sysCond_iv,
            wbs_iv, revision_iv;
    TextView notifNum_tv;
    LinearLayout equipId_ll, wbs_ll, revision_ll;
    TextInputLayout equipName_til;
    Error_Dialog error_dialog = new Error_Dialog();
    Orders_Create_Activity ma;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    static final int ORDR_TYP = 1;
    static final int FUNC_LOC = 2;
    static final int EQUIP_ID = 3;
    static final int EQUIP_SCAN = 217;
    static final int WRKCNTR_ID = 4;
    static final int RESP_COST_CNTR = 5;
    static final int PRIORITY = 6;
    static final int PLNR_GRP = 7;
    static final int PER_RESP = 8;
    static final int SYS_COND = 9;
    static final int WBS_ELE = 522;
    static final int REVISO = 534;
    private static final int STDT_SELECTED = 200;
    private static final int EDDT_SELECTED = 300;
    private static final int custom_info = 14;
    Button header_custominfo_button;
    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.order_general_fragment, container,
                false);

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        ma = (Orders_Create_Activity) this.getActivity();

        wbs_tiet = rootView.findViewById(R.id.wbs_tiet);
        revision_tiet = rootView.findViewById(R.id.revision_tiet);
        revision_ll = rootView.findViewById(R.id.revision_ll);
        wbs_iv = rootView.findViewById(R.id.wbs_iv);
        revision_iv = rootView.findViewById(R.id.revision_iv);
        wbs_ll = rootView.findViewById(R.id.wbs_ll);
        equipName_til = rootView.findViewById(R.id.equipName_til);
        equipId_ll = rootView.findViewById(R.id.equipId_ll);
        ordrTyp_iv = rootView.findViewById(R.id.ordrTyp_iv);
        funcLoc_iv = rootView.findViewById(R.id.funcLoc_iv);
        equipId_iv = rootView.findViewById(R.id.equipId_iv);
        equipIdScan_iv = rootView.findViewById(R.id.equipIdScan_iv);
        wrkCntr_iv = rootView.findViewById(R.id.wrkCntr_iv);
        respCstCntr_iv = rootView.findViewById(R.id.respCstCntr_iv);
        priority_iv = rootView.findViewById(R.id.priority_iv);
        plannerGroup_iv = rootView.findViewById(R.id.plannerGroup_iv);
        personResp_iv = rootView.findViewById(R.id.personResp_iv);
        basicStDt_iv = rootView.findViewById(R.id.basicStDt_iv);
        basicEdDt_iv = rootView.findViewById(R.id.basicEdDt_iv);
        sysCond_iv = rootView.findViewById(R.id.sysCond_iv);
        ordrTyp_tiet = rootView.findViewById(R.id.ordrTyp_tiet);
        ordrLngTxt_tiet = rootView.findViewById(R.id.ordrLngTxt_tiet);
        funcLocId_tiet = rootView.findViewById(R.id.funcLocId_tiet);
        funcLocName_tiet = rootView.findViewById(R.id.funcLocName_tiet);
        equipId_tiet = rootView.findViewById(R.id.equipId_tiet);
        equipName_tiet = rootView.findViewById(R.id.equipName_tiet);
        wrkCntr_tiet = rootView.findViewById(R.id.wrkCntr_tiet);
        respCostCntr_tiet = rootView.findViewById(R.id.respCostCntr_tiet);
        priority_tiet = rootView.findViewById(R.id.priority_tiet);
        plannerGroup_tiet = rootView.findViewById(R.id.plannerGroup_tiet);
        personResp_tiet = rootView.findViewById(R.id.personResp_tiet);
        basicStDt_tiet = rootView.findViewById(R.id.basicStDt_tiet);
        basicEnDt_tiet = rootView.findViewById(R.id.basicEnDt_tiet);
        sysCond_tiet = rootView.findViewById(R.id.sysCond_tiet);
        notifNum_tv = rootView.findViewById(R.id.notifNum_tv);
        header_custominfo_button = (Button)rootView.findViewById(R.id.header_custominfo_button);

        /*Written by SuryaKiran for Order Create from Notification Change*/
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            ordrTyp_tiet.setText(bundle.getString("Order_type_id"));
            ordrLngTxt_tiet.setText(bundle.getString("short_text"));
            funcLocId_tiet.setText(bundle.getString("functionlocation_id"));
            equipId_tiet.setText(bundle.getString("equipment_id"));
            wrkCntr_tiet.setText(bundle.getString("workcenter"));
            String pri = bundle.getString("priority_id");
            if (pri != null && !pri.equals(""))
            {
                priority_tiet.setText(bundle.getString("priority_id") + " - " + bundle.getString("priority_name"));
            }
            String plann = bundle.getString("plannergrp_id");
            if (plann != null && !plann.equals(""))
            {
                plannerGroup_tiet.setText(bundle.getString("plannergrp_id") + " - " + bundle.getString("plannergrp_text"));
            }
            String persp = bundle.getString("prsnresp_id");
            if (persp != null && !persp.equals(""))
            {
                personResp_tiet.setText(bundle.getString("prsnresp_id") + " - " + bundle.getString("prsnresp_text"));
            }
            String floc_id = bundle.getString("functionlocation_id");
            String floc_name = "", costcenter_id = "";
            if (floc_id != null && !floc_id.equals("")) {
                try {
                    Cursor cursor = App_db.rawQuery("select * from EtFuncEquip where Tplnr = ?", new String[]{floc_id});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                floc_name = cursor.getString(2);
                                costcenter_id = cursor.getString(5);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                        cursor.close();
                    }
                } catch (Exception e) {
                }
            }
            String equip_id = bundle.getString("functionlocation_id");
            String equip_name = "";
            if (equip_id != null && !equip_id.equals("")) {
                try {
                    Cursor cursor = App_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip_id});
                    if (cursor != null && cursor.getCount() > 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                equip_name = cursor.getString(5);
                                costcenter_id = cursor.getString(12);
                            }
                            while (cursor.moveToNext());
                        }
                    } else {
                        cursor.close();
                    }
                } catch (Exception e) {
                }
            }
            funcLocName_tiet.setText(floc_name);
            equipName_tiet.setText(equip_name);
            respCostCntr_tiet.setText(costcenter_id);

        }
         /*Written by SuryaKiran for Order Create from Notification Change*/


        ordrTyp_iv.setOnClickListener(this);
        funcLoc_iv.setOnClickListener(this);
        equipId_iv.setOnClickListener(this);
        equipIdScan_iv.setOnClickListener(this);
        wrkCntr_iv.setOnClickListener(this);
        respCstCntr_iv.setOnClickListener(this);
        priority_iv.setOnClickListener(this);
        plannerGroup_iv.setOnClickListener(this);
        personResp_iv.setOnClickListener(this);
        basicStDt_iv.setOnClickListener(this);
        basicEdDt_iv.setOnClickListener(this);
        sysCond_iv.setOnClickListener(this);
        wbs_iv.setOnClickListener(this);
        revision_iv.setOnClickListener(this);
        header_custominfo_button.setOnClickListener(this);

        selected_custom_info_arraylist.clear();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        basicStDt_tiet.setText(formattedDate);
        basicEnDt_tiet.setText(formattedDate);
        ma.ohp.setBasicStart(formattedDate);
        ma.ohp.setBasicEnd(formattedDate);

        ordrLngTxt_tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ma.ohp.setOrdrShrtTxt("");
                if (ordrLngTxt_tiet.getText().toString() != null && !ordrLngTxt_tiet.getText().toString().equals("")) {
                    if (ordrLngTxt_tiet.getText().toString().contains("\n")) {
                        String[] streets;
                        streets = ordrLngTxt_tiet.getText().toString().split("/n");
                        ma.ohp.setOrdrShrtTxt(streets[0]);
                    } else {
                        ma.ohp.setOrdrShrtTxt(ordrLngTxt_tiet.getText().toString());
                    }
                    ma.ohp.setOrdrLngTxt(ordrLngTxt_tiet.getText().toString());
                } else {
                    ma.ohp.setOrdrShrtTxt("");
                    ma.ohp.setOrdrLngTxt("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case (R.id.header_custominfo_button):
                Intent custominfo_intent = new Intent(getActivity(), CustomInfo_Activity.class);
                custominfo_intent.putExtra("Zdoctype","W");
                custominfo_intent.putExtra("ZdoctypeItem","WH");
                custominfo_intent.putExtra("custom_info_arraylist",selected_custom_info_arraylist);
                custominfo_intent.putExtra("request_id", Integer.toString(custom_info));
                startActivityForResult(custominfo_intent,custom_info);
                break;

            case (R.id.ordrTyp_iv):
                Intent ordrTypIntent = new Intent(getActivity(), OrderType_Activity.class);
                startActivityForResult(ordrTypIntent, ORDR_TYP);
                break;

            case (R.id.funcLoc_iv):
                Intent funcLocIntent = new Intent(getActivity(), FunctionLocation_Activity.class);
                startActivityForResult(funcLocIntent, FUNC_LOC);
                break;

            case (R.id.equipId_iv):
                Intent equipIdIntent = new Intent(getActivity(), Equipment_Activity.class);
                equipIdIntent.putExtra("functionlocation_id", funcLocId_tiet.getText().toString());
                startActivityForResult(equipIdIntent, EQUIP_ID);
                break;

            case (R.id.equipIdScan_iv):
                Intent intent = new Intent(getActivity(), Barcode_Scanner_Activity.class);
                startActivityForResult(intent, EQUIP_SCAN);
                break;

            case (R.id.wrkCntr_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent wrkCntrIntent = new Intent(getActivity(), WorkCenter_Type_Activity.class);
                    wrkCntrIntent.putExtra("plant_id", ma.ohp.getPlant());
                    startActivityForResult(wrkCntrIntent, WRKCNTR_ID);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.respCstCntr_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent respCstCntrIntent = new Intent(getActivity(), CostCenter_Activity.class);
                    respCstCntrIntent.putExtra("werk", ma.ohp.getPlant());
                    respCstCntrIntent.putExtra("bukrs", ma.ohp.getBukrs());
                    startActivityForResult(respCstCntrIntent, RESP_COST_CNTR);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.priority_iv):
                Intent priorityIntent = new Intent(getActivity(), Priority_Activity.class);
                startActivityForResult(priorityIntent, PRIORITY);
                break;

            case (R.id.plannerGroup_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent plnrGrpIntent = new Intent(getActivity(), PlannerGroup_Activity.class);
                    plnrGrpIntent.putExtra("iwerk", ma.ohp.getIwerk());
                    startActivityForResult(plnrGrpIntent, PLNR_GRP);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.personResp_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent perRespIntent = new Intent(getActivity(), Personresponsible_Activity.class);
                    perRespIntent.putExtra("werks", ma.ohp.getPlant());
                    startActivityForResult(perRespIntent, PER_RESP);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.basicStDt_iv):
                Intent stDtIntent = new Intent(getActivity(), DatePickerDialog1.class);
                stDtIntent.putExtra("givenDate", basicStDt_tiet.getText().toString());
                startActivityForResult(stDtIntent, STDT_SELECTED);
                break;

            case (R.id.basicEdDt_iv):
                Intent enDtIntent = new Intent(getActivity(), DatePickerDialog1.class);
                enDtIntent.putExtra("givenDate", basicEnDt_tiet.getText().toString());
                enDtIntent.putExtra("startDate", basicStDt_tiet.getText().toString());
                startActivityForResult(enDtIntent, EDDT_SELECTED);
                break;

            case (R.id.sysCond_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent sysCondIntent = new Intent(getActivity(), SystemCondidtions_Activity.class);
                    startActivityForResult(sysCondIntent, SYS_COND);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.wbs_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent WBSIntent = new Intent(getActivity(), WBS_Element_Activity.class);
                    WBSIntent.putExtra("iwerk", ma.ohp.getIwerk());
                    startActivityForResult(WBSIntent, WBS_ELE);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

            case (R.id.revision_iv):
                if (!equipName_tiet.getText().toString().equals("") ||
                        (!funcLocId_tiet.getText().toString().equals(""))) {
                    Intent WBSIntent = new Intent(getActivity(), Revision_Activity.class);
                    WBSIntent.putExtra("iwerk", ma.ohp.getIwerk());
                    startActivityForResult(WBSIntent, REVISO);
                } else {
                    error_dialog.show_error_dialog(getActivity(), "Please select Equipment / \nFunction Location");
                }
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (ORDR_TYP):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setOrdrTypId(data.getStringExtra("ordrTypId"));
                    ma.ohp.setOrdrTypName(data.getStringExtra("ordrTypTxt"));
                    ordrTyp_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("ordrTypId"),
                            data.getStringExtra("ordrTypTxt")));
                    if (data.getStringExtra("ordrTypId").equals("PM08")) {
                        equipId_ll.setVisibility(View.GONE);
                        equipName_til.setVisibility(View.GONE);
                        wbs_ll.setVisibility(View.GONE);
                        revision_ll.setVisibility(View.GONE);
                    } else if (data.getStringExtra("ordrTypId").equals("PM06")) {
                        equipId_ll.setVisibility(View.VISIBLE);
                        equipName_til.setVisibility(View.VISIBLE);
                        wbs_ll.setVisibility(View.VISIBLE);
                        revision_ll.setVisibility(View.VISIBLE);
                    } else {
                        equipId_ll.setVisibility(View.VISIBLE);
                        equipName_til.setVisibility(View.VISIBLE);
                        wbs_ll.setVisibility(View.GONE);
                        revision_ll.setVisibility(View.GONE);
                    }
                }
                break;

            case (FUNC_LOC):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setFuncLocId(data.getStringExtra("functionlocation_id"));
                    ma.ohp.setFuncLocName(data.getStringExtra("functionlocation_text"));
                    ma.ohp.setRespCostCntrId(data.getStringExtra("kostl_id"));
                    ma.ohp.setRespCostCntrName(respCostCntrName(data.getStringExtra("kostl_id")));
                    ma.ohp.setPlant(data.getStringExtra("plant_id"));
                    ma.ohp.setPlantName(plantName(data.getStringExtra("plant_id")));
                    ma.ohp.setIwerk(data.getStringExtra("iwerk"));
                    funcLocId_tiet.setText(data.getStringExtra("functionlocation_id"));
                    funcLocName_tiet.setText(data.getStringExtra("functionlocation_text"));
                    respCostCntr_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("kostl_id"), respCostCntrName(data.getStringExtra("kostl_id"))));
                }
                break;

            case (EQUIP_ID):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setFuncLocId(data.getStringExtra("functionlocation_id"));
                    ma.ohp.setFuncLocName(funcLocName(data.getStringExtra("functionlocation_id")));
                    ma.ohp.setWrkCntrId(data.getStringExtra("wrkCntr_id"));
                    ma.ohp.setWrkCntrName(wrkCntrName(data.getStringExtra("wrkCntr_id")));
                    ma.ohp.setEquipNum(data.getStringExtra("equipment_id"));
                    ma.ohp.setEquipName(data.getStringExtra("equipment_text"));
                    ma.ohp.setPlant(data.getStringExtra("plant_id"));
                    ma.ohp.setPlantName(plantName(data.getStringExtra("plant_id")));
                    ma.ohp.setPlnrGrpId(data.getStringExtra("ingrp_id"));
                    ma.ohp.setPlnrGrpName(plnrGrpName(data.getStringExtra("ingrp_id")));
                    ma.ohp.setRespCostCntrId(data.getStringExtra("kostl_id"));
                    ma.ohp.setRespCostCntrName(respCostCntrName(data.getStringExtra("kostl_id")));
                    ma.ohp.setIwerk(data.getStringExtra("iwerk"));
                    ma.ohp.setBukrs(data.getStringExtra("bukrs"));
                    funcLocId_tiet.setText(data.getStringExtra("functionlocation_id"));
                    funcLocName_tiet.setText(funcLocName(data.getStringExtra("functionlocation_id")));
                    equipId_tiet.setText(data.getStringExtra("equipment_id"));
                    equipName_tiet.setText(data.getStringExtra("equipment_text"));
                    plannerGroup_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("ingrp_id"), plnrGrpName(data.getStringExtra("ingrp_id"))));
                    wrkCntr_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("wrkCntr_id"), wrkCntrName(data.getStringExtra("wrkCntr_id"))));
                    respCostCntr_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("kostl_id"), respCostCntrName(data.getStringExtra("kostl_id"))));
                }
                break;

            case (EQUIP_SCAN):
                if (resultCode == RESULT_OK) {
                    Intent equipScan = new Intent(getActivity(), Equipment_Activity.class);
                    equipScan.putExtra("equip", data.getStringExtra("MESSAGE"));
                    startActivityForResult(equipScan, EQUIP_ID);
                }
                break;

            case (WRKCNTR_ID):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setWrkCntrId(data.getStringExtra("workcenter_id"));
                    ma.ohp.setWrkCntrName(data.getStringExtra("workcenter_text"));
                    wrkCntr_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("workcenter_id"),
                            data.getStringExtra("workcenter_text")));
                }
                break;

            case (RESP_COST_CNTR):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setRespCostCntrId(data.getStringExtra("kostl_id"));
                    ma.ohp.setRespCostCntrName(data.getStringExtra("kostl_text"));
                    respCostCntr_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("kostl_id"),
                            data.getStringExtra("kostl_text")));
                }
                break;

            case (PRIORITY):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setPriorityId(data.getStringExtra("priority_type_id"));
                    ma.ohp.setPriorityTxt(data.getStringExtra("priority_type_text"));
                    priority_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("priority_type_id"),
                            data.getStringExtra("priority_type_text")));
                }
                break;

            case (PLNR_GRP):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setPlnrGrpId(data.getStringExtra("plannergroup_id"));
                    ma.ohp.setPlnrGrpName(data.getStringExtra("plannergroup_text"));
                    plannerGroup_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("plannergroup_id"),
                            data.getStringExtra("plannergroup_text")));
                }
                break;

            case (PER_RESP):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setPerRespId(data.getStringExtra("personresponsible_id"));
                    ma.ohp.setPerRespName(data.getStringExtra("personresponsible_text"));
                    personResp_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("personresponsible_id"),
                            data.getStringExtra("personresponsible_text")));
                }
                break;

            case (SYS_COND):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setSysCondId(data.getStringExtra("sysCondID"));
                    ma.ohp.setSysCondName(data.getStringExtra("sysCondTxt"));
                    sysCond_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("sysCondID"),
                            data.getStringExtra("sysCondTxt")));
                }
                break;

            case (STDT_SELECTED):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setBasicStart(data.getStringExtra("selectedDate"));
                    basicStDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (EDDT_SELECTED):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setBasicEnd(data.getStringExtra("selectedDate"));
                    basicEnDt_tiet.setText(data.getStringExtra("selectedDate"));
                }
                break;

            case (WBS_ELE):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setPosid(data.getStringExtra("wbs_id"));
                    wbs_tiet.setText(data.getStringExtra("wbs_id"));
                }
                break;

            case (REVISO):
                if (resultCode == RESULT_OK) {
                    ma.ohp.setRevnr(data.getStringExtra("rev_id"));
                    revision_tiet.setText(getString(R.string.hypen_text, data.getStringExtra("rev_id"),
                            data.getStringExtra("rev_txt")));
                }
                break;

            case (custom_info):
                if (resultCode == RESULT_OK)
                {
                    selected_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("selected_custom_info_arraylist");
                }
                break;
        }
    }


    private String funcLocName(String funcLocId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from EtFuncEquip where Tplnr = ?", new String[]{funcLocId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(2);
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

    private String wrkCntrName(String wrkCntrId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_WKCTR where Arbpl = ?", new String[]{wrkCntrId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(8);
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

    private String plnrGrpName(String plnrGrpId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_EtIngrp where Ingrp = ?", new String[]{plnrGrpId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(3);
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

    private String plantName(String plantId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_PLANTS where Werks = ?", new String[]{plantId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(2);
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

    private String respCostCntrName(String respCostCntrId) {
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from GET_LIST_COST_CENTER where Kostl = ?", new String[]{respCostCntrId});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(3);
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

    /*public OrdrHeaderPrcbl getHeaderData() {
        return ohp_h;
    }*/

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


    public ArrayList<HashMap<String, String>> getHeaderCustominfoData()
    {
        return selected_custom_info_arraylist;
    }

}
