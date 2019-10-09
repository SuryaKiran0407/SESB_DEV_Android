package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Task_REST
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
    @SerializedName("TASK_KEY")
    @Expose
    private String tASKKEY;
    @SerializedName("TASK_GRP")
    @Expose
    private String tASKGRP;
    @SerializedName("TASKGRPTEXT")
    @Expose
    private String tASKGRPTEXT;
    @SerializedName("TASK_COD")
    @Expose
    private String tASKCOD;
    @SerializedName("TASKCODETEXT")
    @Expose
    private String tASKCODETEXT;
    @SerializedName("TASK_SHTXT")
    @Expose
    private String tASKSHTXT;
    @SerializedName("PSTER")
    @Expose
    private String pSTER;
    @SerializedName("PETER")
    @Expose
    private String pETER;
    @SerializedName("PSTUR")
    @Expose
    private String pSTUR;
    @SerializedName("PETUR")
    @Expose
    private String pETUR;
    @SerializedName("PARVW")
    @Expose
    private String pARVW;
    @SerializedName("PARNR")
    @Expose
    private String pARNR;
    @SerializedName("ERLNAM")
    @Expose
    private String eRLNAM;
    @SerializedName("ERLDAT")
    @Expose
    private String eRLDAT;
    @SerializedName("ERLZEIT")
    @Expose
    private String eRLZEIT;
    @SerializedName("RELEASE")
    @Expose
    private String rELEASE;
    @SerializedName("COMPLETE")
    @Expose
    private String cOMPLETE;
    @SerializedName("SUCCESS")
    @Expose
    private String sUCCESS;
    @SerializedName("USER_STATUS")
    @Expose
    private String uSERSTATUS;
    @SerializedName("SYS_STATUS")
    @Expose
    private String sYSSTATUS;
    @SerializedName("SMSTTXT")
    @Expose
    private String sMSTTXT;
    @SerializedName("SMASTXT")
    @Expose
    private String sMASTXT;
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

    public String getTASKKEY() {
        return tASKKEY;
    }

    public void setTASKKEY(String tASKKEY) {
        this.tASKKEY = tASKKEY;
    }

    public String getTASKGRP() {
        return tASKGRP;
    }

    public void setTASKGRP(String tASKGRP) {
        this.tASKGRP = tASKGRP;
    }

    public String getTASKGRPTEXT() {
        return tASKGRPTEXT;
    }

    public void setTASKGRPTEXT(String tASKGRPTEXT) {
        this.tASKGRPTEXT = tASKGRPTEXT;
    }

    public String getTASKCOD() {
        return tASKCOD;
    }

    public void setTASKCOD(String tASKCOD) {
        this.tASKCOD = tASKCOD;
    }

    public String getTASKCODETEXT() {
        return tASKCODETEXT;
    }

    public void setTASKCODETEXT(String tASKCODETEXT) {
        this.tASKCODETEXT = tASKCODETEXT;
    }

    public String getTASKSHTXT() {
        return tASKSHTXT;
    }

    public void setTASKSHTXT(String tASKSHTXT) {
        this.tASKSHTXT = tASKSHTXT;
    }

    public String getPSTER() {
        return pSTER;
    }

    public void setPSTER(String pSTER) {
        this.pSTER = pSTER;
    }

    public String getPETER() {
        return pETER;
    }

    public void setPETER(String pETER) {
        this.pETER = pETER;
    }

    public String getPSTUR() {
        return pSTUR;
    }

    public void setPSTUR(String pSTUR) {
        this.pSTUR = pSTUR;
    }

    public String getPETUR() {
        return pETUR;
    }

    public void setPETUR(String pETUR) {
        this.pETUR = pETUR;
    }

    public String getPARVW() {
        return pARVW;
    }

    public void setPARVW(String pARVW) {
        this.pARVW = pARVW;
    }

    public String getPARNR() {
        return pARNR;
    }

    public void setPARNR(String pARNR) {
        this.pARNR = pARNR;
    }

    public String getERLNAM() {
        return eRLNAM;
    }

    public void setERLNAM(String eRLNAM) {
        this.eRLNAM = eRLNAM;
    }

    public String getERLDAT() {
        return eRLDAT;
    }

    public void setERLDAT(String eRLDAT) {
        this.eRLDAT = eRLDAT;
    }

    public String getERLZEIT() {
        return eRLZEIT;
    }

    public void setERLZEIT(String eRLZEIT) {
        this.eRLZEIT = eRLZEIT;
    }

    public String getRELEASE() {
        return rELEASE;
    }

    public void setRELEASE(String rELEASE) {
        this.rELEASE = rELEASE;
    }

    public String getCOMPLETE() {
        return cOMPLETE;
    }

    public void setCOMPLETE(String cOMPLETE) {
        this.cOMPLETE = cOMPLETE;
    }

    public String getSUCCESS() {
        return sUCCESS;
    }

    public void setSUCCESS(String sUCCESS) {
        this.sUCCESS = sUCCESS;
    }

    public String getUSERSTATUS() {
        return uSERSTATUS;
    }

    public void setUSERSTATUS(String uSERSTATUS) {
        this.uSERSTATUS = uSERSTATUS;
    }

    public String getSYSSTATUS() {
        return sYSSTATUS;
    }

    public void setSYSSTATUS(String sYSSTATUS) {
        this.sYSSTATUS = sYSSTATUS;
    }

    public String getSMSTTXT() {
        return sMSTTXT;
    }

    public void setSMSTTXT(String sMSTTXT) {
        this.sMSTTXT = sMSTTXT;
    }

    public String getSMASTXT() {
        return sMASTXT;
    }

    public void setSMASTXT(String sMASTXT) {
        this.sMASTXT = sMASTXT;
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