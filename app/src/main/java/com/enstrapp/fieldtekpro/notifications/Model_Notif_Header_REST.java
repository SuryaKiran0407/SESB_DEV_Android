package com.enstrapp.fieldtekpro.notifications;

import com.enstrapp.fieldtekpro.CustomInfo.Model_CustomInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Model_Notif_Header_REST
{
    @SerializedName("NOTIF_TYPE")
    @Expose
    private String notifType;
    @SerializedName("QMNUM")
    @Expose
    private String qmnum;
    @SerializedName("NOTIF_SHORTTXT")
    @Expose
    private String notifShorttxt;
    @SerializedName("FUNCTION_LOC")
    @Expose
    private String functionLoc;
    @SerializedName("EQUIPMENT")
    @Expose
    private String equipment;
    @SerializedName("REPORTED_BY")
    @Expose
    private String reportedBy;
    @SerializedName("MALFUNC_STDATE")
    @Expose
    private String malfuncStdate;
    @SerializedName("MALFUNC_EDDATE")
    @Expose
    private String malfuncEddate;
    @SerializedName("MALFUNC_STTIME")
    @Expose
    private String malfuncSttime;
    @SerializedName("MALFUNC_EDTIME")
    @Expose
    private String malfuncEdtime;
    @SerializedName("BREAKDOWN_IND")
    @Expose
    private String breakdownInd;
    @SerializedName("PRIORITY")
    @Expose
    private String priority;
    @SerializedName("INGRP")
    @Expose
    private String ingrp;
    @SerializedName("ARBPL")
    @Expose
    private String arbpl;
    @SerializedName("WERKS")
    @Expose
    private String werks;
    @SerializedName("QMDAT")
    @Expose
    private String qmdat;
    @SerializedName("LTRMN")
    @Expose
    private String ltrmn;
    @SerializedName("STRMN")
    @Expose
    private String strmn;
    @SerializedName("STRUR")
    @Expose
    private String strur;
    @SerializedName("LTRUR")
    @Expose
    private String ltrur;
    @SerializedName("AUFNR")
    @Expose
    private String aufnr;
    @SerializedName("PARNR_VW")
    @Expose
    private String parnrVw;
    @SerializedName("NAME_VW")
    @Expose
    private String nameVw;
    @SerializedName("AUSWK")
    @Expose
    private String auswk;
    @SerializedName("SHIFT")
    @Expose
    private String shift;
    @SerializedName("NOOFPERSON")
    @Expose
    private Integer noofperson;
    @SerializedName("DOCS")
    @Expose
    private String docs;
    @SerializedName("ALTITUDE")
    @Expose
    private String altitude;
    @SerializedName("LATITUDE")
    @Expose
    private String latitude;
    @SerializedName("LONGITUDE")
    @Expose
    private String longitude;
    @SerializedName("CLOSED")
    @Expose
    private String closed;
    @SerializedName("COMPLETED")
    @Expose
    private String completed;
    @SerializedName("CREATEDON")
    @Expose
    private String createdon;
    @SerializedName("QMARTX")
    @Expose
    private String qmartx;
    @SerializedName("PLTXT")
    @Expose
    private String pltxt;
    @SerializedName("EQKTX")
    @Expose
    private String eqktx;
    @SerializedName("PRIOKX")
    @Expose
    private String priokx;
    @SerializedName("AUFTEXT")
    @Expose
    private String auftext;
    @SerializedName("AUARTTEXT")
    @Expose
    private String auarttext;
    @SerializedName("PLANTNAME")
    @Expose
    private String plantname;
    @SerializedName("WKCTRNAME")
    @Expose
    private String wkctrname;
    @SerializedName("INGRPNAME")
    @Expose
    private String ingrpname;
    @SerializedName("MAKTX")
    @Expose
    private String maktx;
    @SerializedName("AUSWKT")
    @Expose
    private String auswkt;
    @SerializedName("XSTATUS")
    @Expose
    private String xstatus;
    @SerializedName("USR01")
    @Expose
    private String usr01;
    @SerializedName("USR02")
    @Expose
    private String usr02;
    @SerializedName("USR03")
    @Expose
    private String usr03;
    @SerializedName("USR04")
    @Expose
    private String usr04;
    @SerializedName("USR05")
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