package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdrOprtnSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Vornr")
    @Expose
    private String vornr;
    @SerializedName("Uvorn")
    @Expose
    private String uvorn;
    @SerializedName("Ltxa1")
    @Expose
    private String ltxa1;
    @SerializedName("Arbpl")
    @Expose
    private String arbpl;
    @SerializedName("Werks")
    @Expose
    private String werks;
    @SerializedName("Steus")
    @Expose
    private String steus;
    @SerializedName("Larnt")
    @Expose
    private String larnt;
    @SerializedName("Dauno")
    @Expose
    private String dauno;
    @SerializedName("Daune")
    @Expose
    private String daune;
    @SerializedName("Fsavd")
    @Expose
    private String fsavd;
    @SerializedName("Ssedd")
    @Expose
    private String ssedd;
    @SerializedName("Pernr")
    @Expose
    private String pernr;
    @SerializedName("Asnum")
    @Expose
    private String asnum;
    @SerializedName("Plnty")
    @Expose
    private String plnty;
    @SerializedName("Plnal")
    @Expose
    private String plnal;
    @SerializedName("Plnnr")
    @Expose
    private String plnnr;
    @SerializedName("Rueck")
    @Expose
    private String rueck;
    @SerializedName("Aueru")
    @Expose
    private String aueru;
    @SerializedName("ArbplText")
    @Expose
    private String arbplText;
    @SerializedName("WerksText")
    @Expose
    private String werksText;
    @SerializedName("SteusText")
    @Expose
    private String steusText;
    @SerializedName("LarntText")
    @Expose
    private String larntText;
    @SerializedName("Usr01")
    @Expose
    private String usr01;
    @SerializedName("Usr02")
    @Expose
    private String usr02;
    @SerializedName("Usr03")
    @Expose
    private String usr03;
    @SerializedName("Usr04")
    @Expose
    private String usr04;
    @SerializedName("Usr05")
    @Expose
    private String usr05;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("ItOrderOperationFields")
    @Expose
    private List<Model_CustomInfo> itOrderOperationFields = null;

    public List<Model_CustomInfo> getItOrderOperationFields() {
        return itOrderOperationFields;
    }

    public void setItOrderOperationFields(List<Model_CustomInfo> itOrderOperationFields) {
        this.itOrderOperationFields = itOrderOperationFields;
    }

    public String getDauno() {
        return dauno;
    }

    public void setDauno(String dauno) {
        this.dauno = dauno;
    }

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

    public String getUvorn() {
        return uvorn;
    }

    public void setUvorn(String uvorn) {
        this.uvorn = uvorn;
    }

    public String getLtxa1() {
        return ltxa1;
    }

    public void setLtxa1(String ltxa1) {
        this.ltxa1 = ltxa1;
    }

    public String getArbpl() {
        return arbpl;
    }

    public void setArbpl(String arbpl) {
        this.arbpl = arbpl;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getSteus() {
        return steus;
    }

    public void setSteus(String steus) {
        this.steus = steus;
    }

    public String getLarnt() {
        return larnt;
    }

    public void setLarnt(String larnt) {
        this.larnt = larnt;
    }

    public String getDaune() {
        return daune;
    }

    public void setDaune(String daune) {
        this.daune = daune;
    }

    public String getFsavd() {
        return fsavd;
    }

    public void setFsavd(String fsavd) {
        this.fsavd = fsavd;
    }

    public String getSsedd() {
        return ssedd;
    }

    public void setSsedd(String ssedd) {
        this.ssedd = ssedd;
    }

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getAsnum() {
        return asnum;
    }

    public void setAsnum(String asnum) {
        this.asnum = asnum;
    }

    public String getPlnty() {
        return plnty;
    }

    public void setPlnty(String plnty) {
        this.plnty = plnty;
    }

    public String getPlnal() {
        return plnal;
    }

    public void setPlnal(String plnal) {
        this.plnal = plnal;
    }

    public String getPlnnr() {
        return plnnr;
    }

    public void setPlnnr(String plnnr) {
        this.plnnr = plnnr;
    }

    public String getRueck() {
        return rueck;
    }

    public void setRueck(String rueck) {
        this.rueck = rueck;
    }

    public String getAueru() {
        return aueru;
    }

    public void setAueru(String aueru) {
        this.aueru = aueru;
    }

    public String getArbplText() {
        return arbplText;
    }

    public void setArbplText(String arbplText) {
        this.arbplText = arbplText;
    }

    public String getWerksText() {
        return werksText;
    }

    public void setWerksText(String werksText) {
        this.werksText = werksText;
    }

    public String getSteusText() {
        return steusText;
    }

    public void setSteusText(String steusText) {
        this.steusText = steusText;
    }

    public String getLarntText() {
        return larntText;
    }

    public void setLarntText(String larntText) {
        this.larntText = larntText;
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

    public String getUsr04() {
        return usr04;
    }

    public void setUsr04(String usr04) {
        this.usr04 = usr04;
    }

    public String getUsr05() {
        return usr05;
    }

    public void setUsr05(String usr05) {
        this.usr05 = usr05;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

