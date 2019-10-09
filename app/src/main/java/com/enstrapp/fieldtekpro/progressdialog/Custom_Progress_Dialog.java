package com.enstrapp.fieldtekpro.progressdialog;


import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.enstrapp.fieldtekpro_sesb_dev.R;

public class Custom_Progress_Dialog
{

    Dialog dialog;

    public String show_progress_dialog(Activity activity, String message)
    {
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.custom_progress_dialog);
        TextView description_textview = (TextView) dialog.findViewById(R.id.animation);
        description_textview.setText(message);
        dialog.show();
        return null;
    }

    public String dismiss_progress_dialog()
    {
        dialog.dismiss();
        return null;
    }

}

