package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CH_Operation_Fragment extends Fragment {

    TextView add_tv, remove_tv;
    RecyclerView operations_rv;
    OrdrHeaderPrcbl ohp_h = null;
    ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
    ArrayList<OrdrOprtnPrcbl> oop_al_rv = new ArrayList<OrdrOprtnPrcbl>();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    boolean isSelected = false;
    String ordrPlant = "", ordrWrkCntr = "", order = "";
    Orders_Change_Activity ma;
    static final int OPRTN_CRT = 10;
    static final int OPRTN_UPDATE = 11;
    static final int OPRTN_CONFRM = 12;
    Error_Dialog errorDialog = new Error_Dialog();
    int count = 0;

    OperationsAdapter operationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        add_tv = rootView.findViewById(R.id.add_tv);
        remove_tv = rootView.findViewById(R.id.remove_tv);
        operations_rv = rootView.findViewById(R.id.recyclerView);
        ma = (Orders_Change_Activity) this.getActivity();

        if (ma.ohp.getOrdrOprtnPrcbls() != null) {
            oop_al.clear();
            oop_al_rv.clear();
            oop_al.addAll(ma.ohp.getOrdrOprtnPrcbls());
            oop_al_rv.addAll(ma.ohp.getOrdrOprtnPrcbls());
            ordrPlant = ma.ohp.getPlant();
            ordrWrkCntr = ma.ohp.getWrkCntrId();
            Collections.sort(oop_al_rv, new Comparator<OrdrOprtnPrcbl>() {
                public int compare(OrdrOprtnPrcbl oop1, OrdrOprtnPrcbl oop2) {
                    return oop1.getOprtnId().compareToIgnoreCase(oop2.getOprtnId());
                }
            });
            operationsAdapter = new OperationsAdapter(getActivity(), oop_al_rv);
            operations_rv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            operations_rv.setLayoutManager(layoutManager);
            operations_rv.setItemAnimator(new DefaultItemAnimator());
            operations_rv.setAdapter(operationsAdapter);
            ma.updateTabDataCount();
        }
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

        if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
            if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
                if (ma.ohp.getOrdrOprtnPrcbls() != null) {
                    operationsAdapter.notifyDataSetChanged();
                }
            }
        }

        String auth_status = Authorizations.Get_Authorizations_Data(getActivity(),"W","U");
        if (auth_status.equalsIgnoreCase("true"))
        {
            ma.fab.show();
        }
        else
        {
            ma.fab.hide();
        }

        if(isSelected){
            ma.animateFab(true);
        } else{
            ma.animateFab(false);
        }
        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
//                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
//                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
                    String optId = "";

                    rmoop.addAll(oop_al_rv);
                    for (OrdrOprtnPrcbl oo : rmoop) {
                        if (oo.isSelected() && oo.getStatus().equals("I")) {
                            oop_al_rv.remove(oo);
                            oop_al.remove(oo);
                        } else if (oo.isSelected()) {
                            oop_al_rv.remove(oo);
                            optId = oo.getOprtnId();
                            for(int i = 0; i < oop_al.size(); i++){
                                if(oop_al.get(i).getOprtnId().equals(optId))
                                    oop_al.get(i).setStatus("D");
                            }
                        }
                    }
                    /*
                    ArrayList<OrdrOprtnPrcbl> addoop = new ArrayList<>();
                    rmoop = new ArrayList<>();
                    rmoop.addAll(oop_al);
                    for (OrdrOprtnPrcbl oo : rmoop) {
                        if (oo.getStatus().equals("I")) {
                            addoop.add(oo);
                            oop_al.remove(oo);
                        }
                    }
                    if (addoop.size() > 0)
                        for (int i = 0; i < addoop.size(); i++) {
                            addoop.get(i).setOprtnId(gnrtOprtnId(oop_al.size()));
                            oop_al.add(addoop.get(i));
                        }
*/
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    ma.animateFab(false);
                    isSelected = false;
                    operationsAdapter.notifyDataSetChanged();
                    ma.updateTabDataCount();
                    rmoop = null;
