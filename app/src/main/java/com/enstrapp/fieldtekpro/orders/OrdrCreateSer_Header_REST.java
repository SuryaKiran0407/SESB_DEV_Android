package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrdrCreateSer_Header_REST
{
    @SerializedName("AUFNR")
    @Expose
    private String aUFNR;
    @SerializedName("AUART")
    @Expose
    private String aUART;
    @SerializedName("KTEXT")
    @Expose
    private String kTEXT;
    @SerializedName("ILART")
    @Expose
    private String iLART;
    @SerializedName("ERNAM")
    @Expose
    private String eRNAM;
    @SerializedName("ERDAT")
    @Expose
    private String eRDAT;
    @SerializedName("PRIOK")
    @Expose
    private String pRIOK;
    @SerializedName("EQUNR")
    @Expose
    private String eQUNR;
    @SerializedName("STRNO")
    @Expose
    private String sTRNO;
    @SerializedName("TPLNR_INT")
    @Expose
    private String tPLNRINT;
    @SerializedName("BAUTL")
    @Expose
    private String bAUTL;
    @SerializedName("GLTRP")
    @Expose
    private String gLTRP;
    @SerializedName("GSTRP")
    @Expose
    private String gSTRP;
    @SerializedName("MSAUS")
    @Expose
    private String mSAUS;
    @SerializedName("ANLZU")
    @Expose
    private String aNLZU;
    @SerializedName("AUSVN")
    @Expose
    private String aUSVN;
    @SerializedName("AUSBS")
    @Expose
    private String aUSBS;
    @SerializedName("QMNAM")
    @Expose
    private String qMNAM;
    @SerializedName("AUSWK")
    @Expose
    private String aUSWK;
    @SerializedName("PARNR_VW")
    @Expose
    private String pARNRVW;
    @SerializedName("NAME_VW")
    @Expose
    private String nAMEVW;
    @SerializedName("DOCS")
    @Expose
    private String dOCS;
    @SerializedName("PERMITS")
    @Expose
    private String pERMITS;
    @SerializedName("ALTITUDE")
    @Expose
    private String aLTITUDE;
    @SerializedName("LATITUDE")
    @Expose
    private String lATITUDE;
    @SerializedName("LONGITUDE")
    @Expose
    private String lONGITUDE;
    @SerializedName("QMNUM")
    @Expose
    private String qMNUM;
    @SerializedName("QCREATE")
    @Expose
    private String qCREATE;
    @SerializedName("CLOSED")
    @Expose
    private String cLOSED;
    @SerializedName("COMPLETED")
    @Expose
    private String cOMPLETED;
    @SerializedName("WCM")
    @Expose
    private String wCM;
    @SerializedName("WSM")
    @Expose
    private String wSM;
    @SerializedName("INGRP")
    @Expose
    private String iNGRP;
    @SerializedName("ARBPL")
    @Expose
    private String aRBPL;
    @SerializedName("WERKS")
    @Expose
    private String wERKS;
    @SerializedName("BEMOT")
    @Expose
    private String bEMOT;
    @SerializedName("AUERU")
    @Expose
    private String aUERU;
    @SerializedName("AUARTTEXT")
    @Expose
    private String aUARTTEXT;
    @SerializedName("QMARTX")
    @Expose
    private String qMARTX;
    @SerializedName("QMTXT")
    @Expose
    private String qMTXT;
    @SerializedName("PLTXT")
    @Expose
    private String pLTXT;
    @SerializedName("EQKTX")
    @Expose
    private String eQKTX;
    @SerializedName("PRIOKX")
    @Expose
    private String pRIOKX;
    @SerializedName("ILATX")
    @Expose
    private String iLATX;
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
    @SerializedName("ANLZUX")
    @Expose
    private String aNLZUX;
    @SerializedName("XSTATUS")
    @Expose
    private String xSTATUS;
    @SerializedName("KOKRS")
    @Expose
    private String kOKRS;
    @SerializedName("KOSTL")
    @Expose
    private String kOSTL;
    @SerializedName("GSBER")
    @Expose
    private String gSBER;
    @SerializedName("POSID")
    @Expose
    private String pOSID;
    @SerializedName("REVNR")
    @Expose
    private String rEVNR;
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

    public String getAUFNR() {
        return aUFNR;
    }

    public void setAUFNR(String aUFNR) {
        this.aUFNR = aUFNR;
    }

    public String getAUART() {
        return aUART;
    }

    public void setAUART(String aUART) {
        this.aUART = aUART;
    }

    public String getKTEXT() {
        return kTEXT;
    }

    public void setKTEXT(String kTEXT) {
        this.kTEXT = kTEXT;
    }

    public String getILART() {
        return iLART;
    }

    public void setILART(String iLART) {
        this.iLART = iLART;
    }

    public String getERNAM() {
        return eRNAM;
    }

    public void setERNAM(String eRNAM) {
        this.eRNAM = eRNAM;
    }

    public String getERDAT() {
        return eRDAT;
    }

    public void setERDAT(String eRDAT) {
        this.eRDAT = eRDAT;
    }

    public String getPRIOK() {
        return pRIOK;
    }

    public void setPRIOK(String pRIOK) {
        this.pRIOK = pRIOK;
    }

    public String getEQUNR() {
        return eQUNR;
    }

    public void setEQUNR(String eQUNR) {
        this.eQUNR = eQUNR;
    }

    public String getSTRNO() {
        return sTRNO;
    }

    public void setSTRNO(String sTRNO) {
        this.sTRNO = sTRNO;
    }

    public String getTPLNRINT() {
        return tPLNRINT;
    }

    public void setTPLNRINT(String tPLNRINT) {
        this.tPLNRINT = tPLNRINT;
    }

    public String getBAUTL() {
        return bAUTL;
    }

    public void setBAUTL(String bAUTL) {
        this.bAUTL = bAUTL;
    }

    public String getGLTRP() {
        return gLTRP;
    }

    public void setGLTRP(String gLTRP) {
        this.gLTRP = gLTRP;
    }

    public String getGSTRP() {
        return gSTRP;
    }

    public void setGSTRP(String gSTRP) {
        this.gSTRP = gSTRP;
    }

    public String getMSAUS() {
        return mSAUS;
    }

    public void setMSAUS(String mSAUS) {
        this.mSAUS = mSAUS;
    }

    public String getANLZU() {
        return aNLZU;
    }

    public void setANLZU(String aNLZU) {
        this.aNLZU = aNLZU;
    }

    public String getAUSVN() {
        return aUSVN;
    }

    public void setAUSVN(String aUSVN) {
        this.aUSVN = aUSVN;
    }

    public String getAUSBS() {
        return aUSBS;
    }

    public void setAUSBS(String aUSBS) {
        this.aUSBS = aUSBS;
    }

    public String getQMNAM() {
        return qMNAM;
    }

    public void setQMNAM(String qMNAM) {
        this.qMNAM = qMNAM;
    }

    public String getAUSWK() {
        return aUSWK;
    }

    public void setAUSWK(String aUSWK) {
        this.aUSWK = aUSWK;
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

    public String getDOCS() {
        return dOCS;
    }

    public void setDOCS(String dOCS) {
        this.dOCS = dOCS;
    }

    public String getPERMITS() {
        return pERMITS;
    }

    public void setPERMITS(String pERMITS) {
        this.pERMITS = pERMITS;
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

    public String getQMNUM() {
        return qMNUM;
    }

    public void setQMNUM(String qMNUM) {
        this.qMNUM = qMNUM;
    }

    public String getQCREATE() {
        return qCREATE;
    }

    public void setQCREATE(String qCREATE) {
        this.qCREATE = qCREATE;
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

    public String getWCM() {
        return wCM;
    }

    public void setWCM(String wCM) {
        this.wCM = wCM;
    }

    public String getWSM() {
        return wSM;
    }

    public void setWSM(String wSM) {
        this.wSM = wSM;
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

    public String getBEMOT() {
        return bEMOT;
    }

    public void setBEMOT(String bEMOT) {
        this.bEMOT = bEMOT;
    }

    public String getAUERU() {
        return aUERU;
    }

    public void setAUERU(String aUERU) {
        this.aUERU = aUERU;
    }

    public String getAUARTTEXT() {
        return aUARTTEXT;
    }

    public void setAUARTTEXT(String aUARTTEXT) {
        this.aUARTTEXT = aUARTTEXT;
    }

    public String getQMARTX() {
        return qMARTX;
    }

    public void setQMARTX(String qMARTX) {
        this.qMARTX = qMARTX;
    }

    public String getQMTXT() {
        return qMTXT;
    }

    public void setQMTXT(String qMTXT) {
        this.qMTXT = qMTXT;
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

    public String getILATX() {
        return iLATX;
    }

    public void setILATX(String iLATX) {
        this.iLATX = iLATX;
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

    public String getANLZUX() {
        return aNLZUX;
    }

    public void setANLZUX(String aNLZUX) {
        this.aNLZUX = aNLZUX;
    }

    public String getXSTATUS() {
        return xSTATUS;
    }

    public void setXSTATUS(String xSTATUS) {
        this.xSTATUS = xSTATUS;
    }

    public String getKOKRS() {
        return kOKRS;
    }

    public void setKOKRS(String kOKRS) {
        this.kOKRS = kOKRS;
    }

    public String getKOSTL() {
        return kOSTL;
    }

    public void setKOSTL(String kOSTL) {
        this.kOSTL = kOSTL;
    }

    public String getGSBER() {
        return gSBER;
    }

    public void setGSBER(String gSBER) {
        this.gSBER = gSBER;
    }

    public String getPOSID() {
        return pOSID;
    }

    public void setPOSID(String pOSID) {
        this.pOSID = pOSID;
    }

    public String getREVNR() {
        return rEVNR;
    }

    public void setREVNR(String rEVNR) {
        this.rEVNR = rEVNR;
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