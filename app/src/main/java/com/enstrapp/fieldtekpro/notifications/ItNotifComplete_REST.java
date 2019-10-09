package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItNotifComplete_REST {


    @SerializedName("QMNUM")
    @Expose
    private String qMNUM;

    public String getQMNUM() {
        return qMNUM;
    }

    public void setQMNUM(String qMNUM) {
        this.qMNUM = qMNUM;
    }
}