//                    addoop = null;
                } else {
                    if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
                        if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
                            Intent intent = new Intent(getActivity(), Operations_Add_Update_Activity.class);
                            intent.putExtra("order_id", ma.ohp.getOrdrId());
                            intent.putExtra("type_oprtn", "C");
                            intent.putExtra("ordrPlant", ma.ohp.getPlant());
                            intent.putExtra("ordrPlantName", ma.ohp.getPlantName());
                            intent.putExtra("ordrWrkCntrName", ma.ohp.getWrkCntrName());
                            intent.putExtra("ordrWrkCntr", ma.ohp.getWrkCntrId());
                            startActivityForResult(intent, OPRTN_CRT);
                        } else {
                            errorDialog.show_error_dialog(getActivity(), getResources().getString(R.string.equipFunc_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(getActivity(), getResources().getString(R.string.text_mandate));
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (OPRTN_CRT):
                if (resultCode == RESULT_OK) {
                    OrdrOprtnPrcbl oop = data.getParcelableExtra("oop");
                    oop.setSelected(false);
                    oop.setOrdrId(ma.ohp.getOrdrId());
                    oop.setOprtnId(gnrtOprtnId(oop_al.size()));
                    oop.setAueru("");
                    oop_al.add(oop);
                    oop_al_rv.add(oop);
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    ma.updateTabDataCount();
                }
                break;

            case (OPRTN_UPDATE):
                if (resultCode == RESULT_OK) {
                    OrdrOprtnPrcbl oop = data.getParcelableExtra("oop");
                    oop.setSelected(false);
                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
                    rmoop.addAll(oop_al);
                    for (OrdrOprtnPrcbl oo : rmoop) {
                        if (oo.getOprtnId().equals(oop.getOprtnId())) {
                            oop_al.remove(oo);
                            oop_al_rv.remove(oo);
                            oop_al.add(oop);
                            oop_al_rv.add(oop);
                        }
                    }
                    Collections.sort(oop_al_rv, new Comparator<OrdrOprtnPrcbl>() {
                        public int compare(OrdrOprtnPrcbl oop1, OrdrOprtnPrcbl oop2) {
                            return oop1.getOprtnId().compareToIgnoreCase(oop2.getOprtnId());
                        }
                    });
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    rmoop = null;
                    oop = null;
                    ma.updateTabDataCount();
                }
                break;
        }
    }

    private class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrOprtnPrcbl> operationsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView oprtnId_tv, duration_tv, oprtnShrtTxt_tv;
            ImageView red_iv, yellow_iv, green_iv, confirm_iv;
            CheckBox checkBox;
            LinearLayout oprtnList_ll;

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
            }
        }

        public OperationsAdapter(Context mContext, ArrayList<OrdrOprtnPrcbl> list) {
            this.mContext = mContext;
            this.operationsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_operation_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrOprtnPrcbl oop = operationsList.get(position);
            if (!oop.getStatus().equals("D")) {
                holder.oprtnId_tv.setText(oop.getOprtnId());
                holder.duration_tv.setText(oop.getDuration() + " " + oop.getDurationUnit());
                holder.oprtnShrtTxt_tv.setText(oop.getOprtnShrtTxt());
                holder.checkBox.setChecked(oop.isSelected());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.checkBox.isChecked()) {
                            count = 0;
                            operationsList.get(position).setSelected(true);
                            for (OrdrOprtnPrcbl oop : operationsList) {
                                if (oop.isSelected()) {
                                    count = count + 1;
                                    isSelected = true;
                                }
                            }
                            if (count == 1)
                                ma.animateFab(true);
                        } else {
                            count = 0;
                            operationsList.get(position).setSelected(false);
                            for (OrdrOprtnPrcbl oop : operationsList) {
                                if (oop.isSelected()) {
                                    count = count + 1;
                                }
                            }
                            if (count == 0) {
                                ma.animateFab(false);
                                isSelected = false;
                            }
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
                        Intent intent = new Intent(getActivity(), Orders_Confirm_Activity.class);
                        intent.putExtra("cnfoprtn", operationsList.get(position));
                        intent.putExtra("strDt", ma.ohp.getBasicStart());
                        intent.putExtra("endDt", ma.ohp.getBasicEnd());
                        startActivityForResult(intent, OPRTN_CONFRM);
                    }
                });

                holder.oprtnList_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<HashMap<String, String>> selected_operation_custom_info_arraylist = new ArrayList<>();
                        try
                        {
                            Cursor cursor = App_db.rawQuery("select * from DUE_ORDERS_EtOrderOperations_FIELDS where Aufnr = ? and OperationID = ? order by Sequence", new String[]{ma.ohp.getOrdrId(),holder.oprtnId_tv.getText().toString()});
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
                                        selected_operation_custom_info_arraylist.add(custom_info_hashMap);
                                    }
                                    while (cursor.moveToNext());
                                }
                            }
                            else
                            {
                                cursor.close();

                                Cursor cursor1 = App_db.rawQuery("select * from GET_CUSTOM_FIELDS where Zdoctype = ? and ZdoctypeItem = ? order by Sequence", new String[]{"W","WO"});
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
                                            selected_operation_custom_info_arraylist.add(custom_info_hashMap);
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

                        Intent intent = new Intent(getActivity(), Operations_Add_Update_Activity.class);
                        intent.putExtra("order_id", ma.ohp.getOrdrId());
                        intent.putExtra("type_oprtn", "U");
                        intent.putExtra("cnfoprtn", operationsList.get(position));
                        intent.putExtra("selected_operation_custom_info_arraylist", selected_operation_custom_info_arraylist);
                        startActivityForResult(intent, OPRTN_UPDATE);


                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return operationsList.size();
        }
    }


    private String gnrtOprtnId(int size) {
        if (size < 9) {
            return "00" + String.valueOf(size + 1) + "0";
        } else {
            return "0" + String.valueOf(size + 1) + "0";
        }
    }


    public int OperationsSize()
    {
        if (oop_al_rv.size() > 0)
        {
            return  oop_al_rv.size();
        }
        else
        {
            return  0;
        }
    }


}
