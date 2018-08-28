package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JSA_Model_ItJSAImp
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
    @SerializedName("RiskID")
    @Expose
    private String riskID;
    @SerializedName("Impact")
    @Expose
    private String impact;
    @SerializedName("Impacttxt")
    @Expose
    private String impacttxt;
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

    public String getRiskID() {
        return riskID;
    }

    public void setRiskID(String riskID) {
        this.riskID = riskID;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getImpacttxt() {
        return impacttxt;
    }

    public void setImpacttxt(String impacttxt) {
        this.impacttxt = impacttxt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}