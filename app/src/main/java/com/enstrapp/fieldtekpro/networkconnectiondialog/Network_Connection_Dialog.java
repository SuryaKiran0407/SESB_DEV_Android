package com.enstrapp.fieldtekpro.networkconnectiondialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enstrapp.fieldtekpro.R;

public class Network_Connection_Dialog
{
    public String show_network_connection_dialog(final Activity activity)
    {
        final Dialog network_connect_dialog = new Dialog(activity);
        network_connect_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        network_connect_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        network_connect_dialog.setCancelable(false);
        network_connect_dialog.setCanceledOnTouchOutside(false);
        network_connect_dialog.setContentView(R.layout.network_connection_dialog);
        ImageView imageview = (ImageView) network_connect_dialog.findViewById(R.id.imageView1);
        Button connect_button = (Button) network_connect_dialog.findViewById(R.id.connect_button);
        Button cancel_button = (Button) network_connect_dialog.findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                network_connect_dialog.dismiss();
            }
        });
        connect_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                network_connect_dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings","com.android.settings.wifi.WifiSettings");
                activity.startActivity(intent);
            }
        });
        Glide.with(activity).load(R.drawable.internet_connection_unavailable_gif).into(imageview);
        network_connect_dialog.show();
        return null;
    }
}

