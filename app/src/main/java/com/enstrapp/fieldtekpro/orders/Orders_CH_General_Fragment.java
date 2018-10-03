package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
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
import com.enstrapp.fieldtekpro.notifications.NotifCausCodActvPrcbl;
import com.enstrapp.fieldtekpro.notifications.NotifHeaderPrcbl;
import com.enstrapp.fieldtekpro.notifications.NotifTaskPrcbl;
import com.enstrapp.fieldtekpro.notifications.Notif_EtDocs_Parcelable;
import com.enstrapp.fieldtekpro.notifications.Notif_Status_WithNum_Prcbl;
import com.enstrapp.fieldtekpro.notifications.Notifications_Change_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CH_General_Fragment extends Fragment implements View.OnClickListener {

    TextInputEditText ordrTyp_tiet, ordrLngTxt_tiet, funcLocId_tiet, funcLocName_tiet, equipId_tiet,
            equipName_tiet, wrkCntr_tiet, respCostCntr_tiet, priority_tiet, plannerGroup_tiet,
            personResp_tiet, basicStDt_tiet, basicEnDt_tiet, sysCond_tiet, status_tiet,
            revision_tiet, wbs_tiet;
    ImageView ordrTyp_iv, funcLoc_iv, equipId_iv, equipIdScan_iv, wrkCntr_iv, respCstCntr_iv,
            priority_iv, plannerGroup_iv, personResp_iv, basicStDt_iv, basicEdDt_iv, sysCond_iv,
            wbs_iv, revision_iv;
    TextView notifNum_tv;
    LinearLayout equipId_ll, notifNum_ll, ordrTyp_ll, wbs_ll, revision_ll;
    TextInputLayout equipName_til, status_til;

    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    String order = "";
    OrdrHeaderPrcbl ohp_h = null;
    Error_Dialog error_dialog = new Error_Dialog();
    Orders_Change_Activity ma;

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
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    private static final int custom_info = 14;
    Button header_custominfo_button;
    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.order_general_fragment, container,
                false);

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        wbs_tiet = rootView.findViewById(R.id.wbs_tiet);
        revision_tiet = rootView.findViewById(R.id.revision_tiet);
        revision_ll = rootView.findViewById(R.id.revision_ll);
        wbs_iv = rootView.findViewById(R.id.wbs_iv);
        revision_iv = rootView.findViewById(R.id.revision_iv);
        wbs_ll = rootView.findViewById(R.id.wbs_ll);
        status_til = rootView.findViewById(R.id.status_til);
        ordrTyp_ll = rootView.findViewById(R.id.ordrTyp_ll);
        notifNum_ll = rootView.findViewById(R.id.notifNum_ll);
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
        status_tiet = rootView.findViewById(R.id.status_tiet);
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
        equipId_ll = rootView.findViewById(R.id.equipId_ll);
        equipName_til = rootView.findViewById(R.id.equipName_til);
        header_custominfo_button = (Button)rootView.findViewById(R.id.header_custominfo_button);

        ma = (Orders_Change_Activity) this.getActivity();
        ordrTyp_tiet.setEnabled(false);
        ordrTyp_tiet.setTextColor(getResources().getColor(R.color.dark_grey2));
        ordrTyp_ll.setBackground(getResources().getDrawable(R.drawable.bluedashborder, null));

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

        if (ma.ohp != null) {
            ordrTyp_tiet.setText(getResources().getString(R.string.hypen_text, ma.ohp.getOrdrTypId(), ma.ohp.getOrdrTypName()));
            ordrLngTxt_tiet.setText(ma.ohp.getOrdrLngTxt());
            if (ma.ohp.getOrdrTypId().equals("PM08")) {
                equipId_ll.setVisibility(View.GONE);
                equipName_til.setVisibility(View.GONE);
                wbs_ll.setVisibility(View.GONE);
                revision_ll.setVisibility(View.GONE);
            } else if (ma.ohp.getOrdrTypId().equals("PM06")) {
                equipId_ll.setVisibility(View.VISIBLE);
                equipName_til.setVisibility(View.VISIBLE);
                wbs_ll.setVisibility(View.VISIBLE);
                revision_ll.setVisibility(View.VISIBLE);
                equipId_tiet.setText(ma.ohp.getEquipNum());
                equipName_tiet.setText(ma.ohp.getEquipName());
                wbs_tiet.setText(ma.ohp.getPosid());
                revision_tiet.setText(ma.ohp.getRevnr());
            } else {
                equipId_ll.setVisibility(View.VISIBLE);
                equipName_til.setVisibility(View.VISIBLE);
                equipId_tiet.setText(ma.ohp.getEquipNum());
                equipName_tiet.setText(ma.ohp.getEquipName());
                wbs_ll.setVisibility(View.GONE);
                revision_ll.setVisibility(View.GONE);
            }
            if (ma.ohp.getNotifId() != null && !ma.ohp.getNotifId().equals("")) {
                SpannableString content = new SpannableString(ma.ohp.getNotifId());
                content.setSpan(new UnderlineSpan(), 0, ma.ohp.getNotifId().length(), 0);
                notifNum_tv.setText(content);
                notifNum_ll.setVisibility(View.VISIBLE);

                notifNum_tv.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String uuid = "";
                        try
                        {
                            Cursor cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{ma.ohp.getNotifId()});
                            if (cursor != null && cursor.getCount() > 0)
                            {
                                if (cursor.moveToFirst())
                                {
                                    do
                                    {
                                        uuid = cursor.getString(1);
                                    }
                                    while (cursor.moveToNext());
                                }
                            }
                            else
                            {
                                cursor.close();
                            }
                        }
                        catch (Exception e)
                        {
                        }
                        if (uuid != null && !uuid.equals(""))
                        {
                            new Get_Notification_Data().execute(ma.ohp.getNotifId(),uuid);
                        }
                        else
                        {
                            error_dialog.show_error_dialog(getActivity(),"No data found for Notification "+ma.ohp.getNotifId());
                        }
                    }
                });
            }
            status_til.setVisibility(View.VISIBLE);
            if (ma.ohp.getStatusALL() != null && !ma.ohp.getStatusALL().equals(""))
                status_tiet.setText(ma.ohp.getStatusALL());
            funcLocId_tiet.setText(ma.ohp.getFuncLocId());
            funcLocName_tiet.setText(ma.ohp.getFuncLocName());
            if (ma.ohp.getWrkCntrId() != null && !ma.ohp.getWrkCntrId().equals(""))
                wrkCntr_tiet.setText(getResources().getString(R.string.hypen_text, ma.ohp.getWrkCntrId(), ma.ohp.getWrkCntrName()));
            if (ma.ohp.getRespCostCntrId() != null && !ma.ohp.getRespCostCntrId().equals(""))
                respCostCntr_tiet.setText(getResources().getString(R.string.hypen_text, ma.ohp.getRespCostCntrId(), respCostCntrName(ma.ohp.getRespCostCntrName())));
            priority_tiet.setText(ma.ohp.getPriorityTxt());
            if (ma.ohp.getPlnrGrpId() != null && !ma.ohp.getPlnrGrpId().equals(""))
                plannerGroup_tiet.setText(getResources().getString(R.string.hypen_text, ma.ohp.getPlnrGrpId(), ma.ohp.getPlnrGrpName()));
            if (ma.ohp.getPerRespId() != null && !ma.ohp.getPerRespId().equals(""))
                personResp_tiet.setText(getResources().getString(R.string.hypen_text, ma.ohp.getPerRespId(), ma.ohp.getPerRespName()));
            basicStDt_tiet.setText(dateDisplayFormat(ma.ohp.getBasicStart()));
            basicEnDt_tiet.setText(dateDisplayFormat(ma.ohp.getBasicEnd()));
            sysCond_tiet.setText(ma.ohp.getSysCondName());
        }

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


        try
        {
            Cursor cursor = App_db.rawQuery("select * from EtOrderHeader_CustomInfo where Zdoctype = ? and ZdoctypeItem = ? and Aufnr = ? order by Sequence", new String[]{"W","WH", ma.ohp.getOrdrId()});
            if (cursor != null && cursor.getCount() > 0)
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                        custom_info_hashMap.put("Fieldname", cursor.getString(6));
                        custom_info_hashMap.put("ZdoctypeItem", cursor.getString(4));
                        custom_info_hashMap.put("Datatype", cursor.getString(11));
                        custom_info_hashMap.put("Tabname", cursor.getString(5));
                        custom_info_hashMap.put("Zdoctype", cursor.getString(3));
                        custom_info_hashMap.put("Sequence", cursor.getString(9));
                        custom_info_hashMap.put("Flabel", cursor.getString(8));
                        custom_info_hashMap.put("Length", cursor.getString(10));

                        String datatype = cursor.getString(11);
                        String value = cursor.getString(7);
                        if(datatype.equalsIgnoreCase("DATS"))
                        {
                            if(value.equalsIgnoreCase("00000000"))
                            {
                                custom_info_hashMap.put("Value", "");
                            }
                            else
                            {
                                String inputPattern = "yyyyMMdd";
                                String outputPattern = "MMM dd, yyyy" ;
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    custom_info_hashMap.put("Value", formatted_date);
                                }
                                catch (Exception e)
                                {
                                    custom_info_hashMap.put("Value", "");
                                }
                            }

                        }
                        else if(datatype.equalsIgnoreCase("TIMS"))
                        {
                            if(value.equalsIgnoreCase("000000"))
                            {
                                custom_info_hashMap.put("Value", "");
                            }
                            else
                            {
                                String inputPattern = "HHmmss";
                                String outputPattern = "HH:mm:ss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_time =  outputFormat.format(date);
                                    custom_info_hashMap.put("Value", formatted_time);
                                }
                                catch (Exception e)
                                {
                                    custom_info_hashMap.put("Value", "");
                                }
                            }
                        }
                        else
                        {
                            custom_info_hashMap.put("Value", cursor.getString(7));
                        }

                        custom_info_hashMap.put("Spras", "");
                        selected_custom_info_arraylist.add(custom_info_hashMap);
                    }
                    while (cursor.moveToNext());
                }
            }
            else
            {
                cursor.close();

                Cursor cursor1 = App_db.rawQuery("select * from GET_CUSTOM_FIELDS where Zdoctype = ? and ZdoctypeItem = ? order by Sequence", new String[]{"W","WH"});
                if (cursor1 != null && cursor1.getCount() > 0)
                {
                    if (cursor1.moveToFirst())
                    {
                        do
                        {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Fieldname", cursor1.getString(1));
                            custom_info_hashMap.put("ZdoctypeItem", cursor1.getString(2));
                            custom_info_hashMap.put("Datatype", cursor1.getString(3));
                            custom_info_hashMap.put("Tabname", cursor1.getString(4));
                            custom_info_hashMap.put("Zdoctype", cursor1.getString(5));
                            custom_info_hashMap.put("Sequence", cursor1.getString(6));
                            custom_info_hashMap.put("Flabel", cursor1.getString(7));
                            custom_info_hashMap.put("Spras", cursor1.getString(8));
                            custom_info_hashMap.put("Length", cursor1.getString(9));
                            custom_info_hashMap.put("Value", "");
                            selected_custom_info_arraylist.add(custom_info_hashMap);
                        }
                        while (cursor1.moveToNext());
                    }
                }
                else
                {
                    cursor1.close();
                }

            }
        }
        catch (Exception e)
        {
        }


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
                    } else {
                        equipId_ll.setVisibility(View.VISIBLE);
                        equipName_til.setVisibility(View.VISIBLE);
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
                    plannerGroup_tiet.setText(getResources().getString(R.string.hypen_text,
                            data.getStringExtra("ingrp_id"), plnrGrpName(data.getStringExtra("ingrp_id"))));
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

    private class Get_Notification_Data extends AsyncTask<String, Integer, Void>
    {
        String notification_id = "", notification_uuid = "";
        NotifHeaderPrcbl nhp;
        ArrayList<NotifCausCodActvPrcbl> activity_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
        ArrayList<NotifCausCodActvPrcbl> causecode_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
        ArrayList<Notif_EtDocs_Parcelable> etdocs_parcablearray = new ArrayList<Notif_EtDocs_Parcelable>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_withnum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_withoutnum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
        ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
        ArrayList<NotifTaskPrcbl> tasks_parcablearray = new ArrayList<NotifTaskPrcbl>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.show_progress_dialog(getActivity(),getResources().getString(R.string.fetching_notifcation));
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                notification_id = params[0];
                notification_uuid = params[1];

                /*Fetching Data for Notification Activity*/
                Cursor cursor = null;
                try
                {
                    activity_parcablearray.clear();
                    cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_EtNotifActvs where UUID = ?", new String[]{notification_uuid});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                NotifCausCodActvPrcbl nap = new NotifCausCodActvPrcbl();
                                nap.setQmnum(cursor.getString(2));
                                nap.setItmKey(cursor.getString(3));
                                nap.setPrtGrpTxt(cursor.getString(5));
                                nap.setItmPrtCod(cursor.getString(6));
                                nap.setPrtCodTxt(cursor.getString(7));
                                nap.setDefectGrpTxt(cursor.getString(9));
                                nap.setItmDefectCod(cursor.getString(10));
                                nap.setDefectCodTxt(cursor.getString(11));
                                nap.setItmDefectShTxt(cursor.getString(12));
                                nap.setCausKey(cursor.getString(13));
                                nap.setActvKey(cursor.getString(14));
                                nap.setActvGrp(cursor.getString(15));
                                nap.setActGrpTxt(cursor.getString(16));
                                nap.setActvCod(cursor.getString(17));
                                nap.setActvCodTxt(cursor.getString(18));
                                nap.setActvShTxt(cursor.getString(19));
                                nap.setUsr01(cursor.getString(20));
                                nap.setUsr02(cursor.getString(21));
                                nap.setUsr03(cursor.getString(22));
                                nap.setUsr04(cursor.getString(23));
                                nap.setUsr05(cursor.getString(24));
                                nap.setFields(cursor.getString(25));
                                nap.setAction(cursor.getString(26));


                                /*Fetching Data for ItmPrtGrp, ItmDefectGrp from EtNotifItems*/
                                Cursor cursor1 = null;
                                try
                                {
                                    cursor1 = App_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where UUID = ? and ItemKey = ?", new String[]{notification_uuid, cursor.getString(3)});
                                    if (cursor1 != null && cursor1.getCount() > 0)
                                    {
                                        if (cursor1.moveToFirst())
                                        {
                                            do
                                            {
                                                nap.setItmPrtGrp(cursor1.getString(4));
                                                nap.setItmDefectGrp(cursor1.getString(8));
                                            }
                                            while (cursor1.moveToNext());
                                        }
                                    }
                                    else
                                    {
                                        cursor.close();
                                        nap.setItmPrtGrp("");
                                        nap.setItmDefectGrp("");
                                    }
                                }
                                catch (Exception e)
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
                                    nap.setItmPrtGrp("");
                                    nap.setItmDefectGrp("");
                                }
                                finally
                                {
                                    if (cursor1 != null)
                                    {
                                        cursor1.close();
                                    }
                                }
                                /*Fetching Data for ItmPrtGrp, ItmDefectGrp from EtNotifItems*/


                                /*Adding Activity Data to Header Object*/
                                activity_parcablearray.add(nap);
                                /*Adding Activity Data to Header Object*/

                            }
                            while (cursor.moveToNext());
                        }
                    }
                    else
                    {
                        cursor.close();
                    }
                }
                catch (Exception e)
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                /*Fetching Data for Notification Activity*/


                /*Fetching Data for Notification Status for Displaying in Notification Header*/
                Cursor stats_cursor = null;
                StringBuilder not_stBuilder = new StringBuilder();
                try
                {
                    stats_cursor = App_db.rawQuery("select * from EtNotifStatus where UUID = ?", new String[]{notification_uuid});
                    if (stats_cursor != null && stats_cursor.getCount() > 0)
                    {
                        if (stats_cursor.moveToFirst())
                        {
                            do
                            {
                                String act = stats_cursor.getString(12);
                                String Stonr = stats_cursor.getString(8);
                                String Stat = stats_cursor.getString(11);
                                if (!Stonr.equals("00") && Stat.startsWith("E"))
                                {
                                    if(act.equalsIgnoreCase("X"))
                                    {
                                        String txt04 = stats_cursor.getString(13);
                                        not_stBuilder.append(txt04);
                                        not_stBuilder.append(" ");
                                    }
                                }
                                else if (Stonr.equals("00") && Stat.startsWith("E"))
                                {
                                    if(act.equalsIgnoreCase("X"))
                                    {
                                        String txt04 = stats_cursor.getString(13);
                                        not_stBuilder.append(txt04);
                                        not_stBuilder.append(" ");
                                    }
                                }
                            }
                            while (stats_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (stats_cursor != null)
                        {
                            stats_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (stats_cursor != null)
                    {
                        stats_cursor.close();
                    }
                }
                finally
                {
                    if (stats_cursor != null)
                    {
                        stats_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status for Displaying in Notification Header*/


                /*Fetching Data for Notification EtDocs*/
                Cursor EtDocs_cursor = null;
                try
                {
                    EtDocs_cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_EtDocs where Zobjid = ?", new String[]{notification_id});
                    if (EtDocs_cursor != null && EtDocs_cursor.getCount() > 0)
                    {
                        if (EtDocs_cursor.moveToFirst())
                        {
                            do
                            {
                                Notif_EtDocs_Parcelable nap = new Notif_EtDocs_Parcelable();
                                nap.setZobjid(EtDocs_cursor.getString(2));
                                nap.setZdoctype(EtDocs_cursor.getString(3));
                                nap.setZdoctypeitem(EtDocs_cursor.getString(4));
                                nap.setFilename(EtDocs_cursor.getString(5));
                                nap.setFiletype(EtDocs_cursor.getString(6));
                                nap.setFsize(EtDocs_cursor.getString(7));
                                nap.setContent(EtDocs_cursor.getString(8));
                                nap.setDocid(EtDocs_cursor.getString(9));
                                nap.setDoctype(EtDocs_cursor.getString(10));
                                nap.setObjtype(EtDocs_cursor.getString(11));
                                nap.setStatus("old");
                                etdocs_parcablearray.add(nap);
                            }
                            while (EtDocs_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (EtDocs_cursor != null)
                        {
                            EtDocs_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (EtDocs_cursor != null)
                    {
                        EtDocs_cursor.close();
                    }
                }
                finally
                {
                    if (EtDocs_cursor != null)
                    {
                        EtDocs_cursor.close();
                    }
                }
                /*Fetching Data for Notification EtDocs*/


                /*Fetching Data for Notification Causecode*/
                Cursor Causecode_cursor = null;
                try
                {
                    causecode_parcablearray.clear();
                    Causecode_cursor = App_db.rawQuery("select * from DUE_NOTIFICATIONS_EtNotifItems where UUID = ?", new String[]{notification_uuid});
                    if (Causecode_cursor != null && Causecode_cursor.getCount() > 0)
                    {
                        if (Causecode_cursor.moveToFirst())
                        {
                            do
                            {
                                NotifCausCodActvPrcbl nccp = new NotifCausCodActvPrcbl();
                                nccp.setQmnum(Causecode_cursor.getString(2));
                                nccp.setItmKey(Causecode_cursor.getString(3));
                                nccp.setItmPrtGrp(Causecode_cursor.getString(4));
                                nccp.setPrtGrpTxt(Causecode_cursor.getString(5));
                                nccp.setItmPrtCod(Causecode_cursor.getString(6));
                                nccp.setPrtCodTxt(Causecode_cursor.getString(7));
                                nccp.setItmDefectGrp(Causecode_cursor.getString(8));
                                nccp.setDefectGrpTxt(Causecode_cursor.getString(9));
                                nccp.setItmDefectCod(Causecode_cursor.getString(10));
                                nccp.setDefectCodTxt(Causecode_cursor.getString(11));
                                nccp.setItmDefectShTxt(Causecode_cursor.getString(12));
                                nccp.setCausKey(Causecode_cursor.getString(13));
                                nccp.setCausGrp(Causecode_cursor.getString(14));
                                nccp.setCausGrpTxt(Causecode_cursor.getString(15));
                                nccp.setCausCod(Causecode_cursor.getString(16));
                                nccp.setCausCodTxt(Causecode_cursor.getString(17));
                                nccp.setCausShTxt(Causecode_cursor.getString(18));
                                nccp.setUsr01(Causecode_cursor.getString(19));
                                nccp.setUsr02(Causecode_cursor.getString(20));
                                nccp.setUsr03(Causecode_cursor.getString(21));
                                nccp.setUsr04(Causecode_cursor.getString(22));
                                nccp.setUsr05(Causecode_cursor.getString(23));
                                nccp.setStatus(Causecode_cursor.getString(24));
                                causecode_parcablearray.add(nccp);
                            }
                            while (Causecode_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if(Causecode_cursor != null)
                        {
                            Causecode_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if(Causecode_cursor != null)
                    {
                        Causecode_cursor.close();
                    }
                }
                finally
                {
                    if(Causecode_cursor != null)
                    {
                        Causecode_cursor.close();
                    }
                }
                /*Fetching Data for Notification Causecode*/


                /*Fetching Data for Notification Status With Number*/
                Cursor status_withnum_cursor = null;
                try
                {
                    status_withnum_array.clear();
                    status_withnum_cursor = App_db.rawQuery("select * from EtNotifStatus where Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_withnum_cursor != null && status_withnum_cursor.getCount() > 0)
                    {
                        if (status_withnum_cursor.moveToFirst())
                        {
                            do
                            {
                                String Stonr = status_withnum_cursor.getString(8);
                                String Stat = status_withnum_cursor.getString(11);
                                if (!Stonr.equals("00") && Stat.startsWith("E"))
                                {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_withnum_cursor.getString(2));
                                    nap.setAufnr(status_withnum_cursor.getString(3));
                                    nap.setObjnr(status_withnum_cursor.getString(4));
                                    nap.setManum(status_withnum_cursor.getString(5));
                                    nap.setStsma(status_withnum_cursor.getString(6));
                                    nap.setInist(status_withnum_cursor.getString(7));
                                    nap.setStonr(status_withnum_cursor.getString(8));
                                    nap.setHsonr(status_withnum_cursor.getString(9));
                                    nap.setNsonr(status_withnum_cursor.getString(10));
                                    nap.setStat(status_withnum_cursor.getString(11));
                                    nap.setAct(status_withnum_cursor.getString(12));
                                    nap.setTxt04(status_withnum_cursor.getString(13));
                                    nap.setTxt30(status_withnum_cursor.getString(14));
                                    String Act_Status = status_withnum_cursor.getString(12);
                                    if(Act_Status.equalsIgnoreCase("X"))
                                    {
                                        nap.setAct_Status("true");
                                    }
                                    else
                                    {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_withnum_array.add(nap);
                                }
                            }
                            while (status_withnum_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (status_withnum_cursor != null)
                        {
                            status_withnum_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (status_withnum_cursor != null)
                    {
                        status_withnum_cursor.close();
                    }
                }
                finally
                {
                    if (status_withnum_cursor != null)
                    {
                        status_withnum_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status With Number*/


                /*Fetching Data for Notification Status Without Number*/
                Cursor status_withoutnum_cursor = null;
                try
                {
                    status_withoutnum_array.clear();
                    status_withoutnum_cursor = App_db.rawQuery("select * from EtNotifStatus where Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_withoutnum_cursor != null && status_withoutnum_cursor.getCount() > 0)
                    {
                        if (status_withoutnum_cursor.moveToFirst())
                        {
                            do
                            {
                                String Stonr = status_withoutnum_cursor.getString(8);
                                String Stat = status_withoutnum_cursor.getString(11);
                                if (Stonr.equals("00") && Stat.startsWith("E"))
                                {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_withoutnum_cursor.getString(2));
                                    nap.setAufnr(status_withoutnum_cursor.getString(3));
                                    nap.setObjnr(status_withoutnum_cursor.getString(4));
                                    nap.setManum(status_withoutnum_cursor.getString(5));
                                    nap.setStsma(status_withoutnum_cursor.getString(6));
                                    nap.setInist(status_withoutnum_cursor.getString(7));
                                    nap.setStonr(status_withoutnum_cursor.getString(8));
                                    nap.setHsonr(status_withoutnum_cursor.getString(9));
                                    nap.setNsonr(status_withoutnum_cursor.getString(10));
                                    nap.setStat(status_withoutnum_cursor.getString(11));
                                    nap.setAct(status_withoutnum_cursor.getString(12));
                                    nap.setTxt04(status_withoutnum_cursor.getString(13));
                                    nap.setTxt30(status_withoutnum_cursor.getString(14));
                                    String Act_Status = status_withoutnum_cursor.getString(12);
                                    if(Act_Status.equalsIgnoreCase("X"))
                                    {
                                        nap.setAct_Status("true");
                                    }
                                    else
                                    {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_withoutnum_array.add(nap);
                                }
                            }
                            while (status_withoutnum_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (status_withoutnum_cursor != null)
                        {
                            status_withoutnum_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (status_withoutnum_cursor != null)
                    {
                        status_withoutnum_cursor.close();
                    }
                }
                finally
                {
                    if (status_withoutnum_cursor != null)
                    {
                        status_withoutnum_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status Without Number*/


                /*Fetching Data for Notification Status System_Status Number*/
                Cursor status_system_cursor = null;
                try
                {
                    status_systemstatus_array.clear();
                    status_system_cursor = App_db.rawQuery("select * from EtNotifStatus where Qmnum = ? order by Stonr", new String[]{notification_id});
                    if (status_system_cursor != null && status_system_cursor.getCount() > 0)
                    {
                        if (status_system_cursor.moveToFirst())
                        {
                            do
                            {
                                String Stat = status_system_cursor.getString(11);
                                if (Stat.startsWith("I"))
                                {
                                    Notif_Status_WithNum_Prcbl nap = new Notif_Status_WithNum_Prcbl();
                                    nap.setQmnum(status_system_cursor.getString(2));
                                    nap.setAufnr(status_system_cursor.getString(3));
                                    nap.setObjnr(status_system_cursor.getString(4));
                                    nap.setManum(status_system_cursor.getString(5));
                                    nap.setStsma(status_system_cursor.getString(6));
                                    nap.setInist(status_system_cursor.getString(7));
                                    nap.setStonr(status_system_cursor.getString(8));
                                    nap.setHsonr(status_system_cursor.getString(9));
                                    nap.setNsonr(status_system_cursor.getString(10));
                                    nap.setStat(status_system_cursor.getString(11));
                                    nap.setAct(status_system_cursor.getString(12));
                                    nap.setTxt04(status_system_cursor.getString(13));
                                    nap.setTxt30(status_system_cursor.getString(14));
                                    String Act_Status = status_system_cursor.getString(12);
                                    if(Act_Status.equalsIgnoreCase("X"))
                                    {
                                        nap.setAct_Status("true");
                                    }
                                    else
                                    {
                                        nap.setAct_Status("");
                                    }
                                    nap.setChecked_Status("");
                                    status_systemstatus_array.add(nap);
                                }
                            }
                            while (status_system_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (status_system_cursor != null)
                        {
                            status_system_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (status_system_cursor != null)
                    {
                        status_system_cursor.close();
                    }
                }
                finally
                {
                    if (status_system_cursor != null)
                    {
                        status_system_cursor.close();
                    }
                }
                /*Fetching Data for Notification Status System_Status Number*/


                /*Fetching Data for Notification Header*/
                Cursor headerdata_cursor = null;
                try
                {
                    headerdata_cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where UUID = ?", new String[]{notification_uuid});
                    if (headerdata_cursor != null && headerdata_cursor.getCount() > 0)
                    {
                        if (headerdata_cursor.moveToFirst())
                        {
                            do
                            {
                                nhp = new NotifHeaderPrcbl();
                                nhp.setUUID(notification_uuid);
                                nhp.setNotf_Status(not_stBuilder.toString());
                                nhp.setNotifType(headerdata_cursor.getString(2));
                                nhp.setQmnum(headerdata_cursor.getString(3));
                                nhp.setNotifShrtTxt(headerdata_cursor.getString(4));
                                nhp.setFuncLoc(headerdata_cursor.getString(5));
                                nhp.setEquip(headerdata_cursor.getString(6));
                                nhp.setBautl(headerdata_cursor.getString(7));
                                nhp.setReportedBy(headerdata_cursor.getString(8));
                                nhp.setMalfuncStDt(headerdata_cursor.getString(9));
                                nhp.setMalfuncEdDt(headerdata_cursor.getString(10));
                                nhp.setMalfuncSttm(headerdata_cursor.getString(11));
                                nhp.setMalfuncEdtm(headerdata_cursor.getString(12));
                                nhp.setBrkDwnInd(headerdata_cursor.getString(13));
                                nhp.setPriority(headerdata_cursor.getString(14));
                                nhp.setIngrp(headerdata_cursor.getString(15));
                                nhp.setArbpl(headerdata_cursor.getString(16));
                                nhp.setWerks(headerdata_cursor.getString(17));
                                nhp.setStrmn(headerdata_cursor.getString(18));
                                nhp.setLtrmn(headerdata_cursor.getString(19));
                                nhp.setAufnr(headerdata_cursor.getString(20));
                                nhp.setDocs(headerdata_cursor.getString(21));
                                nhp.setAltitude(headerdata_cursor.getString(22));
                                nhp.setLatitude(headerdata_cursor.getString(23));
                                nhp.setLongitude(headerdata_cursor.getString(24));
                                nhp.setClosed(headerdata_cursor.getString(25));
                                nhp.setCompleted(headerdata_cursor.getString(26));
                                nhp.setCreatedOn(headerdata_cursor.getString(27));
                                nhp.setQmartx(headerdata_cursor.getString(28));
                                nhp.setPltxt(headerdata_cursor.getString(29));
                                nhp.setEquipTxt(headerdata_cursor.getString(30));
                                nhp.setPriorityTxt(headerdata_cursor.getString(31));
                                nhp.setAufTxt(headerdata_cursor.getString(32));
                                nhp.setAuartTxt(headerdata_cursor.getString(33));
                                nhp.setPlantTxt(headerdata_cursor.getString(34));
                                nhp.setWrkCntrTxt(headerdata_cursor.getString(35));
                                nhp.setIngrpName(headerdata_cursor.getString(36));
                                nhp.setMaktx(headerdata_cursor.getString(37));
                                nhp.setXStatus(headerdata_cursor.getString(44));
                                nhp.setUsr01(headerdata_cursor.getString(39));
                                nhp.setUsr02(headerdata_cursor.getString(40));
                                nhp.setUsr03(headerdata_cursor.getString(41));
                                nhp.setUsr04(headerdata_cursor.getString(42));
                                nhp.setUsr05(headerdata_cursor.getString(43));
                                nhp.setParnrVw(headerdata_cursor.getString(45));
                                nhp.setNameVw(headerdata_cursor.getString(46));
                                nhp.setAuswk(headerdata_cursor.getString(47));
                                nhp.setShift(headerdata_cursor.getString(48));
                                nhp.setNoOfPerson(headerdata_cursor.getString(49));
                                nhp.setAuswkt(headerdata_cursor.getString(50));
                                nhp.setStrur(headerdata_cursor.getString(51));
                                nhp.setLtrur(headerdata_cursor.getString(52));
                                nhp.setQmdat(headerdata_cursor.getString(54));
                                nhp.setActvPrcblAl(activity_parcablearray);
                                nhp.setCausCodPrcblAl(causecode_parcablearray);
                                nhp.setEtDocsParcelables(etdocs_parcablearray);
                                nhp.setStatus_withNum_prcbls(status_withnum_array);
                                nhp.setStatus_withoutNum_prcbls(status_withoutnum_array);
                                nhp.setStatus_systemstatus_prcbls(status_systemstatus_array);
                                nhp.setNotifTaskPrcbls(tasks_parcablearray);
                            }
                            while (headerdata_cursor.moveToNext());
                        }
                    }
                    else
                    {
                        if (headerdata_cursor != null)
                        {
                            headerdata_cursor.close();
                        }
                    }
                }
                catch (Exception e)
                {
                    if (headerdata_cursor != null)
                    {
                        headerdata_cursor.close();
                    }
                }
                finally
                {
                    if (headerdata_cursor != null)
                    {
                        headerdata_cursor.close();
                    }
                }
                /*Fetching Data for Notification Header*/


            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progressDialog.dismiss_progress_dialog();
            if (nhp != null)
            {
                Intent ordrIntent = new Intent(getActivity(), Notifications_Change_Activity.class);
                ordrIntent.putExtra("notif_status","U");
                ordrIntent.putExtra("notif_parcel", nhp);
                startActivity(ordrIntent);
            }
            else
            {

            }
        }
    }


    public ArrayList<HashMap<String, String>> getHeaderCustominfoData()
    {
        return selected_custom_info_arraylist;
    }

}
