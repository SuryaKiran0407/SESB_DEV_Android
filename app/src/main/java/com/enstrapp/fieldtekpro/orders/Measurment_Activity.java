package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;

import java.util.ArrayList;

public class Measurment_Activity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView list_recycleview;
    LinearLayout no_data_layout;
    Button cancel_bt, save_bt;
    FloatingActionButton fab;
    ImageButton back_ib;
    String equip = "", funcLoc = "", ordrId = "", orderUUID = "";
    static final int MEASUADD = 8756;
    ArrayList<Measurement_Parceble> mpo_al = new ArrayList<>();
    int count = 0;
    boolean isSelected = false;
    MeasurementsAdapter measurementsAdapter;
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    Error_Dialog errorDialog = new Error_Dialog();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurment_activity);

        list_recycleview = findViewById(R.id.list_recycleview);
        no_data_layout = findViewById(R.id.no_data_layout);
        cancel_bt = findViewById(R.id.cancel_bt);
        save_bt = findViewById(R.id.save_bt);
        fab = findViewById(R.id.fab);
        back_ib = findViewById(R.id.back_ib);

        DATABASE_NAME = Measurment_Activity.this.getString(R.string.database_name);
        App_db = Measurment_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            equip = extras.getString("equipId");
            funcLoc = extras.getString("funcLoc");
            ordrId = extras.getString("ordrId");
            orderUUID = extras.getString("UUID");
        }

        fab.setOnClickListener(this);
        cancel_bt.setOnClickListener(this);
        save_bt.setOnClickListener(this);
        back_ib.setOnClickListener(this);

        GetMeasurmentData(orderUUID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.fab):
                if (isSelected) {
                    ArrayList<Measurement_Parceble> rmoop = new ArrayList<>();
                    rmoop.addAll(mpo_al);
                    for (Measurement_Parceble oo : rmoop) {
                        if (oo.isSelected())
                            mpo_al.remove(oo);
                    }
                    if (mpo_al.size() > 0)
                        for (int i = 0; i < mpo_al.size(); i++) {
                            mpo_al.get(i).setiKey(gnrtMeasuId(i));
                        }
                    animateFab(false);
                    isSelected = false;
                    measurementsAdapter.notifyDataSetChanged();
                    rmoop = null;
                } else {
                    Intent mDocAddIntent = new Intent(Measurment_Activity.this,
                            MeasurmentAdd_Activity.class);
                    mDocAddIntent.putExtra("equip", equip);
                    startActivityForResult(mDocAddIntent, MEASUADD);
                }
                break;

            case (R.id.cancel_bt):
                setResult(RESULT_CANCELED);
                Measurment_Activity.this.finish();
                break;

            case (R.id.save_bt):
                if (mpo_al.size() > 0) {
                    confirmationDialog(getString(R.string.save_measurment));
                } else {
                    errorDialog.show_error_dialog(Measurment_Activity.this,
                            getString(R.string.no_measurment));
                }
                break;

            case (R.id.back_ib):
                setResult(RESULT_CANCELED);
                Measurment_Activity.this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (MEASUADD):
                if (resultCode == RESULT_OK) {
                    Measurement_Parceble mpo = new Measurement_Parceble();
                    mpo = data.getParcelableExtra("mpo");
                    if (mpo_al != null) {
                        if (mpo_al.size() > 0) {
                            mpo.setiKey(gnrtMeasuId(mpo_al.size()));
                            mpo.setAction("I");
                        } else {
                            mpo.setiKey(gnrtMeasuId(0));
                            mpo.setAction("I");
                        }
                    } else {
                        mpo.setiKey(gnrtMeasuId(0));
                        mpo.setAction("I");
                    }
                    mpo_al.add(mpo);
                    if (measurementsAdapter != null) {
                        measurementsAdapter.notifyDataSetChanged();
                    } else {
                        measurementsAdapter =
                                new MeasurementsAdapter(Measurment_Activity.this, mpo_al);
                        list_recycleview.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(Measurment_Activity.this);
                        list_recycleview.setLayoutManager(layoutManager);
                        list_recycleview.setItemAnimator(new DefaultItemAnimator());
                        list_recycleview.setAdapter(measurementsAdapter);
                        list_recycleview.setVisibility(View.VISIBLE);
                        no_data_layout.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    private void GetMeasurmentData(String UUID) {
        mpo_al.clear();
        Cursor cursor = null;
        try {
            cursor = App_db.rawQuery("select * from Orders_EtImrg where UUID = ?",
                    new String[]{UUID});
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Measurement_Parceble mpo = new Measurement_Parceble();
                        mpo.setiKey("");
                        mpo.setSelected(false);
                        mpo.setQmnum(cursor.getString(2));
                        mpo.setAufnr(cursor.getString(3));
                        mpo.setVornr(cursor.getString(4));
                        mpo.setMdocm(cursor.getString(5));
                        mpo.setPoint(cursor.getString(6));
                        mpo.setMpobj(cursor.getString(7));
                        mpo.setMpobt(cursor.getString(8));
                        mpo.setPsort(cursor.getString(9));
                        mpo.setPttxt(cursor.getString(10));
                        mpo.setAtinn(cursor.getString(11));
                        mpo.setIdate(cursor.getString(12));
                        mpo.setItime(cursor.getString(13));
                        mpo.setMdtxt(cursor.getString(14));
                        mpo.setReadr(cursor.getString(15));
                        mpo.setAtbez(cursor.getString(16));
                        mpo.setMsehi(cursor.getString(17));
                        mpo.setMsehl(cursor.getString(18));
                        mpo.setReadc(cursor.getString(19));
                        mpo.setDesic(cursor.getString(20));
                        mpo.setPrest(cursor.getString(21));
                        mpo.setDocaf(cursor.getString(22));
                        mpo.setCodct(cursor.getString(23));
                        mpo.setCodgr(cursor.getString(24));
                        mpo.setVlcod(cursor.getString(25));
                        mpo.setAction(cursor.getString(26));
                        mpo.setEqunr(cursor.getString(27));
                        mpo_al.add(mpo);
                    }
                    while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null)
                cursor.close();
        }

        if (mpo_al.size() > 0) {
            measurementsAdapter = new MeasurementsAdapter(Measurment_Activity.this, mpo_al);
            list_recycleview.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(Measurment_Activity.this);
            list_recycleview.setLayoutManager(layoutManager);
            list_recycleview.setItemAnimator(new DefaultItemAnimator());
            list_recycleview.setAdapter(measurementsAdapter);
            list_recycleview.setVisibility(View.VISIBLE);
            no_data_layout.setVisibility(View.GONE);
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
            list_recycleview.setVisibility(View.GONE);
        }
    }

    private class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Measurement_Parceble> measurementsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mdoc_id, point_tv, desc_tv, date_tv, targ_tv, reading_tv, unit_tv,
                    person_tv, result_tv, notes_tv, action_tv;
            CheckBox checkBox;

            public MyViewHolder(View view) {
                super(view);
                checkBox = view.findViewById(R.id.checkBox);
                mdoc_id = view.findViewById(R.id.mdoc_id);
                point_tv = view.findViewById(R.id.point_tv);
                desc_tv = view.findViewById(R.id.desc_tv);
                date_tv = view.findViewById(R.id.date_tv);
                reading_tv = view.findViewById(R.id.reading_tv);
                targ_tv = view.findViewById(R.id.targ_tv);
                unit_tv = view.findViewById(R.id.unit_tv);
                person_tv = view.findViewById(R.id.person_tv);
                result_tv = view.findViewById(R.id.result_tv);
                notes_tv = view.findViewById(R.id.notes_tv);
                action_tv = view.findViewById(R.id.action_tv);
            }
        }

        public MeasurementsAdapter(Context mContext, ArrayList<Measurement_Parceble> list) {
            this.mContext = mContext;
            this.measurementsList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.measurement_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final Measurement_Parceble mpo = measurementsList.get(position);
            holder.mdoc_id.setText(mpo.getMdocm());
            holder.point_tv.setText(mpo.getPoint());
            holder.desc_tv.setText(mpo.getPttxt());
            holder.date_tv.setText(mpo.getIdate());
            holder.reading_tv.setText(mpo.getReadc());
            holder.targ_tv.setText(mpo.getDesic());
            holder.unit_tv.setText(mpo.getMsehl());
            holder.person_tv.setText(mpo.getReadr());
            holder.result_tv.setText(mpo.getAtbez());
            holder.notes_tv.setText(mpo.getMdtxt());
            holder.action_tv.setText(mpo.getAction());
            holder.checkBox.setTag(position);
            if (holder.action_tv.getText().toString().equals("I")) {
                holder.checkBox.setEnabled(true);
            } else {
                holder.checkBox.setEnabled(false);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        count = 0;
                        measurementsList.get((Integer) v.getTag()).setSelected(true);
                        for (Measurement_Parceble oop : measurementsList) {
                            if (oop.isSelected()) {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            animateFab(true);
                    } else {
                        measurementsList.get((Integer) v.getTag()).setSelected(false);
                        count = 0;
                        for (Measurement_Parceble oop : measurementsList) {
                            if (oop.isSelected())
                                count = count + 1;
                        }
                        if (count == 0) {
                            animateFab(false);
                            isSelected = false;
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return measurementsList.size();
        }
    }

    protected void animateFab(final boolean selected) {
        fab.clearAnimation();
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon
                if (selected) {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    fab.setImageResource(R.drawable.ic_delete_icon);
                } else {
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    fab.setImageResource(R.drawable.ic_add_white_24px);
                }

                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f,
                        1f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
    }

    private String gnrtMeasuId(int size) {
        if (size < 9) {
            return "00" + String.valueOf(size + 1) + "0";
        } else {
            return "0" + String.valueOf(size + 1) + "0";
        }
    }

    private void confirmationDialog(String message) {
        final Dialog cancel_dialog =
                new Dialog(Measurment_Activity.this, R.style.PauseDialog);
        cancel_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cancel_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancel_dialog.setCancelable(false);
        cancel_dialog.setCanceledOnTouchOutside(false);
        cancel_dialog.setContentView(R.layout.network_error_dialog);
        final TextView description_textview = cancel_dialog.findViewById(R.id.description_textview);
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
                cd = new ConnectionDetector(Measurment_Activity.this);
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    cancel_dialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("mpo_al", mpo_al);
                    setResult(RESULT_OK, intent);
                } else {
                    new Network_Connection_Dialog()
                            .show_network_connection_dialog(Measurment_Activity.this);
                }
            }
        });
    }
}
