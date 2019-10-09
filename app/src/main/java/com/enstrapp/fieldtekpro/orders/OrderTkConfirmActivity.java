package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.enstrapp.fieldtekpro.successdialog.Success_Dialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import static android.view.View.VISIBLE;

public class OrderTkConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    Dialog decision_dialog;
    Button copy_bt, mDoc_bt, save_bt;
    RecyclerView operations_rv;
    LinearLayout no_data_layout;
    ImageButton attachments_ib, back_ib;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    String orderUUID = "", ordrId = "", equip = "", funcLoc = "", ordrStDt = "", ordrEdDt = "",
            slctStDt = "", slctStTm = "", slctEdDt = "", slctEdTm = "", slctCntrmTxt = "",
            slctEmply = "", slctNoRmWrk = "", slctFnlCnfrm = "", Response = "";
    ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<>();
    int count = 0;
    boolean oprtnSelected = false;
    OperationsAdapter operationsAdapter;
    Error_Dialog errorDialog = new Error_Dialog();
    static final int COPY = 279;
    static final int MDOC = 722;
    CheckBox selectAll_cb;
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    Success_Dialog successDialog = new Success_Dialog();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ArrayList<Measurement_Parceble> mpo_al = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_tk_confirm_activity);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            orderUUID = bundle.getString("uuid");
            ordrId = bundle.getString("orderId");
            equip = bundle.getString("equip");
            funcLoc = bundle.getString("funcLoc");
            ordrStDt = bundle.getString("stDt");
            ordrEdDt = bundle.getString("edDt");
        }

        selectAll_cb = findViewById(R.id.selectAll_cb);
        copy_bt = findViewById(R.id.copy_bt);
        mDoc_bt = findViewById(R.id.mDoc_bt);
        save_bt = findViewById(R.id.save_bt);
        operations_rv = findViewById(R.id.list_recycleview);
        attachments_ib = findViewById(R.id.attachments_ib);
        no_data_layout = findViewById(R.id.no_data_layout);
        back_ib = findViewById(R.id.back_ib);

        DATABASE_NAME = OrderTkConfirmActivity.this.getString(R.string.database_name);
        App_db = OrderTkConfirmActivity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,
                null);

        back_ib.setOnClickListener(this);
        mDoc_bt.setOnClickListener(this);
        copy_bt.setOnClickListener(this);
        save_bt.setOnClickListener(this);
        attachments_ib.setOnClickListener(this);
        selectAll_cb.setOnClickListener(this);

        getOprtnData(orderUUID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.back_ib):
                onBackPressed();
                break;

            case (R.id.attachments_ib):
                Intent attachIntent = new Intent(OrderTkConfirmActivity.this,
                        OrdersConfirmAttachment_Activity.class);
                attachIntent.putExtra("ordrId", ordrId);
                startActivity(attachIntent);

                break;
            case (R.id.save_bt):
                oprtnSelected = false;
                for (OrdrOprtnPrcbl oop : oop_al) {
                    if (oop.isSelected())
                        oprtnSelected = true;
                }
                if (oprtnSelected)
                {
                    if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
                    {
                        confirmationDialog(getString(R.string.confirm_msg));
                    }
                    else
                    {
                        errorDialog.show_error_dialog(OrderTkConfirmActivity.this,"Please Enter Confirmation Text.");
                    }
                }
                else
                {
                    errorDialog.show_error_dialog(OrderTkConfirmActivity.this,
                            getString(R.string.select_one));
                }
                break;

            case (R.id.mDoc_bt):
                Intent mDocIntent = new Intent(OrderTkConfirmActivity.this,
                        Measurment_Activity.class);
                mDocIntent.putExtra("equipId", equip);
                mDocIntent.putExtra("ordrId", ordrId);
                mDocIntent.putExtra("UUID", orderUUID);
                mDocIntent.putExtra("funcLoc", funcLoc);
                mDocIntent.putExtra("mpo_al", mpo_al);
                startActivityForResult(mDocIntent, MDOC);
                break;

            case (R.id.copy_bt):
                oprtnSelected = false;
                for (OrdrOprtnPrcbl oop : oop_al) {
                    if (oop.isSelected())
                        oprtnSelected = true;
                }
                if (oprtnSelected) {
                    Intent intent = new Intent(OrderTkConfirmActivity.this,
                            TimeConfirmation.class);
                    intent.putExtra("type", "C");
                    if (slctStDt != null && !slctStDt.equals(""))
                        intent.putExtra("stDt", slctStDt);
                    else
                        intent.putExtra("stDt", ordrStDt);
                    if (slctEdDt != null && !slctEdDt.equals(""))
                        intent.putExtra("edDt", slctEdDt);
                    else
                        intent.putExtra("edDt", ordrEdDt);
                    if (slctStTm != null && !slctStTm.equals(""))
                        intent.putExtra("stTm", slctStTm);
                    else
                        intent.putExtra("stTm", "");
                    if (slctEdTm != null && !slctEdTm.equals(""))
                        intent.putExtra("edTm", slctEdTm);
                    else
                        intent.putExtra("edTm", "");
                    if (slctNoRmWrk != null && !slctNoRmWrk.equals(""))
                        intent.putExtra("noRmWrk", slctNoRmWrk);
                    else
                        intent.putExtra("noRmWrk", "");
                    if (slctFnlCnfrm != null && !slctFnlCnfrm.equals(""))
                        intent.putExtra("fnlCnfrm", slctFnlCnfrm);
                    else
                        intent.putExtra("fnlCnfrm", "");
                    if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
                        intent.putExtra("cntrmTxt", slctCntrmTxt);
                    else
                        intent.putExtra("cntrmTxt", "");
                    if (slctEmply != null && !slctEmply.equals(""))
                        intent.putExtra("emply", slctEmply);
                    else
                        intent.putExtra("emply", "");
                    startActivityForResult(intent, COPY);
                } else {
                    errorDialog.show_error_dialog(OrderTkConfirmActivity.this,
                            getString(R.string.select_one));
                }
                break;

            case (R.id.selectAll_cb):
                if (selectAll_cb.isChecked()) {
                    for (OrdrOprtnPrcbl oop : oop_al) {
                        oop.setSelected(true);
                    }
                    operationsAdapter.notifyDataSetChanged();
                } else {
                    for (OrdrOprtnPrcbl oop : oop_al) {
                        oop.setSelected(false);
                    }
                    operationsAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (COPY):
                if (resultCode == RESULT_OK) {
                    slctStTm = data.getStringExtra("stTm");
                    slctStDt = data.getStringExtra("stDt");
                    slctEdDt = data.getStringExtra("edDt");
                    slctEdTm = data.getStringExtra("edTm");
                    slctCntrmTxt = data.getStringExtra("cntrmTxt");
                    slctFnlCnfrm = data.getStringExtra("fnlCnfrm");
                    slctEmply = data.getStringExtra("emply");
                    slctNoRmWrk = data.getStringExtra("noRmWrk");
                }
                break;

            case (MDOC):
                if (resultCode == RESULT_OK) {
                    mpo_al = data.getParcelableArrayListExtra("mpo_al");
                }
                break;
        }
    }


    public class GetToken extends AsyncTask<Void, Integer, Void> {
        String Response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(OrderTkConfirmActivity.this,
                    getResources().getString(R.string.confirm_progs));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Token().Get_Token(OrderTkConfirmActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Response.equals("success"))
                new POST_TK_CONFIRM().execute();
            else {
                customProgressDialog.dismiss_progress_dialog();
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this,
                        getResources().getString(R.string.unable_confirm));
            }
        }
    }



    private class POST_TK_CONFIRM extends AsyncTask<Void, Integer, Void> {

        ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cop_al.clear();
            for (OrdrOprtnPrcbl oop : oop_al) {
                if (oop.isSelected()) {
                    ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
                    cop.setAufnr(oop.getOrdrId());
                    cop.setVornr(oop.getOprtnId());
                    cop.setConfNo("");
                    if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
                        cop.setConfText(slctCntrmTxt);
                    else
                        cop.setConfText("");
                    cop.setActWork("0");
                    cop.setUnWork("");
                    cop.setPlanWork("0");
                    cop.setLearr("");
                    cop.setBemot("");
                    cop.setGrund("");
                    if (slctNoRmWrk != null && !slctNoRmWrk.equals("")) {
                        if (slctNoRmWrk.equals("X"))
                            cop.setLeknw(slctNoRmWrk);
                    } else
                        cop.setLeknw("");
                    if (slctFnlCnfrm != null && !slctFnlCnfrm.equals("")) {
                        if (slctFnlCnfrm.equals("X"))
                            cop.setAueru(slctFnlCnfrm);
                    } else
                        cop.setAueru("");
                    cop.setAusor("");
                    cop.setPernr("");
                    cop.setLoart("");
                    cop.setStatus("");
                    cop.setRsnum("");
                    cop.setPostgDate("");
                    cop.setPlant("");
                    cop.setWorkCntr(oop.getWrkCntrId());
                    if (slctStDt != null && !slctStDt.equals(""))
                        cop.setExecStartDate(slctStDt);
                    else
                        cop.setExecStartDate(dateFormat(ordrStDt));
                    if (slctStTm != null && !slctStTm.equals(""))
                        cop.setExecStartTime(slctStTm);
                    else
                        cop.setExecStartTime("");
                    if (slctEdDt != null && !slctEdDt.equals(""))
                        cop.setExecFinDate(slctEdDt);
                    else
                        cop.setExecFinDate(ordrEdDt);
                    if (slctEdTm != null && !slctEdTm.equals(""))
                        cop.setExecFinTime(slctEdTm);
                    else
                        cop.setExecFinTime("");
                    if (slctEmply != null && !slctEmply.equals(""))
                        cop.setPernr(slctEmply);
                    else
                        cop.setPernr("");
                    cop_al.add(cop);
                }
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Order_CConfirmation().Get_Data(OrderTkConfirmActivity.this,
                    cop_al, mpo_al, "", "CCORD", ordrId, "ORCC",
                    "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("S"))
                successDialog.dismissActivity(OrderTkConfirmActivity.this,
                        Response.substring(2));
            else if (Response.startsWith("E"))
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this,
                        Response.substring(2));
            else
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this, Response);
        }
    }



    private class POST_TK_CONFIRM_REST extends AsyncTask<Void, Integer, Void>
    {
        ArrayList<ConfirmOrder_Prcbl> cop_al = new ArrayList<>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(OrderTkConfirmActivity.this,getResources().getString(R.string.confirm_progs));
            cop_al.clear();
            for (OrdrOprtnPrcbl oop : oop_al)
            {
                if (oop.isSelected())
                {
                    ConfirmOrder_Prcbl cop = new ConfirmOrder_Prcbl();
                    cop.setAufnr(oop.getOrdrId());
                    cop.setVornr(oop.getOprtnId());
                    cop.setConfNo("");
                    if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
                        cop.setConfText(slctCntrmTxt);
                    else
                        cop.setConfText("");
                    cop.setActWork("0");
                    cop.setUnWork("");
                    cop.setPlanWork("0");
                    cop.setLearr("");
                    cop.setBemot("");
                    cop.setGrund("");
                    if (slctNoRmWrk != null && !slctNoRmWrk.equals(""))
                    {
                        if (slctNoRmWrk.equals("X"))
                            cop.setLeknw(slctNoRmWrk);
                    }
                    else
                    {
                        cop.setLeknw("");
                    }
                    if (slctFnlCnfrm != null && !slctFnlCnfrm.equals(""))
                    {
                        if (slctFnlCnfrm.equals("X"))
                            cop.setAueru(slctFnlCnfrm);
                    }
                    else
                    {
                        cop.setAueru("");
                    }
                    cop.setAusor("");
                    cop.setPernr("");
                    cop.setLoart("");
                    cop.setStatus("");
                    cop.setRsnum("");
                    cop.setPostgDate("");
                    cop.setPlant("");
                    cop.setWorkCntr(oop.getWrkCntrId());
                    if (slctStDt != null && !slctStDt.equals(""))
                        cop.setExecStartDate(slctStDt);
                    else
                        cop.setExecStartDate(dateFormat(ordrStDt));
                    if (slctStTm != null && !slctStTm.equals(""))
                        cop.setExecStartTime(slctStTm);
                    else
                        cop.setExecStartTime("");
                    if (slctEdDt != null && !slctEdDt.equals(""))
                        cop.setExecFinDate(slctEdDt);
                    else
                        cop.setExecFinDate(ordrEdDt);
                    if (slctEdTm != null && !slctEdTm.equals(""))
                        cop.setExecFinTime(slctEdTm);
                    else
                        cop.setExecFinTime("");
                    if (slctEmply != null && !slctEmply.equals(""))
                        cop.setPernr(slctEmply);
                    else
                        cop.setPernr("");
                    cop_al.add(cop);
                }
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response = new Order_CConfirmation_REST().Get_Data(OrderTkConfirmActivity.this,
                    cop_al, mpo_al, "", "CCORD", ordrId, "ORCC",
                    "");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customProgressDialog.dismiss_progress_dialog();
            if (Response.startsWith("S"))
            {
                successDialog.dismissActivity(OrderTkConfirmActivity.this,
                        Response.substring(2));
            }
            else if (Response.startsWith("Ex"))
            {
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this, getString(R.string.cnfrmordr_unable));
            }
            else if (Response.startsWith("E"))
            {
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this,
                        Response.substring(2));
            }
            else
            {
                errorDialog.show_error_dialog(OrderTkConfirmActivity.this, Response);
            }
        }
    }



    private void getOprtnData(String orderUUID) {
        oop_al.clear();
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations where UUID = ?"
                    + "ORDER BY id DESC", new String[]{orderUUID});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                        oop.setSelected(false);
                        oop.setOrdrId(cursor.getString(2));
                        oop.setOprtnId(cursor.getString(3));
                        oop.setOprtnShrtTxt(cursor.getString(5));
                        oop.setDuration(cursor.getString(10));
                        oop.setDurationUnit(cursor.getString(11));
                        oop.setPlantId(cursor.getString(7));
                        oop.setPlantTxt(cursor.getString(22));
                        oop.setWrkCntrId(cursor.getString(6));
                        oop.setWrkCntrTxt(cursor.getString(21));
                        oop.setCntrlKeyId(cursor.getString(8));
                        oop.setCntrlKeyTxt(cursor.getString(23));
                        oop.setAueru(cursor.getString(20));
                        oop.setUsr01(cursor.getString(25));
                        oop.setLarnt(cursor.getString(9));
                        oop.setFsavd(cursor.getString(12));
                        oop.setSsedd(cursor.getString(13));
                        oop.setRueck(cursor.getString(19));
                        oop.setUsr04(cursor.getString(28));
                        oop.setStatus("");
                        oop_al.add(oop);
                    }
                    while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        if (oop_al.size() > 0) {
            Collections.sort(oop_al, new Comparator<OrdrOprtnPrcbl>() {
                public int compare(OrdrOprtnPrcbl oop1, OrdrOprtnPrcbl oop2) {
                    return oop1.getOprtnId().compareToIgnoreCase(oop2.getOprtnId());
                }
            });
            operationsAdapter = new OperationsAdapter(OrderTkConfirmActivity.this, oop_al);
            operations_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(OrderTkConfirmActivity.this);
            operations_rv.setLayoutManager(layoutManager);
            operations_rv.setItemAnimator(new DefaultItemAnimator());
            no_data_layout.setVisibility(View.GONE);
            operations_rv.setAdapter(operationsAdapter);
        } else {
            no_data_layout.setVisibility(VISIBLE);
            operations_rv.setVisibility(View.GONE);
        }
    }


    private class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrOprtnPrcbl> operationsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView oprtnId_tv, duration_tv, oprtnShrtTxt_tv, remainingWork_tv;
            ImageView red_iv, yellow_iv, green_iv, confirm_iv;
            CheckBox checkBox;
            LinearLayout oprtnList_ll, remainingWork_ll;

            public MyViewHolder(View view) {
                super(view);
                checkBox = view.findViewById(R.id.checkBox);
                oprtnId_tv = view.findViewById(R.id.oprtnId_tv);
                duration_tv = view.findViewById(R.id.duration_tv);
                confirm_iv = view.findViewById(R.id.confirm_iv);
                oprtnShrtTxt_tv = view.findViewById(R.id.oprtnShrtTxt_tv);
                red_iv = view.findViewById(R.id.red_iv);
                yellow_iv = view.findViewById(R.id.yellow_iv);
                green_iv = view.findViewById(R.id.green_iv);
                oprtnList_ll = view.findViewById(R.id.oprtnList_ll);
                remainingWork_ll = view.findViewById(R.id.remainingWork_ll);
                remainingWork_tv = view.findViewById(R.id.remainingWork_tv);
            }
        }

        public OperationsAdapter(Context mContext, ArrayList<OrdrOprtnPrcbl> list) {
            this.mContext = mContext;
            this.operationsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.orders_operation_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrOprtnPrcbl oop = operationsList.get(position);
            if (!oop.getStatus().equals("D")) {
                holder.oprtnId_tv.setText(oop.getOprtnId());
                holder.duration_tv.setText(oop.getDuration() + " " + oop.getDurationUnit());
                holder.remainingWork_ll.setVisibility(VISIBLE);
                holder.oprtnShrtTxt_tv.setText(oop.getOprtnShrtTxt());
                holder.remainingWork_tv.setText(oop.getUsr04() + " " + oop.getDurationUnit());
                holder.checkBox.setTag(position);
                holder.oprtnId_tv.setTag(position);
                holder.checkBox.setChecked(oop.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.checkBox.isChecked()) {
                            count = 0;
                            operationsList.get((Integer) v.getTag()).setSelected(true);
                            for (OrdrOprtnPrcbl oop : oop_al) {
                                if (oop.isSelected()) {
                                    count = count + 1;
                                }
                            }
                            if (count == operationsList.size())
                                selectAll_cb.setChecked(true);
                        } else {
                            operationsList.get((Integer) v.getTag()).setSelected(false);
                            selectAll_cb.setChecked(false);
                        }
                    }
                });

                if (oop.getAueru().equalsIgnoreCase("X")) {
                    holder.red_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                    holder.yellow_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                    holder.green_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                } else if (oop.getAueru().equalsIgnoreCase("Y")) {
                    holder.red_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                    holder.yellow_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                    holder.green_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                } else {
                    holder.red_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    holder.yellow_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                    holder.green_iv.setImageResource(R.drawable.ic_pause_circle_icon);
                }

                holder.confirm_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderTkConfirmActivity.this,
                                TimeConfirmation.class);
                        intent.putExtra("type", "D");
                        if (slctStDt != null && !slctStDt.equals(""))
                            intent.putExtra("stDt", slctStDt);
                        else
                            intent.putExtra("stDt", ordrStDt);
                        if (slctEdDt != null && !slctEdDt.equals(""))
                            intent.putExtra("edDt", slctEdDt);
                        else
                            intent.putExtra("edDt", ordrEdDt);
                        if (slctStTm != null && !slctStTm.equals(""))
                            intent.putExtra("stTm", slctStTm);
                        else
                            intent.putExtra("stTm", "");
                        if (slctEdTm != null && !slctEdTm.equals(""))
                            intent.putExtra("edTm", slctEdTm);
                        else
                            intent.putExtra("edTm", "");
                        if (slctNoRmWrk != null && !slctNoRmWrk.equals(""))
                            intent.putExtra("noRmWrk", slctNoRmWrk);
                        else
                            intent.putExtra("noRmWrk", "");
                        if (slctFnlCnfrm != null && !slctFnlCnfrm.equals(""))
                            intent.putExtra("fnlCnfrm", slctFnlCnfrm);
                        else
                            intent.putExtra("fnlCnfrm", "");
                        if (slctCntrmTxt != null && !slctCntrmTxt.equals(""))
                            intent.putExtra("cntrmTxt", slctCntrmTxt);
                        else
                            intent.putExtra("cntrmTxt", "");
                        if (slctEmply != null && !slctEmply.equals(""))
                            intent.putExtra("emply", slctEmply);
                        else
                            intent.putExtra("emply", "");
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return operationsList.size();
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

    private void confirmationDialog(String message)
    {
        final Dialog cancel_dialog = new Dialog(OrderTkConfirmActivity.this, R.style.PauseDialog);
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
                cd = new ConnectionDetector(OrderTkConfirmActivity.this);
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent)
                {
                    cancel_dialog.dismiss();
                    String webservice_type = getString(R.string.webservice_type);
                    if(webservice_type.equalsIgnoreCase("odata"))
                    {
                        new GetToken().execute();
                    }
                    else
                    {
                        new POST_TK_CONFIRM_REST().execute();
                    }
                }
                else
                {
                    cancel_dialog.dismiss();
                    decision_dialog = new Dialog(OrderTkConfirmActivity.this);
                    decision_dialog.getWindow()
                            .setBackgroundDrawableResource(android.R.color.transparent);
                    decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    decision_dialog.setCancelable(false);
                    decision_dialog.setCanceledOnTouchOutside(false);
                    decision_dialog.setContentView(R.layout.offline_decision_dialog);
                    TextView description_textview = decision_dialog
                            .findViewById(R.id.description_textview);
                    Button confirm = (Button) decision_dialog.findViewById(R.id.yes_button);
                    Button cancel = (Button) decision_dialog.findViewById(R.id.no_button);
                    Button connect_button = decision_dialog.findViewById(R.id.connect_button);
                    description_textview.setText(getString(R.string.ordtimecnfr_offline));
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
                            try {
                                decision_dialog.dismiss();
                                customProgressDialog
                                        .show_progress_dialog(OrderTkConfirmActivity.this,
                                                getResources().getString(R.string.loading));
                                App_db.beginTransaction();
                                UUID uniqueKey = UUID.randomUUID();
                                String TKConfirm_sql = "Insert into Orders_TKConfirm (UUID, Aufnr," +
                                        " Vornr, ConfNo, ConfText, ActWork, UnWork, PlanWork," +
                                        " Learr, Bemot, Grund, Leknw, Aueru, Ausor, Loart, Status," +
                                        " Rsnum, PostgDate, Plant, WorkCntr, ExecStartDate, " +
                                        "ExecStartTime, ExecFinDate, ExecFinTime, Pernr) " +
                                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement TKConfirm_statement = App_db
                                        .compileStatement(TKConfirm_sql);
                                TKConfirm_statement.clearBindings();
                                for (OrdrOprtnPrcbl oop : oop_al) {
                                    if (oop.isSelected()) {
                                        TKConfirm_statement.bindString(1, uniqueKey.toString());
                                        TKConfirm_statement.bindString(2, oop.getOrdrId());
                                        TKConfirm_statement.bindString(3, oop.getOprtnId());
                                        TKConfirm_statement.bindString(4, "");
                                        TKConfirm_statement.bindString(5, slctCntrmTxt);
                                        TKConfirm_statement.bindString(6, "0");
                                        TKConfirm_statement.bindString(7, "");
                                        TKConfirm_statement.bindString(8, "0");
                                        TKConfirm_statement.bindString(9, "");
                                        TKConfirm_statement.bindString(10, "");
                                        TKConfirm_statement.bindString(11, "");
                                        if (slctNoRmWrk != null && !slctNoRmWrk.equals("")) {
                                            if (slctNoRmWrk.equals("X")) {
                                                TKConfirm_statement.bindString(12, "X");
                                            } else {
                                                TKConfirm_statement.bindString(12, "");
                                            }
                                        } else {
                                            TKConfirm_statement.bindString(12, "");
                                        }
                                        if (slctFnlCnfrm != null && !slctFnlCnfrm.equals("")) {
                                            if (slctFnlCnfrm.equals("X")) {
                                                TKConfirm_statement.bindString(13, "X");
                                            } else {
                                                TKConfirm_statement.bindString(13, "");
                                            }
                                        } else {
                                            TKConfirm_statement.bindString(13, "");
                                        }
                                        TKConfirm_statement.bindString(14, "");
                                        TKConfirm_statement.bindString(15, "");
                                        TKConfirm_statement.bindString(16, "");
                                        TKConfirm_statement.bindString(17, "");
                                        TKConfirm_statement.bindString(18, "");
                                        TKConfirm_statement.bindString(19, "");
                                        TKConfirm_statement.bindString(20, "");
                                        TKConfirm_statement.bindString(21, slctStDt);
                                        TKConfirm_statement.bindString(22, slctStTm);
                                        TKConfirm_statement.bindString(23, slctEdDt);
                                        TKConfirm_statement.bindString(24, slctEdTm);
                                        TKConfirm_statement.bindString(25, slctEmply);
                                        TKConfirm_statement.execute();
                                    }
                                }
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();

                                ContentValues cv = new ContentValues();
                                cv.put("Xstatus", "CNF");
                                App_db.update("DUE_ORDERS_EtOrderHeader", cv,
                                        "Aufnr=" + ordrId, null);

                                DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                Date todaysdate = new Date();
                                String date = date_format.format(todaysdate.getTime());
                                String time = time_format.format(todaysdate.getTime());

                                String sql11 = "Insert into Alert_Log (DATE, TIME, " +
                                        "DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID," +
                                        " STATUS, UUID, MESSAGE, LOG_UUID)" +
                                        " values(?,?,?,?,?,?,?,?,?,?);";
                                SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                App_db.beginTransaction();
                                statement11.clearBindings();
                                statement11.bindString(1, date);
                                statement11.bindString(2, time);
                                statement11.bindString(3, "Order");
                                statement11.bindString(4, "Time Confirmation");
                                statement11.bindString(5, "");
                                statement11.bindString(6, ordrId);
                                statement11.bindString(7, "Fail");
                                statement11.bindString(8, "");
                                statement11.bindString(9, "");
                                statement11.bindString(10, uniqueKey.toString());
                                statement11.execute();
                                App_db.setTransactionSuccessful();
                                App_db.endTransaction();
                                OrderTkConfirmActivity.this.finish();
                            } catch (Exception e) {
                            } finally {
                                customProgressDialog.dismiss_progress_dialog();
                            }
                        }
                    });
                }
            }
        });
    }
}
