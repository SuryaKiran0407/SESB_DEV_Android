package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdrMatrlSer {
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Vornr")
    @Expose
    private String vornr;
    @SerializedName("Uvorn")
    @Expose
    private String uvorn;
    @SerializedName("Rsnum")
    @Expose
    private String rsnum;
    @SerializedName("Rspos")
    @Expose
    private String rspos;
    @SerializedName("Matnr")
    @Expose
    private String matnr;
    @SerializedName("Werks")
    @Expose
    private String werks;
    @SerializedName("Lgort")
    @Expose
    private String lgort;
    @SerializedName("Posnr")
    @Expose
    private String posnr;
    @SerializedName("Bdmng")
    @Expose
    private String bdmng;
    @SerializedName("Meins")
    @Expose
    private String meins;
    @SerializedName("Postp")
    @Expose
    private String postp;
    @SerializedName("Wempf")
    @Expose
    private String wempf;
    @SerializedName("Ablad")
    @Expose
    private String ablad;
    @SerializedName("MatnrText")
    @Expose
    private String matnrText;
    @SerializedName("WerksText")
    @Expose
    private String werksText;
    @SerializedName("LgortText")
    @Expose
    private String lgortText;
    @SerializedName("PostpText")
    @Expose
    private String postpText;
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
    @SerializedName("ItOrderComponentFields")
    @Expose
    private List<Model_CustomInfo> itOrderComponentsFields = null;

    public List<Model_CustomInfo> getItOrderComponentsFields() {
        return itOrderComponentsFields;
    }

    public void setItOrderComponentsFields(List<Model_CustomInfo> itOrderComponentsFields) {
        this.itOrderComponentsFields = itOrderComponentsFields;
    }

    public String getBdmng() {
        return bdmng;
    }

    public void setBdmng(String bdmng) {
        this.bdmng = bdmng;
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

    public String getRsnum() {
        return rsnum;
    }

    public void setRsnum(String rsnum) {
        this.rsnum = rsnum;
    }

    public String getRspos() {
        return rspos;
    }

    public void setRspos(String rspos) {
        this.rspos = rspos;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
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

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getPostp() {
        return postp;
    }

    public void setPostp(String postp) {
        this.postp = postp;
    }

    public String getWempf() {
        return wempf;
    }

    public void setWempf(String wempf) {
        this.wempf = wempf;
    }

    public String getAblad() {
        return ablad;
    }

    public void setAblad(String ablad) {
        this.ablad = ablad;
    }

    public String getMatnrText() {
        return matnrText;
    }

    public void setMatnrText(String matnrText) {
        this.matnrText = matnrText;
    }

    public String getWerksText() {
        return werksText;
    }

    public void setWerksText(String werksText) {
        this.werksText = werksText;
    }

    public String getLgortText() {
        return lgortText;
    }

    public void setLgortText(String lgortText) {
        this.lgortText = lgortText;
    }

    public String getPostpText() {
        return postpText;
    }

    public void setPostpText(String postpText) {
        this.postpText = postpText;
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

