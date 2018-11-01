package com.enstrapp.fieldtekpro.notifications;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications_Status_System_Fragment extends Fragment {

    RecyclerView list_recycleview;
    TextView no_data_textview;
    ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatus_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
    Status_Adapter status_adapter;
    Notifications_Status_Activity nsa;

    public Notifications_Status_System_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.notifications_status_fragemnt, container, false);

        list_recycleview = (RecyclerView) rootView.findViewById(R.id.list_recycleview);
        no_data_textview = (TextView) rootView.findViewById(R.id.no_data_textview);

        nsa = (Notifications_Status_Activity) this.getActivity();

        try {
            status_systemstatus_array = nsa.status_systemstatus_array;
        } catch (Exception e) {
        }
        if (status_systemstatus_array.size() > 0) {
            list_recycleview.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list_recycleview.setLayoutManager(mLayoutManager);
            status_adapter = new Status_Adapter(getActivity(), status_systemstatus_array);
            list_recycleview.setAdapter(status_adapter);
            list_recycleview.setVisibility(View.VISIBLE);
            no_data_textview.setVisibility(View.GONE);
        } else {
            list_recycleview.setVisibility(View.GONE);
            no_data_textview.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.MyViewHolder> {
        private Context mContext;
        private List<Notif_Status_WithNum_Prcbl> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txt04_textview, txt30_textview, act_textview, checked_status_textview,
                    act_status_textview;
            CheckBox status_checkbox;

            public MyViewHolder(View view) {
                super(view);
                txt04_textview = (TextView) view.findViewById(R.id.txt04_textview);
                txt30_textview = (TextView) view.findViewById(R.id.txt30_textview);
                act_textview = (TextView) view.findViewById(R.id.act_textview);
                status_checkbox = (CheckBox) view.findViewById(R.id.status_checkbox);
                checked_status_textview = (TextView) view.findViewById(R.id.checked_status_textview);
                act_status_textview = (TextView) view.findViewById(R.id.act_status_textview);
            }
        }

        public Status_Adapter(Context mContext, List<Notif_Status_WithNum_Prcbl> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_orders_status_list_data, parent,
                            false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Notif_Status_WithNum_Prcbl olo = list_data.get(position);
            holder.txt04_textview.setText(olo.getTxt04());
            holder.txt30_textview.setText(olo.getTxt30());
            holder.checked_status_textview.setText(olo.getChecked_Status());
            holder.act_status_textview.setText(olo.getAct_Status());
            holder.act_textview.setText(olo.getAct());
            if (holder.act_textview.getText().toString().equalsIgnoreCase("X")) {
                holder.status_checkbox.setChecked(true);
                holder.status_checkbox.setEnabled(false);
            } else {
                holder.status_checkbox.setChecked(false);
                holder.status_checkbox.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }


    public ArrayList<Notif_Status_WithNum_Prcbl> getstatus_systemstatusData() {
        return status_systemstatus_array;
    }
}
