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
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;

import java.util.List;

public class StatusSystemFragment extends Fragment{

    SearchView searchView;
    RecyclerView list_recycleview;
    LinearLayout no_data_layout;
    Order_Status_Activity ma;
    SysStsAdapter sysStsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f4_list_fragment, container,
                false);

        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        list_recycleview = rootView.findViewById(R.id.list_recycleview);
        searchView = rootView.findViewById(R.id.search);
        ma = (Order_Status_Activity)this.getActivity();

        searchView.setVisibility(View.GONE);

        if(ma.sysSts_al != null) {
            if(ma.sysSts_al.size() > 0) {
                sysStsAdapter = new SysStsAdapter(getActivity(), ma.sysSts_al);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(sysStsAdapter);
                no_data_layout.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
            } else {
                no_data_layout.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
            }
        }
        return  rootView;
    }

    public class SysStsAdapter extends RecyclerView.Adapter<SysStsAdapter.MyViewHolder> {
        private Context mContext;
        private List<NotifOrdrStatusPrcbl> sysStsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txt04_tv, txt30_tv;
            CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                txt04_tv = (TextView) view.findViewById(R.id.txt04_tv);
                txt30_tv = (TextView) view.findViewById(R.id.txt30_tv);
                checkBox = view.findViewById(R.id.checkBox);
            }
        }

        public SysStsAdapter(Context mContext, List<NotifOrdrStatusPrcbl> list) {
            this.mContext = mContext;
            this.sysStsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_with_cb_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final NotifOrdrStatusPrcbl sso = sysStsList.get(position);
            holder.txt04_tv.setText(sso.getTxt04());
            holder.txt30_tv.setText(sso.getTxt30());
            holder.checkBox.setChecked(sso.isSelected());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.setChecked(true);
                }
            });
        }

        @Override
        public int getItemCount() {
            return sysStsList.size();
        }
    }



}
