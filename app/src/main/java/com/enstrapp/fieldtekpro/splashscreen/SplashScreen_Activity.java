package com.enstrapp.fieldtekpro.splashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.enstrapp.fieldtekpro_sesb_dev.R;
import com.enstrapp.fieldtekpro.login.Login_Activity;


public class SplashScreen_Activity extends AppCompatActivity
{

    private static int SPLASH_TIME_OUT = 3000;
    SharedPreferences FieldTekPro_SharedPref;
    SharedPreferences.Editor FieldTekPro_SharedPrefeditor;
    private SQLiteDatabase FieldTekPro_db;
    private static String DATABASE_NAME = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);


        FieldTekPro_SharedPref = getApplicationContext().getSharedPreferences("FieldTekPro_SharedPreferences", MODE_PRIVATE);
        FieldTekPro_SharedPrefeditor = FieldTekPro_SharedPref.edit();


        DATABASE_NAME = getString(R.string.database_name);
        FieldTekPro_db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        String fieldtekpro_login_status = FieldTekPro_SharedPref.getString("App_Login_Status", null);
        if (fieldtekpro_login_status != null && !fieldtekpro_login_status.equals(""))
        {
        }


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent splash_login_intent = new Intent(SplashScreen_Activity.this, Login_Activity.class);
                startActivity(splash_login_intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
