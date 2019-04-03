package com.enstrapp.fieldtekpro.Initialload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Calibration_SER {

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


        @SerializedName("EtQinspData")
        @Expose
        private EtQinspData etQinspData;
        @SerializedName("EtQudData")
        @Expose
        private EtQudData EtQudData;

        public Calibration_SER.EtQudData getEtQudData() {
            return EtQudData;
        }

        public void setEtQudData(Calibration_SER.EtQudData etQudData) {
            EtQudData = etQudData;
        }

        public EtQinspData getEtQinspData() {
            return etQinspData;
        }

        public void setEtQinspData(EtQinspData etQinspData) {
            this.etQinspData = etQinspData;
        }


        @SerializedName("EtMessage")
        @Expose
        private EtMessage etMessage;

        public EtMessage getEtMessage() {
            return etMessage;
        }

        public void setEtMessage(EtMessage etMessage) {
            this.etMessage = etMessage;
        }
    }


    /*For Parsing EtMessage*/
    public class EtMessage {
        @SerializedName("results")
        @Expose
        private List<EtMessage_Result> results = null;

        public List<EtMessage_Result> getResults() {
            return results;
        }

        public void setResults(List<EtMessage_Result> results) {
            this.results = results;
        }
    }

    public class EtMessage_Result {
        @SerializedName("Message")
        @Expose
        private String Message;
        @SerializedName("Resnum")
        @Expose
        private String Resnum;

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getResnum() {
            return Resnum;
        }

        public void setResnum(String Resnum) {
            this.Resnum = Resnum;
        }
    }
    /*For Parsing EtMessage*/

    public class Result {
        @SerializedName("EtQinspData")
        @Expose
        private EtQinspData etQinspData;
        @SerializedName("EtQudData")
        @Expose
        private EtQudData EtQudData;

        public Calibration_SER.EtQudData getEtQudData() {
            return EtQudData;
        }

        public void setEtQudData(Calibration_SER.EtQudData etQudData) {
            EtQudData = etQudData;
        }

        public EtQinspData getEtQinspData() {
            return etQinspData;
        }

        public void setEtQinspData(EtQinspData etQinspData) {
            this.etQinspData = etQinspData;
        }
    }

    public class EtQudData {
        @SerializedName("results")
        @Expose
        private List<EtQudData_Result> results = null;

        public List<EtQudData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtQudData_Result> results) {
            this.results = results;
        }
    }

    public class EtQudData_Result {
        @SerializedName("Prueflos")
        @Expose
        private String prueflos;
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Vkatart")
        @Expose
        private String vkatart;
        @SerializedName("Vcodegrp")
        @Expose
        private String vcodegrp;
        @SerializedName("Vauswahlmg")
        @Expose
        private String vauswahlmg;
        @SerializedName("Vcode")
        @Expose
        private String vcode;
        @SerializedName("Qkennzahl")
        @Expose
        private String qkennzahl;
        @SerializedName("Vname")
        @Expose
        private String vname;
        @SerializedName("Vdatum")
        @Expose
        private String vdatum;
        @SerializedName("Vaedatum")
        @Expose
        private String vaedatum;
        @SerializedName("Vezeitaen")
        @Expose
        private String vezeitaen;
        @SerializedName("Udtext")
        @Expose
        private String udtext;
        @SerializedName("Udforce")
        @Expose
        private String udforce;
        @SerializedName("Rcode")
        @Expose
        private String rcode;
        @SerializedName("Xstatus")
        @Expose
        private String xstatus;
        @SerializedName("Action")
        @Expose
        private String action;
        @SerializedName("Udid")
        @Expose
        private String udid;

        public String getPrueflos() {
            return prueflos;
        }

        public void setPrueflos(String prueflos) {
            this.prueflos = prueflos;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getVkatart() {
            return vkatart;
        }

        public void setVkatart(String vkatart) {
            this.vkatart = vkatart;
        }

        public String getVcodegrp() {
            return vcodegrp;
        }

        public void setVcodegrp(String vcodegrp) {
            this.vcodegrp = vcodegrp;
        }

        public String getVauswahlmg() {
            return vauswahlmg;
        }

        public void setVauswahlmg(String vauswahlmg) {
            this.vauswahlmg = vauswahlmg;
        }

        public String getVcode() {
            return vcode;
        }

        public void setVcode(String vcode) {
            this.vcode = vcode;
        }

        public String getQkennzahl() {
            return qkennzahl;
        }

        public void setQkennzahl(String qkennzahl) {
            this.qkennzahl = qkennzahl;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public String getVdatum() {
            return vdatum;
        }

        public void setVdatum(String vdatum) {
            this.vdatum = vdatum;
        }

        public String getVaedatum() {
            return vaedatum;
        }

        public void setVaedatum(String vaedatum) {
            this.vaedatum = vaedatum;
        }

        public String getVezeitaen() {
            return vezeitaen;
        }

        public void setVezeitaen(String vezeitaen) {
            this.vezeitaen = vezeitaen;
        }

        public String getUdtext() {
            return udtext;
        }

        public void setUdtext(String udtext) {
            this.udtext = udtext;
        }

        public String getUdforce() {
            return udforce;
        }

        public void setUdforce(String udforce) {
            this.udforce = udforce;
        }

        public String getRcode() {
            return rcode;
        }

        public void setRcode(String rcode) {
            this.rcode = rcode;
        }

        public String getXstatus() {
            return xstatus;
        }

        public void setXstatus(String xstatus) {
            this.xstatus = xstatus;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }
    }


    public class EtQinspData {
        @SerializedName("results")
        @Expose
        private List<EtQinspData_Result> results = null;

        public List<EtQinspData_Result> getResults() {
            return results;
        }

        public void setResults(List<EtQinspData_Result> results) {
            this.results = results;
        }
    }

    public class EtQinspData_Result {
        @SerializedName("Aufnr")
        @Expose
        private String aufnr;
        @SerializedName("Prueflos")
        @Expose
        private String prueflos;
        @SerializedName("Vornr")
        @Expose
        private String vornr;
        @SerializedName("Plnty")
        @Expose
        private String plnty;
        @SerializedName("Plnnr")
        @Expose
        private String plnnr;
        @SerializedName("Plnkn")
        @Expose
        private String plnkn;
        @SerializedName("Merknr")
        @Expose
        private String merknr;
        @SerializedName("Werks")
        @Expose
        private String werks;
        @SerializedName("Quantitat")
        @Expose
        private String quantitat;
        @SerializedName("Qualitat")
        @Expose
        private String qualitat;
        @SerializedName("QpmkZaehl")
        @Expose
        private String qpmkZaehl;
        @SerializedName("Msehi")
        @Expose
        private String msehi;
        @SerializedName("Msehl")
        @Expose
        private String msehl;
        @SerializedName("Verwmerkm")
        @Expose
        private String verwmerkm;
        @SerializedName("Kurztext")
        @Expose
        private String kurztext;
        @SerializedName("Result")
        @Expose
        private String result;
        @SerializedName("Sollwert")
        @Expose
        private String sollwert;
        @SerializedName("Toleranzob")
        @Expose
        private String toleranzob;
        @SerializedName("Toleranzub")
        @Expose
        private String toleranzub;
        @SerializedName("Rueckmelnr")
        @Expose
        private String rueckmelnr;
        @SerializedName("Satzstatus")
        @Expose
        private String satzstatus;
        @SerializedName("Equnr")
        @Expose
        private String equnr;
        @SerializedName("Pruefbemkt")
        @Expose
        private String pruefbemkt;
        @SerializedName("Mbewertg")
        @Expose
        private String mbewertg;
        @SerializedName("Pruefer")
        @Expose
        private String pruefer;
        @SerializedName("Pruefdatuv")
        @Expose
        private String pruefdatuv;
        @SerializedName("Pruefdatub")
        @Expose
        private String pruefdatub;
        @SerializedName("Pruefzeitv")
        @Expose
        private String pruefzeitv;
        @SerializedName("Pruefzeitb")
        @Expose
        private String pruefzeitb;
        @SerializedName("Iststpumf")
        @Expose
        private Integer iststpumf;
        @SerializedName("Anzfehleh")
        @Expose
        private Integer anzfehleh;
        @SerializedName("Anzwertg")
        @Expose
        private Integer anzwertg;
        @SerializedName("Ktextmat")
        @Expose
        private String ktextmat;
        @SerializedName("Katab1")
        @Expose
        private String katab1;
        @SerializedName("Katalgart1")
        @Expose
        private String katalgart1;
        @SerializedName("Auswmenge1")
        @Expose
        private String auswmenge1;
        @SerializedName("Codetext")
        @Expose
        private String codetext;
        @SerializedName("Xstatus")
        @Expose
        private String xstatus;
        @SerializedName("Action")
        @Expose
        private String action;
        @SerializedName("Udid")
        @Expose
        private String udid;
        @SerializedName("Ltxa1")
        @Expose
        private String ltxa1;

        public String getLtxa1() {
            return ltxa1;
        }

        public void setLtxa1(String ltxa1) {
            this.ltxa1 = ltxa1;
        }

        public String getAufnr() {
            return aufnr;
        }

        public void setAufnr(String aufnr) {
            this.aufnr = aufnr;
        }

        public String getPrueflos() {
            return prueflos;
        }

        public void setPrueflos(String prueflos) {
            this.prueflos = prueflos;
        }

        public String getVornr() {
            return vornr;
        }

        public void setVornr(String vornr) {
            this.vornr = vornr;
        }

        public String getPlnty() {
            return plnty;
        }

        public void setPlnty(String plnty) {
            this.plnty = plnty;
        }

        public String getPlnnr() {
            return plnnr;
        }

        public void setPlnnr(String plnnr) {
            this.plnnr = plnnr;
        }

        public String getPlnkn() {
            return plnkn;
        }

        public void setPlnkn(String plnkn) {
            this.plnkn = plnkn;
        }

        public String getMerknr() {
            return merknr;
        }

        public void setMerknr(String merknr) {
            this.merknr = merknr;
        }

        public String getWerks() {
            return werks;
        }

        public void setWerks(String werks) {
            this.werks = werks;
        }

        public String getQuantitat() {
            return quantitat;
        }

        public void setQuantitat(String quantitat) {
            this.quantitat = quantitat;
        }

        public String getQualitat() {
            return qualitat;
        }

        public void setQualitat(String qualitat) {
            this.qualitat = qualitat;
        }

        public String getQpmkZaehl() {
            return qpmkZaehl;
        }

        public void setQpmkZaehl(String qpmkZaehl) {
            this.qpmkZaehl = qpmkZaehl;
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

        public String getVerwmerkm() {
            return verwmerkm;
        }

        public void setVerwmerkm(String verwmerkm) {
            this.verwmerkm = verwmerkm;
        }

        public String getKurztext() {
            return kurztext;
        }

        public void setKurztext(String kurztext) {
            this.kurztext = kurztext;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getSollwert() {
            return sollwert;
        }

        public void setSollwert(String sollwert) {
            this.sollwert = sollwert;
        }

        public String getToleranzob() {
            return toleranzob;
        }

        public void setToleranzob(String toleranzob) {
            this.toleranzob = toleranzob;
        }

        public String getToleranzub() {
            return toleranzub;
        }

        public void setToleranzub(String toleranzub) {
            this.toleranzub = toleranzub;
        }

        public String getRueckmelnr() {
            return rueckmelnr;
        }

        public void setRueckmelnr(String rueckmelnr) {
            this.rueckmelnr = rueckmelnr;
        }

        public String getSatzstatus() {
            return satzstatus;
        }

        public void setSatzstatus(String satzstatus) {
            this.satzstatus = satzstatus;
        }

        public String getEqunr() {
            return equnr;
        }

        public void setEqunr(String equnr) {
            this.equnr = equnr;
        }

        public String getPruefbemkt() {
            return pruefbemkt;
        }

        public void setPruefbemkt(String pruefbemkt) {
            this.pruefbemkt = pruefbemkt;
        }

        public String getMbewertg() {
            return mbewertg;
        }

        public void setMbewertg(String mbewertg) {
            this.mbewertg = mbewertg;
        }

        public String getPruefer() {
            return pruefer;
        }

        public void setPruefer(String pruefer) {
            this.pruefer = pruefer;
        }

        public String getPruefdatuv() {
            return pruefdatuv;
        }

        public void setPruefdatuv(String pruefdatuv) {
            this.pruefdatuv = pruefdatuv;
        }

        public String getPruefdatub() {
            return pruefdatub;
        }

        public void setPruefdatub(String pruefdatub) {
            this.pruefdatub = pruefdatub;
        }

        public String getPruefzeitv() {
            return pruefzeitv;
        }

        public void setPruefzeitv(String pruefzeitv) {
            this.pruefzeitv = pruefzeitv;
        }

        public String getPruefzeitb() {
            return pruefzeitb;
        }

        public void setPruefzeitb(String pruefzeitb) {
            this.pruefzeitb = pruefzeitb;
        }

        public Integer getIststpumf() {
            return iststpumf;
        }

        public void setIststpumf(Integer iststpumf) {
            this.iststpumf = iststpumf;
        }

        public Integer getAnzfehleh() {
            return anzfehleh;
        }

        public void setAnzfehleh(Integer anzfehleh) {
            this.anzfehleh = anzfehleh;
        }

        public Integer getAnzwertg() {
            return anzwertg;
        }

        public void setAnzwertg(Integer anzwertg) {
            this.anzwertg = anzwertg;
        }

        public String getKtextmat() {
            return ktextmat;
        }

        public void setKtextmat(String ktextmat) {
            this.ktextmat = ktextmat;
        }

        public String getKatab1() {
            return katab1;
        }

        public void setKatab1(String katab1) {
            this.katab1 = katab1;
        }

        public String getKatalgart1() {
            return katalgart1;
        }

        public void setKatalgart1(String katalgart1) {
            this.katalgart1 = katalgart1;
        }

        public String getAuswmenge1() {
            return auswmenge1;
        }

        public void setAuswmenge1(String auswmenge1) {
            this.auswmenge1 = auswmenge1;
        }

        public String getCodetext() {
            return codetext;
        }

        public void setCodetext(String codetext) {
            this.codetext = codetext;
        }

        public String getXstatus() {
            return xstatus;
        }

        public void setXstatus(String xstatus) {
            this.xstatus = xstatus;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

    }

}