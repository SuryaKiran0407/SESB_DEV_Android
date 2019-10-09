package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrCreateSer_Status_REST {
    @SerializedName("AUFNR")
    @Expose
    private String aufnr;
    @SerializedName("VORNR")
    @Expose
    private String vornr;
    @SerializedName("OBJNR")
    @Expose
    private String objnr;
    @SerializedName("STSMA")
    @Expose
    private String stsma;
    @SerializedName("INIST")
    @Expose
    private String inist;
    @SerializedName("STONR")
    @Expose
    private String stonr;
    @SerializedName("HSONR")
    @Expose
    private String hsonr;
    @SerializedName("NSONR")
    @Expose
    private String nsonr;
    @SerializedName("STAT")
    @Expose
    private String stat;
    @SerializedName("ACT")
    @Expose
    private String act;
    @SerializedName("TXT04")
    @Expose
    private String txt04;
    @SerializedName("TXT30")
    @Expose
    private String txt30;
    @SerializedName("ACTION")
    @Expose
    private String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getVornr() {
        return vornr;
    }

    public void setVornr(String vornr) {
        this.vornr = vornr;
    }

    public String getObjnr() {
        return objnr;
    }

    public void setObjnr(String objnr) {
        this.objnr = objnr;
    }

    public String getStsma() {
        return stsma;
    }

    public void setStsma(String stsma) {
        this.stsma = stsma;
    }

    public String getInist() {
        return inist;
    }

    public void setInist(String inist) {
        this.inist = inist;
    }

    public String getStonr() {
        return stonr;
    }

    public void setStonr(String stonr) {
        this.stonr = stonr;
    }

    public String getHsonr() {
        return hsonr;
    }

    public void setHsonr(String hsonr) {
        this.hsonr = hsonr;
    }

    public String getNsonr() {
        return nsonr;
    }

    public void setNsonr(String nsonr) {
        this.nsonr = nsonr;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getTxt04() {
        return txt04;
    }

    public void setTxt04(String txt04) {
        this.txt04 = txt04;
    }

    public String getTxt30() {
        return txt30;
    }

    public void setTxt30(String txt30) {
        this.txt30 = txt30;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

