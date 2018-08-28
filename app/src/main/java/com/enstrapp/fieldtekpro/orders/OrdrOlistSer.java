package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdrOlistSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Obknr")
    @Expose
    private String obknr;
    @SerializedName("Obzae")
    @Expose
    private String obzae;
    @SerializedName("Qmnum")
    @Expose
    private String qmnum;
    @SerializedName("Equnr")
    @Expose
    private String equnr;
    @SerializedName("Strno")
    @Expose
    private String strno;
    @SerializedName("Tplnr")
    @Expose
    private String tplnr;
    @SerializedName("Bautl")
    @Expose
    private String bautl;
    @SerializedName("Qmtxt")
    @Expose
    private String qmtxt;
    @SerializedName("Pltxt")
    @Expose
    private String pltxt;
    @SerializedName("Eqktx")
    @Expose
    private String eqktx;
    @SerializedName("Maktx")
    @Expose
    private String maktx;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getObknr() {
        return obknr;
    }

    public void setObknr(String obknr) {
        this.obknr = obknr;
    }

    public String getObzae() {
        return obzae;
    }

    public void setObzae(String obzae) {
        this.obzae = obzae;
    }

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getEqunr() {
        return equnr;
    }

    public void setEqunr(String equnr) {
        this.equnr = equnr;
    }

    public String getStrno() {
        return strno;
    }

    public void setStrno(String strno) {
        this.strno = strno;
    }

    public String getTplnr() {
        return tplnr;
    }

    public void setTplnr(String tplnr) {
        this.tplnr = tplnr;
    }

    public String getBautl() {
        return bautl;
    }

    public void setBautl(String bautl) {
        this.bautl = bautl;
    }

    public String getQmtxt() {
        return qmtxt;
    }

    public void setQmtxt(String qmtxt) {
        this.qmtxt = qmtxt;
    }

    public String getPltxt() {
        return pltxt;
    }

    public void setPltxt(String pltxt) {
        this.pltxt = pltxt;
    }

    public String getEqktx() {
        return eqktx;
    }

    public void setEqktx(String eqktx) {
        this.eqktx = eqktx;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

