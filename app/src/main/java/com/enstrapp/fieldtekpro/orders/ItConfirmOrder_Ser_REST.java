package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItConfirmOrder_Ser_REST implements Serializable
{
    @SerializedName("ORDERID")
    @Expose
    private String orderid;
    @SerializedName("OPERATION")
    @Expose
    private String operation;
    @SerializedName("CONFNO")
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
    @SerializedName("RSPOS")
    @Expose
    private String rspos;
    @SerializedName("POSNR")
    @Expose
    private String posnr;
    @SerializedName("MATNR")
    @Expose
    private String matnr;
    @SerializedName("BWART")
    @Expose
    private String bwart;
    @SerializedName("WERKS")
    @Expose
    private String werks;
    @SerializedName("LGORT")
    @Expose
    private String lgort;
    @SerializedName("ERFMG")
    @Expose
    private String erfmg;
    @SerializedName("ERFME")
    @Expose
    private String erfme;
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
    @SerializedName("WORK_CNTR")
    @Expose
    private  String WorkCntr;

    public String getWorkCntr() {
        return WorkCntr;
    }

    public void setWorkCntr(String workCntr) {
        WorkCntr = workCntr;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public String getRspos() {
        return rspos;
    }

    public void setRspos(String rspos) {
        this.rspos = rspos;
    }

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getBwart() {
        return bwart;
    }

    public void setBwart(String bwart) {
        this.bwart = bwart;
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

    public String getErfmg() {
        return erfmg;
    }

    public void setErfmg(String erfmg) {
        this.erfmg = erfmg;
    }

    public String getErfme() {
        return erfme;
    }

    public void setErfme(String erfme) {
        this.erfme = erfme;
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