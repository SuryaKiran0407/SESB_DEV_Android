package com.enstrapp.fieldtekpro.InitialLoad_Rest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class REST_VHLP_SER
{

    @SerializedName("ET_NOTIF_TYPES")
    @Expose
    private List<ETNOTIFTYPE> eTNOTIFTYPES = null;
    @SerializedName("ET_NOTIF_PRIORITY")
    @Expose
    private List<ETNOTIFPRIORITY> eTNOTIFPRIORITY = null;
    @SerializedName("ET_NOTIF_CODES")
    @Expose
    private List<ETNOTIFCODE> eTNOTIFCODES = null;
    @SerializedName("ET_MEAS_CODES")
    @Expose
    private List<ETMEASCODE> eTMEASCODES = null;
    @SerializedName("ET_INSP_CODES")
    @Expose
    private List<ETINSPCODE> eTINSPCODES = null;
    @SerializedName("ET_UDEC_CODES")
    @Expose
    private List<ETUDECCODE> eTUDECCODES = null;
    @SerializedName("ET_ORD_TYPES")
    @Expose
    private List<ETORDTYPE> eTORDTYPES = null;
    @SerializedName("ET_ORD_PRIORITY")
    @Expose
    private List<ETORDPRIORITY> eTORDPRIORITY = null;
    @SerializedName("ET_MOVEMENT_TYPES")
    @Expose
    private List<ETMOVEMENTTYPE> eTMOVEMENTTYPES = null;
    @SerializedName("ET_KOSTL")
    @Expose
    private List<ETKOSTL> eTKOSTL = null;
    @SerializedName("ET_WKCTR_PLANT")
    @Expose
    private List<ETWKCTRPLANT> eTWKCTRPLANT = null;
    @SerializedName("ET_UNITS")
    @Expose
    private List<ETUNIT> eTUNITS = null;
    @SerializedName("ET_PLANTS")
    @Expose
    private List<ETPLANT> eTPLANTS = null;
    @SerializedName("ET_STLOC")
    @Expose
    private List<ETSTLOC> eTSTLOC = null;
    @SerializedName("ET_STEUS")
    @Expose
    private List<ETSTEU> eTSTEUS = null;
    @SerializedName("ET_CONF_REASON")
    @Expose
    private List<ETCONFREASON> eTCONFREASON = null;
    @SerializedName("ET_LSTAR")
    @Expose
    private List<ETLSTAR> eTLSTAR = null;
    @SerializedName("ET_INGRP")
    @Expose
    private List<ETINGRP> eTINGRP = null;
    @SerializedName("ET_FIELDS")
    @Expose
    private List<ETFIELD> eTFIELDS = null;
    @SerializedName("ET_ORD_SYSCOND")
    @Expose
    private List<ETORDSYSCOND> eTORDSYSCOND = null;
    @SerializedName("ET_NOTIF_EFFECT")
    @Expose
    private List<ETNOTIFEFFECT> eTNOTIFEFFECT = null;
    @SerializedName("ET_TQ80")
    @Expose
    private List<ETTQ80> eTTQ80 = null;
    @SerializedName("ET_USERS")
    @Expose
    private List<ETUSER> eTUSERS = null;
    @SerializedName("ET_REVNR")
    @Expose
    private List<ETREVNR> eTREVNR = null;
    @SerializedName("ET_ILART")
    @Expose
    private List<ETILART> eTILART = null;
    @SerializedName("ET_PERNR")
    @Expose
    private List<ETPERNR> eTPERNR = null;

    public List<ETPERNR> geteTPERNR() {
        return eTPERNR;
    }

    public void seteTPERNR(List<ETPERNR> eTPERNR) {
        this.eTPERNR = eTPERNR;
    }

    public List<ETNOTIFTYPE> getETNOTIFTYPES() {
        return eTNOTIFTYPES;
    }

    public void setETNOTIFTYPES(List<ETNOTIFTYPE> eTNOTIFTYPES) {
        this.eTNOTIFTYPES = eTNOTIFTYPES;
    }

    public List<ETNOTIFPRIORITY> getETNOTIFPRIORITY() {
        return eTNOTIFPRIORITY;
    }

    public void setETNOTIFPRIORITY(List<ETNOTIFPRIORITY> eTNOTIFPRIORITY) {
        this.eTNOTIFPRIORITY = eTNOTIFPRIORITY;
    }

    public List<ETNOTIFCODE> getETNOTIFCODES() {
        return eTNOTIFCODES;
    }

    public void setETNOTIFCODES(List<ETNOTIFCODE> eTNOTIFCODES) {
        this.eTNOTIFCODES = eTNOTIFCODES;
    }

    public List<ETMEASCODE> getETMEASCODES() {
        return eTMEASCODES;
    }

    public void setETMEASCODES(List<ETMEASCODE> eTMEASCODES) {
        this.eTMEASCODES = eTMEASCODES;
    }

    public List<ETINSPCODE> getETINSPCODES() {
        return eTINSPCODES;
    }

    public void setETINSPCODES(List<ETINSPCODE> eTINSPCODES) {
        this.eTINSPCODES = eTINSPCODES;
    }

    public List<ETUDECCODE> getETUDECCODES() {
        return eTUDECCODES;
    }

    public void setETUDECCODES(List<ETUDECCODE> eTUDECCODES) {
        this.eTUDECCODES = eTUDECCODES;
    }

    public List<ETORDTYPE> getETORDTYPES() {
        return eTORDTYPES;
    }

    public void setETORDTYPES(List<ETORDTYPE> eTORDTYPES) {
        this.eTORDTYPES = eTORDTYPES;
    }

    public List<ETORDPRIORITY> getETORDPRIORITY() {
        return eTORDPRIORITY;
    }

    public void setETORDPRIORITY(List<ETORDPRIORITY> eTORDPRIORITY) {
        this.eTORDPRIORITY = eTORDPRIORITY;
    }

    public List<ETMOVEMENTTYPE> getETMOVEMENTTYPES() {
        return eTMOVEMENTTYPES;
    }

    public void setETMOVEMENTTYPES(List<ETMOVEMENTTYPE> eTMOVEMENTTYPES) {
        this.eTMOVEMENTTYPES = eTMOVEMENTTYPES;
    }

    public List<ETKOSTL> getETKOSTL() {
        return eTKOSTL;
    }

    public void setETKOSTL(List<ETKOSTL> eTKOSTL) {
        this.eTKOSTL = eTKOSTL;
    }

    public List<ETWKCTRPLANT> getETWKCTRPLANT() {
        return eTWKCTRPLANT;
    }

    public void setETWKCTRPLANT(List<ETWKCTRPLANT> eTWKCTRPLANT) {
        this.eTWKCTRPLANT = eTWKCTRPLANT;
    }

    public List<ETUNIT> getETUNITS() {
        return eTUNITS;
    }

    public void setETUNITS(List<ETUNIT> eTUNITS) {
        this.eTUNITS = eTUNITS;
    }

    public List<ETPLANT> getETPLANTS() {
        return eTPLANTS;
    }

    public void setETPLANTS(List<ETPLANT> eTPLANTS) {
        this.eTPLANTS = eTPLANTS;
    }

    public List<ETSTLOC> getETSTLOC() {
        return eTSTLOC;
    }

    public void setETSTLOC(List<ETSTLOC> eTSTLOC) {
        this.eTSTLOC = eTSTLOC;
    }

    public List<ETSTEU> getETSTEUS() {
        return eTSTEUS;
    }

    public void setETSTEUS(List<ETSTEU> eTSTEUS) {
        this.eTSTEUS = eTSTEUS;
    }

    public List<ETCONFREASON> getETCONFREASON() {
        return eTCONFREASON;
    }

    public void setETCONFREASON(List<ETCONFREASON> eTCONFREASON) {
        this.eTCONFREASON = eTCONFREASON;
    }

    public List<ETLSTAR> getETLSTAR() {
        return eTLSTAR;
    }

    public void setETLSTAR(List<ETLSTAR> eTLSTAR) {
        this.eTLSTAR = eTLSTAR;
    }

    public List<ETINGRP> getETINGRP() {
        return eTINGRP;
    }

    public void setETINGRP(List<ETINGRP> eTINGRP) {
        this.eTINGRP = eTINGRP;
    }

    public List<ETFIELD> getETFIELDS() {
        return eTFIELDS;
    }

    public void setETFIELDS(List<ETFIELD> eTFIELDS) {
        this.eTFIELDS = eTFIELDS;
    }

    public List<ETORDSYSCOND> getETORDSYSCOND() {
        return eTORDSYSCOND;
    }

    public void setETORDSYSCOND(List<ETORDSYSCOND> eTORDSYSCOND) {
        this.eTORDSYSCOND = eTORDSYSCOND;
    }

    public List<ETNOTIFEFFECT> getETNOTIFEFFECT() {
        return eTNOTIFEFFECT;
    }

    public void setETNOTIFEFFECT(List<ETNOTIFEFFECT> eTNOTIFEFFECT) {
        this.eTNOTIFEFFECT = eTNOTIFEFFECT;
    }

    public List<ETTQ80> getETTQ80() {
        return eTTQ80;
    }

    public void setETTQ80(List<ETTQ80> eTTQ80) {
        this.eTTQ80 = eTTQ80;
    }

    public List<ETUSER> getETUSERS() {
        return eTUSERS;
    }

    public void setETUSERS(List<ETUSER> eTUSERS) {
        this.eTUSERS = eTUSERS;
    }

    public List<ETREVNR> getETREVNR() {
        return eTREVNR;
    }

    public void setETREVNR(List<ETREVNR> eTREVNR) {
        this.eTREVNR = eTREVNR;
    }

    public List<ETILART> getETILART() {
        return eTILART;
    }

    public void setETILART(List<ETILART> eTILART) {
        this.eTILART = eTILART;
    }






    public class CAUSECODE {

        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String kURZTEXT;
        @SerializedName("CODES")
        @Expose
        private List<CODE_> cODES = null;

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public String getKURZTEXT() {
            return kURZTEXT;
        }

        public void setKURZTEXT(String kURZTEXT) {
            this.kURZTEXT = kURZTEXT;
        }

        public List<CODE_> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE_> cODES) {
            this.cODES = cODES;
        }

    }





    public class CODE {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

    }







    public class CODE_ {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

    }






    public class CODE__ {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

    }







    public class CODE___ {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

    }






    public class CODE____ {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;
        @SerializedName("BEWERTUNG")
        @Expose
        private String bEWERTUNG;
        @SerializedName("FEHLKLASSE")
        @Expose
        private String fEHLKLASSE;
        @SerializedName("QKENNZAHL")
        @Expose
        private String QKENNZAHL;
        @SerializedName("FOLGEAKTI")
        @Expose
        private String FOLGEAKTI;
        @SerializedName("FEHLKLASSETXT")
        @Expose
        private String FEHLKLASSETXT;

        public String getQKENNZAHL() {
            return QKENNZAHL;
        }

        public void setQKENNZAHL(String QKENNZAHL) {
            this.QKENNZAHL = QKENNZAHL;
        }

        public String getFOLGEAKTI() {
            return FOLGEAKTI;
        }

        public void setFOLGEAKTI(String FOLGEAKTI) {
            this.FOLGEAKTI = FOLGEAKTI;
        }

        public String getFEHLKLASSETXT() {
            return FEHLKLASSETXT;
        }

        public void setFEHLKLASSETXT(String FEHLKLASSETXT) {
            this.FEHLKLASSETXT = FEHLKLASSETXT;
        }

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

        public String getBEWERTUNG() {
            return bEWERTUNG;
        }

        public void setBEWERTUNG(String bEWERTUNG) {
            this.bEWERTUNG = bEWERTUNG;
        }

        public String getFEHLKLASSE() {
            return fEHLKLASSE;
        }

        public void setFEHLKLASSE(String fEHLKLASSE) {
            this.fEHLKLASSE = fEHLKLASSE;
        }

    }






    public class CODE_____ {

        @SerializedName("CODE")
        @Expose
        private String cODE;
        @SerializedName("FEHLKLASSE")
        @Expose
        private String FEHLKLASSE;
        @SerializedName("FEHLKLASSETXT")
        @Expose
        private String FEHLKLASSETXT;
        @SerializedName("KURZTEXT1")
        @Expose
        private String kURZTEXT1;
        @SerializedName("BEWERTUNG")
        @Expose
        private String bEWERTUNG;
        @SerializedName("QKENNZAHL")
        @Expose
        private String qKENNZAHL;
        @SerializedName("FOLGEAKTI")
        @Expose
        private String fOLGEAKTI;

        public String getFEHLKLASSE() {
            return FEHLKLASSE;
        }

        public void setFEHLKLASSE(String FEHLKLASSE) {
            this.FEHLKLASSE = FEHLKLASSE;
        }

        public String getFEHLKLASSETXT() {
            return FEHLKLASSETXT;
        }

        public void setFEHLKLASSETXT(String FEHLKLASSETXT) {
            this.FEHLKLASSETXT = FEHLKLASSETXT;
        }

        public String getCODE() {
            return cODE;
        }

        public void setCODE(String cODE) {
            this.cODE = cODE;
        }

        public String getKURZTEXT1() {
            return kURZTEXT1;
        }

        public void setKURZTEXT1(String kURZTEXT1) {
            this.kURZTEXT1 = kURZTEXT1;
        }

        public String getBEWERTUNG() {
            return bEWERTUNG;
        }

        public void setBEWERTUNG(String bEWERTUNG) {
            this.bEWERTUNG = bEWERTUNG;
        }

        public String getQKENNZAHL() {
            return qKENNZAHL;
        }

        public void setQKENNZAHL(String qKENNZAHL) {
            this.qKENNZAHL = qKENNZAHL;
        }

        public String getFOLGEAKTI() {
            return fOLGEAKTI;
        }

        public void setFOLGEAKTI(String fOLGEAKTI) {
            this.fOLGEAKTI = fOLGEAKTI;
        }

    }







    public class ETCONFREASON {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("GRUND")
        @Expose
        private String gRUND;
        @SerializedName("GRDTX")
        @Expose
        private String gRDTX;

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getGRUND() {
            return gRUND;
        }

        public void setGRUND(String gRUND) {
            this.gRUND = gRUND;
        }

        public String getGRDTX() {
            return gRDTX;
        }

        public void setGRDTX(String gRDTX) {
            this.gRDTX = gRDTX;
        }

    }






    public class ETFIELD {

        @SerializedName("SPRAS")
        @Expose
        private String sPRAS;
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
        @SerializedName("FLABEL")
        @Expose
        private String fLABEL;
        @SerializedName("DATATYPE")
        @Expose
        private String dATATYPE;
        @SerializedName("SEQUENCE")
        @Expose
        private Integer sEQUENCE;
        @SerializedName("LENGTH")
        @Expose
        private Integer lENGTH;

        public String getSPRAS() {
            return sPRAS;
        }

        public void setSPRAS(String sPRAS) {
            this.sPRAS = sPRAS;
        }

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

        public String getFLABEL() {
            return fLABEL;
        }

        public void setFLABEL(String fLABEL) {
            this.fLABEL = fLABEL;
        }

        public String getDATATYPE() {
            return dATATYPE;
        }

        public void setDATATYPE(String dATATYPE) {
            this.dATATYPE = dATATYPE;
        }

        public Integer getSEQUENCE() {
            return sEQUENCE;
        }

        public void setSEQUENCE(Integer sEQUENCE) {
            this.sEQUENCE = sEQUENCE;
        }

        public Integer getLENGTH() {
            return lENGTH;
        }

        public void setLENGTH(Integer lENGTH) {
            this.lENGTH = lENGTH;
        }

    }




    public class ETPERNR {

        @SerializedName("WERKS")
        @Expose
        private String Werks;
        @SerializedName("ARBPL")
        @Expose
        private String Arbpl;
        @SerializedName("OBJID")
        @Expose
        private String Objid;
        @SerializedName("LASTNAME")
        @Expose
        private String Lastname;
        @SerializedName("FIRSTNAME")
        @Expose
        private String Firstname;

        public String getWerks() {
            return Werks;
        }

        public void setWerks(String werks) {
            Werks = werks;
        }

        public String getArbpl() {
            return Arbpl;
        }

        public void setArbpl(String arbpl) {
            Arbpl = arbpl;
        }

        public String getObjid() {
            return Objid;
        }

        public void setObjid(String objid) {
            Objid = objid;
        }

        public String getLastname() {
            return Lastname;
        }

        public void setLastname(String lastname) {
            Lastname = lastname;
        }

        public String getFirstname() {
            return Firstname;
        }

        public void setFirstname(String firstname) {
            Firstname = firstname;
        }
    }





    public class ETILART {

        @SerializedName("AUART")
        @Expose
        private String aUART;
        @SerializedName("ILART")
        @Expose
        private String iLART;
        @SerializedName("ILATX")
        @Expose
        private String iLATX;

        public String getAUART() {
            return aUART;
        }

        public void setAUART(String aUART) {
            this.aUART = aUART;
        }

        public String getILART() {
            return iLART;
        }

        public void setILART(String iLART) {
            this.iLART = iLART;
        }

        public String getILATX() {
            return iLATX;
        }

        public void setILATX(String iLATX) {
            this.iLATX = iLATX;
        }

    }





    public class ETINGRP {

        @SerializedName("IWERK")
        @Expose
        private String iWERK;
        @SerializedName("INGRP")
        @Expose
        private String iNGRP;
        @SerializedName("INNAM")
        @Expose
        private String iNNAM;

        public String getIWERK() {
            return iWERK;
        }

        public void setIWERK(String iWERK) {
            this.iWERK = iWERK;
        }

        public String getINGRP() {
            return iNGRP;
        }

        public void setINGRP(String iNGRP) {
            this.iNGRP = iNGRP;
        }

        public String getINNAM() {
            return iNNAM;
        }

        public void setINNAM(String iNNAM) {
            this.iNNAM = iNNAM;
        }

    }





    public class ETINSPCODE {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("KATALOGART")
        @Expose
        private String kATALOGART;
        @SerializedName("AUSWAHLMGE")
        @Expose
        private String aUSWAHLMGE;
        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String Kurztext;
        @SerializedName("CODES")
        @Expose
        private List<CODE____> cODES = null;

        public String getKurztext() {
            return Kurztext;
        }

        public void setKurztext(String kurztext) {
            Kurztext = kurztext;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getKATALOGART() {
            return kATALOGART;
        }

        public void setKATALOGART(String kATALOGART) {
            this.kATALOGART = kATALOGART;
        }

        public String getAUSWAHLMGE() {
            return aUSWAHLMGE;
        }

        public void setAUSWAHLMGE(String aUSWAHLMGE) {
            this.aUSWAHLMGE = aUSWAHLMGE;
        }

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public List<CODE____> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE____> cODES) {
            this.cODES = cODES;
        }

    }






    public class ETKOSTL {

        @SerializedName("BUKRS")
        @Expose
        private String bUKRS;
        @SerializedName("KOKRS")
        @Expose
        private String kOKRS;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("KOSTL")
        @Expose
        private String kOSTL;
        @SerializedName("KTEXT")
        @Expose
        private String kTEXT;
        @SerializedName("WAREA")
        @Expose
        private String WAREA;

        public String getWAREA() {
            return WAREA;
        }

        public void setWAREA(String WAREA) {
            this.WAREA = WAREA;
        }

        public String getBUKRS() {
            return bUKRS;
        }

        public void setBUKRS(String bUKRS) {
            this.bUKRS = bUKRS;
        }

        public String getKOKRS() {
            return kOKRS;
        }

        public void setKOKRS(String kOKRS) {
            this.kOKRS = kOKRS;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getKOSTL() {
            return kOSTL;
        }

        public void setKOSTL(String kOSTL) {
            this.kOSTL = kOSTL;
        }

        public String getKTEXT() {
            return kTEXT;
        }

        public void setKTEXT(String kTEXT) {
            this.kTEXT = kTEXT;
        }

    }






    public class ETLSTAR {

        @SerializedName("KOKRS")
        @Expose
        private String kOKRS;
        @SerializedName("KOSTL")
        @Expose
        private String kOSTL;
        @SerializedName("LATYP")
        @Expose
        private String lATYP;
        @SerializedName("LSTAR")
        @Expose
        private String lSTAR;
        @SerializedName("KTEXT")
        @Expose
        private String kTEXT;

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

        public String getLATYP() {
            return lATYP;
        }

        public void setLATYP(String lATYP) {
            this.lATYP = lATYP;
        }

        public String getLSTAR() {
            return lSTAR;
        }

        public void setLSTAR(String lSTAR) {
            this.lSTAR = lSTAR;
        }

        public String getKTEXT() {
            return kTEXT;
        }

        public void setKTEXT(String kTEXT) {
            this.kTEXT = kTEXT;
        }

    }







    public class ETMEASCODE {

        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String KURZTEXT;
        @SerializedName("CODES")
        @Expose
        private List<CODE___> cODES = null;

        public String getKURZTEXT() {
            return KURZTEXT;
        }

        public void setKURZTEXT(String KURZTEXT) {
            this.KURZTEXT = KURZTEXT;
        }

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public List<CODE___> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE___> cODES) {
            this.cODES = cODES;
        }

    }








    public class ETMOVEMENTTYPE {

        @SerializedName("BWART")
        @Expose
        private String bWART;
        @SerializedName("BTEXT")
        @Expose
        private String bTEXT;

        public String getBWART() {
            return bWART;
        }

        public void setBWART(String bWART) {
            this.bWART = bWART;
        }

        public String getBTEXT() {
            return bTEXT;
        }

        public void setBTEXT(String bTEXT) {
            this.bTEXT = bTEXT;
        }

    }






    public class ETNOTIFCODE {

        @SerializedName("NOTIFTYPE")
        @Expose
        private String NOTIFTYPE;
        @SerializedName("RBNR")
        @Expose
        private String rBNR;
        @SerializedName("ITEM_CODES")
        @Expose
        private List<ITEMCODE> iTEMCODES = null;
        @SerializedName("CAUSE_CODES")
        @Expose
        private List<CAUSECODE> cAUSECODES = null;
        @SerializedName("OBJECT_CODES")
        @Expose
        private List<OBJECTCODE> oBJECTCODES = null;

        public String getNOTIFTYPE() {
            return NOTIFTYPE;
        }

        public void setNOTIFTYPE(String NOTIFTYPE) {
            this.NOTIFTYPE = NOTIFTYPE;
        }

        public String getRBNR() {
            return rBNR;
        }

        public void setRBNR(String rBNR) {
            this.rBNR = rBNR;
        }

        public List<ITEMCODE> getITEMCODES() {
            return iTEMCODES;
        }

        public void setITEMCODES(List<ITEMCODE> iTEMCODES) {
            this.iTEMCODES = iTEMCODES;
        }

        public List<CAUSECODE> getCAUSECODES() {
            return cAUSECODES;
        }

        public void setCAUSECODES(List<CAUSECODE> cAUSECODES) {
            this.cAUSECODES = cAUSECODES;
        }

        public List<OBJECTCODE> getOBJECTCODES() {
            return oBJECTCODES;
        }

        public void setOBJECTCODES(List<OBJECTCODE> oBJECTCODES) {
            this.oBJECTCODES = oBJECTCODES;
        }

    }






    public class ETNOTIFEFFECT {

        @SerializedName("AUSWK")
        @Expose
        private String aUSWK;
        @SerializedName("AUSWKT")
        @Expose
        private String aUSWKT;

        public String getAUSWK() {
            return aUSWK;
        }

        public void setAUSWK(String aUSWK) {
            this.aUSWK = aUSWK;
        }

        public String getAUSWKT() {
            return aUSWKT;
        }

        public void setAUSWKT(String aUSWKT) {
            this.aUSWKT = aUSWKT;
        }

    }






    public class ETNOTIFPRIORITY {

        @SerializedName("PRIOK")
        @Expose
        private String pRIOK;
        @SerializedName("PRIOKX")
        @Expose
        private String pRIOKX;

        public String getPRIOK() {
            return pRIOK;
        }

        public void setPRIOK(String pRIOK) {
            this.pRIOK = pRIOK;
        }

        public String getPRIOKX() {
            return pRIOKX;
        }

        public void setPRIOKX(String pRIOKX) {
            this.pRIOKX = pRIOKX;
        }

    }








    public class ETNOTIFTYPE {

        @SerializedName("QMART")
        @Expose
        private String qMART;
        @SerializedName("QMARTX")
        @Expose
        private String qMARTX;
        @SerializedName("ACTIVE")
        @Expose
        private String aCTIVE;

        public String getQMART() {
            return qMART;
        }

        public void setQMART(String qMART) {
            this.qMART = qMART;
        }

        public String getQMARTX() {
            return qMARTX;
        }

        public void setQMARTX(String qMARTX) {
            this.qMARTX = qMARTX;
        }

        public String getACTIVE() {
            return aCTIVE;
        }

        public void setACTIVE(String aCTIVE) {
            this.aCTIVE = aCTIVE;
        }

    }






    public class ETORDPRIORITY {

        @SerializedName("PRIOK")
        @Expose
        private String pRIOK;
        @SerializedName("PRIOKX")
        @Expose
        private String pRIOKX;

        public String getPRIOK() {
            return pRIOK;
        }

        public void setPRIOK(String pRIOK) {
            this.pRIOK = pRIOK;
        }

        public String getPRIOKX() {
            return pRIOKX;
        }

        public void setPRIOKX(String pRIOKX) {
            this.pRIOKX = pRIOKX;
        }

    }







    public class ETORDSYSCOND {

        @SerializedName("ANLZU")
        @Expose
        private String aNLZU;
        @SerializedName("ANLZUX")
        @Expose
        private String aNLZUX;

        public String getANLZU() {
            return aNLZU;
        }

        public void setANLZU(String aNLZU) {
            this.aNLZU = aNLZU;
        }

        public String getANLZUX() {
            return aNLZUX;
        }

        public void setANLZUX(String aNLZUX) {
            this.aNLZUX = aNLZUX;
        }

    }







    public class ETORDTYPE {

        @SerializedName("AUART")
        @Expose
        private String aUART;
        @SerializedName("TXT")
        @Expose
        private String tXT;
        @SerializedName("ACTIVE")
        @Expose
        private String aCTIVE;

        public String getAUART() {
            return aUART;
        }

        public void setAUART(String aUART) {
            this.aUART = aUART;
        }

        public String getTXT() {
            return tXT;
        }

        public void setTXT(String tXT) {
            this.tXT = tXT;
        }

        public String getACTIVE() {
            return aCTIVE;
        }

        public void setACTIVE(String aCTIVE) {
            this.aCTIVE = aCTIVE;
        }

    }







    public class ETPLANT {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("NAME1")
        @Expose
        private String nAME1;

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getNAME1() {
            return nAME1;
        }

        public void setNAME1(String nAME1) {
            this.nAME1 = nAME1;
        }

    }








    public class ETREVNR {

        @SerializedName("IWERK")
        @Expose
        private String iWERK;
        @SerializedName("REVNR")
        @Expose
        private String rEVNR;
        @SerializedName("REVTX")
        @Expose
        private String rEVTX;

        public String getIWERK() {
            return iWERK;
        }

        public void setIWERK(String iWERK) {
            this.iWERK = iWERK;
        }

        public String getREVNR() {
            return rEVNR;
        }

        public void setREVNR(String rEVNR) {
            this.rEVNR = rEVNR;
        }

        public String getREVTX() {
            return rEVTX;
        }

        public void setREVTX(String rEVTX) {
            this.rEVTX = rEVTX;
        }

    }








    public class ETSTEU {

        @SerializedName("STEUS")
        @Expose
        private String sTEUS;
        @SerializedName("TXT")
        @Expose
        private String tXT;

        public String getSTEUS() {
            return sTEUS;
        }

        public void setSTEUS(String sTEUS) {
            this.sTEUS = sTEUS;
        }

        public String getTXT() {
            return tXT;
        }

        public void setTXT(String tXT) {
            this.tXT = tXT;
        }

    }







    public class ETSTLOC {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("NAME1")
        @Expose
        private String nAME1;
        @SerializedName("LGORT")
        @Expose
        private String lGORT;
        @SerializedName("LGOBE")
        @Expose
        private String lGOBE;

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getNAME1() {
            return nAME1;
        }

        public void setNAME1(String nAME1) {
            this.nAME1 = nAME1;
        }

        public String getLGORT() {
            return lGORT;
        }

        public void setLGORT(String lGORT) {
            this.lGORT = lGORT;
        }

        public String getLGOBE() {
            return lGOBE;
        }

        public void setLGOBE(String lGOBE) {
            this.lGOBE = lGOBE;
        }

    }





    public class ETTQ80 {

        @SerializedName("QMART")
        @Expose
        private String qMART;
        @SerializedName("AUART")
        @Expose
        private String aUART;

        public String getQMART() {
            return qMART;
        }

        public void setQMART(String qMART) {
            this.qMART = qMART;
        }

        public String getAUART() {
            return aUART;
        }

        public void setAUART(String aUART) {
            this.aUART = aUART;
        }

    }






    public class ETUDECCODE {

        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("KATALOGART")
        @Expose
        private String kATALOGART;
        @SerializedName("AUSWAHLMGE")
        @Expose
        private String aUSWAHLMGE;
        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String KURZTEXT;
        @SerializedName("CODES")
        @Expose
        private List<CODE_____> cODES = null;

        public String getKURZTEXT() {
            return KURZTEXT;
        }

        public void setKURZTEXT(String KURZTEXT) {
            this.KURZTEXT = KURZTEXT;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getKATALOGART() {
            return kATALOGART;
        }

        public void setKATALOGART(String kATALOGART) {
            this.kATALOGART = kATALOGART;
        }

        public String getAUSWAHLMGE() {
            return aUSWAHLMGE;
        }

        public void setAUSWAHLMGE(String aUSWAHLMGE) {
            this.aUSWAHLMGE = aUSWAHLMGE;
        }

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public List<CODE_____> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE_____> cODES) {
            this.cODES = cODES;
        }

    }






    public class ETUNIT {

        @SerializedName("UNIT_TYPE")
        @Expose
        private String uNITTYPE;
        @SerializedName("MEINS")
        @Expose
        private String mEINS;

        public String getUNITTYPE() {
            return uNITTYPE;
        }

        public void setUNITTYPE(String uNITTYPE) {
            this.uNITTYPE = uNITTYPE;
        }

        public String getMEINS() {
            return mEINS;
        }

        public void setMEINS(String mEINS) {
            this.mEINS = mEINS;
        }

    }






    public class ETUSER {

        @SerializedName("MUSER")
        @Expose
        private String mUSER;
        @SerializedName("FNAME")
        @Expose
        private String fNAME;
        @SerializedName("LNAME")
        @Expose
        private String lNAME;
        @SerializedName("TOKENID")
        @Expose
        private String tOKENID;

        public String getMUSER() {
            return mUSER;
        }

        public void setMUSER(String mUSER) {
            this.mUSER = mUSER;
        }

        public String getFNAME() {
            return fNAME;
        }

        public void setFNAME(String fNAME) {
            this.fNAME = fNAME;
        }

        public String getLNAME() {
            return lNAME;
        }

        public void setLNAME(String lNAME) {
            this.lNAME = lNAME;
        }

        public String getTOKENID() {
            return tOKENID;
        }

        public void setTOKENID(String tOKENID) {
            this.tOKENID = tOKENID;
        }

    }






    public class ETWKCTRPLANT {

        @SerializedName("OBJID")
        @Expose
        private String oBJID;
        @SerializedName("WERKS")
        @Expose
        private String wERKS;
        @SerializedName("NAME1")
        @Expose
        private String nAME1;
        @SerializedName("ARBPL")
        @Expose
        private String aRBPL;
        @SerializedName("KTEXT")
        @Expose
        private String kTEXT;
        @SerializedName("BUKRS")
        @Expose
        private String BUKRS;
        @SerializedName("KOKRS")
        @Expose
        private String KOKRS;
        @SerializedName("STEUS")
        @Expose
        private String STEUS;

        public String getBUKRS() {
            return BUKRS;
        }

        public void setBUKRS(String BUKRS) {
            this.BUKRS = BUKRS;
        }

        public String getKOKRS() {
            return KOKRS;
        }

        public void setKOKRS(String KOKRS) {
            this.KOKRS = KOKRS;
        }

        public String getSTEUS() {
            return STEUS;
        }

        public void setSTEUS(String STEUS) {
            this.STEUS = STEUS;
        }

        public String getOBJID() {
            return oBJID;
        }

        public void setOBJID(String oBJID) {
            this.oBJID = oBJID;
        }

        public String getWERKS() {
            return wERKS;
        }

        public void setWERKS(String wERKS) {
            this.wERKS = wERKS;
        }

        public String getNAME1() {
            return nAME1;
        }

        public void setNAME1(String nAME1) {
            this.nAME1 = nAME1;
        }

        public String getARBPL() {
            return aRBPL;
        }

        public void setARBPL(String aRBPL) {
            this.aRBPL = aRBPL;
        }

        public String getKTEXT() {
            return kTEXT;
        }

        public void setKTEXT(String kTEXT) {
            this.kTEXT = kTEXT;
        }

    }







    public class ITEMCODE {

        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String kURZTEXT;
        @SerializedName("CODES")
        @Expose
        private List<CODE> cODES = null;

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public String getKURZTEXT() {
            return kURZTEXT;
        }

        public void setKURZTEXT(String kURZTEXT) {
            this.kURZTEXT = kURZTEXT;
        }

        public List<CODE> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE> cODES) {
            this.cODES = cODES;
        }

    }






    public class OBJECTCODE {

        @SerializedName("CODEGRUPPE")
        @Expose
        private String cODEGRUPPE;
        @SerializedName("KURZTEXT")
        @Expose
        private String kURZTEXT;
        @SerializedName("CODES")
        @Expose
        private List<CODE__> cODES = null;

        public String getCODEGRUPPE() {
            return cODEGRUPPE;
        }

        public void setCODEGRUPPE(String cODEGRUPPE) {
            this.cODEGRUPPE = cODEGRUPPE;
        }

        public String getKURZTEXT() {
            return kURZTEXT;
        }

        public void setKURZTEXT(String kURZTEXT) {
            this.kURZTEXT = kURZTEXT;
        }

        public List<CODE__> getCODES() {
            return cODES;
        }

        public void setCODES(List<CODE__> cODES) {
            this.cODES = cODES;
        }

    }
}