package com.enstrapp.fieldtekpro.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.enstrapp.fieldtekpro.Auto_Sync.Auto_Sync_BackgroundService;
import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.login.Login_Activity;


public class SplashScreen_Activity extends AppCompatActivity {

    /*Splash screen timer*/
    private static int SPLASH_TIME_OUT = 3000;
    /*Splash screen timer*/
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);


        /* Initializing Shared Preferences */
        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();
        /* Initializing Shared Preferences */


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
        if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals("")) {
//            startService(new Intent(this, Auto_Sync_BackgroundService.class));
            /*ArrayList<String> alertlog_list = new ArrayList<String>();
            try
            {
                Cursor cursor = FieldTekPro_db.rawQuery("select * from Alert_Log ORDER BY ID DESC",null);
                if(cursor!=null && cursor.getCount()> 0)
                {
                    cursor.moveToNext();
                    do
                    {
                        String status = cursor.getString(7);
                        if(status.equalsIgnoreCase("Fail"))
                        {
                            alertlog_list.add(cursor.getString(6));
                        }
                    }
                    while
                    (
                        cursor.moveToNext()
                    );
                }
                else
                {
                    cursor.close();
                }
            }
            catch (Exception e)
            {
            }
            if(alertlog_list.size() > 0)
            {
                startService(new Intent(this, Auto_Sync_BackgroundService.class));
            }*/
        }

        /*Handler to execute timer and navigate to login activity*/
        new Handler().postDelayed(new Runnable() {
            /*Showing splash screen with a timer. This will be useful when you want to show case your app logo / company */
            @Override
            public void run() {
                /*This method will be executed once the timer is over.*/
                /*Navigating to Login Activity*/
                Intent splash_login_intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                startActivity(splash_login_intent);
                /*close this splash screen activity*/
                finish();
            }
        }, SPLASH_TIME_OUT);
        /*Handler to execute timer and navigate to login activity*/

    }

}
