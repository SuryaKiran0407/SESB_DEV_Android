package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_Orders_SER
{

    @SerializedName("EV_MESSAGE")
    @Expose
    private  String eVMESSAGE;
    @SerializedName("ET_MESSAGE")
    @Expose
    private List<EtMessages> EtMessages;
    @SerializedName("EV_AUFNR")
    @Expose
    private  String eVAUFNR;
    @SerializedName("EV_ORDER_NO")
    @Expose
    private  String evorderno;
    @SerializedName("ET_ORDER_HEADER")
    @Expose
    private List<ETORDERHEADER> eTORDERHEADER = null;
    @SerializedName("ET_ORDER_OPERATIONS")
    @Expose
    private List<ETORDEROPERATION> eTORDEROPERATIONS = null;
    @SerializedName("ET_ORDER_COMPONENTS")
    @Expose
    private List<ETORDERCOMPONENTS> eTORDERCOMPONENTS = null;
    @SerializedName("ET_ORDER_LONGTEXT")
    @Expose
    private List<ETORDERLONGTEXT> eTORDERLONGTEXT = null;
    @SerializedName("ET_ORDER_OLIST")
    @Expose
    private List<ETORDEROLIST> eTORDEROLIST = null;
    @SerializedName("ET_ORDER_STATUS")
    @Expose
    private List<ETORDERStatus> eTORDERSTATUS = null;
    @SerializedName("ET_NOTIF_HEADER")
    @Expose
    private List<ETNOTIFHEADER> eTNOTIFHEADER = null;
    @SerializedName("ET_DOCS")
    @Expose
    private List<ETDOC> eTDOCS = null;

    public List<ETDOC> geteTDOCS() {
        return eTDOCS;
    }

    public void seteTDOCS(List<ETDOC> eTDOCS) {
        this.eTDOCS = eTDOCS;
    }

    public String getEvorderno() {
        return evorderno;
    }

    public void setEvorderno(String evorderno) {
        this.evorderno = evorderno;
    }

    public List<REST_Orders_SER.EtMessages> getEtMessages() {
        return EtMessages;
    }

    public void setEtMessages(List<REST_Orders_SER.EtMessages> etMessages) {
        EtMessages = etMessages;
    }

    public String geteVAUFNR() {
        return eVAUFNR;
    }

    public void seteVAUFNR(String eVAUFNR) {
        this.eVAUFNR = eVAUFNR;
    }

    public List<ETORDERCOMPONENTS> geteTORDERCOMPONENTS() {
        return eTORDERCOMPONENTS;
    }

    public void seteTORDERCOMPONENTS(List<ETORDERCOMPONENTS> eTORDERCOMPONENTS) {
        this.eTORDERCOMPONENTS = eTORDERCOMPONENTS;
    }

    public String geteVMESSAGE() {
        return eVMESSAGE;
    }

    public void seteVMESSAGE(String eVMESSAGE) {
        this.eVMESSAGE = eVMESSAGE;
    }

    public List<ETORDERHEADER> getETORDERHEADER() {
        return eTORDERHEADER;
    }

    public void setETORDERHEADER(List<ETORDERHEADER> eTORDERHEADER) {
        this.eTORDERHEADER = eTORDERHEADER;
    }

    public List<ETORDEROPERATION> getETORDEROPERATIONS() {
        return eTORDEROPERATIONS;
    }

    public void setETORDEROPERATIONS(List<ETORDEROPERATION> eTORDEROPERATIONS) {
        this.eTORDEROPERATIONS = eTORDEROPERATIONS;
    }

    public List<ETORDERLONGTEXT> getETORDERLONGTEXT() {
        return eTORDERLONGTEXT;
    }

    public void setETORDERLONGTEXT(List<ETORDERLONGTEXT> eTORDERLONGTEXT) {
        this.eTORDERLONGTEXT = eTORDERLONGTEXT;
    }

    public List<ETORDEROLIST> getETORDEROLIST() {
        return eTORDEROLIST;
    }

    public void setETORDEROLIST(List<ETORDEROLIST> eTORDEROLIST) {
        this.eTORDEROLIST = eTORDEROLIST;
    }

    public List<ETORDERStatus> getETORDERSTATUS() {
        return eTORDERSTATUS;
    }

    public void setETORDERSTATUS(List<ETORDERStatus> eTORDERSTATUS) {
        this.eTORDERSTATUS = eTORDERSTATUS;
    }

    public List<ETNOTIFHEADER> getETNOTIFHEADER() {
        return eTNOTIFHEADER;
    }

    public void setETNOTIFHEADER(List<ETNOTIFHEADER> eTNOTIFHEADER) {
        this.eTNOTIFHEADER = eTNOTIFHEADER;
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
        @SerializedName("ZDOCTYPEITEM")
        @Expose
        private String ZDOCTYPEITEM;

        public String getZDOCTYPEITEM() {
            return ZDOCTYPEITEM;
        }

        public void setZDOCTYPEITEM(String ZDOCTYPEITEM) {
            this.ZDOCTYPEITEM = ZDOCTYPEITEM;
        }

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




    public class ETNOTIFHEADER {

        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;

        public String getQMNUM() {
            return qMNUM;
        }

        public void setQMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

    }




    public class EtMessages
    {
        @SerializedName("MESSAGE")
        @Expose
        private String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }





    public class ETORDERHEADER {

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
        @SerializedName("GLTRP")
        @Expose
        private String gLTRP;
        @SerializedName("GSTRP")
        @Expose
        private String gSTRP;
        @SerializedName("AUSVN")
        @Expose
        private String aUSVN;
        @SerializedName("AUSBS")
        @Expose
        private String aUSBS;
        @SerializedName("QMNAM")
        @Expose
        private String qMNAM;
        @SerializedName("PARNR_VW")
        @Expose
        private String pARNRVW;
        @SerializedName("NAME_VW")
        @Expose
        private String nAMEVW;
        @SerializedName("LATITUDE")
        @Expose
        private String lATITUDE;
        @SerializedName("LONGITUDE")
        @Expose
        private String lONGITUDE;
        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("INGRP")
        @Expose
        private String iNGRP;
        @SerializedName("ARBPL")
        @Expose
        private String aRBPL;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
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
        @SerializedName("POSID")
        @Expose
        private String POSID;
        @SerializedName("BEMOT")
        @Expose
        private String BEMOT;
        @SerializedName("AUERU")
        @Expose
        private String AUERU;
        @SerializedName("MAKTX")
        @Expose
        private String MAKTX;
        @SerializedName("ANLZU")
        @Expose
        private String ANLZU;
        @SerializedName("ANLZUX")
        @Expose
        private String ANLZUX;
        @SerializedName("RSNUM")
        @Expose
        private String RSNUM;
        @SerializedName("AUSWK")
        @Expose
        private String AUSWK;
        @SerializedName("REVNR")
        @Expose
        private String REVNR;
        @SerializedName("FISTL")
        @Expose
        private String FISTL;
        @SerializedName("ZZPERMIT")
        @Expose
        private String ZZPERMIT;
        @SerializedName("ZZPMISTDT")
        @Expose
        private String ZZPMISTDT;
        @SerializedName("ZZPMISTTM")
        @Expose
        private String ZZPMISTTM;
        @SerializedName("ZZPMIENDT")
        @Expose
        private String ZZPMIENDT;
        @SerializedName("ZZPMIENTM")
        @Expose
        private String ZZPMIENTM;
        @SerializedName("ZZPMIHR1")
        @Expose
        private String ZZPMIHR1;
        @SerializedName("IDEALHOURS")
        @Expose
        private String IDEALHOURS;
        @SerializedName("BAUTL")
        @Expose
        private String BAUTL;
        @SerializedName("DOCS")
        @Expose
        private String DOCS;
        @SerializedName("PERMITS")
        @Expose
        private String PERMITS;
        @SerializedName("FIELDS")
        @Expose
        private List<FIELD> fIELDS = null;

        public String getRSNUM() {
            return RSNUM;
        }

        public void setRSNUM(String RSNUM) {
            this.RSNUM = RSNUM;
        }

        public String getDOCS() {
            return DOCS;
        }

        public void setDOCS(String DOCS) {
            this.DOCS = DOCS;
        }

        public String getPERMITS() {
            return PERMITS;
        }

        public void setPERMITS(String PERMITS) {
            this.PERMITS = PERMITS;
        }

        public String getBAUTL() {
            return BAUTL;
        }

        public void setBAUTL(String BAUTL) {
            this.BAUTL = BAUTL;
        }

        public String getZZPERMIT() {
            return ZZPERMIT;
        }

        public void setZZPERMIT(String ZZPERMIT) {
            this.ZZPERMIT = ZZPERMIT;
        }

        public String getZZPMISTDT() {
            return ZZPMISTDT;
        }

        public void setZZPMISTDT(String ZZPMISTDT) {
            this.ZZPMISTDT = ZZPMISTDT;
        }

        public String getZZPMISTTM() {
            return ZZPMISTTM;
        }

        public void setZZPMISTTM(String ZZPMISTTM) {
            this.ZZPMISTTM = ZZPMISTTM;
        }

        public String getZZPMIENDT() {
            return ZZPMIENDT;
        }

        public void setZZPMIENDT(String ZZPMIENDT) {
            this.ZZPMIENDT = ZZPMIENDT;
        }

        public String getZZPMIENTM() {
            return ZZPMIENTM;
        }

        public void setZZPMIENTM(String ZZPMIENTM) {
            this.ZZPMIENTM = ZZPMIENTM;
        }

        public String getZZPMIHR1() {
            return ZZPMIHR1;
        }

        public void setZZPMIHR1(String ZZPMIHR1) {
            this.ZZPMIHR1 = ZZPMIHR1;
        }

        public String getIDEALHOURS() {
            return IDEALHOURS;
        }

        public void setIDEALHOURS(String IDEALHOURS) {
            this.IDEALHOURS = IDEALHOURS;
        }

        public String getBEMOT() {
            return BEMOT;
        }

        public void setBEMOT(String BEMOT) {
            this.BEMOT = BEMOT;
        }

        public String getAUERU() {
            return AUERU;
        }

        public void setAUERU(String AUERU) {
            this.AUERU = AUERU;
        }

        public String getMAKTX() {
            return MAKTX;
        }

        public void setMAKTX(String MAKTX) {
            this.MAKTX = MAKTX;
        }

        public String getANLZU() {
            return ANLZU;
        }

        public void setANLZU(String ANLZU) {
            this.ANLZU = ANLZU;
        }

        public String getANLZUX() {
            return ANLZUX;
        }

        public void setANLZUX(String ANLZUX) {
            this.ANLZUX = ANLZUX;
        }

        public String getAUSWK() {
            return AUSWK;
        }

        public void setAUSWK(String AUSWK) {
            this.AUSWK = AUSWK;
        }

        public String getPOSID() {
            return POSID;
        }

        public void setPOSID(String POSID) {
            this.POSID = POSID;
        }

        public String getREVNR() {
            return REVNR;
        }

        public void setREVNR(String REVNR) {
            this.REVNR = REVNR;
        }

        public String getFISTL() {
            return FISTL;
        }

        public void setFISTL(String FISTL) {
            this.FISTL = FISTL;
        }

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

        public List<FIELD> getFIELDS() {
            return fIELDS;
        }

        public void setFIELDS(List<FIELD> fIELDS) {
            this.fIELDS = fIELDS;
        }

    }







    public class ETORDERLONGTEXT {

        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("TEXT_LINE")
        @Expose
        private String tEXTLINE;
        @SerializedName("ACTIVITY")
        @Expose
        private String aCTIVITY;
        @SerializedName("TDID")
        @Expose
        private String Tdid;

        public String getTdid() {
            return Tdid;
        }

        public void setTdid(String tdid) {
            Tdid = tdid;
        }

        public String getAUFNR() {
            return aUFNR;
        }

        public void setAUFNR(String aUFNR) {
            this.aUFNR = aUFNR;
        }

        public String getTEXTLINE() {
            return tEXTLINE;
        }

        public void setTEXTLINE(String tEXTLINE) {
            this.tEXTLINE = tEXTLINE;
        }

        public String getACTIVITY() {
            return aCTIVITY;
        }

        public void setACTIVITY(String aCTIVITY) {
            this.aCTIVITY = aCTIVITY;
        }

    }







    public class ETORDEROLIST {

        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("OBKNR")
        @Expose
        private String oBKNR;
        @SerializedName("OBZAE")
        @Expose
        private String oBZAE;
        @SerializedName("QMNUM")
        @Expose
        private String qMNUM;
        @SerializedName("EQUNR")
        @Expose
        private String eQUNR;
        @SerializedName("TPLNR")
        @Expose
        private String tPLNR;
        @SerializedName("EQKTX")
        @Expose
        private String eQKTX;
        @SerializedName("STRNO")
        @Expose
        private String Strno;
        @SerializedName("BAUTL")
        @Expose
        private String Bautl;
        @SerializedName("QMTXT")
        @Expose
        private String Qmtxt;
        @SerializedName("PLTXT")
        @Expose
        private String Pltxt;
        @SerializedName("MAKTX")
        @Expose
        private String Maktx;
        @SerializedName("ACTION")
        @Expose
        private String Action;

        public String getPltxt() {
            return Pltxt;
        }

        public void setPltxt(String pltxt) {
            Pltxt = pltxt;
        }

        public String getMaktx() {
            return Maktx;
        }

        public void setMaktx(String maktx) {
            Maktx = maktx;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }

        public String getQmtxt() {
            return Qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            Qmtxt = qmtxt;
        }

        public String getStrno() {
            return Strno;
        }

        public void setStrno(String strno) {
            Strno = strno;
        }

        public String getBautl() {
            return Bautl;
        }

        public void setBautl(String bautl) {
            Bautl = bautl;
        }

        public String getAUFNR() {
            return aUFNR;
        }

        public void setAUFNR(String aUFNR) {
            this.aUFNR = aUFNR;
        }

        public String getOBKNR() {
            return oBKNR;
        }

        public void setOBKNR(String oBKNR) {
            this.oBKNR = oBKNR;
        }

        public String getOBZAE() {
            return oBZAE;
        }

        public void setOBZAE(String oBZAE) {
            this.oBZAE = oBZAE;
        }

        public String getQMNUM() {
            return qMNUM;
        }

        public void setQMNUM(String qMNUM) {
            this.qMNUM = qMNUM;
        }

        public String getEQUNR() {
            return eQUNR;
        }

        public void setEQUNR(String eQUNR) {
            this.eQUNR = eQUNR;
        }

        public String getTPLNR() {
            return tPLNR;
        }

        public void setTPLNR(String tPLNR) {
            this.tPLNR = tPLNR;
        }

        public String getEQKTX() {
            return eQKTX;
        }

        public void setEQKTX(String eQKTX) {
            this.eQKTX = eQKTX;
        }

    }







    public class ETORDEROPERATION {

        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("VORNR")
        @Expose
        private String vORNR;
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
        private List<FIELD_> fIELDS = null;
        @SerializedName("LARNT")
        @Expose
        private String lARNT;
        @SerializedName("LARNT_TEXT")
        @Expose
        private String lARNTTEXT;
        @SerializedName("UVORN")
        @Expose
        private String Uvorn;
        @SerializedName("PERNR")
        @Expose
        private String Pernr;
        @SerializedName("ASNUM")
        @Expose
        private String Asnum;
        @SerializedName("PLNTY")
        @Expose
        private String Plnty;
        @SerializedName("PLNAL")
        @Expose
        private String Plnal;
        @SerializedName("PLNNR")
        @Expose
        private String Plnnr;
        @SerializedName("ACTION")
        @Expose
        private String Action;
        @SerializedName("ANLZU")
        @Expose
        private String Anlzu;
        @SerializedName("SORTL")
        @Expose
        private String Sortl;
        @SerializedName("IDEALCOND")
        @Expose
        private String IdealCond;
        @SerializedName("ACTUALCOND")
        @Expose
        private String ActualCond;
        @SerializedName("SAKTO")
        @Expose
        private String SAKTO;

        public String getSAKTO() {
            return SAKTO;
        }

        public void setSAKTO(String SAKTO) {
            this.SAKTO = SAKTO;
        }

        public String getUvorn() {
            return Uvorn;
        }

        public void setUvorn(String uvorn) {
            Uvorn = uvorn;
        }

        public String getPernr() {
            return Pernr;
        }

        public void setPernr(String pernr) {
            Pernr = pernr;
        }

        public String getAsnum() {
            return Asnum;
        }

        public void setAsnum(String asnum) {
            Asnum = asnum;
        }

        public String getPlnty() {
            return Plnty;
        }

        public void setPlnty(String plnty) {
            Plnty = plnty;
        }

        public String getPlnal() {
            return Plnal;
        }

        public void setPlnal(String plnal) {
            Plnal = plnal;
        }

        public String getPlnnr() {
            return Plnnr;
        }

        public void setPlnnr(String plnnr) {
            Plnnr = plnnr;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }

        public String getAnlzu() {
            return Anlzu;
        }

        public void setAnlzu(String anlzu) {
            Anlzu = anlzu;
        }

        public String getSortl() {
            return Sortl;
        }

        public void setSortl(String sortl) {
            Sortl = sortl;
        }

        public String getIdealCond() {
            return IdealCond;
        }

        public void setIdealCond(String idealCond) {
            IdealCond = idealCond;
        }

        public String getActualCond() {
            return ActualCond;
        }

        public void setActualCond(String actualCond) {
            ActualCond = actualCond;
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

        public List<FIELD_> getFIELDS() {
            return fIELDS;
        }

        public void setFIELDS(List<FIELD_> fIELDS) {
            this.fIELDS = fIELDS;
        }

        public String getLARNT() {
            return lARNT;
        }

        public void setLARNT(String lARNT) {
            this.lARNT = lARNT;
        }

        public String getLARNTTEXT() {
            return lARNTTEXT;
        }

        public void setLARNTTEXT(String lARNTTEXT) {
            this.lARNTTEXT = lARNTTEXT;
        }

    }




    public class ETORDERCOMPONENTS
    {
        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("VORNR")
        @Expose
        private String vORNR;
        @SerializedName("UVORN")
        @Expose
        private String uVORN;
        @SerializedName("RSNUM")
        @Expose
        private String rSNUM;
        @SerializedName("RSPOS")
        @Expose
        private String rSPOS;
        @SerializedName("MATNR")
        @Expose
        private String mATNR;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("LGORT")
        @Expose
        private String lGORT;
        @SerializedName("POSNR")
        @Expose
        private String pOSNR;
        @SerializedName("BDMNG")
        @Expose
        private String bDMNG;
        @SerializedName("MEINS")
        @Expose
        private String mEINS;
        @SerializedName("POSTP")
        @Expose
        private String pOSTP;
        @SerializedName("WEMPF")
        @Expose
        private String wEMPF;
        @SerializedName("ABLAD")
        @Expose
        private String aBLAD;
        @SerializedName("MFRNR")
        @Expose
        private String mFRNR;
        @SerializedName("MFRPN")
        @Expose
        private String mFRPN;
        @SerializedName("MATNR_TEXT")
        @Expose
        private String mATNRTEXT;
        @SerializedName("WERKS_TEXT")
        @Expose
        private String wERKSTEXT;
        @SerializedName("LGORT_TEXT")
        @Expose
        private String lGORTTEXT;
        @SerializedName("POSTP_TEXT")
        @Expose
        private String pOSTPTEXT;
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
        @SerializedName("CHARG")
        @Expose
        private String charg;

        public String getCharg() {
            return charg;
        }

        public void setCharg(String charg) {
            this.charg = charg;
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

        public String getRSNUM() {
            return rSNUM;
        }

        public void setRSNUM(String rSNUM) {
            this.rSNUM = rSNUM;
        }

        public String getRSPOS() {
            return rSPOS;
        }

        public void setRSPOS(String rSPOS) {
            this.rSPOS = rSPOS;
        }

        public String getMATNR() {
            return mATNR;
        }

        public void setMATNR(String mATNR) {
            this.mATNR = mATNR;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getLGORT() {
            return lGORT;
        }

        public void setLGORT(String lGORT) {
            this.lGORT = lGORT;
        }

        public String getPOSNR() {
            return pOSNR;
        }

        public void setPOSNR(String pOSNR) {
            this.pOSNR = pOSNR;
        }

        public String getBDMNG() {
            return bDMNG;
        }

        public void setBDMNG(String bDMNG) {
            this.bDMNG = bDMNG;
        }

        public String getMEINS() {
            return mEINS;
        }

        public void setMEINS(String mEINS) {
            this.mEINS = mEINS;
        }

        public String getPOSTP() {
            return pOSTP;
        }

        public void setPOSTP(String pOSTP) {
            this.pOSTP = pOSTP;
        }

        public String getWEMPF() {
            return wEMPF;
        }

        public void setWEMPF(String wEMPF) {
            this.wEMPF = wEMPF;
        }

        public String getABLAD() {
            return aBLAD;
        }

        public void setABLAD(String aBLAD) {
            this.aBLAD = aBLAD;
        }

        public String getMFRNR() {
            return mFRNR;
        }

        public void setMFRNR(String mFRNR) {
            this.mFRNR = mFRNR;
        }

        public String getMFRPN() {
            return mFRPN;
        }

        public void setMFRPN(String mFRPN) {
            this.mFRPN = mFRPN;
        }

        public String getMATNRTEXT() {
            return mATNRTEXT;
        }

        public void setMATNRTEXT(String mATNRTEXT) {
            this.mATNRTEXT = mATNRTEXT;
        }

        public String getWERKSTEXT() {
            return wERKSTEXT;
        }

        public void setWERKSTEXT(String wERKSTEXT) {
            this.wERKSTEXT = wERKSTEXT;
        }

        public String getLGORTTEXT() {
            return lGORTTEXT;
        }

        public void setLGORTTEXT(String lGORTTEXT) {
            this.lGORTTEXT = lGORTTEXT;
        }

        public String getPOSTPTEXT() {
            return pOSTPTEXT;
        }

        public void setPOSTPTEXT(String pOSTPTEXT) {
            this.pOSTPTEXT = pOSTPTEXT;
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






    public class ETORDERStatus {

        @SerializedName("AUFNR")
        @Expose
        private String aUFNR;
        @SerializedName("OBJNR")
        @Expose
        private String oBJNR;
        @SerializedName("STSMA")
        @Expose
        private String sTSMA;
        @SerializedName("STONR")
        @Expose
        private String sTONR;
        @SerializedName("HSONR")
        @Expose
        private String hSONR;
        @SerializedName("NSONR")
        @Expose
        private String nSONR;
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
        private String aCT;
        @SerializedName("INIST")
        @Expose
        private String iNIST;
        @SerializedName("VORNR")
        @Expose
        private String Vornr;
        @SerializedName("ACTION")
        @Expose
        private String Action;

        public String getVornr() {
            return Vornr;
        }

        public void setVornr(String vornr) {
            Vornr = vornr;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }

        public String getAUFNR() {
            return aUFNR;
        }

        public void setAUFNR(String aUFNR) {
            this.aUFNR = aUFNR;
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

        public String getSTONR() {
            return sTONR;
        }

        public void setSTONR(String sTONR) {
            this.sTONR = sTONR;
        }

        public String getHSONR() {
            return hSONR;
        }

        public void setHSONR(String hSONR) {
            this.hSONR = hSONR;
        }

        public String getNSONR() {
            return nSONR;
        }

        public void setNSONR(String nSONR) {
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

        public String getACT() {
            return aCT;
        }

        public void setACT(String aCT) {
            this.aCT = aCT;
        }

        public String getINIST() {
            return iNIST;
        }

        public void setINIST(String iNIST) {
            this.iNIST = iNIST;
        }

    }








    public class FIELD {

        @SerializedName("ZDOCTYPE")
        @Expose
        private String zDOCTYPE;
        @SerializedName("ZDOCTYPE_ITEM")
        @Expose
        private String zDOCTYPEITEM;
        @SerializedName("TABNAME")
        @Expose
        private String tABNAME;
        @SerializedName("FIELDNAME")
        @Expose
        private String fIELDNAME;
        @SerializedName("DATATYPE")
        @Expose
        private String dATATYPE;
        @SerializedName("VALUE")
        @Expose
        private String vALUE;
        @SerializedName("FLABEL")
        @Expose
        private String fLABEL;
        @SerializedName("SEQUENCE")
        @Expose
        private String sEQUENCE;
        @SerializedName("LENGTH")
        @Expose
        private String lENGTH;

        public String getZDOCTYPE() {
            return zDOCTYPE;
        }

        public void setZDOCTYPE(String zDOCTYPE) {
            this.zDOCTYPE = zDOCTYPE;
        }

        public String getZDOCTYPEITEM() {
            return zDOCTYPEITEM;
        }

        public void setZDOCTYPEITEM(String zDOCTYPEITEM) {
            this.zDOCTYPEITEM = zDOCTYPEITEM;
        }

        public String getTABNAME() {
            return tABNAME;
        }

        public void setTABNAME(String tABNAME) {
            this.tABNAME = tABNAME;
        }

        public String getFIELDNAME() {
            return fIELDNAME;
        }

        public void setFIELDNAME(String fIELDNAME) {
            this.fIELDNAME = fIELDNAME;
        }

        public String getDATATYPE() {
            return dATATYPE;
        }

        public void setDATATYPE(String dATATYPE) {
            this.dATATYPE = dATATYPE;
        }

        public String getVALUE() {
            return vALUE;
        }

        public void setVALUE(String vALUE) {
            this.vALUE = vALUE;
        }

        public String getFLABEL() {
            return fLABEL;
        }

        public void setFLABEL(String fLABEL) {
            this.fLABEL = fLABEL;
        }

        public String getSEQUENCE() {
            return sEQUENCE;
        }

        public void setSEQUENCE(String sEQUENCE) {
            this.sEQUENCE = sEQUENCE;
        }

        public String getLENGTH() {
            return lENGTH;
        }

        public void setLENGTH(String lENGTH) {
            this.lENGTH = lENGTH;
        }

    }








    public class FIELD_ {

        @SerializedName("ZDOCTYPE")
        @Expose
        private String zDOCTYPE;
        @SerializedName("ZDOCTYPE_ITEM")
        @Expose
        private String zDOCTYPEITEM;
        @SerializedName("TABNAME")
        @Expose
        private String tABNAME;
        @SerializedName("FIELDNAME")
        @Expose
        private String fIELDNAME;
        @SerializedName("DATATYPE")
        @Expose
        private String dATATYPE;
        @SerializedName("FLABEL")
        @Expose
        private String fLABEL;
        @SerializedName("SEQUENCE")
        @Expose
        private String sEQUENCE;
        @SerializedName("LENGTH")
        @Expose
        private String lENGTH;

        public String getZDOCTYPE() {
            return zDOCTYPE;
        }

        public void setZDOCTYPE(String zDOCTYPE) {
            this.zDOCTYPE = zDOCTYPE;
        }

        public String getZDOCTYPEITEM() {
            return zDOCTYPEITEM;
        }

        public void setZDOCTYPEITEM(String zDOCTYPEITEM) {
            this.zDOCTYPEITEM = zDOCTYPEITEM;
        }

        public String getTABNAME() {
            return tABNAME;
        }

        public void setTABNAME(String tABNAME) {
            this.tABNAME = tABNAME;
        }

        public String getFIELDNAME() {
            return fIELDNAME;
        }

        public void setFIELDNAME(String fIELDNAME) {
            this.fIELDNAME = fIELDNAME;
        }

        public String getDATATYPE() {
            return dATATYPE;
        }

        public void setDATATYPE(String dATATYPE) {
            this.dATATYPE = dATATYPE;
        }

        public String getFLABEL() {
            return fLABEL;
        }

        public void setFLABEL(String fLABEL) {
            this.fLABEL = fLABEL;
        }

        public String getSEQUENCE() {
            return sEQUENCE;
        }

        public void setSEQUENCE(String sEQUENCE) {
            this.sEQUENCE = sEQUENCE;
        }

        public String getLENGTH() {
            return lENGTH;
        }

        public void setLENGTH(String lENGTH) {
            this.lENGTH = lENGTH;
        }

    }
}