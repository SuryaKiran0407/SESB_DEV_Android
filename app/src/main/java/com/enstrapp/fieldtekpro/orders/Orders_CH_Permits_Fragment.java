package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class Orders_CH_Permits_Fragment extends Fragment {

    ArrayList<OrdrPermitPrcbl> Wa_al = new ArrayList<OrdrPermitPrcbl>();
    Orders_Change_Activity ma;
    Error_Dialog errorDialog = new Error_Dialog();
    Custom_Progress_Dialog customProgressDialog = new Custom_Progress_Dialog();
    private List<Type_Object> type_list = new ArrayList<>();
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";
    TextView title_textview, no_data_textview, searchview_textview;
    SearchView search;
    TYPE_ADAPTER adapter;
    LinearLayout no_data_layout;
    RecyclerView list_recycleview, recyclerView;
    static final int PERMIT = 40;
    static final int UPDATE = 41;
    Dialog create_wcm_appl_type_dialog;
    ArrayList<OrdrPermitPrcbl> ww_al = new ArrayList<>();
    ArrayList<OrdrPermitPrcbl> wa_al = new ArrayList<>();
    ApplicationAdapter applicationAdapter;
    String Refobj = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container,
                false);
        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        ma = (Orders_Change_Activity) this.getActivity();

        if (ma.ohp.getOrdrPermitPrcbls() != null) {
            ww_al.clear();
            ww_al.addAll(ma.ohp.getOrdrPermitPrcbls());
            if (ww_al.size() > 0)
                if (ma.ohp.getOrdrPermitPrcbls().get(0).getWaWdPrcbl_Al() != null) {
                    if (ma.ohp.getOrdrPermitPrcbls().get(0).getWaWdPrcbl_Al().size() > 0) {
                        wa_al.addAll(ma.ohp.getOrdrPermitPrcbls().get(0).getWaWdPrcbl_Al());
                        Refobj = wa_al.get(0).getRefobj();
                        applicationAdapter = new ApplicationAdapter(getActivity(), wa_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(applicationAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
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

        if (workComplt(ma.ohp.getOrdrId())) {
            ma.fab.hide();
        } else {
            ma.animateFab(false);
            String auth_status1 = Authorizations.Get_Authorizations_Data(getActivity(), "C",
                    "I");
            if (auth_status1.equalsIgnoreCase("true")) {
                String auth_status2 = Authorizations.Get_Authorizations_Data(getActivity(),
                        "C", "U");
                if (auth_status2.equalsIgnoreCase("true")) {
                    ma.fab.show();
                } else {
                    ma.fab.hide();
                }
            } else {
                ma.fab.hide();
            }
        }

        ma.fab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (ma.ohp.getOrdrShrtTxt() != null && !ma.ohp.getOrdrShrtTxt().equals("")) {
                    if (ma.ohp.getEquipNum() != null || ma.ohp.getFuncLocId() != null) {
                        if (ma.ohp.getPriorityId() != null && !ma.ohp.getPriorityId().equals("")) {
                            applicationTypes();
                        } else {
                            errorDialog.show_error_dialog(getActivity(), getResources().getString(R.string.priority_mandate));
                        }
                    } else {
                        errorDialog.show_error_dialog(getActivity(),
                                getResources().getString(R.string.equipFunc_mandate));
                    }
                } else {
                    errorDialog.show_error_dialog(getActivity(),
                            getResources().getString(R.string.text_mandate));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PERMIT):
                if (resultCode == RESULT_OK) {
                    ArrayList<OrdrPermitPrcbl> wa_al_d = new ArrayList<>();
                    OrdrPermitPrcbl wa = new OrdrPermitPrcbl();
                    ww_al = data.getParcelableArrayListExtra("opp");
                    if (ww_al != null) {
                        if (ww_al.size() > 0) {
                            wa_al = new ArrayList<>();
                            wa_al.addAll(ww_al.get(0).getWaWdPrcbl_Al());
                            ma.ohp.setOrdrPermitPrcbls(ww_al);
                            applicationAdapter = new ApplicationAdapter(getActivity(), wa_al);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(applicationAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            OrdrPermitPrcbl wwp = new OrdrPermitPrcbl();
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                            SimpleDateFormat sdf = new SimpleDateFormat("HH");
                            final int time = Integer.parseInt(sdf.format(new Date()));

                            wwp.setWapnr("WW0001");
                            wwp.setStxt(ma.ohp.getOrdrShrtTxt());
                            if (time >= 8) {
                                wwp.setTimefr("08:00:00");
                                wwp.setTimeto("08:00:00");
                                wwp.setDatefr(df.format(c.getTime()));
                                wwp.setDateto(GetFutureDate());
                            } else if (time < 8) {
                                wwp.setTimefr("08:00:00");
                                wwp.setTimeto("08:00:00");
                                wwp.setDatefr(GetPreviousDate());
                                wwp.setDateto(df.format(c.getTime()));
                            }

                            wwp.setPriok(ma.ohp.getPriorityId());
                            wwp.setPriokx(ma.ohp.getPriorityTxt());
                            wwp.setCrea("X");
                            wwp.setAction("I");

                            wa.setAction("I");
                            wa_al_d.add(wa);
                            wwp.setWaWdPrcbl_Al(wa_al_d);
                            wa_al.addAll(wwp.getWaWdPrcbl_Al());
                            ww_al.add(wwp);
                            ma.ohp.setOrdrPermitPrcbls(ww_al);

                            applicationAdapter = new ApplicationAdapter(getActivity(), wa_al);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(applicationAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        OrdrPermitPrcbl wwp = new OrdrPermitPrcbl();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                        SimpleDateFormat sdf = new SimpleDateFormat("HH");
                        final int time = Integer.parseInt(sdf.format(new Date()));

                        wwp.setWapnr("WW0001");
                        wwp.setStxt(ma.ohp.getOrdrShrtTxt());
                        if (time >= 8) {
                            wwp.setTimefr("080000");
                            wwp.setTimeto("080000");
                            wwp.setDatefr(df.format(c.getTime()));
                            wwp.setDateto(GetFutureDate());
                        } else if (time < 8) {
                            wwp.setTimefr("08:00:00");
                            wwp.setTimeto("08:00:00");
                            wwp.setDatefr(GetPreviousDate());
                            wwp.setDateto(df.format(c.getTime()));
                        }

                        wwp.setPriok(ma.ohp.getPriorityId());
                        wwp.setPriokx(ma.ohp.getPriorityTxt());
                        wwp.setCrea("X");
                        wwp.setAction("I");

                        wa.setAction("I");
                        wa_al_d.add(wa);
                        wwp.setWaWdPrcbl_Al(wa_al_d);
                        wa_al.addAll(wwp.getWaWdPrcbl_Al());
                        ww_al.add(wwp);
                        ma.ohp.setOrdrPermitPrcbls(ww_al);

                        applicationAdapter = new ApplicationAdapter(getActivity(), wa_al);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(applicationAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            case (UPDATE):
                if (resultCode == RESULT_OK) {
                    OrdrPermitPrcbl wa = new OrdrPermitPrcbl();
                    wa_al.clear();
                    ww_al = data.getParcelableArrayListExtra("opp");
                    wa_al.addAll(ww_al.get(0).getWaWdPrcbl_Al());
                    ma.ohp.setOrdrPermitPrcbls(ww_al);
                    Collections.sort(wa_al, new Comparator<OrdrPermitPrcbl>() {
                        public int compare(OrdrPermitPrcbl oop1, OrdrPermitPrcbl oop2) {
                            return oop1.getWapinr().compareToIgnoreCase(oop2.getWapinr());
                        }
                    });
                    applicationAdapter = new ApplicationAdapter(getActivity(), wa_al);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(applicationAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<OrdrPermitPrcbl> walist;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView perimtId_tv, permitName_tv, appr_tv, validFrm_tv, validTo_tv;
            LinearLayout permit_ll;
            ImageView permitStatus_iv, issueStatus1_iv, issueStatus2_iv, issueStatus3_iv;

            public MyViewHolder(View view) {
                super(view);
                perimtId_tv = view.findViewById(R.id.perimtId_tv);
                permitName_tv = view.findViewById(R.id.permitName_tv);
                permit_ll = view.findViewById(R.id.permit_ll);
                permitStatus_iv = view.findViewById(R.id.permitStatus_iv);
                issueStatus1_iv = view.findViewById(R.id.issueStatus1_iv);
                issueStatus2_iv = view.findViewById(R.id.issueStatus2_iv);
                issueStatus3_iv = view.findViewById(R.id.issueStatus3_iv);
                appr_tv = view.findViewById(R.id.appr_tv);
                validFrm_tv = view.findViewById(R.id.validFrm_tv);
                validTo_tv = view.findViewById(R.id.validTo_tv);
            }
        }

        public ApplicationAdapter(Context mContext, ArrayList<OrdrPermitPrcbl> list) {
            this.mContext = mContext;
            this.walist = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.permit_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final OrdrPermitPrcbl wapo = walist.get(position);
            holder.perimtId_tv.setText(wapo.getWapinr());
            holder.permitName_tv.setText(wapo.getApplName());
            holder.validTo_tv.setText(dateFormat(wapo.getDateto()));
            holder.validFrm_tv.setText(dateFormat(wapo.getDatefr()));

            if (wapo.getComp().equals("X")) {
                holder.permitStatus_iv.setImageDrawable(getResources()
                        .getDrawable(R.drawable.permit_status_comp, null));
            } else if (wapo.getPrep().equals("X")) {
                holder.permitStatus_iv.setImageDrawable(getResources()
                        .getDrawable(R.drawable.permit_status_prep, null));
            } else {
                holder.permitStatus_iv.setImageDrawable(getResources()
                        .getDrawable(R.drawable.permit_status_crea, null));
            }

            if (!wapo.getAction().equals("I"))
                if (wapo.getAppr().equals("G")) {
                    holder.issueStatus1_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                    holder.issueStatus2_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                    holder.issueStatus3_iv.setImageResource(R.drawable.ic_completed_circle_icon);
                } else if (wapo.getAppr().equals("Y")) {
                    holder.issueStatus1_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                    holder.issueStatus2_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                    holder.issueStatus3_iv.setImageResource(R.drawable.ic_pending_circle_icon);
                } else if (wapo.getAppr().equals("R")) {
                    holder.issueStatus1_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    holder.issueStatus2_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    holder.issueStatus3_iv.setImageResource(R.drawable.ic_create_circle_icon);
                } else {
                    holder.issueStatus1_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    holder.issueStatus2_iv.setImageResource(R.drawable.ic_create_circle_icon);
                    holder.issueStatus3_iv.setImageResource(R.drawable.ic_create_circle_icon);
                }
            holder.permit_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String woco = "";
                    if (workComplt(ma.ohp.getOrdrId()))
                        woco = "X";
                    else
                        woco = "";
                    Intent intent = new Intent(getActivity(), Permits_Add_Update_Activity.class);
                    intent.putExtra("shrt_txt", ma.ohp.getOrdrShrtTxt());
                    intent.putExtra("func_loc", ma.ohp.getFuncLocId());
                    intent.putExtra("priority_id", ma.ohp.getPriorityId());
                    intent.putExtra("priority_txt", ma.ohp.getPriorityTxt());
                    intent.putExtra("equip", ma.ohp.getEquipNum());
                    intent.putExtra("equip_txt", ma.ohp.getEquipName());
                    intent.putExtra("iwerk", ma.ohp.getIwerk());
                    intent.putExtra("woco", woco);
                    intent.putExtra("opp", ww_al);
                    intent.putExtra("wapinr", wapo.getWapinr());
                    intent.putExtra("flag", "CH");
                    intent.putExtra("Refobj", Refobj);
                    intent.putExtra("order", ma.ohp.getOrdrId());
                    intent.putExtra("UUID", ma.ohp.getOrdrUUId());
                    startActivityForResult(intent, UPDATE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return walist.size();
        }
    }

    private void applicationTypes() {
        create_wcm_appl_type_dialog = new Dialog(getActivity(), R.style.AppThemeDialog_Dark);
        create_wcm_appl_type_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        create_wcm_appl_type_dialog.setCancelable(true);
        create_wcm_appl_type_dialog.setCanceledOnTouchOutside(false);
        create_wcm_appl_type_dialog.setContentView(R.layout.f4_list_activity);
        create_wcm_appl_type_dialog
                .getWindow().getAttributes().windowAnimations = R.style.ErrorDialog;
        title_textview = create_wcm_appl_type_dialog.findViewById(R.id.title_textview);
        no_data_textview = create_wcm_appl_type_dialog.findViewById(R.id.no_data_textview);
        ImageView back_imageview = create_wcm_appl_type_dialog.findViewById(R.id.back_imageview);
        search = create_wcm_appl_type_dialog.findViewById(R.id.search);
        list_recycleview = create_wcm_appl_type_dialog.findViewById(R.id.list_recycleview);
        no_data_layout = create_wcm_appl_type_dialog.findViewById(R.id.no_data_layout);
        back_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_wcm_appl_type_dialog.dismiss();
            }
        });
        new Get_Types_Data().execute();
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text",
                null, null);
        search.setQueryHint("Search...");
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/metropolis_medium.ttf");
        searchview_textview = (TextView) search.findViewById(id);
        searchview_textview.setTextColor(getResources().getColor(R.color.black));
        search.setBaselineAligned(false);
        searchview_textview.setTypeface(myCustomFont);
        searchview_textview.setTextSize(16);
        EditText searchEditText = (EditText) search.findViewById(id);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        create_wcm_appl_type_dialog.show();
    }

    private class Get_Types_Data extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.show_progress_dialog(getActivity(), getResources().getString(R.string.loading));
            type_list.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtWcmTypes where Iwerk" +
                        " = ?", new String[]{ma.ohp.getIwerk()});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Type_Object nto = new Type_Object(cursor.getString(3),
                                    cursor.getString(4));
                            type_list.add(nto);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (type_list.size() > 0) {
                title_textview.setText(getResources().getString(R.string.permit_type) + " (" + type_list.size() + ")");
                adapter = new TYPE_ADAPTER(getActivity(), type_list);
                list_recycleview.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                list_recycleview.setLayoutManager(layoutManager);
                list_recycleview.setItemAnimator(new DefaultItemAnimator());
                list_recycleview.setAdapter(adapter);
                search.setOnQueryTextListener(listener);
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                title_textview.setText(getResources().getString(R.string.permit_type) + " (0)");
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            customProgressDialog.dismiss_progress_dialog();
        }
    }

    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();
            final List<Type_Object> filteredList = new ArrayList<>();
            for (int i = 0; i < type_list.size(); i++) {
                String id = type_list.get(i).getId().toLowerCase();
                String value = type_list.get(i).getText().toLowerCase();
                if (id.contains(query) || value.contains(query)) {
                    Type_Object nto = new Type_Object(type_list.get(i).getId(),
                            type_list.get(i).getText());
                    filteredList.add(nto);
                }
            }
            if (filteredList.size() > 0) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                list_recycleview.setLayoutManager(layoutManager);
                adapter = new TYPE_ADAPTER(getActivity(), filteredList);
                list_recycleview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                no_data_textview.setVisibility(View.GONE);
                list_recycleview.setVisibility(View.VISIBLE);
                no_data_layout.setVisibility(View.GONE);
            } else {
                no_data_textview.setVisibility(View.VISIBLE);
                list_recycleview.setVisibility(View.GONE);
                no_data_layout.setVisibility(View.VISIBLE);
            }
            return true;
        }

        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };

    public class TYPE_ADAPTER extends RecyclerView.Adapter<TYPE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Type_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView id_textview, value_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                id_textview = (TextView) view.findViewById(R.id.id_textview);
                value_textview = (TextView) view.findViewById(R.id.text_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
            }
        }

        public TYPE_ADAPTER(Context mContext, List<Type_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.f4_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Type_Object nto = type_details_list.get(position);
            holder.id_textview.setText(nto.getId());
            holder.value_textview.setText(nto.getText());

            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String wapinr = "";
                    if (ww_al != null) {
                        if (ww_al.size() > 0) {
                            if (ww_al.get(0).getWaWdPrcbl_Al() != null)
                                if (ww_al.get(0).getWaWdPrcbl_Al().size() > 0) {
                                    wapinr = "WA000" + (ww_al.get(0).getWaWdPrcbl_Al().size() + 1);
                                } else {
                                    wapinr = "WA0001";
                                }
                            else
                                wapinr = "WA0001";
                        } else {
                            ww_al = new ArrayList<>();
                            OrdrPermitPrcbl wwp = new OrdrPermitPrcbl();
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                            SimpleDateFormat sdf = new SimpleDateFormat("HH");
                            final int time = Integer.parseInt(sdf.format(new Date()));

                            wwp.setAufnr(ma.ohp.getOrdrId());
                            wwp.setWapnr("WW0001");
                            wwp.setStxt(ma.ohp.getOrdrShrtTxt());
                            if (time >= 8) {
                                wwp.setTimefr("080000");
                                wwp.setTimeto("080000");
                                wwp.setDatefr(df.format(c.getTime()));
                                wwp.setDateto(GetFutureDate());
                            } else if (time < 8) {
                                wwp.setTimefr("08:00:00");
                                wwp.setTimeto("08:00:00");
                                wwp.setDatefr(GetPreviousDate());
                                wwp.setDateto(df.format(c.getTime()));
                            }

                            wwp.setPriok(ma.ohp.getPriorityId());
                            wwp.setPriokx(ma.ohp.getPriorityTxt());
                            wwp.setCrea("X");
                            wwp.setAction("I");

                            ww_al.add(wwp);
                            ma.ohp.setOrdrPermitPrcbls(ww_al);
                            wapinr = "WA001";
                        }
                    } else {
                        ww_al = new ArrayList<>();
                        OrdrPermitPrcbl wwp = new OrdrPermitPrcbl();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");
                        SimpleDateFormat sdf = new SimpleDateFormat("HH");
                        final int time = Integer.parseInt(sdf.format(new Date()));

                        wwp.setAufnr(ma.ohp.getOrdrId());
                        wwp.setWapnr("WW0001");
                        wwp.setStxt(ma.ohp.getOrdrShrtTxt());
                        if (time >= 8) {
                            wwp.setTimefr("080000");
                            wwp.setTimeto("080000");
                            wwp.setDatefr(df.format(c.getTime()));
                            wwp.setDateto(GetFutureDate());
                        } else if (time < 8) {
                            wwp.setTimefr("08:00:00");
                            wwp.setTimeto("08:00:00");
                            wwp.setDatefr(GetPreviousDate());
                            wwp.setDateto(df.format(c.getTime()));
                        }

                        wwp.setPriok(ma.ohp.getPriorityId());
                        wwp.setPriokx(ma.ohp.getPriorityTxt());
                        wwp.setCrea("X");
                        wwp.setAction("I");

                        ww_al.add(wwp);
                        ma.ohp.setOrdrPermitPrcbls(ww_al);
                        wapinr = "WA001";
                    }
                    String woco = "";
                    if (workComplt(ma.ohp.getOrdrId()))
                        woco = "X";
                    else
                        woco = "";
                    Intent intent = new Intent(getActivity(), Permits_Add_Update_Activity.class);
                    intent.putExtra("permit_id", holder.id_textview.getText().toString());
                    intent.putExtra("permit_txt", holder.value_textview.getText().toString());
                    intent.putExtra("shrt_txt", ma.ohp.getOrdrShrtTxt());
                    intent.putExtra("func_loc", ma.ohp.getFuncLocId());
                    intent.putExtra("priority_id", ma.ohp.getPriorityId());
                    intent.putExtra("priority_txt", ma.ohp.getPriorityTxt());
                    intent.putExtra("equip", ma.ohp.getEquipNum());
                    intent.putExtra("equip_txt", ma.ohp.getEquipName());
                    intent.putExtra("iwerk", ma.ohp.getIwerk());
                    intent.putExtra("wapinr", wapinr);
                    intent.putExtra("opp", ww_al);
                    intent.putExtra("woco", woco);
                    intent.putExtra("flag", "CH");
                    intent.putExtra("Refobj", Refobj);
                    intent.putExtra("order", ma.ohp.getOrdrId());
                    intent.putExtra("UUID", ma.ohp.getOrdrUUId());
                    create_wcm_appl_type_dialog.dismiss();
                    startActivityForResult(intent, PERMIT);
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public class Type_Object {
        private String id;
        private String text;

        public Type_Object(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    private String GetFutureDate() {
        String new_date = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            new_date = new SimpleDateFormat("MMM dd, yyyy").format(cal.getTime());

        } catch (Exception e) {
        }
        return new_date;
    }

    private String GetPreviousDate() {
        String new_date = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            new_date = new SimpleDateFormat("MMM dd, yyyy").format(cal.getTime());

        } catch (Exception e) {
        }
        return new_date;
    }

    private boolean workComplt(String OrderNo) {
        Cursor cursor1 = null;
        try {
            cursor1 = FieldTekPro_db.rawQuery("select * from EtOrderStatus where Aufnr = ? and" +
                    " Txt04 = ?", new String[]{OrderNo, "WOCO"});
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

    private static String dateFormat(String date) {
        if (date != null && !date.equals("")) {
            if (date.equals("00000000"))
                return date;
            String outputPattern1 = "MMM dd, yyyy";
            String inputPattern1 = "yyyyMMdd";
            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);
            try {
                Date date1 = inputFormat1.parse(date);
                String Datefr = outputFormat1.format(date1);
                return Datefr;
            } catch (ParseException e) {
                return date;
            }
        } else {
            return "";
        }
    }
}
