package com.enstrapp.fieldtekpro.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Notifications_Change_Activity_Fragment extends Fragment {

    private List<Activity_Object> activity_list = new ArrayList<>();
    private List<Activity_Object> activity_list_delete = new ArrayList<>();
    List cc_list = new ArrayList();
    String selected_pos = "", selected_status = "", code_id = "", code_text = "", shttext = "",
            start_date = "", start_date_formatted = "", start_time = "", start_time_formatted = "",
            end_date = "", end_date_formatted = "", end_time = "", end_time_formatted = "",
            codegroup_id = "", codegroup_text = "", objectpartcode_id = "", cause_itemkey = "",
            cause_text = "", item_key = "0001", event_id = "";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ACTIVITY_ADAPTER activity_adapter;
    TextView noData_tv;
    Error_Dialog error_dialog = new Error_Dialog();
    int add_activity_type = 1, selected_position = 0;
    ArrayList<HashMap<String, String>> causecode_array_list = new ArrayList<HashMap<String, String>>();
    NotifHeaderPrcbl nhp = new NotifHeaderPrcbl();
    ArrayList<NotifCausCodActvPrcbl> activity_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    ArrayList<HashMap<String, String>> selected_activity_custom_info_arraylist = new ArrayList<>();
    int count = 0;
    boolean isSelected = false;
    Notifications_Change_Activity nca;

    public Notifications_Change_Activity_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_activities_fragment, container, false);

        noData_tv = (TextView) rootView.findViewById(R.id.noData_tv);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nca = (Notifications_Change_Activity) this.getActivity();

        recyclerView.setVisibility(View.GONE);
        noData_tv.setVisibility(View.VISIBLE);

        activity_list.clear();
        activity_list_delete.clear();

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            nhp = bundle.getParcelable("notif_parcel");
            activity_parcablearray = nhp.getActvPrcblAl();
            if (activity_parcablearray.size() > 0) {
                for (int i = 0; i < activity_parcablearray.size(); i++) {

                    String ActvKey = activity_parcablearray.get(i).getActvKey();
                    selected_activity_custom_info_arraylist.clear();
                    try {
                        Cursor cursor = App_db.rawQuery("select * from " +
                                        "EtNotifActivity_CustomInfo where Qmnum = ? and ActvKey = ?",
                                new String[]{nhp.getQmnum(), ActvKey});
                        if (cursor != null && cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                                    custom_info_hashMap.put("Fieldname", cursor.getString(7));
                                    custom_info_hashMap.put("ZdoctypeItem", cursor.getString(5));
                                    custom_info_hashMap.put("Datatype", cursor.getString(12));
                                    custom_info_hashMap.put("Tabname", cursor.getString(6));
                                    custom_info_hashMap.put("Zdoctype", cursor.getString(4));
                                    custom_info_hashMap.put("Sequence", cursor.getString(10));
                                    custom_info_hashMap.put("Flabel", cursor.getString(9));
                                    custom_info_hashMap.put("Spras", "");
                                    custom_info_hashMap.put("Length", cursor.getString(11));
                                    String datatype = cursor.getString(12);
                                    String value = cursor.getString(8);
                                    if (datatype.equalsIgnoreCase("DATS")) {
                                        if (value.equalsIgnoreCase("00000000")) {
                                            custom_info_hashMap.put("Value", "");
                                        } else {
                                            String inputPattern = "yyyyMMdd";
                                            String outputPattern = "MMM dd, yyyy";
                                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                            try {
                                                Date date = inputFormat.parse(value);
                                                String formatted_date = outputFormat.format(date);
                                                custom_info_hashMap.put("Value", formatted_date);
                                            } catch (Exception e) {
                                                custom_info_hashMap.put("Value", "");
                                            }
                                        }

                                    } else if (datatype.equalsIgnoreCase("TIMS")) {
                                        if (value.equalsIgnoreCase("000000")) {
                                            custom_info_hashMap.put("Value", "");
                                        } else {
                                            String inputPattern = "HHmmss";
                                            String outputPattern = "HH:mm:ss";
                                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                            try {
                                                Date date = inputFormat.parse(value);
                                                String formatted_time = outputFormat.format(date);
                                                custom_info_hashMap.put("Value", formatted_time);
                                            } catch (Exception e) {
                                                custom_info_hashMap.put("Value", "");
                                            }
                                        }
                                    } else {
                                        custom_info_hashMap.put("Value", cursor.getString(8));
                                    }
                                    selected_activity_custom_info_arraylist.add(custom_info_hashMap);
                                }
                                while (cursor.moveToNext());
                            }
                        } else {
                            cursor.close();
                        }
                    } catch (Exception e) {
                    }

                    Activity_Object to = new Activity_Object(
                            activity_parcablearray.get(i).getItmKey(),
                            activity_parcablearray.get(i).getItmDefectShTxt(),
                            activity_parcablearray.get(i).getActvKey(),
                            activity_parcablearray.get(i).getPrtGrpTxt(),
                            activity_parcablearray.get(i).getItmPrtCod(),
                            activity_parcablearray.get(i).getActvGrp(),
                            activity_parcablearray.get(i).getActGrpTxt(),
                            activity_parcablearray.get(i).getActvCod(),
                            activity_parcablearray.get(i).getActvCodTxt(),
                            activity_parcablearray.get(i).getActvShTxt(),
                            activity_parcablearray.get(i).getUsr01(),
                            activity_parcablearray.get(i).getUsr02(),
                            activity_parcablearray.get(i).getUsr03(),
                            activity_parcablearray.get(i).getUsr04(),
                            activity_parcablearray.get(i).getStatus(),
                            selected_activity_custom_info_arraylist,
                            false
                    );
                    activity_list.add(to);
                }
            }
            if (activity_list.size() > 0) {
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                activity_adapter = new ACTIVITY_ADAPTER(getActivity(), activity_list);
                recyclerView.setAdapter(activity_adapter);
                recyclerView.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            }
            nca.updateTabDataCount();
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
        String xstatus = nhp.getXStatus();
        if (xstatus.equalsIgnoreCase("NOCO")) {
            String auth_status = Authorizations.Get_Authorizations_Data(getActivity(),
                    "Q", "U");
            if (auth_status.equalsIgnoreCase("true")) {
                if (nhp.getQmnum().startsWith("NOT")) {
                    nca.fab.hide();
                } else {
                    nca.fab.show();
                }
            } else {
                nca.fab.hide();
            }
        } else {
            String auth_status = Authorizations.Get_Authorizations_Data(getActivity(),
                    "Q", "U");
            if (auth_status.equalsIgnoreCase("true")) {
                if (nhp.getQmnum().startsWith("NOT")) {
                    nca.fab.hide();
                } else {
                    nca.fab.show();
                }
            } else {
                nca.fab.hide();
            }
            nca.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected) {
                        final Dialog delete_decision_dialog = new Dialog(getActivity());
                        delete_decision_dialog.getWindow()
                                .setBackgroundDrawableResource(android.R.color.transparent);
                        delete_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        delete_decision_dialog.setCancelable(false);
                        delete_decision_dialog.setCanceledOnTouchOutside(false);
                        delete_decision_dialog.setContentView(R.layout.decision_dialog);
                        TextView description_textview = delete_decision_dialog
                                .findViewById(R.id.description_textview);
                        description_textview.setText(getString(R.string.activity_delete));
                        Button ok_button = delete_decision_dialog.findViewById(R.id.yes_button);
                        Button cancel_button = delete_decision_dialog.findViewById(R.id.no_button);
                        delete_decision_dialog.show();
                        ok_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < activity_list.size(); i++) {
                                    boolean selected_status = activity_list.get(i).isSelected();
                                    if (selected_status) {
                                        String action = activity_list.get(i).getStatus();
                                        if (action.equalsIgnoreCase("U")) {
                                            Activity_Object to = new Activity_Object(
                                                    activity_list.get(i).getCause_itemkey(),
                                                    activity_list.get(i).getCause_shtxt(),
                                                    activity_list.get(i).getActivity_itemkey(),
                                                    activity_list.get(i).getObj_part(),
                                                    activity_list.get(i).getEvent_code(),
                                                    activity_list.get(i).getCodegroup_id(),
                                                    activity_list.get(i).getCodegroup_text(),
                                                    activity_list.get(i).getCode_id(),
                                                    activity_list.get(i).getCode_text(),
                                                    activity_list.get(i).getCause_shtxt(),
                                                    activity_list.get(i).getSt_date(),
                                                    activity_list.get(i).getEnd_date(),
                                                    activity_list.get(i).getSt_time(),
                                                    activity_list.get(i).getEnd_time(),
                                                    "D",
                                                    activity_list.get(i).getSelected_activity_custom_info_arraylist(),
                                                    false);
                                            activity_list_delete.add(to);
                                            activity_list.remove(i);
                                        } else {
                                            activity_list.remove(i);
                                        }
                                    }
                                }

                                nca.animateFab(false);
                                isSelected = false;

                                if (activity_list.size() > 0) {
                                    activity_adapter = new ACTIVITY_ADAPTER(getActivity(), activity_list);
                                    recyclerView.setAdapter(activity_adapter);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    noData_tv.setVisibility(View.GONE);
                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    noData_tv.setVisibility(View.VISIBLE);
                                }
                                nca.updateTabDataCount();
                                delete_decision_dialog.dismiss();
                            }
                        });
                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete_decision_dialog.dismiss();
                            }
                        });
                    } else {
                        Notifications_Change_Header_Fragment header_tab =
                                (Notifications_Change_Header_Fragment) getFragmentManager()
                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                        Notifications_Create_Header_Object header_data = header_tab.getData();
                        String functionlocation_id = header_data.getFunctionlocation_id();
                        String equipment_id = header_data.getEquipment_id();
                        Notifications_Change_Causecode_Fragment causecode_fragment =
                                (Notifications_Change_Causecode_Fragment) getFragmentManager()
                                        .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                        List<Notifications_Change_Causecode_Fragment.Cause_Code_Object>
                                causecode_list = causecode_fragment.getCauseCodeData();
                        if (causecode_list.size() > 0) {
                            causecode_array_list.clear();
                            for (int i = 0; i < causecode_list.size(); i++) {
                                HashMap<String, String> array_hashmap = new HashMap<String, String>();
                                array_hashmap.put("itemkey", causecode_list.get(i).getitem_key());
                                array_hashmap.put("objpart_id", causecode_list.get(i).getobject_part_id());
                                array_hashmap.put("event_id", causecode_list.get(i).getevent_id());
                                array_hashmap.put("event_desc", causecode_list.get(i).getevent_desc());
                                causecode_array_list.add(array_hashmap);
                            }
                            Intent intent = new Intent(getActivity(),
                                    Notifications_Activity_Add_Activity.class);
                            intent.putExtra("functionlocation_id", functionlocation_id);
                            intent.putExtra("equipment_id", equipment_id);
                            intent.putExtra("causecode_array_list", causecode_array_list);
                            intent.putExtra("request_id", Integer.toString(add_activity_type));
                            intent.putExtra("status", "I");
                            startActivityForResult(intent, add_activity_type);
                        } else {
                            error_dialog.show_error_dialog(getActivity(),
                                    getString(R.string.activity_addnotifitem));
                        }
                    }
                }
            });
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 1) {
                cause_itemkey = data.getStringExtra("cause_itemkey");
                cause_text = data.getStringExtra("cause_text");
                objectpartcode_id = data.getStringExtra("objectpartcode_id");
                event_id = data.getStringExtra("event_id");
                codegroup_id = data.getStringExtra("codegroup_id");
                codegroup_text = data.getStringExtra("codegroup_text");
                code_id = data.getStringExtra("code_id");
                code_text = data.getStringExtra("code_text");
                shttext = data.getStringExtra("shttext");
                start_date = data.getStringExtra("start_date");
                start_date_formatted = data.getStringExtra("start_date_formatted");
                start_time = data.getStringExtra("start_time");
                start_time_formatted = data.getStringExtra("start_time_formatted");
                end_date = data.getStringExtra("end_date");
                end_date_formatted = data.getStringExtra("end_date_formatted");
                end_time = data.getStringExtra("end_time");
                end_time_formatted = data.getStringExtra("end_time_formatted");
                selected_activity_custom_info_arraylist.clear();
                selected_activity_custom_info_arraylist =
                        (ArrayList<HashMap<String, String>>) data
                                .getSerializableExtra("selected_activity_custom_info_arraylist");
                String itemkey = data.getStringExtra("activity_itemkey");
                String status = data.getStringExtra("status");
                if (itemkey != null && !itemkey.equals("")) {
                    if (status.equalsIgnoreCase("U")) {
                        selected_status = "U";
                        item_key = data.getStringExtra("activity_itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        activity_list.remove(selected_position);
                    } else {
                        selected_status = "I";
                        item_key = data.getStringExtra("activity_itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        activity_list.remove(selected_position);
                    }
                } else {
                    if (activity_list.size() > 0) {
                        for (Activity_Object bean : activity_list) {
                            cc_list.add(bean.getActivity_itemkey());
                        }
                        String max_id = Collections.max(cc_list).toString();
                        int last_num = Integer.parseInt(max_id);
                        int new_num = last_num + 1;
                        String new_item_number = "";
                        if (new_num >= 10) {
                            new_item_number = "00" + new_num;
                        } else {
                            new_item_number = "000" + new_num;
                        }
                        item_key = new_item_number;
                    } else {
                        item_key = "0001";
                    }
                    selected_status = "I";
                }
                new Get_Added_Activity_Data().execute();
            }
        }
    }

    private class Get_Added_Activity_Data extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (selected_pos != null && !selected_pos.equals("")) {
                    Activity_Object to = new Activity_Object(cause_itemkey, cause_text, item_key,
                            objectpartcode_id, event_id, codegroup_id, codegroup_text, code_id,
                            code_text, shttext, start_date_formatted, end_date_formatted,
                            start_time_formatted, end_time_formatted, selected_status,
                            selected_activity_custom_info_arraylist, false);
                    activity_list.add(selected_position, to);
                } else {
                    Activity_Object to = new Activity_Object(cause_itemkey, cause_text, item_key,
                            objectpartcode_id, event_id, codegroup_id, codegroup_text, code_id,
                            code_text, shttext, start_date_formatted, end_date_formatted,
                            start_time_formatted, end_time_formatted, selected_status,
                            selected_activity_custom_info_arraylist, false);
                    activity_list.add(to);
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (activity_list.size() > 0) {
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                activity_adapter = new ACTIVITY_ADAPTER(getActivity(), activity_list);
                recyclerView.setAdapter(activity_adapter);
                recyclerView.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            }
            nca.updateTabDataCount();
        }
    }

    public class Activity_Object {
        private String cause_itemkey;
        private String cause_shtxt;
        private String activity_itemkey;
        private String obj_part;
        private String event_code;
        private String codegroup_id;
        private String codegroup_text;
        private String code_id;
        private String code_text;
        private String activity_shtxt;
        private String st_date;
        private String st_time;
        private String end_date;
        private String end_time;
        private String status;
        public boolean selected;
        ArrayList<HashMap<String, String>> selected_activity_custom_info_arraylist;

        public ArrayList<HashMap<String, String>> getSelected_activity_custom_info_arraylist() {
            return selected_activity_custom_info_arraylist;
        }

        public void setSelected_activity_custom_info_arraylist(ArrayList<HashMap<String, String>> selected_activity_custom_info_arraylist) {
            this.selected_activity_custom_info_arraylist = selected_activity_custom_info_arraylist;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCause_itemkey() {
            return cause_itemkey;
        }

        public void setCause_itemkey(String cause_itemkey) {
            this.cause_itemkey = cause_itemkey;
        }

        public String getCause_shtxt() {
            return cause_shtxt;
        }

        public void setCause_shtxt(String cause_shtxt) {
            this.cause_shtxt = cause_shtxt;
        }

        public String getActivity_itemkey() {
            return activity_itemkey;
        }

        public void setActivity_itemkey(String activity_itemkey) {
            this.activity_itemkey = activity_itemkey;
        }

        public String getObj_part() {
            return obj_part;
        }

        public void setObj_part(String obj_part) {
            this.obj_part = obj_part;
        }

        public String getEvent_code() {
            return event_code;
        }

        public void setEvent_code(String event_code) {
            this.event_code = event_code;
        }

        public String getCodegroup_id() {
            return codegroup_id;
        }

        public void setCodegroup_id(String codegroup_id) {
            this.codegroup_id = codegroup_id;
        }

        public String getCodegroup_text() {
            return codegroup_text;
        }

        public void setCodegroup_text(String codegroup_text) {
            this.codegroup_text = codegroup_text;
        }

        public String getCode_id() {
            return code_id;
        }

        public void setCode_id(String code_id) {
            this.code_id = code_id;
        }

        public String getCode_text() {
            return code_text;
        }

        public void setCode_text(String code_text) {
            this.code_text = code_text;
        }

        public String getActivity_shtxt() {
            return activity_shtxt;
        }

        public void setActivity_shtxt(String activity_shtxt) {
            this.activity_shtxt = activity_shtxt;
        }

        public String getSt_date() {
            return st_date;
        }

        public void setSt_date(String st_date) {
            this.st_date = st_date;
        }

        public String getSt_time() {
            return st_time;
        }

        public void setSt_time(String st_time) {
            this.st_time = st_time;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Activity_Object(String cause_itemkey, String cause_shtxt, String activity_itemkey,
                               String obj_part, String event_code, String codegroup_id,
                               String codegroup_text, String code_id, String code_text,
                               String activity_shtxt, String st_date, String end_date,
                               String st_time, String end_time, String status,
                               ArrayList<HashMap<String, String>>
                                       selected_activity_custom_info_arraylist, boolean selected) {
            this.cause_itemkey = cause_itemkey;
            this.cause_shtxt = cause_shtxt;
            this.activity_itemkey = activity_itemkey;
            this.obj_part = obj_part;
            this.event_code = event_code;
            this.codegroup_id = codegroup_id;
            this.codegroup_text = codegroup_text;
            this.code_id = code_id;
            this.code_text = code_text;
            this.activity_shtxt = activity_shtxt;
            this.st_date = st_date;
            this.st_time = st_time;
            this.end_date = end_date;
            this.end_time = end_time;
            this.status = status;
            this.selected_activity_custom_info_arraylist = selected_activity_custom_info_arraylist;
            this.selected = selected;
        }

    }


    public class ACTIVITY_ADAPTER extends RecyclerView.Adapter<ACTIVITY_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Activity_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView st_date, code_textview, activity_key_textview, activity_text_textview, code_group_textview;
            LinearLayout data_layout;
            CheckBox checkbox;

            public MyViewHolder(View view) {
                super(view);
                activity_key_textview = (TextView) view.findViewById(R.id.activity_key_textview);
                activity_text_textview = (TextView) view.findViewById(R.id.activity_text_textview);
                code_group_textview = (TextView) view.findViewById(R.id.code_group_textview);
                code_textview = (TextView) view.findViewById(R.id.code_textview);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            }
        }

        public ACTIVITY_ADAPTER(Context mContext, List<Activity_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public ACTIVITY_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_activity_list_data, parent, false);
            return new ACTIVITY_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ACTIVITY_ADAPTER.MyViewHolder holder, final int position) {
            final Activity_Object nto = type_details_list.get(position);
            holder.activity_key_textview.setText(nto.getActivity_itemkey());
            holder.activity_text_textview.setText(nto.getActivity_shtxt());
            holder.code_group_textview.setText(nto.getCodegroup_id());
            holder.code_textview.setText(nto.getCode_id());
            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notifications_Change_Header_Fragment header_tab =
                            (Notifications_Change_Header_Fragment) getFragmentManager()
                                    .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                    Notifications_Create_Header_Object header_data = header_tab.getData();
                    String functionlocation_id = header_data.getFunctionlocation_id();
                    String equipment_id = header_data.getEquipment_id();
                    Notifications_Change_Causecode_Fragment causecode_fragment =
                            (Notifications_Change_Causecode_Fragment) getFragmentManager()
                                    .findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                    List<Notifications_Change_Causecode_Fragment.Cause_Code_Object>
                            causecode_list = causecode_fragment.getCauseCodeData();
                    if (causecode_list.size() > 0) {
                        causecode_array_list.clear();
                        for (int i = 0; i < causecode_list.size(); i++) {
                            HashMap<String, String> array_hashmap = new HashMap<String, String>();
                            array_hashmap.put("itemkey", causecode_list.get(i).getitem_key());
                            array_hashmap.put("objpart_id", causecode_list.get(i).getobject_part_id());
                            array_hashmap.put("event_id", causecode_list.get(i).getevent_id());
                            array_hashmap.put("event_desc", causecode_list.get(i).getevent_desc());
                            causecode_array_list.add(array_hashmap);
                        }
                        Intent intent = new Intent(getActivity(),
                                Notifications_Activity_Add_Activity.class);
                        intent.putExtra("functionlocation_id", functionlocation_id);
                        intent.putExtra("equipment_id", equipment_id);
                        intent.putExtra("causecode_array_list", causecode_array_list);
                        intent.putExtra("request_id", Integer.toString(add_activity_type));
                        intent.putExtra("position", Integer.toString(position));
                        intent.putExtra("activity_itemkey", nto.getActivity_itemkey());
                        intent.putExtra("cause_itemkey", nto.getCause_itemkey());
                        intent.putExtra("cause_text", nto.getCause_shtxt());
                        intent.putExtra("objectpartcode_id", nto.getObj_part());
                        intent.putExtra("event_id", nto.getEvent_code());
                        intent.putExtra("codegroup_id", nto.getCodegroup_id());
                        intent.putExtra("codegroup_text", nto.getCodegroup_text());
                        intent.putExtra("code_id", nto.getCode_id());
                        intent.putExtra("code_text", nto.getCode_text());
                        intent.putExtra("shttext", nto.getActivity_shtxt());
                        intent.putExtra("stdate_date", nto.getSt_date());
                        intent.putExtra("stdate_time", nto.getSt_time());
                        intent.putExtra("enddate_date", nto.getEnd_date());
                        intent.putExtra("enddate_time", nto.getEnd_time());
                        intent.putExtra("status", nto.getStatus());
                        intent.putExtra("selected_activity_custom_info_arraylist",
                                nto.getSelected_activity_custom_info_arraylist());
                        startActivityForResult(intent, add_activity_type);
                    }
                }
            });

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkbox.isChecked()) {
                        count = 0;
                        type_details_list.get(position).setSelected(true);
                        for (Activity_Object oop : type_details_list) {
                            if (oop.isSelected()) {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            nca.animateFab(true);
                    } else {
                        count = 0;
                        type_details_list.get(position).setSelected(false);
                        for (Activity_Object oop : type_details_list) {
                            if (oop.isSelected()) {
                                count = count + 1;
                            }
                        }
                        if (count == 0) {
                            nca.animateFab(false);
                            isSelected = false;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public List<Activity_Object> getActivityData() {
        return activity_list;
    }

    public List<Activity_Object> getActivityData_Delete() {
        return activity_list_delete;
    }

    public int activitySize() {
        if (activity_list.size() > 0) {
            return activity_list.size();
        } else {
            return 0;
        }
    }
}
