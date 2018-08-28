package com.enstrapp.fieldtekpro.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JSA_Model_AssessmentTeam
{
    @SerializedName("Aufnr")
    @Expose
    private String aufnr;
    @SerializedName("Rasid")
    @Expose
    private String rasid;
    @SerializedName("PersonID")
    @Expose
    private String personID;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("Roletxt")
    @Expose
    private String roletxt;
    @SerializedName("Persontxt")
    @Expose
    private String persontxt;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoletxt() {
        return roletxt;
    }

    public void setRoletxt(String roletxt) {
        this.roletxt = roletxt;
    }

    public String getPersontxt() {
        return persontxt;
    }

    public void setPersontxt(String persontxt) {
        this.persontxt = persontxt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}