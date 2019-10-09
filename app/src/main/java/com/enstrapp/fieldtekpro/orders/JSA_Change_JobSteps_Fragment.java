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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_JobSteps_Fragment extends Fragment {

    RecyclerView recyclerView;
    TextView nodata_textview;
    String rasid = "";
    int add_jobstep = 0;
    private List<JobStep_List_Object> jobsteps_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    JSA_Change_Activity ma;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public JSA_Change_JobSteps_Fragment() {
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
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSARisks where Rasid" +
                        " = ?", new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            JobStep_List_Object olo =
                                    new JobStep_List_Object(cursor.getString(3),
                                            cursor.getString(16),
                                            cursor.getString(6),
                                            cursor.getString(5),
                                            false);
                            jobsteps_list.add(olo);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            if (jobsteps_list.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                nodata_textview.setVisibility(View.GONE);
                assess_adapter = new Assess_Adapter(getActivity(), jobsteps_list);
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
            if (requestCode == add_jobstep) {
                String step_no = data.getStringExtra("step_no");
                String jobstep_text = data.getStringExtra("jobstep_text");
                String person_responsible_text = data.getStringExtra("person_responsible_text");
                UUID uniqueKey = UUID.randomUUID();
                JobStep_List_Object olo = new JobStep_List_Object(step_no, jobstep_text,
                        person_responsible_text, uniqueKey.toString(), false);
                jobsteps_list.add(olo);
                if (jobsteps_list.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata_textview.setVisibility(View.GONE);
                    assess_adapter = new Assess_Adapter(getActivity(), jobsteps_list);
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
        private List<JobStep_List_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView step_no_textview, job_steps_textview, person_resp_textview, risk_id_textview;
            public CheckBox checkBox;
            public LinearLayout layout, riskid_layout;

            public MyViewHolder(View view) {
                super(view);
                step_no_textview = (TextView) view.findViewById(R.id.step_no_textview);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                person_resp_textview = (TextView) view.findViewById(R.id.person_resp_textview);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                checkBox.setVisibility(View.GONE);
                layout = (LinearLayout) view.findViewById(R.id.layout);
                risk_id_textview = (TextView) view.findViewById(R.id.risk_id_textview);
                riskid_layout = (LinearLayout) view.findViewById(R.id.riskid_layout);
                riskid_layout.setVisibility(View.VISIBLE);
            }
        }

        public Assess_Adapter(Context mContext, List<JobStep_List_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.jsa_jobsteps_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final JobStep_List_Object olo = list_data.get(position);
            holder.step_no_textview.setText(olo.getStep_no());
            holder.job_steps_textview.setText(olo.getJobstep_text());
            holder.person_resp_textview.setText(olo.getPerson_responsible());
            holder.risk_id_textview.setText(olo.getUuid());
            holder.step_no_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.job_steps_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.person_resp_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.risk_id_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    public class JobStep_List_Object {
        private String step_no;
        private String jobstep_text;
        private String person_responsible;
        private String uuid;
        private Boolean isSelected;

        public String getStep_no() {
            return step_no;
        }

        public void setStep_no(String step_no) {
            this.step_no = step_no;
        }

        public String getJobstep_text() {
            return jobstep_text;
        }

        public void setJobstep_text(String jobstep_text) {
            this.jobstep_text = jobstep_text;
        }

        public String getPerson_responsible() {
            return person_responsible;
        }

        public void setPerson_responsible(String person_responsible) {
            this.person_responsible = person_responsible;
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

        public JobStep_List_Object(String step_no, String jobstep_text, String person_responsible,
                                   String uuid, boolean isSelected) {
            this.step_no = step_no;
            this.jobstep_text = jobstep_text;
            this.person_responsible = person_responsible;
            this.uuid = uuid;
            this.isSelected = isSelected;
        }
    }
}
