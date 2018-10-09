package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrLngTxtSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Activity")
    @Expose
    private String activity;
    @SerializedName("TextLine")
    @Expose
    private String textLine;
    @SerializedName("Tdid")
    @Expose
    private String Tdid;

    public String getTdid() {
        return Tdid;
    }

    public void setTdid(String tdid) {
        Tdid = tdid;
    }

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }
}

