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

public class Notifications_Change_Causecode_Fragment extends Fragment {

    private List<Cause_Code_Object> causecode_list = new ArrayList<>();
    private List<Cause_Code_Object> causecode_list_delete = new ArrayList<>();
    List cc_list = new ArrayList();
    String selected_status = "", selected_pos = "", causekey = "", cause_id = "", cause_text = "",
            causecode_id = "", causecode_text = "", cause_desc = "", item_key = "0001",
            object_part_id = "", object_part_text = "", objectcode_id = "", object_code_text = "",
            event_id = "", event_text = "", eventcode_id = "", eventcode_text = "", event_desc = "";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    CAUSE_CODE_ADAPTER cause_code_adapter;
    TextView noData_tv;
    Error_Dialog error_dialog = new Error_Dialog();
    int add_causecode_type = 1, selected_position = 0;
    NotifHeaderPrcbl nhp = new NotifHeaderPrcbl();
    ArrayList<NotifCausCodActvPrcbl> causecode_parcablearray = new ArrayList<NotifCausCodActvPrcbl>();
    ArrayList<HashMap<String, String>> selected_object_custom_info_arraylist = new ArrayList<>();
    ArrayList<HashMap<String, String>> selected_cause_custom_info_arraylist = new ArrayList<>();
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    int count = 0;
    boolean isSelected = false;
    Notifications_Change_Activity nca;

