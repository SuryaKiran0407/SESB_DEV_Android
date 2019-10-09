package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdrCreateSer_Operation_REST {
    @SerializedName("AUFNR")
    @Expose
    private String aUFNR;
    @SerializedName("VORNR")
    @Expose
    private String vORNR;
    @SerializedName("UVORN")
    @Expose
    private String uVORN;
    @SerializedName("LTXA1")
    @Expose
    private String lTXA1;
    @SerializedName("ARBPL")
    @Expose
    private String aRBPL;
    @SerializedName("WERKS")
    @Expose
    private String wERKS;
    @SerializedName("STEUS")
    @Expose
    private String sTEUS;
    @SerializedName("LARNT")
    @Expose
    private String lARNT;
    @SerializedName("DAUNO")
    @Expose
    private String dAUNO;
    @SerializedName("DAUNE")
    @Expose
    private String dAUNE;
    @SerializedName("FSAVD")
    @Expose
    private String fSAVD;
    @SerializedName("SSEDD")
    @Expose
    private String sSEDD;
    @SerializedName("PERNR")
    @Expose
    private String pERNR;
    @SerializedName("ASNUM")
    @Expose
    private String aSNUM;
    @SerializedName("PLNTY")
    @Expose
    private String pLNTY;
    @SerializedName("PLNAL")
    @Expose
    private String pLNAL;
    @SerializedName("PLNNR")
    @Expose
    private String pLNNR;
    @SerializedName("RUECK")
    @Expose
    private String rUECK;
    @SerializedName("AUERU")
    @Expose
    private String aUERU;
    @SerializedName("ARBPL_TEXT")
    @Expose
    private String aRBPLTEXT;
    @SerializedName("WERKS_TEXT")
    @Expose
    private String wERKSTEXT;
    @SerializedName("STEUS_TEXT")
    @Expose
    private String sTEUSTEXT;
    @SerializedName("LARNT_TEXT")
    @Expose
    private String lARNTTEXT;
    @SerializedName("KTSCH")
    @Expose
    private String kTSCH;
    @SerializedName("USR01")
    @Expose
    private String uSR01;
    @SerializedName("USR02")
    @Expose
    private String uSR02;
    @SerializedName("USR03")
    @Expose
    private String uSR03;
    @SerializedName("USR04")
    @Expose
    private String uSR04;
    @SerializedName("USR05")
    @Expose
    private String uSR05;
    @SerializedName("ACTION")
    @Expose
    private String action;
    @SerializedName("SAKTO")
    @Expose
    private String sakto;

    public String getSakto() {
        return sakto;
    }

    public void setSakto(String sakto) {
        this.sakto = sakto;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAUFNR() {
        return aUFNR;
    }

    public void setAUFNR(String aUFNR) {
        this.aUFNR = aUFNR;
    }

    public String getVORNR() {
        return vORNR;
    }

    public void setVORNR(String vORNR) {
        this.vORNR = vORNR;
    }

    public String getUVORN() {
        return uVORN;
    }

    public void setUVORN(String uVORN) {
        this.uVORN = uVORN;
    }

    public String getLTXA1() {
        return lTXA1;
    }

    public void setLTXA1(String lTXA1) {
        this.lTXA1 = lTXA1;
    }

    public String getARBPL() {
        return aRBPL;
    }

    public void setARBPL(String aRBPL) {
        this.aRBPL = aRBPL;
    }

    public String getWERKS() {
        return wERKS;
    }

    public void setWERKS(String wERKS) {
        this.wERKS = wERKS;
    }

    public String getSTEUS() {
        return sTEUS;
    }

    public void setSTEUS(String sTEUS) {
        this.sTEUS = sTEUS;
    }

    public String getLARNT() {
        return lARNT;
    }

    public void setLARNT(String lARNT) {
        this.lARNT = lARNT;
    }

    public String getDAUNO() {
        return dAUNO;
    }

    public void setDAUNO(String dAUNO) {
        this.dAUNO = dAUNO;
    }

    public String getDAUNE() {
        return dAUNE;
    }

    public void setDAUNE(String dAUNE) {
        this.dAUNE = dAUNE;
    }

    public String getFSAVD() {
        return fSAVD;
    }

    public void setFSAVD(String fSAVD) {
        this.fSAVD = fSAVD;
    }

    public String getSSEDD() {
        return sSEDD;
    }

    public void setSSEDD(String sSEDD) {
        this.sSEDD = sSEDD;
    }

    public String getPERNR() {
        return pERNR;
    }

    public void setPERNR(String pERNR) {
        this.pERNR = pERNR;
    }

    public String getASNUM() {
        return aSNUM;
    }

    public void setASNUM(String aSNUM) {
        this.aSNUM = aSNUM;
    }

    public String getPLNTY() {
        return pLNTY;
    }

    public void setPLNTY(String pLNTY) {
        this.pLNTY = pLNTY;
    }

    public String getPLNAL() {
        return pLNAL;
    }

    public void setPLNAL(String pLNAL) {
        this.pLNAL = pLNAL;
    }

    public String getPLNNR() {
        return pLNNR;
    }

    public void setPLNNR(String pLNNR) {
        this.pLNNR = pLNNR;
    }

    public String getRUECK() {
        return rUECK;
    }

    public void setRUECK(String rUECK) {
        this.rUECK = rUECK;
    }

    public String getAUERU() {
        return aUERU;
    }

    public void setAUERU(String aUERU) {
        this.aUERU = aUERU;
    }

    public String getARBPLTEXT() {
        return aRBPLTEXT;
    }

    public void setARBPLTEXT(String aRBPLTEXT) {
        this.aRBPLTEXT = aRBPLTEXT;
    }

    public String getWERKSTEXT() {
        return wERKSTEXT;
    }

    public void setWERKSTEXT(String wERKSTEXT) {
        this.wERKSTEXT = wERKSTEXT;
    }

    public String getSTEUSTEXT() {
        return sTEUSTEXT;
    }

    public void setSTEUSTEXT(String sTEUSTEXT) {
        this.sTEUSTEXT = sTEUSTEXT;
    }

    public String getLARNTTEXT() {
        return lARNTTEXT;
    }

    public void setLARNTTEXT(String lARNTTEXT) {
        this.lARNTTEXT = lARNTTEXT;
    }

    public String getKTSCH() {
        return kTSCH;
    }

    public void setKTSCH(String kTSCH) {
        this.kTSCH = kTSCH;
    }

    public String getUSR01() {
        return uSR01;
    }

    public void setUSR01(String uSR01) {
        this.uSR01 = uSR01;
    }

    public String getUSR02() {
        return uSR02;
    }

    public void setUSR02(String uSR02) {
        this.uSR02 = uSR02;
    }

    public String getUSR03() {
        return uSR03;
    }

    public void setUSR03(String uSR03) {
        this.uSR03 = uSR03;
    }

    public String getUSR04() {
        return uSR04;
    }

    public void setUSR04(String uSR04) {
        this.uSR04 = uSR04;
    }

    public String getUSR05() {
        return uSR05;
    }

    public void setUSR05(String uSR05) {
        this.uSR05 = uSR05;
    }

}

