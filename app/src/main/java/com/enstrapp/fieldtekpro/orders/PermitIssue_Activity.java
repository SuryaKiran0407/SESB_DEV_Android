package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro_sesb_dev.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PermitIssue_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout footer_ll;
    Button cancel_bt, save_bt;
    Toolbar toolBar;
    ArrayList<OrdrWcagnsPrcbl> wcg_al_d = new ArrayList<>();
    ArrayList<OrdrWcagnsPrcbl> wcg_al = new ArrayList<>();
    PERMITLIST_ADAPTER adapter;
    String prep = "", woco = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permit_issue_activity);

        save_bt = findViewById(R.id.save_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        footer_ll = findViewById(R.id.footer_ll);
        recyclerView = findViewById(R.id.recyclerView);
        toolBar = findViewById(R.id.toolBar);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            wcg_al = extras.getParcelableArrayList("wcg_al");
            prep = extras.getString("prep");
            woco = extras.getString("woco");
            if (woco != null)
                if (woco.equals("X")) {
                    footer_ll.setVisibility(GONE);
                } else {

                    String auth_status2 = Authorizations
                            .Get_Authorizations_Data(PermitIssue_Activity.this, "C",
                                    "PI");
                    if (auth_status2.equalsIgnoreCase("true")) {
                        footer_ll.setVisibility(VISIBLE);
                    } else {
                        footer_ll.setVisibility(GONE);
                    }
                }

            if (wcg_al != null) {
                if (wcg_al.size() > 0) {
                    wcg_al_d = new ArrayList<>();
                    for (OrdrWcagnsPrcbl wcg : wcg_al) {
                        if (wcg.getGeniakt().equals("X"))
                            wcg.setGeniakt_status(false);
                        else
                            wcg.setGeniakt_status(true);
                    }
                    for (OrdrWcagnsPrcbl wcg : wcg_al) {
                        if (!wcg.getAction().equals("D") && !wcg.getPmsog().equals("I_TAG") &&
                                !wcg.getPmsog().equals("I_TAGGED"))
                            wcg_al_d.add(wcg);
                    }
                    if (wcg_al_d.size() > 0) {
                        adapter = new PERMITLIST_ADAPTER(PermitIssue_Activity.this,
                                wcg_al_d);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(PermitIssue_Activity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<OrdrWcagnsPrcbl> rmo_al = new ArrayList<>();
                rmo_al.addAll(wcg_al);
                for (int j = 0; j < wcg_al_d.size(); j++) {
                    for (OrdrWcagnsPrcbl wcg : rmo_al) {
                        if (wcg.getPmsog().equals(wcg_al_d.get(j).getPmsog()))
                            wcg_al.remove(wcg);

                    }
                }
                wcg_al.addAll(wcg_al_d);
                boolean appr = false;
                for (OrdrWcagnsPrcbl wcg : wcg_al_d) {
                    if (wcg.getGeniakt().equals("X"))
                        appr = true;
                }
                String app = "";

                if (appr)
                    app = "X";
                else
                    app = "";
                Intent intent = new Intent();
                intent.putExtra("wcg_al", wcg_al);
                intent.putExtra("appr", app);
                setResult(RESULT_OK, intent);
                PermitIssue_Activity.this.finish();
            }
        });

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                PermitIssue_Activity.this.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class PERMITLIST_ADAPTER extends RecyclerView.Adapter<PERMITLIST_ADAPTER.MyViewHolder> {
        private Context mContext;
        private List<OrdrWcagnsPrcbl> issuelist;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView date_time_textview, Objnr, permit_textview, permit_text_textview,
                    Geniakt, Crname;
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
                delIssue_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationDialog(getString(R.string.del_approvals),
                                wcg_al_d.get((Integer) v.getTag()).getPmsog());
                    }
                });
                permit_issued_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (permit_issued_checkbox.isChecked()) {
                            int position = (Integer) v.getTag();
                            wcg_al_d.get(position).setGenvname(Crname.getText().toString());
                            wcg_al_d.get(position).setGeniakt_status(true);
                            permit_issued_checkbox.setChecked(true);
                            Geniakt.setText("");
                            person_name_edittext.setText(Crname.getText().toString());
                            date_time_textview.setText(currentDate() + " " + currentTime());
                            wcg_al_d.get(position).setGeniakt("");
                            wcg_al_d.get(position).setGendatum(currentDate());
                            wcg_al_d.get(position).setGentime(currentTime());
                            wcg_al_d.get(position).setAction("I");
                        } else {
                            int position = (Integer) v.getTag();
                            wcg_al_d.get(position).setGenvname("");
                            wcg_al_d.get(position).setGeniakt_status(false);
                            permit_issued_checkbox.setChecked(false);
                            Geniakt.setText("X");
                            person_name_edittext.setText("");
                            date_time_textview.setText("");
                            wcg_al_d.get(position).setGeniakt("X");
                            wcg_al_d.get(position).setGendatum("");
                            wcg_al_d.get(position).setAction("I");
                            wcg_al_d.get(position).setGentime("");
                        }
                    }
                });
                person_name_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int pos = (int) person_name_edittext.getTag();
                        wcg_al_d.get(pos).setGenvname(person_name_edittext.getText().toString());
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
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.permit_issue_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final OrdrWcagnsPrcbl nto = issuelist.get(position);

            holder.permit_textview.setText(nto.getPmsog());
            holder.permit_text_textview.setText(nto.getGntxt());
            holder.person_name_edittext.setTag(position);
            holder.person_name_edittext.setText(nto.getGenvname());
            holder.Crname.setText(nto.getCrname());
            holder.Objnr.setText(nto.getObjnr());
            holder.date_time_textview.setText(nto.getGendatum() + " " + nto.getGentime());
            holder.permit_issued_checkbox.setTag(position);
            holder.delIssue_iv.setTag(position);
            holder.permit_issued_checkbox.setChecked((issuelist.get(position)
                    .isGeniakt_status() == true ? true : false));
            if (holder.permit_textview.getText().toString().equals("HOD") ||
                    holder.permit_textview.getText().toString().equals("SAFETY")) {
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
            }
            if (prep.equals("X")) {
                holder.permit_issued_checkbox.setEnabled(true);
                holder.person_name_edittext.setEnabled(true);
            } else {
                holder.permit_issued_checkbox.setEnabled(false);
                holder.person_name_edittext.setEnabled(false);
            }
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

    private void confirmationDialog(String message, final String type) {
        final Dialog cancel_dialog = new Dialog(PermitIssue_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = (TextView) cancel_dialog.findViewById(R.id.description_textview);
        description_textview.setText(message);
        Button confirm = (Button) cancel_dialog.findViewById(R.id.ok_button);
        Button cancel = (Button) cancel_dialog.findViewById(R.id.cancel_button);
        cancel_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_dialog.dismiss();
                ArrayList<OrdrWcagnsPrcbl> rmo_al = new ArrayList<>();
                rmo_al.addAll(wcg_al_d);
                for (int i = 0; i < rmo_al.size(); i++) {
                    if (rmo_al.get(i).getPmsog().equals(type))
                        wcg_al_d.remove(i);
                }
                for (OrdrWcagnsPrcbl wcg : wcg_al) {
                    if (wcg.getPmsog().equals(type) && !wcg.getPmsog().equals("I_TAG") &&
                            !wcg.getPmsog().equals("I_TAGGED"))
                        wcg.setAction("D");
                }
                adapter = new PERMITLIST_ADAPTER(PermitIssue_Activity.this, wcg_al_d);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(PermitIssue_Activity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}
