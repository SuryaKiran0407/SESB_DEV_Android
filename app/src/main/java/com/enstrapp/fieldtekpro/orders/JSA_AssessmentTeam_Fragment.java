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

public class JSA_AssessmentTeam_Fragment extends Fragment
{


    RecyclerView recyclerView;
    TextView nodata_textview;
    String iwerk = "", equipId = "";
    int add_asstm = 0;
    private List<AssessTeam_List_Object> assessteam_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    boolean Selected_status = false;
    JSA_Add_Activity ma;
    Dialog delete_decision_dialog;


    public JSA_AssessmentTeam_Fragment()
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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null)
        {
            iwerk = bundle.getString("iwerk");
            equipId = bundle.getString("equipId");
        }

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
                    description_textview.setText("Do you want to delete selected Assessment Team ?");
                    Button ok_button = (Button) delete_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = (Button) delete_decision_dialog.findViewById(R.id.no_button);
                    delete_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ArrayList selected_assessmentteam_count = new ArrayList();
                            for (AssessTeam_List_Object bean : assessteam_list)
                            {
                                if (bean.isSelected())
                                {
                                    selected_assessmentteam_count.add(bean.getUuid());
                                }
                            }
                            for (int i = assessteam_list.size() - 1; i >= 0; i--)
                            {
                                String delete_item_key = assessteam_list.get(i).getUuid();
                                if (selected_assessmentteam_count.contains(delete_item_key))
                                {
                                    assessteam_list.remove(i);
                                }
                            }
                            if(assessteam_list.size() > 0)
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                nodata_textview.setVisibility(View.GONE);
                                assess_adapter = new Assess_Adapter(getActivity(), assessteam_list);
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
                    JSA_BasicInfo_Fragment basicinfo_tab = (JSA_BasicInfo_Fragment)getFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,0));
                    JSA_BasicInfo_Object basicinfo_tabData = basicinfo_tab.getData();
                    String title = basicinfo_tabData.getTitle();
                    String job_id = basicinfo_tabData.getJob_id();
                    if (title != null && !title.equals(""))
                    {
                        if (job_id != null && !job_id.equals(""))
                        {
                            Intent intent = new Intent(getActivity(),JSA_Add_AssessmentTeam_Activity.class);
                            intent.putExtra("equipId",equipId);
                            startActivityForResult(intent,add_asstm);
                        }
                        else
                        {
                            error_dialog.show_error_dialog(getActivity(),"Please Select Job");
                        }
                    }
                    else
                    {
                        error_dialog.show_error_dialog(getActivity(),"Please Select Title");
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
            if(requestCode == add_asstm)
            {
                String name_id = data.getStringExtra("name_id");
                String name_text = data.getStringExtra("name_text");
                String role_id = data.getStringExtra("role_id");
                String role_text = data.getStringExtra("role_text");
                UUID uniqueKey = UUID.randomUUID();
                AssessTeam_List_Object olo = new AssessTeam_List_Object(name_id, name_text, role_id, role_text, false, uniqueKey.toString());
                assessteam_list.add(olo);
                if(assessteam_list.size() > 0)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata_textview.setVisibility(View.GONE);
                    assess_adapter = new Assess_Adapter(getActivity(), assessteam_list);
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
        private List<AssessTeam_List_Object> list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView name_textview, role_textview;
            public CheckBox checkBox;
            public MyViewHolder(View view)
            {
                super(view);
                name_textview = (TextView) view.findViewById(R.id.name_textview);
                role_textview = (TextView) view.findViewById(R.id.role_textview);
                checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            }
        }
        public Assess_Adapter(Context mContext, List<AssessTeam_List_Object> list)
        {
            this.mContext = mContext;
            this.list_data = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jsa_assessmentteam_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            final AssessTeam_List_Object olo = list_data.get(position);
            holder.name_textview.setText(olo.getName_id());
            holder.role_textview.setText(olo.getRole_text());
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
                        for(AssessTeam_List_Object alo : list_data)
                        {
                            if(alo.isSelected())
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
                        for(AssessTeam_List_Object alo : list_data)
                        {
                            if(alo.isSelected())
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
        }
        @Override
        public int getItemCount()
        {
            return list_data.size();
        }
    }


    public class AssessTeam_List_Object
    {
        private String name_id;
        private String name_text;
        private String role_id;
        private String role_text;
        public boolean selected;
        private String uuid;
        public String getUuid() {
            return uuid;
        }
        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
        public boolean isSelected() {
            return selected;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }
        public String getName_id() {
            return name_id;
        }
        public void setName_id(String name_id) {
            this.name_id = name_id;
        }
        public String getName_text() {
            return name_text;
        }
        public void setName_text(String name_text) {
            this.name_text = name_text;
        }
        public String getRole_id() {
            return role_id;
        }
        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }
        public String getRole_text() {
            return role_text;
        }
        public void setRole_text(String role_text) {
            this.role_text = role_text;
        }
        public AssessTeam_List_Object(String name_id, String name_text, String role_id, String role_text, boolean selected, String uuid)
        {
            this.name_id = name_id;
            this.name_text = name_text;
            this.role_id = role_id;
            this.role_text = role_text;
            this.selected = selected;
            this.uuid = uuid;
        }
    }


    public List<AssessTeam_List_Object> getAssessmentTeamData()
    {
        return assessteam_list;
    }


}
