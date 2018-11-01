package com.enstrapp.fieldtekpro.orders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class JSA_Change_AssessmentTeam_Fragment extends Fragment {

    RecyclerView recyclerView;
    TextView nodata_textview;
    String rasid = "";
    int add_asstm = 0;
    private List<AssessTeam_List_Object> assessteam_list = new ArrayList<>();
    Assess_Adapter assess_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    JSA_Change_Activity ma;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";

    public JSA_Change_AssessmentTeam_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.jsa_assessmentteam_fragment, container, false);

        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        ma = (JSA_Change_Activity) this.getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nodata_textview = (TextView) rootView.findViewById(R.id.nodata_textview);

        recyclerView.setVisibility(View.GONE);
        nodata_textview.setVisibility(View.VISIBLE);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            rasid = bundle.getString("rasid");
            try {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from EtJSAPer where Rasid =" +
                        " ?", new String[]{rasid});
                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            AssessTeam_List_Object olo =
                                    new AssessTeam_List_Object(cursor.getString(3),
                                            cursor.getString(6),
                                            cursor.getString(4),
                                            cursor.getString(5),
                                            false, "");
                            assessteam_list.add(olo);
                        }
                        while (cursor.moveToNext());
                    }
                } else {
                    cursor.close();
                }
            } catch (Exception e) {
            }
            if (assessteam_list.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                nodata_textview.setVisibility(View.GONE);
                assess_adapter = new Assess_Adapter(getActivity(), assessteam_list);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(assess_adapter);
            } else {
                recyclerView.setVisibility(View.GONE);
                nodata_textview.setVisibility(View.VISIBLE);
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
        JSA_Change_Activity jsa_change_activity = (JSA_Change_Activity) this.getActivity();
        jsa_change_activity.add_fab.hide();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == add_asstm) {
                String name_id = data.getStringExtra("name_id");
                String name_text = data.getStringExtra("name_text");
                String role_id = data.getStringExtra("role_id");
                String role_text = data.getStringExtra("role_text");
                UUID uniqueKey = UUID.randomUUID();
                AssessTeam_List_Object olo = new AssessTeam_List_Object(name_id, name_text, role_id,
                        role_text, false, uniqueKey.toString());
                assessteam_list.add(olo);
                if (assessteam_list.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    nodata_textview.setVisibility(View.GONE);
                    assess_adapter = new Assess_Adapter(getActivity(), assessteam_list);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(assess_adapter);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    nodata_textview.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class Assess_Adapter extends RecyclerView.Adapter<Assess_Adapter.MyViewHolder> {
        private Context mContext;
        private List<AssessTeam_List_Object> list_data;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name_textview, role_textview;
            public CheckBox checkBox;
            public LinearLayout layout;

            public MyViewHolder(View view) {
                super(view);
                name_textview = (TextView) view.findViewById(R.id.name_textview);
                role_textview = (TextView) view.findViewById(R.id.role_textview);
                checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                checkBox.setVisibility(View.GONE);
                layout = (LinearLayout) view.findViewById(R.id.layout);
            }
        }

        public Assess_Adapter(Context mContext, List<AssessTeam_List_Object> list) {
            this.mContext = mContext;
            this.list_data = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jsa_assessmentteam_list_data, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final AssessTeam_List_Object olo = list_data.get(position);
            holder.name_textview.setText(olo.getName_id() + " - " + olo.getName_text());
            holder.role_textview.setText(olo.getRole_text());
            holder.name_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.role_textview.setTextColor(getResources().getColor(R.color.dark_grey2));
            holder.layout.setBackground(getResources().getDrawable(R.drawable.bluedashborder));
        }

        @Override
        public int getItemCount() {
            return list_data.size();
        }
    }

    public class AssessTeam_List_Object {
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

        public AssessTeam_List_Object(String name_id, String name_text, String role_id,
                                      String role_text, boolean selected, String uuid) {
            this.name_id = name_id;
            this.name_text = name_text;
            this.role_id = role_id;
            this.role_text = role_text;
            this.selected = selected;
            this.uuid = uuid;
        }
    }
}
