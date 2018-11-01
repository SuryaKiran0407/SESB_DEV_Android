package com.enstrapp.fieldtekpro.checkempty;

import android.text.TextUtils;

public class Check_Empty {
    String data = "";

    public String check_empty(String message) {
        if (message != null && !message.equals("")) {
            if (message.equalsIgnoreCase("null")) {
                data = "";
            } else {
                boolean digitsOnly = TextUtils.isDigitsOnly(message);
                if (digitsOnly) {
                    long a = Long.parseLong(message);
                    String msg = Long.valueOf(a).toString();
                    data = msg;
                } else {
                    data = message;
                }
            }
        } else {
            data = "";
        }
        return data;
    }
}


