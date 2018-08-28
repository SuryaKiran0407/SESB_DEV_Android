package com.enstrapp.fieldtekpro.orders;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdrHdrSer {

    @SerializedName("Aufnr")
    @Expose
    private String Aufnr;
    @SerializedName("Auart")
    @Expose
    private String Auart;
    @SerializedName("Ktext")
    @Expose
    private String Ktext;
    @SerializedName("Ilart")
    @Expose
    private String Ilart;
    @SerializedName("Ernam")
    @Expose
    private String Ernam;
    @SerializedName("Erdat")
    @Expose
    private String Erdat;
    @SerializedName("Priok")
    @Expose
    private String Priok;
    @SerializedName("Equnr")
    @Expose
    private String Equnr;
    @SerializedName("Strno")
    @Expose
    private String Strno;
    @SerializedName("TplnrInt")
    @Expose
    private String TplnrInt;
    @SerializedName("Bautl")
    @Expose
    private String Bautl;
    @SerializedName("Gltrp")
    @Expose
    private String Gltrp;
    @SerializedName("Gstrp")
    @Expose
    private String Gstrp;
    @SerializedName("Msaus")
    @Expose
    private String Msaus;
    @SerializedName("Anlzu")
    @Expose
    private String Anlzu;
    @SerializedName("Ausvn")
    @Expose
    private String Ausvn;
    @SerializedName("Ausbs")
    @Expose
    private String Ausbs;
    @SerializedName("Qmnam")
    @Expose
    private String Qmnam;
    @SerializedName("Auswk")
    @Expose
    private String Auswk;
    @SerializedName("ParnrVw")
    @Expose
    private String ParnrVw;
    @SerializedName("NameVw")
    @Expose
    private String NameVw;
    @SerializedName("Docs")
    @Expose
    private String Docs;
    @SerializedName("Permits")
    @Expose
    private String Permits;
    @SerializedName("Altitude")
    @Expose
    private String Altitude;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Qmnum")
    @Expose
    private String Qmnum;
    @SerializedName("Qcreate")
    @Expose
    private String Qcreate;
    @SerializedName("Closed")
    @Expose
    private String Closed;
    @SerializedName("Completed")
    @Expose
    private String Completed;
    @SerializedName("Wcm")
    @Expose
    private String Wcm;
    @SerializedName("Wsm")
    @Expose
    private String Wsm;
    @SerializedName("Ingrp")
    @Expose
    private String Ingrp;
    @SerializedName("Arbpl")
    @Expose
    private String Arbpl;
    @SerializedName("Werks")
    @Expose
    private String Werks;
    @SerializedName("Bemot")
    @Expose
    private String Bemot;
    @SerializedName("Aueru")
    @Expose
    private String Aueru;
    @SerializedName("Auarttext")
    @Expose
    private String Auarttext;
    @SerializedName("Qmartx")
    @Expose
    private String Qmartx;
    @SerializedName("Qmtxt")
    @Expose
    private String Qmtxt;
    @SerializedName("Pltxt")
    @Expose
    private String Pltxt;
    @SerializedName("Eqktx")
    @Expose
    private String Eqktx;
    @SerializedName("Priokx")
    @Expose
    private String Priokx;
    @SerializedName("Ilatx")
    @Expose
    private String Ilatx;
    @SerializedName("Plantname")
    @Expose
    private String Plantname;
    @SerializedName("Wkctrname")
    @Expose
    private String Wkctrname;
    @SerializedName("Ingrpname")
    @Expose
    private String Ingrpname;
    @SerializedName("Maktx")
    @Expose
    private String Maktx;
    @SerializedName("Anlzux")
    @Expose
    private String Anlzux;
    @SerializedName("Xstatus")
    @Expose
    private String Xstatus;
    @SerializedName("Kokrs")
    @Expose
    private String Kokrs;
    @SerializedName("Kostl")
    @Expose
    private String Kostl;
    @SerializedName("Posid")
    @Expose
    public String posid;
    @SerializedName("Revnr")
    @Expose
    public String revnr;
    @SerializedName("Usr01")
    @Expose
    private String Usr01;
    @SerializedName("Usr02")
    @Expose
    private String Usr02;
    @SerializedName("Usr03")
    @Expose
    private String Usr03;
    @SerializedName("Usr04")
    @Expose
    private String Usr04;
    @SerializedName("Usr05")
    @Expose
    private String Usr05;
    @SerializedName("ItOrderHeadeFields")
    @Expose
    private List<Model_CustomInfo> itOrderHeaderFields = null;


    public List<Model_CustomInfo> getItOrderHeaderFields() {
        return itOrderHeaderFields;
    }

    public void setItOrderHeaderFields(List<Model_CustomInfo> itOrderHeaderFields) {
        this.itOrderHeaderFields = itOrderHeaderFields;
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
        return Aufnr;
    }

    public void setAufnr(String aufnr) {
        Aufnr = aufnr;
    }

    public String getAuart() {
        return Auart;
    }

    public void setAuart(String auart) {
        Auart = auart;
    }

    public String getKtext() {
        return Ktext;
    }

    public void setKtext(String ktext) {
        Ktext = ktext;
    }

    public String getIlart() {
        return Ilart;
    }

    public void setIlart(String ilart) {
        Ilart = ilart;
    }

    public String getErnam() {
        return Ernam;
    }

    public void setErnam(String ernam) {
        Ernam = ernam;
    }

    public String getErdat() {
        return Erdat;
    }

    public void setErdat(String erdat) {
        Erdat = erdat;
    }

    public String getPriok() {
        return Priok;
    }

    public void setPriok(String priok) {
        Priok = priok;
    }

    public String getEqunr() {
        return Equnr;
    }

    public void setEqunr(String equnr) {
        Equnr = equnr;
    }

    public String getStrno() {
        return Strno;
    }

    public void setStrno(String strno) {
        Strno = strno;
    }

    public String getTplnrInt() {
        return TplnrInt;
    }

    public void setTplnrInt(String tplnrInt) {
        TplnrInt = tplnrInt;
    }

    public String getBautl() {
        return Bautl;
    }

    public void setBautl(String bautl) {
        Bautl = bautl;
    }

    public String getGltrp() {
        return Gltrp;
    }

    public void setGltrp(String gltrp) {
        Gltrp = gltrp;
    }

    public String getGstrp() {
        return Gstrp;
    }

    public void setGstrp(String gstrp) {
        Gstrp = gstrp;
    }

    public String getMsaus() {
        return Msaus;
    }

    public void setMsaus(String msaus) {
        Msaus = msaus;
    }

    public String getAnlzu() {
        return Anlzu;
    }

    public void setAnlzu(String anlzu) {
        Anlzu = anlzu;
    }

    public String getAusvn() {
        return Ausvn;
    }

    public void setAusvn(String ausvn) {
        Ausvn = ausvn;
    }

    public String getAusbs() {
        return Ausbs;
    }

    public void setAusbs(String ausbs) {
        Ausbs = ausbs;
    }

    public String getQmnam() {
        return Qmnam;
    }

    public void setQmnam(String qmnam) {
        Qmnam = qmnam;
    }

    public String getAuswk() {
        return Auswk;
    }

    public void setAuswk(String auswk) {
        Auswk = auswk;
    }

    public String getParnrVw() {
        return ParnrVw;
    }

    public void setParnrVw(String parnrVw) {
        ParnrVw = parnrVw;
    }

    public String getNameVw() {
        return NameVw;
    }

    public void setNameVw(String nameVw) {
        NameVw = nameVw;
    }

    public String getDocs() {
        return Docs;
    }

    public void setDocs(String docs) {
        Docs = docs;
    }

    public String getPermits() {
        return Permits;
    }

    public void setPermits(String permits) {
        Permits = permits;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getQmnum() {
        return Qmnum;
    }

    public void setQmnum(String qmnum) {
        Qmnum = qmnum;
    }

    public String getQcreate() {
        return Qcreate;
    }

    public void setQcreate(String qcreate) {
        Qcreate = qcreate;
    }

    public String getClosed() {
        return Closed;
    }

    public void setClosed(String closed) {
        Closed = closed;
    }

    public String getCompleted() {
        return Completed;
    }

    public void setCompleted(String completed) {
        Completed = completed;
    }

    public String getWcm() {
        return Wcm;
    }

    public void setWcm(String wcm) {
        Wcm = wcm;
    }

    public String getWsm() {
        return Wsm;
    }

    public void setWsm(String wsm) {
        Wsm = wsm;
    }

    public String getIngrp() {
        return Ingrp;
    }

    public void setIngrp(String ingrp) {
        Ingrp = ingrp;
    }

    public String getArbpl() {
        return Arbpl;
    }

    public void setArbpl(String arbpl) {
        Arbpl = arbpl;
    }

    public String getWerks() {
        return Werks;
    }

    public void setWerks(String werks) {
        Werks = werks;
    }

    public String getBemot() {
        return Bemot;
    }

    public void setBemot(String bemot) {
        Bemot = bemot;
    }

    public String getAueru() {
        return Aueru;
    }

    public void setAueru(String aueru) {
        Aueru = aueru;
    }

    public String getAuarttext() {
        return Auarttext;
    }

    public void setAuarttext(String auarttext) {
        Auarttext = auarttext;
    }

    public String getQmartx() {
        return Qmartx;
    }

    public void setQmartx(String qmartx) {
        Qmartx = qmartx;
    }

    public String getQmtxt() {
        return Qmtxt;
    }

    public void setQmtxt(String qmtxt) {
        Qmtxt = qmtxt;
    }

    public String getPltxt() {
        return Pltxt;
    }

    public void setPltxt(String pltxt) {
        Pltxt = pltxt;
    }

    public String getEqktx() {
        return Eqktx;
    }

    public void setEqktx(String eqktx) {
        Eqktx = eqktx;
    }

    public String getPriokx() {
        return Priokx;
    }

    public void setPriokx(String priokx) {
        Priokx = priokx;
    }

    public String getIlatx() {
        return Ilatx;
    }

    public void setIlatx(String ilatx) {
        Ilatx = ilatx;
    }

    public String getPlantname() {
        return Plantname;
    }

    public void setPlantname(String plantname) {
        Plantname = plantname;
    }

    public String getWkctrname() {
        return Wkctrname;
    }

    public void setWkctrname(String wkctrname) {
        Wkctrname = wkctrname;
    }

    public String getIngrpname() {
        return Ingrpname;
    }

    public void setIngrpname(String ingrpname) {
        Ingrpname = ingrpname;
    }

    public String getMaktx() {
        return Maktx;
    }

    public void setMaktx(String maktx) {
        Maktx = maktx;
    }

    public String getAnlzux() {
        return Anlzux;
    }

    public void setAnlzux(String anlzux) {
        Anlzux = anlzux;
    }

    public String getXstatus() {
        return Xstatus;
    }

    public void setXstatus(String xstatus) {
        Xstatus = xstatus;
    }

    public String getKokrs() {
        return Kokrs;
    }

    public void setKokrs(String kokrs) {
        Kokrs = kokrs;
    }

    public String getKostl() {
        return Kostl;
    }

    public void setKostl(String kostl) {
        Kostl = kostl;
    }

    public String getUsr01() {
        return Usr01;
    }

    public void setUsr01(String usr01) {
        Usr01 = usr01;
    }

    public String getUsr02() {
        return Usr02;
    }

    public void setUsr02(String usr02) {
        Usr02 = usr02;
    }

    public String getUsr03() {
        return Usr03;
    }

    public void setUsr03(String usr03) {
        Usr03 = usr03;
    }

    public String getUsr04() {
        return Usr04;
    }

    public void setUsr04(String usr04) {
        Usr04 = usr04;
    }

    public String getUsr05() {
        return Usr05;
    }

    public void setUsr05(String usr05) {
        Usr05 = usr05;
    }
}

