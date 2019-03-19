package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class Orders_CR_Material_Fragment extends Fragment {
    OrdrHeaderPrcbl ohp_h = null;
    ArrayList<OrdrMatrlPrcbl> omp_al = new ArrayList<OrdrMatrlPrcbl>();
    String ordrPlant = "", order = "";
    MaterialAdapter materialAdapter;
    RecyclerView material_rv;
    Orders_Create_Activity ma;
    Error_Dialog errorDialog = new Error_Dialog();
    OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
    boolean isSelected = false;

    static final int MATRL_CRT = 30;
    static final int MATRL_UPDT = 31;
    int count = 0;

    ArrayList<HashMap<String, String>> selected_material_custom_info_arraylist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);
        omp_al.clear();
        material_rv = rootView.findViewById(R.id.recyclerView);
        ma = (Orders_Create_Activity) this.getActivity();
        materialAdapter = new MaterialAdapter(getActivity(), omp_al);
        material_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        material_rv.setLayoutManager(layoutManager);
        material_rv.setItemAnimator(new DefaultItemAnimator());
        material_rv.setAdapter(materialAdapter);

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

        if (ma.ohp.getOrdrMatrlPrcbls() != null) {
            if (omp_al.size() > 0) {
                materialAdapter = new MaterialAdapter(getActivity(), omp_al);
                material_rv.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                material_rv.setLayoutManager(layoutManager);
                material_rv.setItemAnimator(new DefaultItemAnimator());
                material_rv.setAdapter(materialAdapter);
            }
        }

        if (ma.ohp.getOrdrTypId() != null)
            if (ma.ohp.getOrdrTypId().equals("PM08")) {
                ma.fab.hide();
            } else {
                ma.fab.show();
                if (isSelected) {
                    ma.animateFab(true);
                } else {
                    ma.animateFab(false);
                }
            }
        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    ArrayList<OrdrMatrlPrcbl> rmomp = new ArrayList<>();
                    rmomp.addAll(omp_al);
                    for (OrdrMatrlPrcbl oo : rmomp) {
                        if (oo.isSelected())
                            omp_al.remove(oo);
                    }
                    if (omp_al.size() > 0)
                        for (int i = 0; i < omp_al.size(); i++) {
                            omp_al.get(i).setPartId(gnrtMatrlId(i));
                        }
                    ma.animateFab(false);
                    isSelected = false;
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    materialAdapter.notifyDataSetChanged();
                    rmomp = null;
                } else {
                    if (ma.ohp.getOrdrTypId() != null && !ma.ohp.getOrdrTypId().equals("")) {
                        if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
                            if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
                                ma.addOprtn();
                                Intent intent = new Intent(getActivity(), Material_Add_Update_Activity.class);
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

                    /*Written By SuryaKiran for Updating Custom Info Data*/
                    String Operation_id = omp_al_d.get(0).getOprtnId();
                    String part_id = gnrtMatrlId(omp_al.size());
                    Log.v("kiran_part_id", part_id + "....");
                    if (selected_material_custom_info_arraylist.size() > 0) {
                        for (int i = selected_material_custom_info_arraylist.size() - 1; i >= 0; i--) {
                            String op_id = selected_material_custom_info_arraylist.get(i).get("Operation_id");
                            String pt_id = selected_material_custom_info_arraylist.get(i).get("Part_id");
                            if (pt_id.equalsIgnoreCase(part_id)) {
                                selected_material_custom_info_arraylist.remove(i);
                            }
                        }
                    }
                    ArrayList<HashMap<String, String>> operation_custom_info_arraylist =
                            (ArrayList<HashMap<String, String>>) data
                                    .getSerializableExtra("selected_material_custom_info_arraylist");
                    if (operation_custom_info_arraylist.size() > 0) {
                        for (int i = 0; i < operation_custom_info_arraylist.size(); i++) {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Operation_id", Operation_id);
                            custom_info_hashMap.put("Part_id", part_id);
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
                            selected_material_custom_info_arraylist.add(custom_info_hashMap);
                        }
                    }
                    /*Written By SuryaKiran for Updating Custom Info Data*/

                    for (int i = 0; i < omp_al_d.size(); i++) {
                        OrdrMatrlPrcbl omp = new OrdrMatrlPrcbl();
                        omp = omp_al_d.get(i);
                        omp.setPartId(gnrtMatrlId(omp_al.size()));
                        omp_al.add(omp);
                    }
                    omp_al_d = null;
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    materialAdapter.notifyDataSetChanged();
                }
                break;

            case (MATRL_UPDT):
                if (resultCode == RESULT_OK) {
                    OrdrMatrlPrcbl omp_d = new OrdrMatrlPrcbl();
                    omp_d = data.getParcelableExtra("omp_prcbl");

                    /*Written By SuryaKiran for Updating Custom Info Data*/
                    String Operation_id = omp_d.getOprtnId();
                    String part_id = omp_d.getPartId();
                    if (selected_material_custom_info_arraylist.size() > 0) {
                        for (int i = selected_material_custom_info_arraylist.size() - 1; i >= 0; i--) {
                            String op_id = selected_material_custom_info_arraylist.get(i).get("Operation_id");
                            String pt_id = selected_material_custom_info_arraylist.get(i).get("Part_id");
                              if (pt_id.equalsIgnoreCase(part_id)) {
                                selected_material_custom_info_arraylist.remove(i);
                            }
                        }
                    }
                    ArrayList<HashMap<String, String>> operation_custom_info_arraylist =
                            (ArrayList<HashMap<String, String>>) data
                                    .getSerializableExtra("selected_material_custom_info_arraylist");
                    if (operation_custom_info_arraylist.size() > 0) {
                        for (int i = 0; i < operation_custom_info_arraylist.size(); i++) {
                            HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                            custom_info_hashMap.put("Operation_id", Operation_id);
                            custom_info_hashMap.put("Part_id", part_id);
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
                            selected_material_custom_info_arraylist.add(custom_info_hashMap);
                        }
                    }
                    /*Written By SuryaKiran for Updating Custom Info Data*/

                    ArrayList<OrdrMatrlPrcbl> omp_al_d = new ArrayList<>();
                    omp_al_d.addAll(omp_al);
                    for (OrdrMatrlPrcbl omp : omp_al_d) {
                        if (omp.getPartId().equals(omp_d.getPartId()))
                        {
                            omp_al.remove(omp);
                        }

                    }
                    omp_al.add(omp_d);
                    ma.ohp.setOrdrMatrlPrcbls(omp_al);
                    omp_d = null;
                    omp_al_d = null;
                    materialAdapter.notifyDataSetChanged();
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

    /*Written By SuryaKiran to Return Custom Info Data*/
    public ArrayList<HashMap<String, String>> getMaterialCustominfoData() {
        return selected_material_custom_info_arraylist;
    }
    /*Written By SuryaKiran to Return Custom Info Data*/
}
