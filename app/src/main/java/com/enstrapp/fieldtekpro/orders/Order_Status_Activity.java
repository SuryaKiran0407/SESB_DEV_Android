package com.enstrapp.fieldtekpro.orders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.Parcelable_Objects.NotifOrdrStatusPrcbl;
import com.enstrapp.fieldtekpro.R;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Order_Status_Activity extends AppCompatActivity {

    TextView title_tv;
    ImageView back_iv;
    Button save_bt, cancel_bt;
    ViewPager viewPager;
    TabLayout tabLayout;
    LinearLayout footer;
    ArrayList<NotifOrdrStatusPrcbl> osp_al = new ArrayList<>();
    ArrayList<NotifOrdrStatusPrcbl> wthSts_al = new ArrayList<>();
    ArrayList<NotifOrdrStatusPrcbl> wthOtSts_al = new ArrayList<>();
    ArrayList<NotifOrdrStatusPrcbl> sysSts_al = new ArrayList<>();
    TabAdapter statusTabAdapter;
    TextView status_tv, orders_tv;
    String Status = "", woco = "", type = "", heading = "";
    boolean WA = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_tablayout);

        orders_tv = findViewById(R.id.orders_tv);
        status_tv = findViewById(R.id.status_tv);
        viewPager = findViewById(R.id.viewPager);
        footer = findViewById(R.id.footer);
        save_bt = findViewById(R.id.save_bt);
        cancel_bt = findViewById(R.id.cancel_bt);
        back_iv = findViewById(R.id.back_iv);
        tabLayout = findViewById(R.id.tabLayout);
        title_tv = findViewById(R.id.title_tv);

        footer.setVisibility(VISIBLE);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            osp_al = bundle.getParcelableArrayList("statusObject");
            Status = bundle.getString("status");
            woco = bundle.getString("woco");
            type = bundle.getString("type");
            heading = bundle.getString("heading");
            if (type != null) {
                if (type.equals("WA")) {
                    WA = true;
                } else {
                    WA = false;
                }
                orders_tv.setText(heading);
            } else {
                WA = false;
            }
            if (woco != null)
                if (woco.equals("X"))
                    footer.setVisibility(GONE);
                else
                    footer.setVisibility(VISIBLE);
            if (bundle.getString("ordrId") != null)
                orders_tv.setText(bundle.getString("ordrId"));
            if (Status != null) {
                status_tv.setVisibility(VISIBLE);
                status_tv.setText(bundle.getString("status"));
            }

        }

        statusTabAdapter = new TabAdapter(this, getSupportFragmentManager());

        if (Status != null)
            if (status_tv.getText().toString().equalsIgnoreCase("CRTD")) {
                status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("CANC")) {
                status_tv.setBackground(getDrawable(R.drawable.red_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("REL")) {
                status_tv.setBackground(getDrawable(R.drawable.blue_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("ISSU")) {
                status_tv.setBackground(getDrawable(R.drawable.lightgren_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("DLFL")) {
                status_tv.setBackground(getDrawable(R.drawable.red_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("CLSD")) {
                status_tv.setBackground(getDrawable(R.drawable.green_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else if (status_tv.getText().toString().equalsIgnoreCase("CNF")) {
                status_tv.setBackground(getDrawable(R.drawable.orange_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            } else {
                status_tv.setBackground(getDrawable(R.drawable.yellow_circle));
                status_tv.setTextColor(getResources().getColor(R.color.light_grey2));
            }

        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                osp_al = new ArrayList<>();
                osp_al.addAll(wthSts_al);
                osp_al.addAll(wthOtSts_al);
                osp_al.addAll(sysSts_al);
                Intent intent = new Intent();
                intent.putExtra("status_object", osp_al);
                setResult(RESULT_OK, intent);
                Order_Status_Activity.this.finish();
            }
        });

        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                Order_Status_Activity.this.finish();
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new Get_Order_Data().execute();
    }

    private class Get_Order_Data extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (osp_al != null) {
                wthSts_al.clear();
                wthOtSts_al.clear();
                sysSts_al.clear();

                for (NotifOrdrStatusPrcbl osp : osp_al) {
                    if (WA) {
                        if (osp.getStonr().equals("00") && osp.getStat().startsWith("E")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            wthOtSts_al.add(osp);
                        } else if (!osp.getStonr().equals("00") && osp.getStat().startsWith("E")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            wthSts_al.add(osp);
                        } else if (osp.getStat().startsWith("I")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            sysSts_al.add(osp);
                        }
                    } else {
                        if (!osp.getStonr().equals("00") && osp.getStat().startsWith("E")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            wthSts_al.add(osp);
                        } else if (osp.getStonr().equals("00") && osp.getStat().startsWith("E")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            wthOtSts_al.add(osp);
                        } else if (osp.getStat().startsWith("I")) {
                            if (osp.getAct().equalsIgnoreCase("X"))
                                osp.setSelected(true);
                            else
                                osp.setSelected(false);

                            sysSts_al.add(osp);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager = findViewById(R.id.viewPager);
            viewPager.setOffscreenPageLimit(3);
            statusTabAdapter.addFragment(new StatusSystemFragment(), "System Status");
            statusTabAdapter.addFragment(new StatusWithFragment(), "With Status No.");
            statusTabAdapter.addFragment(new StatusWithOutFragment(), "W/O Status No.");
            viewPager.setAdapter(statusTabAdapter);

            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
