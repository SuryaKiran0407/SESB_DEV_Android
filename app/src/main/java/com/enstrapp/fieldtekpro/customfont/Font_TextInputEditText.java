package com.enstrapp.fieldtekpro.customfont;


import android.content.Context;
import android.util.AttributeSet;

public class Font_TextInputEditText extends android.support.design.widget.TextInputEditText
{
    public Font_TextInputEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    public Font_TextInputEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public Font_TextInputEditText(Context context)
    {
        super(context);
        init();
    }
    private void init()
    {
        setTypeface(new CustomFont().openSansLight);
    }
}