    public Notifications_Change_Causecode_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications_causecode_fragment, container, false);

        noData_tv = (TextView) rootView.findViewById(R.id.noData_tv);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nca = (Notifications_Change_Activity) this.getActivity();

        recyclerView.setVisibility(View.GONE);
        noData_tv.setVisibility(View.VISIBLE);

        causecode_list.clear();
        causecode_list_delete.clear();
        selected_object_custom_info_arraylist.clear();
        selected_cause_custom_info_arraylist.clear();

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            nhp = bundle.getParcelable("notif_parcel");
            if (nhp != null && !nhp.equals("") && !nhp.equals("null")) {
                causecode_parcablearray = nhp.getCausCodPrcblAl();
                if (causecode_parcablearray.size() > 0) {
                    for (int i = 0; i < causecode_parcablearray.size(); i++) {
                        String item_key = causecode_parcablearray.get(i).getItmKey();
                        String cause_key = causecode_parcablearray.get(i).getCausKey();
                        selected_object_custom_info_arraylist.clear();
                        selected_cause_custom_info_arraylist.clear();
                        try {
                            Cursor cursor = App_db.rawQuery("select * from " +
                                            "EtNotifItems_CustomInfo where Qmnum = ? and ItemKey = ? and" +
                                            " CauseKey = ? and Zdoctype = ? and ZdoctypeItem = ?",
                                    new String[]{nhp.getQmnum(), item_key, cause_key, "Q", "QI"});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                                        custom_info_hashMap.put("Fieldname", cursor.getString(8));
                                        custom_info_hashMap.put("ZdoctypeItem", cursor.getString(6));
                                        custom_info_hashMap.put("Datatype", cursor.getString(13));
                                        custom_info_hashMap.put("Tabname", cursor.getString(7));
                                        custom_info_hashMap.put("Zdoctype", cursor.getString(5));
                                        custom_info_hashMap.put("Sequence", cursor.getString(11));
                                        custom_info_hashMap.put("Flabel", cursor.getString(10));
                                        custom_info_hashMap.put("Spras", "");
                                        custom_info_hashMap.put("Length", cursor.getString(12));
                                        String datatype = cursor.getString(13);
                                        String value = cursor.getString(9);
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
                                            custom_info_hashMap.put("Value", cursor.getString(9));
                                        }
                                        selected_object_custom_info_arraylist.add(custom_info_hashMap);
                                    }
                                    while (cursor.moveToNext());
                                }
                            } else {
                                cursor.close();
                            }
                        } catch (Exception e) {
                        }

                        try {
                            Cursor cursor = App_db.rawQuery("select * from " +
                                            "EtNotifItems_CustomInfo where Qmnum = ? and ItemKey = ? and" +
                                            " CauseKey = ? and Zdoctype = ? and ZdoctypeItem = ?",
                                    new String[]{nhp.getQmnum(), item_key, cause_key, "Q", "QC"});
                            if (cursor != null && cursor.getCount() > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        HashMap<String, String> custom_info_hashMap = new HashMap<String, String>();
                                        custom_info_hashMap.put("Fieldname", cursor.getString(8));
                                        custom_info_hashMap.put("ZdoctypeItem", cursor.getString(6));
                                        custom_info_hashMap.put("Datatype", cursor.getString(13));
                                        custom_info_hashMap.put("Tabname", cursor.getString(7));
                                        custom_info_hashMap.put("Zdoctype", cursor.getString(5));
                                        custom_info_hashMap.put("Sequence", cursor.getString(11));
                                        custom_info_hashMap.put("Flabel", cursor.getString(10));
                                        custom_info_hashMap.put("Spras", "");
                                        custom_info_hashMap.put("Length", cursor.getString(12));
                                        String datatype = cursor.getString(13);
                                        String value = cursor.getString(9);
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
                                            custom_info_hashMap.put("Value", cursor.getString(9));
                                        }
                                        selected_cause_custom_info_arraylist.add(custom_info_hashMap);
                                    }
                                    while (cursor.moveToNext());
                                }
                            } else {
                                cursor.close();
                            }
                        } catch (Exception e) {
                        }

                        Cause_Code_Object to = new Cause_Code_Object(
                                causecode_parcablearray.get(i).getItmKey(),
                                causecode_parcablearray.get(i).getItmPrtGrp(),
                                causecode_parcablearray.get(i).getPrtGrpTxt(),
                                causecode_parcablearray.get(i).getItmPrtCod(),
                                causecode_parcablearray.get(i).getPrtCodTxt(),
                                causecode_parcablearray.get(i).getItmDefectGrp(),
                                causecode_parcablearray.get(i).getDefectGrpTxt(),
                                causecode_parcablearray.get(i).getItmDefectCod(),
                                causecode_parcablearray.get(i).getDefectCodTxt(),
                                causecode_parcablearray.get(i).getItmDefectShTxt(),
                                causecode_parcablearray.get(i).getCausGrp(),
                                causecode_parcablearray.get(i).getCausGrpTxt(),
                                causecode_parcablearray.get(i).getCausCod(),
                                causecode_parcablearray.get(i).getCausCodTxt(),
                                causecode_parcablearray.get(i).getCausShTxt(),
                                causecode_parcablearray.get(i).getCausKey(),
                                causecode_parcablearray.get(i).getStatus(),
                                selected_object_custom_info_arraylist,
                                selected_cause_custom_info_arraylist,
                                false);
                        causecode_list.add(to);
                    }
                }
            }
            if (causecode_list.size() > 0) {
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                cause_code_adapter = new CAUSE_CODE_ADAPTER(getActivity(), causecode_list);
                recyclerView.setAdapter(cause_code_adapter);
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
                nca.fab.show();
            } else {
                nca.fab.hide();
            }
        } else {
            String auth_status = Authorizations.Get_Authorizations_Data(getActivity(),
                    "Q", "U");
            if (auth_status.equalsIgnoreCase("true")) {
                nca.fab.show();
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
                        description_textview.setText(getString(R.string.causecode_delete));
                        Button ok_button = delete_decision_dialog.findViewById(R.id.yes_button);
                        Button cancel_button = delete_decision_dialog.findViewById(R.id.no_button);
                        delete_decision_dialog.show();
                        ok_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < causecode_list.size(); i++) {
                                    boolean selected_status = causecode_list.get(i).isSelected();
                                    if (selected_status) {
                                        String action = causecode_list.get(i).getStatus();
                                        if (action.equalsIgnoreCase("U")) {
                                            Cause_Code_Object to = new Cause_Code_Object(
                                                    causecode_list.get(i).getitem_key(),
                                                    causecode_list.get(i).getobject_part_id(),
                                                    causecode_list.get(i).getobject_part_text(),
                                                    causecode_list.get(i).getobjectcode_id(),
                                                    causecode_list.get(i).getobject_code_text(),
                                                    causecode_list.get(i).getevent_id(),
                                                    causecode_list.get(i).getevent_text(),
                                                    causecode_list.get(i).geteventcode_id(),
                                                    causecode_list.get(i).geteventcode_text(),
                                                    causecode_list.get(i).getevent_desc(),
                                                    causecode_list.get(i).getcause_id(),
                                                    causecode_list.get(i).getcause_text(),
                                                    causecode_list.get(i).getcausecode_id(),
                                                    causecode_list.get(i).getcausecode_text(),
                                                    causecode_list.get(i).getcause_desc(),
                                                    causecode_list.get(i).getitem_key(),
                                                    "D",
                                                    causecode_list.get(i).getSelected_object_custom_info_arraylist(),
                                                    causecode_list.get(i).getSelected_cause_custom_info_arraylist(),
                                                    false);
                                            causecode_list_delete.add(to);
                                            causecode_list.remove(i);
                                        } else {
                                            causecode_list.remove(i);
                                        }
                                    }
                                }

                                nca.animateFab(false);
                                isSelected = false;

                                if (causecode_list.size() > 0) {
                                    cause_code_adapter = new CAUSE_CODE_ADAPTER(getActivity(), causecode_list);
                                    recyclerView.setAdapter(cause_code_adapter);
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
                        if ((equipment_id != null && !equipment_id.equals("")) ||
                                (functionlocation_id != null && !functionlocation_id.equals(""))) {
                            Intent intent = new Intent(getActivity(),
                                    Notifications_CauseCode_Add_Activity.class);
                            intent.putExtra("functionlocation_id", functionlocation_id);
                            intent.putExtra("equipment_id", equipment_id);
                            intent.putExtra("request_id", Integer.toString(add_causecode_type));
                            startActivityForResult(intent, add_causecode_type);
                        } else {
                            error_dialog.show_error_dialog(getActivity(),
                                    getString(R.string.equipFunc_mandate));
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
                object_part_id = data.getStringExtra("objectpart_id");
                object_part_text = data.getStringExtra("objectpart_text");
                objectcode_id = data.getStringExtra("objectpartcode_id");
                object_code_text = data.getStringExtra("objectpartcode_text");
                event_id = data.getStringExtra("event_id");
                event_text = data.getStringExtra("event_text");
                eventcode_id = data.getStringExtra("eventcode_id");
                eventcode_text = data.getStringExtra("eventcode_text");
                event_desc = data.getStringExtra("event_descritpion");
                causekey = data.getStringExtra("causekey");
                cause_id = data.getStringExtra("cause_id");
                cause_text = data.getStringExtra("cause_text");
                causecode_id = data.getStringExtra("causecode_id");
                causecode_text = data.getStringExtra("causecode_text");
                cause_desc = data.getStringExtra("cause_descritpion");
                selected_object_custom_info_arraylist.clear();
                selected_cause_custom_info_arraylist.clear();
                selected_object_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data
                        .getSerializableExtra("selected_object_custom_info_arraylist");
                selected_cause_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data
                        .getSerializableExtra("selected_cause_custom_info_arraylist");
                String itemkey = data.getStringExtra("itemkey");
                String status = data.getStringExtra("status");
                if (itemkey != null && !itemkey.equals("")) {
                    if (status.equalsIgnoreCase("U")) {
                        selected_status = "U";
                        item_key = data.getStringExtra("itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        causecode_list.remove(selected_position);
                    } else {
                        selected_status = "I";
                        item_key = data.getStringExtra("itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        causecode_list.remove(selected_position);
                    }
                } else {
                    if (causecode_list.size() > 0) {
                        for (Cause_Code_Object bean : causecode_list) {
                            cc_list.add(bean.getitem_key());
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
                new Get_Added_CauseCode_Data().execute();
            }
        }
    }

    private class Get_Added_CauseCode_Data extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (selected_pos != null && !selected_pos.equals("")) {
                    Cause_Code_Object to = new Cause_Code_Object(item_key, object_part_id,
                            object_part_text, objectcode_id, object_code_text, event_id, event_text,
                            eventcode_id, eventcode_text, event_desc, cause_id, cause_text,
                            causecode_id, causecode_text, cause_desc, causekey, selected_status,
                            selected_object_custom_info_arraylist,
                            selected_cause_custom_info_arraylist, false);
                    causecode_list.add(selected_position, to);
                } else {
                    Cause_Code_Object to = new Cause_Code_Object(item_key, object_part_id,
                            object_part_text, objectcode_id, object_code_text, event_id, event_text,
                            eventcode_id, eventcode_text, event_desc, cause_id, cause_text,
                            causecode_id, causecode_text, cause_desc, causekey, selected_status,
                            selected_object_custom_info_arraylist,
                            selected_cause_custom_info_arraylist, false);
                    causecode_list.add(to);
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (causecode_list.size() > 0) {
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                cause_code_adapter = new CAUSE_CODE_ADAPTER(getActivity(), causecode_list);
                recyclerView.setAdapter(cause_code_adapter);
                recyclerView.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            }
            nca.updateTabDataCount();
        }
    }

    public class Cause_Code_Object {
        private String cause_key;
        private String item_key;
        private String object_part_id;
        private String object_part_text;
        private String objectcode_id;
        private String object_code_text;
        private String event_id;
        private String event_text;
        private String eventcode_id;
        private String eventcode_text;
        private String event_desc;
        private String cause_id;
        private String cause_text;
        private String causecode_id;
        private String causecode_text;
        private String cause_desc;
        private String status;
        public boolean selected;
        ArrayList<HashMap<String, String>> selected_object_custom_info_arraylist;
        ArrayList<HashMap<String, String>> selected_cause_custom_info_arraylist;

        public Cause_Code_Object(String item_key, String object_part_id, String object_part_text,
                                 String objectcode_id, String object_code_text, String event_id,
                                 String event_text, String eventcode_id, String eventcode_text,
                                 String event_desc, String cause_id, String cause_text,
                                 String causecode_id, String causecode_text, String cause_desc,
                                 String cause_key, String status,
                                 ArrayList<HashMap<String, String>>
                                         selected_object_custom_info_arraylist,
                                 ArrayList<HashMap<String, String>>
                                         selected_cause_custom_info_arraylist, boolean selected) {
            this.item_key = item_key;
            this.object_part_id = object_part_id;
            this.object_part_text = object_part_text;
            this.objectcode_id = objectcode_id;
            this.object_code_text = object_code_text;
            this.event_id = event_id;
            this.event_text = event_text;
            this.eventcode_id = eventcode_id;
            this.eventcode_text = eventcode_text;
            this.event_desc = event_desc;
            this.cause_id = cause_id;
            this.cause_text = cause_text;
            this.causecode_id = causecode_id;
            this.causecode_text = causecode_text;
            this.cause_desc = cause_desc;
            this.cause_key = cause_key;
            this.status = status;
            this.selected = selected;
            this.selected_object_custom_info_arraylist = selected_object_custom_info_arraylist;
            this.selected_cause_custom_info_arraylist = selected_cause_custom_info_arraylist;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public ArrayList<HashMap<String, String>> getSelected_cause_custom_info_arraylist() {
            return selected_cause_custom_info_arraylist;
        }

        public void setSelected_cause_custom_info_arraylist(ArrayList<HashMap<String, String>> selected_cause_custom_info_arraylist) {
            this.selected_cause_custom_info_arraylist = selected_cause_custom_info_arraylist;
        }

        public ArrayList<HashMap<String, String>> getSelected_object_custom_info_arraylist() {
            return selected_object_custom_info_arraylist;
        }

        public void setSelected_object_custom_info_arraylist(ArrayList<HashMap<String, String>> selected_object_custom_info_arraylist) {
            this.selected_object_custom_info_arraylist = selected_object_custom_info_arraylist;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCause_key() {
            return cause_key;
        }

        public void setCause_key(String cause_key) {
            this.cause_key = cause_key;
        }

        public String getitem_key() {
            return item_key;
        }

        public void setitem_key(String item_key) {
            this.item_key = item_key;
        }

        public String getobject_part_id() {
            return object_part_id;
        }

        public void setobject_part_id(String object_part_id) {
            this.object_part_id = object_part_id;
        }

        public String getobject_part_text() {
            return object_part_text;
        }

        public void setobject_part_text(String object_part_text) {
            this.object_part_text = object_part_text;
        }

        public String getobjectcode_id() {
            return objectcode_id;
        }

        public void setobjectcode_id(String objectcode_id) {
            this.objectcode_id = objectcode_id;
        }

        public String getobject_code_text() {
            return object_code_text;
        }

        public void setobject_code_text(String object_code_text) {
            this.object_code_text = object_code_text;
        }

        public String getevent_id() {
            return event_id;
        }

        public void setevent_id(String event_id) {
            this.event_id = event_id;
        }

        public String getevent_text() {
            return event_text;
        }

        public void setevent_text(String event_text) {
            this.event_text = event_text;
        }

        public String geteventcode_id() {
            return eventcode_id;
        }

        public void seteventcode_id(String eventcode_id) {
            this.eventcode_id = eventcode_id;
        }

        public String geteventcode_text() {
            return eventcode_text;
        }

        public void seteventcode_text(String eventcode_text) {
            this.eventcode_text = eventcode_text;
        }

        public String getevent_desc() {
            return event_desc;
        }

        public void setevent_desc(String event_desc) {
            this.event_desc = event_desc;
        }

        public String getcause_id() {
            return cause_id;
        }

        public void setcause_id(String cause_id) {
            this.cause_id = cause_id;
        }

        public String getcause_text() {
            return cause_text;
        }

        public void setcause_text(String cause_text) {
            this.cause_text = cause_text;
        }

        public String getcausecode_id() {
            return causecode_id;
        }

        public void setcausecode_id(String causecode_id) {
            this.causecode_id = causecode_id;
        }

        public String getcausecode_text() {
            return causecode_text;
        }

        public void setcausecode_text(String causecode_text) {
            this.causecode_text = causecode_text;
        }

        public String getcause_desc() {
            return cause_desc;
        }

        public void setcause_desc(String cause_desc) {
            this.cause_desc = cause_desc;
        }
    }

    public class CAUSE_CODE_ADAPTER extends RecyclerView.Adapter<CAUSE_CODE_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Cause_Code_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView objectpartcode_textview, objectpart_textview,cause_key, item_key, causecode_text, causecode_desc, causecode_id,
                    cause_text, cause_id, event_desc, event_code_text, event_code_id, event_text,
                    event_id, objpart_code_text_tv, objpart_code_id_tv, objpart_text_tv,
                    objpart_id_tv, cause_code_textview, cause_textview, event_textview,
                    event_code_textview, event_description_textview;
            LinearLayout data_layout;
            CheckBox checkbox;

            public MyViewHolder(View view) {
                super(view);
                event_textview = (TextView) view.findViewById(R.id.event_textview);
                event_code_textview = (TextView) view.findViewById(R.id.event_code_textview);
                event_description_textview = view.findViewById(R.id.event_description_textview);
                cause_textview = (TextView) view.findViewById(R.id.cause_textview);
                cause_code_textview = (TextView) view.findViewById(R.id.cause_code_textview);
                objpart_id_tv = (TextView) view.findViewById(R.id.objpart_id_tv);
                objpart_text_tv = (TextView) view.findViewById(R.id.objpart_text_tv);
                objpart_code_id_tv = (TextView) view.findViewById(R.id.objpart_code_id_tv);
                objpart_code_text_tv = (TextView) view.findViewById(R.id.objpart_code_text_tv);
                event_id = (TextView) view.findViewById(R.id.event_id);
                event_text = (TextView) view.findViewById(R.id.event_text);
                event_code_id = (TextView) view.findViewById(R.id.event_code_id);
                event_code_text = (TextView) view.findViewById(R.id.event_code_text);
                event_desc = (TextView) view.findViewById(R.id.event_desc);
                cause_id = (TextView) view.findViewById(R.id.cause_id);
                cause_text = (TextView) view.findViewById(R.id.cause_text);
                causecode_id = (TextView) view.findViewById(R.id.causecode_id);
                causecode_text = (TextView) view.findViewById(R.id.causecode_text);
                causecode_desc = (TextView) view.findViewById(R.id.causecode_desc);
                item_key = (TextView) view.findViewById(R.id.item_key);
                cause_key = (TextView) view.findViewById(R.id.cause_key);
                data_layout = (LinearLayout) view.findViewById(R.id.data_layout);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                objectpart_textview = view.findViewById(R.id.objectpart_textview);
                objectpartcode_textview = view.findViewById(R.id.objectpartcode_textview);
            }
        }

        public CAUSE_CODE_ADAPTER(Context mContext, List<Cause_Code_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public CAUSE_CODE_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notifications_causecode_list_data, parent, false);
            return new CAUSE_CODE_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CAUSE_CODE_ADAPTER.MyViewHolder holder, final int position) {
            final Cause_Code_Object nto = type_details_list.get(position);
            holder.objectpart_textview.setText(nto.getobject_part_id()+" - "+nto.getobject_part_text());
            holder.objectpartcode_textview.setText(nto.getobjectcode_id()+" - "+nto.getobject_code_text());
            holder.event_textview.setText(nto.getevent_text());
            holder.event_code_textview.setText(nto.geteventcode_text());
            holder.event_description_textview.setText(nto.getevent_desc());
            holder.cause_textview.setText(nto.getcause_text());
            holder.cause_code_textview.setText(nto.getcausecode_text());
            holder.objpart_id_tv.setText(nto.getobject_part_id());
            holder.objpart_text_tv.setText(nto.getobject_part_text());
            holder.objpart_code_id_tv.setText(nto.getobjectcode_id());
            holder.objpart_code_text_tv.setText(nto.getobject_code_text());
            holder.event_id.setText(nto.getevent_id());
            holder.event_text.setText(nto.getevent_text());
            holder.event_code_id.setText(nto.geteventcode_id());
            holder.event_code_text.setText(nto.geteventcode_text());
            holder.event_desc.setText(nto.getevent_desc());
            holder.cause_id.setText(nto.getcause_id());
            holder.cause_text.setText(nto.getcause_text());
            holder.causecode_id.setText(nto.getcausecode_id());
            holder.causecode_text.setText(nto.getcausecode_text());
            holder.causecode_desc.setText(nto.getcause_desc());
            holder.item_key.setText(nto.getitem_key());
            holder.cause_key.setText(nto.getCause_key());
            holder.data_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notifications_Change_Header_Fragment header_tab =
                            (Notifications_Change_Header_Fragment) getFragmentManager()
                                    .findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                    Notifications_Create_Header_Object header_data = header_tab.getData();
                    String functionlocation_id = header_data.getFunctionlocation_id();
                    String equipment_id = header_data.getEquipment_id();
                    Intent intent = new Intent(getActivity(),
                            Notifications_CauseCode_Add_Activity.class);
                    intent.putExtra("functionlocation_id", functionlocation_id);
                    intent.putExtra("equipment_id", equipment_id);
                    intent.putExtra("request_id", Integer.toString(add_causecode_type));
                    intent.putExtra("position", Integer.toString(position));
                    intent.putExtra("itemkey", holder.item_key.getText().toString());
                    intent.putExtra("causekey", holder.cause_key.getText().toString());
                    intent.putExtra("objectpart_id",
                            holder.objpart_id_tv.getText().toString());
                    intent.putExtra("objectpart_text",
                            holder.objpart_text_tv.getText().toString());
                    intent.putExtra("objectpartcode_id",
                            holder.objpart_code_id_tv.getText().toString());
                    intent.putExtra("objectpartcode_text",
                            holder.objpart_code_text_tv.getText().toString());
                    intent.putExtra("event_id", holder.event_id.getText().toString());
                    intent.putExtra("event_text", holder.event_text.getText().toString());
                    intent.putExtra("eventcode_id", holder.event_code_id.getText().toString());
                    intent.putExtra("eventcode_text",
                            holder.event_code_text.getText().toString());
                    intent.putExtra("event_descritpion",
                            holder.event_desc.getText().toString());
                    intent.putExtra("cause_id", holder.cause_id.getText().toString());
                    intent.putExtra("cause_text", holder.cause_text.getText().toString());
                    intent.putExtra("causecode_id", holder.causecode_id.getText().toString());
                    intent.putExtra("causecode_text",
                            holder.causecode_text.getText().toString());
                    intent.putExtra("cause_descritpion",
                            holder.causecode_desc.getText().toString());
                    intent.putExtra("status", nto.getStatus());
                    intent.putExtra("selected_object_custom_info_arraylist",
                            nto.getSelected_object_custom_info_arraylist());
                    intent.putExtra("selected_cause_custom_info_arraylist",
                            nto.getSelected_cause_custom_info_arraylist());
                    startActivityForResult(intent, add_causecode_type);
                }
            });

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkbox.isChecked()) {
                        count = 0;
                        type_details_list.get(position).setSelected(true);
                        for (Cause_Code_Object oop : type_details_list) {
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
                        for (Cause_Code_Object oop : type_details_list) {
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

    public List<Cause_Code_Object> getCauseCodeData() {
        return causecode_list;
    }

    public List<Cause_Code_Object> getCauseCodeData_Delete() {
        return causecode_list_delete;
    }

    public int causeSize() {
        if (causecode_list.size() > 0) {
            return causecode_list.size();
        } else {
            return 0;
        }
    }
}
