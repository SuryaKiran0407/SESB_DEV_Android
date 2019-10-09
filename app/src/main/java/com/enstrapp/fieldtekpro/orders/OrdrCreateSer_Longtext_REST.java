package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrCreateSer_Longtext_REST {
    @SerializedName("AUFNR")
    @Expose
    private String aufnr;
    @SerializedName("ACTIVITY")
    @Expose
    private String activity;
    @SerializedName("TEXT_LINE")
    @Expose
    private String textLine;
    @SerializedName("TDID")
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

