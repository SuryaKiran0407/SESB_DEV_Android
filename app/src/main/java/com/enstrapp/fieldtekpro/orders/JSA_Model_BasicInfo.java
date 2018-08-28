package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JSA_Model_BasicInfo
{
    @SerializedName("DbKey")
    @Expose
    private String dbKey;
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Rasid")
    @Expose
    private String rasid;
    @SerializedName("Rasstatus")
    @Expose
    private String rasstatus;
    @SerializedName("Rastype")
    @Expose
    private String rastype;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Job")
    @Expose
    private String job;
    @SerializedName("Opstatus")
    @Expose
    private String opstatus;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Statustxt")
    @Expose
    private String statustxt;
    @SerializedName("Rastypetxt")
    @Expose
    private String rastypetxt;
    @SerializedName("Opstatustxt")
    @Expose
    private String opstatustxt;
    @SerializedName("Locationtxt")
    @Expose
    private String locationtxt;
    @SerializedName("Jobtxt")
    @Expose
    private String jobtxt;
    @SerializedName("Action")
    @Expose
    private String action;

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

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

    public String getRasstatus() {
        return rasstatus;
    }

    public void setRasstatus(String rasstatus) {
        this.rasstatus = rasstatus;
    }

    public String getRastype() {
        return rastype;
    }

    public void setRastype(String rastype) {
        this.rastype = rastype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOpstatus() {
        return opstatus;
    }

    public void setOpstatus(String opstatus) {
        this.opstatus = opstatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatustxt() {
        return statustxt;
    }

    public void setStatustxt(String statustxt) {
        this.statustxt = statustxt;
    }

    public String getRastypetxt() {
        return rastypetxt;
    }

    public void setRastypetxt(String rastypetxt) {
        this.rastypetxt = rastypetxt;
    }

    public String getOpstatustxt() {
        return opstatustxt;
    }

    public void setOpstatustxt(String opstatustxt) {
        this.opstatustxt = opstatustxt;
    }

    public String getLocationtxt() {
        return locationtxt;
    }

    public void setLocationtxt(String locationtxt) {
        this.locationtxt = locationtxt;
    }

    public String getJobtxt() {
        return jobtxt;
    }

    public void setJobtxt(String jobtxt) {
        this.jobtxt = jobtxt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}