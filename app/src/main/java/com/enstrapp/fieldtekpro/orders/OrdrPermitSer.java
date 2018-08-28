package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrPermitSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Vornr")
    @Expose
    private String vornr;
    @SerializedName("Permit")
    @Expose
    private String permit;
    @SerializedName("Gntxt")
    @Expose
    private String gntxt;
    @SerializedName("Gntyp")
    @Expose
    private String gntyp;
    @SerializedName("Gytxt")
    @Expose
    private String gytxt;
    @SerializedName("Release")
    @Expose
    private String release;
    @SerializedName("Complete")
    @Expose
    private String complete;
    @SerializedName("NotRelevant")
    @Expose
    private String notRelevant;
    @SerializedName("IssuedBy")
    @Expose
    private String issuedBy;
    @SerializedName("Usr01")
    @Expose
    private String usr01;
    @SerializedName("Usr02")
    @Expose
    private String usr02;
    @SerializedName("Usr03")
    @Expose
    private String usr03;
    @SerializedName("Action")
    @Expose
    private String action;
    private final static long serialVersionUID = 7215552767927365641L;

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

    public String getPermit() {
        return permit;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public String getGntxt() {
        return gntxt;
    }

    public void setGntxt(String gntxt) {
        this.gntxt = gntxt;
    }

    public String getGntyp() {
        return gntyp;
    }

    public void setGntyp(String gntyp) {
        this.gntyp = gntyp;
    }

    public String getGytxt() {
        return gytxt;
    }

    public void setGytxt(String gytxt) {
        this.gytxt = gytxt;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getNotRelevant() {
        return notRelevant;
    }

    public void setNotRelevant(String notRelevant) {
        this.notRelevant = notRelevant;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getUsr01() {
        return usr01;
    }

    public void setUsr01(String usr01) {
        this.usr01 = usr01;
    }

    public String getUsr02() {
        return usr02;
    }

    public void setUsr02(String usr02) {
        this.usr02 = usr02;
    }

    public String getUsr03() {
        return usr03;
    }

    public void setUsr03(String usr03) {
        this.usr03 = usr03;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}

