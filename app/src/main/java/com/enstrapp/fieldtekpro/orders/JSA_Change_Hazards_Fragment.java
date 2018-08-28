package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
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

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_Hazards_Fragment extends Fragment
{


    RecyclerView recyclerView;
    TextView nodata_textview;
    String rasid = "";
    private List<Hazards_List_Object> jobsteps_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    JSA_Change_Activity ma;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public JSA_Change_Hazards_Fragment()
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

        ma = (JSA_Change_Activity)this.getActivity();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        nodata_textview = (TextView)rootView.findViewById(R.id.nodata_textview);

        recyclerView.setVisibility(View.GONE);
        nodata_textview.setVisibility(View.VISIBLE);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null)
        {
            rasid = bundle.getString("rasid");
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSARisks where Rasid = ?", new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0)
                {
                    if (cursor.moveToFirst())
                    {
                        do
                        {
                            Hazards_List_Object olo = new Hazards_List_Object(cursor.getString(3), cursor.getString(16), cursor.getString(13), cursor.getString(15), cursor.getString(7)+" - "+cursor.getString(14), cursor.getString(5), "", false, cursor.getString(6));
                            jobsteps_list.add(olo);
                        }
                        while (cursor.moveToNext());
                    }
                }
                else
                {
                    cursor.close();
                }
            }
            catch (Exception e)
            {
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
    public void onResume()
    {
        super.onResume();

        if (!getUserVisibleHint())
            return;
        JSA_Change_Activity jsa_change_activity = (JSA_Change_Activity)this. getActivity();
        jsa_change_activity.add_fab.hide();
    }


    public class Assess_Adapter extends RecyclerView.Adapter<Assess_Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<Hazards_List_Object> list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView job_steps_textview, hazard_categories_textview, hazard_textview, risk_id_textview;
            public CheckBox checkBox;
            public LinearLayout layout, riskid_layout;
            public MyViewHolder(View view)
            {
                super(view);
                job_steps_textview = (TextView) view.findViewById(R.id.job_steps_textview);
                hazard_categories_textview = (TextView) view.findViewById(R.id.hazard_categories_textview);
                hazard_textview = (TextView) view.findViewById(R.id.hazard_textview);
                checkBox = (CheckBox)view.findViewById(R.id.checkBox);
                checkBox.setVisibility(View.GONE);
                layout = (LinearLayout)view.findViewById(R.id.layout);
                risk_id_textview = (TextView) view.findViewById(R.id.risk_id_textview);
                riskid_layout = (LinearLayout) view.findViewById(R.id.riskid_layout);
                riskid_layout.setVisibility(View.VISIBLE);
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
            holder.job_steps_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.hazard_categories_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.hazard_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
            holder.risk_id_textview.setText(olo.getUuid());
            holder.risk_id_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
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


}
