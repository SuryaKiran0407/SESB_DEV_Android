package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItConfirmOrderColl_Ser implements Serializable
{
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Vornr")
    @Expose
    private String vornr;
    @SerializedName("ConfNo")
    @Expose
    private String confNo;
    @SerializedName("ConfText")
    @Expose
    private String confText;
    @SerializedName("ActWork")
    @Expose
    private String actWork;
    @SerializedName("UnWork")
    @Expose
    private String unWork;
    @SerializedName("PlanWork")
    @Expose
    private String planWork;
    @SerializedName("Learr")
    @Expose
    private String learr;
    @SerializedName("Bemot")
    @Expose
    private String bemot;
    @SerializedName("Grund")
    @Expose
    private String grund;
    @SerializedName("Leknw")
    @Expose
    private String leknw;
    @SerializedName("Aueru")
    @Expose
    private String aueru;
    @SerializedName("Ausor")
    @Expose
    private String ausor;
    @SerializedName("Pernr")
    @Expose
    private String pernr;
    @SerializedName("Loart")
    @Expose
    private String loart;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Rsnum")
    @Expose
    private String rsnum;
    @SerializedName("PostgDate")
    @Expose
    private String postgDate;
    @SerializedName("Plant")
    @Expose
    private String plant;
    @SerializedName("WorkCntr")
    @Expose
    private String workCntr;
    @SerializedName("ExecStartDate")
    @Expose
    private String execStartDate;
    @SerializedName("ExecStartTime")
    @Expose
    private String execStartTime;
    @SerializedName("ExecFinDate")
    @Expose
    private String execFinDate;
    @SerializedName("ExecFinTime")
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
