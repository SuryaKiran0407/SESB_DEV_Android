package com.enstrapp.fieldtekpro.notifications;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Notifications_Status_WithoutNum_Fragment extends Fragment
{

    RecyclerView list_recycleview;
    TextView no_data_textview;
    ArrayList<Notif_Status_WithNum_Prcbl> status_withoutNum_array = new ArrayList<Notif_Status_WithNum_Prcbl>();
    Status_Adapter status_adapter;
    Notifications_Status_Activity nsa;
    Error_Dialog error_dialog = new Error_Dialog();
    String qmnum = "", notifUuid = "", causecode_status = "", malfunc_enddate_status = "";
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public Notifications_Status_WithoutNum_Fragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.notifications_status_fragemnt, container, false);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        list_recycleview = (RecyclerView)rootView.findViewById(R.id.list_recycleview);
        no_data_textview = (TextView) rootView.findViewById(R.id.no_data_textview);

        nsa = (Notifications_Status_Activity)this.getActivity();

        try
        {
            status_withoutNum_array = nsa.status_withoutNum_array;
            qmnum = nsa.qmnum;
            notifUuid = nsa.uuid;
            causecode_status = nsa.causecode_status;
            malfunc_enddate_status = nsa.malfunc_enddate_status;
        }
        catch (Exception e)
        {
        }
        if (status_withoutNum_array.size() > 0)
        {
            list_recycleview.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list_recycleview.setLayoutManager(mLayoutManager);
            status_adapter = new Status_Adapter(getActivity(),status_withoutNum_array);
            list_recycleview.setAdapter(status_adapter);
            list_recycleview.setVisibility(View.VISIBLE);
            no_data_textview.setVisibility(View.GONE);
        }
        else
        {
            list_recycleview.setVisibility(View.GONE);
            no_data_textview.setVisibility(View.VISIBLE);
        }

        return rootView;
    }


    public class Status_Adapter extends RecyclerView.Adapter<Status_Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<Notif_Status_WithNum_Prcbl> list_data;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView txt04_textview, txt30_textview, act_textview, checked_status_textview, act_status_textview;
            CheckBox status_checkbox;
            public MyViewHolder(View view)
            {
                super(view);
                txt04_textview = (TextView) view.findViewById(R.id.txt04_textview);
                txt30_textview = (TextView) view.findViewById(R.id.txt30_textview);
                act_textview = (TextView) view.findViewById(R.id.act_textview);
                status_checkbox = (CheckBox) view.findViewById(R.id.status_checkbox);
                checked_status_textview = (TextView) view.findViewById(R.id.checked_status_textview);
                act_status_textview = (TextView) view.findViewById(R.id.act_status_textview);
            }
        }
        public Status_Adapter(Context mContext, List<Notif_Status_WithNum_Prcbl> list)
        {
            this.mContext = mContext;
            this.list_data = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_orders_status_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            final Notif_Status_WithNum_Prcbl olo = list_data.get(position);
            holder.txt04_textview.setText(olo.getTxt04());
            holder.txt30_textview.setText(olo.getTxt30());
            holder.checked_status_textview.setText(olo.getChecked_Status());
            holder.act_status_textview.setText(olo.getAct_Status());
            holder.act_textview.setText(olo.getAct());
            if(holder.act_textview.getText().toString().equalsIgnoreCase("X"))
            {
                holder.status_checkbox.setChecked(true);
                holder.status_checkbox.setEnabled(true);
            }
            else if(holder.checked_status_textview.getText().toString().equalsIgnoreCase("true"))
            {
                if(holder.act_status_textview.getText().toString().equalsIgnoreCase("X"))
                {
                    holder.status_checkbox.setChecked(true);
                    holder.status_checkbox.setEnabled(true);
                }
                else
                {
                    holder.status_checkbox.setChecked(false);
                    holder.status_checkbox.setEnabled(true);
                }
            }
            else
            {
                holder.status_checkbox.setChecked(false);
                holder.status_checkbox.setEnabled(true);
            }

            holder.status_checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(holder.status_checkbox.isChecked())
                    {
                        boolean isNodf = false;
                        for (int i = 0; i < list_data.size(); i++)
                        {
                            if (list_data.get(i).getTxt04().equalsIgnoreCase("NODF") && list_data.get(i).getAct_Status().equalsIgnoreCase("true"))
                            {
                                isNodf = true;
                            }
                        }
                        if (isNodf == true)
                        {
                            if (holder.txt04_textview.getText().toString().equalsIgnoreCase("MJCO"))
                            {
                                if (causecode_status.equalsIgnoreCase("true"))
                                {
                                    if (malfunc_enddate_status.equalsIgnoreCase("true"))
                                    {
                                        olo.setChecked_Status("true");
                                        holder.checked_status_textview.setText("true");
                                        olo.setAct_Status("X");
                                        holder.act_status_textview.setText("X");
                                        list_data.get(position).setAct_Status("X");
                                        list_data.get(position).setChecked_Status("true");
                                        list_data.get(position).setAct("X");
                                    }
                                    else
                                    {
                                        holder.status_checkbox.setChecked(false);
                                        error_dialog.show_error_dialog(getActivity(),"Please Enter Malfunction End Date & Time");
                                    }
                                }
                                else
                                {
                                    holder.status_checkbox.setChecked(false);
                                    error_dialog.show_error_dialog(getActivity(),"Please Enter Object Part Code for this Notification");
                                }
                            }
                            else
                            {
                                holder.status_checkbox.setChecked(false);
                                error_dialog.show_error_dialog(getActivity(),"Please remove user status 'NODF'");
                            }
                        }
                        else
                        {
                            if (holder.txt04_textview.getText().toString().equalsIgnoreCase("MJCO"))
                            {
                                if (causecode_status.equalsIgnoreCase("true"))
                                {
                                    if (malfunc_enddate_status.equalsIgnoreCase("true"))
                                    {
                                        olo.setChecked_Status("true");
                                        holder.checked_status_textview.setText("true");
                                        olo.setAct_Status("X");
                                        holder.act_status_textview.setText("X");
                                        list_data.get(position).setAct_Status("X");
                                        list_data.get(position).setChecked_Status("true");
                                        list_data.get(position).setAct("X");
                                    }
                                    else
                                    {
                                        holder.status_checkbox.setChecked(false);
                                        error_dialog.show_error_dialog(getActivity(),"Please Enter Malfunction End Date & Time");
                                    }
                                }
                                else
                                {
                                    holder.status_checkbox.setChecked(false);
                                    error_dialog.show_error_dialog(getActivity(),"Please Enter Object Part Code for this Notification");
                                }
                            }
                            else
                            {
                                if (holder.txt04_textview.getText().toString().equals("IDCO"))
                                {
                                    if (malfunc_enddate_status.equalsIgnoreCase("true"))
                                    {
                                        olo.setChecked_Status("true");
                                        holder.checked_status_textview.setText("true");
                                        olo.setAct_Status("X");
                                        holder.act_status_textview.setText("X");
                                        list_data.get(position).setAct_Status("X");
                                        list_data.get(position).setChecked_Status("true");
                                        list_data.get(position).setAct("X");
                                    }
                                    else
                                    {
                                        holder.status_checkbox.setChecked(false);
                                        error_dialog.show_error_dialog(getActivity(),"Please Enter Malfunction End Date & Time");
                                    }
                                }
                                else
                                {
                                    olo.setChecked_Status("true");
                                    holder.checked_status_textview.setText("true");
                                    olo.setAct_Status("X");
                                    holder.act_status_textview.setText("X");
                                    list_data.get(position).setAct_Status("X");
                                    list_data.get(position).setChecked_Status("true");
                                    list_data.get(position).setAct("X");
                                }
                            }
                        }
                    }
                    else
                    {
                        olo.setChecked_Status("");
                        holder.checked_status_textview.setText("");
                        olo.setAct_Status("");
                        holder.act_status_textview.setText("");
                        list_data.get(position).setAct_Status("");
                        list_data.get(position).setChecked_Status("");
                        list_data.get(position).setAct("");
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


    public ArrayList<Notif_Status_WithNum_Prcbl> getstatus_withoutNumData()
    {
        return status_withoutNum_array;
    }

}
