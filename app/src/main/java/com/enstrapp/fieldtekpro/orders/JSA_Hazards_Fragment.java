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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSA_Hazards_Fragment extends Fragment
{


    RecyclerView recyclerView;
    TextView nodata_textview;
    String iwerk = "", equipId = "";
    int add_hazard = 0, selected_hazard = -1;
    private List<Hazards_List_Object> jobsteps_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    boolean Selected_status = false;
    JSA_Add_Activity ma;
    Dialog delete_decision_dialog;


    public JSA_Hazards_Fragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.jsa_assessmentteam_fragment, container, false);

        ma = (JSA_Add_Activity)this.getActivity();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        nodata_textview = (TextView)rootView.findViewById(R.id.nodata_textview);

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
    public void onResume()
    {
        super.onResume();

        if (!getUserVisibleHint())
            return;
        JSA_Add_Activity jsa_add_activity = (JSA_Add_Activity)this. getActivity();
        jsa_add_activity.add_fab.show();
        jsa_add_activity.add_fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(Selected_status)
                {
                    delete_decision_dialog = new Dialog(getActivity());
                    delete_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    delete_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    delete_decision_dialog.setCancelable(false);
                    delete_decision_dialog.setCanceledOnTouchOutside(false);
                    delete_decision_dialog.setContentView(R.layout.decision_dialog);
                    ImageView imageView1 = (ImageView)delete_decision_dialog.findViewById(R.id.imageView1);
                    Glide.with(getActivity()).load(R.drawable.error_dialog_gif).into(imageView1);
                    TextView description_textview = (TextView) delete_decision_dialog.findViewById(R.id.description_textview);
                    description_textview.setText("Do you want to delete selected Hazard ?");
                    Button ok_button = (Button) delete_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = (Button) delete_decision_dialog.findViewById(R.id.no_button);
                    delete_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ArrayList selected_assessmentteam_count = new ArrayList();
                            for (Hazards_List_Object bean : jobsteps_list)
                            {
                                if (bean.getSelected())
                                {
                                    selected_assessmentteam_count.add(bean.getUuid());
                                }
                            }
                            for (int i = jobsteps_list.size() - 1; i >= 0; i--)
                            {
                                String delete_item_key = jobsteps_list.get(i).getUuid();
                                if (selected_assessmentteam_count.contains(delete_item_key))
                                {
                                    jobsteps_list.remove(i);
                                }
                            }
                            if(jobsteps_list.size() > 0)
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                nodata_textview.setVisibility(View.GONE);
                                assess_adapter = new Assess_Adapter(getActivity(), jobsteps_list);
                                recyclerView.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(assess_adapter);
                            }
                            else
                            {
                                recyclerView.setVisibility(View.GONE);
                                nodata_textview.setVisibility(View.VISIBLE);
                            }
                            Selected_status = false;
                            ma.animateFab(false);
                            delete_decision_dialog.dismiss();
                        }
                    });
                    cancel_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            delete_decision_dialog.dismiss();
                        }
                    });
                }
                else
                {
                    JSA_JobSteps_Fragment jobsteps_fragment = (JSA_JobSteps_Fragment)getFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,3));
                    List<JSA_JobSteps_Fragment.JobStep_List_Object> jobsteps_list_objects  = jobsteps_fragment.getJobStepsData();
                    if (jobsteps_list_objects.size() > 0)
                    {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<JSA_JobSteps_Fragment.JobStep_List_Object>>() {}.getType();
                        String json = gson.toJson(jobsteps_list_objects, type);
                        Intent intent = new Intent(getActivity(), JSA_Add_Hazards_Activity.class);
                        intent.putExtra("jobstep_list",json);
                        startActivityForResult(intent,add_hazard);
                    }
                    else
                    {
                        error_dialog.show_error_dialog(getActivity(),"Please Add Atleast One Job Step");
                    }
                }
            }
        });
    }


    private static String makeFragmentName(int viewPagerId, int index)
    {
        return "android:switcher:" + viewPagerId + ":" + index;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == add_hazard)
            {
                String jobstep_id = data.getStringExtra("jobstep_id");
                String jobstep_text = data.getStringExtra("jobstep_text");
                String person_resp = data.getStringExtra("person_resp");
                String hazardcategory_id = data.getStringExtra("hazardcategory_id");
                String hazardcategory_text = data.getStringExtra("hazardcategory_text");
                String hazard_array = data.getStringExtra("hazard_data");
                Gson gson = new Gson();
                Type type = new TypeToken<List<JSA_Hazards_List_Activity.Type_Object>>() {}.getType();
                List<JSA_Hazards_List_Activity.Type_Object> objectList = gson.fromJson(hazard_array, type);
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < objectList.size(); i++)
                {
                    boolean checked_status = objectList.get(i).getSelected_status();
                    if(checked_status)
                    {
                        stringBuilder.append(objectList.get(i).getWork_text());
                        stringBuilder.append(", ");
                    }
                }
                UUID uniqueKey = UUID.randomUUID();
                Hazards_List_Object olo = new Hazards_List_Object(jobstep_id, jobstep_text, hazardcategory_id, hazardcategory_text, stringBuilder.toString(), uniqueKey.toString(), hazard_array, false, person_resp);
                jobsteps_list.add(olo);
                if(jobsteps_list.size() > 0)
                {
                    if(selected_hazard >= 0)
                    {
                        jobsteps_list.remove(selected_hazard);
                    }
                    selected_hazard = -1;
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata_textview.setVisibility(View.GONE);
                    assess_adapter = new Assess_Adapter(getActivity(), jobsteps_list);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(assess_adapter);
                }
                else
                {
                    recyclerView.setVisibility(View.GONE);
                    nodata_textview.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public class Assess_Adapter extends RecyclerView.Adapter<Assess_Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<Hazards_List_Object> list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView job_steps_textview, hazard_categories_textview, hazard_textview;
            public CheckBox checkBox;
            public LinearLayout layout;
            public MyViewHolder(View view)
            {
                super(view);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                hazard_categories_textview = (TextView) view.findViewById(R.id.hazard_categories_textview);
                hazard_textview = (TextView) view.findViewById(R.id.hazard_textview);
                checkBox = (CheckBox)view.findViewById(R.id.checkBox);
                layout = (LinearLayout)view.findViewById(R.id.layout);
            }
        }
        public Assess_Adapter(Context mContext, List<Hazards_List_Object> list)
        {
            this.mContext = mContext;
            this.list_data = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jsa_hazards_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            final Hazards_List_Object olo = list_data.get(position);
            holder.job_steps_textview.setText(olo.getJobstep_id()+" - "+olo.getJobstep_text());
            holder.hazard_categories_textview.setText(olo.getHazardcategory_id()+" - "+olo.getHazardcategory_text());
            holder.hazard_textview.setText(olo.getHazard_data());
            holder.checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (holder.checkBox.isChecked())
                    {
                        list_data.get(position).setSelected(true);
                        Selected_status = true;
                        int count = 0;
                        for(Hazards_List_Object alo : list_data)
                        {
                            if(alo.getSelected())
                            {
                                count = count + 1;
                            }
                        }
                        if (count == 1)
                        {
                            ma.animateFab(true);
                        }
                    }
                    else
                    {
                        int count = 0;
                        list_data.get(position).setSelected(false);
                        for(Hazards_List_Object alo : list_data)
                        {
                            if(alo.getSelected())
                            {
                                count = count + 1;
                            }
                        }
                        if (count == 0)
                        {
                            ma.animateFab(false);
                            Selected_status = false;
                        }
                    }
                }
            });
            holder.layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    selected_hazard = position;
                    Intent intent = new Intent(getActivity(), JSA_Add_Hazards_Activity.class);
                    intent.putExtra("jobstep_list","");
                    intent.putExtra("jobstep_id",olo.getJobstep_id());
                    intent.putExtra("jobstep_text",olo.getJobstep_text());
                    intent.putExtra("hazardcategory_id",olo.getHazardcategory_id());
                    intent.putExtra("hazardcategory_text",olo.getHazardcategory_text());
                    intent.putExtra("hazard_data",olo.getHazard_data());
                    intent.putExtra("hazard_array",olo.getHazard_array());
                    intent.putExtra("Person_resp",olo.getPerson_resp());
                    startActivityForResult(intent,add_hazard);
                }
            });
        }
        @Override
        public int getItemCount()
        {
            return list_data.size();
        }
    }


    public class Hazards_List_Object
    {
        private String jobstep_id;
        private String jobstep_text;
        private String hazardcategory_id;
        private String hazardcategory_text;
        private String hazard_data;
        private String uuid;
        private Boolean isSelected;
        private String hazard_array;
        private String person_resp;

        public String getPerson_resp() {
            return person_resp;
        }

        public void setPerson_resp(String person_resp) {
            this.person_resp = person_resp;
        }

        public String getHazard_array() {
            return hazard_array;
        }

        public void setHazard_array(String hazard_array) {
            this.hazard_array = hazard_array;
        }

        public String getHazard_data() {
            return hazard_data;
        }
        public void setHazard_data(String hazard_data) {
            this.hazard_data = hazard_data;
        }
        public String getJobstep_id() {
            return jobstep_id;
        }

        public void setJobstep_id(String jobstep_id) {
            this.jobstep_id = jobstep_id;
        }

        public String getJobstep_text() {
            return jobstep_text;
        }

        public void setJobstep_text(String jobstep_text) {
            this.jobstep_text = jobstep_text;
        }

        public String getHazardcategory_id() {
            return hazardcategory_id;
        }

        public void setHazardcategory_id(String hazardcategory_id) {
            this.hazardcategory_id = hazardcategory_id;
        }

        public String getHazardcategory_text() {
            return hazardcategory_text;
        }

        public void setHazardcategory_text(String hazardcategory_text) {
            this.hazardcategory_text = hazardcategory_text;
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

        public Hazards_List_Object(String jobstep_id, String jobstep_text, String hazardcategory_id, String hazardcategory_text, String hazard_data, String uuid, String hazard_array, boolean isSelected, String person_resp)
        {
            this.jobstep_id = jobstep_id;
            this.jobstep_text = jobstep_text;
            this.hazardcategory_id = hazardcategory_id;
            this.hazardcategory_text = hazardcategory_text;
            this.hazard_data = hazard_data;
            this.uuid = uuid;
            this.hazard_array = hazard_array;
            this.isSelected = isSelected;
            this.person_resp = person_resp;
        }
    }


    public List<Hazards_List_Object> getHazardListData()
    {
        return jobsteps_list;
    }


}
