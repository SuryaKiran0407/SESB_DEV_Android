package com.enstrapp.fieldtekpro.InitialLoad_Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SER_DueNotifications
{
    @SerializedName("ET_NOTIF_HEADER")
    @Expose
    private List<ETNOTIFHEADER> eTNOTIFHEADER = null;


    public List<ETNOTIFHEADER> geteTNOTIFHEADER() {
        return eTNOTIFHEADER;
    }

    public void seteTNOTIFHEADER(List<ETNOTIFHEADER> eTNOTIFHEADER) {
        this.eTNOTIFHEADER = eTNOTIFHEADER;
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
}




