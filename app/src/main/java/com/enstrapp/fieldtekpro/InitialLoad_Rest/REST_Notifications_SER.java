package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_Notifications_SER
{

    @SerializedName("ET_MESSAGE")
    @Expose
    private List<ETMESSAGE> eTMESSAGE = null;

    @SerializedName("EV_MESSAGE")
    @Expose
    private  String eVMESSAGE;

    @SerializedName("EV_NOTIF_NUM")
    @Expose
    private String evNotifNum;

    @SerializedName("ET_NOTIF_DUP")
    @Expose
    private List<ETNOTIFDUP> eTNOTIFDUP = null;


    @SerializedName("ET_NOTIF_HEADER")
    @Expose
    private List<ETNOTIFHEADER> eTNOTIFHEADER = null;
    @SerializedName("ET_NOTIF_ITEMS")
    @Expose
    private List<ETNOTIFITEM> eTNOTIFITEMS = null;
    @SerializedName("ET_NOTIF_STATUS")
    @Expose
    private List<ETNOTIFStatus> eTNOTIFSTATUS = null;
    @SerializedName("ET_NOTIF_LONGTEXT")
    @Expose
    private List<ETNOTIFLONGTEXT> eTNOTIFLONGTEXT = null;
    @SerializedName("ET_DOCS")
    @Expose
    private List<ETDOC> eTDOCS = null;
    /*@SerializedName("ET_NOTIF_ACTVS")
    @Expose
    private List<ETNOTIFACTVS> etnotifactvs = null;
    @SerializedName("ET_NOTIF_TASKS")
    @Expose
    private List<ET_NOTIF_TASKS> et_notif_tasks = null;*/

    public List<ETMESSAGE> geteTMESSAGE() {
        return eTMESSAGE;
    }

    public void seteTMESSAGE(List<ETMESSAGE> eTMESSAGE) {
        this.eTMESSAGE = eTMESSAGE;
    }

    public String geteVMESSAGE() {
        return eVMESSAGE;
    }

    public void seteVMESSAGE(String eVMESSAGE) {
        this.eVMESSAGE = eVMESSAGE;
    }

    public String getEvNotifNum() {
        return evNotifNum;
    }

    public void setEvNotifNum(String evNotifNum) {
        this.evNotifNum = evNotifNum;
    }

    public List<ETNOTIFDUP> geteTNOTIFDUP() {
        return eTNOTIFDUP;
    }

    public void seteTNOTIFDUP(List<ETNOTIFDUP> eTNOTIFDUP) {
        this.eTNOTIFDUP = eTNOTIFDUP;
    }

    public List<ETNOTIFHEADER> geteTNOTIFHEADER() {
        return eTNOTIFHEADER;
    }

    public void seteTNOTIFHEADER(List<ETNOTIFHEADER> eTNOTIFHEADER) {
        this.eTNOTIFHEADER = eTNOTIFHEADER;
    }

    public List<ETNOTIFITEM> geteTNOTIFITEMS() {
        return eTNOTIFITEMS;
    }

    public void seteTNOTIFITEMS(List<ETNOTIFITEM> eTNOTIFITEMS) {
        this.eTNOTIFITEMS = eTNOTIFITEMS;
    }

    public List<ETNOTIFStatus> geteTNOTIFSTATUS() {
        return eTNOTIFSTATUS;
    }

    public void seteTNOTIFSTATUS(List<ETNOTIFStatus> eTNOTIFSTATUS) {
        this.eTNOTIFSTATUS = eTNOTIFSTATUS;
    }

    public List<ETNOTIFLONGTEXT> geteTNOTIFLONGTEXT() {
        return eTNOTIFLONGTEXT;
    }

    public void seteTNOTIFLONGTEXT(List<ETNOTIFLONGTEXT> eTNOTIFLONGTEXT) {
        this.eTNOTIFLONGTEXT = eTNOTIFLONGTEXT;
    }

    public List<ETDOC> geteTDOCS() {
        return eTDOCS;
    }

    public void seteTDOCS(List<ETDOC> eTDOCS) {
        this.eTDOCS = eTDOCS;
    }

    /*public List<ETNOTIFACTVS> getEtnotifactvs() {
        return etnotifactvs;
    }

    public void setEtnotifactvs(List<ETNOTIFACTVS> etnotifactvs) {
        this.etnotifactvs = etnotifactvs;
    }

    public List<ET_NOTIF_TASKS> getEt_notif_tasks() {
        return et_notif_tasks;
    }

    public void setEt_notif_tasks(List<ET_NOTIF_TASKS> et_notif_tasks) {
        this.et_notif_tasks = et_notif_tasks;
    }*/

    public class ET_NOTIF_TASKS {
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
        private Integer tASKKEY;
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

        public Integer getTASKKEY() {
            return tASKKEY;
        }

        public void setTASKKEY(Integer tASKKEY) {
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




    public class ETNOTIFACTVS {
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
        private Integer aCTKEY;
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

        public void setACTKEY(Integer aCTKEY) {
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





    public class ETNOTIFStatus {

        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("OBJNR")
        @Expose
        private String oBJNR;
        @SerializedName("STSMA")
        @Expose
        private String sTSMA;
        @SerializedName("STONR")
        @Expose
        private Integer sTONR;
        @SerializedName("HSONR")
        @Expose
        private Integer hSONR;
        @SerializedName("NSONR")
        @Expose
        private Integer nSONR;
        @SerializedName("STAT")
        @Expose
        private String sTAT;
        @SerializedName("TXT04")
        @Expose
        private String tXT04;
        @SerializedName("TXT30")
        @Expose
        private String tXT30;
        @SerializedName("ACT")
        @Expose
        private Boolean aCT;
        @SerializedName("INIST")
        @Expose
        private String iNIST;

        public String getQMNUM() {
            return qMNUM;
        }

        public void setQMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

        public String getOBJNR() {
            return oBJNR;
        }

        public void setOBJNR(String oBJNR) {
            this.oBJNR = oBJNR;
        }

        public String getSTSMA() {
            return sTSMA;
        }

        public void setSTSMA(String sTSMA) {
            this.sTSMA = sTSMA;
        }

        public Integer getSTONR() {
            return sTONR;
        }

        public void setSTONR(Integer sTONR) {
            this.sTONR = sTONR;
        }

        public Integer getHSONR() {
            return hSONR;
        }

        public void setHSONR(Integer hSONR) {
            this.hSONR = hSONR;
        }

        public Integer getNSONR() {
            return nSONR;
        }

        public void setNSONR(Integer nSONR) {
            this.nSONR = nSONR;
        }

        public String getSTAT() {
            return sTAT;
        }

        public void setSTAT(String sTAT) {
            this.sTAT = sTAT;
        }

        public String getTXT04() {
            return tXT04;
        }

        public void setTXT04(String tXT04) {
            this.tXT04 = tXT04;
        }

        public String getTXT30() {
            return tXT30;
        }

        public void setTXT30(String tXT30) {
            this.tXT30 = tXT30;
        }

        public Boolean getACT() {
            return aCT;
        }

        public void setACT(Boolean aCT) {
            this.aCT = aCT;
        }

        public String getINIST() {
            return iNIST;
        }

        public void setINIST(String iNIST) {
            this.iNIST = iNIST;
        }

    }





    public class ETNOTIFLONGTEXT {

        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("OBJTYPE")
        @Expose
        private String oBJTYPE;
        @SerializedName("TEXT_LINE")
        @Expose
        private String tEXTLINE;
        @SerializedName("OBJKEY")
        @Expose
        private String oBJKEY;

        public String getQMNUM() {
            return qMNUM;
        }

        public void setQMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

        public String getOBJTYPE() {
            return oBJTYPE;
        }

        public void setOBJTYPE(String oBJTYPE) {
            this.oBJTYPE = oBJTYPE;
        }

        public String getTEXTLINE() {
            return tEXTLINE;
        }

        public void setTEXTLINE(String tEXTLINE) {
            this.tEXTLINE = tEXTLINE;
        }

        public String getOBJKEY() {
            return oBJKEY;
        }

        public void setOBJKEY(String oBJKEY) {
            this.oBJKEY = oBJKEY;
        }

    }




    public class ETNOTIFITEM {

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
        @SerializedName("CAUSE_GRP")
        @Expose
        private String cAUSEGRP;
        @SerializedName("CAUSEGRPTEXT")
        @Expose
        private String cAUSEGRPTEXT;
        @SerializedName("CAUSE_COD")
        @Expose
        private String cAUSECOD;
        @SerializedName("CAUSECODETEXT")
        @Expose
        private String cAUSECODETEXT;
        @SerializedName("CAUSE_SHTXT")
        @Expose
        private String cAUSESHTXT;

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

        public String getCAUSEGRP() {
            return cAUSEGRP;
        }

        public void setCAUSEGRP(String cAUSEGRP) {
            this.cAUSEGRP = cAUSEGRP;
        }

        public String getCAUSEGRPTEXT() {
            return cAUSEGRPTEXT;
        }

        public void setCAUSEGRPTEXT(String cAUSEGRPTEXT) {
            this.cAUSEGRPTEXT = cAUSEGRPTEXT;
        }

        public String getCAUSECOD() {
            return cAUSECOD;
        }

        public void setCAUSECOD(String cAUSECOD) {
            this.cAUSECOD = cAUSECOD;
        }

        public String getCAUSECODETEXT() {
            return cAUSECODETEXT;
        }

        public void setCAUSECODETEXT(String cAUSECODETEXT) {
            this.cAUSECODETEXT = cAUSECODETEXT;
        }

        public String getCAUSESHTXT() {
            return cAUSESHTXT;
        }

        public void setCAUSESHTXT(String cAUSESHTXT) {
            this.cAUSESHTXT = cAUSESHTXT;
        }

    }



    public class ETNOTIFHEADER
    {
        @SerializedName("NOTIF_TYPE")
        @Expose
        private String nOTIFTYPE;
        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("NOTIF_SHORTTXT")
        @Expose
        private String nOTIFSHORTTXT;
        @SerializedName("FUNCTION_LOC")
        @Expose
        private String fUNCTIONLOC;
        @SerializedName("EQUIPMENT")
        @Expose
        private String eQUIPMENT;
        @SerializedName("BAUTL")
        @Expose
        private String bAUTL;
        @SerializedName("REPORTED_BY")
        @Expose
        private String rEPORTEDBY;
        @SerializedName("MALFUNC_STDATE")
        @Expose
        private String mALFUNCSTDATE;
        @SerializedName("MALFUNC_EDDATE")
        @Expose
        private String mALFUNCEDDATE;
        @SerializedName("MALFUNC_STTIME")
        @Expose
        private String mALFUNCSTTIME;
        @SerializedName("MALFUNC_EDTIME")
        @Expose
        private String mALFUNCEDTIME;
        @SerializedName("BREAKDOWN_IND")
        @Expose
        private String bREAKDOWNIND;
        @SerializedName("PRIORITY")
        @Expose
        private String pRIORITY;
        @SerializedName("INGRP")
        @Expose
        private String iNGRP;
        @SerializedName("ARBPL")
        @Expose
        private String aRBPL;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("QMDAT")
        @Expose
        private String qMDAT;
        @SerializedName("STRMN")
        @Expose
        private String sTRMN;
        @SerializedName("LTRMN")
        @Expose
        private String lTRMN;
        @SerializedName("STRUR")
        @Expose
        private String sTRUR;
        @SerializedName("LTRUR")
        @Expose
        private String lTRUR;
        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("PARNR_VW")
        @Expose
        private String pARNRVW;
        @SerializedName("NAME_VW")
        @Expose
        private String nAMEVW;
        @SerializedName("AUSWK")
        @Expose
        private String aUSWK;
        @SerializedName("SHIFT")
        @Expose
        private String sHIFT;
        @SerializedName("NOOFPERSON")
        @Expose
        private String nOOFPERSON;
        @SerializedName("DOCS")
        @Expose
        private Boolean dOCS;
        @SerializedName("ALTITUDE")
        @Expose
        private String aLTITUDE;
        @SerializedName("LATITUDE")
        @Expose
        private String lATITUDE;
        @SerializedName("LONGITUDE")
        @Expose
        private String lONGITUDE;
        @SerializedName("CLOSED")
        @Expose
        private String cLOSED;
        @SerializedName("COMPLETED")
        @Expose
        private String cOMPLETED;
        @SerializedName("CREATEDON")
        @Expose
        private String cREATEDON;
        @SerializedName("QMARTX")
        @Expose
        private String qMARTX;
        @SerializedName("PLTXT")
        @Expose
        private String pLTXT;
        @SerializedName("EQKTX")
        @Expose
        private String eQKTX;
        @SerializedName("PRIOKX")
        @Expose
        private String pRIOKX;
        @SerializedName("AUFTEXT")
        @Expose
        private String aUFTEXT;
        @SerializedName("AUARTTEXT")
        @Expose
        private String aUARTTEXT;
        @SerializedName("PLANTNAME")
        @Expose
        private String pLANTNAME;
        @SerializedName("WKCTRNAME")
        @Expose
        private String wKCTRNAME;
        @SerializedName("INGRPNAME")
        @Expose
        private String iNGRPNAME;
        @SerializedName("MAKTX")
        @Expose
        private String mAKTX;
        @SerializedName("AUSWKT")
        @Expose
        private String aUSWKT;
        @SerializedName("XSTATUS")
        @Expose
        private String xSTATUS;
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
        @SerializedName("VKORG")
        @Expose
        private String vKORG;
        @SerializedName("VTWEG")
        @Expose
        private String vTWEG;
        @SerializedName("SPART")
        @Expose
        private String sPART;
        @SerializedName("VKGRP")
        @Expose
        private String vKGRP;
        @SerializedName("VKBUR")
        @Expose
        private String vKBUR;
        @SerializedName("BSTNK")
        @Expose
        private String bSTNK;
        @SerializedName("VBELN")
        @Expose
        private String vBELN;
        @SerializedName("VKORGTEXT")
        @Expose
        private String vKORGTEXT;
        @SerializedName("VTWEGTEXT")
        @Expose
        private String vTWEGTEXT;
        @SerializedName("SPARTTEXT")
        @Expose
        private String sPARTTEXT;
        @SerializedName("VKGRPTEXT")
        @Expose
        private String vKGRPTEXT;
        @SerializedName("VKBURTEXT")
        @Expose
        private String vKBURTEXT;

        public String getVKORG() {
            return vKORG;
        }

        public void setVKORG(String vKORG) {
            this.vKORG = vKORG;
        }

        public String getVTWEG() {
            return vTWEG;
        }

        public void setVTWEG(String vTWEG) {
            this.vTWEG = vTWEG;
        }

        public String getSPART() {
            return sPART;
        }

        public void setSPART(String sPART) {
            this.sPART = sPART;
        }

        public String getVKGRP() {
            return vKGRP;
        }

        public void setVKGRP(String vKGRP) {
            this.vKGRP = vKGRP;
        }

        public String getVKBUR() {
            return vKBUR;
        }

        public void setVKBUR(String vKBUR) {
            this.vKBUR = vKBUR;
        }

        public String getBSTNK() {
            return bSTNK;
        }

        public void setBSTNK(String bSTNK) {
            this.bSTNK = bSTNK;
        }

        public String getVBELN() {
            return vBELN;
        }

        public void setVBELN(String vBELN) {
            this.vBELN = vBELN;
        }

        public String getVKORGTEXT() {
            return vKORGTEXT;
        }

        public void setVKORGTEXT(String vKORGTEXT) {
            this.vKORGTEXT = vKORGTEXT;
        }

        public String getVTWEGTEXT() {
            return vTWEGTEXT;
        }

        public void setVTWEGTEXT(String vTWEGTEXT) {
            this.vTWEGTEXT = vTWEGTEXT;
        }

        public String getSPARTTEXT() {
            return sPARTTEXT;
        }

        public void setSPARTTEXT(String sPARTTEXT) {
            this.sPARTTEXT = sPARTTEXT;
        }

        public String getVKGRPTEXT() {
            return vKGRPTEXT;
        }

        public void setVKGRPTEXT(String vKGRPTEXT) {
            this.vKGRPTEXT = vKGRPTEXT;
        }

        public String getVKBURTEXT() {
            return vKBURTEXT;
        }

        public void setVKBURTEXT(String vKBURTEXT) {
            this.vKBURTEXT = vKBURTEXT;
        }


        public String getNOTIFTYPE() {
            return nOTIFTYPE;
        }

        public void setNOTIFTYPE(String nOTIFTYPE) {
            this.nOTIFTYPE = nOTIFTYPE;
        }

        public String getQMNUM() {
            return qMNUM;
        }

        public void setQMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

        public String getNOTIFSHORTTXT() {
            return nOTIFSHORTTXT;
        }

        public void setNOTIFSHORTTXT(String nOTIFSHORTTXT) {
            this.nOTIFSHORTTXT = nOTIFSHORTTXT;
        }

        public String getFUNCTIONLOC() {
            return fUNCTIONLOC;
        }

        public void setFUNCTIONLOC(String fUNCTIONLOC) {
            this.fUNCTIONLOC = fUNCTIONLOC;
        }

        public String getEQUIPMENT() {
            return eQUIPMENT;
        }

        public void setEQUIPMENT(String eQUIPMENT) {
            this.eQUIPMENT = eQUIPMENT;
        }

        public String getBAUTL() {
            return bAUTL;
        }

        public void setBAUTL(String bAUTL) {
            this.bAUTL = bAUTL;
        }

        public String getREPORTEDBY() {
            return rEPORTEDBY;
        }

        public void setREPORTEDBY(String rEPORTEDBY) {
            this.rEPORTEDBY = rEPORTEDBY;
        }

        public String getMALFUNCSTDATE() {
            return mALFUNCSTDATE;
        }

        public void setMALFUNCSTDATE(String mALFUNCSTDATE) {
            this.mALFUNCSTDATE = mALFUNCSTDATE;
        }

        public String getMALFUNCEDDATE() {
            return mALFUNCEDDATE;
        }

        public void setMALFUNCEDDATE(String mALFUNCEDDATE) {
            this.mALFUNCEDDATE = mALFUNCEDDATE;
        }

        public String getMALFUNCSTTIME() {
            return mALFUNCSTTIME;
        }

        public void setMALFUNCSTTIME(String mALFUNCSTTIME) {
            this.mALFUNCSTTIME = mALFUNCSTTIME;
        }

        public String getMALFUNCEDTIME() {
            return mALFUNCEDTIME;
        }

        public void setMALFUNCEDTIME(String mALFUNCEDTIME) {
            this.mALFUNCEDTIME = mALFUNCEDTIME;
        }

        public String getBREAKDOWNIND() {
            return bREAKDOWNIND;
        }

        public void setBREAKDOWNIND(String bREAKDOWNIND) {
            this.bREAKDOWNIND = bREAKDOWNIND;
        }

        public String getPRIORITY() {
            return pRIORITY;
        }

        public void setPRIORITY(String pRIORITY) {
            this.pRIORITY = pRIORITY;
        }

        public String getINGRP() {
            return iNGRP;
        }

        public void setINGRP(String iNGRP) {
            this.iNGRP = iNGRP;
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

        public String getQMDAT() {
            return qMDAT;
        }

        public void setQMDAT(String qMDAT) {
            this.qMDAT = qMDAT;
        }

        public String getSTRMN() {
            return sTRMN;
        }

        public void setSTRMN(String sTRMN) {
            this.sTRMN = sTRMN;
        }

        public String getLTRMN() {
            return lTRMN;
        }

        public void setLTRMN(String lTRMN) {
            this.lTRMN = lTRMN;
        }

        public String getSTRUR() {
            return sTRUR;
        }

        public void setSTRUR(String sTRUR) {
            this.sTRUR = sTRUR;
        }

        public String getLTRUR() {
            return lTRUR;
        }

        public void setLTRUR(String lTRUR) {
            this.lTRUR = lTRUR;
        }

        public String getAUFNR() {
            return aUFNR;
        }

        public void setAUFNR(String aUFNR) {
            this.aUFNR = aUFNR;
        }

        public String getPARNRVW() {
            return pARNRVW;
        }

        public void setPARNRVW(String pARNRVW) {
            this.pARNRVW = pARNRVW;
        }

        public String getNAMEVW() {
            return nAMEVW;
        }

        public void setNAMEVW(String nAMEVW) {
            this.nAMEVW = nAMEVW;
        }

        public String getAUSWK() {
            return aUSWK;
        }

        public void setAUSWK(String aUSWK) {
            this.aUSWK = aUSWK;
        }

        public String getSHIFT() {
            return sHIFT;
        }

        public void setSHIFT(String sHIFT) {
            this.sHIFT = sHIFT;
        }

        public String getNOOFPERSON() {
            return nOOFPERSON;
        }

        public void setNOOFPERSON(String nOOFPERSON) {
            this.nOOFPERSON = nOOFPERSON;
        }

        public String getDOCS() {
            return String.valueOf(dOCS);
        }

        public void setDOCS(Boolean dOCS) {
            this.dOCS = dOCS;
        }

        public String getALTITUDE() {
            return aLTITUDE;
        }

        public void setALTITUDE(String aLTITUDE) {
            this.aLTITUDE = aLTITUDE;
        }

        public String getLATITUDE() {
            return lATITUDE;
        }

        public void setLATITUDE(String lATITUDE) {
            this.lATITUDE = lATITUDE;
        }

        public String getLONGITUDE() {
            return lONGITUDE;
        }

        public void setLONGITUDE(String lONGITUDE) {
            this.lONGITUDE = lONGITUDE;
        }

        public String getCLOSED() {
            return cLOSED;
        }

        public void setCLOSED(String cLOSED) {
            this.cLOSED = cLOSED;
        }

        public String getCOMPLETED() {
            return cOMPLETED;
        }

        public void setCOMPLETED(String cOMPLETED) {
            this.cOMPLETED = cOMPLETED;
        }

        public String getCREATEDON() {
            return cREATEDON;
        }

        public void setCREATEDON(String cREATEDON) {
            this.cREATEDON = cREATEDON;
        }

        public String getQMARTX() {
            return qMARTX;
        }

        public void setQMARTX(String qMARTX) {
            this.qMARTX = qMARTX;
        }

        public String getPLTXT() {
            return pLTXT;
        }

        public void setPLTXT(String pLTXT) {
            this.pLTXT = pLTXT;
        }

        public String getEQKTX() {
            return eQKTX;
        }

        public void setEQKTX(String eQKTX) {
            this.eQKTX = eQKTX;
        }

        public String getPRIOKX() {
            return pRIOKX;
        }

        public void setPRIOKX(String pRIOKX) {
            this.pRIOKX = pRIOKX;
        }

        public String getAUFTEXT() {
            return aUFTEXT;
        }

        public void setAUFTEXT(String aUFTEXT) {
            this.aUFTEXT = aUFTEXT;
        }

        public String getAUARTTEXT() {
            return aUARTTEXT;
        }

        public void setAUARTTEXT(String aUARTTEXT) {
            this.aUARTTEXT = aUARTTEXT;
        }

        public String getPLANTNAME() {
            return pLANTNAME;
        }

        public void setPLANTNAME(String pLANTNAME) {
            this.pLANTNAME = pLANTNAME;
        }

        public String getWKCTRNAME() {
            return wKCTRNAME;
        }

        public void setWKCTRNAME(String wKCTRNAME) {
            this.wKCTRNAME = wKCTRNAME;
        }

        public String getINGRPNAME() {
            return iNGRPNAME;
        }

        public void setINGRPNAME(String iNGRPNAME) {
            this.iNGRPNAME = iNGRPNAME;
        }

        public String getMAKTX() {
            return mAKTX;
        }

        public void setMAKTX(String mAKTX) {
            this.mAKTX = mAKTX;
        }

        public String getAUSWKT() {
            return aUSWKT;
        }

        public void setAUSWKT(String aUSWKT) {
            this.aUSWKT = aUSWKT;
        }

        public String getXSTATUS() {
            return xSTATUS;
        }

        public void setXSTATUS(String xSTATUS) {
            this.xSTATUS = xSTATUS;
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



    public class ETDOC {

        @SerializedName("OBJTYPE")
        @Expose
        private String oBJTYPE;
        @SerializedName("ZOBJID")
        @Expose
        private String zOBJID;
        @SerializedName("ZDOCTYPE")
        @Expose
        private String zDOCTYPE;
        @SerializedName("FILENAME")
        @Expose
        private String fILENAME;
        @SerializedName("FILETYPE")
        @Expose
        private String fILETYPE;
        @SerializedName("FSIZE")
        @Expose
        private String fSIZE;
        @SerializedName("CONTENT")
        @Expose
        private String cONTENT;
        @SerializedName("DOC_ID")
        @Expose
        private String dOCID;
        @SerializedName("DOC_TYPE")
        @Expose
        private String dOCTYPE;
        @SerializedName("CONTENTX")
        @Expose
        private String cONTENTX;

        public String getOBJTYPE() {
            return oBJTYPE;
        }

        public void setOBJTYPE(String oBJTYPE) {
            this.oBJTYPE = oBJTYPE;
        }

        public String getZOBJID() {
            return zOBJID;
        }

        public void setZOBJID(String zOBJID) {
            this.zOBJID = zOBJID;
        }

        public String getZDOCTYPE() {
            return zDOCTYPE;
        }

        public void setZDOCTYPE(String zDOCTYPE) {
            this.zDOCTYPE = zDOCTYPE;
        }

        public String getFILENAME() {
            return fILENAME;
        }

        public void setFILENAME(String fILENAME) {
            this.fILENAME = fILENAME;
        }

        public String getFILETYPE() {
            return fILETYPE;
        }

        public void setFILETYPE(String fILETYPE) {
            this.fILETYPE = fILETYPE;
        }

        public String getFSIZE() {
            return fSIZE;
        }

        public void setFSIZE(String fSIZE) {
            this.fSIZE = fSIZE;
        }

        public String getCONTENT() {
            return cONTENT;
        }

        public void setCONTENT(String cONTENT) {
            this.cONTENT = cONTENT;
        }

        public String getDOCID() {
            return dOCID;
        }

        public void setDOCID(String dOCID) {
            this.dOCID = dOCID;
        }

        public String getDOCTYPE() {
            return dOCTYPE;
        }

        public void setDOCTYPE(String dOCTYPE) {
            this.dOCTYPE = dOCTYPE;
        }

        public String getCONTENTX() {
            return cONTENTX;
        }

        public void setCONTENTX(String cONTENTX) {
            this.cONTENTX = cONTENTX;
        }

    }




    public class ETMESSAGE {
        @SerializedName("MESSAGE")
        @Expose
        private String mESSAGE;

        public String getMESSAGE() {
            return mESSAGE;
        }

        public void setMESSAGE(String mESSAGE) {
            this.mESSAGE = mESSAGE;
        }
    }




    public class ETNOTIFDUP
    {
        @SerializedName("QMART")
        @Expose
        private String qMART;
        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("QMTXT")
        @Expose
        private String qMTXT;
        @SerializedName("EQUNR")
        @Expose
        private String eQUNR;
        @SerializedName("PRIOK")
        @Expose
        private String pRIOK;
        @SerializedName("OBJNR")
        @Expose
        private String oBJNR;

        public String getqMNUM() {
            return qMNUM;
        }

        public void setqMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

        public String getQMART() {
            return qMART;
        }

        public void setQMART(String qMART) {
            this.qMART = qMART;
        }

        public String getQMTXT() {
            return qMTXT;
        }

        public void setQMTXT(String qMTXT) {
            this.qMTXT = qMTXT;
        }

        public String getEQUNR() {
            return eQUNR;
        }

        public void setEQUNR(String eQUNR) {
            this.eQUNR = eQUNR;
        }

        public String getPRIOK() {
            return pRIOK;
        }

        public void setPRIOK(String pRIOK) {
            this.pRIOK = pRIOK;
        }

        public String getOBJNR() {
            return oBJNR;
        }

        public void setOBJNR(String oBJNR) {
            this.oBJNR = oBJNR;
        }
    }


}