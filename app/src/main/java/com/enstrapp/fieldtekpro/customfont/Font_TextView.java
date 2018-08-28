package com.enstrapp.fieldtekpro.customfont;


import android.content.Context;
import android.util.AttributeSet;

public class Font_TextView extends android.support.v7.widget.AppCompatTextView
{
    public Font_TextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    public Font_TextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public Font_TextView(Context context)
    {
        super(context);
        init();
    }
    private void init()
    {
        setTypeface(new CustomFont().openSansLight);
    }
}
