package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;

public class IsolationIssue_Fragment extends Fragment{

    RecyclerView recyclerView;
    TextView noData_tv;
    Isolation_Add_Update_Activity ma;
    ArrayList<OrdrWcagnsPrcbl> wcgnWd_al = new ArrayList<>();
    ArrayList<OrdrWcagnsPrcbl> wcgnWd_al_d = new ArrayList<>();
    OrdrWcagnsPrcbl wcgWd = new OrdrWcagnsPrcbl();
    PERMITLIST_ADAPTER adapter;
    String prep = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);
        ma = (Isolation_Add_Update_Activity) this.getActivity();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        noData_tv = rootView.findViewById(R.id.noData_tv);

        wcgnWd_al = ma.iso.getWcagnsPrcbl_Al();

        if (wcgnWd_al != null) {
            if(ma.iso.getPrep() != null){
                if(ma.iso.getPrep().equals("X"))
                    prep = "X";
                else
                    prep = "";
            }
            wcgnWd_al_d = new ArrayList<>();
            for (OrdrWcagnsPrcbl wcg : wcgnWd_al) {
                if (wcg.getGeniakt().equals("X"))
                    wcg.setGeniakt_status(false);
                else
                    wcg.setGeniakt_status(true);
            }
            for (OrdrWcagnsPrcbl wcg : wcgnWd_al) {
                if (!wcg.getAction().equals("D"))
                    wcgnWd_al_d.add(wcg);
            }
            if (wcgnWd_al_d.size() > 0) {
                adapter = new PERMITLIST_ADAPTER(getActivity(), wcgnWd_al_d);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
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

        ma.fab.hide();
    }

    public class PERMITLIST_ADAPTER extends RecyclerView.Adapter<PERMITLIST_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<OrdrWcagnsPrcbl> issuelist;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView date_time_textview, Objnr, permit_textview, permit_text_textview, Geniakt, Crname;
            public EditText person_name_edittext;
            public CheckBox permit_issued_checkbox;
            public ImageView delIssue_iv;

            public MyViewHolder(final View view) {
                super(view);
                permit_textview = (TextView) view.findViewById(R.id.permit_textview);
                permit_text_textview = (TextView) view.findViewById(R.id.permit_text_textview);
                person_name_edittext = (EditText) view.findViewById(R.id.person_name_edittext);
                Geniakt = (TextView) view.findViewById(R.id.Geniakt);
                permit_issued_checkbox = (CheckBox) view.findViewById(R.id.permit_issued_checkbox);
                Crname = (TextView) view.findViewById(R.id.Crname);
                Objnr = (TextView) view.findViewById(R.id.Objnr);
                date_time_textview = (TextView) view.findViewById(R.id.date_time_textview);
                delIssue_iv = view.findViewById(R.id.delIssue_iv);
                permit_issued_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (permit_issued_checkbox.isChecked()) {
                            int position = (Integer) v.getTag();
                            wcgnWd_al_d.get(position).setGenvname(Crname.getText().toString());
                            wcgnWd_al_d.get(position).setGeniakt_status(true);
                            permit_issued_checkbox.setChecked(true);
                            Geniakt.setText("");
                            person_name_edittext.setText(Crname.getText().toString());
                            date_time_textview.setText(currentDate() + " " + currentTime());
                            wcgnWd_al_d.get(position).setGeniakt("");
                            wcgnWd_al_d.get(position).setGendatum(currentDate());
                            wcgnWd_al_d.get(position).setGentime(currentTime());
                            wcgnWd_al_d.get(position).setAction("I");
                            ma.iso.setWcagnsPrcbl_Al(wcgnWd_al_d);
                        } else {
                            int position = (Integer) v.getTag();
                            wcgnWd_al_d.get(position).setGenvname("");
                            wcgnWd_al_d.get(position).setGeniakt_status(false);
                            permit_issued_checkbox.setChecked(false);
                            Geniakt.setText("X");
                            person_name_edittext.setText("");
                            date_time_textview.setText("");
                            wcgnWd_al_d.get(position).setGeniakt("X");
                            wcgnWd_al_d.get(position).setGendatum("");
                            wcgnWd_al_d.get(position).setAction("I");
                            wcgnWd_al_d.get(position).setGentime("");
                            ma.iso.setWcagnsPrcbl_Al(wcgnWd_al_d);
                        }
                    }
                });
                person_name_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int pos = (int) person_name_edittext.getTag();
                        wcgnWd_al_d.get(pos).setGenvname(person_name_edittext.getText().toString());
                        ma.iso.setWcagnsPrcbl_Al(wcgnWd_al_d);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }

        public PERMITLIST_ADAPTER(Context mContext, List<OrdrWcagnsPrcbl> list) {
            this.mContext = mContext;
            this.issuelist = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.permit_issue_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrWcagnsPrcbl nto = issuelist.get(position);

            holder.permit_textview.setText(nto.getPmsog());
            holder.permit_text_textview.setText(nto.getGntxt());
            holder.person_name_edittext.setTag(position);
            holder.person_name_edittext.setEnabled(false);
            holder.person_name_edittext.setText(nto.getGenvname());
            holder.Crname.setText(nto.getCrname());
            holder.Objnr.setText(nto.getObjnr());
            holder.date_time_textview.setText(nto.getGendatum() + " " + nto.getGentime());
            holder.permit_issued_checkbox.setTag(position);
            holder.delIssue_iv.setVisibility(GONE);
            holder.permit_issued_checkbox.setChecked((issuelist.get(position).isGeniakt_status() == true ? true : false));
            holder.permit_issued_checkbox.setEnabled(false);
            /*if (holder.permit_textview.getText().toString().equals("HOD") || holder.permit_textview.getText().toString().equals("SAFETY")) {
                if (prep.equals("X")) {
                    if (woco.equals("X")) {
                        holder.delIssue_iv.setVisibility(View.GONE);
                    } else {
                        holder.delIssue_iv.setVisibility(View.GONE);
                    }
                } else {
                    if (woco.equals("X"))
                        holder.delIssue_iv.setVisibility(View.GONE);
                    else
                        holder.delIssue_iv.setVisibility(View.VISIBLE);
                }
            } else {
                holder.delIssue_iv.setVisibility(GONE);
            }*/
            /*if (prep.equals("X")) {
                holder.permit_issued_checkbox.setEnabled(true);
                holder.person_name_edittext.setEnabled(true);
            } else {
                holder.permit_issued_checkbox.setEnabled(false);
                holder.person_name_edittext.setEnabled(false);
            }*/
        }

        @Override
        public int getItemCount() {
            return issuelist.size();
        }
    }

    private String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        return sdf.format(new Date());
    }

    private String currentTime() {
        SimpleDateFormat sdf_t = new SimpleDateFormat("HH:mm:ss");
        return sdf_t.format(new Date());
    }
}
