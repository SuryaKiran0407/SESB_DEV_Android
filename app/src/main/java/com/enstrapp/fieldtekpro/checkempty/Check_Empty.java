package com.enstrapp.fieldtekpro.checkempty;

import android.text.TextUtils;

public class Check_Empty {
    public String check_empty(String message) {
        if (message != null && !message.equals("")) {
            if (message.equalsIgnoreCase("null")) {
                return "";
            } else {
                boolean digitsOnly = TextUtils.isDigitsOnly(message);
                /*if (digitsOnly) {
                    long a = Long.parseLong(message);
                    String msg = Long.valueOf(a).toString();
                    return msg;
                } else {
                    return message;
                }*/
                return message;
            }
        } else {
            return "";
        }
    }
}


