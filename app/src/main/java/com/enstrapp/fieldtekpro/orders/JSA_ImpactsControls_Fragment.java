package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSA_ImpactsControls_Fragment extends Fragment {

    RecyclerView recyclerView;
    TextView nodata_textview;
    String iwerk = "", equipId = "";
    int add_impactscontrols = 0;
    private List<ImpactsControls_List_Object> impactscontrols_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    boolean Selected_status = false;
    JSA_Add_Activity ma;
    Dialog delete_decision_dialog;


    public JSA_ImpactsControls_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_assessmentteam_fragment, container, false);

        ma = (JSA_Add_Activity) this.getActivity();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nodata_textview = (TextView) rootView.findViewById(R.id.nodata_textview);

        recyclerView.setVisibility(View.GONE);
        nodata_textview.setVisibility(View.VISIBLE);

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
        JSA_Add_Activity jsa_add_activity = (JSA_Add_Activity) this.getActivity();
        jsa_add_activity.add_fab.show();
        jsa_add_activity.add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Selected_status) {
                    delete_decision_dialog = new Dialog(getActivity());
                    delete_decision_dialog.getWindow()
                            .setBackgroundDrawableResource(android.R.color.transparent);
                    delete_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    delete_decision_dialog.setCancelable(false);
                    delete_decision_dialog.setCanceledOnTouchOutside(false);
                    delete_decision_dialog.setContentView(R.layout.decision_dialog);
                    ImageView imageView1 = delete_decision_dialog.findViewById(R.id.imageView1);
                    Glide.with(getActivity()).load(R.drawable.error_dialog_gif).into(imageView1);
                    TextView description_textview = delete_decision_dialog
                            .findViewById(R.id.description_textview);
                    description_textview.setText(getString(R.string.jsa_impactcntrldel));
                    Button ok_button = (Button) delete_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = delete_decision_dialog.findViewById(R.id.no_button);
                    delete_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList selected_assessmentteam_count = new ArrayList();
                            for (ImpactsControls_List_Object bean : impactscontrols_list) {
                                if (bean.getSelected()) {
                                    selected_assessmentteam_count.add(bean.getUuid());
                                }
                            }
                            for (int i = impactscontrols_list.size() - 1; i >= 0; i--) {
                                String delete_item_key = impactscontrols_list.get(i).getUuid();
                                if (selected_assessmentteam_count.contains(delete_item_key)) {
                                    impactscontrols_list.remove(i);
                                }
                            }
                            if (impactscontrols_list.size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                nodata_textview.setVisibility(View.GONE);
                                assess_adapter = new Assess_Adapter(getActivity(),
                                        impactscontrols_list);
                                recyclerView.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager =
                                        new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(assess_adapter);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                nodata_textview.setVisibility(View.VISIBLE);
                            }
                            Selected_status = false;
                            ma.animateFab(false);
                            delete_decision_dialog.dismiss();
                        }
                    });
                    cancel_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete_decision_dialog.dismiss();
                        }
                    });
                } else {
                    JSA_Hazards_Fragment jobsteps_fragment =
                            (JSA_Hazards_Fragment) getFragmentManager()
                                    .findFragmentByTag(makeFragmentName(R.id.viewpager, 4));
                    List<JSA_Hazards_Fragment.Hazards_List_Object> list_objects =
                            jobsteps_fragment.getHazardListData();
                    if (list_objects.size() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<JSA_Hazards_Fragment.Hazards_List_Object>>() {
                        }.getType();
                        String json = gson.toJson(list_objects, type);
                        Intent intent = new Intent(getActivity(),
                                JSA_Add_ImpactsControls_Activity.class);
                        intent.putExtra("hazards_list", json);
                        startActivityForResult(intent, add_impactscontrols);
                    } else {
                        error_dialog.show_error_dialog(getActivity(),
                                getString(R.string.jsa_jonhzrdatleast));
                    }
                }
            }
        });
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == add_impactscontrols) {
                String hazardtype_id = data.getStringExtra("hazardtype_id");
                String hazardtype_text = data.getStringExtra("hazardtype_text");
                String impact_data = data.getStringExtra("impact_data");
                String controls_data = data.getStringExtra("controls_data");
                Gson gson = new Gson();
                StringBuilder stringBuilder = new StringBuilder();
                if (impact_data != null && !impact_data.equals("")) {
                    Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {
                    }.getType();
                    List<JSA_Impacts_Activity.Type_Object> objectList = gson.fromJson(impact_data, type);
                    for (int i = 0; i < objectList.size(); i++) {
                        boolean checked_status = objectList.get(i).getSelected_status();
                        if (checked_status) {
                            stringBuilder.append(objectList.get(i).getText());
                            stringBuilder.append(", ");
                        }
                    }
                }
                StringBuilder stringBuilder1 = new StringBuilder();
                if (controls_data != null && !controls_data.equals("")) {
                    Type type1 = new TypeToken<List<JSA_Controls_Activity.Type_Object>>() {
                    }.getType();
                    List<JSA_Controls_Activity.Type_Object> objectList1 = gson.fromJson(controls_data, type1);
                    for (int i = 0; i < objectList1.size(); i++) {
                        boolean checked_status = objectList1.get(i).getSelected_status();
                        if (checked_status) {
                            stringBuilder1.append(objectList1.get(i).getText());
                            stringBuilder1.append(", ");
                        }
                    }
                }
                UUID uniqueKey = UUID.randomUUID();
                ImpactsControls_List_Object olo = new ImpactsControls_List_Object(hazardtype_id,
                        hazardtype_text, impact_data, stringBuilder.toString(), controls_data,
                        stringBuilder1.toString(), uniqueKey.toString(), false);
                impactscontrols_list.add(olo);
                if (impactscontrols_list.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata_textview.setVisibility(View.GONE);
                    assess_adapter = new Assess_Adapter(getActivity(), impactscontrols_list);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(assess_adapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    nodata_textview.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class Assess_Adapter extends RecyclerView.Adapter<Assess_Adapter.MyViewHolder> {
        private Context mContext;
        private List<ImpactsControls_List_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView job_steps_textview, hazard_categories_textview, hazard_textview;
            public CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                hazard_categories_textview = view.findViewById(R.id.hazard_categories_textview);
                hazard_textview = (TextView) view.findViewById(R.id.hazard_textview);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            }
        }

        public Assess_Adapter(Context mContext, List<ImpactsControls_List_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jsa_impactscontrols_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final ImpactsControls_List_Object olo = list_data.get(position);
            holder.job_steps_textview.setText(olo.getHazardtype_id() + " - " + olo.getHazardtype_text());
            holder.hazard_categories_textview.setText(olo.getImpact_string());
            holder.hazard_textview.setText(olo.getControls_string());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        list_data.get(position).setSelected(true);
                        Selected_status = true;
                        int count = 0;
                        for (ImpactsControls_List_Object alo : list_data) {
                            if (alo.getSelected()) {
                                count = count + 1;
                            }
                        }
                        if (count == 1) {
                            ma.animateFab(true);
                        }
                    } else {
                        int count = 0;
                        list_data.get(position).setSelected(false);
                        for (ImpactsControls_List_Object alo : list_data) {
                            if (alo.getSelected()) {
                                count = count + 1;
                            }
                        }
                        if (count == 0) {
                            ma.animateFab(false);
                            Selected_status = false;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    public class ImpactsControls_List_Object {
        private String hazardtype_id;
        private String hazardtype_text;
        private String impact_data;
        private String impact_string;
        private String controls_data;
        private String controls_string;
        private String uuid;
        private Boolean isSelected;

        public String getControls_data() {
            return controls_data;
        }

        public void setControls_data(String controls_data) {
            this.controls_data = controls_data;
        }

        public String getControls_string() {
            return controls_string;
        }

        public void setControls_string(String controls_string) {
            this.controls_string = controls_string;
        }

        public String getImpact_data() {
            return impact_data;
        }

        public void setImpact_data(String impact_data) {
            this.impact_data = impact_data;
        }

        public String getImpact_string() {
            return impact_string;
        }

        public void setImpact_string(String impact_string) {
            this.impact_string = impact_string;
        }

        public String getHazardtype_id() {
            return hazardtype_id;
        }

        public void setHazardtype_id(String hazardtype_id) {
            this.hazardtype_id = hazardtype_id;
        }

        public String getHazardtype_text() {
            return hazardtype_text;
        }

        public void setHazardtype_text(String hazardtype_text) {
            this.hazardtype_text = hazardtype_text;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }

        public ImpactsControls_List_Object(String hazardtype_id, String hazardtype_text,
                                           String impact_data, String impact_string,
                                           String controls_data, String controls_string,
                                           String uuid, boolean isSelected) {
            this.hazardtype_id = hazardtype_id;
            this.hazardtype_text = hazardtype_text;
            this.impact_data = impact_data;
            this.impact_string = impact_string;
            this.controls_data = controls_data;
            this.controls_string = controls_string;
            this.uuid = uuid;
            this.isSelected = isSelected;
        }
    }

    public List<ImpactsControls_List_Object> getImpactsControlsData() {
        return impactscontrols_list;
    }
}
