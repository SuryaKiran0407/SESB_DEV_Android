package com.enstrapp.fieldtekpro.Calibration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class Calibration_Defects_Fragment extends Fragment implements View.OnClickListener {

    String insp_lot = "";
    FloatingActionButton add_defect_button;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    private List<Defects_Object> defects_list = new ArrayList<>();
    RecyclerView recyclerview;
    DEFECTS_ADAPTER defects_adapter;
    Calibration_Orders_Operations_List_Activity activity;

    public Calibration_Defects_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calibration_defects_fragment, container, false);

        activity = (Calibration_Orders_Operations_List_Activity) getActivity();

        DATABASE_NAME = getActivity().getString(R.string.database_name);
        App_db = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        try {
            Cursor cursor1 = App_db.rawQuery("select * from EtQudData where Aufnr = ?",
                    new String[]{activity.order_id});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        insp_lot = cursor1.getString(1);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
            }
        } catch (Exception e) {
        }

        add_defect_button = (FloatingActionButton) rootView.findViewById(R.id.add_defect_button);
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        add_defect_button.setOnClickListener(this);


        String vkatart = "";
        try {
            Cursor cursor1 = null;
            cursor1 = App_db.rawQuery("select * from EtQudData Where Aufnr = ?",
                    new String[]{activity.order_id});
            if (cursor1 != null && cursor1.getCount() > 0) {
                if (cursor1.moveToFirst()) {
                    do {
                        vkatart = cursor1.getString(8);
                    }
                    while (cursor1.moveToNext());
                }
            } else {
                cursor1.close();
                vkatart = "";
            }
        } catch (Exception e) {
            vkatart = "";
        }
        if (vkatart != null && !vkatart.equals("")) {
            add_defect_button.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == add_defect_button) {
            Intent intent = new Intent(getActivity(), Calibration_Add_Defect_Activity.class);
            intent.putExtra("Insp_Lot", insp_lot);
            intent.putExtra("order_id", activity.order_id);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals("")) {
            if (requestCode == 1) {
                String object_part_id = data.getStringExtra("object_part_id");
                String object_part_text = data.getStringExtra("object_part_text");
                String object_part_code_id = data.getStringExtra("object_part_code_id");
                String object_part_code_text = data.getStringExtra("object_part_code_text");
                String defect_id = data.getStringExtra("defect_id");
                String defect_text = data.getStringExtra("defect_text");
                String defect_code_id = data.getStringExtra("defect_code_id");
                String defect_code_text = data.getStringExtra("defect_code_text");
                String defect_description = data.getStringExtra("defect_description");
                String num_defects = data.getStringExtra("num_defects");
                Defects_Object to = new Defects_Object(object_part_id, object_part_text, object_part_code_id, object_part_code_text, defect_id, defect_text, defect_code_id, defect_code_text, defect_description, num_defects);
                defects_list.add(to);
                if (defects_list.size() > 0) {
                    recyclerview.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerview.setLayoutManager(mLayoutManager);
                    defects_adapter = new DEFECTS_ADAPTER(getActivity(), defects_list);
                    recyclerview.setAdapter(defects_adapter);
                    recyclerview.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class DEFECTS_ADAPTER extends RecyclerView.Adapter<DEFECTS_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<Defects_Object> type_details_list;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView obj_part_textview, item_key, obj_part_code_textview, defect_textview, defect_code_textview;
            LinearLayout data_layout;

            public MyViewHolder(View view) {
                super(view);
                obj_part_textview = (TextView) view.findViewById(R.id.obj_part_textview);
                obj_part_code_textview = (TextView) view.findViewById(R.id.obj_part_code_textview);
                defect_textview = (TextView) view.findViewById(R.id.defect_textview);
                defect_code_textview = (TextView) view.findViewById(R.id.defect_code_textview);
            }
        }

        public DEFECTS_ADAPTER(Context mContext, List<Defects_Object> list) {
            this.mContext = mContext;
            this.type_details_list = list;
        }

        @Override
        public DEFECTS_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calibration_defects_list_data, parent, false);
            return new DEFECTS_ADAPTER.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final DEFECTS_ADAPTER.MyViewHolder holder, int position) {
            final Defects_Object nto = type_details_list.get(position);
            holder.obj_part_textview.setText(nto.getObject_part_id() + " - " + nto.getObject_part_text());
            holder.obj_part_code_textview.setText(nto.getObject_part_code_id() + " - " + nto.getObject_part_code_text());
            holder.defect_textview.setText(nto.getDefect_id() + " - " + nto.getDefect_text());
            holder.defect_code_textview.setText(nto.getDefect_code_id() + " - " + nto.getDefect_code_text());
        }

        @Override
        public int getItemCount() {
            return type_details_list.size();
        }
    }

    public class Defects_Object {
        private String object_part_id, object_part_text, object_part_code_id, object_part_code_text, defect_id, defect_text, defect_code_id, defect_code_text, defect_description, num_defects;

        public String getObject_part_id() {
            return object_part_id;
        }

        public void setObject_part_id(String object_part_id) {
            this.object_part_id = object_part_id;
        }

        public String getObject_part_text() {
            return object_part_text;
        }

        public void setObject_part_text(String object_part_text) {
            this.object_part_text = object_part_text;
        }

        public String getObject_part_code_id() {
            return object_part_code_id;
        }

        public void setObject_part_code_id(String object_part_code_id) {
            this.object_part_code_id = object_part_code_id;
        }

        public String getObject_part_code_text() {
            return object_part_code_text;
        }

        public void setObject_part_code_text(String object_part_code_text) {
            this.object_part_code_text = object_part_code_text;
        }

        public String getDefect_id() {
            return defect_id;
        }

        public void setDefect_id(String defect_id) {
            this.defect_id = defect_id;
        }

        public String getDefect_text() {
            return defect_text;
        }

        public void setDefect_text(String defect_text) {
            this.defect_text = defect_text;
        }

        public String getDefect_code_id() {
            return defect_code_id;
        }

        public void setDefect_code_id(String defect_code_id) {
            this.defect_code_id = defect_code_id;
        }

        public String getDefect_code_text() {
            return defect_code_text;
        }

        public void setDefect_code_text(String defect_code_text) {
            this.defect_code_text = defect_code_text;
        }

        public String getDefect_description() {
            return defect_description;
        }

        public void setDefect_description(String defect_description) {
            this.defect_description = defect_description;
        }

        public String getNum_defects() {
            return num_defects;
        }

        public void setNum_defects(String num_defects) {
            this.num_defects = num_defects;
        }

        public Defects_Object(String object_part_id, String object_part_text, String object_part_code_id, String object_part_code_text, String defect_id, String defect_text, String defect_code_id, String defect_code_text, String defect_description, String num_defects) {
            this.object_part_id = object_part_id;
            this.object_part_text = object_part_text;
            this.object_part_code_id = object_part_code_id;
            this.object_part_code_text = object_part_code_text;
            this.defect_id = defect_id;
            this.defect_text = defect_text;
            this.defect_code_id = defect_code_id;
            this.defect_code_text = defect_code_text;
            this.defect_description = defect_description;
            this.num_defects = num_defects;
        }

    }

    public List<Defects_Object> getDefectsData() {
        return defects_list;
    }
}
