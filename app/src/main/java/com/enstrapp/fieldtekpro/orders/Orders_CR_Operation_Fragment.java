package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
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

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CR_Operation_Fragment extends Fragment {

    TextView add_tv, remove_tv;
    RecyclerView operations_rv;
    OrdrHeaderPrcbl ohp_h = null;
    ArrayList<OrdrOprtnPrcbl> oop_al = new ArrayList<OrdrOprtnPrcbl>();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    boolean isSelected = false;
    String ordrPlant = "", ordrWrkCntr = "", order = "";
    Orders_Create_Activity ma;
    Error_Dialog errorDialog = new Error_Dialog();
    OrdrOprtnPrcbl oop;
    static final int OPRTN_CRT = 10;
    static final int OPRTN_UPDATE = 11;
    int count = 0;
    OperationsAdapter operationsAdapter;
    ArrayList<HashMap<String, String>> selected_operation_custom_info_arraylist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);
        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        add_tv = rootView.findViewById(R.id.add_tv);
        remove_tv = rootView.findViewById(R.id.remove_tv);
        operations_rv = rootView.findViewById(R.id.recyclerView);
        ma = (Orders_Create_Activity) this.getActivity();

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
                } else {
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    if (!(ma.ohp.getOrdrOprtnPrcbls().size() > 0)) {
                        OrdrOprtnPrcbl oop = new OrdrOprtnPrcbl();
                        oop.setSelected(false);
                        oop.setOrdrId("");
                        oop.setOrdrSatus("");
                        oop.setOprtnId("0020");
                        oop.setOprtnShrtTxt(ma.ohp.getOrdrShrtTxt());
                        oop.setOprtnLngTxt(ma.ohp.getOrdrLngTxt());
                        oop.setDuration("0");
                        oop.setDurationUnit("HR");
                        oop.setPlantId(ma.ohp.getPlant());
                        oop.setPlantTxt(ma.ohp.getPlantName());
                        oop.setWrkCntrId(ma.ohp.getWrkCntrId());
                        oop.setWrkCntrTxt(ma.ohp.getWrkCntrName());
                        oop.setCntrlKeyId("");
                        oop.setCntrlKeyTxt("");
                        oop.setAueru("");
                        oop.setUsr01("");
                        oop.setStatus("I");
                        oop_al.add(oop);
                        ma.ohp.setOrdrOprtnPrcbls(oop_al);
                        operationsAdapter = new OperationsAdapter(getActivity(), oop_al);
                        operations_rv.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getActivity());
                        operations_rv.setLayoutManager(layoutManager);
                        operations_rv.setItemAnimator(new DefaultItemAnimator());
                        operations_rv.setAdapter(operationsAdapter);
                    }
                }
            }
        }
        ma.updateTabDataCount();
        ma.fab.show();
        if (isSelected) {
            ma.animateFab(true);
        } else {
            ma.animateFab(false);
        }
        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
                    rmoop.addAll(oop_al);
                    for (OrdrOprtnPrcbl oo : rmoop) {
                        if (oo.isSelected()) {
                            ma.remove_component(oo.getOprtnId());
                            oop_al.remove(oo);

                            /*Written By SuryaKiran for Deleting Custom Info Data based on Operation ID*/
                            String Operation_id = oo.getOprtnId();
                            if (selected_operation_custom_info_arraylist.size() > 0) {
                                for (int i = selected_operation_custom_info_arraylist.size() - 1;
                                     i >= 0; i--) {
                                    String op_id = selected_operation_custom_info_arraylist.get(i)
                                            .get("Operation_id");
                                    if (op_id.equalsIgnoreCase(Operation_id)) {
                                        selected_operation_custom_info_arraylist.remove(i);
                                    }
                                }
                            }
                            /*Written By SuryaKiran for Deleting Custom Info Data based on Operation ID*/
                        }
                    }
                    if (oop_al.size() > 0)
                        for (int i = 0; i < oop_al.size(); i++) {
                            ma.replaceOprtnIds(oop_al.get(i).getOprtnId(),
                                    gnrtOprtnId(i), oop_al.get(i).getOprtnShrtTxt());
                            oop_al.get(i).setOprtnId(gnrtOprtnId(i));
                        }
                    ma.animateFab(false);
                    isSelected = false;
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    operationsAdapter.notifyDataSetChanged();
                    rmoop = null;
                    ma.updateTabDataCount();
                } else {
                    if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
                        if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
                            Intent intent = new Intent(getActivity(),
                                    Operations_Add_Update_Activity.class);
                            intent.putExtra("order_id", ma.ohp.getOrdrId());
                            intent.putExtra("type_oprtn", "C");
                            intent.putExtra("ordrPlant", ma.ohp.getPlant());
                            intent.putExtra("ordrPlantName", ma.ohp.getPlantName());
                            intent.putExtra("ordrWrkCntrName", ma.ohp.getWrkCntrName());
                            intent.putExtra("ordrWrkCntr", ma.ohp.getWrkCntrId());
                            startActivityForResult(intent, OPRTN_CRT);
                        } else {
                            errorDialog.show_error_dialog(getActivity(),
                                    getResources().getString(R.string.equipFunc_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(getActivity(),
                                getResources().getString(R.string.text_mandate));
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

                    /*Written By SuryaKiran for Adding Custom Info Data based on Operation ID*/
                    String Operation_id = gnrtOprtnId(oop_al.size());
                    if (selected_operation_custom_info_arraylist.size() > 0) {
                        for (int i = selected_operation_custom_info_arraylist.size() - 1;
                             i >= 0; i--) {
                            String op_id = selected_operation_custom_info_arraylist.get(i)
                                    .get("Operation_id");
                            if (op_id.equalsIgnoreCase(Operation_id)) {
                                selected_operation_custom_info_arraylist.remove(i);
                            }
                        }
                    }
                    ArrayList<HashMap<String, String>> operation_custom_info_arraylist =
                            (ArrayList<HashMap<String, String>>) data
                                    .getSerializableExtra("selected_operation_custom_info_arraylist");
                    if (operation_custom_info_arraylist.size() > 0) {
                        for (int i = 0; i < operation_custom_info_arraylist.size(); i++) {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Operation_id", Operation_id);
                            custom_info_hashMap.put("Fieldname", operation_custom_info_arraylist.get(i).get("Fieldname"));
                            custom_info_hashMap.put("ZdoctypeItem", operation_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                            custom_info_hashMap.put("Datatype", operation_custom_info_arraylist.get(i).get("Datatype"));
                            custom_info_hashMap.put("Tabname", operation_custom_info_arraylist.get(i).get("Tabname"));
                            custom_info_hashMap.put("Zdoctype", operation_custom_info_arraylist.get(i).get("Zdoctype"));
                            custom_info_hashMap.put("Sequence", operation_custom_info_arraylist.get(i).get("Sequence"));
                            custom_info_hashMap.put("Flabel", operation_custom_info_arraylist.get(i).get("Flabel"));
                            custom_info_hashMap.put("Spras", operation_custom_info_arraylist.get(i).get("Spras"));
                            custom_info_hashMap.put("Length", operation_custom_info_arraylist.get(i).get("Length"));
                            custom_info_hashMap.put("Value", operation_custom_info_arraylist.get(i).get("Value"));
                            selected_operation_custom_info_arraylist.add(custom_info_hashMap);
                        }
                    }
                    /*Written By SuryaKiran for Adding Custom Info Data based on Operation ID*/

                    oop.setSelected(false);
                    oop.setOprtnId(gnrtOprtnId(oop_al.size()));
                    oop.setAueru("");
                    oop_al.add(oop);
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    operationsAdapter.notifyDataSetChanged();
                    ma.updateTabDataCount();
                }
                break;

            case (OPRTN_UPDATE):
                if (resultCode == RESULT_OK) {
                    OrdrOprtnPrcbl oop = data.getParcelableExtra("oop");

                    /*Written By SuryaKiran for Updating Custom Info Data based on Operation ID*/
                    String Operation_id = oop.getOprtnId();
                    if (selected_operation_custom_info_arraylist.size() > 0) {
                        for (int i = selected_operation_custom_info_arraylist.size() - 1; i >= 0; i--) {
                            String op_id = selected_operation_custom_info_arraylist.get(i).get("Operation_id");
                            if (op_id.equalsIgnoreCase(Operation_id)) {
                                selected_operation_custom_info_arraylist.remove(i);
                            }
                        }
                    }
                    ArrayList<HashMap<String, String>> operation_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("selected_operation_custom_info_arraylist");
                    if (operation_custom_info_arraylist.size() > 0) {
                        for (int i = 0; i < operation_custom_info_arraylist.size(); i++) {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Operation_id", Operation_id);
                            custom_info_hashMap.put("Fieldname", operation_custom_info_arraylist.get(i).get("Fieldname"));
                            custom_info_hashMap.put("ZdoctypeItem", operation_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                            custom_info_hashMap.put("Datatype", operation_custom_info_arraylist.get(i).get("Datatype"));
                            custom_info_hashMap.put("Tabname", operation_custom_info_arraylist.get(i).get("Tabname"));
                            custom_info_hashMap.put("Zdoctype", operation_custom_info_arraylist.get(i).get("Zdoctype"));
                            custom_info_hashMap.put("Sequence", operation_custom_info_arraylist.get(i).get("Sequence"));
                            custom_info_hashMap.put("Flabel", operation_custom_info_arraylist.get(i).get("Flabel"));
                            custom_info_hashMap.put("Spras", operation_custom_info_arraylist.get(i).get("Spras"));
                            custom_info_hashMap.put("Length", operation_custom_info_arraylist.get(i).get("Length"));
                            custom_info_hashMap.put("Value", operation_custom_info_arraylist.get(i).get("Value"));
                            selected_operation_custom_info_arraylist.add(custom_info_hashMap);
                        }
                    }
                    /*Written By SuryaKiran for Updating Custom Info Data based on Operation ID*/

                    oop.setSelected(false);
                    ArrayList<OrdrOprtnPrcbl> rmoop = new ArrayList<>();
                    rmoop.addAll(oop_al);
                    for (OrdrOprtnPrcbl oo : rmoop) {
                        if (oo.getOprtnId().equals(oop.getOprtnId())) {
                            oop_al.remove(oo);
                            oop_al.add(oop);
                        }
                    }
                    Collections.sort(oop_al, new Comparator<OrdrOprtnPrcbl>() {
                        public int compare(OrdrOprtnPrcbl oop1, OrdrOprtnPrcbl oop2) {
                            return oop1.oprtnId.compareToIgnoreCase(oop2.oprtnId);
                        }
                    });
                    rmoop = null;
                    ma.ohp.setOrdrOprtnPrcbls(oop_al);
                    operationsAdapter.notifyDataSetChanged();
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
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.orders_operation_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrOprtnPrcbl oop = operationsList.get(position);
            holder.oprtnId_tv.setText(oop.getOprtnId());
            holder.duration_tv.setText(oop.getDuration() + " " + oop.getDurationUnit());
            holder.confirm_iv.setVisibility(View.GONE);
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
                            if (oop.isSelected())
                                count = count + 1;
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
                    startActivity(intent);
                }
            });

            holder.oprtnList_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<HashMap<String, String>> selected_custom_info_arraylist = new ArrayList<HashMap<String, String>>();
                    if (selected_operation_custom_info_arraylist.size() > 0) {
                        String selected_Operation_id = holder.oprtnId_tv.getText().toString();
                        for (int i = 0; i < selected_operation_custom_info_arraylist.size(); i++) {
                            String op_id = selected_operation_custom_info_arraylist.get(i).get("Operation_id");
                            if (op_id.equalsIgnoreCase(selected_Operation_id)) {
                                HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                                custom_info_hashMap.put("Fieldname", selected_operation_custom_info_arraylist.get(i).get("Fieldname"));
                                custom_info_hashMap.put("ZdoctypeItem", selected_operation_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                                custom_info_hashMap.put("Datatype", selected_operation_custom_info_arraylist.get(i).get("Datatype"));
                                custom_info_hashMap.put("Tabname", selected_operation_custom_info_arraylist.get(i).get("Tabname"));
                                custom_info_hashMap.put("Zdoctype", selected_operation_custom_info_arraylist.get(i).get("Zdoctype"));
                                custom_info_hashMap.put("Sequence", selected_operation_custom_info_arraylist.get(i).get("Sequence"));
                                custom_info_hashMap.put("Flabel", selected_operation_custom_info_arraylist.get(i).get("Flabel"));
                                custom_info_hashMap.put("Spras", selected_operation_custom_info_arraylist.get(i).get("Spras"));
                                custom_info_hashMap.put("Length", selected_operation_custom_info_arraylist.get(i).get("Length"));
                                custom_info_hashMap.put("Value", selected_operation_custom_info_arraylist.get(i).get("Value"));
                                selected_custom_info_arraylist.add(custom_info_hashMap);
                            }
                        }
                    }

                    Intent intent = new Intent(getActivity(), Operations_Add_Update_Activity.class);
                    intent.putExtra("order_id", ma.ohp.getOrdrId());
                    intent.putExtra("type_oprtn", "U");
                    intent.putExtra("cnfoprtn", operationsList.get(position));
                    intent.putExtra("selected_operation_custom_info_arraylist", selected_custom_info_arraylist);
                    startActivityForResult(intent, OPRTN_UPDATE);
                }
            });
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

    /*Written By SuryaKiran to Return Custom Info Data*/
    public ArrayList<HashMap<String, String>> getOperationCustominfoData() {
        return selected_operation_custom_info_arraylist;
    }
    /*Written By SuryaKiran to Return Custom Info Data*/

    public int OperationsSize() {
        if (oop_al.size() > 0) {
            return oop_al.size();
        } else {
            return 0;
        }
    }
}
