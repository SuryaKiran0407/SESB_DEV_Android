package com.enstrapp.fieldtekpro.orders;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItMatnrPost_REST
{
    @SerializedName("MATNR")
    @Expose
    private String matnr;
    @SerializedName("MAKTX")
    @Expose
    private String maktx;
    @SerializedName("WERKS")
    @Expose
    private String werks;
    @SerializedName("LGORT")
    @Expose
    private String lgort;
    @SerializedName("RDATE")
    @Expose
    private String rdate;
    @SerializedName("ERFMG")
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