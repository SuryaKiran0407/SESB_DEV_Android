package com.enstrapp.fieldtekpro.orders;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.util.List;

public class JSA_Add_Activity extends AppCompatActivity implements View.OnClickListener {

    String aufnr = "", wapinr = "", iwerk = "", equipId = "";
    TabLayout tablayout;
    ViewPager viewpager;
    Button cancel_button, submit_button;
    FloatingActionButton add_fab;
    ImageView back_imageview;
    JSA_Tab_Adapter jsa_tab_adapter;
    Error_Dialog error_dialog = new Error_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Dialog submit_decision_dialog;
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    String opstatus_id = "", opstatus_text = "", loc_id = "", loc_text = "", title = "", remark = "", job_id = "", job_text = "";
    List<JSA_AssessmentTeam_Fragment.AssessTeam_List_Object> assessTeam_list_objects = null;
    List<JSA_JobSteps_Fragment.JobStep_List_Object> jobstep_list_object = null;
    List<JSA_Hazards_Fragment.Hazards_List_Object> hazards_list_object = null;
    List<JSA_ImpactsControls_Fragment.ImpactsControls_List_Object> impactsControls_list_objects = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsa_add_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aufnr = extras.getString("aufnr");
            wapinr = extras.getString("wapinr");
            iwerk = extras.getString("iwerk");
            equipId = extras.getString("equipId");
        }


        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        submit_button = (Button) findViewById(R.id.save_button);
        add_fab = (FloatingActionButton) findViewById(R.id.fab);
        back_imageview = (ImageView) findViewById(R.id.back_imageview);

        jsa_tab_adapter = new JSA_Tab_Adapter(this, getSupportFragmentManager());
        jsa_tab_adapter.addFragment(new JSA_BasicInfo_Fragment(), getResources().getString(R.string.basic_info));
        jsa_tab_adapter.addFragment(new JSA_AssessmentTeam_Fragment(), getResources().getString(R.string.assessment_team));
        jsa_tab_adapter.addFragment(new JSA_JobLocation_Fragment(), getResources().getString(R.string.job_location));
        jsa_tab_adapter.addFragment(new JSA_JobSteps_Fragment(), getResources().getString(R.string.job_steps));
        jsa_tab_adapter.addFragment(new JSA_Hazards_Fragment(), getResources().getString(R.string.hazards));
        jsa_tab_adapter.addFragment(new JSA_ImpactsControls_Fragment(), getResources().getString(R.string.impacts_controls));
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
        if (v == cancel_button) {
            JSA_Add_Activity.this.finish();
        } else if (v == back_imageview) {
            JSA_Add_Activity.this.finish();
        } else if (v == submit_button) {
            cd = new ConnectionDetector(JSA_Add_Activity.this);
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                /*Fetching Basic Info*/
                JSA_BasicInfo_Fragment basicinfo_tab = (JSA_BasicInfo_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 0));
                JSA_BasicInfo_Object basicinfo_tabData = basicinfo_tab.getData();
                title = basicinfo_tabData.getTitle();
                remark = basicinfo_tabData.getRemark();
                job_id = basicinfo_tabData.getJob_id();
                job_text = basicinfo_tabData.getJob_text();
                aufnr = basicinfo_tabData.getAufnr();
                /*Fetching Basic Info*/


                /*Fetching Assessment Team Data*/
                JSA_AssessmentTeam_Fragment jsa_assessmentTeam_fragment = (JSA_AssessmentTeam_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 1));
                assessTeam_list_objects = jsa_assessmentTeam_fragment.getAssessmentTeamData();
                /*Fetching Assessment Team Data*/


                /*Fetching Job Location*/
                JSA_JobLocation_Fragment jobLocation_fragment = (JSA_JobLocation_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 2));
                JSA_JobLocation_Object jobLocation_tabData = jobLocation_fragment.getData();
                opstatus_id = jobLocation_tabData.getOpstatus_type_id();
                opstatus_text = jobLocation_tabData.getOpstatus_type_text();
                loc_id = jobLocation_tabData.getLoc_type_id();
                loc_text = jobLocation_tabData.getLoc_type_text();
                /*Fetching Job Location*/


                /*Fetching Job Steps*/
                JSA_JobSteps_Fragment jobsteps_fragment = (JSA_JobSteps_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 3));
                jobstep_list_object = jobsteps_fragment.getJobStepsData();
                /*Fetching Job Steps*/


                /*Fetching Hazards List*/
                JSA_Hazards_Fragment hazards_fragment = (JSA_Hazards_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 4));
                hazards_list_object = hazards_fragment.getHazardListData();
                /*Fetching Hazards List*/


                /*Fetching Impacts & Controls List*/
                JSA_ImpactsControls_Fragment impactsControls_fragment = (JSA_ImpactsControls_Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager, 5));
                impactsControls_list_objects = impactsControls_fragment.getImpactsControlsData();
                /*Fetching Impacts & Controls List*/


                if (title != null && !title.equals("")) {
                    if (job_id != null && !job_id.equals("")) {
                        if (opstatus_id != null && !opstatus_id.equals("")) {
                            if (loc_id != null && !loc_id.equals("")) {
                                if (jobstep_list_object.size() > 0) {
                                    if (hazards_list_object.size() > 0) {
                                        submit_decision_dialog = new Dialog(JSA_Add_Activity.this);
                                        submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        submit_decision_dialog.setCancelable(false);
                                        submit_decision_dialog.setCanceledOnTouchOutside(false);
                                        submit_decision_dialog.setContentView(R.layout.decision_dialog);
                                        ImageView imageView1 = (ImageView) submit_decision_dialog.findViewById(R.id.imageView1);
                                        Glide.with(JSA_Add_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
                                        TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
                                        description_textview.setText(getString(R.string.jsa_creation));
                                        Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
                                        Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
                                        submit_decision_dialog.show();
                                        ok_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                submit_decision_dialog.dismiss();
                                                new Get_Token().execute();
                                            }
                                        });
                                        cancel_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                submit_decision_dialog.dismiss();
                                            }
                                        });
                                    } else {
                                        error_dialog.show_error_dialog(JSA_Add_Activity.this,
                                                getString(R.string.jsa_hazardatleast));
                                    }
                                } else {
                                    error_dialog.show_error_dialog(JSA_Add_Activity.this,
                                            getString(R.string.jsa_jobstpatleast));
                                }
                            } else {
                                error_dialog.show_error_dialog(JSA_Add_Activity.this,
                                        getString(R.string.jsa_locstrtselect));
                            }
                        } else {
                            error_dialog.show_error_dialog(JSA_Add_Activity.this,
                                    getString(R.string.jsa_oprtnstatsselect));
                        }
                    } else {
                        error_dialog.show_error_dialog(JSA_Add_Activity.this,
                                getString(R.string.jsa_jobselect));
                    }
                } else {
                    error_dialog.show_error_dialog(JSA_Add_Activity.this,
                            getString(R.string.jsa_titleselect));
                }
            } else {
                network_connection_dialog.show_network_connection_dialog(JSA_Add_Activity.this);
            }
        }
    }

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    private class Get_Token extends AsyncTask<Void, Integer, Void> {
        String token_status = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(JSA_Add_Activity.this, getResources().getString(R.string.create_jsa_inprogress));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                token_status = Token.Get_Token(JSA_Add_Activity.this);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (token_status.equalsIgnoreCase("success")) {
                custom_progress_dialog.dismiss_progress_dialog();
                new Post_Create_JSA().execute();
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(JSA_Add_Activity.this,
                        getString(R.string.jsa_unable));
            }
        }
    }

    /*Posting JSA Create to Backend Server*/
    private class Post_Create_JSA extends AsyncTask<String, Integer, Void> {
        String jsa_create_status = getString(R.string.jsa_unable);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(JSA_Add_Activity.this,
                    getResources().getString(R.string.create_jsa_inprogress));
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                jsa_create_status = JSA_Create.Post_JSA_Data(JSA_Add_Activity.this,
                        title, remark, job_id, job_text, aufnr, assessTeam_list_objects, opstatus_id,
                        opstatus_text, loc_id, loc_text, jobstep_list_object, hazards_list_object,
                        impactsControls_list_objects);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.v("kiran_jsa_create_status", jsa_create_status + "....");
            if (jsa_create_status != null && !jsa_create_status.equals("")) {
                if (jsa_create_status.startsWith("S")) {
                    final Dialog success_dialog = new Dialog(JSA_Add_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText(jsa_create_status.substring(1));
                    Glide.with(JSA_Add_Activity.this)
                            .load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            success_dialog.dismiss();
                            JSA_Add_Activity.this.finish();
                        }
                    });
                } else if (jsa_create_status.startsWith("U")) {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(JSA_Add_Activity.this,
                            getString(R.string.jsa_unable));
                } else {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(JSA_Add_Activity.this, jsa_create_status.substring(1));
                }
            } else {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(JSA_Add_Activity.this,
                        getString(R.string.jsa_unable));
            }
        }
    }
    /*Posting JSA Create to Backend Server*/

    protected void animateFab(final boolean selected) {
        add_fab.clearAnimation();
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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
                    add_fab.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    add_fab.setImageResource(R.drawable.ic_delete_icon);
                } else {
                    add_fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    add_fab.setImageResource(R.drawable.ic_add_white_24px);
                }

                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f,
                        1f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                add_fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        add_fab.startAnimation(shrink);
    }
}
