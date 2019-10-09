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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_ImpactsControls_Fragment extends Fragment {

    RecyclerView recyclerView;
    TextView nodata_textview;
    String rasid = "";
    int add_impactscontrols = 0;
    private List<ImpactsControls_List_Object> impactscontrols_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    boolean Selected_status = false;
    JSA_Change_Activity ma;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public JSA_Change_ImpactsControls_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_assessmentteam_fragment, container, false);

        ma = (JSA_Change_Activity) this.getActivity();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nodata_textview = (TextView) rootView.findViewById(R.id.nodata_textview);

        recyclerView.setVisibility(View.GONE);
        nodata_textview.setVisibility(View.VISIBLE);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            rasid = bundle.getString("rasid");
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSARisks where Rasid =" +
                        " ?", new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            StringBuilder impact_data_StringBuilder = new StringBuilder();
                            StringBuilder control_data_StringBuilder = new StringBuilder();
                            String RiskID = cursor.getString(5);
                            try {
                                Cursor cursor1 = FieldTekPro_db.rawQuery("select * from " +
                                        "EtJSAImp where RiskID = ?", new String[]{RiskID});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            impact_data_StringBuilder.append(cursor1
                                                    .getString(6));
                                            impact_data_StringBuilder.append(", ");
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    cursor1.close();
                                }
                            } catch (Exception e) {
                            }
                            try {
                                Cursor cursor1 = FieldTekPro_db.rawQuery("select * from" +
                                        " EtJSARskCtrl where RiskID = ?", new String[]{RiskID});
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    if (cursor1.moveToFirst()) {
                                        do {
                                            control_data_StringBuilder.append(cursor1
                                                    .getString(22));
                                            control_data_StringBuilder.append(", ");
                                        }
                                        while (cursor1.moveToNext());
                                    }
                                } else {
                                    cursor1.close();
                                }
                            } catch (Exception e) {
                            }
                            if (impact_data_StringBuilder.toString().length() > 0 ||
                                    control_data_StringBuilder.toString().length() > 0) {
                                ImpactsControls_List_Object olo =
                                        new ImpactsControls_List_Object(cursor.getString(7),
                                                cursor.getString(14), "",
                                                impact_data_StringBuilder.toString(),
                                                "", control_data_StringBuilder.toString(),
                                                "", false);
                                impactscontrols_list.add(olo);
                            }
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
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
        JSA_Change_Activity jsa_change_activity = (JSA_Change_Activity) this.getActivity();
        jsa_change_activity.add_fab.hide();
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
                Type type = new TypeToken<List<JSA_Impacts_Activity.Type_Object>>() {
                }.getType();
                List<JSA_Impacts_Activity.Type_Object> objectList = gson.fromJson(impact_data, type);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < objectList.size(); i++) {
                    boolean checked_status = objectList.get(i).getSelected_status();
                    if (checked_status) {
                        stringBuilder.append(objectList.get(i).getText());
                        stringBuilder.append(", ");
                    }
                }
                Type type1 = new TypeToken<List<JSA_Controls_Activity.Type_Object>>() {
                }.getType();
                List<JSA_Controls_Activity.Type_Object> objectList1 = gson.fromJson(controls_data, type1);
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int i = 0; i < objectList1.size(); i++) {
                    boolean checked_status = objectList1.get(i).getSelected_status();
                    if (checked_status) {
                        stringBuilder1.append(objectList1.get(i).getText());
                        stringBuilder1.append(", ");
                    }
                }
                UUID uniqueKey = UUID.randomUUID();
                ImpactsControls_List_Object olo =
                        new ImpactsControls_List_Object(hazardtype_id, hazardtype_text, impact_data,
                                stringBuilder.toString(), controls_data, stringBuilder1.toString(),
                                uniqueKey.toString(), false);
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
            public LinearLayout layout;

            public MyViewHolder(View view) {
                super(view);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                hazard_categories_textview = view.findViewById(R.id.hazard_categories_textview);
                hazard_textview = (TextView) view.findViewById(R.id.hazard_textview);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                checkBox.setVisibility(View.GONE);
                layout = (LinearLayout) view.findViewById(R.id.layout);
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
            holder.job_steps_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.hazard_categories_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.hazard_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
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
