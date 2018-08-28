package com.enstrapp.fieldtekpro.customfont;

import android.app.Application;
import android.graphics.Typeface;

public class CustomFont extends Application
{
    public static Typeface openSansLight;
    @Override
    public void onCreate()
    {
        super.onCreate();
        openSansLight = Typeface.createFromAsset(getAssets(), "fonts/metropolis_medium.ttf");
    }
}
