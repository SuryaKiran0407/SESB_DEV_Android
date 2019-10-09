package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItConfirmOrderColl_Ser_REST implements Serializable
{
    @SerializedName("AUFNR")
    @Expose
    private String aufnr;
    @SerializedName("VORNR")
    @Expose
    private String vornr;
    @SerializedName("CONF_NO")
    @Expose
    private String confNo;
    @SerializedName("CONF_TEXT")
    @Expose
    private String confText;
    @SerializedName("ACT_WORK")
    @Expose
    private String actWork;
    @SerializedName("UN_WORK")
    @Expose
    private String unWork;
    @SerializedName("PLAN_WORK")
    @Expose
    private String planWork;
    @SerializedName("LEARR")
    @Expose
    private String learr;
    @SerializedName("BEMOT")
    @Expose
    private String bemot;
    @SerializedName("GRUND")
    @Expose
    private String grund;
    @SerializedName("LEKNW")
    @Expose
    private String leknw;
    @SerializedName("AUERU")
    @Expose
    private String aueru;
    @SerializedName("AUSOR")
    @Expose
    private String ausor;
    @SerializedName("PERNR")
    @Expose
    private String pernr;
    @SerializedName("LOART")
    @Expose
    private String loart;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("RSNUM")
    @Expose
    private String rsnum;
    @SerializedName("POSTGDATE")
    @Expose
    private String postgDate;
    @SerializedName("PLANT")
    @Expose
    private String plant;
    @SerializedName("WORK_CNTR")
    @Expose
    private String workCntr;
    @SerializedName("EXEC_START_DATE")
    @Expose
    private String execStartDate;
    @SerializedName("EXEC_START_TIME")
    @Expose
    private String execStartTime;
    @SerializedName("EXEC_FIN_DATE")
    @Expose
    private String execFinDate;
    @SerializedName("EXEC_FIN_TIME")
    @Expose
    private String execFinTime;

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

    public String getConfNo() {
        return confNo;
    }

    public void setConfNo(String confNo) {
        this.confNo = confNo;
    }

    public String getConfText() {
        return confText;
    }

    public void setConfText(String confText) {
        this.confText = confText;
    }

    public String getActWork() {
        return actWork;
    }

    public void setActWork(String actWork) {
        this.actWork = actWork;
    }

    public String getUnWork() {
        return unWork;
    }

    public void setUnWork(String unWork) {
        this.unWork = unWork;
    }

    public String getPlanWork() {
        return planWork;
    }

    public void setPlanWork(String planWork) {
        this.planWork = planWork;
    }

    public String getLearr() {
        return learr;
    }

    public void setLearr(String learr) {
        this.learr = learr;
    }

    public String getBemot() {
        return bemot;
    }

    public void setBemot(String bemot) {
        this.bemot = bemot;
    }

    public String getGrund() {
        return grund;
    }

    public void setGrund(String grund) {
        this.grund = grund;
    }

    public String getLeknw() {
        return leknw;
    }

    public void setLeknw(String leknw) {
        this.leknw = leknw;
    }

    public String getAueru() {
        return aueru;
    }

    public void setAueru(String aueru) {
        this.aueru = aueru;
    }

    public String getAusor() {
        return ausor;
    }

    public void setAusor(String ausor) {
        this.ausor = ausor;
    }

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getLoart() {
        return loart;
    }

    public void setLoart(String loart) {
        this.loart = loart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRsnum() {
        return rsnum;
    }

    public void setRsnum(String rsnum) {
        this.rsnum = rsnum;
    }

    public String getPostgDate() {
        return postgDate;
    }

    public void setPostgDate(String postgDate) {
        this.postgDate = postgDate;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getWorkCntr() {
        return workCntr;
    }

    public void setWorkCntr(String workCntr) {
        this.workCntr = workCntr;
    }

    public String getExecStartDate() {
        return execStartDate;
    }

    public void setExecStartDate(String execStartDate) {
        this.execStartDate = execStartDate;
    }

    public String getExecStartTime() {
        return execStartTime;
    }

    public void setExecStartTime(String execStartTime) {
        this.execStartTime = execStartTime;
    }

    public String getExecFinDate() {
        return execFinDate;
    }

    public void setExecFinDate(String execFinDate) {
        this.execFinDate = execFinDate;
    }

    public String getExecFinTime() {
        return execFinTime;
    }

    public void setExecFinTime(String execFinTime) {
        this.execFinTime = execFinTime;
    }

}
