package com.enstrapp.fieldtekpro.orders;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;


public class JSA_Change_Activity extends AppCompatActivity implements View.OnClickListener {

    String rasid = "";
    TabLayout tablayout;
    ViewPager viewpager;
    Button cancel_button, submit_button;
    FloatingActionButton add_fab;
    ImageView back_imageview;
    JSA_Tab_Adapter jsa_tab_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    LinearLayout footer_layout;
    TextView title_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rasid = extras.getString("rasid");
        }

        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.save_button);
        add_fab = (FloatingActionButton) findViewById(R.id.fab);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);
        footer_layout = (LinearLayout) findViewById(R.id.footer_layout);
        title_textview = (TextView) findViewById(R.id.title_textview);

        title_textview.setText("RAS ID : " + rasid);

        footer_layout.setVisibility(View.GONE);
        add_fab.setVisibility(View.GONE);

        jsa_tab_adapter = new JSA_Tab_Adapter(this, getSupportFragmentManager());
        jsa_tab_adapter.addFragment(new JSA_Change_BasicInfo_Fragment(), getResources().getString(R.string.basic_info));
        jsa_tab_adapter.addFragment(new JSA_Change_AssessmentTeam_Fragment(), getResources().getString(R.string.assessment_team));
        jsa_tab_adapter.addFragment(new JSA_Change_JobLocation_Fragment(), getResources().getString(R.string.job_location));
        jsa_tab_adapter.addFragment(new JSA_Change_JobSteps_Fragment(), getResources().getString(R.string.job_steps));
        jsa_tab_adapter.addFragment(new JSA_Change_Hazards_Fragment(), getResources().getString(R.string.hazards));
        jsa_tab_adapter.addFragment(new JSA_Change_ImpactsControls_Fragment(), getResources().getString(R.string.impacts_controls));
        viewpager.setOffscreenPageLimit(6);
        viewpager.setAdapter(jsa_tab_adapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
               /* switch (position)
                {
                    case 0:
                        add_fab.hide();
                        break;
                    case 1:
                        add_fab.show();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tablayout.setupWithViewPager(viewpager);

        setCustomFont();

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_imageview.setOnClickListener(this);

    }


    public void setCustomFont() {
        ViewGroup vg = (ViewGroup) tablayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(),
                            "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == back_imageview) {
            JSA_Change_Activity.this.finish();
        }
    }
}
