package com.enstrapp.fieldtekpro.successdialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.R;

public class Success_Dialog {
    public String show_success_dialog(final Activity activity, String message) {
        final Dialog success_dialog = new Dialog(activity);
        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.success_checkmark).into(imageview);
        success_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success_dialog.dismiss();
            }
        });
        return null;
    }

    public String dismissActivity(final Activity activity, String message) {
        final Dialog success_dialog = new Dialog(activity);
        success_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        success_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        success_dialog.setCancelable(false);
        success_dialog.setCanceledOnTouchOutside(false);
        success_dialog.setContentView(R.layout.error_dialog);
        ImageView imageview = (ImageView) success_dialog.findViewById(R.id.imageView1);
        TextView description_textview = (TextView) success_dialog.findViewById(R.id.description_textview);
        Button ok_button = (Button) success_dialog.findViewById(R.id.ok_button);
        description_textview.setText(message);
        Glide.with(activity).load(R.drawable.success_checkmark).into(imageview);
        success_dialog.show();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success_dialog.dismiss();
                activity.finish();
            }
        });
        return null;
    }
}

