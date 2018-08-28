package com.enstrapp.fieldtekpro.PermitList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.getOrderDetail.GetOrderDetail;
import com.enstrapp.fieldtekpro.orders.Get_Isolation_Detail;
import com.enstrapp.fieldtekpro.orders.Get_Permit_Detail;
import com.enstrapp.fieldtekpro.orders.Isolation_Add_Update_Activity;
import com.enstrapp.fieldtekpro.orders.Orders_Change_Activity;
import com.enstrapp.fieldtekpro.orders.OrdrHeaderPrcbl;
import com.enstrapp.fieldtekpro.orders.OrdrPermitPrcbl;
import com.enstrapp.fieldtekpro.orders.Permits_Add_Update_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Isolation_List_UnTagUnTagged_Fragment extends Fragment
{

    private Adapter adapter;
    List<Isolation_Object> isoaltion_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    public SearchView search;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    TextView title_textview, no_data_textview, searchview_textview;
    ImageView back_imageview;
    RecyclerView recyclerview;
    Button cancel_button, submit_button;
    Set<HashMap<String, String>> abc = new HashSet<HashMap<String, String>>();
    String selected_orderstatus = "", selected_Iwerk = "", selected_orderID = "", selected_orderUUID = "",
            woco = "", permit_type = "", permit_no = "", wcnr_no = "", refObj = "", filter_funcloc = "", filter_plant = "";
    Error_Dialog error_dialog = new Error_Dialog();


    public Isolation_List_UnTagUnTagged_Fragment()
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
        View rootView = inflater.inflate(R.layout.isolation_list_activity, container, false);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getActivity().getSharedPreferences("FieldTekPro_SharedPreferences", Context.MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
		/* Initializing Shared Preferences */

        search = (SearchView)rootView.findViewById(R.id.search);
        title_textview = (TextView)rootView.findViewById(R.id.title_textview);
        no_data_textview = (TextView)rootView.findViewById(R.id.no_data_textview);
        back_imageview = (ImageView)rootView.findViewById(R.id.back_imageview);
        recyclerview = (RecyclerView)rootView.findViewById(R.id.recyclerview);
        cancel_button = (Button)rootView.findViewById(R.id.cancel_button);
        submit_button = (Button)rootView.findViewById(R.id.submit_button);

        int id = search.getContext() .getResources().getIdentifier("android:id/search_src_text", null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.white));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText)search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.dark_grey2));

        new Get_Isolation_List_Data().execute();

        return rootView;
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener()
    {
        @Override
        public boolean onQueryTextChange(String query)
        {
            query = query.toLowerCase();
            final List<Isolation_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < isoaltion_list.size(); i++)
            {
                String ordNo = isoaltion_list.get(i).getOrder_num().toLowerCase();
                String permitNo = isoaltion_list.get(i).getPermit_number().toLowerCase();
                String permitTxt = isoaltion_list.get(i).getPermit_text().toLowerCase();
                String authGrp = isoaltion_list.get(i).getAuth_grp().toLowerCase();
                String tagged = isoaltion_list.get(i).getTagged().toLowerCase();
                String untagged = isoaltion_list.get(i).getUntagged().toLowerCase();
                if (ordNo.contains(query) || permitNo.contains(query) || permitTxt.contains(query) || authGrp.contains(query)|| tagged.contains(query) || untagged.contains(query))
                {
                    Isolation_Object nto = new Isolation_Object(isoaltion_list.get(i).getOrder_num(), isoaltion_list.get(i).getPmsog(),
                            isoaltion_list.get(i).getGntxt(), isoaltion_list.get(i).getGenvname(),
                            isoaltion_list.get(i).getCrname(), isoaltion_list.get(i).getObjnr(),
                            isoaltion_list.get(i).getDate_time(), isoaltion_list.get(i).getWerk(),
                            isoaltion_list.get(i).getCounter(), isoaltion_list.get(i).getHilvl(),
                            isoaltion_list.get(i).isGeniakt_status(), isoaltion_list.get(i).getPermit_number(),
                            isoaltion_list.get(i).getFloc_number(), isoaltion_list.get(i).getPermit_text(),
                            isoaltion_list.get(i).getAuth_grp(), isoaltion_list.get(i).getPermit_typ(),
                            isoaltion_list.get(i).getWcnr(), isoaltion_list.get(i).getRefobj(),
                            isoaltion_list.get(i).getTagged(), isoaltion_list.get(i).getUntagged(), isoaltion_list.get(i).getIwerk());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0)
            {
                adapter = new Adapter(getActivity(), filteredList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            }
            else
            {
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
            return true;
        }
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };


    private class Get_Isolation_List_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
            isoaltion_list.clear();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                String Usgrp = "";
                Cursor cursor1 = FieldTekPro_db.rawQuery("select * from GET_USER_DATA", null);
                if (cursor1 != null && cursor1.getCount() > 0)
                {
                    if (cursor1.moveToFirst())
                    {
                        do
                        {
                            Usgrp = cursor1.getString(15);
                        }
                        while (cursor1.moveToNext());
                    }
                }
                else
                {
                    cursor1.close();
                }

                if (Usgrp != null && !Usgrp.equals(""))
                {
                    ArrayList<String> list = new ArrayList<String>();
                    list.clear();
                    Cursor cursor2 = FieldTekPro_db.rawQuery("select * from EtUsgrpWccp where Usgrp = ?", new String[]{Usgrp});
                    if (cursor2 != null && cursor2.getCount() > 0)
                    {
                        if (cursor2.moveToFirst())
                        {
                            do
                            {
                                String Pmsog = cursor2.getString(2);
                                list.add(Pmsog);
                            }
                            while (cursor2.moveToNext());
                        }
                    }
                    else
                    {
                        cursor2.close();
                    }

                    Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmWcagns where Objart = ? and Geniakt = ?", new String[]{"WD", "X"});
                    if (cursor != null && cursor.getCount() > 0)
                    {
                        if (cursor.moveToFirst())
                        {
                            do
                            {
                                HashMap<String, String> hashmap = new HashMap<String, String>();
                                hashmap.put("uuid", cursor.getString(1));
                                hashmap.put("Aufnr", cursor.getString(2));
                                abc.add(hashmap);
                                String EtWcmWcagns_Pmsog = cursor.getString(7);
                                if (list.contains(EtWcmWcagns_Pmsog))
                                {
                                    String date = cursor.getString(26);
                                    String date_format = "";
                                    if (date != null && !date.equals(""))
                                    {
                                        if (date.equalsIgnoreCase("00000000"))
                                        {
                                            date_format = "";
                                        }
                                        else
                                        {
                                            String Date_format = "";
                                            DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            Date date1;
                                            try
                                            {
                                                date1 = inputFormat.parse(date);
                                                String outputDateStr = outputFormat.format(date1);
                                                Date_format = outputDateStr;
                                            }
                                            catch (Exception e)
                                            {
                                                Date_format = "";
                                            }
                                            String time = cursor.getString(27);
                                            String time_formatted = "";
                                            if (time != null && !time.equals(""))
                                            {
                                                DateFormat inputFormat1 = new SimpleDateFormat("HHmmss");
                                                DateFormat outputFormat1 = new SimpleDateFormat("HH:mm:ss");
                                                Date date2;
                                                try
                                                {
                                                    date2 = inputFormat1.parse(date);
                                                    String outputDateStr = outputFormat1.format(date2);
                                                    time_formatted = outputDateStr;
                                                }
                                                catch (Exception e)
                                                {
                                                    time_formatted = "";
                                                }
                                            }
                                            date_format = Date_format + "  " + time_formatted;
                                        }
                                    }
                                    else
                                    {
                                        date_format = "";
                                    }
                                    boolean Geniakt_status = false;
                                    String Geniakt = cursor.getString(9);
                                    if (Geniakt.equalsIgnoreCase("X"))
                                    {
                                        Geniakt_status = false;
                                    }
                                    else
                                    {
                                        Geniakt_status = true;
                                    }
                                    String permit_number = "";
                                    String permit_text = "";
                                    String auth_grp = "";
                                    String permit_type = "";
                                    String ref_obj = "";
                                    String objnr = cursor.getString(3);
                                    String wcnr = "";
                                    if (objnr != null && !objnr.equals(""))
                                    {
                                        try
                                        {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdData where Objnr = ?", new String[]{objnr});
                                            if (cursor3 != null && cursor3.getCount() > 0)
                                            {
                                                if (cursor3.moveToFirst())
                                                {
                                                    do
                                                    {
                                                        wcnr = cursor3.getString(3);
                                                        permit_text = cursor3.getString(14);
                                                        permit_type = cursor3.getString(6);
                                                        auth_grp = cursor3.getString(30) + " - " + cursor3.getString(31);
                                                        ref_obj = cursor3.getString(24);
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            }
                                            else
                                            {
                                                cursor3.close();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                        }
                                    }
                                    String tagged = "";
                                    String untagged = "";
                                    if (objnr != null && !objnr.equals(""))
                                    {
                                        try
                                        {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWdItemData where Wcnr = ? and (Bug = ? or Eug = ?)", new String[]{wcnr, "X","X"});
                                            if (cursor3 != null && cursor3.getCount() > 0)
                                            {
                                                if (cursor3.moveToFirst())
                                                {
                                                    do
                                                    {
                                                        if (cursor3.getString(36).equals("X"))
                                                        {
                                                            untagged = "UNTAGGED";
                                                        }
                                                        else if (cursor3.getString(37).equals("X"))
                                                        {
                                                            untagged = "UNTAGGED";
                                                        }
                                                        else
                                                        {
                                                            untagged = "UNTAGGED";
                                                        }
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            }
                                            else
                                            {
                                                cursor3.close();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                        }
                                    }
                                    if (ref_obj != null && !ref_obj.equals(""))
                                    {
                                        try
                                        {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from EtWcmWaData where Objnr = ?", new String[]{ref_obj});
                                            if (cursor3 != null && cursor3.getCount() > 0)
                                            {
                                                if (cursor3.moveToFirst())
                                                {
                                                    do
                                                    {
                                                        permit_number = cursor3.getString(4);
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            }
                                            else
                                            {
                                                cursor3.close();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                        }
                                    }
                                    String iwerk = "";
                                    String floc_number = "";
                                    String aufnr = cursor.getString(2);
                                    if (aufnr != null && !aufnr.equals(""))
                                    {
                                        try
                                        {
                                            Cursor cursor3 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{aufnr});
                                            if (cursor3 != null && cursor3.getCount() > 0)
                                            {
                                                if (cursor3.moveToFirst())
                                                {
                                                    do
                                                    {
                                                        floc_number = cursor3.getString(10);
                                                        iwerk = cursor3.getString(25);
                                                    }
                                                    while (cursor3.moveToNext());
                                                }
                                            }
                                            else
                                            {
                                                cursor3.close();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                        }
                                    }
                                    if (untagged != null && !untagged.equals(""))
                                    {
                                        Isolation_Object nto = new Isolation_Object(cursor.getString(2), cursor.getString(7), cursor.getString(8), cursor.getString(10), cursor.getString(13), cursor.getString(3), date_format, cursor.getString(12), cursor.getString(4), cursor.getString(14), Geniakt_status, permit_number, floc_number, permit_text, auth_grp, permit_type, wcnr, ref_obj,tagged,untagged, iwerk);
                                        isoaltion_list.add(nto);
                                    }
                                }
                                else
                                {
                                }
                            }
                            while (cursor.moveToNext());
                        }
                    }
                    else
                    {
                        cursor.close();
                    }
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (isoaltion_list.size() > 0)
            {
                if (filter_funcloc != null && !filter_funcloc.equals(""))
                {
                    CollectionUtils.filter(isoaltion_list, new Predicate()
                    {
                        @Override
                        public boolean evaluate(Object o)
                        {
                            return ((Isolation_Object) o).getFloc_number().matches(filter_funcloc);
                        }
                    });
                }
                else if (filter_plant != null && !filter_plant.equals(""))
                {
                    CollectionUtils.filter(isoaltion_list, new Predicate()
                    {
                        @Override
                        public boolean evaluate(Object o)
                        {
                            return ((Isolation_Object) o).getIwerk().matches(filter_plant);
                        }
                    });
                }
            }
            if (isoaltion_list.size() > 0)
            {
                adapter = new Adapter(getActivity(), isoaltion_list);
                recyclerview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            }
            else
            {
                no_data_textview.setVisibility(View.VISIBLE);
                recyclerview.setVisibility(View.GONE);
            }
            custom_progress_dialog.dismiss_progress_dialog();
        }
    }


    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>
    {
        private Context mContext;
        private List<Isolation_Object> type_details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView auth_grp_textview, floc_id_textview, permit_no_textview, date_time_textview, Objnr, order_num_textview, permit_textview, permit_text_textview, Geniakt, Crname, permit_typ, wcnr_no_textview;
            public EditText person_name_edittext;
            public CheckBox permit_issued_checkbox;
            public LinearLayout datetime_layout, permit_issued_layout, approved_by_layout, wcnr_layout;
            public MyViewHolder(final View view)
            {
                super(view);
                permit_typ = (TextView) view.findViewById(R.id.permit_typ);
                order_num_textview = (TextView) view.findViewById(R.id.order_num_textview);
                permit_textview = (TextView) view.findViewById(R.id.permit_textview);
                permit_text_textview = (TextView) view.findViewById(R.id.permit_text_textview);
                person_name_edittext = (EditText) view.findViewById(R.id.person_name_edittext);
                Geniakt = (TextView) view.findViewById(R.id.Geniakt);
                permit_issued_checkbox = (CheckBox) view.findViewById(R.id.permit_issued_checkbox);
                Crname = (TextView) view.findViewById(R.id.Crname);
                Objnr = (TextView) view.findViewById(R.id.Objnr);
                date_time_textview = (TextView) view.findViewById(R.id.date_time_textview);
                permit_no_textview = (TextView) view.findViewById(R.id.permit_no_textview);
                floc_id_textview = (TextView) view.findViewById(R.id.floc_id_textview);
                auth_grp_textview = (TextView) view.findViewById(R.id.auth_grp_textview);
                wcnr_no_textview = (TextView) view.findViewById(R.id.wcnr_no_textview);
                permit_issued_layout = (LinearLayout) view.findViewById(R.id.permit_issued_layout);
                approved_by_layout = (LinearLayout) view.findViewById(R.id.approved_by_layout);
                datetime_layout = (LinearLayout) view.findViewById(R.id.datetime_layout);
                wcnr_layout = (LinearLayout) view.findViewById(R.id.wcnr_layout);
                permit_issued_layout.setVisibility(View.GONE);
                approved_by_layout.setVisibility(View.GONE);
                datetime_layout.setVisibility(View.GONE);
                permit_issued_checkbox.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (permit_issued_checkbox.isChecked())
                        {
                            int position = (Integer) v.getTag();
                            isoaltion_list.get(position).setGenvname(Crname.getText().toString());
                            isoaltion_list.get(position).setGeniakt_status(true);
                            permit_issued_checkbox.setChecked(true);
                            Geniakt.setText("");
                            person_name_edittext.setText(Crname.getText().toString());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                            String currentDateandTime = sdf.format(new Date());
                            date_time_textview.setText(currentDateandTime);
                            isoaltion_list.get(position).setDate_time(currentDateandTime);
                        }
                        else
                        {
                            int position = (Integer) v.getTag();
                            isoaltion_list.get(position).setGenvname("");
                            isoaltion_list.get(position).setGeniakt_status(false);
                            permit_issued_checkbox.setChecked(false);
                            Geniakt.setText("X");
                            person_name_edittext.setText("");
                            date_time_textview.setText("");
                            isoaltion_list.get(position).setDate_time("");
                        }
                    }
                });
                person_name_edittext.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        int pos = (int) person_name_edittext.getTag();
                        isoaltion_list.get(pos).setGenvname(person_name_edittext.getText().toString());
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {
                    }
                    @Override
                    public void afterTextChanged(Editable s)
                    {
                    }
                });
            }
        }
        public Adapter(Context mContext, List<Isolation_Object> list)
        {
            this.mContext = mContext;
            this.type_details_list = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.permitlist_list_data, parent, false);
            return new MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position)
        {
            final Isolation_Object nto = type_details_list.get(position);
            SpannableString content = new SpannableString(nto.getOrder_num());
            content.setSpan(new UnderlineSpan(), 0, nto.getOrder_num().length(), 0);
            SpannableString permit_content = new SpannableString(nto.getPermit_number());
            permit_content.setSpan(new UnderlineSpan(), 0, nto.getPermit_number().length(), 0);
            SpannableString wcnr_content = new SpannableString(nto.getWcnr());
            wcnr_content.setSpan(new UnderlineSpan(), 0, nto.getWcnr().length(), 0);
            holder.wcnr_layout.setVisibility(View.VISIBLE);
            holder.wcnr_no_textview.setText(wcnr_content);
            holder.wcnr_no_textview.setTag(position);
            holder.order_num_textview.setText(content);
            holder.permit_textview.setText(nto.getPmsog());
            holder.permit_text_textview.setText(nto.getPermit_text());
            holder.person_name_edittext.setTag(position);
            holder.person_name_edittext.setText(nto.getGenvname());
            holder.Crname.setText(nto.getCrname());
            holder.Objnr.setText(nto.getObjnr());
            holder.date_time_textview.setText(nto.getDate_time());
            holder.permit_no_textview.setText(permit_content);
            holder.floc_id_textview.setText(nto.getFloc_number());
            holder.auth_grp_textview.setText(nto.getAuth_grp());
            holder.permit_issued_checkbox.setTag(position);
            holder.permit_typ.setText(nto.getPermit_typ());
            holder.permit_issued_checkbox.setChecked((type_details_list.get(position).isGeniakt_status() == true ? true : false));
            holder.order_num_textview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        selected_orderID = holder.order_num_textview.getText().toString();
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{selected_orderID});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals(""))
                                    {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    }
                                    else
                                    {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    if (selected_orderUUID != null && !selected_orderUUID.equals(""))
                    {
                        new Get_Order_Data().execute();
                    }
                    else
                    {
                        error_dialog.show_error_dialog(getActivity(), "No data found for Order " + selected_orderID);
                    }
                }
            });
            holder.permit_no_textview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        selected_orderID = holder.order_num_textview.getText().toString();
                        permit_no = holder.permit_no_textview.getText().toString();
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{selected_orderID});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals(""))
                                    {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    }
                                    else
                                    {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    if (workComplt(selected_orderID))
                        woco = "X";
                    else
                        woco = "";
                    permit_type = holder.permit_typ.getText().toString();
                    new Get_Permit_Data().execute();
                }
            });
            holder.wcnr_no_textview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        selected_orderID = holder.order_num_textview.getText().toString();
                        permit_no = holder.permit_no_textview.getText().toString();
                        wcnr_no = holder.wcnr_no_textview.getText().toString();
                        refObj = type_details_list.get((Integer) v.getTag()).getRefobj();
                        Cursor cursor = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{selected_orderID});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    selected_orderUUID = cursor.getString(1);
                                    String equnr = cursor.getString(9);
                                    if (equnr != null && !equnr.equals(""))
                                    {
                                        selected_Iwerk = getIwerk(cursor.getString(9));
                                    }
                                    else
                                    {
                                        selected_Iwerk = getfuncIwerk(cursor.getString(10));
                                    }
                                    selected_orderstatus = cursor.getString(39);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    if (workComplt(selected_orderID))
                        woco = "X";
                    else
                        woco = "";
                    permit_type = holder.permit_typ.getText().toString();
                    new Get_Isolation_Data().execute();
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }


    public class Isolation_Object
    {
        private String Order_num;
        private String Pmsog;
        private String Gntxt;
        private String Genvname;
        private String Crname;
        private String Objnr;
        private String date_time;
        private String werk;
        private String counter;
        private String Hilvl;
        private boolean Geniakt_status;
        private String permit_number;
        private String floc_number;
        private String permit_text;
        private String auth_grp;
        private String wcnr;
        private String permit_typ;
        private String refobj;
        private String tagged;
        private String untagged;
        private String iwerk;

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getTagged() {
            return tagged;
        }

        public void setTagged(String tagged) {
            this.tagged = tagged;
        }

        public String getUntagged() {
            return untagged;
        }

        public void setUntagged(String untagged) {
            this.untagged = untagged;
        }

        public String getRefobj() {
            return refobj;
        }

        public void setRefobj(String refobj) {
            this.refobj = refobj;
        }

        public String getWcnr() {
            return wcnr;
        }

        public void setWcnr(String wcnr) {
            this.wcnr = wcnr;
        }

        public String getPermit_typ() {
            return permit_typ;
        }

        public void setPermit_typ(String permit_typ) {
            this.permit_typ = permit_typ;
        }

        public String getPermit_text() {
            return permit_text;
        }

        public void setPermit_text(String permit_text) {
            this.permit_text = permit_text;
        }

        public String getAuth_grp() {
            return auth_grp;
        }

        public void setAuth_grp(String auth_grp) {
            this.auth_grp = auth_grp;
        }

        public String getFloc_number() {
            return floc_number;
        }

        public void setFloc_number(String floc_number) {
            this.floc_number = floc_number;
        }

        public String getPermit_number() {
            return permit_number;
        }

        public void setPermit_number(String permit_number) {
            this.permit_number = permit_number;
        }

        public boolean isGeniakt_status() {
            return Geniakt_status;
        }

        public void setGeniakt_status(boolean geniakt_status) {
            Geniakt_status = geniakt_status;
        }

        public String getHilvl() {
            return Hilvl;
        }

        public void setHilvl(String hilvl) {
            Hilvl = hilvl;
        }

        public String getCounter() {
            return counter;
        }

        public void setCounter(String counter) {
            this.counter = counter;
        }

        public String getWerk() {
            return werk;
        }

        public void setWerk(String werk) {
            this.werk = werk;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getObjnr() {
            return Objnr;
        }

        public void setObjnr(String objnr) {
            Objnr = objnr;
        }

        public String getCrname() {
            return Crname;
        }

        public void setCrname(String crname) {
            Crname = crname;
        }

        public String getGenvname() {
            return Genvname;
        }

        public void setGenvname(String genvname) {
            Genvname = genvname;
        }

        public String getGntxt() {
            return Gntxt;
        }

        public void setGntxt(String gntxt) {
            Gntxt = gntxt;
        }

        public String getOrder_num() {
            return Order_num;
        }

        public void setOrder_num(String order_num) {
            Order_num = order_num;
        }

        public String getPmsog() {
            return Pmsog;
        }

        public void setPmsog(String pmsog) {
            Pmsog = pmsog;
        }

        public Isolation_Object(String Order_num, String Pmsog, String Gntxt, String Genvname, String Crname, String Objnr, String date_time, String werk, String counter, String Hilvl, boolean Geniakt_status, String permit_number, String floc_number, String permit_text, String auth_grp, String permit_typ, String wcnr, String refobj, String tagged, String untagged, String iwerk) {
            this.Order_num = Order_num;
            this.Pmsog = Pmsog;
            this.Gntxt = Gntxt;
            this.Genvname = Genvname;
            this.Crname = Crname;
            this.Objnr = Objnr;
            this.date_time = date_time;
            this.werk = werk;
            this.counter = counter;
            this.Hilvl = Hilvl;
            this.Geniakt_status = Geniakt_status;
            this.permit_number = permit_number;
            this.floc_number = floc_number;
            this.permit_text = permit_text;
            this.auth_grp = auth_grp;
            this.permit_typ = permit_typ;
            this.wcnr = wcnr;
            this.refobj = refobj;
            this.tagged = tagged;
            this.untagged = untagged;
            this.iwerk = iwerk;
        }
    }


    private class Get_Order_Data extends AsyncTask<Void, Integer, Void>
    {
        OrdrHeaderPrcbl ohp = new OrdrHeaderPrcbl();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.get_order_data));
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            ohp = GetOrderDetail.GetData(getActivity(), selected_orderUUID,selected_orderID, selected_orderstatus, selected_Iwerk);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            custom_progress_dialog.dismiss_progress_dialog();
            if (ohp != null)
            {
                Intent ordrIntent = new Intent(getActivity(), Orders_Change_Activity.class);
                ordrIntent.putExtra("order", "U");
                ordrIntent.putExtra("ordr_parcel", ohp);
                startActivity(ordrIntent);
            }
        }
    }


    private class Get_Permit_Data extends AsyncTask<Void, Integer, Void>
    {
        ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<OrdrPermitPrcbl>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.get_order_data));
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            ww_al = Get_Permit_Detail.GetData(getActivity(), selected_orderUUID,selected_orderID, selected_orderstatus, selected_Iwerk);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            custom_progress_dialog.dismiss_progress_dialog();
            if (ww_al != null)
            {
                Intent ordrIntent = new Intent(getActivity(), Permits_Add_Update_Activity.class);
                ordrIntent.putExtra("order", selected_orderID);
                ordrIntent.putExtra("opp", ww_al);
                ordrIntent.putExtra("woco", woco);
                ordrIntent.putExtra("func_loc", funcId(selected_orderID));
                ordrIntent.putExtra("equip", equipId(selected_orderID));
                ordrIntent.putExtra("equip_txt", equipName(selected_orderID));
                ordrIntent.putExtra("iwerk", selected_Iwerk);
                ordrIntent.putExtra("wapinr", permit_no);
                ordrIntent.putExtra("flag", "CH");
                startActivity(ordrIntent);
            }
        }
    }


    private class Get_Isolation_Data extends AsyncTask<Void, Integer, Void>
    {
        ArrayList<OrdrPermitPrcbl> wd_al = new ArrayList<OrdrPermitPrcbl>();
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(getActivity(), getResources().getString(R.string.get_order_data));
        }
        @Override
        protected Void doInBackground(Void... voids)
        {
            wd_al = Get_Isolation_Detail.GetData(getActivity(),selected_orderID, refObj, selected_Iwerk);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            custom_progress_dialog.dismiss_progress_dialog();
            if (wd_al != null)
            {
                Intent ordrIntent = new Intent(getActivity(), Isolation_Add_Update_Activity.class);
                ordrIntent.putExtra("order", selected_orderID);
                ordrIntent.putExtra("iso", wd_al);
                ordrIntent.putExtra("woco", woco);
                ordrIntent.putExtra("func_loc", funcId(selected_orderID));
                ordrIntent.putExtra("equip", equipId(selected_orderID));
                ordrIntent.putExtra("equip_txt", equipName(selected_orderID));
                ordrIntent.putExtra("iwerk", selected_Iwerk);
                ordrIntent.putExtra("wapinr", permit_no);
                ordrIntent.putExtra("flag", "CH");
                ordrIntent.putExtra("switch_tab", "T");
                startActivity(ordrIntent);
            }
        }
    }


    private String getIwerk(String equip) {
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtEqui where Equnr = ?", new String[]{equip});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(29);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }


    private String getfuncIwerk(String func) {
        Cursor cursor = null;
        try {
            cursor = FieldTekPro_db.rawQuery("select * from EtFuncEquip where Tplnr = ?", new String[]{func});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(14);
                }
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return "";
    }


    private boolean workComplt(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and Txt04 = ?", new String[]{OrderNo, "WOCO"});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        if (cursor1.getString(11).equalsIgnoreCase("X"))
                            return true;
                        else
                            return false;
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return false;
    }


    private String funcId(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(11);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }


    private String equipId(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(9);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }


    private String equipName(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from DUE_ORDERS_EtOrderHeader where Aufnr = ?", new String[]{OrderNo});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        return cursor1.getString(32);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor1 != null)
                cursor1.close();
        }
        return "";
    }


    public void refresh_untag_untagged_data(final String funcloc, final String plant)
    {
        filter_funcloc = funcloc;
        filter_plant = plant;
        new Get_Isolation_List_Data().execute();
    }


}
