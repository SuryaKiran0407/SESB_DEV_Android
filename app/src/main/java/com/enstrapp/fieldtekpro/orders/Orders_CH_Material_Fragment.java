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
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CH_Material_Fragment extends Fragment {
    OrdrHeaderPrcbl ohp_h = null;
    ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
    ArrayList<OrdrMatrlPrcbl> omp_al_rv = new ArrayList<OrdrMatrlPrcbl>();
    String ordrPlant = "", order = "";
    MaterialAdapter materialAdapter;
    RecyclerView material_rv;
    boolean isSelected = false;
    static final int MATRL_CRT = 30;
    static final int MATRL_UPDT = 31;
    Error_Dialog errorDialog = new Error_Dialog();
    Orders_Change_Activity ma;
    int count = 0;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);

        material_rv = rootView.findViewById(R.id.recyclerView);

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        ma = (Orders_Change_Activity) this.getActivity();

        omp_al.clear();
        omp_al_rv.clear();
        if (ma.ohp.getOrdrMatrlPrcbls() != null) {
            ordrPlant = ma.ohp.getPlant();
            omp_al.addAll(ma.ohp.getOrdrMatrlPrcbls());
            omp_al_rv.addAll(ma.ohp.getOrdrMatrlPrcbls());
            if (omp_al.size() > 0) {
                materialAdapter = new MaterialAdapter(getActivity(), omp_al_rv);
                material_rv.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                material_rv.setLayoutManager(layoutManager);
                material_rv.setItemAnimator(new DefaultItemAnimator());
                material_rv.setAdapter(materialAdapter);
                ma.updateTabDataCount();
            }
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

        String auth_status = Authorizations.Get_Authorizations_Data(getActivity(), "W",
                "U");
        if (auth_status.equalsIgnoreCase("true")) {
            ma.fab.show();
        } else {
            ma.fab.hide();
        }

        if (isSelected) {
            ma.animateFab(true);
        } else {
            ma.animateFab(false);
        }
        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    ArrayList<OrdrMatrlPrcbl> rmomp = new ArrayList<>();
                    rmomp.addAll(omp_al_rv);
                    String partId = "";

                    for (OrdrMatrlPrcbl oo : rmomp) {
                        if (oo.isSelected() && oo.getStatus().equals("I")) {
                            omp_al_rv.remove(oo);
                            omp_al.remove(oo);
                        } else if (oo.isSelected()) {
                            omp_al_rv.remove(oo);
                            partId = oo.getPartId();
                            for (int i = 0; i < omp_al.size(); i++) {
                                if (omp_al.get(i).getPartId().equals(partId))
                                    omp_al.get(i).setStatus("D");
                            }
                        }
                    }
                    ma.animateFab(false);
                    isSelected = false;
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    materialAdapter.notifyDataSetChanged();
                    ma.updateTabDataCount();
                    rmomp = null;
                } else {
                    if (ma.ohp.getOrdrTypId() != null && !ma.ohp.getOrdrTypId().equals("")) {
                        if (!ma.ohp.getOrdrTypId().equals("PM08")) {
                            if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
                                if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
//                                    ma.addOprtn();
                                    Intent intent = new Intent(getActivity(),
                                            Material_Add_Update_Activity.class);
                                    intent.putExtra("type_matrl", "C");
                                    intent.putExtra("ordrEquip", ma.ohp.getEquipNum());
                                    intent.putExtra("ordrPlant", ma.ohp.getPlant());
                                    intent.putExtra("iwerk", ma.ohp.getIwerk());
                                    intent.putExtra("oprtn_list", ma.ohp.getOrdrOprtnPrcbls());
                                    startActivityForResult(intent, MATRL_CRT);
                                } else {
                                    errorDialog.show_error_dialog(getActivity(),
                                            getResources().getString(R.string.equipFunc_mandate));
                                }
                            } else {
                                errorDialog.show_error_dialog(getActivity(),
                                        getResources().getString(R.string.text_mandate));
                            }
                        } else {
                            errorDialog.show_error_dialog(getActivity(),
                                    getResources().getString(R.string.material_block));
                        }
                    } else {
                        errorDialog.show_error_dialog(getActivity(),
                                getResources().getString(R.string.ordTyp_mandate));
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (MATRL_CRT):
                if (resultCode == RESULT_OK) {
                    ArrayList<OrdrMatrlPrcbl> omp_al_d = new ArrayList<OrdrMatrlPrcbl>();
                    omp_al_d = data.getParcelableArrayListExtra("omp_prcbl_al");
                    for (int i = 0; i < omp_al_d.size(); i++) {
                        OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                        omp = omp_al_d.get(i);
                        omp.setPartId(gnrtMatrlId(omp_al.size()));
                        omp_al.add(omp);
                        omp_al_rv.add(omp);
                    }
                    omp_al_d = null;
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    if (materialAdapter != null)
                        materialAdapter.notifyDataSetChanged();
                    else {
                        materialAdapter = new MaterialAdapter(getActivity(), omp_al_rv);
                        material_rv.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        material_rv.setLayoutManager(layoutManager);
                        material_rv.setItemAnimator(new DefaultItemAnimator());
                        material_rv.setAdapter(materialAdapter);
                    }
                    ma.updateTabDataCount();
                }
                break;

            case (MATRL_UPDT):
                if (resultCode == RESULT_OK) {
                    OrdrMatrlPrcbl omp_d = data.getParcelableExtra("omp_prcbl");
                    omp_d.setSelected(false);
                    ArrayList<OrdrMatrlPrcbl> rmomp = new ArrayList<>();
                    rmomp.addAll(omp_al);
                    for (OrdrMatrlPrcbl omp : rmomp) {
                        if (omp.getPartId().equals(omp_d.getPartId())) {
                            omp_al.remove(omp);
                            omp_al_rv.remove(omp);
                            omp_al.add(omp_d);
                            omp_al_rv.add(omp_d);
                        }
                    }
                    Collections.sort(omp_al_rv, new Comparator<OrdrMatrlPrcbl>() {
                        public int compare(OrdrMatrlPrcbl omp1, OrdrMatrlPrcbl omp2) {
                            return omp1.getPartId().compareToIgnoreCase(omp2.getPartId());
                        }
                    });
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    omp_d = null;
                    rmomp = null;
                    materialAdapter.notifyDataSetChanged();
                    ma.updateTabDataCount();
                }
                break;
        }
    }

    private class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrMatrlPrcbl> materialList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView oprtnId_tv, partId_tv, compntTxt_tv, quantity_tv;
            ImageView detailed_iv;
            CheckBox checkBox;
            LinearLayout matrlList_ll;

            public MyViewHolder(View view) {
                super(view);
                checkBox = view.findViewById(R.id.checkBox);
                oprtnId_tv = view.findViewById(R.id.oprtnId_tv);
                partId_tv = view.findViewById(R.id.partId_tv);
                compntTxt_tv = view.findViewById(R.id.compntTxt_tv);
                quantity_tv = view.findViewById(R.id.quantity_tv);
                detailed_iv = view.findViewById(R.id.detailed_iv);
                matrlList_ll = view.findViewById(R.id.matrlList_ll);
            }
        }

        public MaterialAdapter(Context mContext, ArrayList<OrdrMatrlPrcbl> list) {
            this.mContext = mContext;
            this.materialList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.orders_material_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrMatrlPrcbl omp = materialList.get(position);
            holder.oprtnId_tv.setText(omp.getOprtnId());
            holder.partId_tv.setText(omp.getPartId());
            holder.compntTxt_tv.setText(omp.getMatrlTxt());
            holder.quantity_tv.setText(omp.getQuantity());
            holder.checkBox.setChecked(omp.isSelected());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        count = 0;
                        materialList.get(position).setSelected(true);
                        for (OrdrMatrlPrcbl omp : materialList) {
                            if (omp.isSelected()) {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            ma.animateFab(true);
                    } else {
                        count = 0;
                        materialList.get(position).setSelected(false);
                        for (OrdrMatrlPrcbl omp : materialList) {
                            if (omp.isSelected())
                                count = count + 1;
                        }
                        if (count == 0) {
                            ma.animateFab(false);
                            isSelected = false;
                        }
                    }
                }
            });

            holder.matrlList_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<HashMap<String, String>> selected_material_custom_info_arraylist
                            = new ArrayList<>();
                    try {
                        Cursor cursor = App_db.rawQuery("select * from " +
                                        "DUE_ORDERS_EtOrderComponents_FIELDS where Aufnr = ? and " +
                                        "OperationID = ? and PartID = ? order by Sequence",
                                new String[]{ma.ohp.getOrdrId(),
                                        holder.oprtnId_tv.getText().toString(),
                                        holder.partId_tv.getText().toString()});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
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
                                    if (datatype.equalsIgnoreCase("DATS")) {
                                        if (value.equalsIgnoreCase("00000000")) {
                                            custom_info_hashMap.put("Value", "");
                                        } else {
                                            String inputPattern = "yyyyMMdd";
                                            String outputPattern = "MMM dd, yyyy";
                                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                            try {
                                                Date date = inputFormat.parse(value);
                                                String formatted_date = outputFormat.format(date);
                                                custom_info_hashMap.put("Value", formatted_date);
                                            } catch (Exception e) {
                                                custom_info_hashMap.put("Value", "");
                                            }
                                        }

                                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                                        if (value.equalsIgnoreCase("000000")) {
                                            custom_info_hashMap.put("Value", "");
                                        } else {
                                            String inputPattern = "HHmmss";
                                            String outputPattern = "HH:mm:ss";
                                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                            try {
                                                Date date = inputFormat.parse(value);
                                                String formatted_time = outputFormat.format(date);
                                                custom_info_hashMap.put("Value", formatted_time);
                                            } catch (Exception e) {
                                                custom_info_hashMap.put("Value", "");
                                            }
                                        }
                                    } else {
                                        custom_info_hashMap.put("Value", cursor.getString(7));
                                    }

                                    custom_info_hashMap.put("Spras", "");
                                    selected_material_custom_info_arraylist.add(custom_info_hashMap);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                            Cursor cursor1 = App_db.rawQuery("select * from GET_CUSTOM_FIELDS" +
                                            " where Zdoctype = ? and ZdoctypeItem = ? order by Sequence",
                                    new String[]{"W", "WC"});
                            if (cursor1 != null && cursor1.getCount() > 0) {
                                if (cursor1.moveToFirst()) {
                                    do {
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
                                        selected_material_custom_info_arraylist.add(custom_info_hashMap);
                                    }
                                    while (cursor1.moveToNext());
                                }
                            } else {
                                cursor1.close();
                            }
                        }
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(getActivity(), Material_Add_Update_Activity.class);
                    intent.putExtra("type_matrl", "U");
                    intent.putExtra("cnfmatrl", materialList.get(position));
                    intent.putExtra("oprtn_list", ma.ohp.getOrdrOprtnPrcbls());
                    intent.putExtra("ordrEquip", ma.ohp.getEquipNum());
                    intent.putExtra("iwerk", ma.ohp.getIwerk());
                    intent.putExtra("selected_material_custom_info_arraylist",
                            selected_material_custom_info_arraylist);
                    startActivityForResult(intent, MATRL_UPDT);
                }
            });
        }

        @Override
        public int getItemCount() {
            return materialList.size();
        }
    }

    private String gnrtMatrlId(int size) {
        if (size < 9) {
            return "00" + String.valueOf(size + 1) + "0";
        } else {
            return "0" + String.valueOf(size + 1) + "0";
        }
    }

    public int MaterialSize() {
        if (omp_al_rv.size() > 0) {
            return omp_al_rv.size();
        } else {
            return 0;
        }
    }
    public void remove_component(String operation_id)
    {
        if(omp_al_rv.size() > 0)
        {
            ArrayList<OrdrMatrlPrcbl> rmomp = new ArrayList<>();
            rmomp.addAll(omp_al_rv);

            for (OrdrMatrlPrcbl oo : rmomp)
            {
                if (oo.getOprtnId().equalsIgnoreCase(operation_id))
                {
                    if (oo.getStatus().equals("I"))
                    {
                        omp_al_rv.remove(oo);
                        omp_al.remove(oo);
                    }
                    else
                    {
                        omp_al_rv.remove(oo);
                        String partId = oo.getPartId();
                        for(int i = 0; i < omp_al.size(); i++)
                        {
                            if(omp_al.get(i).getPartId().equals(partId))
                                omp_al.get(i).setStatus("D");
                        }
                    }
                }
            }
            materialAdapter.notifyDataSetChanged();
        }
    }
}
