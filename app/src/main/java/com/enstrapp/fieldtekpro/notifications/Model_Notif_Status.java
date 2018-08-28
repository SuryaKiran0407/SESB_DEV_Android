package com.enstrapp.fieldtekpro.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Model_Notif_Status
{
    @SerializedName("Qmnum")
    @Expose
    private String qmnum;
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Objnr")
    @Expose
    private String objnr;
    @SerializedName("Manum")
    @Expose
    private String manum;
    @SerializedName("Stsma")
    @Expose
    private String stsma;
    @SerializedName("Inist")
    @Expose
    private String inist;
    @SerializedName("Stonr")
    @Expose
    private String stonr;
    @SerializedName("Hsonr")
    @Expose
    private String hsonr;
    @SerializedName("Nsonr")
    @Expose
    private String nsonr;
    @SerializedName("Stat")
    @Expose
    private String stat;
    @SerializedName("Act")
    @Expose
    private String act;
    @SerializedName("Txt04")
    @Expose
    private String txt04;
    @SerializedName("Txt30")
    @Expose
    private String txt30;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getObjnr() {
        return objnr;
    }

    public void setObjnr(String objnr) {
        this.objnr = objnr;
    }

    public String getManum() {
        return manum;
    }

    public void setManum(String manum) {
        this.manum = manum;
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