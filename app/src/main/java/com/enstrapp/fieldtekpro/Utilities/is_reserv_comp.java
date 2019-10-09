package com.enstrapp.fieldtekpro.Utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class is_reserv_comp
{

    @SerializedName("BOM_COMPONENT")
    @Expose
    private String bOMCOMPONENT;
    @SerializedName("QUANTITY")
    @Expose
    private String qUANTITY;
    @SerializedName("UNIT")
    @Expose
    private String uNIT;
    @SerializedName("REQMT_DATE")
    @Expose
    private String rEQMTDATE;
    @SerializedName("STORE_LOC")
    @Expose
    private String sTORELOC;
    @SerializedName("BWTAR")
    @Expose
    private String Bwtar;
    @SerializedName("CHARG")
    @Expose
    private String Charg;
    @SerializedName("FIELDS")
    @Expose
    private String fIELDS;
    @SerializedName("UDID")
    @Expose
    private String uDID;

    public String getBwtar() {
        return Bwtar;
    }

    public void setBwtar(String bwtar) {
        Bwtar = bwtar;
    }

    public String getCharg() {
        return Charg;
    }

    public void setCharg(String charg) {
        Charg = charg;
    }

    public String getBOMCOMPONENT() {
        return bOMCOMPONENT;
    }

    public void setBOMCOMPONENT(String bOMCOMPONENT) {
        this.bOMCOMPONENT = bOMCOMPONENT;
    }

    public String getQUANTITY() {
        return qUANTITY;
    }

    public void setQUANTITY(String qUANTITY) {
        this.qUANTITY = qUANTITY;
    }

    public String getUNIT() {
        return uNIT;
    }

    public void setUNIT(String uNIT) {
        this.uNIT = uNIT;
    }

    public String getREQMTDATE() {
        return rEQMTDATE;
    }

    public void setREQMTDATE(String rEQMTDATE) {
        this.rEQMTDATE = rEQMTDATE;
    }

    public String getSTORELOC() {
        return sTORELOC;
    }

    public void setSTORELOC(String sTORELOC) {
        this.sTORELOC = sTORELOC;
    }

    public String getFIELDS() {
        return fIELDS;
    }

    public void setFIELDS(String fIELDS) {
        this.fIELDS = fIELDS;
    }

    public String getUDID() {
        return uDID;
    }

    public void setUDID(String uDID) {
        this.uDID = uDID;
    }

}


