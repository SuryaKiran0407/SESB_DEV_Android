package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Activity_REST
{
    @SerializedName("QMNUM")
    @Expose
    private String qMNUM;
    @SerializedName("ITEM_KEY")
    @Expose
    private String iTEMKEY;
    @SerializedName("ITEMPART_GRP")
    @Expose
    private String iTEMPARTGRP;
    @SerializedName("PARTGRPTEXT")
    @Expose
    private String pARTGRPTEXT;
    @SerializedName("ITEMPART_COD")
    @Expose
    private String iTEMPARTCOD;
    @SerializedName("PARTCODETEXT")
    @Expose
    private String pARTCODETEXT;
    @SerializedName("ITEMDEFECT_GRP")
    @Expose
    private String iTEMDEFECTGRP;
    @SerializedName("DEFECTGRPTEXT")
    @Expose
    private String dEFECTGRPTEXT;
    @SerializedName("ITEMDEFECT_COD")
    @Expose
    private String iTEMDEFECTCOD;
    @SerializedName("DEFECTCODETEXT")
    @Expose
    private String dEFECTCODETEXT;
    @SerializedName("ITEMDEFECT_SHTXT")
    @Expose
    private String iTEMDEFECTSHTXT;
    @SerializedName("CAUSE_KEY")
    @Expose
    private String cAUSEKEY;
    @SerializedName("ACT_KEY")
    @Expose
    private String aCTKEY;
    @SerializedName("ACT_CODEGRP")
    @Expose
    private String aCTVGRP;
    @SerializedName("ACTGRPTEXT")
    @Expose
    private String aCTGRPTEXT;
    @SerializedName("ACT_CODE")
    @Expose
    private String aCTVCOD;
    @SerializedName("ACTCODETEXT")
    @Expose
    private String aCTCODETEXT;
    @SerializedName("ACTTEXT")
    @Expose
    private String aCTVSHTXT;
    @SerializedName("START_DATE")
    @Expose
    private String sTARTDATE;
    @SerializedName("START_TIME")
    @Expose
    private String sTARTTIME;
    @SerializedName("END_DATE")
    @Expose
    private String eNDDATE;
    @SerializedName("END_TIME")
    @Expose
    private String eNDTIME;
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
    @SerializedName("FIELDS")
    @Expose
    private String fIELDS;
    @SerializedName("ACTION")
    @Expose
    private String aCTION;

    public String getSTARTDATE() {
        return sTARTDATE;
    }

    public void setSTARTDATE(String sTARTDATE) {
        this.sTARTDATE = sTARTDATE;
    }

    public String getSTARTTIME() {
        return sTARTTIME;
    }

    public void setSTARTTIME(String sTARTTIME) {
        this.sTARTTIME = sTARTTIME;
    }

    public String getENDDATE() {
        return eNDDATE;
    }

    public void setENDDATE(String eNDDATE) {
        this.eNDDATE = eNDDATE;
    }

    public String getENDTIME() {
        return eNDTIME;
    }

    public void setENDTIME(String eNDTIME) {
        this.eNDTIME = eNDTIME;
    }

    public String getQMNUM() {
        return qMNUM;
    }

    public void setQMNUM(String qMNUM) {
        this.qMNUM = qMNUM;
    }

    public String getITEMKEY() {
        return iTEMKEY;
    }

    public void setITEMKEY(String iTEMKEY) {
        this.iTEMKEY = iTEMKEY;
    }

    public String getITEMPARTGRP() {
        return iTEMPARTGRP;
    }

    public void setITEMPARTGRP(String iTEMPARTGRP) {
        this.iTEMPARTGRP = iTEMPARTGRP;
    }

    public String getPARTGRPTEXT() {
        return pARTGRPTEXT;
    }

    public void setPARTGRPTEXT(String pARTGRPTEXT) {
        this.pARTGRPTEXT = pARTGRPTEXT;
    }

    public String getITEMPARTCOD() {
        return iTEMPARTCOD;
    }

    public void setITEMPARTCOD(String iTEMPARTCOD) {
        this.iTEMPARTCOD = iTEMPARTCOD;
    }

    public String getPARTCODETEXT() {
        return pARTCODETEXT;
    }

    public void setPARTCODETEXT(String pARTCODETEXT) {
        this.pARTCODETEXT = pARTCODETEXT;
    }

    public String getITEMDEFECTGRP() {
        return iTEMDEFECTGRP;
    }

    public void setITEMDEFECTGRP(String iTEMDEFECTGRP) {
        this.iTEMDEFECTGRP = iTEMDEFECTGRP;
    }

    public String getDEFECTGRPTEXT() {
        return dEFECTGRPTEXT;
    }

    public void setDEFECTGRPTEXT(String dEFECTGRPTEXT) {
        this.dEFECTGRPTEXT = dEFECTGRPTEXT;
    }

    public String getITEMDEFECTCOD() {
        return iTEMDEFECTCOD;
    }

    public void setITEMDEFECTCOD(String iTEMDEFECTCOD) {
        this.iTEMDEFECTCOD = iTEMDEFECTCOD;
    }

    public String getDEFECTCODETEXT() {
        return dEFECTCODETEXT;
    }

    public void setDEFECTCODETEXT(String dEFECTCODETEXT) {
        this.dEFECTCODETEXT = dEFECTCODETEXT;
    }

    public String getITEMDEFECTSHTXT() {
        return iTEMDEFECTSHTXT;
    }

    public void setITEMDEFECTSHTXT(String iTEMDEFECTSHTXT) {
        this.iTEMDEFECTSHTXT = iTEMDEFECTSHTXT;
    }

    public String getCAUSEKEY() {
        return cAUSEKEY;
    }

    public void setCAUSEKEY(String cAUSEKEY) {
        this.cAUSEKEY = cAUSEKEY;
    }

    public String getACTKEY() {
        return String.valueOf(aCTKEY);
    }

    public void setACTKEY(String aCTKEY) {
        this.aCTKEY = aCTKEY;
    }

    public String getACTVGRP() {
        return aCTVGRP;
    }

    public void setACTVGRP(String aCTVGRP) {
        this.aCTVGRP = aCTVGRP;
    }

    public String getACTGRPTEXT() {
        return aCTGRPTEXT;
    }

    public void setACTGRPTEXT(String aCTGRPTEXT) {
        this.aCTGRPTEXT = aCTGRPTEXT;
    }

    public String getACTVCOD() {
        return aCTVCOD;
    }

    public void setACTVCOD(String aCTVCOD) {
        this.aCTVCOD = aCTVCOD;
    }

    public String getACTCODETEXT() {
        return aCTCODETEXT;
    }

    public void setACTCODETEXT(String aCTCODETEXT) {
        this.aCTCODETEXT = aCTCODETEXT;
    }

    public String getACTVSHTXT() {
        return aCTVSHTXT;
    }

    public void setACTVSHTXT(String aCTVSHTXT) {
        this.aCTVSHTXT = aCTVSHTXT;
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

    public String getFIELDS() {
        return fIELDS;
    }

    public void setFIELDS(String fIELDS) {
        this.fIELDS = fIELDS;
    }

    public String getACTION() {
        return aCTION;
    }

    public void setACTION(String aCTION) {
        this.aCTION = aCTION;
    }

}