package com.enstrapp.fieldtekpro.errordialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro_sesb_dev.R;

public class Error_Dialog
{
    public String show_error_dialog(Activity activity, String message)
    {
        final Dialog error_dialog = new Dialog(activity);
        error_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        error_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        error_dialog.setCancelable(false);
        error_dialog.setCanceledOnTouchOutside(false);
        error_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) error_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) error_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) error_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.error_dialog_gif).into(imageview);
        error_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                error_dialog.dismiss();
            }
        });
        return null;
    }
}

