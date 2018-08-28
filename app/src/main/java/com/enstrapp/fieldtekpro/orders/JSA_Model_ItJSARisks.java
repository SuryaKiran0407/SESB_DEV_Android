package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JSA_Model_ItJSARisks
{
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Rasid")
    @Expose
    private String rasid;
    @SerializedName("StepID")
    @Expose
    private String stepID;
    @SerializedName("StepSeq")
    @Expose
    private Integer stepSeq;
    @SerializedName("RiskID")
    @Expose
    private String riskID;
    @SerializedName("StepPers")
    @Expose
    private String stepPers;
    @SerializedName("Hazard")
    @Expose
    private String hazard;
    @SerializedName("RiskLevel")
    @Expose
    private String riskLevel;
    @SerializedName("RiskType")
    @Expose
    private String riskType;
    @SerializedName("Evaluation")
    @Expose
    private String evaluation;
    @SerializedName("Likelihood")
    @Expose
    private String likelihood;
    @SerializedName("Severity")
    @Expose
    private String severity;
    @SerializedName("HazCat")
    @Expose
    private String hazCat;
    @SerializedName("Hazardtxt")
    @Expose
    private String hazardtxt;
    @SerializedName("HazCattxt")
    @Expose
    private String hazCattxt;
    @SerializedName("StepTxt")
    @Expose
    private String stepTxt;
    @SerializedName("RiskLeveltxt")
    @Expose
    private String riskLeveltxt;
    @SerializedName("RiskTypetxt")
    @Expose
    private String riskTypetxt;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getAufnr() {
        return aufnr;
    }

    public void setAufnr(String aufnr) {
        this.aufnr = aufnr;
    }

    public String getRasid() {
        return rasid;
    }

    public void setRasid(String rasid) {
        this.rasid = rasid;
    }

    public String getStepID() {
        return stepID;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public Integer getStepSeq() {
        return stepSeq;
    }

    public void setStepSeq(Integer stepSeq) {
        this.stepSeq = stepSeq;
    }

    public String getRiskID() {
        return riskID;
    }

    public void setRiskID(String riskID) {
        this.riskID = riskID;
    }

    public String getStepPers() {
        return stepPers;
    }

    public void setStepPers(String stepPers) {
        this.stepPers = stepPers;
    }

    public String getHazard() {
        return hazard;
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(String likelihood) {
        this.likelihood = likelihood;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getHazCat() {
        return hazCat;
    }

    public void setHazCat(String hazCat) {
        this.hazCat = hazCat;
    }

    public String getHazardtxt() {
        return hazardtxt;
    }

    public void setHazardtxt(String hazardtxt) {
        this.hazardtxt = hazardtxt;
    }

    public String getHazCattxt() {
        return hazCattxt;
    }

    public void setHazCattxt(String hazCattxt) {
        this.hazCattxt = hazCattxt;
    }

    public String getStepTxt() {
        return stepTxt;
    }

    public void setStepTxt(String stepTxt) {
        this.stepTxt = stepTxt;
    }

    public String getRiskLeveltxt() {
        return riskLeveltxt;
    }

    public void setRiskLeveltxt(String riskLeveltxt) {
        this.riskLeveltxt = riskLeveltxt;
    }

    public String getRiskTypetxt() {
        return riskTypetxt;
    }

    public void setRiskTypetxt(String riskTypetxt) {
        this.riskTypetxt = riskTypetxt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}