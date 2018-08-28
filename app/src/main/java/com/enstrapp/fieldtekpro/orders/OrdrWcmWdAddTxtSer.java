package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrWcmWdAddTxtSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Wcnr")
    @Expose
    private String wcnr;
    @SerializedName("Objtype")
    @Expose
    private String objtype;
    @SerializedName("FormatCol")
    @Expose
    private String formatCol;
    @SerializedName("TextLine")
    @Expose
    private String textLine;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getWcnr() {
        return wcnr;
    }

    public void setWcnr(String wcnr) {
        this.wcnr = wcnr;
    }

    public String getObjtype() {
        return objtype;
    }

    public void setObjtype(String objtype) {
        this.objtype = objtype;
    }

    public String getFormatCol() {
        return formatCol;
    }

    public void setFormatCol(String formatCol) {
        this.formatCol = formatCol;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
