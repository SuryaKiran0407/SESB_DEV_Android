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
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSA_JobSteps_Fragment extends Fragment {

    RecyclerView recyclerView;
    TextView nodata_textview;
    String iwerk = "", equipId = "";
    int add_jobstep = 0;
    private List<JobStep_List_Object> jobsteps_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    boolean Selected_status = false;
    JSA_Add_Activity ma;
    Dialog delete_decision_dialog;

    public JSA_JobSteps_Fragment() {
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
                    description_textview.setText(getString(R.string.jsa_jobstpdel));
                    Button ok_button = (Button) delete_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = delete_decision_dialog.findViewById(R.id.no_button);
                    delete_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList selected_assessmentteam_count = new ArrayList();
                            for (JobStep_List_Object bean : jobsteps_list) {
                                if (bean.getSelected()) {
                                    selected_assessmentteam_count.add(bean.getUuid());
                                }
                            }
                            for (int i = jobsteps_list.size() - 1; i >= 0; i--) {
                                String delete_item_key = jobsteps_list.get(i).getUuid();
                                if (selected_assessmentteam_count.contains(delete_item_key)) {
                                    jobsteps_list.remove(i);
                                }
                            }
                            if (jobsteps_list.size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                nodata_textview.setVisibility(View.GONE);
                                assess_adapter = new Assess_Adapter(getActivity(), jobsteps_list);
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
                    JSA_JobLocation_Fragment jobLocation_fragment =
                            (JSA_JobLocation_Fragment) getFragmentManager()
                                    .findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
                    JSA_JobLocation_Object jobLocation_tabData = jobLocation_fragment.getData();
                    String loc_id = jobLocation_tabData.getLoc_type_id();
                    if (loc_id != null && !loc_id.equals("")) {
                        Intent intent = new Intent(getActivity(), JSA_Add_JobSteps_Activity.class);
                        intent.putExtra("step_no", Integer.toString(jobsteps_list.size() + 1));
                        startActivityForResult(intent, add_jobstep);
                    } else {
                        error_dialog.show_error_dialog(getActivity(),
                                getString(R.string.jsa_locationselct));
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
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(getActivity());
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
            public TextView step_no_textview, job_steps_textview, person_resp_textview;
            public CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                step_no_textview = (TextView) view.findViewById(R.id.step_no_textview);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                person_resp_textview = (TextView) view.findViewById(R.id.person_resp_textview);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
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
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        list_data.get(position).setSelected(true);
                        Selected_status = true;
                        int count = 0;
                        for (JobStep_List_Object alo : list_data) {
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
                        for (JobStep_List_Object alo : list_data) {
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

    public List<JobStep_List_Object> getJobStepsData() {
        return jobsteps_list;
    }
}
