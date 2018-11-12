package com.enstrapp.fieldtekpro.login;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enstrapp.fieldtekpro.GPS.GPSTracker;
import com.enstrapp.fieldtekpro.GPS.Location_Checker;
import com.enstrapp.fieldtekpro.Initialload.Auth_SER;
import com.enstrapp.fieldtekpro.Interface.Interface;
import com.enstrapp.fieldtekpro.Passcode.Passcode_Fragment;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.Settings.Settings_Activity;
import com.enstrapp.fieldtekpro.checkempty.Check_Empty;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;
import com.enstrapp.fieldtekpro.fcm.Config;
import com.enstrapp.fieldtekpro.fcm.NotificationUtils;
import com.enstrapp.fieldtekpro.networkconnection.ConnectionDetector;
import com.enstrapp.fieldtekpro.networkconnectiondialog.Network_Connection_Dialog;
import com.enstrapp.fieldtekpro.progressdialog.Custom_Progress_Dialog;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView, settings_iv;
    TextView copyright_textview;
    Button login_button;
    EditText username_edittext, password_edittext;
    CheckBox rememberme_checkbox;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Network_Connection_Dialog network_connection_dialog = new Network_Connection_Dialog();
    String device_id = "", device_serial_number = "", device_uuid = "", header_username = "",
            header_password = "", fieldtekpro_login_username = "", fieldtekpro_login_password = "",
            user_remember_me = "";
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Custom_Progress_Dialog progressDialog = new Custom_Progress_Dialog();
    Error_Dialog error_dialog = new Error_Dialog();
    GPSTracker gps;
    private SQLiteDatabase App_db;
    private String DATABASE_NAME = "";
    private Check_Empty c_e = new Check_Empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */
        device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        device_serial_number = Build.SERIAL;
        String androidId = "" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) device_id.hashCode() << 32) | device_serial_number.hashCode());
        device_uuid = deviceUuid.toString();
        /* Fetching Device Details like Device ID, Device Serial Number and Device UUID */

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    //Toast.makeText(getApplicationContext(), "Got Push notification: " + message, Toast.LENGTH_LONG).show();
                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        if (checkPermissions()) {
            //  permissions  granted.
        }

        gps = new GPSTracker(Login_Activity.this);
        if (gps.canGetLocation()) {
            //double latitude = gps.getLatitude();
            //double longitude = gps.getLongitude();
        } else {
            Location_Checker lc = new Location_Checker();
            lc.location_checker(Login_Activity.this);
        }

        settings_iv = findViewById(R.id.settings_iv);
        imageView = (ImageView) findViewById(R.id.imageView);
        copyright_textview = (TextView) findViewById(R.id.copyright_textview);
        login_button = (Button) findViewById(R.id.login_button);
        username_edittext = (EditText) findViewById(R.id.username_edittext);
        password_edittext = (EditText) findViewById(R.id.password_edittext);
        rememberme_checkbox = (CheckBox) findViewById(R.id.rememberme_checkbox);

        TranslateAnimation animation = new TranslateAnimation(-800.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(35000);
        animation.setRepeatCount(1000000);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

        /* getting current year and application version */
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            copyright_textview.setText("© " + year + " | " + getResources().getString(R.string.app_name) + ". All rights reserved. | Ver: " + version);
        } catch (PackageManager.NameNotFoundException e) {
        }
        /* getting current year and application version */

        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        /* Initializing Shared Preferences */

        fieldtekpro_login_username = FieldTekPro_SharedPref.getString("Username", null);
        fieldtekpro_login_password = FieldTekPro_SharedPref.getString("Password", null);
        user_remember_me = FieldTekPro_SharedPref.getString("FieldTekPro_Remember_Me", null);

        if (user_remember_me == null || user_remember_me.equals("")) {
            rememberme_checkbox.setChecked(false);
        } else {
            if (user_remember_me.equals("X")) {
                rememberme_checkbox.setChecked(true);
                username_edittext.setText(fieldtekpro_login_username);
                password_edittext.setText(fieldtekpro_login_password);
            }
        }

        rememberme_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Remember_Me", "X");
                    FieldTekPro_SharedPrefeditor.commit();
                } else {
                    FieldTekPro_SharedPrefeditor.putString("FieldTekPro_Remember_Me", "");
                    FieldTekPro_SharedPrefeditor.commit();
                }
            }
        });

        /* Fetching Push Interval From Shared Preferences */
        String PushInterval = FieldTekPro_SharedPref.getString("FieldTekPro_PushInterval", null);
        if (PushInterval != null && !PushInterval.equals("")) {
        } else {
            FieldTekPro_SharedPrefeditor.putString("FieldTekPro_PushInterval", "30");
        }
        /* Fetching Push Interval From Shared Preferences */

        FieldTekPro_SharedPrefeditor.commit();

        login_button.setOnClickListener(this);
        settings_iv.setOnClickListener(this);

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(Login_Activity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                } else {
                    String permissionss = "";
                    for (String per : permissions) {
                        permissionss += "\n" + per;
                    }
                    // permissions list of don't granted permission
                }
                return;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.v("kiran_fcm1", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            //txtRegId.setText("Firebase Reg Id: " + regId);
            //Log.v("kiran_fcm2", "Firebase reg id: " + regId);
        } else {
            //txtRegId.setText("Firebase Reg Id is not received yet!");
            //Log.v("kiran_fcm3", "Firebase Reg Id is not received yet!");
        }
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        if (v == login_button)// if login button is clicked
        {
            // checking whether username is entered or not
            if (username_edittext.getText().toString() != null && !username_edittext.getText().toString().equals("")) {
                // checking whether password is entered or not
                if (password_edittext.getText().toString() != null && !password_edittext.getText().toString().equals("")) {
                    fieldtekpro_login_username = FieldTekPro_SharedPref.getString("Username", null);
                    fieldtekpro_login_password = FieldTekPro_SharedPref.getString("Password", null);
                    // checking earlier login is done or not. If done, directly navigating to Dashboard Activity, or else performing login and sending username and password to backend server.
                    String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
                    if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals("")) {
                        if (fieldtekpro_login_password.equals(password_edittext.getText().toString()) && fieldtekpro_login_username.equals(username_edittext.getText().toString())) {
                            cd = new ConnectionDetector(getApplicationContext());
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                FieldTekPro_SharedPrefeditor.putString("Username", fieldtekpro_login_username);
                                FieldTekPro_SharedPrefeditor.putString("Password", fieldtekpro_login_password);
                                FieldTekPro_SharedPrefeditor.putString("header_credentials", header_username + ":" + header_password);
                                FieldTekPro_SharedPrefeditor.commit();

                                String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
                                String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
                                if (InitialLoad != null && !InitialLoad.equals("")) {
                                    if (InitialLoad.equalsIgnoreCase("Checked")) {
                                        new Login().execute();
                                    } else if (Refresh != null && !Refresh.equals("")) {
                                        new Login().execute();
                                    } else {
                                        /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                        startActivity(notification_intent);
                                        Login_Activity.this.finish();*/
                                        /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                        startActivity(notification_intent);
                                        Login_Activity.this.finish();*/
                                        getSupportFragmentManager().beginTransaction()
                                                .add(R.id.main_frag, new Passcode_Fragment())
                                                .commit();
                                    }
                                } else if (Refresh != null && !Refresh.equals("")) {
                                    new Login().execute();
                                } else {
                                    /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    getSupportFragmentManager().beginTransaction()
                                            .add(R.id.main_frag, new Passcode_Fragment())
                                            .commit();
                                }
                            } else {
                                FieldTekPro_SharedPrefeditor.putString("Username", fieldtekpro_login_username);
                                FieldTekPro_SharedPrefeditor.putString("Password", fieldtekpro_login_password);
                                FieldTekPro_SharedPrefeditor.putString("header_credentials", header_username + ":" + header_password);
                                FieldTekPro_SharedPrefeditor.commit();

                                String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
                                String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
                                if (InitialLoad != null && !InitialLoad.equals("")) {
                                    if (InitialLoad.equalsIgnoreCase("Checked")) {
                                        new Login().execute();
                                    } else if (Refresh != null && !Refresh.equals("")) {
                                        new Login().execute();
                                    } else {
                                        /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                        startActivity(notification_intent);
                                        Login_Activity.this.finish();*/
                                        /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                        startActivity(notification_intent);
                                        Login_Activity.this.finish();*/
                                        getSupportFragmentManager().beginTransaction()
                                                .add(R.id.main_frag, new Passcode_Fragment())
                                                .commit();
                                    }
                                } else if (Refresh != null && !Refresh.equals("")) {
                                    new Login().execute();
                                } else {
                                    /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    getSupportFragmentManager().beginTransaction()
                                            .add(R.id.main_frag, new Passcode_Fragment())
                                            .commit();
                                }
                            }
                        } else if (!(fieldtekpro_login_username == username_edittext.getText().toString()) && !(fieldtekpro_login_password == password_edittext.getText().toString())) {
                            cd = new ConnectionDetector(getApplicationContext());
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                new Login().execute();
                            } else {
                                final Dialog network_error_dialog = new Dialog(Login_Activity.this);
                                network_error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                network_error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                network_error_dialog.setCancelable(true);
                                network_error_dialog.setCanceledOnTouchOutside(false);
                                network_error_dialog.setContentView(R.layout.network_error_dialog);
                                final TextView description_textview = (TextView) network_error_dialog.findViewById(R.id.description_textview);
                                final TextView description_textview1 = (TextView) network_error_dialog.findViewById(R.id.description_textview1);
                                Button ok_button = (Button) network_error_dialog.findViewById(R.id.ok_button);
                                Button cancel_button = (Button) network_error_dialog.findViewById(R.id.cancel_button);
                                description_textview.setText(getResources()
                                        .getString(R.string.no_network));
                                description_textview1.setText(getResources()
                                        .getString(R.string.connect_internet));
                                ok_button.setText(getResources().getString(
                                        R.string.connect));
                                description_textview1.setVisibility(View.VISIBLE);
                                network_error_dialog.show();
                                ok_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        network_error_dialog.dismiss();
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                                        startActivity(intent);
                                    }
                                });
                                cancel_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        network_error_dialog.dismiss();
                                    }
                                });
                            }
                        } else {
                            FieldTekPro_SharedPrefeditor.putString("Username", fieldtekpro_login_username);
                            FieldTekPro_SharedPrefeditor.putString("Password", fieldtekpro_login_password);
                            FieldTekPro_SharedPrefeditor.putString("header_credentials", header_username + ":" + header_password);
                            FieldTekPro_SharedPrefeditor.commit();

                            String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
                            String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
                            if (InitialLoad != null && !InitialLoad.equals("")) {
                                if (InitialLoad.equalsIgnoreCase("Checked")) {
                                    new Login().execute();
                                } else if (Refresh != null && !Refresh.equals("")) {
                                    new Login().execute();
                                } else {
                                    /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                    startActivity(notification_intent);
                                    Login_Activity.this.finish();*/
                                    getSupportFragmentManager().beginTransaction()
                                            .add(R.id.main_frag, new Passcode_Fragment())
                                            .commit();
                                }
                            } else if (Refresh != null && !Refresh.equals("")) {
                                new Login().execute();
                            } else {
                                /*Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                startActivity(notification_intent);
                                Login_Activity.this.finish();*/
                                /*Intent notification_intent = new Intent(Login_Activity.this,MainActivity.class);
                                startActivity(notification_intent);
                                Login_Activity.this.finish();*/
                                getSupportFragmentManager().beginTransaction()
                                        .add(R.id.main_frag, new Passcode_Fragment())
                                        .commit();
                            }

                        }
                        /*String InitialLoad = FieldTekPro_SharedPref.getString("FieldTekPro_InitialLoad", null);
                        String Refresh = FieldTekPro_SharedPref.getString("FieldTekPro_Refresh", null);
                        if (InitialLoad != null && !InitialLoad.equals(""))
                        {
                            if (InitialLoad.equalsIgnoreCase("Checked"))
                            {
                                new Login().execute();
                            }
                            else if (Refresh.equalsIgnoreCase("Checked"))
                            {
                                new Login().execute();
                            }
                            else
                            {
                                Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                                startActivity(notification_intent);
                                Login_Activity.this.finish();
                            }
                        }
                        else if (Refresh.equalsIgnoreCase("Checked"))
                        {
                            new Login().execute();
                        }
                        else
                        {
                            Intent notification_intent = new Intent(Login_Activity.this,DashBoard_Activity.class);
                            startActivity(notification_intent);
                            Login_Activity.this.finish();
                        }
                        Intent dashboard_intent = new Intent(Login_Activity.this, DashBoard_Activity.class);
                        startActivity(dashboard_intent);
                        Login_Activity.this.finish();*/
                        //new Login().execute();
                    } else {
                        //if user is not login previously
                        //checking internet connection availability
                        cd = new ConnectionDetector(getApplicationContext());
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            //Performing login asynctask and sending data to backend server.
                            new Login().execute();
                            //Intent dashboard_intent = new Intent(Login_Activity.this, DashBoard_Activity.class);
                            //startActivity(dashboard_intent);
                        } else {
                            //showing network error and navigating to wifi settings.
                            network_connection_dialog.show_network_connection_dialog(Login_Activity.this);
                        }
                    }
                } else {
                    //showing alert message if password is not entered.
                    error_dialog.show_error_dialog(Login_Activity.this,
                            getString(R.string.pls_entpass));
                }
            } else {
                //showing alert message if username is not entered.
                error_dialog.show_error_dialog(Login_Activity.this,
                        getString(R.string.pls_entusr));
            }
        } else if (v == settings_iv) {
            Intent settings_intent = new Intent(Login_Activity.this, Settings_Activity.class);
//            settings_intent.putExtra("Came_From", "Login_Activity");
            startActivity(settings_intent);
        }
    }


    /*Performing Login Asynctask*/
    private class Login extends AsyncTask<Void, Integer, Void> {
        /* Get_User_Data Table and Fields Names */
        private static final String TABLE_GET_USER_DATA = "GET_USER_DATA";
        private static final String KEY_GET_USER_DATA_ID = "id";
        private static final String KEY_SAPUSER = "Sapuser";
        private static final String KEY_MUSER = "Muser";
        private static final String KEY_FNAME = "Fname";
        private static final String KEY_LNAME = "Lname";
        private static final String KEY_KOSTL = "Kostl";
        private static final String KEY_ARBPL = "Arbpl";
        private static final String KEY_IWERK = "Iwerk";
        private static final String KEY_OUNIT = "Ounit";
        private static final String KEY_PERNR = "Pernr";
        private static final String KEY_INGRP = "Ingrp";
        private static final String KEY_PARVW = "Parvw";
        private static final String KEY_PARNR = "Parnr";
        private static final String KEY_SUSER = "Suser";
        private static final String KEY_USTYP = "Ustyp";
        private static final String KEY_USGRP = "Usgrp";
        /* Get_User_Data Table and Fields Names */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show_progress_dialog(Login_Activity.this, getResources().getString(R.string.signing_in));
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                byte[] password_data = password_edittext.getText().toString().getBytes("UTF-8");
                String password_base64 = Base64.encodeToString(password_data, Base64.DEFAULT);
                if (password_base64.contains("\n")) {
                    password_base64 = password_base64.replace("\n", "");
                }
                String username_cap = username_edittext.getText().toString().toUpperCase();
                String username = username_edittext.getText().toString();
                String login_ip_address = getResources().getString(R.string.ip_address);
                String login_endpoint = getResources().getString(R.string.login_url);
                Map<String, String> map = new HashMap<>();
                map.put("x-csrf-token", "fetch");
                map.put("IvUsername", username);
                map.put("IvPassword", password_edittext.getText().toString());
                map.put("IvLanguage", "EN");
                map.put("Muser", username_cap);
                map.put("Deviceid", device_id);
                map.put("Devicesno", device_serial_number);
                map.put("Udid", device_uuid);
                map.put("Accept", "application/json");
                OkHttpClient client = new OkHttpClient.Builder().connectTimeout(90000, TimeUnit.MILLISECONDS).writeTimeout(90000, TimeUnit.MILLISECONDS).readTimeout(90000, TimeUnit.MILLISECONDS).build();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(login_ip_address).client(client).build();
                Interface service = retrofit.create(Interface.class);
                String credentials = username + ":" + password_edittext.getText().toString();
                final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Call<SER_Login> call = service.getLoginDetails(login_endpoint, basic, map);
                call.enqueue(new Callback<SER_Login>() {
                    @Override
                    public void onResponse(Call<SER_Login> call, Response<SER_Login> response) {
                        int login_response_status_code = response.code();
                        Log.v("kiran_login_response_code", login_response_status_code + "...");
                        if (login_response_status_code == 200) {
                            if (response.isSuccessful() && response.body() != null) {
                                try {
                                    DATABASE_NAME = getString(R.string.database_name);
                                    App_db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

                                    /* Creating GET_USER_DATA Table with Fields */
                                    App_db.execSQL("DROP TABLE IF EXISTS " + TABLE_GET_USER_DATA);
                                    String CREATE_GET_USER_DATA_TABLE = "CREATE TABLE IF NOT EXISTS "
                                            + TABLE_GET_USER_DATA + ""
                                            + "( "
                                            + KEY_GET_USER_DATA_ID + " INTEGER PRIMARY KEY,"
                                            + KEY_SAPUSER + " TEXT,"
                                            + KEY_MUSER + " TEXT,"
                                            + KEY_FNAME + " TEXT,"
                                            + KEY_LNAME + " TEXT,"
                                            + KEY_KOSTL + " TEXT,"
                                            + KEY_ARBPL + " TEXT,"
                                            + KEY_IWERK + " TEXT,"
                                            + KEY_OUNIT + " TEXT,"
                                            + KEY_PERNR + " TEXT,"
                                            + KEY_INGRP + " TEXT,"
                                            + KEY_PARVW + " TEXT,"
                                            + KEY_PARNR + " TEXT,"
                                            + KEY_SUSER + " TEXT,"
                                            + KEY_USTYP + " TEXT,"
                                            + KEY_USGRP + " TEXT"
                                            + ")";
                                    App_db.execSQL(CREATE_GET_USER_DATA_TABLE);
                                    /* Creating GET_USER_DATA Table with Fields */

                                    List<SER_Login.Result> results = response.body().getD().getResults();
                                    App_db.beginTransaction();

                                    if (results != null && results.size() > 0) {
                                        Auth_SER.EsUser esUser = results.get(0).getEsuser();
                                        if (esUser != null) {
                                            List<Auth_SER.EsUser_Result> esUserResults = esUser.getResults();
                                            if (esUserResults != null && esUserResults.size() > 0) {
                                                String sql = "Insert into GET_USER_DATA (Sapuser,Muser,Fname,Lname," +
                                                        "Kostl,Arbpl,Iwerk,Ounit,Pernr,Ingrp,Parvw,Parnr,Suser," +
                                                        "Ustyp,Usgrp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                                                SQLiteStatement statement = App_db.compileStatement(sql);
                                                statement.clearBindings();
                                                for (Auth_SER.EsUser_Result eU : esUserResults) {
                                                    statement.bindString(1, c_e.check_empty(eU.getSapuser()));
                                                    statement.bindString(2, c_e.check_empty(eU.getMuser()));
                                                    statement.bindString(3, c_e.check_empty(eU.getFname()));
                                                    statement.bindString(4, c_e.check_empty(eU.getLname()));
                                                    statement.bindString(5, c_e.check_empty(eU.getKostl()));
                                                    statement.bindString(6, c_e.check_empty(eU.getArbpl()));
                                                    statement.bindString(7, c_e.check_empty(eU.getIwerk()));
                                                    statement.bindString(8, c_e.check_empty(eU.getOunit()));
                                                    statement.bindString(9, c_e.check_empty(eU.getPernr()));
                                                    statement.bindString(10, c_e.check_empty(eU.getIngrp()));
                                                    statement.bindString(11, c_e.check_empty(eU.getParvw()));
                                                    statement.bindString(12, c_e.check_empty(eU.getParnr()));
                                                    statement.bindString(13, c_e.check_empty(eU.getSuser()));
                                                    statement.bindString(14, c_e.check_empty(eU.getUstyp()));
                                                    statement.bindString(15, c_e.check_empty(eU.getUsgrp()));
                                                    statement.execute();
                                                }
                                            }
                                        }
                                    }
                                    App_db.setTransactionSuccessful();
                                    App_db.endTransaction();

                                    if (results != null && results.size() > 0) {
                                        if (!results.get(0).getEvFailed()) {
                                            String token = response.headers().get("x-csrf-token");
                                            progressDialog.dismiss_progress_dialog();
                                            FieldTekPro_SharedPrefeditor.putString("Username",
                                                    username_edittext.getText().toString());
                                            FieldTekPro_SharedPrefeditor.putString("Password",
                                                    password_edittext.getText().toString());
                                            FieldTekPro_SharedPrefeditor.putString("header_credentials",
                                                    username_edittext.getText().toString() + ":" +
                                                            password_edittext.getText().toString());
                                            FieldTekPro_SharedPrefeditor.putString("token", token);
                                            FieldTekPro_SharedPrefeditor.putString("webservice_type", "ODATA");
                                            FieldTekPro_SharedPrefeditor.commit();
                                            progressDialog.dismiss_progress_dialog();
                                            getSupportFragmentManager().beginTransaction()
                                                    .add(R.id.main_frag, new Passcode_Fragment())
                                                    .commit();

                                        } else {
                                            progressDialog.dismiss_progress_dialog();
                                            error_dialog.show_error_dialog(Login_Activity.this,
                                                    getString(R.string.lgn_fail));
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            } else if (login_response_status_code == 400) {
                                progressDialog.dismiss_progress_dialog();
                                error_dialog.show_error_dialog(Login_Activity.this,
                                        getString(R.string.usr_notrgstrd));
                            } else if (login_response_status_code == 401) {
                                progressDialog.dismiss_progress_dialog();
                                error_dialog.show_error_dialog(Login_Activity.this,
                                        getString(R.string.auth_fail));
                            } else {
                                progressDialog.dismiss_progress_dialog();
                                error_dialog.show_error_dialog(Login_Activity.this,
                                        getString(R.string.lgn_fail));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SER_Login> call, Throwable t) {
                        progressDialog.dismiss_progress_dialog();
                        error_dialog.show_error_dialog(Login_Activity.this,
                                getString(R.string.lgn_fail));
                    }
                });

            } catch (Exception e) {
                progressDialog.dismiss_progress_dialog();
                error_dialog.show_error_dialog(Login_Activity.this,
                        getString(R.string.lgn_fail));
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
        }
    }
    /*Performing Login Asynctask*/


}
