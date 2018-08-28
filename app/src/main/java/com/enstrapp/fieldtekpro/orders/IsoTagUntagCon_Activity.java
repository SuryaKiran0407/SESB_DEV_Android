package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.equipment.Equipment_Activity;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.functionlocation.FunctionLocation_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class IsoTagUntagCon_Activity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText shrtText_tiet, type_tiet, obj_tiet, tagCondtn_tiet, untagCondtn_tiet,
            lockNo_tiet, tagTxt_tiet, untagTxt_tiet;
    ImageView tagUntag_iv, obj_iv, type_iv;
    ArrayList<HashMap<String, String>> wcm_wcd_type_list = new ArrayList<>();
    static final int TYPE = 985;
    static final int OBJ = 565;
    static final int TGUNTG = 379;
    OrdrWDItemPrcbl wdip = new OrdrWDItemPrcbl();
    Toolbar toolBar;
    TYPE_ADAPTER type_adapter;
    String funcId = "", equiId = "", equipName = "", iwerk = "", wcnr = "";
    Error_Dialog errorDialog = new Error_Dialog();
    static final int EQUIP_ID = 3;
    static final int FUNC_LOC = 2;
    Dialog wcm_wcd_type_dialog;
    Button submit_bt, cancel_bt;
    LinearLayout footer_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_screen);

        footer_ll = findViewById(R.id.footer_ll);
        cancel_bt = findViewById(R.id.cancel_bt);
        submit_bt = findViewById(R.id.submit_bt);
        shrtText_tiet = findViewById(R.id.shrtText_tiet);
        type_tiet = findViewById(R.id.type_tiet);
        obj_tiet = findViewById(R.id.obj_tiet);
        tagCondtn_tiet = findViewById(R.id.tagCondtn_tiet);
        untagCondtn_tiet = findViewById(R.id.untagCondtn_tiet);
        lockNo_tiet = findViewById(R.id.lockNo_tiet);
        tagTxt_tiet = findViewById(R.id.tagTxt_tiet);
        untagTxt_tiet = findViewById(R.id.untagTxt_tiet);
        tagUntag_iv = findViewById(R.id.tagUntag_iv);
        obj_iv = findViewById(R.id.obj_iv);
        type_iv = findViewById(R.id.type_iv);
        toolBar = findViewById(R.id.toolBar);

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tagUntag_iv.setOnClickListener(this);
        obj_iv.setOnClickListener(this);
        type_iv.setOnClickListener(this);
        submit_bt.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            funcId = extras.getString("func_loc");
            equiId = extras.getString("equip");
            equipName = extras.getString("equip_txt");
            iwerk = extras.getString("iwerk");
            wcnr = extras.getString("wcnr");
            wdip = extras.getParcelable("wdip");

            if (wdip != null) {
                if (wdip.getWctim().startsWith("00")) {
                    shrtText_tiet.setEnabled(false);
                    type_iv.setEnabled(false);
                    obj_iv.setEnabled(false);
                    obj_tiet.setEnabled(false);
                    tagUntag_iv.setEnabled(false);
                    lockNo_tiet.setEnabled(false);
                    tagTxt_tiet.setEnabled(false);
                    untagTxt_tiet.setEnabled(false);
                    footer_ll.setVisibility(GONE);
                } else if (wdip.isTagStatus()) {
                    shrtText_tiet.setEnabled(false);
                    type_iv.setEnabled(false);
                    obj_iv.setEnabled(false);
                    obj_tiet.setEnabled(false);
                    tagUntag_iv.setEnabled(false);
                    lockNo_tiet.setEnabled(false);
                    tagTxt_tiet.setEnabled(false);
                    untagTxt_tiet.setEnabled(false);
                    footer_ll.setVisibility(GONE);
                }
                shrtText_tiet.setText(wdip.getStxt());
                if (wdip.getCctyp().equalsIgnoreCase("E"))
                    type_tiet.setText("E - Equipment");
                else if (wdip.getCctyp().equalsIgnoreCase("F"))
                    type_tiet.setText("F - Functional Location");
                else if (wdip.getCctyp().equalsIgnoreCase("N"))
                    type_tiet.setText("N - Object");
                else
                    type_tiet.setText("  - Comments");

                obj_tiet.setText(wdip.getCcobj());
                tagCondtn_tiet.setText(wdip.getTgproc());
                untagCondtn_tiet.setText(wdip.getUnproc());
                lockNo_tiet.setText(wdip.getPhblnr());
                tagTxt_tiet.setText(wdip.getTgtxt());
                untagTxt_tiet.setText(wdip.getUntxt());
            } else {
                wdip = new OrdrWDItemPrcbl();
            }
        }

        obj_tiet.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                wdip.setCcobj("");
                if (obj_tiet.getText().toString() != null && !obj_tiet.getText().toString().equals("")) {
                    if (!type_tiet.getText().toString().startsWith("E"))
                        wdip.setCcobj(obj_tiet.getText().toString());
                } else {
                    wdip.setCcobj("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

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
            case (R.id.tagUntag_iv):
                Intent tagCondIntent = new Intent(IsoTagUntagCon_Activity.this, TaggingConditions.class);
                tagCondIntent.putExtra("iwerk", iwerk);
                startActivityForResult(tagCondIntent, TGUNTG);
                break;

            case (R.id.obj_iv):
                if (type_tiet.getText().toString() != null && !type_tiet.getText().toString().equals("")) {
                    if (type_tiet.getText().toString().startsWith("E")) {
                        Intent equipIdIntent = new Intent(IsoTagUntagCon_Activity.this, Equipment_Activity.class);
                        startActivityForResult(equipIdIntent, EQUIP_ID);
                    } else if (type_tiet.getText().toString().startsWith("F")) {
                        Intent funcLocIntent = new Intent(IsoTagUntagCon_Activity.this, FunctionLocation_Activity.class);
                        startActivityForResult(funcLocIntent, FUNC_LOC);
                    }
                } else {
                    errorDialog.show_error_dialog(IsoTagUntagCon_Activity.this, getString(R.string.type_mandate));
                }

                break;

            case (R.id.type_iv):
                typeData();
                break;

            case (R.id.submit_bt):
                if (type_tiet.getText().toString() != null && !type_tiet.getText().toString().equals("")) {
                    if (untagCondtn_tiet.getText().toString() != null && !untagCondtn_tiet.getText().toString().equals("")) {
                        wdip.setPhblnr(lockNo_tiet.getText().toString());
                        wdip.setTgtxt(tagTxt_tiet.getText().toString());
                        wdip.setUntxt(untagTxt_tiet.getText().toString());
                        wdip.setWcnr(wcnr);
                        wdip.setAction("I");
                        wdip.setBtg("");
                        wdip.setEtg("");
                        wdip.setBug("");
                        wdip.setEug("");
                        wdip.setStxt(shrtText_tiet.getText().toString());
                        Intent intent = new Intent();
                        intent.putExtra("wdip", wdip);
                        setResult(RESULT_OK, intent);
                        IsoTagUntagCon_Activity.this.finish();
                    } else {
                        errorDialog.show_error_dialog(IsoTagUntagCon_Activity.this, getString(R.string.tag_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(IsoTagUntagCon_Activity.this, getString(R.string.type_mandate));
                }
                break;

            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                IsoTagUntagCon_Activity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (EQUIP_ID):
                if (resultCode == RESULT_OK) {
                    obj_tiet.setText(data.getStringExtra("equipment_text"));
                    wdip.setCcobj(data.getStringExtra("equipment_id"));
                    wdip.setEquipdDesc(data.getStringExtra("equipment_text"));
                }
                break;

            case (FUNC_LOC):
                if (resultCode == RESULT_OK) {
                    obj_tiet.setText(data.getStringExtra("functionlocation_id"));
                    wdip.setCcobj(data.getStringExtra("functionlocation_id"));
                }
                break;

            case (TGUNTG):
                if (resultCode == RESULT_OK) {
                    tagCondtn_tiet.setText(data.getStringExtra("tgproc"));
                    untagCondtn_tiet.setText(data.getStringExtra("unproc"));
                    wdip.setTggrp(data.getStringExtra("tggrp"));
                    wdip.setTgproc(data.getStringExtra("tgproc"));
                    wdip.setUnproc(data.getStringExtra("unproc"));
                    wdip.setTgprox(data.getStringExtra("tgprox"));
                    wdip.setTgtyp(data.getStringExtra("tgtyp"));
                    wdip.setUntyp(data.getStringExtra("untyp"));
                    wdip.setPhblnr(data.getStringExtra("phblflg"));
                    wdip.setTgflg(data.getStringExtra("tgflg"));
                }
                break;
        }
    }

    private void typeData() {
        wcm_wcd_type_list.clear();
        wcm_wcd_type_dialog = new Dialog(IsoTagUntagCon_Activity.this, R.style.AppThemeDialog_Dark);
        wcm_wcd_type_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        wcm_wcd_type_dialog.setCancelable(true);
        wcm_wcd_type_dialog.setCanceledOnTouchOutside(false);
        wcm_wcd_type_dialog.setContentView(R.layout.iso_type_dialog);
        wcm_wcd_type_dialog.getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
        RecyclerView listview = wcm_wcd_type_dialog.findViewById(R.id.list_recycleview);
        TextView title_textview = wcm_wcd_type_dialog.findViewById(R.id.title_textview);
        LinearLayout noData_ll = wcm_wcd_type_dialog.findViewById(R.id.noData_ll);
        ImageView back_imageview = wcm_wcd_type_dialog.findViewById(R.id.back_imageview);
        title_textview.setText("Type");
        String[] wcm_applicationtype_list = getResources().getStringArray(R.array.wcm_wcd_type_list);
        for (int i = 0; i < wcm_applicationtype_list.length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("wcm_wcd_type_list", wcm_applicationtype_list[i]);
            wcm_wcd_type_list.add(map);
        }
        wcm_wcd_type_dialog.show();
        type_adapter = new TYPE_ADAPTER(IsoTagUntagCon_Activity.this, wcm_wcd_type_list);
        listview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IsoTagUntagCon_Activity.this);
        listview.setLayoutManager(layoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setAdapter(type_adapter);
        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wcm_wcd_type_dialog.dismiss();
            }
        });
    }

    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<HashMap<String, String>> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, value_textview;
            LinearLayout data_layout, id_ll;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                id_ll = (LinearLayout) view.findViewById(R.id.id_ll);
            }
        }

        public TYPE_ADAPTER(Context mContext, List<HashMap<String, String>> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final HashMap<String, String> nto = type_details_list.get(position);
//            holder.id_textview.setText(nto.getId());
            holder.value_textview.setText(nto.get("wcm_wcd_type_list"));
            holder.id_ll.setVisibility(GONE);

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.value_textview.getText().toString().startsWith("E")) {
                        if (equipName != null && !equipName.equals(""))
                            obj_tiet.setText(equipName);
                        type_tiet.setText(holder.value_textview.getText().toString());
                        obj_iv.setVisibility(View.VISIBLE);
                        obj_tiet.setEnabled(false);
                        wdip.setCcobj(equiId);
                        wdip.setCctyp("E");
                        wdip.setEquipdDesc(equipName);
                    } else if (holder.value_textview.getText().toString().startsWith("F")) {
                        if (funcId != null && !funcId.equals(""))
                            obj_tiet.setText(funcId);
                        type_tiet.setText(holder.value_textview.getText().toString());
                        obj_iv.setVisibility(View.VISIBLE);
                        obj_tiet.setEnabled(false);
                        wdip.setCcobj(funcId);
                        wdip.setCctyp("F");
                    } else if (holder.value_textview.getText().toString().startsWith("N")) {
                        type_tiet.setText(holder.value_textview.getText().toString());
                        obj_iv.setVisibility(GONE);
                        obj_tiet.setEnabled(true);
                        wdip.setCctyp("N");
                    } else {
                        type_tiet.setText(holder.value_textview.getText().toString());
                        obj_iv.setVisibility(GONE);
                        obj_tiet.setEnabled(true);
                        wdip.setCctyp(" ");
                    }
                    wcm_wcd_type_dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

}
