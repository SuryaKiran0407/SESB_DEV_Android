package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Model_Notif_Longtext_REST
{
    @SerializedName("QMNUM")
    @Expose
    private String qmnum;
    @SerializedName("OBJTYPE")
    @Expose
    private String objtype;
    @SerializedName("OBJKEY")
    @Expose
    private String objkey;
    @SerializedName("TEXT_LINE")
    @Expose
    private String textLine;

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getObjkey() {
        return objkey;
    }

    public void setObjkey(String objkey) {
        this.objkey = objkey;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }
}