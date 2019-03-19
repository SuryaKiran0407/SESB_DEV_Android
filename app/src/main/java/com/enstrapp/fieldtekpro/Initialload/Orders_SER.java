package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orders_SER {

    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public class D {
        @SerializedName("results")
        @Expose
        private List<Result> results = null;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public EsAufnr getEsAufnr() {
            return EsAufnr;
        }

        public void setEsAufnr(EsAufnr esAufnr) {
            EsAufnr = esAufnr;
        }

        @SerializedName("EsAufnr")
        @Expose
        private EsAufnr EsAufnr;
        @SerializedName("EtMessages")
        @Expose
        private EtMessages EtMessages;
        @SerializedName("EtOrderHeader")
        @Expose
        private EtOrderHeader EtOrderHeader;
        @SerializedName("EtOrderOperations")
        @Expose
        private EtOrderOperations EtOrderOperations;
        @SerializedName("EtOrderLongtext")
        @Expose
        private EtOrderLongtext EtOrderLongtext;
        @SerializedName("EtOrderOlist")
        @Expose
        private EtOrderOlist EtOrderOlist;
        @SerializedName("EtOrderStatus")
        @Expose
        private EtOrderStatus EtOrderStatus;
        @SerializedName("EtDocs")
        @Expose
        private EtDocs EtDocs;
        @SerializedName("EtWcmWwData")
        @Expose
        private EtWcmWwData EtWcmWwData;
        @SerializedName("EtWcmWaData")
        @Expose
        private EtWcmWaData EtWcmWaData;
        @SerializedName("EtWcmWaChkReq")
        @Expose
        private EtWcmWaChkReq EtWcmWaChkReq;
        @SerializedName("EtWcmWdData")
        @Expose
        private EtWcmWdData EtWcmWdData;
        @SerializedName("EtWcmWdItemData")
        @Expose
        private EtWcmWdItemData EtWcmWdItemData;
        @SerializedName("EtWcmWcagns")
        @Expose
        private EtWcmWcagns EtWcmWcagns;
        @SerializedName("EtOrderComponents")
        @Expose
        private EtOrderComponents EtOrderComponents;
        @SerializedName("EtImrg")
        @Expose
        private EtImrg EtImrg;
        @SerializedName("EtWcmWdDup")
        @Expose
        private EtWcmWdDup EtWcmWdDup;

        public EtWcmWdDup getEtWcmWdDup() {
            return EtWcmWdDup;
        }

        public void setEtWcmWdDup(EtWcmWdDup etWcmWdDup) {
            EtWcmWdDup = etWcmWdDup;
        }

        public EtImrg getEtImrg() {
            return EtImrg;
        }

        public void setEtImrg(EtImrg etImrg) {
            EtImrg = etImrg;
        }

        public Orders_SER.EtMessages getEtMessages() {
            return EtMessages;
        }

        public void setEtMessages(Orders_SER.EtMessages etMessages) {
            EtMessages = etMessages;
        }

        public Orders_SER.EtOrderComponents getEtOrderComponents() {
            return EtOrderComponents;
        }

        public void setEtOrderComponents(Orders_SER.EtOrderComponents etOrderComponents) {
            EtOrderComponents = etOrderComponents;
        }

        public Orders_SER.EtWcmWcagns getEtWcmWcagns() {
            return EtWcmWcagns;
        }

        public void setEtWcmWcagns(Orders_SER.EtWcmWcagns etWcmWcagns) {
            EtWcmWcagns = etWcmWcagns;
        }

        public Orders_SER.EtWcmWdItemData getEtWcmWdItemData() {
            return EtWcmWdItemData;
        }

        public void setEtWcmWdItemData(Orders_SER.EtWcmWdItemData etWcmWdItemData) {
            EtWcmWdItemData = etWcmWdItemData;
        }

        public Orders_SER.EtWcmWdData getEtWcmWdData() {
            return EtWcmWdData;
        }

        public void setEtWcmWdData(Orders_SER.EtWcmWdData etWcmWdData) {
            EtWcmWdData = etWcmWdData;
        }

        public Orders_SER.EtWcmWaChkReq getEtWcmWaChkReq() {
            return EtWcmWaChkReq;
        }

        public void setEtWcmWaChkReq(Orders_SER.EtWcmWaChkReq etWcmWaChkReq) {
            EtWcmWaChkReq = etWcmWaChkReq;
        }

        public Orders_SER.EtWcmWaData getEtWcmWaData() {
            return EtWcmWaData;
        }

        public void setEtWcmWaData(Orders_SER.EtWcmWaData etWcmWaData) {
            EtWcmWaData = etWcmWaData;
        }

        public Orders_SER.EtWcmWwData getEtWcmWwData() {
            return EtWcmWwData;
        }

        public void setEtWcmWwData(Orders_SER.EtWcmWwData etWcmWwData) {
            EtWcmWwData = etWcmWwData;
        }

        public Orders_SER.EtDocs getEtDocs() {
            return EtDocs;
        }

        public void setEtDocs(Orders_SER.EtDocs etDocs) {
            EtDocs = etDocs;
        }

        public Orders_SER.EtOrderStatus getEtOrderStatus() {
            return EtOrderStatus;
        }

        public void setEtOrderStatus(Orders_SER.EtOrderStatus etOrderStatus) {
            EtOrderStatus = etOrderStatus;
        }

        public Orders_SER.EtOrderOlist getEtOrderOlist() {
            return EtOrderOlist;
        }

        public void setEtOrderOlist(Orders_SER.EtOrderOlist etOrderOlist) {
            EtOrderOlist = etOrderOlist;
        }

        public Orders_SER.EtOrderLongtext getEtOrderLongtext() {
            return EtOrderLongtext;
        }

        public void setEtOrderLongtext(Orders_SER.EtOrderLongtext etOrderLongtext) {
            EtOrderLongtext = etOrderLongtext;
        }

        public Orders_SER.EtOrderOperations getEtOrderOperations() {
            return EtOrderOperations;
        }

        public void setEtOrderOperations(Orders_SER.EtOrderOperations etOrderOperations) {
            EtOrderOperations = etOrderOperations;
        }

        public Orders_SER.EtOrderHeader getEtOrderHeader() {
            return EtOrderHeader;
        }

        public void setEtOrderHeader(Orders_SER.EtOrderHeader etOrderHeader) {
            EtOrderHeader = etOrderHeader;
        }
    }

    public class EtMessages {
        @SerializedName("results")
        @Expose
        private List<EtMessages_Result> results = null;

        public List<EtMessages_Result> getResults() {
            return results;
        }

        public void setResults(List<EtMessages_Result> results) {
            this.results = results;
        }
    }

    public class EtMessages_Result {
        @SerializedName("Message")
        @Expose
        private String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }

    public class EsAufnr {
        @SerializedName("results")
        @Expose
        private List<EsAufnr_Result> results = null;

        public List<EsAufnr_Result> getResults() {
            return results;
        }

        public void setResults(List<EsAufnr_Result> results) {
            this.results = results;
        }
    }

    public class EsAufnr_Result {
        @SerializedName("Aufnr")
        @Expose
        private String Aufnr;
        @SerializedName("Message")
        @Expose
        private String Message;

        public String getAufnr() {
            return Aufnr;
        }

        public void setAufnr(String aufnr) {
            Aufnr = aufnr;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }

    public class EvMessageRe {
        @SerializedName("results")
        @Expose
        private List<EvMessageRe_Result> results = null;

        public List<EvMessageRe_Result> getResults() {
            return results;
        }

        public void setResults(List<EvMessageRe_Result> results) {
            this.results = results;
        }
    }

    public class EvMessageRe_Result {
        @SerializedName("Message")
        @Expose
        private String Message;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }
    }

    public class Result {
        @SerializedName("EvMessageRe")
        @Expose
        private EvMessageRe EvMessageRe;
        @SerializedName("EtOrderHeader")
        @Expose
        private EtOrderHeader EtOrderHeader;
        @SerializedName("EtOrderOperations")
        @Expose
        private EtOrderOperations EtOrderOperations;
        @SerializedName("EtOrderLongtext")
        @Expose
        private EtOrderLongtext EtOrderLongtext;
        @SerializedName("EtOrderOlist")
        @Expose
        private EtOrderOlist EtOrderOlist;
        @SerializedName("EtOrderStatus")
        @Expose
        private EtOrderStatus EtOrderStatus;
        @SerializedName("EtDocs")
        @Expose
        private EtDocs EtDocs;
        @SerializedName("EtWcmWwData")
        @Expose
        private EtWcmWwData EtWcmWwData;
        @SerializedName("EtWcmWaData")
        @Expose
        private EtWcmWaData EtWcmWaData;
        @SerializedName("EtWcmWaChkReq")
        @Expose
        private EtWcmWaChkReq EtWcmWaChkReq;
        @SerializedName("EtWcmWdData")
        @Expose
        private EtWcmWdData EtWcmWdData;
        @SerializedName("EtWcmWdItemData")
        @Expose
        private EtWcmWdItemData EtWcmWdItemData;
        @SerializedName("EtWcmWcagns")
        @Expose
        private EtWcmWcagns EtWcmWcagns;
        @SerializedName("EtOrderComponents")
        @Expose
        private EtOrderComponents EtOrderComponents;
        @SerializedName("EtImrg")
        @Expose
        private EtImrg EtImrg;

        public EtImrg getEtImrg() {
            return EtImrg;
        }

        public void setEtImrg(EtImrg etImrg) {
            EtImrg = etImrg;
        }

        public Orders_SER.EvMessageRe getEvMessageRe() {
            return EvMessageRe;
        }

        public void setEvMessageRe(Orders_SER.EvMessageRe evMessageRe) {
            EvMessageRe = evMessageRe;
        }

        public Orders_SER.EtOrderComponents getEtOrderComponents() {
            return EtOrderComponents;
        }

        public void setEtOrderComponents(Orders_SER.EtOrderComponents etOrderComponents) {
            EtOrderComponents = etOrderComponents;
        }

        public Orders_SER.EtWcmWcagns getEtWcmWcagns() {
            return EtWcmWcagns;
        }

        public void setEtWcmWcagns(Orders_SER.EtWcmWcagns etWcmWcagns) {
            EtWcmWcagns = etWcmWcagns;
        }

        public Orders_SER.EtWcmWdItemData getEtWcmWdItemData() {
            return EtWcmWdItemData;
        }

        public void setEtWcmWdItemData(Orders_SER.EtWcmWdItemData etWcmWdItemData) {
            EtWcmWdItemData = etWcmWdItemData;
        }

        public Orders_SER.EtWcmWdData getEtWcmWdData() {
            return EtWcmWdData;
        }

        public void setEtWcmWdData(Orders_SER.EtWcmWdData etWcmWdData) {
            EtWcmWdData = etWcmWdData;
        }

        public Orders_SER.EtWcmWaChkReq getEtWcmWaChkReq() {
            return EtWcmWaChkReq;
        }

        public void setEtWcmWaChkReq(Orders_SER.EtWcmWaChkReq etWcmWaChkReq) {
            EtWcmWaChkReq = etWcmWaChkReq;
        }

        public Orders_SER.EtWcmWaData getEtWcmWaData() {
            return EtWcmWaData;
        }

        public void setEtWcmWaData(Orders_SER.EtWcmWaData etWcmWaData) {
            EtWcmWaData = etWcmWaData;
        }

        public Orders_SER.EtWcmWwData getEtWcmWwData() {
            return EtWcmWwData;
        }

        public void setEtWcmWwData(Orders_SER.EtWcmWwData etWcmWwData) {
            EtWcmWwData = etWcmWwData;
        }

        public Orders_SER.EtDocs getEtDocs() {
            return EtDocs;
        }

        public void setEtDocs(Orders_SER.EtDocs etDocs) {
            EtDocs = etDocs;
        }

        public Orders_SER.EtOrderStatus getEtOrderStatus() {
            return EtOrderStatus;
        }

        public void setEtOrderStatus(Orders_SER.EtOrderStatus etOrderStatus) {
            EtOrderStatus = etOrderStatus;
        }

        public Orders_SER.EtOrderOlist getEtOrderOlist() {
            return EtOrderOlist;
        }

        public void setEtOrderOlist(Orders_SER.EtOrderOlist etOrderOlist) {
            EtOrderOlist = etOrderOlist;
        }

        public Orders_SER.EtOrderLongtext getEtOrderLongtext() {
            return EtOrderLongtext;
        }

        public void setEtOrderLongtext(Orders_SER.EtOrderLongtext etOrderLongtext) {
            EtOrderLongtext = etOrderLongtext;
        }

        public Orders_SER.EtOrderOperations getEtOrderOperations() {
            return EtOrderOperations;
        }

        public void setEtOrderOperations(Orders_SER.EtOrderOperations etOrderOperations) {
            EtOrderOperations = etOrderOperations;
        }

        public Orders_SER.EtOrderHeader getEtOrderHeader() {
            return EtOrderHeader;
        }

        public void setEtOrderHeader(Orders_SER.EtOrderHeader etOrderHeader) {
            EtOrderHeader = etOrderHeader;
        }
    }


    /*For Parsing EtOrderComponents*/
    public class EtOrderComponents {
        @SerializedName("results")
        @Expose
        private List<EtOrderComponents_Result> results = null;

        public List<EtOrderComponents_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderComponents_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderComponents_Result {
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

        public String getBdmng() {
            return bdmng;
        }

        public void setBdmng(String bdmng) {
            this.bdmng = bdmng;
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
        @SerializedName("EtOrderComponentsFields")
        @Expose
        private EtOrderHeaderFields etOrderComponentsFields;

        public EtOrderHeaderFields getEtOrderComponentsFields() {
            return etOrderComponentsFields;
        }

        public void setEtOrderComponentsFields(EtOrderHeaderFields etOrderComponentsFields) {
            this.etOrderComponentsFields = etOrderComponentsFields;
        }
    }
    /*For Parsing EtOrderComponents*/

    /*For Parsing EtWcmWdDup*/
    public class EtWcmWdDup {
        @SerializedName("results")
        @Expose
        private List<EtWcmWdDup_Result> results = null;

        public List<EtWcmWdDup_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWdDup_Result> results) {
            this.results = results;
        }
    }

    /*For Parsing EtImrg*/
    public class EtImrg {
        @SerializedName("results")
        @Expose
        private List<EtImrg_Result> results = null;

        public List<EtImrg_Result> getResults() {
            return results;
        }

        public void setResults(List<EtImrg_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWdDup_Result {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Wcnr")
        @Expose
        private String wcnr;
        @SerializedName("Stxt")
        @Expose
        private String stxt;
        @SerializedName("Sysst")
        @Expose
        private String sysst;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getWcnr() {
            return wcnr;
        }

        public void setWcnr(String wcnr) {
            this.wcnr = wcnr;
        }

        public String getStxt() {
            return stxt;
        }

        public void setStxt(String stxt) {
            this.stxt = stxt;
        }

        public String getSysst() {
            return sysst;
        }

        public void setSysst(String sysst) {
            this.sysst = sysst;
        }
    }

    public class EtImrg_Result {
        @SerializedName("Qmnum")
        @Expose
        private String qmnum;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Vornr")
        @Expose
        private String vornr;
        @SerializedName("Mdocm")
        @Expose
        private String mdocm;
        @SerializedName("Point")
        @Expose
        private String point;
        @SerializedName("Mpobj")
        @Expose
        private String mpobj;
        @SerializedName("Mpobt")
        @Expose
        private String mpobt;
        @SerializedName("Psort")
        @Expose
        private String psort;
        @SerializedName("Pttxt")
        @Expose
        private String pttxt;
        @SerializedName("Atinn")
        @Expose
        private String atinn;
        @SerializedName("Idate")
        @Expose
        private String idate;
        @SerializedName("Itime")
        @Expose
        private String itime;
        @SerializedName("Mdtxt")
        @Expose
        private String mdtxt;
        @SerializedName("Readr")
        @Expose
        private String readr;
        @SerializedName("Atbez")
        @Expose
        private String atbez;
        @SerializedName("Msehi")
        @Expose
        private String msehi;
        @SerializedName("Msehl")
        @Expose
        private String msehl;
        @SerializedName("Readc")
        @Expose
        private String readc;
        @SerializedName("Desic")
        @Expose
        private String desic;
        @SerializedName("Prest")
        @Expose
        private String prest;
        @SerializedName("Docaf")
        @Expose
        private String docaf;
        @SerializedName("Codct")
        @Expose
        private String codct;
        @SerializedName("Codgr")
        @Expose
        private String codgr;
        @SerializedName("Vlcod")
        @Expose
        private String vlcod;
        @SerializedName("Action")
        @Expose
        private String action;
        @SerializedName("Equnr")
        @Expose
        private String equnr;

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
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

        public String getMdocm() {
            return mdocm;
        }

        public void setMdocm(String mdocm) {
            this.mdocm = mdocm;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getMpobj() {
            return mpobj;
        }

        public void setMpobj(String mpobj) {
            this.mpobj = mpobj;
        }

        public String getMpobt() {
            return mpobt;
        }

        public void setMpobt(String mpobt) {
            this.mpobt = mpobt;
        }

        public String getPsort() {
            return psort;
        }

        public void setPsort(String psort) {
            this.psort = psort;
        }

        public String getPttxt() {
            return pttxt;
        }

        public void setPttxt(String pttxt) {
            this.pttxt = pttxt;
        }

        public String getAtinn() {
            return atinn;
        }

        public void setAtinn(String atinn) {
            this.atinn = atinn;
        }

        public String getIdate() {
            return idate;
        }

        public void setIdate(String idate) {
            this.idate = idate;
        }

        public String getItime() {
            return itime;
        }

        public void setItime(String itime) {
            this.itime = itime;
        }

        public String getMdtxt() {
            return mdtxt;
        }

        public void setMdtxt(String mdtxt) {
            this.mdtxt = mdtxt;
        }

        public String getReadr() {
            return readr;
        }

        public void setReadr(String readr) {
            this.readr = readr;
        }

        public String getAtbez() {
            return atbez;
        }

        public void setAtbez(String atbez) {
            this.atbez = atbez;
        }

        public String getMsehi() {
            return msehi;
        }

        public void setMsehi(String msehi) {
            this.msehi = msehi;
        }

        public String getMsehl() {
            return msehl;
        }

        public void setMsehl(String msehl) {
            this.msehl = msehl;
        }

        public String getReadc() {
            return readc;
        }

        public void setReadc(String readc) {
            this.readc = readc;
        }

        public String getDesic() {
            return desic;
        }

        public void setDesic(String desic) {
            this.desic = desic;
        }

        public String getPrest() {
            return prest;
        }

        public void setPrest(String prest) {
            this.prest = prest;
        }

        public String getDocaf() {
            return docaf;
        }

        public void setDocaf(String docaf) {
            this.docaf = docaf;
        }

        public String getCodct() {
            return codct;
        }

        public void setCodct(String codct) {
            this.codct = codct;
        }

        public String getCodgr() {
            return codgr;
        }

        public void setCodgr(String codgr) {
            this.codgr = codgr;
        }

        public String getVlcod() {
            return vlcod;
        }

        public void setVlcod(String vlcod) {
            this.vlcod = vlcod;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }
    }
    /*For Parsing EtImrg*/


    /*For Parsing EtWcmWcagns*/
    public class EtWcmWcagns {
        @SerializedName("results")
        @Expose
        private List<EtWcmWcagns_Result> results = null;

        public List<EtWcmWcagns_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWcagns_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWcagns_Result {
        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getCounter() {
            return counter;
        }

        public void setCounter(String counter) {
            this.counter = counter;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getCrname() {
            return crname;
        }

        public void setCrname(String crname) {
            this.crname = crname;
        }

        public String getObjart() {
            return objart;
        }

        public void setObjart(String objart) {
            this.objart = objart;
        }

        public String getObjtyp() {
            return objtyp;
        }

        public void setObjtyp(String objtyp) {
            this.objtyp = objtyp;
        }

        public String getPmsog() {
            return pmsog;
        }

        public void setPmsog(String pmsog) {
            this.pmsog = pmsog;
        }

        public String getGntxt() {
            return gntxt;
        }

        public void setGntxt(String gntxt) {
            this.gntxt = gntxt;
        }

        public String getGeniakt() {
            return geniakt;
        }

        public void setGeniakt(String geniakt) {
            this.geniakt = geniakt;
        }

        public String getGenvname() {
            return genvname;
        }

        public void setGenvname(String genvname) {
            this.genvname = genvname;
        }

        public String getGendatum() {
            return gendatum;
        }

        public void setGendatum(String gendatum) {
            this.gendatum = gendatum;
        }

        public String getGentime() {
            return gentime;
        }

        public void setGentime(String gentime) {
            this.gentime = gentime;
        }

        public Integer getHilvl() {
            return hilvl;
        }

        public void setHilvl(Integer hilvl) {
            this.hilvl = hilvl;
        }

        public String getProcflg() {
            return procflg;
        }

        public void setProcflg(String procflg) {
            this.procflg = procflg;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getCopyflg() {
            return copyflg;
        }

        public void setCopyflg(String copyflg) {
            this.copyflg = copyflg;
        }

        public String getMandflg() {
            return mandflg;
        }

        public void setMandflg(String mandflg) {
            this.mandflg = mandflg;
        }

        public String getDeacflg() {
            return deacflg;
        }

        public void setDeacflg(String deacflg) {
            this.deacflg = deacflg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAsgnflg() {
            return asgnflg;
        }

        public void setAsgnflg(String asgnflg) {
            this.asgnflg = asgnflg;
        }

        public String getAutoflg() {
            return autoflg;
        }

        public void setAutoflg(String autoflg) {
            this.autoflg = autoflg;
        }

        public String getAgent() {
            return agent;
        }

        public void setAgent(String agent) {
            this.agent = agent;
        }

        public String getValflg() {
            return valflg;
        }

        public void setValflg(String valflg) {
            this.valflg = valflg;
        }

        public String getWcmuse() {
            return wcmuse;
        }

        public void setWcmuse(String wcmuse) {
            this.wcmuse = wcmuse;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Objnr")
        @Expose
        private String objnr;
        @SerializedName("Counter")
        @Expose
        private String counter;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Crname")
        @Expose
        private String crname;
        @SerializedName("Objart")
        @Expose
        private String objart;
        @SerializedName("Objtyp")
        @Expose
        private String objtyp;
        @SerializedName("Pmsog")
        @Expose
        private String pmsog;
        @SerializedName("Gntxt")
        @Expose
        private String gntxt;
        @SerializedName("Geniakt")
        @Expose
        private String geniakt;
        @SerializedName("Genvname")
        @Expose
        private String genvname;
        @SerializedName("Gendatum")
        @Expose
        private String gendatum;
        @SerializedName("Gentime")
        @Expose
        private String gentime;
        @SerializedName("Hilvl")
        @Expose
        private Integer hilvl;
        @SerializedName("Procflg")
        @Expose
        private String procflg;
        @SerializedName("Direction")
        @Expose
        private String direction;
        @SerializedName("Copyflg")
        @Expose
        private String copyflg;
        @SerializedName("Mandflg")
        @Expose
        private String mandflg;
        @SerializedName("Deacflg")
        @Expose
        private String deacflg;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Asgnflg")
        @Expose
        private String asgnflg;
        @SerializedName("Autoflg")
        @Expose
        private String autoflg;
        @SerializedName("Agent")
        @Expose
        private String agent;
        @SerializedName("Valflg")
        @Expose
        private String valflg;
        @SerializedName("Wcmuse")
        @Expose
        private String wcmuse;
        @SerializedName("Action")
        @Expose
        private String action;
    }
    /*For Parsing EtWcmWcagns*/


    /*For Parsing EtWcmWdItemData*/
    public class EtWcmWdItemData {
        @SerializedName("results")
        @Expose
        private List<EtWcmWdItemData_Result> results = null;

        public List<EtWcmWdItemData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWdItemData_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWdItemData_Result {
        public String getWcnr() {
            return wcnr;
        }

        public void setWcnr(String wcnr) {
            this.wcnr = wcnr;
        }

        public String getWcitm() {
            return wcitm;
        }

        public void setWcitm(String wcitm) {
            this.wcitm = wcitm;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getItmtyp() {
            return itmtyp;
        }

        public void setItmtyp(String itmtyp) {
            this.itmtyp = itmtyp;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getPred() {
            return pred;
        }

        public void setPred(String pred) {
            this.pred = pred;
        }

        public String getSucc() {
            return succ;
        }

        public void setSucc(String succ) {
            this.succ = succ;
        }

        public String getCcobj() {
            return ccobj;
        }

        public void setCcobj(String ccobj) {
            this.ccobj = ccobj;
        }

        public String getCctyp() {
            return cctyp;
        }

        public void setCctyp(String cctyp) {
            this.cctyp = cctyp;
        }

        public String getStxt() {
            return stxt;
        }

        public void setStxt(String stxt) {
            this.stxt = stxt;
        }

        public String getTggrp() {
            return tggrp;
        }

        public void setTggrp(String tggrp) {
            this.tggrp = tggrp;
        }

        public String getTgstep() {
            return tgstep;
        }

        public void setTgstep(String tgstep) {
            this.tgstep = tgstep;
        }

        public String getTgproc() {
            return tgproc;
        }

        public void setTgproc(String tgproc) {
            this.tgproc = tgproc;
        }

        public String getTgtyp() {
            return tgtyp;
        }

        public void setTgtyp(String tgtyp) {
            this.tgtyp = tgtyp;
        }

        public String getTgseq() {
            return tgseq;
        }

        public void setTgseq(String tgseq) {
            this.tgseq = tgseq;
        }

        public String getTgtxt() {
            return tgtxt;
        }

        public void setTgtxt(String tgtxt) {
            this.tgtxt = tgtxt;
        }

        public String getUnstep() {
            return unstep;
        }

        public void setUnstep(String unstep) {
            this.unstep = unstep;
        }

        public String getUnproc() {
            return unproc;
        }

        public void setUnproc(String unproc) {
            this.unproc = unproc;
        }

        public String getUntyp() {
            return untyp;
        }

        public void setUntyp(String untyp) {
            this.untyp = untyp;
        }

        public String getUnseq() {
            return unseq;
        }

        public void setUnseq(String unseq) {
            this.unseq = unseq;
        }

        public String getUntxt() {
            return untxt;
        }

        public void setUntxt(String untxt) {
            this.untxt = untxt;
        }

        public String getPhblflg() {
            return phblflg;
        }

        public void setPhblflg(String phblflg) {
            this.phblflg = phblflg;
        }

        public String getPhbltyp() {
            return phbltyp;
        }

        public void setPhbltyp(String phbltyp) {
            this.phbltyp = phbltyp;
        }

        public String getPhblnr() {
            return phblnr;
        }

        public void setPhblnr(String phblnr) {
            this.phblnr = phblnr;
        }

        public String getTgflg() {
            return tgflg;
        }

        public void setTgflg(String tgflg) {
            this.tgflg = tgflg;
        }

        public String getTgform() {
            return tgform;
        }

        public void setTgform(String tgform) {
            this.tgform = tgform;
        }

        public String getTgnr() {
            return tgnr;
        }

        public void setTgnr(String tgnr) {
            this.tgnr = tgnr;
        }

        public String getUnform() {
            return unform;
        }

        public void setUnform(String unform) {
            this.unform = unform;
        }

        public String getUnnr() {
            return unnr;
        }

        public void setUnnr(String unnr) {
            this.unnr = unnr;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBtg() {
            return btg;
        }

        public void setBtg(String btg) {
            this.btg = btg;
        }

        public String getEtg() {
            return etg;
        }

        public void setEtg(String etg) {
            this.etg = etg;
        }

        public String getBug() {
            return bug;
        }

        public void setBug(String bug) {
            this.bug = bug;
        }

        public String getEug() {
            return eug;
        }

        public void setEug(String eug) {
            this.eug = eug;
        }

        public String getRefobj() {
            return refobj;
        }

        public void setRefobj(String refobj) {
            this.refobj = refobj;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @SerializedName("Wcnr")
        @Expose
        private String wcnr;
        @SerializedName("Wcitm")
        @Expose
        private String wcitm;
        @SerializedName("Objnr")
        @Expose
        private String objnr;
        @SerializedName("Itmtyp")
        @Expose
        private String itmtyp;
        @SerializedName("Seq")
        @Expose
        private String seq;
        @SerializedName("Pred")
        @Expose
        private String pred;
        @SerializedName("Succ")
        @Expose
        private String succ;
        @SerializedName("Ccobj")
        @Expose
        private String ccobj;
        @SerializedName("Cctyp")
        @Expose
        private String cctyp;
        @SerializedName("Stxt")
        @Expose
        private String stxt;
        @SerializedName("Tggrp")
        @Expose
        private String tggrp;
        @SerializedName("Tgstep")
        @Expose
        private String tgstep;
        @SerializedName("Tgproc")
        @Expose
        private String tgproc;
        @SerializedName("Tgtyp")
        @Expose
        private String tgtyp;
        @SerializedName("Tgseq")
        @Expose
        private String tgseq;
        @SerializedName("Tgtxt")
        @Expose
        private String tgtxt;
        @SerializedName("Unstep")
        @Expose
        private String unstep;
        @SerializedName("Unproc")
        @Expose
        private String unproc;
        @SerializedName("Untyp")
        @Expose
        private String untyp;
        @SerializedName("Unseq")
        @Expose
        private String unseq;
        @SerializedName("Untxt")
        @Expose
        private String untxt;
        @SerializedName("Phblflg")
        @Expose
        private String phblflg;
        @SerializedName("Phbltyp")
        @Expose
        private String phbltyp;
        @SerializedName("Phblnr")
        @Expose
        private String phblnr;
        @SerializedName("Tgflg")
        @Expose
        private String tgflg;
        @SerializedName("Tgform")
        @Expose
        private String tgform;
        @SerializedName("Tgnr")
        @Expose
        private String tgnr;
        @SerializedName("Unform")
        @Expose
        private String unform;
        @SerializedName("Unnr")
        @Expose
        private String unnr;
        @SerializedName("Control")
        @Expose
        private String control;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("Btg")
        @Expose
        private String btg;
        @SerializedName("Etg")
        @Expose
        private String etg;
        @SerializedName("Bug")
        @Expose
        private String bug;
        @SerializedName("Eug")
        @Expose
        private String eug;
        @SerializedName("Refobj")
        @Expose
        private String refobj;
        @SerializedName("Action")
        @Expose
        private String action;
    }
    /*For Parsing EtWcmWdItemData*/


    /*For Parsing EtWcmWdData*/
    public class EtWcmWdData {
        @SerializedName("results")
        @Expose
        private List<EtWcmWdData_Result> results = null;

        public List<EtWcmWdData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWdData_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWdData_Result {
        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Objart")
        @Expose
        public String objart;
        @SerializedName("Wcnr")
        @Expose
        public String Wcnr;
        @SerializedName("Iwerk")
        @Expose
        public String iwerk;
        @SerializedName("Objtyp")
        @Expose
        public String objtyp;
        @SerializedName("Wapinr")
        @Expose
        public String wapinr;
        @SerializedName("Usage")
        @Expose
        public String usage;
        @SerializedName("Usagex")
        @Expose
        public String usagex;
        @SerializedName("Train")
        @Expose
        public String train;
        @SerializedName("Trainx")
        @Expose
        public String trainx;
        @SerializedName("Anlzu")
        @Expose
        public String anlzu;
        @SerializedName("Anlzux")
        @Expose
        public String anlzux;
        @SerializedName("Etape")
        @Expose
        public String etape;
        @SerializedName("Etapex")
        @Expose
        public String etapex;
        @SerializedName("Stxt")
        @Expose
        public String stxt;
        @SerializedName("Datefr")
        @Expose
        public String datefr;
        @SerializedName("Timefr")
        @Expose
        public String timefr;
        @SerializedName("Dateto")
        @Expose
        public String dateto;
        @SerializedName("Timeto")
        @Expose
        public String timeto;
        @SerializedName("Begru")
        @Expose
        public String begru;
        @SerializedName("Begtx")
        @Expose
        public String begtx;
        @SerializedName("Priok")
        @Expose
        public String priok;
        @SerializedName("Priokx")
        @Expose
        public String priokx;
        @SerializedName("Rctime")
        @Expose
        public String rctime;
        @SerializedName("Rcunit")
        @Expose
        public String rcunit;
        @SerializedName("Objnr")
        @Expose
        public String objnr;
        @SerializedName("Refobj")
        @Expose
        public String refobj;
        @SerializedName("Crea")
        @Expose
        public String crea;
        @SerializedName("Prep")
        @Expose
        public String prep;
        @SerializedName("Comp")
        @Expose
        public String comp;
        @SerializedName("Appr")
        @Expose
        public String appr;
        @SerializedName("Pappr")
        @Expose
        public String pappr;
        @SerializedName("Action")
        @Expose
        public String action;
        @SerializedName("EtWcmWdDataTagtext")
        @Expose
        public EtWcmWdDataTagtext EtWcmWdDataTagtext;
        @SerializedName("EtWcmWdDataUntagtext")
        @Expose
        public EtWcmWdDataUntagtext EtWcmWdDataUntagtext;

        public Orders_SER.EtWcmWdDataTagtext getEtWcmWdDataTagtext() {
            return EtWcmWdDataTagtext;
        }

        public void setEtWcmWdDataTagtext(Orders_SER.EtWcmWdDataTagtext etWcmWdDataTagtext) {
            EtWcmWdDataTagtext = etWcmWdDataTagtext;
        }

        public Orders_SER.EtWcmWdDataUntagtext getEtWcmWdDataUntagtext() {
            return EtWcmWdDataUntagtext;
        }

        public void setEtWcmWdDataUntagtext(Orders_SER.EtWcmWdDataUntagtext etWcmWdDataUntagtext) {
            EtWcmWdDataUntagtext = etWcmWdDataUntagtext;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObjart() {
            return objart;
        }

        public void setObjart(String objart) {
            this.objart = objart;
        }

        public String getWcnr() {
            return Wcnr;
        }

        public void setWcnr(String wcnr) {
            Wcnr = wcnr;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getObjtyp() {
            return objtyp;
        }

        public void setObjtyp(String objtyp) {
            this.objtyp = objtyp;
        }

        public String getWapinr() {
            return wapinr;
        }

        public void setWapinr(String wapinr) {
            this.wapinr = wapinr;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public String getUsagex() {
            return usagex;
        }

        public void setUsagex(String usagex) {
            this.usagex = usagex;
        }

        public String getTrain() {
            return train;
        }

        public void setTrain(String train) {
            this.train = train;
        }

        public String getTrainx() {
            return trainx;
        }

        public void setTrainx(String trainx) {
            this.trainx = trainx;
        }

        public String getAnlzu() {
            return anlzu;
        }

        public void setAnlzu(String anlzu) {
            this.anlzu = anlzu;
        }

        public String getAnlzux() {
            return anlzux;
        }

        public void setAnlzux(String anlzux) {
            this.anlzux = anlzux;
        }

        public String getEtape() {
            return etape;
        }

        public void setEtape(String etape) {
            this.etape = etape;
        }

        public String getEtapex() {
            return etapex;
        }

        public void setEtapex(String etapex) {
            this.etapex = etapex;
        }

        public String getStxt() {
            return stxt;
        }

        public void setStxt(String stxt) {
            this.stxt = stxt;
        }

        public String getDatefr() {
            return datefr;
        }

        public void setDatefr(String datefr) {
            this.datefr = datefr;
        }

        public String getTimefr() {
            return timefr;
        }

        public void setTimefr(String timefr) {
            this.timefr = timefr;
        }

        public String getDateto() {
            return dateto;
        }

        public void setDateto(String dateto) {
            this.dateto = dateto;
        }

        public String getTimeto() {
            return timeto;
        }

        public void setTimeto(String timeto) {
            this.timeto = timeto;
        }

        public String getBegru() {
            return begru;
        }

        public void setBegru(String begru) {
            this.begru = begru;
        }

        public String getBegtx() {
            return begtx;
        }

        public void setBegtx(String begtx) {
            this.begtx = begtx;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getPriokx() {
            return priokx;
        }

        public void setPriokx(String priokx) {
            this.priokx = priokx;
        }

        public String getRctime() {
            return rctime;
        }

        public void setRctime(String rctime) {
            this.rctime = rctime;
        }

        public String getRcunit() {
            return rcunit;
        }

        public void setRcunit(String rcunit) {
            this.rcunit = rcunit;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getRefobj() {
            return refobj;
        }

        public void setRefobj(String refobj) {
            this.refobj = refobj;
        }

        public String getCrea() {
            return crea;
        }

        public void setCrea(String crea) {
            this.crea = crea;
        }

        public String getPrep() {
            return prep;
        }

        public void setPrep(String prep) {
            this.prep = prep;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getAppr() {
            return appr;
        }

        public void setAppr(String appr) {
            this.appr = appr;
        }

        public String getPappr() {
            return pappr;
        }

        public void setPappr(String pappr) {
            this.pappr = pappr;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For Parsing EtWcmWdData*/

    public class EtWcmWdDataTagtext {
        @SerializedName("results")
        @Expose
        private List<EtWcmWdDataTagtext_Result> results = null;

        public List<EtWcmWdDataTagtext_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWdDataTagtext_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWdDataTagtext_Result {
        @SerializedName("Aufnr")
        @Expose
        public String Aufnr;
        @SerializedName("Wcnr")
        @Expose
        public String Wcnr;
        @SerializedName("Objtype")
        @Expose
        public String Objtype;
        @SerializedName("FormatCol")
        @Expose
        public String FormatCol;
        @SerializedName("TextLine")
        @Expose
        public String TextLine;
        @SerializedName("Action")
        @Expose
        public String Action;

        public String getAufnr() {
            return Aufnr;
        }

        public void setAufnr(String aufnr) {
            Aufnr = aufnr;
        }

        public String getWcnr() {
            return Wcnr;
        }

        public void setWcnr(String wcnr) {
            Wcnr = wcnr;
        }

        public String getObjtype() {
            return Objtype;
        }

        public void setObjtype(String objtype) {
            Objtype = objtype;
        }

        public String getFormatCol() {
            return FormatCol;
        }

        public void setFormatCol(String formatCol) {
            FormatCol = formatCol;
        }

        public String getTextLine() {
            return TextLine;
        }

        public void setTextLine(String textLine) {
            TextLine = textLine;
        }

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }
    }

    public class EtWcmWdDataUntagtext {
        @SerializedName("results")
        @Expose
        private List<EtWcmWdDataTagtext_Result> results = null;

        public List<EtWcmWdDataTagtext_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWdDataTagtext_Result> results) {
            this.results = results;
        }
    }


    /*For Parsing EtWcmWaChkReq*/
    public class EtWcmWaChkReq {
        @SerializedName("results")
        @Expose
        private List<EtWcmWaChkReq_Result> results = null;

        public List<EtWcmWaChkReq_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWaChkReq_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWaChkReq_Result {
        @SerializedName("Wapinr")
        @Expose
        public String Wapinr;
        @SerializedName("Wapityp")
        @Expose
        public String Wapityp;
        @SerializedName("ChkPointType")
        @Expose
        public String ChkPointType;
        @SerializedName("Wkid")
        @Expose
        public String Wkid;
        @SerializedName("Needid")
        @Expose
        public String Needid;
        @SerializedName("Value")
        @Expose
        public String Value;
        @SerializedName("Desctext")
        @Expose
        public String Desctext;
        @SerializedName("Wkgrp")
        @Expose
        public String Wkgrp;
        @SerializedName("Needgrp")
        @Expose
        public String Needgrp;
        @SerializedName("Tplnr")
        @Expose
        public String Tplnr;
        @SerializedName("Equnr")
        @Expose
        public String Equnr;

        public String getWapinr() {
            return Wapinr;
        }

        public void setWapinr(String wapinr) {
            Wapinr = wapinr;
        }

        public String getWapityp() {
            return Wapityp;
        }

        public void setWapityp(String wapityp) {
            Wapityp = wapityp;
        }

        public String getChkPointType() {
            return ChkPointType;
        }

        public void setChkPointType(String chkPointType) {
            ChkPointType = chkPointType;
        }

        public String getWkid() {
            return Wkid;
        }

        public void setWkid(String wkid) {
            Wkid = wkid;
        }

        public String getNeedid() {
            return Needid;
        }

        public void setNeedid(String needid) {
            Needid = needid;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public String getDesctext() {
            return Desctext;
        }

        public void setDesctext(String desctext) {
            Desctext = desctext;
        }

        public String getWkgrp() {
            return Wkgrp;
        }

        public void setWkgrp(String wkgrp) {
            Wkgrp = wkgrp;
        }

        public String getNeedgrp() {
            return Needgrp;
        }

        public void setNeedgrp(String needgrp) {
            Needgrp = needgrp;
        }

        public String getTplnr() {
            return Tplnr;
        }

        public void setTplnr(String tplnr) {
            Tplnr = tplnr;
        }

        public String getEqunr() {
            return Equnr;
        }

        public void setEqunr(String equnr) {
            Equnr = equnr;
        }
    }
    /*For Parsing EtWcmWaChkReq*/


    /*For Parsing EtWcmWaData*/
    public class EtWcmWaData {
        @SerializedName("results")
        @Expose
        private List<EtWcmWaData_Result> results = null;

        public List<EtWcmWaData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWaData_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWaData_Result {
        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObjart() {
            return objart;
        }

        public void setObjart(String objart) {
            this.objart = objart;
        }

        public String getObjtyp() {
            return objtyp;
        }

        public void setObjtyp(String objtyp) {
            this.objtyp = objtyp;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getWapinr() {
            return wapinr;
        }

        public void setWapinr(String wapinr) {
            this.wapinr = wapinr;
        }

        public String getStxt() {
            return stxt;
        }

        public void setStxt(String stxt) {
            this.stxt = stxt;
        }

        public String getDatefr() {
            return datefr;
        }

        public void setDatefr(String datefr) {
            this.datefr = datefr;
        }

        public String getTimefr() {
            return timefr;
        }

        public void setTimefr(String timefr) {
            this.timefr = timefr;
        }

        public String getDateto() {
            return dateto;
        }

        public void setDateto(String dateto) {
            this.dateto = dateto;
        }

        public String getTimeto() {
            return timeto;
        }

        public void setTimeto(String timeto) {
            this.timeto = timeto;
        }

        public Integer getExtperiod() {
            return extperiod;
        }

        public void setExtperiod(Integer extperiod) {
            this.extperiod = extperiod;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public String getUsagex() {
            return usagex;
        }

        public void setUsagex(String usagex) {
            this.usagex = usagex;
        }

        public String getTrain() {
            return train;
        }

        public void setTrain(String train) {
            this.train = train;
        }

        public String getTrainx() {
            return trainx;
        }

        public void setTrainx(String trainx) {
            this.trainx = trainx;
        }

        public String getAnlzu() {
            return anlzu;
        }

        public void setAnlzu(String anlzu) {
            this.anlzu = anlzu;
        }

        public String getAnlzux() {
            return anlzux;
        }

        public void setAnlzux(String anlzux) {
            this.anlzux = anlzux;
        }

        public String getEtape() {
            return etape;
        }

        public void setEtape(String etape) {
            this.etape = etape;
        }

        public String getEtapex() {
            return etapex;
        }

        public void setEtapex(String etapex) {
            this.etapex = etapex;
        }

        public String getBegru() {
            return begru;
        }

        public void setBegru(String begru) {
            this.begru = begru;
        }

        public String getBegtx() {
            return begtx;
        }

        public void setBegtx(String begtx) {
            this.begtx = begtx;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getPriokx() {
            return priokx;
        }

        public void setPriokx(String priokx) {
            this.priokx = priokx;
        }

        public String getRctime() {
            return rctime;
        }

        public void setRctime(String rctime) {
            this.rctime = rctime;
        }

        public String getRcunit() {
            return rcunit;
        }

        public void setRcunit(String rcunit) {
            this.rcunit = rcunit;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getRefobj() {
            return refobj;
        }

        public void setRefobj(String refobj) {
            this.refobj = refobj;
        }

        public String getCrea() {
            return crea;
        }

        public void setCrea(String crea) {
            this.crea = crea;
        }

        public String getPrep() {
            return prep;
        }

        public void setPrep(String prep) {
            this.prep = prep;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getAppr() {
            return appr;
        }

        public void setAppr(String appr) {
            this.appr = appr;
        }

        public String getPappr() {
            return pappr;
        }

        public void setPappr(String pappr) {
            this.pappr = pappr;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
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

        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Objart")
        @Expose
        public String objart;
        @SerializedName("Objtyp")
        @Expose
        public String objtyp;
        @SerializedName("Iwerk")
        @Expose
        public String iwerk;
        @SerializedName("Wapinr")
        @Expose
        public String wapinr;
        @SerializedName("Stxt")
        @Expose
        public String stxt;
        @SerializedName("Datefr")
        @Expose
        public String datefr;
        @SerializedName("Timefr")
        @Expose
        public String timefr;
        @SerializedName("Dateto")
        @Expose
        public String dateto;
        @SerializedName("Timeto")
        @Expose
        public String timeto;
        @SerializedName("Extperiod")
        @Expose
        public Integer extperiod;
        @SerializedName("Usage")
        @Expose
        public String usage;
        @SerializedName("Usagex")
        @Expose
        public String usagex;
        @SerializedName("Train")
        @Expose
        public String train;
        @SerializedName("Trainx")
        @Expose
        public String trainx;
        @SerializedName("Anlzu")
        @Expose
        public String anlzu;
        @SerializedName("Anlzux")
        @Expose
        public String anlzux;
        @SerializedName("Etape")
        @Expose
        public String etape;
        @SerializedName("Etapex")
        @Expose
        public String etapex;
        @SerializedName("Begru")
        @Expose
        public String begru;
        @SerializedName("Begtx")
        @Expose
        public String begtx;
        @SerializedName("Priok")
        @Expose
        public String priok;
        @SerializedName("Priokx")
        @Expose
        public String priokx;
        @SerializedName("Rctime")
        @Expose
        public String rctime;
        @SerializedName("Rcunit")
        @Expose
        public String rcunit;
        @SerializedName("Objnr")
        @Expose
        public String objnr;
        @SerializedName("Refobj")
        @Expose
        public String refobj;
        @SerializedName("Crea")
        @Expose
        public String crea;
        @SerializedName("Prep")
        @Expose
        public String prep;
        @SerializedName("Comp")
        @Expose
        public String comp;
        @SerializedName("Appr")
        @Expose
        public String appr;
        @SerializedName("Pappr")
        @Expose
        public String pappr;
        @SerializedName("Action")
        @Expose
        public String action;
        @SerializedName("Usr01")
        @Expose
        public String usr01;
        @SerializedName("Usr02")
        @Expose
        public String usr02;
        @SerializedName("Usr03")
        @Expose
        public String usr03;
    }
    /*For Parsing EtWcmWaData*/


    /*For Parsing EtWcmWwData*/
    public class EtWcmWwData {
        @SerializedName("results")
        @Expose
        private List<EtWcmWwData_Result> results = null;

        public List<EtWcmWwData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtWcmWwData_Result> results) {
            this.results = results;
        }
    }

    public class EtWcmWwData_Result {
        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObjart() {
            return objart;
        }

        public void setObjart(String objart) {
            this.objart = objart;
        }

        public String getIwerk() {
            return iwerk;
        }

        public void setIwerk(String iwerk) {
            this.iwerk = iwerk;
        }

        public String getWapnr() {
            return wapnr;
        }

        public void setWapnr(String wapnr) {
            this.wapnr = wapnr;
        }

        public String getDatefr() {
            return datefr;
        }

        public void setDatefr(String datefr) {
            this.datefr = datefr;
        }

        public String getTimefr() {
            return timefr;
        }

        public void setTimefr(String timefr) {
            this.timefr = timefr;
        }

        public String getDateto() {
            return dateto;
        }

        public void setDateto(String dateto) {
            this.dateto = dateto;
        }

        public String getTimeto() {
            return timeto;
        }

        public void setTimeto(String timeto) {
            this.timeto = timeto;
        }

        public String getStxt() {
            return stxt;
        }

        public void setStxt(String stxt) {
            this.stxt = stxt;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public String getUsagex() {
            return usagex;
        }

        public void setUsagex(String usagex) {
            this.usagex = usagex;
        }

        public String getTrain() {
            return train;
        }

        public void setTrain(String train) {
            this.train = train;
        }

        public String getTrainx() {
            return trainx;
        }

        public void setTrainx(String trainx) {
            this.trainx = trainx;
        }

        public String getAnlzu() {
            return anlzu;
        }

        public void setAnlzu(String anlzu) {
            this.anlzu = anlzu;
        }

        public String getAnlzux() {
            return anlzux;
        }

        public void setAnlzux(String anlzux) {
            this.anlzux = anlzux;
        }

        public String getEtape() {
            return etape;
        }

        public void setEtape(String etape) {
            this.etape = etape;
        }

        public String getEtapex() {
            return etapex;
        }

        public void setEtapex(String etapex) {
            this.etapex = etapex;
        }

        public String getRctime() {
            return rctime;
        }

        public void setRctime(String rctime) {
            this.rctime = rctime;
        }

        public String getRcunit() {
            return rcunit;
        }

        public void setRcunit(String rcunit) {
            this.rcunit = rcunit;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getPriokx() {
            return priokx;
        }

        public void setPriokx(String priokx) {
            this.priokx = priokx;
        }

        public String getBegru() {
            return begru;
        }

        public void setBegru(String begru) {
            this.begru = begru;
        }

        public String getBegtx() {
            return begtx;
        }

        public void setBegtx(String begtx) {
            this.begtx = begtx;
        }

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getCrea() {
            return crea;
        }

        public void setCrea(String crea) {
            this.crea = crea;
        }

        public String getPrep() {
            return prep;
        }

        public void setPrep(String prep) {
            this.prep = prep;
        }

        public String getComp() {
            return comp;
        }

        public void setComp(String comp) {
            this.comp = comp;
        }

        public String getAppr() {
            return appr;
        }

        public void setAppr(String appr) {
            this.appr = appr;
        }

        public String getPappr() {
            return pappr;
        }

        public void setPappr(String pappr) {
            this.pappr = pappr;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Objart")
        @Expose
        public String objart;
        @SerializedName("Iwerk")
        @Expose
        public String iwerk;
        @SerializedName("Wapnr")
        @Expose
        public String wapnr;
        @SerializedName("Datefr")
        @Expose
        public String datefr;
        @SerializedName("Timefr")
        @Expose
        public String timefr;
        @SerializedName("Dateto")
        @Expose
        public String dateto;
        @SerializedName("Timeto")
        @Expose
        public String timeto;
        @SerializedName("Stxt")
        @Expose
        public String stxt;
        @SerializedName("Usage")
        @Expose
        public String usage;
        @SerializedName("Usagex")
        @Expose
        public String usagex;
        @SerializedName("Train")
        @Expose
        public String train;
        @SerializedName("Trainx")
        @Expose
        public String trainx;
        @SerializedName("Anlzu")
        @Expose
        public String anlzu;
        @SerializedName("Anlzux")
        @Expose
        public String anlzux;
        @SerializedName("Etape")
        @Expose
        public String etape;
        @SerializedName("Etapex")
        @Expose
        public String etapex;
        @SerializedName("Rctime")
        @Expose
        public String rctime;
        @SerializedName("Rcunit")
        @Expose
        public String rcunit;
        @SerializedName("Priok")
        @Expose
        public String priok;
        @SerializedName("Priokx")
        @Expose
        public String priokx;
        @SerializedName("Begru")
        @Expose
        public String begru;
        @SerializedName("Begtx")
        @Expose
        public String begtx;
        @SerializedName("Objnr")
        @Expose
        public String objnr;
        @SerializedName("Crea")
        @Expose
        public String crea;
        @SerializedName("Prep")
        @Expose
        public String prep;
        @SerializedName("Comp")
        @Expose
        public String comp;
        @SerializedName("Appr")
        @Expose
        public String appr;
        @SerializedName("Pappr")
        @Expose
        public String pappr;
        @SerializedName("Action")
        @Expose
        public String action;
    }
    /*For Parsing EtWcmWwData*/


    /*For Parsing EtDocs*/
    public class EtDocs {
        @SerializedName("results")
        @Expose
        private List<EtDocs_Result> results = null;

        public List<EtDocs_Result> getResults() {
            return results;
        }

        public void setResults(List<EtDocs_Result> results) {
            this.results = results;
        }
    }

    public class EtDocs_Result {
        public String getObjtype() {
            return objtype;
        }

        public void setObjtype(String objtype) {
            this.objtype = objtype;
        }

        public String getZobjid() {
            return zobjid;
        }

        public void setZobjid(String zobjid) {
            this.zobjid = zobjid;
        }

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZdoctypeItem() {
            return zdoctypeItem;
        }

        public void setZdoctypeItem(String zdoctypeItem) {
            this.zdoctypeItem = zdoctypeItem;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getFsize() {
            return fsize;
        }

        public void setFsize(String fsize) {
            this.fsize = fsize;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDocID() {
            return docID;
        }

        public void setDocID(String docID) {
            this.docID = docID;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public String getContentX() {
            return contentX;
        }

        public void setContentX(String contentX) {
            this.contentX = contentX;
        }

        @SerializedName("Objtype")
        @Expose
        public String objtype;
        @SerializedName("Zobjid")
        @Expose
        public String zobjid;
        @SerializedName("Zdoctype")
        @Expose
        public String zdoctype;
        @SerializedName("ZdoctypeItem")
        @Expose
        public String zdoctypeItem;
        @SerializedName("Filename")
        @Expose
        public String filename;
        @SerializedName("Filetype")
        @Expose
        public String filetype;
        @SerializedName("Fsize")
        @Expose
        public String fsize;
        @SerializedName("Content")
        @Expose
        public String content;
        @SerializedName("DocID")
        @Expose
        public String docID;
        @SerializedName("DocType")
        @Expose
        public String docType;
        @SerializedName("Contentx")
        @Expose
        public String contentX;
    }
    /*For Parsing EtDocs*/


    /*For Parsing EtOrderStatus*/
    public class EtOrderStatus {
        @SerializedName("results")
        @Expose
        private List<EtOrderStatus_Result> results = null;

        public List<EtOrderStatus_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderStatus_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderStatus_Result {
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

        public String getObjnr() {
            return objnr;
        }

        public void setObjnr(String objnr) {
            this.objnr = objnr;
        }

        public String getStsma() {
            return stsma;
        }

        public void setStsma(String stsma) {
            this.stsma = stsma;
        }

        public String getInist() {
            return inist;
        }

        public void setInist(String inist) {
            this.inist = inist;
        }

        public String getStonr() {
            return stonr;
        }

        public void setStonr(String stonr) {
            this.stonr = stonr;
        }

        public String getHsonr() {
            return hsonr;
        }

        public void setHsonr(String hsonr) {
            this.hsonr = hsonr;
        }

        public String getNsonr() {
            return nsonr;
        }

        public void setNsonr(String nsonr) {
            this.nsonr = nsonr;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getTxt04() {
            return txt04;
        }

        public void setTxt04(String txt04) {
            this.txt04 = txt04;
        }

        public String getTxt30() {
            return txt30;
        }

        public void setTxt30(String txt30) {
            this.txt30 = txt30;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Vornr")
        @Expose
        public String vornr;
        @SerializedName("Objnr")
        @Expose
        public String objnr;
        @SerializedName("Stsma")
        @Expose
        public String stsma;
        @SerializedName("Inist")
        @Expose
        public String inist;
        @SerializedName("Stonr")
        @Expose
        public String stonr;
        @SerializedName("Hsonr")
        @Expose
        public String hsonr;
        @SerializedName("Nsonr")
        @Expose
        public String nsonr;
        @SerializedName("Stat")
        @Expose
        public String stat;
        @SerializedName("Act")
        @Expose
        public String act;
        @SerializedName("Txt04")
        @Expose
        public String txt04;
        @SerializedName("Txt30")
        @Expose
        public String txt30;
        @SerializedName("Action")
        @Expose
        public String action;
    }
    /*For Parsing EtOrderStatus*/


    /*For Parsing EtOrderOlist*/
    public class EtOrderOlist {
        @SerializedName("results")
        @Expose
        private List<EtOrderOlist_Result> results = null;

        public List<EtOrderOlist_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderOlist_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderOlist_Result {
        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Obknr")
        @Expose
        public String obknr;
        @SerializedName("Obzae")
        @Expose
        public String obzae;
        @SerializedName("Qmnum")
        @Expose
        public String qmnum;
        @SerializedName("Equnr")
        @Expose
        public String equnr;
        @SerializedName("Strno")
        @Expose
        public String strno;
        @SerializedName("Tplnr")
        @Expose
        public String tplnr;
        @SerializedName("Bautl")
        @Expose
        public String bautl;
        @SerializedName("Qmtxt")
        @Expose
        public String qmtxt;
        @SerializedName("Pltxt")
        @Expose
        public String pltxt;
        @SerializedName("Eqktx")
        @Expose
        public String eqktx;
        @SerializedName("Maktx")
        @Expose
        public String maktx;
        @SerializedName("Action")
        @Expose
        public String action;

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getObknr() {
            return obknr;
        }

        public void setObknr(String obknr) {
            this.obknr = obknr;
        }

        public String getObzae() {
            return obzae;
        }

        public void setObzae(String obzae) {
            this.obzae = obzae;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getStrno() {
            return strno;
        }

        public void setStrno(String strno) {
            this.strno = strno;
        }

        public String getTplnr() {
            return tplnr;
        }

        public void setTplnr(String tplnr) {
            this.tplnr = tplnr;
        }

        public String getBautl() {
            return bautl;
        }

        public void setBautl(String bautl) {
            this.bautl = bautl;
        }

        public String getQmtxt() {
            return qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            this.qmtxt = qmtxt;
        }

        public String getPltxt() {
            return pltxt;
        }

        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }

        public String getEqktx() {
            return eqktx;
        }

        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }

        public String getMaktx() {
            return maktx;
        }

        public void setMaktx(String maktx) {
            this.maktx = maktx;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
    /*For Parsing EtOrderOlist*/


    /*For Parsing EtOrderLongtext*/
    public class EtOrderLongtext {
        @SerializedName("results")
        @Expose
        private List<EtOrderLongtext_Result> results = null;

        public List<EtOrderLongtext_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderLongtext_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderLongtext_Result {
        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Activity")
        @Expose
        public String activity;
        @SerializedName("TextLine")
        @Expose
        public String textLine;
        @SerializedName("Tdid")
        @Expose
        public String Tdid;

        public String getTdid() {
            return Tdid;
        }

        public void setTdid(String tdid) {
            Tdid = tdid;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getTextLine() {
            return textLine;
        }

        public void setTextLine(String textLine) {
            this.textLine = textLine;
        }
    }
    /*For Parsing EtOrderOperations*/


    /*For Parsing EtOrderOperations*/
    public class EtOrderOperations {
        @SerializedName("results")
        @Expose
        private List<EtOrderOperations_Result> results = null;

        public List<EtOrderOperations_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderOperations_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderOperations_Result {
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

        public String getDauno() {
            return dauno;
        }

        public void setDauno(String dauno) {
            this.dauno = dauno;
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

        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Vornr")
        @Expose
        public String vornr;
        @SerializedName("Uvorn")
        @Expose
        public String uvorn;
        @SerializedName("Ltxa1")
        @Expose
        public String ltxa1;
        @SerializedName("Arbpl")
        @Expose
        public String arbpl;
        @SerializedName("Werks")
        @Expose
        public String werks;
        @SerializedName("Steus")
        @Expose
        public String steus;
        @SerializedName("Larnt")
        @Expose
        public String larnt;
        @SerializedName("Dauno")
        @Expose
        public String dauno;
        @SerializedName("Daune")
        @Expose
        public String daune;
        @SerializedName("Fsavd")
        @Expose
        public String fsavd;
        @SerializedName("Ssedd")
        @Expose
        public String ssedd;
        @SerializedName("Pernr")
        @Expose
        public String pernr;
        @SerializedName("Asnum")
        @Expose
        public String asnum;
        @SerializedName("Plnty")
        @Expose
        public String plnty;
        @SerializedName("Plnal")
        @Expose
        public String plnal;
        @SerializedName("Plnnr")
        @Expose
        public String plnnr;
        @SerializedName("Rueck")
        @Expose
        public String rueck;
        @SerializedName("Aueru")
        @Expose
        public String aueru;
        @SerializedName("ArbplText")
        @Expose
        public String arbplText;
        @SerializedName("WerksText")
        @Expose
        public String werksText;
        @SerializedName("SteusText")
        @Expose
        public String steusText;
        @SerializedName("LarntText")
        @Expose
        public String larntText;
        @SerializedName("Usr01")
        @Expose
        public String usr01;
        @SerializedName("Usr02")
        @Expose
        public String usr02;
        @SerializedName("Usr03")
        @Expose
        public String usr03;
        @SerializedName("Usr04")
        @Expose
        public String usr04;
        @SerializedName("Usr05")
        @Expose
        public String usr05;
        @SerializedName("Action")
        @Expose
        public String action;
        @SerializedName("EtOrderOperationsFields")
        @Expose
        private EtOrderHeaderFields etOrderOperationsFields;

        public EtOrderHeaderFields getEtOrderOperationsFields() {
            return etOrderOperationsFields;
        }

        public void setEtOrderOperationsFields(EtOrderHeaderFields etOrderOperationsFields) {
            this.etOrderOperationsFields = etOrderOperationsFields;
        }
    }
    /*For Parsing EtOrderOperations*/


    /*For Parsing EtOrderHeader*/
    public class EtOrderHeader {
        @SerializedName("results")
        @Expose
        private List<EtOrderHeader_Result> results = null;

        public List<EtOrderHeader_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderHeader_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderHeader_Result {
        @SerializedName("Aufnr")
        @Expose
        public String aufnr;
        @SerializedName("Auart")
        @Expose
        public String auart;
        @SerializedName("Ktext")
        @Expose
        public String ktext;
        @SerializedName("Ilart")
        @Expose
        public String ilart;
        @SerializedName("Ernam")
        @Expose
        public String ernam;
        @SerializedName("Erdat")
        @Expose
        public String erdat;
        @SerializedName("Priok")
        @Expose
        public String priok;
        @SerializedName("Equnr")
        @Expose
        public String equnr;
        @SerializedName("Strno")
        @Expose
        public String strno;
        @SerializedName("TplnrInt")
        @Expose
        public String tplnrInt;
        @SerializedName("Bautl")
        @Expose
        public String bautl;
        @SerializedName("Gltrp")
        @Expose
        public String gltrp;
        @SerializedName("Gstrp")
        @Expose
        public String gstrp;
        @SerializedName("Msaus")
        @Expose
        public String msaus;
        @SerializedName("Anlzu")
        @Expose
        public String anlzu;
        @SerializedName("Ausvn")
        @Expose
        public String ausvn;
        @SerializedName("Ausbs")
        @Expose
        public String ausbs;
        @SerializedName("Qmnam")
        @Expose
        public String qmnam;
        @SerializedName("Auswk")
        @Expose
        public String auswk;
        @SerializedName("ParnrVw")
        @Expose
        public String parnrVw;
        @SerializedName("NameVw")
        @Expose
        public String nameVw;
        @SerializedName("Docs")
        @Expose
        public String docs;
        @SerializedName("Permits")
        @Expose
        public String permits;
        @SerializedName("Altitude")
        @Expose
        public String altitude;
        @SerializedName("Latitude")
        @Expose
        public String latitude;
        @SerializedName("Longitude")
        @Expose
        public String longitude;
        @SerializedName("Qmnum")
        @Expose
        public String qmnum;
        @SerializedName("Qcreate")
        @Expose
        public String qcreate;
        @SerializedName("Closed")
        @Expose
        public String closed;
        @SerializedName("Completed")
        @Expose
        public String completed;
        @SerializedName("Wcm")
        @Expose
        public String wcm;
        @SerializedName("Wsm")
        @Expose
        public String wsm;
        @SerializedName("Ingrp")
        @Expose
        public String ingrp;
        @SerializedName("Arbpl")
        @Expose
        public String arbpl;
        @SerializedName("Werks")
        @Expose
        public String werks;
        @SerializedName("Bemot")
        @Expose
        public String bemot;
        @SerializedName("Aueru")
        @Expose
        public String aueru;
        @SerializedName("Auarttext")
        @Expose
        public String auarttext;
        @SerializedName("Qmartx")
        @Expose
        public String qmartx;
        @SerializedName("Qmtxt")
        @Expose
        public String qmtxt;
        @SerializedName("Pltxt")
        @Expose
        public String pltxt;
        @SerializedName("Eqktx")
        @Expose
        public String eqktx;
        @SerializedName("Priokx")
        @Expose
        public String priokx;
        @SerializedName("Ilatx")
        @Expose
        public String ilatx;
        @SerializedName("Plantname")
        @Expose
        public String plantname;
        @SerializedName("Wkctrname")
        @Expose
        public String wkctrname;
        @SerializedName("Ingrpname")
        @Expose
        public String ingrpname;
        @SerializedName("Maktx")
        @Expose
        public String maktx;
        @SerializedName("Anlzux")
        @Expose
        public String anlzux;
        @SerializedName("Xstatus")
        @Expose
        public String xstatus;
        @SerializedName("Kokrs")
        @Expose
        public String kokrs;
        @SerializedName("Kostl")
        @Expose
        public String kostl;
        @SerializedName("Posid")
        @Expose
        public String posid;
        @SerializedName("Revnr")
        @Expose
        public String revnr;
        @SerializedName("Usr01")
        @Expose
        public String usr01;
        @SerializedName("Usr02")
        @Expose
        public String usr02;
        @SerializedName("Usr03")
        @Expose
        public String usr03;
        @SerializedName("Usr04")
        @Expose
        public String usr04;
        @SerializedName("Usr05")
        @Expose
        public String usr05;
        @SerializedName("EtOrderHeaderFields")
        @Expose
        private EtOrderHeaderFields etOrderHeaderFields;

        public EtOrderHeaderFields getEtOrderHeaderFields() {
            return etOrderHeaderFields;
        }

        public void setEtOrderHeaderFields(EtOrderHeaderFields etOrderHeaderFields) {
            this.etOrderHeaderFields = etOrderHeaderFields;
        }

        public String getPosid() {
            return posid;
        }

        public void setPosid(String posid) {
            this.posid = posid;
        }

        public String getRevnr() {
            return revnr;
        }

        public void setRevnr(String revnr) {
            this.revnr = revnr;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getAuart() {
            return auart;
        }

        public void setAuart(String auart) {
            this.auart = auart;
        }

        public String getKtext() {
            return ktext;
        }

        public void setKtext(String ktext) {
            this.ktext = ktext;
        }

        public String getIlart() {
            return ilart;
        }

        public void setIlart(String ilart) {
            this.ilart = ilart;
        }

        public String getErnam() {
            return ernam;
        }

        public void setErnam(String ernam) {
            this.ernam = ernam;
        }

        public String getErdat() {
            return erdat;
        }

        public void setErdat(String erdat) {
            this.erdat = erdat;
        }

        public String getPriok() {
            return priok;
        }

        public void setPriok(String priok) {
            this.priok = priok;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getStrno() {
            return strno;
        }

        public void setStrno(String strno) {
            this.strno = strno;
        }

        public String getTplnrInt() {
            return tplnrInt;
        }

        public void setTplnrInt(String tplnrInt) {
            this.tplnrInt = tplnrInt;
        }

        public String getBautl() {
            return bautl;
        }

        public void setBautl(String bautl) {
            this.bautl = bautl;
        }

        public String getGltrp() {
            return gltrp;
        }

        public void setGltrp(String gltrp) {
            this.gltrp = gltrp;
        }

        public String getGstrp() {
            return gstrp;
        }

        public void setGstrp(String gstrp) {
            this.gstrp = gstrp;
        }

        public String getMsaus() {
            return msaus;
        }

        public void setMsaus(String msaus) {
            this.msaus = msaus;
        }

        public String getAnlzu() {
            return anlzu;
        }

        public void setAnlzu(String anlzu) {
            this.anlzu = anlzu;
        }

        public String getAusvn() {
            return ausvn;
        }

        public void setAusvn(String ausvn) {
            this.ausvn = ausvn;
        }

        public String getAusbs() {
            return ausbs;
        }

        public void setAusbs(String ausbs) {
            this.ausbs = ausbs;
        }

        public String getQmnam() {
            return qmnam;
        }

        public void setQmnam(String qmnam) {
            this.qmnam = qmnam;
        }

        public String getAuswk() {
            return auswk;
        }

        public void setAuswk(String auswk) {
            this.auswk = auswk;
        }

        public String getParnrVw() {
            return parnrVw;
        }

        public void setParnrVw(String parnrVw) {
            this.parnrVw = parnrVw;
        }

        public String getNameVw() {
            return nameVw;
        }

        public void setNameVw(String nameVw) {
            this.nameVw = nameVw;
        }

        public String getDocs() {
            return docs;
        }

        public void setDocs(String docs) {
            this.docs = docs;
        }

        public String getPermits() {
            return permits;
        }

        public void setPermits(String permits) {
            this.permits = permits;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getQmnum() {
            return qmnum;
        }

        public void setQmnum(String qmnum) {
            this.qmnum = qmnum;
        }

        public String getQcreate() {
            return qcreate;
        }

        public void setQcreate(String qcreate) {
            this.qcreate = qcreate;
        }

        public String getClosed() {
            return closed;
        }

        public void setClosed(String closed) {
            this.closed = closed;
        }

        public String getCompleted() {
            return completed;
        }

        public void setCompleted(String completed) {
            this.completed = completed;
        }

        public String getWcm() {
            return wcm;
        }

        public void setWcm(String wcm) {
            this.wcm = wcm;
        }

        public String getWsm() {
            return wsm;
        }

        public void setWsm(String wsm) {
            this.wsm = wsm;
        }

        public String getIngrp() {
            return ingrp;
        }

        public void setIngrp(String ingrp) {
            this.ingrp = ingrp;
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

        public String getBemot() {
            return bemot;
        }

        public void setBemot(String bemot) {
            this.bemot = bemot;
        }

        public String getAueru() {
            return aueru;
        }

        public void setAueru(String aueru) {
            this.aueru = aueru;
        }

        public String getAuarttext() {
            return auarttext;
        }

        public void setAuarttext(String auarttext) {
            this.auarttext = auarttext;
        }

        public String getQmartx() {
            return qmartx;
        }

        public void setQmartx(String qmartx) {
            this.qmartx = qmartx;
        }

        public String getQmtxt() {
            return qmtxt;
        }

        public void setQmtxt(String qmtxt) {
            this.qmtxt = qmtxt;
        }

        public String getPltxt() {
            return pltxt;
        }

        public void setPltxt(String pltxt) {
            this.pltxt = pltxt;
        }

        public String getEqktx() {
            return eqktx;
        }

        public void setEqktx(String eqktx) {
            this.eqktx = eqktx;
        }

        public String getPriokx() {
            return priokx;
        }

        public void setPriokx(String priokx) {
            this.priokx = priokx;
        }

        public String getIlatx() {
            return ilatx;
        }

        public void setIlatx(String ilatx) {
            this.ilatx = ilatx;
        }

        public String getPlantname() {
            return plantname;
        }

        public void setPlantname(String plantname) {
            this.plantname = plantname;
        }

        public String getWkctrname() {
            return wkctrname;
        }

        public void setWkctrname(String wkctrname) {
            this.wkctrname = wkctrname;
        }

        public String getIngrpname() {
            return ingrpname;
        }

        public void setIngrpname(String ingrpname) {
            this.ingrpname = ingrpname;
        }

        public String getMaktx() {
            return maktx;
        }

        public void setMaktx(String maktx) {
            this.maktx = maktx;
        }

        public String getAnlzux() {
            return anlzux;
        }

        public void setAnlzux(String anlzux) {
            this.anlzux = anlzux;
        }

        public String getXstatus() {
            return xstatus;
        }

        public void setXstatus(String xstatus) {
            this.xstatus = xstatus;
        }

        public String getKokrs() {
            return kokrs;
        }

        public void setKokrs(String kokrs) {
            this.kokrs = kokrs;
        }

        public String getKostl() {
            return kostl;
        }

        public void setKostl(String kostl) {
            this.kostl = kostl;
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
    }
    /*For Parsing EtOrderHeader*/


    /*For Parsing EtCustomFields*/
    public class EtOrderHeaderFields {
        @SerializedName("results")
        @Expose
        private List<EtOrderHeaderFields_Result> results = null;

        public List<EtOrderHeaderFields_Result> getResults() {
            return results;
        }

        public void setResults(List<EtOrderHeaderFields_Result> results) {
            this.results = results;
        }
    }

    public class EtOrderHeaderFields_Result {
        @SerializedName("Zdoctype")
        @Expose
        private String zdoctype;
        @SerializedName("ZdoctypeItem")
        @Expose
        private String zdoctypeItem;
        @SerializedName("Tabname")
        @Expose
        private String tabname;
        @SerializedName("Fieldname")
        @Expose
        private String fieldname;
        @SerializedName("Datatype")
        @Expose
        private String datatype;
        @SerializedName("Value")
        @Expose
        private String value;
        @SerializedName("Flabel")
        @Expose
        private String flabel;
        @SerializedName("Sequence")
        @Expose
        private String sequence;
        @SerializedName("Length")
        @Expose
        private String length;

        public String getZdoctype() {
            return zdoctype;
        }

        public void setZdoctype(String zdoctype) {
            this.zdoctype = zdoctype;
        }

        public String getZdoctypeItem() {
            return zdoctypeItem;
        }

        public void setZdoctypeItem(String zdoctypeItem) {
            this.zdoctypeItem = zdoctypeItem;
        }

        public String getTabname() {
            return tabname;
        }

        public void setTabname(String tabname) {
            this.tabname = tabname;
        }

        public String getFieldname() {
            return fieldname;
        }

        public void setFieldname(String fieldname) {
            this.fieldname = fieldname;
        }

        public String getDatatype() {
            return datatype;
        }

        public void setDatatype(String datatype) {
            this.datatype = datatype;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getFlabel() {
            return flabel;
        }

        public void setFlabel(String flabel) {
            this.flabel = flabel;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }
    /*For Parsing EtCustomFields*/

}