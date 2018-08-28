package com.enstrapp.fieldtekpro.Utilities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItMatnrPost
{
    @SerializedName("Matnr")
    @Expose
    private String matnr;
    @SerializedName("Maktx")
    @Expose
    private String maktx;
    @SerializedName("Werks")
    @Expose
    private String werks;
    @SerializedName("Lgort")
    @Expose
    private String lgort;
    @SerializedName("Rdate")
    @Expose
    private String rdate;
    @SerializedName("Erfmg")
    @Expose
    private String erfmg;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getErfmg() {
        return erfmg;
    }

    public void setErfmg(String erfmg) {
        this.erfmg = erfmg;
    }
}