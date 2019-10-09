package com.enstrapp.fieldtekpro.equipment_update;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EQUI_Model_REST
{
    @SerializedName("TPLNR")
    @Expose
    private String TPLNR;
    @SerializedName("EQUNR")
    @Expose
    private String EQUNR;
    @SerializedName("EQTYP")
    @Expose
    private String EQTYP;
    @SerializedName("EQART")
    @Expose
    private String EQART;
    @SerializedName("WERKS")
    @Expose
    private String WERKS;
    @SerializedName("ARBPL")
    @Expose
    private String ARBPL;
    @SerializedName("HERST")
    @Expose
    private String HERST;
    @SerializedName("SERGE")
    @Expose
    private String SERGE;
    @SerializedName("TYPBZ")
    @Expose
    private String TYPBZ;
    @SerializedName("MAPAR")
    @Expose
    private String MAPAR;
    @SerializedName("HERLD")
    @Expose
    private String HERLD;
    @SerializedName("BAUJJ")
    @Expose
    private String BAUJJ;
    @SerializedName("BAUMM")
    @Expose
    private String BAUMM;


    public String getTPLNR() {
        return TPLNR;
    }

    public void setTPLNR(String TPLNR) {
        this.TPLNR = TPLNR;
    }

    public String getEQUNR() {
        return EQUNR;
    }

    public void setEQUNR(String EQUNR) {
        this.EQUNR = EQUNR;
    }

    public String getEQTYP() {
        return EQTYP;
    }

    public void setEQTYP(String EQTYP) {
        this.EQTYP = EQTYP;
    }

    public String getEQART() {
        return EQART;
    }

    public void setEQART(String EQART) {
        this.EQART = EQART;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getARBPL() {
        return ARBPL;
    }

    public void setARBPL(String ARBPL) {
        this.ARBPL = ARBPL;
    }

    public String getHERST() {
        return HERST;
    }

    public void setHERST(String HERST) {
        this.HERST = HERST;
    }

    public String getSERGE() {
        return SERGE;
    }

    public void setSERGE(String SERGE) {
        this.SERGE = SERGE;
    }

    public String getTYPBZ() {
        return TYPBZ;
    }

    public void setTYPBZ(String TYPBZ) {
        this.TYPBZ = TYPBZ;
    }

    public String getMAPAR() {
        return MAPAR;
    }

    public void setMAPAR(String MAPAR) {
        this.MAPAR = MAPAR;
    }

    public String getHERLD() {
        return HERLD;
    }

    public void setHERLD(String HERLD) {
        this.HERLD = HERLD;
    }

    public String getBAUJJ() {
        return BAUJJ;
    }

    public void setBAUJJ(String BAUJJ) {
        this.BAUJJ = BAUJJ;
    }

    public String getBAUMM() {
        return BAUMM;
    }

    public void setBAUMM(String BAUMM) {
        this.BAUMM = BAUMM;
    }
}