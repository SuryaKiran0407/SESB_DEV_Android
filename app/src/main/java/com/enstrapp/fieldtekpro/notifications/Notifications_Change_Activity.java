package com.enstrapp.fieldtekpro.notifications;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.Authorizations.Authorizations;
import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.enstrapp.fieldtekpro.Initialload.Token;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.orders.Orders_Create_Activity;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Notifications_Change_Activity extends AppCompatActivity implements View.OnClickListener
{

    TabLayout tablayout;
    ViewPager viewpager;
    Notifications_Tab_Adapter orders_ta;
    Button cancel_button, submit_button;
    Map<String, String> notif_change_status;
    String longtext = "", qmnum = "", uuid = "", primary_user_resp = "", workcenter_id = "", workcenter_text = "", notification_type_id = "", notification_type_text = "", notif_text = "",functionlocation_id = "",functionlocation_text = "",equipment_id = "",equipment_text = "",priority_type_id = "",priority_type_text = "",plannergroup_id = "",plannergroup_text = "",Reported_by = "",personresponsible_id = "",personresponsible_text = "",req_st_date = "",req_st_time = "",req_end_date = "",req_end_time = "",mal_st_date = "",mal_st_time = "",mal_end_date = "",mal_end_time = "",effect_id = "",effect_text = "", plant_id = "";
    Error_Dialog error_dialog = new Error_Dialog();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog();
    FloatingActionButton fab;
    ArrayList<Model_Notif_Causecode> causecodeArrayList = new ArrayList<>();
    ArrayList<Model_CustomInfo> object_custominfo = new ArrayList<>();
    ArrayList<Model_Notif_Activity> ActivityArrayList = new ArrayList<>();
    ArrayList<Model_CustomInfo> activity_custominfo = new ArrayList<>();
    ArrayList<Model_CustomInfo> task_custominfo = new ArrayList<>();
    ArrayList<Model_Notif_Attachments> AttachmentsArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Longtext> LongtextsArrayList = new ArrayList<>();
    ArrayList<Notif_Status_WithNum_Prcbl> status_withnumarray = new ArrayList<>();
    ArrayList<Notif_Status_WithNum_Prcbl> status_withoutnumarray = new ArrayList<>();
    ArrayList<Notif_Status_WithNum_Prcbl> status_systemstatusarray = new ArrayList<>();
    ArrayList<Model_Notif_Status> statusArrayList = new ArrayList<>();
    ArrayList<Model_Notif_Task> TasksArrayList = new ArrayList<>();
    NotifHeaderPrcbl nhp;
    TextView title_tv;
    ImageView back_iv, menu_imageview;
    Dialog submit_decision_dialog, decision_dialog;
    int notif_status = 0;
    LinearLayout order_footer;
    String xstatus = "";
    private static SQLiteDatabase App_db;
    private static String DATABASE_NAME = "";
    ArrayList<HashMap<String, String>> header_custom_info_arraylist = new ArrayList<>();
    ArrayList<Model_CustomInfo> header_custominfo = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_orders_create_change_activity);

        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        submit_button = (Button)findViewById(R.id.save_button);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        title_tv = (TextView)findViewById(R.id.title_tv);
        back_iv = (ImageView)findViewById(R.id.back_iv);
        menu_imageview = (ImageView)findViewById(R.id.menu_imageview);
        order_footer = (LinearLayout)findViewById(R.id.order_footer);

        DATABASE_NAME = Notifications_Change_Activity.this.getString(R.string.database_name);
        App_db = Notifications_Change_Activity.this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String notif_status = extras.getString("notif_status");
            nhp = extras.getParcelable("notif_parcel");
            title_tv.setText(nhp.getQmnum());
            qmnum = nhp.getQmnum();
            uuid = nhp.getUUID();
            if (qmnum != null && !qmnum.equals(""))
            {
                menu_imageview.setVisibility(View.VISIBLE);
                menu_imageview.setVisibility(View.VISIBLE);
            }
            else
            {
                menu_imageview.setVisibility(View.GONE);
                menu_imageview.setVisibility(View.GONE);
            }
            xstatus = nhp.getXStatus();
            if(xstatus.equalsIgnoreCase("NOCO"))
            {
                order_footer.setVisibility(View.GONE);
            }
            else
            {
                order_footer.setVisibility(View.VISIBLE);
            }

        }

        orders_ta = new Notifications_Tab_Adapter(this, getSupportFragmentManager());
        orders_ta.addFragment(new Notifications_Change_Header_Fragment(), getResources().getString(R.string.header));
        orders_ta.addFragment(new Notifications_Change_Causecode_Fragment(), getResources().getString(R.string.causecode));
        orders_ta.addFragment(new Notifications_Change_Activity_Fragment(), getResources().getString(R.string.activity));
        orders_ta.addFragment(new Notifications_Change_Task_Fragment(), getResources().getString(R.string.task));
        orders_ta.addFragment(new Notifications_Change_Attachments_Fragment(), getResources().getString(R.string.attachments));
        viewpager.setOffscreenPageLimit(5);
        viewpager.setAdapter(orders_ta);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels)
            {
            }
            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        fab.hide();
                        break;
                    case 1:
                        String auth_status = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
                        if (auth_status.equalsIgnoreCase("true"))
                        {
                            if(nhp.getQmnum().startsWith("NOT"))
                            {
                                fab.hide();
                            }
                            else
                            {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }
                        break;
                    case 2:
                        String auth_status1 = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
                        if (auth_status1.equalsIgnoreCase("true"))
                        {
                            if(nhp.getQmnum().startsWith("NOT"))
                            {
                                fab.hide();
                            }
                            else
                            {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }
                        break;
                    case 3:
                        String auth_status2 = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
                        if (auth_status2.equalsIgnoreCase("true"))
                        {
                            if(nhp.getQmnum().startsWith("NOT"))
                            {
                                fab.hide();
                            }
                            else
                            {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }
                        break;
                    case 4:
                        String auth_status4 = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
                        if (auth_status4.equalsIgnoreCase("true"))
                        {
                            if(nhp.getQmnum().startsWith("NOT"))
                            {
                                fab.hide();
                            }
                            else
                            {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }
                        break;
                    default:
                        String auth_status3 = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
                        if (auth_status3.equalsIgnoreCase("true"))
                        {
                            if(nhp.getQmnum().startsWith("NOT"))
                            {
                                fab.hide();
                            }
                            else
                            {
                                fab.show();
                            }
                        }
                        else
                        {
                            fab.hide();
                        }
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });

        tablayout.setupWithViewPager(viewpager);

        fab.hide();

        setCustomFont();

        status_withnumarray.clear();
        status_withoutnumarray.clear();
        status_systemstatusarray.clear();

        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        menu_imageview.setOnClickListener(this);

        /*Authorization For Change Notification */
        String auth_change_status = Authorizations.Get_Authorizations_Data(Notifications_Change_Activity.this,"Q","U");
        if (auth_change_status.equalsIgnoreCase("true"))
        {
            if(nhp.getQmnum().startsWith("NOT"))
            {
                menu_imageview.setVisibility(View.GONE);
                order_footer.setVisibility(View.GONE);
            }
            else
            {
                menu_imageview.setVisibility(View.VISIBLE);
                order_footer.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            order_footer.setVisibility(View.GONE);
        }
        /*Authorization For Change Notification */

    }


    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            String qmnum = nhp.getQmnum();
            Cursor cursor = App_db.rawQuery("select * from DUE_NOTIFICATION_NotifHeader where Qmnum = ?", new String[]{qmnum});
            if (cursor != null && cursor.getCount() > 0)
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        String created_aufnr = cursor.getString(20);
                        nhp.setAufnr(created_aufnr);
                    }
                    while (cursor.moveToNext());
                }
            }
            else
            {
                cursor.close();
            }
        }
        catch (Exception e)
        {
        }
    }


    public void setCustomFont()
    {
        ViewGroup vg = (ViewGroup) tablayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++)
        {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++)
            {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView)
                {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/metropolis_medium.ttf"));
                }
            }
        }
    }


    private static String makeFragmentName(int viewPagerId, int index)
    {
        return "android:switcher:" + viewPagerId + ":" + index;
    }


    @Override
    public void onClick(View v)
    {
        if (v == cancel_button)
        {
            Notifications_Change_Activity.this.finish();
        }
        else if (v == back_iv)
        {
            Notifications_Change_Activity.this.finish();
        }
        else if (v == menu_imageview)
        {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.notifications_menu, null);
            final PopupWindow popupWindow = new PopupWindow(popupView,380,ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            TextView status_textview = (TextView)popupView.findViewById(R.id.status_textview);
            TextView create_order_textview = (TextView)popupView.findViewById(R.id.create_order_textview);
            String aufnr = nhp.getAufnr();
            if (aufnr != null && !aufnr.equals(""))
            {
                create_order_textview.setVisibility(View.GONE);
            }
            else
            {
                create_order_textview.setVisibility(View.VISIBLE);
            }
            popupWindow.showAsDropDown(v);
            status_textview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    popupWindow.dismiss();
                    Intent status_intent = new Intent(Notifications_Change_Activity.this,Notifications_Status_Activity.class);
                    status_intent.putExtra("qmnum",qmnum);
                    status_intent.putExtra("uuid",uuid);
                    status_intent.putExtra("status_withNum_array", nhp.getStatus_withNum_prcbls());
                    status_intent.putExtra("status_withoutNum_array", nhp.getStatus_withoutNum_prcbls());
                    status_intent.putExtra("status_systemstatus_array", nhp.getStatus_systemstatus_prcbls());
                    Notifications_Change_Causecode_Fragment causecode_fragment = (Notifications_Change_Causecode_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,1));
                    List<Notifications_Change_Causecode_Fragment.Cause_Code_Object> causecode_list = causecode_fragment.getCauseCodeData();
                    if(causecode_list.size() > 0)
                    {
                        status_intent.putExtra("causecode_status", "true");
                    }
                    else
                    {
                        status_intent.putExtra("causecode_status", "false");
                    }
                    Notifications_Change_Header_Fragment header_tab = (Notifications_Change_Header_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,0));
                    Notifications_Create_Header_Object header_data = header_tab.getData();
                    String mal_end_date = header_data.getMal_end_date_formatted();
                    String mal_end_time = header_data.getMal_end_time_formatted();
                    if (mal_end_date != null && !mal_end_date.equals("") && mal_end_time != null && !mal_end_time.equals(""))
                    {
                        status_intent.putExtra("malfunc_enddate_status", "true");
                    }
                    else
                    {
                        status_intent.putExtra("malfunc_enddate_status", "false");
                    }
                    startActivityForResult(status_intent,notif_status);
                }
            });
            create_order_textview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String order_type_id = "";
                    try
                    {
                        String notif_type_id = nhp.getNotifType();
                        Cursor cursor = App_db.rawQuery("select * from EtTq80 where QMART = ?", new String[]{notif_type_id});
                        if (cursor != null && cursor.getCount() > 0)
                        {
                            if (cursor.moveToFirst())
                            {
                                do
                                {
                                    order_type_id = cursor.getString(2);
                                }
                                while (cursor.moveToNext());
                            }
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    popupWindow.dismiss();
                    Intent notification_order_intent = new Intent(Notifications_Change_Activity.this, Orders_Create_Activity.class);
                    notification_order_intent.putExtra("Order_type_id", order_type_id);
                    notification_order_intent.putExtra("Notification_id", nhp.getQmnum());
                    notification_order_intent.putExtra("short_text", nhp.getNotifShrtTxt());
                    notification_order_intent.putExtra("long_text", nhp.getNotifLngTxt());
                    notification_order_intent.putExtra("functionlocation_id", nhp.getFuncLoc());
                    notification_order_intent.putExtra("equipment_id", nhp.getEquip());
                    notification_order_intent.putExtra("priority_id", nhp.getPriority());
                    notification_order_intent.putExtra("priority_name", nhp.getPriorityTxt());
                    notification_order_intent.putExtra("plannergrp_id", nhp.getIngrp());
                    notification_order_intent.putExtra("plannergrp_text", nhp.getIngrpName());
                    notification_order_intent.putExtra("prsnresp_id", nhp.getParnrVw());
                    notification_order_intent.putExtra("prsnresp_text", nhp.getNameVw());
                    notification_order_intent.putExtra("workcenter", nhp.getArbpl());
                    notification_order_intent.putExtra("Plant", nhp.getWerks());
                    startActivity(notification_order_intent);
                }
            });
        }
        else if (v == submit_button)
        {
            /*Fetching Header Data*/
            Notifications_Change_Header_Fragment header_tab = (Notifications_Change_Header_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,0));
            Notifications_Create_Header_Object header_data = header_tab.getData();
            notification_type_id = header_data.getNotification_type_id();
            notification_type_text = header_data.getNotification_type_text();
            notif_text = header_data.getNotifshtTxt();
            functionlocation_id = header_data.getFunctionlocation_id();
            functionlocation_text = header_data.getFunctionlocation_text();
            equipment_id = header_data.getEquipment_id();
            equipment_text = header_data.getEquipment_text();
            plant_id = header_data.getPlant_id();
            workcenter_id = header_data.getWorkcenter_id();
            workcenter_text = header_data.getWorkcenter_text();
            priority_type_id = header_data.getPriority_type_id();
            priority_type_text = header_data.getPriority_type_text();
            plannergroup_id = header_data.getPlannergroup_id();
            plannergroup_text = header_data.getPlannergroup_text();
            Reported_by = header_data.getReportedby();
            primary_user_resp = header_data.getPrimUsrResp();
            personresponsible_id = header_data.getPersonresponsible_id();
            personresponsible_text = header_data.getPersonresponsible_text();
            req_st_date = header_data.getReq_stdate_date_formated();
            req_st_time = header_data.getReq_stdate_time_formatted();
            req_end_date = header_data.getReq_end_date_formatted();
            req_end_time = header_data.getReq_end_time_formatted();
            mal_st_date = header_data.getMal_st_date_formatted();
            mal_st_time = header_data.getMal_st_time_formatted();
            mal_end_date = header_data.getMal_end_date_formatted();
            mal_end_time = header_data.getMal_end_time_formatted();
            effect_id = header_data.getEffect_id();
            effect_text = header_data.getEffect_text();
            longtext = header_data.getLongtext_text();

            header_custominfo.clear();
            header_custom_info_arraylist.clear();
            header_custom_info_arraylist = header_data.getCustom_info_arraylist();
            if(header_custom_info_arraylist.size() > 0)
            {
                for(int i = 0; i < header_custom_info_arraylist.size(); i++)
                {
                    Model_CustomInfo mnc = new Model_CustomInfo();
                    mnc.setZdoctype(header_custom_info_arraylist.get(i).get("Zdoctype"));
                    mnc.setZdoctypeItem(header_custom_info_arraylist.get(i).get("ZdoctypeItem"));
                    mnc.setTabname(header_custom_info_arraylist.get(i).get("Tabname"));
                    mnc.setFieldname(header_custom_info_arraylist.get(i).get("Fieldname"));
                    mnc.setDatatype(header_custom_info_arraylist.get(i).get("Datatype"));
                    String datatype = header_custom_info_arraylist.get(i).get("Datatype");
                    if(datatype.equalsIgnoreCase("DATS"))
                    {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "MMM dd, yyyy";
                        String outputPattern = "yyyyMMdd";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try
                        {
                            Date date = inputFormat.parse(value);
                            String formatted_date =  outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        }
                        catch (Exception e)
                        {
                            mnc.setValue("");
                        }
                    }
                    else if(datatype.equalsIgnoreCase("TIMS"))
                    {
                        String value = header_custom_info_arraylist.get(i).get("Value");
                        String inputPattern = "HH:mm:ss";
                        String outputPattern = "HHmmss";
                        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        try
                        {
                            Date date = inputFormat.parse(value);
                            String formatted_date =  outputFormat.format(date);
                            mnc.setValue(formatted_date);
                        }
                        catch (Exception e)
                        {
                            mnc.setValue("");
                        }
                    }
                    else
                    {
                        mnc.setValue(header_custom_info_arraylist.get(i).get("Value"));
                    }
                    mnc.setFlabel(header_custom_info_arraylist.get(i).get("Flabel"));
                    mnc.setSequence(header_custom_info_arraylist.get(i).get("Sequence"));
                    mnc.setLength(header_custom_info_arraylist.get(i).get("Length"));
                    header_custominfo.add(mnc);
                }
            }
            /*Fetching Header Data*/


            if (notification_type_id != null && !notification_type_id.equals(""))
            {
                if (notif_text != null && !notif_text.equals(""))
                {
                    if (workcenter_id != null && !workcenter_id.equals(""))
                    {
                        if (priority_type_id != null && !priority_type_id.equals(""))
                        {
                            if (plannergroup_id != null && !plannergroup_id.equals(""))
                            {
                                if (req_st_date != null && !req_st_date.equals("") && req_st_time != null && !req_st_time.equals(""))
                                {
                                    cd = new ConnectionDetector(Notifications_Change_Activity.this);
                                    isInternetPresent = cd.isConnectingToInternet();
                                    if (isInternetPresent)
                                    {
                                        submit_decision_dialog = new Dialog(Notifications_Change_Activity.this);
                                        submit_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        submit_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        submit_decision_dialog.setCancelable(false);
                                        submit_decision_dialog.setCanceledOnTouchOutside(false);
                                        submit_decision_dialog.setContentView(R.layout.decision_dialog);
                                        ImageView imageView1 = (ImageView)submit_decision_dialog.findViewById(R.id.imageView1);
                                        Glide.with(Notifications_Change_Activity.this).load(R.drawable.error_dialog_gif).into(imageView1);
                                        TextView description_textview = (TextView) submit_decision_dialog.findViewById(R.id.description_textview);
                                        description_textview.setText("Do you want to change the selected notification ?");
                                        Button ok_button = (Button) submit_decision_dialog.findViewById(R.id.yes_button);
                                        Button cancel_button = (Button) submit_decision_dialog.findViewById(R.id.no_button);
                                        submit_decision_dialog.show();
                                        ok_button.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                submit_decision_dialog.dismiss();
                                                new Get_Token().execute();
                                            }
                                        });
                                        cancel_button.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                submit_decision_dialog.dismiss();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        decision_dialog = new Dialog(Notifications_Change_Activity.this);
                                        decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        decision_dialog.setCancelable(false);
                                        decision_dialog.setCanceledOnTouchOutside(false);
                                        decision_dialog.setContentView(R.layout.offline_decision_dialog);
                                        TextView description_textview = (TextView)decision_dialog.findViewById(R.id.description_textview);
                                        Button confirm = (Button)decision_dialog.findViewById(R.id.yes_button);
                                        Button cancel = (Button)decision_dialog.findViewById(R.id.no_button);
                                        Button connect_button =(Button) decision_dialog.findViewById(R.id.connect_button);
                                        description_textview.setText("No Internet Connectivity. Do you want to proceed Notification Change with offline ?");
                                        confirm.setText("Yes");
                                        cancel.setText("No");
                                        decision_dialog.show();
                                        cancel.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                decision_dialog.dismiss();
                                            }
                                        });
                                        connect_button.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                decision_dialog.dismiss();
                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                intent.setClassName("com.android.settings","com.android.settings.wifi.WifiSettings");
                                                startActivity(intent);
                                            }
                                        });
                                        confirm.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                decision_dialog.dismiss();
                                                custom_progress_dialog.show_progress_dialog(Notifications_Change_Activity.this,getResources().getString(R.string.change_notif_inprogress));
                                                try
                                                {
                                                    ContentValues values = new ContentValues();
                                                    values.put("NotifShorttxt", notif_text);
                                                    values.put("FunctionLoc", functionlocation_id);
                                                    values.put("Equipment", equipment_id);
                                                    values.put("ReportedBy", Reported_by);
                                                    values.put("MalfuncStdate", mal_st_date);
                                                    values.put("MalfuncEddate", mal_end_date);
                                                    values.put("MalfuncSttime",mal_st_time);
                                                    values.put("MalfuncEdtime",mal_end_time);
                                                    values.put("Priority", priority_type_id);
                                                    values.put("Pltxt", functionlocation_text);
                                                    values.put("Eqktx", equipment_text);
                                                    values.put("Priokx", priority_type_text);
                                                    values.put("Strmn", req_st_date);
                                                    values.put("Ltrmn", req_end_date);
                                                    values.put("Strur",req_st_time);
                                                    values.put("Ltrur",req_end_time);
                                                    values.put("Auswk", effect_id);
                                                    values.put("Auswkt", effect_text);
                                                    values.put("ParnrVw",personresponsible_id);
                                                    values.put("NameVw",personresponsible_text);
                                                    values.put("Ingrp", plannergroup_id);
                                                    values.put("Ingrpname", plannergroup_text);
                                                    values.put("Usr01", primary_user_resp);
                                                    App_db.update("DUE_NOTIFICATION_NotifHeader", values, "Qmnum = ?", new String[] {nhp.getQmnum()});
                                                }
                                                catch(Exception e)
                                                {
                                                }

                                                try
                                                {
                                                    Notifications_Change_Causecode_Fragment causecode_fragment = (Notifications_Change_Causecode_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,1));
                                                    List<Notifications_Change_Causecode_Fragment.Cause_Code_Object> causecode_list = causecode_fragment.getCauseCodeData();
                                                    if(causecode_list.size() > 0)
                                                    {
                                                        String EtNotifItems_sql = "Insert into DUE_NOTIFICATIONS_EtNotifItems (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, CauseGrp, Causegrptext, CauseCod, Causecodetext, CauseShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtNotifItems_statement = App_db.compileStatement(EtNotifItems_sql);
                                                        EtNotifItems_statement.clearBindings();
                                                        App_db.execSQL("delete from DUE_NOTIFICATIONS_EtNotifItems where Qmnum = ?", new String[] {nhp.getQmnum()});
                                                        App_db.beginTransaction();
                                                        for(int i = 0 ; i < causecode_list.size(); i++)
                                                        {
                                                            EtNotifItems_statement.bindString(1,uuid);
                                                            EtNotifItems_statement.bindString(2,nhp.getQmnum());
                                                            EtNotifItems_statement.bindString(3,causecode_list.get(i).getitem_key());
                                                            EtNotifItems_statement.bindString(4,causecode_list.get(i).getobject_part_id());
                                                            EtNotifItems_statement.bindString(5,causecode_list.get(i).getobject_part_text());
                                                            EtNotifItems_statement.bindString(6,causecode_list.get(i).getobjectcode_id());
                                                            EtNotifItems_statement.bindString(7,causecode_list.get(i).getobject_code_text());
                                                            EtNotifItems_statement.bindString(8,causecode_list.get(i).getevent_id());
                                                            EtNotifItems_statement.bindString(9,causecode_list.get(i).getevent_text());
                                                            EtNotifItems_statement.bindString(10,causecode_list.get(i).geteventcode_id());
                                                            EtNotifItems_statement.bindString(11,causecode_list.get(i).geteventcode_text());
                                                            EtNotifItems_statement.bindString(12,causecode_list.get(i).getevent_desc());
                                                            EtNotifItems_statement.bindString(13,causecode_list.get(i).getCause_key());
                                                            EtNotifItems_statement.bindString(14,causecode_list.get(i).getcause_id());
                                                            EtNotifItems_statement.bindString(15,causecode_list.get(i).getcause_text());
                                                            EtNotifItems_statement.bindString(16,causecode_list.get(i).getcausecode_id());
                                                            EtNotifItems_statement.bindString(17,causecode_list.get(i).getcausecode_text());
                                                            EtNotifItems_statement.bindString(18,causecode_list.get(i).getcause_desc());
                                                            EtNotifItems_statement.bindString(19,"");
                                                            EtNotifItems_statement.bindString(20,"");
                                                            EtNotifItems_statement.bindString(21,"");
                                                            EtNotifItems_statement.bindString(22,"");
                                                            EtNotifItems_statement.bindString(23,"");
                                                            EtNotifItems_statement.bindString(24,causecode_list.get(i).getStatus());
                                                            EtNotifItems_statement.execute();
                                                        }
                                                        App_db.setTransactionSuccessful();
                                                        App_db.endTransaction();
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                }

                                                try
                                                {
                                                    Notifications_Change_Activity_Fragment activity_fragment = (Notifications_Change_Activity_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,2));
                                                    List<Notifications_Change_Activity_Fragment.Activity_Object> activity_list = activity_fragment.getActivityData();
                                                    if(activity_list.size() > 0)
                                                    {
                                                        String EtNotifActvs_sql = "Insert into DUE_NOTIFICATION_EtNotifActvs (UUID, Qmnum, ItemKey, ItempartGrp, Partgrptext, ItempartCod, Partcodetext, ItemdefectGrp, Defectgrptext, ItemdefectCod, Defectcodetext, ItemdefectShtxt, CauseKey, ActvKey, ActvGrp, Actgrptext, ActvCod, Actcodetext, ActvShtxt, Usr01, Usr02, Usr03, Usr04, Usr05, Fields, Action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtNotifActvs_statement = App_db.compileStatement(EtNotifActvs_sql);
                                                        EtNotifActvs_statement.clearBindings();
                                                        App_db.execSQL("delete from DUE_NOTIFICATION_EtNotifActvs where Qmnum = ?", new String[] {nhp.getQmnum()});
                                                        App_db.beginTransaction();
                                                        for(int i = 0 ; i < activity_list.size(); i++)
                                                        {
                                                            EtNotifActvs_statement.bindString(1,uuid);
                                                            EtNotifActvs_statement.bindString(2,nhp.getQmnum());
                                                            EtNotifActvs_statement.bindString(3,activity_list.get(i).getCause_itemkey());
                                                            EtNotifActvs_statement.bindString(4,"");
                                                            EtNotifActvs_statement.bindString(5,"");
                                                            EtNotifActvs_statement.bindString(6,activity_list.get(i).getEvent_code());
                                                            EtNotifActvs_statement.bindString(7,"");
                                                            EtNotifActvs_statement.bindString(8,"");
                                                            EtNotifActvs_statement.bindString(9,"");
                                                            EtNotifActvs_statement.bindString(10,activity_list.get(i).getObj_part());
                                                            EtNotifActvs_statement.bindString(11,"");
                                                            EtNotifActvs_statement.bindString(12,"");
                                                            EtNotifActvs_statement.bindString(13,"");
                                                            EtNotifActvs_statement.bindString(14,activity_list.get(i).getActivity_itemkey());
                                                            EtNotifActvs_statement.bindString(15,activity_list.get(i).getCodegroup_id());
                                                            EtNotifActvs_statement.bindString(16,activity_list.get(i).getCodegroup_text());
                                                            EtNotifActvs_statement.bindString(17,activity_list.get(i).getCode_id());
                                                            EtNotifActvs_statement.bindString(18,activity_list.get(i).getCode_text());
                                                            EtNotifActvs_statement.bindString(19,activity_list.get(i).getActivity_shtxt());
                                                            EtNotifActvs_statement.bindString(20,activity_list.get(i).getSt_date());
                                                            EtNotifActvs_statement.bindString(21,activity_list.get(i).getEnd_date());
                                                            EtNotifActvs_statement.bindString(22,activity_list.get(i).getSt_time());
                                                            EtNotifActvs_statement.bindString(23,activity_list.get(i).getEnd_time());
                                                            EtNotifActvs_statement.bindString(24,"");
                                                            EtNotifActvs_statement.bindString(25,"");
                                                            EtNotifActvs_statement.bindString(26,activity_list.get(i).getStatus());
                                                            EtNotifActvs_statement.execute();
                                                        }
                                                        App_db.setTransactionSuccessful();
                                                        App_db.endTransaction();
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                }

                                                try
                                                {
                                                    Notifications_Change_Attachments_Fragment attachment_fragment = (Notifications_Change_Attachments_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,3));
                                                    List<Notif_EtDocs_Parcelable> attachment_list = attachment_fragment.getAttachmentsData();
                                                    if(attachment_list.size() > 0)
                                                    {
                                                        String EtDocs_sql = "Insert into DUE_NOTIFICATION_EtDocs(UUID, Zobjid, Zdoctype, ZdoctypeItem, Filename, Filetype, Fsize, Content, DocId, DocType, Objtype, Filepath, Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                        SQLiteStatement EtDocs_statement = App_db.compileStatement(EtDocs_sql);
                                                        EtDocs_statement.clearBindings();
                                                        App_db.execSQL("delete from DUE_NOTIFICATION_EtDocs where Zobjid = ? and Status = ?", new String[] {nhp.getQmnum(),"New"});
                                                        App_db.beginTransaction();
                                                        for(int i = 0 ; i < attachment_list.size(); i++)
                                                        {
                                                            String status = attachment_list.get(i).getStatus();
                                                            if(status.equalsIgnoreCase("old"))
                                                            {
                                                            }
                                                            else
                                                            {
                                                                EtDocs_statement.bindString(1,uuid);
                                                                EtDocs_statement.bindString(2,nhp.getQmnum());
                                                                EtDocs_statement.bindString(3,attachment_list.get(i).getZdoctype());
                                                                EtDocs_statement.bindString(4,attachment_list.get(i).getZdoctypeitem());
                                                                EtDocs_statement.bindString(5,attachment_list.get(i).getFilename());
                                                                EtDocs_statement.bindString(6,attachment_list.get(i).getFiletype());
                                                                EtDocs_statement.bindString(7,attachment_list.get(i).getFsize());
                                                                EtDocs_statement.bindString(8,"");
                                                                EtDocs_statement.bindString(9,attachment_list.get(i).getDocid());
                                                                EtDocs_statement.bindString(10,attachment_list.get(i).getDoctype());
                                                                EtDocs_statement.bindString(11,attachment_list.get(i).getObjtype());
                                                                EtDocs_statement.bindString(12,attachment_list.get(i).getFilepath());
                                                                EtDocs_statement.bindString(13,"New");
                                                                EtDocs_statement.execute();
                                                            }
                                                        }
                                                        App_db.setTransactionSuccessful();
                                                        App_db.endTransaction();
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                }

                                                try
                                                {
                                                    UUID uniqueKey = UUID.randomUUID();
                                                    DateFormat date_format = new SimpleDateFormat("MMM dd, yyyy");
                                                    DateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                                                    Date todaysdate = new Date();
                                                    String date = date_format.format(todaysdate.getTime());
                                                    String time = time_format.format(todaysdate.getTime());

                                                    String sql11 = "Insert into Alert_Log (DATE, TIME, DOCUMENT_CATEGORY, ACTIVITY_TYPE, USER, OBJECT_ID, STATUS, UUID, MESSAGE, LOG_UUID) values(?,?,?,?,?,?,?,?,?,?);";
                                                    SQLiteStatement statement11 = App_db.compileStatement(sql11);
                                                    App_db.beginTransaction();
                                                    statement11.clearBindings();
                                                    statement11.bindString(1, date);
                                                    statement11.bindString(2, time);
                                                    statement11.bindString(3, "Notification");
                                                    statement11.bindString(4, "Change");
                                                    statement11.bindString(5, "");
                                                    statement11.bindString(6, nhp.getQmnum());
                                                    statement11.bindString(7, "Fail");
                                                    statement11.bindString(8, uuid);
                                                    statement11.bindString(9, "");
                                                    statement11.bindString(10, uniqueKey.toString());
                                                    statement11.execute();
                                                    App_db.setTransactionSuccessful();
                                                    App_db.endTransaction();
                                                }
                                                catch (Exception e)
                                                {
                                                }

                                                custom_progress_dialog.dismiss_progress_dialog();
                                                Notifications_Change_Activity.this.finish();
                                            }
                                        });
                                    }
                                }
                                else
                                {
                                    error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Select Required Start Date & Time");
                                }
                            }
                            else
                            {
                                error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Select Planner Group");
                            }
                        }
                        else
                        {
                            error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Select Priority");
                        }
                    }
                    else
                    {
                        error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Select Work Center");
                    }
                }
                else
                {
                    error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Enter Notification Short Text");
                }
            }
            else
            {
                error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Please Select Notification Type");
            }
        }
    }


    private class Get_Token extends AsyncTask<Void, Integer, Void>
    {
        String token_status = "";
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_Change_Activity.this,getResources().getString(R.string.change_notif_inprogress));
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                token_status = Token.Get_Token(Notifications_Change_Activity.this);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if(token_status.equalsIgnoreCase("success"))
            {
                custom_progress_dialog.dismiss_progress_dialog();
                new Post_Change_Notification().execute("");
            }
            else
            {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Unable to process Notification Change. Please try again");
            }
        }
    }


    /*Posting Notification Change to Backend Server*/
    private class Post_Change_Notification extends AsyncTask<String, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            custom_progress_dialog.show_progress_dialog(Notifications_Change_Activity.this,getResources().getString(R.string.change_notif_inprogress));
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                /*Adding Notification Longtext to Arraylist*/
                if (longtext != null && !longtext.equals(""))
                {
                    if(longtext.contains("\n"))
                    {
                        String[] longtext_array = longtext.split("\n");
                        for(int i = 0; i < longtext_array.length; i++)
                        {
                            Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                            mnc.setQmnum(qmnum);
                            mnc.setObjkey("");
                            mnc.setObjtype("");
                            mnc.setTextLine(longtext_array[i]);
                            LongtextsArrayList.add(mnc);
                        }
                    }
                    else
                    {
                        Model_Notif_Longtext mnc = new Model_Notif_Longtext();
                        mnc.setQmnum(qmnum);
                        mnc.setObjkey("");
                        mnc.setObjtype("");
                        mnc.setTextLine(longtext);
                        LongtextsArrayList.add(mnc);
                    }
                }
                /*Adding Notification Longtext to Arraylist*/


                /*Fetching Cause Code Data*/
                causecodeArrayList = new ArrayList<>();
                Notifications_Change_Causecode_Fragment causecode_fragment = (Notifications_Change_Causecode_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,1));
                List<Notifications_Change_Causecode_Fragment.Cause_Code_Object> causecode_list = causecode_fragment.getCauseCodeData();
                for(int i = 0 ; i < causecode_list.size(); i++)
                {
                    Model_Notif_Causecode mnc = new Model_Notif_Causecode();
                    mnc.setQmnum(qmnum);
                    mnc.setItemKey(causecode_list.get(i).getitem_key());
                    mnc.setItempartGrp(causecode_list.get(i).getobject_part_id());
                    mnc.setPartgrptext(causecode_list.get(i).getobject_part_text());
                    mnc.setItempartCod(causecode_list.get(i).getobjectcode_id());
                    mnc.setPartcodetext(causecode_list.get(i).getobject_code_text());
                    mnc.setItemdefectGrp(causecode_list.get(i).getevent_id());
                    mnc.setDefectgrptext(causecode_list.get(i).getevent_text());
                    mnc.setItemdefectCod(causecode_list.get(i).geteventcode_id());
                    mnc.setDefectcodetext(causecode_list.get(i).geteventcode_text());
                    mnc.setItemdefectShtxt(causecode_list.get(i).getevent_desc());
                    mnc.setCauseKey(causecode_list.get(i).getCause_key());
                    mnc.setCauseGrp(causecode_list.get(i).getcause_id());
                    mnc.setCausegrptext(causecode_list.get(i).getcause_text());
                    mnc.setCauseCod(causecode_list.get(i).getcausecode_id());
                    mnc.setCausecodetext(causecode_list.get(i).getcausecode_text());
                    mnc.setCauseShtxt(causecode_list.get(i).getcause_desc());
                    mnc.setUsr01("");
                    mnc.setUsr02("");
                    mnc.setUsr03("");
                    mnc.setUsr04("");
                    mnc.setUsr05("");
                    mnc.setAction(causecode_list.get(i).getStatus());

                    object_custominfo.clear();
                    /*Fetching Object Custom Fields*/
                    ArrayList<HashMap<String, String>> selected_object_custominfo = causecode_list.get(i).getSelected_object_custom_info_arraylist();
                    if(selected_object_custominfo.size() > 0)
                    {
                        for(int j = 0; j < selected_object_custominfo.size(); j++)
                        {
                            Model_CustomInfo model_customInfo = new Model_CustomInfo();
                            model_customInfo.setZdoctype(selected_object_custominfo.get(j).get("Zdoctype"));
                            model_customInfo.setZdoctypeItem(selected_object_custominfo.get(j).get("ZdoctypeItem"));
                            model_customInfo.setTabname(selected_object_custominfo.get(j).get("Tabname"));
                            model_customInfo.setFieldname(selected_object_custominfo.get(j).get("Fieldname"));
                            model_customInfo.setDatatype(selected_object_custominfo.get(j).get("Datatype"));
                            String datatype = selected_object_custominfo.get(j).get("Datatype");
                            if(datatype.equalsIgnoreCase("DATS"))
                            {
                                String value = selected_object_custominfo.get(j).get("Value");
                                String inputPattern = "MMM dd, yyyy";
                                String outputPattern = "yyyyMMdd";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else if(datatype.equalsIgnoreCase("TIMS"))
                            {
                                String value = selected_object_custominfo.get(j).get("Value");
                                String inputPattern = "HH:mm:ss";
                                String outputPattern = "HHmmss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else
                            {
                                model_customInfo.setValue(selected_object_custominfo.get(j).get("Value"));
                            }
                            model_customInfo.setFlabel(selected_object_custominfo.get(j).get("Flabel"));
                            model_customInfo.setSequence(selected_object_custominfo.get(j).get("Sequence"));
                            model_customInfo.setLength(selected_object_custominfo.get(j).get("Length"));
                            object_custominfo.add(model_customInfo);
                        }
                    }
                    mnc.setItNotifItemsFields(object_custominfo);
                    /*Fetching Object Custom Fields*/


                    /*Fetching Cause Custom Fields*/
                    ArrayList<HashMap<String, String>> selected_Cause_custominfo = causecode_list.get(i).getSelected_cause_custom_info_arraylist();
                    if(selected_Cause_custominfo.size() > 0)
                    {
                        for(int j = 0; j < selected_Cause_custominfo.size(); j++)
                        {
                            Model_CustomInfo model_customInfo = new Model_CustomInfo();
                            model_customInfo.setZdoctype(selected_Cause_custominfo.get(j).get("Zdoctype"));
                            model_customInfo.setZdoctypeItem(selected_Cause_custominfo.get(j).get("ZdoctypeItem"));
                            model_customInfo.setTabname(selected_Cause_custominfo.get(j).get("Tabname"));
                            model_customInfo.setFieldname(selected_Cause_custominfo.get(j).get("Fieldname"));
                            model_customInfo.setDatatype(selected_Cause_custominfo.get(j).get("Datatype"));
                            String datatype = selected_Cause_custominfo.get(j).get("Datatype");
                            if(datatype.equalsIgnoreCase("DATS"))
                            {
                                String value = selected_Cause_custominfo.get(j).get("Value");
                                String inputPattern = "MMM dd, yyyy";
                                String outputPattern = "yyyyMMdd";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else if(datatype.equalsIgnoreCase("TIMS"))
                            {
                                String value = selected_Cause_custominfo.get(j).get("Value");
                                String inputPattern = "HH:mm:ss";
                                String outputPattern = "HHmmss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else
                            {
                                model_customInfo.setValue(selected_Cause_custominfo.get(j).get("Value"));
                            }
                            model_customInfo.setFlabel(selected_Cause_custominfo.get(j).get("Flabel"));
                            model_customInfo.setSequence(selected_Cause_custominfo.get(j).get("Sequence"));
                            model_customInfo.setLength(selected_Cause_custominfo.get(j).get("Length"));
                            object_custominfo.add(model_customInfo);
                        }
                    }
                    mnc.setItNotifItemsFields(object_custominfo);
                    /*Fetching Cause Custom Fields*/


                    causecodeArrayList.add(mnc);
                }
                /*Fetching Cause Code Data*/



                /*Fetching Cause Code Data Deletion*/
                Notifications_Change_Causecode_Fragment causecode_fragment1 = (Notifications_Change_Causecode_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,1));
                List<Notifications_Change_Causecode_Fragment.Cause_Code_Object> causecode_list_delete = causecode_fragment1.getCauseCodeData_Delete();
                for(int i = 0 ; i < causecode_list_delete.size(); i++)
                {
                    Model_Notif_Causecode mnc = new Model_Notif_Causecode();
                    mnc.setQmnum(qmnum);
                    mnc.setItemKey(causecode_list_delete.get(i).getitem_key());
                    mnc.setItempartGrp(causecode_list_delete.get(i).getobject_part_id());
                    mnc.setPartgrptext(causecode_list_delete.get(i).getobject_part_text());
                    mnc.setItempartCod(causecode_list_delete.get(i).getobjectcode_id());
                    mnc.setPartcodetext(causecode_list_delete.get(i).getobject_code_text());
                    mnc.setItemdefectGrp(causecode_list_delete.get(i).getevent_id());
                    mnc.setDefectgrptext(causecode_list_delete.get(i).getevent_text());
                    mnc.setItemdefectCod(causecode_list_delete.get(i).geteventcode_id());
                    mnc.setDefectcodetext(causecode_list_delete.get(i).geteventcode_text());
                    mnc.setItemdefectShtxt(causecode_list_delete.get(i).getevent_desc());
                    mnc.setCauseKey(causecode_list_delete.get(i).getCause_key());
                    mnc.setCauseGrp(causecode_list_delete.get(i).getcause_id());
                    mnc.setCausegrptext(causecode_list_delete.get(i).getcause_text());
                    mnc.setCauseCod(causecode_list_delete.get(i).getcausecode_id());
                    mnc.setCausecodetext(causecode_list_delete.get(i).getcausecode_text());
                    mnc.setCauseShtxt(causecode_list_delete.get(i).getcause_desc());
                    mnc.setUsr01("");
                    mnc.setUsr02("");
                    mnc.setUsr03("");
                    mnc.setUsr04("");
                    mnc.setUsr05("");
                    mnc.setAction("D");
                    causecodeArrayList.add(mnc);
                }
                /*Fetching Cause Code Data Deletion*/




                /*Fetching Activity Data*/
                ActivityArrayList = new ArrayList<>();
                Notifications_Change_Activity_Fragment activity_fragment = (Notifications_Change_Activity_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,2));
                List<Notifications_Change_Activity_Fragment.Activity_Object> activity_list = activity_fragment.getActivityData();
                for(int i = 0 ; i < activity_list.size(); i++)
                {
                    Model_Notif_Activity mnc = new Model_Notif_Activity();
                    mnc.setQmnum(qmnum);
                    mnc.setActvKey(activity_list.get(i).getActivity_itemkey());
                    mnc.setItemKey(activity_list.get(i).getCause_itemkey());
                    mnc.setActvGrp(activity_list.get(i).getCodegroup_id());
                    mnc.setActvCod(activity_list.get(i).getCode_id());
                    mnc.setActcodetext(activity_list.get(i).getCode_text());
                    mnc.setActvShtxt(activity_list.get(i).getActivity_shtxt());
                    mnc.setUsr01(activity_list.get(i).getSt_date());
                    mnc.setUsr02(activity_list.get(i).getEnd_date());
                    mnc.setUsr03(activity_list.get(i).getSt_time());
                    mnc.setUsr04(activity_list.get(i).getEnd_time());
                    mnc.setUsr05("");
                    mnc.setAction(activity_list.get(i).getStatus());

                    activity_custominfo.clear();
                    /*Fetching Activity Custom Fields*/
                    ArrayList<HashMap<String, String>> selected_activity_custominfo = activity_list.get(i).getSelected_activity_custom_info_arraylist();
                    if(selected_activity_custominfo.size() > 0)
                    {
                        for(int j = 0; j < selected_activity_custominfo.size(); j++)
                        {
                            Model_CustomInfo model_customInfo = new Model_CustomInfo();
                            model_customInfo.setZdoctype(selected_activity_custominfo.get(j).get("Zdoctype"));
                            model_customInfo.setZdoctypeItem(selected_activity_custominfo.get(j).get("ZdoctypeItem"));
                            model_customInfo.setTabname(selected_activity_custominfo.get(j).get("Tabname"));
                            model_customInfo.setFieldname(selected_activity_custominfo.get(j).get("Fieldname"));
                            model_customInfo.setDatatype(selected_activity_custominfo.get(j).get("Datatype"));
                            String datatype = selected_activity_custominfo.get(j).get("Datatype");
                            if(datatype.equalsIgnoreCase("DATS"))
                            {
                                String value = selected_activity_custominfo.get(j).get("Value");
                                String inputPattern = "MMM dd, yyyy";
                                String outputPattern = "yyyyMMdd";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else if(datatype.equalsIgnoreCase("TIMS"))
                            {
                                String value = selected_activity_custominfo.get(j).get("Value");
                                String inputPattern = "HH:mm:ss";
                                String outputPattern = "HHmmss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else
                            {
                                model_customInfo.setValue(selected_activity_custominfo.get(j).get("Value"));
                            }
                            model_customInfo.setFlabel(selected_activity_custominfo.get(j).get("Flabel"));
                            model_customInfo.setSequence(selected_activity_custominfo.get(j).get("Sequence"));
                            model_customInfo.setLength(selected_activity_custominfo.get(j).get("Length"));
                            activity_custominfo.add(model_customInfo);
                        }
                    }
                    mnc.setItNotifActvsFields(activity_custominfo);
                    /*Fetching Activity Custom Fields*/


                    ActivityArrayList.add(mnc);
                }
                /*Fetching Activity Data*/



                /*Fetching Activity Data Deletion*/
                ActivityArrayList = new ArrayList<>();
                Notifications_Change_Activity_Fragment activity_fragment1 = (Notifications_Change_Activity_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,2));
                List<Notifications_Change_Activity_Fragment.Activity_Object> activity_list_delete = activity_fragment1.getActivityData_Delete();
                for(int i = 0 ; i < activity_list_delete.size(); i++)
                {
                    Model_Notif_Activity mnc = new Model_Notif_Activity();
                    mnc.setQmnum(qmnum);
                    mnc.setActvKey(activity_list_delete.get(i).getActivity_itemkey());
                    mnc.setItemKey(activity_list_delete.get(i).getCause_itemkey());
                    mnc.setActvGrp(activity_list_delete.get(i).getCodegroup_id());
                    mnc.setActvCod(activity_list_delete.get(i).getCode_id());
                    mnc.setActcodetext(activity_list_delete.get(i).getCode_text());
                    mnc.setActvShtxt(activity_list_delete.get(i).getActivity_shtxt());
                    mnc.setUsr01(activity_list_delete.get(i).getSt_date());
                    mnc.setUsr02(activity_list_delete.get(i).getEnd_date());
                    mnc.setUsr03(activity_list_delete.get(i).getSt_time());
                    mnc.setUsr04(activity_list_delete.get(i).getEnd_time());
                    mnc.setUsr05("");
                    mnc.setAction("D");
                    ActivityArrayList.add(mnc);
                }
                /*Fetching Activity Data Deletion*/



                /*Fetching Task Data*/
                TasksArrayList = new ArrayList<>();
                Notifications_Change_Task_Fragment task_fragment = (Notifications_Change_Task_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,3));
                List<Notifications_Change_Task_Fragment.Task_Object> task_list = task_fragment.getTaskData();
                for(int i = 0 ; i < task_list.size(); i++)
                {
                    Model_Notif_Task mnc = new Model_Notif_Task();
                    mnc.setQmnum(qmnum);
                    mnc.setItemKey(task_list.get(i).getItem_key());
                    mnc.setTaskKey(task_list.get(i).getItem_key());
                    mnc.setTaskGrp(task_list.get(i).getTaskcodegroup_id());
                    mnc.setTaskgrptext(task_list.get(i).getTaskcodegroup_text());
                    mnc.setTaskCod(task_list.get(i).getTaskcode_id());
                    mnc.setTaskcodetext(task_list.get(i).getTaskcode_text());
                    mnc.setTaskShtxt(task_list.get(i).getTask_text());
                    mnc.setPster(task_list.get(i).getPlanned_st_date_formatted());
                    mnc.setPeter(task_list.get(i).getPlanned_end_date_formatted());
                    mnc.setPstur(task_list.get(i).getPlanned_st_time_formatted());
                    mnc.setPetur(task_list.get(i).getPlanned_end_time_formatted());
                    mnc.setParvw(task_list.get(i).getTaskprocessor_id());
                    mnc.setParnr(task_list.get(i).getTask_responsible());
                    mnc.setErlnam(task_list.get(i).getCompletedby());
                    mnc.setErldat(task_list.get(i).getCompletion_date_formatted());
                    mnc.setErlzeit(task_list.get(i).getCompletion_time_formatted());
                    String release = task_list.get(i).getRelease_status();
                    if(release.equalsIgnoreCase("X"))
                    {
                        mnc.setRelease("X");
                    }
                    else
                    {
                        mnc.setRelease("");
                    }
                    String Complete = task_list.get(i).getCompleted_status();
                    if(Complete.equalsIgnoreCase("X"))
                    {
                        mnc.setComplete("X");
                    }
                    else
                    {
                        mnc.setComplete("");
                    }
                    String Success = task_list.get(i).getSuccess_status();
                    if(Success.equalsIgnoreCase("X"))
                    {
                        mnc.setSuccess("X");
                    }
                    else
                    {
                        mnc.setSuccess("");
                    }
                    mnc.setAction(task_list.get(i).getAction());

                    task_custominfo.clear();
                    /*Fetching Task Custom Fields*/
                    ArrayList<HashMap<String, String>> selected_task_custominfo = task_list.get(i).getSelected_tasks_custom_info_arraylist();
                    if(selected_task_custominfo.size() > 0)
                    {
                        for(int j = 0; j < selected_task_custominfo.size(); j++)
                        {
                            Model_CustomInfo model_customInfo = new Model_CustomInfo();
                            model_customInfo.setZdoctype(selected_task_custominfo.get(j).get("Zdoctype"));
                            model_customInfo.setZdoctypeItem(selected_task_custominfo.get(j).get("ZdoctypeItem"));
                            model_customInfo.setTabname(selected_task_custominfo.get(j).get("Tabname"));
                            model_customInfo.setFieldname(selected_task_custominfo.get(j).get("Fieldname"));
                            model_customInfo.setDatatype(selected_task_custominfo.get(j).get("Datatype"));
                            String datatype = selected_task_custominfo.get(j).get("Datatype");
                            if(datatype.equalsIgnoreCase("DATS"))
                            {
                                String value = selected_task_custominfo.get(j).get("Value");
                                String inputPattern = "MMM dd, yyyy";
                                String outputPattern = "yyyyMMdd";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else if(datatype.equalsIgnoreCase("TIMS"))
                            {
                                String value = selected_task_custominfo.get(j).get("Value");
                                String inputPattern = "HH:mm:ss";
                                String outputPattern = "HHmmss";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                try
                                {
                                    Date date = inputFormat.parse(value);
                                    String formatted_date =  outputFormat.format(date);
                                    model_customInfo.setValue(formatted_date);
                                }
                                catch (Exception e)
                                {
                                    model_customInfo.setValue("");
                                }
                            }
                            else
                            {
                                model_customInfo.setValue(selected_task_custominfo.get(j).get("Value"));
                            }
                            model_customInfo.setFlabel(selected_task_custominfo.get(j).get("Flabel"));
                            model_customInfo.setSequence(selected_task_custominfo.get(j).get("Sequence"));
                            model_customInfo.setLength(selected_task_custominfo.get(j).get("Length"));
                            task_custominfo.add(model_customInfo);
                        }
                    }
                    mnc.setItNotfTaskFields(task_custominfo);
                    /*Fetching Task Custom Fields*/


                    TasksArrayList.add(mnc);
                }
                /*Fetching Task Data*/



                /*Fetching Task Data Deletion*/
                Notifications_Change_Task_Fragment task_fragment1 = (Notifications_Change_Task_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,3));
                List<Notifications_Change_Task_Fragment.Task_Object> task_list_delete = task_fragment1.getTaskData_Delete();
                for(int i = 0 ; i < task_list_delete.size(); i++)
                {
                    Model_Notif_Task mnc = new Model_Notif_Task();
                    mnc.setQmnum(qmnum);
                    mnc.setItemKey(task_list_delete.get(i).getItem_key());
                    mnc.setTaskKey(task_list_delete.get(i).getItem_key());
                    mnc.setTaskGrp(task_list_delete.get(i).getTaskcodegroup_id());
                    mnc.setTaskgrptext(task_list_delete.get(i).getTaskcodegroup_text());
                    mnc.setTaskCod(task_list_delete.get(i).getTaskcode_id());
                    mnc.setTaskcodetext(task_list_delete.get(i).getTaskcode_text());
                    mnc.setTaskShtxt(task_list_delete.get(i).getTask_text());
                    mnc.setPster(task_list_delete.get(i).getPlanned_st_date_formatted());
                    mnc.setPeter(task_list_delete.get(i).getPlanned_end_date_formatted());
                    mnc.setPstur(task_list_delete.get(i).getPlanned_st_time_formatted());
                    mnc.setPetur(task_list_delete.get(i).getPlanned_end_time_formatted());
                    mnc.setParvw(task_list_delete.get(i).getTaskprocessor_id());
                    mnc.setParnr(task_list_delete.get(i).getTask_responsible());
                    mnc.setErlnam(task_list_delete.get(i).getCompletedby());
                    mnc.setErldat(task_list_delete.get(i).getCompletion_date_formatted());
                    mnc.setErlzeit(task_list_delete.get(i).getCompletion_time_formatted());
                    String release = task_list_delete.get(i).getRelease_status();
                    if(release.equalsIgnoreCase("X"))
                    {
                        mnc.setRelease("X");
                    }
                    else
                    {
                        mnc.setRelease("");
                    }
                    String Complete = task_list_delete.get(i).getCompleted_status();
                    if(Complete.equalsIgnoreCase("X"))
                    {
                        mnc.setComplete("X");
                    }
                    else
                    {
                        mnc.setComplete("");
                    }
                    String Success = task_list_delete.get(i).getSuccess_status();
                    if(Success.equalsIgnoreCase("X"))
                    {
                        mnc.setSuccess("X");
                    }
                    else
                    {
                        mnc.setSuccess("");
                    }
                    mnc.setAction("D");
                    TasksArrayList.add(mnc);
                }
                /*Fetching Task Data Deletion*/



                /*Fetching Attachments Data*/
                AttachmentsArrayList = new ArrayList<>();
                Notifications_Change_Attachments_Fragment attachment_fragment = (Notifications_Change_Attachments_Fragment)getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.viewpager,4));
                List<Notif_EtDocs_Parcelable> attachment_list = attachment_fragment.getAttachmentsData();
                for(int i = 0 ; i < attachment_list.size(); i++)
                {
                    String status = attachment_list.get(i).getStatus();
                    if(status.equalsIgnoreCase("old"))
                    {
                    }
                    else
                    {
                        Model_Notif_Attachments mnc = new Model_Notif_Attachments();
                        mnc.setObjtype(attachment_list.get(i).getObjtype());
                        mnc.setZobjid(attachment_list.get(i).getZobjid());
                        mnc.setZdoctype(attachment_list.get(i).getZdoctype());
                        mnc.setZdoctypeItem(attachment_list.get(i).getZdoctypeitem());
                        mnc.setFilename(attachment_list.get(i).getFilename());
                        mnc.setFiletype(attachment_list.get(i).getFiletype());
                        mnc.setFsize(attachment_list.get(i).getFsize());
                        mnc.setContent(attachment_list.get(i).getContent());
                        mnc.setDocId(attachment_list.get(i).getDocid());
                        mnc.setDocType(attachment_list.get(i).getDoctype());
                        AttachmentsArrayList.add(mnc);
                    }
                }
                /*Fetching Attachments Data*/


                /*Fetching status withnum Data*/
                if(status_withnumarray.size() > 0)
                {
                    for(int i = 0; i < status_withnumarray.size(); i++)
                    {
                        Model_Notif_Status mns = new Model_Notif_Status();
                        mns.setQmnum(qmnum);
                        mns.setObjnr(status_withnumarray.get(i).getObjnr());
                        mns.setManum(status_withnumarray.get(i).getManum());
                        mns.setStsma(status_withnumarray.get(i).getStsma());
                        mns.setInist(status_withnumarray.get(i).getInist());
                        mns.setStonr(status_withnumarray.get(i).getStonr());
                        mns.setHsonr(status_withnumarray.get(i).getHsonr());
                        mns.setNsonr(status_withnumarray.get(i).getNsonr());
                        mns.setStat(status_withnumarray.get(i).getStat());
                        String act = status_withnumarray.get(i).getAct();
                        String act_status = status_withnumarray.get(i).getAct_Status();
                        if ((act!= null && !act.equals("")) || (act_status!= null && !act_status.equals("")))
                        {
                            mns.setAct("X");
                        }
                        else
                        {
                            mns.setAct("");
                        }
                        mns.setTxt04(status_withnumarray.get(i).getTxt04());
                        mns.setTxt30(status_withnumarray.get(i).getTxt30());
                        mns.setAction("I");
                        statusArrayList.add(mns);
                    }
                }
                /*Fetching status withnum Data*/


                 /*Fetching status withoutnum Data*/
                if(status_withoutnumarray.size() > 0)
                {
                    for(int i = 0; i < status_withoutnumarray.size(); i++)
                    {
                        Model_Notif_Status mns = new Model_Notif_Status();
                        mns.setQmnum(qmnum);
                        mns.setObjnr(status_withoutnumarray.get(i).getObjnr());
                        mns.setManum(status_withoutnumarray.get(i).getManum());
                        mns.setStsma(status_withoutnumarray.get(i).getStsma());
                        mns.setInist(status_withoutnumarray.get(i).getInist());
                        mns.setStonr(status_withoutnumarray.get(i).getStonr());
                        mns.setHsonr(status_withoutnumarray.get(i).getHsonr());
                        mns.setNsonr(status_withoutnumarray.get(i).getNsonr());
                        mns.setStat(status_withoutnumarray.get(i).getStat());
                        String act = status_withoutnumarray.get(i).getAct();
                        String act_status = status_withoutnumarray.get(i).getAct_Status();
                        if ((act!= null && !act.equals("")) || (act_status!= null && !act_status.equals("")))
                        {
                            mns.setAct("X");
                        }
                        else
                        {
                            mns.setAct("");
                        }
                        mns.setTxt04(status_withoutnumarray.get(i).getTxt04());
                        mns.setTxt30(status_withoutnumarray.get(i).getTxt30());
                        mns.setAction("I");
                        statusArrayList.add(mns);
                    }
                }
                /*Fetching status withoutnum Data*/


                String transmit_type = params[0];
                notif_change_status = Notifications_Change.Post_NotifChange_Data(Notifications_Change_Activity.this,transmit_type,qmnum,notification_type_id, notif_text,functionlocation_id, equipment_id, equipment_text,priority_type_id, priority_type_text, plannergroup_id, plannergroup_text, Reported_by, personresponsible_id, personresponsible_text, req_st_date, req_st_time, req_end_date, req_end_time, mal_st_date, mal_st_time, mal_end_date, mal_end_time,effect_id, effect_text, plant_id, workcenter_id, primary_user_resp, causecodeArrayList, ActivityArrayList, AttachmentsArrayList, LongtextsArrayList, statusArrayList, TasksArrayList, header_custominfo);
            }
            catch (Exception e)
            {
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (notif_change_status.get("response_status") != null && !notif_change_status.get("response_status").equals(""))
            {
                if(notif_change_status.get("response_status").equalsIgnoreCase("Duplicate"))
                {
                    String duplicate_data = notif_change_status.get("response_data");
                    if (duplicate_data != null && ! duplicate_data.equals(""))
                    {

                    }
                    else
                    {
                        custom_progress_dialog.dismiss_progress_dialog();
                        error_dialog.show_error_dialog(Notifications_Change_Activity.this,"No Duplicate Notifications Found. Please try again.");
                    }
                }
                else if(notif_change_status.get("response_status").equalsIgnoreCase("success"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    final Dialog success_dialog = new Dialog(Notifications_Change_Activity.this);
                    success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    success_dialog.setCancelable(false);
                    success_dialog.setCanceledOnTouchOutside(false);
                    success_dialog.setContentView(R.layout.error_dialog);
                    ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
                    TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
                    Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
                    description_textview.setText("Notification "+ notif_change_status.get("response_data")+" has been changed successfully.");
                    Glide.with(Notifications_Change_Activity.this).load(R.drawable.success_checkmark).into(imageview);
                    success_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            success_dialog.dismiss();
                            Notifications_Change_Activity.this.finish();
                        }
                    });
                }
                else if(notif_change_status.get("response_status").startsWith("E"))
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_Change_Activity.this,notif_change_status.get("response_status").toString().substring(1));
                }
                else
                {
                    custom_progress_dialog.dismiss_progress_dialog();
                    error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Unable to process Notification Change. Please try again.");
                }
            }
            else
            {
                custom_progress_dialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Notifications_Change_Activity.this,"Unable to process Notification Change. Please try again.");
            }
        }
    }
    /*Posting Notification Change to Backend Server*/


    // Call Back method  to get the Message form other Activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == notif_status)
            {
                if(resultCode == RESULT_OK)
                {
                    status_withnumarray = data.getParcelableArrayListExtra("status_withNum_array");
                    nhp.setStatus_withNum_prcbls(status_withnumarray);
                    status_withoutnumarray = data.getParcelableArrayListExtra("status_withoutNum_array");
                    nhp.setStatus_withoutNum_prcbls(status_withoutnumarray);
                    status_systemstatusarray = data.getParcelableArrayListExtra("status_systemstatus_array");
                    nhp.setStatus_systemstatus_prcbls(status_systemstatusarray);
                }
            }
        }
    }



    protected void animateFab(final boolean selected)
    {
        fab.clearAnimation();
        ScaleAnimation shrink = new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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



}
