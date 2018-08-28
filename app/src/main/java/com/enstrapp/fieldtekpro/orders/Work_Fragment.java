package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.List;

public class Work_Fragment extends Fragment {

    WorkRequirements_Activity ma;
    RecyclerView recyclerView;
    TextView noData_tv;
    WorkAdapter workAdapter;
    LinearLayout operations_ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.work_requir_fragment, container,
                false);

        ma = (WorkRequirements_Activity) this.getActivity();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        noData_tv = rootView.findViewById(R.id.noData_tv);
        operations_ll = rootView.findViewById(R.id.operations_ll);

        if (ma.work_al != null) {
            if (ma.work_al.size() > 0) {
                workAdapter = new WorkAdapter(getActivity(), ma.work_al);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(workAdapter);
                noData_tv.setVisibility(View.GONE);
                operations_ll.setVisibility(View.VISIBLE);
            } else {
                noData_tv.setVisibility(View.VISIBLE);
                operations_ll.setVisibility(View.GONE);
            }
        }
        return rootView;
    }

    public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder> {
        private Context mContext;
        private List<OrdrWaChkReqPrcbl> workData_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView text_tv;
            CheckBox yes_cb, no_cb;

            public MyViewHolder(View view) {
                super(view);
                yes_cb = view.findViewById(R.id.yes_cb);
                no_cb = view.findViewById(R.id.no_cb);
                text_tv = view.findViewById(R.id.text_tv);
                yes_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (yes_cb.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            ma.work_al.get(getPosition).setYes_checked(true);
                            ma.work_al.get(getPosition).setNo_checked(false);
                            ma.work_al.get(getPosition).setValue("Y");
                            yes_cb.setChecked(true);
                            no_cb.setChecked(false);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            ma.work_al.get(getPosition).setYes_checked(false);
                            ma.work_al.get(getPosition).setValue("");
                            yes_cb.setChecked(false);
                        }
                    }
                });
                no_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (no_cb.isChecked()) {
                            int getPosition = (Integer) v.getTag();
                            ma.work_al.get(getPosition).setYes_checked(false);
                            ma.work_al.get(getPosition).setNo_checked(true);
                            ma.work_al.get(getPosition).setValue("N");
                            yes_cb.setChecked(false);
                            no_cb.setChecked(true);
                        } else {
                            int getPosition = (Integer) v.getTag();
                            ma.work_al.get(getPosition).setValue("");
                            ma.work_al.get(getPosition).setNo_checked(false);
                            no_cb.setChecked(true);
                        }
                    }
                });
            }
        }

        public WorkAdapter(Context mContext, List<OrdrWaChkReqPrcbl> work_list) {
            this.mContext = mContext;
            this.workData_list = work_list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workrequir_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final OrdrWaChkReqPrcbl owrp = workData_list.get(position);
            holder.text_tv.setText(owrp.getDesctext());
            holder.yes_cb.setTag(position);
            holder.yes_cb.setChecked((owrp.isYes_checked() == true ? true : false));
            holder.no_cb.setTag(position);
            holder.no_cb.setChecked((owrp.isNo_checked() == true ? true : false));
            if(ma.prep.equals("X")){
                holder.yes_cb.setEnabled(false);
                holder.no_cb.setEnabled(false);
            } else{
                holder.yes_cb.setEnabled(true);
                holder.no_cb.setEnabled(true);
            }
        }

        @Override
        public int getItemCount() {
            return workData_list.size();
        }
    }

}
