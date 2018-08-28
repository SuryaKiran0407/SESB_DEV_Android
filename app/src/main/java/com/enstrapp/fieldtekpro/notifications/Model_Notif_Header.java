package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Header
{
    @SerializedName("NotifType")
    @Expose
    private String notifType;
    @SerializedName("Qmnum")
    @Expose
    private String qmnum;
    @SerializedName("NotifShorttxt")
    @Expose
    private String notifShorttxt;
    @SerializedName("FunctionLoc")
    @Expose
    private String functionLoc;
    @SerializedName("Equipment")
    @Expose
    private String equipment;
    @SerializedName("ReportedBy")
    @Expose
    private String reportedBy;
    @SerializedName("MalfuncStdate")
    @Expose
    private String malfuncStdate;
    @SerializedName("MalfuncEddate")
    @Expose
    private String malfuncEddate;
    @SerializedName("MalfuncSttime")
    @Expose
    private String malfuncSttime;
    @SerializedName("MalfuncEdtime")
    @Expose
    private String malfuncEdtime;
    @SerializedName("BreakdownInd")
    @Expose
    private String breakdownInd;
    @SerializedName("Priority")
    @Expose
    private String priority;
    @SerializedName("Ingrp")
    @Expose
    private String ingrp;
    @SerializedName("Arbpl")
    @Expose
    private String arbpl;
    @SerializedName("Werks")
    @Expose
    private String werks;
    @SerializedName("Qmdat")
    @Expose
    private String qmdat;
    @SerializedName("Ltrmn")
    @Expose
    private String ltrmn;
    @SerializedName("Strmn")
    @Expose
    private String strmn;
    @SerializedName("Strur")
    @Expose
    private String strur;
    @SerializedName("Ltrur")
    @Expose
    private String ltrur;
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("ParnrVw")
    @Expose
    private String parnrVw;
    @SerializedName("NameVw")
    @Expose
    private String nameVw;
    @SerializedName("Auswk")
    @Expose
    private String auswk;
    @SerializedName("Shift")
    @Expose
    private String shift;
    @SerializedName("Noofperson")
    @Expose
    private Integer noofperson;
    @SerializedName("Docs")
    @Expose
    private String docs;
    @SerializedName("Altitude")
    @Expose
    private String altitude;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Closed")
    @Expose
    private String closed;
    @SerializedName("Completed")
    @Expose
    private String completed;
    @SerializedName("Createdon")
    @Expose
    private String createdon;
    @SerializedName("Qmartx")
    @Expose
    private String qmartx;
    @SerializedName("Pltxt")
    @Expose
    private String pltxt;
    @SerializedName("Eqktx")
    @Expose
    private String eqktx;
    @SerializedName("Priokx")
    @Expose
    private String priokx;
    @SerializedName("Auftext")
    @Expose
    private String auftext;
    @SerializedName("Auarttext")
    @Expose
    private String auarttext;
    @SerializedName("Plantname")
    @Expose
    private String plantname;
    @SerializedName("Wkctrname")
    @Expose
    private String wkctrname;
    @SerializedName("Ingrpname")
    @Expose
    private String ingrpname;
    @SerializedName("Maktx")
    @Expose
    private String maktx;
    @SerializedName("Auswkt")
    @Expose
    private String auswkt;
    @SerializedName("Xstatus")
    @Expose
    private String xstatus;
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
    @SerializedName("ItNotifHeaderFields")
    @Expose
    private List<Model_CustomInfo> itNotifHeaderFields = null;

    public List<Model_CustomInfo> getItNotifHeaderFields() {
        return itNotifHeaderFields;
    }

    public void setItNotifHeaderFields(List<Model_CustomInfo> itNotifHeaderFields) {
        this.itNotifHeaderFields = itNotifHeaderFields;
    }

    public String getStrmn() {
        return strmn;
    }

    public void setStrmn(String strmn) {
        this.strmn = strmn;
    }
    public String getNotifType() {
        return notifType;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    public String getQmnum() {
        return qmnum;
    }

    public void setQmnum(String qmnum) {
        this.qmnum = qmnum;
    }

    public String getNotifShorttxt() {
        return notifShorttxt;
    }

    public void setNotifShorttxt(String notifShorttxt) {
        this.notifShorttxt = notifShorttxt;
    }

    public String getFunctionLoc() {
        return functionLoc;
    }

    public void setFunctionLoc(String functionLoc) {
        this.functionLoc = functionLoc;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getMalfuncStdate() {
        return malfuncStdate;
    }

    public void setMalfuncStdate(String malfuncStdate) {
        this.malfuncStdate = malfuncStdate;
    }

    public String getMalfuncEddate() {
        return malfuncEddate;
    }

    public void setMalfuncEddate(String malfuncEddate) {
        this.malfuncEddate = malfuncEddate;
    }

    public String getMalfuncSttime() {
        return malfuncSttime;
    }

    public void setMalfuncSttime(String malfuncSttime) {
        this.malfuncSttime = malfuncSttime;
    }

    public String getMalfuncEdtime() {
        return malfuncEdtime;
    }

    public void setMalfuncEdtime(String malfuncEdtime) {
        this.malfuncEdtime = malfuncEdtime;
    }

    public String getBreakdownInd() {
        return breakdownInd;
    }

    public void setBreakdownInd(String breakdownInd) {
        this.breakdownInd = breakdownInd;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getQmdat() {
        return qmdat;
    }

    public void setQmdat(String qmdat) {
        this.qmdat = qmdat;
    }

    public String getLtrmn() {
        return ltrmn;
    }

    public void setLtrmn(String ltrmn) {
        this.ltrmn = ltrmn;
    }

    public String getStrur() {
        return strur;
    }

    public void setStrur(String strur) {
        this.strur = strur;
    }

    public String getLtrur() {
        return ltrur;
    }

    public void setLtrur(String ltrur) {
        this.ltrur = ltrur;
    }

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
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

    public String getAuswk() {
        return auswk;
    }

    public void setAuswk(String auswk) {
        this.auswk = auswk;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Integer getNoofperson() {
        return noofperson;
    }

    public void setNoofperson(Integer noofperson) {
        this.noofperson = noofperson;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
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

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getQmartx() {
        return qmartx;
    }

    public void setQmartx(String qmartx) {
        this.qmartx = qmartx;
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

    public String getAuftext() {
        return auftext;
    }

    public void setAuftext(String auftext) {
        this.auftext = auftext;
    }

    public String getAuarttext() {
        return auarttext;
    }

    public void setAuarttext(String auarttext) {
        this.auarttext = auarttext;
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

    public String getAuswkt() {
        return auswkt;
    }

    public void setAuswkt(String auswkt) {
        this.auswkt = auswkt;
    }

    public String getXstatus() {
        return xstatus;
    }

    public void setXstatus(String xstatus) {
        this.xstatus = xstatus;
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